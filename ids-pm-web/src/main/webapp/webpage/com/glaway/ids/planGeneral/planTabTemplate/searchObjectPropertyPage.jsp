<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery_iframe,easyui_iframe,tools"></t:base>
<style type="text/css">

</style>

<div class="easyui-layout" fit="true" style="margin-top: 20px;height: 600px">
    <fd:combobox  title="控件" name="control" id="control" textField="text" editable="false" valueField="value"
                  onChange="changeControl" panelMaxHeight="90" panelHeight="90"></fd:combobox>
    <div id="obj">
        <div>
            <fd:combobox  title="对象类路径" name="objPath" id="objPath" textField="objectPath" editable="false" valueField="id"
                          url="dataSourceObjectController.do?getAllDataSourceObject&tabId=${tabId}" onChange="changeObjPath" panelMaxHeight="150" panelHeight="150"></fd:combobox>
        </div>
        <div id="propertyDiv">
            <fd:combobox  title="对象属性" name="property" id="property" textField="name" editable="false" valueField="value" panelMaxHeight="90" panelHeight="90"></fd:combobox>
        </div>
        <div id="columPropertyDiv">
            <fd:combobox  title="列表属性" name="columProperty" id="columProperty" textField="name" editable="false" valueField="value" panelMaxHeight="90" multiple="true" panelHeight="90"></fd:combobox>
        </div>
    </div>
    <fd:inputText title="属性名称" name="controlName" id="controlName" required="true" />
</div>
<input type="button" id="btn_sub" style="display: none;" onclick="formSubmit();">
<script>
    $(document).ready(function (){
        setTimeout(function(){
            var array =[{value: '文本框', text: '文本框'},{value: '多行文本框',text: '多行文本框'},{value: '单选',text: '单选'},
                {value: '选人框', text: '选人框'},{value: '日期框',text: '日期框'},{value: '下拉框',text: '下拉框'},
                {value: '表格', text: '表格'},{value: '按钮',text: '按钮'},{value: '选择框',text: '选择框'},{value: '可编辑下拉框',text: '可编辑下拉框'}];
            $('#control').combobox('loadData', array);

            var win = $.fn.lhgdialog("getSelectParentWin");
            if (win.modfiy) {
                //获取当前的选择行
                var selectrow = win.$('#addObjectPropertyList').datagrid('getSelected');
                var rows = win.$("#addObjectPropertyList").datagrid('getRows');
                if (selectrow.control=="按钮") {
                    $("#control").combobox("setValue", "按钮");
                    $("#controlName").textbox("setValue", selectrow.propertyName);
                } else if (selectrow.control!="表格"){
                    $("#objPath").combobox("setValue", selectrow.objectPath);
                    $("#control").combobox("setValue", selectrow.control);
                    $("#property").combobox("setValue", selectrow.propertyValue);
                    $("#controlName").textbox("setValue", selectrow.propertyName);
                } else if (selectrow.control=="表格"){
                    $("#control").combobox("setValue", selectrow.control);
                    $("#controlName").textbox("setValue", selectrow.propertyName);
                    $("#objPath").combobox("setValue", selectrow.objectPath);
                    var propetyValue = new Array();
                    for (var i=0; i<rows.length;i++){
                        if (rows[i].objectPath==selectrow.objectPath&&rows[i].control=="列") {
                            propetyValue.push(rows[i].propertyValue);
                        }
                    }
                    $("#columProperty").combobox("setValues", propetyValue);
                }
            }
            changeControl();
        },300);
    })

    function changeControl() {
        var controlValue = $("#control").combobox("getValue");
        if (controlValue=="按钮") {
            $("#obj").hide();
        }else{
            $("#obj").show();
        }

        if (controlValue=="表格") {
            $("#propertyDiv").hide();
            $("#columPropertyDiv").show();
        }else {
            $("#columPropertyDiv").hide();
            $("#propertyDiv").show();
        }
    }

    function changeObjPath() {
        var array = new Array();
        $('#property').combobox('setValues', array);
        $('#columProperty').combobox('setValues', array);
        //获取对象类路径
        var objectPath = $("#objPath").combobox("getText");
        var win = $.fn.lhgdialog("getSelectParentWin");
        var rows = win.$("#addDataSourceObjecList").datagrid('getRows');
        var objectModelProperty = "";
        for (var i = 0 ; i < rows.length ; i++) {
            if (objectPath==rows[i].objectPath) {
                objectModelProperty = rows[i].objectModelProperty;
                break;
            }
        }
        var objectModelPropertyArray = objectModelProperty.split(",");
        var array = new Array();
        for (var i = 0 ; i < objectModelPropertyArray.length ; i++) {
            var obj = new Object();
            obj['name'] = objectModelPropertyArray[i];
            obj['value'] = objectModelPropertyArray[i];
            array.push(obj);
        }
        $('#property').combobox('loadData', array);
        $('#columProperty').combobox('loadData', array);
    }

    //数据提交
    function formSubmit() {
        var win = $.fn.lhgdialog("getSelectParentWin");
        var dataSourceId = $("#objPath").combobox("getValue");
        var objectPath = $("#objPath").combobox("getText");
        var propertyValue = $("#property").combobox("getValue");
        var controlValue = $("#control").combobox("getText");
        var columProperty = $("#columProperty").combobox("getText");
        var controlName = $("#controlName").textbox("getValue");
        //针对表格Id被覆盖
        if (dataSourceId==objectPath){
            var dataRows = win.$("#addDataSourceObjecList").datagrid('getRows');
            for (var i = 0 ; i < dataRows.length ; i++) {
                if (objectPath==dataRows[i].objectPath) {
                    dataSourceId = dataRows[i].id;
                    break;
                }
            }
        }

        if (controlName==""){
            top.tip("属性名称不能为空")
            return;
        }

        var rows = win.$("#addObjectPropertyList").datagrid('getRows');
        var oldArray = new Array();
        var newArray = new Array();
        //元数据属性分隔
        var propety = columProperty.split(",");
        //获取当前的选择行
        var selectrow = win.$('#addObjectPropertyList').datagrid('getSelected');
        //获取选择的数据
        var rowIndex = win.$('#addObjectPropertyList').datagrid('getRowIndex',selectrow);
        //获取当前数据
        var obj = win.objectPropertyArray[rowIndex];

        //校验数据是否重复
        if (controlValue!="按钮") {
            if (controlValue!="表格") {
                if (propertyValue==""||propertyValue==undefined) {
                    top.tip("属性不能为空");
                    return;
                }
                for (var i=0; i<rows.length; i++){
                    if (rowIndex!=i&&rows[i].objectPath==objectPath&&rows[i].propertyValue==propertyValue){
                        top.tip("该属性已经存在，请重新选择数据");
                        return;
                    }
                }
            }else {
                for (var i=0; i<rows.length; i++){
                    if (rowIndex!=i&&rows[i].objectPath==objectPath&&rows[i].control=="表格"){
                        top.tip("该表格已经存在，请重新选择数据");
                        return;
                    }
                }
            }
        }

        if (controlValue!="按钮") {
            if (controlValue=="表格"){
                if (objectPath==""||columProperty==""){
                    top.tip("对象类路径与列表属性不能为空")
                    return;
                }else {
                    //表格修改
                    if (win.modfiy){
                        //获取表格前置数据
                        if (rowIndex>0){
                            for (var i=0;i<rowIndex;i++){
                                newArray.push(rows[i]);
                            }
                        }
                        //获取表格后置数据
                        for (var i=rowIndex+1;i<rows.length;i++){
                            if (obj.objectPath==rows[i].objectPath&&rows[i].control=="列")  {
                                var id = rows[i].id;
                                if (id){
                                    deleteObj(id);
                                }
                            }else {
                                oldArray.push(rows[i]);
                            }
                        }

                        var obj = addObj(rows[rowIndex].id,dataSourceId,objectPath,'',controlValue,controlName,"/","/","是","","");
                        newArray.push(obj);
                        //循环遍历列表值
                        for (var i=0;i<propety.length;i++){
                            var proObj = addObj('',dataSourceId,objectPath,propety[i],"列","","","","是","","");
                            newArray.push(proObj);
                        }
                        if (oldArray.length>0){
                            for (var i=0;i<oldArray.length;i++){
                                newArray.push(oldArray[i]);
                            }
                        }
                        win.objectPropertyArray = newArray;
                        //数据加载
                        win.$("#addObjectPropertyList").datagrid('loadData',win.objectPropertyArray);
                        //清除行焦点
                        win.$('#addObjectPropertyList').datagrid('clearSelections');
                    } else{
                        //添加表格属性值
                        win.addObjectProperty(dataSourceId, objectPath, '',controlName, controlValue,"","/","是")
                        //循环遍历列表值
                        for (var i=0;i<propety.length;i++){
                            win.addObjectProperty(dataSourceId, objectPath, propety[i],"","列","","","是")
                        }
                    }
                }
            }

            if (controlValue!="表格") {
                if (objectPath==""||propertyValue==""){
                    top.tip("对象类路径与对象属性不能为空")
                    return;
                }else {
                    //非按钮非表格修改
                    if (win.modfiy){
                        if (obj.control=="表格") {
                            for (var i = (rows.length-1); i >rowIndex;i--) {
                                if (obj.objectPath == rows[i].objectPath && rows[i].control == "列") {
                                    var id = rows[i].id;
                                    if (id) {
                                        deleteObj(id);
                                    }
                                    //获取当前行的index
                                    var indexDel = win.$("#addObjectPropertyList").datagrid('getRowIndex',rows[i]);
                                    // 删除选择行
                                    win.$("#addObjectPropertyList").datagrid('deleteRow',indexDel);
                                }
                            }
                        }

                        var obj = win.objectPropertyArray[rowIndex];
                        obj['dataSourceId']=dataSourceId;
                        obj['objectPath']=objectPath;
                        obj['propertyValue']=propertyValue;
                        if(controlName!=""){
                            obj['propertyName']=controlName;
                        }
                        obj['control']=controlValue;
                        //数据加载
                        win.$("#addObjectPropertyList").datagrid('loadData',win.objectPropertyArray);

                    } else{
                        win.addObjectProperty(dataSourceId, objectPath, propertyValue, controlName,controlValue,"","","")
                    }
                }
            }
        }else {
            var anId = win.$("#addDataSourceObjecList").datagrid('getRows')[0].id;
            //按钮修改
            if (win.modfiy){
                if (obj.control=="表格") {
                    for (var i = (rows.length-1); i >=rowIndex;i--) {
                        if (obj.objectPath == rows[i].objectPath && rows[i].control == "列") {
                            var id = rows[i].id;
                            if (id) {
                                deleteObj(id);
                            }
                            //获取当前行的index
                            var indexDel = win.$("#addObjectPropertyList").datagrid('getRowIndex',rows[i]);
                            // 删除选择行
                            win.$("#addObjectPropertyList").datagrid('deleteRow',indexDel);
                        }
                    }
                }

                var obj = win.objectPropertyArray[rowIndex];
                obj['dataSourceId']=anId;
                obj['objectPath']="/";
                obj['propertyValue']="/";
                if(controlName!=""){
                    obj['propertyName']=controlName;
                }
                obj['control']=controlValue;
                //数据加载
                win.$("#addObjectPropertyList").datagrid('loadData',win.objectPropertyArray);
            } else{
                win.addObjectProperty(anId, "/", "/", controlName,controlValue,"","/","/")
            }
        }
        //关闭窗口
        setTimeout(function(){
            $.fn.lhgdialog("closeSelect");
        },300);
    }

    //后台执行删除元数据
    function deleteObj(id) {
        $.ajax({
            url : 'objectPropertyController.do?doDelete',
            type : 'post',
            data : {
                id: id
            },
            cache : false,
            success : function(data) {
                var d = $.parseJSON(data);
                if (!d.success) {
                    top.tip("操作失败");
                    return false;
                }
            }
        });
    }

    function addObj(id,dataSourceId,objectPath,propertyValue,control,propertyName,display,readWriteAccess,required,format,operationEvent) {
        var objectProperty = new Object();
        //初始化赋值
        objectProperty['id']=id;
        objectProperty['dataSourceId']=dataSourceId;
        objectProperty['objectPath']=objectPath;
        objectProperty['propertyValue']=propertyValue;
        objectProperty['control']=control;
        objectProperty['propertyName']=propertyName;
        objectProperty['display']=display;
        objectProperty['readWriteAccess']=readWriteAccess;
        objectProperty['required']=required;
        objectProperty['format']=format;
        objectProperty['operationEvent']=operationEvent;
        return objectProperty;
    }
</script>