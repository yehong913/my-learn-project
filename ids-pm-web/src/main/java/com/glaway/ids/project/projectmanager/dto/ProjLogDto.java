package com.glaway.ids.project.projectmanager.dto;


import com.glaway.foundation.businessobject.entity.BusinessObject;
import com.glaway.foundation.common.vdata.GLVData;

import javax.persistence.*;


/**
 * A representation of the model object '<em><b>ProjLog</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProjLogDto extends GLVData {
    
    /**
     * <!-- begin-user-doc -->项目id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projectId = null;

    /**
     * <!-- begin-user-doc -->项目编号
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projectNumber = null;

    /**
     * <!-- begin-user-doc -->操作信息
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String logInfo = null;

    /**
     * <!-- begin-user-doc -->操作人
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String createUserName = null;

    /**
     * <!-- begin-user-doc -->备注
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String remark = null;
    
    /**
     * <!-- begin-user-doc -->用于显示日志操作人
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String showName;
    
    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
    
    public String getProjectId()
    {
        return projectId;
    }

    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }

    /**
     * Returns the value of '<em><b>projectNumber</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>projectNumber</b></em>' feature
     * @generated
     */
    public String getProjectNumber() {
        return projectNumber;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjectNumber
     * @generated
     */
    public void setProjectNumber(String newProjectNumber) {
        projectNumber = newProjectNumber;
    }

    /**
     * Returns the value of '<em><b>logInfo</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>logInfo</b></em>' feature
     * @generated
     */
    public String getLogInfo() {
        return logInfo;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newLogInfo
     * @generated
     */
    public void setLogInfo(String newLogInfo) {
        logInfo = newLogInfo;
    }

    /**
     * Returns the value of '<em><b>createUserName</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>createUserName</b></em>' feature
     * @generated
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newCreateUserName
     *            feature.
     * @generated
     */
    public void setCreateUserName(String newCreateUserName) {
        createUserName = newCreateUserName;
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRemark
     * @generated
     */
    public void setRemark(String newRemark) {
        remark = newRemark;
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
        return "ProjLog " + " [projectNumber: " + getProjectNumber() + "]" + " [logInfo: "
               + getLogInfo() + "]" + " [createUserName: " + getCreateUserName() + "]"
               + " [remark: " + getRemark() + "]";
    }
}
