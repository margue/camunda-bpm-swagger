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
import org.camunda.bpm.engine.rest.dto.management.JobDefinitionDto;
import org.camunda.bpm.engine.rest.dto.management.JobDefinitionQueryDto;
import org.camunda.bpm.engine.rest.dto.management.JobDefinitionSuspensionStateDto;
import org.camunda.bpm.engine.rest.dto.runtime.JobDefinitionPriorityDto;
import org.camunda.bpm.engine.rest.dto.runtime.RetriesDto;
import org.camunda.bpm.engine.rest.dto.swagger.JobDefinitionDtoSwagger;
import org.camunda.bpm.engine.rest.impl.JobDefinitionRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.management.JobDefinitionResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/job-definition")
@Api(value = "Job Rest", tags = "Job")
public class JobDefinitionRestServiceSwagger
    extends JobDefinitionRestServiceImpl
{

    public JobDefinitionRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Job Definitions Count", notes = "Operation Get Job Definitions Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getJobDefinitionsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getJobDefinitionsCount(uriInfo);
    }

    @Path("/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Job Definitions Count", notes = "Operation Query Job Definitions Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger queryJobDefinitionsCount(
        @ApiParam("Parameter jobDefinitionQueryDto")
        JobDefinitionQueryDto jobDefinitionQueryDto) {
        return super.queryJobDefinitionsCount(jobDefinitionQueryDto);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Job Definitions", notes = "Operation Query Job Definitions")
    @Override
    public List<JobDefinitionDto> queryJobDefinitions(
        @ApiParam("Parameter jobDefinitionQueryDto")
        JobDefinitionQueryDto jobDefinitionQueryDto,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryJobDefinitions(jobDefinitionQueryDto, firstResult, maxResults);
    }

    @Path("/suspended")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Suspension State", notes = "Operation Update Suspension State")
    @Override
    public void updateSuspensionState(
        @ApiParam("Parameter jobDefinitionSuspensionStateDto")
        JobDefinitionSuspensionStateDto jobDefinitionSuspensionStateDto) {
        super.updateSuspensionState(jobDefinitionSuspensionStateDto);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Job Definitions", notes = "Operation Get Job Definitions")
    @Override
    public List<JobDefinitionDto> getJobDefinitions(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getJobDefinitions(uriInfo, firstResult, maxResults);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Job Definition", notes = "Operation Get Job Definition", response = org.camunda.bpm.engine.rest.dto.management.JobDefinitionDto.class)
    @Override
    public JobDefinitionResource getJobDefinition(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getJobDefinition(id);
    }

    @Path("/{id}/jobPriority")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Set Job Priority", notes = "Operation Set Job Priority")
    public void getJobDefinitionSetJobPriority(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter jobDefinitionPriorityDto")
        JobDefinitionPriorityDto jobDefinitionPriorityDto) {
        getJobDefinition(id).setJobPriority(jobDefinitionPriorityDto);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Job Definition", notes = "Operation Get Job Definition")
    public JobDefinitionDtoSwagger getJobDefinitionGetJobDefinition(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getJobDefinition(id).getJobDefinition();
    }

    @Path("/{id}/retries")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Set Job Retries", notes = "Operation Set Job Retries")
    public void getJobDefinitionSetJobRetries(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter retriesDto")
        RetriesDto retriesDto) {
        getJobDefinition(id).setJobRetries(retriesDto);
    }

    @Path("/{id}/suspended")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Suspension State", notes = "Operation Update Suspension State")
    public void getJobDefinitionUpdateSuspensionState(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter jobDefinitionSuspensionStateDto")
        JobDefinitionSuspensionStateDto jobDefinitionSuspensionStateDto) {
        getJobDefinition(id).updateSuspensionState(jobDefinitionSuspensionStateDto);
    }
}
