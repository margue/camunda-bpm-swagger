package org.camunda.bpm.engine.rest.swagger;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Generated;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import org.camunda.bpm.engine.rest.dto.runtime.FilterDto;
import org.camunda.bpm.engine.rest.impl.FilterRestServiceImpl;
import org.camunda.bpm.engine.rest.sub.runtime.FilterResource;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@Path("/filter")
@Api(value = "Filter Service", tags = "Filter")
public class FilterRestServiceSwagger
    extends FilterRestServiceImpl
{

    public FilterRestServiceSwagger(String string, ObjectMapper objectMapper) {
        super(string, objectMapper);
    }

    @Path("")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Filters", notes = "Operation Get Filters")
    @Override
    public List<FilterDto> getFilters(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo,
        @QueryParam("itemCount")
        @ApiParam("Parameter itemCount")
        Boolean itemCount,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return super.getFilters(uriInfo, itemCount, firstResult, maxResults);
    }

    @Path("/create")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Create Filter", notes = "Operation Create Filter")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.FilterDtoSwagger createFilter(
        @ApiParam("Parameter filterDto")
        FilterDto filterDto) {
        return super.createFilter(filterDto);
    }

    @Path("")
    @Produces("application/json")
    @ApiOperation(value = "Available Operations", notes = "Operation Available Operations")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.ResourceOptionsDtoSwagger availableOperations(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.availableOperations(uriInfo);
    }

    @Path("/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Filters Count", notes = "Operation Get Filters Count")
    @Override
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getFiltersCount(
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return super.getFiltersCount(uriInfo);
    }

    @Path("/{id}")
    @ApiOperation(value = "Get Filter", notes = "Operation Get Filter", response = org.camunda.bpm.engine.rest.dto.runtime.FilterDto.class)
    @Override
    public FilterResource getFilter(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return super.getFilter(id);
    }

    @Path("/{id}/list")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query List", notes = "Operation Query List")
    public Object getFilterQueryList(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter request")
        Request request,
        @ApiParam("Parameter string")
        String string,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return getFilter(id).queryList(request, string, firstResult, maxResults);
    }

    @Path("/{id}/singleResult")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Execute Single Result", notes = "Operation Execute Single Result")
    public Object getFilterExecuteSingleResult(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter request")
        Request request) {
        return getFilter(id).executeSingleResult(request);
    }

    @Path("/{id}/count")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Execute Count", notes = "Operation Execute Count")
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getFilterExecuteCount(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        return getFilter(id).executeCount();
    }

    @Path("/{id}")
    @Produces("application/json")
    @ApiOperation(value = "Available Operations", notes = "Operation Available Operations")
    public org.camunda.bpm.engine.rest.dto.swagger.ResourceOptionsDtoSwagger getFilterAvailableOperations(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter uriInfo")
        UriInfo uriInfo) {
        return getFilter(id).availableOperations(uriInfo);
    }

    @Path("/{id}/count")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Count", notes = "Operation Query Count")
    public org.camunda.bpm.engine.rest.dto.swagger.CountResultDtoSwagger getFilterQueryCount(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter string")
        String string) {
        return getFilter(id).queryCount(string);
    }

    @Path("/{id}/list")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Execute List", notes = "Operation Execute List")
    public Object getFilterExecuteList(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter request")
        Request request,
        @QueryParam("firstResult")
        @ApiParam("Parameter firstResult")
        Integer firstResult,
        @QueryParam("maxResults")
        @ApiParam("Parameter maxResults")
        Integer maxResults) {
        return getFilter(id).executeList(request, firstResult, maxResults);
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @ApiOperation(value = "Update Filter", notes = "Operation Update Filter")
    public void getFilterUpdateFilter(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter filterDto")
        FilterDto filterDto) {
        getFilter(id).updateFilter(filterDto);
    }

    @Path("/{id}")
    @DELETE
    @ApiOperation(value = "Delete Filter", notes = "Operation Delete Filter")
    public void getFilterDeleteFilter(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id) {
        getFilter(id).deleteFilter();
    }

    @Path("/{id}/singleResult")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @ApiOperation(value = "Query Single Result", notes = "Operation Query Single Result")
    public Object getFilterQuerySingleResult(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @ApiParam("Parameter request")
        Request request,
        @ApiParam("Parameter string")
        String string) {
        return getFilter(id).querySingleResult(request, string);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get Filter", notes = "Operation Get Filter")
    public org.camunda.bpm.engine.rest.dto.swagger.FilterDtoSwagger getFilterGetFilter(
        @PathParam("id")
        @ApiParam("Parameter id")
        String id,
        @QueryParam("itemCount")
        @ApiParam("Parameter itemCount")
        Boolean itemCount) {
        return getFilter(id).getFilter(itemCount);
    }
}
