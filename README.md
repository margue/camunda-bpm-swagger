# Camunda BPM Swagger

Swagger Support for Camunda BPM REST API. 


**Features:**

* Provides a Swagger OpenSpec API documentation of Camunda BPM Engine REST.
* Provides Swagger UI archive which can be used to explore the API.
* Supports Camunda BPM Engine 7.7


## Installation and Usage

**How to build**

Checkout the code and run 

    mvn clean install -PrestDocs -PgenerateJson
    
The profiles are needed only once to generate the documentation dictionary and the `swagger.json` artifact.

**How to use with Spring Boot**

In order to use the Swagger in context of a Camunda SpringBoot application, just include 

    <dependency>
      <groupId>org.camunda.bpm.extension.swagger</groupId>
      <artifactId>camunda-bpm-swagger-json</artifactId>
      <version>7.7.0</version>
    </dependency>

into your application and include the content of `example/spring-boot/src/main/resource/static` into your application.
After packaging and start of Camunda SpringBoot application, call [http://localhost:8080](http://localhost:8080)

**How to use in container**

This project produces a WAR artifact `swagger-ui.war`. Please deploy it into the container (tested with Wildfly 10 Camunda distribution). To invoke the Swagger UI, 
call [http://localhost:8080/swagger/webjars/swagger-ui/3.1.4/index.html?url=/swagger/swagger.json?docExpansion=none](http://localhost:8080/swagger/webjars/swagger-ui/3.1.4/index.html?url=/swagger/swagger.json?docExpansion=none) 
in your browser.




## Release Notes

### 0.1

* Initial contribution

## Limitations

* Current Camunda BPM REST reference guide supplies not all information for definition of query parameters in some methods. 
Especially, some data type information is not present and is considered as simple string.

## Resources

* [Issue Tracker](https://github.com/holisticon/camunda-bpm-swagger/issues)
* [Contributing](./CONTRIBUTING) 


## Maintainer

* [Jan Galinski](https://github.com/jangalinski)
* [Simon Zambrovski](https://github.com/zambrovski)
* [Nils Ernsing](https://github.com/nernsting)


## License

* [Apache License, Version 2.0](./LICENSE)
