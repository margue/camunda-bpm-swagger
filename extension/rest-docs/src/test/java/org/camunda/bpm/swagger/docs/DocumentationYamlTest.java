package org.camunda.bpm.swagger.docs;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentationYamlTest {
  @Ignore
  @Test
  public void name() throws Exception {
    final Map<Pair<String, String>, RestOperation> map = new DocumentationYaml().get();

    map.keySet().forEach(System.out::println);

    // System.out.println(map.get(Pair.of("/task/{id}/complete", "POST")).getResponseCodes());
  }

  @Test
  public void normalizePath() {
    assertThat(DocumentationYaml.normalizePath("This . is . a . text")).isEqualTo("This . is . a . text");
    assertThat(DocumentationYaml.normalizePath("/path/to/some/resource/")).isEqualTo("/path/to/some/resource/");
    assertThat(DocumentationYaml.normalizePath("/path/to/{some}/resource/")).isEqualTo("/path/to/{}/resource/");
    assertThat(DocumentationYaml.normalizePath("/path/to/{some}/{resource}/")).isEqualTo("/path/to/{}/{}/");

  }

}
