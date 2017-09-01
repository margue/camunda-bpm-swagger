package org.camunda.bpm.engine.rest.swagger;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.history.DeleteHistoricProcessInstancesDto;
import org.camunda.bpm.engine.rest.dto.history.HistoricProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.history.HistoricProcessInstanceQueryDto;
import org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.HistoricProcessInstanceDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.HistoricProcessInstanceRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.history.HistoricProcessInstanceResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/process-instance")
@Api(value = "Historic Instance", tags = "Historic")
public class HistoricProcessInstanceRestServiceSwagger
    extends HistoricProcessInstanceRestServiceImpl
{

    public HistoricProcessInstanceRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Process Instances", notes = "Operation Get Historic Process Instances")
    @Override
    public List<HistoricProcessInstanceDto> getHistoricProcessInstances(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getHistoricProcessInstances(uriInfo, firstResult, maxResults);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Historic Process Instance", notes = "Operation Get Historic Process Instance", response = org.camunda.bpm.engine.rest.dto.history.HistoricProcessInstanceDto.class)
    @Override
    public HistoricProcessInstanceResource getHistoricProcessInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getHistoricProcessInstance(id);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Process Instance", notes = "Operation Get Historic Process Instance")
    public HistoricProcessInstanceDtoSwagger getHistoricProcessInstanceGetHistoricProcessInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getHistoricProcessInstance(id).getHistoricProcessInstance();
    }

    @Path("/{id}")
    @DELETE
    @ApiOperation(value = "Delete Historic Process Instance", notes = "Operation Delete Historic Process Instance")
    public void getHistoricProcessInstanceDeleteHistoricProcessInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        getHistoricProcessInstance(id).deleteHistoricProcessInstance();
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Process Instances", notes = "Operation Query Historic Process Instances")
    @Override
    public List<HistoricProcessInstanceDto> queryHistoricProcessInstances(
        @ApiParam("Parameter historicProcessInstanceQueryDto")
        HistoricProcessInstanceQueryDto historicProcessInstanceQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryHistoricProcessInstances(historicProcessInstanceQueryDto, firstResult, maxResults);
    }

    @Path("/delete")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Delete Async", notes = "Operation Delete Async")
    @Override
    public BatchDtoSwagger deleteAsync(
        @ApiParam("Parameter deleteHistoricProcessInstancesDto")
        DeleteHistoricProcessInstancesDto deleteHistoricProcessInstancesDto) {
        return super.deleteAsync(deleteHistoricProcessInstancesDto);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Process Instances Count", notes = "Operation Get Historic Process Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getHistoricProcessInstancesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricProcessInstancesCount(uriInfo);
    }

    @Path("/report")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Process Instances Report", notes = "Operation Get Historic Process Instances Report")
    @Override
    public Response getHistoricProcessInstancesReport(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter request")
        Request request) {
        return super.getHistoricProcessInstancesReport(uriInfo, request);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Process Instances Count", notes = "Operation Query Historic Process Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryHistoricProcessInstancesCount(
        @ApiParam("Parameter historicProcessInstanceQueryDto")
        HistoricProcessInstanceQueryDto historicProcessInstanceQueryDto) {
        return super.queryHistoricProcessInstancesCount(historicProcessInstanceQueryDto);
    }
}
