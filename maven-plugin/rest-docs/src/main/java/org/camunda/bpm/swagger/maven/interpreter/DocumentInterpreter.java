package org.camunda.bpm.swagger.maven.interpreter;

import com.vladsch.flexmark.ast.Code;
import com.vladsch.flexmark.ast.HtmlBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.ThematicBreak;
import org.apache.maven.plugin.logging.Log;
import org.camunda.bpm.swagger.maven.model.RestOperation;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class DocumentInterpreter extends AbstractDocumentInterpreter {

  private static final String METHOD = "Method";
  private static final String RESULT = "Result";
  private final Log log;
  private final DocumentParameterDescriptionInterpreter parameterDescriptionInterpreter;

  public DocumentInterpreter(Log log) {
    parameterDescriptionInterpreter = new DocumentParameterDescriptionInterpreter(log);
    this.log = log;
  }

  public RestOperation interpret(Map<String, Node> parsedObject) {
    RestOperation.RestOperationBuilder builder = RestOperation.builder();
    resolveMethod(parsedObject).ifPresent(builder::method);
    resolvePath(parsedObject).ifPresent(builder::path);
    resolveDescription(parsedObject).ifPresent(builder::description);
    resolveResultDescription(parsedObject).ifPresent(builder::resultDescription);
    parameterDescriptionInterpreter.resolvePathParameters(parsedObject).ifPresent(builder::pathParameters);
    parameterDescriptionInterpreter.resolveQueryParameters(parsedObject).ifPresent(builder::queryParameters);
    parameterDescriptionInterpreter.resolveRequestBody(parsedObject).ifPresent(builder::requestBody);
    parameterDescriptionInterpreter.resolveResponseCodes(parsedObject).ifPresent(builder::responseCodes);
    return builder.build();
  }

  private Optional<String> resolveResultDescription(Map<String, Node> parsedObject) {
    Stack<Class> classes = createPath(Paragraph.class);
    return Optional.ofNullable(parsedObject.get(RESULT))
      .map(node -> resolveNode(classes, node.getPreviousAny(ThematicBreak.class), Paragraph.class))
      .map(this::nodeToString);
  }

  private Optional<String> resolveDescription(Map<String, Node> parsedObject) {
    Stack<Class> classes = createPath(Paragraph.class);
    return Optional.ofNullable(parsedObject.get(METHOD))
      .map(node -> resolveNode(classes, node.getPreviousAny(ThematicBreak.class), Paragraph.class))
      .map(this::nodeToString);
  }

  private Optional<String> resolveMethod(Map<String, Node> parsedObject) {
    Stack<Class> classes = createPath(Paragraph.class, Text.class);
    return Optional.ofNullable(parsedObject.get(METHOD))
      .map(node -> resolveNode(classes, node, Text.class))
      .map(Text::getChars)
      .map(Object::toString)
      .map(String::trim);
  }

  private Optional<String> resolvePath(Map<String, Node> parsedObject) {
    Stack<Class> classes = createPath(Paragraph.class, Code.class, Text.class);
    return Optional.ofNullable(parsedObject.get(METHOD))
      .map(node -> resolveNode(classes, node, Text.class))
      .map(Text::getChars)
      .map(Object::toString)
      .map(String::trim);
  }

  private String nodeToString(Node node) {
    StringBuffer sb = new StringBuffer();
    nodeToString(node, sb);
    String trim = sb.toString().trim();
    log.info(trim);
    return trim;
  }

  private boolean nodeToString(Node node, StringBuffer sb) {
    if (node != null) {
      switch (node.getClass().getSimpleName()) {
        case "Text":
          sb.append(node.getChars());
          break;
        case "Code":
          sb.append("`");
          nodeToString(node.getFirstChildAny(Text.class), sb);
          sb.append("`");
          break;
        case "Paragraph":
          boolean ignoreNext = false;
          for (Node childNode : node.getChildren()) {
            ignoreNext = !ignoreNext && nodeToString(childNode, sb);
          }
          break;
        case "SoftLineBreak":
          sb.append("\n");
          break;
        case "LinkRef":
          nodeToString(node.getFirstChildAny(Text.class), sb);
          return true;
      case "StrongEmphasis":
      case "Emphasis":
        nodeToString(node.getFirstChildAny(Text.class), sb);
        break;
      case "HtmlInline":
        if(node.getChars().toString().equals("<br/>") || node.getChars().toString().equals("</br>")) {
          sb.append("\n");
        } else {
          log.debug("unknown htmlInline element: (" + node.getChars().toString() +")");

        }
        break;
      default:
          log.debug("class " + node.getClass().getSimpleName() + " not known: (" + node.getChars().toString() +")");
      }
    }
    return false;
  }

  private Node printTree(int indentionLevel, Node node) {
    String indention = String.join("", Collections.nCopies(indentionLevel, "\t"));
    log.info(indention + node.toString());
    node.getChildren().forEach(childNode -> printTree(indentionLevel + 1, childNode));
    return node;
  }
}
