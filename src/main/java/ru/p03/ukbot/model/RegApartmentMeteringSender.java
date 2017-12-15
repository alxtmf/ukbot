/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import ru.p03.classifier.model.IBean;

/**
 *
 * @author altmf
 */
@Entity
@Table(name = "REG_APARTMENT_METERING_SENDER", catalog = "UK", schema = "UK")
@XmlRootElement
public class RegApartmentMeteringSender implements Serializable, IBean {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "DATE_BEGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBegin;
    @Column(name = "DATE_END")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;
    @JoinColumn(name = "ID_APARTMENT", referencedColumnName = "ID")
    @ManyToOne
    private ClsApartment idApartment;
    @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "ID")
    @ManyToOne
    private ClsCustomer idCustomer;

    public RegApartmentMeteringSender() {
    }

    public RegApartmentMeteringSender(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public ClsApartment getIdApartment() {
        return idApartment;
    }

    public void setIdApartment(ClsApartment idApartment) {
        this.idApartment = idApartment;
    }

    public ClsCustomer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(ClsCustomer idCustomer) {
        this.idCustomer = idCustomer;
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
        if (!(object instanceof RegApartmentMeteringSender)) {
            return false;
        }
        RegApartmentMeteringSender other = (RegApartmentMeteringSender) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.ukbot.model.RegApartmentMeteringSender[ id=" + id + " ]";
    }
    
}
