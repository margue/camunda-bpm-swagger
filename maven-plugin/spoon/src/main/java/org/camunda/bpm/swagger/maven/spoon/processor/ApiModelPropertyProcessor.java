package org.camunda.bpm.swagger.maven.spoon.processor;

import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.camunda.bpm.swagger.maven.spoon.processor.MethodPredicates.*;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.camunda.bpm.swagger.docs.model.ParameterDescription;
import org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtMethod;

@Slf4j
public class ApiModelPropertyProcessor extends AbstractProcessor<CtMethod<?>> {


  private final SpoonProcessingMojo.Context context;

  public ApiModelPropertyProcessor(final SpoonProcessingMojo.Context context) {
    this.context = context;
  }

  @Override
  public boolean isToBeProcessed(final CtMethod<?> candidate) {
    return classIsNamedDto.and(startsWithGet).and(isPublic).and(takesNoParameters).test(candidate);
  }

  @Override
  public void process(final CtMethod<?> method) {
    final CtAnnotation<Annotation> annotation = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(ApiModelProperty.class));

    final String fieldName = uncapitalize(removeStart(method.getSimpleName(), "get"));
    final String classFqn = TypeHelper.getClassname(method);

    final Map<String, ParameterDescription> docs = context.getDtoDocumentation().get(classFqn);
    if (docs != null) {
      final ParameterDescription parameterDescription = docs.get(fieldName);
      if (parameterDescription != null) {
        log.debug("Found parameter description for {} {}", classFqn, fieldName);
        annotation.addValue("value", parameterDescription.getDescription());
        if (parameterDescription.getRequired() != null) {
          annotation.addValue("required", parameterDescription.getRequired().booleanValue());
        }
      }
    }

    annotation.addValue("name", fieldName);

    method.addAnnotation(annotation);
  }

}
