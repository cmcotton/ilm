/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.service.impl;

import java.util.ArrayList;
import java.util.List;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cht.db.MySQLDBConnection;
import com.cht.entity.EventEntity;
import com.cht.service.EventManager;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：EventManagerImpl.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author su
 *@version 1.0
 *@since 1.0
 */
@Service
public class EventManagerImpl implements EventManager {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /* (non-Javadoc)
     * @see com.cht.service.EventManager#queryEvent()
     */
    @Override
    public List<EventEntity> queryEvent(String ruleId, String date1, String date2) {
        List<EventEntity> results = new ArrayList<EventEntity>();
        
        try {
            MySQLDBConnection db = new MySQLDBConnection();
            results = db.queryEvent(ruleId, date1, date2);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        return results;
    }

    /* (non-Javadoc)
     * @see com.cht.service.EventManager#queryRecentEvent(java.lang.String)
     */
    @Override
    public List<EventEntity> queryRecentEvent(int period) {
        List<EventEntity> results = new ArrayList<EventEntity>();
        
        try {
            MySQLDBConnection db = new MySQLDBConnection();
            results = db.queryRecentEvent(period);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        return results;
    }

}
