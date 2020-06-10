package com.glaway.ids.rdtask.task.vo;

import java.util.Date;

/**
 * 流程模板VO对象
 * @author wqb
 *
 */
public class TaskProcTemplateVo {
    
    /**
     * 页数
     */
    private String count = "1";
    
    /**
     * 启动状态
     */
    private String status = null;
    
    /** 任务流程编号*/
    private String id;
    
    /** 创建者*/
    private String firstBy;
    
    /** 创建时间*/
    private String firstTimeStr;
    
    /** 创建人信息*/
    private String creator;
    
    /** 创建者*/
    private String createBy;
    
    /** 更新者*/
    private String updateBy;

    /** 创建名称*/
    private String createName;

    /** 创建时间*/
    private Date createTime;
    
    /** 创建时间*/
    private String createTimeStr;

    /** 更新名称*/
    private String updateName;

    /** 更新时间*/
    private Date updateTime;

    /** 是否删除*/
    private String avaliable;

    /** 生命周期状态*/
    private String bizCurrent;

    /** 版本编号*/
    private String bizId;

    /** 版本号*/
    private String bizVersion;

    /** 备注*/
    private String remark;

    /** 密级*/
    private String securityLevel;

    /** 生命周期父编号*/
    private String policy_Id;

    /** 使用数量*/
    private String activityCount;

    /** 模板子名称*/
    private String procTmpSubName;

    /** 模板名称*/
    private String procTmplName;

    /** 流程模板编辑器*/
    private String temlXml;
    
    /**操作*/
    private String operation;
    
    private String ProcessInstanceId;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getProcTmpSubName() {
    return procTmpSubName;
    }

    public void setProcTmpSubName(String procTmpSubName) {
    this.procTmpSubName = procTmpSubName;
    }

    public String getProcTmplName() {
    return procTmplName;
    }

    public void setProcTmplName(String procTmplName) {
    this.procTmplName = procTmplName;
    }

    public String getId() {
    return id;
    }

    public void setId(String id) {
    this.id = id;
    }

    public String getCreateBy() {
    return createBy;
    }

    public void setCreateBy(String createBy) {
    this.createBy = createBy;
    }

    public String getUpdateBy() {
    return updateBy;
    }

    public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
    }

    public String getCreateName() {
    return createName;
    }

    public void setCreateName(String createName) {
    this.createName = createName;
    }

    public Date getCreateTime() {
    return createTime;
    }

    public void setCreateTime(Date createTime) {
    this.createTime = createTime;
    }

    public String getUpdateName() {
    return updateName;
    }

    public void setUpdateName(String updateName) {
    this.updateName = updateName;
    }

    public Date getUpdateTime() {
    return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
    }

    public String getAvaliable() {
    return avaliable;
    }

    public void setAvaliable(String avaliable) {
    this.avaliable = avaliable;
    }

    public String getBizCurrent() {
    return bizCurrent;
    }

    public void setBizCurrent(String bizCurrent) {
    this.bizCurrent = bizCurrent;
    }

    public String getBizId() {
    return bizId;
    }

    public void setBizId(String bizId) {
    this.bizId = bizId;
    }

    public String getBizVersion() {
    return bizVersion;
    }

    public void setBizVersion(String bizVersion) {
    this.bizVersion = bizVersion;
    }

    public String getRemark() {
    return remark;
    }

    public void setRemark(String remark) {
    this.remark = remark;
    }

    public String getSecurityLevel() {
    return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
    this.securityLevel = securityLevel;
    }

    public String getPolicy_Id() {
    return policy_Id;
    }

    public void setPolicy_Id(String policy_Id) {
    this.policy_Id = policy_Id;
    }

    public String getActivityCount() {
    return activityCount;
    }

    public void setActivityCount(String activityCount) {
    this.activityCount = activityCount;
    }

    public String getTemlXml() {
    return temlXml;
    }

    public void setTemlXml(String temlXml) {
    this.temlXml = temlXml;
    }

    public String getProcessInstanceId() {
        return ProcessInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        ProcessInstanceId = processInstanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstBy() {
        return firstBy;
    }

    public void setFirstBy(String firstBy) {
        this.firstBy = firstBy;
    }

    public String getFirstTimeStr() {
        return firstTimeStr;
    }

    public void setFirstTimeStr(String firstTimeStr) {
        this.firstTimeStr = firstTimeStr;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
