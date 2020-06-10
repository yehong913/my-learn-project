package com.glaway.ids.project.projectmanager.dto;


import com.glaway.foundation.businessobject.entity.BusinessObject;


/**
 * A representation of the model object '<em><b>ProjLibFile</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProjLibFileDto extends BusinessObject {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projectStatus = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projectFileId = "";

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String feedbackProcInstId = null;

    /**
     * <!-- begin-model-doc -->
     * 老任务ID
     * <!-- end-model-doc -->
     */
    private String oldTaskId = null;

    /**
     * Returns the value of '<em><b>projectStatus</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>projectStatus</b></em>' feature
     * @generated
     */
    public String getProjectStatus() {
        return projectStatus;
    }

    /**
     * Sets the '{@link ProjLibFileDto#getProjectStatus() <em>projectStatus</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjectStatus
     *            the new value of the '{@link ProjLibFileDto#getProjectStatus() projectStatus}'
     *            feature.
     * @generated
     */
    public void setProjectStatus(String newProjectStatus) {
        projectStatus = newProjectStatus;
    }

    /**
     * Returns the value of '<em><b>projectFileId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>projectFileId</b></em>' feature
     * @generated
     */
    public String getProjectFileId() {
        return projectFileId;
    }

    /**
     * Sets the '{@link ProjLibFileDto#getProjectFileId() <em>projectFileId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjectFileId
     *            the new value of the '{@link ProjLibFileDto#getProjectFileId() projectFileId}'
     *            feature.
     * @generated
     */
    public void setProjectFileId(String newProjectFileId) {
        projectFileId = newProjectFileId;
    }

    /**
     * Returns the value of '<em><b>feedbackProcInstId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>feedbackProcInstId</b></em>' feature
     * @generated
     */
    public String getFeedbackProcInstId() {
        return feedbackProcInstId;
    }

    /**
     * Sets the '{@link ProjLibFileDto#getFeedbackProcInstId() <em>feedbackProcInstId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFeedbackProcInstId
     *            the new value of the '{@link ProjLibFileDto#getFeedbackProcInstId()
     *            feedbackProcInstId}' feature.
     * @generated
     */
    public void setFeedbackProcInstId(String newFeedbackProcInstId) {
        feedbackProcInstId = newFeedbackProcInstId;
    }

    /**
     * Returns the value of '<em><b>oldTaskId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>oldTaskId</b></em>' feature
     * @generated
     */
    public String getOldTaskId() {
        return oldTaskId;
    }

    /**
     * Sets the '{@link ProjLibFileDto#getOldTaskId() <em>oldTaskId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOldTaskId
     *            the new value of the '{@link ProjLibFileDto#getOldTaskId() oldTaskId}' feature.
     * @generated
     */
    public void setOldTaskId(String newOldTaskId) {
        oldTaskId = newOldTaskId;
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
        return "ProjLibFile " + " [projectStatus: " + getProjectStatus() + "]"
               + " [projectFileId: " + getProjectFileId() + "]" + " [feedbackProcInstId: "
               + getFeedbackProcInstId() + "]" + " [oldTaskId: " + getOldTaskId() + "]";
    }
}
