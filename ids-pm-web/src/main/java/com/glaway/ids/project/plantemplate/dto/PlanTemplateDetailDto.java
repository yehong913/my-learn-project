package com.glaway.ids.project.plantemplate.dto;


import com.glaway.foundation.common.vdata.GLVData;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A representation of the model object '<em><b>PlanTemplateDetail</b></em>'.
 * <!-- begin-user-doc -->
 * 计划模版细节表
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class PlanTemplateDetailDto extends GLVData {

    /**
     * <!-- begin-user-doc -->
     * 计划名称
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String name = null;

    /**
     * <!-- begin-user-doc -->
     * 父计划ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String parentPlanId = null;

    /**
     * <!-- begin-user-doc -->
     * 计划等级
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planLevel = null;

    private BusinessConfigDto planLevelInfo = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private int num = 0;

    /**
     * <!-- begin-user-doc -->
     * 工期
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String workTime = null;

    /**
     * <!-- begin-user-doc -->
     * 备注
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String remark = null;

    /**
     * <!-- begin-user-doc -->
     * 计划模版ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String planTemplateId = null;
    
    /**
     * <!-- begin-user-doc -->
     * 项目模版ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projectTemplateId = null;

    private PlanTemplateDto planTemplateInfo = null;

    /**
     * <!-- begin-user-doc -->
     * 里程碑
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String milestone = null;

    /**
     * <!-- begin-user-doc -->
     * 计划序号
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private int planNumber = 0;
    
    /**
     * 是否必要
     */
    private String isNecessary = null;

    /**
     * 文档
     */
    private String documents = null;

    /**
     * <!-- begin-user-doc -->
     * 子计划的开始时间,临时保存
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date planStartTime = null;
    
    /**
     * 输入
     */
    private List<InputsDto> inputsList= new ArrayList<InputsDto>();
    
    
    /**
     * 输出
     */
    private List<DeliverablesInfoDto> deliverablesInfo = new ArrayList<DeliverablesInfoDto>();
    
    /**
     * 前置计划id
     */
    private String preposeIds = null;
    
    /**
     * 前置计划名称
     */
    private String preposeNames = null;
    
    /**
     * 计划等级名称
     */
    private String planLevelName = null;
    
    /**
     * 里程碑显示
     */
    private String mileStoneShow = null;
    
    /**
     * 来源<br>
     */
    private String origin = null;
    
    /**
     * 输入项名称<br>
     */
    private String inputsName;
    
    /**
     * 排序
     */
    private Integer storeyNo;

    /**
     * <!-- begin-user-doc --> 计划类型：研发类、评审类、风险类等 <!-- end-user-doc -->
     *
     * @generated
     */
    private String taskNameType;

    /**
     * 绑定的页签组合模板id
     */
    private String tabCbTemplateId;

    /**
     * 计划类别：WBS计划、任务计划、流程计划
     */
    private String taskType;

    /**
     * Returns the value of '<em><b>name</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>name</b></em>' feature
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the '{@link PlanTemplateDetailDto#getName() <em>name</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newName
     *            the new value of the '{@link PlanTemplateDetailDto#getName() name}' feature.
     * @generated
     */
    public void setName(String newName) {
        name = newName;
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
     * Sets the '{@link PlanTemplateDetailDto#getParentPlanId() <em>parentPlanId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newParentPlanId
     *            the new value of the '{@link PlanTemplateDetailDto#getParentPlanId() parentPlanId}'
     *            feature.
     * @generated
     */
    public void setParentPlanId(String newParentPlanId) {
        parentPlanId = newParentPlanId;
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
     * Sets the '{@link PlanTemplateDetailDto#getPlanLevel() <em>planLevel</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanLevel
     *            the new value of the '{@link PlanTemplateDetailDto#getPlanLevel() planLevel}'
     *            feature.
     * @generated
     */
    public void setPlanLevel(String newPlanLevel) {
        planLevel = newPlanLevel;
    }

    /**
     * Returns the value of '<em><b>num</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>num</b></em>' feature
     * @generated
     */
    public int getNum() {
        return num;
    }

    /**
     * Sets the '{@link PlanTemplateDetailDto#getNum() <em>num</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newNum
     *            the new value of the '{@link PlanTemplateDetailDto#getNum() num}' feature.
     * @generated
     */
    public void setNum(int newNum) {
        num = newNum;
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
     * Sets the '{@link PlanTemplateDetailDto#getWorkTime() <em>workTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newWorkTime
     *            the new value of the '{@link PlanTemplateDetailDto#getWorkTime() workTime}' feature.
     * @generated
     */
    public void setWorkTime(String newWorkTime) {
        workTime = newWorkTime;
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
     * Sets the '{@link PlanTemplateDetailDto#getRemark() <em>remark</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRemark
     *            the new value of the '{@link PlanTemplateDetailDto#getRemark() remark}' feature.
     * @generated
     */
    public void setRemark(String newRemark) {
        remark = newRemark;
    }

    /**
     * Returns the value of '<em><b>planTemplateId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>planTemplateId</b></em>' feature
     * @generated
     */
    public String getPlanTemplateId() {
        return planTemplateId;
    }

    /**
     * Sets the '{@link PlanTemplateDetailDto#getPlanTemplateId() <em>planTemplateId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanTemplateId
     *            the new value of the '{@link PlanTemplateDetailDto#getPlanTemplateId()
     *            planTemplateId}' feature.
     * @generated
     */
    public void setPlanTemplateId(String newPlanTemplateId) {
        planTemplateId = newPlanTemplateId;
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
     * Sets the '{@link PlanTemplateDetailDto#getMilestone() <em>milestone</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newMilestone
     *            the new value of the '{@link PlanTemplateDetailDto#getMilestone() milestone}'
     *            feature.
     * @generated
     */
    public void setMilestone(String newMilestone) {
        milestone = newMilestone;
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
     * Sets the '{@link PlanTemplateDetailDto#getPlanNumber() <em>planNumber</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanNumber
     *            the new value of the '{@link PlanTemplateDetailDto#getPlanNumber() planNumber}'
     *            feature.
     * @generated
     */
    public void setPlanNumber(int newPlanNumber) {
        planNumber = newPlanNumber;
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
     * Sets the '{@link PlanTemplateDetailDto#getPlanStartTime() <em>planStartTime</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newPlanStartTime
     *            the new value of the '{@link PlanTemplateDetailDto#getPlanStartTime() planStartTime}
     *            ' feature.
     * @generated
     */
    public void setPlanStartTime(Date newPlanStartTime) {
        planStartTime = newPlanStartTime;
    }

    public BusinessConfigDto getPlanLevelInfo() {
        return planLevelInfo;
    }

    public void setPlanLevelInfo(BusinessConfigDto planLevelInfo) {
        this.planLevelInfo = planLevelInfo;
    }

    public PlanTemplateDto getPlanTemplateInfo() {
        return planTemplateInfo;
    }

    public void setPlanTemplateInfo(PlanTemplateDto planTemplateInfo) {
        this.planTemplateInfo = planTemplateInfo;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }
    

    public List<InputsDto> getInputsList() {
        return inputsList;
    }

    public void setInputsList(List<InputsDto> inputsList) {
        this.inputsList = inputsList;
    }

    public List<DeliverablesInfoDto> getDeliverablesInfo() {
        return deliverablesInfo;
    }

    public void setDeliverablesInfo(List<DeliverablesInfoDto> deliverablesInfo) {
        this.deliverablesInfo = deliverablesInfo;
    }
    
    
    public String getIsNecessary() {
        return isNecessary;
    }

    public void setIsNecessary(String isNecessary) {
        this.isNecessary = isNecessary;
    }



    public String getPreposeIds() {
        return preposeIds;
    }

    public void setPreposeIds(String preposeIds) {
        this.preposeIds = preposeIds;
    }



    public String getPreposeNames() {
        return preposeNames;
    }

    public void setPreposeNames(String preposeNames) {
        this.preposeNames = preposeNames;
    }

    public String getPlanLevelName() {
        return planLevelName;
    }

    public void setPlanLevelName(String planLevelName) {
        this.planLevelName = planLevelName;
    }

    public String getMileStoneShow() {
        return mileStoneShow;
    }

    public void setMileStoneShow(String mileStoneShow) {
        this.mileStoneShow = mileStoneShow;
    }

    public Integer getStoreyNo() {
        return storeyNo;
    }

    public void setStoreyNo(Integer storeyNo) {
        this.storeyNo = storeyNo;
    }
    
    public String getProjectTemplateId() {
        return projectTemplateId;
    }

    public void setProjectTemplateId(String projectTemplateId) {
        this.projectTemplateId = projectTemplateId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getInputsName() {
        return inputsName;
    }

    public void setInputsName(String inputsName) {
        this.inputsName = inputsName;
    }

    public String getTaskNameType() {
        return taskNameType;
    }

    public void setTaskNameType(String taskNameType) {
        this.taskNameType = taskNameType;
    }

    public String getTabCbTemplateId() {
        return tabCbTemplateId;
    }

    public void setTabCbTemplateId(String tabCbTemplateId) {
        this.tabCbTemplateId = tabCbTemplateId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
