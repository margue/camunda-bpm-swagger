package org.camunda.bpm.swagger.maven.fn;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.camunda.bpm.swagger.maven.model.CamundaRestService;
import org.camunda.bpm.swagger.maven.model.ModelRepository;
import org.reflections.Reflections;

public class ScanRestServices implements Supplier<Set<CamundaRestService>> {

  private final Reflections reflections;
  private final ModelRepository modelRespotory;

  public ScanRestServices(final Reflections reflections, final ModelRepository modelRespotory) {
    this.reflections = reflections;
    this.modelRespotory = modelRespotory;
  }

  @Override
  public Set<CamundaRestService> get() {
    return reflections.getSubTypesOf(Object.class).stream().filter(Class::isInterface).filter(i -> i.getSimpleName().endsWith("RestService")).map(i -> {
      final Class<?> impl = reflections.getSubTypesOf(i).stream().reduce((a, b) -> {
        throw new IllegalStateException("Multiple elements: " + a + ", " + b);
      }).orElseThrow(() -> new IllegalStateException("no implementation found"));
      return new CamundaRestService(modelRespotory, i, impl);
    }).collect(Collectors.toSet());
  }
}
