package org.camunda.bpm.swagger.maven.spoon.processor;

import org.apache.commons.lang3.tuple.Pair;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.reference.CtTypeReference;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

public class TypeHelper {

  public static String getFirstInterfaceClassName(final CtClass<?> impl) {
    if (impl == null) {
      return "";
    }
    return getFirstSuperInterface(impl).map(CtTypeReference::getQualifiedName).orElse("");
  }

  public static Optional<CtTypeReference<?>> getFirstSuperInterface(final CtClass<?> impl) {
    return impl.getSuperInterfaces().stream().findFirst();
  }

  public static String getClassname(final CtMethod<?> method) {
    return Optional.of(method).map(m -> m.getParent(CtClass.class)).map(CtClass::getQualifiedName).orElse("");
  }

  /**
   * Tries to find the HTTP path and HTTP Method annotated to this method (either directly or to it's definition in the interface).
   *
   * @param ctMethod method to inspect.
   * @return a pair of path and method.
   */
  public static Pair<String, String> getPathAndHttpMethod(final CtMethod<?> ctMethod) {

    final Optional<Class<? extends Annotation>> annotation = javaxRsAnnotation(ctMethod);
    final String method = annotation.map(Class::getSimpleName).orElse("");
    final String path = path(ctMethod);

    return Pair.of(path, method);
  }

  public static Optional<Class<? extends Annotation>> javaxRsAnnotation(final CtMethod<?> ctMethod) {
    final Optional<CtMethod<?>> interfaceMethod = getInterfaceMethod(ctMethod);
    if (interfaceMethod.isPresent()) {
      for (final Class<? extends Annotation> a : Arrays.asList(POST.class, GET.class, PUT.class, DELETE.class, OPTIONS.class)) {
        if (interfaceMethod.get().getAnnotation(a) != null) {
          return Optional.of(a);
        }
      }
    }
    return Optional.of(GET.class);
  }

  public static String path(final CtMethod<?> ctMethod) {
    final Optional<CtMethod<?>> interfaceMethod = getInterfaceMethod(ctMethod);
    return interfaceMethod.map(m -> m.getAnnotation(Path.class)).map(a -> a.value()).orElse("/");
  }

  public static Optional<CtMethod<?>> getInterfaceMethod(CtMethod<?> ctMethod) {
    if (ctMethod.getDeclaringType() instanceof CtClass){
      final Optional<CtTypeReference<?>> firstSuperInterface = getFirstSuperInterface((CtClass<?>) ctMethod.getDeclaringType());
      if (firstSuperInterface.isPresent()) {
        return firstSuperInterface.get().getDeclaration().getMethods().stream().filter(m -> m.getSignature().equals(ctMethod.getSignature())).findFirst();
      }
    }
    return Optional.empty();
  }

  /**
   * Extends a parameter annotation from the method parameter from the declaring interface.
   * @param ctParameter parameter of the implementation.
   * @return annotation class, if present in the defining interface.
   */
  public static Optional<? extends Class<?>> getParameterAnnotation(final CtParameter ctParameter, final CtMethod ctMethod) {
    final Optional<CtParameter<?>> interfaceParameter = getInterfaceParameter(ctParameter, ctMethod);
    return interfaceParameter.map(p -> p.getAnnotations().stream()
      .map(a -> a.getType().getTypeDeclaration().getActualClass())
      .filter(c -> c.isAssignableFrom(QueryParam.class) || c.isAssignableFrom(PathParam.class))
      .findFirst()
    ).orElse(Optional.empty());
  }

  /**
   * Retrieves the parameter from the super interface if exists.
   * @param parameter parameter of the method.
   * @param ctMethod method.
   * @return parameter from the super interface with the same type and same name.
   */
  public static Optional<CtParameter<?>> getInterfaceParameter(final CtParameter parameter, final CtMethod ctMethod) {
    return getInterfaceMethod(ctMethod).map(m -> m.getParameters().stream()
      .filter(p -> parameter.getType().isSubtypeOf(p.getType()))
      .filter(p -> p.getSimpleName().equals(parameter.getSimpleName()))
      .findFirst())
    .orElse(Optional.empty());
  }
}
