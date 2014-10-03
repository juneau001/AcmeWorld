
package org.javaee7.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedExecutors;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.ManagedTaskListener;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.javaee7.alerter.ReservationAlerter;
import org.javaee7.concurrency.SkipSpecifiedTimeTrigger;
import org.javaee7.entity.ParkReservation;
import org.javaee7.interceptor.NewLoggable;
import org.javaee7.jms.MessageReceiver;
import org.javaee7.jms.QueueMessageProducer;
import org.javaee7.listener.CustomManagedTaskListener;
import org.javaee7.reports.AcmeParkReservation;
import org.javaee7.reports.AcmeParkReservationLong;
import org.javaee7.reports.AcmeReservationReport;
import org.javaee7.reports.AcmeWeeklyReservationCount;
import org.javaee7.rss.ConcurrentFeedReader;
import org.javaee7.rss.Feed;
import org.javaee7.session.ParkAdmissionFacade;
import org.javaee7.session.ParkFacade;
import org.javaee7.session.ParkReservationFacade;
import org.javaee7.session.ReservationBookingBean;
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
    @EJB
    private ReservationBookingBean reservationBookingBean;
    
    @Inject
    RestaurantReservationController restaurantReservationController;
    
    @Inject
    QueueMessageProducer messageProducer;
    
    @Inject
    MessageReceiver messageReceiver;
    
    @Resource(name = "concurrent/__defaultManagedExecutorService")
    ManagedExecutorService mes;
    
    @Resource(name="concurrent/__defaultManagedThreadFactory") 
    ManagedThreadFactory threadFactory;
    
    @Resource(name="concurrent/_defaultManagedScheduledExecutorService")
    ManagedScheduledExecutorService msexec;
    
    
    private ParkReservation reservation;
    private String restaurantReservationNotification;
    
    private BigDecimal parkReservationId;
    
    private Future reportFuture = null;
    private List<Future<ParkReservation>> reservations;

    private String reservationReportMessage;
    private String receivedMessage;
    
    private String reservationRestXml;
    
    private String reservationId;
    private String feedUrl;
    private Future feedFuture;
    private Feed foundFeed;
    private String feedMessage;
    
    // Weekly Report Skip Time
    private int schedulerSkipTime = 0;
    
    private Future longRunningParkReservationFuture;
    ManagedTaskListener mtListener;
    private ParkReservation longRunningReservation;
    

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
    
    public void runAndDisplayReservationReport(){
        longRunningParkReservationFuture = null;
        AcmeParkReservationLong res = new AcmeParkReservationLong(reservationId,
                                            new BigDecimal(reservationId), ejbFacade);
        //mtListener = res.getManagedTaskListener();
        Callable parkReservationCallable = ManagedExecutors.managedTask(res, new CustomManagedTaskListener());
        longRunningParkReservationFuture = mes.submit(parkReservationCallable);
        setReservationReportMessage("In Progress");
        reservationId = null;
        
    }

    public void pollLongRunningReservation(){
        if(longRunningParkReservationFuture != null){
            if(longRunningParkReservationFuture.isDone()){
                setReservationReportMessage("Report Complete!");
                try {
                    longRunningReservation = (ParkReservation) longRunningParkReservationFuture.get();
                } catch (InterruptedException|ExecutionException ex) {
                    Logger.getLogger(ParkReservationController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            } else {
                setReservationReportMessage("In Progress...");
            }
        }
        
    }
    
    public void submitFeed(){
        feedFuture = null;
        ConcurrentFeedReader freader = new ConcurrentFeedReader(feedUrl);
      
        feedFuture = mes.submit(freader);
        setFeedMessage("Feed Reading...");
        feedUrl = null;
    }
    
    public void pollFeedRead(){
        if(feedFuture != null){
            if(feedFuture.isDone()){
                setFeedMessage("Feed Read Complete!");
                try {
                    foundFeed = (Feed) feedFuture.get();
                    System.out.println("How many: " + foundFeed.getMessages().size());
                } catch (InterruptedException|ExecutionException ex) {
                    Logger.getLogger(ParkReservationController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            } else {
                setReservationReportMessage("Feed Reading...");
            }
        }
        
    }
    
    /**
     * Create a new reservation
     *
     * @return 
     */
    //@NewLoggable
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
    
    /**
     * Modify a reservation
     * @return 
     */

    public String modifyReservation(){
        if(reservation.getFirstName() != null && reservation.getLastName()!= null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No Reservation made, please try again", "No Reservation made, please try again"));
        } else {
            System.out.println(reservation.getFirstName());
            ejbFacade.edit(reservation);
            // Invoke JMS message sending within our MessageProducer bean
            messageProducer.sendMessageNew(reservation);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successful Reservation Modification", "Successful Reservation Modification"));
        }
        return null;
    }
    
    /**
     * Invoke manual reservation creation
     * @return 
     */
    public String invokeManualReservation(){
        return createReservation(reservation.getFirstName(),
                          reservation.getLastName(),
                          reservation.getNumAdults(),
                          reservation.getNumChild(),
                          reservation.getNumDays(),
                          reservation.getTripStartDate());
    }
    
    /**
     * Manually create a reservation.
     * 
     * @param firstName
     * @param lastName
     * @param numberAdults
     * @param numberChildren
     * @param numberDays
     * @param tripStartDate
     * @return 
     */
    public String createReservation(@NotNull 
                                    String firstName,
                                    @NotNull 
                                    String lastName,
                                    @Min(value=1) 
                                    @Max(value=15) 
                                    int     numberAdults,
                                    @Min(value=0) 
                                    @Max(value=15) 
                                    int     numberChildren,
                                    @Min(value=1)
                                    int     numberDays,
                                    Date    tripStartDate){
        ParkReservation newReservation = new ParkReservation();
        newReservation.setFirstName(firstName);
        newReservation.setLastName(lastName);
        newReservation.setNumAdults(numberAdults);
        newReservation.setNumChild(numberChildren);
        newReservation.setNumDays(numberDays);
        newReservation.setTripStartDate(tripStartDate);
        newReservation.setEnterDate(new Date());
        ejbFacade.create(newReservation);
        return "RESERVATION_CREATED";
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
     * Recipe 6: Implementing a managed thread
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
     * Recipe 1:  viewAction
     * 
     * Loads the currently selected reservation from the currentReservations view, 
     * and displays the detail within the parkReservation view
     * @return 
     */
    public String loadReservation(){
        if (parkReservationId != null){
        reservation = ejbFacade.findById(parkReservationId);
        checkRestaurantReservationPolicy();
        }
        return null;
    }
    
    /**
     * Initiate a new reservation
     * @return 
     */
    public String createNewReservation(){
        reservation = null;
        return null;
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

    /**
     * @return the parkReservation
     */
    public BigDecimal getParkReservationId() {
        return parkReservationId;
    }

    /**
     * @param parkReservation the parkReservation to set
     */
    public void setParkReservationId(BigDecimal parkReservationId) {
        this.parkReservationId = parkReservationId;
    }

    /**
     * @return the restaurantReservationNotification
     */
    public String getRestaurantReservationNotification() {
        return restaurantReservationNotification;
    }

    /**
     * @param restaurantReservationNotification the restaurantReservationNotification to set
     */
    public void setRestaurantReservationNotification(String restaurantReservationNotification) {
        this.restaurantReservationNotification = restaurantReservationNotification;
    }
    
    /**
     * Checks to ensure that the park reservation meets the restaurant reservation policy
     */
    public void checkRestaurantReservationPolicy(){
        Long reservationCount = restaurantReservationController.countByParkReservation(reservation);
        if(reservationCount < 1){
            setRestaurantReservationNotification("<font color='red'>Guest must book at least one restaurant reservation</font>");
        } else {
            setRestaurantReservationNotification("<font color='green'>Minimum restaurant reservation booked.</font>");
        }
    }
    
    /**
     * Navigate to Reservation Details
     * @return 
     */
    public String reservationDetails(){
        
        return "reservationDetail";
    }
    
    public void invokeRestClient(){
        Client client = ClientBuilder.newClient();
       
        WebTarget webTarget =  client.target("http://localhost:8080/AcmeWorld/rest/reservationRest/all");
        Response res = webTarget.request("application/xml").get();
            
        setReservationRestXml(res.readEntity(String.class));
    }

    /**
     * @return the reservationRestXml
     */
    public String getReservationRestXml() {
        return reservationRestXml;
    }

    /**
     * @param reservationRestXml the reservationRestXml to set
     */
    public void setReservationRestXml(String reservationRestXml) {
        this.reservationRestXml = reservationRestXml;
    }
    
    public void scheduleWeeklyReports(){
        System.out.println("Scheduling Weekly Reports");
        AcmeWeeklyReservationCount weekly = new AcmeWeeklyReservationCount();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);
        // Schedule to begin in 5 seconds, and then execute every n seconds
        // thereafter
        msexec.schedule(weekly, new SkipSpecifiedTimeTrigger(cal.getTime(), schedulerSkipTime));
        String message = "Weekly Reports Scheduled for " + schedulerSkipTime + " second intervals";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    message,message));
    }
    
    public void cancelWeekly(){
        msexec.shutdown();
    }

    /**
     * @return the longRunningReservation
     */
    public ParkReservation getLongRunningReservation() {
        return longRunningReservation;
    }

    /**
     * @param longRunningReservation the longRunningReservation to set
     */
    public void setLongRunningReservation(ParkReservation longRunningReservation) {
        this.longRunningReservation = longRunningReservation;
    }

    /**
     * @return the reservationId
     */
    public String getReservationId() {
        return reservationId;
    }

    /**
     * @param reservationId the reservationId to set
     */
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    /**
     * @return the feedUrl
     */
    public String getFeedUrl() {
        return feedUrl;
    }

    /**
     * @param feedUrl the feedUrl to set
     */
    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    /**
     * @return the feedFuture
     */
    public Future getFeedFuture() {
        return feedFuture;
    }

    /**
     * @param feedFuture the feedFuture to set
     */
    public void setFeedFuture(Future feedFuture) {
        this.feedFuture = feedFuture;
    }

    /**
     * @return the foundFeed
     */
    public Feed getFoundFeed() {
        return foundFeed;
    }

    /**
     * @param foundFeed the foundFeed to set
     */
    public void setFoundFeed(Feed foundFeed) {
        this.foundFeed = foundFeed;
    }

    /**
     * @return the feedMessage
     */
    public String getFeedMessage() {
        return feedMessage;
    }

    /**
     * @param feedMessage the feedMessage to set
     */
    public void setFeedMessage(String feedMessage) {
        this.feedMessage = feedMessage;
    }

    /**
     * @return the schedulerSkipTime
     */
    public int getSchedulerSkipTime() {
        return schedulerSkipTime;
    }

    /**
     * @param schedulerSkipTime the schedulerSkipTime to set
     */
    public void setSchedulerSkipTime(int schedulerSkipTime) {
        this.schedulerSkipTime = schedulerSkipTime;
    }

    
}
