package com.glaway.ids.jGraph.vo;


import java.util.Date;
import java.util.List;


public class PlanVO {
    private String uuid; // 主键id

    private String planOid; // 计划oid

    private String planName; // 计划名称

    private String planDep; // 计划执行部门

    private Date beginTime; // 计划开始时间

    private Date endTime; // 计划结束时间

    private String beginTimeStr;// 开始时间

    private String endTimeStr; // 结束时间

    private String parentOid; // 前置计划oid 多个用','逗号分隔

    private String status; // 计划状态

    private String executeStatus;// 计划执行状态

    private int delayDays; // 超期天数

    private String lineNumber; // 行号

    private String color; // 计划的颜色

    private int swimLaneIdx; // 所在泳道下标位置

    private int panelIdx; // 图层号 一个图层号里存放一张图（可以是一个离散计划，也可以是一个单代号计划图）

    private String valign; // 上下位置

    private int times; // 工时 单位：天

    private int totalTimes; // 总工时 单位：天

    private int x; // x坐标轴

    private int y; // y坐标轴

    private int planTreeTotal; // 总共有多少个计划（计算计划树所有节点个数）

    private List<String> childPoid; // 后置计划oid 多个用','逗号分隔

    private List<String> totalChildPoid;// 后置递归下去的计划oid 多个用','逗号分隔

    public PlanVO() {}

    public PlanVO(String uuid, String planOid, String planName, String planDep, Date beginTime,
                  Date endTime, String beginTimeStr, String endTimeStr, String parentOid,
                  String status, String executeStatus, int delayDays, String lineNumber,
                  String color, int swimLaneIdx, int panelIdx, String valign, int times,
                  int totalTimes, int x, int y, int planTreeTotal, List<String> childPoid,
                  List<String> totalChildPoid) {
        super();
        this.uuid = uuid;
        this.planOid = planOid;
        this.planName = planName;
        this.planDep = planDep;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.beginTimeStr = beginTimeStr;
        this.endTimeStr = endTimeStr;
        this.parentOid = parentOid;
        this.status = status;
        this.executeStatus = executeStatus;
        this.delayDays = delayDays;
        this.lineNumber = lineNumber;
        this.color = color;
        this.swimLaneIdx = swimLaneIdx;
        this.panelIdx = panelIdx;
        this.valign = valign;
        this.times = times;
        this.totalTimes = totalTimes;
        this.x = x;
        this.y = y;
        this.planTreeTotal = planTreeTotal;
        this.childPoid = childPoid;
        this.totalChildPoid = totalChildPoid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlanOid() {
        return planOid;
    }

    public void setPlanOid(String planOid) {
        this.planOid = planOid;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDep() {
        return planDep;
    }

    public void setPlanDep(String planDep) {
        this.planDep = planDep;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBeginTimeStr() {
        return beginTimeStr;
    }

    public void setBeginTimeStr(String beginTimeStr) {
        this.beginTimeStr = beginTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getParentOid() {
        return parentOid;
    }

    public void setParentOid(String parentOid) {
        this.parentOid = parentOid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public int getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(int delayDays) {
        this.delayDays = delayDays;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSwimLaneIdx() {
        return swimLaneIdx;
    }

    public void setSwimLaneIdx(int swimLaneIdx) {
        this.swimLaneIdx = swimLaneIdx;
    }

    public int getPanelIdx() {
        return panelIdx;
    }

    public void setPanelIdx(int panelIdx) {
        this.panelIdx = panelIdx;
    }

    public String getValign() {
        return valign;
    }

    public void setValign(String valign) {
        this.valign = valign;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(int totalTimes) {
        this.totalTimes = totalTimes;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPlanTreeTotal() {
        return planTreeTotal;
    }

    public void setPlanTreeTotal(int planTreeTotal) {
        this.planTreeTotal = planTreeTotal;
    }

    public List<String> getChildPoid() {
        return childPoid;
    }

    public void setChildPoid(List<String> childPoid) {
        this.childPoid = childPoid;
    }

    public List<String> getTotalChildPoid() {
        return totalChildPoid;
    }

    public void setTotalChildPoid(List<String> totalChildPoid) {
        this.totalChildPoid = totalChildPoid;
    }

}
