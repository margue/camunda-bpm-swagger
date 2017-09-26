package org.camunda.bpm.swagger.maven.spoon.processor;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo;
import org.camunda.bpm.swagger.maven.spoon.processor.step.MethodStep;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.util.Optional;

import static org.camunda.bpm.swagger.maven.spoon.processor.MethodPredicates.*;

public class RestResourceMethodProcessor extends AbstractProcessor<CtMethod<?>> {

  private final SpoonProcessingMojo.Context context;

  public RestResourceMethodProcessor(final SpoonProcessingMojo.Context context) {
    this.context = context;
  }

  @Override
  public boolean isToBeProcessed(final CtMethod<?> candidate) {
    return classIsNamedResourceImpl
      .and(isPublic)
      .and(classImplementsResourceInterface)
      .and(existsInInterface)
      .test(candidate);
  }

  @Override
  public void process(final CtMethod<?> ctMethod) {
    final MethodStep step = new MethodStep(this.context, getFactory());
    step.process(ctMethod);
  }

}
