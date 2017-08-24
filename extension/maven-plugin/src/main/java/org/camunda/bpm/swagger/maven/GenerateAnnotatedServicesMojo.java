package org.camunda.bpm.swagger.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_SOURCES;
import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;
import static org.camunda.bpm.swagger.maven.GenerateAnnotatedServicesMojo.NAME;

@Mojo(name = NAME,
  defaultPhase = GENERATE_SOURCES,
  requiresDependencyResolution = COMPILE_PLUS_RUNTIME
)
public class GenerateAnnotatedServicesMojo extends AbstractMojo{

  public static final String NAME = "generate-annotated-sources";

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    System.out.println("====");
    System.out.println("  mojo running");
    System.out.println("====");
  }
}
