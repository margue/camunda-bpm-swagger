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
import org.camunda.bpm.engine.rest.dto.ModificationDto;
import org.camunda.bpm.engine.rest.dto.swagger.BatchDtoSwagger;
import org.camunda.bpm.engine.rest.impl.ModificationRestServiceImpl;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/modification")
@Api(value = "Modification Service", tags = "Modification")
public class ModificationRestServiceSwagger
    extends ModificationRestServiceImpl
{

    public ModificationRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("/executeAsync")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Execute Modification Async", notes = "Operation Execute Modification Async")
    @Override
    public BatchDtoSwagger executeModificationAsync(
        @ApiParam("Parameter modificationDto")
        ModificationDto modificationDto) {
        return super.executeModificationAsync(modificationDto);
    }

    @Path("/execute")
    @POST
    @Consumes("application/json")
    @ApiOperation(value = "Execute Modification", notes = "Operation Execute Modification")
    @Override
    public void executeModification(
        @ApiParam("Parameter modificationDto")
        ModificationDto modificationDto) {
        super.executeModification(modificationDto);
    }
}
