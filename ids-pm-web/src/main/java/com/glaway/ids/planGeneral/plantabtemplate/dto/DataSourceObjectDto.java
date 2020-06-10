package com.glaway.ids.planGeneral.plantabtemplate.dto;

import com.glaway.foundation.common.vdata.GLVData;


/**
 * 数据源对象信息DTO
 * @Date: 2019/8/28
 */
public class DataSourceObjectDto extends GLVData {

    //对象选择
    private String objectPath;

    //数据转换接口
    private String dataToInterface;

    //页签模版Id
    private String tabId;

    //对象所属模块（Review,PM）
    private String projectModel;

    //对象数据结果
    private String objectModelProperty;

    //SQL条件
    private String resultSql;

    //表名
    private String tableName;

    public String getObjectPath() {
        return objectPath;
    }

    public void setObjectPath(String objectPath) {
        this.objectPath = objectPath;
    }

    public String getDataToInterface() {
        return dataToInterface;
    }

    public void setDataToInterface(String dataToInterface) {
        this.dataToInterface = dataToInterface;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getProjectModel() {
        return projectModel;
    }

    public void setProjectModel(String projectModel) {
        this.projectModel = projectModel;
    }

    public String getObjectModelProperty() {
        return objectModelProperty;
    }

    public void setObjectModelProperty(String objectModelProperty) {
        this.objectModelProperty = objectModelProperty;
    }

    public String getResultSql() {
        return resultSql;
    }

    public void setResultSql(String resultSql) {
        this.resultSql = resultSql;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "DataSourceObjectDto{" + "objectPath='" + objectPath + '\'' + ", dataToInterface='"
               + dataToInterface + '\'' + ", tabId='" + tabId + '\'' + ", projectModel='"
               + projectModel + '\'' + ", objectModelProperty='" + objectModelProperty + '\''
               + ", resultSql='" + resultSql + '\'' + ", tableName='" + tableName + '\'' + '}';
    }
}
