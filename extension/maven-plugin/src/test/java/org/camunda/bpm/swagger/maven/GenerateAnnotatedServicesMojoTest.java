package org.camunda.bpm.swagger.maven;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.swagger.maven.GenerateAnnotatedServicesMojo.NAME;

public class GenerateAnnotatedServicesMojoTest {

  @Rule
  public MojoRule rule = new MojoRule();

  @Rule
  public TestResources resources = new TestResources();

  @Test
  public void testInvalidProject() throws Exception {

    File projectCopy = this.resources.getBasedir("default");
    File pom = new File(projectCopy, "pom.xml");

    assertThat(pom).isNotNull().exists();

    GenerateAnnotatedServicesMojo mojo = (GenerateAnnotatedServicesMojo) this.rule.lookupMojo(NAME, pom);
    assertThat(mojo).isNotNull();

    mojo.execute();
  }
}
