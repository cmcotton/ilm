/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cht.analyzer.Analyzer;
import com.cht.analyzer.Loader;
import com.cht.db.MySQLDBConnection;
import com.cht.entity.FillinEntity;
import com.cht.entity.RegulationEntity;
import com.cht.entity.RuleEntity;
import com.cht.service.RegulationManager;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：ReportServiceImpl.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */

@Service
public class RegulationManagerImpl implements RegulationManager {
        
    private Logger logger = LoggerFactory.getLogger(RegulationManagerImpl.class);

    /* (non-Javadoc)
     * @see com.cht.service.ReportService#getRegulation()
     */
    @Override
    public List<RegulationEntity> getRegulation() {
        
        MySQLDBConnection db = new MySQLDBConnection();
        List<RegulationEntity> regus = db.queryRegulation();
        return regus;
        
    }    
    

    /* (non-Javadoc)
     * @see com.cht.service.RegulationMgtService#getRule()
     */
    @Override
    public List<RuleEntity> getRule(String regu) {
//        MySQLDBConnection db = new MySQLDBConnection();
//        List<RuleEntity> rules = db.queryRule(regu);
        List<RuleEntity> rules = new ArrayList<>();
        
        Loader loader = new Loader();
        
        List<Analyzer> analyzers = loader.loadRule();
        
        analyzers.forEach(a -> {
                RuleEntity e = new RuleEntity();
                e.setNo(a.getRuleId());
                e.setName(a.getName());
                
                rules.add(e);
            });
        
        
        
        return rules;
    }


    /* (non-Javadoc)
     * @see com.cht.service.RegulationManager#modifyApplicable(java.lang.String, java.lang.String[])
     */
    /**
     * @param oper operations
     * @param regus regulations to be modified
     */
    @Override
    public void modifyApplicable(String oper, String[] regus) {
        MySQLDBConnection db = new MySQLDBConnection();// MySQL
        db.updateRegulationApplicable(oper, regus);
    }


    /* (non-Javadoc)
     * @see com.cht.service.RegulationManager#fillinRule(java.lang.String, java.lang.String)
     */
    @Override
    public void fillinRule(String oper, String rule, String attachment) {
        MySQLDBConnection db = new MySQLDBConnection();// MySQL
        
        FillinEntity entiy = new FillinEntity();
        entiy.setSubmitter("柯交哲");
        entiy.setRule(rule);
        entiy.setAction(oper);
        entiy.setAttachment(attachment);
        
        db.insertFillin(entiy);
    }

    /* (non-Javadoc)
     * @see com.cht.service.RegulationManager#goPage(java.lang.String)
     */
    @Override
    public List goPage(String rule) {
        
        MySQLDBConnection db = new MySQLDBConnection();// MySQL
        List<FillinEntity> datalist = db.queryFillin(rule);
        return datalist;
    }

}
