package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Transient;


/**
 * A representation of the model object '<em><b>TempPlanDeliverablesInfo</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
@Data
public class TempPlanDeliverablesInfoDto extends GLVData {

    /**
     * <!-- begin-user-doc --> 交付物id <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String deliverablesId = null;

    /**
     * <!-- begin-user-doc -->名称 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String name = null;

    /**
     * <!-- begin-user-doc --> 关联的外键类型 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String useObjectType = null;

    /**
     * <!-- begin-user-doc --> 关联的外键id <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String useObjectId = null;

    /**
     * <!-- begin-user-doc --> 是否来源与活动名称库 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String origin = null;

    /**
     * <!-- begin-user-doc --> 关联的资源id <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String deliverLinkId = null;

    /**
     * 文件
     */
    @Transient()
    private DocumentDto document = null;
    
    
    /**
     * 文件
     */
    @Transient()
    private String docId = null;
    
    /**
     * 文件
     */
    @Transient()
    private String docName = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String formId = null;

    @Override
    public String toString() {
        return "TempPlanDeliverablesInfo " + " [deliverablesId: " + getDeliverablesId() + "]"
               + " [name: " + getName() + "]" + " [useObjectType: " + getUseObjectType() + "]"
               + " [useObjectId: " + getUseObjectId() + "]" + " [origin: " + getOrigin() + "]"
               + " [deliverLinkId: " + getDeliverLinkId() + "]" + " [formId: " + getFormId() + "]";
    }
}
