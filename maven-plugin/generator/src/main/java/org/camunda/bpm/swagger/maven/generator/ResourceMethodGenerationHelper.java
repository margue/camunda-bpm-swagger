package org.camunda.bpm.swagger.maven.generator;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.camunda.bpm.swagger.maven.model.ModelRepository;

import com.helger.jcodemodel.JCodeModel;

public class ResourceMethodGenerationHelper {

  /**
   * Creates a new array of invocations in order to model recursive resource boxing.
   * 
   * @param parentInvocations
   *          parent invocation of current method.
   * @param method
   *          new method to invoke.
   * @return new invocation including current method.
   */
  public static ParentInvocation[] createParentInvocations(final ParentInvocation[] parentInvocations, final Method method) {
    final ParentInvocation[] newParentInvocations = new ParentInvocation[parentInvocations.length + 1];
    System.arraycopy(parentInvocations, 0, newParentInvocations, 0, parentInvocations.length);
    newParentInvocations[parentInvocations.length] = new ParentInvocation(method.getName(), method.getParameters());
    return newParentInvocations;
  }

  /**
   * Returns a map of ReturnTypeInfos for all declared methods of a type.
   * 
   * @param resource
   *          type to look for methods.
   * @return map from method to return type info.
   */
  public static Map<Method, ReturnTypeInfo> resourceReturnTypeInfos(final ModelRepository modelRepository, final JCodeModel codeModel, final Class<?> resource) {
    final Map<Method, ReturnTypeInfo> resourceReturnTypeInfos = Arrays.stream(resource.getDeclaredMethods())
        .collect(Collectors.toMap(returnMethod -> returnMethod, returnMethod -> new ReturnTypeInfo(modelRepository, codeModel, returnMethod)));
    return resourceReturnTypeInfos;
  }

}
