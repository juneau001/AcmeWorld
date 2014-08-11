/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.interceptor;

import java.io.Serializable;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.javaee7.jsf.ParkReservationController;

/**
 *
 * @author Juneau
 */
@Priority(Interceptor.Priority.APPLICATION + 20)
public class ReservationModificationLogger implements Serializable {
    
    @Inject
    ParkReservationController parkReservationController;

    @AroundInvoke
    public Object modifyReservation(InvocationContext ctx) throws Exception {
        
            // normally write to a formal log
            System.out.println("Resrvation modification made for #" + parkReservationController.getReservation().getId());
       return ctx.getTarget();
    }

}
