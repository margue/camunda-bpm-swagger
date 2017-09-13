package org.camunda.bpm.swagger.maven.interpreter;

import com.vladsch.flexmark.ast.*;
import org.apache.maven.plugin.logging.Log;
import org.camunda.bpm.swagger.maven.model.ParameterDescription;
import org.camunda.bpm.swagger.maven.model.RestOperation;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Consumer;

public class DocumentInterpreter extends AbstractDocumentInterpreter {

  private static final String DESCRIPTION = "#";
  private static final String METHOD = "Method";
  private static final String RESULT = "Result";

  private static final String PARAMETERS__PATH_PARAMETERS = "Parameters.Path Parameters";
  private static final String PARAMETERS__QUERY_PARAMETERS = "Parameters.Query Parameters";
  private static final String PARAMETERS__REQUEST_BODY = "Parameters.Request Body";
  private static final String RESPONSE_CODES = "Response Codes";

  private final Log log;
  private final DocumentParameterDescriptionInterpreter parameterDescriptionInterpreter;

  public DocumentInterpreter(final Log log) {
    parameterDescriptionInterpreter = new DocumentParameterDescriptionInterpreter(log);
    this.log = log;
  }

  public RestOperation interpret(final Map<String, Node> parsed) {
    final RestOperation.RestOperationBuilder builder = RestOperation.builder();
    resolveSubDocument(METHOD, parsed).map(this::resolvePath).ifPresent(builder::path);
    resolveSubDocument(METHOD, parsed).map(this::resolveMethod).ifPresent(builder::method);
    resolveSubDocument(DESCRIPTION, parsed).map(this::resolveDescription).ifPresent(builder::description);
    resolveSubDocument(RESULT, parsed).map(this::resolveText).ifPresent(builder::resultDescription);

    resolveParameter(PARAMETERS__REQUEST_BODY, builder::requestBody, parsed);
    resolveParameter(PARAMETERS__QUERY_PARAMETERS, builder::queryParameters, parsed);
    resolveParameter(PARAMETERS__PATH_PARAMETERS, builder::pathParameters, parsed);
    resolveParameter(RESULT, builder::result, parsed);
    resolveParameter(RESPONSE_CODES, builder::responseCodes, parsed);

    final RestOperation build = builder.build();
    log.info(build.toString());
    return build;
  }

  private void resolveParameter(final String key, final Consumer<Map<String, ParameterDescription>> consumer, final Map<String, Node> parsed) {
    resolveSubDocument(key, parsed).map(this::resolveHtmlNode)
      .map(parameterDescriptionInterpreter::getParameterDescription)
      .ifPresent(consumer);
  }

  private String resolvePath(final Node node) {
    final Stack<Class> classes = createPath(Paragraph.class, Code.class, Text.class);
    return Optional.ofNullable(resolveNode(classes, node, Text.class))
      .map(Text::getChars)
      .map(Object::toString)
      .map(String::trim)
      .orElse(null);
  }

  private String resolveMethod(final Node node) {
    final Stack<Class> classes = createPath(Paragraph.class, Text.class);
    return Optional.ofNullable(resolveNode(classes, node, Text.class))
      .map(Text::getChars)
      .map(Object::toString)
      .map(String::trim)
      .orElse(null);
  }

  private String resolveDescription(final Node node) {
    return this.resolveText(node.getLastChildAny(ThematicBreak.class));
  }

  private String resolveText(final Node node) {
    final Stack<Class> classes = createPath(Paragraph.class);
    return Optional.ofNullable(resolveNode(classes, node, Paragraph.class))
      .map(this::nodeToString)
      .orElse(null);
  }

  private HtmlBlock resolveHtmlNode(final Node node) {
    final Stack<Class> classes = createPath(HtmlBlock.class);
    return Optional.ofNullable(resolveNode(classes, node, HtmlBlock.class)).orElse(null);
  }

  private Optional<Node> resolveSubDocument(final String key, final Map<String, Node> parsedObject) {
    return Optional.ofNullable(parsedObject.get(key));
  }

  private String nodeToString(final Node node) {
    final StringBuffer sb = new StringBuffer();
    nodeToString(node, sb);
    return sb.toString().trim();
  }

  private boolean nodeToString(final Node node, final StringBuffer sb) {
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
        for (final Node childNode : node.getChildren()) {
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

  private Node printTree(final int indentionLevel, final Node node) {
    final String indention = String.join("", Collections.nCopies(indentionLevel, "\t"));
    log.info(indention + node.toString());
    node.getChildren().forEach(childNode -> printTree(indentionLevel + 1, childNode));
    return node;
  }
}
