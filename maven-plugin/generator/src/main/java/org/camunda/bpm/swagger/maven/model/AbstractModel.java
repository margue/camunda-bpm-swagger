package org.camunda.bpm.swagger.maven.model;

import java.io.File;

import org.camunda.bpm.swagger.maven.generator.StringHelper;

import com.helger.jcodemodel.JCodeModel;

import lombok.Getter;
import lombok.SneakyThrows;

public abstract class AbstractModel {

  @Getter
  private final ModelRepository modelRepository;

  public AbstractModel(final ModelRepository modelRepository) {
    this.modelRepository = modelRepository;
    this.modelRepository.addModel(this);
  }

  public abstract JCodeModel getCodeModel();

  public abstract String getFullQualifiedName();

  public abstract String getSimpleName();

  public abstract Package getPackage();

  public abstract Class<?> getBaseClass();


  @SneakyThrows
  public void write(final File destination) {
    if (destination == null || !destination.canWrite() || !destination.exists() || !destination.isDirectory()) {
      throw new IllegalStateException("Cannot write to " + destination);
    }
    getCodeModel().build(destination);
  }

  public String getName() {
    final String[] n = StringHelper.splitCamelCase(getSimpleName()).split(" ");
    return n[0] + " " + n[2];
  }

  public String getPackageName() {
    return getPackage().getName();
  }

}
