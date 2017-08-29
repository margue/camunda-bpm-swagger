package org.camunda.bpm.swagger.maven.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.swagger.maven.generator.SwaggerServiceModelGenerator.camelize;
import static org.camunda.bpm.swagger.maven.generator.SwaggerServiceModelGenerator.path;

import javax.ws.rs.Path;

import org.junit.Test;

public class SwaggerServiceModelGeneratorTest {

  @Test
  public void camelizeTest() throws Exception {
    assertThat(camelize("tenant-id")).isEqualTo("tenantId");
  }

  @Test
  public void camelizeTest2() throws Exception {
    assertThat(camelize("tenantId")).isEqualTo("tenantId");
  }

  @Test
  public void pathTestNull() throws Exception {
    assertThat(path(null, Sample.class.getDeclaredMethod("withSlash"))).isEqualTo("/with-slash");
  }


  @Test
  public void pathTestWithSlash() throws Exception {
    assertThat(path("/parent", Sample.class.getDeclaredMethod("withSlash"))).isEqualTo("/parent/with-slash");
  }

  @Test
  public void pathTestWithSlash2() throws Exception {
    assertThat(path("/parent/", Sample.class.getDeclaredMethod("withSlash"))).isEqualTo("/parent/with-slash");
  }

  @Test
  public void pathTestWithoutSlash() throws Exception {
    assertThat(path("/parent", Sample.class.getDeclaredMethod("withoutSlash"))).isEqualTo("/parent/without-slash");
  }

  @Test
  public void pathTestWithoutSlash2() throws Exception {
    assertThat(path("/parent/", Sample.class.getDeclaredMethod("withoutSlash"))).isEqualTo("/parent/without-slash");
  }

  @Test
  public void pathTestEmpty() throws Exception {
    assertThat(path("/parent", Sample.class.getDeclaredMethod("empty"))).isEqualTo("/parent");
  }

  @Test
  public void pathTestEmpty2() throws Exception {
    assertThat(path("/parent/", Sample.class.getDeclaredMethod("empty"))).isEqualTo("/parent");
  }

  @Test
  public void pathTestNotAnnotated() throws Exception {
    assertThat(path("/parent", Sample.class.getDeclaredMethod("notAnnotated"))).isEqualTo("/parent");
  }

  @Test
  public void pathTestNotAnnotated2() throws Exception {
    assertThat(path("/parent/", Sample.class.getDeclaredMethod("notAnnotated"))).isEqualTo("/parent");
  }


  static class Sample {
    @Path("/with-slash")
    void withSlash() {

    }

    @Path("without-slash")
    void withoutSlash() {

    }

    @Path("")
    void empty() {

    }

    void notAnnotated() {

    }

  }
}
