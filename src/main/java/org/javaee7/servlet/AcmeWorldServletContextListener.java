
package org.javaee7.servlet;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.javaee7.reports.AcmeReservationCount;

/**
 *
 * @author Juneau
 */
public class AcmeWorldServletContextListener implements ServletContextListener {

    Future counterHandle = null;
    @Resource(name="concurrent/__defaultManagedScheduledExecutorService")
    ManagedScheduledExecutorService mes;

    /**
     * Recipe 3:  Scheduling a Managed Task
     * @param sce 
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context is initializing...");
        AcmeReservationCount reservationCount = new AcmeReservationCount();
         counterHandle = mes.scheduleAtFixedRate(
            reservationCount, 60, 60, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

         if(counterHandle!=null) {
            counterHandle.cancel(true);
         }
    }
    
}
