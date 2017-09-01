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
import org.camunda.bpm.engine.rest.dto.history.HistoricDecisionInstanceStatisticsDto;
import org.camunda.bpm.engine.rest.impl.history.HistoricDecisionStatisticsRestServiceImpl;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/decision-requirements-definition")
@Api(value = "Historic Statistics", tags = "Historic")
public class HistoricDecisionStatisticsRestServiceSwagger
    extends HistoricDecisionStatisticsRestServiceImpl
{

    public HistoricDecisionStatisticsRestServiceSwagger(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Path("/{id}/statistics")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Decision Statistics", notes = "Operation Get Decision Statistics")
    @Override
    public List<HistoricDecisionInstanceStatisticsDto> getDecisionStatistics(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("decisionInstanceId")
        @ApiParam("Parameter decisionInstanceId")
        String decisionInstanceId) {
        return super.getDecisionStatistics(id, decisionInstanceId);
    }
}
