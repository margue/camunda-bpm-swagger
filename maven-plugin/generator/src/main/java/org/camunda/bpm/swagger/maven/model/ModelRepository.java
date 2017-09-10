package org.camunda.bpm.swagger.maven.model;

import lombok.Data;
import lombok.Getter;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.swagger.docs.DocumentationYaml;
import org.camunda.bpm.swagger.docs.model.RestOperation;

import javax.ws.rs.GET;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Data
@Slf4j
public class ModelRepository {

  private final Set<AbstractModel> models = new HashSet<>();

  private final DocumentationYaml documentation;

  @Singular
  private final Map<String, RestOperation> dtoDocs = new HashMap<>();

  public ModelRepository(final DocumentationYaml documentation) {
    this.documentation = documentation;
  }

  public AbstractModel addModel(final AbstractModel model) {
    if (!models.contains(model)) {
      models.add(model);
    }
    return model;
  }

  public <T extends AbstractModel> Optional<T> get(Class<?> type) {
    return (Optional<T>) models.stream()
      .filter(m -> type.equals(m.getBaseClass()))
      .findFirst();
  }

}
