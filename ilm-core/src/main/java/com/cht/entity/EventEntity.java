/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：Event.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */

@Entity
@Table(name="event")
public class EventEntity {
    
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    private String content;
    private String ruleId;
    private Integer severity;    
    private String userId;
    private String userName;
    private String evtTime;
    private String ip;
    
    public EventEntity() {
        
    }
    
    /**
     * @param id
     * @param name
     * @param desc
     * @param tag
     * @param severity
     */
    public EventEntity(String name, String content, String ruleId, Integer severity) {
        super();
        this.name = name;
        this.content = content;
        this.ruleId = ruleId;
        this.severity = severity;
    }
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }
    /**
     * @param desc the desc to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * @return the tag
     */
    public String getRuleId() {
        return ruleId;
    }
    /**
     * @param tag the tag to set
     */
    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }
    /**
     * @return the severity
     */
    public Integer getSeverity() {
        return severity;
    }
    /**
     * @param severity the severity to set
     */
    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

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
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the evtTime
     */
    public String getEvtTime() {
        return evtTime;
    }

    /**
     * @param evtTime the evtTime to set
     */
    public void setEvtTime(String evtTime) {
        this.evtTime = evtTime;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    
}
