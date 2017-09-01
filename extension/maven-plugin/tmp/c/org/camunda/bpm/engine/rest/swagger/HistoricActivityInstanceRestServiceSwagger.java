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
import org.camunda.bpm.engine.rest.dto.history.HistoricActivityInstanceDto;
import org.camunda.bpm.engine.rest.dto.history.HistoricActivityInstanceQueryDto;
import org.camunda.bpm.engine.rest.dto.swagger.HistoricActivityInstanceDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.HistoricActivityInstanceRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.history.HistoricActivityInstanceResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/activity-instance")
@Api(value = "Historic Instance", tags = "Historic")
public class HistoricActivityInstanceRestServiceSwagger
    extends HistoricActivityInstanceRestServiceImpl
{

    public HistoricActivityInstanceRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Historic Case Instance", notes = "Operation Get Historic Case Instance", response = org.camunda.bpm.engine.rest.dto.history.HistoricActivityInstanceDto.class)
    @Override
    public HistoricActivityInstanceResource getHistoricCaseInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getHistoricCaseInstance(id);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Activity Instance", notes = "Operation Get Historic Activity Instance")
    public HistoricActivityInstanceDtoSwagger getHistoricCaseInstanceGetHistoricActivityInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getHistoricCaseInstance(id).getHistoricActivityInstance();
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Activity Instances", notes = "Operation Get Historic Activity Instances")
    @Override
    public List<HistoricActivityInstanceDto> getHistoricActivityInstances(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getHistoricActivityInstances(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Activity Instances Count", notes = "Operation Get Historic Activity Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getHistoricActivityInstancesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricActivityInstancesCount(uriInfo);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Activity Instances Count", notes = "Operation Query Historic Activity Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryHistoricActivityInstancesCount(
        @ApiParam("Parameter historicActivityInstanceQueryDto")
        HistoricActivityInstanceQueryDto historicActivityInstanceQueryDto) {
        return super.queryHistoricActivityInstancesCount(historicActivityInstanceQueryDto);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Activity Instances", notes = "Operation Query Historic Activity Instances")
    @Override
    public List<HistoricActivityInstanceDto> queryHistoricActivityInstances(
        @ApiParam("Parameter historicActivityInstanceQueryDto")
        HistoricActivityInstanceQueryDto historicActivityInstanceQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryHistoricActivityInstances(historicActivityInstanceQueryDto, firstResult, maxResults);
    }
}
