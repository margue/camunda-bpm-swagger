package org.camunda.bpm.swagger.maven.model;

import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import lombok.Getter;
import lombok.SneakyThrows;
import org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo;

import javax.annotation.Generated;
import java.io.File;
import java.lang.reflect.Field;

import static org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo.CAMUNDA_REST_ROOT_PKG;


public class CamundaRestService {

  public static final String PACKAGE = CAMUNDA_REST_ROOT_PKG + ".swagger";

  @Getter
  private final Class<?> serviceInterfaceClass;

  @Getter
  private final Class<?> serviceImplClass;

  @Getter
  private final JCodeModel codeModel;

  @Getter
  private final JDefinedClass definedClass;

  @SneakyThrows
  public CamundaRestService(final Class<?> serviceInterfaceClass, final Class<?> serviceImplClass) {
    if (!serviceInterfaceClass.isAssignableFrom(serviceImplClass)) {
      throw new IllegalStateException(String.format("%s does not implement %s", serviceImplClass, serviceInterfaceClass));
    }

    this.serviceInterfaceClass = serviceInterfaceClass;
    this.serviceImplClass = serviceImplClass;

    this.codeModel = new JCodeModel();
    this.codeModel._package(PACKAGE);
    definedClass = this.codeModel._class(getFullQualifiedName());
    definedClass.annotate(Generated.class).param("value", GenerateSwaggerServicesMojo.class.getCanonicalName());

  }

  public String getFullQualifiedName() {
    return String.format("%s.%sSwagger", PACKAGE, getSimpleName());
  }


  public String getSimpleName() {
    return serviceInterfaceClass.getSimpleName();
  }

  public Package getPackage() {
    return serviceInterfaceClass.getPackage();
  }

  public String getPackageName() {
    return getPackage().getName();
  }

  public String getTag() {
    return splitCamelCase(getSimpleName()).split(" ")[0];
  }

  public String getName() {
    String[] n = splitCamelCase(getSimpleName()).split(" ");
    return n[0] + " " + n[2] ;
  }

  @SneakyThrows
  public String getPath() {
    Field field = serviceInterfaceClass.getDeclaredField("PATH");
    return field != null ? (String) field.get(null) : "";
  }

  public static String splitCamelCase(String s) {
    return s.replaceAll(
      String.format("%s|%s|%s",
        "(?<=[A-Z])(?=[A-Z][a-z])",
        "(?<=[^A-Z])(?=[A-Z])",
        "(?<=[A-Za-z])(?=[^A-Za-z])"
      ),
      " "
    );
  }

  @SneakyThrows
  public void write(final File destination) {
    if (destination == null || !destination.canWrite() || !destination.exists() || !destination.isDirectory()) {
      throw new IllegalStateException("cannot write to " + destination);
    }
    getCodeModel().build(destination);
  }

}
