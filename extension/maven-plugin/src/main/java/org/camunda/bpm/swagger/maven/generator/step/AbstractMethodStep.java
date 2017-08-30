package org.camunda.bpm.swagger.maven.generator.step;

import com.helger.jcodemodel.JMethod;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract class AbstractMethodStep {

  private final JMethod method;

}
