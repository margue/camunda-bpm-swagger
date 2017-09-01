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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.history.HistoricCaseInstanceDto;
import org.camunda.bpm.engine.rest.dto.history.HistoricCaseInstanceQueryDto;
import org.camunda.bpm.engine.rest.dto.swagger.HistoricCaseInstanceDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.HistoricCaseInstanceRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.history.HistoricCaseInstanceResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/case-instance")
@Api(value = "Historic Instance", tags = "Historic")
public class HistoricCaseInstanceRestServiceSwagger
    extends HistoricCaseInstanceRestServiceImpl
{

    public HistoricCaseInstanceRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Case Instances Count", notes = "Operation Get Historic Case Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getHistoricCaseInstancesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricCaseInstancesCount(uriInfo);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Historic Case Instance", notes = "Operation Get Historic Case Instance", response = org.camunda.bpm.engine.rest.dto.history.HistoricCaseInstanceDto.class)
    @Override
    public HistoricCaseInstanceResource getHistoricCaseInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getHistoricCaseInstance(id);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Case Instance", notes = "Operation Get Historic Case Instance")
    public HistoricCaseInstanceDtoSwagger getHistoricCaseInstanceGetHistoricCaseInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getHistoricCaseInstance(id).getHistoricCaseInstance();
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Case Instances", notes = "Operation Query Historic Case Instances")
    @Override
    public List<HistoricCaseInstanceDto> queryHistoricCaseInstances(
        @ApiParam("Parameter historicCaseInstanceQueryDto")
        HistoricCaseInstanceQueryDto historicCaseInstanceQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryHistoricCaseInstances(historicCaseInstanceQueryDto, firstResult, maxResults);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Case Instances", notes = "Operation Get Historic Case Instances")
    @Override
    public List<HistoricCaseInstanceDto> getHistoricCaseInstances(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getHistoricCaseInstances(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Case Instances Count", notes = "Operation Query Historic Case Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryHistoricCaseInstancesCount(
        @ApiParam("Parameter historicCaseInstanceQueryDto")
        HistoricCaseInstanceQueryDto historicCaseInstanceQueryDto) {
        return super.queryHistoricCaseInstancesCount(historicCaseInstanceQueryDto);
    }
}
