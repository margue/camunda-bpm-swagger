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
import org.camunda.bpm.engine.rest.dto.history.HistoricIncidentDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.impl.history.HistoricIncidentRestServiceImpl;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/incident")
@Api(value = "Historic Rest", tags = "Historic")
public class HistoricIncidentRestServiceSwagger
    extends HistoricIncidentRestServiceImpl
{

    public HistoricIncidentRestServiceSwagger(ObjectMapper objectMapper, ProcessEngine processEngine) {
        super(objectMapper, processEngine);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Incidents", notes = "Operation Get Historic Incidents")
    @Override
    public List<HistoricIncidentDto> getHistoricIncidents(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getHistoricIncidents(uriInfo, firstResult, maxResults);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Incidents Count", notes = "Operation Get Historic Incidents Count")
    @Override
    public CountResultDtoSwagger getHistoricIncidentsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getHistoricIncidentsCount(uriInfo);
    }
}
