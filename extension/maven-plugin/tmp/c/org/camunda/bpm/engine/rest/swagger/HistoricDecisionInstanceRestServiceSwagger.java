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
import org.camunda.bpm.engine.rest.dto.history.HistoricDecisionInstanceDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.HistoricDecisionInstanceDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.HistoricDecisionInstanceRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.history.HistoricDecisionInstanceResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/decision-instance")
@Api(value = "Historic Instance", tags = "Historic")
public class HistoricDecisionInstanceRestServiceSwagger
    extends HistoricDecisionInstanceRestServiceImpl
{

    public HistoricDecisionInstanceRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Historic Decision Instance", notes = "Operation Get Historic Decision Instance", response = org.camunda.bpm.engine.rest.dto.history.HistoricDecisionInstanceDto.class)
    @Override
    public HistoricDecisionInstanceResource getHistoricDecisionInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getHistoricDecisionInstance(id);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Decision Instance", notes = "Operation Get Historic Decision Instance")
    public HistoricDecisionInstanceDtoSwagger getHistoricDecisionInstanceGetHistoricDecisionInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("includeInputs")
        @ApiParam("Parameter includeInputs")
        Boolean includeInputs,
        @QueryParam("includeOutputs")
        @ApiParam("Parameter includeOutputs")
        Boolean includeOutputs,
        @QueryParam("disableBinaryFetching")
        @ApiParam("Parameter disableBinaryFetching")
        Boolean disableBinaryFetching,
        @QueryParam("disableCustomObjectDeserialization")
        @ApiParam("Parameter disableCustomObjectDeserialization")
        Boolean disableCustomObjectDeserialization) {
        return getHistoricDecisionInstance(id).getHistoricDecisionInstance(includeInputs, includeOutputs, disableBinaryFetching, disableCustomObjectDeserialization);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Decision Instances Count", notes = "Operation Get Historic Decision Instances Count")
    @Override
    public CountResultDtoSwagger getHistoricDecisionInstancesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricDecisionInstancesCount(uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Decision Instances", notes = "Operation Get Historic Decision Instances")
    @Override
    public List<HistoricDecisionInstanceDto> getHistoricDecisionInstances(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getHistoricDecisionInstances(uriInfo, firstResult, maxResults);
    }
}
