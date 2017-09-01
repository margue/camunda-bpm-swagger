package org.camunda.bpm.swagger.maven.generator.step;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class ReturnListTest {

  @Test
  public void testReturn() {

  }

  class Foo {
    public List<Some> getSome() {
      return null;
    }
  }

  class Bar extends Foo {

    public List<SomeOther> getSomeList() {
      return super.getSome().stream().map(o -> new SomeOther(o)).collect(Collectors.toList());
    }
  }

  class Some {

  }

  class SomeOther extends Some {
    public SomeOther(final Some o) {

    }
  }
}
