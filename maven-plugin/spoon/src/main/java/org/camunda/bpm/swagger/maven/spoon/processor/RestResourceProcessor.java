package org.camunda.bpm.swagger.maven.spoon.processor;

import lombok.extern.slf4j.Slf4j;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtVisitor;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.chain.CtConsumableFunction;
import spoon.reflect.visitor.chain.CtFunction;
import spoon.reflect.visitor.chain.CtQuery;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Predicate;

@Slf4j
public class RestResourceProcessor extends AbstractProcessor<CtMethod<?>> {

  Predicate<CtMethod<?>> classIsNamedResource = m -> getClassname(m).endsWith("Resource");
  Predicate<CtMethod<?>> isPathAnnotated = m -> m.getAnnotation(Path.class) != null;

  @Override
  public boolean isToBeProcessed(final CtMethod<?> candidate) {
    return classIsNamedResource.and(isPathAnnotated).test(candidate);
  }

  @Override
  public void process(final CtMethod<?> ctMethod) {

    log.debug("Processing {}#{}", getClassname(ctMethod), ctMethod.getSimpleName());
    final CtTypeReference<Path> pathRef = getFactory().createCtTypeReference(Path.class);
    final Optional<CtAnnotation<? extends Annotation>> ann = ctMethod.getAnnotations().stream().filter(a -> a.getAnnotationType().isSubtypeOf(pathRef)).findFirst();

    if (ann.isPresent()) {
      ctMethod.removeAnnotation(ann.get());
    }
  }

  private static String getClassname(final CtMethod<?> method) {
    return Optional.of(method).map(m -> m.getDeclaringType()).map(CtType::getQualifiedName).orElse("");
  }
}
