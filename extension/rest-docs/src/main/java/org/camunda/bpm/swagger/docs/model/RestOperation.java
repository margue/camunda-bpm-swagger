package org.camunda.bpm.swagger.docs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
@Setter
public class RestOperation {

  private String method;
    private String path;
    private String description;
    private String resultDescription;
    private Map<String, ParameterDescription> pathParameters = new HashMap<>();
    private Map<String, ParameterDescription> queryParameters = new HashMap<>();
    private Map<String, ParameterDescription> responseCodes = new HashMap<>();
}
