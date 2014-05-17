
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
public class ParkAdmissionFacade extends AbstractFacade<ParkAdmission> {
    @PersistenceContext(unitName = "AcmeWorldPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParkAdmissionFacade() {
        super(ParkAdmission.class);
    }
    
    /**
     * Create a ParkAdmissionFacade object
     * @param parkAdmissionFacade 
     */
    public void create(ParkAdmissionFacade parkAdmissionFacade){
        em.persist(parkAdmissionFacade);
    }
    
    /**
     * Update a parkAdmissionFacade object
     * @param parkAdmissionFacade 
     */
    public void edit(ParkAdmissionFacade parkAdmissionFacade){
        em.merge(parkAdmissionFacade);
    }
    
    /**
     * Remove a parkAdmissionFacade object
     * @param parkAdmissionFacade 
     */
    public void remove(ParkAdmissionFacade parkAdmissionFacade){
        em.remove(parkAdmissionFacade);
    }
    
     public ParkAdmission findById(BigDecimal parkId){
        return  (ParkAdmission) em.createQuery("select object(o) from ParkAdmission o " +
                "where o.parkId = :parkId ")
                .setParameter("parkId", parkId).getSingleResult();
    }
}
