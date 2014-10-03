package org.javaee7.jsf;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.javaee7.entity.ParkReservation;
import org.javaee7.entity.RestaurantReservation;
import org.javaee7.reports.AcmeParkReservation;
import org.javaee7.reports.AcmeRestaurantReservation;
import org.javaee7.session.ParkReservationFacade;
import org.javaee7.session.RestaurantReservationFacade;

/**
 *
 * @author Juneau
 */
@Named(value = "acmeSummaryEmailController")
@SessionScoped
public class AcmeSummaryEmailController implements java.io.Serializable {

    // Inject without explicitly naming the concurrent/_defaultManagedExecutorService
    // This makes the example portable across multiple Java EE containers
    @Resource
    ManagedExecutorService mes;

    @EJB
    ParkReservationFacade parkReservationFacade;

    @EJB
    RestaurantReservationFacade restaurantReservationFacade;

    private String emailSummaryReportMessage = null;
    private boolean displaySummaryReportMessage = false;
    private Future<ParkReservation> parkReservationFuture;
    private Future<RestaurantReservation> restaurantReservationFuture;

    public AcmeSummaryEmailController() {

    }

    /**
     * Recipe 3
     */
    public void runReservationSummaryReport() {
        String reportId = "001";
        BigDecimal accountID = new BigDecimal("1");
        parkReservationFuture = mes.submit(
                new AcmeParkReservation(reportId, accountID, parkReservationFacade));
        restaurantReservationFuture = mes.submit(
                new AcmeRestaurantReservation(reportId, accountID, restaurantReservationFacade));

    }

    /**
     * @return the emailSummaryReportMessage
     */
    public String getEmailSummaryReportMessage() {
        return emailSummaryReportMessage;
    }

    /**
     * @param emailSummaryReportMessage the emailSummaryReportMessage to set
     */
    public void setEmailSummaryReportMessage(String emailSummaryReportMessage) {
        this.emailSummaryReportMessage = emailSummaryReportMessage;
    }

    /**
     * Polls the Future objects
     */
    public void pollSummaryReport() {
        if (parkReservationFuture != null) {
            if (parkReservationFuture.isDone()) {
                if (restaurantReservationFuture != null) {
                    if (restaurantReservationFuture.isDone()) {
                        // Wait for the results.
                        try {
                            ParkReservation parkReservation = parkReservationFuture.get();
                            RestaurantReservation restaurantReservation = restaurantReservationFuture.get();
                            // Process the results
                            System.out.println("Park Reservation Info: "
                                    + parkReservation.getFirstName() + " " + parkReservation.getLastName());
                            System.out.println("Restaurant Reservation Info: " + restaurantReservation.getReservationDate());
                            setEmailSummaryReportMessage("Email Summary Complete: "
                                    + "Park Reservation Info: "
                                    + parkReservation.getFirstName() + " " + parkReservation.getLastName());
                            displaySummaryReportMessage = true;
                        } catch (InterruptedException | ExecutionException ex) {
                            // Do something nice in production and comment this line out
                            System.out.println("Exception: " + ex);
                            setEmailSummaryReportMessage("Email Summary Report Issue Occurred");
                        }
                        parkReservationFuture = null;
                        restaurantReservationFuture = null;
                        displaySummaryReportMessage = false;
                    }
                }
            }
        } else {
            if (displaySummaryReportMessage) {
                setEmailSummaryReportMessage("Report Still Running...");
            } else {
                setEmailSummaryReportMessage("");
            }
        }

    }

    /**
     * @return the displaySummaryReportMessage
     */
    public boolean isDisplaySummaryReportMessage() {
        return displaySummaryReportMessage;
    }

    /**
     * @param displaySummaryReportMessage the displaySummaryReportMessage to set
     */
    public void setDisplaySummaryReportMessage(boolean displaySummaryReportMessage) {
        this.displaySummaryReportMessage = displaySummaryReportMessage;
    }

}
