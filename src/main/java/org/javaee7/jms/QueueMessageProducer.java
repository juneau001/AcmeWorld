
package org.javaee7.jms;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;
import javax.jms.Session;
import org.javaee7.entity.ParkReservation;

/**
 *
 * @author Juneau
 */
@Named("queueMessageProducer")
@Dependent
public class QueueMessageProducer implements java.io.Serializable {
    
    @Resource(name = "jms/acmeConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/Queue")
    Queue inboundQueue;
    
    /**
     * Classic API
     * @param reservation 
     */
    public void sendMessageOld(ParkReservation reservation){
        try{
            javax.jms.Connection conn = connectionFactory.createConnection();
            try{
                // Create session (transacted, acknowledgeMode)
                Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                javax.jms.MessageProducer messageProducer = session.createProducer(inboundQueue);
                javax.jms.TextMessage message = session.createTextMessage(reservation.getLastName());
                messageProducer.send(message);
                System.out.println("Message sent to queue...");
            } finally {
                conn.close();
            }
        } catch (JMSException ex){
            System.out.println("Exception: " + ex);
        }
    }
    
    /**
     * Simplified API
     * @param reservation
     */
    public void sendMessageNew(ParkReservation reservation) {
        try(JMSContext context = connectionFactory.createContext();){
            context.createProducer().send(inboundQueue, reservation.getLastName());
            System.out.println("Message sent to queue...");
        } catch (JMSRuntimeException jre){
            // Do something
        }

    }
    
    /**
     * Simplified API
     * @param reservation
     */
    public void sendMessageNewDelay(ParkReservation reservation) {
        try(JMSContext context = connectionFactory.createContext();){
            context.createProducer().setDeliveryDelay(1000).send(inboundQueue, reservation.getLastName());
            System.out.println("Message sent to queue...");
        } catch (JMSRuntimeException jre){
            // Do something
        }

    }
    
    
    
    
}
