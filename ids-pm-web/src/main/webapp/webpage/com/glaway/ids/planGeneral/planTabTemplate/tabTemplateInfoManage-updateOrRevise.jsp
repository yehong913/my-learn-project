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
        <fd:inputText title="页签名称" name="name" id="addTabTemplate_name" required="true" value="${tebDto.name}"/>
        <fd:combobox  title="页签类型" name="tabType" id="addTabTemplate_tabType" textField="text" editable="false" valueField="value"
                      data="1_工作内容,2_职责权限,3_资源,4_风险控制,5_过程管理,6_过程结果" value="${tebDto.tabType}"></fd:combobox>
        <fd:combobox  title="页面显示方式" name="displayUsage" id="addTabTemplate_displayUsage" textField="text"
                      editable="false" valueField="value" data="1_对象选择,2_外部URL" value="${tebDto.displayUsage}" onChange="changeDisplayUsage"></fd:combobox>
        <fd:inputTextArea title="备注" name="remark" id="addTabTemplate_remark" value="${tebDto.remake}"/>
        <div style="display: none">
            <fd:inputText name="externalURL" id="addTabTemplate_externalURL"/>
        </div>
        <input type="button" id="btn_sub" style="display: none;" onclick="repeatName();">
    </fd:form>
</div>
<div data-options="region:'center'">
    <div style="display: none" id="urlDivShow">
        <fd:inputText title="外部URL" id="addTabTemplate_externalURL_copy" required="true" maxLength="200" value="${tebDto.externalURL}"/>
    </div>
    <div id="dataSourceDiv">
        <!-- 数据源对象信息数据区 -->
        <fd:panel id="one" border="false" title="对象数据编辑" fit="false" collapsed="false" width="100%" height="200px" >
            <fd:toolbar id="addDataSourceObjectDiv">
                <fd:toolbarGroup align="left">
                    <fd:linkbutton id="addDataSourceObjectBtn" onclick="addDSOBtn()" value="{com.glaway.ids.common.btn.create}" iconCls="basis ui-icon-plus" />
                    <fd:linkbutton id="deleteDataSourceObjectBtn" onclick="deleteSelectionsDSOBtn()" value="{com.glaway.ids.common.btn.remove}" iconCls="basis ui-icon-minus"/>
                </fd:toolbarGroup>
            </fd:toolbar>
            <fd:datagrid toolbar="#addDataSourceObjectDiv" pagination="false" checkOnSelect="true" url="dataSourceObjectController.do?getAllDataByTabId&tabId=${tebDto.id}"
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
            <fd:toolbar id="addObjectPropertyDiv">
                <fd:toolbarGroup align="left">
                    <fd:linkbutton id="addObjectPropertyBtn" onclick="addOPIBtn()" value="{com.glaway.ids.common.btn.create}" iconCls="basis ui-icon-plus"/>
                    <fd:linkbutton id="upObjectPropertyBtn" onclick="upOPIBtn()" value="{com.glaway.ids.common.btn.moveNodeUp}" iconCls="basis ui-icon-up"/>
                    <fd:linkbutton id="downObjectPropertyBtn" onclick="downOPIBtn()" value="{com.glaway.ids.common.btn.moveNodeDown}" iconCls="basis ui-icon-down"/>
                    <fd:linkbutton id="deleteObjectPropertyBtn" onclick="deleteSelectionsOPIBtn()" value="{com.glaway.ids.common.btn.remove}" iconCls="basis ui-icon-minus"/>
                    <fd:linkbutton id="modifyObjectPropertyBtn" onclick="modifyOPIBtn()" value="修改对象类路径" iconCls="basis ui-icon-pencil"/>
                </fd:toolbarGroup>
            </fd:toolbar>
            <fd:datagrid toolbar="#addObjectPropertyDiv" pagination="false" onDblClickRow="doObjectPropertyEdit" url="objectPropertyController.do?getAllPropertyByTabId&tabId=${tebDto.id}"
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
<input type="button" id="updateBtn_sub" style="display: none;" onclick="updateCheck();">

<!-- 查找数据源对象Dialog -->
<fd:dialog id="searchDataSourceDialog" width="750px" height="500px" modal="true" title="实体对象选择">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="saveDataSource"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="closeDataDialog"></fd:dialogbutton>
</fd:dialog>

<!-- 视图控件编辑选择数据Dialog -->
<fd:dialog id="searchObjectPropertyDialog" width="500px" height="250px" modal="true" title="视图控件编辑选择数据">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="saveObjectProperty"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="closeDataDialog"></fd:dialogbutton>
</fd:dialog>

<!-- 修改对象类路径Dialog -->
<fd:dialog id="modfiyObjectPropertyDialog" width="500px" height="250px" modal="true" title="修改对象类路径">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="saveObjectProperty"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="closeDataDialog"></fd:dialogbutton>
</fd:dialog>

<script type="text/javascript">
    function requiredFun(value,row,index){
        var ObjectPropertyRows = $("#addObjectPropertyList").datagrid('getRows');
        var control = ObjectPropertyRows[index].control;
        if (control=="按钮"){
            return "/";
        }else {
            return value;
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

    //元数据数组
    var objectPropertyArray = new Array();
    //数据源对象信息数组
    var dataSourceObjectArray = new Array();
    var dataSourceObjectRollbackArray = new Array();
    var objectPropertyRollbackArray = new Array();
    $(document).ready(function (){
        setTimeout(function(){
            changeDisplayUsage();

            //加载数据
            var ObjectPropertyRows = $("#addObjectPropertyList").datagrid('getRows');
            objectPropertyArray = new Array();
            for (var i = 0 ; i < ObjectPropertyRows.length ; i++) {
                var objectProperty = new Object();
                //初始化赋值
                objectProperty['id']=ObjectPropertyRows[i].id;
                objectProperty['dataSourceId']=ObjectPropertyRows[i].dataSourceId;
                objectProperty['objectPath']=ObjectPropertyRows[i].objectPath;
                objectProperty['propertyValue']=ObjectPropertyRows[i].propertyValue;
                objectProperty['control']=ObjectPropertyRows[i].control;
                objectProperty['propertyName']=ObjectPropertyRows[i].propertyName;
                objectProperty['display']=ObjectPropertyRows[i].display;
                objectProperty['readWriteAccess']=ObjectPropertyRows[i].readWriteAccess;
                if (ObjectPropertyRows[i].required=="true"){
                    objectProperty['required'] = "是";
                } else  {
                    objectProperty['required'] = "否";
                }
                objectProperty['format']=ObjectPropertyRows[i].format;
                objectProperty['operationEvent']=ObjectPropertyRows[i].operationEvent;
                objectPropertyArray.push(objectProperty);
                objectPropertyRollbackArray.push(ObjectPropertyRows[i].id);
            }

            var rows = $("#addDataSourceObjecList").datagrid('getRows');
            for (var i = 0 ; i < rows.length ; i++) {
                dataSourceObjectArray.push(rows[i]);
                //记录数据对象回滚Id值
                var obj = new Object();
                obj["id"]= rows[i].id;
                obj["status"] ="1"
                dataSourceObjectRollbackArray.push(obj);
            }
        },500);
    })

    //对象正在修改的行
    var dataEditingIndex;
    //属性正在修改的行
    var propertyEditingIndex;
    //页签模板Id
    var tabId = "${tebDto.id}";
    //是否修改元数据
    var modfiy = false;
    //code
    var code = "${tebDto.code}";

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

    //数据校验，文本赋值
    function dataValidate(){
        var isSubmit = true;
        //获取页签名称值
        var name = $("#addTabTemplate_name").textbox("getValue");
        if (name==""){
            top.tip("页签模板名称不能为空");
            isSubmit = false;
        }
        //获取页面显示方式值
        var displayUsageValue = $("#addTabTemplate_displayUsage").combobox('getValue');
        //当前显示为外部URL方式，值不能为空
        if (displayUsageValue=="2"){
            //获取外部URL_副本
            var externalURL = $("#addTabTemplate_externalURL_copy").textbox("getValue");
            if (externalURL==""){
                top.tip("外部URL不能为空");
                isSubmit = false;
            }else{
                //把副本值赋给外部URL值
                $("#addTabTemplate_externalURL").textbox("setValue", externalURL);
            }
        }
        return isSubmit;
    }

    //提交页签模板数据
    function tabTemplateInfoSubmit(avaliable) {
        //获取外部URL_副本
        var externalURL = $("#addTabTemplate_externalURL_copy").textbox("getValue");
        //获取页签名称值
        var name = $("#addTabTemplate_name").textbox("getValue");
        //获取页面显示方式值
        var displayUsage = $("#addTabTemplate_displayUsage").combobox('getValue');
        //获取页签类型值
        var tabType = $("#addTabTemplate_tabType").combobox('getValue');
        //获取备注数据
        var remark = $("#addTabTemplate_remark").textbox('getValue');
        //修改或者修订
        var updateOrRevise = "${updateOrRevise}";
        if (displayUsage=="对象选择"){
            displayUsage = "1";
        }
        if (tabType=="工作内容"){
            tabType = "1";
        }
        var tab = "${tebDto.id}";
        $.ajax({
            url : 'tabTemplateController.do?doUpdateOrRevise',
            type : 'post',
            data : {
                updateOrRevise:updateOrRevise,
                id: tab,
                avaliable:avaliable,
                externalURL:externalURL,
                name:name,
                displayUsage:displayUsage,
                tabType:tabType,
                remake:remark,
                code:code
            },
            cache : false,
            async:false,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var obj =  d.obj;
                    tabId = obj.id;
                    code = obj.code;
                }
            }
        });
        return tabId;
    }


    //新增一条数据源对象信息
    function addDSOBtn() {
        //打开查找Dialog
        createDialog("searchDataSourceDialog", "dataSourceObjectController.do?searchDataSourcePage");
    }

    //查找对象数据进行保存赋值
    function addDataSourceObjectInfo(id, objectPath, objectModelProperty, projectModel) {
        var rows = $("#addDataSourceObjecList").datagrid('getRows');
        dataSourceObjectArray = new Array();
        if (rows.length>0){
            for (var i=0;i<rows.length;i++){
                dataSourceObjectArray.push(rows[i]);
            }
        }
        var dataSourceObject = new Object();
        //初始化赋值
        dataSourceObject['id']=id;
        dataSourceObject['objectPath']=objectPath;
        dataSourceObject['projectModel']=projectModel;
        dataSourceObject['resultSql']='';
        dataSourceObject['dataToInterface']='';
        dataSourceObject['objectModelProperty']=objectModelProperty;
        dataSourceObjectArray.push(dataSourceObject);
        //数据加载
        $("#addDataSourceObjecList").datagrid('loadData',dataSourceObjectArray);
    }

    //取消按钮
    function closeDataDialog(){
        //关闭窗口
        hideDiaLog();
    }

    //触发数据对象保存提交按钮
    function saveDataSource(iframe) {
        //触发新增页面提交
        saveOrUp(iframe);
        return false;
    }

    //触发元数据对象保存提交按钮
    function saveObjectProperty(iframe) {
        //触发新增页面提交
        saveOrUp(iframe);
        return false;
    }

    //删除数据源对象信息
    function deleteSelectionsDSOBtn(){
        //获取选中行数据
        var rows = $("#addDataSourceObjecList").datagrid('getSelections');
        //判断当前是否选择数据
        if(rows.length > 0){
            //弹出确认框
            top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmBatchDel"/>', function(r) {
                if (r) {
                    for(var i = (rows.length-1); i >=0;i-- ){
                        //获取当前行的index
                        var index = $("#addDataSourceObjecList").datagrid('getRowIndex',rows[i].id);
                        var id = rows[i].id;

                        // 删除选择行
                        $("#addDataSourceObjecList").datagrid('deleteRow',index);
                        //清除与之关联的元素数据信息
                        deleteObjectPropertyByDataSourceId(id);
                        //清除行焦点
                        $('#addDataSourceObjecList').datagrid('clearSelections');
                        top.tip("操作成功");
                    }
                }
            });
        }else{
            top.tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
            return false;
        }
    }

    //删除操作---数据源对象信息与元素数据联动
    function deleteObjectPropertyByDataSourceId(dataSourceObjectId) {
        //获取当前所有元素数据
        var rows = $("#addObjectPropertyList").datagrid('getRows');
        //判断当前是否有数据
        if (rows.length>0){
            for (var i = (rows.length-1); i >=0;i-- ){
                //获取元素对象--数据源ID
                var dataSourceId = rows[i].dataSourceId;
                //如果当元素数据包含-数据源对象则选择删除
                if (dataSourceId==dataSourceObjectId) {
                    //获取当前行的index
                    var index = $("#addObjectPropertyList").datagrid('getRowIndex',rows[i].id);
                    // 删除选择行
                    $("#addObjectPropertyList").datagrid('deleteRow',index);
                }
            }
        }
    }

    //修改事件
    function modifyOPIBtn() {
        //获取选中行数据
        var rows = $("#addObjectPropertyList").datagrid('getSelections');
        if (rows[0].control=="列"){
            top.tip("列控件不支持修改操作");
            return;
        }

        if (rows.length>0){
            modfiy = true;
            openObjectPropertyDialog("modfiyObjectPropertyDialog");
        }else {
            top.tip("请选择一条需要修改的数据");
            return;
        }
    }

    function modifyOPI(dataSourceId, objectPath, propertyValue, propertyName,control) {
        var selectrow = $('#addObjectPropertyList').datagrid('getSelected');
        var rowIndex = $('#addObjectPropertyList').datagrid('getRowIndex',selectrow);
        var obj = objectPropertyArray[rowIndex];
        obj['dataSourceId']=dataSourceId;
        obj['objectPath']=objectPath;
        obj['propertyValue']=propertyValue;
        if(propertyName!=""){
            obj['propertyName']=propertyName;
        }
        obj['control']=control;
        //数据加载
        $("#addObjectPropertyList").datagrid('loadData',objectPropertyArray);
    }


    //新增一条元数据性信息
    function addOPIBtn() {
        modfiy = false;
        //获取当前实体对象所有元素数据
        var addDataSourceObjecListRows = $("#addDataSourceObjecList").datagrid('getRows');
        if (addDataSourceObjecListRows.length==0){
            top.tip('请先完成对象数据编辑操作');
            return false;
        }
        //增加元素据空行
        openObjectPropertyDialog("searchObjectPropertyDialog");
    }

    //打开视图控件编辑选择数据Dialog
    function openObjectPropertyDialog(id) {
        createDialog(id, "objectPropertyController.do?searchObjectPropertyPage&tabId="+tabId);
    }

    //增加元素
    function addObjectProperty(dataSourceId, objectPath, propertyValue, propertyName,control,display,readWriteAccess,required) {
        //数组数据重置
        var rows = $("#addObjectPropertyList").datagrid('getRows');
        objectPropertyArray = new Array();
        if (rows.length>0){
            for (var i=0;i<rows.length;i++){
                objectPropertyArray.push(rows[i]);
            }
        }
        var objectProperty = new Object();
        //初始化赋值
        objectProperty['id']='';
        objectProperty['dataSourceId']=dataSourceId;
        objectProperty['objectPath']=objectPath;
        objectProperty['propertyValue']=propertyValue;
        objectProperty['control']=control;
        objectProperty['propertyName']=propertyName;
        objectProperty['display']=display;
        objectProperty['readWriteAccess']=readWriteAccess;
        objectProperty['required']=required;
        objectProperty['format']='';
        objectProperty['operationEvent']='';
        objectPropertyArray.push(objectProperty);
        //数据加载
        $("#addObjectPropertyList").datagrid('loadData',objectPropertyArray);
    }

    //上移一条元数据性信息
    function upOPIBtn(){
        var selectrow = $('#addObjectPropertyList').datagrid('getSelected');
        var rowIndex = $('#addObjectPropertyList').datagrid('getRowIndex',selectrow);
        var rows = $("#addObjectPropertyList").datagrid('getRows');
        var rowlength = $('#addObjectPropertyList').datagrid('getRows').length;
        if (selectrow == null) {
            top.tip('请选择需要上移的数据');
        } else {
            if (rowIndex == 0) {
                top.tip('当前数据不可以进行上移操作');
            } else {
                //当前为其他控件
                if (selectrow.control!="表格"&&selectrow.control!="列"){
                    //上一级不为列，直接上移操作
                    if (rows[rowIndex-1].control!="列") {
                        $('#addObjectPropertyList').datagrid('deleteRow',rowIndex);//删除一行
                        rowIndex--;
                        $('#addObjectPropertyList').datagrid('insertRow',{
                            index:rowIndex,
                            row:selectrow
                        });
                        $('#addObjectPropertyList').datagrid('selectRow',rowIndex);
                        //上一级为列控件，需要跳过当前表格进行上移
                    }else if (rows[rowIndex-1].control=="列") {
                        var tableRowIndex = "";
                        for (var i=0; i<rows.length;i++){
                            //找出上一级为表的行下标
                            if (rows[i].control=="表格"&&rows[i].objectPath==rows[rowIndex-1].objectPath) {
                                tableRowIndex = i;
                                break;
                            }
                        }
                        $('#addObjectPropertyList').datagrid('deleteRow',rowIndex);//删除一行
                        if (tableRowIndex==0){
                            $('#addObjectPropertyList').datagrid('insertRow',{
                                index:0,
                                row:selectrow
                            });
                            $('#addObjectPropertyList').datagrid('selectRow',0);
                        } else {
                            tableRowIndex;
                            $('#addObjectPropertyList').datagrid('insertRow',{
                                index:tableRowIndex,
                                row:selectrow
                            });
                            $('#addObjectPropertyList').datagrid('selectRow',tableRowIndex);
                        }
                    }
                    //当前为列，上一级为列，说明为同一张表格，可以直接上移操作
                }else if (selectrow.control=="列"&&rows[rowIndex-1].control=="列"){
                    $('#addObjectPropertyList').datagrid('deleteRow',rowIndex);//删除一行
                    rowIndex--;
                    $('#addObjectPropertyList').datagrid('insertRow',{
                        index:rowIndex,
                        row:selectrow
                    });
                    $('#addObjectPropertyList').datagrid('selectRow',rowIndex);
                    //当前为表格
                }else if (selectrow.control=="表格"){
                    var tableRows = new Array();
                    for (var i=0; i<rows.length;i++){
                        //找出当前表列集合
                        if (rows[i].control=="列"&&rows[i].objectPath==rows[rowIndex].objectPath) {
                            tableRows.push(rows[i]);
                        }
                    }
                    //删除当前表格
                    for (var i = (rows.length-1); i >=rowIndex;i--) {
                        if (selectrow.objectPath == rows[i].objectPath&&(rows[i].control=="列"||rows[i].control=="表格")) {
                            $('#addObjectPropertyList').datagrid('deleteRow',i);
                        }
                    }
                    //判断上级是否为列，不为列整体表格进行上移操作
                    if (rows[rowIndex-1].control!="列") {
                        if (rowIndex-1==0){
                            $('#addObjectPropertyList').datagrid('insertRow',{
                                index:0,
                                row:selectrow
                            });
                            $('#addObjectPropertyList').datagrid('selectRow',0);
                            for (var i=0; i<tableRows.length;i++){
                                $('#addObjectPropertyList').datagrid('insertRow',{
                                    index:i+1,
                                    row:tableRows[i]
                                });
                            }
                        }else {
                            $('#addObjectPropertyList').datagrid('insertRow',{
                                index:rowIndex-1,
                                row:selectrow
                            });
                            $('#addObjectPropertyList').datagrid('selectRow',rowIndex-1);
                            for (var i=0; i<tableRows.length;i++){
                                $('#addObjectPropertyList').datagrid('insertRow',{
                                    index:i+rowIndex,
                                    row:tableRows[i]
                                });
                            }
                        }
                        //上一级为列，跳过上一个表格进行上移操作
                    }else if (rows[rowIndex-1].control=="列") {
                        var upTableRowIndex = "";
                        for (var i=0; i<rows.length;i++){
                            //找出上一级的表格下表
                            if (rows[i].control=="表格"&&rows[i].objectPath==rows[rowIndex-1].objectPath) {
                                upTableRowIndex = i;
                                break;
                            }
                        }
                        if (upTableRowIndex==0){
                            $('#addObjectPropertyList').datagrid('insertRow',{
                                index:0,
                                row:selectrow
                            });
                            $('#addObjectPropertyList').datagrid('selectRow',0);
                            for (var i=0; i<tableRows.length;i++){
                                $('#addObjectPropertyList').datagrid('insertRow',{
                                    index:i+1,
                                    row:tableRows[i]
                                });
                            }
                        } else{
                            upTableRowIndex;
                            $('#addObjectPropertyList').datagrid('insertRow',{
                                index:upTableRowIndex,
                                row:selectrow
                            });
                            $('#addObjectPropertyList').datagrid('selectRow',upTableRowIndex);
                            for (var i=0; i<tableRows.length;i++){
                                $('#addObjectPropertyList').datagrid('insertRow',{
                                    index:i+1+upTableRowIndex,
                                    row:tableRows[i]
                                });
                            }
                        }
                    }
                }else {
                    top.tip('当前数据不可以进行上移操作');
                }
            }
        }
    }


    //下移一条元数据性信息
    function downOPIBtn(){
        var selectrow = $('#addObjectPropertyList').datagrid('getSelected');
        var rowIndex = $('#addObjectPropertyList').datagrid('getRowIndex',selectrow);
        var rowlength = $('#addObjectPropertyList').datagrid('getRows').length;
        var rows = $("#addObjectPropertyList").datagrid('getRows');
        if (selectrow == null) {
            top.tip('请选择需要下移的数据');
        } else {
            if (rowIndex == rowlength-1) {
                top.tip('当前数据不可以进行下移操作');
            } else {
                //当前为其他控件
                if (selectrow.control!="表格"&&selectrow.control!="列"){
                    //下一级不为表格，直接下移操作
                    if (rows[rowIndex+1].control!="表格") {
                        $('#addObjectPropertyList').datagrid('deleteRow',rowIndex);//删除一行
                        rowIndex++;
                        $('#addObjectPropertyList').datagrid('insertRow',{
                            index:rowIndex,
                            row:selectrow
                        });
                        $('#addObjectPropertyList').datagrid('selectRow',rowIndex);
                    }else {
                        var downIndex = 0;
                        for (var i=0; i<rows.length;i++){
                            //下移的表大小
                            if (rows[i].objectPath==rows[rowIndex+1].objectPath&&(rows[i].control=="列"||rows[i].control=="表格")) {
                                downIndex++;
                            }
                        }
                        $('#addObjectPropertyList').datagrid('deleteRow',rowIndex);//删除一行
                        rowIndex = rowIndex+downIndex;
                        $('#addObjectPropertyList').datagrid('insertRow',{
                            index:rowIndex,
                            row:selectrow
                        });
                        $('#addObjectPropertyList').datagrid('selectRow',rowIndex);
                    }
                    //表格内列表操作直接下移
                }else if (selectrow.control=="列"&&rows[rowIndex+1].control=="列") {
                    $('#addObjectPropertyList').datagrid('deleteRow',rowIndex);//删除一行
                    rowIndex++;
                    $('#addObjectPropertyList').datagrid('insertRow',{
                        index:rowIndex,
                        row:selectrow
                    });
                    $('#addObjectPropertyList').datagrid('selectRow',rowIndex);
                    //当前为表操作
                }else if (selectrow.control=="表格"){
                    var tableRows = new Array();
                    var lieRowLength = 0;
                    //找出当前表列集合
                    for (var i=0; i<rows.length;i++){
                        if (rows[i].control=="列"&&rows[i].objectPath==selectrow.objectPath) {
                            tableRows.push(rows[i]);
                            lieRowLength++;
                        }
                    }
                    //判断当前是否只有此表格，不包含其他数据。
                    if (rows.length==tableRows.length+1){
                        top.tip('当前数据不可以进行下移操作');
                        return false;
                    }

                    if (rows[rowIndex+1].control=="列") {
                        if (rows[rowIndex+1+lieRowLength].control=="表格"){
                            var downIndex = 0;
                            for (var i=0; i<rows.length;i++){
                                //下移的表大小
                                if (rows[i].objectPath==rows[rowIndex+1+lieRowLength].objectPath&&(rows[i].control=="列"||rows[i].control=="表格")) {
                                    downIndex++;
                                }
                            }
                            //删除当前表格
                            for (var i = (rows.length-1); i >=rowIndex;i--) {
                                if (selectrow.objectPath == rows[i].objectPath&&(rows[i].control=="列"||rows[i].control=="表格")) {
                                    $('#addObjectPropertyList').datagrid('deleteRow',i);
                                }
                            }
                            rowIndex = rowIndex+downIndex;
                            $('#addObjectPropertyList').datagrid('insertRow',{
                                index:rowIndex,
                                row:selectrow
                            });
                            $('#addObjectPropertyList').datagrid('selectRow',rowIndex);
                            for (var i=0; i<tableRows.length;i++){
                                $('#addObjectPropertyList').datagrid('insertRow',{
                                    index:i+1+rowIndex,
                                    row:tableRows[i]
                                });
                            }
                        }else {
                            //删除当前表格
                            for (var i = (rows.length-1); i >=rowIndex;i--) {
                                if (selectrow.objectPath == rows[i].objectPath&&(rows[i].control=="列"||rows[i].control=="表格")) {
                                    $('#addObjectPropertyList').datagrid('deleteRow',i);
                                }
                            }
                            rowIndex ++;
                            $('#addObjectPropertyList').datagrid('insertRow',{
                                index:rowIndex,
                                row:selectrow
                            });
                            for (var i=0; i<tableRows.length;i++){
                                $('#addObjectPropertyList').datagrid('insertRow',{
                                    index:i+1+rowIndex,
                                    row:tableRows[i]
                                });
                            }
                        }
                    }else {
                        //删除当前表格
                        for (var i = (rows.length-1); i >=rowIndex;i--) {
                            if (selectrow.objectPath == rows[i].objectPath&&(rows[i].control=="列"||rows[i].control=="表格")) {
                                $('#addObjectPropertyList').datagrid('deleteRow',i);
                            }
                        }
                        rowIndex ++;
                        $('#addObjectPropertyList').datagrid('insertRow',{
                            index:rowIndex,
                            row:selectrow
                        });
                        for (var i=0; i<tableRows.length;i++){
                            $('#addObjectPropertyList').datagrid('insertRow',{
                                index:i+1+rowIndex,
                                row:tableRows[i]
                            });
                        }
                    }
                }else {
                    top.tip('当前数据不可以进行下移操作');
                }
            }
        }
    }

    //删除元数据性信息
    function deleteSelectionsOPIBtn(){
        //获取选中行数据
        var rows = $("#addObjectPropertyList").datagrid('getSelections');
        //获取所有数据
        var allRows = $("#addObjectPropertyList").datagrid('getRows');
        var obj = rows[0].objectPath;
        var successFlag = true;
        if(rows.length > 0){
            //弹出确认框
            top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmBatchDel"/>', function(r) {
                if (r) {
                    if (rows[0].control=="表格"){
                        //获取当前行的index
                        var index = $("#addObjectPropertyList").datagrid('getRowIndex',rows[0]);
                        //删除当前表格
                        for (var i = (allRows.length-1); i >=index;i--) {
                            if (obj == allRows[i].objectPath&&(allRows[i].control=="列"||allRows[i].control=="表格")) {
                                // 删除选择行
                                $('#addObjectPropertyList').datagrid('deleteRow',i);
                            }
                        }
                        if (successFlag){
                            top.tip("删除成功");
                        } else {
                            top.tip("删除失败");
                        }
                        //清除行焦点
                        $('#addObjectPropertyList').datagrid('clearSelections');
                    } else {
                        for(var i = (rows.length-1); i >=0;i-- ){
                            //获取当前行的index
                            var index = $("#addObjectPropertyList").datagrid('getRowIndex',rows[i]);
                                top.tip("删除成功");
                                // 删除选择行
                                $("#addObjectPropertyList").datagrid('deleteRow',index);
                                //清除行焦点
                                $('#addObjectPropertyList').datagrid('clearSelections');
                        }
                    }
                }
            });
        }else{
            top.tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
            return false;
        }
    }

    //重复选择实体对象判断
    function repeatRemove(objectPath) {
        var isRepeat = false;
        var rows = $("#addDataSourceObjecList").datagrid('getRows');
        for (var i = 0 ; i < rows.length ; i++) {
            if (rows[i].objectPath==objectPath){
                isRepeat = true;
                break;
            }
        }
        return isRepeat;
    }


    //对象移除修改，如果存在正在修改的行则结束该行修改
    function doDataSourceEdit(index) {
        dataEditingIndex = index;
        $('#addDataSourceObjecList').datagrid('selectRow', index).datagrid('beginEdit', index);
    }

    //属性移除修改，如果存在正在修改的行则结束该行修改
    function doObjectPropertyEdit(index) {
        propertyEditingIndex = index;
        var selectRow = $("#addObjectPropertyList").datagrid('getRows')[index];
        $('#addObjectPropertyList').datagrid('selectRow', index).datagrid('beginEdit', index);
        var readWriteAccessEd = $('#addObjectPropertyList').datagrid('getEditor', {index:index,field:"readWriteAccess"});
        var requiredEd = $('#addObjectPropertyList').datagrid('getEditor', {index:index,field:"required"});
        if (selectRow.control=="按钮"){
            $(readWriteAccessEd.target).combobox({disabled:true});
            $(readWriteAccessEd.target).combobox('setValue', '/');
            $(requiredEd.target).combobox({disabled:true});
            $(requiredEd.target).combobox('setValue', '/');
        }else if (selectRow.control=="表格"){
            $(readWriteAccessEd.target).combobox({disabled:true});
            $(readWriteAccessEd.target).combobox('setValue', '/');
        } else if (selectRow.control=="列") {
            $(readWriteAccessEd.target).combobox({disabled:true});
            $(readWriteAccessEd.target).combobox('setValue', '/');
        }
    }

    //对象单击结束修改
    function editDataSourceOver(index) {
        if (dataEditingIndex != undefined) {
            $('#addDataSourceObjecList').datagrid('selectRow', dataEditingIndex).datagrid('endEdit', dataEditingIndex);
            dataEditingIndex = undefined;
        } else {
            $('#addDataSourceObjecList').datagrid('selectRow', index).datagrid('endEdit', index);
        }
    }

    //属性单击结束修改
    function editObjectPropertyOver(index) {
        if (propertyEditingIndex != undefined) {
            $('#addObjectPropertyList').datagrid('selectRow', propertyEditingIndex).datagrid('endEdit', propertyEditingIndex);
            propertyEditingIndex = undefined;
        } else {
            $('#addObjectPropertyList').datagrid('selectRow', index).datagrid('endEdit', index);
        }
    }

    //判断名称是否重复
    function repeatName() {
        var tab = "${tebDto.id}";
        var name = $("#addTabTemplate_name").textbox("getValue");
        if (name==""){
            top.tip("页签模板名称不能为空");
            return false;
        }
        $.ajax({
            url : 'tabTemplateController.do?isRepeatName',
            type : 'post',
            data : {
                name:name,
                id:tab
            },
            cache : false,
            success : function(data) {
                var d = $.parseJSON(data);
                if (!d.success) {
                    top.tip("页签模板名称已存在，请重新填写");
                }else {
                    formSubmit();
                }
            }
        });
    }

    //数据提交
    function formSubmit() {
        debugger;
        var tab = "${tebDto.id}";
        var win = $.fn.lhgdialog("getSelectParentWin");
        //页签基础数据校验
        var isSubmit = dataValidate();
        if (!isSubmit){
            return false;
        }
        //获取当前实体对象所有元素数据
        var addDataSourceObjecListRows = $("#addDataSourceObjecList").datagrid('getRows');
        dataSourceObjectArray = new Array();
        var dataId = "";
        for (var i = 0 ; i < addDataSourceObjecListRows.length ; i++) {
            var dataSourceObject = new Object();
            dataSourceObject['tabId']=tab;
            dataSourceObject['id']=addDataSourceObjecListRows[i].id;
            dataSourceObject['objectPath']=addDataSourceObjecListRows[i].objectPath;
            dataSourceObject['projectModel']=addDataSourceObjecListRows[i].projectModel;
            dataSourceObject['resultSql']=addDataSourceObjecListRows[i].resultSql;
            dataSourceObject['dataToInterface']=addDataSourceObjecListRows[i].dataToInterface;
            dataSourceObject['objectModelProperty']=addDataSourceObjecListRows[i].objectModelProperty;
            dataSourceObjectArray.push(dataSourceObject);
            dataId = addDataSourceObjecListRows[i].id;
        }
        //获取当前所有元素数据
        var ObjectPropertyRows = $("#addObjectPropertyList").datagrid('getRows');
        objectPropertyArray = new Array();
        for (var i = 0 ; i < ObjectPropertyRows.length ; i++) {
            var objectProperty = new Object();
            //初始化赋值
            objectProperty['id']=ObjectPropertyRows[i].id;
            objectProperty['dataSourceId']=ObjectPropertyRows[i].dataSourceId;
            objectProperty['objectPath']=ObjectPropertyRows[i].objectPath;
            objectProperty['propertyValue']=ObjectPropertyRows[i].propertyValue;
            objectProperty['control']=submitValue(ObjectPropertyRows[i].control);
            objectProperty['propertyName']=ObjectPropertyRows[i].propertyName;
            objectProperty['display']=ObjectPropertyRows[i].display;
            objectProperty['readWriteAccess']=submitValue(ObjectPropertyRows[i].readWriteAccess);
            if (ObjectPropertyRows[i].required=="是"){
                objectProperty['required'] = true;
            } else  {
                objectProperty['required'] = false;
            }
            objectProperty['format']=ObjectPropertyRows[i].format;
            objectProperty['operationEvent']=ObjectPropertyRows[i].operationEvent;
            objectProperty['orderNumber'] = i;
            objectPropertyArray.push(objectProperty);
        }
        var displayUsageValue = $("#addTabTemplate_displayUsage").combobox('getValue');
        //对象选择需要填写数据
        if (displayUsageValue=="1"){
            if (dataSourceObjectArray.length>0&&objectPropertyArray.length>0) {
                //提交页签模板数据
                 tabId = tabTemplateInfoSubmit("1");
                 var mainId = tabId;
                //保存信息
                $.ajax({
                    type : 'post',
                    url : 'dataSourceObjectController.do?updateOrReviseInfo',
                    data : {
                        tabId:mainId,
                        dataSourceObjectList: JSON.stringify(dataSourceObjectArray),
                        objectPropertyList : JSON.stringify(objectPropertyArray)
                    },
                    cache : false,
                    async:false,
                    success : function (data) {
                        var d = $.parseJSON(data);
                        if (!d.success) {
                            top.tip("数据保存失败");
                            return;
                        }else{
                            updateCheck();
                        }
                    }
                });
            }else{
                top.tip("对象数据与视图编辑需要填写数据");
                return;
            }
            top.tip("${msg}");
            win.searchTabTemplateManage();
            $.fn.lhgdialog("closeSelect");
        }else{
            top.tip("${msg}");
            //提交页签模板数据
            tabTemplateInfoSubmit("1");
            win.searchTabTemplateManage();
            $.fn.lhgdialog("closeSelect");
        }
    }

    //数据转换
    function submitValue(value) {
        var controlArray = [{value: '文本框', text: '文本框'},{value: '多行文本框',text: '多行文本框'},{value: '单选',text: '单选'},
            {value: '选人框', text: '选人框'},{value: '日期框',text: '日期框'},{value: '下拉框',text: '下拉框'},
            {value: '表格', text: '表格'},{value: '列',text: '列'},{value: '按钮',text: '按钮'},{value: '选择框',text: '选择框'},{value: '可编辑下拉框',text: '可编辑下拉框'}];
        var accessArray = [{value: '编制', text: '编制'},{value: '编制&启动',text: '编制&启动'},{value: '启动',text: '启动'},{value: '/',text: '/'}];
        for (var i = 0 ; i < accessArray.length ; i++) {
            if (value ==accessArray[i].value){
                return i;
            }
        }
        for (var i = 0 ; i < controlArray.length ; i++) {
            if (value ==controlArray[i].value){
                return i;
            }
        }
    }

    //取消按钮H回滚事件
   function updateCheck(){
       //获取当前实体对象所有元素数据
       var addDataSourceObjecListRows = $("#addDataSourceObjecList").datagrid('getRows');
       var objArray = new Array();
       //回滚数据
       if (addDataSourceObjecListRows.length>0){
           for (var i=0; i<addDataSourceObjecListRows.length;i++){
               var obj = new Object();
               obj["id"] = addDataSourceObjecListRows[i].id;
               obj["status"] = "0";
               objArray.push(obj);
           }
       }
       for (var i=0;i<dataSourceObjectRollbackArray.length;i++){
           objArray.push(dataSourceObjectRollbackArray[i]);
       }
       $.ajax({
           type : 'post',
           url : 'dataSourceObjectController.do?dataRollBack',
           data : {
               dataSourceObjectList: JSON.stringify(objArray),
               objectPropertyList : JSON.stringify(objectPropertyRollbackArray)
           },
           cache : false,
           async:false,
           success : function (data) {
               var d = $.parseJSON(data);
               if (!d.success) {
                   top.tip("数据保存失败");
                   return;
               }
           }
       });
   }
</script>
</body>