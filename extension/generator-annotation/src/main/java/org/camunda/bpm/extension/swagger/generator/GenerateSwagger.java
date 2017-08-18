package org.camunda.bpm.extension.swagger.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( ElementType.TYPE )
@Retention( RetentionPolicy.CLASS )
public @interface GenerateSwagger {

  String NAME = "org.camunda.bpm.extension.swagger.generator.GenerateSwagger";
}
