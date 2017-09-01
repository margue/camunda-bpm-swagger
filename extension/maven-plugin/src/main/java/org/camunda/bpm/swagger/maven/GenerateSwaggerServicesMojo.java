package org.camunda.bpm.swagger.maven;

import lombok.SneakyThrows;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.camunda.bpm.swagger.docs.DocumentationYaml;
import org.camunda.bpm.swagger.maven.fn.ReflectionsFactory;
import org.camunda.bpm.swagger.maven.fn.ScanRestServices;
import org.camunda.bpm.swagger.maven.generator.SwaggerCodeGeneratorFactory;
import org.camunda.bpm.swagger.maven.model.CamundaRestService;
import org.camunda.bpm.swagger.maven.spi.CodeGenerator;
import org.camunda.bpm.swagger.maven.spi.CodeGeneratorFactory;
import org.reflections.Reflections;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_SOURCES;
import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;
import static org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo.GOAL;

@Mojo(
  name = GOAL,
  defaultPhase = GENERATE_SOURCES,
  requiresDependencyResolution = COMPILE_PLUS_RUNTIME
)
public class GenerateSwaggerServicesMojo extends AbstractMojo {

  public static DocumentationYaml DOCUMENTATION;

  public static final String CAMUNDA_REST_ROOT_PKG = "org.camunda.bpm.engine.rest";
  public static final String GOAL = "generate-swagger-services";

  @Parameter(defaultValue = "${project}", required = true, readonly = true)
  private MavenProject project;

  @Parameter(property = "outputDirectory", required = true, defaultValue = "${project.build.directory}/generated-sources/java")
  protected File outputDirectory;

  @Parameter(property = "codeGeneratorFactory", required = true, defaultValue = "org.camunda.bpm.swagger.maven.generator.SwaggerCodeGeneratorFactory")
  protected String codeGeneratorFactoryClass;

  protected final CodeGeneratorFactory codeGeneratorFactory = new SwaggerCodeGeneratorFactory();

  @Override
  @SneakyThrows
  public void execute() throws MojoExecutionException, MojoFailureException {
    DOCUMENTATION = new DocumentationYaml();

    if (!outputDirectory.exists()) {
      Files.createDirectories(outputDirectory.toPath());
    }
    Optional.ofNullable(project).ifPresent(p -> p.addCompileSourceRoot(outputDirectory.getPath()));

    final Reflections reflections = new ReflectionsFactory().get();
    final ScanRestServices scanRestServices = new ScanRestServices(reflections);

    Set<CamundaRestService> camundaRestServices = scanRestServices.get();

    getLog().info("==================");
    getLog().info("processing Services: " + camundaRestServices);

    camundaRestServices.stream()
      .map(codeGeneratorFactory::createCodeGenerator)
      .map(CodeGenerator::generate)
      .forEach(r -> r.write(outputDirectory));

    getLog().info("==================");
  }

}
