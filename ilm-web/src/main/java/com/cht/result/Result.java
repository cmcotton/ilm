/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.result;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：Result.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class Result {
    String who = "Kevin";
    
    String what = "administrator";

    /**
     * @return the who
     */
    public String getWho() {
        return who;
    }

    /**
     * @param who the who to set
     */
    public void setWho(String who) {
        this.who = who;
    }

    /**
     * @return the what
     */
    public String getWhat() {
        return what;
    }

    /**
     * @param what the what to set
     */
    public void setWhat(String what) {
        this.what = what;
    }
    
    
    public String toString() {
        return "Result [who=" + who + ", what=" + what + "]";
    }
}
