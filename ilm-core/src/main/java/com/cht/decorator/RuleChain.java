/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.decorator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cht.analyzer.Loader;
import com.cht.analyzer.RuleBlock;
import com.cht.db.CassandraConnection;
import com.cht.db.MySQLDBConnection;
import com.cht.entity.EventEntity;
import com.cht.entity.RuleChainEntity;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：DecoratingComponent.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author su
 *@version 1.0
 *@since 1.0
 */
public class RuleChain {
    Logger logger = LoggerFactory.getLogger(getClass());
    
    RuleChain next = null;
    
    String ruleId;
    String ruleName;
    String operand1;
    String oper;
    String operand2;
    
    List<?> source1;
    
    List<?> pipelineData;
    
    List<?> source2;
    
    List<RuleBlock> ruleBlocks;
   
    /**
     * 創建下一個RuleChain
     * @param oper
     * @param source2
     */
    public RuleChain(String oper, String operand2) {
        super();
        this.oper = oper;
        this.operand2 = operand2;
    }
    
    /**
     * 創建第一個RuleChain
     * @param source1
     * @param oper
     * @param source2
     */
    public RuleChain(String operand1, String oper, String operand2) {
        super();
        this.operand1 = operand1;
        this.oper = oper;
        this.operand2 = operand2;
    }
    
    public RuleChain(RuleChainEntity ruleChain) {
        super();
        ruleId = ruleChain.getId();
        ruleName = ruleChain.getName();
        operand1 = ruleChain.getOperand1();
        oper = ruleChain.getOper();
        operand2 = ruleChain.getOperand2();
    }
    
    void setRuleId(String ruleId) {
        this.ruleId = ruleId;        
    }
    
    void setNext(RuleChain next) {
        this.next = next;        
    }
    
    public void run(List<?> pipelineData) {
        setPipelineData(pipelineData);
        run();
    }
    
    public void run() {
        if (ruleBlocks == null) {
            Loader loader = new Loader();
            ruleBlocks = loader.loadRuleBlock();
        }
        
        List<String> data = null;
        
        // run this 
        if (oper.equals("not_in")) {
            if (pipelineData != null) {
                data = (List<String>) pipelineData;
            } else {
             // data IN comparedData e.g. sensitive user id
                Optional<RuleBlock> ruleBlock = ruleBlocks.stream().filter(rb -> rb.getId().equals(operand1)).findFirst();
                 
                if (ruleBlock.isPresent()) {
                    data = ruleBlock.get().importData();
                }
            }
            
            Optional<RuleBlock> ruleBlock2 = ruleBlocks.stream().filter(rb -> rb.getId().equals(operand2)).findFirst();
            
            
            if (ruleBlock2.isPresent()) {
                final List<String> comparedData = ruleBlock2.get().importData();
               // full text search                              
                   data = data.stream().filter(d -> 
                       !comparedData.stream().filter(c -> d.contains(c)).findAny().isPresent()
                     ).collect(Collectors.toList());
            }
//            writeFile("abnormal_user_id", data);

//            MySQLDBConnection db = new MySQLDBConnection();
//            
//            data.forEach(d -> {
//                EventEntity newItem = new EventEntity();
//                
//                List<String> tokens = Arrays.asList(d.split(","));
//                newItem.setUserId(tokens.get(1));
//                newItem.setUserName("姓名: " + tokens.get(1));
//                newItem.setEvtTime(tokens.get(0));
//                newItem.setContent(tokens.get(2));
//                newItem.setIp(tokens.get(3));
//                newItem.setSeverity(3);
//                newItem.setRuleId(ruleId);
//                newItem.setName(ruleName);
//                
//                db.insertEvent(newItem);
//            });
            
            
            
        } else if (oper.equals("in")) {
            // data NOT IN comparedData e.g. abnormal user id
            
            if (pipelineData != null) {
                data = (List<String>) pipelineData;
            } else {
                Optional<RuleBlock> ruleBlock = ruleBlocks.stream().filter(rb -> rb.getId().equals(operand1)).findFirst();
                
                if (ruleBlock.isPresent()) {
                    data = ruleBlock.get().importData();
                }
            }
            
            Optional<RuleBlock> ruleBlock2 = ruleBlocks.stream().filter(rb -> rb.getId().equals(operand2)).findFirst();
            
            
            if (ruleBlock2.isPresent()) {
                final List<String> comparedData = ruleBlock2.get().importData();
             // 暫時改為全文檢索
                data = data.stream().filter(d -> 
                        comparedData.stream().filter(c -> d.contains(c)).findFirst().isPresent()
                ).collect(Collectors.toList());
            }
            
        } else if ("contains".equals(oper)) { // contains
            if (pipelineData != null) {
                data = (List<String>) pipelineData;
            } else {
                Optional<RuleBlock> ruleBlock = ruleBlocks.stream().filter(rb -> rb.getId().equals(operand1)).findFirst();
                
                if (ruleBlock.isPresent()) {
                    data = ruleBlock.get().importData();
                }
            }
            
            data = data.stream().filter(d -> d.contains(operand2)).collect(Collectors.toList());
//            writeFile("demo-contains", data);
            
        } else {
            logger.info("wrong rule chain id");
        }  

        // run next
        if (next != null) {
            next.run(data);
        } else {
            MySQLDBConnection db = new MySQLDBConnection();
            CassandraConnection cass = new CassandraConnection();
            
            data.forEach(d -> {
                EventEntity newItem = new EventEntity();
                
                List<String> tokens = Arrays.asList(d.split(","));
                newItem.setUserId(tokens.get(1));
                newItem.setUserName("姓名: " + tokens.get(1));
                newItem.setEvtTime(tokens.get(0));
                newItem.setContent(tokens.get(2));
                newItem.setIp(tokens.get(3));
                newItem.setSeverity(3);
                newItem.setRuleId(ruleId);
                newItem.setName(ruleName);
                
                db.insertEvent(newItem);
                
                // Cassandra test
                cass.insertEvent(newItem);
            });
            
            String[] ids = {ruleId};
            
            // 
            if (data.size() > 0) {
                db.updateRegulationPass(0, ids);
                db.updateRegulationVio(1, ids);
            } else {
                db.updateRegulationPass(1, ids);
                db.updateRegulationVio(0, ids);
            }
          
            db.close(db.getConnection());
        }
    }
    
    private void setPipelineData(List<?> pipelineData) {
        this.pipelineData = pipelineData;
    }

}
