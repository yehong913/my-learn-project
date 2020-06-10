package com.glaway.ids.project.planview.dto;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.glaway.foundation.common.entity.GLObject;
import com.glaway.foundation.common.vdata.GLVData;

/**
 * 视图查询条件
 * @author likaiyong
 * @version 2018年5月30日
 */
public class PlanViewSearchConditionDto extends GLVData {
    
    /**
     * 视图信息ID
     */
    private String planViewInfoId = null;
    
    /**
     * 属性名称,值与计划表对象属性名称保持一致
     */
    private String attributeName = null;
    
    /**
     * 属性条件
     */
    private String attributeCondition = null;
    
    /**
     * 属性值 也许是一个范围，例如“开始时间”
     */
    private String attributeValue = null;

    public String getPlanViewInfoId() {
        return planViewInfoId;
    }

    public void setPlanViewInfoId(String planViewInfoId) {
        this.planViewInfoId = planViewInfoId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeCondition() {
        return attributeCondition;
    }

    public void setAttributeCondition(String attributeCondition) {
        this.attributeCondition = attributeCondition;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public String toString() {
        return "PlanViewSearchCondition [planViewInfoId=" + planViewInfoId
                + ", attributeName=" + attributeName + ", attributeCondition="
                + attributeCondition + ", attributeValue=" + attributeValue
                + "]";
    }
    
}
