package org.camunda.bpm.swagger.maven;

import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
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

    File projectCopy = this.resources.getBasedir("project-to-test");
    File pom = new File(projectCopy, "pom.xml");

    assertThat(pom).isNotNull().exists();

    GenerateDocumentationYamlMojo mojo = (GenerateDocumentationYamlMojo) this.rule.lookupMojo(GenerateDocumentationYamlMojo.GOAL, pom);
    assertThat(mojo).isNotNull();

    mojo.execute();
  }
}
