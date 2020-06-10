/*
 * 文件名：ganttData.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年5月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.gantt.vo;


public class GanttData {

    /** 行号*/
    private int lineNumber;

    /** 计划编号*/
    private String id;

    /** 开始时间*/
    private String start_date;

    /** 结束时间*/
    private String end_date;

    /** 甘特图完成时间*/
    private String ganttEndDate;

    /** 工期*/
    private int duration;

    /** 计划名称*/
    private String text;

    /** 进度*/
    private double progress;

    /** 排序*/
    private int sortorder;

    /** 父的计划编号*/
    private String parent;

    /** 是否打开*/
    private boolean open;

    /** 负责人*/
    private String owner;

    /** 状态*/
    private String status;

//    /** 风险*/
//    private String risk;
    
    /** 工时*/
    private String workTime;
    
    /** 部门 */
    private String department;
    
    /** 输出*/
    private String deliInfo;
    
    /** 计划等级*/
    private String planLevel;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDeliInfo() {
        return deliInfo;
    }

    public void setDeliInfo(String deliInfo) {
        this.deliInfo = deliInfo;
    }

    public String getPlanLevel() {
        return planLevel;
    }

    public void setPlanLevel(String planLevel) {
        this.planLevel = planLevel;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getGanttEndDate() {
        return ganttEndDate;
    }

    public void setGanttEndDate(String ganttEndDate) {
        this.ganttEndDate = ganttEndDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getSortorder() {
        return sortorder;
    }

    public void setSortorder(int sortorder) {
        this.sortorder = sortorder;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

/*    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }*/

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

}
