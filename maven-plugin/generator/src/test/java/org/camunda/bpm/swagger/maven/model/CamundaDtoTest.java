package org.camunda.bpm.swagger.maven.model;

import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class CamundaDtoTest {

  private final ModelRepository repository = Mockito.mock(ModelRepository.class);

  @Test
  public void equals_for_same_baseClass() throws Exception {
    assertThat(new CamundaDto(repository, TaskDto.class)).isEqualTo(new CamundaDto(repository,TaskDto.class));
  }
}
