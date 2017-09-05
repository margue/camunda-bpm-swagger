package org.camunda.bpm.swagger.maven.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ModelRepository {

  private final Set<AbstractModel> models = new HashSet<>();

  public AbstractModel addModel(final AbstractModel model) {
    if (models.contains(model)) {
      log.warn("Model {} already exists in repository.", model.getFullQualifiedName());
    }
    models.add(model);
    return model;
  }

}
