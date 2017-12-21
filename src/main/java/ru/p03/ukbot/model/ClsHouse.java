/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import ru.p03.classifier.model.Classifier;

/**
 *
 * @author altmf
 */
@Entity
@Table(name = "CLS_HOUSE", catalog = "UK", schema = "UK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsHouse.findAll", query = "SELECT c FROM ClsHouse c")})
public class ClsHouse extends Classifier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "STREET")
    private String street;
    @Column(name = "NUMBER")
    private String number;
    @OneToMany(mappedBy = "idHouse")
    private Collection<ClsApartment> clsApartmentCollection;

    public ClsHouse() {
    }

    public ClsHouse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @XmlTransient
    public Collection<ClsApartment> getClsApartmentCollection() {
        return clsApartmentCollection;
    }

    public void setClsApartmentCollection(Collection<ClsApartment> clsApartmentCollection) {
        this.clsApartmentCollection = clsApartmentCollection;
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
        if (!(object instanceof ClsHouse)) {
            return false;
        }
        ClsHouse other = (ClsHouse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ул." + getStreet() + " " + getNumber();
    }
    
}
