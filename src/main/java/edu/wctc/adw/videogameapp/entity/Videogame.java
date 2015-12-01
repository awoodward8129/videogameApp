/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "videogame")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Videogame.findAll", query = "SELECT v FROM Videogame v"),
    @NamedQuery(name = "Videogame.findByVideogameId", query = "SELECT v FROM Videogame v WHERE v.videogameId = :videogameId"),
    @NamedQuery(name = "Videogame.findByTitle", query = "SELECT v FROM Videogame v WHERE v.title = :title"),
    @NamedQuery(name = "Videogame.findBySystem", query = "SELECT v FROM Videogame v WHERE v.system = :system"),
    @NamedQuery(name = "Videogame.findByLogDate", query = "SELECT v FROM Videogame v WHERE v.logDate = :logDate"),
    @NamedQuery(name = "Videogame.findByPrice", query = "SELECT v FROM Videogame v WHERE v.price = :price"),
    @NamedQuery(name = "Videogame.findByImageUrl", query = "SELECT v FROM Videogame v WHERE v.imageUrl = :imageUrl")})
public class Videogame implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "videogame_id")
    private Integer videogameId;
    @Size(max = 150)
    @Column(name = "title")
    private String title;
    @Size(max = 45)
    @Column(name = "system")
    private String system;
    @Column(name = "log_date")
    @Temporal(TemporalType.DATE)
    private Date logDate;
    @Column(name = "price")
    private Long price;
    @Size(max = 1000)
    @Column(name = "image_url")
    private String imageUrl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "videogame")
    private Collection<Orderline> orderlineCollection;

    public Videogame() {
    }

    public Videogame(Integer videogameId) {
        this.videogameId = videogameId;
    }

    public Integer getVideogameId() {
        return videogameId;
    }

    public void setVideogameId(Integer videogameId) {
        this.videogameId = videogameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @XmlTransient
    public Collection<Orderline> getOrderlineCollection() {
        return orderlineCollection;
    }

    public void setOrderlineCollection(Collection<Orderline> orderlineCollection) {
        this.orderlineCollection = orderlineCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (videogameId != null ? videogameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Videogame)) {
            return false;
        }
        Videogame other = (Videogame) object;
        if ((this.videogameId == null && other.videogameId != null) || (this.videogameId != null && !this.videogameId.equals(other.videogameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.adw.videogameapp.entity.Videogame[ videogameId=" + videogameId + " ]";
    }
    
}
