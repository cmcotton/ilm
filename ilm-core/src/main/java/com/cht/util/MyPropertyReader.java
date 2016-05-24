/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Properties;

/**
 * 程式資訊摘要：
 * <P>
 * 類別名稱　　：PropertyReader.java
 * <P>
 * 程式內容說明：
 * <P>
 * 程式修改記錄：
 * <P>
 * XXXX-XX-XX：
 * <P>
 * @author chtd
 * @version 1.0
 * @since 1.0
 */
public class MyPropertyReader {
    
    public static String REPORT_PATH = "REPORT_PATH";
    public static String DB_USER = "DB_USER";
    public static String DB_ADV = "DB_ADV";
    
    public enum Env {DEV, PRODUCTION}
    

    public String getPropValues(String key) throws IOException {

        String result = "";
                        
        Properties prop = new Properties();
        String propFileName = "config.properties";
        
        if (Env.DEV == determineEnv()) {
            String path = this.getClass().getClassLoader().getResource("").getPath();
            propFileName = path + "config_dev.properties";
        }
         
        
        
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        FileInputStream inputStream = new FileInputStream(propFileName);
        prop.load(inputStream);

        Date time = new Date(System.currentTimeMillis());

        // get the property value and print it out
        result = prop.getProperty(key);
        return result;
    }
    
    private Env determineEnv() {
        Env env = Env.PRODUCTION;
        
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            
            if (ip.startsWith("10.77")) {
                env = Env.DEV;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        return env;
    }
    
   
}
