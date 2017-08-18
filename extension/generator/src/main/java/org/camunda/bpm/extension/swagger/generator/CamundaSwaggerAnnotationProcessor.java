package org.camunda.bpm.extension.swagger.generator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes(GenerateSwagger.NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CamundaSwaggerAnnotationProcessor extends AbstractProcessor {
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    for( final Element element: roundEnv.getElementsAnnotatedWith( GenerateSwagger.class ) ) {


      System.out.println("=====");
      System.out.println("    generate for: " + element.toString());
      System.out.println("=====");



    }

    return false;
  }
}
