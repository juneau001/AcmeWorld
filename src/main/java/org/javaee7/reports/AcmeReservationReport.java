package org.javaee7.reports;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.javaee7.entity.ParkAdmission;
import org.javaee7.entity.ParkReservation;
import org.javaee7.entity.ReservationSchedule;
import org.javaee7.session.ParkAdmissionFacade;
import org.javaee7.session.ParkFacade;
import org.javaee7.session.ParkReservationFacade;
import org.javaee7.session.ReservationScheduleFacade;

/**
 * Example of a Reporter Task
 *
 * @author Juneau
 */
public class AcmeReservationReport implements Runnable, Serializable {

    String reportName;

    private ParkReservationFacade parkReservationFacade;
    private ReservationScheduleFacade reservationScheduleFacade;
    private ParkAdmissionFacade parkAdmissionFacade;
    private ParkFacade parkFacade;


    public AcmeReservationReport(String report,
                                 ParkReservationFacade parkReservationFacade,
                                 ReservationScheduleFacade reservationScheduleFacade,
                                 ParkAdmissionFacade parkAdmissionFacade,
                                 ParkFacade parkFacade) {
        this.reportName = report;
        this.parkReservationFacade = parkReservationFacade;
        this.reservationScheduleFacade = reservationScheduleFacade;
        this.parkAdmissionFacade = parkAdmissionFacade;
        this.parkFacade = parkFacade;
    }

    public void run() {
        // Run the named report
        if ("reservationReport".equals(reportName)) {
            runReservationReport();
        } 
    }

    /**
     * Prints a list of reservations to the system log.
     */
    public void runReservationReport() {
        System.out.println("Park Reservation Report");
        System.out.println("=======================");
        System.out.println("park res facade:" + parkReservationFacade);
        List<ParkReservation> reservationList = parkReservationFacade.findAll();
        List<ReservationSchedule> schedule = null;
        System.out.println("Reservation List Number: " + reservationList.size());
         
        
        for (ParkReservation reservation : reservationList) {
            BigDecimal totalCost = BigDecimal.ZERO;
            schedule = reservationScheduleFacade.findById(reservation.getId());
            for(ReservationSchedule scheduleDate:schedule){
                BigDecimal totalAdult = new BigDecimal(reservation.getNumAdults());
                BigDecimal totalAdultChg;
                BigDecimal totalChild = new BigDecimal(reservation.getNumChild());
                BigDecimal totalChildChg;
                ParkAdmission admission = parkAdmissionFacade.findById(scheduleDate.getParkId());
                totalAdultChg = admission.getAdultAdmission().multiply(totalAdult);
                totalChildChg = admission.getChildAdmission().multiply(totalChild);
                totalCost = totalCost.add(totalAdultChg.add(totalChildChg));
            }

            System.out.println(reservation.getFirstName() + " " + reservation.getLastName());
            System.out.println("Adults: " + reservation.getNumAdults() + " Children: " + reservation.getNumChild() + " Days: " + reservation.getNumDays());
            System.out.println("Total Charges: " + totalCost);
            System.out.println(" ----- ");
            
        }
        // Email in production application
    }


}
