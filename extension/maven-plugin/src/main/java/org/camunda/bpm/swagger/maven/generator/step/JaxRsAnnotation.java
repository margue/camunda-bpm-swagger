package org.camunda.bpm.swagger.maven.generator.step;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.helger.jcodemodel.JMethod;

public class JaxRsAnnotation extends AbstractMethodStep {

  public JaxRsAnnotation(final JMethod method) {
    super(method);
  }

  public JMethod annotate(final Method m) {
    javaxRsAnnotation(m).ifPresent(a -> getMethod().annotate(a));
    return getMethod();
  }

  static Optional<Class<? extends Annotation>> javaxRsAnnotation(final Method method) {
    for (final Class<? extends Annotation> a : Arrays.asList(POST.class, GET.class, PUT.class, DELETE.class)) {
      if (method.getAnnotation(a) != null) {
        return Optional.of(a);
      }
    }

    return Optional.empty();
  }

}
