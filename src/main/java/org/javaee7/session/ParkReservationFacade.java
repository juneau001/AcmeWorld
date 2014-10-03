
package org.javaee7.session;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import org.javaee7.entity.ParkReservation;

/**
 *
 * @author Juneau
 */
@Stateless(mappedName="ejb/ParkReservationFacade")
public class ParkReservationFacade extends AbstractFacade<ParkReservation> {
    @PersistenceContext(unitName = "AcmeWorldPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParkReservationFacade() {
        super(ParkReservation.class);
    }
    
    /**
     * Create a ParkReservationFacade object
     * @param parkReservationFacade 
     */
    public void create(ParkReservationFacade parkReservationFacade){
        em.persist(parkReservationFacade);
    }
    
    /**
     * Update a parkReservationFacade object
     * @param parkReservationFacade 
     */
    public void edit(ParkReservationFacade parkReservationFacade){
        em.merge(parkReservationFacade);
    }
    
    /**
     * Remove a parkReservationFacade object
     * @param parkReservationFacade 
     */
    public void remove(ParkReservationFacade parkReservationFacade){
        em.remove(parkReservationFacade);
    }
    
    public ParkReservation findById(BigDecimal reservationId){
        return (ParkReservation) em.createQuery("select object(o) from ParkReservation o " +
                "where o.id = :reservationId ")
                .setParameter("reservationId", reservationId).getSingleResult();
    }
    
    /**
     * Returns the count of new reservations for the past day
     * @return 
     */
    public long findCount(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return (long) em.createQuery("select count(o) from ParkReservation o " +
                "where o.enterDate > :today")
                .setParameter("today", cal.getTime()).getSingleResult();
    }
    
    /**
     * Returns the count of new reservations for the past week
     * @return 
     */
    public long findWeeklyCount(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        return (long) em.createQuery("select count(o) from ParkReservation o " +
                "where o.enterDate > :week")
                .setParameter("week", cal.getTime()).getSingleResult();
    }

}
