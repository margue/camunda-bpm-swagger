package org.camunda.bpm.swagger.docs;

import lombok.SneakyThrows;
import org.camunda.bpm.swagger.docs.model.DtoDocs;
import org.camunda.bpm.swagger.docs.model.ServiceDocs;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.function.Function;

public class ServiceDocsYaml implements Function<File, ServiceDocs> {

  @Override
  @SneakyThrows
  public ServiceDocs apply(final File file) {
    try (final InputStream in = new FileInputStream(file)) {
      final Yaml yaml = new Yaml(new Constructor(ServiceDocs.class));
      return (ServiceDocs) yaml.load(in);
    }
  }
}
