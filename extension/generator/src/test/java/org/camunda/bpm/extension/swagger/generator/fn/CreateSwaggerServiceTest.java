package org.camunda.bpm.extension.swagger.generator.fn;

import com.helger.jcodemodel.AbstractCodeWriter;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JPackage;
import org.camunda.bpm.engine.rest.TaskRestService;
import org.camunda.bpm.engine.rest.impl.TaskRestServiceImpl;
import org.camunda.bpm.extension.swagger.generator.model.CamundaRestService;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@Ignore
public class CreateSwaggerServiceTest {

  private final CreateSwaggerService service = new CreateSwaggerService();

  @Test
  public void name() throws Exception {
    JCodeModel model = service.apply(new CamundaRestService(TaskRestService.class, TaskRestServiceImpl.class));

    model.build(new AbstractCodeWriter(Charset.forName("UTF-8"), System.lineSeparator()) {
      @Override
      public OutputStream openBinary(JPackage jPackage, String s) throws IOException {
        return System.out;
      }

      @Override
      public void close() throws IOException {

      }
    });

  }
}
