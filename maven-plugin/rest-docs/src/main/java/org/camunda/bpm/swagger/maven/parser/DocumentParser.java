package org.camunda.bpm.swagger.maven.parser;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.options.MutableDataSet;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class DocumentParser {

    private final Parser parser;

    public DocumentParser () {
        MutableDataSet options = new MutableDataSet();
        parser = Parser.builder(options).build();
    }

    @SneakyThrows
    public Map<String, Node> parse(String fileContents) {
        Node document = parser.parse(fileContents);
        ReversiblePeekingIterable<Node> children = document.getChildren();
        Stack<String> headingStack = new Stack<>();
        HashMap<String, Node> documentTree = new HashMap<>();
        for (Node next : children)
            resolveHeading(next)
                    .map((Heading heading) -> {
                        clearHeadingStack(headingStack, heading.getLevel());
                        headingStack.push(heading.getText().toString());
                        String key = headingStack.stream().reduce("", (acc, curr) -> acc.equals("") ? curr : acc + "." + curr);
                        documentTree.put(key, heading);
                        return key;
                    });
        return documentTree;
    }

    private void clearHeadingStack(Stack<String> headingStack, int lvl) {
        while (headingStack.size() >= lvl) {
            headingStack.pop();
        }
    }

    private Optional<Heading> resolveHeading(Node next) {
        if (!(next instanceof Heading))
            return Optional.empty();
        return Optional.of((Heading) next);
    }


}
