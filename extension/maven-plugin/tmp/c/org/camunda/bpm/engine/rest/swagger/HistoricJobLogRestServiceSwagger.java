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
import org.camunda.bpm.engine.rest.dto.history.HistoricJobLogDto;
import org.camunda.bpm.engine.rest.dto.history.HistoricJobLogQueryDto;
import org.camunda.bpm.engine.rest.dto.swagger.HistoricJobLogDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.HistoricJobLogRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.history.HistoricJobLogResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/job-log")
@Api(value = "Historic Log", tags = "Historic")
public class HistoricJobLogRestServiceSwagger
    extends HistoricJobLogRestServiceImpl
{

    public HistoricJobLogRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Job Logs Count", notes = "Operation Get Historic Job Logs Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getHistoricJobLogsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricJobLogsCount(uriInfo);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Job Logs Count", notes = "Operation Query Historic Job Logs Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryHistoricJobLogsCount(
        @ApiParam("Parameter historicJobLogQueryDto")
        HistoricJobLogQueryDto historicJobLogQueryDto) {
        return super.queryHistoricJobLogsCount(historicJobLogQueryDto);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Historic Job Log", notes = "Operation Get Historic Job Log", response = org.camunda.bpm.engine.rest.dto.history.HistoricJobLogDto.class)
    @Override
    public HistoricJobLogResource getHistoricJobLog(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getHistoricJobLog(id);
    }

    @Path("/{id}/stacktrace")
    @GET
    @Produces("text/plain")
    @ApiOperation(value = "Get Stacktrace", notes = "Operation Get Stacktrace")
    public String getHistoricJobLogGetStacktrace(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getHistoricJobLog(id).getStacktrace();
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Job Log", notes = "Operation Get Historic Job Log")
    public HistoricJobLogDtoSwagger getHistoricJobLogGetHistoricJobLog(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getHistoricJobLog(id).getHistoricJobLog();
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Job Logs", notes = "Operation Get Historic Job Logs")
    @Override
    public List<HistoricJobLogDto> getHistoricJobLogs(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getHistoricJobLogs(uriInfo, firstResult, maxResults);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Job Logs", notes = "Operation Query Historic Job Logs")
    @Override
    public List<HistoricJobLogDto> queryHistoricJobLogs(
        @ApiParam("Parameter historicJobLogQueryDto")
        HistoricJobLogQueryDto historicJobLogQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryHistoricJobLogs(historicJobLogQueryDto, firstResult, maxResults);
    }
}
