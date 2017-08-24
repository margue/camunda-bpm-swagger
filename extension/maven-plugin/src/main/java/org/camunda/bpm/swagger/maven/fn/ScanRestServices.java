package org.camunda.bpm.swagger.maven.fn;


import org.camunda.bpm.swagger.maven.model.CamundaRestService;
import org.reflections.Reflections;

import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ScanRestServices implements Supplier<Set<CamundaRestService>> {

  private final Reflections reflections;

  public ScanRestServices(Reflections reflections) {
    this.reflections = reflections;
  }

  @Override
  public Set<CamundaRestService> get() {
    return reflections.getSubTypesOf(Object.class).stream()
      .filter(Class::isInterface)
      .filter(i -> i.getSimpleName().endsWith("RestService"))
      .map(i -> {
        final Class<?> impl = reflections.getSubTypesOf(i).stream().reduce((a, b) -> {
          throw new IllegalStateException("Multiple elements: " + a + ", " + b);
        }).orElseThrow(() -> new IllegalStateException("no implementation found"));
        return new CamundaRestService(i, impl);
      })
      .collect(Collectors.toSet());
  }
}
