package org.camunda.bpm.swagger.maven.spoon;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_SOURCES;
import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;
import static org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo.GOAL;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.camunda.bpm.swagger.docs.DtoDocsYaml;
import org.camunda.bpm.swagger.docs.ServiceDocsYaml;
import org.camunda.bpm.swagger.docs.model.DtoDocs;
import org.camunda.bpm.swagger.docs.model.ServiceDocs;
import org.camunda.bpm.swagger.maven.spoon.fn.DownloadCamundaSources;
import org.camunda.bpm.swagger.maven.spoon.processor.*;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import spoon.Launcher;
import spoon.SpoonAPI;

@Mojo(
    name = GOAL,
    defaultPhase = GENERATE_SOURCES,
    requiresDependencyResolution = COMPILE_PLUS_RUNTIME
    )
@Slf4j
public class SpoonProcessingMojo extends AbstractMojo {

  @Builder
  @Getter
  public static class Context {

    private final boolean shouldCompile;
    private final boolean autoImports;
    private final boolean noClasspath;

    @NonNull
    private final File unpackDirectory;

    @NonNull
    private final File outputDirectory;

    @NonNull
    private final String camundaVersion;

    @NonNull
    private final MojoExecutor.ExecutionEnvironment executionEnvironment;

    private final File dtoYamlFile;
    private final File serviceYamlFile;

    // late init
    private DtoDocs dtoDocumentation;
    // late init
    private ServiceDocs serviceDocumentation;

    @SneakyThrows
    public Context initDirectory() {
      if (!unpackDirectory.exists()) {
        Files.createDirectories(unpackDirectory.toPath());
      }

      return this;
    }

    @SneakyThrows
    private SpoonAPI spoon() {
      final Launcher spoon = new Launcher();
      spoon.getEnvironment().setShouldCompile(shouldCompile);
      spoon.getEnvironment().setAutoImports(autoImports);
      spoon.getEnvironment().setNoClasspath(noClasspath);

      spoon.addProcessor(new ApiModelProcessor());
      spoon.addProcessor(new ApiModelPropertyProcessor(this));
      spoon.addProcessor(new RestResourceAndRestServiceMethodProcessor());
      spoon.addProcessor(new RestServiceProcessor(this));
      spoon.addProcessor(new RestServiceMethodProcessor(this));

      final String[] classpathElements = executionEnvironment.getMavenProject()
          .getCompileClasspathElements()
          .stream()
          .filter(s -> !executionEnvironment.getMavenProject().getBuild().getOutputDirectory().equals(s))
          .toArray(String[]::new);

      final StringBuilder builder = new StringBuilder();
      Arrays.stream(classpathElements).forEach(builder::append);
      log.debug("Using classpath: {}", builder.toString());

      spoon.getEnvironment().setSourceClasspath(classpathElements);

      spoon.addInputResource(unpackDirectory.getPath());
      spoon.setSourceOutputDirectory(outputDirectory.getPath());

      return spoon;
    }

    public Context loadDtoDocumentation() {
      this.dtoDocumentation = new DtoDocsYaml().apply(dtoYamlFile);
      return this;
    }

    public Context loadServiceDocumentation() {
      this.serviceDocumentation = new ServiceDocsYaml().apply(serviceYamlFile);
      return this;
    }


    public Context downloadSources() {
      new DownloadCamundaSources(this).run();
      return this;
    }

    public Context processSources() {
      spoon().run();
      return this;
    }
  }

  public static final String GOAL = "process";

  @Parameter(property = "camunda.version", required = true)
  protected String camundaVersion;

  @Parameter(property = "project", required = true, readonly = true)
  protected MavenProject project;

  @Parameter(property = "session", required = true, readonly = true)
  protected MavenSession session;

  @Parameter(defaultValue = "target/unpack")
  protected File unpackDirectory;

  @Parameter(defaultValue = "target/generated-sources/java")
  protected File outputDirectory;

  @Parameter(defaultValue = "${project.build.directory}/generated-sources/camunda-rest-dto-docs.yaml", required = true, readonly = true)
  protected File dtoYamlFile;

  @Parameter(defaultValue = "${project.build.directory}/generated-sources/camunda-rest-service-docs.yaml", required = true, readonly = true)
  protected File serviceYamlFile;

  @Component
  protected BuildPluginManager buildPluginManager;

  @Override
  @SneakyThrows
  public void execute() throws MojoExecutionException, MojoFailureException {
    Context.builder()
    .executionEnvironment(executionEnvironment(project, session, buildPluginManager))
    .camundaVersion(camundaVersion)
    .unpackDirectory(unpackDirectory)
    .outputDirectory(outputDirectory)
    .noClasspath(false)
    .autoImports(false)
    .shouldCompile(false)
    .dtoYamlFile(dtoYamlFile)
    .serviceYamlFile(serviceYamlFile)
    .build()
    .initDirectory()
    .downloadSources()
    .loadDtoDocumentation()
    .loadServiceDocumentation()
    .processSources()
    ;
  }

}
