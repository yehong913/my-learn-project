package com.glaway.ids.common.pbmn.activity.dto;


import com.glaway.foundation.common.vdata.GLVData;


/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author shitian
 * @version 2018年8月20日
 * @see BpmnTaskDto
 * @since
 */
public class BpmnTaskDto extends GLVData {

    /**
     * 流程来源Id
     */
    private String originId;

    /**
     * 流程节点顺序()<br>
     */
    private String orderNum;
    
    /**
     * 有效: 1, 无效： 0<br>
     */
    private String avaliable;
    
    /**
     * 当前流程ID<br>
     */
    private String processId;

    /**
     * Bpmn节点名称<br>
     */
    private String name;

    /**
     * Bpmn节点包含角色<br>
     */
    private String roles;

    /**
     * 流程节点审批方式<br>
     */
    private String approveType;

    /**
     * 流程节点可选人数<br>
     * 0：单人，1：多人
     */
    private String numbers;

    /**
     * 备注<br>
     */
    private String remark;
   
    /**
     * 表单<br>
     */
    private String formId;
    
    /**
     * 版本<br>
     */
    private String version;
    
    /**
     * 会签人数百分比（1~100）<br>
     */
    private String approvePercent;

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
    
    public String getApprovePercent() {
        return approvePercent;
    }

    public void setApprovePercent(String approvePercent) {
        this.approvePercent = approvePercent;
    }
    
    public String getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(String avaliable) {
        this.avaliable = avaliable;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    @Override
    public String toString() {
        return "BpmnTask[originId=" + originId + ", orderNum=" + orderNum + ", name=" + name
               + ", roles=" + roles + ", approveType=" + approveType + ", numbers=" + numbers + ", version=" + version
               + ", remark=" + remark +", formId=" + formId + ", approvePercent=" + approvePercent +"]";
    }
}
