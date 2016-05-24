package com.cht.analyzer;
/*
 * 版權宣告: FDC all rights reserved.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cht.entity.AccountEntity;
import com.cht.entity.EventEntity;

/**
 * 程式資訊摘要：
 * <P>
 * 類別名稱　　：DoubleLoginAnalyzer.java
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
public class ReloginAnalyzer implements Analyzer {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private String ruleId = "r9"; // 必須符合table [rule]中定義的id 
    
    private String name = "分析異地同時登入事件"; 
    
    private static Map<String, String> monthMap = new HashMap<>();
    
    static {
        monthMap.put("Jan", "01");
        monthMap.put("Feb", "02");
        monthMap.put("Mar", "03");
    }
    
    private static Map<String, String> loginMap = new HashMap<>();
    
    /*
     * (non-Javadoc)
     * 
     * @see com.cht.analyzer.Analyzer#analyze(java.util.List)
     */
    public List analyze(List source) {

        List<String> list = null;
        try {            
            list = readline(new File("d:/login.csv"));                        
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        List<?> matchStr = verify(list);
        
        return matchStr;
    }

    /* (non-Javadoc)
     * @see com.cht.analyzer.Analyzer#getRuleId()
     */
    public String getRuleId() {
        return ruleId;
    }

    /* (non-Javadoc)
     * @see com.cht.analyzer.Analyzer#getName()
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * 主力判斷邏輯在這裡
     * @param source
     * @return
     */
    private List<EventEntity> verify(List<String> source) {
        List<EventEntity> results = new ArrayList<>();
        
        source.forEach(str -> {
            String[] tokens = str.split(",");
            
            if (tokens[2].equals("login") && !loginMap.containsKey(tokens[1])) {
                loginMap.put(tokens[1], tokens[3]);
            } else if (loginMap.containsKey(tokens[1]) && (tokens[2].equals("logout") || tokens[2].equals("timeout"))) {
                loginMap.remove(tokens[1]);
            } else if (loginMap.containsKey(tokens[1]) && !tokens[3].equals(loginMap.get(tokens[1]))) {
                EventEntity evt = new EventEntity();
                
                evt.setUserId(tokens[1]);
                evt.setUserName("姓名:" + tokens[1]);
                evt.setIp(tokens[3]);
                evt.setSeverity(1);
                evt.setRuleId(ruleId);
                evt.setContent("同時於" + tokens[3] + "及" + loginMap.get(tokens[1]) + "登入");
                evt.setName(name);
                evt.setEvtTime(tokens[0]);
                
                results.add(evt);
            }
        });
        
        return results;
    }
    
    private boolean isDedicateIP(String evt, List<AccountEntity> accIps) {
        String[] tokens = evt.split(",");
        String acc = tokens[1];
        String ip = tokens[3];
        
        Optional<?> dedicate = accIps.stream().filter(a -> a.getAccount().equals(acc) && a.getHostName().equals(ip)).findAny();
        
        return dedicate.isPresent();
    }

    private List<String> readline(File file) throws IOException {
        if (!file.exists()) {
            return new ArrayList<String>();
        }
        
        List<String> lines = new ArrayList<>();
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> results = new ArrayList<String>();
        String line = reader.readLine();
        while (line != null) {
            lines.add(line);
            
            line = reader.readLine();
        }
        
        return lines;
    }   
}
