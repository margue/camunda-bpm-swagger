# Camunda BPM Swagger

Swagger Support for Camunda BPM REST API. 

**Features:**

* Provides a Swagger OpenSpec API documentation of Camunda BPM Engine REST.
* Provides Swagger UI archive which can be used to explore the API.
* Supports Camunda BPM Engine 7.7

## Installation and Usage

**How to use with Spring Boot**

In order to use the Swagger in context of a Camunda SpringBoot application, just include 

    <dependency>
      <groupId>org.camunda.bpm.extension.swagger</groupId>
      <artifactId>camunda-bpm-swagger-json</artifactId>
      <version>7.7.0</version>
    </dependency>
    <dependency>
      <groupId>org.webjars</groupId>
      <artifactId>swagger-ui</artifactId>
      <version>3.1.4</version>
    </dependency>

into your `pom.xml``.

* swagger-ui-webjar - supplies the Swagger-UI application and allows access via /webjars/swagger-ui/<version>
* camunda-swagger-json - contains the Camunda swagger.json, so its accesible on "/swagger.json"

To use, run the main application and go to

[http://localhost:8080/webjars/swagger-ui/3.1.4/index.html?docExpansion=false&url=/swagger.json](http://localhost:8080/webjars/swagger-ui/3.1.4/index.html?docExpansion=false&url=/swagger.json)

**How to use in container**

This project produces a WAR artifact `swagger-ui.war` which can be obtained under the following coordinates.

    <dependency>
      <groupId>org.camunda.bpm.extension.swagger</groupId>
      <artifactId>swagger-ui</artifactId>
      <version>1.0.0</version>	  
      <type>war</type>
    </dependency>

Please deploy it into the container with your Camunda ditribution (tested with Wildfly 10 Camunda distribution). To invoke the Swagger UI, 
call [http://localhost:8080/swagger/webjars/swagger-ui/3.1.4/index.html?docExpansion=none&url=/swagger/swagger.json](http://localhost:8080/swagger/webjars/swagger-ui/3.1.4/index.html?docExpansion=none&url=/swagger/swagger.json) 
in your browser.


## Release Notes

### 1.0.0

* Initial contribution

## Limitations

* Current Camunda BPM REST reference guide supplies not all information for definition of query parameters in some methods. 
Especially, some data type information is not present and is considered as simple string.
* Due to the naming conventions and the size of the resulting specification, the entire History API is excluded from the spec.


## Development

**How to build**

Checkout the code and run 

    mvn clean install -PrestDocs -PgenerateJson
    
The profiles are needed only once to generate the documentation dictionary and the `swagger.json` artifact.

If you are curious how it works, check out the `maven-plugin/generator/README.md`.

* [Issue Tracker](https://github.com/holisticon/camunda-bpm-swagger/issues)
* [Contributing](./CONTRIBUTING) 


## Maintainer

* [Jan Galinski](https://github.com/jangalinski)
* [Simon Zambrovski](https://github.com/zambrovski)
* [Nils Ernsing](https://github.com/nernsting)


## License

* [Apache License, Version 2.0](./LICENSE)
