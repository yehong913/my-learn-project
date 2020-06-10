package com.glaway.ids.project.plan.dto;


import java.util.Date;


import javax.persistence.Basic;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.glaway.foundation.common.vdata.GLVData;


/**
 * 流程任务变更 资源表
 * 
 * @generated
 */
public class FlowTaskResourceLinkInfoDto extends GLVData {

    /**
     * 流程任务变更 资源表
     * 
     * @generated
     */
    @Basic()
    private String linkInfoId = null;

    /**
     * 流程任务变更 资源表
     * 
     * @generated
     */
    @Basic()
    private String resourceId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String useObjectType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String useObjectId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String useObjectTempId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Temporal(TemporalType.DATE)
    private Date startTime = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Temporal(TemporalType.DATE)
    private Date endTime = null;

    /**
     * 
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date paramTime = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String useRate = null;

    /**
     * 
     */
    @Transient()
    private String resourceName = null;

    /**
     * 
     */
    @Transient()
    private String resourceType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String formId = null;

    /**
     * Returns the value of '<em><b>linkInfoId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>linkInfoId</b></em>' feature
     * @generated
     */
    public String getLinkInfoId() {
        return linkInfoId;
    }

    /**
     * Sets the '{@link FlowTaskResourceLinkInfo#getLinkInfoId() <em>linkInfoId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newLinkInfoId
     *            the new value of the '{@link FlowTaskResourceLinkInfo#getLinkInfoId() linkInfoId}
     *            ' feature.
     * @generated
     */
    public void setLinkInfoId(String newLinkInfoId) {
        linkInfoId = newLinkInfoId;
    }

    /**
     * Returns the value of '<em><b>resourceId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>resourceId</b></em>' feature
     * @generated
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Sets the '{@link FlowTaskResourceLinkInfo#getResourceId() <em>resourceId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newResourceId
     *            the new value of the '{@link FlowTaskResourceLinkInfo#getResourceId() resourceId}
     *            ' feature.
     * @generated
     */
    public void setResourceId(String newResourceId) {
        resourceId = newResourceId;
    }

    /**
     * Returns the value of '<em><b>useObjectType</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>useObjectType</b></em>' feature
     * @generated
     */
    public String getUseObjectType() {
        return useObjectType;
    }

    /**
     * Sets the '{@link FlowTaskResourceLinkInfo#getUseObjectType() <em>useObjectType</em>}'
     * feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectType
     *            the new value of the '{@link FlowTaskResourceLinkInfo#getUseObjectType()
     *            useObjectType}' feature.
     * @generated
     */
    public void setUseObjectType(String newUseObjectType) {
        useObjectType = newUseObjectType;
    }

    /**
     * Returns the value of '<em><b>useObjectId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>useObjectId</b></em>' feature
     * @generated
     */
    public String getUseObjectId() {
        return useObjectId;
    }

    /**
     * Sets the '{@link FlowTaskResourceLinkInfo#getUseObjectId() <em>useObjectId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectId
     *            the new value of the '{@link FlowTaskResourceLinkInfo#getUseObjectId()
     *            useObjectId}' feature.
     * @generated
     */
    public void setUseObjectId(String newUseObjectId) {
        useObjectId = newUseObjectId;
    }

    /**
     * Returns the value of '<em><b>useObjectTempId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>useObjectTempId</b></em>' feature
     * @generated
     */
    public String getUseObjectTempId() {
        return useObjectTempId;
    }

    /**
     * Sets the '{@link FlowTaskResourceLinkInfo#getUseObjectTempId() <em>useObjectTempId</em>}'
     * feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectTempId
     *            the new value of the '{@link FlowTaskResourceLinkInfo#getUseObjectTempId()
     *            useObjectTempId}' feature.
     * @generated
     */
    public void setUseObjectTempId(String newUseObjectTempId) {
        useObjectTempId = newUseObjectTempId;
    }

    /**
     * Returns the value of '<em><b>startTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>startTime</b></em>' feature
     * @generated
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the '{@link FlowTaskResourceLinkInfo#getStartTime() <em>startTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStartTime
     *            the new value of the '{@link FlowTaskResourceLinkInfo#getStartTime() startTime}'
     *            feature.
     * @generated
     */
    public void setStartTime(Date newStartTime) {
        startTime = newStartTime;
    }

    /**
     * Returns the value of '<em><b>endTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>endTime</b></em>' feature
     * @generated
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the '{@link FlowTaskResourceLinkInfo#getEndTime() <em>endTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newEndTime
     *            the new value of the '{@link FlowTaskResourceLinkInfo#getEndTime() endTime}'
     *            feature.
     * @generated
     */
    public void setEndTime(Date newEndTime) {
        endTime = newEndTime;
    }

    /**
     * Returns the value of '<em><b>useRate</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>useRate</b></em>' feature
     * @generated
     */
    public String getUseRate() {
        return useRate;
    }

    /**
     * Sets the '{@link FlowTaskResourceLinkInfo#getUseRate() <em>useRate</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseRate
     *            the new value of the '{@link FlowTaskResourceLinkInfo#getUseRate() useRate}'
     *            feature.
     * @generated
     */
    public void setUseRate(String newUseRate) {
        useRate = newUseRate;
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
     * Sets the '{@link FlowTaskResourceLinkInfo#getFormId() <em>formId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFormId
     *            the new value of the '{@link FlowTaskResourceLinkInfo#getFormId() formId}'
     *            feature.
     * @generated
     */
    public void setFormId(String newFormId) {
        formId = newFormId;
    }

//    public Resource getResourceInfo() {
//        return resourceInfo;
//    }
//
//    public void setResourceInfo(Resource resourceInfo) {
//        this.resourceInfo = resourceInfo;
//    }

    public Date getParamTime() {
        return paramTime;
    }

    public void setParamTime(Date paramTime) {
        this.paramTime = paramTime;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

}
