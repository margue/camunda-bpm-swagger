package org.camunda.bpm.swagger.docs;

import org.camunda.bpm.swagger.docs.model.DocStyleOperationPair;
import org.camunda.bpm.swagger.docs.model.DtoDocumentation;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DtoDocumentationYamlTest {

  private final File  file = new File("src/test/resources/camunda-rest-dto-docs.yaml");

  private final DtoDocumentationYaml yaml = new DtoDocumentationYaml();

  @Test
  public void read() throws Exception {

    DtoDocumentation dtoDocumentation = yaml.apply(file);

    assertThat(dtoDocumentation).isNotNull();

    List<DocStyleOperationPair> docStyleOperationPairs = dtoDocumentation.get("org.camunda.bpm.engine.rest.sub.history.HistoricVariableInstanceResource");

    assertThat(docStyleOperationPairs).isNotNull().hasSize(1);

    DocStyleOperationPair p = docStyleOperationPairs.get(0);

    assertThat(p.getDocStyle()).isEqualTo("RETURN_TYPE");
    assertThat(p.getRestOperation().getDescription()).isEqualTo("Retrieves a variable by id.");
    assertThat(p.getRestOperation().getMethod()).isEqualTo("GET");

  }
}
