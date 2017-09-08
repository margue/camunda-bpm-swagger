package org.camunda.bpm.swagger.maven.model;

import static org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo.CAMUNDA_REST_ROOT_PKG;

import org.camunda.bpm.swagger.docs.model.RestOperation;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.JCodeModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@EqualsAndHashCode(exclude = { "codeModel", "definedClass" }, callSuper = false)
public class CamundaDto {

  public static final String PACKAGE = CAMUNDA_REST_ROOT_PKG + ".swagger.dto";

  private final JCodeModel codeModel;

  @Getter
  private final AbstractJClass definedClass;

  @Getter
  private Class<?> baseClass;

  @Getter
  @Setter
  private RestOperation restOperation;

  @SneakyThrows
  public CamundaDto(final ModelRepository modelRepository, final Class<?> baseClass, final JCodeModel owner) {
    this.baseClass = baseClass;
    this.codeModel = owner;
    this.definedClass = this.codeModel.ref(this.baseClass);
  }

  public String getFullQualifiedName() {
    return String.format("%s.%sSwagger", PACKAGE, getSimpleName());
  }

  public String getSimpleName() {
    return baseClass.getSimpleName();
  }

  public Package getPackage() {
    return baseClass.getPackage();
  }

}
