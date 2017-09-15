package org.camunda.bpm.swagger.maven;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.vladsch.flexmark.ast.Node;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.camunda.bpm.swagger.maven.interpreter.DocumentInterpreter;
import org.camunda.bpm.swagger.maven.model.RestOperation;
import org.camunda.bpm.swagger.maven.model.RestOperations;
import org.camunda.bpm.swagger.maven.parser.DocumentParser;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_RESOURCES;
import static org.apache.maven.plugins.annotations.ResolutionScope.COMPILE_PLUS_RUNTIME;
import static org.camunda.bpm.swagger.maven.GenerateDocumentationYamlMojo.GOAL;

@Mojo(name = GOAL, defaultPhase = GENERATE_RESOURCES, requiresDependencyResolution = COMPILE_PLUS_RUNTIME)
public class GenerateDocumentationYamlMojo extends AbstractMojo {
  public static final String GOAL = "generate-documentation-yaml";

  @Parameter(defaultValue = "${project.basedir}/src/main/resources/rest", required = true, readonly = true)
  private File markdownDir;

  @Parameter(defaultValue = "${project.build.outputDirectory}/camunda-rest-docs.yaml", required = true, readonly = true)
  private File yamlFile;

  @Parameter(defaultValue = "https://docs.camunda.org/manual/7.7/reference/rest/", required = true, readonly = true)
  private String camundaDocsBase;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    getLog().info("Working on :" + markdownDir);

    final YamlWriter writer = new YamlWriter(yamlFile);

    final DocumentParser parser = new DocumentParser();
    final DocumentInterpreter interpreter = new DocumentInterpreter(getLog());

    final Function<String, String> prepareFileContents = fileContents ->
      fileContents.replaceAll("\\{\\{[^}]+\\}\\}", "");

    final Function<String, RestOperation> createRestOperation = filename -> {
      final Path path = Paths.get(filename);
      final String fileContents = prepareFileContents.apply(readFileContents(path));
      final Map<String, Node> parsedTree = parser.parse(fileContents);
      final String camundaDocURI = Optional.of(path.toFile().getPath())
        .map(p -> p.substring(p.lastIndexOf("/rest/") + 6, p.lastIndexOf(".")))
        .map(p -> camundaDocsBase + p + "/")
        .orElse(null);
      return Optional.ofNullable(parsedTree)
        .map(parsed -> interpreter.interpret(parsed, camundaDocURI))
        .orElse(null);
    };

    final Supplier<List<RestOperation>> documentations = () -> {
      final WildcardFileFilter fileFilter = new WildcardFileFilter("*.md");
      final Collection<File> files = FileUtils.listFiles(markdownDir, fileFilter, TrueFileFilter.INSTANCE);
      getLog().info("Reading from " + files.size() + " resources");
      return files.stream().map(File::getAbsolutePath).map(createRestOperation).filter(Objects::nonNull)
        .filter(res -> res.getPath() != null && res.getMethod() != null).collect(Collectors.toList());
    };

    writer.accept(documentations.get());

  }

  static class YamlWriter implements Consumer<List<RestOperation>> {

    private final YAMLGenerator generator;

    @SneakyThrows
    public YamlWriter(final File target) {
      if (!target.exists()) {
        target.getParentFile().mkdirs();
        target.createNewFile();
      }
      generator = new YAMLFactory().createGenerator(new PrintWriter(target, "UTF-8"));
      generator.setCodec(new ObjectMapper());
    }

    @Override
    @SneakyThrows
    public void accept(final List<RestOperation> restOperations) {
      final RestOperations wrapper = new RestOperations();
      wrapper.getRestOperations().addAll(restOperations);

      generator.writeObject(wrapper);
    }

  }

  @SneakyThrows
  private String readFileContents(final Path path) {
    return new String(Files.readAllBytes(path));
  }

}
