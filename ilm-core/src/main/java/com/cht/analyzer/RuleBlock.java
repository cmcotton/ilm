/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.analyzer;

import java.util.List;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：RuleBlock.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public interface RuleBlock {
    
    List<String> importData();   
    
    String getId();
    
    String getName();
}
