package com.glaway.ids.rdtask.task.vo;


import javax.persistence.Basic;
import javax.persistence.Transient;


/**
 * 流程任务变更 前后置关系Vo
 * 
 * @generated
 */
public class FlowTaskPreposeVo {
    
    /**
     * id
     */
    private String id = null;

    /**
     * <!-- begin-user-doc -->流程任务id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String flowTaskId = null;
    
    @Transient
    private FlowTaskVo flowTaskInfo = null;
    
    /**
     * <!-- begin-user-doc -->前置计划id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String preposeId = null;

    @Transient
    private FlowTaskVo preposePlanInfo = null;

    /**
     * <!-- begin-user-doc -->是否可用
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private short avaliable = 1;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String formId = null;
    
    /**
     * Returns the value of '<em><b>flowTaskId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>flowTaskId</b></em>' feature
     * @generated
     */
    public String getFlowTaskId() {
        return flowTaskId;
    }

    /**
     * Sets the '{@link FlowTaskPrepose#getFlowTaskId() <em>flowTaskId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFlowTaskId
     *            the new value of the '{@link FlowTaskPrepose#getFlowTaskId() flowTaskId}'
     *            feature.
     * @generated
     */
    public void setFlowTaskId(String newFlowTaskId) {
        flowTaskId = newFlowTaskId;
    }

    /**
     * Returns the value of '<em><b>avaliable</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>avaliable</b></em>' feature
     * @generated
     */
    public short getAvaliable() {
        return avaliable;
    }

    /**
     * Sets the '{@link FlowTaskPrepose#getAvaliable() <em>avaliable</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAvaliable
     *            the new value of the '{@link FlowTaskPrepose#getAvaliable() avaliable}' feature.
     * @generated
     */
    public void setAvaliable(short newAvaliable) {
        avaliable = newAvaliable;
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
     * Sets the '{@link FlowTaskPrepose#getFormId() <em>formId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFormId
     *            the new value of the '{@link FlowTaskPrepose#getFormId() formId}' feature.
     * @generated
     */
    public void setFormId(String newFormId) {
        formId = newFormId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FlowTaskVo getFlowTaskInfo() {
        return flowTaskInfo;
    }

    public void setFlowTaskInfo(FlowTaskVo flowTaskInfo) {
        this.flowTaskInfo = flowTaskInfo;
    }

    public String getPreposeId() {
        return preposeId;
    }

    public void setPreposeId(String preposeId) {
        this.preposeId = preposeId;
    }

    public FlowTaskVo getPreposePlanInfo() {
        return preposePlanInfo;
    }

    public void setPreposePlanInfo(FlowTaskVo preposePlanInfo) {
        this.preposePlanInfo = preposePlanInfo;
    } 

}
