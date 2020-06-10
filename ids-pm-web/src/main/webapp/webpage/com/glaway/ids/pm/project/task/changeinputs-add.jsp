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
	<input id="useObjectId" name="useObjectId" type="hidden" value="${inputs_.useObjectId}">
	<input id="useObjectType" name="useObjectType" type="hidden" value="${inputs_.useObjectType}">
	<input id="preposeIds" name="preposeIds" type="hidden" value="${preposeIds}">
	<input id="allPreposeIds" name="allPreposeIds" type="hidden" value="${allPreposeIds}">
	<input id="type" name="type" type="hidden" value="${type}">
	<input id="cellId" name="cellId" type="hidden" value="${cellId}">
	<input id="parentPlanId" name="parentPlanId" type="hidden" value="${parentPlanId}">
	<fd:datagrid checkbox="true"  fitColumns="true"  checked="true" checkOnSelect="true" idField="id" id="preposeDeliverablesList" pagination="false"
		url="taskFlowResolveController.do?changeDeliverablesList&useObjectId=${inputs_.useObjectId}&allPreposeIds=${allPreposeIds}&useObjectType=${inputs_.useObjectType}&type=${type}&cellId=${cellId}&parentPlanId=${parentPlanId}&preposeIds=${preposeIds}" fit="true">
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.planName}"  field="useObjectName"  sortable="false"/>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.outName}"  field="name"  sortable="false"/>
	</fd:datagrid>
</div>	

<script type="text/javascript">
function submitSelectData(){	
    var names = [];
    var ids = [];     
    var originObjectIds = [];
    var originObjectNames = [];
    var  datas;
    var rows = $("#preposeDeliverablesList").datagrid('getSelections');
    if (rows.length > 0) {
		for ( var i = 0; i < rows.length; i++) {
			names.push(rows[i].name);
			ids.push(rows[i].id);
			originObjectIds.push(rows[i].useObjectId);
			originObjectNames.push(rows[i].useObjectName);
		}
		 datas= {
					cellId : $("#cellId").val(),
					parentPlanId : $("#parentPlanId").val(),
					type : $("#type").val(),
					useObjectId : $("#useObjectId").val(),
					useObjectType : $("#useObjectType").val(),
					names : names.join(','),
					ids : ids.join(','),
					originObjectIds : originObjectIds.join(','),
					originObjectNames : originObjectNames.join(','),
					allPreposeIds: $("#allPreposeIds").val(),
					preposeIds : $("#preposeIds").val()
			};
		 
		$.ajax({
			url : 'taskFlowResolveController.do?changdoAddInputs',
			async : false,
			cache : false,
			type : 'post',
			data : datas,
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var win = $.fn.lhgdialog("getSelectParentWin") ;
 					win.initInputsChangeFlow(); 
					$.fn.lhgdialog("closeSelect");
				}
			}
		});
	} else {
		top.tip('<spring:message code="com.glaway.ids.pm.project.task.selectAdd"/>');
	}
}
</script>
</body>