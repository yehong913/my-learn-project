<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="plug-in/jquery-easyui/themes/icon.css">

</head>
<body>
<div class="easyui-panel" fit="true">
	<input id="cellId" name="cellId" type="hidden" value="${cellId}">
	<input id="parentPlanId" name="parentPlanId" type="hidden" value="${parentPlanId}">
	<input id="useObjectId" name="useObjectId" type="hidden" value="${inputs_.useObjectId}">
	<input id="useObjectType" name="useObjectType" type="hidden" value="${inputs_.useObjectType}">
	<input id="preposeIds" name="preposeIds" type="hidden" value="${preposeIds}">
	<input id="allPreposeIds" name="allPreposeIds" type="hidden" value="${allPreposeIds}">
	<input id="type" name="type" type="hidden" value="${type}">
	<fd:datagrid checkbox="true"  fitColumns="true"  checked="true" checkOnSelect="true" idField="id" id="preposeDeliverablesList" pagination="false"
		url="taskFlowResolveController.do?deliverablesList&useObjectId=${inputs_.useObjectId}&allPreposeIds=${allPreposeIds}&useObjectType=${inputs_.useObjectType}&type=${type}&cellId=${cellId}&parentPlanId=${parentPlanId}&preposeIds=${preposeIds}" fit="true">
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.planName}"  field="useObjectName" />
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.outName}"  field="name" />
	</fd:datagrid>
</div>	

<script type="text/javascript">
function submitSelectData(){
    var names = [];
    var ids = [];
    var  datas;
    var rows = $("#preposeDeliverablesList").datagrid('getSelections');
    if (rows.length > 0) {
		for ( var i = 0; i < rows.length; i++) {
			names.push(rows[i].name);
			ids.push(rows[i].id);
		}
		 datas= {
				cellId : $("#cellId").val(),
				parentPlanId : $("#parentPlanId").val(),
				type : $("#type").val(),
				useObjectId : $("#useObjectId").val(),
				useObjectType : $("#useObjectType").val(),
				names : names.join(','),
				ids : ids.join(','),
				allPreposeIds: $("#allPreposeIds").val(),
				preposeIds : $("#preposeIds").val()
			};
		$.ajax({
			url : 'taskFlowResolveController.do?doAddInputs',
			async : false,
			cache : false,
			type : 'post',
			data : datas,
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var win = $.fn.lhgdialog("getSelectParentWin") ;
					win.initInputsFlowTask();
					$.fn.lhgdialog("closeSelect");
				}
				else{
					top.tip(d.msg);
				}
			}
		});
		
	} else {
		top.tip('<spring:message code="com.glaway.ids.pm.project.task.selectAdd"/>');
	}
}
</script>
</body>