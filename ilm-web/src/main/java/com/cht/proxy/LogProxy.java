/*
 * 版權宣告: FDC all rights reserved.
 */
package com.cht.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：LogProxy.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class LogProxy implements MethodInterceptor {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /* (non-Javadoc)
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation method) throws Throwable {
        String className = method.getThis().getClass().getName();
        String methodName = method.getMethod().getName();
        logger.info("{}.{} start", className, methodName);
//        System.out.printf("%s.%s start\n", className, methodName);
        
        Object obj = method.proceed();
        
        logger.info("{}.{} end", className, methodName);
//        System.out.printf("%s.%s end\n", className, methodName);
        return obj;
    }
    
}
