package org.camunda.bpm.engine.rest.swagger;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.history.HistoricDetailDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.HistoricDetailDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.HistoricDetailRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.history.HistoricDetailResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/detail")
@Api(value = "Historic Rest", tags = "Historic")
public class HistoricDetailRestServiceSwagger
    extends HistoricDetailRestServiceImpl
{

    public HistoricDetailRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("/{id}")
    @ApiOperation(value = "Historic Detail", notes = "Operation Historic Detail", response = org.camunda.bpm.engine.rest.dto.history.HistoricDetailDto.class)
    @Override
    public HistoricDetailResource historicDetail(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.historicDetail(id);
    }

    @Path("/{id}/data")
    @GET
    @ApiOperation(value = "Get Resource Binary", notes = "Operation Get Resource Binary")
    public Response historicDetailGetResourceBinary(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return historicDetail(id).getResourceBinary();
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Resource", notes = "Operation Get Resource")
    public HistoricDetailDtoSwagger historicDetailGetResource(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("deserializeValue")
        @ApiParam("Parameter deserializeValue")
        boolean deserializeValue) {
        return historicDetail(id).getResource(deserializeValue);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Details Count", notes = "Operation Get Historic Details Count")
    @Override
    public CountResultDtoSwagger getHistoricDetailsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricDetailsCount(uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Details", notes = "Operation Get Historic Details")
    @Override
    public List<HistoricDetailDto> getHistoricDetails(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults,
        @QueryParam("deserializeValues")
        @ApiParam("Parameter deserializeValues")
        boolean deserializeValues) {
        return super.getHistoricDetails(uriInfo, firstResult, maxResults, deserializeValues);
    }
}
