/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.jsf;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import org.javaee7.entity.ParkReservation;
import org.javaee7.session.ParkReservationFacade;

/**
 *
 * @author Juneau
 */
@Path("/reservationRest")
public class ReservationRest {

    @EJB
    ParkReservationFacade parkReservationFacade;

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("all")
    public List<ParkReservation> obtainReservations() {
        return parkReservationFacade.findAll();
    }

    @GET
    @Path("allAsync")
    public void getAllReservationsAsync(@Suspended final AsyncResponse async) {
        // This is for example purposes only...recreating long-running process
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
        }
        // Long running process
        List<ParkReservation> result = parkReservationFacade.findAll();
        Response response = Response.ok(result).build();
        async.resume(response);

    }

}
