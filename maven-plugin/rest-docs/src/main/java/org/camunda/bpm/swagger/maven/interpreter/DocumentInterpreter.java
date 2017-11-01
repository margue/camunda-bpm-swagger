package org.camunda.bpm.swagger.maven.interpreter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.apache.maven.plugin.logging.Log;
import org.camunda.bpm.swagger.maven.model.ParameterDescription;
import org.camunda.bpm.swagger.maven.model.RestOperation;
import com.google.common.collect.Lists;
import com.vladsch.flexmark.ast.*;

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

  public List<RestOperation> interpret(final Map<String, Node> parsed, final String camundaDocURI) {
    final RestOperation.RestOperationBuilder builder = RestOperation.builder();
    Optional.of(camundaDocURI).ifPresent(builder::externalDocUrl);
    resolveSubDocument(DESCRIPTION, parsed).map(this::resolveDescription).ifPresent(builder::description);
    resolveSubDocument(RESULT, parsed).map(mdInterpreter::resolveText).ifPresent(builder::resultDescription);
    resolveExample(parsed).ifPresent(builder::responseExample);

    resolveParameter(PARAMETERS__REQUEST_BODY, builder::requestBody, parsed);
    resolveParameter(PARAMETERS__QUERY_PARAMETERS, builder::queryParameters, parsed);
    resolveParameter(PARAMETERS__PATH_PARAMETERS, builder::pathParameters, parsed);
    resolveParameter(RESULT, builder::result, parsed);
    resolveParameter(RESPONSE_CODES, builder::responseCodes, parsed);
    final RestOperation build = builder.build();

    return resolveSubDocument(METHOD, parsed)
      .map(node -> this.resolveMethods(node, build))
      .orElse(Collections.emptyList());
  }

  private List<RestOperation> resolveMethods(final Node node, final RestOperation restOperation) {
    final List<RestOperation> results = Lists.newArrayList();
    node.getChildren().forEach(child -> {
      if (child.getClass().equals(Paragraph.class)) {
        final Optional<String> method = resolveMethod(child);
        final Optional<String> path = resolvePath(child);
        if (method.isPresent() && path.isPresent()) {
          results.add(restOperation.toBuilder().path(path.get()).method(method.get()).build());
        }
      }
    });
    return results;
  }

  private Optional<String> resolvePath(final Node child) {
    return Optional.of(child)
      .map(n -> mdInterpreter.getChildNode(n, Code.class))
      .map(n -> mdInterpreter.getChildNode(n, Text.class))
      .map(Text::getChars)
      .map(Object::toString)
      .map(String::trim);
  }

  private Optional<String> resolveMethod(final Node child) {
    return Optional.of(child)
      .map(n -> mdInterpreter.getChildNode(n, Text.class))
      .map(Text::getChars)
      .map(Object::toString)
      .map(String::trim);
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
