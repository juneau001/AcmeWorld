/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.javaee7.entity.AcmeUser;

/**
 *
 * @author Juneau
 */
@Stateless
public class AuthenticationFacade  {
    
    @PersistenceContext(unitName = "AcmeWorldPU")
    private EntityManager em;
    
    private AcmeUser user = null;
    
    public AuthenticationFacade(){
        
    }
    
    public boolean authenticate(String user){
 
        try {
            this.setUser((AcmeUser) em.createQuery("select object(o) from AcmeUser o " +
                "where o.userName = :userName")
                .setParameter("userName", user.toUpperCase())
                .getSingleResult());
        } catch (NoResultException ex){
            System.out.println("User Not Found");      
        }
        
        if(this.getUser() != null){
            return true;
        } else {
            return false;
        }
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
    
}
