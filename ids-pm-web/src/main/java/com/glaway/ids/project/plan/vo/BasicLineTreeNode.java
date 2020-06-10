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


import com.alibaba.fastjson.annotation.JSONField;
import com.glaway.ids.project.plan.dto.BasicLinePlanDto;
import com.glaway.ids.project.plan.dto.PlanDto;

import java.text.SimpleDateFormat;
import java.util.*;


public class BasicLineTreeNode {

    private String id = null;
    
    private String rownumber=null;
    
    
    private String name = null;

    private String basicLineId = null;

    private String planId = null;

    private String parentId = "";

    private String planLevel = null;

    private String bizCurrent = null;

    private String status = null;

    private String planStartTime = null;

    private String planEndTime = null;

    private String workTime = null;

    private String deliverables = null;

    private String creator = null;

    private String ownerDept = null;

    private String owner = null;

    private String assigner = null;

    private String assignTime = null;

    private String remark = null;

    private String milestone = "false";

    private String milestoneName = null;

    private String risk = null;

    private String preposeIds = null;

    private String parentStorey = null;

    private Integer storeyNo = null;

    private String implementation = null;

    private String progressRate = null;
    @JSONField(name="rows")
    private List<BasicLineTreeNode> children = new ArrayList<BasicLineTreeNode>();

    private String belong = null;// 判断节点属于哪棵树

    private List<String> changeNames = new ArrayList<String>();// 定位具体的改变属性，进行红色定位

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBasicLineId() {
        return basicLineId;
    }

    public void setBasicLineId(String basicLineId) {
        this.basicLineId = basicLineId;
    }
    
    public String getRownumber() {
        return rownumber;
    }

    public void setRownumber(String rownumber) {
        this.rownumber = rownumber;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPlanLevel() {
        return planLevel;
    }

    public void setPlanLevel(String planLevel) {
        this.planLevel = planLevel;
    }

    public String getBizCurrent() {
        return bizCurrent;
    }

    public void setBizCurrent(String bizCurrent) {
        this.bizCurrent = bizCurrent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(String deliverables) {
        this.deliverables = deliverables;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOwnerDept() {
        return ownerDept;
    }

    public void setOwnerDept(String ownerDept) {
        this.ownerDept = ownerDept;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getPreposeIds() {
        return preposeIds;
    }

    public void setPreposeIds(String preposeIds) {
        this.preposeIds = preposeIds;
    }

    public String getParentStorey() {
        return parentStorey;
    }

    public void setParentStorey(String parentStorey) {
        this.parentStorey = parentStorey;
    }

    public Integer getStoreyNo() {
        return storeyNo;
    }

    public void setStoreyNo(Integer storeyNo) {
        this.storeyNo = storeyNo;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public String getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(String progressRate) {
        this.progressRate = progressRate;
    }

    public List<BasicLineTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<BasicLineTreeNode> children) {
        this.children = children;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public List<String> getChangeNames() {
        return changeNames;
    }

    public void setChangeNames(List<String> changeNames) {
        this.changeNames = changeNames;
    }

    @Override
    public boolean equals(Object obj) {
        BasicLineTreeNode other = (BasicLineTreeNode)obj;
        if (this.name.equals(other.getName())) {
            return true;
        }
        return false;
    }

    /**
     * 节点的比较
     * 
     * @param obj
     * @param names
     * @return
     * @see
     */
    public boolean compareRecord(Object obj, String names) {
        BasicLineTreeNode other = (BasicLineTreeNode)obj;
        boolean column1 = true;
        boolean column2 = true;
        boolean column3 = true;
        boolean column4 = true;
        boolean column5 = true;
        boolean column6 = true;
        boolean column7 = true;
        boolean column8 = true;

        if (names.equals("all")) {
            names = "计划等级,状态,负责人,开始时间,结束时间,工期,输出";
        }

        if (!this.name.equals(other.getName())) {
            column1 = false;
        }

        if (names.contains("计划等级")) {
            if (!this.planLevel.equals(other.getPlanLevel())) {
                column2 = false;
            }
        }

        if (names.contains("状态")) {
            if (!this.bizCurrent.equals(other.getBizCurrent())) {
                column3 = false;
            }
        }

        if (names.contains("负责人")) {
            if (!this.owner.equals(other.getOwner())) {
                column4 = false;
            }
        }

        if (names.contains("开始时间")) {
            if (!this.planStartTime.equals(other.getPlanStartTime())) {
                column5 = false;
            }
        }

        if (names.contains("结束时间")) {
            if (!this.planEndTime.equals(other.getPlanEndTime())) {
                column6 = false;
            }
        }

        if (names.contains("工期")) {
            if (!this.workTime.equals(other.getWorkTime())) {
                column7 = false;
            }
        }

        if (names.contains("输出")) {
            if (!this.deliverables.equals(other.getDeliverables())) {
                column8 = false;
            }
        }

        return column1 && column2 && column3 && column4 && column5 && column6 && column7
               && column8;
    }

    /**
     * 获取比较的属性名称
     * 
     * @param obj
     * @param names
     * @see
     */
    public void changeNameInit(Object obj, String names) {
        BasicLineTreeNode other = (BasicLineTreeNode)obj;

        if (names.equals("all")) {
            names = "计划等级,状态,负责人,开始时间,结束时间,工期,输出";
        }

        if (!this.name.equals(other.getName())) {
            this.changeNames.add("name");
        }

        if (names.contains("计划等级")) {
            if (!this.planLevel.equals(other.getPlanLevel())) {
                this.changeNames.add("planLevel");
            }
        }

        if (names.contains("状态")) {
            if (!this.bizCurrent.equals(other.getBizCurrent())) {
                this.changeNames.add("bizCurrent");
            }
        }

        if (names.contains("负责人")) {
            if (!this.owner.equals(other.getOwner())) {
                this.changeNames.add("owner");
            }
        }

        if (names.contains("开始时间")) {
            if (!this.planStartTime.equals(other.getPlanStartTime())) {
                this.changeNames.add("planStartTime");
            }
        }

        if (names.contains("结束时间")) {
            if (!this.planEndTime.equals(other.getPlanEndTime())) {
                this.changeNames.add("planEndTime");
            }
        }

        if (names.contains("工期")) {
            if (!this.workTime.equals(other.getWorkTime())) {
                this.changeNames.add("workTime");
            }
        }
        if (names.contains("输出")) {
            if (!this.deliverables.equals(other.getDeliverables())) {
                this.changeNames.add("deliverables");
            }
        }
    }

    /**
     * 清空节点信息
     * 
     * @see
     */
    public void clear() {
        this.setName("");
        this.setAssigner("");
        this.setAssignTime(null);
        this.setBasicLineId("");
        this.setBizCurrent("");
        this.setCreator("");
        this.setDeliverables("");
        this.setImplementation("");
        this.setMilestone("");
        this.setMilestoneName("");
        this.setOwner("");
        this.setOwnerDept("");
        this.setParentStorey("");
        this.setPlanEndTime(null);
        this.setPlanLevel("");
        this.setPlanStartTime(null);
        this.setPreposeIds("");
        this.setProgressRate("");
        this.setRemark("");
        this.setRisk("");
        this.setStatus("");
        this.setWorkTime("");
    }

    /**
     * 复制节点
     * 
     * @param other
     * @see
     */
    public void copy(BasicLineTreeNode other) {
        this.name = other.getName();
        this.planLevel = other.getPlanLevel();
        this.bizCurrent = other.getBizCurrent();
        this.owner = other.getOwner();
        this.planStartTime = other.getPlanStartTime();
        this.planEndTime = other.getPlanEndTime();
        this.workTime = other.getWorkTime();
        this.deliverables = other.getDeliverables();
    }

    /**
     * 基线节点转换
     * 
     * @param plan
     * @return
     * @see
     */
    public static BasicLineTreeNode transfer(BasicLinePlanDto plan, Map<String, String> statusMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        BasicLineTreeNode node = new BasicLineTreeNode();
        node.setId(plan.getId());
        node.setName(plan.getPlanName());
        node.setAssigner(plan.getAssignerInfo() == null ? "" : plan.getAssignerInfo().getRealName());
        node.setAssignTime(plan.getAssignTime() == null ? "" : sdf.format(plan.getAssignTime()));
        node.setBasicLineId(plan.getBasicLineId() == null ? "" : plan.getBasicLineId());
        node.setBizCurrent(plan.getBizCurrent() == null ? "" : statusMap.get(plan.getBizCurrent()));
        node.setCreator(plan.getCreatorInfo() == null ? "" : plan.getCreatorInfo().getRealName());
        node.setDeliverables(processDeliverables(plan.getDeliverables()));
        node.setImplementation(plan.getImplementation());
        node.setMilestone(plan.getMilestone());
        node.setMilestoneName(plan.getMilestoneName());
        node.setOwner(plan.getOwnerInfo() == null ? "" : plan.getOwnerInfo().getRealName()+"-"+plan.getOwnerInfo().getUserName());
        node.setOwnerDept(plan.getOwnerDept());
        node.setParentId(plan.getParentPlanId());
        node.setParentStorey(plan.getParentStorey());
        node.setPlanEndTime(plan.getPlanEndTime() == null ? "" : sdf.format(plan.getPlanEndTime()));
        node.setPlanId(plan.getPlanId());
        node.setPlanLevel(plan.getPlanLevelInfo() == null ? "" : plan.getPlanLevelInfo().getName());
        node.setPlanStartTime(plan.getPlanStartTime() == null ? "" : sdf.format(plan.getPlanStartTime()));
        node.setPreposeIds(plan.getPreposeIds());
        node.setProgressRate(plan.getProgressRate());
        node.setRemark(plan.getRemark());
        node.setRisk(plan.getRisk());
        node.setStatus(plan.getStatus());
        node.setStoreyNo(plan.getStoreyNo());
        node.setWorkTime(plan.getWorkTime() == null ? "" : plan.getWorkTime());

        return node;
    }

    public static BasicLineTreeNode transferPlan(PlanDto plan) {
        BasicLineTreeNode node = new BasicLineTreeNode();
        node.setId(plan.getId());
        node.setParentId(plan.getParentPlanId());
        return node;
    }

    public static String processDeliverables(String deliverables) {
        String ret = "";
        if (deliverables == null || deliverables.trim().equals("")) {
            return "";
        }
        String[] array = deliverables.split(",");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < array.length; i++ ) {
            list.add(array[i]);
        }

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.compareTo(o2) > 0) {
                    return 1;
                }
                else if (o1.compareTo(o2) < 0) {
                    return -1;
                }
                return 0;
            }
        });

        for (int i = 0; i < list.size(); i++ ) {
            if (i == (list.size() - 1)) {
                ret = ret + list.get(i);
            }
            else {
                ret = ret + list.get(i) + ",";
            }
        }
        return ret;
    }
}
