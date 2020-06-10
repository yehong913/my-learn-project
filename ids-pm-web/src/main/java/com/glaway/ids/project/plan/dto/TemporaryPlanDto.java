package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vdata.GLVData;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;
import lombok.Data;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A representation of the model object '<em><b>TemporaryPlan</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
@Data
public class TemporaryPlanDto extends GLVData {

    /**
     * <!-- begin-user-doc --> 关联计划id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planId = null;

    /**
     * <!-- begin-user-doc --> 计划序号 <!-- end-user-doc -->
     * 
     * @generated
     */
    private int planNumber = 0;

    /**
     * <!-- begin-user-doc --> 计划顺序 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planOrder = null;

    /**
     * <!-- begin-user-doc --> 计划名称<!-- end-user-doc -->
     * 
     * @generated
     */
    private String planName = null;

    /**
     * <!-- begin-user-doc --> 项目id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projectId = null;

    /**
     * 项目信息
     */
    private Project project;

    /**
     * <!-- begin-user-doc --> 父节点计划id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String parentPlanId = null;

    /**
     * 父计划信息
     */
    private PlanDto parentPlan;

    /**
     * 父计划名称
     */
    private String parentPlanName = null;


    private String owner = null;

    /**
     * 负责人信息
     */
    private TSUserDto ownerInfo;

    /**
     * 创建者
     */
    private TSUserDto creator;

    /**
     * 责任部门
     */
    private String ownerDept = null;

    /**
     * 负责人真实名称
     */
    private String ownerRealName = null;

    /**
     * <!-- begin-user-doc --> 应用情况 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String implementation = null;

    /**
     * <!-- begin-user-doc -->下达人 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String assigner = null;

    /**
     * 下达人信息
     */
    private TSUserDto assignerInfo;

    /**
     * <!-- begin-user-doc --> 下达时间<!-- end-user-doc -->
     * 
     * @generated
     */
    private Date assignTime = null;

    /**
     * 下达开始时间
     */
    private Date assignTimeStart = null;

    /**
     * 下达结束时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date assignTimeEnd = null;

    /**
     * <!-- begin-user-doc --> 发起人 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String launcher = null;

    /**
     * <!-- begin-user-doc --> 发起时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Temporal(TemporalType.DATE)
    private Date launchTime = null;

    /**
     * 发起人 信息
     */
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "launcher", insertable = false, updatable = false)
    @ForeignKey(name = "none")
    @NotFound(action = NotFoundAction.IGNORE)
    private TSUserDto launcherInfo;

    /**
     * <!-- begin-user-doc --> 计划等级 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String planLevel = null;

    /**
     * 计划等级信息
     */

    private BusinessConfigDto planLevelInfo;

    /**
     * 计划等级名称
     */
    @Transient()
    private String planLevelName = null;

    /**
     * <!-- begin-user-doc --> 进度 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String progressRate = null;

    /**
     * <!-- begin-user-doc --> 计划开始时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Temporal(TemporalType.DATE)
    private Date planStartTime = null;

    /**
     * 计划开始时间的结束时间
     */
    @Transient()
    private String planStartTimeView = null;

    /**
     * 计划开始时间的开始时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date planStartTimeStart = null;

    /**
     * 计划开始时间的结束时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date planStartTimeEnd = null;

    /**
     * <!-- begin-user-doc --> 计划结束时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date planEndTime = null;

    /**
     * 计划结束时间的开始时间
     */
    private String planEndTimeView = null;

    /**
     * 计划结束时间的开始时间
     */
    private Date planEndTimeStart = null;

    /**
     * 计划结束时间的结束时间
     */
    private Date planEndTimeEnd = null;

    /**
     * <!-- begin-user-doc --> 工期 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String workTime = null;

    /**
     * <!-- begin-user-doc --> 实际开始时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date actualStartTime = null;

    /**
     * 实际开始时间的开始时间
     */
    private Date actualStartTimeStart = null;

    /**
     * 实际开始时间的结束时间
     */
    private Date actualStartTimeEnd = null;

    /**
     * <!-- begin-user-doc --> 实际结束时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date actualEndTime = null;

    /**
     * 实际结束时间的开始时间
     */
    private Date actualEndTimeStart = null;

    /**
     * 实际结束时间的结束时间
     */
    private Date actualEndTimeEnd = null;

    /**
     * <!-- begin-user-doc --> 备注 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String remark = null;

    /**
     * 状态
     */
    private String status = null;

    /**
     * <!-- begin-user-doc --> 项目状态 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projectStatus = "";

    /**
     * <!-- begin-user-doc --> 流程状态<!-- end-user-doc -->
     * 
     * @generated
     */
    private String flowStatus = "NORMAL";

    /**
     * <!-- begin-user-doc --> 里程碑 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String milestone = "false";

    /**
     * 里程碑中文
     */
    private String milestoneName = null;

    /**
     * <!-- begin-user-doc --> 风险 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String risk = null;

    /**
     * <!-- begin-user-doc --> 变更类型<!-- end-user-doc -->
     * 
     * @generated
     */
    private String changeType = null;

    /**
     * 变更类型信息
     */
    private BusinessConfig changeTypeInfo;

    /**
     * <!-- begin-user-doc --> 变更备注 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String changeRemark = null;

    /**
     * <!-- begin-user-doc --> 附件id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String changeInfoDocId = null;

    /**
     * <!-- begin-user-doc -->附件名称<!-- end-user-doc -->
     * 
     * @generated
     */
    private String changeInfoDocName = null;

    /**
     * <!-- begin-user-doc --> 附件路径<!-- end-user-doc -->
     * 
     * @generated
     */
    private String changeInfoDocPath = null;

    /**
     * <!-- begin-user-doc --> 前置计划id<!-- end-user-doc -->
     * 
     * @generated
     */
    private String preposeIds = null;

    /**
     * 前置计划
     */
    private String preposePlans = null;

    /**
     * 文件
     */
    private String documents = null;

    /**
     * 父节点顺序
     */
    private String parentStorey = null;

    /**
     * <!-- begin-user-doc --> 顺序 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Integer storeyNo = null;

    /**
     * <!-- begin-user-doc --> 上层计划id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String beforePlanId = null;

    /**
     * <!-- begin-user-doc --> 计划类别 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String taskType = "";

    /**
     * <!-- begin-user-doc -->活动名称类型<!-- end-user-doc -->
     * 
     * @generated
     */
    private String taskNameType = null;

    /**
     * 临时父节点id
     */
    private String _parentId = null;

    /**
     * 顺序
     */
    private String order = null;

    /**
     * <!-- begin-user-doc --> 废弃时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date invalidTime = null;

    /**
     * 
     */
    private List<PlanDto> preposeList = new ArrayList<>();

    /**
     * 
     */
    private List<ResourceLinkInfoDto> rescLinkInfoList = new ArrayList<>();

    /**
     * 
     */
    private List<DeliverablesInfoDto> deliInfoList = new ArrayList<>();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String formId = null;


    /**
     * A toString method which prints the values of all EAttributes of this instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "TemporaryPlan " + " [planId: " + getPlanId() + "]" + " [planNumber: "
               + getPlanNumber() + "]" + " [planOrder: " + getPlanOrder() + "]" + " [planName: "
               + getPlanName() + "]" + " [projectId: " + getProjectId() + "]" + " [parentPlanId: "
               + getParentPlanId() + "]" + " [owner: " + getOwner() + "]" + " [implementation: "
               + getImplementation() + "]" + " [assigner: " + getAssigner() + "]"
               + " [assignTime: " + getAssignTime() + "]" + " [launcher: " + getLauncher() + "]"
               + " [launchTime: " + getLaunchTime() + "]" + " [planLevel: " + getPlanLevel() + "]"
               + " [progressRate: " + getProgressRate() + "]" + " [planStartTime: "
               + getPlanStartTime() + "]" + " [planEndTime: " + getPlanEndTime() + "]"
               + " [workTime: " + getWorkTime() + "]" + " [actualStartTime: "
               + getActualStartTime() + "]" + " [actualEndTime: " + getActualEndTime() + "]"
               + " [remark: " + getRemark() + "]" + " [projectStatus: " + getProjectStatus() + "]"
               + " [flowStatus: " + getFlowStatus() + "]" + " [milestone: " + getMilestone() + "]"
               + " [risk: " + getRisk() + "]" + " [changeType: " + getChangeType() + "]"
               + " [changeRemark: " + getChangeRemark() + "]" + " [changeInfoDocId: "
               + getChangeInfoDocId() + "]" + " [changeInfoDocName: " + getChangeInfoDocName()
               + "]" + " [changeInfoDocPath: " + getChangeInfoDocPath() + "]" + " [preposeIds: "
               + getPreposeIds() + "]" + " [storeyNo: " + getStoreyNo() + "]" + " [beforePlanId: "
               + getBeforePlanId() + "]" + " [taskType: " + getTaskType() + "]"
               + " [taskNameType: " + getTaskNameType() + "]" + " [invalidTime: "
               + getInvalidTime() + "]" + " [formId: " + getFormId() + "]";
    }
}
