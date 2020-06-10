package com.glaway.ids.project.plan.dto;


import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.glaway.foundation.common.entity.GLObject;
import com.glaway.foundation.common.vdata.GLVData;


/**
 * A representation of the model object '<em><b>ApprovePlanInfo</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ApprovePlanInfoDto extends GLVData {

    /**
     * <!-- begin-user-doc --> 关联的id <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String formId = null;

    /**
     * 
     */
    @Transient
    private ApprovePlanFormDto approvePlanForm;

    /**
     * 
     */
    @Transient
    private PlanDto plan;

    /**
     * <!-- begin-user-doc -->计划id <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String planId = null;

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
     * Sets the '{@link ApprovePlanInfo#getFormId() <em>formId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFormId
     *            the new value of the '{@link ApprovePlanInfo#getFormId() formId}' feature.
     * @generated
     */
    public void setFormId(String newFormId) {
        formId = newFormId;
    }

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
     * Sets the '{@link ApprovePlanInfo#getPlanId() <em>planId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanId
     *            the new value of the '{@link ApprovePlanInfo#getPlanId() planId}' feature.
     * @generated
     */
    public void setPlanId(String newPlanId) {
        planId = newPlanId;
    }

    public ApprovePlanFormDto getApprovePlanForm() {
        return approvePlanForm;
    }

    public void setApprovePlanForm(ApprovePlanFormDto approvePlanForm) {
        this.approvePlanForm = approvePlanForm;
    }

    public PlanDto getPlan() {
        return plan;
    }

    public void setPlan(PlanDto plan) {
        this.plan = plan;
    }

}
