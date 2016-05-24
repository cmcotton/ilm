/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.layout;


/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：DataTableColumn.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class DataTableColumn {
    
    String name;
    
    String title;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public DataTableColumn setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public DataTableColumn setTitle(String title) {
        this.title = title;
        return this;
    }

    
}
