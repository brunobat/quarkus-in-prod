package org.acme.rest.json;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/legumes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class LegumeResource {

    @Inject
    private EntityManager manager;

    @POST
    @Transactional
    @Operation(
            operationId = "ProvisionLegumes",
            summary = "Add default legumes to the Database"
    )
    @APIResponse(
            responseCode = "201",
            description = "Default legumes created"
    )
    @APIResponse(
            name = "notFound",
            responseCode = "404",
            description = "Legume provision not found"
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error"
    )
    public Response provision() {
        final Legume carrot = new Legume("Carrot", "Root vegetable, usually orange");
        final Legume zucchini = new Legume("Zucchini", "Summer squash");
        manager.merge(carrot);
        manager.merge(zucchini);
        return Response.status(CREATED).build();
    }

    @Operation(
            operationId = "ListLegumes",
            summary = "List all legumes"
    )
    @APIResponse(
            responseCode = "200",
            description = "The List with all legumes"
    )
    @APIResponse(
            name = "notFound",
            responseCode = "404",
            description = "Legume list not found"
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error"
    )
    @GET
    @Fallback(fallbackMethod = "fallback")
    @Timeout(500)
    public List<Legume> list() {
        final List resultList = manager.createQuery("SELECT l FROM Legume l").getResultList();
        return resultList;
    }

    /**
     * To be used in case of exception or timeout
     *
     * @return a list of alternative legumes.
     */
    public List<Legume> fallback() {
        return Arrays.asList(new Legume("Failed Legume", "Fallback answer due to timeout"));
    }
}
