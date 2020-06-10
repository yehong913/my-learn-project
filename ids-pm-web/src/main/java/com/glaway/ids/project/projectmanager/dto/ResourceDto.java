package com.glaway.ids.project.projectmanager.dto;


import com.glaway.foundation.common.vdata.GLVData;

import java.util.Date;


/**
 * A representation of the model object '<em><b>ResourceDto</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @author wangyangzan
 * @generated
 */
public class ResourceDto extends GLVData {

    /**
     * <!-- begin-user-doc -->资源编号
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String no = null;

    /**
     * <!-- begin-user-doc -->资源名称
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String name = null;

    /**
     * <!-- begin-user-doc -->父节点
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String parentId = null;

    /**
     * 父节点
     */
    private ResourceFolderDto parent = null;

    /**
     * <!-- begin-user-doc -->可用时间类型
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String useableTimeType = null;

    /**
     * <!-- begin-user-doc -->可用时间段
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String useableTimeSection = null;

    /**
     * <!-- begin-user-doc -->开始时间
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date startTime = null;

    /**
     * <!-- begin-user-doc -->结束时间
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date endTime = null;

    /**
     * <!-- begin-user-doc -->关键资源
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String keyResource = null;

    /**
     * <!-- begin-user-doc -->状态
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String stopFlag = null;

    /**
     * <!-- begin-user-doc -->占用预警
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String occupationWarn = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String path = null;

    public ResourceFolderDto getParent() {
        return parent;
    }

    public void setParent(ResourceFolderDto parent) {
        this.parent = parent;
    }

    /**
     * Returns the value of '<em><b>no</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>no</b></em>' feature
     * @generated
     */
    public String getNo() {
        return no;
    }

    /**
     * Sets the '{@link ResourceDto#getNo() <em>no</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newNo
     *            the new value of the '{@link ResourceDto#getNo() no}' feature.
     * @generated
     */
    public void setNo(String newNo) {
        no = newNo;
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
     * Sets the '{@link ResourceDto#getName() <em>name</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newName
     *            the new value of the '{@link ResourceDto#getName() name}' feature.
     * @generated
     */
    public void setName(String newName) {
        name = newName;
    }

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
     * Sets the '{@link ResourceDto#getParentId() <em>parentId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newParentId
     *            the new value of the '{@link ResourceDto#getParentId() parentId}' feature.
     * @generated
     */
    public void setParentId(String newParentId) {
        parentId = newParentId;
    }

    /**
     * Returns the value of '<em><b>useableTimeType</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>useableTimeType</b></em>' feature
     * @generated
     */
    public String getUseableTimeType() {
        return useableTimeType;
    }

    /**
     * Sets the '{@link ResourceDto#getUseableTimeType() <em>useableTimeType</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseableTimeType
     *            the new value of the '{@link ResourceDto#getUseableTimeType() useableTimeType}'
     *            feature.
     * @generated
     */
    public void setUseableTimeType(String newUseableTimeType) {
        useableTimeType = newUseableTimeType;
    }

    /**
     * Returns the value of '<em><b>useableTimeSection</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>useableTimeSection</b></em>' feature
     * @generated
     */
    public String getUseableTimeSection() {
        return useableTimeSection;
    }

    /**
     * Sets the '{@link ResourceDto#getUseableTimeSection() <em>useableTimeSection</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseableTimeSection
     *            the new value of the '{@link ResourceDto#getUseableTimeSection() useableTimeSection}
     *            ' feature.
     * @generated
     */
    public void setUseableTimeSection(String newUseableTimeSection) {
        useableTimeSection = newUseableTimeSection;
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
     * Sets the '{@link ResourceDto#getStartTime() <em>startTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStartTime
     *            the new value of the '{@link ResourceDto#getStartTime() startTime}' feature.
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
     * Sets the '{@link ResourceDto#getEndTime() <em>endTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newEndTime
     *            the new value of the '{@link ResourceDto#getEndTime() endTime}' feature.
     * @generated
     */
    public void setEndTime(Date newEndTime) {
        endTime = newEndTime;
    }

    /**
     * Returns the value of '<em><b>keyResource</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>keyResource</b></em>' feature
     * @generated
     */
    public String getKeyResource() {
        return keyResource;
    }

    /**
     * Sets the '{@link ResourceDto#getKeyResource() <em>keyResource</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newKeyResource
     *            the new value of the '{@link ResourceDto#getKeyResource() keyResource}' feature.
     * @generated
     */
    public void setKeyResource(String newKeyResource) {
        keyResource = newKeyResource;
    }

    /**
     * Returns the value of '<em><b>stopFlag</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>stopFlag</b></em>' feature
     * @generated
     */
    public String getStopFlag() {
        return stopFlag;
    }

    /**
     * Sets the '{@link ResourceDto#getStopFlag() <em>stopFlag</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStopFlag
     *            the new value of the '{@link ResourceDto#getStopFlag() stopFlag}' feature.
     * @generated
     */
    public void setStopFlag(String newStopFlag) {
        stopFlag = newStopFlag;
    }

    /**
     * Returns the value of '<em><b>occupationWarn</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>occupationWarn</b></em>' feature
     * @generated
     */
    public String getOccupationWarn() {
        return occupationWarn;
    }

    /**
     * Sets the '{@link ResourceDto#getOccupationWarn() <em>occupationWarn</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOccupationWarn
     *            the new value of the '{@link ResourceDto#getOccupationWarn() occupationWarn}'
     *            feature.
     * @generated
     */
    public void setOccupationWarn(String newOccupationWarn) {
        occupationWarn = newOccupationWarn;
    }

    /**
     * Returns the value of '<em><b>path</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>path</b></em>' feature
     * @generated
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the '{@link ResourceDto#getPath() <em>path</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPath
     *            the new value of the '{@link ResourceDto#getPath() path}' feature.
     * @generated
     */
    public void setPath(String newPath) {
        path = newPath;
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
        return "ResourceDto " + " [no: " + getNo() + "]" + " [name: " + getName() + "]"
               + " [parentId: " + getParentId() + "]" + " [useableTimeType: "
               + getUseableTimeType() + "]" + " [useableTimeSection: " + getUseableTimeSection()
               + "]" + " [startTime: " + getStartTime() + "]" + " [endTime: " + getEndTime() + "]"
               + " [keyResource: " + getKeyResource() + "]" + " [stopFlag: " + getStopFlag() + "]"
               + " [occupationWarn: " + getOccupationWarn() + "]" + " [path: " + getPath() + "]";
    }
}
