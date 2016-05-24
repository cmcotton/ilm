/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.service;

import java.util.List;

import com.cht.entity.EventEntity;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：EventManager.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author su
 *@version 1.0
 *@since 1.0
 */
public interface EventManager {
    
    List<EventEntity> queryEvent(String ruleId, String date1, String date2);
    
    List<EventEntity> queryRecentEvent(int period);
}
