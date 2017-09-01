package org.camunda.bpm.engine.rest.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.camunda.bpm.engine.rest.dto.swagger.JobDtoSwagger;
import org.camunda.bpm.engine.rest.history.HistoricActivityInstanceRestService;
import org.camunda.bpm.engine.rest.history.HistoricActivityStatisticsRestService;
import org.camunda.bpm.engine.rest.history.HistoricBatchRestService;
import org.camunda.bpm.engine.rest.history.HistoricCaseActivityInstanceRestService;
import org.camunda.bpm.engine.rest.history.HistoricCaseActivityStatisticsRestService;
import org.camunda.bpm.engine.rest.history.HistoricCaseInstanceRestService;
import org.camunda.bpm.engine.rest.history.HistoricDecisionInstanceRestService;
import org.camunda.bpm.engine.rest.history.HistoricDecisionStatisticsRestService;
import org.camunda.bpm.engine.rest.history.HistoricDetailRestService;
import org.camunda.bpm.engine.rest.history.HistoricExternalTaskLogRestService;
import org.camunda.bpm.engine.rest.history.HistoricIdentityLinkLogRestService;
import org.camunda.bpm.engine.rest.history.HistoricIncidentRestService;
import org.camunda.bpm.engine.rest.history.HistoricJobLogRestService;
import org.camunda.bpm.engine.rest.history.HistoricProcessInstanceRestService;
import org.camunda.bpm.engine.rest.history.HistoricTaskInstanceRestService;
import org.camunda.bpm.engine.rest.history.HistoricVariableInstanceRestService;
import org.camunda.bpm.engine.rest.history.UserOperationLogRestService;
import org.camunda.bpm.engine.rest.impl.history.HistoryRestServiceImpl;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/history")
@Api(value = "History Service", tags = "History")
public class HistoryRestServiceSwagger
    extends HistoryRestServiceImpl
{

    public HistoryRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/identity-link-log")
    @ApiOperation(value = "Get Identity Link Service", notes = "Operation Get Identity Link Service")
    @Override
    public HistoricIdentityLinkLogRestService getIdentityLinkService() {
        return super.getIdentityLinkService();
    }

    @Path("/cleanup")
    @POST
    @Produces("application/json")
    @ApiOperation(value = "Cleanup Async", notes = "Operation Cleanup Async")
    @Override
    public JobDtoSwagger cleanupAsync(
        @QueryParam("immediatelyDue")
        @ApiParam("Parameter immediatelyDue")
        boolean immediatelyDue) {
        return super.cleanupAsync(immediatelyDue);
    }

    @Path("/batch")
    @ApiOperation(value = "Get Batch Service", notes = "Operation Get Batch Service")
    @Override
    public HistoricBatchRestService getBatchService() {
        return super.getBatchService();
    }

    @Path("/decision-requirements-definition")
    @ApiOperation(value = "Get Decision Statistics Service", notes = "Operation Get Decision Statistics Service")
    @Override
    public HistoricDecisionStatisticsRestService getDecisionStatisticsService() {
        return super.getDecisionStatisticsService();
    }

    @Path("/external-task-log")
    @ApiOperation(value = "Get External Task Log Service", notes = "Operation Get External Task Log Service")
    @Override
    public HistoricExternalTaskLogRestService getExternalTaskLogService() {
        return super.getExternalTaskLogService();
    }

    @Path("/activity-instance")
    @ApiOperation(value = "Get Activity Instance Service", notes = "Operation Get Activity Instance Service")
    @Override
    public HistoricActivityInstanceRestService getActivityInstanceService() {
        return super.getActivityInstanceService();
    }

    @Path("/case-definition/{id}/statistics")
    @ApiOperation(value = "Get Case Activity Statistics Service", notes = "Operation Get Case Activity Statistics Service")
    @Override
    public HistoricCaseActivityStatisticsRestService getCaseActivityStatisticsService() {
        return super.getCaseActivityStatisticsService();
    }

    @Path("/task")
    @ApiOperation(value = "Get Task Instance Service", notes = "Operation Get Task Instance Service")
    @Override
    public HistoricTaskInstanceRestService getTaskInstanceService() {
        return super.getTaskInstanceService();
    }

    @Path("/detail")
    @ApiOperation(value = "Get Detail Service", notes = "Operation Get Detail Service")
    @Override
    public HistoricDetailRestService getDetailService() {
        return super.getDetailService();
    }

    @Path("/case-instance")
    @ApiOperation(value = "Get Case Instance Service", notes = "Operation Get Case Instance Service")
    @Override
    public HistoricCaseInstanceRestService getCaseInstanceService() {
        return super.getCaseInstanceService();
    }

    @Path("/decision-instance")
    @ApiOperation(value = "Get Decision Instance Service", notes = "Operation Get Decision Instance Service")
    @Override
    public HistoricDecisionInstanceRestService getDecisionInstanceService() {
        return super.getDecisionInstanceService();
    }

    @Path("/user-operation")
    @ApiOperation(value = "Get User Operation Log Rest Service", notes = "Operation Get User Operation Log Rest Service")
    @Override
    public UserOperationLogRestService getUserOperationLogRestService() {
        return super.getUserOperationLogRestService();
    }

    @Path("/process-definition/{id}/statistics")
    @ApiOperation(value = "Get Activity Statistics Service", notes = "Operation Get Activity Statistics Service")
    @Override
    public HistoricActivityStatisticsRestService getActivityStatisticsService() {
        return super.getActivityStatisticsService();
    }

    @Path("/variable-instance")
    @ApiOperation(value = "Get Variable Instance Service", notes = "Operation Get Variable Instance Service")
    @Override
    public HistoricVariableInstanceRestService getVariableInstanceService() {
        return super.getVariableInstanceService();
    }

    @Path("/incident")
    @ApiOperation(value = "Get Incident Service", notes = "Operation Get Incident Service")
    @Override
    public HistoricIncidentRestService getIncidentService() {
        return super.getIncidentService();
    }

    @Path("/case-activity-instance")
    @ApiOperation(value = "Get Case Activity Instance Service", notes = "Operation Get Case Activity Instance Service")
    @Override
    public HistoricCaseActivityInstanceRestService getCaseActivityInstanceService() {
        return super.getCaseActivityInstanceService();
    }

    @Path("/process-instance")
    @ApiOperation(value = "Get Process Instance Service", notes = "Operation Get Process Instance Service")
    @Override
    public HistoricProcessInstanceRestService getProcessInstanceService() {
        return super.getProcessInstanceService();
    }

    @Path("/job-log")
    @ApiOperation(value = "Get Job Log Service", notes = "Operation Get Job Log Service")
    @Override
    public HistoricJobLogRestService getJobLogService() {
        return super.getJobLogService();
    }
}
