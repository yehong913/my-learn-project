package com.glaway.ids.project.plan.vo;



/**
 * 计划Excel导入:用来保存Excel导入数据
 * @author lky
 * @version 2018年6月21日
 *
 */
public class PlanExcelSaveVo
{
    /* 序号  */
    private String planNumber = null;
    
    /* 父计划序号  */
    private String parentPlanNo = null;
    
    /* 计划名称  */
    private String planName = null;
    
    /* 负责人名称  */
    private String ownerRealName = null;
    
    /* 计划等级名称  */
    private String planLevelName = null;
    
    /* 工期  */
    private String worktime = null;

    /* 里程碑  */
    private String milestoneName = null;
    
    /* 前置计划序号  */
    private String preposeNos = null;

    /*交付项名称*/
    private String deliverablesName = null;
      
    /* 开始时间  */
    private String planStartTime = null;
    
    /* 结束时间  */
    private String planEndTime = null;
    
    /* 备注 */
    private String remark = null;

    /* 计划类型 */
    private String taskNameType = null;

    public String getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber;
    }

    public String getParentPlanNo() {
        return parentPlanNo;
    }

    public void setParentPlanNo(String parentPlanNo) {
        this.parentPlanNo = parentPlanNo;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getOwnerRealName() {
        return ownerRealName;
    }

    public void setOwnerRealName(String ownerRealName) {
        this.ownerRealName = ownerRealName;
    }

    public String getPlanLevelName() {
        return planLevelName;
    }

    public void setPlanLevelName(String planLevelName) {
        this.planLevelName = planLevelName;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
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

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaskNameType() {
        return taskNameType;
    }

    public void setTaskNameType(String taskNameType) {
        this.taskNameType = taskNameType;
    }
}
