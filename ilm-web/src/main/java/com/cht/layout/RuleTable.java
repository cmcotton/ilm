/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.layout;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cht.db.MySQLDBConnection;
import com.cht.entity.RuleEntity;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：ReportTable.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class RuleTable implements Layout {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /* (non-Javadoc)
     * @see com.cht.layout.ILayout#arrange()
     */
    @Override
    public List arrange() {
        
        List dataList = new ArrayList<>();
        
        SecureRandom ran = null;
        try {
            ran = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }        
        int ranInt = ran.nextInt(3);
        
        logger.debug(">>>ranInt: {}", ranInt);
        
        MySQLDBConnection db = new MySQLDBConnection();
        List<RuleEntity> regus = db.queryRule(new Integer(ranInt).toString());
        
        logger.debug("Rules: {}", regus.size());
        
        for (RuleEntity e : regus) {
            logger.debug(">>>: {},{},{}.", e.getNo(), e.getName(), e.getDesc());
            
            List<String> l = Arrays.asList(e.getNo(), e.getName(), e.getDesc());
            dataList.add(l);
        }
        
        return dataList;
    }
    
    public List<String> renderDataTable(List<RuleEntity> data) {
        List dataList = new ArrayList<>();
                
        for (RuleEntity e : data) {
            logger.debug(">>>: {},{},{}.", e.getNo(), e.getName(), e.getDesc());
            
            List<String> l = Arrays.asList(e.getNo(), e.getName(), e.getDesc());
            dataList.add(l);
        }
        
        return dataList; 
    }

}
