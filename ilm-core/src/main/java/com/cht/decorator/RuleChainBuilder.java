/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.decorator;

import java.util.Arrays;
import java.util.List;

import com.cht.entity.RuleChainEntity;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：RuleChainBuilder.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author su
 *@version 1.0
 *@since 1.0
 */
public class RuleChainBuilder {
    
    public static RuleChain build(RuleChainEntity e) {
        RuleChain rc = null;
        
        String ruleId = e.getId();
        
        List<String> operands = Arrays.asList(ruleId.split(":"));
        
//        rc = new RuleChain(e);
        
        if (operands.size() == 3) {
            rc = new RuleChain(e);
        } else {
            for (int i = operands.size() - 1; i > 0; i -= 2) {
                RuleChain next = new RuleChain(operands.get(i - 1), operands.get(i)); // last block
                next.setRuleId(ruleId);
                
                if (i < 5) {
                    rc = new RuleChain(operands.get(0), operands.get(1), operands.get(2));
                    rc.setNext(next);
                    break;
                } else {
                    rc = new RuleChain(operands.get(i - 3), operands.get(i - 2));
                    rc.setNext(next);
                }
            }
        }
        
        return rc;
    }
}