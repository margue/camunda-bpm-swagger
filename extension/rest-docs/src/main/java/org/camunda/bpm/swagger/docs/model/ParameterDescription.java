package org.camunda.bpm.swagger.docs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ParameterDescription {
  private String id;
  private String type;
  private String description;
}
