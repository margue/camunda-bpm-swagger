package org.camunda.bpm.swagger.maven.generator;

import static org.apache.commons.lang3.text.WordUtils.capitalize;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.camunda.bpm.swagger.maven.generator.step.ConsumesAndProduces;
import org.camunda.bpm.swagger.maven.generator.step.Invocation;
import org.camunda.bpm.swagger.maven.generator.step.JaxRsAnnotation;
import org.camunda.bpm.swagger.maven.generator.step.MethodStep;
import org.camunda.bpm.swagger.maven.generator.step.PathAnnotation;
import org.camunda.bpm.swagger.maven.model.CamundaRestService;
import org.camunda.bpm.swagger.maven.spi.CodeGenerator;

import com.helger.jcodemodel.JAnnotationUse;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;

public class SwaggerServiceModelGenerator implements CodeGenerator {

  private final CamundaRestService camundaRestService;
  private final JCodeModel codeModel;

  public SwaggerServiceModelGenerator(final CamundaRestService camundaRestService) {
    this.camundaRestService = camundaRestService;
    this.codeModel = camundaRestService.getCodeModel();
  }

  @Override
  @SneakyThrows
  public CamundaRestService generate() {
    final JDefinedClass c = camundaRestService.getDefinedClass();

    c.annotate(codeModel.ref(Path.class)).param("value", camundaRestService.getPath());
    c.annotate(codeModel.ref(Api.class)).param("value", camundaRestService.getName()).param("tags", camundaRestService.getTag());
    c._extends(camundaRestService.getServiceImplClass());

    // generate constructor
    for (final Constructor<?> constructor : camundaRestService.getServiceImplClass().getConstructors()) {
      new Invocation(c.constructor(constructor.getModifiers())).constructor(constructor);
    }

    final Map<Method, ReturnTypeInfo> returnTypes = new HashMap<>();
    for (final Method m : camundaRestService.getServiceInterfaceClass().getDeclaredMethods()) {
      final ReturnTypeInfo info = new ReturnTypeInfo(m);
      info.applyImplementationMethods(camundaRestService.getServiceImplClass().getDeclaredMethods());
      returnTypes.put(m, info);
    }

    generateMethods(c, returnTypes, "");

    return camundaRestService;
  }

  private void generateMethods(final JDefinedClass clazz, final Map<Method, ReturnTypeInfo> methods, final String parentPathPrefix,
      final ParentInvocation... parentInvocations) {

    for (final Method m : methods.keySet()) {

      // create method
      final MethodStep methodStep = new MethodStep(clazz);
      final JMethod method = methodStep.create(methods.get(m), parentInvocations);

      // path annotation
      final PathAnnotation pathAnnotationStep = new PathAnnotation(method);
      pathAnnotationStep.annotate(parentPathPrefix, m);

      // JAX RS
      final JaxRsAnnotation jaxrsAnnotation = new JaxRsAnnotation(method);
      jaxrsAnnotation.annotate(m);

      // consume and produce
      final ConsumesAndProduces consumesAndProduces = new ConsumesAndProduces(method);
      consumesAndProduces.annotate(m);

      final JAnnotationUse apiOperation = method.annotate(ApiOperation.class).param("value", capitalize(StringHelper.splitCamelCase(m.getName())))
          // TODO: inject operation description here
          .param("notes", "Operation " + capitalize(StringHelper.splitCamelCase(m.getName())));

      // create invocation
      final JInvocation invoke = new Invocation(method).method(m, parentInvocations);

      // body
      if (isVoid(methodStep.getReturnType())) {
        method.body().add(invoke);
      } else {
        method.body()._return(invoke);
      }

      if (isResource(methodStep.getReturnType())) {
        // dive into resource processing
        apiOperation.param("response", findReturnTypeOfResource(methodStep.getReturnType()));

        final ParentInvocation[] newParentInvocations = new ParentInvocation[parentInvocations.length + 1];
        System.arraycopy(parentInvocations, 0, newParentInvocations, 0, parentInvocations.length);
        newParentInvocations[parentInvocations.length] = new ParentInvocation(m.getName(), m.getParameters());

        final Map<Method, ReturnTypeInfo> resourceReturnTypeInfos = Arrays.stream(methodStep.getReturnType().getDeclaredMethods())
            .collect(Collectors.toMap(returnMethod -> returnMethod, returnMethod -> new ReturnTypeInfo(returnMethod)));

        generateMethods(clazz, resourceReturnTypeInfos, pathAnnotationStep.getPath(), newParentInvocations);
      }

    }
  }

  static boolean isVoid(final Class<?> returnType) {
    return "void".equals(returnType.getName());
  }

  /**
   * Finds a method annotated with GET annotation, which misses the Path annotation or has a Path annotation without any path specified and uses its return
   * type.
   * 
   * @param resource
   *          resource class.
   * @return type of the resource.
   */
  static Class<?> findReturnTypeOfResource(final Class<?> resource) {
    final Optional<Method> defaultGet = Arrays.stream(resource.getMethods()).filter(m -> m.isAnnotationPresent(GET.class)) // all GET methods
        .filter(m -> !m.isAnnotationPresent(Path.class) // without Path annotation
            || m.isAnnotationPresent(Path.class) && m.getAnnotation(Path.class).value().equals("")) // with empty path annotation
        .findFirst(); // take first.
    if (defaultGet.isPresent()) {
      return defaultGet.get().getReturnType();
    }
    return resource;
  }

  static boolean isResource(final Class<?> clazz) {
    return clazz.isInterface() && clazz.getName().endsWith("Resource");
  }

}
