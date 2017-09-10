package org.camunda.bpm.swagger.maven.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.Path;

import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.swagger.maven.generator.step.Invocation;
import org.camunda.bpm.swagger.maven.generator.step.MethodStep;
import org.camunda.bpm.swagger.maven.generator.step.ResourceMethodGenerationHelper;
import org.camunda.bpm.swagger.maven.model.CamundaRestService;
import org.camunda.bpm.swagger.maven.spi.CodeGenerator;

import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;

import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwaggerServiceModelGenerator implements CodeGenerator {

  private static final String NO_PREFIX = "";
  private final CamundaRestService camundaRestService;
  private final JCodeModel codeModel;

  public SwaggerServiceModelGenerator(final CamundaRestService camundaRestService) {
    this.camundaRestService = camundaRestService;
    this.codeModel = camundaRestService.getCodeModel();
    log.debug("Processing REST service: {}", camundaRestService.getFullQualifiedName());
  }

  @Override
  @SneakyThrows
  public JCodeModel generate() {
    final JDefinedClass c = camundaRestService.getDefinedClass();

    // class information
    c._extends(camundaRestService.getServiceImplClass());
    c.annotate(codeModel.ref(Path.class)).param("value", camundaRestService.getPath());
    // TODO add docs here
//    c.annotate(codeModel.ref(Api.class)).param("value", camundaRestService.getName()).param("tags", TagRespository.lookup(camundaRestService));

    // generate constructor
    for (final Constructor<?> constructor : camundaRestService.getServiceImplClass().getConstructors()) {
      new Invocation(c.constructor(constructor.getModifiers())).constructor(constructor);
    }

    // construct return type information
    final Map<Method, ReturnTypeInfo> returnTypes = Arrays.stream(camundaRestService.getServiceInterfaceClass().getDeclaredMethods()) // iterate over interface
        // methods
        .map(m -> new ReturnTypeInfo(camundaRestService.getModelRepository(), codeModel, m)
            .applyImplementationMethods(camundaRestService.getServiceImplClass().getDeclaredMethods())) // apply impl methods
        .collect(Collectors.toMap(r -> r.getMethod(), r -> r)); // build the map

    generateMethods(c, returnTypes, NO_PREFIX);

    return this.codeModel;
  }

  private void generateMethods(final JDefinedClass clazz, final Map<Method, ReturnTypeInfo> methods, final String parentPathPrefix,
      final ParentInvocation... parentInvocations) {

    for (final Method m : methods.keySet()) {
      // create method
      final MethodStep methodStep = new MethodStep(camundaRestService.getModelRepository(), clazz);
      methodStep.create(methods.get(m), Pair.of(camundaRestService.getPath(), parentPathPrefix), camundaRestService.getModelRepository().getDocumentation().get(), parentInvocations);

      // dive into resource processing
      if (TypeHelper.isResource(methodStep.getReturnType())) {
        generateMethods(clazz, // the class
            ResourceMethodGenerationHelper.resourceReturnTypeInfos(camundaRestService.getModelRepository(), codeModel, methodStep.getReturnType()), // info
            // about
            // return
            // types
            methodStep.getPath(), // path prefix to generate REST paths
            ResourceMethodGenerationHelper.createParentInvocations(parentInvocations, m) // invocation hierarchy
            );
      }

    }
  }
}
