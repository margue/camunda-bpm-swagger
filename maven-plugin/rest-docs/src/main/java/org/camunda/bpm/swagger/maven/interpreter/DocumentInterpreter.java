package org.camunda.bpm.swagger.maven.interpreter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

import org.apache.maven.plugin.logging.Log;
import org.camunda.bpm.swagger.maven.model.ParameterDescription;
import org.camunda.bpm.swagger.maven.model.RestOperation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vladsch.flexmark.ast.Code;
import com.vladsch.flexmark.ast.HtmlBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.ThematicBreak;

public class DocumentInterpreter {

  private static final String METHOD = "Method";
  private static final String RESULT = "Result";
  private static final String PARAMETERS__PATH_PARAMETERS = "Parameters.Path Parameters";
  private static final String PARAMETERS__QUERY_PARAMETERS = "Parameters.Query Parameters";
  private static final String PARAMETERS__REQUEST_BODY = "Parameters.Request Body";
  private static final String RESPONSE_CODES = "Response Codes";
  private final Log log;

  public DocumentInterpreter(final Log log) {
    this.log = log;
  }

  public RestOperation interpret(final Map<String, Node> parsedObject) {
    final RestOperation.RestOperationBuilder builder = RestOperation.builder();
    resolveMethod(parsedObject).ifPresent(builder::method);
    resolvePath(parsedObject).ifPresent(builder::path);
    resolveDescription(parsedObject).ifPresent(builder::description);
    resolveResultDescription(parsedObject).ifPresent(builder::resultDescription);
    resolveResult(parsedObject).ifPresent(builder::result);
    resolvePathParameters(parsedObject).ifPresent(builder::pathParameters);
    resolveQueryParameters(parsedObject).ifPresent(builder::queryParameters);
    resolveRequestBody(parsedObject).ifPresent(builder::requestBody);
    resolveResponseCodes(parsedObject).ifPresent(builder::responseCodes);
    return builder.build();
  }

  private Optional<Map<String, ParameterDescription>> resolveResult(final Map<String, Node> parsedObject) {
    final Stack<Class> classes = createPath(HtmlBlock.class);
    return Optional.ofNullable(parsedObject.get(RESULT))
        .map(node -> resolveNode(classes, node, HtmlBlock.class))
        .map(this::htmlNodeToMap);

  }

  private Optional<String> resolveResultDescription(final Map<String, Node> parsedObject) {
    final Stack<Class> classes = createPath(Paragraph.class);
    return Optional.ofNullable(parsedObject.get(RESULT))
        .map(node -> resolveNode(classes, node.getPreviousAny(ThematicBreak.class), Paragraph.class))
        .map(this::nodeToString);
  }


  private Optional<Map<String, ParameterDescription>> resolvePathParameters(final Map<String, Node> parsedObject) {
    final Stack<Class> classes = createPath(HtmlBlock.class);
    return Optional.ofNullable(parsedObject.get(PARAMETERS__PATH_PARAMETERS))
        .map(node -> resolveNode(classes, node, HtmlBlock.class))
        .map(this::htmlNodeToMap);
  }

  private Optional<Map<String, ParameterDescription>> resolveQueryParameters(final Map<String, Node> parsedObject) {
    final Stack<Class> classes = createPath(HtmlBlock.class);
    return Optional.ofNullable(parsedObject.get(PARAMETERS__QUERY_PARAMETERS))
        .map(node -> resolveNode(classes, node, HtmlBlock.class))
        .map(this::htmlNodeToMap);
  }

  private Optional<Map<String, ParameterDescription>> resolveRequestBody(final Map<String, Node> parsedObject) {
    final Stack<Class> classes = createPath(HtmlBlock.class);
    return Optional.ofNullable(parsedObject.get(PARAMETERS__REQUEST_BODY))
        .map(node -> resolveNode(classes, node, HtmlBlock.class))
        .map(this::htmlNodeToMap);
  }

  private Optional<Map<String, ParameterDescription>> resolveResponseCodes(final Map<String, Node> parsedObject) {
    final Stack<Class> classes = createPath(HtmlBlock.class);
    return Optional.ofNullable(parsedObject.get(RESPONSE_CODES))
        .map(node -> resolveNode(classes, node, HtmlBlock.class))
        .map(this::htmlNodeToMap);
  }

  private Map<String, ParameterDescription> htmlNodeToMap(final HtmlBlock htmlBlock) {
    final String htmlBlockBody = prepareHTML(htmlBlock);
    final org.jsoup.nodes.Document document = Jsoup.parseBodyFragment(htmlBlockBody);
    final Elements trs = document.select("tr");
    Integer nameIdx = null;
    Integer descriptionIdx = null;
    Integer typeIdx = null;
    Integer requiredIdx = null;
    final Elements ths = trs.get(0).select("th");
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
      final Element element = ths.get(i);
      switch(element.text()) {
      case "Name":
      case "Code":
      case "Form Part Name":
        nameIdx = i;
        break;
      case "Description":
        descriptionIdx = i;
        break;
      case "Media type":
      case "Type":
      case "Content Type":
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
    final HashMap<String, ParameterDescription> result = new HashMap<>();
    for (final Element tr : trs) {
      final Elements tds = tr.select("td");
      if(tds.size() > 2) {
        final ParameterDescription.ParameterDescriptionBuilder builder = ParameterDescription.builder();
        Optional.ofNullable(nameIdx).map(tds::get).map(Element::text).ifPresent(builder::id);
        Optional.ofNullable(descriptionIdx).map(tds::get).map(Element::text).ifPresent(builder::description);
        Optional.ofNullable(typeIdx).map(tds::get).map(Element::text).ifPresent(builder::type);
        Optional.ofNullable(requiredIdx).map(tds::get).map(Element::text).map(o -> o.equals("Yes")).ifPresent(builder::required);
        final ParameterDescription parameterDescription = builder.build();
        result.put(parameterDescription.getId(), parameterDescription);
      }

    }
    return result;
  }

  private String prepareHTML(final HtmlBlock htmlBlock) {
    return htmlBlock
        .getChars().toString()
        .replaceAll("\\{\\{[^}]+\\}\\}", "")
        .replaceAll("\\<\\/tr\\>[\n\t]+\\<tr\\>", "");
  }

  private Optional<String> resolveDescription(final Map<String, Node> parsedObject) {
    final Stack<Class> classes = createPath(Paragraph.class);
    return Optional.ofNullable(parsedObject.get(METHOD))
        .map(node -> resolveNode(classes, node.getPreviousAny(ThematicBreak.class), Paragraph.class))
        .map(this::nodeToString);
  }

  private Optional<String> resolveMethod(final Map<String, Node> parsedObject) {
    final Stack<Class> classes = createPath(Paragraph.class, Text.class);
    return Optional.ofNullable(parsedObject.get(METHOD))
        .map(node -> resolveNode(classes, node, Text.class))
        .map(Text::getChars)
        .map(Object::toString)
        .map(String::trim);
  }

  private Optional<String> resolvePath(final Map<String, Node> parsedObject) {
    final Stack<Class> classes = createPath(Paragraph.class, Code.class, Text.class);
    return Optional.ofNullable(parsedObject.get(METHOD))
        .map(node -> resolveNode(classes, node, Text.class))
        .map(Text::getChars)
        .map(Object::toString)
        .map(String::trim);
  }

  private <T> T resolveNode(final Stack<Class> classes, final Node node, final Class<T> _className) {
    if (node == null) {
      return null;
    }
    final Class type = classes.pop();
    return (T) resolveNode(classes, node.getNextAny(type));
  }

  private Node resolveNode(final Stack<Class> classes, final Node node) {
    if (node == null) {
      return null;
    }
    if (classes.isEmpty()) {
      return node;
    }
    final Class type = classes.pop();
    return resolveNode(classes, node.getFirstChildAny(type));
  }

  private Stack<Class> createPath(final Class... classes) {
    final Stack<Class> result = new Stack<>();
    result.addAll(Arrays.asList(classes));
    Collections.reverse(result);
    return result;
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
