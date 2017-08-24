package org.camunda.bpm.swagger.maven.spi;

import org.camunda.bpm.swagger.maven.model.CamundaRestService;

public interface CodeGeneratorFactory {

  CodeGenerator createCodeGenerator(final CamundaRestService camundaRestService);

}
