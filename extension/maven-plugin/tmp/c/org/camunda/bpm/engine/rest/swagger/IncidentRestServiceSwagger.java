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
import org.camunda.bpm.engine.rest.dto.runtime.IncidentDto;
import org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger;
import org.camunda.bpm.engine.rest.impl.IncidentRestServiceImpl;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/incident")
@Api(value = "Incident Service", tags = "Incident")
public class IncidentRestServiceSwagger
    extends IncidentRestServiceImpl
{

    public IncidentRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Incidents Count", notes = "Operation Get Incidents Count")
    @Override
    public CountResultDtoSwagger getIncidentsCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getIncidentsCount(uriInfo);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Incidents", notes = "Operation Get Incidents")
    @Override
    public List<IncidentDto> getIncidents(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getIncidents(uriInfo, firstResult, maxResults);
    }
}
