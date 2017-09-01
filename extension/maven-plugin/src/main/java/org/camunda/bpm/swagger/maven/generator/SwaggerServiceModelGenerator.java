package org.camunda.bpm.swagger.maven.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.Path;

import org.camunda.bpm.swagger.maven.generator.step.ApiOperation;
import org.camunda.bpm.swagger.maven.generator.step.ConsumesAndProduces;
import org.camunda.bpm.swagger.maven.generator.step.Invocation;
import org.camunda.bpm.swagger.maven.generator.step.JaxRsAnnotation;
import org.camunda.bpm.swagger.maven.generator.step.MethodStep;
import org.camunda.bpm.swagger.maven.generator.step.PathAnnotation;
import org.camunda.bpm.swagger.maven.generator.step.ResourceMethodGenerationHelper;
import org.camunda.bpm.swagger.maven.model.CamundaRestService;
import org.camunda.bpm.swagger.maven.spi.CodeGenerator;

import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;

import io.swagger.annotations.Api;
import lombok.SneakyThrows;

public class SwaggerServiceModelGenerator implements CodeGenerator {

  private static final String NO_PREFIX = "";
  private final CamundaRestService camundaRestService;
  private final JCodeModel codeModel;

  public SwaggerServiceModelGenerator(final CamundaRestService camundaRestService) {
    this.camundaRestService = camundaRestService;
    this.codeModel = camundaRestService.getCodeModel();
  }

  @Override
  @SneakyThrows
  public JCodeModel generate() {
    final JDefinedClass c = camundaRestService.getDefinedClass();

    c.annotate(codeModel.ref(Path.class)).param("value", camundaRestService.getPath());
    c.annotate(codeModel.ref(Api.class)).param("value", camundaRestService.getName()).param("tags", camundaRestService.getTag());
    c._extends(camundaRestService.getServiceImplClass());

    // generate constructor
    for (final Constructor<?> constructor : camundaRestService.getServiceImplClass().getConstructors()) {
      new Invocation(c.constructor(constructor.getModifiers())).constructor(constructor);
    }

    // construct return type information
    final Map<Method, ReturnTypeInfo> returnTypes = Arrays.stream(camundaRestService.getServiceInterfaceClass().getDeclaredMethods()) // iterate over interface methods
        .map(m -> new ReturnTypeInfo(m).applyImplementationMethods(camundaRestService.getServiceImplClass().getDeclaredMethods())) // apply impl methods
        .collect(Collectors.toMap(r -> r.getMethod(), r -> r)); // build the map

    generateMethods(c, returnTypes, NO_PREFIX);

    return this.codeModel;
  }

  private void generateMethods(final JDefinedClass clazz, final Map<Method, ReturnTypeInfo> methods, final String parentPathPrefix,
      final ParentInvocation... parentInvocations) {

    for (final Method m : methods.keySet()) {

      // create method
      final MethodStep methodStep = new MethodStep(camundaRestService.getModelRepository(), clazz);
      final JMethod method = methodStep.create(methods.get(m), parentInvocations);

      // create invocation
      final JInvocation invoke = new Invocation(method).method(m, parentInvocations);

      // body
      if (TypeHelper.isVoid(methodStep.getReturnType())) {
        method.body().add(invoke);
      } else {
        switch (methodStep.getReturnTypeStyle()) {
        case PLAIN:
          method.body()._return(invoke);
          break;
        case DTO:
          method.body()._return(JExpr._new(methodStep.getMethodReturnType()).arg(invoke));
          break;
        case DTO_LIST:
          // TODO implement list return
          // method.body()._return
          method.body()._return(invoke);
          break;
        }
      }

      // path annotation
      final PathAnnotation pathAnnotationStep = new PathAnnotation(method);
      pathAnnotationStep.annotate(parentPathPrefix, m);

      // JAX RS
      final JaxRsAnnotation jaxrsAnnotation = new JaxRsAnnotation(method);
      jaxrsAnnotation.annotate(m);

      // consume and produce
      final ConsumesAndProduces consumesAndProduces = new ConsumesAndProduces(method);
      consumesAndProduces.annotate(m);

      final ApiOperation apiOperation = new ApiOperation(method);
      apiOperation.annotate(methodStep, m);

      if (TypeHelper.isResource(methodStep.getReturnType())) {
        // dive into resource processing
        generateMethods(clazz, // the class
            ResourceMethodGenerationHelper.resourceReturnTypeInfos(methodStep.getReturnType()), // info about return types
            pathAnnotationStep.getPath(), // path prefix to generate REST paths
            ResourceMethodGenerationHelper.createParentInvocations(parentInvocations, m) // invocation hierarchy
            );
      }

    }
  }
}
