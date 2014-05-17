
package org.javaee7.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Juneau
 */
public class AcmeMessageListener implements MessageListener {

    
    @Override
    public void onMessage(Message message){
        try {
            int messageCount = message.getIntProperty("JMSXDeliveryCount");
        
        if (messageCount > 5){
            System.out.println("Message sent too many times...");
        }
        
        } catch (JMSException e){
            throw new RuntimeException(e);
        }
    }
    
}