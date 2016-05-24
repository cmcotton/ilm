/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cht.analyzer.Analyzer;
import com.cht.analyzer.Loader;
import com.cht.analyzer.RuleBlock;
import com.cht.db.CassandraConnection;
import com.cht.db.MySQLDBConnection;
import com.cht.decorator.RuleChain;
import com.cht.decorator.RuleChainBuilder;
import com.cht.entity.EventEntity;
import com.cht.entity.LoginLogEntity;
import com.cht.entity.RuleChainEntity;
import com.cht.entity.RuleEntity;
import com.cht.service.RuleManager;

/**
 * 程式資訊摘要：
 * <P>
 * 類別名稱　　：ReportServiceImpl.java
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

@Service
public class RuleManagerImpl implements RuleManager {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private List riskEvents;
    
    List<RuleBlock> ruleBlocks;
    
    List<Analyzer> rulePlugins;
    
    private Map<String, List> ruleChains;

    private ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");   
    
    /*
     * (non-Javadoc)
     * 
     * @see com.cht.service.RuleManager#runAnalysis()
     */
    public void runAnalysis(String ruleId) {
        Loader loader = new Loader();
        List<Analyzer> analyzers = loader.loadRule();

        MySQLDBConnection db = new MySQLDBConnection();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

        
        try {
            Date[] period = { sdf.parse("2015-08-19 00:00:00"), sdf.parse("2015-10-20 00:00:00") };
            
            final List<LoginLogEntity> source = db.queryLoginLog(period);
            
            // run every analyzer, should be in batch job
            analyzers.forEach(a -> run(a, ruleId, source));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.riskEvents = null;        
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cht.service.RuleManager#getTableData(java.lang.String)
     */
    @Override
    public List getTableData(String ruleId) {

        logger.debug("rule: {}", ruleId);

        runAnalysis(ruleId);

        return this.riskEvents;
    }

    private void run(Analyzer a, String ruleId, List source) {
        if (ruleId.equals(a.getRuleId())) {
            List riskEvents = a.analyze(source); 
            this.riskEvents = riskEvents;
        }
    }

    /* (non-Javadoc)
     * @see com.cht.service.RuleManager#loadRuleBlock()
     */
    @Override
    public List<RuleBlock> loadRuleBlock() {
        
        if (ruleBlocks == null) {
            Loader loader = new Loader();
            ruleBlocks = loader.loadRuleBlock();
        }
        
        ruleBlocks.forEach(rb -> {            
                logger.info("rule block: {}", rb.getName());
                logger.info("123");
            }
        );
        
        return ruleBlocks;
    }
    
    /* (non-Javadoc)
     * @see com.cht.service.RegulationManager#fillinRule(java.lang.String, java.lang.String)
     */
    @Override
    public void runRuleChain(RuleChainEntity ruleChain) {
        
        RuleChain rc = RuleChainBuilder.build(ruleChain);
        rc.run();        
    }
    
    @Override
    public void runRule(String ruleId) {
        
        Loader loader = new Loader();
        if (rulePlugins == null) {
            rulePlugins = loader.loadRule();
        }
        
        List<Analyzer> targetRule = rulePlugins.stream().filter(r -> r.getRuleId().equals(ruleId)).collect(Collectors.toList());
        if (targetRule.size() == 1) {
            List<EventEntity> results = targetRule.get(0).analyze(null);
            logger.info("finish to run rule {}", targetRule.get(0).getName());
             
            // persist
            MySQLDBConnection db = new MySQLDBConnection();
            CassandraConnection cass = new CassandraConnection();
            
            results.forEach(
                evt -> {
                    db.insertEvent(evt);
                    cass.insertEvent(evt);
            });
            
            String[] ids = {ruleId};
            
            // 
            if (results.size() > 0) {
                db.updateRegulationPass(0, ids);
                db.updateRegulationVio(1, ids);
            } else {
                db.updateRegulationPass(1, ids);
                db.updateRegulationVio(0, ids);
            }
          
            db.close(db.getConnection());
            
        } else {
            logger.error("rule {} NOT found", ruleId);
        }
    }
    
    @Override
    public List<RuleEntity> getRule(String regu) {
        List<RuleEntity> rules = new ArrayList<>();
        
        Loader loader = new Loader();
        
        if (rulePlugins == null) {
            rulePlugins = loader.loadRule();
        }
        
        rulePlugins.forEach(a -> {
                RuleEntity e = new RuleEntity();
                e.setNo(a.getRuleId());
                e.setName(a.getName());
                e.setDesc(a.getName());
                rules.add(e);
            });
        
        return rules;
    }
    
    
    private void writeFile(String fileName, List<String> data) {
        try {
            File file = new File("d:/" + fileName + ".txt");
            FileWriter fileWriter = new FileWriter(file);
            try {
                for (String s : data) {
                    fileWriter.write(s + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see com.cht.service.RuleManager#addRule(com.cht.entity.RuleChain)
     */
    @Transactional
    @Override
    public void addRuleChain(RuleChainEntity ruleChain) {
        logger.info("start");
     
        MySQLDBConnection db = new MySQLDBConnection();
        db.insertRuleChain(ruleChain);
        
        logger.info("end");
    }
    
    @Transactional
    @Override
    public void delRuleChain(RuleChainEntity ruleChain) {
        logger.info("start");
     
        MySQLDBConnection db = new MySQLDBConnection();
        db.deleteRuleChain(ruleChain);
        
        logger.info("end");
    }

    /* (non-Javadoc)
     * @see com.cht.service.RuleManager#loadRuleChain()
     */
    @Override
    public List<RuleChainEntity> loadRuleChain() {
        logger.info("start");
        
        MySQLDBConnection db = new MySQLDBConnection();
        List<RuleChainEntity> ruleChains = db.queryRuleChain();
        
        logger.info("end");
        
        return ruleChains;
    }
   
}
