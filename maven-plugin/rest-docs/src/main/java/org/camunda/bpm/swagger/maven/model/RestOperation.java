package org.camunda.bpm.swagger.maven.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestOperation {
  @Getter @Setter private String method;
  @Getter @Setter private String path;
  @Getter @Setter private String description;
  @Getter @Setter private String resultDescription;
  @Getter @Setter private Map<String, ParameterDescription> result;
  @Getter @Setter private Map<String, ParameterDescription> requestBody;
  @Getter @Setter private Map<String, ParameterDescription> pathParameters;
  @Getter @Setter private Map<String, ParameterDescription> queryParameters;
  @Getter @Setter private Map<String, ParameterDescription> responseCodes;
}
