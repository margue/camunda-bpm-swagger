package org.camunda.bpm.swagger.maven.spi;

import org.camunda.bpm.swagger.maven.model.CamundaRestService;

public interface CodeGenerator  {

  CamundaRestService generate();

}
