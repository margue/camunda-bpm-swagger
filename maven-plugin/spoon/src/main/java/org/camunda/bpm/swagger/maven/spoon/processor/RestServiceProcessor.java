package org.camunda.bpm.swagger.maven.spoon.processor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.swagger.docs.model.RestService;
import org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtTypeReference;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static org.camunda.bpm.swagger.maven.spoon.processor.ClassPredicates.*;

/**
 * Processes REST service implementations.
 * Adds API annotations to the implementing class.
 */
@Slf4j
public class RestServiceProcessor extends AbstractProcessor<CtClass<?>> {

  private final SpoonProcessingMojo.Context context;
  public RestServiceProcessor(final SpoonProcessingMojo.Context context) {
    this.context = context;
  }

  @Override
  public boolean isToBeProcessed(final CtClass<?> candidate) {
    return isNamedRestService
      .and(isNotAbstract)
      .and(implementsRestInterface)
      .test(candidate);
  }


  @Override
  public void process(final CtClass<?> impl) {

    final CtAnnotation<Annotation> annotation = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(Api.class));

    final String classFqn = TypeHelper.getFirstInterfaceClassName(impl);
    final RestService restService = context.getServiceDocumentation().get(classFqn);

    if (restService != null) {
      annotation.addValue("value", restService.getDescription());
      annotation.addValue("tags", restService.getTags());
      impl.addAnnotation(annotation);

      log.debug("Added @Api to {} [{}]", impl.getQualifiedName(), classFqn);
    } else {
      log.error("No documentation found for {} [{}]", impl.getQualifiedName(), classFqn);
    }
  }

}
