package org.camunda.bpm.swagger.docs;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.docs.model.RestOperations;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import lombok.SneakyThrows;

public class DocumentationYaml implements Supplier<Map<String, Map<String, RestOperation>>> {

  private final Map<String, Map<String, RestOperation>> operations;

  public DocumentationYaml() {
    this("/camunda-rest-docs.yaml");
  }

  @SneakyThrows
  public DocumentationYaml(final String yamlFile) {
    try (InputStream in = DocumentationYaml.class.getResourceAsStream(yamlFile)) {
      final Yaml yaml = new Yaml(new Constructor(RestOperations.class));
      final RestOperations items = (RestOperations) yaml.load(in);


      this.operations = items.getRestOperations().stream()
          .collect(
              Collectors.groupingBy(RestOperation::getPath,
                  Collectors.groupingBy(RestOperation::getMethod, Collectors.reducing(null, (a,b) -> b)))
              );


    }
  }

  @Override
  public Map<String, Map<String, RestOperation>> get() {

    return operations;
  }
}
