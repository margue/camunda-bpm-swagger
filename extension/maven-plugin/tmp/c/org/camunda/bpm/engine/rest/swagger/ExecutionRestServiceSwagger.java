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
import org.camunda.bpm.engine.rest.dto.runtime.ExecutionDto;
import org.camunda.bpm.engine.rest.dto.runtime.ExecutionQueryDto;
import org.camunda.bpm.engine.rest.dto.runtime.ExecutionTriggerDto;
import org.camunda.bpm.engine.rest.dto.swagger.EventSubscriptionDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.ExecutionDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.VariableValueDtoSwagger;
import org.camunda.bpm.engine.rest.impl.ExecutionRestServiceImpl;
import org.camunda.bpm.engine.rest.mapper.MultipartFormData;
import org.camunda.bpm.engine.rest.sub.VariableResource;
import org.camunda.bpm.engine.rest.sub.runtime.EventSubscriptionResource;
import org.camunda.bpm.engine.rest.sub.runtime.ExecutionResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/execution")
@Api(value = "Execution Service", tags = "Execution")
public class ExecutionRestServiceSwagger
    extends ExecutionRestServiceImpl
{

    public ExecutionRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Executions Count", notes = "Operation Query Executions Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryExecutionsCount(
        @ApiParam("Parameter executionQueryDto")
        ExecutionQueryDto executionQueryDto) {
        return super.queryExecutionsCount(executionQueryDto);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Executions", notes = "Operation Get Executions")
    @Override
    public List<ExecutionDto> getExecutions(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getExecutions(uriInfo, firstResult, maxResults);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Execution", notes = "Operation Get Execution", response = org.camunda.bpm.engine.rest.dto.runtime.ExecutionDto.class)
    @Override
    public ExecutionResource getExecution(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getExecution(id);
    }

    @Path("/{id}/signal")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Signal Execution", notes = "Operation Signal Execution")
    public void getExecutionSignalExecution(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter executionTriggerDto")
        ExecutionTriggerDto executionTriggerDto) {
        getExecution(id).signalExecution(executionTriggerDto);
    }

    @Path("/{id}/messageSubscriptions/{messageName}")
    @ApiOperation(value = "Get Message Event Subscription", notes = "Operation Get Message Event Subscription", response = org.camunda.bpm.engine.rest.dto.runtime.EventSubscriptionDto.class)
    public EventSubscriptionResource getExecutionGetMessageEventSubscription(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("messageName")
        @ApiParam("Parameter messageName")
        String messageName) {
        return getExecution(id).getMessageEventSubscription(messageName);
    }

    @Path("/{id}/messageSubscriptions/{messageName}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Event Subscription", notes = "Operation Get Event Subscription")
    public EventSubscriptionDtoSwagger getExecutionGetMessageEventSubscriptionGetEventSubscription(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("messageName")
        @ApiParam("Parameter messageName")
        String messageName) {
        return getExecution(id).getMessageEventSubscription(messageName).getEventSubscription();
    }

    @Path("/{id}/messageSubscriptions/{messageName}/trigger")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Trigger Event", notes = "Operation Trigger Event")
    public void getExecutionGetMessageEventSubscriptionTriggerEvent(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("messageName")
        @ApiParam("Parameter messageName")
        String messageName,
        @ApiParam("Parameter executionTriggerDto")
        ExecutionTriggerDto executionTriggerDto) {
        getExecution(id).getMessageEventSubscription(messageName).triggerEvent(executionTriggerDto);
    }

    @Path("/{id}/localVariables")
    @ApiOperation(value = "Get Local Variables", notes = "Operation Get Local Variables", response = java.util.Map.class)
    public VariableResource getExecutionGetLocalVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getExecution(id).getLocalVariables();
    }

    @Path("/{id}/localVariables/{varId}/data")
    @POST
    @Consumes("multipart/form-data")
    @ApiOperation(value = "Set Binary Variable", notes = "Operation Set Binary Variable")
    public void getExecutionGetLocalVariablesSetBinaryVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @ApiParam("Parameter multipartFormData")
        MultipartFormData multipartFormData) {
        getExecution(id).getLocalVariables().setBinaryVariable(varId, multipartFormData);
    }

    @Path("/{id}/localVariables/{varId}/data")
    @GET
    @ApiOperation(value = "Get Variable Binary", notes = "Operation Get Variable Binary")
    public Response getExecutionGetLocalVariablesGetVariableBinary(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId) {
        return getExecution(id).getLocalVariables().getVariableBinary(varId);
    }

    @Path("/{id}/localVariables/{varId}")
    @DELETE
    @ApiOperation(value = "Delete Variable", notes = "Operation Delete Variable")
    public void getExecutionGetLocalVariablesDeleteVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId) {
        getExecution(id).getLocalVariables().deleteVariable(varId);
    }

    @Path("/{id}/localVariables")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Modify Variables", notes = "Operation Modify Variables")
    public void getExecutionGetLocalVariablesModifyVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter patchVariablesDto")
        PatchVariablesDto patchVariablesDto) {
        getExecution(id).getLocalVariables().modifyVariables(patchVariablesDto);
    }

    @Path("/{id}/localVariables")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variables", notes = "Operation Get Variables")
    public Map<String, VariableValueDto> getExecutionGetLocalVariablesGetVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return getExecution(id).getLocalVariables().getVariables(deserializeValues);
    }

    @Path("/{id}/localVariables/{varId}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variable", notes = "Operation Get Variable")
    public VariableValueDtoSwagger getExecutionGetLocalVariablesGetVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @QueryParam("deserializeValue")
        @ApiParam("Parameter deserializeValue")
        boolean deserializeValue) {
        return getExecution(id).getLocalVariables().getVariable(varId, deserializeValue);
    }

    @Path("/{id}/localVariables/{varId}")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Put Variable", notes = "Operation Put Variable")
    public void getExecutionGetLocalVariablesPutVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @ApiParam("Parameter variableValueDto")
        VariableValueDto variableValueDto) {
        getExecution(id).getLocalVariables().putVariable(varId, variableValueDto);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Execution", notes = "Operation Get Execution")
    public ExecutionDtoSwagger getExecutionGetExecution(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getExecution(id).getExecution();
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Executions", notes = "Operation Query Executions")
    @Override
    public List<ExecutionDto> queryExecutions(
        @ApiParam("Parameter executionQueryDto")
        ExecutionQueryDto executionQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryExecutions(executionQueryDto, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Executions Count", notes = "Operation Get Executions Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getExecutionsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getExecutionsCount(uriInfo);
    }
}
