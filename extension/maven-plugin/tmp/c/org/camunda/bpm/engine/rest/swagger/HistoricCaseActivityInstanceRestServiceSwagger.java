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
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.history.HistoricCaseActivityInstanceDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.HistoricCaseActivityInstanceDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.HistoricCaseActivityInstanceRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.history.HistoricCaseActivityInstanceResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/case-activity-instance")
@Api(value = "Historic Activity", tags = "Historic")
public class HistoricCaseActivityInstanceRestServiceSwagger
    extends HistoricCaseActivityInstanceRestServiceImpl
{

    public HistoricCaseActivityInstanceRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Case Activity Instances Count", notes = "Operation Get Historic Case Activity Instances Count")
    @Override
    public CountResultDtoSwagger getHistoricCaseActivityInstancesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricCaseActivityInstancesCount(uriInfo);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Historic Case Instance", notes = "Operation Get Historic Case Instance", response = org.camunda.bpm.engine.rest.dto.history.HistoricCaseActivityInstanceDto.class)
    @Override
    public HistoricCaseActivityInstanceResource getHistoricCaseInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getHistoricCaseInstance(id);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Case Activity Instance", notes = "Operation Get Historic Case Activity Instance")
    public HistoricCaseActivityInstanceDtoSwagger getHistoricCaseInstanceGetHistoricCaseActivityInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getHistoricCaseInstance(id).getHistoricCaseActivityInstance();
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Case Activity Instances", notes = "Operation Get Historic Case Activity Instances")
    @Override
    public List<HistoricCaseActivityInstanceDto> getHistoricCaseActivityInstances(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getHistoricCaseActivityInstances(uriInfo, firstResult, maxResults);
    }
}
