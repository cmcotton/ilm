/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.analyzer;

import java.io.File;
import java.util.List;

import com.cht.io.FileManager;

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
public class RuleBlockLogFile implements RuleBlock {
    
    private String id = "rbFile1"; 
    private String name = "操作紀錄清單-從檔案";
    
    public List importData() {
         
        List<String> list = null;
        try {
            FileManager fm = new FileManager();
            list = fm.readLines(new File("d:/secure"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
    
    
    
    public static void main(String[] args) {
        new RuleBlockLogFile().importData();
    }

}

