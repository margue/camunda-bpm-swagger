package org.camunda.bpm.swagger.docs.model;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ServiceDocs {

  private Map<String, RestService> services = new HashMap<>();

  public RestService get(final String classFqn) {
    return services.get(classFqn);
  }

}
