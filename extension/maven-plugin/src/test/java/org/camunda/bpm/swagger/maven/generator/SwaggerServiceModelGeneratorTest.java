package org.camunda.bpm.swagger.maven.generator;

import org.junit.Test;

import java.util.StringTokenizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.swagger.maven.generator.SwaggerServiceModelGenerator.camelize;

public class SwaggerServiceModelGeneratorTest {

  @Test
  public void name() throws Exception {
    assertThat(camelize("tenant-id")).isEqualTo("tenantId");
  }
}
