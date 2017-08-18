package org.camunda.bpm.extension.swagger.generator.fn;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.function.Supplier;

import static org.camunda.bpm.extension.swagger.generator.CamundaSwaggerAnnotationProcessor.CAMUNDA_REST_ROOT_PKG;

/**
 * Supplier for reflections from Camunda REST package.
 * 
 * @author Simon Zambrovski Holisticon AG
 * @author Jan Galinski, Holisticon AG
 *
 */
public class ReflectionsFactory implements Supplier<Reflections> {

  private final Reflections reflections;

  public ReflectionsFactory() {
    this.reflections = new Reflections(new ConfigurationBuilder()
      .setUrls(ClasspathHelper.forPackage(CAMUNDA_REST_ROOT_PKG))
      .setScanners(new SubTypesScanner(false))
    );
  }

  @Override
  public Reflections get() {
    return reflections;
  }
}
