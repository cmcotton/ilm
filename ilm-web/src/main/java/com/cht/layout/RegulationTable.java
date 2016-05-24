/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cht.db.MySQLDBConnection;
import com.cht.entity.RegulationEntity;
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
public class RegulationTable implements Layout {

    /* (non-Javadoc)
     * @see com.cht.layout.ILayout#arrange()
     */
    @Override
    public List arrange() {
        
        List dataList = new ArrayList<>();
        
        MySQLDBConnection db = new MySQLDBConnection();
        List<RegulationEntity> regus = db.queryRegulation();
        
        for (RegulationEntity e : regus) {
//            List<String> l = Arrays.asList(e.getNo(), e.getCriteria(), e.getName(), e.getDesc(), e.getAplpicable(), e.getPass(), e.getNonexec(), e.getViolate(), e.getProperty());
            List<String> l = Arrays.asList(e.getNo(), e.getCriteria(), e.getName(), e.getDesc(), e.getApplicable(), e.getProperty());
            dataList.add(l);
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
