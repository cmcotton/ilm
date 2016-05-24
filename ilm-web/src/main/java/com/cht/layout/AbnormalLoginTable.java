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
public class AbnormalLoginTable implements Layout {

    /* (non-Javadoc)
     * @see com.cht.layout.ILayout#arrange()
     */
    @Override
    public List arrange() {
        
        List dataList = new ArrayList<>();
        

        List<String> list = Arrays.asList("蔡芙文", "2015/07/01 09:35:11", "登入成功", "DPP", "login", "", "", "");
        
        list = Arrays.asList("王全平", "2015/07/03 09:35:11", "登入失敗", "KMT", "login failed", "", "", "未登錄帳號");
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
