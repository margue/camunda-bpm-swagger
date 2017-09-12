package org.camunda.bpm.swagger.maven.generator.step;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.commons.lang3.text.WordUtils;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.maven.generator.StringHelper;
import org.camunda.bpm.swagger.maven.generator.TypeHelper;

import com.helger.jcodemodel.JMethod;

import io.swagger.annotations.ApiOperation;

public class ApiOperationStep extends AbstractMethodStep {

  private final RestOperation restOperation;

  public ApiOperationStep(final JMethod method, final RestOperation restOperation) {
    super(method);
    this.restOperation = restOperation;
  }

  public void annotate(final MethodStep methodStep, final Method m) {

    // resources are not annotated at all, because the resource itself will contain a method
    // that will get into the public API. It is a method with GET annotation and empty path.
    if (!TypeHelper.isResource(methodStep.getReturnType())) {
      final String description = restOperation != null ? restOperation.getDescription() : WordUtils.capitalize(StringHelper.splitCamelCase(m.getName()));

      getMethod().annotate(ApiOperation.class) //
      .param("notes", description)
      .param("value", StringHelper.firstSentence(description));
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