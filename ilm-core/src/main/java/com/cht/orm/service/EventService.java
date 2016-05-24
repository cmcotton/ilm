/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.orm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cht.dao.EventDao;
import com.cht.entity.EventEntity;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：EventService.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
@Service
public class EventService {

    @Resource
    private EventDao eventDao;
    
    @Transactional
    public void add(EventEntity event) {
        eventDao.persist(event);
    }
    
    public List<EventEntity> findAll() {
        return eventDao.findAll();
    }
}
