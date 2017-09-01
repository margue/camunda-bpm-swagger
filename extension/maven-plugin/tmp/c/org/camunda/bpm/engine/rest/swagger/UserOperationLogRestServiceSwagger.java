package org.camunda.bpm.engine.rest.swagger;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.history.UserOperationLogEntryDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.UserOperationLogRestServiceImpl;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/user-operation")
@Api(value = "User Log", tags = "User")
public class UserOperationLogRestServiceSwagger
    extends UserOperationLogRestServiceImpl
{

    public UserOperationLogRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Query User Operation Count", notes = "Operation Query User Operation Count")
    @Override
    public CountResultDtoSwagger queryUserOperationCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.queryUserOperationCount(uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Query User Operation Entries", notes = "Operation Query User Operation Entries")
    @Override
    public List<UserOperationLogEntryDto> queryUserOperationEntries(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryUserOperationEntries(uriInfo, firstResult, maxResults);
    }
}
