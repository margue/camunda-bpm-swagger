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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.history.HistoricTaskInstanceDto;
import org.camunda.bpm.engine.rest.dto.history.HistoricTaskInstanceQueryDto;
import org.camunda.bpm.engine.rest.impl.history.HistoricTaskInstanceRestServiceImpl;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/task")
@Api(value = "Historic Instance", tags = "Historic")
public class HistoricTaskInstanceRestServiceSwagger
    extends HistoricTaskInstanceRestServiceImpl
{

    public HistoricTaskInstanceRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Task Instances", notes = "Operation Get Historic Task Instances")
    @Override
    public List<HistoricTaskInstanceDto> getHistoricTaskInstances(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getHistoricTaskInstances(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Task Instances Count", notes = "Operation Query Historic Task Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryHistoricTaskInstancesCount(
        @ApiParam("Parameter historicTaskInstanceQueryDto")
        HistoricTaskInstanceQueryDto historicTaskInstanceQueryDto) {
        return super.queryHistoricTaskInstancesCount(historicTaskInstanceQueryDto);
    }

    @Path("/report")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Task Instance Report", notes = "Operation Get Historic Task Instance Report")
    @Override
    public Response getHistoricTaskInstanceReport(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricTaskInstanceReport(uriInfo);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Task Instances", notes = "Operation Query Historic Task Instances")
    @Override
    public List<HistoricTaskInstanceDto> queryHistoricTaskInstances(
        @ApiParam("Parameter historicTaskInstanceQueryDto")
        HistoricTaskInstanceQueryDto historicTaskInstanceQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryHistoricTaskInstances(historicTaskInstanceQueryDto, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Task Instances Count", notes = "Operation Get Historic Task Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getHistoricTaskInstancesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricTaskInstancesCount(uriInfo);
    }
}
