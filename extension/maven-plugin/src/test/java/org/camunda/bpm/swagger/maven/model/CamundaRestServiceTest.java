package org.camunda.bpm.swagger.maven.model;


import org.camunda.bpm.engine.rest.TaskRestService;
import org.camunda.bpm.engine.rest.impl.TaskRestServiceImpl;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CamundaRestServiceTest {
  private final CamundaRestService service = new CamundaRestService(TaskRestService.class, TaskRestServiceImpl.class);

  @Test
  public void getFullQualifiedName() throws Exception {

    assertThat(service.getFullQualifiedName()).isEqualTo("org.camunda.bpm.engine.rest.swagger.TaskRestServiceSwagger");

  }


  @Test
  public void getPath() throws Exception {
    assertThat(service.getPath()).isEqualTo(TaskRestService.PATH);
  }

  @Test
  public void getPackageName() throws Exception {
    assertThat(service.getPackageName()).isEqualTo("org.camunda.bpm.engine.rest");
  }
}
