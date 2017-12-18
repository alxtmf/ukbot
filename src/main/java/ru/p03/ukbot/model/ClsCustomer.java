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
@Table(name = "CLS_CUSTOMER", catalog = "UK", schema = "UK")
@XmlRootElement
public class ClsCustomer extends Classifier implements Serializable {

    @OneToMany(mappedBy = "idCustomer")
    private Collection<ClsCustomerPhone> clsCustomerPhoneCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ID_TELEGRAM")
    private Integer idTelegram;
    @Column(name = "ID_CHAT")
    private Integer idChat;
    @Column(name = "IS_DELETED")
    private Integer isDeleted;
    @Column(name = "FAM")
    private String fam;
    @Column(name = "IM")
    private String im;
    @Column(name = "OTC")
    private String otc;
    @OneToMany(mappedBy = "idCustomer")
    private Collection<RegApartmentMeteringSender> regApartmentMeteringSenderCollection;
    @OneToMany(mappedBy = "idCustomer")
    private Collection<RegMeteringDeviceRecords> regMeteringDeviceRecordsCollection;

    public ClsCustomer() {
    }

    public ClsCustomer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdTelegram() {
        return idTelegram;
    }

    public void setIdTelegram(Integer idTelegram) {
        this.idTelegram = idTelegram;
    }

    public Integer getIdChat() {
        return idChat;
    }

    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getOtc() {
        return otc;
    }

    public void setOtc(String otc) {
        this.otc = otc;
    }

    @XmlTransient
    public Collection<RegApartmentMeteringSender> getRegApartmentMeteringSenderCollection() {
        return regApartmentMeteringSenderCollection;
    }

    public void setRegApartmentMeteringSenderCollection(Collection<RegApartmentMeteringSender> regApartmentMeteringSenderCollection) {
        this.regApartmentMeteringSenderCollection = regApartmentMeteringSenderCollection;
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
        if (!(object instanceof ClsCustomer)) {
            return false;
        }
        ClsCustomer other = (ClsCustomer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.p03.ukbot.model.ClsCustomer[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ClsCustomerPhone> getClsCustomerPhoneCollection() {
        return clsCustomerPhoneCollection;
    }

    public void setClsCustomerPhoneCollection(Collection<ClsCustomerPhone> clsCustomerPhoneCollection) {
        this.clsCustomerPhoneCollection = clsCustomerPhoneCollection;
    }
    
}
