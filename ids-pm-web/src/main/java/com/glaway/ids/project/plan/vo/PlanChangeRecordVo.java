/*
 * 文件名：PlanChangeRecordVo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：yourName
 * 修改时间：2018年5月3日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.plan.vo;

/**
 * 计划变更记录vo
 * 
 * @author zhousuxia
 * @version 2018年5月3日
 * @see PlanChangeRecordVo
 * @since
 */
public class PlanChangeRecordVo {
    
    private String id;
    
    private String changePlanId;
    
    private String changeType;
    
    private String changeOwner;
    
    private String changeRemark;
    
    private String changeTime;
    
    private String changeFormId;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChangePlanId() {
        return changePlanId;
    }

    public void setChangePlanId(String changePlanId) {
        this.changePlanId = changePlanId;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getChangeOwner() {
        return changeOwner;
    }

    public void setChangeOwner(String changeOwner) {
        this.changeOwner = changeOwner;
    }

    public String getChangeRemark() {
        return changeRemark;
    }

    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getChangeFormId() {
        return changeFormId;
    }

    public void setChangeFormId(String changeFormId) {
        this.changeFormId = changeFormId;
    }
    
    
}
