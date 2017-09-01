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
import org.camunda.bpm.engine.rest.dto.HistoryTimeToLiveDto;
import org.camunda.bpm.engine.rest.dto.StatisticsResultDto;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDto;
import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionSuspensionStateDto;
import org.camunda.bpm.engine.rest.dto.runtime.RestartProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.impl.ProcessDefinitionRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.repository.ProcessDefinitionResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/process-definition")
@Api(value = "Process Rest", tags = "Process")
public class ProcessDefinitionRestServiceSwagger
    extends ProcessDefinitionRestServiceImpl
{

    public ProcessDefinitionRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Process Definitions", notes = "Operation Get Process Definitions")
    @Override
    public List<ProcessDefinitionDto> getProcessDefinitions(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getProcessDefinitions(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Process Definitions Count", notes = "Operation Get Process Definitions Count")
    @Override
    public CountResultDtoSwagger getProcessDefinitionsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getProcessDefinitionsCount(uriInfo);
    }

    @Path("/key/{key}/tenant-id/{tenantId}")
    @ApiOperation(value = "Get Process Definition By Key And Tenant Id", notes = "Operation Get Process Definition By Key And Tenant Id", response = org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDto.class)
    @Override
    public ProcessDefinitionResource getProcessDefinitionByKeyAndTenantId(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return super.getProcessDefinitionByKeyAndTenantId(key, tenantId);
    }

    @Path("/key/{key}/tenant-id/{tenantId}/rendered-form")
    @GET
    @Produces("application/xhtml+xml")
    @ApiOperation(value = "Get Rendered Form", notes = "Operation Get Rendered Form")
    public Response getProcessDefinitionByKeyAndTenantIdGetRenderedForm(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getProcessDefinitionByKeyAndTenantId(key, tenantId).getRenderedForm();
    }

    @Path("/key/{key}/tenant-id/{tenantId}/diagram")
    @GET
    @ApiOperation(value = "Get Process Definition Diagram", notes = "Operation Get Process Definition Diagram")
    public Response getProcessDefinitionByKeyAndTenantIdGetProcessDefinitionDiagram(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getProcessDefinitionByKeyAndTenantId(key, tenantId).getProcessDefinitionDiagram();
    }

    @Path("/key/{key}/tenant-id/{tenantId}/restart")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Restart Process Instance", notes = "Operation Restart Process Instance")
    public void getProcessDefinitionByKeyAndTenantIdRestartProcessInstance(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @ApiParam("Parameter restartProcessInstanceDto")
        RestartProcessInstanceDto restartProcessInstanceDto) {
        getProcessDefinitionByKeyAndTenantId(key, tenantId).restartProcessInstance(restartProcessInstanceDto);
    }

    @Path("/key/{key}/tenant-id/{tenantId}/statistics")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Activity Statistics", notes = "Operation Get Activity Statistics")
    public List<StatisticsResultDto> getProcessDefinitionByKeyAndTenantIdGetActivityStatistics(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @QueryParam("failedJobs")
        @ApiParam("Parameter failedJobs")
        Boolean failedJobs,
        @QueryParam("incidents")
        @ApiParam("Parameter incidents")
        Boolean incidents,
        @QueryParam("incidentsForType")
        @ApiParam("Parameter incidentsForType")
        String incidentsForType) {
        return getProcessDefinitionByKeyAndTenantId(key, tenantId).getActivityStatistics(failedJobs, incidents, incidentsForType);
    }

    @Path("/key/{key}/tenant-id/{tenantId}/form-variables")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Form Variables", notes = "Operation Get Form Variables")
    public Map<String, VariableValueDto> getProcessDefinitionByKeyAndTenantIdGetFormVariables(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @QueryParam("variableNames")
        @ApiParam("Parameter variableNames")
        String variableNames,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return getProcessDefinitionByKeyAndTenantId(key, tenantId).getFormVariables(variableNames, deserializeValues);
    }

    @Path("/key/{key}/tenant-id/{tenantId}/startForm")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Start Form", notes = "Operation Get Start Form")
    public org.camunda.bpm.engine.rest.dto.swagger.FormDtoSwagger getProcessDefinitionByKeyAndTenantIdGetStartForm(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getProcessDefinitionByKeyAndTenantId(key, tenantId).getStartForm();
    }

    @Path("/key/{key}/tenant-id/{tenantId}/start")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Start Process Instance", notes = "Operation Start Process Instance")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessInstanceDtoSwagger getProcessDefinitionByKeyAndTenantIdStartProcessInstance(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter startProcessInstanceDto")
        StartProcessInstanceDto startProcessInstanceDto) {
        return getProcessDefinitionByKeyAndTenantId(key, tenantId).startProcessInstance(uriInfo, startProcessInstanceDto);
    }

    @Path("/key/{key}/tenant-id/{tenantId}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Process Definition", notes = "Operation Get Process Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessDefinitionDtoSwagger getProcessDefinitionByKeyAndTenantIdGetProcessDefinition(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getProcessDefinitionByKeyAndTenantId(key, tenantId).getProcessDefinition();
    }

    @Path("/key/{key}/tenant-id/{tenantId}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Process Definition Bpmn 20 Xml", notes = "Operation Get Process Definition Bpmn 20 Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessDefinitionDiagramDtoSwagger getProcessDefinitionByKeyAndTenantIdGetProcessDefinitionBpmn20Xml(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId) {
        return getProcessDefinitionByKeyAndTenantId(key, tenantId).getProcessDefinitionBpmn20Xml();
    }

    @Path("/key/{key}/tenant-id/{tenantId}/submit-form")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Submit Form", notes = "Operation Submit Form")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessInstanceDtoSwagger getProcessDefinitionByKeyAndTenantIdSubmitForm(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter startProcessInstanceDto")
        StartProcessInstanceDto startProcessInstanceDto) {
        return getProcessDefinitionByKeyAndTenantId(key, tenantId).submitForm(uriInfo, startProcessInstanceDto);
    }

    @Path("/key/{key}/tenant-id/{tenantId}/suspended")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Suspension State", notes = "Operation Update Suspension State")
    public void getProcessDefinitionByKeyAndTenantIdUpdateSuspensionState(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @ApiParam("Parameter processDefinitionSuspensionStateDto")
        ProcessDefinitionSuspensionStateDto processDefinitionSuspensionStateDto) {
        getProcessDefinitionByKeyAndTenantId(key, tenantId).updateSuspensionState(processDefinitionSuspensionStateDto);
    }

    @Path("/key/{key}/tenant-id/{tenantId}/restart-async")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Restart Process Instance Async", notes = "Operation Restart Process Instance Async")
    public org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger getProcessDefinitionByKeyAndTenantIdRestartProcessInstanceAsync(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @ApiParam("Parameter restartProcessInstanceDto")
        RestartProcessInstanceDto restartProcessInstanceDto) {
        return getProcessDefinitionByKeyAndTenantId(key, tenantId).restartProcessInstanceAsync(restartProcessInstanceDto);
    }

    @Path("/key/{key}/tenant-id/{tenantId}/history-time-to-live")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update History Time To Live", notes = "Operation Update History Time To Live")
    public void getProcessDefinitionByKeyAndTenantIdUpdateHistoryTimeToLive(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @ApiParam("Parameter historyTimeToLiveDto")
        HistoryTimeToLiveDto historyTimeToLiveDto) {
        getProcessDefinitionByKeyAndTenantId(key, tenantId).updateHistoryTimeToLive(historyTimeToLiveDto);
    }

    @Path("/key/{key}/tenant-id/{tenantId}")
    @DELETE
    @ApiOperation(value = "Delete Process Definition", notes = "Operation Delete Process Definition")
    public Response getProcessDefinitionByKeyAndTenantIdDeleteProcessDefinition(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @PathParam("tenantId")
        @ApiParam("Parameter tenantId")
        String tenantId,
        @QueryParam("cascade")
        @ApiParam("Parameter cascade")
        boolean cascade,
        @QueryParam("skipCustomListeners")
        @ApiParam("Parameter skipCustomListeners")
        boolean skipCustomListeners) {
        return getProcessDefinitionByKeyAndTenantId(key, tenantId).deleteProcessDefinition(cascade, skipCustomListeners);
    }

    @Path("/suspended")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Suspension State", notes = "Operation Update Suspension State")
    @Override
    public void updateSuspensionState(
        @ApiParam("Parameter processDefinitionSuspensionStateDto")
        ProcessDefinitionSuspensionStateDto processDefinitionSuspensionStateDto) {
        super.updateSuspensionState(processDefinitionSuspensionStateDto);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Process Definition By Id", notes = "Operation Get Process Definition By Id", response = org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDto.class)
    @Override
    public ProcessDefinitionResource getProcessDefinitionById(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getProcessDefinitionById(id);
    }

    @Path("/{id}/rendered-form")
    @GET
    @Produces("application/xhtml+xml")
    @ApiOperation(value = "Get Rendered Form", notes = "Operation Get Rendered Form")
    public Response getProcessDefinitionByIdGetRenderedForm(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getProcessDefinitionById(id).getRenderedForm();
    }

    @Path("/{id}/diagram")
    @GET
    @ApiOperation(value = "Get Process Definition Diagram", notes = "Operation Get Process Definition Diagram")
    public Response getProcessDefinitionByIdGetProcessDefinitionDiagram(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getProcessDefinitionById(id).getProcessDefinitionDiagram();
    }

    @Path("/{id}/restart")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Restart Process Instance", notes = "Operation Restart Process Instance")
    public void getProcessDefinitionByIdRestartProcessInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter restartProcessInstanceDto")
        RestartProcessInstanceDto restartProcessInstanceDto) {
        getProcessDefinitionById(id).restartProcessInstance(restartProcessInstanceDto);
    }

    @Path("/{id}/statistics")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Activity Statistics", notes = "Operation Get Activity Statistics")
    public List<StatisticsResultDto> getProcessDefinitionByIdGetActivityStatistics(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("failedJobs")
        @ApiParam("Parameter failedJobs")
        Boolean failedJobs,
        @QueryParam("incidents")
        @ApiParam("Parameter incidents")
        Boolean incidents,
        @QueryParam("incidentsForType")
        @ApiParam("Parameter incidentsForType")
        String incidentsForType) {
        return getProcessDefinitionById(id).getActivityStatistics(failedJobs, incidents, incidentsForType);
    }

    @Path("/{id}/form-variables")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Form Variables", notes = "Operation Get Form Variables")
    public Map<String, VariableValueDto> getProcessDefinitionByIdGetFormVariables(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("variableNames")
        @ApiParam("Parameter variableNames")
        String variableNames,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return getProcessDefinitionById(id).getFormVariables(variableNames, deserializeValues);
    }

    @Path("/{id}/startForm")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Start Form", notes = "Operation Get Start Form")
    public org.camunda.bpm.engine.rest.dto.swagger.FormDtoSwagger getProcessDefinitionByIdGetStartForm(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getProcessDefinitionById(id).getStartForm();
    }

    @Path("/{id}/start")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Start Process Instance", notes = "Operation Start Process Instance")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessInstanceDtoSwagger getProcessDefinitionByIdStartProcessInstance(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter startProcessInstanceDto")
        StartProcessInstanceDto startProcessInstanceDto) {
        return getProcessDefinitionById(id).startProcessInstance(uriInfo, startProcessInstanceDto);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Process Definition", notes = "Operation Get Process Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessDefinitionDtoSwagger getProcessDefinitionByIdGetProcessDefinition(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getProcessDefinitionById(id).getProcessDefinition();
    }

    @Path("/{id}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Process Definition Bpmn 20 Xml", notes = "Operation Get Process Definition Bpmn 20 Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessDefinitionDiagramDtoSwagger getProcessDefinitionByIdGetProcessDefinitionBpmn20Xml(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getProcessDefinitionById(id).getProcessDefinitionBpmn20Xml();
    }

    @Path("/{id}/submit-form")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Submit Form", notes = "Operation Submit Form")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessInstanceDtoSwagger getProcessDefinitionByIdSubmitForm(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter startProcessInstanceDto")
        StartProcessInstanceDto startProcessInstanceDto) {
        return getProcessDefinitionById(id).submitForm(uriInfo, startProcessInstanceDto);
    }

    @Path("/{id}/suspended")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Suspension State", notes = "Operation Update Suspension State")
    public void getProcessDefinitionByIdUpdateSuspensionState(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter processDefinitionSuspensionStateDto")
        ProcessDefinitionSuspensionStateDto processDefinitionSuspensionStateDto) {
        getProcessDefinitionById(id).updateSuspensionState(processDefinitionSuspensionStateDto);
    }

    @Path("/{id}/restart-async")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Restart Process Instance Async", notes = "Operation Restart Process Instance Async")
    public org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger getProcessDefinitionByIdRestartProcessInstanceAsync(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter restartProcessInstanceDto")
        RestartProcessInstanceDto restartProcessInstanceDto) {
        return getProcessDefinitionById(id).restartProcessInstanceAsync(restartProcessInstanceDto);
    }

    @Path("/{id}/history-time-to-live")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update History Time To Live", notes = "Operation Update History Time To Live")
    public void getProcessDefinitionByIdUpdateHistoryTimeToLive(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter historyTimeToLiveDto")
        HistoryTimeToLiveDto historyTimeToLiveDto) {
        getProcessDefinitionById(id).updateHistoryTimeToLive(historyTimeToLiveDto);
    }

    @Path("/{id}")
    @DELETE
    @ApiOperation(value = "Delete Process Definition", notes = "Operation Delete Process Definition")
    public Response getProcessDefinitionByIdDeleteProcessDefinition(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("cascade")
        @ApiParam("Parameter cascade")
        boolean cascade,
        @QueryParam("skipCustomListeners")
        @ApiParam("Parameter skipCustomListeners")
        boolean skipCustomListeners) {
        return getProcessDefinitionById(id).deleteProcessDefinition(cascade, skipCustomListeners);
    }

    @Path("/key/{key}")
    @ApiOperation(value = "Get Process Definition By Key", notes = "Operation Get Process Definition By Key", response = org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDto.class)
    @Override
    public ProcessDefinitionResource getProcessDefinitionByKey(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return super.getProcessDefinitionByKey(key);
    }

    @Path("/key/{key}/rendered-form")
    @GET
    @Produces("application/xhtml+xml")
    @ApiOperation(value = "Get Rendered Form", notes = "Operation Get Rendered Form")
    public Response getProcessDefinitionByKeyGetRenderedForm(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getProcessDefinitionByKey(key).getRenderedForm();
    }

    @Path("/key/{key}/diagram")
    @GET
    @ApiOperation(value = "Get Process Definition Diagram", notes = "Operation Get Process Definition Diagram")
    public Response getProcessDefinitionByKeyGetProcessDefinitionDiagram(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getProcessDefinitionByKey(key).getProcessDefinitionDiagram();
    }

    @Path("/key/{key}/restart")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Restart Process Instance", notes = "Operation Restart Process Instance")
    public void getProcessDefinitionByKeyRestartProcessInstance(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @ApiParam("Parameter restartProcessInstanceDto")
        RestartProcessInstanceDto restartProcessInstanceDto) {
        getProcessDefinitionByKey(key).restartProcessInstance(restartProcessInstanceDto);
    }

    @Path("/key/{key}/statistics")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Activity Statistics", notes = "Operation Get Activity Statistics")
    public List<StatisticsResultDto> getProcessDefinitionByKeyGetActivityStatistics(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @QueryParam("failedJobs")
        @ApiParam("Parameter failedJobs")
        Boolean failedJobs,
        @QueryParam("incidents")
        @ApiParam("Parameter incidents")
        Boolean incidents,
        @QueryParam("incidentsForType")
        @ApiParam("Parameter incidentsForType")
        String incidentsForType) {
        return getProcessDefinitionByKey(key).getActivityStatistics(failedJobs, incidents, incidentsForType);
    }

    @Path("/key/{key}/form-variables")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Form Variables", notes = "Operation Get Form Variables")
    public Map<String, VariableValueDto> getProcessDefinitionByKeyGetFormVariables(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @QueryParam("variableNames")
        @ApiParam("Parameter variableNames")
        String variableNames,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return getProcessDefinitionByKey(key).getFormVariables(variableNames, deserializeValues);
    }

    @Path("/key/{key}/startForm")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Start Form", notes = "Operation Get Start Form")
    public org.camunda.bpm.engine.rest.dto.swagger.FormDtoSwagger getProcessDefinitionByKeyGetStartForm(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getProcessDefinitionByKey(key).getStartForm();
    }

    @Path("/key/{key}/start")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Start Process Instance", notes = "Operation Start Process Instance")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessInstanceDtoSwagger getProcessDefinitionByKeyStartProcessInstance(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter startProcessInstanceDto")
        StartProcessInstanceDto startProcessInstanceDto) {
        return getProcessDefinitionByKey(key).startProcessInstance(uriInfo, startProcessInstanceDto);
    }

    @Path("/key/{key}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Process Definition", notes = "Operation Get Process Definition")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessDefinitionDtoSwagger getProcessDefinitionByKeyGetProcessDefinition(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getProcessDefinitionByKey(key).getProcessDefinition();
    }

    @Path("/key/{key}/xml")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Process Definition Bpmn 20 Xml", notes = "Operation Get Process Definition Bpmn 20 Xml")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessDefinitionDiagramDtoSwagger getProcessDefinitionByKeyGetProcessDefinitionBpmn20Xml(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key) {
        return getProcessDefinitionByKey(key).getProcessDefinitionBpmn20Xml();
    }

    @Path("/key/{key}/submit-form")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Submit Form", notes = "Operation Submit Form")
    public org.camunda.bpm.engine.rest.dto.swagger.ProcessInstanceDtoSwagger getProcessDefinitionByKeySubmitForm(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter startProcessInstanceDto")
        StartProcessInstanceDto startProcessInstanceDto) {
        return getProcessDefinitionByKey(key).submitForm(uriInfo, startProcessInstanceDto);
    }

    @Path("/key/{key}/suspended")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Suspension State", notes = "Operation Update Suspension State")
    public void getProcessDefinitionByKeyUpdateSuspensionState(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @ApiParam("Parameter processDefinitionSuspensionStateDto")
        ProcessDefinitionSuspensionStateDto processDefinitionSuspensionStateDto) {
        getProcessDefinitionByKey(key).updateSuspensionState(processDefinitionSuspensionStateDto);
    }

    @Path("/key/{key}/restart-async")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Restart Process Instance Async", notes = "Operation Restart Process Instance Async")
    public org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger getProcessDefinitionByKeyRestartProcessInstanceAsync(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @ApiParam("Parameter restartProcessInstanceDto")
        RestartProcessInstanceDto restartProcessInstanceDto) {
        return getProcessDefinitionByKey(key).restartProcessInstanceAsync(restartProcessInstanceDto);
    }

    @Path("/key/{key}/history-time-to-live")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update History Time To Live", notes = "Operation Update History Time To Live")
    public void getProcessDefinitionByKeyUpdateHistoryTimeToLive(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @ApiParam("Parameter historyTimeToLiveDto")
        HistoryTimeToLiveDto historyTimeToLiveDto) {
        getProcessDefinitionByKey(key).updateHistoryTimeToLive(historyTimeToLiveDto);
    }

    @Path("/key/{key}")
    @DELETE
    @ApiOperation(value = "Delete Process Definition", notes = "Operation Delete Process Definition")
    public Response getProcessDefinitionByKeyDeleteProcessDefinition(
        @PathParam("key")
        @ApiParam("Parameter key")
        String key,
        @QueryParam("cascade")
        @ApiParam("Parameter cascade")
        boolean cascade,
        @QueryParam("skipCustomListeners")
        @ApiParam("Parameter skipCustomListeners")
        boolean skipCustomListeners) {
        return getProcessDefinitionByKey(key).deleteProcessDefinition(cascade, skipCustomListeners);
    }

    @Path("/statistics")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Statistics", notes = "Operation Get Statistics")
    @Override
    public List<StatisticsResultDto> getStatistics(
        @QueryParam("failedJobs")
        @ApiParam("Parameter failedJobs")
        Boolean failedJobs,
        @QueryParam("incidents")
        @ApiParam("Parameter incidents")
        Boolean incidents,
        @QueryParam("incidentsForType")
        @ApiParam("Parameter incidentsForType")
        String incidentsForType) {
        return super.getStatistics(failedJobs, incidents, incidentsForType);
    }
}
