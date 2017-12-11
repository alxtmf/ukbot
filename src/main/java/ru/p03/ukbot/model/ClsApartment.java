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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "CLS_APARTMENT", catalog = "UK", schema = "UK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsApartment.findAll", query = "SELECT c FROM ClsApartment c")})
public class ClsApartment extends Classifier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "NUMBER")
    private String number;
    @OneToMany(mappedBy = "idApartment")
    private Collection<RegApartmentMeteringSender> regApartmentMeteringSenderCollection;
    @OneToMany(mappedBy = "idApartment")
    private Collection<RegApartmentMeteringDevice> regApartmentMeteringDeviceCollection;
    @JoinColumn(name = "ID_HOUSE", referencedColumnName = "ID")
    @ManyToOne
    private ClsHouse idHouse;

    public ClsApartment() {
    }

    public ClsApartment(Long id) {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @XmlTransient
    public Collection<RegApartmentMeteringSender> getRegApartmentMeteringSenderCollection() {
        return regApartmentMeteringSenderCollection;
    }

    public void setRegApartmentMeteringSenderCollection(Collection<RegApartmentMeteringSender> regApartmentMeteringSenderCollection) {
        this.regApartmentMeteringSenderCollection = regApartmentMeteringSenderCollection;
    }

    @XmlTransient
    public Collection<RegApartmentMeteringDevice> getRegApartmentMeteringDeviceCollection() {
        return regApartmentMeteringDeviceCollection;
    }

    public void setRegApartmentMeteringDeviceCollection(Collection<RegApartmentMeteringDevice> regApartmentMeteringDeviceCollection) {
        this.regApartmentMeteringDeviceCollection = regApartmentMeteringDeviceCollection;
    }

    public ClsHouse getIdHouse() {
        return idHouse;
    }

    public void setIdHouse(ClsHouse idHouse) {
        this.idHouse = idHouse;
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
        if (!(object instanceof ClsApartment)) {
            return false;
        }
        ClsApartment other = (ClsApartment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.ukbot.model.ClsApartment[ id=" + id + " ]";
    }
    
}
