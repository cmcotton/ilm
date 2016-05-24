/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cht.util.MyPropertyReader;

/**
 * 程式資訊摘要：
 * 類別名稱　　：FileDownloadController.java
 * 程式內容說明：
 * 程式修改記錄：
 * XXXX-XX-XX：
 * 
 * @author chtd
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/download")
public class FileDownloadController {

    /**
     * Size of a byte buffer to read/write file
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Method for handling file download request from client
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)    
    public void doDownload(HttpServletRequest request, HttpServletResponse response, 
            @RequestParam(value = "fileName") String fileName) throws IOException {

        // get absolute path of the application
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        System.out.println("appPath = " + appPath);

        // construct the complete absolute path of the file
//        String fullPath = appPath + filePath;
//        String fileName = "系統帳號清冊report_南營處中部維運中心一股.xlsx";
        if (StringUtils.isEmpty(fileName)) {
            throw new IOException("Param is empty!");
        }
        
        MyPropertyReader pr = new MyPropertyReader();
        String filePath = pr.getPropValues(MyPropertyReader.REPORT_PATH);
        String fullPath = filePath + fileName;
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        String newFileName = new String(downloadFile.getName().getBytes("UTF-8"), "ISO-8859-1");
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", newFileName);
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();

    }
}
