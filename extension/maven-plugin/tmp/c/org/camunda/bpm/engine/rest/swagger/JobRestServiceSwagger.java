package org.camunda.bpm.engine.rest.swagger;

import java.util.List;
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
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.rest.dto.runtime.JobDto;
import org.camunda.bpm.engine.rest.dto.runtime.JobDuedateDto;
import org.camunda.bpm.engine.rest.dto.runtime.JobQueryDto;
import org.camunda.bpm.engine.rest.dto.runtime.JobSuspensionStateDto;
import org.camunda.bpm.engine.rest.dto.runtime.PriorityDto;
import org.camunda.bpm.engine.rest.dto.runtime.RetriesDto;
import org.camunda.bpm.engine.rest.dto.runtime.SetJobRetriesDto;
import org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.JobDtoSwagger;
import org.camunda.bpm.engine.rest.impl.JobRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.runtime.JobResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/job")
@Api(value = "Job Service", tags = "Job")
public class JobRestServiceSwagger
    extends JobRestServiceImpl
{

    public JobRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Jobs", notes = "Operation Query Jobs")
    @Override
    public List<JobDto> queryJobs(
        @ApiParam("Parameter jobQueryDto")
        JobQueryDto jobQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryJobs(jobQueryDto, firstResult, maxResults);
    }

    @Path("/retries")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Set Retries", notes = "Operation Set Retries")
    @Override
    public BatchDtoSwagger setRetries(
        @ApiParam("Parameter setJobRetriesDto")
        SetJobRetriesDto setJobRetriesDto) {
        return super.setRetries(setJobRetriesDto);
    }

    @Path("/suspended")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Suspension State", notes = "Operation Update Suspension State")
    @Override
    public void updateSuspensionState(
        @ApiParam("Parameter jobSuspensionStateDto")
        JobSuspensionStateDto jobSuspensionStateDto) {
        super.updateSuspensionState(jobSuspensionStateDto);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Jobs", notes = "Operation Get Jobs")
    @Override
    public List<JobDto> getJobs(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getJobs(uriInfo, firstResult, maxResults);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Job", notes = "Operation Get Job", response = org.camunda.bpm.engine.rest.dto.runtime.JobDto.class)
    @Override
    public JobResource getJob(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getJob(id);
    }

    @Path("/{id}/priority")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Set Job Priority", notes = "Operation Set Job Priority")
    public void getJobSetJobPriority(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter priorityDto")
        PriorityDto priorityDto) {
        getJob(id).setJobPriority(priorityDto);
    }

    @Path("/{id}/retries")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Set Job Retries", notes = "Operation Set Job Retries")
    public void getJobSetJobRetries(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter retriesDto")
        RetriesDto retriesDto) {
        getJob(id).setJobRetries(retriesDto);
    }

    @Path("/{id}/stacktrace")
    @GET
    @Produces("text/plain")
    @ApiOperation(value = "Get Stacktrace", notes = "Operation Get Stacktrace")
    public String getJobGetStacktrace(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getJob(id).getStacktrace();
    }

    @Path("/{id}")
    @DELETE
    @ApiOperation(value = "Delete Job", notes = "Operation Delete Job")
    public void getJobDeleteJob(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        getJob(id).deleteJob();
    }

    @Path("/{id}/suspended")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Suspension State", notes = "Operation Update Suspension State")
    public void getJobUpdateSuspensionState(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter jobSuspensionStateDto")
        JobSuspensionStateDto jobSuspensionStateDto) {
        getJob(id).updateSuspensionState(jobSuspensionStateDto);
    }

    @Path("/{id}/execute")
    @POST
    @ApiOperation(value = "Execute Job", notes = "Operation Execute Job")
    public void getJobExecuteJob(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        getJob(id).executeJob();
    }

    @Path("/{id}/duedate")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Set Job Duedate", notes = "Operation Set Job Duedate")
    public void getJobSetJobDuedate(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter jobDuedateDto")
        JobDuedateDto jobDuedateDto) {
        getJob(id).setJobDuedate(jobDuedateDto);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Job", notes = "Operation Get Job")
    public JobDtoSwagger getJobGetJob(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getJob(id).getJob();
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Jobs Count", notes = "Operation Get Jobs Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getJobsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getJobsCount(uriInfo);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Jobs Count", notes = "Operation Query Jobs Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryJobsCount(
        @ApiParam("Parameter jobQueryDto")
        JobQueryDto jobQueryDto) {
        return super.queryJobsCount(jobQueryDto);
    }
}
