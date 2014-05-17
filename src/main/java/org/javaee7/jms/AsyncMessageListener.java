

package org.javaee7.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Juneau
 */
public class AsyncMessageListener implements MessageListener {
 
        @Override
    public void onMessage(Message message) {
        try {
            System.out.println("Do something with this message: " + message.getBody(String.class));
        } catch (JMSException ex) {
            Logger.getLogger(AsyncMessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
