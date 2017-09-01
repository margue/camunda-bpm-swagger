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
import org.camunda.bpm.engine.rest.dto.authorization.AuthorizationCreateDto;
import org.camunda.bpm.engine.rest.dto.authorization.AuthorizationDto;
import org.camunda.bpm.engine.rest.dto.swagger.AuthorizationCheckResultDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.impl.AuthorizationRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.authorization.AuthorizationResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/authorization")
@Api(value = "Authorization Service", tags = "Authorization")
public class AuthorizationRestServiceSwagger
    extends AuthorizationRestServiceImpl
{

    public AuthorizationRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
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

    @Path("/{id}")
    @ApiOperation(value = "Get Authorization", notes = "Operation Get Authorization", response = org.camunda.bpm.engine.rest.dto.authorization.AuthorizationDto.class)
    @Override
    public AuthorizationResource getAuthorization(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getAuthorization(id);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Authorization", notes = "Operation Get Authorization")
    public org.camunda.bpm.engine.rest.dto.swagger.AuthorizationDtoSwagger getAuthorizationGetAuthorization(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getAuthorization(id).getAuthorization(uriInfo);
    }

    @Path("/{id}")
    @Produces("application/json")
    @ApiOperation(value = "Available Operations", notes = "Operation Available Operations")
    public org.camunda.bpm.engine.rest.dto.swagger.ResourceOptionsDtoSwagger getAuthorizationAvailableOperations(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getAuthorization(id).availableOperations(uriInfo);
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Authorization", notes = "Operation Update Authorization")
    public void getAuthorizationUpdateAuthorization(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter authorizationDto")
        AuthorizationDto authorizationDto) {
        getAuthorization(id).updateAuthorization(authorizationDto);
    }

    @Path("/{id}")
    @DELETE
    @Produces("application/json")
    @ApiOperation(value = "Delete Authorization", notes = "Operation Delete Authorization")
    public void getAuthorizationDeleteAuthorization(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        getAuthorization(id).deleteAuthorization();
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Query Authorizations", notes = "Operation Query Authorizations")
    @Override
    public List<AuthorizationDto> queryAuthorizations(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryAuthorizations(uriInfo, firstResult, maxResults);
    }

    @Path("/create")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Create Authorization", notes = "Operation Create Authorization")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.AuthorizationDtoSwagger createAuthorization(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter authorizationCreateDto")
        AuthorizationCreateDto authorizationCreateDto) {
        return super.createAuthorization(uriInfo, authorizationCreateDto);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Authorization Count", notes = "Operation Get Authorization Count")
    @Override
    public CountResultDtoSwagger getAuthorizationCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getAuthorizationCount(uriInfo);
    }

    @Path("/check")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Is User Authorized", notes = "Operation Is User Authorized")
    @Override
    public AuthorizationCheckResultDtoSwagger isUserAuthorized(
        @QueryParam("permissionName")
        @ApiParam("Parameter permissionName")
        String permissionName,
        @QueryParam("resourceName")
        @ApiParam("Parameter resourceName")
        String resourceName,
        @QueryParam("resourceType")
        @ApiParam("Parameter resourceType")
        Integer resourceType,
        @QueryParam("resourceId")
        @ApiParam("Parameter resourceId")
        String resourceId) {
        return super.isUserAuthorized(permissionName, resourceName, resourceType, resourceId);
    }
}
