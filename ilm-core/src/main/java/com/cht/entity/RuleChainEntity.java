package com.cht.entity;
/*
 * 版權宣告: FDC all rights reserved.
 */

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：Command.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class RuleChainEntity {
    String id;
    String name;
    
    String operand1;
    String operand1Name;
    
    String oper;
    
    String operand2;
    String operand2Name;
    
    String threshold;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public RuleChainEntity setId(String id) {
        this.id = id;
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
    public RuleChainEntity setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return the operand1
     */
    public String getOperand1() {
        return operand1;
    }

    /**
     * @param operand1 the operand1 to set
     */
    public RuleChainEntity setOperand1(String operand1) {
        this.operand1 = operand1;
        return this;
    }

    /**
     * @return the oper
     */
    public String getOper() {
        return oper;
    }

    /**
     * @param oper the oper to set
     */
    public RuleChainEntity setOper(String oper) {
        this.oper = oper;
        return this;
    }

    /**
     * @return the operand2
     */
    public String getOperand2() {
        return operand2;
    }

    /**
     * @param operand2 the operand2 to set
     */
    public RuleChainEntity setOperand2(String operand2) {
        this.operand2 = operand2;
        return this;
    }

    /**
     * @return the operand1Name
     */
    public String getOperand1Name() {
        return operand1Name;
    }

    /**
     * @param operand1Name the operand1Name to set
     */
    public RuleChainEntity setOperand1Name(String operand1Name) {
        this.operand1Name = operand1Name;
        return this;
    }

    /**
     * @return the operand2Name
     */
    public String getOperand2Name() {
        return operand2Name;
    }

    /**
     * @param operand2Name the operand2Name to set
     */
    public RuleChainEntity setOperand2Name(String operand2Name) {
        this.operand2Name = operand2Name;
        return this;
    }

    /**
     * @return the threshold
     */
    public String getThreshold() {
        return threshold;
    }

    /**
     * @param threshold the threshold to set
     */
    public RuleChainEntity setThreshold(String threshold) {
        this.threshold = threshold;
        return this;
    }

    
}
