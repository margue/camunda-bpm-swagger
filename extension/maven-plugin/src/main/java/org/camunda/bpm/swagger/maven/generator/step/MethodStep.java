package org.camunda.bpm.swagger.maven.generator.step;

import org.camunda.bpm.swagger.maven.generator.ParentInvocation;
import org.camunda.bpm.swagger.maven.generator.ReturnTypeInfo;
import org.camunda.bpm.swagger.maven.generator.StringHelper;
import org.camunda.bpm.swagger.maven.model.ModelRepository;

import com.helger.jcodemodel.AbstractJType;
import com.helger.jcodemodel.JDefinedClass;
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

  public JMethod create(final ReturnTypeInfo info, final ParentInvocation... parentInvocations) {

    // extract method name to avoid name collisions
    final String methodName = methodName(parentInvocations, info.getMethod().getName());

    this.returnType = info.getRawType();

    if (info.isParametrized()) {
      methodReturnType = this.clazz.owner().ref(info.getRawType()).narrow(info.getParameterTypes());
      this.returnTypeStyle = ReturnTypeStyle.DTO_LIST;
    } else {
      final DtoStep returnType = new DtoStep(modelRepository, info.getRawType());
      this.returnTypeStyle = returnType.isDto() ? ReturnTypeStyle.DTO : ReturnTypeStyle.PLAIN;
      methodReturnType = returnType.getType(this.clazz.owner());
    }

    method = clazz.method(JMod.PUBLIC, methodReturnType, methodName);
    return method;
  }

  static String methodName(final ParentInvocation[] parentInvocations, final String name) {
    final StringBuilder builder = new StringBuilder();
    for (final ParentInvocation parentInvocation : parentInvocations) {
      builder.append(StringHelper.toFirstUpper(parentInvocation.getMethodName()));
    }
    return StringHelper.toFirstLower(builder.append(StringHelper.toFirstUpper(name)).toString());
  }

}
