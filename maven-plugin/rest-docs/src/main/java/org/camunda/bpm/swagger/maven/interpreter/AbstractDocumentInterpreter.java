package org.camunda.bpm.swagger.maven.interpreter;

import com.vladsch.flexmark.ast.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class AbstractDocumentInterpreter {
  <T> T resolveNode(Stack<Class> classes, Node node, Class<T> _className) {
    if (node == null)
      return null;
    Class type = classes.pop();
    return (T) resolveNode(classes, node.getNextAny(type));
  }

  Node resolveNode(Stack<Class> classes, Node node) {
    if (node == null)
      return null;
    if (classes.isEmpty())
      return node;
    Class type = classes.pop();
    return resolveNode(classes, node.getFirstChildAny(type));
  }

  Stack<Class> createPath(Class... classes) {
    Stack<Class> result = new Stack<>();
    result.addAll(Arrays.asList(classes));
    Collections.reverse(result);
    return result;
  }
}
