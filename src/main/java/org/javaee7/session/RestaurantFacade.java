
package org.javaee7.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.javaee7.entity.Restaurant;

/**
 *
 * @author Juneau
 */

@Stateless
public class RestaurantFacade extends AbstractFacade<Restaurant> {
    @PersistenceContext(unitName = "AcmeWorldPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RestaurantFacade() {
        super(Restaurant.class);
    }
    
    /**
     * Create a RestaurantFacade object
     * @param restaurantFacade 
     */
    public void create(RestaurantFacade restaurantFacade){
        em.persist(restaurantFacade);
    }
    
    /**
     * Update a restaurantFacade object
     * @param restaurantFacade 
     */
    public void edit(RestaurantFacade restaurantFacade){
        em.merge(restaurantFacade);
    }
    
    /**
     * Remove a restaurantFacade object
     * @param restaurantFacade 
     */
    public void remove(RestaurantFacade restaurantFacade){
        em.remove(restaurantFacade);
    }
    
    
}
