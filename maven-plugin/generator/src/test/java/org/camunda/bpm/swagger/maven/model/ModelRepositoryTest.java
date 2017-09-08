package org.camunda.bpm.swagger.maven.model;

import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.swagger.docs.DocumentationYaml;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ModelRepositoryTest {

  private final DocumentationYaml yaml = mock(DocumentationYaml.class);

   private final ModelRepository repository = new ModelRepository(yaml);

  @Test
  public void addsCamundaDtoIfNotExists() throws Exception {
    CamundaDto taskDto = new CamundaDto(repository, TaskDto.class);

    repository.addModel(taskDto);

    assertThat(repository.getModels()).containsOnly(taskDto);
  }

  @Test
  public void returnsExistingCamundaDtoIfExists() throws Exception {
    CamundaDto taskDto = new CamundaDto(repository, TaskDto.class);

    repository.addModel(taskDto);
    AbstractModel abstractModel = repository.addModel(taskDto);

    assertThat(repository.getModels()).containsOnly(abstractModel);
  }
}
