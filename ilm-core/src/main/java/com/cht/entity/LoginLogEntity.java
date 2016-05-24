/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.entity;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：RegulationEntity.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class LoginLogEntity {
    
    private String id;    
    
    private String userid;
    
    private String datetime;
    
    private String action;
    
    private long ip;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
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

    
}
