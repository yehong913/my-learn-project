package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vdata.GLVData;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A representation of the model object '<em><b>PlanHistory</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class PlanHistoryDto extends GLVData {

    /**
     * Plan对象ID
     * 
     * @generated
     */
    private String planId = null;

    /**
     * Plan对象ID
     * 
     * @generated
     */
    private String formId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String bizId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String bizVersion = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String bizCurrent = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private Short securityLevel = new Short((short)0);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String avaliable = "1";

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planCreateBy = null;

    /**
     * 计划创建者信息
     */

    private TSUserDto creator;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date planCreateTime = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planUpdateBy = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date planUpdateTime = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
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
    private String projectId = null;

    /**
     * 项目信息
     */
    private Project project;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String owner = null;

    /**
     * 负责人信息
     */

    private TSUserDto ownerInfo;

    /**
     * 责任部门
     */
    private String ownerDept = null;

    /**
     * 负责人名称
     */
    private String ownerRealName = null;

    /**
     * 应用场景
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

    private TSUserDto assignerInfo;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date assignTime = null;

    /**
     * 
     */
    private Date assignTimeStart = null;

    /**
     * 
     */
    private Date assignTimeEnd = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planLevel = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    private BusinessConfigDto planLevelInfo;

    /**
     * 
     */
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
    private Date planStartTime = null;

    /**
     * 
     */
    private Date planStartTimeStart = null;

    /**
     * 
     */
    private Date planStartTimeEnd = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date planEndTime = null;

    /**
     * 
     */
    private Date planEndTimeStart = null;

    /**
     * 
     */
    private Date planEndTimeEnd = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String workTime = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date actualStartTime = null;

    /**
     * 
     */
    private Date actualStartTimeStart = null;

    /**
     * 
     */
    private Date actualStartTimeEnd = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date actualEndTime = null;

    /**
     * 
     */
    private Date actualEndTimeStart = null;

    /**
     * 
     */
    private Date actualEndTimeEnd = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String remark = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String status = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projectStatus = "";

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
    private String milestone = "false";

    /**
     * 
     */
    private String milestoneName = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String risk = null;

    /**
     * 
     */
    private String preposeIds = null;

    /**
     * 
     */
    private String preposePlans = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String documents = null;

    /**
     * 
     */
    private String parentStorey = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private Integer storeyNo = null;

    /**
     * 
     */
    private String beforePlanId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String feedbackProcInstId = null;

    /**
     * <!-- begin-user-doc -->
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
    private String planSource = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String opContent = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planType = null;

    /**
     * <!-- begin-user-doc -->变更类型
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String changeType = null;

    /**
     * <!-- begin-user-doc -->计划类别
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String taskType = null;

    /**
     * <!-- begin-user-doc -->计划类型
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String taskNameType = null;

    /**
     * 
     */
    private List<PlanDto> preposeList = new ArrayList<PlanDto>();

    /**
     * 
     */
    private List<ResourceLinkInfoDto> rescLinkInfoList = new ArrayList<ResourceLinkInfoDto>();

    /**
     * 
     */
    private List<DeliverablesInfoDto> deliInfoList = new ArrayList<DeliverablesInfoDto>();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date invalidTime = null;

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
     * Sets the '{@link PlanHistoryDto#getPlanId() <em>planId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanId
     *            the new value of the '{@link PlanHistoryDto#getPlanId() planId}' feature.
     * @generated
     */
    public void setPlanId(String newPlanId) {
        planId = newPlanId;
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
     * Sets the '{@link PlanHistoryDto#getFormId() <em>formId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFormId
     *            the new value of the '{@link PlanHistoryDto#getFormId() formId}' feature.
     * @generated
     */
    public void setFormId(String newFormId) {
        formId = newFormId;
    }

    /**
     * Returns the value of '<em><b>bizId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>bizId</b></em>' feature
     * @generated
     */
    public String getBizId() {
        return bizId;
    }

    /**
     * Sets the '{@link PlanHistoryDto#getBizId() <em>bizId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newBizId
     *            the new value of the '{@link PlanHistoryDto#getBizId() bizId}' feature.
     * @generated
     */
    public void setBizId(String newBizId) {
        bizId = newBizId;
    }

    /**
     * Returns the value of '<em><b>bizVersion</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>bizVersion</b></em>' feature
     * @generated
     */
    public String getBizVersion() {
        return bizVersion;
    }

    /**
     * Sets the '{@link PlanHistoryDto#getBizVersion() <em>bizVersion</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newBizVersion
     *            the new value of the '{@link PlanHistoryDto#getBizVersion() bizVersion}' feature.
     * @generated
     */
    public void setBizVersion(String newBizVersion) {
        bizVersion = newBizVersion;
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
     * Sets the '{@link PlanHistoryDto#getBizCurrent() <em>bizCurrent</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newBizCurrent
     *            the new value of the '{@link PlanHistoryDto#getBizCurrent() bizCurrent}' feature.
     * @generated
     */
    public void setBizCurrent(String newBizCurrent) {
        bizCurrent = newBizCurrent;
    }

    public Short getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(Short securityLevel) {
        this.securityLevel = securityLevel;
    }

    /**
     * Returns the value of '<em><b>avaliable</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>avaliable</b></em>' feature
     * @generated
     */
    public String getAvaliable() {
        return avaliable;
    }

    /**
     * Sets the '{@link PlanHistoryDto#getAvaliable() <em>avaliable</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAvaliable
     *            the new value of the '{@link PlanHistoryDto#getAvaliable() avaliable}' feature.
     * @generated
     */
    public void setAvaliable(String newAvaliable) {
        avaliable = newAvaliable;
    }

    /**
     * Returns the value of '<em><b>planCreateBy</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planCreateBy</b></em>' feature
     * @generated
     */
    public String getPlanCreateBy() {
        return planCreateBy;
    }

    /**
     * Sets the '{@link PlanHistoryDto#getPlanCreateBy() <em>planCreateBy</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanCreateBy
     *            the new value of the '{@link PlanHistoryDto#getPlanCreateBy() planCreateBy}'
     *            feature.
     * @generated
     */
    public void setPlanCreateBy(String newPlanCreateBy) {
        planCreateBy = newPlanCreateBy;
    }

    /**
     * Returns the value of '<em><b>planCreateTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planCreateTime</b></em>' feature
     * @generated
     */
    public Date getPlanCreateTime() {
        return planCreateTime;
    }

    /**
     * Sets the '{@link PlanHistoryDto#getPlanCreateTime() <em>planCreateTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanCreateTime
     *            the new value of the '{@link PlanHistoryDto#getPlanCreateTime() planCreateTime}'
     *            feature.
     * @generated
     */
    public void setPlanCreateTime(Date newPlanCreateTime) {
        planCreateTime = newPlanCreateTime;
    }

    /**
     * Returns the value of '<em><b>planUpdateBy</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planUpdateBy</b></em>' feature
     * @generated
     */
    public String getPlanUpdateBy() {
        return planUpdateBy;
    }

    /**
     * Sets the '{@link PlanHistoryDto#getPlanUpdateBy() <em>planUpdateBy</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanUpdateBy
     *            the new value of the '{@link PlanHistoryDto#getPlanUpdateBy() planUpdateBy}'
     *            feature.
     * @generated
     */
    public void setPlanUpdateBy(String newPlanUpdateBy) {
        planUpdateBy = newPlanUpdateBy;
    }

    /**
     * Returns the value of '<em><b>planUpdateTime</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planUpdateTime</b></em>' feature
     * @generated
     */
    public Date getPlanUpdateTime() {
        return planUpdateTime;
    }

    /**
     * Sets the '{@link PlanHistoryDto#getPlanUpdateTime() <em>planUpdateTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanUpdateTime
     *            the new value of the '{@link PlanHistoryDto#getPlanUpdateTime() planUpdateTime}'
     *            feature.
     * @generated
     */
    public void setPlanUpdateTime(Date newPlanUpdateTime) {
        planUpdateTime = newPlanUpdateTime;
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
     * Sets the '{@link PlanHistoryDto#getPlanNumber() <em>planNumber</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanNumber
     *            the new value of the '{@link PlanHistoryDto#getPlanNumber() planNumber}' feature.
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
     * Sets the '{@link PlanHistoryDto#getPlanOrder() <em>planOrder</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanOrder
     *            the new value of the '{@link PlanHistoryDto#getPlanOrder() planOrder}' feature.
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
     * Sets the '{@link PlanHistoryDto#getPlanName() <em>planName</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanName
     *            the new value of the '{@link PlanHistoryDto#getPlanName() planName}' feature.
     * @generated
     */
    public void setPlanName(String newPlanName) {
        planName = newPlanName;
    }

    /**
     * Returns the value of '<em><b>projectId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>projectId</b></em>' feature
     * @generated
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Sets the '{@link PlanHistoryDto#getProjectId() <em>projectId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjectId
     *            the new value of the '{@link PlanHistoryDto#getProjectId() projectId}' feature.
     * @generated
     */
    public void setProjectId(String newProjectId) {
        projectId = newProjectId;
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
     * Sets the '{@link PlanHistoryDto#getParentPlanId() <em>parentPlanId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newParentPlanId
     *            the new value of the '{@link PlanHistoryDto#getParentPlanId() parentPlanId}'
     *            feature.
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
     * Sets the '{@link PlanHistoryDto#getOwner() <em>owner</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOwner
     *            the new value of the '{@link PlanHistoryDto#getOwner() owner}' feature.
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
     * Sets the '{@link PlanHistoryDto#getAssigner() <em>assigner</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAssigner
     *            the new value of the '{@link PlanHistoryDto#getAssigner() assigner}' feature.
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
     * Sets the '{@link PlanHistoryDto#getAssignTime() <em>assignTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAssignTime
     *            the new value of the '{@link PlanHistoryDto#getAssignTime() assignTime}' feature.
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
     * Sets the '{@link PlanHistoryDto#getPlanLevel() <em>planLevel</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanLevel
     *            the new value of the '{@link PlanHistoryDto#getPlanLevel() planLevel}' feature.
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
     * Sets the '{@link PlanHistoryDto#getProgressRate() <em>progressRate</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProgressRate
     *            the new value of the '{@link PlanHistoryDto#getProgressRate() progressRate}'
     *            feature.
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
     * Sets the '{@link PlanHistoryDto#getPlanStartTime() <em>planStartTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanStartTime
     *            the new value of the '{@link PlanHistoryDto#getPlanStartTime() planStartTime}'
     *            feature.
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
     * Sets the '{@link PlanHistoryDto#getPlanEndTime() <em>planEndTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanEndTime
     *            the new value of the '{@link PlanHistoryDto#getPlanEndTime() planEndTime}' feature.
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
     * Sets the '{@link PlanHistoryDto#getWorkTime() <em>workTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newWorkTime
     *            the new value of the '{@link PlanHistoryDto#getWorkTime() workTime}' feature.
     * @generated
     */
    public void setWorkTime(String newWorkTime) {
        workTime = newWorkTime;
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
     * Sets the '{@link PlanHistoryDto#getActualStartTime() <em>actualStartTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newActualStartTime
     *            the new value of the '{@link PlanHistoryDto#getActualStartTime() actualStartTime}'
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
     * Sets the '{@link PlanHistoryDto#getActualEndTime() <em>actualEndTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newActualEndTime
     *            the new value of the '{@link PlanHistoryDto#getActualEndTime() actualEndTime}'
     *            feature.
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
     * Sets the '{@link PlanHistoryDto#getRemark() <em>remark</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRemark
     *            the new value of the '{@link PlanHistoryDto#getRemark() remark}' feature.
     * @generated
     */
    public void setRemark(String newRemark) {
        remark = newRemark;
    }

    /**
     * Returns the value of '<em><b>status</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>status</b></em>' feature
     * @generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the '{@link PlanHistoryDto#getStatus() <em>status</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStatus
     *            the new value of the '{@link PlanHistoryDto#getStatus() status}' feature.
     * @generated
     */
    public void setStatus(String newStatus) {
        status = newStatus;
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
     * Sets the '{@link PlanHistoryDto#getMilestone() <em>milestone</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newMilestone
     *            the new value of the '{@link PlanHistoryDto#getMilestone() milestone}' feature.
     * @generated
     */
    public void setMilestone(String newMilestone) {
        milestone = newMilestone;
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
     * Sets the '{@link PlanHistoryDto#getRisk() <em>risk</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRisk
     *            the new value of the '{@link PlanHistoryDto#getRisk() risk}' feature.
     * @generated
     */
    public void setRisk(String newRisk) {
        risk = newRisk;
    }

    /**
     * Returns the value of '<em><b>documents</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>documents</b></em>' feature
     * @generated
     */
    public String getDocuments() {
        return documents;
    }

    /**
     * Sets the '{@link PlanHistoryDto#getDocuments() <em>documents</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newDocuments
     *            the new value of the '{@link PlanHistoryDto#getDocuments() documents}' feature.
     * @generated
     */
    public void setDocuments(String newDocuments) {
        documents = newDocuments;
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
     * Sets the '{@link PlanHistoryDto#getStoreyNo() <em>storeyNo</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStoreyNo
     *            the new value of the '{@link PlanHistoryDto#getStoreyNo() storeyNo}' feature.
     * @generated
     */
    public void setStoreyNo(Integer newStoreyNo) {
        storeyNo = newStoreyNo;
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
     * Sets the '{@link PlanHistoryDto#getProjectStatus() <em>projectStatus</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjectStatus
     *            the new value of the '{@link PlanHistoryDto#getProjectStatus() projectStatus}'
     *            feature.
     * @generated
     */
    public void setProjectStatus(String newProjectStatus) {
        projectStatus = newProjectStatus;
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
     * Sets the '{@link PlanHistoryDto#getFlowStatus() <em>flowStatus</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFlowStatus
     *            the new value of the '{@link PlanHistoryDto#getFlowStatus() flowStatus}' feature.
     * @generated
     */
    public void setFlowStatus(String newFlowStatus) {
        flowStatus = newFlowStatus;
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
     * Sets the '{@link PlanHistoryDto#getFeedbackProcInstId() <em>feedbackProcInstId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFeedbackProcInstId
     *            the new value of the '{@link PlanHistoryDto#getFeedbackProcInstId()
     *            feedbackProcInstId}' feature.
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
     * Sets the '{@link PlanHistoryDto#getFeedbackRateBefore() <em>feedbackRateBefore</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFeedbackRateBefore
     *            the new value of the '{@link PlanHistoryDto#getFeedbackRateBefore()
     *            feedbackRateBefore}' feature.
     * @generated
     */
    public void setFeedbackRateBefore(String newFeedbackRateBefore) {
        feedbackRateBefore = newFeedbackRateBefore;
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
     * Sets the '{@link PlanHistoryDto#getPlanSource() <em>planSource</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanSource
     *            the new value of the '{@link PlanHistoryDto#getPlanSource() planSource}' feature.
     * @generated
     */
    public void setPlanSource(String newPlanSource) {
        planSource = newPlanSource;
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
     * Sets the '{@link PlanHistoryDto#getOpContent() <em>opContent</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOpContent
     *            the new value of the '{@link PlanHistoryDto#getOpContent() opContent}' feature.
     * @generated
     */
    public void setOpContent(String newOpContent) {
        opContent = newOpContent;
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
     * Sets the '{@link PlanHistoryDto#getPlanType() <em>planType</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanType
     *            the new value of the '{@link PlanHistoryDto#getPlanType() planType}' feature.
     * @generated
     */
    public void setPlanType(String newPlanType) {
        planType = newPlanType;
    }

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
     * Sets the '{@link PlanHistoryDto#getChangeType() <em>changeType</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newChangeType
     *            the new value of the '{@link PlanHistoryDto#getChangeType() changeType}' feature.
     * @generated
     */
    public void setChangeType(String newChangeType) {
        changeType = newChangeType;
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
     * Sets the '{@link PlanHistoryDto#getTaskType() <em>taskType</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newTaskType
     *            the new value of the '{@link PlanHistoryDto#getTaskType() taskType}' feature.
     * @generated
     */
    public void setTaskType(String newTaskType) {
        taskType = newTaskType;
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
     * Sets the '{@link PlanHistoryDto#getTaskNameType() <em>taskNameType</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newTaskNameType
     *            the new value of the '{@link PlanHistoryDto#getTaskNameType() taskNameType}'
     *            feature.
     * @generated
     */
    public void setTaskNameType(String newTaskNameType) {
        taskNameType = newTaskNameType;
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
     * Sets the '{@link PlanHistoryDto#getInvalidTime() <em>invalidTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newInvalidTime
     *            the new value of the '{@link PlanHistoryDto#getInvalidTime() invalidTime}' feature.
     * @generated
     */
    public void setInvalidTime(Date newInvalidTime) {
        invalidTime = newInvalidTime;
    }

    public TSUserDto getCreator() {
        return creator;
    }

    public void setCreator(TSUserDto creator) {
        this.creator = creator;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PlanDto getParentPlan() {
        return parentPlan;
    }

    public void setParentPlan(PlanDto parentPlan) {
        this.parentPlan = parentPlan;
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

    public BusinessConfigDto getPlanLevelInfo() {
        return planLevelInfo;
    }

    public void setPlanLevelInfo(BusinessConfigDto planLevelInfo) {
        this.planLevelInfo = planLevelInfo;
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

    public List<PlanDto> getPreposeList() {
        return preposeList;
    }

    public void setPreposeList(List<PlanDto> preposeList) {
        this.preposeList = preposeList;
    }

    public List<ResourceLinkInfoDto> getRescLinkInfoList() {
        return rescLinkInfoList;
    }

    public void setRescLinkInfoList(List<ResourceLinkInfoDto> rescLinkInfoList) {
        this.rescLinkInfoList = rescLinkInfoList;
    }

    public List<DeliverablesInfoDto> getDeliInfoList() {
        return deliInfoList;
    }

    public void setDeliInfoList(List<DeliverablesInfoDto> deliInfoList) {
        this.deliInfoList = deliInfoList;
    }

    /**
     * A toString method which prints the values of all EAttributes of this instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "PlanHistory " + " [planId: " + getPlanId() + "]" + " [formId: " + getFormId()
               + "]" + " [bizId: " + getBizId() + "]" + " [bizVersion: " + getBizVersion() + "]"
               + " [bizCurrent: " + getBizCurrent() + "]" + " [securityLevel: "
               + getSecurityLevel() + "]" + " [avaliable: " + getAvaliable() + "]"
               + " [planCreateBy: " + getPlanCreateBy() + "]" + " [planCreateTime: "
               + getPlanCreateTime() + "]" + " [planUpdateBy: " + getPlanUpdateBy() + "]"
               + " [planUpdateTime: " + getPlanUpdateTime() + "]" + " [planNumber: "
               + getPlanNumber() + "]" + " [planOrder: " + getPlanOrder() + "]" + " [planName: "
               + getPlanName() + "]" + " [projectId: " + getProjectId() + "]" + " [parentPlanId: "
               + getParentPlanId() + "]" + " [owner: " + getOwner() + "]" + " [assigner: "
               + getAssigner() + "]" + " [assignTime: " + getAssignTime() + "]" + " [planLevel: "
               + getPlanLevel() + "]" + " [progressRate: " + getProgressRate() + "]"
               + " [planStartTime: " + getPlanStartTime() + "]" + " [planEndTime: "
               + getPlanEndTime() + "]" + " [workTime: " + getWorkTime() + "]"
               + " [actualStartTime: " + getActualStartTime() + "]" + " [actualEndTime: "
               + getActualEndTime() + "]" + " [remark: " + getRemark() + "]" + " [status: "
               + getStatus() + "]" + " [milestone: " + getMilestone() + "]" + " [risk: "
               + getRisk() + "]" + " [documents: " + getDocuments() + "]" + " [storeyNo: "
               + getStoreyNo() + "]" + " [projectStatus: " + getProjectStatus() + "]"
               + " [flowStatus: " + getFlowStatus() + "]" + " [feedbackProcInstId: "
               + getFeedbackProcInstId() + "]" + " [feedbackRateBefore: "
               + getFeedbackRateBefore() + "]" + " [planSource: " + getPlanSource() + "]"
               + " [opContent: " + getOpContent() + "]" + " [planType: " + getPlanType() + "]"
               + " [changeType: " + getChangeType() + "]" + " [taskType: " + getTaskType() + "]"
               + " [taskNameType: " + getTaskNameType() + "]" + " [invalidTime: "
               + getInvalidTime() + "]";
    }
}
