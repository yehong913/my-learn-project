package com.glaway.ids.planGeneral.plantabtemplate.dto;

import com.glaway.foundation.common.vdata.GLVData;

import java.util.Date;


/**
 * 数据源属性信息DTO
 * @Date: 2019/8/28
 */
public class ObjectPropertyInfoDto extends GLVData {

    //按钮外层div的Id（用来控制按钮是否显示）
    private String buttonDivId;

    //数据源对象Id
    private String dataSourceId;

    //对象路径
    private String objectPath;

    //属性名称
    private String propertyName;

    //对象属性
    private String propertyValue;

    //控件
    private String control;

    //format
    private String format;

    //显示(0-编制，1-编制&启动，2-启动，3-/)
    private String display;

    //读写权限(0-编制，1-编制&启动，2-启动，3-/)
    private String readWriteAccess;

    //必填项(默认为“false”，显示为不必填，true为必填)
    private boolean required = false;

    //操作事件
    private String operationEvent;

    //排序序号
    private String orderNumber;

    private Date defaultValue;
    
    //属性值
    private String valueInfo;

    private boolean readOnly = false;

    private String loadUrl = null;

    private String textField = null;

    private String valueField = null;

    private String displayUsage = null;


    public String getButtonDivId() {
        return buttonDivId;
    }

    public void setButtonDivId(String buttonDivId) {
        this.buttonDivId = buttonDivId;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getObjectPath() {
        return objectPath;
    }

    public void setObjectPath(String objectPath) {
        this.objectPath = objectPath;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getReadWriteAccess() {
        return readWriteAccess;
    }

    public void setReadWriteAccess(String readWriteAccess) {
        this.readWriteAccess = readWriteAccess;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getOperationEvent() {
        return operationEvent;
    }

    public void setOperationEvent(String operationEvent) {
        this.operationEvent = operationEvent;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Date defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValueInfo() {
        return valueInfo;
    }

    public void setValueInfo(String valueInfo) {
        this.valueInfo = valueInfo;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getLoadUrl() {
        return loadUrl;
    }

    public void setLoadUrl(String loadUrl) {
        this.loadUrl = loadUrl;
    }

    public String getTextField() {
        return textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public String getDisplayUsage() {
        return displayUsage;
    }

    public void setDisplayUsage(String displayUsage) {
        this.displayUsage = displayUsage;
    }
}
