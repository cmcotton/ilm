/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.service;

import java.util.List;

import com.cht.analyzer.RuleBlock;
import com.cht.entity.RuleChainEntity;
import com.cht.entity.RuleEntity;


/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：ReportService.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public interface RuleManager {
    
    List getTableData(String rule);
    
    void runAnalysis(String ruleId);
    
    List<RuleBlock> loadRuleBlock();
    
    List<RuleEntity> getRule(String regu);

    // rule chain
    List<RuleChainEntity> loadRuleChain();
    
    void addRuleChain(RuleChainEntity ruleChain);
    
    void delRuleChain(RuleChainEntity ruleChain);
    
    void runRule(String ruleId);
    
    void runRuleChain(RuleChainEntity ruleId);
}
