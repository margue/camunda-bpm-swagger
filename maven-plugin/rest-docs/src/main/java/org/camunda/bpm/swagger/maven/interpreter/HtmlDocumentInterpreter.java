package org.camunda.bpm.swagger.maven.interpreter;

import com.vladsch.flexmark.ast.HtmlBlock;
import org.apache.maven.plugin.logging.Log;
import org.camunda.bpm.swagger.maven.model.ParameterDescription;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class HtmlDocumentInterpreter {

  private final Log log;

  HtmlDocumentInterpreter(final Log log) {
    this.log = log;
  }

  Map<String, ParameterDescription> getParameterDescription(final HtmlBlock node) {
    return htmlNodeToMap(node);
  }

  String getText(final HtmlBlock node) {
    final Document document = Jsoup.parseBodyFragment(node.getChars().toString());
    return document.text();
  }

  private Map<String, ParameterDescription> htmlNodeToMap(final HtmlBlock htmlBlock) {
    final String htmlBlockBody = prepareHTML(htmlBlock);
    final Document document = Jsoup.parseBodyFragment(htmlBlockBody);
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
}
