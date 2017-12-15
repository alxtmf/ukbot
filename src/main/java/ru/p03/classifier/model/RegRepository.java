/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.classifier.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 *
 * @author altmf
 * @param <T>
 */
public interface RegRepository<T extends IBean> extends Serializable {

    void create(T reg);

    void destroy(Long id) throws Exception;

    void edit(T reg) throws Exception;

    default T find(Long id){
        EntityManager em = getEntityManager();
        try {
            T find = (T)em.find(getRegClass(), id);
            return find;
        } finally {
            em.close();
        }
    }
    
    default List<T> find(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(getRegClass()));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    default List<T> find(){
        return find(true, -1, -1);
    }

    default List<T> find(int maxResults, int firstResult){
        return find(false, maxResults, firstResult);
    }

    EntityManager getEntityManager();
    
    Class getRegClass();

    default int getCount(){
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<T> rt = cq.from(getRegClass());
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
