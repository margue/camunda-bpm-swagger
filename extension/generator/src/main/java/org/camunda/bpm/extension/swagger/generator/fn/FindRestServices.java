package org.camunda.bpm.extension.swagger.generator.fn;


import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.reflections.Reflections;

public class FindRestServices implements Supplier<Set<String>> {


  private final Reflections reflections;


  public FindRestServices(Reflections reflections) {
    this.reflections = reflections;
  }

  @Override
  public Set<String> get() {
    return reflections.getAllTypes().stream()
      .filter(fqn -> fqn.endsWith("CamundaRestResource"))
      .collect(Collectors.toSet());
  }
}
