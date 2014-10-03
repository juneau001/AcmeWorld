/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.jsf;

import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import org.javaee7.bean.Promotion;
import org.javaee7.interfaces.PromotionType;

/**
 *
 * @author Juneau
 */
@Named(value = "createPromotionController")
@RequestScoped
public class CreatePromotionController {

    private String name;
    
    @Inject
    AuthenticationController authenticationController;
    
    @Inject
    PromotionController promotionController;
    
    @Resource
    ContextService ctxSvc;
    
    /**
     * Creates a new instance of CreatePromotionController
     */
    public CreatePromotionController() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public void createPromotion(){
        PromotionType newPromotion = new Promotion(name, authenticationController);
        
        PromotionType proxy = ctxSvc.createContextualProxy(newPromotion, PromotionType.class);
        promotionController.addPromotion(proxy);
        // Obtain the Execution Properties:
        // ctxSvc.getExecutionProperties(proxy));
        String message = "Successfully created promotion: " + name;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                   message, message));
    }

    
}
