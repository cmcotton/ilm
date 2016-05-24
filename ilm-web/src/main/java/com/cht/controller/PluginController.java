/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.controller;

import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cht.db.MySQLDBConnection;
import com.cht.entity.RegulationEntity;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：PluginController.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author su
 *@version 1.0
 *@since 1.0
 */
@Controller
@RequestMapping("/plugin")
public class PluginController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    static Map<String, String> pluginInfos = new HashMap();
    
    static {
        pluginInfos.put("ilm-rl-holiday", "{id:r5,name:例假日異動事件,type:完整規則,desc:分析出例假日有異動的行為,price:0}");
        pluginInfos.put("ilm-rl-notdedicate", "{id:r6,name:非所屬IP 位址登入事件,type:完整規則,desc:分析出帳號位於他人電腦的操作行為，可能帳號被他人利用,price:0}");
        pluginInfos.put("ilm-rl-spclact", "{id:r7,name:處理特殊稅務資料事件,type:完整規則,desc:分析出敏感資料操作行為,price:10000}");
        pluginInfos.put("ilm-rl-relative", "{id:r8,name:內部關係人操作事件,type:完整規則,desc:分析使用者操作的資料屬於他的關係人，屬於高風險事件,price:500000}");
        pluginInfos.put("ilm-rl-relogin", "{id:r9,name:異地同時登入事件,type:完整規則,desc:分析使用者同時於兩處登入，可能帳號被他人利用,price:200}");
        
        pluginInfos.put("ilm-rb-log1", "{id:rbFile1,name:登入清單-從檔案,type:規則積木,desc:匯入應用系統作業紀錄,price:500}");
        pluginInfos.put("ilm-rb-vldacc", "{id:rbFile2,name:合法帳號清單-從DB,type:規則積木,desc:合法帳號，不在其中者為異常帳號,price:500}");
        pluginInfos.put("ilm-rb-splacc", "{id:rbFile3,name:VIP 客戶-從DB,type:規則積木,desc:VIP 帳號可檢查哪些使用者操作VIP客戶的資料,price:20000}");
    }
    
    @RequestMapping("/store")
    public ModelAndView loadStore() {
        return new ModelAndView("pages/main", "content", "store");
    }
    
    
    @RequestMapping(value = "/getAllPlugins", method = RequestMethod.POST)
    public @ResponseBody void getAllPlugins(HttpServletResponse res) {
        
        // find all files in one directory
        Path pluginPath = Paths.get("D:\\pluginstore");
        Path installedPluginPath = Paths.get("D:", "Eclipse_workspace_ilm/.metadata/.plugins/org.eclipse.wst.server.core/tmp5/wtpwebapps/ilm-web/WEB-INF/lib");               
                
        try {
            JSONArray jsonArr = new JSONArray();
            DirectoryStream<Path> plugins = Files.newDirectoryStream(pluginPath);
            
            
            for (Path p : plugins) {
                try {
                    String[] tokens = p.getFileName().toString().split("-");
                    String id = tokens[0] + "-" + tokens[1] + "-" + tokens[2]; // parse file name without ver.
                    
                    JSONObject json = new JSONObject(pluginInfos.get(id));
                    
                    // determine whether installed
                    String fn = p.getFileName().toString();
                    
                    for (Path ip : Files.newDirectoryStream(installedPluginPath, "ilm*")) {
                        if (fn.equals(ip.getFileName().toString())) {
                            json.put("installed", 1);
                            break;
                        }
                    };
                    
                    jsonArr.put(json);
                } catch (Exception e) {
                    logger.error(e.toString());
                }                
            }
        
            res.setCharacterEncoding("UTF-8");    
            res.setContentType("json");
            res.getWriter().write(jsonArr.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } 
    }
    
    @RequestMapping(value = "/buyPlugin", method = RequestMethod.POST)
    public @ResponseBody void buyPlugin(@RequestParam("ruleId") String ruleId,
            HttpServletResponse res) {
        logger.info("buy rule/block: {}", ruleId);
        
        Entry<String, String> targetPlugin = pluginInfos.entrySet().stream().filter(e -> {
            boolean found = false;
            JSONObject json = null;;
            try {
                json = new JSONObject(e.getValue());
                return json.get("id").equals(ruleId);    
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            
            return false;
            
        }).findFirst().get();
                
         // find all files in one directory
        Path pluginPath = Paths.get("D:\\pluginstore");        
        try {
            DirectoryStream<Path> plugins = Files.newDirectoryStream(pluginPath, targetPlugin.getKey() + "*");
            plugins.forEach(p -> {
                    Path destFile = Paths.get("D:/Eclipse_workspace_ilm/.metadata/.plugins/org.eclipse.wst.server.core/tmp5/wtpwebapps/ilm-web/WEB-INF/lib/" + p.getFileName().toString());
                    try {
                        Files.copy(p, destFile, new CopyOption[] {
                                StandardCopyOption.REPLACE_EXISTING,
                                StandardCopyOption.COPY_ATTRIBUTES
                              });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    
                    // add a "rule" as a regulation, but not rule block
                    JSONObject json;
                    try {
                        json = new JSONObject(targetPlugin.getValue());
                        if (!((String)json.get("id")).startsWith("rb")) {
                            MySQLDBConnection db = new MySQLDBConnection();
                            RegulationEntity newItem = new RegulationEntity();
                            newItem.setApplicable("1").setNo((String)json.get("id")).setName((String)json.get("name")).setDesc((String)json.get("desc"));
                            db.insertRegulation(newItem);
                            db.close(db.getConnection());
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
            });
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        try {        
            res.setCharacterEncoding("UTF-8");    
            res.setContentType("json");
            res.getWriter().write("{}");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } 
    }


    @RequestMapping(value = "/delPlugin", method = RequestMethod.POST)
    public @ResponseBody void delPlugin(@RequestParam("ruleId") String ruleId,
            HttpServletResponse res) {
        logger.info("del rule/block: {}", ruleId);        
        
        String targetPlugin = pluginInfos.entrySet().stream().filter(e -> {
            
            JSONObject json = null;;
            try {
                json = new JSONObject(e.getValue());
                return json.get("id").equals(ruleId);    
            } catch (Exception e1) {
                e1.printStackTrace();
                return false;
            }                  
        }).map(Map.Entry::getKey).findFirst().get();
        
        
        // find all files in one directory
        Path pluginPath = Paths.get("D:/Eclipse_workspace_ilm/.metadata/.plugins/org.eclipse.wst.server.core/tmp5/wtpwebapps/ilm-web/WEB-INF/lib/");
                
        try { 
            DirectoryStream<Path> pluginFile = Files.newDirectoryStream(pluginPath, targetPlugin + "*");
            pluginFile.forEach(pf -> {
                try {
                    Files.delete(pf);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        try {
            MySQLDBConnection db = new MySQLDBConnection();
            db.deletePlugin(new RegulationEntity().setNo(ruleId));
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        try {
            res.setCharacterEncoding("UTF-8");    
            res.setContentType("json");
            res.getWriter().write("{}");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } 
    }
}
