package org.camunda.bpm.swagger.docs;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DocumentationYamlTest {
  @Test
  public void name() throws Exception {
    final Map<Pair<String, String>, RestOperation> map = new DocumentationYaml().get();

    map.keySet().forEach(System.out::println);

    System.out.println(map.get(Pair.of("/task/{id}/complete", "POST")).getResponseCodes());

  }
}
