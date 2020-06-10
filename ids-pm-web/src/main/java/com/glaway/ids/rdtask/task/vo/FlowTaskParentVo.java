package com.glaway.ids.rdtask.task.vo;


import com.glaway.ids.project.plan.dto.PlanDto;
import org.springframework.data.annotation.Transient;

import javax.persistence.Basic;
import javax.persistence.Column;



/**
 * 流程任务变更时、父计划的flowResolveXml存储Vo
 * 
 * @generated
 */
public class FlowTaskParentVo {

    /**
     * 页签组合模板id
     */
    @Basic()
    private String tabCbTemplateId = null;
    
    /**
     * <!-- begin-user-doc --> 是否单条下达驳回的 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String isChangeSingleBack = "false";
    
    /**
     * 
     */
    private String id = null;
    
    /**
     * 提交标志
     */
    @Basic()
    private String submitFlag = null;

    /**
     * <!-- begin-user-doc -->父节点id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String parentId = null;

    /**
     * 父计划
     */
    @Transient
    private PlanDto parentPlan;
    
    
    /**
     * 日期类型(1.公司日历（companyDay）,2.自然日（naturalDay）,3。工作日（workDay）)
     */
    @Basic()
    private String workTimeType = null;

    /**
     * <!-- begin-user-doc -->关联的外键id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String formId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(columnDefinition = "CLOB")
    private String flowResolveXml = null;

    /**
     * Returns the value of '<em><b>parentId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>parentId</b></em>' feature
     * @generated
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Sets the '{@link FlowTaskParent#getParentId() <em>parentId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newParentId
     *            the new value of the '{@link FlowTaskParent#getParentId() parentId}' feature.
     * @generated
     */
    public void setParentId(String newParentId) {
        parentId = newParentId;
    }

    /**
     * Returns the value of '<em><b>formId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>formId</b></em>' feature
     * @generated
     */
    public String getFormId() {
        return formId;
    }

    /**
     * Sets the '{@link FlowTaskParent#getFormId() <em>formId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFormId
     *            the new value of the '{@link FlowTaskParent#getFormId() formId}' feature.
     * @generated
     */
    public void setFormId(String newFormId) {
        formId = newFormId;
    }

    /**
     * Returns the value of '<em><b>flowResolveXml</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>flowResolveXml</b></em>' feature
     * @generated
     */
    public String getFlowResolveXml() {
        return flowResolveXml;
    }

    /**
     * Sets the '{@link FlowTaskParent#getFlowResolveXml() <em>flowResolveXml</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFlowResolveXml
     *            the new value of the '{@link FlowTaskParent#getFlowResolveXml() flowResolveXml}'
     *            feature.
     * @generated
     */
    public void setFlowResolveXml(String newFlowResolveXml) {
        flowResolveXml = newFlowResolveXml;
    }

    public PlanDto getParentPlan() {
        return parentPlan;
    }

    public void setParentPlan(PlanDto parentPlan) {
        this.parentPlan = parentPlan;
    }

    public String getWorkTimeType() {
        return workTimeType;
    }

    public void setWorkTimeType(String workTimeType) {
        this.workTimeType = workTimeType;
    }

    public String getSubmitFlag() {
        return submitFlag;
    }

    public void setSubmitFlag(String submitFlag) {
        this.submitFlag = submitFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsChangeSingleBack() {
        return isChangeSingleBack;
    }

    public void setIsChangeSingleBack(String isChangeSingleBack) {
        this.isChangeSingleBack = isChangeSingleBack;
    }

    public String getTabCbTemplateId() {
        return tabCbTemplateId;
    }

    public void setTabCbTemplateId(String tabCbTemplateId) {
        this.tabCbTemplateId = tabCbTemplateId;
    }

}
