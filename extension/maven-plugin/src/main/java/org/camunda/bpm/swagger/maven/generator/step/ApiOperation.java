package org.camunda.bpm.swagger.maven.generator.step;

import static org.apache.commons.lang3.text.WordUtils.capitalize;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.camunda.bpm.swagger.maven.generator.StringHelper;
import org.camunda.bpm.swagger.maven.generator.TypeHelper;

import com.helger.jcodemodel.JAnnotationUse;
import com.helger.jcodemodel.JMethod;

public class ApiOperation extends AbstractMethodStep {

  public ApiOperation(final JMethod method) {
    super(method);
  }

  public void annotate(final MethodStep methodStep, final Method m) {
    final JAnnotationUse apiOperation = getMethod().annotate(io.swagger.annotations.ApiOperation.class) //
        .param("value", capitalize(StringHelper.splitCamelCase(m.getName())))
        // TODO: inject operation description here
        .param("notes", "Operation " + capitalize(StringHelper.splitCamelCase(m.getName())));

    if (TypeHelper.isResource(methodStep.getReturnType())) {
      // dive into resource processing
      apiOperation.param("response", findReturnTypeOfResource(methodStep.getReturnType()));

    }
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

}