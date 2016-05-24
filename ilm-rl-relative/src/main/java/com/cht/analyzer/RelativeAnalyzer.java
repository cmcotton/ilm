package com.cht.analyzer;
/*
 * 版權宣告: FDC all rights reserved.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cht.db.MySQLDBConnection;
import com.cht.entity.EventEntity;
import com.cht.entity.RelativeEntity;
import com.cht.io.FileManager;

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
public class RelativeAnalyzer implements Analyzer {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private String ruleId = "r8"; // 必須符合table [rule]中定義的id 
    
    private String name = "分析內部關係人作業"; 
    
    private static Map<String, String> monthMap = new HashMap<>();
    
    static {
        monthMap.put("Jan", "01");
        monthMap.put("Feb", "02");
        monthMap.put("Mar", "03");
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.cht.analyzer.Analyzer#analyze(java.util.List)
     */
    public List analyze(List source) {

        List<String> list = null;
        try {
            FileManager fm = new FileManager();
            list = readline(new File("d:/secure"));                        
        } catch (Exception e) {
            logger.error(e.toString());
        }


        // get data to correlated
        MySQLDBConnection db = null;
        List<RelativeEntity> relatives = new ArrayList<>();        
        try {
            db = new MySQLDBConnection();
            relatives = db.queryRelative();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        List<?> matchStr = verify(list, relatives);
        
        
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
    private List<?> verify(List<String> source, List<RelativeEntity> rels) {
        List<EventEntity> results = new ArrayList<>();
        
        List<String> evtTouched = source.stream().filter(evt -> touchRelative(evt, rels)).collect(Collectors.toList());
        
        evtTouched.forEach(str -> {
            EventEntity evt = new EventEntity();
            String[] tokens = str.split(",");
            evt.setUserId(tokens[1]);
            evt.setUserName(tokens[1]);
            evt.setIp(tokens[3]);
            evt.setSeverity(5);
            evt.setRuleId(ruleId);
            evt.setContent(tokens[2]);
            evt.setName(name);
            evt.setEvtTime(tokens[0]);
            
            results.add(evt);
        });

        return results;
    }
    
    private boolean touchRelative(String evt, List<RelativeEntity> rels) {
        List<String> tokens = Arrays.asList(evt.split(","));
        String acc = tokens.get(1);
        String content = tokens.get(2);
        
        // find my relatives
        Optional<RelativeEntity> opt = rels.parallelStream().filter(r -> r.getUserid().equals(acc)).findFirst();
        RelativeEntity myRelE = null;
        if (opt.isPresent()) {
            myRelE = opt.get();
        }
        
        List<String> myRels = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(myRelE.getRelatives());
            for (int i = 0; i < jsonArray.length(); i++){ 
                myRels.add((String)jsonArray.get(i));
            } 
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        Optional rel = myRels.stream().filter(r -> content.contains(r)).findAny();
        
        return rel.isPresent();
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
