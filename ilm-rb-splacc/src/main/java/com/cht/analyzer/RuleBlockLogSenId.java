/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.analyzer;

import java.util.ArrayList;
import java.util.List;

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
public class RuleBlockLogSenId implements RuleBlock {
    
    private String id = "rbFile3"; 
    private String name = "VIP 客戶-從DB";
    
    public List importData() {
        List<String> list = new ArrayList<>();
        
        list.add("N0001");
        
        return list;        
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

