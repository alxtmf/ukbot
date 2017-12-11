/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author altmf
 */
@Entity
@Table(name = "REG_METERING_DEVICE_RECORDS", catalog = "UK", schema = "UK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegMeteringDeviceRecords.findAll", query = "SELECT r FROM RegMeteringDeviceRecords r")})
public class RegMeteringDeviceRecords implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "DATE_REG")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReg;
    @Column(name = "INT_PART")
    private Integer intPart;
    @Column(name = "FRACT_PART")
    private Integer fractPart;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "RAW_RECORD")
    private BigDecimal rawRecord;
    @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "ID")
    @ManyToOne
    private ClsCustomer idCustomer;
    @JoinColumn(name = "ID_PERIOD", referencedColumnName = "ID")
    @ManyToOne
    private ClsPeriod idPeriod;
    @JoinColumn(name = "ID_APARTMENT_METERING_DEVICE", referencedColumnName = "ID")
    @ManyToOne
    private RegApartmentMeteringDevice idApartmentMeteringDevice;

    public RegMeteringDeviceRecords() {
    }

    public RegMeteringDeviceRecords(Long id) {
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

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public Integer getIntPart() {
        return intPart;
    }

    public void setIntPart(Integer intPart) {
        this.intPart = intPart;
    }

    public Integer getFractPart() {
        return fractPart;
    }

    public void setFractPart(Integer fractPart) {
        this.fractPart = fractPart;
    }

    public BigDecimal getRawRecord() {
        return rawRecord;
    }

    public void setRawRecord(BigDecimal rawRecord) {
        this.rawRecord = rawRecord;
    }

    public ClsCustomer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(ClsCustomer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public ClsPeriod getIdPeriod() {
        return idPeriod;
    }

    public void setIdPeriod(ClsPeriod idPeriod) {
        this.idPeriod = idPeriod;
    }

    public RegApartmentMeteringDevice getIdApartmentMeteringDevice() {
        return idApartmentMeteringDevice;
    }

    public void setIdApartmentMeteringDevice(RegApartmentMeteringDevice idApartmentMeteringDevice) {
        this.idApartmentMeteringDevice = idApartmentMeteringDevice;
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
        if (!(object instanceof RegMeteringDeviceRecords)) {
            return false;
        }
        RegMeteringDeviceRecords other = (RegMeteringDeviceRecords) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.ukbot.model.RegMeteringDeviceRecords[ id=" + id + " ]";
    }
    
}
