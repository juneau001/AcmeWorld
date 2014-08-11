
package org.javaee7.jsf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.javaee7.entity.Restaurant;
import org.javaee7.session.RestaurantFacade;

/**
 *
 * @author Juneau
 */
@Named(value = "restaurantController")
@RequestScoped
public class RestaurantController {
    
    @EJB
    RestaurantFacade ejbFacade;
    
    private Restaurant current;

    /**
     * Creates a new instance of RestaurantController
     */
    public RestaurantController() {
    }
    
    /**
     * Return all restaurants
     * @return 
     */
    public List<Restaurant> findAll(){
        return ejbFacade.findAll();
    }
    
    /**
     * Returns a List of SelectItems
     * @return 
     */
    public List<SelectItem> restaurantList(){
        List<Restaurant> restaurantList = findAll();
        List<SelectItem> selectList = new ArrayList<>();
        for(Restaurant restaurant:restaurantList){
            selectList.add(new SelectItem(restaurant.getId(), restaurant.getName()));
        }
        return selectList;
    }

    /**
     * @return the current
     */
    public Restaurant getCurrent() {
        if(current == null){
            current = new Restaurant();
        }
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(Restaurant current) {
        this.current = current;
    }
    
    public void createRestaurant(){
        // Simple and easy primary key generation...not recommended for production use
        int restaurantCount = ejbFacade.findAll().size();
        current.setId(new BigDecimal(restaurantCount + 1));
        ejbFacade.create(current);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successful Restaurant Creation", "Successful Restaurant Creation"));
        current =null;
    }
    
}
