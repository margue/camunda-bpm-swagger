package org.camunda.bpm.swagger.maven.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RestOperations {

  private List<RestOperation> restOperations = new ArrayList<>();
}
