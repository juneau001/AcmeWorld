/*
CREATE TABLE RESERVATION_SCHEDULE(
ID                  NUMERIC PRIMARY KEY,
RESERVATION_ID      NUMERIC,
VACATION_DAY        NUMERIC,
PARK_ID             NUMERIC,
NOTES               CLOB
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
@Table(name = "RESERVATION_SCHEDULE")
@XmlRootElement
public class ReservationSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @NotNull
    @Column(name = "RESERVATION_ID")
    private BigDecimal reservationId;
    @NotNull
    @Column(name = "VACATION_DAY")
    private BigDecimal vacationDay;
    @NotNull
    @Column(name = "PARK_ID")
    private BigDecimal parkId;
    @Column(name = "NOTES")
    private String notes;
           
  
    
    public ReservationSchedule() {
    }

    public ReservationSchedule(BigDecimal id) {
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
        if (!(object instanceof ReservationSchedule)) {
            return false;
        }
        ReservationSchedule other = (ReservationSchedule) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.javaee7.entity.ReservationSchedule[ id=" + getId() + " ]";
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
     * @return the vacationDay
     */
    public BigDecimal getVacationDay() {
        return vacationDay;
    }

    /**
     * @param vacationDay the vacationDay to set
     */
    public void setVacationDay(BigDecimal vacationDay) {
        this.vacationDay = vacationDay;
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
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    

    
}
