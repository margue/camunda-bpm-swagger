package org.camunda.bpm.extension.swagger.camundabpmswaggerroot;

import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.config.ConfigFactory;
import io.swagger.config.ScannerFactory;
import io.swagger.config.SwaggerConfig;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.ReflectiveJaxrsScanner;
import io.swagger.jaxrs.config.SwaggerConfigLocator;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Swagger;
import org.camunda.bpm.engine.rest.TaskRestService;
import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


  @Component
  @DependsOn()
  public static class MyCamundaJerseyResourceConfig extends CamundaJerseyResourceConfig {


    @Value("${spring.jersey.application-path:/rest}")
    private String apiPath;



    @Override
    public void afterPropertiesSet() throws Exception {
      this.configureSwagger();
      registerCamundaRestResources();
      registerAdditionalResources();
      this.register(WadlResource.class);


    }

    protected void registerCamundaRestResources() {
      this.registerClasses(CamundaRestResources.getResourceClasses());
      this.registerClasses(CamundaRestResources.getConfigurationClasses());
      this.register(JacksonFeature.class);

    }

    private void configureSwagger() {
      // Available at localhost:port/swagger.json
      this.register(ApiListingResource.class);
      this.register(SwaggerSerializers.class);

      BeanConfig config = new BeanConfig();
      config.setConfigId("springboot-jersey-swagger-docker-example");
      config.setTitle("Spring Boot + Jersey + Swagger + Docker Example");
      config.setVersion("v1");
      config.setContact("Orlando L Otero");
      config.setSchemes(new String[] { "http", "https" });
      config.setBasePath(this.apiPath);
      config.setResourcePackage(CamundaBpmSwaggerRootApplication.class.getPackage().getName());
      config.setPrettyPrint(true);
      config.setScan(true);
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
