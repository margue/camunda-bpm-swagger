package de.holisticon.rest.sub;

import de.holisticon.rest.dto.ExampleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;

@Api(tags = "service", hidden = true)
public interface ExampleResource {

  @PUT
  @ApiOperation("update")
  @Path("")
  void update(ExampleDto dto);

  @ApiOperation("create")
  @POST
  @Path("")
  void create(ExampleDto dto);

  @ApiOperation("delete")
  @DELETE
  @Path("")
  void delete();

  @ApiOperation("all")
  @Path("")
  List<ExampleDto> all();
}
