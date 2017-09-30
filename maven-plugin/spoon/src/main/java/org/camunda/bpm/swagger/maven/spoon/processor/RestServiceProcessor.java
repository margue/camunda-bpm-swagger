package org.camunda.bpm.swagger.maven.spoon.processor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.swagger.docs.model.RestService;
import org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtTypeReference;

import javax.ws.rs.Path;
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
public class RestServiceProcessor extends AbstractProcessor<CtInterface<?>> {

  private final SpoonProcessingMojo.Context context;
  public RestServiceProcessor(final SpoonProcessingMojo.Context context) {
    this.context = context;
  }

  @Override
  public boolean isToBeProcessed(final CtInterface<?> candidate) {
    return isNamedRestService
      .test(candidate);
  }

  @Override
  public void process(final CtInterface<?> restServiceInterface) {

    final CtAnnotation<Annotation> annotation = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(Api.class));
    final String classFqn = restServiceInterface.getQualifiedName();
    final RestService restService = context.getServiceDocumentation().get(classFqn);

    if (restService != null) {
      annotation.addValue("tags", restService.getTags());
      restServiceInterface.addAnnotation(annotation);

      // Add path annotation if missing
      final Path pathAnnotation = restServiceInterface.getAnnotation(Path.class);
      if (pathAnnotation == null) {
        final CtAnnotation<Path> path = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(Path.class));
        path.addValue("value", restService.getPath());
        restServiceInterface.addAnnotation(path);
      }

      log.debug("Added @Api to {} [{}]", restServiceInterface.getQualifiedName(), classFqn);
    } else {
      log.error("No documentation found for {} [{}]", restServiceInterface.getQualifiedName(), classFqn);
    }
  }

}
