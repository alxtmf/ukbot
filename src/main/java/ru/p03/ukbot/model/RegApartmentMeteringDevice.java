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

/**
 *
 * @author altmf
 */
@Entity
@Table(name = "REG_APARTMENT_METERING_DEVICE", catalog = "UK", schema = "UK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegApartmentMeteringDevice.findAll", query = "SELECT r FROM RegApartmentMeteringDevice r")})
public class RegApartmentMeteringDevice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "SERIAL")
    private String serial;
    @JoinColumn(name = "ID_APARTMENT", referencedColumnName = "ID")
    @ManyToOne
    private ClsApartment idApartment;
    @JoinColumn(name = "ID_METERING_DEVICE_TYPE", referencedColumnName = "ID")
    @ManyToOne
    private ClsMeteringDeviceType idMeteringDeviceType;
    @OneToMany(mappedBy = "idApartmentMeteringDevice")
    private Collection<RegMeteringDeviceRecords> regMeteringDeviceRecordsCollection;

    public RegApartmentMeteringDevice() {
    }

    public RegApartmentMeteringDevice(Long id) {
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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public ClsApartment getIdApartment() {
        return idApartment;
    }

    public void setIdApartment(ClsApartment idApartment) {
        this.idApartment = idApartment;
    }

    public ClsMeteringDeviceType getIdMeteringDeviceType() {
        return idMeteringDeviceType;
    }

    public void setIdMeteringDeviceType(ClsMeteringDeviceType idMeteringDeviceType) {
        this.idMeteringDeviceType = idMeteringDeviceType;
    }

    @XmlTransient
    public Collection<RegMeteringDeviceRecords> getRegMeteringDeviceRecordsCollection() {
        return regMeteringDeviceRecordsCollection;
    }

    public void setRegMeteringDeviceRecordsCollection(Collection<RegMeteringDeviceRecords> regMeteringDeviceRecordsCollection) {
        this.regMeteringDeviceRecordsCollection = regMeteringDeviceRecordsCollection;
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
        if (!(object instanceof RegApartmentMeteringDevice)) {
            return false;
        }
        RegApartmentMeteringDevice other = (RegApartmentMeteringDevice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.ukbot.model.RegApartmentMeteringDevice[ id=" + id + " ]";
    }
    
}
