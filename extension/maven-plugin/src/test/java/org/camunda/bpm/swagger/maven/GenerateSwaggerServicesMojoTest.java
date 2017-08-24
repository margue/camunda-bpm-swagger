package org.camunda.bpm.swagger.maven;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo.GOAL;

public class GenerateSwaggerServicesMojoTest {

  @Rule
  public MojoRule rule = new MojoRule();

  @Rule
  public TestResources resources = new TestResources();

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void generateSources() throws Exception {

    folder.create();

    File projectCopy = this.resources.getBasedir("project-to-test");
    File pom = new File(projectCopy, "pom.xml");

    assertThat(pom).isNotNull().exists();

    GenerateSwaggerServicesMojo mojo = (GenerateSwaggerServicesMojo) this.rule.lookupMojo(GOAL, pom);
    assertThat(mojo).isNotNull();

    mojo.execute();
  }
}
