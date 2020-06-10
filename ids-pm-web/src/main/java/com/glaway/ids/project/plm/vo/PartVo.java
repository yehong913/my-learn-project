package com.glaway.ids.project.plm.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.glaway.foundation.common.vo.TreeNode;


/**
 * 零部件详细信息展示vo
 * @Title PartVo.java
 * @Description
 * @author wangdali
 * @date 2018年10月11日 上午10:45:30
 * @version v1.0
 */
public class PartVo {



    @Override
    public String toString() {
        return "PartVo [id=" + id + ", name=" + name + ", bizCurrent=" + bizCurrent + ", location=" + location
               + ", context=" + context + ", partNo=" + partNo + ", bizVersion=" + bizVersion + ", amount=" + amount
               + ", traceCode=" + traceCode + ", placeNo=" + placeNo + ", findNo=" + findNo + ", partTypeId="
               + partTypeId + ", containerId=" + containerId + ", containerClass=" + containerClass + ", parentId="
               + parentId + ", parentNo=" + parentNo + ", parentName=" + parentName + ", partId=" + partId + ", bizId="
               + bizId + ", stateCheckoutInfo=" + stateCheckoutInfo + ", description=" + description + ", version="
               + version + ", treeNodes=" + treeNodes + ", linkId=" + linkId + ", isTranscript=" + isTranscript
               + ", lockId=" + lockId + ", viewId=" + viewId + ", level=" + level + "]";
    }
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 修改者
     */
    private String updateFullName;
    /**
     * 上次修改者
     */
    private String lastUpdateFullName;
    /**
     * 创建者
     */
    private String createfullname;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建时间
     */
    private String createTimeStr;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改时间
     */
    private String updateTimeStr;

    /**
     * 装配模式
     */
    private String assemblyModel;

    private String assemblyModelTitle;
    /**
     * 是否成品
     */
    private String endItem;
    /**
     * 是否主成品
     */
    private String mainEndItem;
    /**
     * 源
     */
    private String source;

    private String sourceTitle;
    /**
     * 默认单位
     */
    private String unit;
    /**
     * 状态
     */
    private String bizCurrent;
    /**
     * 位置
     */
    private String location;
    /**
     * 上下文
     */
    private String context;
    /**
     * 状况
     */
    private String condition;
    /**
     * 编号
     */
    private String partNo;
    /**
     * 版本
     */
    private String bizVersion;
    /**
     * 行号
     */
    private String lineNo;
    /**
     * 数量
     */
    private String amount;
    /**
     * 追踪代码
     */
    private String traceCode;
    /**
     * 位号
     */
    private String placeNo;
    /**
     * 检索号
     */
    private String findNo;

    private String partTypeId;

    private String containerId;

    private String containerClass;
    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 父节点编号
     */
    private String parentNo;

    /**
     * 父节点名称
     */
    private String parentName;

    /**
     * 部件类型名称
     */
    private String partTypeName;
    /**
     * 双向(默认否)
     */
    private String isTwoWay;
    /**
     * 对象类型指示符
     */
    private String symbol;
    /**
     * 常规状态
     */
    private String groovyState;

    /**
     * 创建时间
     */
    private String createTimeFormat;

    /**
     * 修改时间
     */
    private String updateTimeFormat;

    private String partId;

    /**
     * bizId
     */
    private String bizId;

    /**
     * 检入检出状态
     */
    private String stateCheckoutInfo;

    /**
     * id所对应的类名
     */
    private String className;

    /**
     * 备注
     */
    private String description;

    /**
     * 版本（含视图）
     */
    private String version;

    /**
     * BOM树结构
     */
    List<TreeNode> treeNodes;

    private String linkId;

    private String isTranscript;

    private String lockId;

    private String viewId;

    private String remark;

    private String partViewId;

    /**
     * 对象显示名称
     */

    private String typename;
    /**
     * 对象显示图标
     */
    private String typeicon;

    /**
     * 优先级
     */
    private String priority;

    /**
     * 全局替换roleAId
     */
    private String roleAId;

    /**
     * 全局替换id
     */
    private String alternateId;

    /**
     * 装配对象名称
     */
    private String assemblyObjectName;

    /**
     * 层级
     */
    private int level;

    private String status;

    private String policyId;

    @JSONField(name="rows")
    private List<PartVo> children = new ArrayList<PartVo>();
    private String parent;
    private String xmlkids;

    private String viewName;

    private String iconCls;

    private String avaliable;


    public List<PartVo> getChildren() {
        return children;
    }
    public void setChildren(List<PartVo> children) {
        this.children = children;
    }
    public String getXmlkids() {
        return xmlkids;
    }
    public void setXmlkids(String xmlkids) {
        this.xmlkids = xmlkids;
    }
    public String getParent() {
        return parent;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
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
    public String getUpdateFullName() {
        return updateFullName;
    }
    public void setUpdateFullName(String updateFullName) {
        this.updateFullName = updateFullName;
    }
    public String getLastUpdateFullName() {
        return lastUpdateFullName;
    }
    public void setLastUpdateFullName(String lastUpdateFullName) {
        this.lastUpdateFullName = lastUpdateFullName;
    }
    public String getCreatefullname() {
        return createfullname;
    }
    public void setCreatefullname(String createfullname) {
        this.createfullname = createfullname;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getAssemblyModel() {
        return assemblyModel;
    }
    public void setAssemblyModel(String assemblyModel) {
        this.assemblyModel = assemblyModel;
    }
    public String getEndItem() {
        return endItem;
    }
    public void setEndItem(String endItem) {
        this.endItem = endItem;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getBizCurrent() {
        return bizCurrent;
    }
    public void setBizCurrent(String bizCurrent) {
        this.bizCurrent = bizCurrent;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getContext() {
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public String getPartTypeId() {
        return partTypeId;
    }
    public void setPartTypeId(String partTypeId) {
        this.partTypeId = partTypeId;
    }
    public String getPartNo() {
        return partNo;
    }
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }
    public String getBizVersion() {
        return bizVersion;
    }
    public void setBizVersion(String bizVersion) {
        this.bizVersion = bizVersion;
    }
    public String getLineNo() {
        return lineNo;
    }
    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getTraceCode() {
        return traceCode;
    }
    public void setTraceCode(String traceCode) {
        this.traceCode = traceCode;
    }
    public String getFindNo() {
        return findNo;
    }
    public void setFindNo(String findNo) {
        this.findNo = findNo;
    }
    public String getPlaceNo() {
        return placeNo;
    }
    public void setPlaceNo(String placeNo) {
        this.placeNo = placeNo;
    }
    public String getContainerId() {
        return containerId;
    }
    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getParentNo() {
        return parentNo;
    }
    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public String getPartTypeName() {
        return partTypeName;
    }
    public void setPartTypeName(String partTypeName) {
        this.partTypeName = partTypeName;
    }
    public String getIsTwoWay() {
        return isTwoWay;
    }
    public void setIsTwoWay(String isTwoWay) {
        this.isTwoWay = isTwoWay;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getGroovyState() {
        return groovyState;
    }
    public void setGroovyState(String groovyState) {
        this.groovyState = groovyState;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getMainEndItem() {
        return mainEndItem;
    }
    public void setMainEndItem(String mainEndItem) {
        this.mainEndItem = mainEndItem;
    }
    public String getCreateTimeFormat() {
        return createTimeFormat;
    }
    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
    }
    public String getUpdateTimeFormat() {
        return updateTimeFormat;
    }
    public void setUpdateTimeFormat(String updateTimeFormat) {
        this.updateTimeFormat = updateTimeFormat;
    }
    public String getContainerClass() {
        return containerClass;
    }
    public void setContainerClass(String containerClass) {
        this.containerClass = containerClass;
    }
    public String getPartId() {
        return partId;
    }
    public void setPartId(String partId) {
        this.partId = partId;
    }
    public String getBizId() {
        return bizId;
    }
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }
    public String getStateCheckoutInfo() {
        return stateCheckoutInfo;
    }
    public void setStateCheckoutInfo(String stateCheckoutInfo) {
        this.stateCheckoutInfo = stateCheckoutInfo;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public List<TreeNode> getTreeNodes() {
        return treeNodes;
    }
    public void setTreeNodes(List<TreeNode> treeNodes) {
        this.treeNodes = treeNodes;
    }
    public String getLinkId() {
        return linkId;
    }
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }
    public String getCreateTimeStr() {
        return createTimeStr;
    }
    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
    public String getUpdateTimeStr() {
        return updateTimeStr;
    }
    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
    public String getIsTranscript() {
        return isTranscript;
    }
    public void setIsTranscript(String isTranscript) {
        this.isTranscript = isTranscript;
    }
    public String getLockId() {
        return lockId;
    }
    public void setLockId(String lockId) {
        this.lockId = lockId;
    }
    public String getViewId() {
        return viewId;
    }
    public void setViewId(String viewId) {
        this.viewId = viewId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getPartViewId() {
        return partViewId;
    }
    public void setPartViewId(String partViewId) {
        this.partViewId = partViewId;
    }
    public String getTypename() {
        return typename;
    }
    public void setTypename(String typename) {
        this.typename = typename;
    }
    public String getTypeicon() {
        return typeicon;
    }
    public void setTypeicon(String typeicon) {
        this.typeicon = typeicon;
    }
    public String getRoleAId() {
        return roleAId;
    }
    public void setRoleAId(String roleAId) {
        this.roleAId = roleAId;
    }
    public String getAlternateId() {
        return alternateId;
    }
    public void setAlternateId(String alternateId) {
        this.alternateId = alternateId;
    }
    public String getAssemblyObjectName() {
        return assemblyObjectName;
    }
    public void setAssemblyObjectName(String assemblyObjectName) {
        this.assemblyObjectName = assemblyObjectName;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPolicyId() {
        return policyId;
    }
    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }
    public String getAssemblyModelTitle() {
        return assemblyModelTitle;
    }
    public void setAssemblyModelTitle(String assemblyModelTitle) {
        this.assemblyModelTitle = assemblyModelTitle;
    }
    public String getSourceTitle() {
        return sourceTitle;
    }
    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }
    public String getViewName() {
        return viewName;
    }
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
    public String getIconCls() {
        return iconCls;
    }
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
    public String getAvaliable() {
        return avaliable;
    }
    public void setAvaliable(String avaliable) {
        this.avaliable = avaliable;
    }
}
