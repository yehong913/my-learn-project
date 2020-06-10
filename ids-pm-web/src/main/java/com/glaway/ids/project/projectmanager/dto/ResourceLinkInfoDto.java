package com.glaway.ids.project.projectmanager.dto;


import com.glaway.foundation.common.vdata.GLVData;

import java.util.Date;


/**
 * A representation of the model object '<em><b>ResourceLinkInfoDto</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @author wangyangzan
 * @generated
 */
public class ResourceLinkInfoDto extends GLVData {

    /**
     * <!-- begin-user-doc --> 资源id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String resourceId = null;

    /**
     * <!-- begin-user-doc --> 关联的资源信息<!-- end-user-doc -->
     */
    private ResourceDto resourceInfo;

    /**
     * <!-- begin-user-doc --> 关联的外键类型<!-- end-user-doc -->
     * 
     * @generated
     */
    private String useObjectType = null;

    /**
     * <!-- begin-user-doc --> 关联的外键id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String useObjectId = null;

    /**
     * <!-- begin-user-doc --> 关联的外键临时id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String useObjectTempId = null;

    /**
     * <!-- begin-user-doc --> 开始时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date startTime = null;

    /**
     * <!-- begin-user-doc --> 结束时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date endTime = null;

    /**
     * 时间
     */
    private Date paramTime = null;

    /**
     * <!-- begin-user-doc --> 进度 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String useRate = "0";

    /**
     * 资源名称
     */
    private String resourceName = null;

    /**
     * 资源类型
     */
    private String resourceType = null;

    public ResourceDto getResourceInfo() {
        return resourceInfo;
    }

    public void setResourceInfo(ResourceDto resourceInfo) {
        this.resourceInfo = resourceInfo;
    }

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
     * Sets the '{@link ResourceLinkInfoDto#getResourceId() <em>resourceId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newResourceId
     *            the new value of the '{@link ResourceLinkInfoDto#getResourceId() resourceId}'
     *            feature.
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
     * Sets the '{@link ResourceLinkInfoDto#getUseObjectType() <em>useObjectType</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectType
     *            the new value of the '{@link ResourceLinkInfoDto#getUseObjectType() useObjectType}'
     *            feature.
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
     * Sets the '{@link ResourceLinkInfoDto#getUseObjectId() <em>useObjectId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectId
     *            the new value of the '{@link ResourceLinkInfoDto#getUseObjectId() useObjectId}'
     *            feature.
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
     * Sets the '{@link ResourceLinkInfoDto#getUseObjectTempId() <em>useObjectTempId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectTempId
     *            the new value of the '{@link ResourceLinkInfoDto#getUseObjectTempId()
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
     * Sets the '{@link ResourceLinkInfoDto#getStartTime() <em>startTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStartTime
     *            the new value of the '{@link ResourceLinkInfoDto#getStartTime() startTime}' feature.
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
     * Sets the '{@link ResourceLinkInfoDto#getEndTime() <em>endTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newEndTime
     *            the new value of the '{@link ResourceLinkInfoDto#getEndTime() endTime}' feature.
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
     * Sets the '{@link ResourceLinkInfoDto#getUseRate() <em>useRate</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseRate
     *            the new value of the '{@link ResourceLinkInfoDto#getUseRate() useRate}' feature.
     * @generated
     */
    public void setUseRate(String newUseRate) {
        useRate = newUseRate;
    }

    /**
     * A toString method which prints the values of all EAttributes of this instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "ResourceLinkInfoDto " + " [resourceId: " + getResourceId() + "]"
               + " [useObjectType: " + getUseObjectType() + "]" + " [useObjectId: "
               + getUseObjectId() + "]" + " [useObjectTempId: " + getUseObjectTempId() + "]"
               + " [startTime: " + getStartTime() + "]" + " [endTime: " + getEndTime() + "]"
               + " [useRate: " + getUseRate() + "]";
    }
}
