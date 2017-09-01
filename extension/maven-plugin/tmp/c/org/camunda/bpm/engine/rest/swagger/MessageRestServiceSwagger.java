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
import javax.ws.rs.core.Response;
import org.camunda.bpm.engine.rest.dto.message.CorrelationMessageDto;
import org.camunda.bpm.engine.rest.impl.MessageRestServiceImpl;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/message")
@Api(value = "Message Service", tags = "Message")
public class MessageRestServiceSwagger
    extends MessageRestServiceImpl
{

    public MessageRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Deliver Message", notes = "Operation Deliver Message")
    @Override
    public Response deliverMessage(
        @ApiParam("Parameter correlationMessageDto")
        CorrelationMessageDto correlationMessageDto) {
        return super.deliverMessage(correlationMessageDto);
    }
}
