package com.glaway.ids.project.planview.dto;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.glaway.foundation.common.entity.GLObject;
import com.glaway.foundation.common.vdata.GLVData;

/**
 * 计划视图展示列信息
 * @author likaiyong
 * @version 2018年6月13日
 */
public class PlanViewColumnInfoDto extends GLVData {

    /**
     * 视图信息ID
     */
    @Basic()
    private String planViewInfoId = null;
    
    /**
     * 展示列ID
     */
    @Basic()
    private String columnId = null;

    public String getPlanViewInfoId() {
        return planViewInfoId;
    }


    public void setPlanViewInfoId(String planViewInfoId) {
        this.planViewInfoId = planViewInfoId;
    }


    public String getColumnId() {
        return columnId;
    }


    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }
    
    @Override
    public String toString() {
        return "PlanViewColumnInfo [planViewInfoId=" + planViewInfoId
                + ", columnId=" + columnId + "]";
    }
}
