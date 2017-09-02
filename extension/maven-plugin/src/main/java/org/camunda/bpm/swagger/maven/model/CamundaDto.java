package org.camunda.bpm.swagger.maven.model;

import static org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo.CAMUNDA_REST_ROOT_PKG;

import javax.annotation.Generated;

import org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo;
import org.camunda.bpm.swagger.maven.generator.StringHelper;
import org.camunda.bpm.swagger.maven.generator.SwaggerCodeGeneratorFactory;

import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;

@EqualsAndHashCode(exclude = { "codeModel", "definedClass" }, callSuper = false)
public class CamundaDto extends Model {

  public static final String PACKAGE = CAMUNDA_REST_ROOT_PKG + ".swagger.dto";

  @Getter
  private final JCodeModel codeModel;

  @Getter
  private final JDefinedClass definedClass;

  @Getter
  private Class<?> baseClass;

  @SneakyThrows
  public CamundaDto(final ModelRepository modelRepository, final Class<?> baseClass) {
    super(modelRepository);

    this.baseClass = baseClass;
    this.codeModel = new JCodeModel();
    this.codeModel._package(PACKAGE);
    definedClass = this.codeModel._class(getFullQualifiedName());
    definedClass.annotate(Generated.class).param("value", GenerateSwaggerServicesMojo.class.getCanonicalName());
  }

  @Override
  public String getFullQualifiedName() {
    return String.format("%s.%sSwagger", PACKAGE, getSimpleName());
  }

  public String getSimpleName() {
    return baseClass.getSimpleName();
  }

  public Package getPackage() {
    return baseClass.getPackage();
  }

  public String getPackageName() {
    return getPackage().getName();
  }

  public String getName() {
    final String[] n = StringHelper.splitCamelCase(getSimpleName()).split(" ");
    return n[0] + " " + n[2];
  }

  public void generate() {
    new SwaggerCodeGeneratorFactory().createDtoCodeGenerator(this).generate();
  }
}
