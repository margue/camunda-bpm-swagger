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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.rest.dto.repository.DeploymentDto;
import org.camunda.bpm.engine.rest.dto.repository.DeploymentResourceDto;
import org.camunda.bpm.engine.rest.dto.repository.RedeploymentDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.DeploymentResourceDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.DeploymentWithDefinitionsDtoSwagger;
import org.camunda.bpm.engine.rest.impl.DeploymentRestServiceImpl;
import org.camunda.bpm.engine.rest.mapper.MultipartFormData;
import org.camunda.bpm.engine.rest.sub.repository.DeploymentResource;
import org.camunda.bpm.engine.rest.sub.repository.DeploymentResourcesResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/deployment")
@Api(value = "Deployment Service", tags = "Deployment")
public class DeploymentRestServiceSwagger
    extends DeploymentRestServiceImpl
{

    public DeploymentRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Deployment", notes = "Operation Get Deployment", response = org.camunda.bpm.engine.rest.dto.repository.DeploymentDto.class)
    @Override
    public DeploymentResource getDeployment(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getDeployment(id);
    }

    @Path("/{id}/redeploy")
    @POST
    @Produces("application/json")
    @ApiOperation(value = "Redeploy", notes = "Operation Redeploy")
    public org.camunda.bpm.engine.rest.dto.swagger.DeploymentDtoSwagger getDeploymentRedeploy(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter redeploymentDto")
        RedeploymentDto redeploymentDto) {
        return getDeployment(id).redeploy(uriInfo, redeploymentDto);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Deployment", notes = "Operation Get Deployment")
    public org.camunda.bpm.engine.rest.dto.swagger.DeploymentDtoSwagger getDeploymentGetDeployment(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getDeployment(id).getDeployment();
    }

    @Path("/{id}/resources")
    @ApiOperation(value = "Get Deployment Resources", notes = "Operation Get Deployment Resources", response = java.util.List.class)
    public DeploymentResourcesResource getDeploymentGetDeploymentResources(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getDeployment(id).getDeploymentResources();
    }

    @Path("/{id}/resources/{resourceId}/data")
    @GET
    @ApiOperation(value = "Get Deployment Resource Data", notes = "Operation Get Deployment Resource Data")
    public Response getDeploymentGetDeploymentResourcesGetDeploymentResourceData(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("resourceId")
        @ApiParam("Parameter resourceId")
        String resourceId) {
        return getDeployment(id).getDeploymentResources().getDeploymentResourceData(resourceId);
    }

    @Path("/{id}/resources")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Deployment Resources", notes = "Operation Get Deployment Resources")
    public List<DeploymentResourceDto> getDeploymentGetDeploymentResourcesGetDeploymentResources(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getDeployment(id).getDeploymentResources().getDeploymentResources();
    }

    @Path("/{id}/resources/{resourceId}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Deployment Resource", notes = "Operation Get Deployment Resource")
    public DeploymentResourceDtoSwagger getDeploymentGetDeploymentResourcesGetDeploymentResource(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @PathParam("resourceId")
        @ApiParam("Parameter resourceId")
        String resourceId) {
        return getDeployment(id).getDeploymentResources().getDeploymentResource(resourceId);
    }

    @Path("/{id}")
    @DELETE
    @ApiOperation(value = "Delete Deployment", notes = "Operation Delete Deployment")
    public void getDeploymentDeleteDeployment(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        getDeployment(id).deleteDeployment(id, uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Deployments", notes = "Operation Get Deployments")
    @Override
    public List<DeploymentDto> getDeployments(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getDeployments(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Deployments Count", notes = "Operation Get Deployments Count")
    @Override
    public CountResultDtoSwagger getDeploymentsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getDeploymentsCount(uriInfo);
    }

    @Path("/create")
    @POST
    @Consumes("multipart/form-data")
    @Produces("application/json")
    @ApiOperation(value = "Create Deployment", notes = "Operation Create Deployment")
    @Override
    public DeploymentWithDefinitionsDtoSwagger createDeployment(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @ApiParam("Parameter multipartFormData")
        MultipartFormData multipartFormData) {
        return super.createDeployment(uriInfo, multipartFormData);
    }
}
