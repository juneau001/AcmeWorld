
package org.javaee7.session;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.javaee7.entity.ParkReservation;
import org.javaee7.entity.RestaurantReservation;

/**
 *
 * @author Juneau
 */
@Stateless
public class RestaurantReservationFacade extends AbstractFacade<RestaurantReservation> {
    @PersistenceContext(unitName = "AcmeWorldPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RestaurantReservationFacade() {
        super(RestaurantReservation.class);
    }
    
    /**
     * Create a RestaurantReservationFacade object
     * @param restaurantReservationFacade 
     */
    public void create(RestaurantReservationFacade restaurantReservationFacade){
        em.persist(restaurantReservationFacade);
    }
    
    /**
     * Update a restaurantReservationFacade object
     * @param restaurantReservationFacade 
     */
    public void edit(RestaurantReservationFacade restaurantReservationFacade){
        em.merge(restaurantReservationFacade);
    }
    
    /**
     * Remove a restaurantReservationFacade object
     * @param restaurantReservationFacade 
     */
    public void remove(RestaurantReservationFacade restaurantReservationFacade){
        em.remove(restaurantReservationFacade);
    }
    
    public RestaurantReservation findById(BigDecimal reservationId){
        return (RestaurantReservation) em.createQuery("select object(o) from RestaurantReservation o " +
                "where o.id = :reservationId ")
                .setParameter("reservationId", reservationId).getSingleResult();
    }
    
    /**
     * Returns the number of restaurant reservations per park reservation...must
     * be at least one restaurant reservation!
     * 
     * @param parkReservation
     * @return 
     */
    public Long findCountByParkReservation(ParkReservation parkReservation){
        return (Long) em.createQuery("select count(o) from RestaurantReservation o " +
                "where o.reservationId = :parkReservationId")
                .setParameter("parkReservationId", parkReservation.getId())
                .getSingleResult();
    }
   
    
}
