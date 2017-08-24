package org.camunda.bpm.engine.rest.impl;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.TaskRestService;
import org.camunda.bpm.engine.rest.dto.CountResultDto;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.task.CompleteTaskDto;
import org.camunda.bpm.engine.rest.dto.task.FormDto;
import org.camunda.bpm.engine.rest.dto.task.IdentityLinkDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.rest.dto.task.TaskQueryDto;
import org.camunda.bpm.engine.rest.dto.task.UserIdDto;
import org.camunda.bpm.engine.rest.hal.Hal;
import org.camunda.bpm.engine.rest.sub.VariableResource;
import org.camunda.bpm.engine.rest.sub.task.TaskAttachmentResource;
import org.camunda.bpm.engine.rest.sub.task.TaskCommentResource;
import org.camunda.bpm.engine.rest.sub.task.TaskReportResource;
import org.camunda.bpm.engine.rest.sub.task.TaskResource;
import org.camunda.bpm.engine.rest.sub.task.impl.TaskResourceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/task")
@Api(tags = "Task", value = "Task Service")
public class TaskServiceSwaggerManual extends TaskRestServiceImpl {

  public TaskServiceSwaggerManual(String engineName, ObjectMapper objectMapper) {
    super(engineName, objectMapper);
  }

  @Override
  @GET
  @Path("/{id}")
  @ApiOperation(value = "Get Task", notes = "Retrieves a task by id.", response=TaskDto.class)
  @ApiResponses({ @ApiResponse(code = 404, message = "If no tasks with given id is found."),
      @ApiResponse(code = 200, message = "Lauft.") })
  public TaskResource getTask(@ApiParam(value = "The id of the task to be retrieved.", example = "1") @PathParam("id") String id) {
    return super.getTask(id);
  }

  @POST
  @Path("/create")
  @ApiOperation(value = "Create Task")
  @Consumes(MediaType.APPLICATION_JSON)
  @Override
  public void createTask(TaskDto taskDto) {
    super.createTask(taskDto);
  }
  
  @GET
  @Path("/{id}/form")
  public Object getForm(@ApiParam(value = "The id of the task to be retrieved.", example = "1") @PathParam("id") String id, Request request) {
    return getTask(id).getForm();
  }
  
  
  

  /*
  public static class TaskResourceSwagger implements TaskResource {

    private TaskResource resource;

    public TaskResourceSwagger(TaskResource resource) {
      this.resource = resource;
    }
    
    @Override
    public Object getTask(Request request) {
      return resource.getTask(request);
    }

    @Override
    public FormDto getForm() {
      return resource.getForm();
    }

    @Override
    public void submit(CompleteTaskDto dto) {
      resource.submit(dto);
    }

    @Override
    public Response getRenderedForm() {
      return resource.getRenderedForm();
    }

    @Override
    public void claim(UserIdDto dto) {
      resource.claim(dto);
    }

    @Override
    public void unclaim() {
      resource.unclaim();
    }

    @Override
    public void complete(CompleteTaskDto dto) {
      resource.complete(dto);
    }

    @Override
    public void resolve(CompleteTaskDto dto) {
      resource.resolve(dto);
    }

    @Override
    public void delegate(UserIdDto delegatedUser) {
      resource.delegate(delegatedUser);
    }

    @Override
    public void setAssignee(UserIdDto dto) {
      resource.setAssignee(dto);
    }

    @Override
    public List<IdentityLinkDto> getIdentityLinks(String type) {
      return resource.getIdentityLinks(type);
    }

    @Override
    public void addIdentityLink(IdentityLinkDto identityLink) {
      resource.addIdentityLink(identityLink);
    }

    @Override
    public void deleteIdentityLink(IdentityLinkDto identityLink) {
      resource.deleteIdentityLink(identityLink);
    }

    @Override
    public TaskCommentResource getTaskCommentResource() {
      return resource.getTaskCommentResource();
    }

    @Override
    public TaskAttachmentResource getAttachmentResource() {
      return resource.getAttachmentResource();
    }

    @Override
    public VariableResource getVariables() {
      return resource.getVariables();
    }

    @Override
    public VariableResource getLocalVariables() {
      return resource.getLocalVariables();
    }

    @Override
    public Map<String, VariableValueDto> getFormVariables(String variableNames, boolean deserializeValues) {
      return resource.getFormVariables(variableNames, deserializeValues);
    }

    @Override
    public void updateTask(TaskDto task) {
      resource.updateTask(task);
    }

    @Override
    public void deleteTask(String id) {
      resource.deleteTask(id);
    }
  }
   */
}
