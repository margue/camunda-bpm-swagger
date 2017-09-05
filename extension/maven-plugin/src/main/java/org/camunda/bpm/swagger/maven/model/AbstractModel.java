package org.camunda.bpm.swagger.maven.model;

import java.io.File;

import com.helger.jcodemodel.JCodeModel;

import lombok.Getter;
import lombok.SneakyThrows;

public abstract class AbstractModel {

  @Getter
  private final ModelRepository modelRepository;

  public AbstractModel(final ModelRepository modelRepository) {
    this.modelRepository = modelRepository;
    modelRepository.addModel(this);
  }

  public abstract JCodeModel getCodeModel();

  public abstract String getFullQualifiedName();

  @SneakyThrows
  public void write(final File destination) {
    if (destination == null || !destination.canWrite() || !destination.exists() || !destination.isDirectory()) {
      throw new IllegalStateException("Cannot write to " + destination);
    }
    getCodeModel().build(destination);
  }
}
