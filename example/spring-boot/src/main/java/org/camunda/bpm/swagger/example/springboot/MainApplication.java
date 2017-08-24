package org.camunda.bpm.swagger.example.springboot;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class MainApplication {

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class,args);
  }
}
