package org.camunda.bpm.swagger.maven.generator;

import static org.assertj.core.api.Assertions.assertThat;

import org.camunda.bpm.swagger.maven.generator.ReturnTypeInfo.MethodMatcher;
import org.junit.Test;

public class ReturnTypeInfoTest {

  static interface Iface {
    public BasicType some(String param1, String param2);
  }

  static class Impl implements Iface {
    @Override
    public BasicType2 some(final String param1, final String param2) {
      return null;
    }

    public BasicType2 some(final String param1) {
      return null;
    }
  }

  static class Impl2 implements Iface {
    @Override
    public BasicType some(final String param1, final String param2) {
      return null;
    }

    public BasicType some(final String param1) {
      return null;
    }
  }

  static class BasicType {

  }

  static class BasicType2 extends BasicType {

  }


  @Test
  public void testMatcherPlain() throws Exception {
    final MethodMatcher mm = new MethodMatcher(Iface.class.getDeclaredMethod("some", String.class, String.class));
    assertThat(mm.test(Impl.class.getMethod("some", String.class, String.class))).isTrue();
    assertThat(mm.test(Impl.class.getMethod("some", String.class))).isFalse();
  }

  @Test
  public void testMatcherPlain2() throws Exception {
    final MethodMatcher mm = new MethodMatcher(Iface.class.getDeclaredMethod("some", String.class, String.class));
    assertThat(mm.test(Impl2.class.getMethod("some", String.class, String.class))).isFalse();
    assertThat(mm.test(Impl2.class.getMethod("some", String.class))).isFalse();
  }

  @Test
  public void testMatcherParametrized() throws Exception {
    final MethodMatcher mm = new MethodMatcher(Iface.class.getDeclaredMethod("some", String.class, String.class));
    assertThat(mm.test(Impl2.class.getMethod("some", String.class, String.class))).isFalse();
    assertThat(mm.test(Impl2.class.getMethod("some", String.class))).isFalse();
  }

}
