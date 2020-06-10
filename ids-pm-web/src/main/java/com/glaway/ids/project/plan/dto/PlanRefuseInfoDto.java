package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.vdata.GLVData;


/**
 * A representation of the model object '<em><b>PlanLog</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */

public class PlanRefuseInfoDto extends GLVData {

    private String refuseReason = null;

    private String remark = null;

    private String planId = null;

    public String getRefuseReason() {
        return refuseReason;
    }
    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
