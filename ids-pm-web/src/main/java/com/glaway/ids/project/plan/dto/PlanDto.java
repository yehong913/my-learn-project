package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vdata.GLVData;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;

import javax.persistence.Basic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A representation of the model object '<em><b>Plan</b></em>'. <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * @author wangyangzan
 * @generated
 */
public class PlanDto extends GLVData implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> 计划序号 <!-- end-user-doc -->
     * 
     * @generated
     */
    private int planNumber = 0;

    /**
     * <!-- begin-user-doc --> 表单ID<!-- end-user-doc -->
     * 
     * @generated
     */
    private String formId = null;

    /**
     * <!-- begin-user-doc --> 计划顺序 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planOrder = null;

    /**
     * 是否项目管理员创建
     */
    private Boolean isCreateByPmo = null;

    /**
     * <!-- begin-user-doc --> 计划名称 <!-- end-user-doc -->
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
     * 项目名称
     */
    private String projectName = null;

    /**
     * 当前用户
     */
    private TSUserDto currentUser = null;

    /**
     * <!-- begin-user-doc --> 父节点id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String parentPlanId = null;

    /**
     * 父节点计划信息
     */
    private PlanDto parentPlan;

    /**
     * 父节点计划名称
     */
    private String parentPlanName = null;
    
    /**
     * 父计划序号
     */
    private Integer parentPlanNo = null;

    /**
     * <!-- begin-user-doc --> 负责人id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String owner = null;

    /**
     * 负责人信息
     */
    private TSUserDto ownerInfo;

    /**
     * 负责人部门
     */
    private String ownerDept = null;

    /**
     * 负责人
     */
    private String ownerRealName = null;

    /**
     * 应用情况
     */
    private String implementation = null;

    /**
     * <!-- begin-user-doc --> 下达人 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String assigner = null;

    /**
     * 下达人信息
     */
    private TSUserDto assignerInfo;

    /**
     * <!-- begin-user-doc --> 下达时间 <!-- end-user-doc -->
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
    private Date assignTimeEnd = null;

    /**
     * <!-- begin-user-doc --> 流程发起人 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String launcher = null;

    /**
     * 流程发起人信息
     */
    private TSUserDto launcherInfo;

    /**
     * <!-- begin-user-doc --> 流程发起时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date launchTime = null;

    /**
     * 创建者信息
     */
    private TSUserDto creator;

    /**
     * <!-- begin-user-doc --> 计划等级id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planLevel = null;

    /**
     * 计划等级信息
     */
    private BusinessConfigDto planLevelInfo;

    /**
     * 计划等级中文
     */
    private String planLevelName = null;

    /**
     * <!-- begin-user-doc --> 进度 <!-- end-user-doc -->
     */
    private String progressRate = "0";

    /**
     * <!-- begin-user-doc --> 计划开始时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date planStartTime = null;

    /**
     * 计划开始时间的开始时间
     */
    private Date planStartTimeStart = null;

    /**
     * 计划开始时间的结束时间
     */
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
     * <!-- begin-user-doc --> 参考工期 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String workTimeReference = "0";

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
     * <!-- begin-user-doc --> 实际完成时间 <!-- end-user-doc -->
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
     * <!-- begin-user-doc --> 流程状态 "NORMAL":无流程 "ORDERED":下达流程中 "CHANGE":变更流程中
     * "FEEDBACKING":完工反馈流程中 <!-- end-user-doc -->
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
     * <!-- begin-user-doc --> 是否批量下达驳回的 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String isAssignBack = "false";

    /**
     * <!-- begin-user-doc --> 是否单条下达驳回的 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String isAssignSingleBack = "false";

    /**
     * <!-- begin-user-doc --> 是否单条变更驳回的 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String isChangeSingleBack = "false";

    /**
     * 里程碑中文
     */
    private String milestoneName;

    /**
     * 前置计划id
     */
    private String preposeIds = null;
    
    /**
     * 前置计划序号
     */
    private String preposeNos = null;

    /**
     * 前置计划
     */
    private String preposePlans = null;

    /**
     * 文档
     */
    private String documents;

    /**
     * 父节点顺序
     */
    private String parentStorey = null;

    /**
     * <!-- begin-user-doc --> 同级顺序 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Integer storeyNo = null;

    /**
     * 上层计划id
     */
    private String beforePlanId = null;

    /**
     * <!-- begin-user-doc --> 反馈流程实例id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String feedbackProcInstId = null;

    /**
     * <!-- begin-user-doc --> 反馈时记录之前的进度，如果反馈流程被驳回，则恢复之前的进度到progressRate字段 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String feedbackRateBefore = null;

    /**
     * <!-- begin-user-doc --> 计划资源 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planSource = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String fromTemplate = null;

    /**
     * <!-- begin-user-doc --> 流程分解XML<!-- end-user-doc -->
     * 
     * @generated
     */
    private String flowResolveXml = null;

    /**
     * <!-- begin-user-doc -->任务操作类型：计划分解、流程分解、下发评审任务、撤销计划分解、撤销流程分解等 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String opContent = null;

    /**
     * <!-- begin-user-doc --> 计划类型 (无用字段，后续去除)<!-- end-user-doc -->
     * 
     * @generated
     */
    private String planType = null;

    /**
     * <!-- begin-user-doc --> 计划类别：WBS计划、任务计划、流程计划 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String taskType = "";

    /**
     * <!-- begin-user-doc --> 计划类型：研发类、评审类、风险类等 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String taskNameType = null;

    /**
     * <!-- begin-user-doc --> 单元格id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String cellId = null;

    /**
     * <!-- begin-user-doc --> 是否必要 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String required = null;

    /**
     * 顺序
     */
    private String order = null;

    /**
     * 标识该数据是否查询结果
     */
    private String result = "false";

    /**
     * 是否存在流程 0:不存在; 1:存在
     */
    private String flowFlag = null;

    /**
     * 父节点临时id
     */
    private String _parentId = null;

    /**
     * 资源列表
     */
    private List<ResourceLinkInfoDto> rescLinkInfoList = new ArrayList<ResourceLinkInfoDto>();

    /**
     * 交付物列表
     */
    private List<DeliverablesInfoDto> deliInfoList = new ArrayList<DeliverablesInfoDto>();

    /**
     * 输入列表
     */
    private List<InputsDto> inputList = new ArrayList<InputsDto>();

    /**
     * 前置计划列表
     */
    private List<PlanDto> preposeList = new ArrayList<PlanDto>();

    /**
     * <!-- begin-user-doc --> 废弃时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date invalidTime = null;
    
    /**
     * 交付物名称
     */
    private String deliverablesName = null;
    
    
    private String planTemplateId = null;
    
    /**
     * 计划模板：是否必要
     */
    private String isNecessary = null;
    
    
    /**
     * 视图id
     */
    private String planViewInfoId = null;

    private String planReceivedProcInstId = null;

    private Date planReceivedCompleteTime;

    /**
     * 计划委派流程id
     */
    private String planDelegateProcInstId = null;

    /**
     * <!-- begin-user-doc --> 计划委派流程是否结束 <!-- end-user-doc -->
     *
     * @generated
     */
    private String isDelegateComplete = "false";

    /**
     * 关注计划，0为取消关注
     */
    private String concernCode = "0";

    /**
     * 绑定的页签组合模板id
     */
    private String tabCbTemplateId = null;
    
    
    /**
     * 备注名称
     */
    private String remarkName;
    
    /**
     * 图号
     */
    private String drawingNo;
    
    
    /**
     * 依赖关系
     */
    private String relayOn;
    
    
    /**
     * 延搁时间
     */
    private String delayDay;
    
    
    /**
     * 限制类型
     */
    private String limitType;
    
    /**
     * 限制日期
     */
    private Date limitTime;
    
    
    

    public String getConcernCode() {
        return concernCode;
    }

    public void setConcernCode(String concernCode) {
        this.concernCode = concernCode;
    }

    /**
     * Returns the value of '<em><b>planNumber</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>planNumber</b></em>' feature
     * @generated
     */
    public int getPlanNumber()
    {
        return planNumber;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newPlanNumber
     * @generated
     */
    public void setPlanNumber(int newPlanNumber)
    {
        planNumber = newPlanNumber;
    }

    /**
     * Returns the value of '<em><b>formId</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>formId</b></em>' feature
     * @generated
     */
    public String getFormId()
    {
        return formId;
    }

    /**
     * end-user-doc -->
     * 
     * @param newFormId
     * @generated
     */
    public void setFormId(String newFormId)
    {
        formId = newFormId;
    }

    /**
     * Returns the value of '<em><b>planOrder</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>planOrder</b></em>' feature
     * @generated
     */
    public String getPlanOrder()
    {
        return planOrder;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newPlanOrder
     * @generated
     */
    public void setPlanOrder(String newPlanOrder)
    {
        planOrder = newPlanOrder;
    }

    /**
     * Returns the value of '<em><b>planName</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>planName</b></em>' feature
     * @generated
     */
    public String getPlanName()
    {
        return planName;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newPlanName
     * @generated
     */
    public void setPlanName(String newPlanName)
    {
        planName = newPlanName;
    }

    /**
     * Returns the value of '<em><b>projectId</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>projectId</b></em>' feature
     * @generated
     */
    public String getProjectId()
    {
        return projectId;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newProjectId
     * @generated
     */
    public void setProjectId(String newProjectId)
    {
        projectId = newProjectId;
    }

    /**
     * Returns the value of '<em><b>parentPlanId</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>parentPlanId</b></em>' feature
     * @generated
     */
    public String getParentPlanId()
    {
        return parentPlanId;
    }

    /**
     * --> <!-- end-user-doc -->
     * 
     * @param newParentPlanId
     * @generated
     */
    public void setParentPlanId(String newParentPlanId)
    {
        parentPlanId = newParentPlanId;
    }

    /**
     * Returns the value of '<em><b>owner</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>owner</b></em>' feature
     * @generated
     */
    public String getOwner()
    {
        return owner;
    }

    /**
     * end-user-doc -->
     * 
     * @param newOwner
     * @generated
     */
    public void setOwner(String newOwner)
    {
        owner = newOwner;
    }

    /**
     * Returns the value of '<em><b>assigner</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>assigner</b></em>' feature
     * @generated
     */
    public String getAssigner()
    {
        return assigner;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newAssigner
     * @generated
     */
    public void setAssigner(String newAssigner)
    {
        assigner = newAssigner;
    }

    /**
     * Returns the value of '<em><b>assignTime</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>assignTime</b></em>' feature
     * @generated
     */
    public Date getAssignTime()
    {
        return assignTime;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newAssignTime
     * @generated
     */
    public void setAssignTime(Date newAssignTime)
    {
        assignTime = newAssignTime;
    }

    /**
     * Returns the value of '<em><b>launcher</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>launcher</b></em>' feature
     * @generated
     */
    public String getLauncher()
    {
        return launcher;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newLauncher
     * @generated
     */
    public void setLauncher(String newLauncher)
    {
        launcher = newLauncher;
    }

    /**
     * Returns the value of '<em><b>launchTime</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>launchTime</b></em>' feature
     * @generated
     */
    public Date getLaunchTime()
    {
        return launchTime;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newLaunchTime
     * @generated
     */
    public void setLaunchTime(Date newLaunchTime)
    {
        launchTime = newLaunchTime;
    }

    /**
     * Returns the value of '<em><b>planLevel</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>planLevel</b></em>' feature
     * @generated
     */
    public String getPlanLevel()
    {
        return planLevel;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newPlanLevel
     * @generated
     */
    public void setPlanLevel(String newPlanLevel)
    {
        planLevel = newPlanLevel;
    }

    /**
     * Returns the value of '<em><b>progressRate</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>progressRate</b></em>' feature
     * @generated
     */
    public String getProgressRate()
    {
        return progressRate;
    }

    /**
     * --> <!-- end-user-doc -->
     * 
     * @param newProgressRate
     * @generated
     */
    public void setProgressRate(String newProgressRate)
    {
        progressRate = newProgressRate;
    }

    /**
     * Returns the value of '<em><b>planStartTime</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>planStartTime</b></em>' feature
     * @generated
     */
    public Date getPlanStartTime()
    {
        return planStartTime;
    }

    /**
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newPlanStartTime
     * @generated
     */
    public void setPlanStartTime(Date newPlanStartTime)
    {
        planStartTime = newPlanStartTime;
    }

    /**
     * Returns the value of '<em><b>planEndTime</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>planEndTime</b></em>' feature
     * @generated
     */
    public Date getPlanEndTime()
    {
        return planEndTime;
    }

    /**
     * --> <!-- end-user-doc -->
     * 
     * @param newPlanEndTime
     * @generated
     */
    public void setPlanEndTime(Date newPlanEndTime)
    {
        planEndTime = newPlanEndTime;
    }

    /**
     * Returns the value of '<em><b>workTime</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>workTime</b></em>' feature
     * @generated
     */
    public String getWorkTime()
    {
        return workTime;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newWorkTime
     * @generated
     */
    public void setWorkTime(String newWorkTime)
    {
        workTime = newWorkTime;
    }

    /**
     * Returns the value of '<em><b>workTimeReference</b></em>' feature. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>workTimeReference</b></em>' feature
     * @generated
     */
    public String getWorkTimeReference()
    {
        return workTimeReference;
    }

    /**
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newWorkTimeReference
     *            feature.
     * @generated
     */
    public void setWorkTimeReference(String newWorkTimeReference)
    {
        workTimeReference = newWorkTimeReference;
    }

    /**
     * Returns the value of '<em><b>actualStartTime</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>actualStartTime</b></em>' feature
     * @generated
     */
    public Date getActualStartTime()
    {
        return actualStartTime;
    }

    /**
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newActualStartTime
     * @generated
     */
    public void setActualStartTime(Date newActualStartTime)
    {
        actualStartTime = newActualStartTime;
    }

    /**
     * Returns the value of '<em><b>actualEndTime</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>actualEndTime</b></em>' feature
     * @generated
     */
    public Date getActualEndTime()
    {
        return actualEndTime;
    }

    /**
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newActualEndTime
     * @generated
     */
    public void setActualEndTime(Date newActualEndTime)
    {
        actualEndTime = newActualEndTime;
    }

    /**
     * Returns the value of '<em><b>remark</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>remark</b></em>' feature
     * @generated
     */
    public String getRemark()
    {
        return remark;
    }

    /**
     * end-user-doc -->
     *
     * @param newRemark
     * @generated
     */
    public void setRemark(String newRemark)
    {
        remark = newRemark;
    }

    /**
     * Returns the value of '<em><b>projectStatus</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>projectStatus</b></em>' feature
     * @generated
     */
    public String getProjectStatus()
    {
        return projectStatus;
    }

    /**
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newProjectStatus
     * @generated
     */
    public void setProjectStatus(String newProjectStatus)
    {
        projectStatus = newProjectStatus;
    }

    /**
     * Returns the value of '<em><b>flowStatus</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>flowStatus</b></em>' feature
     * @generated
     */
    public String getFlowStatus()
    {
        return flowStatus;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newFlowStatus
     * @generated
     */
    public void setFlowStatus(String newFlowStatus)
    {
        flowStatus = newFlowStatus;
    }

    /**
     * Returns the value of '<em><b>milestone</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>milestone</b></em>' feature
     * @generated
     */
    public String getMilestone()
    {
        return milestone;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newMilestone
     * @generated
     */
    public void setMilestone(String newMilestone)
    {
        milestone = newMilestone;
    }

    /**
     * Returns the value of '<em><b>storeyNo</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>storeyNo</b></em>' feature
     * @generated
     */
    public Integer getStoreyNo()
    {
        return storeyNo;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newStoreyNo
     * @generated
     */
    public void setStoreyNo(Integer newStoreyNo)
    {
        storeyNo = newStoreyNo;
    }

    /**
     * Returns the value of '<em><b>feedbackProcInstId</b></em>' feature. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>feedbackProcInstId</b></em>' feature
     * @generated
     */
    public String getFeedbackProcInstId()
    {
        return feedbackProcInstId;
    }

    /**
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newFeedbackProcInstId
     *            feature.
     * @generated
     */
    public void setFeedbackProcInstId(String newFeedbackProcInstId)
    {
        feedbackProcInstId = newFeedbackProcInstId;
    }

    /**
     * Returns the value of '<em><b>feedbackRateBefore</b></em>' feature. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>feedbackRateBefore</b></em>' feature
     * @generated
     */
    public String getFeedbackRateBefore()
    {
        return feedbackRateBefore;
    }

    /**
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newFeedbackRateBefore
     *            feature.
     * @generated
     */
    public void setFeedbackRateBefore(String newFeedbackRateBefore)
    {
        feedbackRateBefore = newFeedbackRateBefore;
    }

    /**
     * Returns the value of '<em><b>planSource</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>planSource</b></em>' feature
     * @generated
     */
    public String getPlanSource()
    {
        return planSource;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newPlanSource
     * @generated
     */
    public void setPlanSource(String newPlanSource)
    {
        planSource = newPlanSource;
    }

    /**
     * Returns the value of '<em><b>fromTemplate</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>fromTemplate</b></em>' feature
     * @generated
     */
    public String getFromTemplate()
    {
        return fromTemplate;
    }

    /**
     * --> <!-- end-user-doc -->
     * 
     * @param newFromTemplate
     * @generated
     */
    public void setFromTemplate(String newFromTemplate)
    {
        fromTemplate = newFromTemplate;
    }

    /**
     * Returns the value of '<em><b>flowResolveXml</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>flowResolveXml</b></em>' feature
     * @generated
     */
    public String getFlowResolveXml()
    {
        return flowResolveXml;
    }

    /**
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newFlowResolveXml
     * @generated
     */
    public void setFlowResolveXml(String newFlowResolveXml)
    {
        flowResolveXml = newFlowResolveXml;
    }

    /**
     * Returns the value of '<em><b>opContent</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>opContent</b></em>' feature
     * @generated
     */
    public String getOpContent()
    {
        return opContent;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newOpContent
     * @generated
     */
    public void setOpContent(String newOpContent)
    {
        opContent = newOpContent;
    }

    /**
     * Returns the value of '<em><b>planType</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>planType</b></em>' feature
     * @generated
     */
    public String getPlanType()
    {
        return planType;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newPlanType
     * @generated
     */
    public void setPlanType(String newPlanType)
    {
        planType = newPlanType;
    }

    /**
     * Returns the value of '<em><b>taskType</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>taskType</b></em>' feature
     * @generated
     */
    public String getTaskType()
    {
        return taskType;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newTaskType
     * @generated
     */
    public void setTaskType(String newTaskType)
    {
        taskType = newTaskType;
    }

    /**
     * Returns the value of '<em><b>taskNameType</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>taskNameType</b></em>' feature
     * @generated
     */
    public String getTaskNameType()
    {
        return taskNameType;
    }

    /**
     * --> <!-- end-user-doc -->
     * 
     * @param newTaskNameType
     * @generated
     */
    public void setTaskNameType(String newTaskNameType)
    {
        taskNameType = newTaskNameType;
    }

    /**
     * Returns the value of '<em><b>cellId</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>cellId</b></em>' feature
     * @generated
     */
    public String getCellId()
    {
        return cellId;
    }

    /**
     * end-user-doc -->
     * 
     * @param newCellId
     * @generated
     */
    public void setCellId(String newCellId)
    {
        cellId = newCellId;
    }

    /**
     * Returns the value of '<em><b>required</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>required</b></em>' feature
     * @generated
     */
    public String getRequired()
    {
        return required;
    }

    /**
     * <!-- end-user-doc -->
     * 
     * @param newRequired
     * @generated
     */
    public void setRequired(String newRequired)
    {
        required = newRequired;
    }

    /**
     * Returns the value of '<em><b>invalidTime</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>invalidTime</b></em>' feature
     * @generated
     */
    public Date getInvalidTime()
    {
        return invalidTime;
    }

    /**
     * --> <!-- end-user-doc -->
     * 
     * @param newInvalidTime
     * @generated
     */
    public void setInvalidTime(Date newInvalidTime)
    {
        invalidTime = newInvalidTime;
    }

    public Boolean getIsCreateByPmo()
    {
        return isCreateByPmo;
    }

    public void setIsCreateByPmo(Boolean isCreateByPmo)
    {
        this.isCreateByPmo = isCreateByPmo;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    public PlanDto getParentPlan()
    {
        return parentPlan;
    }

    public void setParentPlan(PlanDto parentPlan)
    {
        this.parentPlan = parentPlan;
    }

    public String getParentPlanName()
    {
        return parentPlanName;
    }

    public void setParentPlanName(String parentPlanName)
    {
        this.parentPlanName = parentPlanName;
    }

    public TSUserDto getOwnerInfo()
    {
        return ownerInfo;
    }

    public void setOwnerInfo(TSUserDto ownerInfo)
    {
        this.ownerInfo = ownerInfo;
    }

    public String getOwnerDept()
    {
        return ownerDept;
    }

    public void setOwnerDept(String ownerDept)
    {
        this.ownerDept = ownerDept;
    }

    public String getOwnerRealName()
    {
        return ownerRealName;
    }

    public void setOwnerRealName(String ownerRealName)
    {
        this.ownerRealName = ownerRealName;
    }

    public String getImplementation()
    {
        return implementation;
    }

    public void setImplementation(String implementation)
    {
        this.implementation = implementation;
    }

    public TSUserDto getAssignerInfo()
    {
        return assignerInfo;
    }

    public void setAssignerInfo(TSUserDto assignerInfo)
    {
        this.assignerInfo = assignerInfo;
    }

    public Date getAssignTimeStart()
    {
        return assignTimeStart;
    }

    public void setAssignTimeStart(Date assignTimeStart)
    {
        this.assignTimeStart = assignTimeStart;
    }

    public Date getAssignTimeEnd()
    {
        return assignTimeEnd;
    }

    public void setAssignTimeEnd(Date assignTimeEnd)
    {
        this.assignTimeEnd = assignTimeEnd;
    }

    public TSUserDto getLauncherInfo()
    {
        return launcherInfo;
    }

    public void setLauncherInfo(TSUserDto launcherInfo)
    {
        this.launcherInfo = launcherInfo;
    }

    public TSUserDto getCreator()
    {
        return creator;
    }

    public void setCreator(TSUserDto creator)
    {
        this.creator = creator;
    }

    public BusinessConfigDto getPlanLevelInfo()
    {
        return planLevelInfo;
    }

    public void setPlanLevelInfo(BusinessConfigDto planLevelInfo)
    {
        this.planLevelInfo = planLevelInfo;
    }

    public String getPlanLevelName()
    {
        return planLevelName;
    }

    public void setPlanLevelName(String planLevelName)
    {
        this.planLevelName = planLevelName;
    }

    public Date getPlanStartTimeStart()
    {
        return planStartTimeStart;
    }

    public void setPlanStartTimeStart(Date planStartTimeStart)
    {
        this.planStartTimeStart = planStartTimeStart;
    }

    public Date getPlanStartTimeEnd()
    {
        return planStartTimeEnd;
    }

    public void setPlanStartTimeEnd(Date planStartTimeEnd)
    {
        this.planStartTimeEnd = planStartTimeEnd;
    }

    public Date getPlanEndTimeStart()
    {
        return planEndTimeStart;
    }

    public void setPlanEndTimeStart(Date planEndTimeStart)
    {
        this.planEndTimeStart = planEndTimeStart;
    }

    public Date getPlanEndTimeEnd()
    {
        return planEndTimeEnd;
    }

    public void setPlanEndTimeEnd(Date planEndTimeEnd)
    {
        this.planEndTimeEnd = planEndTimeEnd;
    }

    public Date getActualStartTimeStart()
    {
        return actualStartTimeStart;
    }

    public void setActualStartTimeStart(Date actualStartTimeStart)
    {
        this.actualStartTimeStart = actualStartTimeStart;
    }

    public Date getActualStartTimeEnd()
    {
        return actualStartTimeEnd;
    }

    public void setActualStartTimeEnd(Date actualStartTimeEnd)
    {
        this.actualStartTimeEnd = actualStartTimeEnd;
    }

    public Date getActualEndTimeStart()
    {
        return actualEndTimeStart;
    }

    public void setActualEndTimeStart(Date actualEndTimeStart)
    {
        this.actualEndTimeStart = actualEndTimeStart;
    }

    public Date getActualEndTimeEnd()
    {
        return actualEndTimeEnd;
    }

    public void setActualEndTimeEnd(Date actualEndTimeEnd)
    {
        this.actualEndTimeEnd = actualEndTimeEnd;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getMilestoneName()
    {
        String s = "";
        if (getMilestone().equals("false")){
            s = "否";
        }else {
            s = "是";
        }
        return s;
    }

    public void setMilestoneName(String milestoneName)
    {
        this.milestoneName = milestoneName;
    }

    public String getPreposeIds()
    {
        return preposeIds;
    }

    public void setPreposeIds(String preposeIds)
    {
        this.preposeIds = preposeIds;
    }

    public String getPreposePlans()
    {
        return preposePlans;
    }

    public void setPreposePlans(String preposePlans)
    {
        this.preposePlans = preposePlans;
    }

    public String getDocuments()
    {
        return documents;
    }

    public void setDocuments(String documents)
    {
        this.documents = documents;
    }

    public String getParentStorey()
    {
        return parentStorey;
    }

    public void setParentStorey(String parentStorey)
    {
        this.parentStorey = parentStorey;
    }

    public String getBeforePlanId()
    {
        return beforePlanId;
    }

    public void setBeforePlanId(String beforePlanId)
    {
        this.beforePlanId = beforePlanId;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getFlowFlag()
    {
        return flowFlag;
    }

    public void setFlowFlag(String flowFlag)
    {
        this.flowFlag = flowFlag;
    }

    public String get_parentId()
    {
        return _parentId;
    }

    public void set_parentId(String _parentId)
    {
        this._parentId = _parentId;
    }

    public List<ResourceLinkInfoDto> getRescLinkInfoList()
    {
        return rescLinkInfoList;
    }

    public void setRescLinkInfoList(List<ResourceLinkInfoDto> rescLinkInfoList)
    {
        this.rescLinkInfoList = rescLinkInfoList;
    }

    public List<DeliverablesInfoDto> getDeliInfoList()
    {
        return deliInfoList;
    }

    public void setDeliInfoList(List<DeliverablesInfoDto> deliInfoList)
    {
        this.deliInfoList = deliInfoList;
    }

    public List<InputsDto> getInputList()
    {
        return inputList;
    }

    public void setInputList(List<InputsDto> inputList)
    {
        this.inputList = inputList;
    }

    public List<PlanDto> getPreposeList()
    {
        return preposeList;
    }

    public void setPreposeList(List<PlanDto> preposeList)
    {
        this.preposeList = preposeList;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public TSUserDto getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(TSUserDto currentUser)
    {
        this.currentUser = currentUser;
    }

    public String getIsAssignBack()
    {
        return isAssignBack;
    }

    public void setIsAssignBack(String isAssignBack)
    {
        this.isAssignBack = isAssignBack;
    }

    public String getIsAssignSingleBack()
    {
        return isAssignSingleBack;
    }

    public void setIsAssignSingleBack(String isAssignSingleBack)
    {
        this.isAssignSingleBack = isAssignSingleBack;
    }

    public String getIsChangeSingleBack()
    {
        return isChangeSingleBack;
    }

    public void setIsChangeSingleBack(String isChangeSingleBack)
    {
        this.isChangeSingleBack = isChangeSingleBack;
    }

    public Integer getParentPlanNo() {
        return parentPlanNo;
    }

    public void setParentPlanNo(Integer parentPlanNo) {
        this.parentPlanNo = parentPlanNo;
    }

    public String getPreposeNos() {
        return preposeNos;
    }

    public void setPreposeNos(String preposeNos) {
        this.preposeNos = preposeNos;
    }
    
    public String getDeliverablesName() {
        return deliverablesName;
    }

    public void setDeliverablesName(String deliverablesName) {
        this.deliverablesName = deliverablesName;
    }
    
    public String getPlanTemplateId() {
        return planTemplateId;
    }

    public void setPlanTemplateId(String planTemplateId) {
        this.planTemplateId = planTemplateId;
    }
    
    public String getIsNecessary() {
        return isNecessary;
    }

    public void setIsNecessary(String isNecessary) {
        this.isNecessary = isNecessary;
    }

    
    public String getPlanViewInfoId() {
        return planViewInfoId;
    }

    public void setPlanViewInfoId(String planViewInfoId) {
        this.planViewInfoId = planViewInfoId;
    }

    public Boolean getCreateByPmo() {
        return isCreateByPmo;
    }

    public void setCreateByPmo(Boolean createByPmo) {
        isCreateByPmo = createByPmo;
    }

    public String getPlanReceivedProcInstId() {
        return planReceivedProcInstId;
    }

    public void setPlanReceivedProcInstId(String planReceivedProcInstId) {
        this.planReceivedProcInstId = planReceivedProcInstId;
    }

    public Date getPlanReceivedCompleteTime() {
        return planReceivedCompleteTime;
    }

    public void setPlanReceivedCompleteTime(Date planReceivedCompleteTime) {
        this.planReceivedCompleteTime = planReceivedCompleteTime;
    }

    public String getPlanDelegateProcInstId() {
        return planDelegateProcInstId;
    }

    public void setPlanDelegateProcInstId(String planDelegateProcInstId) {
        this.planDelegateProcInstId = planDelegateProcInstId;
    }

    public String getIsDelegateComplete() {
        return isDelegateComplete;
    }

    public void setIsDelegateComplete(String isDelegateComplete) {
        this.isDelegateComplete = isDelegateComplete;
    }

    public String getTabCbTemplateId() {
        return tabCbTemplateId;
    }

    public void setTabCbTemplateId(String tabCbTemplateId) {
        this.tabCbTemplateId = tabCbTemplateId;
    }
    
    

    public String getRemarkName() {
		return remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}

	public String getDrawingNo() {
		return drawingNo;
	}

	public void setDrawingNo(String drawingNo) {
		this.drawingNo = drawingNo;
	}

	public String getRelayOn() {
		return relayOn;
	}

	public void setRelayOn(String relayOn) {
		this.relayOn = relayOn;
	}

	public String getDelayDay() {
		return delayDay;
	}

	public void setDelayDay(String delayDay) {
		this.delayDay = delayDay;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public Date getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}

	@Override
    public String toString() {
        return "PlanDto{" +
                "planNumber=" + planNumber +
                ", formId='" + formId + '\'' +
                ", planOrder='" + planOrder + '\'' +
                ", isCreateByPmo=" + isCreateByPmo +
                ", planName='" + planName + '\'' +
                ", projectId='" + projectId + '\'' +
                ", project=" + project +
                ", projectName='" + projectName + '\'' +
                ", currentUser=" + currentUser +
                ", parentPlanId='" + parentPlanId + '\'' +
                ", parentPlan=" + parentPlan +
                ", parentPlanName='" + parentPlanName + '\'' +
                ", parentPlanNo=" + parentPlanNo +
                ", owner='" + owner + '\'' +
                ", ownerInfo=" + ownerInfo +
                ", ownerDept='" + ownerDept + '\'' +
                ", ownerRealName='" + ownerRealName + '\'' +
                ", implementation='" + implementation + '\'' +
                ", assigner='" + assigner + '\'' +
                ", assignerInfo=" + assignerInfo +
                ", assignTime=" + assignTime +
                ", assignTimeStart=" + assignTimeStart +
                ", assignTimeEnd=" + assignTimeEnd +
                ", launcher='" + launcher + '\'' +
                ", launcherInfo=" + launcherInfo +
                ", launchTime=" + launchTime +
                ", creator=" + creator +
                ", planLevel='" + planLevel + '\'' +
                ", planLevelInfo=" + planLevelInfo +
                ", planLevelName='" + planLevelName + '\'' +
                ", progressRate='" + progressRate + '\'' +
                ", planStartTime=" + planStartTime +
                ", planStartTimeStart=" + planStartTimeStart +
                ", planStartTimeEnd=" + planStartTimeEnd +
                ", planEndTime=" + planEndTime +
                ", planEndTimeStart=" + planEndTimeStart +
                ", planEndTimeEnd=" + planEndTimeEnd +
                ", workTime='" + workTime + '\'' +
                ", workTimeReference='" + workTimeReference + '\'' +
                ", actualStartTime=" + actualStartTime +
                ", actualStartTimeStart=" + actualStartTimeStart +
                ", actualStartTimeEnd=" + actualStartTimeEnd +
                ", actualEndTime=" + actualEndTime +
                ", actualEndTimeStart=" + actualEndTimeStart +
                ", actualEndTimeEnd=" + actualEndTimeEnd +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                ", flowStatus='" + flowStatus + '\'' +
                ", milestone='" + milestone + '\'' +
                ", isAssignBack='" + isAssignBack + '\'' +
                ", isAssignSingleBack='" + isAssignSingleBack + '\'' +
                ", isChangeSingleBack='" + isChangeSingleBack + '\'' +
                ", milestoneName='" + milestoneName + '\'' +
                ", preposeIds='" + preposeIds + '\'' +
                ", preposeNos='" + preposeNos + '\'' +
                ", preposePlans='" + preposePlans + '\'' +
                ", documents='" + documents + '\'' +
                ", parentStorey='" + parentStorey + '\'' +
                ", storeyNo=" + storeyNo +
                ", beforePlanId='" + beforePlanId + '\'' +
                ", feedbackProcInstId='" + feedbackProcInstId + '\'' +
                ", feedbackRateBefore='" + feedbackRateBefore + '\'' +
                ", planSource='" + planSource + '\'' +
                ", fromTemplate='" + fromTemplate + '\'' +
                ", flowResolveXml='" + flowResolveXml + '\'' +
                ", opContent='" + opContent + '\'' +
                ", planType='" + planType + '\'' +
                ", taskType='" + taskType + '\'' +
                ", taskNameType='" + taskNameType + '\'' +
                ", cellId='" + cellId + '\'' +
                ", required='" + required + '\'' +
                ", order='" + order + '\'' +
                ", result='" + result + '\'' +
                ", flowFlag='" + flowFlag + '\'' +
                ", _parentId='" + _parentId + '\'' +
                ", rescLinkInfoList=" + rescLinkInfoList +
                ", deliInfoList=" + deliInfoList +
                ", inputList=" + inputList +
                ", preposeList=" + preposeList +
                ", invalidTime=" + invalidTime +
                ", deliverablesName='" + deliverablesName + '\'' +
                ", planTemplateId='" + planTemplateId + '\'' +
                ", isNecessary='" + isNecessary + '\'' +
                ", planViewInfoId='" + planViewInfoId + '\'' +
                ", planReceivedProcInstId='" + planReceivedProcInstId + '\'' +
                ", planReceivedCompleteTime=" + planReceivedCompleteTime +
                ", planDelegateProcInstId='" + planDelegateProcInstId + '\'' +
                ", isDelegateComplete='" + isDelegateComplete + '\'' +
                ", concernCode='" + concernCode + '\'' +
                 ", tabCbTemplateId='" + tabCbTemplateId + '\'' +
                ", drawingNo='" + drawingNo + '\'' +
                ", relayOn='" + relayOn + '\'' +
                ", delayDay='" + delayDay + '\'' +
                ", limitType='" + limitType + '\'' +
                ", limitTime='" + limitTime + '\'' +
               
                '}';
    }
}
