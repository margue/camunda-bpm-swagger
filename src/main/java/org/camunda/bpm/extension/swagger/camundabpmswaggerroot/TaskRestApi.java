package org.camunda.bpm.extension.swagger.camundabpmswaggerroot;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.rest.TaskRestService;
import org.camunda.bpm.engine.rest.sub.task.TaskResource;

import javax.ws.rs.Path;

@Api("tasks")
public interface TaskRestApi extends TaskRestService {

  @Path("/rest/foo")
  @ApiOperation("foo")
  public void foo();

  @ApiOperation("task")
  @Override
  TaskResource getTask(String id);
}
