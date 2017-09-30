package org.camunda.bpm.swagger.maven.spoon.processor.step;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;
import org.camunda.bpm.swagger.docs.model.ParameterDescription;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.docs.model.RestService;
import org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo;
import org.camunda.bpm.swagger.maven.spoon.processor.ParameterRepository;
import org.camunda.bpm.swagger.maven.spoon.processor.StringHelper;
import org.camunda.bpm.swagger.maven.spoon.processor.TypeHelper;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.declaration.CtClassImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.lang.annotation.Annotation;
import java.util.Optional;

@Slf4j
public class MethodStep {

  private final SpoonProcessingMojo.Context context;
  private final CodeFactory codeFactory;
  private final CoreFactory core;

  public MethodStep(final SpoonProcessingMojo.Context context, final Factory factory) {
    this.context = context;
    this.codeFactory = factory.Code();
    this.core = factory.Core();
  }

  public void process(final CtMethod<?> ctMethod) {

    final String className = ((CtType<?>)ctMethod.getParent()).getQualifiedName();
    final String methodName = ctMethod.getSimpleName();
    final RestService restService = context.getServiceDocumentation().get(className);

    // path annotation is missing, add default one.
    if (ctMethod.getAnnotation(Path.class) == null) {
      final CtAnnotation<Path> pathAnnotation = codeFactory.createAnnotation(codeFactory.createCtTypeReference(Path.class));
      pathAnnotation.addValue("value", "/");
      ctMethod.addAnnotation(pathAnnotation);
    }

    // no JAX-RS annotation found, put a GET on it
    final Class<? extends Annotation> jaxrsAnnotationClass = TypeHelper.javaxRsAnnotation(ctMethod);
    if (jaxrsAnnotationClass == null) {
      final CtAnnotation<?> jaxRsAnnotation = codeFactory.createAnnotation(codeFactory.createCtTypeReference(GET.class));
      ctMethod.addAnnotation(jaxRsAnnotation);
    }


    final String description;

    if (restService != null) {
      final RestOperation restOperation = restService.getOperations().get(methodName);
      if (restOperation != null) {

        // Api Operation
        description = Optional.ofNullable(restOperation.getDescription()).orElse(WordUtils.capitalize(StringHelper.splitCamelCase(methodName)));

        // Api responses
        if (!restOperation.getResponseCodes().keySet().isEmpty()) {
          final CtAnnotation<ApiResponses> responsesAnnotation = codeFactory.createAnnotation(codeFactory.createCtTypeReference(ApiResponses.class));
          final CtNewArray<ApiResponse> responses = core.createNewArray();

          for (String code : restOperation.getResponseCodes().keySet()) {
            final CtAnnotation<ApiResponse> response = codeFactory.createAnnotation(codeFactory.createCtTypeReference(ApiResponse.class));
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
          final CtAnnotation<ExternalDocs> externalDocs = codeFactory.createAnnotation(codeFactory.createCtTypeReference(ExternalDocs.class));
          externalDocs.addValue("value", "Reference Guide");
          externalDocs.addValue("url", restOperation.getExternalDocUrl());
          ctMethod.addAnnotation(externalDocs);
        }

        // Parameters (implicit or explicit)
        final CtTypeReference<ApiImplicitParams> apiImplicitParamsTypeRef = codeFactory.createCtTypeReference(ApiImplicitParams.class);
        for (CtParameter parameter : ctMethod.getParameters()) {

          if (!ParameterRepository.isPresent(parameter.getType().getQualifiedName())) {
            // extract docs from query or from path parameter description.
            ParameterDescription parameterDescription = null;
            final Optional<? extends Class<?>> parameterAnnotation = TypeHelper.getParameterAnnotation(parameter, ctMethod);
            if (parameterAnnotation.isPresent() && parameterAnnotation.get().isAssignableFrom(QueryParam.class)) {
              parameterDescription = restOperation.getQueryParameters().get(parameter.getSimpleName());
              // final CtAnnotation<QueryParam> queryParamAnnotation = codeFactory.createAnnotation(codeFactory.createCtTypeReference(QueryParam.class));
              // queryParamAnnotation.addValue("value", parameter.getSimpleName());
              // parameter.addAnnotation(queryParamAnnotation);
            } else if (parameterAnnotation.isPresent() && parameterAnnotation.get().isAssignableFrom(PathParam.class)) {
              parameterDescription = restOperation.getPathParameters().get(parameter.getSimpleName());
              // final CtAnnotation<PathParam> pathParamAnnotation = codeFactory.createAnnotation(codeFactory.createCtTypeReference(PathParam.class));
              // pathParamAnnotation.addValue("value", parameter.getSimpleName());
              // parameter.addAnnotation(pathParamAnnotation);
            }
            // annotate parameter
            final String apiDocsParamName = Optional.ofNullable(parameterDescription).map(p -> p.getDescription()).orElse("Parameter " + parameter.getSimpleName());
            final CtAnnotation<ApiParam> paramAnnotation = codeFactory.createAnnotation(codeFactory.createCtTypeReference(ApiParam.class));
            paramAnnotation.addValue("value", apiDocsParamName);
            parameter.addAnnotation(paramAnnotation);
          } else if (ParameterRepository.generateImplicitParams(parameter.getType().getQualifiedName())) {
            // found an implicit parameter
            // retrieve annotation, if present or put one if not.
            CtAnnotation<ApiImplicitParams> implicitParamsAnnotation = ctMethod.getAnnotation(apiImplicitParamsTypeRef);
            final CtNewArray<ApiImplicitParam> params;
            if (implicitParamsAnnotation == null) {
              implicitParamsAnnotation = codeFactory.createAnnotation(apiImplicitParamsTypeRef);
              ctMethod.addAnnotation(implicitParamsAnnotation);
              params = core.createNewArray();
              implicitParamsAnnotation.addValue("value", params);
            } else {
              params = implicitParamsAnnotation.getValue("value");
            }

            for (String paramName : restOperation.getQueryParameters().keySet()) {
              final ParameterDescription parameterDescription = restOperation.getQueryParameters().get(paramName);
              final CtAnnotation<ApiImplicitParam> param = codeFactory.createAnnotation(codeFactory.createCtTypeReference(ApiImplicitParam.class));
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
        log.error("No method documentation found for {}#{}", className, methodName);
        description = WordUtils.capitalize(StringHelper.splitCamelCase(methodName));
      }

      log.debug("Annotating method {}#{}", className, ctMethod.getSimpleName());
      final CtAnnotation<Annotation> apiOperation = codeFactory.createAnnotation(codeFactory.createCtTypeReference(ApiOperation.class));
      apiOperation.addValue("value", StringHelper.firstSentence(description));
      apiOperation.addValue("notes", description);
      ctMethod.addAnnotation(apiOperation);


    } else {
      log.error("No service documentation found for {}", className);
    }
  }
}
