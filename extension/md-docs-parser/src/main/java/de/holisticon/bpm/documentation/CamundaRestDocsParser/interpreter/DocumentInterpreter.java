package de.holisticon.bpm.documentation.CamundaRestDocsParser.interpreter;

import com.vladsch.flexmark.ast.*;
import de.holisticon.bpm.documentation.CamundaRestDocsParser.api.ParameterDescription;
import de.holisticon.bpm.documentation.CamundaRestDocsParser.api.RestOperation;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DocumentInterpreter {

    private static final String METHOD = "Method";
    private static final String RESULT = "Result";
    private static final String PARAMETERS__PATH_PARAMETERS = "Parameters.Path Parameters";
    private static final String PARAMETERS__QUERY_PARAMETERS = "Parameters.Query Parameters";
    private static final String RESPONSE_CODES = "Response Codes";

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
        String htmlBlockBody = htmlBlock
                .getChars().toString()
                .replaceAll("\\{\\{[^}]+\\}\\}", "")
                .replaceAll("\\<\\/tr\\>[\n\t]+\\<tr\\>", "");
        org.jsoup.nodes.Document document = Jsoup.parseBodyFragment(htmlBlockBody);
        Elements trs = document.select("tr");
        HashMap<String, ParameterDescription> result = new HashMap<>();
        trs.stream().forEach((Element tr) -> {
            Elements td = tr.select("td");
            if (td.size() >= 2) {
                ParameterDescription.ParameterDescriptionBuilder builder = ParameterDescription.builder();
                builder.id(td.get(0).text());
                if(td.size() > 2) {
                    builder.description(td.get(1).text());
                    builder.description(td.get(2).text());
                } else {
                    builder.description(td.get(1).text());
                }
                result.put(td.get(0).text(), builder.build());
            }
        });
        return result;
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
        if(node != null) {
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
                default:
                    log.info("class " + node.getClass().getSimpleName() + " not known");
            }
        }
            return false;
    }

    private Node printTree(int indentionLevel, Node node) {
        String indention = String.join("", Collections.nCopies(indentionLevel, "\t"));
        log.info(indention + node.toString());
        node.getChildren().forEach(childNode -> printTree(indentionLevel+1, childNode));
        return node;
    }
}
