/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.controller;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.ServiceLoader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cht.analyzer.RuleBlock;
import com.cht.entity.RegulationEntity;
import com.cht.entity.RuleChainEntity;
import com.cht.entity.RuleEntity;
import com.cht.layout.AbnormalLoginTable;
import com.cht.layout.Layout;
import com.cht.layout.LogTable;
import com.cht.layout.RegulationTable;
import com.cht.layout.ReportTable;
import com.cht.layout.RuleTable;
import com.cht.logic.AccountReview;
import com.cht.logic.IAccountReport;
import com.cht.plugin.RegulationPlugin;
import com.cht.proxy.LogProxy;
import com.cht.service.RegulationManager;
import com.cht.service.RuleManager;

/**
 * 程式資訊摘要：
 * 類別名稱　　：MyHelloWorld.java
 * 程式內容說明：
 * 程式修改記錄：
 * XXXX-XX-XX：
 * 
 * @author chtd
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/regu")
public class RegulationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource
    private RegulationManager reguManager;    
    
    @Resource
    private RuleManager ruleManager;
    
    private List<RuleBlock> ruleBlocks;
    
    private List<RuleChainEntity> ruleChains;
    
    @RequestMapping("/plugin")
    public ModelAndView runPlugin() {

        try {
            LogProxy logAdvice = new LogProxy();

            ProxyFactory factory = new ProxyFactory();
            factory.addAdvice(logAdvice);

            //Get the System Classloader
            ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
     
            //Get the URLs
            URL[] urls = ((URLClassLoader)sysClassLoader).getURLs();
     
            for(int i=0; i< urls.length; i++) {
                System.out.println(urls[i].getFile());
            }    
            
            logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
            URL[] us = ((URLClassLoader) (Thread.currentThread().getContextClassLoader())).getURLs();
            for (URL temp :us) {
                System.out.println(temp.toString());
            }
            
            
         // 載入 ServiceLoader.
            ServiceLoader<RegulationPlugin> serviceLoader = ServiceLoader.load(RegulationPlugin.class);

            System.out.println("before");
            // 遊走所有 service 物件.
            for (RegulationPlugin service : serviceLoader) {
                System.out.println(">>>>>");
                service.get("aaa");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return new ModelAndView("pages/main", "content", "tables");
    }

    @RequestMapping("/dashboard")
    public ModelAndView getDashboard() {

        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);
      
//        return new ModelAndView("pages/index");
        return new ModelAndView("pages/main", "content", "index");
    }
    
    @RequestMapping("/flot") 
    public ModelAndView getFlot() {
        return new ModelAndView("pages/flot");
    }
    
    @RequestMapping("/tables") 
    public ModelAndView getReportTable() {
        return new ModelAndView("pages/main", "content", "tables");
    }
    
    
    @RequestMapping("/fillin") 
    public ModelAndView getFillIn() {
        return new ModelAndView("pages/main", "content", "fillin");
    }
    
    @RequestMapping("/forms") 
    public ModelAndView getForms() {
        return new ModelAndView("pages/main", "content", "forms");
    }
    
    @RequestMapping("/regumgt") 
    public ModelAndView getRegu() {
        return new ModelAndView("pages/main", "content", "regu");
    }
    
    @RequestMapping("/rule") 
    public ModelAndView getRule() {
        return new ModelAndView("pages/main", "content", "rule");
    }
    
    @RequestMapping("/reguToRule") 
    public ModelAndView getReguToRule() {
        return new ModelAndView("pages/main", "content", "reguToRule");
    }
    
    @RequestMapping("/assembleDataset") 
    public ModelAndView getAssembleDataset() {
        return new ModelAndView("pages/main", "content", "assembleDataset");
    }
    
    @RequestMapping(value = "/loadRuleBlock", method = RequestMethod.POST)
    public @ResponseBody void loadRuleBlock(HttpServletResponse res) {
        
        if (ruleBlocks == null) {
            ruleBlocks = ruleManager.loadRuleBlock();
        }
        
        try {
            
            JSONArray jsonArr = new JSONArray();
            ruleBlocks.forEach(rb -> {
                try {
                    JSONObject json = new JSONObject();
                    json.put("id", rb.getId());
                    json.put("name", rb.getName());
                    jsonArr.put(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }                
            });
        
            res.setCharacterEncoding("UTF-8");    
            res.setContentType("json");
            res.getWriter().write(jsonArr.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } 
    }
  

    @RequestMapping(value = "/getReportList", method = RequestMethod.GET)
    public @ResponseBody
    void getReportList(HttpServletResponse res) throws IOException {
        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);

        // gen report
        IAccountReport ar = new AccountReview();
        factory.setTarget(ar);
        IAccountReport proxy = (IAccountReport) factory.getProxy();
        proxy.execute();

        logger.info("after gen reports");

        // list report
        Layout reportTable = new ReportTable();
        factory.setTarget(reportTable);
        Layout reportProxy = (Layout) factory.getProxy();
        List dataList = reportProxy.arrange();

        try {

            JSONObject json = new JSONObject();
            json.put("data", dataList);
            res.setCharacterEncoding("UTF-8");
            // response.setContentType("text/html"); //设置数据格式            
            res.setContentType("json");
            res.getWriter().write(json.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * 登出入紀錄
     * @param res
     * @throws IOException
     */
    @RequestMapping(value = "/getLogin", method = RequestMethod.GET)
    public @ResponseBody
    void getLogin(HttpServletResponse res) throws IOException {
        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);

        // list report
        Layout logTable = new LogTable();
        factory.setTarget(logTable);
        Layout reportProxy = (Layout) factory.getProxy();
        List dataList = reportProxy.arrange();

        try {

            JSONObject json = new JSONObject();
            json.put("data", dataList);
            res.setCharacterEncoding("UTF-8");
            // response.setContentType("text/html"); //设置数据格式            
            res.setContentType("json");
            res.getWriter().write(json.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/getLogSelectAbnormalLogin", method = RequestMethod.GET)
    public @ResponseBody
    void getLogSelect(HttpServletResponse res) throws IOException {
        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);

        // list report
        Layout logTable = new AbnormalLoginTable();
        factory.setTarget(logTable);
        Layout reportProxy = (Layout) factory.getProxy();
        List dataList = reportProxy.arrange();

        try {

            JSONObject json = new JSONObject();
            json.put("data", dataList);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("json");
            res.getWriter().write(json.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
       
    @RequestMapping(value = "/getAllReguEntity", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    void getAllReguEntity(HttpServletResponse res) throws IOException {
        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);

        List<RegulationEntity> dataList = reguManager.getRegulation();
        
        try {
            JSONArray jsonArr = new JSONArray();
            
            dataList.forEach(e -> setJson(e, jsonArr));
            
            res.setCharacterEncoding("UTF-8");
            res.setContentType("json");
            res.getWriter().write(jsonArr.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/getAllRegulation", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    void getAllRegulation(HttpServletResponse res) throws IOException {
        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);

        // list report
        Layout logTable = new RegulationTable();
        factory.setTarget(logTable);
        Layout reportProxy = (Layout) factory.getProxy();
        List dataList = reportProxy.arrange();

        try {

            JSONObject json = new JSONObject();
            json.put("data", dataList);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("json");
            res.getWriter().write(json.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/getAllRule", method = RequestMethod.POST)
    public @ResponseBody
    void getAllRule(HttpServletResponse res) throws IOException {
        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);

        // list report
        Layout logTable = new RegulationTable();
        factory.setTarget(logTable);
        Layout reportProxy = (Layout) factory.getProxy();
        List dataList = reportProxy.arrange();

        try {

            JSONObject json = new JSONObject();
            json.put("data", dataList);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("json");
            res.getWriter().write(json.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/getAllRuleEntity", method = RequestMethod.POST)
    public @ResponseBody
    void getAllRuleEntity(HttpServletResponse res) throws IOException {
        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);

        List<RuleEntity> dataList = ruleManager.getRule("");
        try {
            JSONArray jsonArr = new JSONArray();
            
            for (RuleEntity e : dataList) {
                JSONObject json = new JSONObject(e);
                jsonArr.put(json.toString());
            }
            
            res.setCharacterEncoding("UTF-8");
            res.setContentType("json");
            res.getWriter().write(jsonArr.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/getRuleToRegu", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody    
    void getReguToRule(@RequestParam("reguNo") String reguNo, HttpServletResponse res) throws IOException {
        logger.debug("Start");
        
        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);

        // 
        List<RuleEntity> dataList = reguManager.getRule(reguNo);
        
        
        Layout reguTable = new RuleTable();
        factory.setTarget(reguTable);
        Layout reportProxy = (Layout) factory.getProxy();
        List dataTable = reportProxy.renderDataTable(dataList);

        logger.info(">>> get {} rules", dataTable.size());
        try {

            JSONObject json = new JSONObject();

            json.put("data", dataTable);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("json");
            res.getWriter().write(json.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/modifyApplicable", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody    
    void modifyApplicable(@RequestParam("oper") String oper, @RequestParam("regus[]") String[] regus, 
            HttpServletResponse res) throws IOException {
        logger.debug("Start");
        
        logger.debug("regus size: {}", regus.length);
 
        reguManager.modifyApplicable(oper, regus);        
    }
    

    @RequestMapping(value = "/fillinRule", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody    
    void fillinRule(@RequestParam("oper") String oper, @RequestParam("rule") String rule, 
            @RequestParam("attach") String attach,
            HttpServletResponse res) throws IOException {
        logger.debug("Start");
        logger.debug("rule to insert: {}", rule);
 
        reguManager.fillinRule(oper, rule, attach);
    }
    
    @RequestMapping(value = "/runRuleChain", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody    
    void runRuleChain(@RequestParam("ruleId") String ruleId, 
            @RequestParam("name") String name,
            @RequestParam("operand1") String operand1,
            @RequestParam("oper") String oper,
            @RequestParam("operand2") String operand2,
            HttpServletResponse res) throws IOException {
        logger.debug("Start");
        ruleId = operand1 + ":" + oper + ":" + operand2; 
        logger.debug("rule to insert: {}", ruleId);
 
        // pack ruleChain obj
        RuleChainEntity ruleChain = new RuleChainEntity();
        ruleChain.setId(ruleId).setName(name).setOperand1(operand1).setOper(oper).setOperand2(operand2);
        
        ruleManager.runRuleChain(ruleChain);
    }
    
    @RequestMapping(value = "/runRule", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody    
    void runRule(@RequestParam("ruleId") String ruleId, HttpServletResponse res) throws IOException {
        logger.debug("Start");
        logger.debug("rule to insert: {}", ruleId);
 
        ruleManager.runRule(ruleId);
    }
    
    private void setJson(RegulationEntity e, JSONArray jsonArr) {
        JSONObject json = new JSONObject(e);
        jsonArr.put(json.toString());
    }
    
    @RequestMapping(value = "/addRuleChain", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody    
    void addRuleChain(@RequestParam("name") String name,
            @RequestParam("operand1") String operand1,
            @RequestParam("operand1Name") String operand1Name,
            @RequestParam("oper") String oper,
            @RequestParam("operand2") String operand2,
            @RequestParam("operand2Name") String operand2Name,
            HttpServletResponse res) throws IOException {
        logger.debug("start");
 
        // pack ruleChain obj
        RuleChainEntity ruleChain = new RuleChainEntity();
        ruleChain.setId(operand1 + ":" + oper + ":" + operand2).setName(name).setOperand1(operand1).setOperand1Name(operand1Name).setOper(oper).setOperand2(operand2).setOperand2Name(operand2Name).setThreshold(operand2);
        
        ruleManager.addRuleChain(ruleChain);
    }
    
    @RequestMapping(value = "/delRuleChain/{oper}/{0}/{1}", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody    
    void delRuleChain(
            @PathVariable("oper") String oper,
            @PathVariable("0") String operand1,
            @PathVariable("1") String operand2,
            HttpServletResponse res) throws IOException {
        logger.debug("start");
 
        // pack ruleChain obj
        RuleChainEntity ruleChain = new RuleChainEntity();
        ruleChain.setId(operand1 + ":" + oper + ":" + operand2);
        
        ruleManager.delRuleChain(ruleChain);
    }
    
    @RequestMapping(value = "/loadRuleChain", method = RequestMethod.POST)
    public @ResponseBody void loadRuleChain(HttpServletResponse res) {
        
//        if (ruleChains == null) {
            ruleChains = ruleManager.loadRuleChain();
//        }
        
        try {
            JSONArray jsonArr = new JSONArray();
            ruleChains.forEach(rb -> {
                try {
                    JSONObject json = new JSONObject();
                    
                    json.put("id", rb.getId());
                    json.put("ruleChainName", rb.getName());
                    
                    JSONObject s1 = new JSONObject();
                    s1.put("id", rb.getOperand1());
                    s1.put("name", rb.getOperand1Name());
                    json.put("source1", s1);
                    
                    json.put("oper", rb.getOper());
                    
                    JSONObject s2 = new JSONObject();
                    s2.put("id", rb.getOperand2());
                    s2.put("name", rb.getOperand2Name());
                    json.put("source2", s2);
                    jsonArr.put(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }                
            });
        
            res.setCharacterEncoding("UTF-8");    
            res.setContentType("json");
            res.getWriter().write(jsonArr.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } 
    }

}