<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript" src ="webpage/com/glaway/ids/taskFlow/taskFlowList.js"></script>
</head>
<body>
	<fd:datagrid url="taskFlowCommonController.do?getFlowTaskList&taskNumber=${taskNumber}&type=${type}" fit="true" fitColumns="true" 
		idField="id" checkbox="false" id="taskFlowList_${taskNumber}">
		<fd:dgCol field="op" title="操作"  width="100" formatterFunName="formatOp"/>
		<fd:dgCol title="流程名称" field="title" tipField="title" sortable="false" />
		<fd:dgCol title="创建者" field="createrFullname"  formatterFunName="viewStartName"/>
		<fd:dgCol title="状态" field="status" />
		<fd:dgCol title="开始时间" field="startTime"   formatterFunName="dateFormatter"/>
		<fd:dgCol title="结束时间" field="endTime" formatterFunName="viewEndTime"/>
	</fd:datagrid>
</body>
</html>