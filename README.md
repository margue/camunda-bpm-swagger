# Camunda BPM Swagger

Swagger Support for Camunda BPM REST API. 


**Features:**

* Provides a Swagger OpenSpec API documentation of Camunda BPM Engine REST.
* Provides Swagger UI archive which can be used to explore the API.
* Supports Camunda BPM Engine 7.7


## Installation and Usage

**How to build**

Checkout the code and run 

    mvn clean install -PrestDocs -PgenerateJson -PgenerateJsonModule
    
The profiles are needed only once to generate the documentation dictionaries.

**How to use**

This project produces two WAR artifacts:
 - `camunda-bpm-engine-rest-swagger.war`
 - `swagger-ui.war`
 
in order to use it in your Camunda BPM Engine installation, please remove the original `camunda-bpm-engine-rest.war` 
from your deployment directory and copy both artifacts there.

To invoke the Swagger UI, call [http://localhost:8080/swagger/webjars/swagger-ui/3.1.4/index.html?url=/engine-rest/swagger.json](http://localhost:8080/swagger/webjars/swagger-ui/3.1.4/index.html?url=/engine-rest/swagger.json) 
from your browser.


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
