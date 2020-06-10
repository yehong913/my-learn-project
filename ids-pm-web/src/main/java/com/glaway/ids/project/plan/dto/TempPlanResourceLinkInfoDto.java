package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.vdata.GLVData;
import com.glaway.ids.project.projectmanager.dto.ResourceDto;
import lombok.Data;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;


/**
 * A representation of the model object '<em><b>TempPlanResourceLinkInfo</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
@Data
public class TempPlanResourceLinkInfoDto extends GLVData {

    /**
     * <!-- begin-user-doc --> 关联的资源id <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String resourceLinkId = null;

    /**
     * <!-- begin-user-doc --> 资源id <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String resourceId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "resourceId", insertable = false, updatable = false)
    @ForeignKey(name = "none")
    @NotFound(action = NotFoundAction.IGNORE)
    private ResourceDto resourceInfo;

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
     * <!-- begin-user-doc --> 进度 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String useRate = null;

    /**
     * <!-- begin-user-doc --> 资源类型 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String resourceType = null;

    /**
     * 资源名称
     */
    @Transient()
    private String resourceName = null;

    /**
     * <!-- begin-user-doc --> 开始时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Temporal(TemporalType.DATE)
    private Date startTime = null;

    /**
     * 开始时间
     */
    @Transient()
    private boolean startTimeOverflow = false;

    /**
     * <!-- begin-user-doc --> 结束时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Temporal(TemporalType.DATE)
    private Date endTime = null;

    /**
     * 结束时间
     */
    @Transient()
    private boolean endTimeOverflow = false;

    /**
     * 计划开始时间
     */
    @Transient()
    private String planStartTime = null;

    /**
     * 计划结束时间
     */
    @Transient()
    private String planEndTime = null;

    /**
     * 计划名称
     */
    @Transient()
    private String planName = null;

    /**
     * 外键id
     */
    @Transient()
    private String linkId = null;

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
        return "TempPlanResourceLinkInfo " + " [resourceLinkId: " + getResourceLinkId() + "]"
               + " [resourceId: " + getResourceId() + "]" + " [useObjectType: "
               + getUseObjectType() + "]" + " [useObjectId: " + getUseObjectId() + "]"
               + " [useRate: " + getUseRate() + "]" + " [resourceType: " + getResourceType() + "]"
               + " [startTime: " + getStartTime() + "]" + " [endTime: " + getEndTime() + "]"
               + " [formId: " + getFormId() + "]";
    }
}
