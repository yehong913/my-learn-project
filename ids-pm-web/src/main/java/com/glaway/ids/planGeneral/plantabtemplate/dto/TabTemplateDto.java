package com.glaway.ids.planGeneral.plantabtemplate.dto;

import com.glaway.foundation.common.vdata.GLVData;


/**
 * 页签模版信息DTO
 * @Date: 2019/8/28
 */
public class TabTemplateDto extends GLVData {

    //页签名称
    private String name;

    //活动编号
    private String code;

    //活动状态（默认为“1”表示启用，0表示禁用）
    private String stopFlag = "1";

    //页签类型
    private String tabType;

    //页面显示方式(sql编辑--0，URL接口--1)
    private String displayUsage;

    //备注
    private String remake;

    //外部URL
    private String externalURL;

    //来源
    private String source = "1";

    //流程实例Id
    private String processInstanceId = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(String stopFlag) {
        this.stopFlag = stopFlag;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }

    public String getDisplayUsage() {
        return displayUsage;
    }

    public void setDisplayUsage(String displayUsage) {
        this.displayUsage = displayUsage;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public void setExternalURL(String externalURL) {
        this.externalURL = externalURL;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return "TabTemplateDto{" + "name='" + name + '\'' + ", code='" + code + '\''
               + ", stopFlag='" + stopFlag + '\'' + ", tabType='" + tabType + '\''
               + ", displayUsage='" + displayUsage + '\'' + ", remake='" + remake + '\''
               + ", externalURL='" + externalURL + '\'' + ", source='" + source + '\''
               + ", processInstanceId='" + processInstanceId + '\'' + '}';
    }
}
