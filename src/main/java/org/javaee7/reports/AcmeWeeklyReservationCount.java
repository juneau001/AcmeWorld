package org.javaee7.reports;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.javaee7.session.ParkReservationFacade;

/**
 * Example of a Scheduled Reporter Task
 *
 * @author Juneau
 */
public class AcmeWeeklyReservationCount implements Runnable, Serializable {

    ParkReservationFacade parkReservationFacade = lookupParkReservationFacadeBean();
    String reportName;

    public AcmeWeeklyReservationCount() {
    }

    public void run() {
        runCountReport();
    }

    /**
     * Prints a count of reservations.
     */
    public void runCountReport() {
        System.out.println("Park Reservation Count for the Week");
        System.out.println("===============================");

        Long reservationCount = parkReservationFacade.findWeeklyCount();
        System.out.println(reservationCount);

        // Email in production application
    }

    private ParkReservationFacade lookupParkReservationFacadeBean() {
        try {
            Context c = new InitialContext();
            return (ParkReservationFacade) c.lookup("java:global/AcmeWorld/ParkReservationFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
