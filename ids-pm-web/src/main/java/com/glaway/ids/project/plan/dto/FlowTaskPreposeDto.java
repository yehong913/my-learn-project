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

import lombok.Getter;
import lombok.Setter;


/**
 * 流程任务变更 前后置关系表
 * 
 * @generated
 */
@Getter
@Setter
public class FlowTaskPreposeDto extends GLVData {

    /**
     * <!-- begin-user-doc -->流程任务id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String flowTaskId = null;

    @Transient
    private FlowTaskDto flowTaskInfo = null;

    /**
     * <!-- begin-user-doc -->前置计划id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String preposeId = null;

    @Transient
    private FlowTaskDto preposePlanInfo = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String formId = null;

}
