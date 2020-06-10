package com.glaway.ids.project.plan.vo;


import java.util.Date;

/**
 * 计划Excel导入:用来校验
 * @author lky
 * @version 2018年6月14日
 *
 */
public class PlanExcelVo
{
    
    private String id = null;
    
    /* 序号  */
    private int planNumber = 0;

    /* 开始时间  */
    private Date planStartTime = null;

    /* 结束时间  */
    private Date planEndTime = null;
    
    /* 前置计划序号  */
    private String preposeNos = null;
    
    /* 父计划序号  */
    private String parentNo = null;

    /*交付项名称*/
    private String deliverablesName = null;
    
    /*输出节点ID*/
    private String deliverablesInfoId = null;
    
    /* excel行号  */
    private int rowNum = 0;

    /* 工期  */
    private int worktime = 0;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(int planNumber) {
        this.planNumber = planNumber;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getPreposeNos() {
        return preposeNos;
    }

    public void setPreposeNos(String preposeNos) {
        this.preposeNos = preposeNos;
    }

    public String getParentNo() {
        return parentNo;
    }

    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }

    public String getDeliverablesName() {
        return deliverablesName;
    }

    public void setDeliverablesName(String deliverablesName) {
        this.deliverablesName = deliverablesName;
    }
    
    public String getDeliverablesInfoId() {
        return deliverablesInfoId;
    }

    public void setDeliverablesInfoId(String deliverablesInfoId) {
        this.deliverablesInfoId = deliverablesInfoId;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getWorktime() {
        return worktime;
    }

    public void setWorktime(int worktime) {
        this.worktime = worktime;
    }

    @Override
    public String toString() {
        return "PlanExcelVo [id=" + id + ", planNumber=" + planNumber
                + ", planStartTime=" + planStartTime + ", planEndTime="
                + planEndTime + ", preposeNos=" + preposeNos + ", parentNos="
                + parentNo + ", deliverablesName=" + deliverablesName
                + ", originDeliverablesInfoId=" + deliverablesInfoId
                + "]";
    }

}
