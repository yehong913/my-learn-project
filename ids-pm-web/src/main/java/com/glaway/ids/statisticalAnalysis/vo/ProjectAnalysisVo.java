/*
 * 文件名：ProjectAnalysisVo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：syc
 * 修改时间：2016年4月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.statisticalAnalysis.vo;

import java.io.Serializable;

/**
 * 里程碑图vo
 * 
 * @author bxu
 */
public class ProjectAnalysisVo implements Serializable {
    /**计划名称  */
    private String pname;

    /**负责人  */
    private String aname;

    /**进度  */
    private String rate;

    /**里程碑  */
    private String milestone;

    /**  */
    private String mcount;

    /**  */
    private String divflag;

    /**计划开始时间  */
    private String starttime;

    /**计划结束时间  */
    private String endtime;

    /**计划状态(用于前台展示)  */
    private String finish;
    
    /**工期  */
    private String worktime;
    
    /**计划状态 (用于导出) */
    private String status;
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDivflag() {
        return divflag;
    }

    public void setDivflag(String divflag) {
        this.divflag = divflag;
    }

    public String getMcount() {
        return mcount;
    }

    public void setMcount(String mcount) {
        this.mcount = mcount;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

}
