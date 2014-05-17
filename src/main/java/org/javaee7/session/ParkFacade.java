
package org.javaee7.session;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.javaee7.entity.Park;

/**
 *
 * @author Juneau
 */
@Stateless
public class ParkFacade extends AbstractFacade<Park> {
    @PersistenceContext(unitName = "AcmeWorldPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParkFacade() {
        super(Park.class);
    }
    
    /**
     * Create a ParkFacade object
     * @param parkFacade 
     */
    public void create(ParkFacade parkFacade){
        em.persist(parkFacade);
    }
    
    /**
     * Update a parkFacade object
     * @param parkFacade 
     */
    public void edit(ParkFacade parkFacade){
        em.merge(parkFacade);
    }
    
    /**
     * Remove a parkFacade object
     * @param parkFacade 
     */
    public void remove(ParkFacade parkFacade){
        em.remove(parkFacade);
    }
    
    public Park findById(BigDecimal parkId){
        return (Park) em.createQuery("select object(o) from Park o " +
                "where o.id = :parkId ")
                .setParameter("parkId", parkId).getSingleResult();
    }
     
    
}
