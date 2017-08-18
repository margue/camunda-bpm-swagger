package org.camunda.bpm.extension.swagger.generator.fn;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reflections.Reflections;

import java.util.Set;

@Slf4j
public class FindCamundaRestServicesTest {

  private final FindRestServices find = new FindRestServices(new ReflectionsFactory().get());

  @Test
  public void run() throws Exception {
    find.get().forEach(System.out::println);
  }



  @Test
  public void name() throws Exception {
    find.get().stream().map(fqn -> {
      try {
        return Class.forName(fqn);
      } catch (ClassNotFoundException e) {
        log.error(e.getMessage());
        return Object.class;
      }
    }).forEach(c -> log.info(c.getCanonicalName()));
    ;
  }

  @Test
  public void k1() throws Exception {
    new ReflectionsFactory().get().getSubTypesOf(Object.class).forEach(System.out::println);
  }
}
