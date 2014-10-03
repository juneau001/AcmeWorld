/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.javaee7.jsf;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Juneau
 */
@Named(value = "navigationController")
@RequestScoped
public class NavigationController {

    /**
     * Creates a new instance of NavigationController
     */
    public NavigationController() {
    }
    
    public String navigateToStandard(){
        return "STANDARD";
    }
    
    public String navigateToAdmin(){
        return "ADMIN";
    }
    
    public String navigateToLogin(){
        return "LOGIN";
    }
    
    public String navigateToInfo(){
        return "INFO";
    }
    
    public String navigateToChat(){
        return "CHAT";
    }
}
