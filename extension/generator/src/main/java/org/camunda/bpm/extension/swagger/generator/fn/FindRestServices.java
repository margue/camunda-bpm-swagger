package org.camunda.bpm.extension.swagger.generator.fn;


import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.camunda.bpm.extension.swagger.generator.model.CamundaRestService;
import org.reflections.Reflections;

public class FindRestServices implements Supplier<Set<CamundaRestService>> {

  private final Reflections reflections;

  public FindRestServices(Reflections reflections) {
    this.reflections = reflections;
  }

  @Override
  public Set<CamundaRestService> get() {
    return  reflections.getSubTypesOf(Object.class).stream()
      .filter(c -> c.getSimpleName().endsWith("RestService"))
      .map(c -> {
        Class<?> impl = reflections.getSubTypesOf(c).stream().findFirst().orElseThrow(IllegalStateException::new);
        return new CamundaRestService(c, impl);
      })
      .collect(Collectors.toSet());
  }
}
