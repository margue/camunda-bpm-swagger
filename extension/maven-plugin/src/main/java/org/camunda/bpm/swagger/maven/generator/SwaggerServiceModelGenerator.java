package org.camunda.bpm.swagger.maven.generator;

import static org.apache.commons.lang3.text.WordUtils.capitalize;
import static org.apache.commons.lang3.text.WordUtils.uncapitalize;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

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
import org.camunda.bpm.swagger.maven.model.CamundaRestService;
import org.camunda.bpm.swagger.maven.spi.CodeGenerator;

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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwaggerServiceModelGenerator implements CodeGenerator {

  private final CamundaRestService camundaRestService;
  private final JCodeModel codeModel;

  public SwaggerServiceModelGenerator(final CamundaRestService camundaRestService) {
    this.camundaRestService = camundaRestService;
    this.codeModel = camundaRestService.getCodeModel();
  }

  @Override
  @SneakyThrows
  public CamundaRestService generate() {
    final JDefinedClass c = camundaRestService.getDefinedClass();

    c.annotate(codeModel.ref(Path.class)).param("value", camundaRestService.getPath());
    c.annotate(codeModel.ref(Api.class)).param("value", camundaRestService.getName()).param("tags", camundaRestService.getTag());
    c._extends(camundaRestService.getServiceImplClass());

    for (final Constructor<?> constructor : camundaRestService.getServiceImplClass().getConstructors()) {
      generateConstructor(c, constructor);
    }

    final Method[] interfaceDeclaredMetods = camundaRestService.getServiceInterfaceClass().getDeclaredMethods();
    final Method[] implDeclaredMetods = camundaRestService.getServiceImplClass().getDeclaredMethods();

    final Map<Method, Class<?>> returnTypes = new HashMap<>();
    for (final Method m : interfaceDeclaredMetods) {
      returnTypes.put(m, m.getReturnType());

      // FIXME: schick machen!
      for (final Method s : implDeclaredMetods) {
        if (s.getName().equals(m.getName()) && s.getParameterCount() == m.getParameterCount() && s.getReturnType() != m.getReturnType()) {
          returnTypes.put(m, s.getReturnType());
          log.info("replace return type {} {} {}", m, m.getReturnType(), s.getReturnType());
        }
      }
    }

    generateMethods(c, returnTypes, "");

    return camundaRestService;
  }

  private void generateMethods(final JDefinedClass clazz, final Map<Method, Class<?>> methods, final String parentPathPrefix,
      final ParentInvocation... parentInvocations) {
    for (final Method m : methods.keySet()) {
      final Class<?> returnType = methods.get(m);
      // extract method name to avoid name collisions
      final String methodName = methodName(parentInvocations, m.getName());
      final JMethod method = clazz.method(JMod.PUBLIC, returnType, methodName);

      final String path = path(parentPathPrefix, m);

      method.annotate(Path.class).param("value", path);
      javaxRsAnnotation(m).ifPresent(a -> method.annotate(a));
      consumesAndProduces(m).entrySet().forEach(e -> method.annotate(e.getKey()).param("value", e.getValue()));

      final JAnnotationUse apiOperation = method.annotate(ApiOperation.class).param("value", capitalize(CamundaRestService.splitCamelCase(m.getName())))
          // TODO: inject operation description here
          .param("notes", "Operation " + capitalize(CamundaRestService.splitCamelCase(m.getName())));

      JInvocation invoke = null;
      if (parentInvocations.length == 0) {
        invoke = JExpr._super().invoke(m.getName());
        // overriding
        method.annotate(Override.class);

        for (final Parameter p : m.getParameters()) {
          JVar jvar;
          jvar = addMethodParameter(method, p);
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
        for (final Parameter p : m.getParameters()) {
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

        final ParentInvocation[] newParentInvocations = new ParentInvocation[parentInvocations.length + 1];
        System.arraycopy(parentInvocations, 0, newParentInvocations, 0, parentInvocations.length);
        newParentInvocations[parentInvocations.length] = new ParentInvocation(m.getName(), m.getParameters());

        final Map<Method, Class<?>> rt = new HashMap<>();
        for (final Method method1 : returnType.getDeclaredMethods()) {
          rt.put(method1, method1.getReturnType());
        }

        generateMethods(clazz, rt, path, newParentInvocations);
      }

    }
  }

  static String path(final String parentPathPrefix, final Method method) {
    final StringBuilder pathBuilder = parentPathPrefix == null ? new StringBuilder(""): new StringBuilder(parentPathPrefix);
    if (pathBuilder.length() > 0 && pathBuilder.lastIndexOf("/") == pathBuilder.length() - 1) {
      // ends with a "/", remove it
      pathBuilder.deleteCharAt(pathBuilder.length() - 1);
    }
    final String currentPath = Optional.ofNullable(method.getAnnotation(Path.class)).map(a -> a.value()).orElse("");
    if (!currentPath.isEmpty() && !currentPath.startsWith("/")) {
      pathBuilder.append("/").append(currentPath);
    } else {
      pathBuilder.append(currentPath);
    }
    return pathBuilder.toString();
  }

  static String paramName(final Parameter param) {
    final Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotation = parameterAnnotation(param);
    String paramName;
    if (parameterAnnotation.isPresent()) {
      paramName = parameterAnnotation.get().getValue();
    } else {
      paramName = uncapitalize(param.getType().getSimpleName());
    }
    return paramName;
  }

  static boolean parameterIsUnique(final ParentInvocation[] parentInvocations, final Parameter param) {

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

  static String methodName(final ParentInvocation[] parentInvocations, final String name) {
    final StringBuilder builder = new StringBuilder();
    for (final ParentInvocation parentInvocation : parentInvocations) {
      builder.append(toFirstUpper(parentInvocation.getMethodName()));
    }
    return toFirstLower(builder.append(toFirstUpper(name)).toString());
  }

  static String toFirstUpper(final String text) {
    if (text == null) {
      return null;
    }
    return text.substring(0, 1).toUpperCase() + text.substring(1);
  }

  static String toFirstLower(final String text) {
    if (text == null) {
      return null;
    }
    return text.substring(0, 1).toLowerCase() + text.substring(1);
  }

  static boolean isVoid(final Class<?> returnType) {
    return "void".equals(returnType.getName());
  }

  static JVar addMethodParameter(final JMethod method, final Parameter p) {
    JVar jvar;
    final Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotationValue = parameterAnnotation(p);
    if (parameterAnnotationValue.isPresent()) {
      final Pair<Class<? extends Annotation>, String> parameterAnnotation = parameterAnnotationValue.get();
      jvar = method.param(p.getType(), parameterAnnotation.getValue());
      jvar.annotate(parameterAnnotation.getLeft()).param("value", parameterAnnotation.getRight());
    } else {
      final Pair<Class<?>, String> pair = parameter(p);
      jvar = method.param(pair.getLeft(), pair.getRight());
    }
    // TODO: inject parameter description here.
    jvar.annotate(ApiParam.class).param("value", "Parameter " + jvar.name());
    return jvar;
  }

  private void generateConstructor(final JDefinedClass clazz, final Constructor<?> co) {
    final JMethod constructor = clazz.constructor(co.getModifiers());
    final JInvocation superInvocation = constructor.body().invoke("super");
    for (final Parameter p : co.getParameters()) {
      final Pair<Class<?>, String> pair = parameter(p);
      final JVar param = constructor.param(pair.getLeft(), pair.getRight());
      superInvocation.arg(param);
    }
  }

  static Class<?> findReturnTypeOfResource(final Class<?> resource) {
    final Optional<Method> defaultGet = Arrays.stream(resource.getMethods()).filter(m -> m.isAnnotationPresent(GET.class))
        .filter(m -> !m.isAnnotationPresent(Path.class)).findFirst();
    if (defaultGet.isPresent()) {
      return defaultGet.get().getReturnType();
    }
    return resource;
  }

  static Map<Class<? extends Annotation>, String> consumesAndProduces(final AnnotatedElement e) {
    final Map<Class<? extends Annotation>, String> map = new HashMap<>();

    Optional.ofNullable(e.getAnnotation(Consumes.class)).ifPresent(a -> {
      map.put(Consumes.class, a.value()[0]);
    });

    // FIXME: must support multiple
    Optional.ofNullable(e.getAnnotation(Produces.class)).ifPresent(a -> {
      map.put(Produces.class, a.value()[0]);
    });

    return map;
  }

  static boolean isResource(final Class<?> clazz) {
    return clazz.isInterface() && clazz.getName().endsWith("Resource");
  }

  static Optional<Class<? extends Annotation>> javaxRsAnnotation(final Method method) {
    for (final Class<? extends Annotation> a : Arrays.asList(POST.class, GET.class, PUT.class, DELETE.class)) {
      if (method.getAnnotation(a) != null) {
        return Optional.of(a);
      }
    }

    return Optional.empty();
  }

  static String camelize(final String value) {
    if (value == null || !value.contains("-")) {
      return value;
    }
    final StringTokenizer token = new StringTokenizer(value, "-");
    final StringBuilder str = new StringBuilder(token.nextToken());
    while (token.hasMoreTokens()) {
      final String s = token.nextToken();
      str.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
    }
    return str.toString();
  }

  static Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotation(final AnnotatedElement element) {

    final PathParam pathParam = element.getAnnotation(PathParam.class);
    if (pathParam != null) {
      return Optional.of(Pair.of(PathParam.class, camelize(pathParam.value())));
    }

    final QueryParam queryParam = element.getAnnotation(QueryParam.class);
    if (queryParam != null) {
      return Optional.of(Pair.of(QueryParam.class, camelize(queryParam.value())));
    }

    return Optional.empty();
  }

  static Pair<Class<?>, String> parameter(final Parameter p) {
    return Pair.of(p.getType(), uncapitalize(p.getType().getSimpleName()));
  }

}
