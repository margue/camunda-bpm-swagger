package org.camunda.bpm.swagger.maven.interpreter;

import com.vladsch.flexmark.ast.HtmlBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Paragraph;
import org.apache.maven.plugin.logging.Log;
import org.camunda.bpm.swagger.maven.model.ParameterDescription;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

class DocumentParameterDescriptionInterpreter extends AbstractDocumentInterpreter {

  private static final String PARAMETERS__PATH_PARAMETERS = "Parameters.Path Parameters";
  private static final String PARAMETERS__QUERY_PARAMETERS = "Parameters.Query Parameters";
  private static final String PARAMETERS__REQUEST_BODY = "Parameters.Request Body";
  private static final String RESPONSE_CODES = "Response Codes";
  private static final String RESULT = "Result";

  private final Log log;

  DocumentParameterDescriptionInterpreter(final Log log) {
    this.log = log;
  }

  Optional<Map<String, ParameterDescription>> resolvePathParameters(final Map<String, Node> parsedObject) {
    return getParameterDescription(parsedObject, PARAMETERS__PATH_PARAMETERS);
  }

  Optional<Map<String, ParameterDescription>> resolveQueryParameters(final Map<String, Node> parsedObject) {
    return getParameterDescription(parsedObject, PARAMETERS__QUERY_PARAMETERS);
  }

  Optional<Map<String, ParameterDescription>> resolveRequestBody(final Map<String, Node> parsedObject) {
    return getParameterDescription(parsedObject, PARAMETERS__REQUEST_BODY);
  }

  Optional<Map<String, ParameterDescription>> resolveResponseCodes(final Map<String, Node> parsedObject) {
    return getParameterDescription(parsedObject, RESPONSE_CODES);
  }

  Optional<Map<String, ParameterDescription>> resolveResult(final Map<String, Node> parsedObject) {
    return getParameterDescription(parsedObject, RESULT);
  }

  private Optional<Map<String, ParameterDescription>> getParameterDescription(final Map<String, Node> parsedObject, String entryPoint) {
    final Stack<Class> classes = createPath(HtmlBlock.class);
    return Optional.ofNullable(parsedObject.get(entryPoint))
      .map(node -> resolveNode(classes, node, HtmlBlock.class))
      .map(this::htmlNodeToMap);
  }

  private Map<String, ParameterDescription> htmlNodeToMap(final HtmlBlock htmlBlock) {
    String htmlBlockBody = prepareHTML(htmlBlock);
    org.jsoup.nodes.Document document = Jsoup.parseBodyFragment(htmlBlockBody);
    Elements trs = document.select("tr");
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
    HashMap<String, ParameterDescription> result = new HashMap<>();
    for (Element tr : trs) {
      final Elements tds = tr.select("td");
      if(tds.size() > 2) {
        ParameterDescription.ParameterDescriptionBuilder builder = ParameterDescription.builder();
        Optional.ofNullable(nameIdx).map(tds::get).map(Element::text).ifPresent(builder::id);
        Optional.ofNullable(descriptionIdx).map(tds::get).map(Element::text).ifPresent(builder::description);
        Optional.ofNullable(typeIdx).map(tds::get).map(Element::text).ifPresent(builder::type);
        Optional.ofNullable(requiredIdx).map(tds::get).map(Element::text).map(o -> o.equals("Yes")).ifPresent(builder::required);
        ParameterDescription parameterDescription = builder.build();
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
}
