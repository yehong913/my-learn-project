package com.glaway.ids.project.projectmanager.vo;

public class ProjWarnForGridVo {

    /**
     * 计划id
     */
    private String id;
    
    /** 计划名称*/
    private String planName;
    
    /** 计划等级*/
    private String planLevel;

    /**
     * 计划等级名称
     */
    private String planLevelName;
    
    /** 计划状态*/
    private String bizCurrent;
    
    /** 负责人*/
    private String owner;
    
    /** 负责人名称*/
    private String ownerShow;

    /**
     * 计划开始时间
     */
    private String planStartTime;

    /**
     * 计划结束时间
     */
    private String planEndTime;

    /**
     * 项目进度
     */
    private String progressRate;

    /**
     * 工期
     */
    private String workTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanLevel() {
        return planLevel;
    }

    public void setPlanLevel(String planLevel) {
        this.planLevel = planLevel;
    }

    public String getPlanLevelName() {
        return planLevelName;
    }

    public void setPlanLevelName(String planLevelName) {
        this.planLevelName = planLevelName;
    }

    public String getBizCurrent() {
        return bizCurrent;
    }

    public void setBizCurrent(String bizCurrent) {
        this.bizCurrent = bizCurrent;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerShow() {
        return ownerShow;
    }

    public void setOwnerShow(String ownerShow) {
        this.ownerShow = ownerShow;
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

    public String getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(String progressRate) {
        this.progressRate = progressRate;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
}
