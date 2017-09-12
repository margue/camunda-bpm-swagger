package org.camunda.bpm.swagger.maven.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.swagger.docs.DocumentationYaml;
import org.camunda.bpm.swagger.docs.model.DocStyleOperationPair;
import org.camunda.bpm.swagger.docs.model.RestOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ModelRepository {

  @Getter
  private final Set<AbstractModel> models = new HashSet<>();

  @Getter
  private final DocumentationYaml documentation;

  @Getter
  private final Map<String, List<DocStyleOperationPair>> dtoDocs = new HashMap<>();

  public ModelRepository(final DocumentationYaml documentation) {
    this.documentation = documentation;
  }

  /**
   * Supplies doc for given FQN.
   * @param fqn full qualified DTO name.
   * @param resOp operation documentation.
   */
  public void addDoc(final String fqn, final RestOperation resOp, final DocStyle style) {
    if (resOp == null) {
      return;
    }
    List<DocStyleOperationPair> list = dtoDocs.get(fqn);
    if (list == null) {
      list = new ArrayList<>();
      dtoDocs.put(fqn, list);
    }
    list.add(new DocStyleOperationPair(style.name(), resOp));
  }

  public AbstractModel addModel(final AbstractModel model) {
    if (models.contains(model)) {
      log.error("Duplicate model added to repository {}", model.getFullQualifiedName());
    }
    models.add(model);
    return model;
  }
}
