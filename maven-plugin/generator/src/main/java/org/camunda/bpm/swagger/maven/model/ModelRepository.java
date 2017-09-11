package org.camunda.bpm.swagger.maven.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.swagger.docs.DocumentationYaml;
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
  private final Map<String, List<Pair<DocStyle, RestOperation>>> dtoDocs = new HashMap<>();

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
    List<Pair<DocStyle, RestOperation>> list = dtoDocs.get(fqn);
    if (list == null) {
      list = new ArrayList<>();
      dtoDocs.put(fqn, list);
    }
    list.add(Pair.of(style, resOp));
  }

  public AbstractModel addModel(final AbstractModel model) {
    if (models.contains(model)) {
      log.error("Duplicate model added to repository {}", model.getFullQualifiedName());
    }
    models.add(model);
    return model;
  }
}
