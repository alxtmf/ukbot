/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.model.repository;

import com.google.inject.Inject;
import javax.persistence.EntityNotFoundException;
import ru.p03.ukbot.model.ClsApartment;
import ru.p03.ukbot.model.ClsMeteringDeviceType;
import ru.p03.ukbot.model.RegMeteringDeviceRecords;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import ru.p03.ukbot.model.RegApartmentMeteringDevice;
import ru.p03.ukbot.model.repository.exceptions.NonexistentEntityException;
import ru.p03.classifier.model.RegRepository;
import ru.p03.common.util.QueriesEngine;

/**
 *
 * @author altmf
 */
public class RegApartmentMeteringDeviceRepository implements RegRepository<RegApartmentMeteringDevice> {

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
    public EntityManager getEntityManager() {
        return getDAO().getEntityManager();
    }

    @Override
    public void create(RegApartmentMeteringDevice regApartmentMeteringDevice) {
        if (regApartmentMeteringDevice.getRegMeteringDeviceRecordsCollection() == null) {
            regApartmentMeteringDevice.setRegMeteringDeviceRecordsCollection(new ArrayList<RegMeteringDeviceRecords>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClsApartment idApartment = regApartmentMeteringDevice.getIdApartment();
            if (idApartment != null) {
                idApartment = em.getReference(idApartment.getClass(), idApartment.getId());
                regApartmentMeteringDevice.setIdApartment(idApartment);
            }
            ClsMeteringDeviceType idMeteringDeviceType = regApartmentMeteringDevice.getIdMeteringDeviceType();
            if (idMeteringDeviceType != null) {
                idMeteringDeviceType = em.getReference(idMeteringDeviceType.getClass(), idMeteringDeviceType.getId());
                regApartmentMeteringDevice.setIdMeteringDeviceType(idMeteringDeviceType);
            }
            Collection<RegMeteringDeviceRecords> attachedRegMeteringDeviceRecordsCollection = new ArrayList<RegMeteringDeviceRecords>();
            for (RegMeteringDeviceRecords regMeteringDeviceRecordsCollectionRegMeteringDeviceRecordsToAttach : regApartmentMeteringDevice.getRegMeteringDeviceRecordsCollection()) {
                regMeteringDeviceRecordsCollectionRegMeteringDeviceRecordsToAttach = em.getReference(regMeteringDeviceRecordsCollectionRegMeteringDeviceRecordsToAttach.getClass(), regMeteringDeviceRecordsCollectionRegMeteringDeviceRecordsToAttach.getId());
                attachedRegMeteringDeviceRecordsCollection.add(regMeteringDeviceRecordsCollectionRegMeteringDeviceRecordsToAttach);
            }
            regApartmentMeteringDevice.setRegMeteringDeviceRecordsCollection(attachedRegMeteringDeviceRecordsCollection);
            em.persist(regApartmentMeteringDevice);
//            if (idApartment != null) {
//                idApartment.getRegApartmentMeteringDeviceCollection().add(regApartmentMeteringDevice);
//                idApartment = em.merge(idApartment);
//            }
//            if (idMeteringDeviceType != null) {
//                idMeteringDeviceType.getRegApartmentMeteringDeviceCollection().add(regApartmentMeteringDevice);
//                idMeteringDeviceType = em.merge(idMeteringDeviceType);
//            }
//            for (RegMeteringDeviceRecords regMeteringDeviceRecordsCollectionRegMeteringDeviceRecords : regApartmentMeteringDevice.getRegMeteringDeviceRecordsCollection()) {
//                RegApartmentMeteringDevice oldIdApartmentMeteringDeviceOfRegMeteringDeviceRecordsCollectionRegMeteringDeviceRecords = regMeteringDeviceRecordsCollectionRegMeteringDeviceRecords.getIdApartmentMeteringDevice();
//                regMeteringDeviceRecordsCollectionRegMeteringDeviceRecords.setIdApartmentMeteringDevice(regApartmentMeteringDevice);
//                regMeteringDeviceRecordsCollectionRegMeteringDeviceRecords = em.merge(regMeteringDeviceRecordsCollectionRegMeteringDeviceRecords);
//                if (oldIdApartmentMeteringDeviceOfRegMeteringDeviceRecordsCollectionRegMeteringDeviceRecords != null) {
//                    oldIdApartmentMeteringDeviceOfRegMeteringDeviceRecordsCollectionRegMeteringDeviceRecords.getRegMeteringDeviceRecordsCollection().remove(regMeteringDeviceRecordsCollectionRegMeteringDeviceRecords);
//                    oldIdApartmentMeteringDeviceOfRegMeteringDeviceRecordsCollectionRegMeteringDeviceRecords = em.merge(oldIdApartmentMeteringDeviceOfRegMeteringDeviceRecordsCollectionRegMeteringDeviceRecords);
//                }
//            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(RegApartmentMeteringDevice regApartmentMeteringDevice) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegApartmentMeteringDevice persistentRegApartmentMeteringDevice = em.find(RegApartmentMeteringDevice.class, regApartmentMeteringDevice.getId());
            ClsApartment idApartmentOld = persistentRegApartmentMeteringDevice.getIdApartment();
            ClsApartment idApartmentNew = regApartmentMeteringDevice.getIdApartment();
            ClsMeteringDeviceType idMeteringDeviceTypeOld = persistentRegApartmentMeteringDevice.getIdMeteringDeviceType();
            ClsMeteringDeviceType idMeteringDeviceTypeNew = regApartmentMeteringDevice.getIdMeteringDeviceType();
            Collection<RegMeteringDeviceRecords> regMeteringDeviceRecordsCollectionOld = persistentRegApartmentMeteringDevice.getRegMeteringDeviceRecordsCollection();
            Collection<RegMeteringDeviceRecords> regMeteringDeviceRecordsCollectionNew = regApartmentMeteringDevice.getRegMeteringDeviceRecordsCollection();
            if (idApartmentNew != null) {
                idApartmentNew = em.getReference(idApartmentNew.getClass(), idApartmentNew.getId());
                regApartmentMeteringDevice.setIdApartment(idApartmentNew);
            }
            if (idMeteringDeviceTypeNew != null) {
                idMeteringDeviceTypeNew = em.getReference(idMeteringDeviceTypeNew.getClass(), idMeteringDeviceTypeNew.getId());
                regApartmentMeteringDevice.setIdMeteringDeviceType(idMeteringDeviceTypeNew);
            }
            Collection<RegMeteringDeviceRecords> attachedRegMeteringDeviceRecordsCollectionNew = new ArrayList<RegMeteringDeviceRecords>();
            for (RegMeteringDeviceRecords regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecordsToAttach : regMeteringDeviceRecordsCollectionNew) {
                regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecordsToAttach = em.getReference(regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecordsToAttach.getClass(), regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecordsToAttach.getId());
                attachedRegMeteringDeviceRecordsCollectionNew.add(regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecordsToAttach);
            }
            regMeteringDeviceRecordsCollectionNew = attachedRegMeteringDeviceRecordsCollectionNew;
            regApartmentMeteringDevice.setRegMeteringDeviceRecordsCollection(regMeteringDeviceRecordsCollectionNew);
            regApartmentMeteringDevice = em.merge(regApartmentMeteringDevice);
//            if (idApartmentOld != null && !idApartmentOld.equals(idApartmentNew)) {
//                idApartmentOld.getRegApartmentMeteringDeviceCollection().remove(regApartmentMeteringDevice);
//                idApartmentOld = em.merge(idApartmentOld);
//            }
//            if (idApartmentNew != null && !idApartmentNew.equals(idApartmentOld)) {
//                idApartmentNew.getRegApartmentMeteringDeviceCollection().add(regApartmentMeteringDevice);
//                idApartmentNew = em.merge(idApartmentNew);
//            }
//            if (idMeteringDeviceTypeOld != null && !idMeteringDeviceTypeOld.equals(idMeteringDeviceTypeNew)) {
//                idMeteringDeviceTypeOld.getRegApartmentMeteringDeviceCollection().remove(regApartmentMeteringDevice);
//                idMeteringDeviceTypeOld = em.merge(idMeteringDeviceTypeOld);
//            }
//            if (idMeteringDeviceTypeNew != null && !idMeteringDeviceTypeNew.equals(idMeteringDeviceTypeOld)) {
//                idMeteringDeviceTypeNew.getRegApartmentMeteringDeviceCollection().add(regApartmentMeteringDevice);
//                idMeteringDeviceTypeNew = em.merge(idMeteringDeviceTypeNew);
//            }
//            for (RegMeteringDeviceRecords regMeteringDeviceRecordsCollectionOldRegMeteringDeviceRecords : regMeteringDeviceRecordsCollectionOld) {
//                if (!regMeteringDeviceRecordsCollectionNew.contains(regMeteringDeviceRecordsCollectionOldRegMeteringDeviceRecords)) {
//                    regMeteringDeviceRecordsCollectionOldRegMeteringDeviceRecords.setIdApartmentMeteringDevice(null);
//                    regMeteringDeviceRecordsCollectionOldRegMeteringDeviceRecords = em.merge(regMeteringDeviceRecordsCollectionOldRegMeteringDeviceRecords);
//                }
//            }
//            for (RegMeteringDeviceRecords regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords : regMeteringDeviceRecordsCollectionNew) {
//                if (!regMeteringDeviceRecordsCollectionOld.contains(regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords)) {
//                    RegApartmentMeteringDevice oldIdApartmentMeteringDeviceOfRegMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords = regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords.getIdApartmentMeteringDevice();
//                    regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords.setIdApartmentMeteringDevice(regApartmentMeteringDevice);
//                    regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords = em.merge(regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords);
//                    if (oldIdApartmentMeteringDeviceOfRegMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords != null && !oldIdApartmentMeteringDeviceOfRegMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords.equals(regApartmentMeteringDevice)) {
//                        oldIdApartmentMeteringDeviceOfRegMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords.getRegMeteringDeviceRecordsCollection().remove(regMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords);
//                        oldIdApartmentMeteringDeviceOfRegMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords = em.merge(oldIdApartmentMeteringDeviceOfRegMeteringDeviceRecordsCollectionNewRegMeteringDeviceRecords);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = regApartmentMeteringDevice.getId();
                if (find(id) == null) {
                    throw new NonexistentEntityException("The regApartmentMeteringDevice with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegApartmentMeteringDevice regApartmentMeteringDevice;
            try {
                regApartmentMeteringDevice = em.getReference(RegApartmentMeteringDevice.class, id);
                regApartmentMeteringDevice.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regApartmentMeteringDevice with id " + id + " no longer exists.", enfe);
            }
            ClsApartment idApartment = regApartmentMeteringDevice.getIdApartment();
            if (idApartment != null) {
                idApartment.getRegApartmentMeteringDeviceCollection().remove(regApartmentMeteringDevice);
                idApartment = em.merge(idApartment);
            }
            ClsMeteringDeviceType idMeteringDeviceType = regApartmentMeteringDevice.getIdMeteringDeviceType();
            if (idMeteringDeviceType != null) {
                idMeteringDeviceType.getRegApartmentMeteringDeviceCollection().remove(regApartmentMeteringDevice);
                idMeteringDeviceType = em.merge(idMeteringDeviceType);
            }
            Collection<RegMeteringDeviceRecords> regMeteringDeviceRecordsCollection = regApartmentMeteringDevice.getRegMeteringDeviceRecordsCollection();
            for (RegMeteringDeviceRecords regMeteringDeviceRecordsCollectionRegMeteringDeviceRecords : regMeteringDeviceRecordsCollection) {
                regMeteringDeviceRecordsCollectionRegMeteringDeviceRecords.setIdApartmentMeteringDevice(null);
                regMeteringDeviceRecordsCollectionRegMeteringDeviceRecords = em.merge(regMeteringDeviceRecordsCollectionRegMeteringDeviceRecords);
            }
            em.remove(regApartmentMeteringDevice);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Class getRegClass() {
        return RegApartmentMeteringDevice.class;
    }

}
