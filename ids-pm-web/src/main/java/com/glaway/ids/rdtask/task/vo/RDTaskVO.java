package com.glaway.ids.rdtask.task.vo;


import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import com.alibaba.fastjson.annotation.JSONField;
import com.glaway.foundation.common.dto.TSUserDto;

public class RDTaskVO {
    
    private String procInstId = null;
    
    private String id = null;
    
    private String optBtn = null;
    
    private String PlanStartTimeStr = null;
    
    private String PlanEndTimeStr = null;
    
    private String createFullName = null;
    
    private String  avaliable= null;
    
    private String assignTimeStr = null;
    
    /**
     * 任务显示创建者
     */
    private String createBy ;
    
    /**
     * 任务显示创建者名称
     */
    private String createName ;
    
    /**
     * 任务创建时间
     */
    private Date createTime ;
    
    /**
     * 任务显示时间
     */
    private String createTimeStr ;
    
    /**
     * 流程状态
     */
    private String bizCurrent ;
    
    /**
     * 流程状态中文
     */
    private String bizCurrentCH ;

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
    private TSUserDto currentUser = null;

    /**
     * <!-- begin-user-doc --> 父节点id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String parentPlanId = null;

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
    @Transient()
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
    @Transient()
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
     * 父节点临时id
     */
    @Transient()
    private String _parentId = null;
      
    /**
     * 关联任务
     */
    
    private String linkPlanId = null;
    
    @JSONField(name="rows")
    private List<RDTaskVO> children = new ArrayList<RDTaskVO>();

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
     * Sets the '{@link Plan#getPlanOrder() <em>planOrder</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanOrder
     *            the new value of the '{@link Plan#getPlanOrder() planOrder}' feature.
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
     * Sets the '{@link Plan#getPlanName() <em>planName</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanName
     *            the new value of the '{@link Plan#getPlanName() planName}' feature.
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
     * Sets the '{@link Plan#getParentPlanId() <em>parentPlanId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newParentPlanId
     *            the new value of the '{@link Plan#getParentPlanId() parentPlanId}' feature.
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
     * Sets the '{@link Plan#getOwner() <em>owner</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOwner
     *            the new value of the '{@link Plan#getOwner() owner}' feature.
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
     * Sets the '{@link Plan#getAssigner() <em>assigner</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAssigner
     *            the new value of the '{@link Plan#getAssigner() assigner}' feature.
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
     * Sets the '{@link Plan#getAssignTime() <em>assignTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAssignTime
     *            the new value of the '{@link Plan#getAssignTime() assignTime}' feature.
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
     * Sets the '{@link Plan#getLauncher() <em>launcher</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newLauncher
     *            the new value of the '{@link Plan#getLauncher() launcher}' feature.
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
     * Sets the '{@link Plan#getLaunchTime() <em>launchTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newLaunchTime
     *            the new value of the '{@link Plan#getLaunchTime() launchTime}' feature.
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
     * Sets the '{@link Plan#getProgressRate() <em>progressRate</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProgressRate
     *            the new value of the '{@link Plan#getProgressRate() progressRate}' feature.
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
     * Sets the '{@link Plan#getPlanStartTime() <em>planStartTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanStartTime
     *            the new value of the '{@link Plan#getPlanStartTime() planStartTime}' feature.
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
     * Sets the '{@link Plan#getPlanEndTime() <em>planEndTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanEndTime
     *            the new value of the '{@link Plan#getPlanEndTime() planEndTime}' feature.
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
     * Sets the '{@link Plan#getWorkTime() <em>workTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newWorkTime
     *            the new value of the '{@link Plan#getWorkTime() workTime}' feature.
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
     * Sets the '{@link Plan#getWorkTimeReference() <em>workTimeReference</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newWorkTimeReference
     *            the new value of the '{@link Plan#getWorkTimeReference() workTimeReference}'
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
     * Sets the '{@link Plan#getActualStartTime() <em>actualStartTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newActualStartTime
     *            the new value of the '{@link Plan#getActualStartTime() actualStartTime}' feature.
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
     * Sets the '{@link Plan#getActualEndTime() <em>actualEndTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newActualEndTime
     *            the new value of the '{@link Plan#getActualEndTime() actualEndTime}' feature.
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
     * Sets the '{@link Plan#getRemark() <em>remark</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRemark
     *            the new value of the '{@link Plan#getRemark() remark}' feature.
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
     * Sets the '{@link Plan#getFlowStatus() <em>flowStatus</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFlowStatus
     *            the new value of the '{@link Plan#getFlowStatus() flowStatus}' feature.
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
     * Sets the '{@link Plan#getStoreyNo() <em>storeyNo</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStoreyNo
     *            the new value of the '{@link Plan#getStoreyNo() storeyNo}' feature.
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
     * Sets the '{@link Plan#getFeedbackProcInstId() <em>feedbackProcInstId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFeedbackProcInstId
     *            the new value of the '{@link Plan#getFeedbackProcInstId() feedbackProcInstId}'
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
     * Sets the '{@link Plan#getFeedbackRateBefore() <em>feedbackRateBefore</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFeedbackRateBefore
     *            the new value of the '{@link Plan#getFeedbackRateBefore() feedbackRateBefore}'
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
     * Sets the '{@link Plan#getFromTemplate() <em>fromTemplate</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFromTemplate
     *            the new value of the '{@link Plan#getFromTemplate() fromTemplate}' feature.
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
     * Sets the '{@link Plan#getFlowResolveXml() <em>flowResolveXml</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFlowResolveXml
     *            the new value of the '{@link Plan#getFlowResolveXml() flowResolveXml}' feature.
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
     * Sets the '{@link Plan#getCellId() <em>cellId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newCellId
     *            the new value of the '{@link Plan#getCellId() cellId}' feature.
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
     * Sets the '{@link Plan#getRequired() <em>required</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRequired
     *            the new value of the '{@link Plan#getRequired() required}' feature.
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
     * Sets the '{@link Plan#getInvalidTime() <em>invalidTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newInvalidTime
     *            the new value of the '{@link Plan#getInvalidTime() invalidTime}' feature.
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

    public List<RDTaskVO> getChildren() {
        return children;
    }

    public void setChildren(List<RDTaskVO> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOptBtn() {
        return optBtn;
    }

    public void setOptBtn(String optBtn) {
        this.optBtn = optBtn;
    }

    public String getPlanStartTimeStr() {
        return PlanStartTimeStr;
    }

    public void setPlanStartTimeStr(String planStartTimeStr) {
        PlanStartTimeStr = planStartTimeStr;
    }

    public String getPlanEndTimeStr() {
        return PlanEndTimeStr;
    }

    public void setPlanEndTimeStr(String planEndTimeStr) {
        PlanEndTimeStr = planEndTimeStr;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getBizCurrent() {
        return bizCurrent;
    }

    public void setBizCurrent(String bizCurrent) {
        this.bizCurrent = bizCurrent;
    }

    public String getBizCurrentCH() {
        return bizCurrentCH;
    }

    public void setBizCurrentCH(String bizCurrentCH) {
        this.bizCurrentCH = bizCurrentCH;
    }

    public String getCreateFullName() {
        return createFullName;
    }

    public void setCreateFullName(String createFullName) {
        this.createFullName = createFullName;
    }

    public String getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(String avaliable) {
        this.avaliable = avaliable;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getAssignTimeStr() {
        return assignTimeStr;
    }

    public void setAssignTimeStr(String assignTimeStr) {
        this.assignTimeStr = assignTimeStr;
    }

}
