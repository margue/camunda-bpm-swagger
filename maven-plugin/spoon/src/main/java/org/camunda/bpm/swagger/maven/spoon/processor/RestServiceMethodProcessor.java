package org.camunda.bpm.swagger.maven.spoon.processor;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.swagger.docs.model.ParameterDescription;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.docs.model.RestService;
import org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo;
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

@Slf4j
public class RestServiceMethodProcessor extends AbstractProcessor<CtMethod<?>> {

  private final SpoonProcessingMojo.Context context;

  public RestServiceMethodProcessor(final SpoonProcessingMojo.Context context) {
    this.context = context;
  }


  @Override
  public boolean isToBeProcessed(final CtMethod<?> candidate) {
    return classIsNamedRestServiceImpl
      .and(isPublic)
      .and(classImplementsRestInterface)
      .test(candidate);
  }

  @Override
  public void process(final CtMethod<?> ctMethod) {
    final String firstInterface = TypeHelper.getFirstInterfaceClassName((CtClass<?>) ctMethod.getParent());
    final String methodName = ctMethod.getSimpleName();
    if (firstInterface != null) {
      final RestService restService = context.getServiceDocumentation().get(firstInterface);
      if (restService != null) {

        final RestOperation restOperation = restService.getOperations().get(methodName );
        if (restOperation != null) {
          log.info("Annotating method {}#{}", firstInterface, ctMethod.getSimpleName());

          // api operation
          final String description = Optional.ofNullable(restOperation.getDescription()).orElse(WordUtils.capitalize(StringHelper.splitCamelCase(methodName)));
          final CtAnnotation<Annotation> apiOperation = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(ApiOperation.class));
          apiOperation.addValue("value", StringHelper.firstSentence(description));
          apiOperation.addValue("notes", description);
          ctMethod.addAnnotation(apiOperation);

          // api responses
          if (!restOperation.getResponseCodes().keySet().isEmpty()) {
            final CtAnnotation<ApiResponses> responsesAnnotation = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(ApiResponses.class));
            final CtNewArray<ApiResponse> responses = getFactory().Core().createNewArray();

            for (String code : restOperation.getResponseCodes().keySet()) {
              final CtAnnotation<ApiResponse> response = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(ApiResponse.class));
              response
                .addValue("code", Integer.valueOf(code))
                .addValue("message", restOperation.getResponseCodes().get(code).getDescription());
              responses.addElement(response);
            }

            responsesAnnotation.addValue("value", responses);
            ctMethod.addAnnotation(responsesAnnotation);
          }

          // external reference
          if (restOperation.getExternalDocUrl() != null) {
            final CtAnnotation<ExternalDocs> externalDocs = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(ExternalDocs.class));
            externalDocs.addValue("value", "Reference Guide");
            externalDocs.addValue("url", restOperation.getExternalDocUrl());
            ctMethod.addAnnotation(externalDocs);
          }

          final CtTypeReference<ApiImplicitParams> apiImplicitParamsTypeRef = getFactory().Code().createCtTypeReference(ApiImplicitParams.class);
          for (CtParameter parameter : ctMethod.getParameters()) {

            if (!ParameterRepository.isPresent(parameter.getType().getQualifiedName())) {
              // extract docs from query or from path parameter description.
              ParameterDescription parameterDescription = null;
              final Optional<? extends Class<?>> parameterAnnotation = TypeHelper.getParameterAnnotation(parameter, ctMethod);
              if (parameterAnnotation.isPresent() && parameterAnnotation.get().isAssignableFrom(QueryParam.class)) {
                parameterDescription = restOperation.getQueryParameters().get(parameter.getSimpleName());
              } else if (parameterAnnotation.isPresent() && parameterAnnotation.get().isAssignableFrom(PathParam.class)) {
                parameterDescription = restOperation.getPathParameters().get(parameter.getSimpleName());
              }
              // annotate parameter
              final String apiDocsParamName = Optional.ofNullable(parameterDescription).map(p -> p.getDescription()).orElse("Parameter " + parameter.getSimpleName());
              final CtAnnotation<ApiParam> paramAnnotation = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(ApiParam.class));
              paramAnnotation.addValue("value", apiDocsParamName);
              parameter.addAnnotation(paramAnnotation);
            } else if (ParameterRepository.generateImplicitParams(parameter.getType().getQualifiedName())) {
              // found an implicit parameter
              // retrieve annotation, if present or put one if not.
              CtAnnotation<ApiImplicitParams> implicitParamsAnnotation = ctMethod.getAnnotation(apiImplicitParamsTypeRef);
              final CtNewArray<ApiImplicitParam> params;
              if (implicitParamsAnnotation == null) {
                implicitParamsAnnotation = getFactory().Code().createAnnotation(apiImplicitParamsTypeRef);
                ctMethod.addAnnotation(implicitParamsAnnotation);
                params = getFactory().Core().createNewArray();
                implicitParamsAnnotation.addValue("value", params);
              } else {
                params = implicitParamsAnnotation.getValue("value");
              }


              for (String paramName : restOperation.getQueryParameters().keySet()) {
                final ParameterDescription parameterDescription = restOperation.getQueryParameters().get(paramName);
                final CtAnnotation<ApiImplicitParam> param = getFactory().Code().createAnnotation(getFactory().Code().createCtTypeReference(ApiImplicitParam.class));
                param
                  .addValue("name", paramName)
                  .addValue("value", parameterDescription.getDescription() == null ? "Parameter" + paramName : parameterDescription.getDescription())
                  .addValue("dataType", parameterDescription.getType() == null ? "string" : parameterDescription.getType())
                  .addValue("paramType", "query")
                  .addValue("required", parameterDescription.getRequired() == null ? false : parameterDescription.getRequired())
                ;
                params.addElement(param);
              }



            }

          }


        } else {
          log.error("No method documentation found for {}#{}", firstInterface, methodName );
        }
      } else {
        log.error("No service documentation found for {}", ((CtClass<?>) ctMethod.getParent()).getSimpleName());
      }
    }
  }

}
