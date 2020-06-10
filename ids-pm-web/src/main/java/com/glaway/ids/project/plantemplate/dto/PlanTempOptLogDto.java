package com.glaway.ids.project.plantemplate.dto;

import com.glaway.foundation.common.vdata.GLVData;

/**
 * A representation of the model object '<em><b>planTempOptLog</b></em>'.
 * <!-- begin-user-doc -->计划模版操作日志表 <!-- end-user-doc -->
 * 
 * @generated
 */
public class PlanTempOptLogDto extends GLVData {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String logInfo = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String tmplId = null;
    
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    private PlanTemplateDto planTmpl;


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
     * Returns the value of '<em><b>tmplId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>tmplId</b></em>' feature
     * @generated
     */
    public String getTmplId() {
        return tmplId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newTmplId
     * @generated
     */
    public void setTmplId(String newTmplId) {
        tmplId = newTmplId;
    }
    
    public PlanTemplateDto getProcTmpl() {
        return planTmpl;
    }

    public void setProcTmpl(PlanTemplateDto planTmpl) {
        this.planTmpl = planTmpl;
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
        return "PlanTempOptLog " + " [logInfo: " + getLogInfo() + "]" + " [tmplId: " + getTmplId()
               + "]";
    }
}
