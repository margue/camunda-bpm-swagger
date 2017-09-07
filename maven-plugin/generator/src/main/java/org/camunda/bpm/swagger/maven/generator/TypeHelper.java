package org.camunda.bpm.swagger.maven.generator;

import java.util.List;
import java.util.Map;

import com.helger.jcodemodel.AbstractJClass;

public class TypeHelper {

  public static boolean isVoid(final Class<?> returnType) {
    return "void".equals(returnType.getName());
  }

  public static boolean isResource(final Class<?> clazz) {
    return clazz.isInterface() && clazz.getName().endsWith("Resource");
  }

  public static boolean isDto(final Class<?> clazz) {
    return clazz.getName().endsWith("Dto");
  }

  public static boolean isString(final AbstractJClass abstractJClass) {
    return "String".equals(abstractJClass.name());
  }

  public static boolean isMap(final Class<?> clazz) {
    return clazz.isAssignableFrom(Map.class);
  }

  public static boolean isList(final Class<?> clazz) {
    return clazz.isAssignableFrom(List.class);
  }

}
