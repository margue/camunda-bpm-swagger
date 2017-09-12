package org.camunda.bpm.swagger.maven.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.camunda.bpm.swagger.docs.DocumentationYaml;
import org.camunda.bpm.swagger.docs.model.ParameterDescription;
import org.camunda.bpm.swagger.docs.model.RestOperation;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModelRepository {

  @Getter
  private final Set<AbstractModel> models = new HashSet<>();

  @Getter
  private final DocumentationYaml documentation;

  @Getter
  private final Map<String, Map<String, ParameterDescription>> dtoParameterDescriptions = new HashMap<>();

  public ModelRepository(final DocumentationYaml documentation) {
    this.documentation = documentation;
  }

  /**
   * Supplies doc for given FQN.
   * 
   * @param fqn
   *          full qualified DTO name.
   * @param restOperation
   *          operation documentation.
   */
  public void addDoc(final String fqn, final RestOperation restOperation, final DocStyle style) {
    if (restOperation == null) {
      return;
    }

    Map<String, ParameterDescription> parameters = dtoParameterDescriptions.get(fqn);
    if (parameters == null) {
      parameters = new HashMap<>();
      dtoParameterDescriptions.put(fqn, parameters);
    }

    Map<String, ParameterDescription> fieldDescriptions = null;
    switch (style) {
    case RETURN_TYPE:
      fieldDescriptions = restOperation.getResult();
      break;
    case METHOD_PARAM:
      fieldDescriptions = restOperation.getRequestBody();
      break;
    case TYPE_PARAM:
      break;
    }

    if (fieldDescriptions != null) {
      parameters.putAll(fieldDescriptions);
    }

  }

  public AbstractModel addModel(final AbstractModel model) {
    if (models.contains(model)) {
      log.error("Duplicate model added to repository {}", model.getFullQualifiedName());
    }
    models.add(model);
    return model;
  }
}
