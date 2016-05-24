/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.layout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cht.entity.RuleEntity;
import com.cht.io.FileManager;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：ReportTable.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class ReportTable implements Layout {

    /* (non-Javadoc)
     * @see com.cht.layout.ILayout#arrange()
     */
    @Override
    public List arrange() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        FileManager fm = new FileManager();
        File[] reports = fm.getFiles();
        
        List dataList = new ArrayList<>();
        
        for (File rpt : reports) {
            List<String> list = Arrays.asList(rpt.getName(), sdf.format(rpt.lastModified()), "<a href='../download/doDownload.html?fileName=" + rpt.getName() + "'>下載</a>", "");
            dataList.add(list);
        } 
        return dataList;
    }

    /* (non-Javadoc)
     * @see com.cht.layout.Layout#renderDataTable(java.util.List)
     */
    @Override
    public List renderDataTable(List<RuleEntity> data) {
        // TODO Auto-generated method stub
        return null;
    }

}
