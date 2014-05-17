
package org.javaee7.jsf;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.javaee7.alerter.ReservationAlerter;
import org.javaee7.entity.ParkReservation;
import org.javaee7.jms.MessageReceiver;
import org.javaee7.jms.QueueMessageProducer;
import org.javaee7.reports.AcmeParkReservation;
import org.javaee7.reports.AcmeReservationReport;
import org.javaee7.session.ParkAdmissionFacade;
import org.javaee7.session.ParkFacade;
import org.javaee7.session.ParkReservationFacade;
import org.javaee7.session.ReservationScheduleFacade;

/**
 *
 * @author Juneau
 */
@Named(value = "parkReservationController")
@SessionScoped
public class ParkReservationController implements Serializable {

    @EJB
    ParkReservationFacade ejbFacade;
    @EJB
    private ReservationScheduleFacade reservationScheduleFacade;
    @EJB
    private ParkAdmissionFacade   parkAdmissionFacade;
    @EJB
    private ParkFacade parkFacade;
    
    @Inject
    QueueMessageProducer messageProducer;
    
    @Inject
    MessageReceiver messageReceiver;
    
    @Resource(name = "concurrent/__defaultManagedExecutorService")
    ManagedExecutorService mes;
    
    @Resource(name="concurrent/__defaultManagedThreadFactory") 
    ManagedThreadFactory threadFactory;
    
    
    private ParkReservation reservation;
    
    private Future reportFuture = null;
    private List<Future<ParkReservation>> reservations;

    private String reservationReportMessage;
    private String receivedMessage;
    

    /**
     * Creates a new instance of ParkReservationController
     */
    public ParkReservationController() {
    }

    public List<ParkReservation> currentReservations() {
        return ejbFacade.findAll();
    }

    /**
     * Recipe 1:  Executing a Managed Task
     */
    public void runReservationReport() {
        /*
         * Typically, the Future object should be cached somewhere and then
         * polled periodically to retrieve status of the task
         */
        AcmeReservationReport areport = new AcmeReservationReport("reservationReport",
                                            ejbFacade,
                                            reservationScheduleFacade,
                                            parkAdmissionFacade,
                                            parkFacade);
        reportFuture = mes.submit(areport);
        
        setReservationReportMessage("Report running...please check server log");

    }
    
    public void pollReport(){
        if(reportFuture != null){
            if(reportFuture.isDone()){
                setReservationReportMessage("Report Complete!");
            } else {
                setReservationReportMessage("Report Still Running...");
            }
        }
        
    }
    
    /**
     * Recipe 2:  Executing a Collection of tasks
     */
    // Run many report tasks by passing a Collection of tasks to invokeAll
    public void runManyReservationReports(){
        Collection<Callable<ParkReservation>> parkReportList = new ArrayList<>();
        for(int x = 0; x <= 3; x++){
            AcmeParkReservation res = new AcmeParkReservation(String.valueOf(x+1),
                                            new BigDecimal(x+1), ejbFacade);
            parkReportList.add(res);
        }
        
        try{
            System.out.println("Invoking reports...");
            // Return a List of Future objects
            reservations = mes.invokeAll(parkReportList);
            
            // Process each Future
            for(Future reservation:reservations){
                ParkReservation parkRes = (ParkReservation) reservation.get();
                //perform processing
                System.out.println("Processing Report: " + parkRes.getFirstName() + " " + parkRes.getLastName());
            }
            System.out.println("Finished Processing...");
        } catch (InterruptedException|ExecutionException ex){
            System.out.println("runManyReservationReports Error: " + ex);
        }
        
    }

    // Return a single report by passing a Collection of tasks to invokeAny
    public void runAnyReservationReport(){
        Collection<Callable<ParkReservation>> parkReportList = new ArrayList<>();
        for(int x = 0; x <= 3; x++){
            AcmeParkReservation res = new AcmeParkReservation(String.valueOf(x+1),
                                            new BigDecimal(x+1), ejbFacade);
            parkReportList.add(res);
        }
        
        try{
            System.out.println("Invoking the reports...");
            ParkReservation singleRes = mes.invokeAny(parkReportList);
            System.out.println("Returned the report: " + singleRes.getId());
            
        } catch (InterruptedException|ExecutionException ex){
            System.out.println("runManyReservationReports Error: " + ex);
        }
        
    }

    /**
     * Create a new reservation
     *
     */
    public String createReservation(){
        if(reservation == null && reservation.getLastName()!= null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No Reservation made, please try again", "No Reservation made, please try again"));
        } else {
            System.out.println(reservation.getFirstName());
            reservation.setId(BigDecimal.valueOf(getReservationCount()).add(BigDecimal.ONE));
            reservation.setEnterDate(new Date());
            ejbFacade.create(reservation);
            // Invoke JMS message sending within our MessageProducer bean
            messageProducer.sendMessageNew(reservation);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successful Reservation Creation", "Successful Reservation Creation"));
        }
        return null;
    }
    
    public void receiveMessages(){
        
        String message = messageReceiver.receiveMessageSimplified();
        if(message != null){
            setReceivedMessage("Received Message: " + message);
        } else {
            setReceivedMessage("No messages to be received");
        }
    }
    
    public void receiveMessagesAsync(){
        
        String message = messageReceiver.receiveAsync();
        if(message != null){
            setReceivedMessage("Received Async Message: " + message);
        } else {
            setReceivedMessage("No messages to be received");
        }
    }
    
    /**
     * Recipe 4: Implementing a managed thread
     */
    public void initiateParkReservationAlerter(){   
        reservationReportMessage = "Starting alerter thread...";
        Thread alerterThread = threadFactory.newThread(new ReservationAlerter());
        System.out.println(alerterThread);
        alerterThread.start();
        reservationReportMessage = "Check server log for output...";
    }
    
    /**
     * @return the reservationReportMessage
     */
    public String getReservationReportMessage() {
        return reservationReportMessage;
    }

    /**
     * @param reservationReportMessage the reservationReportMessage to set
     */
    public void setReservationReportMessage(String reservationReportMessage) {
        this.reservationReportMessage = reservationReportMessage;
    }
    
    public int getReservationCount(){
        return ejbFacade.findAll().size();
    }

    /**
     * @return the reservation
     */
    public ParkReservation getReservation() {
        if(reservation == null){
            reservation = new ParkReservation();
        }
        return reservation;
    }

    /**
     * @param reservation the reservation to set
     */
    public void setReservation(ParkReservation reservation) {
        this.reservation = reservation;
    }

    /**
     * @return the receivedMessage
     */
    public String getReceivedMessage() {
        return receivedMessage;
    }

    /**
     * @param receivedMessage the receivedMessage to set
     */
    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    
}
