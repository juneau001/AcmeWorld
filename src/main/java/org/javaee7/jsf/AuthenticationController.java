/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.jsf;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.javaee7.entity.AcmeUser;
import org.javaee7.session.AuthenticationFacade;

/**
 *
 * @author Juneau
 */
@Named(value = "authenticationController")
@SessionScoped
public class AuthenticationController implements Serializable {

    @EJB
    AuthenticationFacade authFacade;
    
    private String username;
    private AcmeUser currentUser;
    private boolean authenticated = false;
    
    /**
     * Creates a new instance of PromotionController
     */
    public AuthenticationController() {
    }
    
    public AcmeUser getCurrentUser(){   
        return currentUser;
    }
    
    public void setCurrentUser(AcmeUser user){
        this.currentUser = user;
    }
    
    /**
     * Example login method.
     * @return 
     */
    public String login(){
        boolean valid = false;
        if(username != null){
            valid = authFacade.authenticate(username);
        }
        if(valid){
            currentUser = authFacade.getUser();
            authenticated = true;
            System.out.println("Logging in successfully");
            return "SUCCESSLOGIN";
        } else {
            authenticated = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid username, please try again", "Invalid username, please try again"));
            return null;
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * @param authenticated the authenticated to set
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
