package org.camunda.bpm.swagger.docs.model;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class RestService {

  private String tags;
  private String description;
  private Map<String, RestOperation> operations = new HashMap<>();

}
