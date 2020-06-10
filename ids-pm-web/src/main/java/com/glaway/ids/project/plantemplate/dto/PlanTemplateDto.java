package com.glaway.ids.project.plantemplate.dto;


import com.glaway.foundation.businessobject.entity.BusinessObject;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vdata.GLVData;


/**
 * A representation of the model object '<em><b>planTemplate</b></em>'.
 * <!-- begin-user-doc -->计划模版表 <!-- end-user-doc -->
 * 
 * @generated
 */
public class PlanTemplateDto extends GLVData {

    /**
     * <!-- begin-user-doc -->计划序号 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planTmplNumber = null;

    /**
     * <!-- begin-user-doc -->计划名称 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String name = null;

    /**
     * <!-- begin-user-doc -->备注 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String remark = "";

    /**
     * 计划开始时间
     */
    private String createTimeStr = "";

    /**
     * <!-- begin-user-doc -->计划状态 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String status = null;


    private TSUserDto user;

    /**
     * <!-- begin-user-doc -->流程实例Id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String processInstanceId = null;
    
    /**
     * 显示创建人
     */
    private String creator = "";

    /**
     * Returns the value of '<em><b>planTmplNumber</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planTmplNumber</b></em>' feature
     * @generated
     */
    public String getPlanTmplNumber() {
        return planTmplNumber;
    }

    /**
     * Sets the '{@link PlanTemplateDto#getPlanTmplNumber() <em>planTmplNumber</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanTmplNumber
     *            the new value of the '{@link PlanTemplateDto#getPlanTmplNumber() planTmplNumber}'
     *            feature.
     * @generated
     */
    public void setPlanTmplNumber(String newPlanTmplNumber) {
        planTmplNumber = newPlanTmplNumber;
    }

    /**
     * Returns the value of '<em><b>name</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>name</b></em>' feature
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the '{@link PlanTemplateDto#getName() <em>name</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newName
     *            the new value of the '{@link PlanTemplateDto#getName() name}' feature.
     * @generated
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Returns the value of '<em><b>remark</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>remark</b></em>' feature
     * @generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the '{@link PlanTemplateDto#getRemark() <em>remark</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRemark
     *            the new value of the '{@link PlanTemplateDto#getRemark() remark}' feature.
     * @generated
     */
    public void setRemark(String newRemark) {
        remark = newRemark;
    }

    /**
     * Returns the value of '<em><b>status</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>status</b></em>' feature
     * @generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the '{@link PlanTemplateDto#getStatus() <em>status</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStatus
     *            the new value of the '{@link PlanTemplateDto#getStatus() status}' feature.
     * @generated
     */
    public void setStatus(String newStatus) {
        status = newStatus;
    }

    /**
     * Returns the value of '<em><b>processInstanceId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>processInstanceId</b></em>' feature
     * @generated
     */
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * Sets the '{@link PlanTemplateDto#getProcessInstanceId() <em>processInstanceId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProcessInstanceId
     *            the new value of the '{@link PlanTemplateDto#getProcessInstanceId()
     *            processInstanceId}' feature.
     * @generated
     */
    public void setProcessInstanceId(String newProcessInstanceId) {
        processInstanceId = newProcessInstanceId;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public TSUserDto getUser() {
        return user;
    }

    public void setUser(TSUserDto user) {
        this.user = user;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * A toString method which prints the values of all EAttributes of this instance. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "planTemplate " + " [planTmplNumber: " + getPlanTmplNumber() + "]" + " [name: "
               + getName() + "]" + " [remark: " + getRemark() + "]" + " [status: " + getStatus()
               + "]" + " [processInstanceId: " + getProcessInstanceId() + "]" + " [id: " + getId() + "]";
    }
}
