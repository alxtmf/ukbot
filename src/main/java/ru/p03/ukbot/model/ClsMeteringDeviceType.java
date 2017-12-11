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
@Table(name = "CLS_METERING_DEVICE_TYPE", catalog = "UK", schema = "UK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsMeteringDeviceType.findAll", query = "SELECT c FROM ClsMeteringDeviceType c")})
public class ClsMeteringDeviceType extends Classifier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "idMeteringDeviceType")
    private Collection<RegApartmentMeteringDevice> regApartmentMeteringDeviceCollection;

    public ClsMeteringDeviceType() {
    }

    public ClsMeteringDeviceType(Long id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<RegApartmentMeteringDevice> getRegApartmentMeteringDeviceCollection() {
        return regApartmentMeteringDeviceCollection;
    }

    public void setRegApartmentMeteringDeviceCollection(Collection<RegApartmentMeteringDevice> regApartmentMeteringDeviceCollection) {
        this.regApartmentMeteringDeviceCollection = regApartmentMeteringDeviceCollection;
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
        if (!(object instanceof ClsMeteringDeviceType)) {
            return false;
        }
        ClsMeteringDeviceType other = (ClsMeteringDeviceType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.ukbot.model.ClsMeteringDeviceType[ id=" + id + " ]";
    }
    
}
