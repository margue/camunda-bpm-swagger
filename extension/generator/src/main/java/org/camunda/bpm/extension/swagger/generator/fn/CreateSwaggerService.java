package org.camunda.bpm.extension.swagger.generator.fn;


import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JVar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.extension.swagger.generator.model.CamundaRestService;
import org.reflections.Reflections;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

import static org.apache.commons.lang3.text.WordUtils.capitalize;
import static org.apache.commons.lang3.text.WordUtils.uncapitalize;

public class CreateSwaggerService implements Function<CamundaRestService, JCodeModel> {

  static Map<Class<? extends Annotation>, String> consumesAndProduces(AnnotatedElement e) {
    Map<Class<? extends  Annotation>, String> map = new HashMap<>();

    Optional.ofNullable(e.getAnnotation(Consumes.class)).ifPresent(a -> {
      map.put(Consumes.class, ((String[])a.value())[0]);
    });

    // fixme: must support multiple
    Optional.ofNullable(e.getAnnotation(Produces.class)).ifPresent(a -> {
      map.put(Produces.class, ((String[])a.value())[0]);
    });


    return map;
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

  private Class<? extends Annotation> annotation;
  private String value;

  static Pair<Class<?>, String> parameter(Parameter p) {
    return Pair.of(p.getType(), uncapitalize(p.getType().getSimpleName()));
  }

  private final Reflections reflections;

  public CreateSwaggerService(Reflections reflections) {
    this.reflections = reflections;
  }



  @Override
  @SneakyThrows
  public JCodeModel apply(CamundaRestService camundaRestService) {


    JCodeModel cm = new JCodeModel();
    cm._package(camundaRestService.getPackageName());
    JDefinedClass c = cm._class(camundaRestService.getSimpleName() + "Swagger");

    c.annotate(cm.ref(Path.class)).param("value", camundaRestService.getPath());
    c.annotate(cm.ref(Api.class)).param("value", camundaRestService.getName()).param("tags", camundaRestService.getTag());
    c._extends(camundaRestService.getServiceImplClass());

    for (Constructor co : camundaRestService.getServiceImplClass().getConstructors()) {

      JMethod constructor = c.constructor(co.getModifiers());

      JInvocation superInvocation = constructor.body().invoke( "super" );

      for (Parameter p : co.getParameters()) {
        Pair<Class<?>, String> pair = parameter(p);
        JVar param = constructor.param(pair.getLeft(), pair.getRight());
        superInvocation.arg(param);
      }
    }

    for (Method m : camundaRestService.getServiceInterfaceClass().getDeclaredMethods()) {
      JMethod method = c.method(JMod.PUBLIC, m.getReturnType(), m.getName());
      method.annotate(Override.class);
      String path = Optional.ofNullable(m.getAnnotation(Path.class)).map(a -> a.value()).orElse("/");
      method.annotate(Path.class).param("value", path);

      javaxRsAnnotation(m).ifPresent(a -> method.annotate(a));

      consumesAndProduces(m).entrySet().forEach(e -> method.annotate(e.getKey()).param("value" , e.getValue()));

      method.annotate(ApiOperation.class).param("value", capitalize(CamundaRestService.splitCamelCase(m.getName())));

      JInvocation invoke = JExpr._super().invoke(m.getName());


      for (Parameter p : m.getParameters()) {
        Optional<Pair<Class<? extends Annotation>, String>> parameterAnnotationValue = parameterAnnotation(p);
        JVar jvar;
        if (parameterAnnotationValue.isPresent()) {
          jvar=method.param(p.getType(), parameterAnnotationValue.get().getValue());

          jvar.annotate(parameterAnnotationValue.get().getLeft())
            .param("value", parameterAnnotationValue.get().getRight());
        } else {
          Pair<Class<?>, String> pair = parameter(p);
          jvar = method.param(pair.getLeft(), pair.getRight());
        }

        invoke.arg(jvar);
      }

      method.body()._return(invoke);

    }

    return cm;
  }


}
