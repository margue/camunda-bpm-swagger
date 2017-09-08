package org.camunda.bpm.swagger.example.springboot;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableProcessApplication
public class MainApplication {

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

}
