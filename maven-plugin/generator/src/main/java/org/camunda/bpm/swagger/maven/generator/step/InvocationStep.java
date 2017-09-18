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

import com.helger.jcodemodel.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.annotations.Param;
import org.camunda.bpm.swagger.docs.model.ParameterDescription;
import org.camunda.bpm.swagger.docs.model.RestOperation;
import org.camunda.bpm.swagger.maven.generator.ParameterRepository;
import org.camunda.bpm.swagger.maven.generator.ParentInvocation;
import org.camunda.bpm.swagger.maven.generator.StringHelper;
import org.camunda.bpm.swagger.maven.model.DocStyle;
import org.camunda.bpm.swagger.maven.model.ModelRepository;
import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiParam;

public class InvocationStep extends AbstractMethodStep {

  public InvocationStep(final JMethod method) {
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
      final Pair<Class<?>, String> pair = parameter(p, constructor.getParameters());
      final JVar param = getMethod().param(pair.getLeft(), pair.getRight());
      superInvocation.arg(param);
    }
    return superInvocation;
  }

  public JInvocation method(final ModelRepository modelRepository, final Method m, final RestOperation doc, final ParentInvocation... parentInvocations) {
    JInvocation invoke = null;
    if (parentInvocations.length == 0) {
      invoke = JExpr._super().invoke(m.getName());
      for (final Parameter p : m.getParameters()) {
        JVar jvar;
        jvar = addMethodParameter(modelRepository, getMethod(), doc, p, m.getParameters());
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
          jvar = addMethodParameter(modelRepository, getMethod(), doc, p, m.getParameters());
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
        if (parameterIsUnique(parentInvocations, p, m.getParameters())) {

          JVar jvar;
          jvar = addMethodParameter(modelRepository, getMethod(), doc, p, m.getParameters());
          // add to invocation
          invoke.arg(jvar);
        } else {
          // parameter already present in the list
          invoke.arg(JExpr.ref(paramName(p, m.getParameters())));
        }
      }
    }
    return invoke;
  }

  public static JVar addMethodParameter(final ModelRepository modelRepository, final JMethod method, final RestOperation doc, final Parameter p,
      final Parameter[] all) {
    JVar jvar;
    final Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotationValue = parameterAnnotation(p);
    String apiDocsParamName = null;

    final Pair<Class<?>, String> pair = parameter(p, all);

    final TypeStep dtoStep = new TypeStep(modelRepository, pair.getLeft(), method.owner());
    final AbstractJType type = dtoStep.getType();

    if (parameterAnnotationValue.isPresent()) {
      final Pair<Class<? extends Annotation>, String> parameterAnnotation = parameterAnnotationValue.get();
      jvar = method.param(type, parameterAnnotation.getRight());
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
      jvar = method.param(type, pair.getRight());
    }

    // annotate
    if (!ParameterRepository.isPresent(type.fullName())) {
      final JAnnotationUse apiParam = jvar
        .annotate(ApiParam.class)
        .param("value", apiDocsParamName == null ? "Parameter " + jvar.name() : apiDocsParamName);

      if (dtoStep.isDto()) {
        apiParam.param("type", type.fullName());

        // add docs
        modelRepository.addDoc(dtoStep.getFullQualifiedName(), doc, DocStyle.METHOD_PARAM);
      }
    } else if (ParameterRepository.generateIimplicitParams(type.fullName()) && doc != null) {
      // implicit parameters
      final JAnnotationArrayMember implicitParamsAnnotation = method.annotate(ApiImplicitParams.class).paramArray("value");
      for (String paramName : doc.getQueryParameters().keySet()) {
        final ParameterDescription parameterDescription = doc.getQueryParameters().get(paramName);
        implicitParamsAnnotation
          .annotate(ApiImplicitParam.class)
          .param("name", paramName)
          .param("value", parameterDescription.getDescription() == null ? "Parameter" + paramName : parameterDescription.getDescription())
          .param("dataType", parameterDescription.getType() == null ? "string" : parameterDescription.getType())
          .param("paramType", "query")
          .param("required", parameterDescription.getRequired() == null ? false : parameterDescription.getRequired())
        ;
      }
    }

    return jvar;
  }

  /**
   * Finds a parameter annotation.
   *
   * @param element
   *          annotated element.
   * @return a pair with annotation type on the left and value on the right.
   */
  public static Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotation(final AnnotatedElement element) {

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

  /**
   * Creates a parameter pair.
   *
   * @param param
   *          parameter to describe.
   * @param all
   *          all parameters for checking
   * @return a pair with type on the left and name on the right.
   */
  public static Pair<Class<?>, String> parameter(final Parameter param, final Parameter[] all) {
    final Optional<Pair<Class<?>, String>> lookup = ParameterRepository.lookup(param);
    return lookup.orElse(Pair.of(param.getType(), paramName(param, all)));
  }

  /**
   * Determines parameter name.
   *
   * @param param
   *          parameter to extract name for.
   * @param all
   *          parameters for checking.
   * @return name of the parameter.
   */
  public static String paramName(final Parameter param, final Parameter[] all) {
    final Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotation = parameterAnnotation(param);
    String paramName;
    if (parameterAnnotation.isPresent()) {
      paramName = parameterAnnotation.get().getValue();
    } else {

      if (param.getType().isPrimitive()) {
        paramName = param.getType().getSimpleName() + "Arg";
      } else {
        paramName = param.getType().getSimpleName();
      }

      // find all occurrences of not annotated parameters with the same type.
      int found = 0;
      for (final Parameter p : all) {
        if (!parameterAnnotation(param).isPresent() && p.getType().equals(param.getType())) {
          found++;
        }
      }

      // if there is more than one, try to avoid name clash
      if (found > 1) {
        // TODO: is this good enough?
        paramName += ThreadLocalRandom.current().nextInt(0, 9999); // add number modify
      }
    }
    return uncapitalize(paramName);
  }

  /**
   * Checks if the parameter is unique along with parent invocations. Currently needed for TaskService#delete only.
   *
   * @param parentInvocations
   *          invocation stack.
   * @param param
   *          parameter to check.
   * @return true if unique.
   */
  public static boolean parameterIsUnique(final ParentInvocation[] parentInvocations, final Parameter param, final Parameter[] all) {

    final String paramName = paramName(param, all);
    for (final ParentInvocation pi : parentInvocations) {
      for (final Parameter p : pi.getParameters()) {
        if (paramName(p, all).equals(paramName)) {
          return false;
        }
      }
    }
    return true;
  }

}
