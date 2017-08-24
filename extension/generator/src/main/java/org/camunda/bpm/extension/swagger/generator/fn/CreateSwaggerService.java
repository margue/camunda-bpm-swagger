package org.camunda.bpm.extension.swagger.generator.fn;

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
import java.util.function.Function;

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

public class CreateSwaggerService implements Function<CamundaRestService, JCodeModel> {

  @Override
  @SneakyThrows
  public JCodeModel apply(CamundaRestService camundaRestService) {

    JCodeModel cm = new JCodeModel();

    cm._package(camundaRestService.getPackageName());
    JDefinedClass c = cm._class(camundaRestService.getSimpleName() + "Swagger");

    c.annotate(cm.ref(Path.class)).param("value", camundaRestService.getPath());
    c.annotate(cm.ref(Api.class)).param("value", camundaRestService.getName()).param("tags", camundaRestService.getTag());
    c._extends(camundaRestService.getServiceImplClass());

    for (Constructor<?> constructor : camundaRestService.getServiceImplClass().getConstructors()) {
      generateConstructor(c, constructor);
    }

    for (Method m : camundaRestService.getServiceInterfaceClass().getDeclaredMethods()) {
      Class<?> returnType = m.getReturnType();
      JMethod method = c.method(JMod.PUBLIC, returnType, m.getName());


      // annotations
      method.annotate(Override.class);
      String path = Optional.ofNullable(m.getAnnotation(Path.class)).map(a -> a.value()).orElse("/");
      method.annotate(Path.class).param("value", path);
      javaxRsAnnotation(m).ifPresent(a -> method.annotate(a));
      consumesAndProduces(m).entrySet().forEach(e -> method.annotate(e.getKey()).param("value", e.getValue()));

      JAnnotationUse apiOperation= method.annotate(ApiOperation.class)
           .param("value", capitalize(CamundaRestService.splitCamelCase(m.getName())))
           // TODO: inject operation description here
           .param("notes", "Operation " + capitalize(CamundaRestService.splitCamelCase(m.getName())));

      if (isResource(returnType)) {
        // dive into resource processing
        apiOperation.param("response", findReturnTypeOfResource(returnType));


      }


      // params
      JInvocation invoke = JExpr._super().invoke(m.getName());
      for (Parameter p : m.getParameters()) {
        Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotationValue = parameterAnnotation(p);
        JVar jvar;
        if (parameterAnnotationValue.isPresent()) {
          jvar = method.param(p.getType(), parameterAnnotationValue.get().getValue());
          jvar.annotate(parameterAnnotationValue.get().getLeft()).param("value", parameterAnnotationValue.get().getRight());
        } else {
          Pair<Class<?>, String> pair = parameter(p);
          jvar = method.param(pair.getLeft(), pair.getRight());
        }
        // TODO: inject parameter description here.
        jvar.annotate(ApiParam.class).param("value", "Parameter " + jvar.name());
        invoke.arg(jvar);
      }
      // body
      method.body()._return(invoke);

    }

    return cm;
  }

  static Class<?> findReturnTypeOfResource(Class<?> resource) {
    Optional<Method> defaultGet = Arrays.stream(resource.getMethods()).filter(m -> m.isAnnotationPresent(GET.class)).filter(m -> !m.isAnnotationPresent(Path.class)).findFirst();
    if (defaultGet.isPresent()) {
      return defaultGet.get().getReturnType();
    }
    return resource;
  }

  static void generateConstructor(JDefinedClass clazz, Constructor<?> co) {
    final JMethod constructor = clazz.constructor(co.getModifiers());
    final JInvocation superInvocation = constructor.body().invoke("super");
    for (Parameter p : co.getParameters()) {
      final Pair<Class<?>, String> pair = parameter(p);
      final JVar param = constructor.param(pair.getLeft(), pair.getRight());
      superInvocation.arg(param);
    }
  }

  static Map<Class<? extends Annotation>, String> consumesAndProduces(AnnotatedElement e) {
    Map<Class<? extends Annotation>, String> map = new HashMap<>();

    Optional.ofNullable(e.getAnnotation(Consumes.class)).ifPresent(a -> {
      map.put(Consumes.class, ((String[]) a.value())[0]);
    });

    // fixme: must support multiple
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
