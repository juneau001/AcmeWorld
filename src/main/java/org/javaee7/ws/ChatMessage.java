/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.javaee7.ws;

import java.util.Date;

/**
 * Java object to hold chat message contents
 * @author Juneau
 */
public class ChatMessage {
    private String sender;
    private String message;
    private Date messageDate;

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
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

    /**
     * @return the messageDate
     */
    public Date getMessageDate() {
        return messageDate;
    }

    /**
     * @param messageDate the messageDate to set
     */
    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }
}
