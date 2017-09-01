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
import org.camunda.bpm.engine.rest.dto.runtime.VariableInstanceDto;
import org.camunda.bpm.engine.rest.dto.runtime.VariableInstanceQueryDto;
import org.camunda.bpm.engine.rest.dto.swagger.VariableInstanceDtoSwagger;
import org.camunda.bpm.engine.rest.impl.VariableInstanceRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.runtime.VariableInstanceResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/variable-instance")
@Api(value = "Variable Rest", tags = "Variable")
public class VariableInstanceRestServiceSwagger
    extends VariableInstanceRestServiceImpl
{

    public VariableInstanceRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Variable Instances Count", notes = "Operation Query Variable Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryVariableInstancesCount(
        @ApiParam("Parameter variableInstanceQueryDto")
        VariableInstanceQueryDto variableInstanceQueryDto) {
        return super.queryVariableInstancesCount(variableInstanceQueryDto);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variable Instances Count", notes = "Operation Get Variable Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getVariableInstancesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getVariableInstancesCount(uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variable Instances", notes = "Operation Get Variable Instances")
    @Override
    public List<VariableInstanceDto> getVariableInstances(
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
        return super.getVariableInstances(uriInfo, firstResult, maxResults, deserializeValues);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Variable Instances", notes = "Operation Query Variable Instances")
    @Override
    public List<VariableInstanceDto> queryVariableInstances(
        @ApiParam("Parameter variableInstanceQueryDto")
        VariableInstanceQueryDto variableInstanceQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return super.queryVariableInstances(variableInstanceQueryDto, firstResult, maxResults, deserializeValues);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Variable Instance", notes = "Operation Get Variable Instance", response = org.camunda.bpm.engine.rest.dto.runtime.VariableInstanceDto.class)
    @Override
    public VariableInstanceResource getVariableInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getVariableInstance(id);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Resource", notes = "Operation Get Resource")
    public VariableInstanceDtoSwagger getVariableInstanceGetResource(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("deserializeValue")
        @ApiParam("Parameter deserializeValue")
        boolean deserializeValue) {
        return getVariableInstance(id).getResource(deserializeValue);
    }

    @Path("/{id}/data")
    @GET
    @ApiOperation(value = "Get Resource Binary", notes = "Operation Get Resource Binary")
    public Response getVariableInstanceGetResourceBinary(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getVariableInstance(id).getResourceBinary();
    }
}
