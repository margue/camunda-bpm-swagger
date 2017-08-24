package org.camunda.bpm.extension.swagger.generator;

import static org.apache.commons.lang3.text.WordUtils.capitalize;
import static org.apache.commons.lang3.text.WordUtils.uncapitalize;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.resource.spi.IllegalStateException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.extension.swagger.generator.model.CamundaRestService;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.AbstractJType;
import com.helger.jcodemodel.IJExpression;
import com.helger.jcodemodel.JAnnotationUse;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JVar;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jersey.repackaged.com.google.common.collect.Lists;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwaggerServiceModelGenerator {

  private final CamundaRestService camundaRestService;
  private final JCodeModel codeModel;

  public SwaggerServiceModelGenerator(final CamundaRestService camundaRestService) {
    this.camundaRestService = camundaRestService;
    this.codeModel = new JCodeModel();

    process();
  }

  public JCodeModel getCodeModel() {
    return codeModel;
  }

  @SneakyThrows
  public JCodeModel process() {

    codeModel._package(camundaRestService.getPackageName());
    JDefinedClass c = codeModel._class(camundaRestService.getSimpleName() + "Swagger");

    c.annotate(codeModel.ref(Path.class)).param("value", camundaRestService.getPath());
    c.annotate(codeModel.ref(Api.class)).param("value", camundaRestService.getName()).param("tags", camundaRestService.getTag());
    c._extends(camundaRestService.getServiceImplClass());

    for (Constructor<?> constructor : camundaRestService.getServiceImplClass().getConstructors()) {
      generateConstructor(c, constructor);
    }

    Method[] declaredMetods = camundaRestService.getServiceInterfaceClass().getDeclaredMethods();
    generateMethods(c, declaredMetods, "");

    return codeModel;
  }

  @Value
  static class ParentInvocation {
    private String methodName;
    private Parameter[] parameters;
  }

  private void generateMethods(JDefinedClass clazz, Method[] declaredMetods, String parentPathPrefix, ParentInvocation... parentInvocations) {
    for (Method m : declaredMetods) {
      Class<?> returnType = m.getReturnType();
      // extract method name to avoid name collisions
      String methodName = methodName(parentInvocations, m.getName());
      JMethod method = clazz.method(JMod.PUBLIC, returnType, methodName);

      // annotations
      String path = parentPathPrefix + Optional.ofNullable(m.getAnnotation(Path.class)).map(a -> a.value()).orElse("");
      method.annotate(Path.class).param("value", path);
      javaxRsAnnotation(m).ifPresent(a -> method.annotate(a));
      consumesAndProduces(m).entrySet().forEach(e -> method.annotate(e.getKey()).param("value", e.getValue()));

      JAnnotationUse apiOperation = method.annotate(ApiOperation.class).param("value", capitalize(CamundaRestService.splitCamelCase(m.getName())))
          // TODO: inject operation description here
          .param("notes", "Operation " + capitalize(CamundaRestService.splitCamelCase(m.getName())));

      JInvocation invoke = null;
      if (parentInvocations.length == 0) {
        invoke = JExpr._super().invoke(m.getName());
        // overriding
        method.annotate(Override.class);

        for (Parameter p : m.getParameters()) {
          JVar jvar;
          jvar = addMethodParameter(method, p);
          // add to invocation
          invoke.arg(jvar);
        }
      } else {
        // invoke to get the parent
        for (ParentInvocation parentInvocation : parentInvocations) {
          if (invoke == null) {
            // first invocation
            invoke = JExpr.invoke(parentInvocation.getMethodName());
          } else {
            // chained
            invoke = invoke.invoke(parentInvocation.getMethodName());
          }

          for (Parameter p : parentInvocation.getParameters()) {
            JVar jvar;
            jvar = addMethodParameter(method, p);
            // add to invocation
            invoke.arg(jvar);
          }
        }
        if (invoke == null) {
          throw new RuntimeException("Invocation was empty.");
        }
        // invoke self
        invoke = invoke.invoke(m.getName());
        for (Parameter p : m.getParameters()) {
          if (parameterIsUnique(parentInvocations, p)) {

            JVar jvar;
            jvar = addMethodParameter(method, p);
            // add to invocation
            invoke.arg(jvar);
          } else {
            // parameter already present in the list
            invoke.arg(JExpr.ref(paramName(p)));
          }
        }
      }
      // body
      if (isVoid(returnType)) {
        method.body().add(invoke);
      } else {
        method.body()._return(invoke);
      }

      if (isResource(returnType)) {
        // dive into resource processing
        apiOperation.param("response", findReturnTypeOfResource(returnType));

        ParentInvocation[] newParentInvocations = new ParentInvocation[parentInvocations.length + 1];
        System.arraycopy(parentInvocations, 0, newParentInvocations, 0, parentInvocations.length);
        newParentInvocations[parentInvocations.length] = new ParentInvocation(m.getName(), m.getParameters());
        generateMethods(clazz, returnType.getDeclaredMethods(), path, newParentInvocations);
      }

    }
  }

  static String paramName(Parameter param) {
    Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotation = parameterAnnotation(param);
    String paramName;
    if (parameterAnnotation.isPresent()) {
      paramName = parameterAnnotation.get().getValue();
    } else {
      paramName = uncapitalize(param.getType().getSimpleName());
    }
    return paramName;
  }

  static boolean parameterIsUnique(ParentInvocation[] parentInvocations, Parameter param) {

    String paramName = paramName(param);
    for (ParentInvocation pi : parentInvocations) {
      for (Parameter p : pi.getParameters()) {
        if (paramName(p).equals(paramName)) {
          return false;
        }
      }
    }
    return true;

  }

  static String methodName(ParentInvocation[] parentInvocations, String name) {
    StringBuilder builder = new StringBuilder();
    for (ParentInvocation parentInvocation : parentInvocations) {
      builder.append(toFirstUpper(parentInvocation.getMethodName()));
    }
    return toFirstLower(builder.append(toFirstUpper(name)).toString());
  }

  static String toFirstUpper(String text) {
    if (text == null) {
      return null;
    }
    return text.substring(0, 1).toUpperCase() + text.substring(1);
  }

  static String toFirstLower(String text) {
    if (text == null) {
      return null;
    }
    return text.substring(0, 1).toLowerCase() + text.substring(1);
  }

  static boolean isVoid(Class<?> returnType) {
    return "void".equals(returnType.getName());
  }

  static JVar addMethodParameter(JMethod method, Parameter p) {
    JVar jvar;
    Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotationValue = parameterAnnotation(p);
    if (parameterAnnotationValue.isPresent()) {
      Pair<Class<? extends Annotation>, String> parameterAnnotation = parameterAnnotationValue.get();
      jvar = method.param(p.getType(), parameterAnnotation.getValue());
      jvar.annotate(parameterAnnotation.getLeft()).param("value", parameterAnnotation.getRight());
    } else {
      Pair<Class<?>, String> pair = parameter(p);
      jvar = method.param(pair.getLeft(), pair.getRight());
    }
    // TODO: inject parameter description here.
    jvar.annotate(ApiParam.class).param("value", "Parameter " + jvar.name());
    return jvar;
  }

  private void generateConstructor(JDefinedClass clazz, Constructor<?> co) {
    final JMethod constructor = clazz.constructor(co.getModifiers());
    final JInvocation superInvocation = constructor.body().invoke("super");
    for (Parameter p : co.getParameters()) {
      final Pair<Class<?>, String> pair = parameter(p);
      final JVar param = constructor.param(pair.getLeft(), pair.getRight());
      superInvocation.arg(param);
    }
  }

  static Class<?> findReturnTypeOfResource(Class<?> resource) {
    Optional<Method> defaultGet = Arrays.stream(resource.getMethods()).filter(m -> m.isAnnotationPresent(GET.class))
        .filter(m -> !m.isAnnotationPresent(Path.class)).findFirst();
    if (defaultGet.isPresent()) {
      return defaultGet.get().getReturnType();
    }
    return resource;
  }

  static Map<Class<? extends Annotation>, String> consumesAndProduces(AnnotatedElement e) {
    Map<Class<? extends Annotation>, String> map = new HashMap<>();

    Optional.ofNullable(e.getAnnotation(Consumes.class)).ifPresent(a -> {
      map.put(Consumes.class, ((String[]) a.value())[0]);
    });

    // FIXME: must support multiple
    Optional.ofNullable(e.getAnnotation(Produces.class)).ifPresent(a -> {
      map.put(Produces.class, ((String[]) a.value())[0]);
    });

    return map;
  }

  static boolean isResource(Class<?> clazz) {
    return clazz.isInterface() && clazz.getName().endsWith("Resource");
  }

  static Optional<Class<? extends Annotation>> javaxRsAnnotation(Method method) {
    for (Class<? extends Annotation> a : Arrays.asList(POST.class, GET.class, PUT.class, DELETE.class)) {
      if (method.getAnnotation(a) != null) {
        return Optional.of(a);
      }
    }

    return Optional.empty();
  }

  static Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotation(AnnotatedElement element) {

    PathParam pathParam = element.getAnnotation(PathParam.class);
    if (pathParam != null) {
      return Optional.of(Pair.of(PathParam.class, pathParam.value()));
    }

    QueryParam queryParam = element.getAnnotation(QueryParam.class);
    if (queryParam != null) {
      return Optional.of(Pair.of(QueryParam.class, queryParam.value()));
    }

    return Optional.empty();
  }

  static Pair<Class<?>, String> parameter(Parameter p) {
    return Pair.of(p.getType(), uncapitalize(p.getType().getSimpleName()));
  }

}
