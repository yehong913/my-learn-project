package com.glaway.ids.project.plan.vo;


import java.util.Date;

/**
 * 计划
 * @author zhousuxia
 * @version 2019年9月14日
 *
 */
public class PlanDataVo
{
    
    private String id = null;
    
    /* 序号  */
    private int planNumber = 0;

    private String planName = null;

    private String owner = null;

    private String planLevel = null;

    private String milestone = null;

    private String taskNameType = null;

    private String taskType = null;

    private String bizCurrent = null;

    private String createFullName = null;

    private String createTime = null;

    private String remark = null;

    private String ownerDept = null;

    /* 开始时间  */
    private String planStartTime = null;

    /* 结束时间  */
    private String planEndTime = null;

    /* 工期  */
    private String workTime = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(int planNumber) {
        this.planNumber = planNumber;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPlanLevel() {
        return planLevel;
    }

    public void setPlanLevel(String planLevel) {
        this.planLevel = planLevel;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public String getTaskNameType() {
        return taskNameType;
    }

    public void setTaskNameType(String taskNameType) {
        this.taskNameType = taskNameType;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getBizCurrent() {
        return bizCurrent;
    }

    public void setBizCurrent(String bizCurrent) {
        this.bizCurrent = bizCurrent;
    }

    public String getCreateFullName() {
        return createFullName;
    }

    public void setCreateFullName(String createFullName) {
        this.createFullName = createFullName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOwnerDept() {
        return ownerDept;
    }

    public void setOwnerDept(String ownerDept) {
        this.ownerDept = ownerDept;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
}
