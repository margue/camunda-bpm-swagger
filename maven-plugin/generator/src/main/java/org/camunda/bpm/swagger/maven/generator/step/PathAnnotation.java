package org.camunda.bpm.swagger.maven.generator.step;

import java.lang.reflect.Method;
import java.util.Optional;

import javax.ws.rs.Path;

import com.helger.jcodemodel.JMethod;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PathAnnotation extends AbstractMethodStep {

  private String path;

  public PathAnnotation(final JMethod method) {
    super(method);
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
    getMethod().annotate(Path.class).param("value", this.path);
    return getMethod();
  }
}
