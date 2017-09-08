package org.camunda.bpm.swagger.maven.generator;

import org.camunda.bpm.swagger.maven.model.CamundaRestService;
import org.camunda.bpm.swagger.maven.spi.CodeGenerator;

public class SwaggerCodeGeneratorFactory {

  public CodeGenerator createCodeGenerator(final CamundaRestService camundaRestService) {
    return new SwaggerServiceModelGenerator(camundaRestService);
  }
}
