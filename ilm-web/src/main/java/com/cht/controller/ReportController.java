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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cht.layout.AbnormalLoginTable;
import com.cht.layout.Layout;
import com.cht.layout.LogTable;
import com.cht.layout.RelativeTable;
import com.cht.layout.ReportTable;
import com.cht.logic.AccountReview;
import com.cht.logic.IAccountReport;
import com.cht.plugin.RegulationPlugin;
import com.cht.proxy.LogProxy;
import com.cht.result.Result;
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
@RequestMapping("/report")
public class ReportController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource
    private RuleManager reportManager;

    @RequestMapping("/welcome")
    public ModelAndView helloWorld() {

        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);

        logger.debug(">>>>>>  test    >>>>>>>>>>>>>>>");       
                
        return new ModelAndView("welcome", "message", "jwviwjbviuwhjghuiqgehoqpgr");
    }
    
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
    
    @RequestMapping("/tables")
    public ModelAndView getTables() {

        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);
      
        return new ModelAndView("pages/main", "content", "tables");
    }
    
    @RequestMapping("/flot") 
    public ModelAndView getFlot() {
        return new ModelAndView("pages/flot");
    }
   
    @RequestMapping("/fillin") 
    public ModelAndView getFillIn() {
        return new ModelAndView("pages/main", "content", "fillin");
    }
    
    @RequestMapping("/forms") 
    public ModelAndView getForms() {
        return new ModelAndView("pages/main", "content", "forms");
    }
    
//    @RequestMapping("/regu") 
//    public ModelAndView getRegu() {
//        return new ModelAndView("pages/main", "content", "regu");
//    }
    
    @RequestMapping("/reguToRule") 
    public ModelAndView getReguToRule() {
        return new ModelAndView("pages/main", "content", "reguToRule");
    }
    
    
    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public @ResponseBody
    void getUser(HttpServletResponse res) throws IOException {
        Result result = new Result();

        String message = "<br><div style='text-align:center;'>"
                + "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
        result.setWhat(message);

        logger.debug(message);

        // List<String> list = Arrays.asList("a", "b");
        Result r = new Result();
        r.setWhat("jeigqjeigrjiqgr");
        r.setWho("I am the king");
        JSONObject json = new JSONObject(r);
        res.setContentType("json");
        res.getWriter().write(json.toString());
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
    
    @RequestMapping(value = "/getLogSelectRelative", method = RequestMethod.GET)
    public @ResponseBody
    void getLogSelectRelative(HttpServletResponse res) throws IOException {
        LogProxy logAdvice = new LogProxy();

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(logAdvice);

        // list report
        Layout logTable = new RelativeTable();
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

}