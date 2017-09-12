package org.camunda.bpm.swagger.maven.generator.step;

import java.lang.reflect.Method;
import java.util.Map;

import org.camunda.bpm.swagger.docs.model.ParameterDescription;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.maven.generator.TypeHelper;

import com.helger.jcodemodel.JAnnotationArrayMember;
import com.helger.jcodemodel.JMethod;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public class ApiResponsesStep extends AbstractMethodStep {

  private final RestOperation restOperation;

  public ApiResponsesStep(final JMethod method, final RestOperation restOperation) {
    super(method);
    this.restOperation = restOperation;
  }

  public void annotate(final MethodStep methodStep, final Method m) {

    // resources are not annotated at all, because the resource itself will contain a method
    // that will get into the public API.
    if (!TypeHelper.isResource(methodStep.getReturnType()) && restOperation != null) {
      final Map<String, ParameterDescription> responseCodes = restOperation.getResponseCodes();
      if (!responseCodes.isEmpty()) {
        final JAnnotationArrayMember responseArray = getMethod().annotate(ApiResponses.class).paramArray("value");
        for (final String code : responseCodes.keySet()) {
          responseArray.annotate(ApiResponse.class).param("code", Integer.valueOf(code)).param("message", responseCodes.get(code).getDescription());
        }
      }

    }
  }

}