package org.camunda.bpm.swagger.maven.spoon.processor;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.ModifierKind;

import java.util.function.Predicate;

public class ClassPredicates {
  public static final Predicate<CtClass<?>> isNamedRestService = c -> c.getSimpleName().endsWith("RestServiceImpl");
  public static final Predicate<CtClass<?>> isNotAbstract = c -> !c.getModifiers().contains(ModifierKind.ABSTRACT);
  public static final Predicate<CtClass<?>> implementsRestInterface = c -> !c.getSuperInterfaces().isEmpty() && c.getSuperInterfaces().stream().allMatch(ref -> ref.getQualifiedName().endsWith("RestService"));
  public static final Predicate<CtClass<?>> isNamedDto = c -> c.getSimpleName().endsWith("Dto");

}
