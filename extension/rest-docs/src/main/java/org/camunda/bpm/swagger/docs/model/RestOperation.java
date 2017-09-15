package org.camunda.bpm.swagger.docs.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
@Setter
public class RestOperation {
  /**
   * HTTP Method. One of GET, POST, PUT, DELETE, OPTIONS
   */
  private String method;
  /**
   * Full path to the REST including placeholder in '{}'
   */
  private String path;
  /**
   * Description of the operation.
   */
  private String description;
  /**
   * Description of the result.
   */
  private String resultDescription;
  /**
   * Example of a response.
   */
  private String responseExample;
  private Map<String, ParameterDescription> requestBody = new HashMap<>();
  private Map<String, ParameterDescription> pathParameters = new HashMap<>();
  private Map<String, ParameterDescription> queryParameters = new HashMap<>();
  private Map<String, ParameterDescription> responseCodes = new HashMap<>();
  private Map<String, ParameterDescription> result = new HashMap<>();
}
