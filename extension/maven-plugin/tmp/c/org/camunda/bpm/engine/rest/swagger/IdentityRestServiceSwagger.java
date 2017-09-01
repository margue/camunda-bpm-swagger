package org.camunda.bpm.engine.rest.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.camunda.bpm.engine.rest.dto.identity.BasicUserCredentialsDto;
import org.camunda.bpm.engine.rest.dto.swagger.GroupInfoDtoSwagger;
import org.camunda.bpm.engine.rest.impl.IdentityRestServiceImpl;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/identity")
@Api(value = "Identity Service", tags = "Identity")
public class IdentityRestServiceSwagger
    extends IdentityRestServiceImpl
{

    public IdentityRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/verify")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Verify User", notes = "Operation Verify User")
    @Override
    public AuthenticationResult verifyUser(
        @ApiParam("Parameter basicUserCredentialsDto")
        BasicUserCredentialsDto basicUserCredentialsDto) {
        return super.verifyUser(basicUserCredentialsDto);
    }

    @Path("/groups")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Group Info", notes = "Operation Get Group Info")
    @Override
    public GroupInfoDtoSwagger getGroupInfo(
        @QueryParam("userId")
        @ApiParam("Parameter userId")
        String userId) {
        return super.getGroupInfo(userId);
    }
}
