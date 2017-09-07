package org.camunda.bpm.swagger.maven.spi;

import com.helger.jcodemodel.JCodeModel;

public interface CodeGenerator {
  JCodeModel generate();
}
