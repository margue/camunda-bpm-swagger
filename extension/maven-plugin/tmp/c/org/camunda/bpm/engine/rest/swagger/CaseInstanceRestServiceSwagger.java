package org.camunda.bpm.engine.rest.swagger;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.rest.dto.PatchVariablesDto;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.CaseExecutionTriggerDto;
import org.camunda.bpm.engine.rest.dto.runtime.CaseInstanceDto;
import org.camunda.bpm.engine.rest.dto.runtime.CaseInstanceQueryDto;
import org.camunda.bpm.engine.rest.dto.swagger.CaseInstanceDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.VariableValueDtoSwagger;
import org.camunda.bpm.engine.rest.impl.CaseInstanceRestServiceImpl;
import org.camunda.bpm.engine.rest.mapper.MultipartFormData;
import org.camunda.bpm.engine.rest.sub.VariableResource;
import org.camunda.bpm.engine.rest.sub.runtime.CaseInstanceResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/case-instance")
@Api(value = "Case Rest", tags = "Case")
public class CaseInstanceRestServiceSwagger
    extends CaseInstanceRestServiceImpl
{

    public CaseInstanceRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Instances", notes = "Operation Get Case Instances")
    @Override
    public List<CaseInstanceDto> getCaseInstances(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getCaseInstances(uriInfo, firstResult, maxResults);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Case Instance", notes = "Operation Get Case Instance", response = org.camunda.bpm.engine.rest.dto.runtime.CaseInstanceDto.class)
    @Override
    public CaseInstanceResource getCaseInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getCaseInstance(id);
    }

    @Path("/{id}/terminate")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Terminate", notes = "Operation Terminate")
    public void getCaseInstanceTerminate(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter caseExecutionTriggerDto")
        CaseExecutionTriggerDto caseExecutionTriggerDto) {
        getCaseInstance(id).terminate(caseExecutionTriggerDto);
    }

    @Path("/{id}/variables")
    @ApiOperation(value = "Get Variables Resource", notes = "Operation Get Variables Resource", response = java.util.Map.class)
    public VariableResource getCaseInstanceGetVariablesResource(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getCaseInstance(id).getVariablesResource();
    }

    @Path("/{id}/variables/{varId}/data")
    @POST
    @Consumes("multipart/form-data")
    @ApiOperation(value = "Set Binary Variable", notes = "Operation Set Binary Variable")
    public void getCaseInstanceGetVariablesResourceSetBinaryVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @ApiParam("Parameter multipartFormData")
        MultipartFormData multipartFormData) {
        getCaseInstance(id).getVariablesResource().setBinaryVariable(varId, multipartFormData);
    }

    @Path("/{id}/variables/{varId}/data")
    @GET
    @ApiOperation(value = "Get Variable Binary", notes = "Operation Get Variable Binary")
    public Response getCaseInstanceGetVariablesResourceGetVariableBinary(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId) {
        return getCaseInstance(id).getVariablesResource().getVariableBinary(varId);
    }

    @Path("/{id}/variables/{varId}")
    @DELETE
    @ApiOperation(value = "Delete Variable", notes = "Operation Delete Variable")
    public void getCaseInstanceGetVariablesResourceDeleteVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId) {
        getCaseInstance(id).getVariablesResource().deleteVariable(varId);
    }

    @Path("/{id}/variables")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Modify Variables", notes = "Operation Modify Variables")
    public void getCaseInstanceGetVariablesResourceModifyVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter patchVariablesDto")
        PatchVariablesDto patchVariablesDto) {
        getCaseInstance(id).getVariablesResource().modifyVariables(patchVariablesDto);
    }

    @Path("/{id}/variables")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variables", notes = "Operation Get Variables")
    public Map<String, VariableValueDto> getCaseInstanceGetVariablesResourceGetVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return getCaseInstance(id).getVariablesResource().getVariables(deserializeValues);
    }

    @Path("/{id}/variables/{varId}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variable", notes = "Operation Get Variable")
    public VariableValueDtoSwagger getCaseInstanceGetVariablesResourceGetVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @QueryParam("deserializeValue")
        @ApiParam("Parameter deserializeValue")
        boolean deserializeValue) {
        return getCaseInstance(id).getVariablesResource().getVariable(varId, deserializeValue);
    }

    @Path("/{id}/variables/{varId}")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Put Variable", notes = "Operation Put Variable")
    public void getCaseInstanceGetVariablesResourcePutVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @ApiParam("Parameter variableValueDto")
        VariableValueDto variableValueDto) {
        getCaseInstance(id).getVariablesResource().putVariable(varId, variableValueDto);
    }

    @Path("/{id}/close")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Close", notes = "Operation Close")
    public void getCaseInstanceClose(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter caseExecutionTriggerDto")
        CaseExecutionTriggerDto caseExecutionTriggerDto) {
        getCaseInstance(id).close(caseExecutionTriggerDto);
    }

    @Path("/{id}/complete")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Complete", notes = "Operation Complete")
    public void getCaseInstanceComplete(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter caseExecutionTriggerDto")
        CaseExecutionTriggerDto caseExecutionTriggerDto) {
        getCaseInstance(id).complete(caseExecutionTriggerDto);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Instance", notes = "Operation Get Case Instance")
    public CaseInstanceDtoSwagger getCaseInstanceGetCaseInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getCaseInstance(id).getCaseInstance();
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Case Instances Count", notes = "Operation Query Case Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryCaseInstancesCount(
        @ApiParam("Parameter caseInstanceQueryDto")
        CaseInstanceQueryDto caseInstanceQueryDto) {
        return super.queryCaseInstancesCount(caseInstanceQueryDto);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Case Instances", notes = "Operation Query Case Instances")
    @Override
    public List<CaseInstanceDto> queryCaseInstances(
        @ApiParam("Parameter caseInstanceQueryDto")
        CaseInstanceQueryDto caseInstanceQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryCaseInstances(caseInstanceQueryDto, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Instances Count", notes = "Operation Get Case Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getCaseInstancesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getCaseInstancesCount(uriInfo);
    }
}
