package org.camunda.bpm.swagger.maven;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

// FIXME! test is not running
// @Ignore
public class GenerateDocumentationYamlMojoTest {

  @Rule
  public MojoRule rule = new MojoRule();

  @Rule
  public TestResources resources = new TestResources();

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void generateSources() throws Exception {

    folder.create();

    final File projectCopy = this.resources.getBasedir("project-to-test");
    final File pom = new File(projectCopy, "pom.xml");

    assertThat(pom).isNotNull().exists();

    final GenerateDocumentationYamlMojo mojo = (GenerateDocumentationYamlMojo) this.rule.lookupMojo(GenerateDocumentationYamlMojo.GOAL, pom);
    assertThat(mojo).isNotNull();

    mojo.execute();
  }
}
