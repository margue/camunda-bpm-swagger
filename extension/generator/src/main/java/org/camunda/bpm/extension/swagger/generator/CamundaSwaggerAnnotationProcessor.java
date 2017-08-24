package org.camunda.bpm.extension.swagger.generator;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import org.camunda.bpm.extension.swagger.generator.fn.CodeWriter;
import org.camunda.bpm.extension.swagger.generator.fn.FindRestServices;
import org.camunda.bpm.extension.swagger.generator.model.CamundaRestService;
import org.reflections.Reflections;

import lombok.SneakyThrows;


// TODO refactor!
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CamundaSwaggerAnnotationProcessor extends AbstractProcessor {

  public static final String CAMUNDA_REST_ROOT_PKG = "org.camunda.bpm.engine.rest";

  private final FindRestServices restServices;

  public CamundaSwaggerAnnotationProcessor() {
    Reflections reflections = new Reflections(CAMUNDA_REST_ROOT_PKG);
    restServices = new FindRestServices(reflections);
  }

  @Override
  @SneakyThrows
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    System.out.println("------ running");
    // if (roundEnv.getElementsAnnotatedWith(GenerateSwagger.class).isEmpty()) {
    // return false;
    // }

    CodeWriter codeWriter = new CodeWriter(processingEnv);

    Set<CamundaRestService> camundaRestServices = restServices.get();
    System.out.println("===== " + camundaRestServices);
    restServices.get().stream().map(SwaggerServiceModelGenerator::new).forEach(generator -> codeWriter.accept(generator.getCodeModel()));
    codeWriter.close();
    
    // TODO:
    // find all services
    // iterate
    // write files

    // CreateSwaggerService service = new CreateSwaggerService();
    // JCodeModel model = service.apply(new CamundaRestService(TaskRestService.class, TaskRestServiceImpl.class));
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

    return false;
  }

}
