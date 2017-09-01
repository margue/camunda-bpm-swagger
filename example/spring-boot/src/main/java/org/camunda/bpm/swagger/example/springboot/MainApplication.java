package org.camunda.bpm.swagger.example.springboot;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;

@SpringBootApplication
@EnableProcessApplication
public class MainApplication implements CommandLineRunner{

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @EventListener
  public void run(PostDeployEvent event) {
    event.getProcessEngine().getRepositoryService().createDeployment()
      .addModelInstance("dummy.bpmn", Bpmn.createExecutableProcess("dummy")
        .startEvent()
        .userTask("task").name("Do Stuff")
        .endEvent()
      .done())
      .deploy();

  }

  @Override
  public void run(String... strings) throws Exception {
    Yaml yaml = new Yaml();

    Object load = yaml.load(MainApplication.class.getResourceAsStream("camunda-rest-docs.yaml"));

    System.out.println(load);
  }
}
