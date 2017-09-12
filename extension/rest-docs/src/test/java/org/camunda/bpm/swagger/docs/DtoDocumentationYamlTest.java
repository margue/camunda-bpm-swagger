package org.camunda.bpm.swagger.docs;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Map;

import org.camunda.bpm.swagger.docs.model.DtoDocs;
import org.camunda.bpm.swagger.docs.model.ParameterDescription;
import org.junit.Test;

public class DtoDocumentationYamlTest {

  private final File  file = new File("src/test/resources/camunda-rest-dto-docs.yaml");

  private final DtoDocsYaml yaml = new DtoDocsYaml();

  @Test
  public void read() throws Exception {

    final DtoDocs dtoDocumentation = yaml.apply(file);
    assertThat(dtoDocumentation).isNotNull();

    final Map<String, ParameterDescription> doc = dtoDocumentation.get("org.camunda.bpm.engine.rest.dto.migration.MigrationPlanDto");
    assertThat(doc).isNotNull().hasSize(4);

    final ParameterDescription p = doc.get("instructions");
    assertThat(p.getDescription()).startsWith("A list of migration instructions which map equal activities.");
    assertThat(p.getType()).isEqualTo(null);

  }
}
