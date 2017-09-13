package org.camunda.bpm.swagger.maven.generator.step;

import java.lang.reflect.Method;
import java.util.Optional;

import javax.ws.rs.Path;

import com.helger.jcodemodel.JMethod;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.camunda.bpm.swagger.maven.generator.TypeHelper;

@Data
@EqualsAndHashCode(callSuper = true)
public class PathAnnotationStep extends AbstractMethodStep {

  private final MethodStep methodStep;
  private String path;

  public PathAnnotationStep(final MethodStep methodStep) {
    super(methodStep.getMethod());
    this.methodStep = methodStep;
  }

  public static String path(final String parentPathPrefix, final Method method) {
    final StringBuilder pathBuilder = parentPathPrefix == null ? new StringBuilder("") : new StringBuilder(parentPathPrefix);
    if (pathBuilder.length() > 0 && pathBuilder.lastIndexOf("/") == pathBuilder.length() - 1) {
      // ends with a "/", remove it
      pathBuilder.deleteCharAt(pathBuilder.length() - 1);
    }
    final String currentPath = Optional.ofNullable(method.getAnnotation(Path.class)).map(a -> a.value()).orElse("");
    if (!currentPath.isEmpty() && !currentPath.startsWith("/")) {
      pathBuilder.append("/").append(currentPath);
    } else {
      pathBuilder.append(currentPath);
    }
    return pathBuilder.toString();
  }

  public JMethod annotate(final String parentPathPrefix, final Method method) {

    this.path = path(parentPathPrefix, method);
    if (!TypeHelper.isResource(methodStep.getReturnType())) {
      getMethod().annotate(Path.class).param("value", this.path);
    }
    return getMethod();
  }
}
