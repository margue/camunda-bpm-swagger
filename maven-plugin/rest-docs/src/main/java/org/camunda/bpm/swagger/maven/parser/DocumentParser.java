package org.camunda.bpm.swagger.maven.parser;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.options.MutableDataSet;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;

public class DocumentParser {

  private final Parser parser;

  public DocumentParser() {
    final MutableDataSet options = new MutableDataSet();
    parser = Parser.builder(options).build();
  }

  @SneakyThrows
  public HashMap<String, Node> parse(final String fileContents) {
    final Node document = parser.parse(fileContents);
    final ReversiblePeekingIterable<Node> children = document.getChildren();
    final Stack<String> headingStack = new Stack<>();
    final HashMap<String, Node> documentTree = new HashMap<>();
    Paragraph subDocument = new Paragraph();
    documentTree.put("#", subDocument);
    for (final Node next : children) {
      final Optional<Paragraph> newSubDocument = resolveHeading(next)
        .map((Heading heading) -> {
          pushHeading(headingStack, heading);
          final String headingTitle = getHeadingTitle(headingStack);
          final Paragraph subDoc = new Paragraph();
          documentTree.put(headingTitle, subDoc);
          return subDoc;
        });
      if (newSubDocument.isPresent()) {
        subDocument = newSubDocument.get();
      }
      else {
        subDocument.appendChild(next);
      }
    }
    return documentTree;
  }

  private String getHeadingTitle(final Stack<String> headingStack) {
    return headingStack.stream().reduce("", (acc, curr) -> acc.equals("") ? curr : acc + "." + curr);
  }

  private void pushHeading(final Stack<String> headingStack, final Heading heading) {
    clearHeadingStack(headingStack, heading.getLevel());
    headingStack.push(heading.getText().toString());
  }

  private void clearHeadingStack(final Stack<String> headingStack, final int lvl) {
    while (headingStack.size() >= lvl) {
      headingStack.pop();
    }
  }

  private Optional<Heading> resolveHeading(final Node next) {
    if (!(next instanceof Heading))
      return Optional.empty();
    return Optional.of((Heading) next);
  }
}
