package org.camunda.bpm.swagger.maven.generator;

import org.camunda.bpm.swagger.maven.model.CamundaRestService;
import org.camunda.bpm.swagger.maven.spi.CodeGenerator;
import org.camunda.bpm.swagger.maven.spi.CodeGeneratorFactory;

@SuppressWarnings("unused")
public class SwaggerCodeGeneratorFactory implements CodeGeneratorFactory {

  @Override
  public CodeGenerator createCodeGenerator(final CamundaRestService camundaRestService) {
    return new SwaggerServiceModelGenerator(camundaRestService);
  }
}
