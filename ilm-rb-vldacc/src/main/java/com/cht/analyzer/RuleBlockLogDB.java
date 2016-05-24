/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cht.db.MySQLDBConnection;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：RuleBlockLogFile.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class RuleBlockLogDB implements RuleBlock {
    
    Logger logger  = LoggerFactory.getLogger(getClass());
    
    private String id = "rbFile2"; 
    private String name = "合法帳號清單-從DB";
    
    public List importData() {
        List<String> accounts = new ArrayList<>();
        
        try {
            MySQLDBConnection db = new MySQLDBConnection();
            accounts = db.queryAccount();
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        
        return accounts;        
    }

    /* (non-Javadoc)
     * @see com.cht.analyzer.RuleBlock#getId()
     */
    @Override
    public String getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see com.cht.analyzer.RuleBlock#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

}

