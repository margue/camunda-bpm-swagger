package org.camunda.bpm.engine.rest.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.camunda.bpm.engine.rest.dto.migration.MigrationExecutionDto;
import org.camunda.bpm.engine.rest.dto.migration.MigrationPlanDto;
import org.camunda.bpm.engine.rest.dto.migration.MigrationPlanGenerationDto;
import org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.MigrationPlanDtoSwagger;
import org.camunda.bpm.engine.rest.dto.swagger.MigrationPlanReportDtoSwagger;
import org.camunda.bpm.engine.rest.impl.MigrationRestServiceImpl;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/migration")
@Api(value = "Migration Service", tags = "Migration")
public class MigrationRestServiceSwagger
    extends MigrationRestServiceImpl
{

    public MigrationRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/generate")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Generate Migration Plan", notes = "Operation Generate Migration Plan")
    @Override
    public MigrationPlanDtoSwagger generateMigrationPlan(
        @ApiParam("Parameter migrationPlanGenerationDto")
        MigrationPlanGenerationDto migrationPlanGenerationDto) {
        return super.generateMigrationPlan(migrationPlanGenerationDto);
    }

    @Path("/execute")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Execute Migration Plan", notes = "Operation Execute Migration Plan")
    @Override
    public void executeMigrationPlan(
        @ApiParam("Parameter migrationExecutionDto")
        MigrationExecutionDto migrationExecutionDto) {
        super.executeMigrationPlan(migrationExecutionDto);
    }

    @Path("/executeAsync")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Execute Migration Plan Async", notes = "Operation Execute Migration Plan Async")
    @Override
    public BatchDtoSwagger executeMigrationPlanAsync(
        @ApiParam("Parameter migrationExecutionDto")
        MigrationExecutionDto migrationExecutionDto) {
        return super.executeMigrationPlanAsync(migrationExecutionDto);
    }

    @Path("/validate")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Validate Migration Plan", notes = "Operation Validate Migration Plan")
    @Override
    public MigrationPlanReportDtoSwagger validateMigrationPlan(
        @ApiParam("Parameter migrationPlanDto")
        MigrationPlanDto migrationPlanDto) {
        return super.validateMigrationPlan(migrationPlanDto);
    }
}
