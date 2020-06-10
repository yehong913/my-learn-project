package com.glaway.ids.project.plan.dto;

import com.glaway.foundation.common.vdata.GLVData;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: ApprovePlanFormDto
 * @Date: 2019/7/30-16:27
 * @since
 */
@Getter
@Setter
public class ApprovePlanFormDto extends GLVData {
    /**
     * <!-- begin-user-doc --> 类型 <!-- end-user-doc -->
     *
     * @generated
     */
    private String approveType = null;

    /**
     * <!-- begin-user-doc --> 流程id <!-- end-user-doc -->
     *
     * @generated
     */
    private String procInstId = null;


    /**
     * A toString method which prints the values of all EAttributes of this instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        return "ApprovePlanForm " + " [approveType: " + getApproveType() + "]" + " [procInstId: "
                + getProcInstId() + "]";
    }
}
