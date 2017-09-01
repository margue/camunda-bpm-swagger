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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.history.HistoricVariableInstanceDto;
import org.camunda.bpm.engine.rest.dto.history.HistoricVariableInstanceQueryDto;
import org.camunda.bpm.engine.rest.dto.swagger.HistoricVariableInstanceDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.HistoricVariableInstanceRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.history.HistoricVariableInstanceResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/variable-instance")
@Api(value = "Historic Instance", tags = "Historic")
public class HistoricVariableInstanceRestServiceSwagger
    extends HistoricVariableInstanceRestServiceImpl
{

    public HistoricVariableInstanceRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("/{id}")
    @ApiOperation(value = "Variable Instance Resource", notes = "Operation Variable Instance Resource", response = org.camunda.bpm.engine.rest.dto.history.HistoricVariableInstanceDto.class)
    @Override
    public HistoricVariableInstanceResource variableInstanceResource(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.variableInstanceResource(id);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Resource", notes = "Operation Get Resource")
    public HistoricVariableInstanceDtoSwagger variableInstanceResourceGetResource(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("deserializeValue")
        @ApiParam("Parameter deserializeValue")
        boolean deserializeValue) {
        return variableInstanceResource(id).getResource(deserializeValue);
    }

    @Path("/{id}/data")
    @GET
    @ApiOperation(value = "Get Resource Binary", notes = "Operation Get Resource Binary")
    public Response variableInstanceResourceGetResourceBinary(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return variableInstanceResource(id).getResourceBinary();
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Variable Instances Count", notes = "Operation Get Historic Variable Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getHistoricVariableInstancesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricVariableInstancesCount(uriInfo);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Variable Instances", notes = "Operation Query Historic Variable Instances")
    @Override
    public List<HistoricVariableInstanceDto> queryHistoricVariableInstances(
        @ApiParam("Parameter historicVariableInstanceQueryDto")
        HistoricVariableInstanceQueryDto historicVariableInstanceQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return super.queryHistoricVariableInstances(historicVariableInstanceQueryDto, firstResult, maxResults, deserializeValues);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Historic Variable Instances Count", notes = "Operation Query Historic Variable Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryHistoricVariableInstancesCount(
        @ApiParam("Parameter historicVariableInstanceQueryDto")
        HistoricVariableInstanceQueryDto historicVariableInstanceQueryDto) {
        return super.queryHistoricVariableInstancesCount(historicVariableInstanceQueryDto);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Variable Instances", notes = "Operation Get Historic Variable Instances")
    @Override
    public List<HistoricVariableInstanceDto> getHistoricVariableInstances(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return super.getHistoricVariableInstances(uriInfo, firstResult, maxResults, deserializeValues);
    }
}
