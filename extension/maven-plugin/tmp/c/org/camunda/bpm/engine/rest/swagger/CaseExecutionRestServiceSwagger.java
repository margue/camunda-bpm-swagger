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
import org.camunda.bpm.engine.rest.dto.runtime.CaseExecutionDto;
import org.camunda.bpm.engine.rest.dto.runtime.CaseExecutionQueryDto;
import org.camunda.bpm.engine.rest.dto.runtime.CaseExecutionTriggerDto;
import org.camunda.bpm.engine.rest.dto.swagger.CaseExecutionDtoSwagger;
import org.camunda.bpm.engine.rest.impl.CaseExecutionRestServiceImpl;
import org.camunda.bpm.engine.rest.mapper.MultipartFormData;
import org.camunda.bpm.engine.rest.sub.VariableResource;
import org.camunda.bpm.engine.rest.sub.runtime.CaseExecutionResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/case-execution")
@Api(value = "Case Rest", tags = "Case")
public class CaseExecutionRestServiceSwagger
    extends CaseExecutionRestServiceImpl
{

    public CaseExecutionRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Case Executions Count", notes = "Operation Query Case Executions Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryCaseExecutionsCount(
        @ApiParam("Parameter caseExecutionQueryDto")
        CaseExecutionQueryDto caseExecutionQueryDto) {
        return super.queryCaseExecutionsCount(caseExecutionQueryDto);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Case Execution", notes = "Operation Get Case Execution", response = org.camunda.bpm.engine.rest.dto.runtime.CaseExecutionDto.class)
    @Override
    public CaseExecutionResource getCaseExecution(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getCaseExecution(id);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Execution", notes = "Operation Get Case Execution")
    public CaseExecutionDtoSwagger getCaseExecutionGetCaseExecution(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getCaseExecution(id).getCaseExecution();
    }

    @Path("/{id}/complete")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Complete", notes = "Operation Complete")
    public void getCaseExecutionComplete(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter caseExecutionTriggerDto")
        CaseExecutionTriggerDto caseExecutionTriggerDto) {
        getCaseExecution(id).complete(caseExecutionTriggerDto);
    }

    @Path("/{id}/terminate")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Terminate", notes = "Operation Terminate")
    public void getCaseExecutionTerminate(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter caseExecutionTriggerDto")
        CaseExecutionTriggerDto caseExecutionTriggerDto) {
        getCaseExecution(id).terminate(caseExecutionTriggerDto);
    }

    @Path("/{id}/manual-start")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Manual Start", notes = "Operation Manual Start")
    public void getCaseExecutionManualStart(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter caseExecutionTriggerDto")
        CaseExecutionTriggerDto caseExecutionTriggerDto) {
        getCaseExecution(id).manualStart(caseExecutionTriggerDto);
    }

    @Path("/{id}/localVariables")
    @ApiOperation(value = "Get Variables Local", notes = "Operation Get Variables Local", response = java.util.Map.class)
    public VariableResource getCaseExecutionGetVariablesLocal(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getCaseExecution(id).getVariablesLocal();
    }

    @Path("/{id}/localVariables/{varId}/data")
    @POST
    @Consumes("multipart/form-data")
    @ApiOperation(value = "Set Binary Variable", notes = "Operation Set Binary Variable")
    public void getCaseExecutionGetVariablesLocalSetBinaryVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @ApiParam("Parameter multipartFormData")
        MultipartFormData multipartFormData) {
        getCaseExecution(id).getVariablesLocal().setBinaryVariable(varId, multipartFormData);
    }

    @Path("/{id}/localVariables/{varId}/data")
    @GET
    @ApiOperation(value = "Get Variable Binary", notes = "Operation Get Variable Binary")
    public Response getCaseExecutionGetVariablesLocalGetVariableBinary(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId) {
        return getCaseExecution(id).getVariablesLocal().getVariableBinary(varId);
    }

    @Path("/{id}/localVariables/{varId}")
    @DELETE
    @ApiOperation(value = "Delete Variable", notes = "Operation Delete Variable")
    public void getCaseExecutionGetVariablesLocalDeleteVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId) {
        getCaseExecution(id).getVariablesLocal().deleteVariable(varId);
    }

    @Path("/{id}/localVariables")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Modify Variables", notes = "Operation Modify Variables")
    public void getCaseExecutionGetVariablesLocalModifyVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter patchVariablesDto")
        PatchVariablesDto patchVariablesDto) {
        getCaseExecution(id).getVariablesLocal().modifyVariables(patchVariablesDto);
    }

    @Path("/{id}/localVariables")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variables", notes = "Operation Get Variables")
    public Map<String, VariableValueDto> getCaseExecutionGetVariablesLocalGetVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return getCaseExecution(id).getVariablesLocal().getVariables(deserializeValues);
    }

    @Path("/{id}/localVariables/{varId}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variable", notes = "Operation Get Variable")
    public org.camunda.bpm.engine.rest.dto.swagger.VariableValueDtoSwagger getCaseExecutionGetVariablesLocalGetVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @QueryParam("deserializeValue")
        @ApiParam("Parameter deserializeValue")
        boolean deserializeValue) {
        return getCaseExecution(id).getVariablesLocal().getVariable(varId, deserializeValue);
    }

    @Path("/{id}/localVariables/{varId}")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Put Variable", notes = "Operation Put Variable")
    public void getCaseExecutionGetVariablesLocalPutVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @ApiParam("Parameter variableValueDto")
        VariableValueDto variableValueDto) {
        getCaseExecution(id).getVariablesLocal().putVariable(varId, variableValueDto);
    }

    @Path("/{id}/reenable")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Reenable", notes = "Operation Reenable")
    public void getCaseExecutionReenable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter caseExecutionTriggerDto")
        CaseExecutionTriggerDto caseExecutionTriggerDto) {
        getCaseExecution(id).reenable(caseExecutionTriggerDto);
    }

    @Path("/{id}/disable")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Disable", notes = "Operation Disable")
    public void getCaseExecutionDisable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter caseExecutionTriggerDto")
        CaseExecutionTriggerDto caseExecutionTriggerDto) {
        getCaseExecution(id).disable(caseExecutionTriggerDto);
    }

    @Path("/{id}/variables")
    @ApiOperation(value = "Get Variables", notes = "Operation Get Variables", response = java.util.Map.class)
    public VariableResource getCaseExecutionGetVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getCaseExecution(id).getVariables();
    }

    @Path("/{id}/variables/{varId}/data")
    @POST
    @Consumes("multipart/form-data")
    @ApiOperation(value = "Set Binary Variable", notes = "Operation Set Binary Variable")
    public void getCaseExecutionGetVariablesSetBinaryVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @ApiParam("Parameter multipartFormData")
        MultipartFormData multipartFormData) {
        getCaseExecution(id).getVariables().setBinaryVariable(varId, multipartFormData);
    }

    @Path("/{id}/variables/{varId}/data")
    @GET
    @ApiOperation(value = "Get Variable Binary", notes = "Operation Get Variable Binary")
    public Response getCaseExecutionGetVariablesGetVariableBinary(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId) {
        return getCaseExecution(id).getVariables().getVariableBinary(varId);
    }

    @Path("/{id}/variables/{varId}")
    @DELETE
    @ApiOperation(value = "Delete Variable", notes = "Operation Delete Variable")
    public void getCaseExecutionGetVariablesDeleteVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId) {
        getCaseExecution(id).getVariables().deleteVariable(varId);
    }

    @Path("/{id}/variables")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Modify Variables", notes = "Operation Modify Variables")
    public void getCaseExecutionGetVariablesModifyVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter patchVariablesDto")
        PatchVariablesDto patchVariablesDto) {
        getCaseExecution(id).getVariables().modifyVariables(patchVariablesDto);
    }

    @Path("/{id}/variables")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variables", notes = "Operation Get Variables")
    public Map<String, VariableValueDto> getCaseExecutionGetVariablesGetVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return getCaseExecution(id).getVariables().getVariables(deserializeValues);
    }

    @Path("/{id}/variables/{varId}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variable", notes = "Operation Get Variable")
    public org.camunda.bpm.engine.rest.dto.swagger.VariableValueDtoSwagger getCaseExecutionGetVariablesGetVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @QueryParam("deserializeValue")
        @ApiParam("Parameter deserializeValue")
        boolean deserializeValue) {
        return getCaseExecution(id).getVariables().getVariable(varId, deserializeValue);
    }

    @Path("/{id}/variables/{varId}")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Put Variable", notes = "Operation Put Variable")
    public void getCaseExecutionGetVariablesPutVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @ApiParam("Parameter variableValueDto")
        VariableValueDto variableValueDto) {
        getCaseExecution(id).getVariables().putVariable(varId, variableValueDto);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Executions Count", notes = "Operation Get Case Executions Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getCaseExecutionsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getCaseExecutionsCount(uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Case Executions", notes = "Operation Get Case Executions")
    @Override
    public List<CaseExecutionDto> getCaseExecutions(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getCaseExecutions(uriInfo, firstResult, maxResults);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Case Executions", notes = "Operation Query Case Executions")
    @Override
    public List<CaseExecutionDto> queryCaseExecutions(
        @ApiParam("Parameter caseExecutionQueryDto")
        CaseExecutionQueryDto caseExecutionQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryCaseExecutions(caseExecutionQueryDto, firstResult, maxResults);
    }
}
