package org.camunda.bpm.swagger.maven.generator;

public class TypeHelper {

  static boolean isVoid(final Class<?> returnType) {
    return "void".equals(returnType.getName());
  }

  public static boolean isResource(final Class<?> clazz) {
    return clazz.isInterface() && clazz.getName().endsWith("Resource");
  }

  public static boolean isDto(final Class<?> clazz) {
    return clazz.getName().endsWith("Dto");
  }

}
