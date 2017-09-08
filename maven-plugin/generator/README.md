# Swagger Code Generator Plugin

This plugin creates new server REST implementation around Camunda Engine REST, based on the original `camunda-engine-rest` containing Swagger annotations, enriched with documentation from Camunda Docs. This implementation is created as generated Java code and performs the following steps:

## Features

 - For every `RestService` class generates a new `RestServiceSwagger` extending its implementation (`RestServiceImpl`)
 - `RestServiceSwagger` overrides all methods returning simple types with simple parameter types (primitives and non-parameterized DTO classes), delegating the invocation to the original methods
 - `RestServiceSwagger` adds additional methods returning parameterized types delegating the invocation to original methods
   - Supports `List<X>` as return type, if X is primitive, simple type or `DTO`
   - Supports `Map<String, X>` as return type, if X is primitive, simple type or `DTO` 
 - `RestServiceSwagger` adds additional methods for every method of returning a `Resource`   by a method in the `RestService` or `Resource` delegating the invocation to retrieval of the `Resource` and invocation of the original method on the `Resource`. This flattens the API.
 - For every `DTO` class used as a parameter or as a return type of a `RestServiceSwagger` generate new `DTOSwagger` extending the original 
 - Annotates every `RestServiceSwagger` with Swagger `Api` annotation
 - Annotates every generated method with Swagger `ApiOperation` annotation
 - Annotates every `DTOSwagger` with Swagger `ApiModel` annotation
 - Annotates every getter of a `DTOSwagger` with Swagger `ApiProperty` annotation 

## Limitations

- The return type `List<Map<String, X>` is not supported 



## Observations

- The method call of `TaskService#getTask(@PathParam("id") String id)` returns the `TaskResource`. The call of `TaskResource#delete(@PathParam("id") String id)` reuses the id of task present in path only once. This is probably not needed / is a bug.
- The method `DecisionDefinitionResource#evaluateDecision(@Context UriInfo context, EvaluateDecisionDto parameters)` returns `List<Map<String, VariableValueDto>>` is delivers a return type not supported by the plugin.
- The method `DeploymentRestServiceImpl#createDeployment(UriInfo uriInfo, MultipartFormData payload)` returns `DeploymentWithDefinitionsDto` extending the `DeploymentDto` which is a return type of the method from interface method `DeploymentRestService#createDeployment(@Context UriInfo uriInfo, MultipartFormData multipartFormData)`. The return type is compatible, but not a part of interface which is probably a bug.

