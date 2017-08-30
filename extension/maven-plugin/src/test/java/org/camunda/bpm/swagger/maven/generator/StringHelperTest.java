package org.camunda.bpm.swagger.maven.generator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringHelperTest {

  @Test
  public void camelizeTest() throws Exception {
    assertThat(StringHelper.camelize("tenant-id")).isEqualTo("tenantId");
  }

  @Test
  public void camelizeTest2() throws Exception {
    assertThat(StringHelper.camelize("tenantId")).isEqualTo("tenantId");
  }
}
