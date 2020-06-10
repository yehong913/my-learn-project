package com.glaway.ids.project.planview.dto;

import com.glaway.foundation.common.entity.GLObject;
import com.glaway.foundation.common.vdata.GLVData;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 视图设置条件
 * @author likaiyong
 * @version 2018年5月30日
 */
public class PlanViewSetConditionDto extends GLVData implements Serializable {
    
    /**
     * 视图信息ID
     */
    private String planViewInfoId = null;
    
    /**
     * 部门ID
     */
    private String departmentId = null;
    
    
    /**
     * 计划Id
     */
    private String planId = null;
    
    /**
     * 时间范围
     */
    private String timeRange = null;
    
    /**
     * 展示范围
     */
    private String showRange = null;

    public String getPlanViewInfoId() {
        return planViewInfoId;
    }

    public void setPlanViewInfoId(String planViewInfoId) {
        this.planViewInfoId = planViewInfoId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public String getShowRange() {
        return showRange;
    }

    public void setShowRange(String showRange) {
        this.showRange = showRange;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Override
    public String toString() {
        return "PlanViewSetCondition [planViewInfoId=" + planViewInfoId + ", departmentId="
               + departmentId + ", planId=" + planId + ", timeRange=" + timeRange + ", showRange="
               + showRange + "]";
    }

    
}
