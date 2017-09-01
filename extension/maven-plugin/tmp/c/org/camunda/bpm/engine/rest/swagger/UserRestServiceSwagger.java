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
import org.camunda.bpm.engine.rest.dto.identity.UserCredentialsDto;
import org.camunda.bpm.engine.rest.dto.identity.UserDto;
import org.camunda.bpm.engine.rest.dto.identity.UserProfileDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.UserProfileDtoSwagger;
import org.camunda.bpm.engine.rest.impl.UserRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.identity.UserResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/user")
@Api(value = "User Service", tags = "User")
public class UserRestServiceSwagger
    extends UserRestServiceImpl
{

    public UserRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get User", notes = "Operation Get User", response = org.camunda.bpm.engine.rest.sub.identity.UserResource.class)
    @Override
    public UserResource getUser(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getUser(id);
    }

    @Path("/{id}/profile")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Profile", notes = "Operation Update Profile")
    public void getUserUpdateProfile(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter userProfileDto")
        UserProfileDto userProfileDto) {
        getUser(id).updateProfile(userProfileDto);
    }

    @Path("/{id}")
    @DELETE
    @Produces("application/json")
    @ApiOperation(value = "Delete User", notes = "Operation Delete User")
    public void getUserDeleteUser(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        getUser(id).deleteUser();
    }

    @Path("/{id}/credentials")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Credentials", notes = "Operation Update Credentials")
    public void getUserUpdateCredentials(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter userCredentialsDto")
        UserCredentialsDto userCredentialsDto) {
        getUser(id).updateCredentials(userCredentialsDto);
    }

    @Path("/{id}")
    @Produces("application/json")
    @ApiOperation(value = "Available Operations", notes = "Operation Available Operations")
    public org.camunda.bpm.engine.rest.dto.swagger.ResourceOptionsDtoSwagger getUserAvailableOperations(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getUser(id).availableOperations(uriInfo);
    }

    @Path("/{id}/profile")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get User Profile", notes = "Operation Get User Profile")
    public UserProfileDtoSwagger getUserGetUserProfile(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getUser(id).getUserProfile(uriInfo);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get User Count", notes = "Operation Get User Count")
    @Override
    public CountResultDtoSwagger getUserCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getUserCount(uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Query Users", notes = "Operation Query Users")
    @Override
    public List<UserProfileDto> queryUsers(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryUsers(uriInfo, firstResult, maxResults);
    }

    @Path("")
    @Produces("application/json")
    @ApiOperation(value = "Available Operations", notes = "Operation Available Operations")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.ResourceOptionsDtoSwagger availableOperations(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.availableOperations(uriInfo);
    }

    @Path("/create")
    @POST
    @Consumes("*/*")
    @ApiOperation(value = "Create User", notes = "Operation Create User")
    @Override
    public void createUser(
        @ApiParam("Parameter userDto")
        UserDto userDto) {
        super.createUser(userDto);
    }
}
