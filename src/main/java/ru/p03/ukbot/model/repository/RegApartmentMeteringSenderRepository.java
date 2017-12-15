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
import ru.p03.ukbot.model.ClsApartment;
import ru.p03.ukbot.model.ClsCustomer;
import ru.p03.ukbot.model.RegApartmentMeteringSender;
import ru.p03.ukbot.model.repository.exceptions.NonexistentEntityException;

/**
 *
 * @author altmf
 */
public class RegApartmentMeteringSenderRepository implements RegRepository<RegApartmentMeteringSender> {

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
        return RegApartmentMeteringSender.class;
    }

    @Override
    public EntityManager getEntityManager() {
        return getDAO().getEntityManager();
    }

    public void create(RegApartmentMeteringSender regApartmentMeteringSender) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClsApartment idApartment = regApartmentMeteringSender.getIdApartment();
            if (idApartment != null) {
                idApartment = em.getReference(idApartment.getClass(), idApartment.getId());
                regApartmentMeteringSender.setIdApartment(idApartment);
            }
            ClsCustomer idCustomer = regApartmentMeteringSender.getIdCustomer();
            if (idCustomer != null) {
                idCustomer = em.getReference(idCustomer.getClass(), idCustomer.getId());
                regApartmentMeteringSender.setIdCustomer(idCustomer);
            }
            em.persist(regApartmentMeteringSender);
            if (idApartment != null) {
                idApartment.getRegApartmentMeteringSenderCollection().add(regApartmentMeteringSender);
                idApartment = em.merge(idApartment);
            }
            if (idCustomer != null) {
                idCustomer.getRegApartmentMeteringSenderCollection().add(regApartmentMeteringSender);
                idCustomer = em.merge(idCustomer);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegApartmentMeteringSender regApartmentMeteringSender) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegApartmentMeteringSender persistentRegApartmentMeteringSender = em.find(RegApartmentMeteringSender.class, regApartmentMeteringSender.getId());
            ClsApartment idApartmentOld = persistentRegApartmentMeteringSender.getIdApartment();
            ClsApartment idApartmentNew = regApartmentMeteringSender.getIdApartment();
            ClsCustomer idCustomerOld = persistentRegApartmentMeteringSender.getIdCustomer();
            ClsCustomer idCustomerNew = regApartmentMeteringSender.getIdCustomer();
            if (idApartmentNew != null) {
                idApartmentNew = em.getReference(idApartmentNew.getClass(), idApartmentNew.getId());
                regApartmentMeteringSender.setIdApartment(idApartmentNew);
            }
            if (idCustomerNew != null) {
                idCustomerNew = em.getReference(idCustomerNew.getClass(), idCustomerNew.getId());
                regApartmentMeteringSender.setIdCustomer(idCustomerNew);
            }
            regApartmentMeteringSender = em.merge(regApartmentMeteringSender);
            if (idApartmentOld != null && !idApartmentOld.equals(idApartmentNew)) {
                idApartmentOld.getRegApartmentMeteringSenderCollection().remove(regApartmentMeteringSender);
                idApartmentOld = em.merge(idApartmentOld);
            }
            if (idApartmentNew != null && !idApartmentNew.equals(idApartmentOld)) {
                idApartmentNew.getRegApartmentMeteringSenderCollection().add(regApartmentMeteringSender);
                idApartmentNew = em.merge(idApartmentNew);
            }
            if (idCustomerOld != null && !idCustomerOld.equals(idCustomerNew)) {
                idCustomerOld.getRegApartmentMeteringSenderCollection().remove(regApartmentMeteringSender);
                idCustomerOld = em.merge(idCustomerOld);
            }
            if (idCustomerNew != null && !idCustomerNew.equals(idCustomerOld)) {
                idCustomerNew.getRegApartmentMeteringSenderCollection().add(regApartmentMeteringSender);
                idCustomerNew = em.merge(idCustomerNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = regApartmentMeteringSender.getId();
                if (findRegApartmentMeteringSender(id) == null) {
                    throw new NonexistentEntityException("The regApartmentMeteringSender with id " + id + " no longer exists.");
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
            RegApartmentMeteringSender regApartmentMeteringSender;
            try {
                regApartmentMeteringSender = em.getReference(RegApartmentMeteringSender.class, id);
                regApartmentMeteringSender.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regApartmentMeteringSender with id " + id + " no longer exists.", enfe);
            }
            ClsApartment idApartment = regApartmentMeteringSender.getIdApartment();
            if (idApartment != null) {
                idApartment.getRegApartmentMeteringSenderCollection().remove(regApartmentMeteringSender);
                idApartment = em.merge(idApartment);
            }
            ClsCustomer idCustomer = regApartmentMeteringSender.getIdCustomer();
            if (idCustomer != null) {
                idCustomer.getRegApartmentMeteringSenderCollection().remove(regApartmentMeteringSender);
                idCustomer = em.merge(idCustomer);
            }
            em.remove(regApartmentMeteringSender);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
