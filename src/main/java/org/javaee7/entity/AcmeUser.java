/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.entity;

import java.io.Serializable;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "ACME_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AcmeUser.findAll", query = "SELECT a FROM AcmeUser a"),
    @NamedQuery(name = "AcmeUser.findById", query = "SELECT a FROM AcmeUser a WHERE a.id = :id"),
    @NamedQuery(name = "AcmeUser.findByFirstName", query = "SELECT a FROM AcmeUser a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "AcmeUser.findByLastName", query = "SELECT a FROM AcmeUser a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "AcmeUser.findByUserName", query = "SELECT a FROM AcmeUser a WHERE a.userName = :userName"),
    @NamedQuery(name = "AcmeUser.findByRole", query = "SELECT a FROM AcmeUser a WHERE a.role = :role")})
public class AcmeUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 100)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Size(max = 100)
    @Column(name = "USER_NAME")
    private String userName;
    @Size(max = 25)
    @Column(name = "ROLE")
    private String role;

    public AcmeUser() {
    }

    public AcmeUser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        if (!(object instanceof AcmeUser)) {
            return false;
        }
        AcmeUser other = (AcmeUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.javaee7.entity.AcmeUser[ id=" + id + " ]";
    }
    
}
