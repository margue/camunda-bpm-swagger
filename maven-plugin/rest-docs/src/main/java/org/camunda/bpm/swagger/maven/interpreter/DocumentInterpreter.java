package org.camunda.bpm.swagger.maven.interpreter;

import com.vladsch.flexmark.ast.*;
import org.apache.maven.plugin.logging.Log;
import org.camunda.bpm.swagger.maven.model.ParameterDescription;
import org.camunda.bpm.swagger.maven.model.RestOperation;

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
  private final HtmlDocumentInterpreter htmlInterpreter;
  private final MarkDownDocumentInterpreter mdInterpreter;

  public DocumentInterpreter(final Log log) {
    htmlInterpreter = new HtmlDocumentInterpreter(log);
    mdInterpreter = new MarkDownDocumentInterpreter(log, htmlInterpreter);
    this.log = log;
  }

  public RestOperation interpret(final Map<String, Node> parsed, final String camundaDocURI) {
    final RestOperation.RestOperationBuilder builder = RestOperation.builder();
    Optional.of(camundaDocURI).ifPresent(builder::externalDocUrl);

    resolveSubDocument(METHOD, parsed).map(this::resolvePath).ifPresent(builder::path);
    resolveSubDocument(METHOD, parsed).map(this::resolveMethod).ifPresent(builder::method);
    resolveSubDocument(DESCRIPTION, parsed).map(this::resolveDescription).ifPresent(builder::description);
    resolveSubDocument(RESULT, parsed).map(mdInterpreter::resolveText).ifPresent(builder::resultDescription);
    resolveExample(parsed).ifPresent(builder::responseExample);

    resolveParameter(PARAMETERS__REQUEST_BODY, builder::requestBody, parsed);
    resolveParameter(PARAMETERS__QUERY_PARAMETERS, builder::queryParameters, parsed);
    resolveParameter(PARAMETERS__PATH_PARAMETERS, builder::pathParameters, parsed);
    resolveParameter(RESULT, builder::result, parsed);
    resolveParameter(RESPONSE_CODES, builder::responseCodes, parsed);

    return builder.build();
  }

  private Optional<Node> resolveSubDocument(final String key, final Map<String, Node> parsedObject) {
    return Optional.ofNullable(parsedObject.get(key));
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
    resolveSubDocument(key, parsed)
      .map(mdInterpreter::resolveHtmlNode)
      .map(htmlInterpreter::getParameterDescription)
      .ifPresent(consumer);
  }

  private String resolveResponse(final Node node) {
    return mdInterpreter.getOpChildNode(node, FencedCodeBlock.class)
      .map(child -> mdInterpreter.getChildNode(child, Text.class))
      .map(child -> mdInterpreter.nodeToString(child, true))
      .orElse(null);
  }

  private String resolvePath(final Node node) {
    return mdInterpreter.getOpChildNode(node, Paragraph.class)
      .map(child -> mdInterpreter.getChildNode(child, Code.class))
      .map(child -> mdInterpreter.getChildNode(child, Text.class))
      .map(Text::getChars)
      .map(Object::toString)
      .map(String::trim)
      .orElse(null);
  }

  private String resolveMethod(final Node node) {
    return mdInterpreter.getOpChildNode(node, Paragraph.class)
      .map(child -> mdInterpreter.getChildNode(child, Text.class))
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
    return mdInterpreter.nodeToString(paragraph, false);
  }
}
