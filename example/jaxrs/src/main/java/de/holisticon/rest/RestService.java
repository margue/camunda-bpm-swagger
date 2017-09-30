package de.holisticon.rest;

import de.holisticon.rest.sub.ExampleResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.UriInfo;

@Path("/service")
@Api(tags = "service")
@Consumes("application/json")
public interface RestService {

  @Path("/{id}")
  @ApiOperation(value = "Retrieves example resource")
  @GET
  ExampleResource getExampleResource(@PathParam("id") String id);

  @GET
  @Path("count")
  Integer countExampleResources();

  @POST
  void createNewResource(@QueryParam("overwrite") Boolean overwrite, UriInfo uriInfo);
}
