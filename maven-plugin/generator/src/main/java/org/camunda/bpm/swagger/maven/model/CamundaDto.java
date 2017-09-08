package org.camunda.bpm.swagger.maven.model;

import static org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo.CAMUNDA_REST_ROOT_PKG;

import javax.annotation.Generated;

import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo;
import org.camunda.bpm.swagger.maven.generator.SwaggerCodeGeneratorFactory;

import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@EqualsAndHashCode(exclude = { "codeModel", "definedClass" }, callSuper = false)
public class CamundaDto extends AbstractModel {

  public static final String PACKAGE = CAMUNDA_REST_ROOT_PKG + ".swagger.dto";

  @Getter
  private final JCodeModel codeModel;

  @Getter
  private final JDefinedClass definedClass;

  @Getter
  private Class<?> baseClass;

  @Getter
  @Setter
  private RestOperation restOperation;

  @SneakyThrows
  public CamundaDto(final ModelRepository modelRepository, final Class<?> baseClass) {

    super(modelRepository);
    this.baseClass = baseClass;
    this.codeModel = new JCodeModel();
    this.codeModel._package(PACKAGE);
    this.definedClass = this.codeModel._class(getFullQualifiedName());
    this.definedClass.annotate(Generated.class).param("value", GenerateSwaggerServicesMojo.class.getCanonicalName());
  }

  @Override
  public String getFullQualifiedName() {
    return String.format("%s.%sSwagger", PACKAGE, getSimpleName());
  }

  @Override
  public String getSimpleName() {
    return baseClass.getSimpleName();
  }

  @Override
  public Package getPackage() {
    return baseClass.getPackage();
  }

  public void generate() {
    new SwaggerCodeGeneratorFactory().createDtoCodeGenerator(this).generate();
  }
}
