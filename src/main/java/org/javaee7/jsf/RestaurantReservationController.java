/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.javaee7.jsf;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Min;
import org.javaee7.entity.ParkReservation;
import org.javaee7.entity.RestaurantReservation;
import org.javaee7.session.RestaurantReservationFacade;

/**
 *
 * @author Juneau
 */
@Named(value = "restaurantReservationController")
@SessionScoped
public class RestaurantReservationController implements Serializable {

    @EJB
    private RestaurantReservationFacade ejbFacade;
    
    private RestaurantReservation current;
    
    
    
    /**
     * Creates a new instance of RestaurantReservationController
     */
    public RestaurantReservationController() {
    }
    
    public RestaurantReservation getCurrent(){
        if(current == null){
            current = new RestaurantReservation();
        }
        return current;
    }
    
    /**
     * Create restaurant reservation
     * @return 
     */
    public String createReservation(){
        ejbFacade.create(current);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful Reservation", "Successful Reservation"));
        return "index";
    }
    
    /**
     * Returns the count of restaurant reservations per a specified
     * park reservation.
     * 
     * @param parkReservation
     * @return 
     */
    public Long countByParkReservation(ParkReservation parkReservation){
        return ejbFacade.findCountByParkReservation(parkReservation);
    }
    
    /**
     * Returns the number of people for a specified restaurant reservation.
     * 
     * Constraint signifies that there must be at least one person included on the
     * restaurant reservation.
     * @return 
     */
    @Min(value=1)
    private int reservationCount(RestaurantReservation res){
         return res.getNumberOfPeople().intValue();
    }
     
    
}
