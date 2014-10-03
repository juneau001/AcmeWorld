/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.javaee7.entity.AcmeUser;
import org.javaee7.interfaces.PromotionType;
import org.javaee7.jsf.AuthenticationController;

/**
 *
 * @author Juneau
 */
public class Promotion implements PromotionType, Serializable {
    private String name;
    private boolean processed;
    private AcmeUser user;
    
    public Promotion(String name,
                     AuthenticationController context){
        this.name = name;
        this.user = context.getCurrentUser();
        processed = false;
    }
    
    @Override
    public void processPromotion(){
        String message = "Successfully processed promotion: " + name + " via " + user.getFirstName() + user.getLastName();
        setProcessed(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    message, message));
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the user
     */
    public AcmeUser getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(AcmeUser user) {
        this.user = user;
    }

    /**
     * @return the processed
     */
    public boolean isProcessed() {
        return processed;
    }

    /**
     * @param processed the processed to set
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
