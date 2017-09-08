package org.camunda.bpm.swagger.maven.generator;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.maven.generator.step.Invocation;
import org.camunda.bpm.swagger.maven.model.CamundaDto;
import org.camunda.bpm.swagger.maven.spi.CodeGenerator;

import com.helger.jcodemodel.JAnnotationUse;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwaggerDtoModelGenerator implements CodeGenerator {

  private static final String DTO_PARAM = "dto";
  private final CamundaDto camundaDto;
  private final JCodeModel codeModel;
  private final RestOperation doc;

  public SwaggerDtoModelGenerator(final CamundaDto camundaDto) {
    this.camundaDto = camundaDto;
    this.codeModel = camundaDto.getCodeModel();
    this.doc = camundaDto.getRestOperation();
    log.debug("Processing DTO: {}", camundaDto.getFullQualifiedName());
  }

  @Override
  public JCodeModel generate() {

    final JDefinedClass c = camundaDto.getDefinedClass();
    c.annotate(ApiModel.class);
    c._extends(camundaDto.getBaseClass());

    // generate constructors
    Arrays.stream(camundaDto.getBaseClass().getConstructors()).forEach(constructor -> {
      new Invocation(c.constructor(constructor.getModifiers())).constructor(constructor);
    });

    // create constructor with base type as parameter
    final JMethod dtoConstructor = c.constructor(JMod.PUBLIC);
    dtoConstructor.param(camundaDto.getBaseClass(), DTO_PARAM);
    new Invocation(dtoConstructor).dtoConstructor(camundaDto.getBaseClass(), DTO_PARAM);

    // generate getters
    final List<Method> getters = Arrays.stream(camundaDto.getBaseClass().getDeclaredMethods()).filter(m -> m.getName().startsWith("get"))
        .filter(m -> Modifier.isPublic(m.getModifiers())).collect(Collectors.toList());
    for (final Method m : getters) {
      final JMethod method = c.method(JMod.PUBLIC, m.getReturnType(), m.getName());
      method.annotate(Override.class);

      final String fieldname = StringHelper.getFieldnameFromGetter(m.getName());

      // TODO Hook with the dictionary
      final JAnnotationUse getterAnnotation = method.annotate(ApiModelProperty.class);
      if (doc != null) {
        // get info from doc and add getterAnnotation
      }
      getterAnnotation.param("example", "some example");
      getterAnnotation.param("value", "description of the property");
      method.body()._return(JExpr._super().invoke(m.getName()));
    }

    return this.codeModel;
  }

}