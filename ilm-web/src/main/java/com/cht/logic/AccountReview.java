/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cht.db.MySQLDBConnection;
import com.cht.util.MyPropertyReader;


/**
 * 程式資訊摘要：
 * 類別名稱　　：AccountReview.java
 * 程式內容說明：
 * 程式修改記錄：
 * XXXX-XX-XX：
 * @author chtd
 * @version 1.0
 * @since 1.0
 */
public class AccountReview implements IAccountReport {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public void execute() {
//        MySQLDBConnection db = new MySQLDBConnection();
//        Connection conDB = db.getConnection();
//        ResultSet rs = null;
//        if (conDB != null) {
//            rs = db.query(conDB);          
//        } else {
//            return;
//        }
//        
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        List<String> title = Arrays.asList(sdf.format(new Date()), "", "", "", "系統帳號清冊", "", "", "", "機密等級:機密級", "");
//        List<String> header = Arrays.asList("管理員", "主機名稱", "類型", "帳號名稱", "群組", "使用者", "單位", "使用中?", "權限類型", "用途");            
//        List<String> footer = Arrays.asList("管理單位組長:", "", "", "", "", "", "使用單位", "組長:", "科長:", "");
//        
//        ExcelDataWriter edw = new ExcelDataWriter();
//        String reportPath = null;
//        
//        PropertyReader pr = new PropertyReader();        
//        try {
//            reportPath = pr.getPropValues(PropertyReader.REPORT_PATH);
//        } catch (IOException e1) {
//            logger.error(e1.getMessage());
//        }
//        
//        List<List<String>> contents = new ArrayList<>();
//        int i = 0;
//        String lastOwnerUnit = "";
//        // iterate through the java resultset
//        try {
//            
//            while (rs.next()) {
//                String curOwnerUnit = rs.getString("OwnerUnit").replaceAll("/", "");
//                if (!lastOwnerUnit.equals("") && !lastOwnerUnit.equals(curOwnerUnit)) {
//                    edw.exportOneSheet(reportPath + "accountreview_" + i + ".xlsx", "系統帳號清冊", header, title, contents, footer);
//                    contents.clear();
//                    i++;
//                }                
//                
//                List<String> row = Arrays.asList(rs.getString("SystemAdmin"), rs.getString("HostName"),
//                        rs.getString("AccountType"), rs.getString("AccountName"), rs.getString("GroupName"),
//                        rs.getString("Owner"), rs.getString("OwnerUnit"), rs.getString("Active"),
//                        rs.getString("Privilege"), rs.getString("purpose"));
//    
//                contents.add(row);
//                
//                lastOwnerUnit = curOwnerUnit;
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        } finally {
//            db.close(conDB);
//        }
//
//        edw.exportOneSheet(reportPath + "accountreview_" + i + ".xlsx", "系統帳號清冊", header, title, contents, footer);
//        
//        logger.debug("End");
    }
}
