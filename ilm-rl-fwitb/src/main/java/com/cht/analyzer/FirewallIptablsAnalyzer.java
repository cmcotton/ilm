package com.cht.analyzer;
/*
 * 版權宣告: FDC all rights reserved.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class FirewallIptablsAnalyzer implements Analyzer {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private String ruleId = "r4"; // 必須符合table [rule]中定義的id 
    
    private String name = "iptables rules verifier"; 
    
    /*
     * (non-Javadoc)
     * 
     * @see com.cht.analyzer.Analyzer#analyze(java.util.List)
     */
    public List analyze(List source) {
        List results = new ArrayList();

        List<String> list = null;
        try {
            FileManager fm = new FileManager();
            list = readline(new File("d:/iptables_sample"));            
            list = list.stream().filter(l -> !l.startsWith("#") && StringUtils.isNoneBlank(l)).collect(Collectors.toList());
            
            Set failedStrs = verify(list);
            
            results.addAll(failedStrs);
        } catch (Exception e) {
            logger.error(e.toString());
        }

        logger.debug("total records: {}", results.size());
        
        try {
            File file = new File("d:/iptables_check.txt");
            FileWriter fileWriter = new FileWriter(file);
            
            results.forEach(s -> {
                try {
                    fileWriter.write(s + "\n");
                } catch (Exception e) {
                    logger.error(e.toString());
                }
            });
                        
            
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return results;
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
    private Set<?> verify(List<String> source) {
        Set failedStrs = new CopyOnWriteArraySet();
        
        if (source.indexOf("deny all") != source.size() - 1) {
            failedStrs.add("deny all 不在最後一行");
        }        
        
        Pattern p = Pattern.compile("/[0-9]+\\s");

        // /n, where n > 24
        Set<?> errorRules = source.stream().filter(s -> {
                Matcher m = p.matcher(s);
                if (m.find()) {
                    try {
                        String oriMask = m.group(0).substring(1).trim();
                        int mask = Integer.parseInt(oriMask);
                        return mask > 24;
                    } catch (Exception e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }).collect(Collectors.toSet());
        
        if (errorRules != null) {
            failedStrs.add(errorRules);
        }
            
        // 不可有 permit all
        Optional<String> result = source.stream().filter(s -> StringUtils.containsIgnoreCase(s, "permit all")).findFirst();
        if (result.isPresent()) {
            failedStrs.add("不可有permit all");
        }

        return failedStrs;
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
