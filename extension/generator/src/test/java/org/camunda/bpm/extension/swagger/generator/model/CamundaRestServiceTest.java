package org.camunda.bpm.extension.swagger.generator.model;

import org.camunda.bpm.engine.rest.TaskRestService;
import org.camunda.bpm.engine.rest.impl.TaskRestServiceImpl;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CamundaRestServiceTest {

  private final CamundaRestResource taskService = new CamundaRestResource(TaskRestService.class, TaskRestServiceImpl.class);

  @Test
  public void getPath() throws Exception {
    assertThat(taskService.getPath()).isEqualTo(TaskRestService.PATH);
  }

  @Test
  public void getPackageName() throws Exception {
    assertThat(taskService.getPackageName()).isEqualTo("org.camunda.bpm.engine.rest");
  }
}
