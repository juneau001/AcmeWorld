
package org.javaee7.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

/**
 *
 * @author Juneau
 */
@Named
@SessionScoped
public class SharedConsumerExamples implements java.io.Serializable {
    
    // Inject JMSContext if working within application server
    //  - Injection without any parameters utlizes the default application server
    //    connection factory
    @Resource(name = "jms/TopicConnectionFactory")
    TopicConnectionFactory topicConnectionFactory;

    @Resource(lookup = "jms/Topic")
    Topic myTopic;
    
    public void createSharedDurable(){
        System.out.println("Consuming Topic Messages");
        String topicName = "JavaEE";

        try (TopicConnection conn = topicConnectionFactory.createTopicConnection();
       
         TopicSession session = conn
                          .createTopicSession(false, Session.AUTO_ACKNOWLEDGE);){
        conn.start();
        TopicSubscriber sub = session.createSubscriber(myTopic);
        javax.jms.Message message = sub.receive();
        System.out.println("received: " + message);
        while(message != null){
           
                System.out.println("Message Received: " + message.getBody(String.class));
           
            message = sub.receive(1000);
        } 
        } catch(JMSException ex){
            System.out.println("Exception:" + ex);
        } 
        
    }
    
    public void createSharedNonDurable(){
        System.out.println("Consuming Topic Messages");
        String topicName = "JavaEE";
        JMSContext context = topicConnectionFactory.createContext();
        JMSConsumer consumer = context.createSharedConsumer(myTopic, topicName);
        javax.jms.Message message = consumer.receive(1000);
        while(message != null){
            try {
                System.out.println("Message Received: " + message.getBody(String.class));
            } catch (JMSException ex) {
                Logger.getLogger(SharedConsumerExamples.class.getName()).log(Level.SEVERE, null, ex);
            }
            message = consumer.receive(1000);
        }
        
    }
    
}
