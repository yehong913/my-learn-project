package com.glaway.ids.project.plan.vo;


/**
 * 计划
 * @author zhousuxia
 * @version 2019年9月14日
 *
 */
public class PlanLoadUrlVo
{
    private String id = null;

    private String propertyName = null;

    private String loadUrl = null;

    private String textField = null;

    private String valueField = null;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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

    public String getLoadUrl() {
        return loadUrl;
    }

    public void setLoadUrl(String loadUrl) {
        this.loadUrl = loadUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
