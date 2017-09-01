package org.camunda.bpm.swagger.maven.generator;

import java.util.Arrays;

import org.camunda.bpm.swagger.maven.generator.step.Invocation;
import org.camunda.bpm.swagger.maven.model.CamundaDto;
import org.camunda.bpm.swagger.maven.spi.CodeGenerator;

import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JMethod;

import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwaggerDtoModelGenerator implements CodeGenerator {

  private static final String DTO_PARAM = "dto";
  private final CamundaDto camundaDto;
  private final JCodeModel codeModel;

  public SwaggerDtoModelGenerator(final CamundaDto camundaDto) {
    this.camundaDto = camundaDto;
    this.codeModel = camundaDto.getCodeModel();
    log.info("processing DTO: {}", camundaDto.getFullQualifiedName());
  }

  @Override
  public JCodeModel generate() {

    final JDefinedClass c = camundaDto.getDefinedClass();
    c.annotate(ApiModel.class);
    c._extends(camundaDto.getBaseClass());

    // generate constructors 
    Arrays.stream(camundaDto.getBaseClass().getConstructors()).forEach(constructor -> {
      new Invocation(c.constructor(constructor.getModifiers())).constructor(constructor);
    });
    final JMethod dtoConstructor = c.constructor(1);

    dtoConstructor.param(camundaDto.getBaseClass(), DTO_PARAM);
    new Invocation(dtoConstructor).dtoConstructor(camundaDto.getBaseClass(), DTO_PARAM);

    return this.codeModel;
  }

}
