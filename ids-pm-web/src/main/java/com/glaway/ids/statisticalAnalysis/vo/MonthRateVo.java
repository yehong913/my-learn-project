/*
 * 文件名：MonthRateVo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：syc
 * 修改时间：2016年4月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.statisticalAnalysis.vo;

import java.io.Serializable;

/**
 * 月度达成率vo
 * 
 * @author bxu
 */
public class MonthRateVo implements Serializable {
    /** 月份 */
    private String month;

    /** wbs任务总数 */
    private String wbstotal;

    /** wbs实际完成数 */
    private String wbscomplete;

    /** wbs达成率 */
    private String wbsrate;

    /** 底层任务总数 */
    private String tasktotal;

    /** 底层实际完成数 */
    private String taskcomplete;

    /** 底层达成率 */
    private String taskrate;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWbstotal() {
        return wbstotal;
    }

    public void setWbstotal(String wbstotal) {
        this.wbstotal = wbstotal;
    }

    public String getWbscomplete() {
        return wbscomplete;
    }

    public void setWbscomplete(String wbscomplete) {
        this.wbscomplete = wbscomplete;
    }

    public String getWbsrate() {
        return wbsrate;
    }

    public void setWbsrate(String wbsrate) {
        this.wbsrate = wbsrate;
    }

    public String getTasktotal() {
        return tasktotal;
    }

    public void setTasktotal(String tasktotal) {
        this.tasktotal = tasktotal;
    }

    public String getTaskcomplete() {
        return taskcomplete;
    }

    public void setTaskcomplete(String taskcomplete) {
        this.taskcomplete = taskcomplete;
    }

    public String getTaskrate() {
        return taskrate;
    }

    public void setTaskrate(String taskrate) {
        this.taskrate = taskrate;
    }
}
