<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <t:base type="jquery_iframe,easyui_iframe,tools"></t:base>
    <style>
        .combo-panel panel-body panel-body-noheader{
            height: 100px;
        }
    </style>
</head>
<body class="easyui-layout">
<!-- 页签模版数据区 -->
<div data-options="region:'north',collapsible:false" style="height: 175px;margin-top: 16px">
    <fd:form id="addTabTemplateForm">
        <fd:inputText title="页签名称" name="name" id="addTabTemplate_name" value="${tabTemplate.name}" readonly="true"/>
        <fd:combobox  title="页签类型" name="tabType" id="addTabTemplate_tabType" textField="text" editable="false" valueField="value"
                      data="1_工作内容,2_职责权限,3_资源,4_风险控制,5_过程管理,6_过程结果" value="${tabTemplate.tabType}" readonly="true"></fd:combobox>
        <fd:combobox  title="页面显示方式" name="displayUsage" id="addTabTemplate_displayUsage" textField="text" readonly="true"
                      editable="false" valueField="value" data="1_对象选择,2_外部URL" value="${tabTemplate.displayUsage}" onChange="changeDisplayUsage"></fd:combobox>
        <fd:inputTextArea title="备注" name="remark" id="addTabTemplate_remark" value="${tabTemplate.remake}" readonly="true"/>
    </fd:form>
</div>
<div data-options="region:'center'">
    <div style="display: none" id="urlDivShow">
        <fd:inputText title="外部URL" id="addTabTemplate_externalURL_copy" required="true" maxLength="200" value="${tabTemplate.externalURL}" readonly="true"/>
    </div>
    <div id="dataSourceDiv">
        <!-- 数据源对象信息数据区 -->
        <fd:panel id="one" border="false" title="对象数据编辑" fit="false" collapsed="false" width="100%" height="200px" >
            <fd:datagrid toolbar="#addDataSourceObjectDiv" pagination="false" checkOnSelect="true" url="dataSourceObjectController.do?getAllDataByTabId&tabId=${tabTemplate.id}"
                         idField="id" id="addDataSourceObjecList" fit="true" fitColumns="true" onDblClickRow="doDataSourceEdit" onClickFunName="editDataSourceOver">
                <fd:dgCol title="主键ID" field="id" hidden="true" sortable="false"/>
                <fd:dgCol title="项目所属模块" field="projectModel" hidden="true" sortable="false"/>
                <fd:dgCol title="对象类路径" field="objectPath" sortable="false" formatterFunName="objectPathValue"/>
                <fd:dgCol title="sql条件" field="resultSql" sortable="false" editor="{type:'textbox'}"/>
                <fd:dgCol title="数据转换接口" field="dataToInterface" sortable="false" editor="{type:'textbox'}"/>
                <fd:dgCol title="对象数据结果" field="objectModelProperty" sortable="false" hidden="true"/>
            </fd:datagrid>
        </fd:panel>
        <!-- 元素属性数据区 -->
        <fd:panel id="two" border="false" title="视图控件编辑"  fit="false"  collapsed="false" width="100%" height="200px" >
            <fd:datagrid toolbar="#addObjectPropertyDiv" pagination="false" onDblClickRow="doObjectPropertyEdit" url="objectPropertyController.do?getAllPropertyByTabId&tabId=${tabTemplate.id}"
                         onClickFunName="editObjectPropertyOver" idField="id" id="addObjectPropertyList" fit="true" fitColumns="true" width="100%">
                <fd:dgCol title="主键ID" field="id" hidden="true" sortable="false"/>
                <fd:dgCol title="数据源对象ID" field="dataSourceId" hidden="true" sortable="false"/>
                <fd:dgCol title="对象类路径" field="objectPath" sortable="false" width="150" formatterFunName="propertyPath"/>
                <fd:dgCol title="对象属性" field="propertyValue" sortable="false"/>
                <fd:dgCol title="控件" field="control" sortable="false" />
                <fd:dgCol title="属性名称" field="propertyName" sortable="false" editor="{type:'textbox'}"/>
                <fd:dgCol title="业务条件" field="display" sortable="false" editor="{type:'textbox'}"/>
                <fd:dgCol title="读写权限" field="readWriteAccess" sortable="false" editor="{type:'combobox',options:{panelMaxHeight:100, valueField: 'value',textField: 'text',
                                    data: [{value: '编制', text: '编制'},{value: '编制&启动',text: '编制&启动'},{value: '启动',text: '启动'},{value: '/',text: '/'}]}}"/>
                <fd:dgCol title="必填项" field="required" sortable="false" editor="{type:'combobox',options:{panelMaxHeight:55, valueField: 'value',textField: 'text',
                                    data: [{value: '是', text: '是'},{value: '否',text: '否'}]}}" formatterFunName="requiredFun"/>
                <fd:dgCol title="format" field="format" sortable="false" editor="{type:'textbox'}"/>
                <fd:dgCol title="操作事件" field="operationEvent" sortable="false" editor="{type:'textbox'}"/>
            </fd:datagrid>
        </fd:panel>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function (){
        setTimeout(function(){
            changeDisplayUsage();
        },500);
    })


    //外部URL显示条件
    function changeDisplayUsage() {
        //获取页面显示方式值
        var displayUsageValue = $("#addTabTemplate_displayUsage").combobox('getValue');
        if (displayUsageValue=="2"){
            $("#urlDivShow").show();
            $("#dataSourceDiv").hide();
        }else{
            $("#urlDivShow").hide();
            $("#dataSourceDiv").show();
        }
    }

    function objectPathValue(value,row,index){
        var objValueArray = value.split("com.glaway.");
        return objValueArray[1];
    }

    function propertyPath(value,row,index){
        if (value=="/"){
            return "/"
        } else{
            var objValueArray = value.split("com.glaway.");
            return objValueArray[1];
        }
    }

    function requiredFun(value,row,index){
        var ObjectPropertyRows = $("#addObjectPropertyList").datagrid('getRows');
        var control = ObjectPropertyRows[index].control;
        if (control=="按钮"){
            return "/";
        }else {
            return value;
        }
    }
</script>
</body>