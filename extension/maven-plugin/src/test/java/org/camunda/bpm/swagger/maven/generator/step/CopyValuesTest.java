package org.camunda.bpm.swagger.maven.generator.step;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import static org.junit.Assert.assertTrue;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class CopyValuesTest {

  @NoArgsConstructor
  @AllArgsConstructor
  static class Foo {
    private String value;
    private String another;
    public String getValue() {
      return value;
    }
    public void setValue(final String value) {
      this.value = value;
    }
    public String getAnother() {
      return another;
    }
    public void setAnother(final String another) {
      this.another = another;
    }
  }

  static class Bar extends Foo {

    Bar(final Foo dto) {
      super();
      BeanUtils.copyProperties(dto, this);
    }
  }

  @Test
  public void copyPropsTest() {

    final Foo foo = new Foo("value", "another");
    final Bar bar = new Bar(foo);

    assertTrue("value".equals(bar.getValue()));
    assertTrue("another".equals(bar.getAnother()));
  }

}
