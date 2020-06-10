package com.glaway.ids.project.plan.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Convert;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.entity.GLObject;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;


/**
 * 流程任务变更信息表
 * 
 * @generated
 */
public class FlowTaskDto extends GLObject {

    /**
     * 页签组合模板id
     */
    private String tabCbTemplateId = null;
    
    /**
     * <!-- begin-user-doc --> 是否单条下达驳回的 <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String isChangeSingleBack = "false";

    /**
     * <!-- begin-user-doc -->变更类型
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String changeType = null;
    
    /**
     * 提交标志
     */
   
    private String submitFlag = null;

    /**
     * 
     */
    @Transient
    private BusinessConfigDto changeTypeInfo;

    /**
     * <!-- begin-user-doc -->备注
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    @Column(length = 2048)
    private String changeRemark = null;

    /**
     * <!-- begin-user-doc -->变更附件id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String changeInfoDocId = null;

    /**
     * <!-- begin-user-doc -->附件名称
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String changeInfoDocName = null;

    /**
     * <!-- begin-user-doc -->附件路径
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String changeInfoDocPath = null;

    /**
     * <!-- begin-user-doc -->计划id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String planId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    @Convert("")
    private int planNumber = 0;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String planOrder = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String planName = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String parentPlanId = null;

//    /**
//     * 
//     */
//    @ManyToOne(optional = true, fetch = FetchType.EAGER)
//    @JoinColumn(name = "parentPlanId", insertable = false, updatable = false)
//    @ForeignKey(name = "none")
//    @NotFound(action = NotFoundAction.IGNORE)
//    private PlanDto parentPlan;
    
    
    /**
     * 父任务信息
     */
    @Transient
    private RDTaskDto parentRDTask;

    /**
     * 日期类型(1.公司日历（companyDay）,2.自然日（naturalDay）,3。工作日（workDay）)
     */
   
    private String workTimeType = null;
    
    /**
     * 父计划名称
     */
    @Transient()
    private String parentPlanName = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String owner = null;

    /**
     * 
     */
    @Transient
    private TSUserDto ownerInfo;

    /**
     * 负责人名称
     */
    @Transient()
    private String ownerRealName = null;

    /**
     * 负责人部门
     */
    @Transient()
    private String ownerDept = null;

    /**
     * 
     */
    @Transient
    private TSUserDto creator;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String implementation = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String assigner = null;

    /**
     * 
     */
    @Transient
    private TSUserDto assignerInfo;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    @Temporal(TemporalType.DATE)
    private Date assignTime = null;

    /**
     * 下发时间 开始
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date assignTimeStart = null;

    /**
     * 下发时间 结束
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date assignTimeEnd = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String planLevel = null;

    /**
     * 
     */
    @Transient
    private BusinessConfigDto planLevelInfo;

    /**
     * 计划等级
     */
    @Transient()
    private String planLevelName = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String progressRate = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    @Temporal(TemporalType.DATE)
    private Date planStartTime = null;

    /**
     * 
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date planStartTimeStart = null;

    /**
     * 
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date planStartTimeEnd = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    @Temporal(TemporalType.DATE)
    private Date planEndTime = null;

    /**
     * 
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date planEndTimeStart = null;

    /**
     * 
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date planEndTimeEnd = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
     * <!-- begin-user-doc -->活动名称类型<!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String taskNameType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    @Temporal(TemporalType.DATE)
    private Date actualStartTime = null;

    /**
     * 
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date actualStartTimeStart = null;

    /**
     * 
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date actualStartTimeEnd = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    @Temporal(TemporalType.DATE)
    private Date actualEndTime = null;

    /**
     * 
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date actualEndTimeStart = null;

    /**
     * 
     */
    @Transient()
    @Temporal(TemporalType.DATE)
    private Date actualEndTimeEnd = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    @Column(length = 2048)
    private String remark = null;

    /**
     * 
     */
    @Transient()
    private String status = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    @Column()
    private String launcher = "";

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    @Temporal(TemporalType.DATE)
    private Date launchTime = null;

    /**
     * 
     */
    @Transient
    private TSUserDto launcherInfo;

    /**
     * <!-- begin-user-doc --> 废弃时间 <!-- end-user-doc -->
     * 
     * @generated
     */
   
    @Temporal(TemporalType.DATE)
    private Date invalidTime = null;

    /**
     * <!-- begin-user-doc --> 计划类别 <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String taskType = "";

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private int storeyNo = 0;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String beforePlanId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String projectStatus = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String milestone = "false";

    /**
     * 
     */
    @Transient()
    private String milestoneName = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String flowStatus = "NORMAL";

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String risk = null;

    /**
     * 前置计划id
     */
    @Transient()
    private String preposeIds = null;

    /**
     * 前置计划
     */
    @Transient()
    private String preposePlans = null;

    /**
     * 外部计划id
     */
    @Transient()
    private String outPreposeIds = null;

    /**
     * 外部计划
     */
    @Transient()
    private String outPreposePlans = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String cellId = null;

    /**
     * <!-- begin-user-doc -->是否来自流程模板
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String fromTemplate = null;

    /**
     * 是否来自流程模板<流程分解的任务使用>
     * 
     * @generated
     */
   
    @Column(columnDefinition = "CLOB")
    private String flowResolveXml = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String required = null;

    /**
     * 是否来自流程模板<流程分解的任务使用>
     * 
     * @generated
     */
   
    private String bizCurrent = null;

    /**
     * 是否来自流程模板<流程分解的任务使用>
     * 
     * @generated
     */
   
    private String planType = null;

    /**
     * 是否来自流程模板<流程分解的任务使用>
     * 
     * @generated
     */
   
    private String opContent = null;

    /**
     * 是否来自流程模板<流程分解的任务使用>
     * 
     * @generated
     */
   
    private String planSource = null;

    /**
     * 是否来自流程模板<流程分解的任务使用>
     * 
     * @generated
     */
   
    private String feedbackRateBefore = null;

    /**
     * 是否来自流程模板<流程分解的任务使用>
     * 
     * @generated
     */ 
   
    private String feedbackProcInstId = null;

    /**
     * 
     */
    @Transient()
    private String _parentId = null;

    /**
     * 
     */
    @Transient()
    private String order = null;

    /**
     * 
     */
    @Transient()
    private String documents = null;

    /**
     * 
     */
    @Transient()
    private String parentStorey = null;

    /**
     * 输入
     */
    @Transient()
    private List<FlowTaskInputsDto> inputList = new ArrayList<FlowTaskInputsDto>();

    /**
     * 输出
     */
    @Transient()
    private List<FlowTaskDeliverablesInfoDto> outputList = new ArrayList<FlowTaskDeliverablesInfoDto>();

    /**
     * 输出
     */
    @Transient()
    private List<FlowTaskPreposeDto> flowTaskPreposeList = new ArrayList<FlowTaskPreposeDto>();

    /**
     * 资源
     */
    @Transient()
    private List<FlowTaskResourceLinkInfoDto> resourceLinkList = new ArrayList<FlowTaskResourceLinkInfoDto>();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
   
    private String formId = null;
    
    /**
     * 名称对应的标准名称库ID
     */
    @Transient()
    private String nameStandardId = null;

    /**
     * Returns the value of '<em><b>changeType</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>changeType</b></em>' feature
     * @generated
     */
    public String getChangeType() {
        return changeType;
    }

    /**
     * Sets the '{@link FlowTask#getChangeType() <em>changeType</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newChangeType
     *            the new value of the '{@link FlowTask#getChangeType() changeType}' feature.
     * @generated
     */
    public void setChangeType(String newChangeType) {
        changeType = newChangeType;
    }

    /**
     * Returns the value of '<em><b>changeRemark</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>changeRemark</b></em>' feature
     * @generated
     */
    public String getChangeRemark() {
        return changeRemark;
    }

    /**
     * Sets the '{@link FlowTask#getChangeRemark() <em>changeRemark</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newChangeRemark
     *            the new value of the '{@link FlowTask#getChangeRemark() changeRemark}' feature.
     * @generated
     */
    public void setChangeRemark(String newChangeRemark) {
        changeRemark = newChangeRemark;
    }

    /**
     * Returns the value of '<em><b>changeInfoDocId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>changeInfoDocId</b></em>' feature
     * @generated
     */
    public String getChangeInfoDocId() {
        return changeInfoDocId;
    }

    /**
     * Sets the '{@link FlowTask#getChangeInfoDocId() <em>changeInfoDocId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newChangeInfoDocId
     *            the new value of the '{@link FlowTask#getChangeInfoDocId() changeInfoDocId}'
     *            feature.
     * @generated
     */
    public void setChangeInfoDocId(String newChangeInfoDocId) {
        changeInfoDocId = newChangeInfoDocId;
    }

    /**
     * Returns the value of '<em><b>changeInfoDocName</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>changeInfoDocName</b></em>' feature
     * @generated
     */
    public String getChangeInfoDocName() {
        return changeInfoDocName;
    }

    /**
     * Sets the '{@link FlowTask#getChangeInfoDocName() <em>changeInfoDocName</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newChangeInfoDocName
     *            the new value of the '{@link FlowTask#getChangeInfoDocName() changeInfoDocName}'
     *            feature.
     * @generated
     */
    public void setChangeInfoDocName(String newChangeInfoDocName) {
        changeInfoDocName = newChangeInfoDocName;
    }

    /**
     * Returns the value of '<em><b>changeInfoDocPath</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>changeInfoDocPath</b></em>' feature
     * @generated
     */
    public String getChangeInfoDocPath() {
        return changeInfoDocPath;
    }

    /**
     * Sets the '{@link FlowTask#getChangeInfoDocPath() <em>changeInfoDocPath</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newChangeInfoDocPath
     *            the new value of the '{@link FlowTask#getChangeInfoDocPath() changeInfoDocPath}'
     *            feature.
     * @generated
     */
    public void setChangeInfoDocPath(String newChangeInfoDocPath) {
        changeInfoDocPath = newChangeInfoDocPath;
    }

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
     * Sets the '{@link FlowTask#getPlanId() <em>planId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanId
     *            the new value of the '{@link FlowTask#getPlanId() planId}' feature.
     * @generated
     */
    public void setPlanId(String newPlanId) {
        planId = newPlanId;
    }

    /**
     * Returns the value of '<em><b>planNumber</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planNumber</b></em>' feature
     * @generated
     */
    public int getPlanNumber() {
        return planNumber;
    }

    /**
     * Sets the '{@link FlowTask#getPlanNumber() <em>planNumber</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanNumber
     *            the new value of the '{@link FlowTask#getPlanNumber() planNumber}' feature.
     * @generated
     */
    public void setPlanNumber(int newPlanNumber) {
        planNumber = newPlanNumber;
    }

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
     * Sets the '{@link FlowTask#getPlanOrder() <em>planOrder</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanOrder
     *            the new value of the '{@link FlowTask#getPlanOrder() planOrder}' feature.
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
     * Sets the '{@link FlowTask#getPlanName() <em>planName</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanName
     *            the new value of the '{@link FlowTask#getPlanName() planName}' feature.
     * @generated
     */
    public void setPlanName(String newPlanName) {
        planName = newPlanName;
    }

//    /**
//     * Returns the value of '<em><b>projectId</b></em>' feature.
//     * <!-- begin-user-doc -->
//     * <!-- end-user-doc -->
//     * 
//     * @return the value of '<em><b>projectId</b></em>' feature
//     * @generated
//     */
//    public String getProjectId() {
//        return projectId;
//    }
//
//    /**
//     * Sets the '{@link FlowTask#getProjectId() <em>projectId</em>}' feature.
//     * <!-- begin-user-doc -->
//     * <!-- end-user-doc -->
//     * 
//     * @param newProjectId
//     *            the new value of the '{@link FlowTask#getProjectId() projectId}' feature.
//     * @generated
//     */
//    public void setProjectId(String newProjectId) {
//        projectId = newProjectId;
//    }

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
     * Sets the '{@link FlowTask#getParentPlanId() <em>parentPlanId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newParentPlanId
     *            the new value of the '{@link FlowTask#getParentPlanId() parentPlanId}' feature.
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
     * Sets the '{@link FlowTask#getOwner() <em>owner</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOwner
     *            the new value of the '{@link FlowTask#getOwner() owner}' feature.
     * @generated
     */
    public void setOwner(String newOwner) {
        owner = newOwner;
    }

    /**
     * Returns the value of '<em><b>implementation</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>implementation</b></em>' feature
     * @generated
     */
    public String getImplementation() {
        return implementation;
    }

    /**
     * Sets the '{@link FlowTask#getImplementation() <em>implementation</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newImplementation
     *            the new value of the '{@link FlowTask#getImplementation() implementation}'
     *            feature.
     * @generated
     */
    public void setImplementation(String newImplementation) {
        implementation = newImplementation;
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
     * Sets the '{@link FlowTask#getAssigner() <em>assigner</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAssigner
     *            the new value of the '{@link FlowTask#getAssigner() assigner}' feature.
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
     * Sets the '{@link FlowTask#getAssignTime() <em>assignTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAssignTime
     *            the new value of the '{@link FlowTask#getAssignTime() assignTime}' feature.
     * @generated
     */
    public void setAssignTime(Date newAssignTime) {
        assignTime = newAssignTime;
    }

    /**
     * Returns the value of '<em><b>planLevel</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planLevel</b></em>' feature
     * @generated
     */
    public String getPlanLevel() {
        return planLevel;
    }

    /**
     * Sets the '{@link FlowTask#getPlanLevel() <em>planLevel</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanLevel
     *            the new value of the '{@link FlowTask#getPlanLevel() planLevel}' feature.
     * @generated
     */
    public void setPlanLevel(String newPlanLevel) {
        planLevel = newPlanLevel;
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
     * Sets the '{@link FlowTask#getProgressRate() <em>progressRate</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProgressRate
     *            the new value of the '{@link FlowTask#getProgressRate() progressRate}' feature.
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
     * Sets the '{@link FlowTask#getPlanStartTime() <em>planStartTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanStartTime
     *            the new value of the '{@link FlowTask#getPlanStartTime() planStartTime}' feature.
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
     * Sets the '{@link FlowTask#getPlanEndTime() <em>planEndTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanEndTime
     *            the new value of the '{@link FlowTask#getPlanEndTime() planEndTime}' feature.
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
     * Sets the '{@link FlowTask#getWorkTime() <em>workTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newWorkTime
     *            the new value of the '{@link FlowTask#getWorkTime() workTime}' feature.
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
     * Sets the '{@link FlowTask#getWorkTimeReference() <em>workTimeReference</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newWorkTimeReference
     *            the new value of the '{@link FlowTask#getWorkTimeReference() workTimeReference}'
     *            feature.
     * @generated
     */
    public void setWorkTimeReference(String newWorkTimeReference) {
        workTimeReference = newWorkTimeReference;
    }

    /**
     * Returns the value of '<em><b>taskNameType</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>taskNameType</b></em>' feature
     * @generated
     */
    public String getTaskNameType() {
        return taskNameType;
    }

    /**
     * Sets the '{@link FlowTask#getTaskNameType() <em>taskNameType</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newTaskNameType
     *            the new value of the '{@link FlowTask#getTaskNameType() taskNameType}' feature.
     * @generated
     */
    public void setTaskNameType(String newTaskNameType) {
        taskNameType = newTaskNameType;
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
     * Sets the '{@link FlowTask#getActualStartTime() <em>actualStartTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newActualStartTime
     *            the new value of the '{@link FlowTask#getActualStartTime() actualStartTime}'
     *            feature.
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
     * Sets the '{@link FlowTask#getActualEndTime() <em>actualEndTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newActualEndTime
     *            the new value of the '{@link FlowTask#getActualEndTime() actualEndTime}' feature.
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
     * Sets the '{@link FlowTask#getRemark() <em>remark</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRemark
     *            the new value of the '{@link FlowTask#getRemark() remark}' feature.
     * @generated
     */
    public void setRemark(String newRemark) {
        remark = newRemark;
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
     * Sets the '{@link FlowTask#getLauncher() <em>launcher</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newLauncher
     *            the new value of the '{@link FlowTask#getLauncher() launcher}' feature.
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
     * Sets the '{@link FlowTask#getLaunchTime() <em>launchTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newLaunchTime
     *            the new value of the '{@link FlowTask#getLaunchTime() launchTime}' feature.
     * @generated
     */
    public void setLaunchTime(Date newLaunchTime) {
        launchTime = newLaunchTime;
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
     * Sets the '{@link FlowTask#getInvalidTime() <em>invalidTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newInvalidTime
     *            the new value of the '{@link FlowTask#getInvalidTime() invalidTime}' feature.
     * @generated
     */
    public void setInvalidTime(Date newInvalidTime) {
        invalidTime = newInvalidTime;
    }

    /**
     * Returns the value of '<em><b>taskType</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>taskType</b></em>' feature
     * @generated
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * Sets the '{@link FlowTask#getTaskType() <em>taskType</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newTaskType
     *            the new value of the '{@link FlowTask#getTaskType() taskType}' feature.
     * @generated
     */
    public void setTaskType(String newTaskType) {
        taskType = newTaskType;
    }

    /**
     * Returns the value of '<em><b>storeyNo</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>storeyNo</b></em>' feature
     * @generated
     */
    public int getStoreyNo() {
        return storeyNo;
    }

    /**
     * Sets the '{@link FlowTask#getStoreyNo() <em>storeyNo</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStoreyNo
     *            the new value of the '{@link FlowTask#getStoreyNo() storeyNo}' feature.
     * @generated
     */
    public void setStoreyNo(int newStoreyNo) {
        storeyNo = newStoreyNo;
    }

    /**
     * Returns the value of '<em><b>beforePlanId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>beforePlanId</b></em>' feature
     * @generated
     */
    public String getBeforePlanId() {
        return beforePlanId;
    }

    /**
     * Sets the '{@link FlowTask#getBeforePlanId() <em>beforePlanId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newBeforePlanId
     *            the new value of the '{@link FlowTask#getBeforePlanId() beforePlanId}' feature.
     * @generated
     */
    public void setBeforePlanId(String newBeforePlanId) {
        beforePlanId = newBeforePlanId;
    }

    /**
     * Returns the value of '<em><b>projectStatus</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>projectStatus</b></em>' feature
     * @generated
     */
    public String getProjectStatus() {
        return projectStatus;
    }

    /**
     * Sets the '{@link FlowTask#getProjectStatus() <em>projectStatus</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjectStatus
     *            the new value of the '{@link FlowTask#getProjectStatus() projectStatus}' feature.
     * @generated
     */
    public void setProjectStatus(String newProjectStatus) {
        projectStatus = newProjectStatus;
    }

    /**
     * Returns the value of '<em><b>milestone</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>milestone</b></em>' feature
     * @generated
     */
    public String getMilestone() {
        return milestone;
    }

    /**
     * Sets the '{@link FlowTask#getMilestone() <em>milestone</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newMilestone
     *            the new value of the '{@link FlowTask#getMilestone() milestone}' feature.
     * @generated
     */
    public void setMilestone(String newMilestone) {
        milestone = newMilestone;
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
     * Sets the '{@link FlowTask#getFlowStatus() <em>flowStatus</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFlowStatus
     *            the new value of the '{@link FlowTask#getFlowStatus() flowStatus}' feature.
     * @generated
     */
    public void setFlowStatus(String newFlowStatus) {
        flowStatus = newFlowStatus;
    }

    /**
     * Returns the value of '<em><b>risk</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>risk</b></em>' feature
     * @generated
     */
    public String getRisk() {
        return risk;
    }

    /**
     * Sets the '{@link FlowTask#getRisk() <em>risk</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRisk
     *            the new value of the '{@link FlowTask#getRisk() risk}' feature.
     * @generated
     */
    public void setRisk(String newRisk) {
        risk = newRisk;
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
     * Sets the '{@link FlowTask#getCellId() <em>cellId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newCellId
     *            the new value of the '{@link FlowTask#getCellId() cellId}' feature.
     * @generated
     */
    public void setCellId(String newCellId) {
        cellId = newCellId;
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
     * Sets the '{@link FlowTask#getFromTemplate() <em>fromTemplate</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFromTemplate
     *            the new value of the '{@link FlowTask#getFromTemplate() fromTemplate}' feature.
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
     * Sets the '{@link FlowTask#getFlowResolveXml() <em>flowResolveXml</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFlowResolveXml
     *            the new value of the '{@link FlowTask#getFlowResolveXml() flowResolveXml}'
     *            feature.
     * @generated
     */
    public void setFlowResolveXml(String newFlowResolveXml) {
        flowResolveXml = newFlowResolveXml;
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
     * Sets the '{@link FlowTask#getRequired() <em>required</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRequired
     *            the new value of the '{@link FlowTask#getRequired() required}' feature.
     * @generated
     */
    public void setRequired(String newRequired) {
        required = newRequired;
    }

    /**
     * Returns the value of '<em><b>bizCurrent</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>bizCurrent</b></em>' feature
     * @generated
     */
    public String getBizCurrent() {
        return bizCurrent;
    }

    /**
     * Sets the '{@link FlowTask#getBizCurrent() <em>bizCurrent</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newBizCurrent
     *            the new value of the '{@link FlowTask#getBizCurrent() bizCurrent}' feature.
     * @generated
     */
    public void setBizCurrent(String newBizCurrent) {
        bizCurrent = newBizCurrent;
    }

    /**
     * Returns the value of '<em><b>planType</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planType</b></em>' feature
     * @generated
     */
    public String getPlanType() {
        return planType;
    }

    /**
     * Sets the '{@link FlowTask#getPlanType() <em>planType</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanType
     *            the new value of the '{@link FlowTask#getPlanType() planType}' feature.
     * @generated
     */
    public void setPlanType(String newPlanType) {
        planType = newPlanType;
    }

    /**
     * Returns the value of '<em><b>opContent</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>opContent</b></em>' feature
     * @generated
     */
    public String getOpContent() {
        return opContent;
    }

    /**
     * Sets the '{@link FlowTask#getOpContent() <em>opContent</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOpContent
     *            the new value of the '{@link FlowTask#getOpContent() opContent}' feature.
     * @generated
     */
    public void setOpContent(String newOpContent) {
        opContent = newOpContent;
    }

    /**
     * Returns the value of '<em><b>planSource</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planSource</b></em>' feature
     * @generated
     */
    public String getPlanSource() {
        return planSource;
    }

    /**
     * Sets the '{@link FlowTask#getPlanSource() <em>planSource</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanSource
     *            the new value of the '{@link FlowTask#getPlanSource() planSource}' feature.
     * @generated
     */
    public void setPlanSource(String newPlanSource) {
        planSource = newPlanSource;
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
     * Sets the '{@link FlowTask#getFeedbackRateBefore() <em>feedbackRateBefore</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFeedbackRateBefore
     *            the new value of the '{@link FlowTask#getFeedbackRateBefore() feedbackRateBefore}
     *            ' feature.
     * @generated
     */
    public void setFeedbackRateBefore(String newFeedbackRateBefore) {
        feedbackRateBefore = newFeedbackRateBefore;
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
     * Sets the '{@link FlowTask#getFeedbackProcInstId() <em>feedbackProcInstId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFeedbackProcInstId
     *            the new value of the '{@link FlowTask#getFeedbackProcInstId() feedbackProcInstId}
     *            ' feature.
     * @generated
     */
    public void setFeedbackProcInstId(String newFeedbackProcInstId) {
        feedbackProcInstId = newFeedbackProcInstId;
    }

    /**
     * Returns the value of '<em><b>formId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>formId</b></em>' feature
     * @generated
     */
    public String getFormId() {
        return formId;
    }

    /**
     * Sets the '{@link FlowTask#getFormId() <em>formId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFormId
     *            the new value of the '{@link FlowTask#getFormId() formId}' feature.
     * @generated
     */
    public void setFormId(String newFormId) {
        formId = newFormId;
    }

    public String getParentPlanName() {
        return parentPlanName;
    }

    public void setParentPlanName(String parentPlanName) {
        this.parentPlanName = parentPlanName;
    }

    public TSUserDto getOwnerInfo() {
        return ownerInfo;
    }

    public void setOwnerInfo(TSUserDto ownerInfo) {
        this.ownerInfo = ownerInfo;
    }

    public String getOwnerRealName() {
        return ownerRealName;
    }

    public void setOwnerRealName(String ownerRealName) {
        this.ownerRealName = ownerRealName;
    }

    public String getOwnerDept() {
        return ownerDept;
    }

    public void setOwnerDept(String ownerDept) {
        this.ownerDept = ownerDept;
    }

    public TSUserDto getCreator() {
        return creator;
    }

    public void setCreator(TSUserDto creator) {
        this.creator = creator;
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

    public String getPlanLevelName() {
        return planLevelName;
    }

    public void setPlanLevelName(String planLevelName) {
        this.planLevelName = planLevelName;
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

    public TSUserDto getLauncherInfo() {
        return launcherInfo;
    }

    public void setLauncherInfo(TSUserDto launcherInfo) {
        this.launcherInfo = launcherInfo;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
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

    public String getOutPreposeIds() {
        return outPreposeIds;
    }

    public void setOutPreposeIds(String outPreposeIds) {
        this.outPreposeIds = outPreposeIds;
    }

    public String getOutPreposePlans() {
        return outPreposePlans;
    }

    public void setOutPreposePlans(String outPreposePlans) {
        this.outPreposePlans = outPreposePlans;
    }

    public String get_parentId() {
        return _parentId;
    }

    public void set_parentId(String _parentId) {
        this._parentId = _parentId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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

    public List<FlowTaskInputsDto> getInputList() {
        return inputList;
    }

    public void setInputList(List<FlowTaskInputsDto> inputList) {
        this.inputList = inputList;
    }

    public List<FlowTaskDeliverablesInfoDto> getOutputList() {
        return outputList;
    }

    public void setOutputList(List<FlowTaskDeliverablesInfoDto> outputList) {
        this.outputList = outputList;
    }

    public List<FlowTaskPreposeDto> getFlowTaskPreposeList() {
        return flowTaskPreposeList;
    }

    public void setFlowTaskPreposeList(List<FlowTaskPreposeDto> flowTaskPreposeList) {
        this.flowTaskPreposeList = flowTaskPreposeList;
    }

    public List<FlowTaskResourceLinkInfoDto> getResourceLinkList() {
        return resourceLinkList;
    }

    public void setResourceLinkList(List<FlowTaskResourceLinkInfoDto> resourceLinkList) {
        this.resourceLinkList = resourceLinkList;
    }
    
    public String getNameStandardId() {
        return nameStandardId;
    }

    public void setNameStandardId(String nameStandardId) {
        this.nameStandardId = nameStandardId;
    }

    public RDTaskDto getParentRDTask() {
        return parentRDTask;
    }

    public void setParentRDTask(RDTaskDto parentRDTask) {
        this.parentRDTask = parentRDTask;
    }

    public String getWorkTimeType() {
        return workTimeType;
    }

    public void setWorkTimeType(String workTimeType) {
        this.workTimeType = workTimeType;
    }

    public String getSubmitFlag() {
        return submitFlag;
    }

    public void setSubmitFlag(String submitFlag) {
        this.submitFlag = submitFlag;
    }

    public String getIsChangeSingleBack() {
        return isChangeSingleBack;
    }

    public void setIsChangeSingleBack(String isChangeSingleBack) {
        this.isChangeSingleBack = isChangeSingleBack;
    }

    public BusinessConfigDto getChangeTypeInfo() {
        return changeTypeInfo;
    }

    public void setChangeTypeInfo(BusinessConfigDto changeTypeInfo) {
        this.changeTypeInfo = changeTypeInfo;
    }

    public BusinessConfigDto getPlanLevelInfo() {
        return planLevelInfo;
    }

    public void setPlanLevelInfo(BusinessConfigDto planLevelInfo) {
        this.planLevelInfo = planLevelInfo;
    }

    public String getTabCbTemplateId() {
        return tabCbTemplateId;
    }

    public void setTabCbTemplateId(String tabCbTemplateId) {
        this.tabCbTemplateId = tabCbTemplateId;
    }
}
