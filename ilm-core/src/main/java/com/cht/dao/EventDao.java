/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.cht.entity.EventEntity;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：EventDao.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
@Repository
public class EventDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public void persist(EventEntity event) {
        em.persist(event);
    }
    
    public List<EventEntity> findAll() {
        return em.createQuery("Select * from event").getResultList();
    }

}
