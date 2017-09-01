package org.camunda.bpm.engine.rest.swagger;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.rest.dto.repository.DecisionRequirementsDefinitionDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.impl.DecisionRequirementsDefinitionRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.repository.DecisionRequirementsDefinitionResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/decision-requirements-definition")
@Api(value = "Decision Definition", tags = "Decision")
public class DecisionRequirementsDefinitionRestServiceSwagger
    extends DecisionRequirementsDefinitionRestServiceImpl
{

    public DecisionRequirementsDefinitionRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Decision Requirements Definition By Id", notes = "Operation Get Decision Requirements Definition By Id", response = org.camunda.bpm.engine.rest.dto.repository.DecisionRequirementsDefinitionDto.class)
    @Override
    public DecisionRequirementsDefinitionResource getDecisionRequirementsDefinitionById(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getDecisionRequirementsDefinitionById(id);
    }

    @Path("/{id}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Requirements Definition Dmn Xml", notes = "Operation Get Decision Requirements Definition Dmn Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionRequirementsDefinitionXmlDtoSwagger getDecisionRequirementsDefinitionByIdGetDecisionRequirementsDefinitionDmnXml(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getDecisionRequirementsDefinitionById(id).getDecisionRequirementsDefinitionDmnXml();
    }

    @Path("/{id}/diagram")
    @GET
    @ApiOperation(value = "Get Decision Requirements Definition Diagram", notes = "Operation Get Decision Requirements Definition Diagram")
    public Response getDecisionRequirementsDefinitionByIdGetDecisionRequirementsDefinitionDiagram(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getDecisionRequirementsDefinitionById(id).getDecisionRequirementsDefinitionDiagram();
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Requirements Definition", notes = "Operation Get Decision Requirements Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionRequirementsDefinitionDtoSwagger getDecisionRequirementsDefinitionByIdGetDecisionRequirementsDefinition(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getDecisionRequirementsDefinitionById(id).getDecisionRequirementsDefinition();
    }

    @Path("/key/{key}/tenant-id/{tenant-id}")
    @ApiOperation(value = "Get Decision Requirements Definition By Key And Tenant Id", notes = "Operation Get Decision Requirements Definition By Key And Tenant Id", response = org.camunda.bpm.engine.rest.dto.repository.DecisionRequirementsDefinitionDto.class)
    @Override
    public DecisionRequirementsDefinitionResource getDecisionRequirementsDefinitionByKeyAndTenantId(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return super.getDecisionRequirementsDefinitionByKeyAndTenantId(key, tenantId);
    }

    @Path("/key/{key}/tenant-id/{tenant-id}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Requirements Definition Dmn Xml", notes = "Operation Get Decision Requirements Definition Dmn Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionRequirementsDefinitionXmlDtoSwagger getDecisionRequirementsDefinitionByKeyAndTenantIdGetDecisionRequirementsDefinitionDmnXml(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getDecisionRequirementsDefinitionByKeyAndTenantId(key, tenantId).getDecisionRequirementsDefinitionDmnXml();
    }

    @Path("/key/{key}/tenant-id/{tenant-id}/diagram")
    @GET
    @ApiOperation(value = "Get Decision Requirements Definition Diagram", notes = "Operation Get Decision Requirements Definition Diagram")
    public Response getDecisionRequirementsDefinitionByKeyAndTenantIdGetDecisionRequirementsDefinitionDiagram(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getDecisionRequirementsDefinitionByKeyAndTenantId(key, tenantId).getDecisionRequirementsDefinitionDiagram();
    }

    @Path("/key/{key}/tenant-id/{tenant-id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Requirements Definition", notes = "Operation Get Decision Requirements Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionRequirementsDefinitionDtoSwagger getDecisionRequirementsDefinitionByKeyAndTenantIdGetDecisionRequirementsDefinition(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getDecisionRequirementsDefinitionByKeyAndTenantId(key, tenantId).getDecisionRequirementsDefinition();
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Requirements Definitions", notes = "Operation Get Decision Requirements Definitions")
    @Override
    public List<DecisionRequirementsDefinitionDto> getDecisionRequirementsDefinitions(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getDecisionRequirementsDefinitions(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Requirements Definitions Count", notes = "Operation Get Decision Requirements Definitions Count")
    @Override
    public CountResultDtoSwagger getDecisionRequirementsDefinitionsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getDecisionRequirementsDefinitionsCount(uriInfo);
    }

    @Path("/key/{key}")
    @ApiOperation(value = "Get Decision Requirements Definition By Key", notes = "Operation Get Decision Requirements Definition By Key", response = org.camunda.bpm.engine.rest.dto.repository.DecisionRequirementsDefinitionDto.class)
    @Override
    public DecisionRequirementsDefinitionResource getDecisionRequirementsDefinitionByKey(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return super.getDecisionRequirementsDefinitionByKey(key);
    }

    @Path("/key/{key}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Requirements Definition Dmn Xml", notes = "Operation Get Decision Requirements Definition Dmn Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionRequirementsDefinitionXmlDtoSwagger getDecisionRequirementsDefinitionByKeyGetDecisionRequirementsDefinitionDmnXml(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getDecisionRequirementsDefinitionByKey(key).getDecisionRequirementsDefinitionDmnXml();
    }

    @Path("/key/{key}/diagram")
    @GET
    @ApiOperation(value = "Get Decision Requirements Definition Diagram", notes = "Operation Get Decision Requirements Definition Diagram")
    public Response getDecisionRequirementsDefinitionByKeyGetDecisionRequirementsDefinitionDiagram(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getDecisionRequirementsDefinitionByKey(key).getDecisionRequirementsDefinitionDiagram();
    }

    @Path("/key/{key}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Requirements Definition", notes = "Operation Get Decision Requirements Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionRequirementsDefinitionDtoSwagger getDecisionRequirementsDefinitionByKeyGetDecisionRequirementsDefinition(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getDecisionRequirementsDefinitionByKey(key).getDecisionRequirementsDefinition();
    }
}
