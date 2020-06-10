package com.glaway.ids.project.plan.dto;


import javax.persistence.Basic;

import org.springframework.data.annotation.Transient;

import com.glaway.foundation.common.vdata.GLVData;


/**
 * A representation of the model object '<em><b>PreposePlan</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class PreposePlanDto extends GLVData {

    /**
     * <!-- begin-user-doc -->计划id <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String planId = null;

    @Transient
    private PlanDto planInfo = null;

    /**
     * <!-- begin-user-doc --> 前置计划id <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String preposePlanId = null;

    @Transient
    private PlanDto preposePlanInfo = null;

    /**
     * <!-- begin-user-doc --> 是否可用 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String avaliable = "1";

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
     * Sets the '{@link PreposePlan#getPlanId() <em>planId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanId
     *            the new value of the '{@link PreposePlan#getPlanId() planId}' feature.
     * @generated
     */
    public void setPlanId(String newPlanId) {
        planId = newPlanId;
    }

    /**
     * Returns the value of '<em><b>preposePlanId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>preposePlanId</b></em>' feature
     * @generated
     */
    public String getPreposePlanId() {
        return preposePlanId;
    }

    /**
     * Sets the '{@link PreposePlan#getPreposePlanId() <em>preposePlanId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPreposePlanId
     *            the new value of the '{@link PreposePlan#getPreposePlanId() preposePlanId}'
     *            feature.
     * @generated
     */
    public void setPreposePlanId(String newPreposePlanId) {
        preposePlanId = newPreposePlanId;
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
     * Sets the '{@link PreposePlan#getAvaliable() <em>avaliable</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAvaliable
     *            the new value of the '{@link PreposePlan#getAvaliable() avaliable}' feature.
     * @generated
     */
    public void setAvaliable(String newAvaliable) {
        avaliable = newAvaliable;
    }

    public PlanDto getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(PlanDto planInfo) {
        this.planInfo = planInfo;
    }

    public PlanDto getPreposePlanInfo() {
        return preposePlanInfo;
    }

    public void setPreposePlanInfo(PlanDto preposePlanInfo) {
        this.preposePlanInfo = preposePlanInfo;
    }

}
