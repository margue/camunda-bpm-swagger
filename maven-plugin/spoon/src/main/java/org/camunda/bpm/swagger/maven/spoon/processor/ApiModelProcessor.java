package org.camunda.bpm.swagger.maven.spoon.processor;

import com.google.inject.Inject;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.logging.Log;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

/**
 * Processes all classes that end with "Dto" and annotates them with {@link ApiModel}.
 */
@Slf4j
public class ApiModelProcessor extends AbstractProcessor<CtClass<?>> {

  Predicate<CtClass<?>> isNamedDto = c -> c.getSimpleName().endsWith("Dto");
  Predicate<CtClass<?>> isNotAbstract = c -> !c.getModifiers().contains(ModifierKind.ABSTRACT);

  @Override
  public boolean isToBeProcessed(final CtClass<?> candidate) {
    return isNamedDto
      .and(isNotAbstract)
      .test(candidate);
  }

  @Override
  public void process(final CtClass<?> element) {
    final CtAnnotation<Annotation> annotation = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(ApiModel.class));

    element.addAnnotation(annotation);

    log.debug("add ApiModel to {}", element.getQualifiedName());
  }

}
