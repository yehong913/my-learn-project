<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>页签组合模板</title>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow: hidden;">

<div class="easyui-layout" fit="true">
    <input type = "hidden" id = "tabCbTemplateId" name = "tabCbTemplateId" value = "${tabCbTemplateId}"/>
    <input id="acessList" name="acessList" type="hidden" value="${acessList}">
    <input id="tabTemplates" name="tabTemplates" type="hidden" value="${tabTemplates}">
    <input id="alltabTemplates" name="alltabTemplates" type="hidden" value="${alltabTemplates}">
    <input id="type" name="type" type="hidden" value="${type}">

    <div region="north" style="width: 100%;height:29%;" title="" id="north_page_panel">
        <fd:inputText id="cbName" name="cbName" title="{com.glaway.ids.pm.project.plantemplate.name}" required="true" value = "${template_.name}"/>
        <fd:combobox id="templateName" url="tabCombinationTemplateController.do?getTemplateCombox" queryMode="in" multiple="false"
                     title="{com.glaway.ids.pm.general.actType}"  textField="name" valueField="id" panelMaxHeight="100"
                     name="templateName" selectedValue="${template_.activityId}" required="true" editable="false"/>
        <fd:inputTextArea id="remake" name="remake" title="{com.glaway.ids.common.lable.remark}" value="${template_.remake}"/>
    </div>

    <div region="center" style="padding: 1px; width: 100%;" title="" id="center_page_panel">
        <fd:linkbutton id="addCbTemplateBtn" onclick="addCbTemplat()" value="{com.glaway.ids.common.btn.create}"
                       iconCls="basis ui-icon-plus" />
        <fd:linkbutton id="upCbTemplateBtn" onclick="upCbTemplat()" value="{com.glaway.ids.common.btn.moveNodeUp}"
                       iconCls="basis ui-icon-up" />
        <fd:linkbutton id="downCbTemplateBtn" onclick="downCbTemplat()" value="{com.glaway.ids.common.btn.moveNodeDown}"
                       iconCls="basis ui-icon-down" />
        <fd:linkbutton onclick="deleteSelectionsCbTemplate()" iconCls="basis ui-icon-minus"
                       value="{com.glaway.ids.common.btn.remove}" id="deleteCbTemplateBtn"/>
    </div>

    <div region="south" style="padding: 1px; width: 100%;height:66%;" title="" id="south_page_panel">
        <fd:datagrid id="CbTemplateInfoList" pagination="false" checkOnSelect="true" toolbar="" url="" height="400"  checkbox="false"
                     fitColumns="true" idField="id" fit="true" onDblClickRow="modify"  onClickFunName="edit">
            <fd:dgCol title="{com.glaway.ids.pm.general.name}" field="name" width="80"  sortable="false" align="center" editor="{type:'textbox'}"></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.general.tabName}" field="tabName" width="80" align="center" sortable="false" formatterFunName="formatterTab" editor="{type:'combobox',options:{
				                    url:'tabCombinationTemplateController.do?getAllTabTemplate',
						            method:'get',
						            editable:false,
						            panelMaxHeight:100,
						            valueField:'id',
						            textField:'tabName',
						            onChange:onChanges
								}}"></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.general.tabType}" field="tabType" width="80" align="center" sortable="false"></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.version}" field="bizVersion" width="80" align="center" sortable="false"></fd:dgCol>
            <fd:dgCol title="{com.glaway.ids.pm.general.displayAccess}" field="displayAccess" width="80" align="center" sortable="false" editor="{type:'textbox'}"></fd:dgCol>
        </fd:datagrid>
    </div>

    <script type="text/javascript">
        var CbTemplateInfo = new Object();
        var CbTemplateInfoArr = new Array();   //页面加载时组合模板信息
        var CbTemplateInfos = new Array();   //组合模板信息保存
        var editingindex;//正在修改的行

        $(document).ready(function (){
            var type = $('#type').val();
            if (type == "copy") {
                setTimeout(function(){
                    $('input', $('#cbName').next('span')).focus();
                },600);
            }
            setTimeout(function(){
                $("#templateName").combobox('setValue','${template_.activityId}');
                $("#templateName").combobox('setText','${template_.templateName}');
            },500);
            var tabCbTemplateId = $("#tabCbTemplateId").val();
            //根据id获取组合模板信息
            if (tabCbTemplateId != '') {
                $.ajax({
                    type : 'get',
                    url : 'tabCombinationTemplateController.do?getCombTemplateInfos&tabCbTemplateId='+tabCbTemplateId,
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            //将组合模板信息放置集合遍历
                            var infos = d.obj;
                            for (var i = 0; i < infos.length; i++) {
                                var info = infos[i];
                                var CbTemplateInfo = new Object();
                                CbTemplateInfo['id']=info.id;
                                CbTemplateInfo['name']=info.name;
                                CbTemplateInfo['typeId']=info.typeId;
                                CbTemplateInfo['tabName']=info.tabName;
                                CbTemplateInfo['tabType']=info.tabType;
                                CbTemplateInfo['displayAccess']=info.displayAccess;
                                CbTemplateInfo['bizVersion']=info.bizVersion;
                                CbTemplateInfoArr.push(CbTemplateInfo);
                            }
                            reoload();
                        }
                    }
                });
            } else {
                reoload();
            }
        });

        function reoload(){
            if (CbTemplateInfoArr.length > 0) {
                $("#CbTemplateInfoList").datagrid('loadData',CbTemplateInfoArr);
            } else {
                $("#CbTemplateInfoList").datagrid('loadData',new Array());
            }

        }

        //新增一条组合模板信息
        function addCbTemplat() {
            if (editingindex != undefined) {
                edit(editingindex);
            }
            var CbTemplateInfo = new Object();
            CbTemplateInfo['id']=Math.random();
            CbTemplateInfo['name']='';
            CbTemplateInfo['typeId']='';
            CbTemplateInfo['tabName']='';
            CbTemplateInfo['tabType']='';
            CbTemplateInfo['displayAccess']='';
            CbTemplateInfo['bizVersion']='';
            CbTemplateInfoArr.push(CbTemplateInfo);
            reoload();
        }

        //移除选中的组合模板信息
        function deleteSelectionsCbTemplate(){
            //弹出确认框
            var rows = $("#CbTemplateInfoList").datagrid('getSelections');
            if(rows.length > 0){
                top.Alert.confirm('<spring:message code="com.glaway.ids.pm.general.confirmDel"/>', function(r) {
                    if (r) {
                        for(var i = (rows.length-1); i >=0;i-- ){
                            var index = $("#CbTemplateInfoList").datagrid('getRowIndex',rows[i].id);
                            $("#CbTemplateInfoList").datagrid('deleteRow',index);
                        }
                    }
                });
            } else {
                top.tip('<spring:message code="com.glaway.ids.pm.general.choseData"/>');
                return false;
            }
        }

        //修改组合模板信息
        function modify() {
            var rows = $("#CbTemplateInfoList").datagrid('getSelections');
            if(rows.length > 0){
                for(var i = (rows.length-1); i >=0;i-- ){
                    var index = $("#CbTemplateInfoList").datagrid('getRowIndex',rows[i].id);
                    editingindex = index;
                    $('#CbTemplateInfoList').datagrid('selectRow', index).datagrid(
                        'beginEdit', index);
                }
            }else{
                top.tip('<spring:message code="com.glaway.ids.pm.general.choseData"/>');
                return false;
            }
        }

        //行数据上移
        function upCbTemplat() {
            var selectrow = $('#CbTemplateInfoList').datagrid('getSelected');
            var rowIndex = $('#CbTemplateInfoList').datagrid('getRowIndex',selectrow);
            var rowlength = $('#CbTemplateInfoList').datagrid('getRows').length;
            if (selectrow == null) {
                top.tip('<spring:message code="com.glaway.ids.pm.general.choseData"/>');
            } else {
                //结束行编辑
                edit(rowIndex);
                if (rowIndex == 0) {
                    top.tip('<spring:message code="com.glaway.ids.pm.general.top"/>');
                } else {
                    $('#CbTemplateInfoList').datagrid('deleteRow',rowIndex);//删除一行
                    rowIndex--;
                    $('#CbTemplateInfoList').datagrid('insertRow',{
                        index:rowIndex,
                        row:selectrow
                    });
                    $('#CbTemplateInfoList').datagrid('selectRow',rowIndex);
                }
            }
        }

        //行数据下移
        function downCbTemplat() {
            var selectrow = $('#CbTemplateInfoList').datagrid('getSelected');
            var rowIndex = $('#CbTemplateInfoList').datagrid('getRowIndex',selectrow);
            var rowlength = $('#CbTemplateInfoList').datagrid('getRows').length;
            if (selectrow == null) {
                top.tip('<spring:message code="com.glaway.ids.pm.general.choseData"/>');
            } else {
                //结束行编辑
                edit(rowIndex);
                if (rowIndex == rowlength-1) {
                    top.tip('<spring:message code="com.glaway.ids.pm.general.down"/>');
                } else {
                    $('#CbTemplateInfoList').datagrid('deleteRow',rowIndex);//删除一行
                    rowIndex++;
                    $('#CbTemplateInfoList').datagrid('insertRow',{
                        index:rowIndex,
                        row:selectrow
                    });
                    $('#CbTemplateInfoList').datagrid('selectRow',rowIndex);
                }
            }
        }

        //移除修改，如果存在正在修改的行则结束该行修改
        function edit(index) {
            if (editingindex != undefined) {
                $('#CbTemplateInfoList').datagrid('selectRow', editingindex).datagrid(
                    'endEdit', editingindex);
                editingindex = undefined;
            } else {
                $('#CbTemplateInfoList').datagrid('selectRow', index).datagrid(
                    'endEdit', index);
            }

        }

        //页签模板显示
        function formatterTab(val, row, index) {
            var tabTemplates = eval('${alltabTemplates}');
            for (var i = 0; i < tabTemplates.length; i++) {
                if (val == tabTemplates[i].id) {
                    return tabTemplates[i].name;
                }
                if (val == tabTemplates[i].name) {
                    return tabTemplates[i].name;
                }
            }
        }

        //页签模板下拉页签类型联动
        function onChanges(newValue, oldValue) {
            if(newValue != undefined){
                var rows = $("#CbTemplateInfoList").datagrid('getSelections');
                var typeId = '';
                var tabType = '';
                var tabName = '';
                var bizVersion = '';
                var tabTemplates = eval('${tabTemplates}');
                for (var i = 0; i < tabTemplates.length; i++) {
                    if (newValue == tabTemplates[i].id) {
                        typeId = tabTemplates[i].id;
                        tabName = tabTemplates[i].name;
                        tabType = tabTemplates[i].tabType;
                        bizVersion = tabTemplates[i].bizVersion;
                        break;
                    }
                    if (newValue == tabTemplates[i].name) {
                        typeId = tabTemplates[i].id;
                        tabName = tabTemplates[i].name;
                        tabType = tabTemplates[i].tabType;
                        bizVersion = tabTemplates[i].bizVersion;
                        break;
                    }
                }
                //修改页签类型
                rows[0]['tabType']=tabType;
                rows[0]['typeId']=typeId;
                rows[0]['bizVersion']=bizVersion;
            }

        }

        //新增页签组合模板
        function addtabCbTemplate() {
            if (editingindex != undefined) {
                edit(editingindex);
            }
            var cbName = $("#cbName").val();
            var remake = $("#remake").val();
            var activityId = $("#templateName").combobox('getValue');
            var templateName = $("#templateName").combobox('getText');
            var tempName = $("#templateName").combobox('getText');
            if(cbName == "" || cbName == null){
                top.tip('<spring:message code="com.glaway.ids.pm.general.checkTabName"/>');
                return false;
            }
            if(templateName == "" || templateName == null){
                top.tip('<spring:message code="com.glaway.ids.pm.general.templateName"/>');
                return false;
            }
            //获取dataGrid所有数据
            CbTemplateInfos = new Array();
            var rows = $('#CbTemplateInfoList').datagrid('getRows');
            for (var i = 0 ; i < rows.length ; i++) {
                CbTemplateInfo = new Object();
                if (rows[i].name == '' || rows[i].name == undefined
                    || rows[i].tabName == '' || rows[i].tabName == undefined
                    || rows[i].tabType == '' || rows[i].tabType == undefined) {
                    $('#CbTemplateInfoList').datagrid('selectRow',i);
                    top.tip('<spring:message code="com.glaway.ids.pm.general.checkDate"/>');
                    return;
                } else {
                    CbTemplateInfo['name'] = rows[i].name;
                    CbTemplateInfo['typeId'] = rows[i].typeId;
                    CbTemplateInfo['tabName'] = rows[i].tabName;
                    CbTemplateInfo['tabType'] = rows[i].tabType;
                    CbTemplateInfo['displayAccess'] = rows[i].displayAccess;
                }
                CbTemplateInfos.push(CbTemplateInfo);
            }

            if (CbTemplateInfos.length > 0) {
                $.ajax({
                    type : 'get',
                    url : 'tabCombinationTemplateController.do?isActivityTypeManageUse&id='+activityId,
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            //直接保存页签组合模板信息
                            $.ajax({
                                type : 'post',
                                url : 'tabCombinationTemplateController.do?saveTabCbTemplateInfo',
                                data : {
                                    name : cbName,
                                    activityId : activityId,
                                    templateName : templateName,
                                    remake : remake,
                                    cbTemplateList : JSON.stringify(CbTemplateInfos)
                                },
                                cache : false,
                                success : function (data) {
                                    var d = $.parseJSON(data);
                                    top.tip(d.msg);
                                    if (d.success) {
                                        var win = $.fn.lhgdialog("getSelectParentWin");
                                        win.reloadTable();
                                        $.fn.lhgdialog("closeSelect");
                                    }
                                }

                            });
                        } else {
                            top.tip(d.msg);
                        }
                    }
                });

            } else {
                top.tip('<spring:message code="com.glaway.ids.pm.general.checkDateGrid"/>');
            }
        }

        //修改页签组合模板
        function updatetabCbTemplate(method) {
            if (editingindex != undefined) {
                edit(editingindex);
            }
            var tabCbTemplateId = $("#tabCbTemplateId").val();//组合页签模板id
            var cbName = $("#cbName").val();
            var remake = $("#remake").val();
            var activityId = $("#templateName").combobox('getValue');
            var templateName = $("#templateName").combobox('getText');
            if(cbName == "" || cbName == null){
                top.tip('<spring:message code="com.glaway.ids.pm.general.checkTabName"/>');
                return false;
            }
            if(templateName == "" || templateName == null){
                top.tip('<spring:message code="com.glaway.ids.pm.general.templateName"/>');
                return false;
            }
            //获取dataGrid所有数据
            var rows = $('#CbTemplateInfoList').datagrid('getRows');
            for (var i = 0 ; i < rows.length ; i++) {
                CbTemplateInfo = new Object();
                if (rows[i].name == '' || rows[i].name == undefined
                    || rows[i].tabName == '' || rows[i].tabName == undefined
                    || rows[i].tabType == '' || rows[i].tabType == undefined) {
                    $('#CbTemplateInfoList').datagrid('selectRow',i);
                    top.tip('<spring:message code="com.glaway.ids.pm.general.checkDate"/>');
                    return;
                } else {
                    CbTemplateInfo['name'] = rows[i].name;
                    CbTemplateInfo['typeId'] = rows[i].typeId;
                    CbTemplateInfo['tabName'] = rows[i].tabName;
                    CbTemplateInfo['tabType'] = rows[i].tabType;
                    CbTemplateInfo['displayAccess'] = rows[i].displayAccess;
                }
                CbTemplateInfos.push(CbTemplateInfo);
            }

            if (CbTemplateInfos.length > 0) {
                $.ajax({
                    type : 'get',
                    url : 'tabCombinationTemplateController.do?isActivityTypeManageUse',
                    data : {
                        id : activityId,
                        templateId : tabCbTemplateId
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            $.ajax({
                                type : 'post',
                                url : 'tabCombinationTemplateController.do?updateTabCbTemplateInfo',
                                data : {
                                    id : tabCbTemplateId,
                                    name : cbName,
                                    activityId : activityId,
                                    templateName : templateName,
                                    remake : remake,
                                    cbTemplateList : JSON.stringify(CbTemplateInfos),
                                    method : method
                                },
                                cache : false,
                                success : function (data) {
                                    var d = $.parseJSON(data);
                                    top.tip(d.msg);
                                    if (d.success) {
                                        var win = $.fn.lhgdialog("getSelectParentWin");
                                        win.reloadTable();
                                        $.fn.lhgdialog("closeSelect");
                                    }
                                }

                            });
                        } else {
                            top.tip(d.msg);
                        }
                    }
                });
            } else {
                top.tip('<spring:message code="com.glaway.ids.pm.general.checkDateGrid"/>');
            }


        }

        //复制页签组合模板
        function doCopyTemplate() {
            if (editingindex != undefined) {
                edit(editingindex);
            }
            var tabCbTemplateId = $("#tabCbTemplateId").val();//组合页签模板id
            var cbName = $("#cbName").val();
            var remake = $("#remake").val();
            var activityId = $("#templateName").combobox('getValue');
            var templateName = $("#templateName").combobox('getText');
            if(cbName == "" || cbName == null){
                top.tip('<spring:message code="com.glaway.ids.pm.general.checkTabName"/>');
                return false;
            }
            if(templateName == "" || templateName == null){
                top.tip('<spring:message code="com.glaway.ids.pm.general.templateName"/>');
                return false;
            }
            //获取dataGrid所有数据
            var rows = $('#CbTemplateInfoList').datagrid('getRows');
            for (var i = 0 ; i < rows.length ; i++) {
                CbTemplateInfo = new Object();
                if (rows[i].name == '' || rows[i].name == undefined
                    || rows[i].tabName == '' || rows[i].tabName == undefined
                    || rows[i].tabType == '' || rows[i].tabType == undefined) {
                    $('#CbTemplateInfoList').datagrid('selectRow',i);
                    top.tip('<spring:message code="com.glaway.ids.pm.general.checkDate"/>');
                    return;
                } else {
                    CbTemplateInfo['name'] = rows[i].name;
                    CbTemplateInfo['typeId'] = rows[i].typeId;
                    CbTemplateInfo['tabName'] = rows[i].tabName;
                    CbTemplateInfo['tabType'] = rows[i].tabType;
                    CbTemplateInfo['displayAccess'] = rows[i].displayAccess;
                }
                CbTemplateInfos.push(CbTemplateInfo);
            }

            if (CbTemplateInfos.length > 0) {
                //检查模板类型下是否存在页签组合模板
                $.ajax({
                    type: 'get',
                    url: 'tabCombinationTemplateController.do?isActivityTypeManageUse&id=' + activityId,
                    cache: false,
                    success: function (data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            //直接保存页签组合模板信息
                            $.ajax({
                                type : 'post',
                                url : 'tabCombinationTemplateController.do?copyTabCbTemplateInfo',
                                data : {
                                    name : cbName,
                                    activityId : activityId,
                                    templateName : templateName,
                                    remake : remake,
                                    cbTemplateList : JSON.stringify(CbTemplateInfos)
                                },
                                cache : false,
                                success : function (data) {
                                    var d = $.parseJSON(data);
                                    top.tip(d.msg);
                                    if (d.success) {
                                        var win = $.fn.lhgdialog("getSelectParentWin");
                                        win.reloadTable();
                                        $.fn.lhgdialog("closeSelect");
                                    }
                                }

                            });
                        } else {
                            top.tip(d.msg);
                        }
                    }
                });
            } else {
                top.tip('<spring:message code="com.glaway.ids.pm.general.checkDateGrid"/>');
            }
        }

    </script>
</div>
</body>
</html>
