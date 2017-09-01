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
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceQueryDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceSuspensionStateDto;
import org.camunda.bpm.engine.rest.dto.runtime.SetJobRetriesByProcessDto;
import org.camunda.bpm.engine.rest.dto.runtime.batch.DeleteProcessInstancesDto;
import org.camunda.bpm.engine.rest.dto.runtime.modification.ProcessInstanceModificationDto;
import org.camunda.bpm.engine.rest.dto.swagger.ActivityInstanceDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.ProcessInstanceDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.VariableValueDtoSwagger;
import org.camunda.bpm.engine.rest.impl.ProcessInstanceRestServiceImpl;
import org.camunda.bpm.engine.rest.mapper.MultipartFormData;
import org.camunda.bpm.engine.rest.sub.VariableResource;
import org.camunda.bpm.engine.rest.sub.runtime.ProcessInstanceResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/process-instance")
@Api(value = "Process Rest", tags = "Process")
public class ProcessInstanceRestServiceSwagger
    extends ProcessInstanceRestServiceImpl
{

    public ProcessInstanceRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/job-retries")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Set Retries By Process", notes = "Operation Set Retries By Process")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger setRetriesByProcess(
        @ApiParam("Parameter setJobRetriesByProcessDto")
        SetJobRetriesByProcessDto setJobRetriesByProcessDto) {
        return super.setRetriesByProcess(setJobRetriesByProcessDto);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Process Instances", notes = "Operation Query Process Instances")
    @Override
    public List<ProcessInstanceDto> queryProcessInstances(
        @ApiParam("Parameter processInstanceQueryDto")
        ProcessInstanceQueryDto processInstanceQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryProcessInstances(processInstanceQueryDto, firstResult, maxResults);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Process Instances", notes = "Operation Get Process Instances")
    @Override
    public List<ProcessInstanceDto> getProcessInstances(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getProcessInstances(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Process Instances Count", notes = "Operation Query Process Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryProcessInstancesCount(
        @ApiParam("Parameter processInstanceQueryDto")
        ProcessInstanceQueryDto processInstanceQueryDto) {
        return super.queryProcessInstancesCount(processInstanceQueryDto);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Process Instance", notes = "Operation Get Process Instance", response = org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto.class)
    @Override
    public ProcessInstanceResource getProcessInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getProcessInstance(id);
    }

    @Path("/{id}/activity-instances")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Activity Instance Tree", notes = "Operation Get Activity Instance Tree")
    public ActivityInstanceDtoSwagger getProcessInstanceGetActivityInstanceTree(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getProcessInstance(id).getActivityInstanceTree();
    }

    @Path("/{id}/suspended")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Suspension State", notes = "Operation Update Suspension State")
    public void getProcessInstanceUpdateSuspensionState(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter processInstanceSuspensionStateDto")
        ProcessInstanceSuspensionStateDto processInstanceSuspensionStateDto) {
        getProcessInstance(id).updateSuspensionState(processInstanceSuspensionStateDto);
    }

    @Path("/{id}/variables")
    @ApiOperation(value = "Get Variables Resource", notes = "Operation Get Variables Resource", response = java.util.Map.class)
    public VariableResource getProcessInstanceGetVariablesResource(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getProcessInstance(id).getVariablesResource();
    }

    @Path("/{id}/variables/{varId}/data")
    @POST
    @Consumes("multipart/form-data")
    @ApiOperation(value = "Set Binary Variable", notes = "Operation Set Binary Variable")
    public void getProcessInstanceGetVariablesResourceSetBinaryVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @ApiParam("Parameter multipartFormData")
        MultipartFormData multipartFormData) {
        getProcessInstance(id).getVariablesResource().setBinaryVariable(varId, multipartFormData);
    }

    @Path("/{id}/variables/{varId}/data")
    @GET
    @ApiOperation(value = "Get Variable Binary", notes = "Operation Get Variable Binary")
    public Response getProcessInstanceGetVariablesResourceGetVariableBinary(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId) {
        return getProcessInstance(id).getVariablesResource().getVariableBinary(varId);
    }

    @Path("/{id}/variables/{varId}")
    @DELETE
    @ApiOperation(value = "Delete Variable", notes = "Operation Delete Variable")
    public void getProcessInstanceGetVariablesResourceDeleteVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId) {
        getProcessInstance(id).getVariablesResource().deleteVariable(varId);
    }

    @Path("/{id}/variables")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Modify Variables", notes = "Operation Modify Variables")
    public void getProcessInstanceGetVariablesResourceModifyVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter patchVariablesDto")
        PatchVariablesDto patchVariablesDto) {
        getProcessInstance(id).getVariablesResource().modifyVariables(patchVariablesDto);
    }

    @Path("/{id}/variables")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variables", notes = "Operation Get Variables")
    public Map<String, VariableValueDto> getProcessInstanceGetVariablesResourceGetVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return getProcessInstance(id).getVariablesResource().getVariables(deserializeValues);
    }

    @Path("/{id}/variables/{varId}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Variable", notes = "Operation Get Variable")
    public VariableValueDtoSwagger getProcessInstanceGetVariablesResourceGetVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @QueryParam("deserializeValue")
        @ApiParam("Parameter deserializeValue")
        boolean deserializeValue) {
        return getProcessInstance(id).getVariablesResource().getVariable(varId, deserializeValue);
    }

    @Path("/{id}/variables/{varId}")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Put Variable", notes = "Operation Put Variable")
    public void getProcessInstanceGetVariablesResourcePutVariable(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("varId")
        @ApiParam("Parameter varId")
        String varId,
        @ApiParam("Parameter variableValueDto")
        VariableValueDto variableValueDto) {
        getProcessInstance(id).getVariablesResource().putVariable(varId, variableValueDto);
    }

    @Path("/{id}/modification")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Modify Process Instance", notes = "Operation Modify Process Instance")
    public void getProcessInstanceModifyProcessInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter processInstanceModificationDto")
        ProcessInstanceModificationDto processInstanceModificationDto) {
        getProcessInstance(id).modifyProcessInstance(processInstanceModificationDto);
    }

    @Path("/{id}")
    @DELETE
    @ApiOperation(value = "Delete Process Instance", notes = "Operation Delete Process Instance")
    public void getProcessInstanceDeleteProcessInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("skipCustomListeners")
        @ApiParam("Parameter skipCustomListeners")
        boolean skipCustomListeners,
        @QueryParam("skipIoMappings")
        @ApiParam("Parameter skipIoMappings")
        boolean skipIoMappings) {
        getProcessInstance(id).deleteProcessInstance(skipCustomListeners, skipIoMappings);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Process Instance", notes = "Operation Get Process Instance")
    public ProcessInstanceDtoSwagger getProcessInstanceGetProcessInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getProcessInstance(id).getProcessInstance();
    }

    @Path("/suspended")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Suspension State", notes = "Operation Update Suspension State")
    @Override
    public void updateSuspensionState(
        @ApiParam("Parameter processInstanceSuspensionStateDto")
        ProcessInstanceSuspensionStateDto processInstanceSuspensionStateDto) {
        super.updateSuspensionState(processInstanceSuspensionStateDto);
    }

    @Path("/job-retries-historic-query-based")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Set Retries By Process Historic Query Based", notes = "Operation Set Retries By Process Historic Query Based")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger setRetriesByProcessHistoricQueryBased(
        @ApiParam("Parameter setJobRetriesByProcessDto")
        SetJobRetriesByProcessDto setJobRetriesByProcessDto) {
        return super.setRetriesByProcessHistoricQueryBased(setJobRetriesByProcessDto);
    }

    @Path("/delete-historic-query-based")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Delete Async Historic Query Based", notes = "Operation Delete Async Historic Query Based")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger deleteAsyncHistoricQueryBased(
        @ApiParam("Parameter deleteProcessInstancesDto")
        DeleteProcessInstancesDto deleteProcessInstancesDto) {
        return super.deleteAsyncHistoricQueryBased(deleteProcessInstancesDto);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Process Instances Count", notes = "Operation Get Process Instances Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getProcessInstancesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getProcessInstancesCount(uriInfo);
    }

    @Path("/delete")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Delete Async", notes = "Operation Delete Async")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger deleteAsync(
        @ApiParam("Parameter deleteProcessInstancesDto")
        DeleteProcessInstancesDto deleteProcessInstancesDto) {
        return super.deleteAsync(deleteProcessInstancesDto);
    }
}
