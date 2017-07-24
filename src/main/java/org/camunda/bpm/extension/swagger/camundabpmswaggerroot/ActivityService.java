package org.camunda.bpm.extension.swagger.camundabpmswaggerroot;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "activity")
@Service
@Path("api/activity")
@Produces({ MediaType.APPLICATION_JSON })
public class ActivityService {


  @ApiOperation(value = "Fetch logged-in user's activity", httpMethod = "GET", response = Response.class)
  @GET
  @Path("/mine")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public String listMyActivities() {
    return "foo";
  }
}
