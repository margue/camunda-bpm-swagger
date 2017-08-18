package org.camunda.bpm.extension.swagger.generator.model;

import lombok.Builder;
import lombok.SneakyThrows;
import lombok.Value;

import java.lang.reflect.Field;

@Value
public class CamundaRestService {

  static String splitCamelCase(String s) {
    return s.replaceAll(
      String.format("%s|%s|%s",
        "(?<=[A-Z])(?=[A-Z][a-z])",
        "(?<=[^A-Z])(?=[A-Z])",
        "(?<=[A-Za-z])(?=[^A-Za-z])"
      ),
      " "
    );
  }

  private Class<?> serviceClass;

  private Class<?> serviceImplClass;

  public String getSimpleName() {
    return serviceClass.getSimpleName();
  }

  public Package getPackage() {
    return serviceClass.getPackage();
  }

  public String getPackageName() {
    return getPackage().getName();
  }

  @SneakyThrows
  public String getPath() {
    Field field = serviceClass.getDeclaredField("PATH");

    return (String) field.get(null);
  }

  public String getTag() {
    return splitCamelCase(getSimpleName()).split(" ")[0];
  }

  public String getName() {
    String[] n = splitCamelCase(getSimpleName()).split(" ");
    return n[0] + " " + n[2] ;
  }
}
