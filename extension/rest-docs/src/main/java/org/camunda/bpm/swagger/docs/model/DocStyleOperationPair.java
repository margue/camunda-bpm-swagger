package org.camunda.bpm.swagger.docs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocStyleOperationPair {

  private String docStyle;
  private RestOperation restOperation;
}
