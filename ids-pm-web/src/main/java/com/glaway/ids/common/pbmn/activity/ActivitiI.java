/*
 * 文件名：ActivitiI.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：duanpengfei
 * 修改时间：2015年4月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.common.pbmn.activity;


import com.glaway.foundation.common.exception.GWException;


/**
 * 〈流程接口〉
 * 〈功能详细描述〉
 * 
 * @author duanpengfei
 * @version 2015年4月8日
 * @see ActivitiI
 * @since
 */

public interface ActivitiI {

    /**
     * Description: <br>
     * 流程接口<br>
     * 
     * @param obj
     * @return
     * @throws GWException
     * @see
     */
    void approve(Object obj) throws GWException;
}
