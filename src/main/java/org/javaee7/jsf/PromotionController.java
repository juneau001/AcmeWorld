/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.jsf;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.javaee7.bean.Promotion;
import org.javaee7.interfaces.PromotionType;

/**
 *
 * @author Juneau
 */
@Named(value = "promotionController")
@ApplicationScoped
public class PromotionController implements Serializable {

    private List<PromotionType> promotionList;
    
    /**
     * Creates a new instance of PromotionController
     */
    public PromotionController() {
        promotionList = new ArrayList();
    }
    
    public void addPromotion(PromotionType proxy){
        promotionList.add(proxy);
    }
    
    public List<PromotionType> obtainPromotions(){
        return promotionList;
    }
    
   
}
