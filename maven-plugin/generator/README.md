# Swagger Code Generator Plugin

This plugin creates new server REST implementation around Camunda Engine REST, based on the original `camunda-engine-rest` containing Swagger annotations, enriched with documentation from Camunda Docs. This implementation is created as generated Java code and performs the following steps:

## Features

 - For every `RestService` class generates a new `RestServiceSwagger` extending its implementation (`RestServiceImpl`)
 - `RestServiceSwagger` overrides all methods, delegating the invocation to the original methods
 - `RestServiceSwagger` adds additional methods for every method of returning a `Resource`   by a method in the `RestService` or `Resource` delegating the invocation to retrieval of the `Resource` and invocation of the original method on the `Resource`. This flattens the API. 
 - Annotates every `RestServiceSwagger` with Swagger `Api` annotation
 - Annotates every generated method with Swagger `ApiOperation` annotation
 - Annotates every method with HTTP return codes.
 - Collects information about every DTO being a parameter or a return type.
 - Naming is considering special types like ObjectMapper, UriInfo
  

## Limitations

- UriInfo is a collecting type, allowing almost every combination of query parameters to be passed.



## Observations

- The method call of `TaskService#getTask(@PathParam("id") String id)` returns the `TaskResource`. The call of `TaskResource#delete(@PathParam("id") String id)` reuses the id of task present in path only once. This is probably not needed / is a bug.
- The method `DecisionDefinitionResource#evaluateDecision(@Context UriInfo context, EvaluateDecisionDto parameters)` returns `List<Map<String, VariableValueDto>>` is delivers a return type not supported by the plugin.
- The method `DeploymentRestServiceImpl#createDeployment(UriInfo uriInfo, MultipartFormData payload)` returns `DeploymentWithDefinitionsDto` extending the `DeploymentDto` which is a return type of the method from interface method `DeploymentRestService#createDeployment(@Context UriInfo uriInfo, MultipartFormData multipartFormData)`. The return type is compatible, but not a part of interface which is probably a bug.

