package com.glaway.ids.project.plan.dto;


import javax.persistence.Basic;
import javax.persistence.Column;

import com.glaway.foundation.common.vdata.GLVData;


/**
 * A representation of the model object '<em><b>PlanLog</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class PlanLogDto extends GLVData {

    /**
     * <!-- begin-user-doc --> 计划id<!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String planId = null;

    /**
     * <!-- begin-user-doc --> 日志信息 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String logInfo = null;

    /**
     * <!-- begin-user-doc --> 备注<!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(length = 1000, name = "")
    private String remark = null;

    /**
     * <!-- begin-user-doc -->是否可用 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String avaliable = "1";

   /* private TSUserDto user;*/

    /**
     * <!-- begin-user-doc --> 文件路径 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String filePath = null;

    /**
     * Returns the value of '<em><b>planId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planId</b></em>' feature
     * @generated
     */
    public String getPlanId() {
        return planId;
    }

    /**
     * Sets the '{@link PlanLog#getPlanId() <em>planId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanId
     *            the new value of the '{@link PlanLog#getPlanId() planId}' feature.
     * @generated
     */
    public void setPlanId(String newPlanId) {
        planId = newPlanId;
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
     * Sets the '{@link PlanLog#getLogInfo() <em>logInfo</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newLogInfo
     *            the new value of the '{@link PlanLog#getLogInfo() logInfo}' feature.
     * @generated
     */
    public void setLogInfo(String newLogInfo) {
        logInfo = newLogInfo;
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
     * Sets the '{@link PlanLog#getRemark() <em>remark</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRemark
     *            the new value of the '{@link PlanLog#getRemark() remark}' feature.
     * @generated
     */
    public void setRemark(String newRemark) {
        remark = newRemark;
    }

    /**
     * Returns the value of '<em><b>avaliable</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>avaliable</b></em>' feature
     * @generated
     */
    public String getAvaliable() {
        return avaliable;
    }

    /**
     * Sets the '{@link PlanLog#getAvaliable() <em>avaliable</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAvaliable
     *            the new value of the '{@link PlanLog#getAvaliable() avaliable}' feature.
     * @generated
     */
    public void setAvaliable(String newAvaliable) {
        avaliable = newAvaliable;
    }

    /**
     * Returns the value of '<em><b>filePath</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>filePath</b></em>' feature
     * @generated
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the '{@link PlanLog#getFilePath() <em>filePath</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFilePath
     *            the new value of the '{@link PlanLog#getFilePath() filePath}' feature.
     * @generated
     */
    public void setFilePath(String newFilePath) {
        filePath = newFilePath;
    }

   /* public TSUserDto getUser() {
        return user;
    }

    public void setUser(TSUserDto user) {
        this.user = user;
    }*/

    /**
     * A toString method which prints the values of all EAttributes of this instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "PlanLog " + " [planId: " + getPlanId() + "]" + " [logInfo: " + getLogInfo() + "]"
               + " [remark: " + getRemark() + "]" + " [avaliable: " + getAvaliable() + "]"
               + " [filePath: " + getFilePath() + "]";
    }
}
