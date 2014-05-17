
package org.javaee7.session;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.javaee7.entity.ParkAdmission;
import org.javaee7.entity.ReservationSchedule;

/**
 *
 * @author Juneau
 */

@Stateless
public class ReservationScheduleFacade extends AbstractFacade<ReservationSchedule> {
    @PersistenceContext(unitName = "AcmeWorldPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationScheduleFacade() {
        super(ReservationSchedule.class);
    }
    
    /**
     * Create a ReservationScheduleFacade object
     * @param reservationScheduleFacade 
     */
    public void create(ReservationScheduleFacade reservationScheduleFacade){
        em.persist(reservationScheduleFacade);
    }
    
    /**
     * Update a reservationScheduleFacade object
     * @param reservationScheduleFacade 
     */
    public void edit(ReservationScheduleFacade reservationScheduleFacade){
        em.merge(reservationScheduleFacade);
    }
    
    /**
     * Remove a reservationScheduleFacade object
     * @param reservationScheduleFacade 
     */
    public void remove(ReservationScheduleFacade reservationScheduleFacade){
        em.remove(reservationScheduleFacade);
    }
    
     public List<ReservationSchedule> findById(BigDecimal reservationId){
        return  em.createQuery("select object(o) from ReservationSchedule o " +
                "where o.reservationId = :reservationId ")
                .setParameter("reservationId", reservationId).getResultList();
    }
    
}
