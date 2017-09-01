package org.camunda.bpm.swagger.docs;


import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;

@Ignore
public class DocumentationYamlTest {
  @Test
  public void name() throws Exception {
    Map<String, Map<String, RestOperation>> map = new DocumentationYaml().get();


    map.keySet().forEach(System.out::println);


    System.out.println(map.get("/task/{id}/complete").get("POST").getResponseCodes());

  }
}
