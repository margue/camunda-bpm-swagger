package org.camunda.bpm.extension.swagger.generator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.camunda.bpm.engine.rest.TaskRestService;
import org.camunda.bpm.engine.rest.impl.TaskRestServiceImpl;
import org.camunda.bpm.extension.swagger.generator.fn.CodeWriter;
import org.camunda.bpm.extension.swagger.generator.fn.CreateSwaggerService;
import org.camunda.bpm.extension.swagger.generator.fn.FindRestServices;
import org.camunda.bpm.extension.swagger.generator.model.CamundaRestResource;
import org.reflections.Reflections;

import com.helger.jcodemodel.AbstractCodeWriter;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JPackage;

@SupportedAnnotationTypes(GenerateSwagger.NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CamundaSwaggerAnnotationProcessor extends AbstractProcessor {

  public static final String CAMUNDA_REST_ROOT_PKG = "org.camunda.bpm.engine.rest";

  private FindRestServices restServices;

  public CamundaSwaggerAnnotationProcessor() {
    Reflections reflections = new Reflections("org.camunda.bpm.engine.rest");
    restServices = new FindRestServices(reflections);
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    if (!roundEnv.getElementsAnnotatedWith(GenerateSwagger.class).isEmpty()) {

      // TODO:
      // find all services
      // iterate
      // write files

      // CreateSwaggerService service = new CreateSwaggerService();
      // JCodeModel model = service.apply(new CamundaRestResource(TaskRestService.class, TaskRestServiceImpl.class));
      //
      // try {
      // model.build(new CodeWriter(processingEnv));
      // } catch (IOException e) {
      // e.printStackTrace();
      // }
      // }

      // for (final Element element : roundEnv.getElementsAnnotatedWith(GenerateSwagger.class)) {
      // if (element.getKind() == ElementKind.CLASS) {
      // TypeElement classElement = (TypeElement) element;
      // PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();
      //
      // try {
      // String className = classElement.getSimpleName() + "BeanInfo";
      // String classFqn = classElement.getQualifiedName() + "BeanInfo";
      //
      //
      //
      // JavaFileObject jfo = processingEnv.getFiler().createSourceFile(classFqn);
      //
      // BufferedWriter bw = new BufferedWriter(jfo.openWriter());
      // bw.append("package ");
      // bw.append(packageElement.getQualifiedName());
      // bw.append(";");
      // bw.newLine();
      // bw.newLine();
      //
      // bw.append("public class " + className + " {}");
      // // rest of generated class contents
      //
      // System.out.println("=====");
      // System.out.println(" generate for: " + element.toString());
      // System.out.println("=====");
      //
      // processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "generating:" + classFqn);
      //
      // bw.close();
      //
      // } catch (IOException e) {
      // e.printStackTrace();
      // }
      // }
      // }
    }
    return false;
  }

}
