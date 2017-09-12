package org.camunda.bpm.swagger.docs.model;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class DtoDocs {

  private Map<String, Map<String, ParameterDescription>> dtoDocumentation = new HashMap<>();


  public Map<String, ParameterDescription> get(final String fqn) {
    return dtoDocumentation.get(fqn);
  }

}
