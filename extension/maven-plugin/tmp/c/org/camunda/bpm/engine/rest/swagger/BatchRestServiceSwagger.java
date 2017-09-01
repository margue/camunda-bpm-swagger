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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.rest.dto.SuspensionStateDto;
import org.camunda.bpm.engine.rest.dto.batch.BatchDto;
import org.camunda.bpm.engine.rest.dto.batch.BatchStatisticsDto;
import org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger;
import org.camunda.bpm.engine.rest.impl.BatchRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.batch.BatchResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/batch")
@Api(value = "Batch Service", tags = "Batch")
public class BatchRestServiceSwagger
    extends BatchRestServiceImpl
{

    public BatchRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/statistics/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Statistics Count", notes = "Operation Get Statistics Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getStatisticsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getStatisticsCount(uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Batches", notes = "Operation Get Batches")
    @Override
    public List<BatchDto> getBatches(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getBatches(uriInfo, firstResult, maxResults);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Batch", notes = "Operation Get Batch", response = org.camunda.bpm.engine.rest.dto.batch.BatchDto.class)
    @Override
    public BatchResource getBatch(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getBatch(id);
    }

    @Path("/{id}/suspended")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Suspension State", notes = "Operation Update Suspension State")
    public void getBatchUpdateSuspensionState(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter suspensionStateDto")
        SuspensionStateDto suspensionStateDto) {
        getBatch(id).updateSuspensionState(suspensionStateDto);
    }

    @Path("/{id}")
    @DELETE
    @ApiOperation(value = "Delete Batch", notes = "Operation Delete Batch")
    public void getBatchDeleteBatch(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("cascade")
        @ApiParam("Parameter cascade")
        boolean cascade) {
        getBatch(id).deleteBatch(cascade);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Batch", notes = "Operation Get Batch")
    public BatchDtoSwagger getBatchGetBatch(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getBatch(id).getBatch();
    }

    @Path("/statistics")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Statistics", notes = "Operation Get Statistics")
    @Override
    public List<BatchStatisticsDto> getStatistics(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getStatistics(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Batches Count", notes = "Operation Get Batches Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getBatchesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getBatchesCount(uriInfo);
    }
}
