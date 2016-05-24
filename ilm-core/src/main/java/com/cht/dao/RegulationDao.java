/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.cht.entity.RegulationEntity;

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
public class RegulationDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public void persist(RegulationEntity newItem) {
        try {
            em.persist(newItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<RegulationEntity> findAll() {
        return em.createQuery("Select * from regulation").getResultList();
    }

}
