package org.camunda.bpm.swagger.docs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.function.Function;

import org.camunda.bpm.swagger.docs.model.DtoDocs;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import lombok.SneakyThrows;

public class DtoDocsYaml implements Function<File, DtoDocs> {

  @Override
  @SneakyThrows
  public DtoDocs apply(final File file) {
    try (final InputStream in = new FileInputStream(file)) {
      final Yaml yaml = new Yaml(new Constructor(DtoDocs.class));
      return (DtoDocs) yaml.load(in);
    }

  }
}
