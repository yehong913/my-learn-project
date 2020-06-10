package com.glaway.ids.rdtask.task.vo;


import java.util.Date;

import javax.persistence.Transient;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vdata.GLVData;


/**	接收的信息对象
 *	wqb 2019年5月31日 16:58:57
 */

public class TaskProcTemplateDto extends GLVData {
	
	 /**
     * <!-- begin-user-doc -->流程实例Id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String processInstanceId = null;

    /**
     * <!-- begin-user-doc -->模版名称 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String procTmplName = null;

    /**
     * <!-- begin-user-doc -->模版子名称 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String procTmpSubName = null;

    /**
     * <!-- begin-user-doc -->备注说明 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String remark = null;

    /**
     * <!-- begin-user-doc -->活动数量 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String activityCount = "";

    /**
     * <!-- begin-user-doc --> 提交标记 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String submitFlag = "false";

    /**
     * 修改日期
     * 
     * @generated
     */
    private Date modifyDate = null;

    /**
     * 初始化日期
     * 
     * @generated
     */
    private Date initDate = null;

    /**
     * 启动状态
     * 
     * @generated
     */
    private String status = null;

    /**
     * 修订人部门
     * 
     * @generated
     */
    private String creatorDept = null;

    /**
     * 流程模板编辑器
     * 
     * @generated
     */
    private String temlXml = null;

    /**
     * 
     */
    private String updateByNameFormat;
    
    private String isStandard;

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }

    /**
     * 是否存在流程 0:不存在; 1:存在
     */
    private String flowFlag = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     */
    private TSUserDto creator;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     */
    private TSUserDto updater;

    /**
     * 是否存在流程 0:不存在; 1:存在
     */
    @Transient
    private String modiferName;

    /**
     * 研发流程分类id
     */
    private String procCategoryId;

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
     * Sets the '{@link TaskProcTemplateDto#getProcessInstanceId() <em>processInstanceId</em>}'
     * feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProcessInstanceId
     *            the new value of the '{@link TaskProcTemplateDto#getProcessInstanceId()
     *            processInstanceId}' feature.
     * @generated
     */
    public void setProcessInstanceId(String newProcessInstanceId) {
        processInstanceId = newProcessInstanceId;
    }

    /**
     * Returns the value of '<em><b>procTmplName</b></em>' feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>procTmplName</b></em>' feature
     * @generated
     */
    public String getProcTmplName() {
        return procTmplName;
    }

    /**
     * Sets the '{@link TaskProcTemplateDto#getProcTmplName() <em>procTmplName</em>}' feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newProcTmplName
     *            the new value of the '{@link TaskProcTemplateDto#getProcTmplName() procTmplName}'
     *            feature.
     * @generated
     */
    public void setProcTmplName(String newProcTmplName) {
        procTmplName = newProcTmplName;
    }

    /**
     * Returns the value of '<em><b>procTmpSubName</b></em>' feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>procTmpSubName</b></em>' feature
     * @generated
     */
    public String getProcTmpSubName() {
        return procTmpSubName;
    }

    /**
     * Sets the '{@link TaskProcTemplateDto#getProcTmpSubName() <em>procTmpSubName</em>}' feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newProcTmpSubName
     *            the new value of the '{@link TaskProcTemplateDto#getProcTmpSubName() procTmpSubName}
     *            ' feature.
     * @generated
     */
    public void setProcTmpSubName(String newProcTmpSubName) {
        procTmpSubName = newProcTmpSubName;
    }

    /**
     * Returns the value of '<em><b>remark</b></em>' feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>remark</b></em>' feature
     * @generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the '{@link TaskProcTemplateDto#getRemark() <em>remark</em>}' feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newRemark
     *            the new value of the '{@link TaskProcTemplateDto#getRemark() remark}' feature.
     * @generated
     */
    public void setRemark(String newRemark) {
        remark = newRemark;
    }

    /**
     * Returns the value of '<em><b>activityCount</b></em>' feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>activityCount</b></em>' feature
     * @generated
     */
    public String getActivityCount() {
        return activityCount;
    }

    /**
     * Sets the '{@link TaskProcTemplateDto#getActivityCount() <em>activityCount</em>}' feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newActivityCount
     *            the new value of the '{@link TaskProcTemplateDto#getActivityCount() activityCount}'
     *            feature.
     * @generated
     */
    public void setActivityCount(String newActivityCount) {
        activityCount = newActivityCount;
    }

    /**
     * Returns the value of '<em><b>submitFlag</b></em>' feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>submitFlag</b></em>' feature
     * @generated
     */
    public String getSubmitFlag() {
        return submitFlag;
    }

    /**
     * Sets the '{@link TaskProcTemplateDto#getSubmitFlag() <em>submitFlag</em>}' feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newSubmitFlag
     *            the new value of the '{@link TaskProcTemplateDto#getSubmitFlag() submitFlag}'
     *            feature.
     * @generated
     */
    public void setSubmitFlag(String newSubmitFlag) {
        submitFlag = newSubmitFlag;
    }

    /**
     * Returns the value of '<em><b>modifyDate</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>modifyDate</b></em>' feature
     * @generated
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Sets the '{@link TaskProcTemplateDto#getModifyDate() <em>modifyDate</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newModifyDate
     *            the new value of the '{@link TaskProcTemplateDto#getModifyDate() modifyDate}'
     *            feature.
     * @generated
     */
    public void setModifyDate(Date newModifyDate) {
        modifyDate = newModifyDate;
    }

    /**
     * Returns the value of '<em><b>initDate</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>initDate</b></em>' feature
     * @generated
     */
    public Date getInitDate() {
        return initDate;
    }

    /**
     * Sets the '{@link TaskProcTemplateDto#getInitDate() <em>initDate</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newInitDate
     *            the new value of the '{@link TaskProcTemplateDto#getInitDate() initDate}' feature.
     * @generated
     */
    public void setInitDate(Date newInitDate) {
        initDate = newInitDate;
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
     * Sets the '{@link TaskProcTemplateDto#getStatus() <em>status</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStatus
     *            the new value of the '{@link TaskProcTemplateDto#getStatus() status}' feature.
     * @generated
     */
    public void setStatus(String newStatus) {
        status = newStatus;
    }

    /**
     * Returns the value of '<em><b>creatorDept</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>creatorDept</b></em>' feature
     * @generated
     */
    public String getCreatorDept() {
        return creatorDept;
    }

    /**
     * Sets the '{@link TaskProcTemplateDto#getCreatorDept() <em>creatorDept</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newCreatorDept
     *            the new value of the '{@link TaskProcTemplateDto#getCreatorDept() creatorDept}'
     *            feature.
     * @generated
     */
    public void setCreatorDept(String newCreatorDept) {
        creatorDept = newCreatorDept;
    }

    /**
     * Returns the value of '<em><b>temlXml</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>temlXml</b></em>' feature
     * @generated
     */
    public String getTemlXml() {
        return temlXml;
    }

    /**
     * Sets the '{@link TaskProcTemplateDto#getTemlXml() <em>temlXml</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newTemlXml
     *            the new value of the '{@link TaskProcTemplateDto#getTemlXml() temlXml}' feature.
     * @generated
     */
    public void setTemlXml(String newTemlXml) {
        temlXml = newTemlXml;
    }

    public String getUpdateByNameFormat() {
        return updateByNameFormat;
    }

    public void setUpdateByNameFormat(String updateByNameFormat) {
        this.updateByNameFormat = updateByNameFormat;
    }

    public String getFlowFlag() {
        return flowFlag;
    }

    public void setFlowFlag(String flowFlag) {
        this.flowFlag = flowFlag;
    }

    public String getModiferName() {
        return modiferName;
    }

    public void setModiferName(String modiferName) {
        this.modiferName = modiferName;
    }

    public TSUserDto getCreator() {
        return creator;
    }

    public void setCreator(TSUserDto creator) {
        this.creator = creator;
    }

    public TSUserDto getUpdater() {
        return updater;
    }

    public void setUpdater(TSUserDto updater) {
        this.updater = updater;
    }

    public String getprocCategoryId() {
        return procCategoryId;
    }

    public void setprocCategoryId(String procCategoryId) {
        this.procCategoryId = procCategoryId;
    }
}
