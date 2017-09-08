package org.camunda.bpm.swagger.maven.generator.step;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.maven.generator.ParentInvocation;
import org.camunda.bpm.swagger.maven.generator.ReturnTypeInfo;
import org.camunda.bpm.swagger.maven.generator.StringHelper;
import org.camunda.bpm.swagger.maven.generator.TypeHelper;
import org.camunda.bpm.swagger.maven.model.CamundaDto;
import org.camunda.bpm.swagger.maven.model.ModelRepository;

import com.helger.jcodemodel.AbstractJType;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JLambda;
import com.helger.jcodemodel.JLambdaParam;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Data
@Slf4j
public class MethodStep {

  public enum ReturnTypeStyle {
    PLAIN, DTO, DTO_LIST, DTO_MAP_STRING;
  }

  private final ModelRepository modelRepository;
  private final JDefinedClass clazz;
  private JMethod method;
  private Class<?> returnType;
  private ReturnTypeStyle returnTypeStyle;
  private AbstractJType methodReturnType;
  private String path;

  public JMethod create(final ReturnTypeInfo info, final Pair<String, String> pathPrefix, final Map<Pair<String, String>, RestOperation> docs,
      final ParentInvocation... parentInvocations) {

    this.returnType = info.getRawType();

    // determine method name and return type
    Optional<DtoStep> dtoStep = Optional.empty();
    final String methodName;
    if (info.isParametrized()) {
      this.methodReturnType = this.clazz.owner().ref(info.getRawType()).narrow(info.getParameterTypes());

      if (TypeHelper.isList(info.getRawType())) {
        this.returnTypeStyle = ReturnTypeStyle.DTO_LIST;
      } else if (TypeHelper.isMap(info.getRawType()) // map
          && info.getParameterTypes().length == 2 // parameterized
          && TypeHelper.isString(info.getParameterTypes()[0])) { // with string as key
        this.returnTypeStyle = ReturnTypeStyle.DTO_MAP_STRING;
      } else {
        // doesn't support return parameterized type
        log.warn("Unsupported return collection type with {} type parameters:", info.getParameterTypes().length, info.getRawType().getName());
        this.returnTypeStyle = ReturnTypeStyle.PLAIN;
      }

      methodName = methodName(parentInvocations, info.getMethod().getName(), this.returnType);
    } else {
      dtoStep = Optional.of(new DtoStep(modelRepository, info.getRawType()));
      this.returnTypeStyle = dtoStep.get().isDto() ? ReturnTypeStyle.DTO : ReturnTypeStyle.PLAIN;
      this.methodReturnType = dtoStep.get().getType(this.clazz.owner());
      methodName = methodName(parentInvocations, info.getMethod().getName(), null);
    }

    method = clazz.method(JMod.PUBLIC, methodReturnType, methodName);

    // path annotation
    final PathAnnotation pathAnnotationStep = new PathAnnotation(method);
    pathAnnotationStep.annotate(pathPrefix.getRight(), info.getMethod());
    this.path = pathAnnotationStep.getPath();

    // JAX RS
    final JaxRsAnnotation jaxrsAnnotation = new JaxRsAnnotation(method);
    jaxrsAnnotation.annotate(info.getMethod());

    // consume and produce
    final ConsumesAndProduces consumesAndProduces = new ConsumesAndProduces(method);
    consumesAndProduces.annotate(info.getMethod());

    final RestOperation doc = docs.get(Pair.of(pathPrefix.getLeft() + this.path, jaxrsAnnotation.getType().getSimpleName()));

    dtoStep.flatMap(DtoStep::getCamundaDto).ifPresent(dto -> {
      dto.setRestOperation(doc);
    });

    // create invocation
    final JInvocation invoke = new Invocation(method).method(info.getMethod(), doc, parentInvocations);

    // body
    if (TypeHelper.isVoid(getReturnType())) {
      method.body().add(invoke);
    } else {
      switch (getReturnTypeStyle()) {
      case PLAIN:

        // overriding, only if it is a simple return type (not parameterized, not DTO, not a resource)
        if (parentInvocations == null) {
          method.annotate(Override.class);
        }
        method.body()._return(invoke);
        break;
      case DTO:
        method.body()._return(JExpr._new(getMethodReturnType()).arg(invoke));
        break;
      case DTO_LIST:
        // return super.getSome().stream().map(o -> new SomeOther(o)).collect(Collectors.toList());
        final JLambda listMapping = new JLambda();
        listMapping.body()
        .lambdaExpr(JExpr
            ._new( // -> new
                info.getParameterTypes()[0]) // DtoSwagger
            .arg(listMapping.addParam("o"))); // (o)

        method.body()._return( // return
            invoke.invoke("stream") // stream
            .invoke("map") // map
            .arg(listMapping) // lambda
            .invoke("collect") // collect
            .arg(getMethod().owner().ref(Collectors.class).staticInvoke("toList")));

        break;
      case DTO_MAP_STRING:

        final JLambda keyMapping = new JLambda();
        final JLambdaParam keyEntry = keyMapping.addParam("e"); // e
        keyMapping.body().lambdaExpr(keyEntry.invoke("getKey")); // -> e.getKey

        final JLambda valueMapping = new JLambda();
        final JLambdaParam valueEntry = valueMapping.addParam("e"); // e
        valueMapping.body()
        .lambdaExpr(JExpr
            ._new( // -> new
                info.getParameterTypes()[1]) // DtoSwagger
            .arg(valueEntry.invoke("getValue"))); // (e.getValue)

        method.body()._return( // return
            invoke.invoke("entrySet") // entrySet()
            .invoke("stream") // stream()
            .invoke("collect") // collect
            .arg(getMethod().owner().ref(Collectors.class).staticInvoke("toMap") // Collectors.toMap
                .arg(keyMapping) // key lambda
                .arg(valueMapping) // value lambda
                ));

        break;
      default:
        throw new IllegalArgumentException("This is a bug. You changed the enum, but forgot to add a case.");
      }
    }

    final ApiOperation apiOperation = new ApiOperation(method, doc);
    apiOperation.annotate(this, info.getMethod());

    return method;
  }

  static String methodName(final ParentInvocation[] parentInvocations, final String name, final Class<?> relevantReturnType) {
    final StringBuilder builder = new StringBuilder();
    for (final ParentInvocation parentInvocation : parentInvocations) {
      builder.append(StringHelper.toFirstUpper(parentInvocation.getMethodName()));
    }
    builder.append(StringHelper.toFirstUpper(name));
    // Add parameter type name
    if (relevantReturnType != null) {
      builder.append(relevantReturnType.getSimpleName());
    }
    return StringHelper.toFirstLower(builder.toString());
  }

}
