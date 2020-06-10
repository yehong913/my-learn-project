/*
 * 文件名：TaskActityInfo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：duanpengfei
 * 修改时间：2015年5月6日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.common.pbmn.activity.entity;


/**
 * 〈完工反馈流程对象〉
 * 〈功能详细描述〉
 * @author duanpengfei
 * @version 2015年5月6日
 * @see TaskActityInfo
 * @since
 */

public class TaskActityInfo extends ActivityInfo{
    /**
     * 当前反馈进度<br>
     */
    private String progressRate;
    
    /**
     * @return Returns the progressRate.
     */
    public String getProgressRate() {
        return progressRate;
    }
    /**
     * @param progressRate The progressRate to set.
     */
    public void setProgressRate(String progressRate) {
        this.progressRate = progressRate;
    }
}
