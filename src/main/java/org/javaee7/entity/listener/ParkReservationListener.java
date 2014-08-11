/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.javaee7.entity.listener;

import javax.annotation.Resource;
import javax.persistence.PrePersist;
import org.javaee7.entity.ParkReservation;

/**
 *
 * @author Juneau
 */
public class ParkReservationListener {
    
    @Resource(name="jndi/AcmeMail")
    javax.mail.Session mailSession;
    
    @PrePersist
    public void prePersist(ParkReservation reservation){
        System.out.println("A reservation is being made for " + reservation.getLastName());
        // use the mail session
    }
    
}
