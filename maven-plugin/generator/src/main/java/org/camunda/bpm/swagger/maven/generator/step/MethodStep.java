package org.camunda.bpm.swagger.maven.generator.step;

import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.swagger.docs.DocumentationYaml;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.docs.model.RestService;
import org.camunda.bpm.swagger.maven.generator.ParentInvocation;
import org.camunda.bpm.swagger.maven.generator.ReturnTypeInfo;
import org.camunda.bpm.swagger.maven.generator.StringHelper;
import org.camunda.bpm.swagger.maven.generator.TypeHelper;
import org.camunda.bpm.swagger.maven.model.DocStyle;
import org.camunda.bpm.swagger.maven.model.ModelRepository;
import com.helger.jcodemodel.AbstractJType;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class MethodStep {

  public enum ReturnTypeStyle {
    PLAIN, DTO, DTO_LIST, DTO_MAP_STRING
  }

  private final ModelRepository modelRepository;
  private final RestService restService;
  private final JDefinedClass clazz;

  private JMethod method;
  private Class<?> returnType;
  private ReturnTypeStyle returnTypeStyle;
  private AbstractJType methodReturnType;
  private String path;

  public MethodStep(final ModelRepository modelRepository, final RestService restService, final JDefinedClass clazz) {
    this.modelRepository = modelRepository;
    this.restService = restService;
    this.clazz = clazz;
  }

  public JMethod create(final ReturnTypeInfo info, final Pair<String, String> pathPrefix, final Map<Pair<String, String>, RestOperation> docs,
      final ParentInvocation... parentInvocations) {

    this.returnType = info.getRawType();

    // determine method name and return type
    Optional<TypeStep> dto = Optional.empty();
    if (info.isParametrized()) {
      this.methodReturnType = this.clazz.owner().ref(info.getRawType()).narrow(info.getParameterTypes());
      if (TypeHelper.isList(info.getRawType())) {
        this.returnTypeStyle = ReturnTypeStyle.DTO_LIST;
      } else if (TypeHelper.isMap(info.getRawType()) // map
          && info.getParameterTypes().length == 2 // parametrized
          && TypeHelper.isString(info.getParameterTypes()[0])) { // with string as key
        this.returnTypeStyle = ReturnTypeStyle.DTO_MAP_STRING;
      } else {
        // doesn't support return parametrized type
        log.debug("Unsupported return collection type with {} type parameters:", info.getParameterTypes().length, info.getRawType().getName());
        this.returnTypeStyle = ReturnTypeStyle.PLAIN;
      }
    } else {
      dto = Optional.of(new TypeStep(modelRepository, info.getRawType(), clazz.owner()));
      this.returnTypeStyle = dto.get().isDto() ? ReturnTypeStyle.DTO : ReturnTypeStyle.PLAIN;
      this.methodReturnType = dto.get().getType();
    }

    final String methodName = methodName(parentInvocations, info.getMethod().getName());
    method = clazz.method(JMod.PUBLIC, methodReturnType, methodName);

    // path annotation
    final PathAnnotationStep pathAnnotationStep = new PathAnnotationStep(this);
    pathAnnotationStep.annotate(pathPrefix.getRight(), info.getMethod());
    this.path = pathAnnotationStep.getPath();

    // JAX RS
    final JaxRsAnnotationStep jaxrsAnnotation = new JaxRsAnnotationStep(method);
    jaxrsAnnotation.annotate(info.getMethod());

    // consume and produce
    final ConsumesAndProducesStep consumesAndProduces = new ConsumesAndProducesStep(method);
    consumesAndProduces.annotate(info.getMethod());

    final Pair key = Pair.of(DocumentationYaml.normalizePath(pathPrefix.getLeft() + this.path), jaxrsAnnotation.getType().getSimpleName());
    final RestOperation doc = docs.get(key);
    if (doc == null) {
      log.error("No doc found for {}", key);
    }

    // register docs for this DTO.
    if (dto.isPresent()) {
      modelRepository.addDoc(dto.get().getFullQualifiedName(), doc, DocStyle.RETURN_TYPE);
    }

    // create invocation
    final JInvocation invoke = new InvocationStep(method).method(modelRepository, info.getMethod(), doc, parentInvocations);

    // body
    if (TypeHelper.isVoid(getReturnType())) {
      method.body().add(invoke);
    } else {

      // overriding, only if it is a simple return type (not parametrized, not DTO, not a resource)
      if (parentInvocations == null) {
        method.annotate(Override.class);
      }

      method.body()._return(invoke);
    }

    // ApiOperation
    final ApiOperationStep apiOperation = new ApiOperationStep(method, doc);
    apiOperation.annotate(this, info.getMethod());
    // add method
    restService.getOperations().put(info.getMethod().getName(), doc);

    // ApiResponse
    final ApiResponsesStep responesStep = new ApiResponsesStep(method, doc);
    responesStep.annotate(this, info.getMethod());

    return method;
  }

  static String methodName(final ParentInvocation[] parentInvocations, final String name) {
    final StringBuilder builder = new StringBuilder();
    for (final ParentInvocation parentInvocation : parentInvocations) {
      builder.append(StringHelper.toFirstUpper(parentInvocation.getMethodName()));
    }
    builder.append(StringHelper.toFirstUpper(name));
    return StringHelper.toFirstLower(builder.toString());
  }
}
