/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.session;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.javaee7.entity.TripReservationDetail;

/**
 *
 * @author Juneau
 */
@Stateful
public class ReservationBookingBean {

    @PersistenceContext(unitName = "AcmeWorldPU")
    private EntityManager em;

    private List<TripReservationDetail> bookingDetail;

    /**
     *
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @PostConstruct
    public void initialize() {
        System.out.println("Reservation booking begins...");
        bookingDetail = new ArrayList();
    }

    public void addBookingDetail(TripReservationDetail detail) {
        bookingDetail.add(detail);
    }

    /**
     * Persist the trip details
     */
    public void persistTripDetails() {
        for (TripReservationDetail tripDetail : bookingDetail) {
            em.persist(tripDetail);
        }
    }

    /**
     *
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @PreDestroy
    public void endSession() {
        System.out.println("Reservation booking ends...");
        em.flush();
    }
}
