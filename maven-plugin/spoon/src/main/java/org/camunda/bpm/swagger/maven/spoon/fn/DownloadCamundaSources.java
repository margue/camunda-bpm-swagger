package org.camunda.bpm.swagger.maven.spoon.fn;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Plugin;
import org.camunda.bpm.swagger.maven.spoon.SpoonProcessingMojo;
import org.twdata.maven.mojoexecutor.MojoExecutor;
import org.twdata.maven.mojoexecutor.MojoExecutor.ExecutionEnvironment;

import static org.twdata.maven.mojoexecutor.MojoExecutor.artifactId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.groupId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.version;

@Slf4j
public class DownloadCamundaSources implements Runnable {

  private static final Plugin DEPENDENCY_PLUGIN = MojoExecutor.plugin(
    groupId("org.apache.maven.plugins"),
    artifactId("maven-dependency-plugin"),
    version("3.0.1")
  );


  private final SpoonProcessingMojo.Context context;

  public DownloadCamundaSources(SpoonProcessingMojo.Context context) {
    this.context = context;
  }

  @Override
  @SneakyThrows
  public void run() {
    Preconditions.checkState(context.getUnpackDirectory().exists());
    Preconditions.checkState(context.getUnpackDirectory().isDirectory());
    Preconditions.checkState(context.getUnpackDirectory().canWrite());

    MojoExecutor.executeMojo(
      DEPENDENCY_PLUGIN,
      "unpack",
      configuration(
        element("artifactItems",
          element("artifactItem",
            element("groupId", "org.camunda.bpm"),
            element("artifactId", "camunda-engine-rest-core"),
            element("version", context.getCamundaVersion()),
            element("classifier", "sources")
          )
        ),
        element("outputDirectory", context.getUnpackDirectory().getPath())
      ),
      context.getExecutionEnvironment());
  }
}
