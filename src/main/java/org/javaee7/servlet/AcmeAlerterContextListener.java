
package org.javaee7.servlet;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedThreadFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.javaee7.alerter.ReservationAlerter;

/**
 *
 * @author Juneau
 */
public class AcmeAlerterContextListener implements ServletContextListener {
    Thread alerterThread = null;
    @Resource(name="concurrent/__defaultManagedThreadFactory") 
    ManagedThreadFactory threadFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing context and creating thread....");
        
        alerterThread = threadFactory.newThread(new ReservationAlerter());
        alerterThread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(alerterThread!=null){
            alerterThread.interrupt();
        }
    }
    
    
}
