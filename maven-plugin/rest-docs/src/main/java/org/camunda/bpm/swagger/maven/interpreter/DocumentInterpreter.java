package org.camunda.bpm.swagger.maven.interpreter;

import com.vladsch.flexmark.ast.Code;
import com.vladsch.flexmark.ast.HtmlBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.ThematicBreak;
import org.apache.maven.plugin.logging.Log;
import org.camunda.bpm.swagger.maven.model.ParameterDescription;
import org.camunda.bpm.swagger.maven.model.RestOperation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class DocumentInterpreter {

  private static final String METHOD = "Method";
  private static final String RESULT = "Result";
  private static final String PARAMETERS__PATH_PARAMETERS = "Parameters.Path Parameters";
  private static final String PARAMETERS__QUERY_PARAMETERS = "Parameters.Query Parameters";
  private static final String RESPONSE_CODES = "Response Codes";
  private final Log log;

  public DocumentInterpreter(Log log) {
    this.log = log;
  }

  public RestOperation interpret(Map<String, Node> parsedObject) {
    RestOperation.RestOperationBuilder builder = RestOperation.builder();
    resolveMethod(parsedObject).ifPresent(builder::method);
    resolvePath(parsedObject).ifPresent(builder::path);
    resolveDescription(parsedObject).ifPresent(builder::description);
    resolveResultDescription(parsedObject).ifPresent(builder::resultDescription);
    resolvePathParameters(parsedObject).ifPresent(builder::pathParameters);
    resolveResponseCodes(parsedObject).ifPresent(builder::responseCodes);
    resolveQueryParameters(parsedObject).ifPresent(builder::queryParameters);
    return builder.build();
  }

  private Optional<String> resolveResultDescription(Map<String, Node> parsedObject) {
    Stack<Class> classes = createPath(Paragraph.class);
    return Optional.ofNullable(parsedObject.get(RESULT))
      .map(node -> resolveNode(classes, node.getPreviousAny(ThematicBreak.class), Paragraph.class))
      .map(this::nodeToString);
  }

  private Optional<Map<String, ParameterDescription>> resolvePathParameters(Map<String, Node> parsedObject) {
    Stack<Class> classes = createPath(HtmlBlock.class);
    return Optional.ofNullable(parsedObject.get(PARAMETERS__PATH_PARAMETERS))
      .map(node -> resolveNode(classes, node, HtmlBlock.class))
      .map(this::htmlNodeToMap);
  }

  private Optional<Map<String, ParameterDescription>> resolveQueryParameters(Map<String, Node> parsedObject) {
    Stack<Class> classes = createPath(HtmlBlock.class);
    return Optional.ofNullable(parsedObject.get(PARAMETERS__QUERY_PARAMETERS))
      .map(node -> resolveNode(classes, node, HtmlBlock.class))
      .map(this::htmlNodeToMap);
  }

  private Optional<Map<String, ParameterDescription>> resolveResponseCodes(Map<String, Node> parsedObject) {
    Stack<Class> classes = createPath(HtmlBlock.class);
    return Optional.ofNullable(parsedObject.get(RESPONSE_CODES))
      .map(node -> resolveNode(classes, node, HtmlBlock.class))
      .map(this::htmlNodeToMap);
  }

  private Map<String, ParameterDescription> htmlNodeToMap(HtmlBlock htmlBlock) {
    String htmlBlockBody = prepareHTML(htmlBlock);
    org.jsoup.nodes.Document document = Jsoup.parseBodyFragment(htmlBlockBody);
    Elements trs = document.select("tr");
    Integer nameIdx = null;
    Integer descriptionIdx = null;
    Integer typeIdx = null;
    Integer requiredIdx = null;
    Elements ths = trs.get(0).select("th");
    if(ths.size() == 0) {
      // Workaround for missing table header
      nameIdx = 0;
      switch(trs.get(0).select("td").size()) {
        case 2:
          descriptionIdx = 1;
          break;
        case 3:
          typeIdx = 1;
          descriptionIdx = 2;
          break;
      }
    }
    for (int i = 0; i < ths.size(); i++) {
      Element element = ths.get(i);
      switch(element.text()) {
        case "Name":
        case "Code":
          nameIdx = i;
          break;
        case "Description":
          descriptionIdx = i;
          break;
        case "Media type":
        case "Type":
        case "Value":
          typeIdx = i;
          break;
        case "Required?":
          requiredIdx = i;
          break;
        default:
          log.debug("Fieldname unknown: " + element.text());
          break;
      }
    }
    HashMap<String, ParameterDescription> result = new HashMap<>();
    for (Element tr : trs) {
      Elements tds = tr.select("td");
      if(tds.size() == 0)
        break;
      ParameterDescription.ParameterDescriptionBuilder builder = ParameterDescription.builder();
      Optional.ofNullable(nameIdx).map(tds::get).map(Element::text).ifPresent(builder::id);
      Optional.ofNullable(descriptionIdx).map(tds::get).map(Element::text).ifPresent(builder::description);
      Optional.ofNullable(typeIdx).map(tds::get).map(Element::text).ifPresent(builder::type);
      Optional.ofNullable(requiredIdx).map(tds::get).map(Element::text).map(o -> o.equals("Yes")).ifPresent(builder::required);
        ParameterDescription parameterDescription = builder.build();
        result.put(parameterDescription.getId(), parameterDescription);

    }
    return result;
  }

  private String prepareHTML(HtmlBlock htmlBlock) {
    return htmlBlock
      .getChars().toString()
      .replaceAll("\\{\\{[^}]+\\}\\}", "")
      .replaceAll("\\<\\/tr\\>[\n\t]+\\<tr\\>", "");
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

  private <T> T resolveNode(Stack<Class> classes, Node node, Class<T> _className) {
    if (node == null)
      return null;
    Class type = classes.pop();
    return (T) resolveNode(classes, node.getNextAny(type));
  }

  private Node resolveNode(Stack<Class> classes, Node node) {
    if (node == null)
      return null;
    if (classes.isEmpty())
      return node;
    Class type = classes.pop();
    return resolveNode(classes, node.getFirstChildAny(type));
  }

  private Stack<Class> createPath(Class... classes) {
    Stack<Class> result = new Stack<>();
    result.addAll(Arrays.asList(classes));
    Collections.reverse(result);
    return result;
  }

  private String nodeToString(Node node) {
    StringBuffer sb = new StringBuffer();
    nodeToString(node, sb);
    return sb.toString().trim();
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
