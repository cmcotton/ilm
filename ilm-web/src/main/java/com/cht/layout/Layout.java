/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.layout;

import java.util.List;

import com.cht.entity.RuleEntity;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：ILayout.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public interface Layout {
    
    List arrange();
    
    List renderDataTable(List<RuleEntity> data);
}
