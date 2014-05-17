
package org.javaee7.jms;

import java.util.Enumeration;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;

/**
 *
 * @author Juneau
 */
@Named("messageReceiver")
@Dependent
public class MessageReceiver implements java.io.Serializable {

    // Inject JMSContext if working within application server
    //  - Injection without any parameters utlizes the default application server
    //    connection factory
    @Inject
    JMSContext myContext;
    @Resource(name = "jms/acmeConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/Queue")
    Queue inboundQueue;

    private String messageReceived = null;

    /**
     * Receive message with Classic API
     * @return 
     */
    public String receiveMessageClassic() {
        String messageBody = null;
        try {
            javax.jms.Connection connection = connectionFactory.createConnection();
            try {
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                javax.jms.MessageConsumer messageConsumer = session.createConsumer(inboundQueue);
                javax.jms.TextMessage message = (javax.jms.TextMessage) messageConsumer.receive();
                messageBody = message.getText();
                // Do something with message (email, perform database query, etc.)
            } finally {
                connection.close();
            }
        } catch (JMSException ex) {
            // Handle Exception
        }
        return messageBody;

    }

    /**
     * Receive message with Simplified API
     * @return 
     */
    public String receiveMessageSimplified() {
        String messageBody = null;
        try{
            System.out.println("Receiving messages from queue...");
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(inboundQueue);
            // No need to cast
            messageBody = consumer.receiveBodyNoWait(String.class);
            System.out.println("Message:" + messageBody);
            // Do something with message...
        } catch (JMSRuntimeException ex){
            // Handle Exception
        }
        return messageBody;
    }

    /**
     * Receive message asynchronously in SE Environment
     *  -- For EE 7 Web or EJB application, use MDB
     * @return 
     */
    public String receiveAsync() {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(inboundQueue);
        javax.jms.MessageListener acmeMessageListener = new AcmeMessageListener();
   
        consumer.setMessageListener(acmeMessageListener);

        messageReceived = consumer.receiveBody(String.class);
        return messageReceived;
    }

    /**
     * @return the messageReceived
     */
    public String getMessageReceived() {
        return messageReceived;
    }

    /**
     * @param messageReceived the messageReceived to set
     */
    public void setMessageReceived(String messageReceived) {
        this.messageReceived = messageReceived;
    }

    public void browseMessages() {

        try (Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                QueueBrowser browser = session.createBrowser(inboundQueue);) {

            Enumeration msgs = browser.getEnumeration();

            if (!msgs.hasMoreElements()) {
                System.out.println("No more messages within the queue...");
            } else {
                while (msgs.hasMoreElements()) {
                    Message currMsg = (Message) msgs.nextElement();
                    System.out.println("Message ID: " + currMsg.getJMSMessageID());
                }
            }

        } catch (JMSException ex) {
            System.out.println(ex);
        }
    }

}
