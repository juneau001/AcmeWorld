/*
CREATE TABLE RESTAURANT_RESERVATION (
ID                  NUMERIC PRIMARY KEY,
RESTAURANT_ID       NUMERIC,
RESERVATION_ID      NUMERIC,
RESERVATION_DATE    DATE,
NUMBER_OF_PEOPLE    NUMERIC
);
 */
package org.javaee7.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juneau
 */
@Vetoed
@Entity
@Table(name = "RESTAURANT_RESERVATION")
@XmlRootElement
public class RestaurantReservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @NotNull
    @Column(name = "RESTAURANT_ID")
    private BigDecimal restaurantId;
    @NotNull
    @Column(name = "RESERVATION_ID")
    private BigDecimal reservationId;
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "RESERVATION_DATE")
    private Date reservationDate;
    @NotNull
    @Min(value=1, message="You entered ${numberOfPeople} for the number of people, please enter at least one person")
    @Column(name = "NUMBER_OF_PEOPLE")
    private BigDecimal numberOfPeople;
   
    
    public RestaurantReservation() {
    }

    public RestaurantReservation(BigDecimal id) {
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
        if (!(object instanceof RestaurantReservation)) {
            return false;
        }
        RestaurantReservation other = (RestaurantReservation) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.javaee7.entity.RestaurantReservation[ id=" + getId() + " ]";
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
     * @return the restaurantId
     */
    public BigDecimal getRestaurantId() {
        return restaurantId;
    }

    /**
     * @param restaurantId the restaurantId to set
     */
    public void setRestaurantId(BigDecimal restaurantId) {
        this.restaurantId = restaurantId;
    }

    /**
     * @return the reservationId
     */
    public BigDecimal getReservationId() {
        return reservationId;
    }

    /**
     * @param reservationId the reservationId to set
     */
    public void setReservationId(BigDecimal reservationId) {
        this.reservationId = reservationId;
    }

    /**
     * @return the reservationDate
     */
    public Date getReservationDate() {
        return reservationDate;
    }

    /**
     * @param reservationDate the reservationDate to set
     */
    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    /**
     * @return the numberOfPeople
     */
    public BigDecimal getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * @param numberOfPeople the numberOfPeople to set
     */
    public void setNumberOfPeople(BigDecimal numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

     

    
}

