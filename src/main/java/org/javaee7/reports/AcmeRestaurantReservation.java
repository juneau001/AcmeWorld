
package org.javaee7.reports;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.enterprise.concurrent.ManagedTask;
import javax.enterprise.concurrent.ManagedTaskListener;
import org.javaee7.entity.RestaurantReservation;
import org.javaee7.session.RestaurantReservationFacade;

/**
 *
 * @author Juneau
 */
public class AcmeRestaurantReservation implements Callable<RestaurantReservation>, ManagedTask {
    String reportId;
    BigDecimal restaruantReservationId;
    Map<String, String> executionProperties;
    private RestaurantReservationFacade restaurantReservationFacade;

    public AcmeRestaurantReservation(String reportId, BigDecimal restaruantReservationId,
            RestaurantReservationFacade restaurantReservationFacade){
        this.reportId = reportId;
        this.restaruantReservationId = restaruantReservationId;
        this.restaurantReservationFacade = restaurantReservationFacade;
       
        executionProperties = new HashMap<>();
        executionProperties.put(ManagedTask.IDENTITY_NAME, getIdentityName());
    }
    
    public String getIdentityName() {
         return "RestaurantReservation:  reportId=" + reportId + ", restaruantReservationId=" + restaruantReservationId;
    }
    
    @Override
    public RestaurantReservation call() throws Exception {
        // Perform long-running task
        return restaurantReservationFacade.findById(restaruantReservationId); 
    }

    @Override
    public ManagedTaskListener getManagedTaskListener() {
        return null;
    }

    @Override
    public Map<String, String> getExecutionProperties() {
        return executionProperties;
    }
}
