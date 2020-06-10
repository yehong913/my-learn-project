/*
 * 文件名：BasicLineColumn.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：xshen
 * 修改时间：2015年6月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.plan.vo;

public class PlanChangeVo {

    private String planName;

    private String field;

    private String type;
    
    private String changeBefore;
    
    private String changeAfter;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChangeBefore() {
        return changeBefore;
    }

    public void setChangeBefore(String changeBefore) {
        this.changeBefore = changeBefore;
    }

    public String getChangeAfter() {
        return changeAfter;
    }

    public void setChangeAfter(String changeAfter) {
        this.changeAfter = changeAfter;
    }


}
