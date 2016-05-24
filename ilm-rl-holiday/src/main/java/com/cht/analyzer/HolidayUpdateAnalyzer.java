package com.cht.analyzer;
/*
 * 版權宣告: FDC all rights reserved.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cht.db.MySQLDBConnection;
import com.cht.entity.EventEntity;
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
public class HolidayUpdateAnalyzer implements Analyzer {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private String ruleId = "r5"; // 必須符合table [rule]中定義的id 
    
    private String name = "例假日異動事件"; 
    
    private static Map<String, String> monthMap = new HashMap<>();
    
    static {
        monthMap.put("Jan", "01");
        monthMap.put("Feb", "02");
        monthMap.put("Mar", "03");
    }
    
    
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/M/d HH:mm");
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
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

//        logger.debug("total records: {}", results.size());
        
        MySQLDBConnection db = null;
        Set<String> holiday = new HashSet<>();
        try {
            db = new MySQLDBConnection();
            holiday = db.queryHoliday();            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        List matchStr = verify(list, holiday);
        
        
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
     * /n  n > 24
     * @param source
     * @return
     */
    private List<?> verify(List<String> source, Set<String> holiday) {
        List<EventEntity> results = new ArrayList<>();
        
        List<String> matches = source.stream().filter(evt -> holiday.contains(getDate(evt))).collect(Collectors.toList());
        
        matches.forEach(str -> {
            EventEntity evt = new EventEntity();
            List<String> tokens = Arrays.asList(str.split(","));
            evt.setUserId(tokens.get(1));
            evt.setUserName("姓名:" + tokens.get(1));
            evt.setIp(tokens.get(3));
            evt.setSeverity(2);
            evt.setRuleId(ruleId);
            evt.setContent(tokens.get(2));
            evt.setName(name);
            evt.setEvtTime(tokens.get(0));
            
            results.add(evt);
        });

        return results;
    }
    
    private String getDateTime(String event) {
        String[] tokens = event.split(" ");
        return getDate(event) + " " + tokens[2]; 
    }
    
    private String getDate(String event) {
        
        List<String> tokens = Arrays.asList(event.split(",")); 
        
        String dateStr = "";
        LocalDateTime dt = LocalDateTime.now();
        try {
            dateStr = tokens.get(0);
            dt = LocalDateTime.parse(dateStr, dtf);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        return dt.format(df);
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
    
    public static void main(String[] args) {
        
        String s = "weughwuethg/111 ";
        String pattern = "/[0-9]+\\s";
        Pattern p = Pattern.compile(pattern);    
        Matcher m = p.matcher(s);
        if (m.find()) {
            System.out.println(m.group());
        }
    }
}
