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
public class RuleEntity {
    
    private String no;    
    
    private String name;
    
    private String desc;
    
    private String appliedRegu;
    
    private String property;

    /**
     * @return the no
     */
    public String getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(String no) {
        this.no = no;
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
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the appliedRegu
     */
    public String getAppliedRegu() {
        return appliedRegu;
    }

    /**
     * @param appliedRegu the appliedRegu to set
     */
    public void setAppliedRegu(String appliedRegu) {
        this.appliedRegu = appliedRegu;
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    
}
