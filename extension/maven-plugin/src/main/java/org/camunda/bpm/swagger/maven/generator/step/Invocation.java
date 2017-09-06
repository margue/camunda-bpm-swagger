package org.camunda.bpm.swagger.maven.generator.step;

import static org.apache.commons.lang3.text.WordUtils.uncapitalize;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.swagger.docs.model.ParameterDescription;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.maven.generator.ParentInvocation;
import org.camunda.bpm.swagger.maven.generator.StringHelper;
import org.springframework.beans.BeanUtils;

import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JVar;

import io.swagger.annotations.ApiParam;

public class Invocation extends AbstractMethodStep {

  public Invocation(final JMethod method) {
    super(method);
  }

  public void dtoConstructor(final Class<?> baseClass, final String varName) {
    final JBlock body = getMethod().body();
    body.invoke("super");
    // BeanUtils.copyProperties(this, dto);
    final JInvocation copyProperties = getMethod().owner().ref(BeanUtils.class) //
        .staticInvoke("copyProperties").arg(JExpr.ref(varName)).arg(JExpr.ref("this"));

    body.add(copyProperties);
  }

  public JInvocation constructor(final Constructor<?> constructor) {
    final JInvocation superInvocation = getMethod().body().invoke("super");
    for (final Parameter p : constructor.getParameters()) {
      final Pair<Class<?>, String> pair = parameter(p);
      final JVar param = getMethod().param(pair.getLeft(), pair.getRight());
      superInvocation.arg(param);
    }
    return superInvocation;
  }

  public JInvocation method(final Method m, final RestOperation doc, final ParentInvocation... parentInvocations) {
    JInvocation invoke = null;
    if (parentInvocations.length == 0) {
      invoke = JExpr._super().invoke(m.getName());
      for (final Parameter p : m.getParameters()) {
        JVar jvar;
        jvar = addMethodParameter(getMethod(), doc, p);
        // add to invocation
        invoke.arg(jvar);
      }
    } else {
      // invoke to get the parent
      for (final ParentInvocation parentInvocation : parentInvocations) {
        if (invoke == null) {
          // first invocation
          invoke = JExpr.invoke(parentInvocation.getMethodName());
        } else {
          // chained
          invoke = invoke.invoke(parentInvocation.getMethodName());
        }

        for (final Parameter p : parentInvocation.getParameters()) {
          JVar jvar;
          jvar = addMethodParameter(getMethod(), doc, p);
          // add to invocation
          invoke.arg(jvar);
        }
      }
      if (invoke == null) {
        throw new IllegalArgumentException("Invocation was empty.");
      }
      // invoke self
      invoke = invoke.invoke(m.getName());
      for (final Parameter p : m.getParameters()) {
        if (parameterIsUnique(parentInvocations, p)) {

          JVar jvar;
          jvar = addMethodParameter(getMethod(), doc, p);
          // add to invocation
          invoke.arg(jvar);
        } else {
          // parameter already present in the list
          invoke.arg(JExpr.ref(paramName(p)));
        }
      }
    }
    return invoke;
  }

  public static JVar addMethodParameter(final JMethod method, final RestOperation doc, final Parameter p) {
    JVar jvar;
    final Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotationValue = parameterAnnotation(p);
    String apiDocsParamName = null;

    if (parameterAnnotationValue.isPresent()) {
      final Pair<Class<? extends Annotation>, String> parameterAnnotation = parameterAnnotationValue.get();
      jvar = method.param(p.getType(), parameterAnnotation.getValue());
      jvar.annotate(parameterAnnotation.getLeft()).param("value", parameterAnnotation.getRight());

      if (doc != null) {
        // extract docs
        ParameterDescription parameterDescription = null;
        if (parameterAnnotation.getLeft().isAssignableFrom(QueryParam.class)) {
          parameterDescription = doc.getQueryParameters().get(parameterAnnotation.getRight());
        } else if (parameterAnnotation.getLeft().isAssignableFrom(PathParam.class)) {
          parameterDescription = doc.getPathParameters().get(parameterAnnotation.getRight());
        } else {
          throw new IllegalArgumentException("Unknown parameter annotation of type " + parameterAnnotation.getLeft().getName());
        }

        if (parameterDescription != null) {
          apiDocsParamName = parameterDescription.getDescription();
        }
      }

    } else {
      final Pair<Class<?>, String> pair = parameter(p);
      jvar = method.param(pair.getLeft(), pair.getRight());
    }

    jvar.annotate(ApiParam.class).param("value", apiDocsParamName == null ? "Parameter " + jvar.name() : apiDocsParamName);
    return jvar;
  }

  static Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotation(final AnnotatedElement element) {

    final PathParam pathParam = element.getAnnotation(PathParam.class);
    if (pathParam != null) {
      return Optional.of(Pair.of(PathParam.class, StringHelper.camelize(pathParam.value())));
    }

    final QueryParam queryParam = element.getAnnotation(QueryParam.class);
    if (queryParam != null) {
      return Optional.of(Pair.of(QueryParam.class, StringHelper.camelize(queryParam.value())));
    }

    return Optional.empty();
  }

  static Pair<Class<?>, String> parameter(final Parameter p) {
    final String type = (p.getType().isPrimitive() ? p.getType().getSimpleName() + "Arg" : p.getType().getSimpleName())
        + ThreadLocalRandom.current().nextInt(0, 5); // add number modify

    return Pair.of(p.getType(), uncapitalize(type));
  }

  public static String paramName(final Parameter param) {
    final Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotation = parameterAnnotation(param);
    String paramName;
    if (parameterAnnotation.isPresent()) {
      paramName = parameterAnnotation.get().getValue();
    } else {
      if (param.getType().isPrimitive()) {
        paramName = uncapitalize(param.getType().getSimpleName()) + "Arg";
      } else {
        paramName = uncapitalize(param.getType().getSimpleName());
      }
    }
    return paramName;
  }

  public static boolean parameterIsUnique(final ParentInvocation[] parentInvocations, final Parameter param) {

    final String paramName = paramName(param);
    for (final ParentInvocation pi : parentInvocations) {
      for (final Parameter p : pi.getParameters()) {
        if (paramName(p).equals(paramName)) {
          return false;
        }
      }
    }
    return true;

  }

}
