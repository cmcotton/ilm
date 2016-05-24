/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cht.model.FileBucket;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：FileUploadController.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author su
 *@version 1.0
 *@since 1.0
 */
@Controller
@RequestMapping("/upload")
public class FileUploadController {

    private static String UPLOAD_LOCATION = "D:/MYTEMP/";
    
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    
    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(ModelMap model) {
        
        FileBucket fileModel = new FileBucket();
        model.addAttribute("fileBucket", fileModel);
        return "singleFileUploader";
        
    }
}
