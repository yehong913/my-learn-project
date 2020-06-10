<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>活动类型配置</title>
</head>
<body style="overflow: hidden;">
<script>
    function addActivityTypeDialog(title, url, id, width, height) {
        createDialog('addActivityTypeDialog', url);
    }

    // 批量删除
    function deleteActivityBatch() {
        var rows = $('#ActivityTypeList').datagrid('getSelections');
        var ids = [];
        if (rows.length > 0) {
        	
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i].id);
            }

            $.ajax({
                url: 'activityTypeManageController.do?deleteBatchBeforeCheckDate',
                type: 'post',
                data: {
                    ids: ids.join(',')
                },
                cache: false,
                success: function (data) {               	
                    var d = $.parseJSON(data);
                    var msg = d.msg;
                    if (d.success) {
			            top.Alert.confirm('<spring:message code="com.glaway.ids.activityTypeManage.delete"/>', function (r) {
			                if (r) {
			                    $.ajax({
			                        url: 'activityTypeManageController.do?doDeleteBatch',
			                        type: 'post',
			                        data: {
			                            ids: ids.join(',')
			                        },
			                        cache: false,
			                        success: function (data) {
			                            var d = $.parseJSON(data);
			                            var msg = d.msg;
			                            if (d.success) {
			                                tip(msg);
			                                $('#ActivityTypeList').datagrid('unselectAll');
			                                $('#ActivityTypeList').datagrid('reload');
			                            } else {
			                                tip(msg);
			                            }
			                        }
			                    });
			                }
			            });
                    } else {
                        tip(msg);
                    }
                }
            });
            
        } else {
            top.tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
            return false;
        }
    }

    function doSaveActivity(iframe) {
        saveOrUp(iframe);
        return false;
    }


    function startOrStopActivityType(title, url, gname) {
        gridname = gname;
        var ids = [];
        var rows = $("#" + gname).datagrid('getSelections');
        if (rows.length > 0) {
        	debugger;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].source == "0") {
                    tip('<spring:message code="com.glaway.ids.activityTypeManage.startOrstopSourse" arguments="' + title + '"/>');
                    return false;
                }
                if (title == '<spring:message code="com.glaway.ids.common.start"/>'
                    && rows[i].status == 'enable') {
                    tip('<spring:message code="com.glaway.ids.common.onlyStartStopped"/>');
                    return false;
                }
                if (title == '<spring:message code="com.glaway.ids.common.stop"/>'
                    && rows[i].status == 'disable') {
                    tip('<spring:message code="com.glaway.ids.common.onlyStopStarted"/>');
                    return false ;
                }
            }
            top.Alert
                .confirm(
                    '<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + title + '"/>',
                    function (r) {
                        if (r) {
                            for (var i = 0; i < rows.length; i++) {
                                ids.push(rows[i].id);
                            }
                            $
                                .ajax({
                                    url: url,
                                    type: 'post',
                                    data: {
                                        ids: ids.join(',')
                                    },
                                    cache: false,
                                    success: function (data) {
                                        var d = $
                                            .parseJSON(data);
                                        if (d.success) {
                                            var msg = d.msg;
                                            tip(msg);
                                            ids = '';
                                            window
                                                .setTimeout(
                                                    "reloadActivityTable()",
                                                    500);//等待0.5秒，让分类树结构加载出来
                                        }
                                    }
                                });
                        }
                    });
        } else {
            tip('<spring:message code="com.glaway.ids.common.selectOperate"/>');
        }
    }


    //修改
    function updateActivity(id) {
        var url = 'activityTypeManageController.do?goAdd';
        url += '&id=' + id;
        //createDialog("updateDialog", url);
        $("#updateDialog").lhgdialog("open", "url:" + url);
    }

    function doUpdateActivity(iframe) {
        saveOrUp(iframe);
        return false;
    }

    function activityStartTable(id) {
        startOrStopTableStatus(id,
            '<spring:message code="com.glaway.ids.common.start"/>');
    }

    function activityStopTable(id) {
        startOrStopTableStatus(id,
            '<spring:message code="com.glaway.ids.common.stop"/>');
    }


    function startOrStopTableStatus(id, type) {
        var url = '';
        var rows = $("#ActivityTypeList").datagrid('getSelections');
        if (type == '<spring:message code="com.glaway.ids.common.start"/>') {
            url = 'activityTypeManageController.do?doStartOrStop&status=enable';
        } else if (type == '<spring:message code="com.glaway.ids.common.stop"/>') {
            url = 'activityTypeManageController.do?doStartOrStop&status=disable';
        }
        for (var i = 0; i < rows.length; i++) {
            if (type == '<spring:message code="com.glaway.ids.common.start"/>'
                && rows[i].stopFlag == '1') {
                tip('<spring:message code="com.glaway.ids.common.onlyStartStopped"/>');
                return;
            }
            if (type == '<spring:message code="com.glaway.ids.common.stop"/>'
                && rows[i].stopFlag == '0') {
                tip('<spring:message code="com.glaway.ids.common.onlyStopStarted"/>');
                return;
            }
        }
        top.Alert
            .confirm(
                '<spring:message code="com.glaway.ids.common.confirmOperate" arguments="' + type + '"/>',
                function (r) {
                    if (r) {
                        $.ajax({
                            url: url,
                            type: 'post',
                            data: {
                                ids: id
                            },
                            cache: false,
                            success: function (data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var msg = d.msg;
                                    tip(msg);
                                    window.setTimeout(
                                        "reloadActivityTable()",
                                        500);//等待0.5秒，让分类树结构加载出来
                                }
                            }
                        });
                    }
                });
    }

    function deleteActiviry(id) {
        var url = 'activityTypeManageController.do?doDelete&id=' + id;
        var title = '删除';
        top.Alert
            .confirm(
                '<spring:message code="com.glaway.ids.activityTypeManage.delete"/>',
                function (r) {
                    if (r) {
                        $.ajax({
                            url: url,
                            type: 'post',
                            data: {
                                ids: id
                            },
                            cache: false,
                            success: function (data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var msg = d.msg;
                                    tip(msg);
                                    ids = '';
                                    window.setTimeout(
                                        "reloadActivityTable()",
                                        500);//等待0.5秒，让分类树结构加载出来
                                }
                            }
                        });
                    }
                });
    }

    //查询
    function searchExpertList() {
        var searchObjArr = $('#searchActivityForm').serializeArray();
        var searchParam = new Object();
        if (searchObjArr != null && searchObjArr.length > 0) {
            for (var i = 0; i < searchObjArr.length; i++) {
                searchParam[searchObjArr[i].name] = searchObjArr[i].value;
            }
        }
        $('#ActivityTypeList').datagrid({
            url: 'activityTypeManageController.do?datagrid',
            queryParams: searchParam
        });

        $('#ActivityTypeList').datagrid('unselectAll');
        $('#ActivityTypeList').datagrid('clearSelections');
        $('#ActivityTypeList').datagrid('clearChecked');
    }

    function reloadActivityTable() {
        $('#ActivityTypeList').datagrid({
            url: 'activityTypeManageController.do?datagrid'
        });
        $('#ActivityTypeList').datagrid('unselectAll');
    }

    //重置
    function searchEpsReset() {
        $('#code').textbox("setValue", "");
        $('#name').textbox("setValue", "");
        $('#status').combobox("setValue", " ");
    }

    function hideActivityStartTable(row, index) {
        if (row.status != 'enable' && row.source != "0") {
            return false;
        } else {
            return true;
        }
    }

    function hideActivityStopTable(row, index) {
        if (row.status != 'disable' && row.source != "0") {
            return false;
        } else {
            return true;
        }
    }

    function isUserForMap(row, index) {
        if (row.source == "0") {
            return true;
        } else {
            return false;
        }
    }

</script>

    <div id="activitytool">
        <fd:form id="searchActivityForm">
            <fd:searchform id="seachExpert" onClickResetBtn="searchEpsReset();"
                           onClickSearchBtn="searchExpertList();" help="helpDoc:ActivityType">
                <fd:inputText id="code" name="code"
                              title="{com.glaway.ids.common.lable.code}"
                              queryMode="like"></fd:inputText>
                <fd:inputText id="name" name="name"
                              title="{com.glaway.ids.common.lable.name}"
                              queryMode="like"></fd:inputText>
                <fd:combobox title="{com.glaway.ids.common.lable.status}" id="status"
                             textField="text" editable="false" valueField="value" name="status"
                             data=" _全部,enable_启用,disable_禁用"
                             value="全部"></fd:combobox>
            </fd:searchform>
        </fd:form>

        <fd:toolbar>
            <fd:toolbarGroup align="left">
                <fd:linkbutton
                        onclick="addActivityTypeDialog('新增','activityTypeManageController.do?goAdd','reviewExpertList',null,null)"
                        value="{com.glaway.ids.common.btn.create}" operationCode="addActivityTypebtn"
                        iconCls="l-btn-icon basis ui-icon-plus"/>

                <fd:linkbutton id="deleteRiskPlan" onclick="deleteActivityBatch()"
                               value="{com.glaway.ids.common.btn.remove}" iconCls="basis ui-icon-minus" operationCode="deleteActiviryBtn"/>
                <fd:linkbutton
                        onclick="startOrStopActivityType('启用','activityTypeManageController.do?doStartOrStop&status=enable','ActivityTypeList')"
                        value="{com.glaway.ids.common.btn.start}"
                        iconCls="basis ui-icon-enable" operationCode="activityStartTableBtn"/>
                <fd:linkbutton
                        onclick="startOrStopActivityType('禁用','activityTypeManageController.do?doStartOrStop&status=disable','ActivityTypeList')"
                        value="{com.glaway.ids.common.btn.stop}"
                        iconCls="basis ui-icon-forbidden" operationCode="activityStopTableBtn"/>

            </fd:toolbarGroup>
        </fd:toolbar>
    </div>

    <fd:datagrid id="ActivityTypeList" checkbox="true" checked="true"
                 checkOnSelect="true" idField="id" fit="true" searchFormId="#seachExpert" toolbar="activitytool"
                 fitColumns="true" pagination="true"
                 url="activityTypeManageController.do?datagrid">
        <fd:colOpt title="{com.glaway.ids.common.lable.operation}"
                   width="30">
            <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.modify}"
                          iconCls="basis ui-icon-pencil" onClick="updateActivity"
                          hideOption="isUserForMap" operationCode="updateActivityBtn"></fd:colOptBtn>
            <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.start}"
                          iconCls="basis ui-icon-enable" onClick="activityStartTable"
                          hideOption="hideActivityStartTable" operationCode="activityStartTableBtn"></fd:colOptBtn>
            <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.stop}"
                          iconCls="basis ui-icon-forbidden" onClick="activityStopTable"
                          hideOption="hideActivityStopTable" operationCode="activityStopTableBtn"></fd:colOptBtn>
            <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.remove}"
                          iconCls="basis ui-icon-minus" onClick="deleteActiviry" hideOption="isUserForMap" operationCode="deleteActiviryBtn"
            ></fd:colOptBtn>

        </fd:colOpt>
        <fd:dgCol title="{com.glaway.ids.common.lable.code}"
                  field="code" width="40"/>
        <fd:dgCol title="{com.glaway.ids.common.lable.name}"
                  field="name"/>
        <fd:dgCol title="{com.glaway.ids.common.lable.status}"
                  field="statusName" width="25" sortable="false"/>
        <fd:dgCol title="{com.glaway.ids.common.lable.remark}"
                  field="remark" />
    </fd:datagrid>


<fd:dialog id="addActivityTypeDialog" width="800px" height="300px"
           modal="true" title="{com.glaway.ids.common.btn.create}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
                     callback="doSaveActivity"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
                     callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="updateDialog" width="800px" height="300px"
           modal="true" title="{com.glaway.ids.common.btn.modify}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
                     callback="doUpdateActivity"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
                     callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
</body>
</html>
