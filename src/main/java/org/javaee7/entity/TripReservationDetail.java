/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.javaee7.entity;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juneau
 */
@Vetoed
@Entity
@Table(name = "TRIP_RESERVATION_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TripReservationDetail.findAll", query = "SELECT t FROM TripReservationDetail t"),
    @NamedQuery(name = "TripReservationDetail.findById", query = "SELECT t FROM TripReservationDetail t WHERE t.id = :id"),
    @NamedQuery(name = "TripReservationDetail.findByReservationDate", query = "SELECT t FROM TripReservationDetail t WHERE t.reservationDate = :reservationDate"),
    @NamedQuery(name = "TripReservationDetail.findByPark", query = "SELECT t FROM TripReservationDetail t WHERE t.park = :park")})
public class TripReservationDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "RESERVATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date reservationDate;
    @Column(name = "PARK")
    private Integer park;
    @JoinColumn(name = "RESERVATION_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ParkReservation reservationId;

    public TripReservationDetail() {
    }

    public TripReservationDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Integer getPark() {
        return park;
    }

    public void setPark(Integer park) {
        this.park = park;
    }

    public ParkReservation getReservationId() {
        return reservationId;
    }

    public void setReservationId(ParkReservation reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TripReservationDetail)) {
            return false;
        }
        TripReservationDetail other = (TripReservationDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.javaee7.entity.TripReservationDetail[ id=" + id + " ]";
    }
    
}
