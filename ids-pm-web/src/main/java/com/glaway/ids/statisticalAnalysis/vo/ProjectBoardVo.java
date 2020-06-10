/*
 * 文件名：ProjectBoardVo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2016年4月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.statisticalAnalysis.vo;

import java.io.Serializable;

/**
 * 计划达成率vo
 *
 * @author bxu
 */
public class ProjectBoardVo implements Serializable {
    
    /** 项目id */
    private String pid;

    /** 项目名称 */
    private String pname;

    /** 项目经理 */
    private String mname;
    
    /** 项目经理(show) */
    private String showName;

    /** 项目成员数量 */
    private String unum;

    /** 项目时间 */
    private String time;
    
    /** 行号 */
    private String rownum;
    
    /** 颜色 */
    private String color;
    
    /** 关键计划等级*/
    private String planlevel;

    public String getPlanlevel() {
        return planlevel;
    }

    public void setPlanlevel(String planlevel) {
        this.planlevel = planlevel;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getUnum() {
        return unum;
    }

    public void setUnum(String unum) {
        this.unum = unum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    
}
