/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.model.repository;

import com.google.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import ru.p03.classifier.model.RegRepository;
import ru.p03.common.util.QueriesEngine;
import ru.p03.ukbot.model.ClsCustomer;
import ru.p03.ukbot.model.ClsPeriod;
import ru.p03.ukbot.model.RegApartmentMeteringDevice;
import ru.p03.ukbot.model.RegMeteringDeviceRecords;
import ru.p03.ukbot.model.repository.exceptions.NonexistentEntityException;

/**
 *
 * @author altmf
 */
public class RegMeteringDeviceRecordsRepository implements RegRepository<RegMeteringDeviceRecords> {

    /**
     * @return the DAO
     */
    public QueriesEngine getDAO() {
        return DAO;
    }

    /**
     * @param DAO the DAO to set
     */
    @Inject
    public void setDAO(QueriesEngine DAO) {
        this.DAO = DAO;
    }

    private QueriesEngine DAO;

    @Override
    public Class getRegClass() {
        return RegMeteringDeviceRecords.class;
    }

    @Override
    public EntityManager getEntityManager() {
        return getDAO().getEntityManager();
    }

    public void create(RegMeteringDeviceRecords regMeteringDeviceRecords) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClsCustomer idCustomer = regMeteringDeviceRecords.getIdCustomer();
            if (idCustomer != null) {
                idCustomer = em.getReference(idCustomer.getClass(), idCustomer.getId());
                regMeteringDeviceRecords.setIdCustomer(idCustomer);
            }
            ClsPeriod idPeriod = regMeteringDeviceRecords.getIdPeriod();
            if (idPeriod != null) {
                idPeriod = em.getReference(idPeriod.getClass(), idPeriod.getId());
                regMeteringDeviceRecords.setIdPeriod(idPeriod);
            }
            RegApartmentMeteringDevice idApartmentMeteringDevice = regMeteringDeviceRecords.getIdApartmentMeteringDevice();
            if (idApartmentMeteringDevice != null) {
                idApartmentMeteringDevice = em.getReference(idApartmentMeteringDevice.getClass(), idApartmentMeteringDevice.getId());
                regMeteringDeviceRecords.setIdApartmentMeteringDevice(idApartmentMeteringDevice);
            }
            em.persist(regMeteringDeviceRecords);
//            if (idCustomer != null) {
//                idCustomer.getRegMeteringDeviceRecordsCollection().add(regMeteringDeviceRecords);
//                idCustomer = em.merge(idCustomer);
//            }
//            if (idPeriod != null) {
//                idPeriod.getRegMeteringDeviceRecordsCollection().add(regMeteringDeviceRecords);
//                idPeriod = em.merge(idPeriod);
//            }
//            if (idApartmentMeteringDevice != null) {
//                idApartmentMeteringDevice.getRegMeteringDeviceRecordsCollection().add(regMeteringDeviceRecords);
//                idApartmentMeteringDevice = em.merge(idApartmentMeteringDevice);
//            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegMeteringDeviceRecords regMeteringDeviceRecords) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegMeteringDeviceRecords persistentRegMeteringDeviceRecords = em.find(RegMeteringDeviceRecords.class, regMeteringDeviceRecords.getId());
            ClsCustomer idCustomerOld = persistentRegMeteringDeviceRecords.getIdCustomer();
            ClsCustomer idCustomerNew = regMeteringDeviceRecords.getIdCustomer();
            ClsPeriod idPeriodOld = persistentRegMeteringDeviceRecords.getIdPeriod();
            ClsPeriod idPeriodNew = regMeteringDeviceRecords.getIdPeriod();
            RegApartmentMeteringDevice idApartmentMeteringDeviceOld = persistentRegMeteringDeviceRecords.getIdApartmentMeteringDevice();
            RegApartmentMeteringDevice idApartmentMeteringDeviceNew = regMeteringDeviceRecords.getIdApartmentMeteringDevice();
            if (idCustomerNew != null) {
                idCustomerNew = em.getReference(idCustomerNew.getClass(), idCustomerNew.getId());
                regMeteringDeviceRecords.setIdCustomer(idCustomerNew);
            }
            if (idPeriodNew != null) {
                idPeriodNew = em.getReference(idPeriodNew.getClass(), idPeriodNew.getId());
                regMeteringDeviceRecords.setIdPeriod(idPeriodNew);
            }
            if (idApartmentMeteringDeviceNew != null) {
                idApartmentMeteringDeviceNew = em.getReference(idApartmentMeteringDeviceNew.getClass(), idApartmentMeteringDeviceNew.getId());
                regMeteringDeviceRecords.setIdApartmentMeteringDevice(idApartmentMeteringDeviceNew);
            }
            regMeteringDeviceRecords = em.merge(regMeteringDeviceRecords);
//            if (idCustomerOld != null && !idCustomerOld.equals(idCustomerNew)) {
//                idCustomerOld.getRegMeteringDeviceRecordsCollection().remove(regMeteringDeviceRecords);
//                idCustomerOld = em.merge(idCustomerOld);
//            }
//            if (idCustomerNew != null && !idCustomerNew.equals(idCustomerOld)) {
//                idCustomerNew.getRegMeteringDeviceRecordsCollection().add(regMeteringDeviceRecords);
//                idCustomerNew = em.merge(idCustomerNew);
//            }
//            if (idPeriodOld != null && !idPeriodOld.equals(idPeriodNew)) {
//                idPeriodOld.getRegMeteringDeviceRecordsCollection().remove(regMeteringDeviceRecords);
//                idPeriodOld = em.merge(idPeriodOld);
//            }
//            if (idPeriodNew != null && !idPeriodNew.equals(idPeriodOld)) {
//                idPeriodNew.getRegMeteringDeviceRecordsCollection().add(regMeteringDeviceRecords);
//                idPeriodNew = em.merge(idPeriodNew);
//            }
//            if (idApartmentMeteringDeviceOld != null && !idApartmentMeteringDeviceOld.equals(idApartmentMeteringDeviceNew)) {
//                idApartmentMeteringDeviceOld.getRegMeteringDeviceRecordsCollection().remove(regMeteringDeviceRecords);
//                idApartmentMeteringDeviceOld = em.merge(idApartmentMeteringDeviceOld);
//            }
//            if (idApartmentMeteringDeviceNew != null && !idApartmentMeteringDeviceNew.equals(idApartmentMeteringDeviceOld)) {
//                idApartmentMeteringDeviceNew.getRegMeteringDeviceRecordsCollection().add(regMeteringDeviceRecords);
//                idApartmentMeteringDeviceNew = em.merge(idApartmentMeteringDeviceNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = regMeteringDeviceRecords.getId();
                if (find(id) == null) {
                    throw new NonexistentEntityException("The regMeteringDeviceRecords with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegMeteringDeviceRecords regMeteringDeviceRecords;
            try {
                regMeteringDeviceRecords = em.getReference(RegMeteringDeviceRecords.class, id);
                regMeteringDeviceRecords.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regMeteringDeviceRecords with id " + id + " no longer exists.", enfe);
            }
            ClsCustomer idCustomer = regMeteringDeviceRecords.getIdCustomer();
            if (idCustomer != null) {
                idCustomer.getRegMeteringDeviceRecordsCollection().remove(regMeteringDeviceRecords);
                idCustomer = em.merge(idCustomer);
            }
            ClsPeriod idPeriod = regMeteringDeviceRecords.getIdPeriod();
            if (idPeriod != null) {
                idPeriod.getRegMeteringDeviceRecordsCollection().remove(regMeteringDeviceRecords);
                idPeriod = em.merge(idPeriod);
            }
            RegApartmentMeteringDevice idApartmentMeteringDevice = regMeteringDeviceRecords.getIdApartmentMeteringDevice();
            if (idApartmentMeteringDevice != null) {
                idApartmentMeteringDevice.getRegMeteringDeviceRecordsCollection().remove(regMeteringDeviceRecords);
                idApartmentMeteringDevice = em.merge(idApartmentMeteringDevice);
            }
            em.remove(regMeteringDeviceRecords);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
