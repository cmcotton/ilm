/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：FileUpload.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author su
 *@version 1.0
 *@since 1.0
 */
public class FileBucket {
    
    MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }
 
    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
