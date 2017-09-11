package org.camunda.bpm.swagger.maven.generator.step;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import com.helger.jcodemodel.JMethod;

public class ConsumesAndProducesStep extends AbstractMethodStep {

  public ConsumesAndProducesStep(final JMethod method) {
    super(method);
  }

  public JMethod annotate(final Method method) {
    consumesAndProduces(method).entrySet().forEach(e -> getMethod().annotate(e.getKey()).param("value", e.getValue()));
    return getMethod();
  }

  static Map<Class<? extends Annotation>, String> consumesAndProduces(final AnnotatedElement e) {
    final Map<Class<? extends Annotation>, String> map = new HashMap<>();

    Optional.ofNullable(e.getAnnotation(Consumes.class)).ifPresent(a -> {
      map.put(Consumes.class, a.value()[0]);
    });

    // FIXME: must support multiple
    Optional.ofNullable(e.getAnnotation(Produces.class)).ifPresent(a -> {
      map.put(Produces.class, a.value()[0]);
    });

    return map;
  }

}
