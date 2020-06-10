package com.glaway.ids.project.projecttemplate.dto;


import com.glaway.foundation.common.vdata.GLVData;


/**
 * A representation of the model object '<em><b>ProjectLibAuthTemplateLink</b></em>'.
 * <!-- begin-user-doc -->项目库权限模板与项目关联表<!-- end-user-doc -->
 * 
 * @generated
 */
public class ProjTmpLibAuthLibTmpLinkDto extends GLVData {

    /**
     * <!-- begin-user-doc -->项目模板ID<!-- end-user-doc -->
     * 
     * @generated
     */
    private String projTmpId = null;

    /**
     * <!-- begin-user-doc -->项目库模板ID<!-- end-user-doc -->
     * 
     * @generated
     */
    private String libTmpId = null;

    
    public String getProjTmpId() {
        return projTmpId;
    }


    public void setProjTmpId(String projTmpId) {
        this.projTmpId = projTmpId;
    }


    public String getLibTmpId() {
        return libTmpId;
    }


    public void setLibTmpId(String libTmpId) {
        this.libTmpId = libTmpId;
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
        return "ProjTmpLibAuthLibTmpLink " + " [projTmpId: " + getProjTmpId() + "]"
               + " [libTmpId: " + getLibTmpId() + "]";
    }
}
