package org.camunda.bpm.engine.rest.swagger;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.rest.dto.HistoryTimeToLiveDto;
import org.camunda.bpm.engine.rest.dto.repository.CaseDefinitionDto;
import org.camunda.bpm.engine.rest.dto.runtime.CreateCaseInstanceDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.impl.CaseDefinitionRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.repository.CaseDefinitionResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/case-definition")
@Api(value = "Case Rest", tags = "Case")
public class CaseDefinitionRestServiceSwagger
    extends CaseDefinitionRestServiceImpl
{

    public CaseDefinitionRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Definitions", notes = "Operation Get Case Definitions")
    @Override
    public List<CaseDefinitionDto> getCaseDefinitions(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getCaseDefinitions(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Definitions Count", notes = "Operation Get Case Definitions Count")
    @Override
    public CountResultDtoSwagger getCaseDefinitionsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getCaseDefinitionsCount(uriInfo);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Case Definition By Id", notes = "Operation Get Case Definition By Id", response = org.camunda.bpm.engine.rest.dto.repository.CaseDefinitionDto.class)
    @Override
    public CaseDefinitionResource getCaseDefinitionById(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getCaseDefinitionById(id);
    }

    @Path("/{id}/create")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Create Case Instance", notes = "Operation Create Case Instance")
    public org.camunda.bpm.engine.rest.dto.swagger.CaseInstanceDtoSwagger getCaseDefinitionByIdCreateCaseInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter createCaseInstanceDto")
        CreateCaseInstanceDto createCaseInstanceDto) {
        return getCaseDefinitionById(id).createCaseInstance(uriInfo, createCaseInstanceDto);
    }

    @Path("/{id}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Definition Cmmn Xml", notes = "Operation Get Case Definition Cmmn Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.CaseDefinitionDiagramDtoSwagger getCaseDefinitionByIdGetCaseDefinitionCmmnXml(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getCaseDefinitionById(id).getCaseDefinitionCmmnXml();
    }

    @Path("/{id}/diagram")
    @GET
    @ApiOperation(value = "Get Case Definition Diagram", notes = "Operation Get Case Definition Diagram")
    public Response getCaseDefinitionByIdGetCaseDefinitionDiagram(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getCaseDefinitionById(id).getCaseDefinitionDiagram();
    }

    @Path("/{id}/history-time-to-live")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update History Time To Live", notes = "Operation Update History Time To Live")
    public void getCaseDefinitionByIdUpdateHistoryTimeToLive(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter historyTimeToLiveDto")
        HistoryTimeToLiveDto historyTimeToLiveDto) {
        getCaseDefinitionById(id).updateHistoryTimeToLive(historyTimeToLiveDto);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Definition", notes = "Operation Get Case Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.CaseDefinitionDtoSwagger getCaseDefinitionByIdGetCaseDefinition(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getCaseDefinitionById(id).getCaseDefinition();
    }

    @Path("/key/{key}")
    @ApiOperation(value = "Get Case Definition By Key", notes = "Operation Get Case Definition By Key", response = org.camunda.bpm.engine.rest.dto.repository.CaseDefinitionDto.class)
    @Override
    public CaseDefinitionResource getCaseDefinitionByKey(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return super.getCaseDefinitionByKey(key);
    }

    @Path("/key/{key}/create")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Create Case Instance", notes = "Operation Create Case Instance")
    public org.camunda.bpm.engine.rest.dto.swagger.CaseInstanceDtoSwagger getCaseDefinitionByKeyCreateCaseInstance(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter createCaseInstanceDto")
        CreateCaseInstanceDto createCaseInstanceDto) {
        return getCaseDefinitionByKey(key).createCaseInstance(uriInfo, createCaseInstanceDto);
    }

    @Path("/key/{key}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Definition Cmmn Xml", notes = "Operation Get Case Definition Cmmn Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.CaseDefinitionDiagramDtoSwagger getCaseDefinitionByKeyGetCaseDefinitionCmmnXml(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getCaseDefinitionByKey(key).getCaseDefinitionCmmnXml();
    }

    @Path("/key/{key}/diagram")
    @GET
    @ApiOperation(value = "Get Case Definition Diagram", notes = "Operation Get Case Definition Diagram")
    public Response getCaseDefinitionByKeyGetCaseDefinitionDiagram(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getCaseDefinitionByKey(key).getCaseDefinitionDiagram();
    }

    @Path("/key/{key}/history-time-to-live")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update History Time To Live", notes = "Operation Update History Time To Live")
    public void getCaseDefinitionByKeyUpdateHistoryTimeToLive(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @ApiParam("Parameter historyTimeToLiveDto")
        HistoryTimeToLiveDto historyTimeToLiveDto) {
        getCaseDefinitionByKey(key).updateHistoryTimeToLive(historyTimeToLiveDto);
    }

    @Path("/key/{key}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Definition", notes = "Operation Get Case Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.CaseDefinitionDtoSwagger getCaseDefinitionByKeyGetCaseDefinition(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getCaseDefinitionByKey(key).getCaseDefinition();
    }

    @Path("/key/{key}/tenant-id/{tenantId}")
    @ApiOperation(value = "Get Case Definition By Key And Tenant Id", notes = "Operation Get Case Definition By Key And Tenant Id", response = org.camunda.bpm.engine.rest.dto.repository.CaseDefinitionDto.class)
    @Override
    public CaseDefinitionResource getCaseDefinitionByKeyAndTenantId(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return super.getCaseDefinitionByKeyAndTenantId(key, tenantId);
    }

    @Path("/key/{key}/tenant-id/{tenantId}/create")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Create Case Instance", notes = "Operation Create Case Instance")
    public org.camunda.bpm.engine.rest.dto.swagger.CaseInstanceDtoSwagger getCaseDefinitionByKeyAndTenantIdCreateCaseInstance(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter createCaseInstanceDto")
        CreateCaseInstanceDto createCaseInstanceDto) {
        return getCaseDefinitionByKeyAndTenantId(key, tenantId).createCaseInstance(uriInfo, createCaseInstanceDto);
    }

    @Path("/key/{key}/tenant-id/{tenantId}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Definition Cmmn Xml", notes = "Operation Get Case Definition Cmmn Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.CaseDefinitionDiagramDtoSwagger getCaseDefinitionByKeyAndTenantIdGetCaseDefinitionCmmnXml(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getCaseDefinitionByKeyAndTenantId(key, tenantId).getCaseDefinitionCmmnXml();
    }

    @Path("/key/{key}/tenant-id/{tenantId}/diagram")
    @GET
    @ApiOperation(value = "Get Case Definition Diagram", notes = "Operation Get Case Definition Diagram")
    public Response getCaseDefinitionByKeyAndTenantIdGetCaseDefinitionDiagram(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getCaseDefinitionByKeyAndTenantId(key, tenantId).getCaseDefinitionDiagram();
    }

    @Path("/key/{key}/tenant-id/{tenantId}/history-time-to-live")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update History Time To Live", notes = "Operation Update History Time To Live")
    public void getCaseDefinitionByKeyAndTenantIdUpdateHistoryTimeToLive(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @ApiParam("Parameter historyTimeToLiveDto")
        HistoryTimeToLiveDto historyTimeToLiveDto) {
        getCaseDefinitionByKeyAndTenantId(key, tenantId).updateHistoryTimeToLive(historyTimeToLiveDto);
    }

    @Path("/key/{key}/tenant-id/{tenantId}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Definition", notes = "Operation Get Case Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.CaseDefinitionDtoSwagger getCaseDefinitionByKeyAndTenantIdGetCaseDefinition(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getCaseDefinitionByKeyAndTenantId(key, tenantId).getCaseDefinition();
    }
}
