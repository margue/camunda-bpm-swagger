package org.camunda.bpm.extension.swagger.camundabpmswaggerroot;

import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.jaxrs.config.BeanConfig;
import org.camunda.bpm.engine.rest.TaskRestService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

@SpringBootApplication
@ComponentScan("org.camunda.bpm.engine.rest")
@EnableProcessApplication
@EnableSwagger2
public class CamundaBpmSwaggerRootApplication {

  public static void main(String... args) {
    SpringApplication.run(CamundaBpmSwaggerRootApplication.class, args);
  }

  @RestController
  @Api
  public static class FooCtrl {

    @GetMapping("/foo")
    @ApiOperation("foo")
    public String foo() {
      return "hello";
    }

  }

  @Bean
  public Docket newsApi() {

    // @formatter:off
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      //.apis(RequestHandlerSelectors.basePackage(TaskRestService.class.getPackage().getName()))
      .apis(RequestHandlerSelectors.any())
      .paths(Predicates.not(PathSelectors.regex("/error.*")))
      .build()

      .pathMapping("/")
      .apiInfo(new ApiInfoBuilder().title("Camunda REST API").build())
      .directModelSubstitute(LocalDate.class, String.class)
      .genericModelSubstitutes(ResponseEntity.class)
      .enableUrlTemplating(true)
      ;
    // @formatter:on
  }
}
