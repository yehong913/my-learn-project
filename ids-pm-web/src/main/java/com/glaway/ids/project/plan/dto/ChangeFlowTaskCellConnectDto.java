package com.glaway.ids.project.plan.dto;


import javax.persistence.Basic;


import com.glaway.foundation.common.vdata.GLVData;

/**
 * 流程任务变更 节点关系表
 * 
 * @generated
 */
public class ChangeFlowTaskCellConnectDto extends GLVData {

    /**
     * <!-- begin-user-doc --> 父计划ID <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String parentPlanId = null;

    /**
     * <!-- begin-user-doc --> 预留前置节点的ID <!-- end-user-doc -->
     * 
     * @generated
     */

    @Basic()
    private String cellId = null;

    /**
     * <!-- begin-user-doc --> 后置节点的ID <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String targetId = null;

    /**
     * <!-- begin-user-doc --> 前置节点的信息 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String infoId = null;

    /**
     * <!-- begin-user-doc --> 前置节点的信息 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String targetInfoId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String formId = null;

    /**
     * Returns the value of '<em><b>parentPlanId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>parentPlanId</b></em>' feature
     * @generated
     */
    public String getParentPlanId() {
        return parentPlanId;
    }

    /**
     * Sets the '{@link ChangeFlowTaskCellConnect#getParentPlanId() <em>parentPlanId</em>}'
     * feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newParentPlanId
     *            the new value of the '{@link ChangeFlowTaskCellConnect#getParentPlanId()
     *            parentPlanId}' feature.
     * @generated
     */
    public void setParentPlanId(String newParentPlanId) {
        parentPlanId = newParentPlanId;
    }

    /**
     * Returns the value of '<em><b>cellId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>cellId</b></em>' feature
     * @generated
     */
    public String getCellId() {
        return cellId;
    }

    /**
     * Sets the '{@link ChangeFlowTaskCellConnect#getCellId() <em>cellId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newCellId
     *            the new value of the '{@link ChangeFlowTaskCellConnect#getCellId() cellId}'
     *            feature.
     * @generated
     */
    public void setCellId(String newCellId) {
        cellId = newCellId;
    }

    /**
     * Returns the value of '<em><b>targetId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>targetId</b></em>' feature
     * @generated
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * Sets the '{@link ChangeFlowTaskCellConnect#getTargetId() <em>targetId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newTargetId
     *            the new value of the '{@link ChangeFlowTaskCellConnect#getTargetId() targetId}'
     *            feature.
     * @generated
     */
    public void setTargetId(String newTargetId) {
        targetId = newTargetId;
    }

    /**
     * Returns the value of '<em><b>infoId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>infoId</b></em>' feature
     * @generated
     */
    public String getInfoId() {
        return infoId;
    }

    /**
     * Sets the '{@link ChangeFlowTaskCellConnect#getInfoId() <em>infoId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newInfoId
     *            the new value of the '{@link ChangeFlowTaskCellConnect#getInfoId() infoId}'
     *            feature.
     * @generated
     */
    public void setInfoId(String newInfoId) {
        infoId = newInfoId;
    }

    /**
     * Returns the value of '<em><b>targetInfoId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>targetInfoId</b></em>' feature
     * @generated
     */
    public String getTargetInfoId() {
        return targetInfoId;
    }

    /**
     * Sets the '{@link ChangeFlowTaskCellConnect#getTargetInfoId() <em>targetInfoId</em>}'
     * feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newTargetInfoId
     *            the new value of the '{@link ChangeFlowTaskCellConnect#getTargetInfoId()
     *            targetInfoId}' feature.
     * @generated
     */
    public void setTargetInfoId(String newTargetInfoId) {
        targetInfoId = newTargetInfoId;
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
     * Sets the '{@link ChangeFlowTaskCellConnect#getFormId() <em>formId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFormId
     *            the new value of the '{@link ChangeFlowTaskCellConnect#getFormId() formId}'
     *            feature.
     * @generated
     */
    public void setFormId(String newFormId) {
        formId = newFormId;
    }

}
