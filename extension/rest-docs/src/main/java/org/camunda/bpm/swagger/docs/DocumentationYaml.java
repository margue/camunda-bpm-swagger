package org.camunda.bpm.swagger.docs;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.docs.model.RestOperations;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocumentationYaml implements Supplier<Map<Pair<String, String>, RestOperation>> {

  private final Map<Pair<String, String>, RestOperation> operations;

  public DocumentationYaml() {
    this("/camunda-rest-docs.yaml");
  }

  @SneakyThrows
  public DocumentationYaml(final String yamlFile) {
    try (final InputStream in = DocumentationYaml.class.getResourceAsStream(yamlFile)) {
      final Yaml yaml = new Yaml(new Constructor(RestOperations.class));
      final RestOperations items = (RestOperations) yaml.load(in);
      this.operations = items.getRestOperations().stream().collect(Collectors.toMap(p -> Pair.of(p.getPath(), p.getMethod()), Function.identity()));
    }
  }

  @Override
  public Map<Pair<String, String>, RestOperation> get() {
    if (this.operations == null) {
      return Collections.emptyMap();
    }
    return operations;
  }

  public void print(final Map<Pair<String, String>, RestOperation> ops) {
    ops.entrySet().stream().forEach(e -> {
      log.info("{} {}", e.getKey().getLeft(), e.getKey().getRight());
      print(e.getValue());
    });
  }

  private void print(final RestOperation op) {
    log.info("--> {}: {}", op.getMethod(), op.getDescription());
  }

  public void info() {
    print(this.operations);
  }
}
