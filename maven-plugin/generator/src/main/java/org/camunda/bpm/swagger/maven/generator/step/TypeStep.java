package org.camunda.bpm.swagger.maven.generator.step;

import org.camunda.bpm.swagger.maven.generator.TypeHelper;
import org.camunda.bpm.swagger.maven.model.ModelRepository;

import com.helger.jcodemodel.AbstractJType;
import com.helger.jcodemodel.JCodeModel;

import lombok.Getter;

public class TypeStep {

  @Getter
  private final AbstractJType type;

  @Getter
  private final Class<?> baseClass;

  public TypeStep(final ModelRepository modelRepository, final Class<?> baseClass, final JCodeModel owner) {
    this.baseClass = baseClass;
    this.type = owner._ref(this.baseClass);
  }

  public boolean isDto() {
    return TypeHelper.isDto(baseClass);
  }

  public String getFullQualifiedName() {
    return baseClass.getCanonicalName();
  }

  public String getSimpleName() {
    return baseClass.getSimpleName();
  }

  public Package getPackage() {
    return baseClass.getPackage();
  }
}
