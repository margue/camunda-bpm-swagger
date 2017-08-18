package org.camunda.bpm.extension.swagger.generator.fn;


import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.camunda.bpm.extension.swagger.generator.model.CamundaRestService;
import org.reflections.Reflections;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Function;

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

    for (Method m : camundaRestService.getServiceClass().getDeclaredMethods()) {
      JMethod method = c.method(JMod.PUBLIC, m.getReturnType(), m.getName());

      String path = Optional.ofNullable(m.getAnnotation(Path.class)).map(a -> a.value()).orElse("/");

      method.annotate(Path.class).param("value", path);
      //method.body()._return(j)

    }

//To generate method
//    JMethod method = definedClass.method(3, String.class, "getCustomerInfo()");
//
//    method.annotate(jCodeModel.ref("javax.ws.rs.GET"));
//    method.annotate(jCodeModel.ref("javax.ws.rs.Path")).param("value", "/getCustomerInfo");

    return cm;
  }
}
