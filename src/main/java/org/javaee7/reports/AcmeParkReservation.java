
package org.javaee7.reports;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.enterprise.concurrent.ManagedTask;
import javax.enterprise.concurrent.ManagedTaskListener;
import org.javaee7.entity.ParkReservation;
import org.javaee7.session.ParkReservationFacade;

/**
 *
 * @author Juneau
 */
public class AcmeParkReservation implements Callable<ParkReservation>, ManagedTask {
    
    String reportId;
    BigDecimal parkReservationId;
    Map<String, String> executionProperties;
    private ParkReservationFacade parkReservationFacade;

    public AcmeParkReservation(String reportId, BigDecimal parkReservationId, ParkReservationFacade parkReservationFacade){
        this.reportId = reportId;
        this.parkReservationId = parkReservationId;
        this.parkReservationFacade = parkReservationFacade;
       
        executionProperties = new HashMap<>();
        executionProperties.put(ManagedTask.IDENTITY_NAME, getIdentityName());
    }
    
    public String getIdentityName() {
         return "AcmeParkReservation:  reportId=" + reportId + ", parkReservationId=" + parkReservationId;
    }
    
    @Override
    public ParkReservation call() throws Exception {
        // Perform long-running task
        return parkReservationFacade.findById(parkReservationId); 
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
