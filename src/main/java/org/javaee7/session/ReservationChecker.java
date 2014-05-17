package org.javaee7.session;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ContextService;
import org.javaee7.entity.ParkReservation;
import org.javaee7.reports.AcmeSingleReservation;

/**
 *
 * @author Juneau
 */
@Stateless
public class ReservationChecker {

    @Resource(name = "concurrent/__defaultContextService")
    ContextService ctxSvc;
    @EJB
    private ExecutorAccessor executorAccessor;

    public ParkReservation[] getReservations(BigDecimal[] reservationId) {
        int idx = 0;

        ExecutorCompletionService<ParkReservation> threadPool =
                new ExecutorCompletionService<>(
                executorAccessor.getThreadPool());
        
        for (idx = 0; idx <= reservationId.length - 1; idx++) {
            Callable<ParkReservation> reservation =  ctxSvc.createContextualProxy(
                    new AcmeSingleReservation(reservationId[idx]), 
                    Callable.class);
            threadPool.submit(reservation);
        }

        ParkReservation[] reservations = new ParkReservation[reservationId.length];
        Future<ParkReservation> futureRes = threadPool.poll();

        System.out.println("Finished...");
        for (idx = 0; idx <= reservationId.length - 1; idx++) {
            try {
                System.out.println("getting reservations");
                reservations[idx] = threadPool.take().get();
                System.out.println("Reservation: " + reservations[idx].getLastName() +
                        " " + reservations[idx].getFirstName());

            } catch (InterruptedException e) {
                System.out.println("InterruptedException: " + e);
            } catch (ExecutionException e) {
                System.out.println("ExecutionException: " + e);
            }
        
        }
        return reservations;
    }
}