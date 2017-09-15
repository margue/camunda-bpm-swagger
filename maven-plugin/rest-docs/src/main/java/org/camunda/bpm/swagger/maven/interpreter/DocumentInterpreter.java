package org.camunda.bpm.swagger.maven.interpreter;

import com.vladsch.flexmark.ast.*;
import org.apache.maven.plugin.logging.Log;
import org.camunda.bpm.swagger.maven.model.ParameterDescription;
import org.camunda.bpm.swagger.maven.model.RestOperation;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class DocumentInterpreter {

  private static final String DESCRIPTION = "#";
  private static final String METHOD = "Method";
  private static final String RESULT = "Result";
  private static final String EXAMPLE__RESPONSE = "Example.Response";
  private static final String EXAMPLE_1__RESPONSE = "Example 1.Response";
  private static final String EXAMPLE_2__RESPONSE = "Example 2.Response";

  private static final String PARAMETERS__PATH_PARAMETERS = "Parameters.Path Parameters";
  private static final String PARAMETERS__QUERY_PARAMETERS = "Parameters.Query Parameters";
  private static final String PARAMETERS__REQUEST_BODY = "Parameters.Request Body";
  private static final String RESPONSE_CODES = "Response Codes";

  private final Log log;
  private final HtmlDocumentInterpreter htmlDocumentInterpreter;

  public DocumentInterpreter(final Log log) {
    htmlDocumentInterpreter = new HtmlDocumentInterpreter(log);
    this.log = log;
  }

  public RestOperation interpret(final Map<String, Node> parsed) {
    final RestOperation.RestOperationBuilder builder = RestOperation.builder();
    resolveSubDocument(METHOD, parsed).map(this::resolvePath).ifPresent(builder::path);
    resolveSubDocument(METHOD, parsed).map(this::resolveMethod).ifPresent(builder::method);
    resolveSubDocument(DESCRIPTION, parsed).map(this::resolveDescription).ifPresent(builder::description);
    resolveSubDocument(RESULT, parsed).map(this::resolveText).ifPresent(builder::resultDescription);
    resolveExample(parsed).ifPresent(builder::responseExample);

    resolveParameter(PARAMETERS__REQUEST_BODY, builder::requestBody, parsed);
    resolveParameter(PARAMETERS__QUERY_PARAMETERS, builder::queryParameters, parsed);
    resolveParameter(PARAMETERS__PATH_PARAMETERS, builder::pathParameters, parsed);
    resolveParameter(RESULT, builder::result, parsed);
    resolveParameter(RESPONSE_CODES, builder::responseCodes, parsed);

    return builder.build();
  }

  private Optional<String> resolveExample(final Map<String, Node> parsed) {
    Optional<String> result = resolveSubDocument(EXAMPLE__RESPONSE, parsed).map(this::resolveResponse);
    if (!result.isPresent())
      result = resolveSubDocument(EXAMPLE_1__RESPONSE, parsed).map(this::resolveResponse);
    if (!result.isPresent())
      result = resolveSubDocument(EXAMPLE_2__RESPONSE, parsed).map(this::resolveResponse);
    return result;
  }

  private void resolveParameter(final String key, final Consumer<Map<String, ParameterDescription>> consumer, final Map<String, Node> parsed) {
    resolveSubDocument(key, parsed).map(this::resolveHtmlNode)
      .map(htmlDocumentInterpreter::getParameterDescription)
      .ifPresent(consumer);
  }

  private String resolveResponse(final Node node) {
    return getOpChildNode(node, FencedCodeBlock.class)
      .map(child -> getChildNode(child, Text.class))
      .map(child -> nodeToString(child, true))
      .orElse(null);
  }

  private String resolvePath(final Node node) {
    return getOpChildNode(node, Paragraph.class)
      .map(child -> getChildNode(child, Code.class))
      .map(childChild -> getChildNode(childChild, Text.class))
      .map(Text::getChars)
      .map(Object::toString)
      .map(String::trim)
      .orElse(null);
  }

  private String resolveMethod(final Node node) {
    return getOpChildNode(node, Paragraph.class)
      .map(child -> getChildNode(child, Text.class))
      .map(Text::getChars)
      .map(Object::toString)
      .map(String::trim)
      .orElse(null);
  }

  private String resolveDescription(final Node node) {
    final Node thematicBreak = node.getLastChildAny(ThematicBreak.class);
    final Paragraph paragraph = new Paragraph();
    Node next = thematicBreak.getNext();
    while (next != null) {
      paragraph.appendChild(next);
      next = thematicBreak.getNext();
    }
    return nodeToString(paragraph, false);
  }

  private String resolveText(final Node node) {
    return getOpChildNode(node, Paragraph.class)
      .map(childNode -> nodeToString(childNode, true))
      .orElse(null);
  }

  private <T> T getChildNode(final Node node, final Class<T> clazz) {
    return getOpChildNode(node, clazz).orElse(null);
  }

  private <T> Optional<T> getOpChildNode(final Node node, final Class<T> clazz) {
    return Optional.ofNullable((T) node.getFirstChildAny(clazz));
  }

  private HtmlBlock resolveHtmlNode(final Node node) {
    return getOpChildNode(node, HtmlBlock.class).orElse(null);
  }

  private Optional<Node> resolveSubDocument(final String key, final Map<String, Node> parsedObject) {
    return Optional.ofNullable(parsedObject.get(key));
  }

  private String nodeToString(final Node node, final Boolean ignoreHtmlBlocks) {
    final StringBuffer sb = new StringBuffer();
    nodeToString(node, sb, ignoreHtmlBlocks);
    return sb.toString().trim();
  }

  private void nodeToString(final Node node, final StringBuffer sb, final Boolean ignoreHtmlBlocks) {
    if (node != null) {
      switch (node.getClass().getSimpleName()) {
      case "Text":
        sb.append(node.getChars());
        break;
      case "Code":
        sb.append("`");
        nodeToString(node.getFirstChildAny(Text.class), sb, ignoreHtmlBlocks);
        sb.append("`");
        break;
      case "Paragraph":
        for (final Node childNode : node.getChildren()) {
          nodeToString(childNode, sb, ignoreHtmlBlocks);
        }
        break;
      case "SoftLineBreak":
        sb.append(" ");
        break;
      case "HtmlBlock":
        if (!ignoreHtmlBlocks) {
          sb.append(htmlDocumentInterpreter.getText((HtmlBlock) node));
          sb.append("\n");
        }
        break;
      case "LinkRef":
      case "Link":
        nodeToString(node.getFirstChildAny(Text.class), sb, ignoreHtmlBlocks);
        break;
      case "BulletList":
        sb.append(node.getChars().toString());
        break;
      case "StrongEmphasis":
      case "Emphasis":
        nodeToString(node.getFirstChildAny(Text.class), sb, ignoreHtmlBlocks);
        break;
      case "HtmlInline":
        if(node.getChars().toString().equals("<br/>") || node.getChars().toString().equals("</br>")) {
          sb.append("\n");
        } else {
          log.debug("unknown htmlInline element: (" + node.getChars().toString() + ")");

        }
        break;
      default:
        log.info("class " + node.getClass().getSimpleName() + " not known: (" + node.getChars().toString() + ")");
      }
    }
  }

  private Node printTree(final int indentionLevel, final Node node) {
    final String indention = String.join("", Collections.nCopies(indentionLevel, "\t"));
    log.info(indention + node.toString());
    node.getChildren().forEach(childNode -> printTree(indentionLevel + 1, childNode));
    return node;
  }
}
