/*
CREATE TABLE RESTAURAUNT (
ID                  NUMERIC PRIMARY KEY,
NAME                VARCHAR(150),
DESCRIPTION         CLOB,
PARK_ID             NUMERIC
);

 */
package org.javaee7.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juneau
 */
@Vetoed
@Entity
@Table(name = "RESTAURANT")
@XmlRootElement
public class Restaurant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @NotNull
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "PARK_ID")
    private BigDecimal parkId;
   
    
    public Restaurant() {
    }

    public Restaurant(BigDecimal id) {
        this.id = id;
    }
  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Restaurant)) {
            return false;
        }
        Restaurant other = (Restaurant) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.javaee7.entity.Restaurant[ id=" + getId() + " ]";
    }

    /**
     * @return the id
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(BigDecimal id) {
        this.id = id;
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

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the parkId
     */
    public BigDecimal getParkId() {
        return parkId;
    }

    /**
     * @param parkId the parkId to set
     */
    public void setParkId(BigDecimal parkId) {
        this.parkId = parkId;
    }

   

    
}
