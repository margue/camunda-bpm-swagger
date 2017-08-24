package org.camunda.bpm.extension.swagger.generator;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.camunda.bpm.engine.rest.TaskRestService;
import org.camunda.bpm.engine.rest.impl.TaskRestServiceImpl;
import org.camunda.bpm.extension.swagger.generator.model.CamundaRestService;
import org.junit.Test;

import com.helger.jcodemodel.AbstractCodeWriter;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JPackage;

public class SwaggerServiceModelGeneratorTest {

  private SwaggerServiceModelGenerator service;

  
  @Test
  public void testRun() throws Exception {
    CamundaRestService camundaRestService = new CamundaRestService(TaskRestService.class, TaskRestServiceImpl.class);
    service = new SwaggerServiceModelGenerator(camundaRestService);
    JCodeModel model = service.getCodeModel();

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
