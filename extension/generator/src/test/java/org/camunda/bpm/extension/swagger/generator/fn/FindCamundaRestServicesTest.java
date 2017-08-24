package org.camunda.bpm.extension.swagger.generator.fn;

import org.junit.Ignore;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Ignore
public class FindCamundaRestServicesTest {

  private final FindRestServices find = new FindRestServices(new ReflectionsFactory().get());

  @Test
  public void run() throws Exception {
    find.get().forEach(System.out::println);
  }


}
