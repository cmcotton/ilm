/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cht.util.MyPropertyReader;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：FileManager.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class FileManager {
        
    private Logger logger = LoggerFactory.getLogger(FileManager.class);
    
    public File[] getFiles() {
        
        MyPropertyReader pr = new MyPropertyReader();
        String reportPath = null;
        try {
            reportPath = pr.getPropValues(MyPropertyReader.REPORT_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        File filePath = new File(reportPath);
        FileFilter filter = new FileFilter() {
            
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().contains("accountreview")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        
        return filePath.listFiles(filter);
    }
    
    public List<String> readLines(File file) throws Exception {
        if (!file.exists()) {
            return new ArrayList<String>();
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> results = new ArrayList<String>();
        String line = reader.readLine();
        while (line != null) {
            /*  暫時取得全部的字串
            String[] tokens = line.split(" ");
            try {
                String loginId = tokens[3];           
                results.add(loginId);
            } catch (Exception e) {
                logger.error(e.toString());
            }
            */
            results.add(line);
            line = reader.readLine();
        }
        return results;
    }

}
