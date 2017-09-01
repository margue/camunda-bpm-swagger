package org.camunda.bpm.engine.rest.swagger;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.history.batch.HistoricBatchDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.HistoricBatchDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.HistoricBatchRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.history.HistoricBatchResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/batch")
@Api(value = "Historic Rest", tags = "Historic")
public class HistoricBatchRestServiceSwagger
    extends HistoricBatchRestServiceImpl
{

    public HistoricBatchRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Batches Count", notes = "Operation Get Historic Batches Count")
    @Override
    public CountResultDtoSwagger getHistoricBatchesCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricBatchesCount(uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Batches", notes = "Operation Get Historic Batches")
    @Override
    public List<HistoricBatchDto> getHistoricBatches(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getHistoricBatches(uriInfo, firstResult, maxResults);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Historic Batch", notes = "Operation Get Historic Batch", response = org.camunda.bpm.engine.rest.dto.history.batch.HistoricBatchDto.class)
    @Override
    public HistoricBatchResource getHistoricBatch(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getHistoricBatch(id);
    }

    @Path("/{id}")
    @DELETE
    @ApiOperation(value = "Delete Historic Batch", notes = "Operation Delete Historic Batch")
    public void getHistoricBatchDeleteHistoricBatch(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        getHistoricBatch(id).deleteHistoricBatch();
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Batch", notes = "Operation Get Historic Batch")
    public HistoricBatchDtoSwagger getHistoricBatchGetHistoricBatch(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getHistoricBatch(id).getHistoricBatch();
    }
}
