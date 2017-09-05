package org.camunda.bpm.swagger.maven.generator.step;

import java.util.List;
import java.util.Map;
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

    public Map<String, Some> getSomeAsMap() {
      return null;
    }
  }

  class Bar extends Foo {

    public List<SomeOther> getSomeList() {
      return super.getSome().stream().map(o -> new SomeOther(o)).collect(Collectors.toList());
    }

    public Map<String, SomeOther> getSomeAsMapMap() {
      return super.getSomeAsMap().entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e -> new SomeOther(e.getValue())));
    }
  }

  class Some {

  }

  class SomeOther extends Some {
    public SomeOther(final Some o) {

    }
  }
}
