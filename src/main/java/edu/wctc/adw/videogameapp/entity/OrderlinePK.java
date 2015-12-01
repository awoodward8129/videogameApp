/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alex
 */
@Embeddable
public class OrderlinePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "customerId")
    private int customerId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_id")
    private int productId;

    public OrderlinePK() {
    }

    public OrderlinePK(int customerId, int productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) customerId;
        hash += (int) productId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderlinePK)) {
            return false;
        }
        OrderlinePK other = (OrderlinePK) object;
        if (this.customerId != other.customerId) {
            return false;
        }
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.adw.videogameapp.entity.OrderlinePK[ customerId=" + customerId + ", productId=" + productId + " ]";
    }
    
}
