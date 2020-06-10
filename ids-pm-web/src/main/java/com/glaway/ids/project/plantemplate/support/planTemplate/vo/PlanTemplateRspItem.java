/*
 * 文件名：PlanTemplateRspItem.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年3月30日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.plantemplate.support.planTemplate.vo;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2015年3月30日
 * @see PlanTemplateRspItem
 * @since
 */

public class PlanTemplateRspItem {
    /**
     * 模板编号<br>
     */
    private String id;
    
    /**
     * 模板计划节点<br>
     */
    private String planTmpNumber;

    /**
     * 模板名称<br>
     */
    private String name;

    /**
     * 创建者编号-创建者姓名<br>
     */
    private String createName;
    
    /**
     * 流程状态<br>
     */
    private String approveStatus;

    /**
     * 状态<br>
     */
    private String status;

    /**
     * 创建者时间<br>
     */
    private String createTimeStr;

    /**
     * 备注<br>
     */
    private String remark;
    
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    
    /**
     * 版本<br>
     */
    private String bizVersion;

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the createName.
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * @param createName
     *            The createName to set.
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Returns the createTimeStr.
     */
    public String getCreateTimeStr() {
        return createTimeStr;
    }

    /**
     * @param createTimeStr
     *            The createTimeStr to set.
     */
    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return Returns the approveStatus.
     */
    public String getApproveStatus() {
        return approveStatus;
    }

    /**
     * @param approveStatus The approveStatus to set.
     */
    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    /**
     * @return Returns the planTmpNumber.
     */
    public String getPlanTmpNumber() {
        return planTmpNumber;
    }

    /**
     * @param planTmpNumber The planTmpNumber to set.
     */
    public void setPlanTmpNumber(String planTmpNumber) {
        this.planTmpNumber = planTmpNumber;
    }

    public String getBizVersion() {
        return bizVersion;
    }

    public void setBizVersion(String bizVersion) {
        this.bizVersion = bizVersion;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
