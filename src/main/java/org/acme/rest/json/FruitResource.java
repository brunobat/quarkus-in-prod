package org.acme.rest.json;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class FruitResource {

    private Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public FruitResource() {
        fruits.add(new Fruit("Apple", "Winter fruit"));
        fruits.add(new Fruit("Pineapple", "Tropical fruit"));
    }

    @GET
    @Operation(
            operationId = "ListFruit",
            summary = "List all fruits"
    )
    @APIResponse(
            responseCode = "200",
            description = "List of all fruits",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = Set.class))
    )
    @APIResponse(
            name = "notFound",
            responseCode = "404",
            description = "Fruit List not found"
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error"
    )
    public Response list() {
        return Response.status(OK).entity(fruits).build();
    }

    @POST
    @Operation(
            operationId = "AddFruit",
            summary = "Add a fruit"
    )
    @APIResponse(
            responseCode = "201",
            description = "Fruit created",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = Fruit.class, ref = "error"))
    )
    @APIResponse(
            name = "notFound",
            responseCode = "400",
            description = "Fruit data is invalid"
    )
    @APIResponse(
            name = "notFound",
            responseCode = "404",
            description = "Fruit provision not found"
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error"
    )
    public Response add(@Valid Fruit fruit) {
        fruits.add(fruit);
        return Response.status(CREATED).entity(fruit).build();
    }

    @DELETE
    @Operation(
            operationId = "DeleteFruit",
            summary = "Delete a fruit"
    )
    @APIResponse(
            responseCode = "204",
            description = "Empty response"
    )
    @APIResponse(
            name = "notFound",
            responseCode = "404",
            description = "Fruit not found"
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error"
    )
    public Response delete(Fruit fruit) {
        if (!fruits.contains(fruit)) {
            return Response.status(NOT_FOUND).build();
        }
        fruits.remove(fruit);
        return Response.status(NO_CONTENT).build();
    }
}
