package com.glaway.ids.project.projectmanager.dto;


import com.glaway.foundation.common.vdata.GLVData;


/**
 * A representation of the model object '<em><b>ProjWarn</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProjWarnDto extends GLVData {

    /**
     * <!-- begin-user-doc -->用户名
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String userName = null;

    /**
     * <!-- begin-user-doc -->项目编号
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projectNum = null;

    /**
     * <!-- begin-user-doc -->标记
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String flag = null;

    /**
     * Returns the value of '<em><b>userName</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>userName</b></em>' feature
     * @generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the '{@link ProjWarnDto#getUserName() <em>userName</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUserName
     *            the new value of the '{@link ProjWarnDto#getUserName() userName}' feature.
     * @generated
     */
    public void setUserName(String newUserName) {
        userName = newUserName;
    }

    /**
     * Returns the value of '<em><b>projectNum</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>projectNum</b></em>' feature
     * @generated
     */
    public String getProjectNum() {
        return projectNum;
    }

    /**
     * Sets the '{@link ProjWarnDto#getProjectNum() <em>projectNum</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjectNum
     *            the new value of the '{@link ProjWarnDto#getProjectNum() projectNum}' feature.
     * @generated
     */
    public void setProjectNum(String newProjectNum) {
        projectNum = newProjectNum;
    }

    /**
     * Returns the value of '<em><b>flag</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>flag</b></em>' feature
     * @generated
     */
    public String getFlag() {
        return flag;
    }

    /**
     * Sets the '{@link ProjWarnDto#getFlag() <em>flag</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFlag
     *            the new value of the '{@link ProjWarnDto#getFlag() flag}' feature.
     * @generated
     */
    public void setFlag(String newFlag) {
        flag = newFlag;
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
        return "ProjWarn " + " [userName: " + getUserName() + "]" + " [projectNum: "
               + getProjectNum() + "]" + " [flag: " + getFlag() + "]";
    }
}
