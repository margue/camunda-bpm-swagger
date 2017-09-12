package org.camunda.bpm.swagger.docs;

import lombok.SneakyThrows;
import org.camunda.bpm.swagger.docs.model.DtoDocumentation;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.function.Function;

public class DtoDocumentationYaml implements Function<File, DtoDocumentation> {


  @Override
  @SneakyThrows
  public DtoDocumentation apply(final File file) {
    try (final InputStream in = new FileInputStream(file)) {
      final Yaml yaml = new Yaml(new Constructor(DtoDocumentation.class));

      return (DtoDocumentation) yaml.load(in);
    }

  }
}
