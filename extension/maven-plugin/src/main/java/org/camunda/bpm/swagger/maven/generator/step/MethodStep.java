package org.camunda.bpm.swagger.maven.generator.step;

import org.camunda.bpm.swagger.maven.generator.ParentInvocation;
import org.camunda.bpm.swagger.maven.generator.ReturnTypeInfo;
import org.camunda.bpm.swagger.maven.generator.StringHelper;
import org.camunda.bpm.swagger.maven.generator.TypeHelper;
import org.camunda.bpm.swagger.maven.model.ModelRepository;

import com.helger.jcodemodel.AbstractJType;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class MethodStep {

  public enum ReturnTypeStyle {
    PLAIN, DTO, DTO_LIST;
  }

  private final ModelRepository modelRepository;
  private final JDefinedClass clazz;
  private JMethod method;
  private Class<?> returnType;
  private ReturnTypeStyle returnTypeStyle;
  private AbstractJType methodReturnType;
  private String path;

  public JMethod create(final ReturnTypeInfo info, final String parentPathPrefix, final ParentInvocation... parentInvocations) {

    this.returnType = info.getRawType();

    // extract method name to avoid name collisions
    final String methodName;

    if (info.isParametrized()) {
      this.methodReturnType = this.clazz.owner().ref(info.getRawType()).narrow(info.getParameterTypes());
      this.returnTypeStyle = ReturnTypeStyle.DTO_LIST;
      methodName = methodName(parentInvocations, info.getMethod().getName(), this.returnType);
    } else {
      final DtoStep returnType = new DtoStep(modelRepository, info.getRawType());
      this.returnTypeStyle = returnType.isDto() ? ReturnTypeStyle.DTO : ReturnTypeStyle.PLAIN;
      this.methodReturnType = returnType.getType(this.clazz.owner());
      methodName = methodName(parentInvocations, info.getMethod().getName(), null);
    }


    method = clazz.method(JMod.PUBLIC, methodReturnType, methodName);

    // create invocation
    final JInvocation invoke = new Invocation(method).method(info.getMethod(), parentInvocations);

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
        // TODO implement list return
        // method.body()._return
        method.body()._return(invoke);

        break;
      }
    }

    // path annotation
    final PathAnnotation pathAnnotationStep = new PathAnnotation(method);
    pathAnnotationStep.annotate(parentPathPrefix, info.getMethod());
    this.path = pathAnnotationStep.getPath();

    // JAX RS
    final JaxRsAnnotation jaxrsAnnotation = new JaxRsAnnotation(method);
    jaxrsAnnotation.annotate(info.getMethod());

    // consume and produce
    final ConsumesAndProduces consumesAndProduces = new ConsumesAndProduces(method);
    consumesAndProduces.annotate(info.getMethod());

    final ApiOperation apiOperation = new ApiOperation(method);
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
