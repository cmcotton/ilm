/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.entity;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：SensitiveActionEntity.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class OperationLogEntity {
    
    String userId;
    
    String datetime;
    
    String action;
    
    long ip;
    
    String function;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the datetime
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     * @param datetime the datetime to set
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the ip
     */
    public long getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(long ip) {
        this.ip = ip;
    }

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
    }
    
    

}
