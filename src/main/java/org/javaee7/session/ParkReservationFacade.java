
package org.javaee7.session;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
}
