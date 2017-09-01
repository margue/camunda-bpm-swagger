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
import org.camunda.bpm.engine.rest.dto.history.HistoricExternalTaskLogDto;
import org.camunda.bpm.engine.rest.dto.history.HistoricExternalTaskLogQueryDto;
import org.camunda.bpm.engine.rest.dto.swagger.HistoricExternalTaskLogDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.HistoricExternalTaskLogRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.history.HistoricExternalTaskLogResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/external-task-log")
@Api(value = "Historic Task", tags = "Historic")
public class HistoricExternalTaskLogRestServiceSwagger
    extends HistoricExternalTaskLogRestServiceImpl
{

    public HistoricExternalTaskLogRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Historic External Task Log", notes = "Operation Get Historic External Task Log", response = org.camunda.bpm.engine.rest.dto.history.HistoricExternalTaskLogDto.class)
    @Override
    public HistoricExternalTaskLogResource getHistoricExternalTaskLog(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getHistoricExternalTaskLog(id);
    }

    @Path("/{id}/error-details")
    @GET
    @Produces("text/plain")
    @ApiOperation(value = "Get Error Details", notes = "Operation Get Error Details")
    public String getHistoricExternalTaskLogGetErrorDetails(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getHistoricExternalTaskLog(id).getErrorDetails();
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic External Task Log", notes = "Operation Get Historic External Task Log")
    public HistoricExternalTaskLogDtoSwagger getHistoricExternalTaskLogGetHistoricExternalTaskLog(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getHistoricExternalTaskLog(id).getHistoricExternalTaskLog();
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic External Task Logs", notes = "Operation Get Historic External Task Logs")
    @Override
    public List<HistoricExternalTaskLogDto> getHistoricExternalTaskLogs(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getHistoricExternalTaskLogs(uriInfo, firstResult, maxResults);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic External Task Logs", notes = "Operation Query Historic External Task Logs")
    @Override
    public List<HistoricExternalTaskLogDto> queryHistoricExternalTaskLogs(
        @ApiParam("Parameter historicExternalTaskLogQueryDto")
        HistoricExternalTaskLogQueryDto historicExternalTaskLogQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryHistoricExternalTaskLogs(historicExternalTaskLogQueryDto, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic External Task Logs Count", notes = "Operation Get Historic External Task Logs Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getHistoricExternalTaskLogsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricExternalTaskLogsCount(uriInfo);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic External Task Logs Count", notes = "Operation Query Historic External Task Logs Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryHistoricExternalTaskLogsCount(
        @ApiParam("Parameter historicExternalTaskLogQueryDto")
        HistoricExternalTaskLogQueryDto historicExternalTaskLogQueryDto) {
        return super.queryHistoricExternalTaskLogsCount(historicExternalTaskLogQueryDto);
    }
}
