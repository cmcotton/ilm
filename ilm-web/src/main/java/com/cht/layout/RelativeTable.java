/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cht.entity.RuleEntity;

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
public class RelativeTable implements Layout {

    /* (non-Javadoc)
     * @see com.cht.layout.ILayout#arrange()
     */
    @Override
    public List arrange() {
        
        List dataList = new ArrayList<>();
        
        List<String> list = Arrays.asList("黃曉明", "2015/07/10 09:35:11", "查詢退稅金額", "DPP", "退稅管理", "姓名", "白海豚", "");
        dataList.add(list);
        
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
