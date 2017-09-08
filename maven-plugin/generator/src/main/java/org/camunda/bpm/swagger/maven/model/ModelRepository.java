package org.camunda.bpm.swagger.maven.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.swagger.docs.DocumentationYaml;

import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
public class ModelRepository {

  private final Set<AbstractModel> models = new HashSet<>();

  private final DocumentationYaml documentation;

  public ModelRepository(final DocumentationYaml documentation) {
    this.documentation = documentation;
  }

  public AbstractModel addModel(final AbstractModel model) {
    if (!models.contains(model)) {
      models.add(model);
    }
    return model;
  }

}
