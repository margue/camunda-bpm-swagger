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
import org.camunda.bpm.engine.rest.dto.dmn.EvaluateDecisionDto;
import org.camunda.bpm.engine.rest.dto.repository.DecisionDefinitionDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.impl.DecisionDefinitionRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.repository.DecisionDefinitionResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/decision-definition")
@Api(value = "Decision Rest", tags = "Decision")
public class DecisionDefinitionRestServiceSwagger
    extends DecisionDefinitionRestServiceImpl
{

    public DecisionDefinitionRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/key/{key}/tenant-id/{tenantId}")
    @ApiOperation(value = "Get Decision Definition By Key And Tenant Id", notes = "Operation Get Decision Definition By Key And Tenant Id", response = org.camunda.bpm.engine.rest.dto.repository.DecisionDefinitionDto.class)
    @Override
    public DecisionDefinitionResource getDecisionDefinitionByKeyAndTenantId(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return super.getDecisionDefinitionByKeyAndTenantId(key, tenantId);
    }

    @Path("/key/{key}/tenant-id/{tenantId}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Definition Dmn Xml", notes = "Operation Get Decision Definition Dmn Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionDefinitionDiagramDtoSwagger getDecisionDefinitionByKeyAndTenantIdGetDecisionDefinitionDmnXml(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getDecisionDefinitionByKeyAndTenantId(key, tenantId).getDecisionDefinitionDmnXml();
    }

    @Path("/key/{key}/tenant-id/{tenantId}/evaluate")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Evaluate Decision", notes = "Operation Evaluate Decision")
    public List getDecisionDefinitionByKeyAndTenantIdEvaluateDecision(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter evaluateDecisionDto")
        EvaluateDecisionDto evaluateDecisionDto) {
        return getDecisionDefinitionByKeyAndTenantId(key, tenantId).evaluateDecision(uriInfo, evaluateDecisionDto);
    }

    @Path("/key/{key}/tenant-id/{tenantId}/history-time-to-live")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update History Time To Live", notes = "Operation Update History Time To Live")
    public void getDecisionDefinitionByKeyAndTenantIdUpdateHistoryTimeToLive(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @ApiParam("Parameter historyTimeToLiveDto")
        HistoryTimeToLiveDto historyTimeToLiveDto) {
        getDecisionDefinitionByKeyAndTenantId(key, tenantId).updateHistoryTimeToLive(historyTimeToLiveDto);
    }

    @Path("/key/{key}/tenant-id/{tenantId}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Definition", notes = "Operation Get Decision Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionDefinitionDtoSwagger getDecisionDefinitionByKeyAndTenantIdGetDecisionDefinition(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getDecisionDefinitionByKeyAndTenantId(key, tenantId).getDecisionDefinition();
    }

    @Path("/key/{key}/tenant-id/{tenantId}/diagram")
    @GET
    @ApiOperation(value = "Get Decision Definition Diagram", notes = "Operation Get Decision Definition Diagram")
    public Response getDecisionDefinitionByKeyAndTenantIdGetDecisionDefinitionDiagram(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getDecisionDefinitionByKeyAndTenantId(key, tenantId).getDecisionDefinitionDiagram();
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Decision Definition By Id", notes = "Operation Get Decision Definition By Id", response = org.camunda.bpm.engine.rest.dto.repository.DecisionDefinitionDto.class)
    @Override
    public DecisionDefinitionResource getDecisionDefinitionById(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getDecisionDefinitionById(id);
    }

    @Path("/{id}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Definition Dmn Xml", notes = "Operation Get Decision Definition Dmn Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionDefinitionDiagramDtoSwagger getDecisionDefinitionByIdGetDecisionDefinitionDmnXml(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getDecisionDefinitionById(id).getDecisionDefinitionDmnXml();
    }

    @Path("/{id}/evaluate")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Evaluate Decision", notes = "Operation Evaluate Decision")
    public List getDecisionDefinitionByIdEvaluateDecision(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter evaluateDecisionDto")
        EvaluateDecisionDto evaluateDecisionDto) {
        return getDecisionDefinitionById(id).evaluateDecision(uriInfo, evaluateDecisionDto);
    }

    @Path("/{id}/history-time-to-live")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update History Time To Live", notes = "Operation Update History Time To Live")
    public void getDecisionDefinitionByIdUpdateHistoryTimeToLive(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter historyTimeToLiveDto")
        HistoryTimeToLiveDto historyTimeToLiveDto) {
        getDecisionDefinitionById(id).updateHistoryTimeToLive(historyTimeToLiveDto);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Definition", notes = "Operation Get Decision Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionDefinitionDtoSwagger getDecisionDefinitionByIdGetDecisionDefinition(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getDecisionDefinitionById(id).getDecisionDefinition();
    }

    @Path("/{id}/diagram")
    @GET
    @ApiOperation(value = "Get Decision Definition Diagram", notes = "Operation Get Decision Definition Diagram")
    public Response getDecisionDefinitionByIdGetDecisionDefinitionDiagram(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getDecisionDefinitionById(id).getDecisionDefinitionDiagram();
    }

    @Path("/key/{key}")
    @ApiOperation(value = "Get Decision Definition By Key", notes = "Operation Get Decision Definition By Key", response = org.camunda.bpm.engine.rest.dto.repository.DecisionDefinitionDto.class)
    @Override
    public DecisionDefinitionResource getDecisionDefinitionByKey(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return super.getDecisionDefinitionByKey(key);
    }

    @Path("/key/{key}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Definition Dmn Xml", notes = "Operation Get Decision Definition Dmn Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionDefinitionDiagramDtoSwagger getDecisionDefinitionByKeyGetDecisionDefinitionDmnXml(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getDecisionDefinitionByKey(key).getDecisionDefinitionDmnXml();
    }

    @Path("/key/{key}/evaluate")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Evaluate Decision", notes = "Operation Evaluate Decision")
    public List getDecisionDefinitionByKeyEvaluateDecision(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter evaluateDecisionDto")
        EvaluateDecisionDto evaluateDecisionDto) {
        return getDecisionDefinitionByKey(key).evaluateDecision(uriInfo, evaluateDecisionDto);
    }

    @Path("/key/{key}/history-time-to-live")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update History Time To Live", notes = "Operation Update History Time To Live")
    public void getDecisionDefinitionByKeyUpdateHistoryTimeToLive(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @ApiParam("Parameter historyTimeToLiveDto")
        HistoryTimeToLiveDto historyTimeToLiveDto) {
        getDecisionDefinitionByKey(key).updateHistoryTimeToLive(historyTimeToLiveDto);
    }

    @Path("/key/{key}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Definition", notes = "Operation Get Decision Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.DecisionDefinitionDtoSwagger getDecisionDefinitionByKeyGetDecisionDefinition(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getDecisionDefinitionByKey(key).getDecisionDefinition();
    }

    @Path("/key/{key}/diagram")
    @GET
    @ApiOperation(value = "Get Decision Definition Diagram", notes = "Operation Get Decision Definition Diagram")
    public Response getDecisionDefinitionByKeyGetDecisionDefinitionDiagram(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getDecisionDefinitionByKey(key).getDecisionDefinitionDiagram();
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Definitions Count", notes = "Operation Get Decision Definitions Count")
    @Override
    public CountResultDtoSwagger getDecisionDefinitionsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getDecisionDefinitionsCount(uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Definitions", notes = "Operation Get Decision Definitions")
    @Override
    public List<DecisionDefinitionDto> getDecisionDefinitions(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getDecisionDefinitions(uriInfo, firstResult, maxResults);
    }
}
