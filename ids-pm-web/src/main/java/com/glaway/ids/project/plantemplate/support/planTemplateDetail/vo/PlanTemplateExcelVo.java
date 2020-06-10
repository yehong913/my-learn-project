package com.glaway.ids.project.plantemplate.support.planTemplateDetail.vo;

/**
 * 计划模板Excel导入
 * @author lky
 * @version 2018年6月29日
 *
 */
public class PlanTemplateExcelVo
{
    /* 计划id */
    private String id = null;
    
    /* 序号  */
    private String planNumber = null;
  
    /* 父计划序号  */
    private String parentNo = null;
    
    /* 计划名称  */
    private String planName = null;

    /* 计划类型 */
    private String taskNameType = null;
    
    /* 计划等级  */
    private String planLevel = null;
    
    /* 参考工期：天 */
    private String worktime = null;
    
    /* 里程碑 */
    private String milestone = null;
    
    /* 是否必要 */
    private String isNecessary = null;

    /*前置计划序号*/
    private String preposeNos = null;

    /*交付项名称*/
    private String deliverablesName = null;
    
    /*前置计划名称*/
    private String preposeName = null;
    
    /* excel行号  */
    private int rowNum = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber;
    }

    public String getParentNo() {
        return parentNo;
    }

    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanLevel() {
        return planLevel;
    }

    public void setPlanLevel(String planLevel) {
        this.planLevel = planLevel;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public String getIsNecessary() {
        return isNecessary;
    }

    public void setIsNecessary(String isNecessary) {
        this.isNecessary = isNecessary;
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

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getTaskNameType() {
        return taskNameType;
    }

    public void setTaskNameType(String taskNameType) {
        this.taskNameType = taskNameType;
    }
}
