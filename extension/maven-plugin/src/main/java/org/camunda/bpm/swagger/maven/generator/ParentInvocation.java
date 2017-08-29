package org.camunda.bpm.swagger.maven.generator;

import java.lang.reflect.Parameter;

import lombok.Value;

@Value class ParentInvocation {
  private String methodName;
  private Parameter[] parameters;
}