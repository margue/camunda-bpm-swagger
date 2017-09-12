package org.camunda.bpm.swagger.docs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class DtoDocumentation {

  private Map<String, List<DocStyleOperationPair>> dtoDocumentation = new HashMap<>();


  public List<DocStyleOperationPair> get(String fqn) {
    return dtoDocumentation.get(fqn);
  }
}
