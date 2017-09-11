package org.camunda.bpm.swagger.maven.generator.step;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.helger.jcodemodel.JMethod;

import lombok.Getter;

public class JaxRsAnnotationStep extends AbstractMethodStep {

  @Getter
  private Class<? extends Annotation> type = GET.class;

  public JaxRsAnnotationStep(final JMethod method) {
    super(method);
  }

  public JMethod annotate(final Method m) {
    javaxRsAnnotation(m).ifPresent(a -> {
      this.type = a;
      getMethod().annotate(a);
    });
    return getMethod();
  }

  static Optional<Class<? extends Annotation>> javaxRsAnnotation(final Method method) {
    for (final Class<? extends Annotation> a : Arrays.asList(POST.class, GET.class, PUT.class, DELETE.class, OPTIONS.class)) {
      if (method.getAnnotation(a) != null) {
        return Optional.of(a);
      }
    }

    return Optional.empty();
  }

}
