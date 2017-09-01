package org.camunda.bpm.swagger.docs;

import lombok.SneakyThrows;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.docs.model.RestOperations;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DocumentationYaml implements Supplier<Map<String, Map<String, List<RestOperation>>>> {

  private final Map<String, Map<String, List<RestOperation>>> operations;

  public DocumentationYaml() {
    this("/camunda-rest-docs.yaml");
  }

  @SneakyThrows
  public DocumentationYaml(String yamlFile) {
    try (InputStream in = DocumentationYaml.class.getResourceAsStream(yamlFile)) {
      Yaml yaml = new Yaml(new Constructor(RestOperations.class));
      RestOperations items = (RestOperations) yaml.load(in);


      this.operations = items.getRestOperations().stream().collect(Collectors.groupingBy(RestOperation::getPath, Collectors.groupingBy(RestOperation::getMethod)));


    }
  }

  @Override
  public Map<String, Map<String, List<RestOperation>>> get() {

    return operations;
  }
}
