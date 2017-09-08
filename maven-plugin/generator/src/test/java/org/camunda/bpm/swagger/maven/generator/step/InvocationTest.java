package org.camunda.bpm.swagger.maven.generator.step;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvocationTest {

  @Test
  public void testParameterName() throws Exception {

    final Method[] methods = Foo.class.getDeclaredMethods();
    for (final Method m : methods) {
      final Parameter[] all = m.getParameters();
      final Parameter param = all[0];
      final String paramName = Invocation.paramName(param, all);
      log.info(paramName);
      assertTrue( all.length == 1 && !paramName.matches(".*\\d+.*") || all.length > 1 && paramName.matches(".*\\d+.*"));
    }
  }


  interface Foo {
    void methodTwo(String x, String y);
    void methodOne(String x);
  }
}
