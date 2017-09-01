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
import org.camunda.bpm.engine.rest.dto.identity.GroupDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.GroupDtoSwagger;
import org.camunda.bpm.engine.rest.impl.GroupRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.identity.GroupMembersResource;
import org.camunda.bpm.engine.rest.sub.identity.GroupResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/group")
@Api(value = "Group Service", tags = "Group")
public class GroupRestServiceSwagger
    extends GroupRestServiceImpl
{

    public GroupRestServiceSwagger(String string, ObjectMapper objectMapper) {
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

    @Path("/create")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Create Group", notes = "Operation Create Group")
    @Override
    public void createGroup(
        @ApiParam("Parameter groupDto")
        GroupDto groupDto) {
        super.createGroup(groupDto);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Query Groups", notes = "Operation Query Groups")
    @Override
    public List<GroupDto> queryGroups(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryGroups(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Group Count", notes = "Operation Get Group Count")
    @Override
    public CountResultDtoSwagger getGroupCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getGroupCount(uriInfo);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Group", notes = "Operation Get Group", response = org.camunda.bpm.engine.rest.dto.identity.GroupDto.class)
    @Override
    public GroupResource getGroup(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getGroup(id);
    }

    @Path("/{id}/members")
    @ApiOperation(value = "Get Group Members Resource", notes = "Operation Get Group Members Resource", response = org.camunda.bpm.engine.rest.sub.identity.GroupMembersResource.class)
    public GroupMembersResource getGroupGetGroupMembersResource(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getGroup(id).getGroupMembersResource();
    }

    @Path("/{id}/members/{userId}")
    @PUT
    @ApiOperation(value = "Create Group Member", notes = "Operation Create Group Member")
    public void getGroupGetGroupMembersResourceCreateGroupMember(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("userId")
        @ApiParam("Parameter userId")
        String userId) {
        getGroup(id).getGroupMembersResource().createGroupMember(userId);
    }

    @Path("/{id}/members")
    @Produces("application/json")
    @ApiOperation(value = "Available Operations", notes = "Operation Available Operations")
    public org.camunda.bpm.engine.rest.dto.swagger.ResourceOptionsDtoSwagger getGroupGetGroupMembersResourceAvailableOperations(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getGroup(id).getGroupMembersResource().availableOperations(uriInfo);
    }

    @Path("/{id}/members/{userId}")
    @DELETE
    @ApiOperation(value = "Delete Group Member", notes = "Operation Delete Group Member")
    public void getGroupGetGroupMembersResourceDeleteGroupMember(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("userId")
        @ApiParam("Parameter userId")
        String userId) {
        getGroup(id).getGroupMembersResource().deleteGroupMember(userId);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Group", notes = "Operation Get Group")
    public GroupDtoSwagger getGroupGetGroup(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getGroup(id).getGroup(uriInfo);
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Group", notes = "Operation Update Group")
    public void getGroupUpdateGroup(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter groupDto")
        GroupDto groupDto) {
        getGroup(id).updateGroup(groupDto);
    }

    @Path("/{id}")
    @DELETE
    @Produces("application/json")
    @ApiOperation(value = "Delete Group", notes = "Operation Delete Group")
    public void getGroupDeleteGroup(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        getGroup(id).deleteGroup();
    }

    @Path("/{id}")
    @Produces("application/json")
    @ApiOperation(value = "Available Operations", notes = "Operation Available Operations")
    public org.camunda.bpm.engine.rest.dto.swagger.ResourceOptionsDtoSwagger getGroupAvailableOperations(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getGroup(id).availableOperations(uriInfo);
    }
}
