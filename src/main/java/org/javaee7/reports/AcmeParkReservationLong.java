
package org.javaee7.reports;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedTask;
import javax.enterprise.concurrent.ManagedTaskListener;
import org.javaee7.entity.ParkReservation;
import org.javaee7.session.ParkReservationFacade;

/**
 *
 * @author Juneau
 */
public class AcmeParkReservationLong implements Callable<ParkReservation> {
    
    String reportId;
    BigDecimal parkReservationId;
    Map<String, String> executionProperties;
    private ParkReservationFacade parkReservationFacade;

    public AcmeParkReservationLong(String reportId, BigDecimal parkReservationId, ParkReservationFacade parkReservationFacade){
        this.reportId = reportId;
        this.parkReservationId = parkReservationId;
        this.parkReservationFacade = parkReservationFacade;
       
        executionProperties = new HashMap<>();
        executionProperties.put(ManagedTask.IDENTITY_NAME, getIdentityName());
    }
    
    public String getIdentityName() {
         return "AcmeParkReservationLong:  reportId=" + reportId + ", parkReservationId=" + parkReservationId;
    }
    
    @Override
    public ParkReservation call() throws Exception {
        // Perform long-running task
        int y = 1;
        for (int x = 0; x <= 1000000; x++){
            y = y + x;
        }
        return parkReservationFacade.findById(parkReservationId); 
    }

//    @Override
//    public ManagedTaskListener getManagedTaskListener() {
//        return null;
//    }
//
//    @Override
//    public Map<String, String> getExecutionProperties() {
//        return executionProperties;
//    }
//    
}
