package org.camunda.bpm.swagger.maven;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_RESOURCES;
import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;
import static org.camunda.bpm.swagger.maven.GenerateSwaggerJsonMojo.GOAL;
import static org.twdata.maven.mojoexecutor.MojoExecutor.artifactId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;
import static org.twdata.maven.mojoexecutor.MojoExecutor.groupId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;
import static org.twdata.maven.mojoexecutor.MojoExecutor.version;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.camunda.bpm.swagger.maven.model.CamundaRestService;
import org.twdata.maven.mojoexecutor.MojoExecutor;
import org.twdata.maven.mojoexecutor.MojoExecutor.ExecutionEnvironment;

/**
 * Generates swagger.json from annotated swagger rest services,
 * using this <a href="https://github.com/kongchen/swagger-maven-plugin">maven plugin</a>.
 * <p>
 * Encapsulating the plugin makes the configuration of the generation much easier, since only a few
 * required fields have to be set.
 *
 * @author Jan Galinski, Holisticon AG
 */
@Mojo(
  name = GOAL,
  defaultPhase = GENERATE_RESOURCES,
  requiresDependencyResolution = COMPILE_PLUS_RUNTIME
)
public class GenerateSwaggerJsonMojo extends AbstractMojo {

  public static final String GOAL = "generate-swagger-json";

  private static final String[] CORE_API = {
    "org.camunda.bpm.engine.rest.AuthorizationRestService",
    "org.camunda.bpm.engine.rest.BatchRestService",
    "org.camunda.bpm.engine.rest.CaseDefinitionRestService",
    "org.camunda.bpm.engine.rest.CaseExecutionRestService",
    "org.camunda.bpm.engine.rest.DecisionDefinitionRestService",
    "org.camunda.bpm.engine.rest.DecisionRequirementDefinitionRestService",
    "org.camunda.bpm.engine.rest.DeploymentRestService",
    "org.camunda.bpm.engine.rest.ExecutionRestService",
    "org.camunda.bpm.engine.rest.ExternalTaskRestService",
    "org.camunda.bpm.engine.rest.FilterRestService",
    "org.camunda.bpm.engine.rest.GroupRestService",
    "org.camunda.bpm.engine.rest.IdentityRestService",
    "org.camunda.bpm.engine.rest.JobDefinitionRestService",
    "org.camunda.bpm.engine.rest.JobRestService",
    "org.camunda.bpm.engine.rest.MessageRestService",
    "org.camunda.bpm.engine.rest.MigrationRestService",
    "org.camunda.bpm.engine.rest.ModificationRestService",
    "org.camunda.bpm.engine.rest.ProcessDefinitionRestService",
    "org.camunda.bpm.engine.rest.ProcessInstanceRestService",
    "org.camunda.bpm.engine.rest.TaskRestService",
    "org.camunda.bpm.engine.rest.TenantRestService",
    "org.camunda.bpm.engine.rest.UserRestService",
    "org.camunda.bpm.engine.rest.VariableInstanceRestService",
  };

  @Parameter(property = "camunda.version", required = true)
  protected String camundaVersion;

  @Parameter(property = "project", required = true, readonly = true)
  protected MavenProject project;

  @Parameter(property = "session", required = true, readonly = true)
  protected MavenSession session;

  @Parameter(property = "swaggerDirectory", required = true, defaultValue = "${project.build.outputDirectory}/META-INF/resources")
  protected String swaggerDirectory;

  @Parameter(property = "basePath", required = true, defaultValue = "/engine-rest/engine/default")
  protected String basePath;

  @Parameter(property = "host", required = true, defaultValue = "localhost:8080")
  protected String host;

  @Component
  protected BuildPluginManager buildPluginManager;

  private static final Plugin SWAGGER_PLUGIN = plugin(
    groupId("com.github.kongchen"),
    artifactId("swagger-maven-plugin"),
    version("3.1.5"),
    Collections.emptyList()
  );

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    final ExecutionEnvironment environment = executionEnvironment(project, session, buildPluginManager);

    executeMojo(SWAGGER_PLUGIN, "generate", configuration(element("apiSources", apiSource(CORE_API))), environment);

    getLog().info(String.format("generated swagger.json in '%s'", swaggerDirectory));
  }

  /**
   * Creates an api source element for a list of locations (packages or classes).
   *
   * @param locations list of class names or packages
   * @return a source element
   */
  MojoExecutor.Element apiSource(final String... locations) {

    final List<MojoExecutor.Element> coreLocations = new ArrayList<>();
    Arrays.stream(locations).forEach(location -> coreLocations.add(element("location", location)));

    return element("apiSource",
      element("swaggerDirectory", swaggerDirectory),
      element("attachSwaggerArtifact", "true"),
      element("springmvc", "false"),
      element("schemes", "http"),
      element("host", host),
      element("basePath", basePath),
      element("info",
        element("title", "Camunda REST API"),
        element("description", "Swagger OpenApi Spec for Camunda REST API."),
        element("version", "v" + camundaVersion),
        element("license",
          element("url", "http://www.apache.org/licenses/LICENSE-2.0.html"),
          element("name", "Apache 2.0")
        )
      ),
      element("locations", coreLocations.toArray(new MojoExecutor.Element[coreLocations.size()]))
    );

  }
}
