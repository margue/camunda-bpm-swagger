package org.camunda.bpm.swagger.maven.spoon.processor;

import org.apache.commons.lang3.tuple.Pair;
import spoon.reflect.declaration.*;
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
    return Optional.of(method).map(m -> m.getParent(CtType.class)).map(CtType::getQualifiedName).orElse("");
  }

  public static Class<? extends Annotation> javaxRsAnnotation(final CtMethod<?> ctMethod) {
    for (final Class<? extends Annotation> a : Arrays.asList(POST.class, GET.class, PUT.class, DELETE.class, OPTIONS.class)) {
      if (ctMethod.getAnnotation(a) != null) {
        return a;
      }
    }
    return null;
  }

  public static String path(final CtMethod<?> ctMethod) {
    final Optional<CtMethod<?>> interfaceMethod = getInterfaceMethod(ctMethod);
    return interfaceMethod.map(m -> m.getAnnotation(Path.class)).map(a -> a.value()).orElse("/");
  }

  public static Optional<CtMethod<?>> getInterfaceMethod(CtMethod<?> ctMethod) {
    if (ctMethod.getDeclaringType() instanceof CtClass) {
      final Optional<CtTypeReference<?>> firstSuperInterface = getFirstSuperInterface((CtClass<?>) ctMethod.getDeclaringType());
      if (firstSuperInterface.isPresent()) {
        return firstSuperInterface.get().getDeclaration().getMethods().stream().filter(m -> m.getSignature().equals(ctMethod.getSignature())).findFirst();
      }
    }
    return Optional.empty();
  }

  /**
   * Extends a parameter annotation from the method parameter from the declaring interface.
   *
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
   *
   * @param parameter parameter of the method.
   * @param ctMethod  method.
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
