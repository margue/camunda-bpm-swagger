package org.camunda.bpm.swagger.maven.spoon.processor;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.swagger.docs.model.RestService;
import org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtInterface;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;

import static org.camunda.bpm.swagger.maven.spoon.processor.ClassPredicates.isNamedResource;
import static org.camunda.bpm.swagger.maven.spoon.processor.ClassPredicates.isNamedRestService;

/**
 * Processes REST service implementations.
 * Adds API annotations to the implementing class.
 */
@Slf4j
public class RestResourceProcessor extends AbstractProcessor<CtInterface<?>> {

  private final SpoonProcessingMojo.Context context;
  public RestResourceProcessor(final SpoonProcessingMojo.Context context) {
    this.context = context;
  }

  @Override
  public boolean isToBeProcessed(final CtInterface<?> candidate) {
    return isNamedResource
      .test(candidate);
  }

  @Override
  public void process(final CtInterface<?> resourceInterface) {
    final CtAnnotation<Annotation> annotation = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(Api.class));
    final RestService restService = context.getServiceDocumentation().get(resourceInterface.getQualifiedName());
    if (restService != null) {
      String[] tags;
      if (restService.getTags().contains(",")) {
        tags = restService.getTags().split(",");
      } else {
        tags = new String[]{ restService.getTags() };
      }

    annotation
      // .addValue("tags", tags)
      .addValue("hidden", true);
    resourceInterface.addAnnotation(annotation);
  } else {
      log.error("No documentation for resource {} found.", resourceInterface.getQualifiedName());
    }
  }

}
