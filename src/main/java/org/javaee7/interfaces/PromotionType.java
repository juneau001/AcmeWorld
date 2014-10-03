/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.interfaces;

import org.javaee7.entity.AcmeUser;

/**
 *
 * @author Juneau
 */
public interface PromotionType {
    public void processPromotion();
    public String getName();
    public AcmeUser getUser();
    public boolean isProcessed();
}
