package com.glaway.ids.project.projectmanager.vo;

public class ProjWarnVo {
    
    /** 项目ID*/
    private String projectId;
    
    /** 项目名称*/
    private String projectName;
    
    /** 计划预警*/
    private String warn;
    
    /** 项目创建时间*/
    private String createTime;
    
    /** 预警数量*/
    private String warnNumber;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getWarnNumber() {
        return warnNumber;
    }

    public void setWarnNumber(String warnNumber) {
        this.warnNumber = warnNumber;
    }
}
