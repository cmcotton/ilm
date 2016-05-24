/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 程式資訊摘要：
 * <P>
 * 類別名稱　　：Loader.java
 * <P>
 * 程式內容說明：
 * <P>
 * 程式修改記錄：
 * <P>
 * XXXX-XX-XX：
 * <P>
 * 
 * @author chtd
 * @version 1.0
 * @since 1.0
 */
public class Loader {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    public List<Analyzer> loadRule() {
        // 載入 ServiceLoader.

        ServiceLoader<Analyzer> serviceLoader = ServiceLoader.load(Analyzer.class);
        List analyzers = new ArrayList();
        
        for (Analyzer service : serviceLoader) {
            logger.info("loading " + service.toString());
            analyzers.add(service);
        }
        
        
        
        return analyzers;
    }
    
    public List<RuleBlock> loadRuleBlock() {
        
        // 載入 ServiceLoader.
        ServiceLoader<RuleBlock> serviceLoader = ServiceLoader.load(RuleBlock.class);
        List<RuleBlock> ruleBlocks = new ArrayList<RuleBlock>();
        
        for (RuleBlock service : serviceLoader) {
            logger.info("loading " + service.toString());
            ruleBlocks.add(service);
        }
        
        return ruleBlocks;
    }

}
