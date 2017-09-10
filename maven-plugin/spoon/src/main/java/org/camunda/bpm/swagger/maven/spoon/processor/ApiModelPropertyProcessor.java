package org.camunda.bpm.swagger.maven.spoon.processor;

import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

@Slf4j
public class ApiModelPropertyProcessor extends AbstractProcessor<CtMethod<?>> {

  Predicate<CtMethod<?>> startsWithGet = m -> m.getSimpleName().startsWith("get");
  Predicate<CtMethod<?>> isPublic = m -> m.getModifiers().contains(ModifierKind.PUBLIC);
  Predicate<CtMethod<?>> takesNoParameters = m -> m.getParameters().isEmpty();

  private final SpoonProcessingMojo.Context context;

  public ApiModelPropertyProcessor(SpoonProcessingMojo.Context context) {
    this.context = context;
  }

  @Override
  public boolean isToBeProcessed(CtMethod<?> candidate) {
    return startsWithGet
      .and(isPublic)
      .and(takesNoParameters)
      .test(candidate);
  }

  @Override
  public void process(CtMethod<?> element) {
    final CtAnnotation<Annotation> annotation = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(ApiModelProperty.class));

    String fieldName = uncapitalize(removeStart(element.getSimpleName(), "get"));

    // TODO: add mapping dto/RestOperation to context, access fields here and map to annotation values
    annotation.addValue("name", fieldName);

    element.addAnnotation(annotation);
  }


}
