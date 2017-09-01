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
import org.camunda.bpm.engine.rest.dto.identity.TenantDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.TenantDtoSwagger;
import org.camunda.bpm.engine.rest.impl.TenantRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.identity.TenantGroupMembersResource;
import org.camunda.bpm.engine.rest.sub.identity.TenantResource;
import org.camunda.bpm.engine.rest.sub.identity.TenantUserMembersResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/tenant")
@Api(value = "Tenant Service", tags = "Tenant")
public class TenantRestServiceSwagger
    extends TenantRestServiceImpl
{

    public TenantRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Query Tenants", notes = "Operation Query Tenants")
    @Override
    public List<TenantDto> queryTenants(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.queryTenants(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Tenant Count", notes = "Operation Get Tenant Count")
    @Override
    public CountResultDtoSwagger getTenantCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getTenantCount(uriInfo);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Tenant", notes = "Operation Get Tenant", response = org.camunda.bpm.engine.rest.dto.identity.TenantDto.class)
    @Override
    public TenantResource getTenant(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getTenant(id);
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Tenant", notes = "Operation Update Tenant")
    public void getTenantUpdateTenant(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter tenantDto")
        TenantDto tenantDto) {
        getTenant(id).updateTenant(tenantDto);
    }

    @Path("/{id}")
    @DELETE
    @Produces("application/json")
    @ApiOperation(value = "Delete Tenant", notes = "Operation Delete Tenant")
    public void getTenantDeleteTenant(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        getTenant(id).deleteTenant();
    }

    @Path("/{id}/group-members")
    @ApiOperation(value = "Get Tenant Group Members Resource", notes = "Operation Get Tenant Group Members Resource", response = org.camunda.bpm.engine.rest.sub.identity.TenantGroupMembersResource.class)
    public TenantGroupMembersResource getTenantGetTenantGroupMembersResource(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getTenant(id).getTenantGroupMembersResource();
    }

    @Path("/{id}/group-members")
    @Produces("application/json")
    @ApiOperation(value = "Available Operations", notes = "Operation Available Operations")
    public org.camunda.bpm.engine.rest.dto.swagger.ResourceOptionsDtoSwagger getTenantGetTenantGroupMembersResourceAvailableOperations(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getTenant(id).getTenantGroupMembersResource().availableOperations(uriInfo);
    }

    @Path("/{id}/group-members/{groupId}")
    @PUT
    @ApiOperation(value = "Create Membership", notes = "Operation Create Membership")
    public void getTenantGetTenantGroupMembersResourceCreateMembership(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("groupId")
        @ApiParam("Parameter groupId")
        String groupId) {
        getTenant(id).getTenantGroupMembersResource().createMembership(groupId);
    }

    @Path("/{id}/group-members/{groupId}")
    @DELETE
    @ApiOperation(value = "Delete Membership", notes = "Operation Delete Membership")
    public void getTenantGetTenantGroupMembersResourceDeleteMembership(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("groupId")
        @ApiParam("Parameter groupId")
        String groupId) {
        getTenant(id).getTenantGroupMembersResource().deleteMembership(groupId);
    }

    @Path("/{id}/user-members")
    @ApiOperation(value = "Get Tenant User Members Resource", notes = "Operation Get Tenant User Members Resource", response = org.camunda.bpm.engine.rest.sub.identity.TenantUserMembersResource.class)
    public TenantUserMembersResource getTenantGetTenantUserMembersResource(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getTenant(id).getTenantUserMembersResource();
    }

    @Path("/{id}/user-members/{userId}")
    @PUT
    @ApiOperation(value = "Create Membership", notes = "Operation Create Membership")
    public void getTenantGetTenantUserMembersResourceCreateMembership(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("userId")
        @ApiParam("Parameter userId")
        String userId) {
        getTenant(id).getTenantUserMembersResource().createMembership(userId);
    }

    @Path("/{id}/user-members")
    @Produces("application/json")
    @ApiOperation(value = "Available Operations", notes = "Operation Available Operations")
    public org.camunda.bpm.engine.rest.dto.swagger.ResourceOptionsDtoSwagger getTenantGetTenantUserMembersResourceAvailableOperations(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getTenant(id).getTenantUserMembersResource().availableOperations(uriInfo);
    }

    @Path("/{id}/user-members/{userId}")
    @DELETE
    @ApiOperation(value = "Delete Membership", notes = "Operation Delete Membership")
    public void getTenantGetTenantUserMembersResourceDeleteMembership(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("userId")
        @ApiParam("Parameter userId")
        String userId) {
        getTenant(id).getTenantUserMembersResource().deleteMembership(userId);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Tenant", notes = "Operation Get Tenant")
    public TenantDtoSwagger getTenantGetTenant(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getTenant(id).getTenant(uriInfo);
    }

    @Path("/{id}")
    @Produces("application/json")
    @ApiOperation(value = "Available Operations", notes = "Operation Available Operations")
    public org.camunda.bpm.engine.rest.dto.swagger.ResourceOptionsDtoSwagger getTenantAvailableOperations(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getTenant(id).availableOperations(uriInfo);
    }

    @Path("/create")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Create Tenant", notes = "Operation Create Tenant")
    @Override
    public void createTenant(
        @ApiParam("Parameter tenantDto")
        TenantDto tenantDto) {
        super.createTenant(tenantDto);
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
}
