package org.camunda.bpm.swagger.maven.spoon.processor;

import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

import org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo;

import io.swagger.annotations.ApiModelProperty;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

public class ApiModelPropertyProcessor extends AbstractProcessor<CtMethod<?>> {

  Predicate<CtMethod<?>> startsWithGet = m -> m.getSimpleName().startsWith("get");
  Predicate<CtMethod<?>> isPublic = m -> m.getModifiers().contains(ModifierKind.PUBLIC);
  Predicate<CtMethod<?>> takesNoParameters = m -> m.getParameters().isEmpty();

  private final SpoonProcessingMojo.Context context;

  public ApiModelPropertyProcessor(final SpoonProcessingMojo.Context context) {
    this.context = context;
  }

  @Override
  public boolean isToBeProcessed(final CtMethod<?> candidate) {
    return startsWithGet
        .and(isPublic)
        .and(takesNoParameters)
        .test(candidate);
  }

  @Override
  public void process(final CtMethod<?> element) {
    final CtAnnotation<Annotation> annotation = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(ApiModelProperty.class));

    final String fieldName = uncapitalize(removeStart(element.getSimpleName(), "get"));

    // TODO: add mapping dto/RestOperation to context, access fields here and map to annotation values
    annotation.addValue("name", fieldName);

    element.addAnnotation(annotation);
  }


}
