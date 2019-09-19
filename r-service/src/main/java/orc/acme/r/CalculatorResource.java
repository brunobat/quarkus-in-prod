package orc.acme.r;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/calculator")
@Produces(TEXT_PLAIN)
@Consumes(APPLICATION_FORM_URLENCODED)
public class CalculatorResource {
    @POST
    @Path("/add")
    public Response add(@FormParam("x") final double x, @FormParam("y") final double y) {
        return Response.ok(CalculatorR.ADD.execute(x, y)).build();
    }

    @POST
    @Path("/subtract")
    public Response subtract(@FormParam("x") final double x, @FormParam("y") final double y) {
        return Response.ok(CalculatorR.SUBSTRACT.execute(x, y)).build();
    }

    @POST
    @Path("/multiply")
    public Response multiply(@FormParam("x") final double x, @FormParam("y") final double y) {
        return Response.ok(CalculatorR.MULTIPLY.execute(x, y)).build();
    }

    @POST
    @Path("/divide")
    public Response divide(@FormParam("x") final double x, @FormParam("y") final double y) {
        return Response.ok(CalculatorR.DIVIDE.execute(x, y)).build();
    }
}
