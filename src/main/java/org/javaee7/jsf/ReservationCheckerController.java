package org.javaee7.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.javaee7.entity.ParkReservation;
import org.javaee7.session.NotificationTimer;
import org.javaee7.session.ReservationChecker;

/**
 *
 * @author Juneau
 */
@Named(value = "reservationCheckerController")
@SessionScoped
public class ReservationCheckerController implements Serializable {

    @EJB
    ReservationChecker ejbFacade;

    @EJB
    NotificationTimer notificationTimer;

    private List<ParkReservation> reservationList;

    private String reservationListMessage;

    public ReservationCheckerController() {

    }

    public void obtainReservations() {

        BigDecimal[] reservations = {BigDecimal.valueOf(Long.valueOf("1")),
            BigDecimal.valueOf(Long.valueOf("2"))};
        System.out.println("Invoking Managed Tasks");

        ejbFacade.getReservations(reservations);

        setReservationListMessage("The reservation report has been initated");

    }

    /**
     * @return the reservationList
     */
    public List<ParkReservation> getReservationList() {
        return reservationList;
    }

    /**
     * @param reservationList the reservationList to set
     */
    public void setReservationList(List<ParkReservation> reservationList) {
        this.reservationList = reservationList;
    }

    /**
     * @return the reservationListMessage
     */
    public String getReservationListMessage() {
        return reservationListMessage;
    }

    /**
     * @param reservationListMessage the reservationListMessage to set
     */
    public void setReservationListMessage(String reservationListMessage) {
        this.reservationListMessage = reservationListMessage;
    }

    
    /**
     * Return all timer data
     * @return 
     */
    public Collection<Timer> obtainActiveTimers() {
        return notificationTimer.obtainActiveTimers();
    }
}
