/*
 * 文件名：PlanTemplateReq.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年3月30日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.config.vo;

import java.io.Serializable;

/**
 * 〈流程节点属性Vo〉
 * 〈功能详细描述〉
 * 
 * @author duanpengfei
 * @version 2015年3月30日
 * @see BpmnTaskVo
 * @since
 */

public class BpmnTaskVo implements Serializable, Cloneable{
    
    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 流程来源Id
     */
    private String originId;
    /**
     * Bpmn节点Id<br>
     */
    private String id;
    /**
     * Bpmn节点序号<br>
     */
    private String orderNum;

    /**
     * Bpmn节点名称<br>
     */
    private String name;

    /**
     * Bpmn节点包含角色<br>
     */
    private String roles;


    /**
     * 创建者审批方式<br>
     */
    private String approveType;

    /**
     * 可选人数<br>
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
     * 审批方式名称
     */
    private String approveTypeName;
    
    /**
     * 会签百分比（1~100）<br>
     */
    private String approvePercent;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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

    
    
    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getApproveTypeName() {
        return approveTypeName;
    }

    public void setApproveTypeName(String approveTypeName) {
        this.approveTypeName = approveTypeName;
    }
    
    
    public String getApprovePercent() {
        return approvePercent;
    }

    public void setApprovePercent(String approvePercent) {
        this.approvePercent = approvePercent;
    }

    @Override
    public Object clone()
        throws CloneNotSupportedException {
        return super.clone();
    }
}
