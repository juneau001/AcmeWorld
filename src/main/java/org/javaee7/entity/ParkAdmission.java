
package org.javaee7.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juneau
 */
@Vetoed
@Entity
@Table(name = "PARK_ADMISSION")
@XmlRootElement
public class ParkAdmission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @NotNull
    @Column(name = "PARK_ID")
    private BigDecimal parkId;
    @Column(name = "ADULT_ADMISSION")
    private BigDecimal adultAdmission;
    @Column(name = "CHILD_ADMISSION")
    private BigDecimal childAdmission;
  
    
    public ParkAdmission() {
    }

    public ParkAdmission(BigDecimal id) {
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
        if (!(object instanceof ParkAdmission)) {
            return false;
        }
        ParkAdmission other = (ParkAdmission) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.javaee7.entity.ParkAdmission[ id=" + getId() + " ]";
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

    /**
     * @return the adultAdmission
     */
    public BigDecimal getAdultAdmission() {
        return adultAdmission;
    }

    /**
     * @param adultAdmission the adultAdmission to set
     */
    public void setAdultAdmission(BigDecimal adultAdmission) {
        this.adultAdmission = adultAdmission;
    }

    /**
     * @return the childAdmission
     */
    public BigDecimal getChildAdmission() {
        return childAdmission;
    }

    /**
     * @param childAdmission the childAdmission to set
     */
    public void setChildAdmission(BigDecimal childAdmission) {
        this.childAdmission = childAdmission;
    }

    
}