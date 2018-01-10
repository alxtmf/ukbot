/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.http.client.utils.DateUtils;
import ru.p03.classifier.model.Classifier;
import ru.p03.common.util.DateUtil;

/**
 *
 * @author altmf
 */
@Entity
@Table(name = "CLS_PERIOD", catalog = "UK", schema = "UK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClsPeriod.findAll", query = "SELECT c FROM ClsPeriod c")})
public class ClsPeriod extends Classifier implements Serializable {

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
    @OneToMany(mappedBy = "idPeriod")
    private Collection<RegMeteringDeviceRecords> regMeteringDeviceRecordsCollection;

    public ClsPeriod() {
    }

    public ClsPeriod(Long id) {
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
        if (!(object instanceof ClsPeriod)) {
            return false;
        }
        ClsPeriod other = (ClsPeriod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return DateUtil.transformDate(dateBegin) + " - " + DateUtil.transformDate(dateEnd);
    }
    
}
