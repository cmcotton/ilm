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
 * 類別名稱　　：RegulationEntity.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
@Entity
@Table(name="regulation")
public class RegulationEntity {
    
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String no;
    
    private String criteria;
    
    private String name;
    
    private String desc;
    
    private String applicable;
    
    private String pass;
    
    private String nonexec;
    
    private String violate;
    
    private String property; // {auto, manual}
       

    /**
     * @return the no
     */
    public String getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public RegulationEntity setNo(String no) {
        this.no = no;
        return this;
    }

    /**
     * @return the criteria
     */
    public String getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public RegulationEntity setCriteria(String criteria) {
        this.criteria = criteria;
        return this;
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
    public RegulationEntity setName(String name) {
        this.name = name;
        return this;
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
    public RegulationEntity setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    /**
     * @return the applicable
     */
    public String getApplicable() {
        return applicable;
    }

    /**
     * @param aplpicable the applicable to set
     */
    public RegulationEntity setApplicable(String applicable) {
        this.applicable = applicable;
        return this;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public RegulationEntity setPass(String pass) {
        this.pass = pass;
        return this;
    }

    /**
     * @return the nonexec
     */
    public String getNonexec() {
        return nonexec;
    }

    /**
     * @param nonexec the nonexec to set
     */
    public RegulationEntity setNonexec(String nonexec) {
        this.nonexec = nonexec;
        return this;
    }

    /**
     * @return the violate
     */
    public String getViolate() {
        return violate;
    }

    /**
     * @param violate the violate to set
     */
    public RegulationEntity setViolate(String violate) {
        this.violate = violate;
        return this;
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
    public RegulationEntity setProperty(String property) {
        this.property = property;
        return this;
    }

    
}
