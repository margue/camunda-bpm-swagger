package org.camunda.bpm.swagger.docs.model;

import lombok.Getter;
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
