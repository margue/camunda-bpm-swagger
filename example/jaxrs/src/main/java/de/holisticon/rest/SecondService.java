package de.holisticon.rest;

import de.holisticon.rest.sub.ExampleResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.UriInfo;

@Path("/second")
@Api(tags = "second")
@Consumes("application/json")
public interface SecondService {

  @Path("")
  @ApiOperation(value = "Retrieves example resource")
  @GET
  ExampleResource getExampleResource();

}
