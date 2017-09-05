package org.camunda.bpm.swagger.docs.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
