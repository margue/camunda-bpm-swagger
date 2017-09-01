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
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.rest.dto.metrics.MetricsIntervalResultDto;
import org.camunda.bpm.engine.rest.dto.swagger.MetricsResultDtoSwagger;
import org.camunda.bpm.engine.rest.impl.MetricsRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.metrics.MetricsResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/metrics")
@Api(value = "Metrics Service", tags = "Metrics")
public class MetricsRestServiceSwagger
    extends MetricsRestServiceImpl
{

    public MetricsRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Interval", notes = "Operation Interval")
    @Override
    public List<MetricsIntervalResultDto> interval(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.interval(uriInfo);
    }

    @Path("/{name}")
    @Produces("application/json")
    @ApiOperation(value = "Get Metrics", notes = "Operation Get Metrics", response = org.camunda.bpm.engine.rest.sub.metrics.MetricsResource.class)
    @Override
    public MetricsResource getMetrics(
        @PathParam("name")
        @ApiParam("Parameter name")
        String name) {
        return super.getMetrics(name);
    }

    @Path("/{name}/sum")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Sum", notes = "Operation Sum")
    public MetricsResultDtoSwagger getMetricsSum(
        @PathParam("name")
        @ApiParam("Parameter name")
        String name,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getMetrics(name).sum(uriInfo);
    }
}
