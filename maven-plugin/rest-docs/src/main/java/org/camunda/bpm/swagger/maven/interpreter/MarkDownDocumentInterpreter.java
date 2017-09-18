package org.camunda.bpm.swagger.maven.interpreter;

import com.vladsch.flexmark.ast.HtmlBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import org.apache.maven.plugin.logging.Log;

import java.util.Collections;
import java.util.Optional;

public class MarkDownDocumentInterpreter {
  private final Log log;

  private final HtmlDocumentInterpreter htmlInterpreter;

  MarkDownDocumentInterpreter(final Log log, final HtmlDocumentInterpreter htmlInterpreter) {
    this.log = log;
    this.htmlInterpreter = htmlInterpreter;
  }

  String resolveText(final Node node) {
    return getOpChildNode(node, Paragraph.class)
      .map(childNode -> nodeToString(childNode, true))
      .orElse(null);
  }

  <T> T getChildNode(final Node node, final Class<T> clazz) {
    return getOpChildNode(node, clazz).orElse(null);
  }

  <T> Optional<T> getOpChildNode(final Node node, final Class<T> clazz) {
    return Optional.ofNullable((T) node.getFirstChildAny(clazz));
  }

  HtmlBlock resolveHtmlNode(final Node node) {
    return getOpChildNode(node, HtmlBlock.class).orElse(null);
  }

  String nodeToString(final Node node, final Boolean ignoreHtmlBlocks) {
    final StringBuffer sb = new StringBuffer();
    nodeToString(node, sb, ignoreHtmlBlocks);
    return sb.toString().trim();
  }

  void nodeToString(final Node node, final StringBuffer sb, final Boolean ignoreHtmlBlocks) {
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
          sb.append(htmlInterpreter.getText((HtmlBlock) node));
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
        if (node.getChars().toString().equals("<br/>") || node.getChars().toString().equals("</br>")) {
          sb.append("\n");
        }
        else {
          log.debug("unknown htmlInline element: (" + node.getChars().toString() + ")");

        }
        break;
      default:
        log.debug("class " + node.getClass().getSimpleName() + " not known: (" + node.getChars().toString() + ")");
      }
    }
  }

  Node printTree(final int indentionLevel, final Node node) {
    final String indention = String.join("", Collections.nCopies(indentionLevel, "\t"));
    log.info(indention + node.toString());
    node.getChildren().forEach(childNode -> printTree(indentionLevel + 1, childNode));
    return node;
  }
}
