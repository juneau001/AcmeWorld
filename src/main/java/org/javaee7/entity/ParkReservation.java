
package org.javaee7.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.javaee7.entity.listener.ParkReservationListener;

/**
 *
 * @author Juneau
 */
@Vetoed
@Entity
@Table(name = "PARK_RESERVATION")
@XmlRootElement
@EntityListeners(ParkReservationListener.class)
public class ParkReservation implements Serializable {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @NotNull
    @Size(min=1, message="Please enter a first name")
    @Column(name = "FIRST_NAME")
    private String firstName;
    @NotNull
    @Size(min=1, message="Please enter a last name")
    @Column(name = "LAST_NAME")
    private String lastName;
    @NotNull(message="You must include a trip start date")
    @Temporal(TemporalType.DATE)
    @Column(name = "TRIP_START_DATE")
    private Date tripStartDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "ENTER_DATE")
    private Date enterDate;
    @Min(value=1, message="Your trip must include one adult")
    @Column(name = "NUM_ADULTS")
    private int numAdults;
    @Column(name = "NUM_CHILD")
    private int numChild;
    @Min(value=1, message="You've selected ${validatedValue} for your reservation, you must book at least one day")
    @Column(name = "NUM_DAYS")
    private int numDays;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservationId")
    private Collection<TripReservationDetail> tripReservationDetailCollection;
    private static final long serialVersionUID = 1L;
    
    
            
    public ParkReservation() {
    }

    public ParkReservation(BigDecimal id) {
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
        if (!(object instanceof ParkReservation)) {
            return false;
        }
        ParkReservation other = (ParkReservation) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.javaee7.entity.ParkReservation[ id=" + getId() + " ]";
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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the numAdults
     */
    public int getNumAdults() {
        return numAdults;
    }

    /**
     * @param numAdults the numAdults to set
     */
    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }

    /**
     * @return the numChild
     */
    public int getNumChild() {
        return numChild;
    }

    /**
     * @param numChild the numChild to set
     */
    public void setNumChild(int numChild) {
        this.numChild = numChild;
    }

    /**
     * @return the numDays
     */
    public int getNumDays() {
        return numDays;
    }

    /**
     * @param numDays the numDays to set
     */
    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    /**
     * @return the tripStartDate
     */
    public Date getTripStartDate() {
        return tripStartDate;
    }

    /**
     * @param tripStartDate the tripStartDate to set
     */
    public void setTripStartDate(Date tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    /**
     * @return the enterDate
     */
    public Date getEnterDate() {
        return enterDate;
    }

    /**
     * @param enterDate the enterDate to set
     */
    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    @XmlTransient
    public Collection<TripReservationDetail> getTripReservationDetailCollection() {
        return tripReservationDetailCollection;
    }

    public void setTripReservationDetailCollection(Collection<TripReservationDetail> tripReservationDetailCollection) {
        this.tripReservationDetailCollection = tripReservationDetailCollection;
    }

    

    
}