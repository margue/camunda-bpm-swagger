package org.camunda.bpm.extension.swagger.generator.fn;


import org.reflections.Reflections;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FindRestServices implements Supplier<Set<String>> {


  private final Reflections reflections;


  public FindRestServices(Reflections reflections) {
    this.reflections = reflections;
  }

  @Override
  public Set<String> get() {
    return reflections.getAllTypes().stream()
      .filter(fqn -> fqn.endsWith("RestService"))
      .collect(Collectors.toSet());
  }
}
