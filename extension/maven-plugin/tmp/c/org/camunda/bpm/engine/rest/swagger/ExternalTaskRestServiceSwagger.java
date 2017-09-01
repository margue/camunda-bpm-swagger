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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.rest.dto.externaltask.CompleteExternalTaskDto;
import org.camunda.bpm.engine.rest.dto.externaltask.ExternalTaskBpmnError;
import org.camunda.bpm.engine.rest.dto.externaltask.ExternalTaskDto;
import org.camunda.bpm.engine.rest.dto.externaltask.ExternalTaskFailureDto;
import org.camunda.bpm.engine.rest.dto.externaltask.ExternalTaskQueryDto;
import org.camunda.bpm.engine.rest.dto.externaltask.FetchExternalTasksDto;
import org.camunda.bpm.engine.rest.dto.externaltask.LockedExternalTaskDto;
import org.camunda.bpm.engine.rest.dto.externaltask.SetRetriesForExternalTasksDto;
import org.camunda.bpm.engine.rest.dto.runtime.PriorityDto;
import org.camunda.bpm.engine.rest.dto.runtime.RetriesDto;
import org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.ExternalTaskDtoSwagger;
import org.camunda.bpm.engine.rest.impl.ExternalTaskRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.externaltask.ExternalTaskResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/external-task")
@Api(value = "External Rest", tags = "External")
public class ExternalTaskRestServiceSwagger
    extends ExternalTaskRestServiceImpl
{

    public ExternalTaskRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/retries-async")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Set Retries Async", notes = "Operation Set Retries Async")
    @Override
    public BatchDtoSwagger setRetriesAsync(
        @ApiParam("Parameter setRetriesForExternalTasksDto")
        SetRetriesForExternalTasksDto setRetriesForExternalTasksDto) {
        return super.setRetriesAsync(setRetriesForExternalTasksDto);
    }

    @Path("/fetchAndLock")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Fetch And Lock", notes = "Operation Fetch And Lock")
    @Override
    public List<LockedExternalTaskDto> fetchAndLock(
        @ApiParam("Parameter fetchExternalTasksDto")
        FetchExternalTasksDto fetchExternalTasksDto) {
        return super.fetchAndLock(fetchExternalTasksDto);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query External Tasks Count", notes = "Operation Query External Tasks Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryExternalTasksCount(
        @ApiParam("Parameter externalTaskQueryDto")
        ExternalTaskQueryDto externalTaskQueryDto) {
        return super.queryExternalTasksCount(externalTaskQueryDto);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get External Task", notes = "Operation Get External Task", response = org.camunda.bpm.engine.rest.dto.externaltask.ExternalTaskDto.class)
    @Override
    public ExternalTaskResource getExternalTask(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getExternalTask(id);
    }

    @Path("/{id}/complete")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Complete", notes = "Operation Complete")
    public void getExternalTaskComplete(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter completeExternalTaskDto")
        CompleteExternalTaskDto completeExternalTaskDto) {
        getExternalTask(id).complete(completeExternalTaskDto);
    }

    @Path("/{id}/priority")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Set Priority", notes = "Operation Set Priority")
    public void getExternalTaskSetPriority(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter priorityDto")
        PriorityDto priorityDto) {
        getExternalTask(id).setPriority(priorityDto);
    }

    @Path("/{id}/errorDetails")
    @GET
    @Produces("text/plain")
    @ApiOperation(value = "Get Error Details", notes = "Operation Get Error Details")
    public String getExternalTaskGetErrorDetails(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getExternalTask(id).getErrorDetails();
    }

    @Path("/{id}/bpmnError")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Handle Bpmn Error", notes = "Operation Handle Bpmn Error")
    public void getExternalTaskHandleBpmnError(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter externalTaskBpmnError")
        ExternalTaskBpmnError externalTaskBpmnError) {
        getExternalTask(id).handleBpmnError(externalTaskBpmnError);
    }

    @Path("/{id}/failure")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Handle Failure", notes = "Operation Handle Failure")
    public void getExternalTaskHandleFailure(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter externalTaskFailureDto")
        ExternalTaskFailureDto externalTaskFailureDto) {
        getExternalTask(id).handleFailure(externalTaskFailureDto);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get External Task", notes = "Operation Get External Task")
    public ExternalTaskDtoSwagger getExternalTaskGetExternalTask(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getExternalTask(id).getExternalTask();
    }

    @Path("/{id}/retries")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Set Retries", notes = "Operation Set Retries")
    public void getExternalTaskSetRetries(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter retriesDto")
        RetriesDto retriesDto) {
        getExternalTask(id).setRetries(retriesDto);
    }

    @Path("/{id}/unlock")
    @POST
    @ApiOperation(value = "Unlock", notes = "Operation Unlock")
    public void getExternalTaskUnlock(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        getExternalTask(id).unlock();
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query External Tasks", notes = "Operation Query External Tasks")
    @Override
    public List<ExternalTaskDto> queryExternalTasks(
        @ApiParam("Parameter externalTaskQueryDto")
        ExternalTaskQueryDto externalTaskQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryExternalTasks(externalTaskQueryDto, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get External Tasks Count", notes = "Operation Get External Tasks Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getExternalTasksCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getExternalTasksCount(uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get External Tasks", notes = "Operation Get External Tasks")
    @Override
    public List<ExternalTaskDto> getExternalTasks(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getExternalTasks(uriInfo, firstResult, maxResults);
    }
}
