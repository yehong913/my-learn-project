/*
 * 文件名：DelayTaskVo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：syc
 * 修改时间：2016年4月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.statisticalAnalysis.vo;

import java.io.Serializable;

public class planChangeAnalysisVo implements Serializable {

    private String planId;

    private String changeReason;


    private String ownerName;


    private String changeNumber;


    private String projectName;


    private String planName;


    private String assignerName;

    private  int allNumber;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getChangeNumber() {
        return changeNumber;
    }

    public void setChangeNumber(String changeNumber) {
        this.changeNumber = changeNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getAssignerName() {
        return assignerName;
    }

    public void setAssignerName(String assignerName) {
        this.assignerName = assignerName;
    }

    public int getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(int allNumber) {
        this.allNumber = allNumber;
    }
}
