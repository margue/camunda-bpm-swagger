package org.camunda.bpm.engine.rest.swagger;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.history.HistoricActivityStatisticsDto;
import org.camunda.bpm.engine.rest.impl.history.HistoricActivityStatisticsRestServiceImpl;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/process-definition/{id}/statistics")
@Api(value = "Historic Statistics", tags = "Historic")
public class HistoricActivityStatisticsRestServiceSwagger
    extends HistoricActivityStatisticsRestServiceImpl
{

    public HistoricActivityStatisticsRestServiceSwagger(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Activity Statistics", notes = "Operation Get Historic Activity Statistics")
    @Override
    public List<HistoricActivityStatisticsDto> getHistoricActivityStatistics(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("canceled")
        @ApiParam("Parameter canceled")
        Boolean canceled,
        @QueryParam("finished")
        @ApiParam("Parameter finished")
        Boolean finished,
        @QueryParam("completeScope")
        @ApiParam("Parameter completeScope")
        Boolean completeScope,
        @QueryParam("sortBy")
        @ApiParam("Parameter sortBy")
        String sortBy,
        @QueryParam("sortOrder")
        @ApiParam("Parameter sortOrder")
        String sortOrder) {
        return super.getHistoricActivityStatistics(id, canceled, finished, completeScope, sortBy, sortOrder);
    }
}
