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
    @Produces({"application/xml"})
    public List<ParkReservation> obtainReservations(){
        return parkReservationFacade.findAll();
    }
}
