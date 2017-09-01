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
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.dto.history.HistoricCaseActivityStatisticsDto;
import org.camunda.bpm.engine.rest.impl.history.HistoricCaseActivityStatisticsRestServiceImpl;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/case-definition/{id}/statistics")
@Api(value = "Historic Activity", tags = "Historic")
public class HistoricCaseActivityStatisticsRestServiceSwagger
    extends HistoricCaseActivityStatisticsRestServiceImpl
{

    public HistoricCaseActivityStatisticsRestServiceSwagger(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Historic Case Activity Statistics", notes = "Operation Get Historic Case Activity Statistics")
    @Override
    public List<HistoricCaseActivityStatisticsDto> getHistoricCaseActivityStatistics(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getHistoricCaseActivityStatistics(id);
    }
}
