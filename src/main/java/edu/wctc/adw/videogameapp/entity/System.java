/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.adw.videogameapp.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alex
 */
@Entity
@Table(name = "system")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "System.findAll", query = "SELECT s FROM System s"),
    @NamedQuery(name = "System.findBySystemId", query = "SELECT s FROM System s WHERE s.systemId = :systemId"),
    @NamedQuery(name = "System.findBySystemName", query = "SELECT s FROM System s WHERE s.systemName = :systemName")})
public class System implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "systemId")
    private Integer systemId;
    @Size(max = 45)
    @Column(name = "systemName")
    private String systemName;
   @OneToMany(fetch = FetchType.EAGER, mappedBy = "systemId", orphanRemoval=true)
    private Set<Videogame> videogameSet;

    public System() {
    }

    public System(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @XmlTransient
    public Set<Videogame> getVideogameSet() {
        return videogameSet;
    }

    public void setVideogameCollection(Set<Videogame> videogameSet) {
        this.videogameSet = videogameSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemId != null ? systemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof System)) {
            return false;
        }
        System other = (System) object;
        if ((this.systemId == null && other.systemId != null) || (this.systemId != null && !this.systemId.equals(other.systemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.adw.videogameapp.entity.System[ systemId=" + systemId + " ]";
    }
    
}
