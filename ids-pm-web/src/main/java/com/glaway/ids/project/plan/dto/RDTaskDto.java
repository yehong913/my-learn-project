package com.glaway.ids.project.plan.dto;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vdata.GLVData;

/**
 * A representation of the model object '<em><b>Plan</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class RDTaskDto extends GLVData {

    /**
     * <!-- begin-user-doc --> 表单ID<!-- end-user-doc -->
     * 
     * @generated
     */
    private String formId = null;
    
    /**
     * <!-- begin-user-doc --> 任务顺序 <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String planOrder = null;

    /**
     * <!-- begin-user-doc --> 任务名称 <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String planName = null;
      
    /**
     * 当前用户
     */
    @Transient()
    private TSUserDto currentUser = null;

    /**
     * <!-- begin-user-doc --> 父节点id <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String parentPlanId = null;

    /**
     * 父节点任务信息
     */
    private RDTaskDto parentPlan;
    
    /**
     * 父节点任务名称
     */
    @Transient()
    private String parentTaskName = null;

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
    @Transient()
    private String ownerDept = null;

    /**
     * 负责人
     */
    @Transient()
    private String ownerRealName = null;

    /**
     * 应用情况
     */
    @Transient()
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
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignTime = null;

    /**
     * 下达开始时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date assignTimeStart = null;

    /**
     * 下达结束时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
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
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date launchTime = null;

    /**
     * 创建者信息
     */
    private TSUserDto creator;

    /**
     * <!-- begin-user-doc --> 进度 <!-- end-user-doc -->
     */
    
    private String progressRate = "0";

    /**
     * <!-- begin-user-doc --> 任务开始时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    
    @Temporal(TemporalType.DATE)
    private Date planStartTime = null;

    /**
     * 任务开始时间的开始时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date planStartTimeStart = null;

    /**
     * 任务开始时间的结束时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date planStartTimeEnd = null;

    /**
     * <!-- begin-user-doc --> 任务结束时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    
    @Temporal(TemporalType.DATE)
    private Date planEndTime = null;

    /**
     * 任务结束时间的开始时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date planEndTimeStart = null;

    /**
     * 任务结束时间的结束时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
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
    
    @Temporal(TemporalType.DATE)
    private Date actualStartTime = null;

    /**
     * 实际开始时间的开始时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date actualStartTimeStart = null;

    /**
     * 实际开始时间的结束时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date actualStartTimeEnd = null;

    /**
     * <!-- begin-user-doc --> 实际完成时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    
    @Temporal(TemporalType.DATE)
    private Date actualEndTime = null;

    /**
     * 实际结束时间的开始时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date actualEndTimeStart = null;

    /**
     * 实际结束时间的结束时间
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date actualEndTimeEnd = null;

    /**
     * <!-- begin-user-doc --> 备注 <!-- end-user-doc -->
     * 
     * @generated
     */
    
    @Column(length = 2048)
    private String remark = null;

    /**
     * 状态
     */
    @Transient()
    private String status = null;

    /**
     * <!-- begin-user-doc -->
     * 流程状态
     * "NORMAL":无流程
     * "ORDERED":下达流程中
     * "CHANGE":变更流程中
     * "FEEDBACKING":完工反馈流程中
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String flowStatus = "NORMAL";

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
     * 前置任务id
     */
    @Transient()
    private String preposeIds = null;

    /**
     * 前置任务
     */
    @Transient()
    private String preposePlans = null;

    /**
     * 文档
     */
    @Transient()
    private String documents;

    /**
     * 父节点顺序
     */
    @Transient()
    private String parentStorey = null;

    /**
     * <!-- begin-user-doc --> 同级顺序 <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private Integer storeyNo = null;

    /**
     * 上层任务id
     */
    @Transient()
    private String beforePlanId = null;

    /**
     * <!-- begin-user-doc --> 反馈流程实例id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String feedbackProcInstId = null;

    /**
     * <!-- begin-user-doc --> 反馈时记录之前的进度，如果反馈流程被驳回，则恢复之前的进度到progressRate字段
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String feedbackRateBefore = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String fromTemplate = null;

    /**
     * <!-- begin-user-doc --> 流程分解XML<!-- end-user-doc -->
     * 
     * @generated
     */
    
    @Column(columnDefinition = "CLOB")
    private String flowResolveXml = null;
    
    
    /**
     * <!-- begin-user-doc -->任务操作类型：计划分解、流程分解、下发评审任务、撤销计划分解、撤销流程分解等
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String opContent = null;
    

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
    @Transient()
    private String order = null;

    /**
     * 标识该数据是否查询结果
     */
    @Transient()
    private String result = "false";

    /**
     * 是否存在流程 0:不存在; 1:存在
     */
    @Transient()
    private String flowFlag = null;
    
    /**
     * 提交标志
     */
    
    private String submitFlag = null;

    /**
     * 父节点临时id
     */
    @Transient()
    private String _parentId = null;
      
    /**
     * 关联任务
     */
    
    private String linkPlanId = null;
    
    /**
     * 关联任务
     */
    
    private String linkProjectId = null;

//    /**
//     * 资源列表
//     */
//    @Transient()
//    private List<ResourceLinkInfo> rescLinkInfoList = new ArrayList<ResourceLinkInfo>();
//
//    /**
//     * 交付物列表
//     */
//    @Transient()
//    private List<DeliverablesInfo> deliInfoList = new ArrayList<DeliverablesInfo>();
//
//    /**
//     * 输入列表
//     */
//    @Transient()
//    private List<Inputs> inputList = new ArrayList<Inputs>();
//
//    /**
//     * 前置几乎列表
//     */
//    @Transient()
//    private List<Plan> preposeList = new ArrayList<Plan>();

    /**
     * <!-- begin-user-doc --> 废弃时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    
    @Temporal(TemporalType.DATE)
    private Date invalidTime = null;
    
    /**
     * 日期类型(1.公司日历（companyDay）,2.自然日（naturalDay）,3。工作日（workDay）)
     */
    
    private String workTimeType = null;
    
    /**
     * 父任务的开始时间
     */
    @Transient()
    private String parentStartTime = null;
    
    /**
     * 父任务的结束时间
     */
    @Transient()
    private String parentEndTime = null;
    
    /**
     * 父节点计划名称
     */
    @Transient()
    private String parentPlanName = null;
    
    /**
     * <!-- begin-user-doc --> 是否批量下达驳回的 <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String isAssignBack = "false";

    /**
     * Returns the value of '<em><b>planOrder</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planOrder</b></em>' feature
     * @generated
     */
    public String getPlanOrder() {
        return planOrder;
    }

    /**
     * Sets the '{@link PlanDto#getPlanOrder() <em>planOrder</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanOrder
     *            the new value of the '{@link PlanDto#getPlanOrder() planOrder}' feature.
     * @generated
     */
    public void setPlanOrder(String newPlanOrder) {
        planOrder = newPlanOrder;
    }

    /**
     * Returns the value of '<em><b>planName</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planName</b></em>' feature
     * @generated
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * Sets the '{@link PlanDto#getPlanName() <em>planName</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanName
     *            the new value of the '{@link PlanDto#getPlanName() planName}' feature.
     * @generated
     */
    public void setPlanName(String newPlanName) {
        planName = newPlanName;
    }

    /**
     * Returns the value of '<em><b>parentPlanId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>parentPlanId</b></em>' feature
     * @generated
     */
    public String getParentPlanId() {
        return parentPlanId;
    }

    /**
     * Sets the '{@link PlanDto#getParentPlanId() <em>parentPlanId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newParentPlanId
     *            the new value of the '{@link PlanDto#getParentPlanId() parentPlanId}' feature.
     * @generated
     */
    public void setParentPlanId(String newParentPlanId) {
        parentPlanId = newParentPlanId;
    }

    /**
     * Returns the value of '<em><b>owner</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>owner</b></em>' feature
     * @generated
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the '{@link PlanDto#getOwner() <em>owner</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOwner
     *            the new value of the '{@link PlanDto#getOwner() owner}' feature.
     * @generated
     */
    public void setOwner(String newOwner) {
        owner = newOwner;
    }

    /**
     * Returns the value of '<em><b>assigner</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>assigner</b></em>' feature
     * @generated
     */
    public String getAssigner() {
        return assigner;
    }

    /**
     * Sets the '{@link PlanDto#getAssigner() <em>assigner</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAssigner
     *            the new value of the '{@link PlanDto#getAssigner() assigner}' feature.
     * @generated
     */
    public void setAssigner(String newAssigner) {
        assigner = newAssigner;
    }

    /**
     * Returns the value of '<em><b>assignTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>assignTime</b></em>' feature
     * @generated
     */
    public Date getAssignTime() {
        return assignTime;
    }

    /**
     * Sets the '{@link PlanDto#getAssignTime() <em>assignTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAssignTime
     *            the new value of the '{@link PlanDto#getAssignTime() assignTime}' feature.
     * @generated
     */
    public void setAssignTime(Date newAssignTime) {
        assignTime = newAssignTime;
    }

    /**
     * Returns the value of '<em><b>launcher</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>launcher</b></em>' feature
     * @generated
     */
    public String getLauncher() {
        return launcher;
    }

    /**
     * Sets the '{@link PlanDto#getLauncher() <em>launcher</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newLauncher
     *            the new value of the '{@link PlanDto#getLauncher() launcher}' feature.
     * @generated
     */
    public void setLauncher(String newLauncher) {
        launcher = newLauncher;
    }

    /**
     * Returns the value of '<em><b>launchTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>launchTime</b></em>' feature
     * @generated
     */
    public Date getLaunchTime() {
        return launchTime;
    }

    /**
     * Sets the '{@link PlanDto#getLaunchTime() <em>launchTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newLaunchTime
     *            the new value of the '{@link PlanDto#getLaunchTime() launchTime}' feature.
     * @generated
     */
    public void setLaunchTime(Date newLaunchTime) {
        launchTime = newLaunchTime;
    }

    /**
     * Returns the value of '<em><b>progressRate</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>progressRate</b></em>' feature
     * @generated
     */
    public String getProgressRate() {
        return progressRate;
    }

    /**
     * Sets the '{@link PlanDto#getProgressRate() <em>progressRate</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProgressRate
     *            the new value of the '{@link PlanDto#getProgressRate() progressRate}' feature.
     * @generated
     */
    public void setProgressRate(String newProgressRate) {
        progressRate = newProgressRate;
    }

    /**
     * Returns the value of '<em><b>planStartTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planStartTime</b></em>' feature
     * @generated
     */
    public Date getPlanStartTime() {
        return planStartTime;
    }

    /**
     * Sets the '{@link PlanDto#getPlanStartTime() <em>planStartTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanStartTime
     *            the new value of the '{@link PlanDto#getPlanStartTime() planStartTime}' feature.
     * @generated
     */
    public void setPlanStartTime(Date newPlanStartTime) {
        planStartTime = newPlanStartTime;
    }

    /**
     * Returns the value of '<em><b>planEndTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planEndTime</b></em>' feature
     * @generated
     */
    public Date getPlanEndTime() {
        return planEndTime;
    }

    /**
     * Sets the '{@link PlanDto#getPlanEndTime() <em>planEndTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanEndTime
     *            the new value of the '{@link PlanDto#getPlanEndTime() planEndTime}' feature.
     * @generated
     */
    public void setPlanEndTime(Date newPlanEndTime) {
        planEndTime = newPlanEndTime;
    }

    /**
     * Returns the value of '<em><b>workTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>workTime</b></em>' feature
     * @generated
     */
    public String getWorkTime() {
        return workTime;
    }

    /**
     * Sets the '{@link PlanDto#getWorkTime() <em>workTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newWorkTime
     *            the new value of the '{@link PlanDto#getWorkTime() workTime}' feature.
     * @generated
     */
    public void setWorkTime(String newWorkTime) {
        workTime = newWorkTime;
    }

    /**
     * Returns the value of '<em><b>workTimeReference</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>workTimeReference</b></em>' feature
     * @generated
     */
    public String getWorkTimeReference() {
        return workTimeReference;
    }

    /**
     * Sets the '{@link PlanDto#getWorkTimeReference() <em>workTimeReference</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newWorkTimeReference
     *            the new value of the '{@link PlanDto#getWorkTimeReference() workTimeReference}'
     *            feature.
     * @generated
     */
    public void setWorkTimeReference(String newWorkTimeReference) {
        workTimeReference = newWorkTimeReference;
    }

    /**
     * Returns the value of '<em><b>actualStartTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>actualStartTime</b></em>' feature
     * @generated
     */
    public Date getActualStartTime() {
        return actualStartTime;
    }

    /**
     * Sets the '{@link PlanDto#getActualStartTime() <em>actualStartTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newActualStartTime
     *            the new value of the '{@link PlanDto#getActualStartTime() actualStartTime}' feature.
     * @generated
     */
    public void setActualStartTime(Date newActualStartTime) {
        actualStartTime = newActualStartTime;
    }

    /**
     * Returns the value of '<em><b>actualEndTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>actualEndTime</b></em>' feature
     * @generated
     */
    public Date getActualEndTime() {
        return actualEndTime;
    }

    /**
     * Sets the '{@link PlanDto#getActualEndTime() <em>actualEndTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newActualEndTime
     *            the new value of the '{@link PlanDto#getActualEndTime() actualEndTime}' feature.
     * @generated
     */
    public void setActualEndTime(Date newActualEndTime) {
        actualEndTime = newActualEndTime;
    }

    /**
     * Returns the value of '<em><b>remark</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>remark</b></em>' feature
     * @generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the '{@link PlanDto#getRemark() <em>remark</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRemark
     *            the new value of the '{@link PlanDto#getRemark() remark}' feature.
     * @generated
     */
    public void setRemark(String newRemark) {
        remark = newRemark;
    }

    /**
     * Returns the value of '<em><b>flowStatus</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>flowStatus</b></em>' feature
     * @generated
     */
    public String getFlowStatus() {
        return flowStatus;
    }

    /**
     * Sets the '{@link PlanDto#getFlowStatus() <em>flowStatus</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFlowStatus
     *            the new value of the '{@link PlanDto#getFlowStatus() flowStatus}' feature.
     * @generated
     */
    public void setFlowStatus(String newFlowStatus) {
        flowStatus = newFlowStatus;
    }

    /**
     * Returns the value of '<em><b>storeyNo</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>storeyNo</b></em>' feature
     * @generated
     */
    public Integer getStoreyNo() {
        return storeyNo;
    }

    /**
     * Sets the '{@link PlanDto#getStoreyNo() <em>storeyNo</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStoreyNo
     *            the new value of the '{@link PlanDto#getStoreyNo() storeyNo}' feature.
     * @generated
     */
    public void setStoreyNo(Integer newStoreyNo) {
        storeyNo = newStoreyNo;
    }

    /**
     * Returns the value of '<em><b>feedbackProcInstId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>feedbackProcInstId</b></em>' feature
     * @generated
     */
    public String getFeedbackProcInstId() {
        return feedbackProcInstId;
    }

    /**
     * Sets the '{@link PlanDto#getFeedbackProcInstId() <em>feedbackProcInstId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFeedbackProcInstId
     *            the new value of the '{@link PlanDto#getFeedbackProcInstId() feedbackProcInstId}'
     *            feature.
     * @generated
     */
    public void setFeedbackProcInstId(String newFeedbackProcInstId) {
        feedbackProcInstId = newFeedbackProcInstId;
    }

    /**
     * Returns the value of '<em><b>feedbackRateBefore</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>feedbackRateBefore</b></em>' feature
     * @generated
     */
    public String getFeedbackRateBefore() {
        return feedbackRateBefore;
    }

    /**
     * Sets the '{@link PlanDto#getFeedbackRateBefore() <em>feedbackRateBefore</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFeedbackRateBefore
     *            the new value of the '{@link PlanDto#getFeedbackRateBefore() feedbackRateBefore}'
     *            feature.
     * @generated
     */
    public void setFeedbackRateBefore(String newFeedbackRateBefore) {
        feedbackRateBefore = newFeedbackRateBefore;
    }

    /**
     * Returns the value of '<em><b>fromTemplate</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>fromTemplate</b></em>' feature
     * @generated
     */
    public String getFromTemplate() {
        return fromTemplate;
    }

    /**
     * Sets the '{@link PlanDto#getFromTemplate() <em>fromTemplate</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFromTemplate
     *            the new value of the '{@link PlanDto#getFromTemplate() fromTemplate}' feature.
     * @generated
     */
    public void setFromTemplate(String newFromTemplate) {
        fromTemplate = newFromTemplate;
    }

    /**
     * Returns the value of '<em><b>flowResolveXml</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>flowResolveXml</b></em>' feature
     * @generated
     */
    public String getFlowResolveXml() {
        return flowResolveXml;
    }

    /**
     * Sets the '{@link PlanDto#getFlowResolveXml() <em>flowResolveXml</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFlowResolveXml
     *            the new value of the '{@link PlanDto#getFlowResolveXml() flowResolveXml}' feature.
     * @generated
     */
    public void setFlowResolveXml(String newFlowResolveXml) {
        flowResolveXml = newFlowResolveXml;
    }

    /**
     * Returns the value of '<em><b>cellId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>cellId</b></em>' feature
     * @generated
     */
    public String getCellId() {
        return cellId;
    }

    /**
     * Sets the '{@link PlanDto#getCellId() <em>cellId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newCellId
     *            the new value of the '{@link PlanDto#getCellId() cellId}' feature.
     * @generated
     */
    public void setCellId(String newCellId) {
        cellId = newCellId;
    }

    /**
     * Returns the value of '<em><b>required</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>required</b></em>' feature
     * @generated
     */
    public String getRequired() {
        return required;
    }

    /**
     * Sets the '{@link PlanDto#getRequired() <em>required</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRequired
     *            the new value of the '{@link PlanDto#getRequired() required}' feature.
     * @generated
     */
    public void setRequired(String newRequired) {
        required = newRequired;
    }

    /**
     * Returns the value of '<em><b>invalidTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>invalidTime</b></em>' feature
     * @generated
     */
    public Date getInvalidTime() {
        return invalidTime;
    }

    /**
     * Sets the '{@link PlanDto#getInvalidTime() <em>invalidTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newInvalidTime
     *            the new value of the '{@link PlanDto#getInvalidTime() invalidTime}' feature.
     * @generated
     */
    public void setInvalidTime(Date newInvalidTime) {
        invalidTime = newInvalidTime;
    }

    public TSUserDto getOwnerInfo() {
        return ownerInfo;
    }

    public void setOwnerInfo(TSUserDto ownerInfo) {
        this.ownerInfo = ownerInfo;
    }

    public String getOwnerDept() {
        return ownerDept;
    }

    public void setOwnerDept(String ownerDept) {
        this.ownerDept = ownerDept;
    }

    public String getOwnerRealName() {
        return ownerRealName;
    }

    public void setOwnerRealName(String ownerRealName) {
        this.ownerRealName = ownerRealName;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public TSUserDto getAssignerInfo() {
        return assignerInfo;
    }

    public void setAssignerInfo(TSUserDto assignerInfo) {
        this.assignerInfo = assignerInfo;
    }

    public Date getAssignTimeStart() {
        return assignTimeStart;
    }

    public void setAssignTimeStart(Date assignTimeStart) {
        this.assignTimeStart = assignTimeStart;
    }

    public Date getAssignTimeEnd() {
        return assignTimeEnd;
    }

    public void setAssignTimeEnd(Date assignTimeEnd) {
        this.assignTimeEnd = assignTimeEnd;
    }

    public TSUserDto getLauncherInfo() {
        return launcherInfo;
    }

    public void setLauncherInfo(TSUserDto launcherInfo) {
        this.launcherInfo = launcherInfo;
    }

    public TSUserDto getCreator() {
        return creator;
    }

    public void setCreator(TSUserDto creator) {
        this.creator = creator;
    }

    public Date getPlanStartTimeStart() {
        return planStartTimeStart;
    }

    public void setPlanStartTimeStart(Date planStartTimeStart) {
        this.planStartTimeStart = planStartTimeStart;
    }

    public Date getPlanStartTimeEnd() {
        return planStartTimeEnd;
    }

    public void setPlanStartTimeEnd(Date planStartTimeEnd) {
        this.planStartTimeEnd = planStartTimeEnd;
    }

    public Date getPlanEndTimeStart() {
        return planEndTimeStart;
    }

    public void setPlanEndTimeStart(Date planEndTimeStart) {
        this.planEndTimeStart = planEndTimeStart;
    }

    public Date getPlanEndTimeEnd() {
        return planEndTimeEnd;
    }

    public void setPlanEndTimeEnd(Date planEndTimeEnd) {
        this.planEndTimeEnd = planEndTimeEnd;
    }

    public Date getActualStartTimeStart() {
        return actualStartTimeStart;
    }

    public void setActualStartTimeStart(Date actualStartTimeStart) {
        this.actualStartTimeStart = actualStartTimeStart;
    }

    public Date getActualStartTimeEnd() {
        return actualStartTimeEnd;
    }

    public void setActualStartTimeEnd(Date actualStartTimeEnd) {
        this.actualStartTimeEnd = actualStartTimeEnd;
    }

    public Date getActualEndTimeStart() {
        return actualEndTimeStart;
    }

    public void setActualEndTimeStart(Date actualEndTimeStart) {
        this.actualEndTimeStart = actualEndTimeStart;
    }

    public Date getActualEndTimeEnd() {
        return actualEndTimeEnd;
    }

    public void setActualEndTimeEnd(Date actualEndTimeEnd) {
        this.actualEndTimeEnd = actualEndTimeEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPreposeIds() {
        return preposeIds;
    }

    public void setPreposeIds(String preposeIds) {
        this.preposeIds = preposeIds;
    }

    public String getPreposePlans() {
        return preposePlans;
    }

    public void setPreposePlans(String preposePlans) {
        this.preposePlans = preposePlans;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getParentStorey() {
        return parentStorey;
    }

    public void setParentStorey(String parentStorey) {
        this.parentStorey = parentStorey;
    }

    public String getBeforePlanId() {
        return beforePlanId;
    }

    public void setBeforePlanId(String beforePlanId) {
        this.beforePlanId = beforePlanId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFlowFlag() {
        return flowFlag;
    }

    public void setFlowFlag(String flowFlag) {
        this.flowFlag = flowFlag;
    }

    public String get_parentId() {
        return _parentId;
    }

    public void set_parentId(String _parentId) {
        this._parentId = _parentId;
    }

    public TSUserDto getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(TSUserDto currentUser) {
        this.currentUser = currentUser;
    }
    
    public String getIsAssignSingleBack() {
        return isAssignSingleBack;
    }

    public void setIsAssignSingleBack(String isAssignSingleBack) {
        this.isAssignSingleBack = isAssignSingleBack;
    }

    public String getIsChangeSingleBack() {
        return isChangeSingleBack;
    }

    public void setIsChangeSingleBack(String isChangeSingleBack) {
        this.isChangeSingleBack = isChangeSingleBack;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public RDTaskDto getParentPlan() {
        return parentPlan;
    }

    public void setParentPlan(RDTaskDto parentPlan) {
        this.parentPlan = parentPlan;
    }

    public String getParentTaskName() {
        return parentTaskName;
    }

    public void setParentTaskName(String parentTaskName) {
        this.parentTaskName = parentTaskName;
    }

    public String getLinkPlanId() {
        return linkPlanId;
    }

    public void setLinkPlanId(String linkPlanId) {
        this.linkPlanId = linkPlanId;
    }

    public String getWorkTimeType() {
        return workTimeType;
    }

    public void setWorkTimeType(String workTimeType) {
        this.workTimeType = workTimeType;
    }

    public String getParentStartTime() {
        return parentStartTime;
    }

    public void setParentStartTime(String parentStartTime) {
        this.parentStartTime = parentStartTime;
    }

    public String getParentEndTime() {
        return parentEndTime;
    }

    public void setParentEndTime(String parentEndTime) {
        this.parentEndTime = parentEndTime;
    }

    public String getOpContent() {
        return opContent;
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }

    public String getParentPlanName() {
        return parentPlanName;
    }

    public void setParentPlanName(String parentPlanName) {
        this.parentPlanName = parentPlanName;
    }

    public String getIsAssignBack() {
        return isAssignBack;
    }

    public void setIsAssignBack(String isAssignBack) {
        this.isAssignBack = isAssignBack;
    }

    public String getSubmitFlag() {
        return submitFlag;
    }

    public void setSubmitFlag(String submitFlag) {
        this.submitFlag = submitFlag;
    }

    public String getLinkProjectId() {
        return linkProjectId;
    }

    public void setLinkProjectId(String linkProjectId) {
        this.linkProjectId = linkProjectId;
    } 

}
