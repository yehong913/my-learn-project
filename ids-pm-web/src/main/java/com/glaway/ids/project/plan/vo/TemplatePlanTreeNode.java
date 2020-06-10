/*
 * 文件名：BasicLineTreeNode.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：xshen
 * 修改时间：2015年6月3日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.plan.vo;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.annotation.JSONField;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;


public class TemplatePlanTreeNode {

    private String id = null;
    
    private String planNumber=null;
    
    private String displayName = null;
    
    
    private String optBtn = null;

    private String planName = null;

    private String planLevel = null;
    
    private String planLevelInfo = null;

    private String workTime = null;

    private String mileStone = null;

    private String isNecessary = null;
    
    private String delivaryName = null;

    private String preposePlanName = null;
    
    private int storeyNo = 1;
    
    @JSONField(name="rows")
    private List<TemplatePlanTreeNode> children = new ArrayList<TemplatePlanTreeNode>();

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

    public String getOptBtn() {
        return optBtn;
    }

    public void setOptBtn(String optBtn) {
        this.optBtn = optBtn;
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

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getMileStone() {
        return mileStone;
    }

    public void setMileStone(String mileStone) {
        this.mileStone = mileStone;
    }

    public String getIsNecessary() {
        return isNecessary;
    }

    public void setIsNecessary(String isNecessary) {
        this.isNecessary = isNecessary;
    }

    public String getPreposePlanName() {
        return preposePlanName;
    }

    public void setPreposePlanName(String preposePlanName) {
        this.preposePlanName = preposePlanName;
    }

    public List<TemplatePlanTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TemplatePlanTreeNode> children) {
        this.children = children;
    }

    public String getDelivaryName() {
        return delivaryName;
    }

    public void setDelivaryName(String delivaryName) {
        this.delivaryName = delivaryName;
    }

    public int getStoreyNo() {
        return storeyNo;
    }

    public void setStoreyNo(int storeyNo) {
        this.storeyNo = storeyNo;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPlanLevelInfo() {
        return planLevelInfo;
    }

    public void setPlanLevelInfo(String planLevelInfo) {
        this.planLevelInfo = planLevelInfo;
    }

    
   
}
