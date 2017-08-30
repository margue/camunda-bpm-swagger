package org.camunda.bpm.swagger.maven.generator.step;

import org.camunda.bpm.swagger.maven.generator.ParentInvocation;
import org.camunda.bpm.swagger.maven.generator.ReturnTypeInfo;
import org.camunda.bpm.swagger.maven.generator.StringHelper;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class MethodStep {

  private final JDefinedClass clazz;
  private JMethod method;
  private Class<?> returnType;

  static String methodName(final ParentInvocation[] parentInvocations, final String name) {
    final StringBuilder builder = new StringBuilder();
    for (final ParentInvocation parentInvocation : parentInvocations) {
      builder.append(StringHelper.toFirstUpper(parentInvocation.getMethodName()));
    }
    return StringHelper.toFirstLower(builder.append(StringHelper.toFirstUpper(name)).toString());
  }

  public JMethod create(final ReturnTypeInfo info, final ParentInvocation... parentInvocations) {

    // extract method name to avoid name collisions
    final String methodName = methodName(parentInvocations, info.getMethod().getName());

    this.returnType = info.getRawType();
    if (info.isParametrized()) {
      final AbstractJClass parametrizedType = this.clazz.owner().ref(info.getRawType()).narrow(info.getParameterTypes());
      method = clazz.method(JMod.PUBLIC, parametrizedType, methodName);
    } else {
      method = clazz.method(JMod.PUBLIC, returnType, methodName);
    }

    return method;
  }
}
