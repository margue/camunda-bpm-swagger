package org.camunda.bpm.swagger.maven.spoon.processor;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.swagger.docs.model.ParameterDescription;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.docs.model.RestService;
import org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo;
import org.camunda.bpm.swagger.maven.spoon.processor.step.MethodStep;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Predicate;

import static org.camunda.bpm.swagger.maven.spoon.processor.MethodPredicates.*;

public class RestServiceMethodProcessor extends AbstractProcessor<CtMethod<?>> {

  private final SpoonProcessingMojo.Context context;

  public RestServiceMethodProcessor(final SpoonProcessingMojo.Context context) {
    this.context = context;
  }

  @Override
  public boolean isToBeProcessed(final CtMethod<?> candidate) {
    return classIsNamedRestService
      .test(candidate);
  }

  @Override
  public void process(final CtMethod<?> ctMethod) {
    final MethodStep step = new MethodStep(this.context, getFactory());
    step.process(ctMethod);
  }

}
