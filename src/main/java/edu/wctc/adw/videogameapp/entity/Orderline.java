/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "orderline")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orderline.findAll", query = "SELECT o FROM Orderline o"),
    @NamedQuery(name = "Orderline.findByCustomerId", query = "SELECT o FROM Orderline o WHERE o.orderlinePK.customerId = :customerId"),
    @NamedQuery(name = "Orderline.findByProductId", query = "SELECT o FROM Orderline o WHERE o.orderlinePK.productId = :productId"),
    @NamedQuery(name = "Orderline.findByQty", query = "SELECT o FROM Orderline o WHERE o.qty = :qty")})
public class Orderline implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderlinePK orderlinePK;
    @Column(name = "qty")
    private Integer qty;
    @JoinColumn(name = "customerId", referencedColumnName = "customerId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Customer customer;
    @JoinColumn(name = "product_id", referencedColumnName = "videogame_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Videogame videogame;

    public Orderline() {
    }

    public Orderline(OrderlinePK orderlinePK) {
        this.orderlinePK = orderlinePK;
    }

    public Orderline(int customerId, int productId) {
        this.orderlinePK = new OrderlinePK(customerId, productId);
    }

    public OrderlinePK getOrderlinePK() {
        return orderlinePK;
    }

    public void setOrderlinePK(OrderlinePK orderlinePK) {
        this.orderlinePK = orderlinePK;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Videogame getVideogame() {
        return videogame;
    }

    public void setVideogame(Videogame videogame) {
        this.videogame = videogame;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderlinePK != null ? orderlinePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderline)) {
            return false;
        }
        Orderline other = (Orderline) object;
        if ((this.orderlinePK == null && other.orderlinePK != null) || (this.orderlinePK != null && !this.orderlinePK.equals(other.orderlinePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.adw.videogameapp.entity.Orderline[ orderlinePK=" + orderlinePK + " ]";
    }
    
}
