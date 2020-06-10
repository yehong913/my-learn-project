package com.glaway.ids.project.plm.dto;

import com.glaway.foundation.common.vdata.GLVData;

public class PartMasterDto extends GLVData{

    /**
     * 部件名称
     */
    private String name;

    /**
     * 默认单位
     */
    private String defaultUnit;

    /**
     * 是否是成品(true/false)
     */
    private String endItem;

    /**
     * 是否是主成品(true/false)
     */
    private String mainEndItem;

    /**
     * 零部件编号
     */
    private String partNumber;

    /**
     * 追踪编号
     */
    private String traceCode;

    /**
     * 停止有效性传播
     */
    private String effpropagationStop;

    /**
     * 部件类型类名
     */
    private String partTypeClass;

    /**
     * 部件类型id
     */
    private String partTypeId;


    /**
     * 文件夹位置
     */
    private String location;

    /**
     * 收集部件
     */
    private String collectPart;

    /**
     * 视图
     */
    private String displayView;

    /**
     * 备注
     */
    private String remark;

    /**
     * 容器对象Class
     */
    private String containerClass;

    /**
     * 容器对象ID
     */
    private String containerId;

    public String getContainerClass() {
        return containerClass;
    }

    public void setContainerClass(String containerClass) {
        this.containerClass = containerClass;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(String defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    public String getEndItem() {
        return endItem;
    }

    public void setEndItem(String endItem) {
        this.endItem = endItem;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getTraceCode() {
        return traceCode;
    }

    public void setTraceCode(String traceCode) {
        this.traceCode = traceCode;
    }

    public String getEffpropagationStop() {
        return effpropagationStop;
    }

    public void setEffpropagationStop(String effpropagationStop) {
        this.effpropagationStop = effpropagationStop;
    }

    public String getPartTypeClass() {
        return partTypeClass;
    }

    public void setPartTypeClass(String partTypeClass) {
        this.partTypeClass = partTypeClass;
    }

    public String getPartTypeId() {
        return partTypeId;
    }

    public void setPartTypeId(String partTypeId) {
        this.partTypeId = partTypeId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCollectPart() {
        return collectPart;
    }

    public void setCollectPart(String collectPart) {
        this.collectPart = collectPart;
    }

    public String getDisplayView() {
        return displayView;
    }

    public void setDisplayView(String displayView) {
        this.displayView = displayView;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMainEndItem() {
        return mainEndItem;
    }

    public void setMainEndItem(String mainEndItem) {
        this.mainEndItem = mainEndItem;
    }

}
