
package org.javaee7.jms;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.NamingException;

/**
 *
 * @author Juneau
 */
@Named("topicPublisher")
@SessionScoped
public class TopicPublisher implements java.io.Serializable {

    @Resource(name = "jms/TopicConnectionFactory")
    private TopicConnectionFactory topicConnFactory;
    @Resource(lookup = "jms/Topic")
    Topic topic;
    TopicSession session;
    
    private String message = null;

    public void publishMessage() throws NamingException, JMSException {
        System.out.println("Publishing message...");
        try (TopicConnection topicConnection = topicConnFactory.createTopicConnection();
            TopicSession topicSession =
                topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);) {
            
            Topic createdtopic = topicSession.createTopic("JavaEE");
            topicConnection.start();
            // create the message to send
            TextMessage textMessage = topicSession.createTextMessage("This is a test message");
            try (javax.jms.TopicPublisher topicPublisher = topicSession.createPublisher(createdtopic)) {
                topicPublisher.publish(textMessage);
                setMessage("Message Published");
            }
            

        }
        
    }
    
    /**
     * Send a message to a Topic with delivery delay
     * @throws NamingException
     * @throws JMSException 
     */
    public void publishMessageWithDelay() throws NamingException, JMSException {

        try (TopicConnection topicConnection = topicConnFactory.createTopicConnection();
                TopicSession topicSession =
                topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);) {
            topicConnection.start();
            Topic createdtopic = topicSession.createTopic("JavaEE");

            // create the message to send
            TextMessage textMessage = topicSession.createTextMessage("This is a test message");
            
            javax.jms.TopicPublisher topicPublisher = topicSession.createPublisher(createdtopic);
            topicPublisher.setDeliveryDelay(1000);
            topicPublisher.publish(textMessage);
            setMessage("Message Published");

        }
        
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
