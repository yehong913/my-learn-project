package com.glaway.ids.project.projectmanager.dto;


import com.glaway.foundation.common.entity.GLObject;
import com.glaway.foundation.common.vdata.GLVData;
import com.glaway.foundation.system.event.EventSource;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * A representation of the model object '<em><b>ProjectLibAuthTemplateLink</b></em>'.
 * <!-- begin-user-doc -->项目库权限模板与项目关联表<!-- end-user-doc -->
 * 
 * @generated
 */
public class ProjectLibAuthTemplateLinkDto extends GLVData {

    /**
     * <!-- begin-user-doc -->项目ID<!-- end-user-doc -->
     * 
     * @generated
     */
    private String projectId = null;

    /**
     * <!-- begin-user-doc -->模板ID<!-- end-user-doc -->
     * 
     * @generated
     */
    private String templateId = null;

    /**
     * Returns the value of '<em><b>projectId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>projectId</b></em>' feature
     * @generated
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjectId
     *            ' feature.
     * @generated
     */
    public void setProjectId(String newProjectId) {
        projectId = newProjectId;
    }

    /**
     * Returns the value of '<em><b>templateId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>templateId</b></em>' feature
     * @generated
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newTemplateId
     *            templateId}' feature.
     * @generated
     */
    public void setTemplateId(String newTemplateId) {
        templateId = newTemplateId;
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
        return "ProjectLibAuthTemplateLink " + " [projectId: " + getProjectId() + "]"
               + " [templateId: " + getTemplateId() + "]";
    }
}
