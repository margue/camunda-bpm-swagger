package org.camunda.bpm.swagger.maven.spoon.processor;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

import javax.ws.rs.Path;
import java.util.function.Predicate;

public class MethodPredicates {

  public static final Predicate<CtMethod<?>> classIsNamedResource = m -> TypeHelper.getClassname(m).endsWith("Resource");
  public static final Predicate<CtMethod<?>> classIsNamedResourceImpl = m -> TypeHelper.getClassname(m).endsWith("ResourceImpl");
  public static final Predicate<CtMethod<?>> classIsNamedService = m -> TypeHelper.getClassname(m).endsWith("Service");
  public static final Predicate<CtMethod<?>> classIsNamedRestServiceImpl = m -> TypeHelper.getClassname(m).endsWith("RestServiceImpl");
  public static final Predicate<CtMethod<?>> classIsNamedDto = m -> TypeHelper.getClassname(m).endsWith("Dto");
  public static final Predicate<CtMethod<?>> classImplementsRestInterface = m -> ClassPredicates.implementsRestInterface.test((CtClass<?>) m.getParent());
  public static final Predicate<CtMethod<?>> classImplementsResourceInterface = m -> ClassPredicates.implementsResourceInterface.test((CtClass<?>) m.getParent());

  public static final Predicate<CtMethod<?>> isPathAnnotated = m -> m.getAnnotation(Path.class) != null;
  public static final Predicate<CtMethod<?>> returnsResource = m -> m.getType().getQualifiedName().endsWith("Resource");

  public static final Predicate<CtMethod<?>> startsWithGet = m -> m.getSimpleName().startsWith("get");
  public static final Predicate<CtMethod<?>> isPublic = m -> m.getModifiers().contains(ModifierKind.PUBLIC);
  public static final Predicate<CtMethod<?>> takesNoParameters = m -> m.getParameters().isEmpty();
  public static final Predicate<CtMethod<?>> existsInInterface = m -> TypeHelper.getInterfaceMethod(m).isPresent();

}
