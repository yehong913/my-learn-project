<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划基本信息查看</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
</script>
</head>
<body>
<div class="easyui-panel"  fit="true" >
	<fd:datagrid id="resourceList" checkbox="false" pagination="false" 
		fitColumns="true" idField="planId" fit="true">
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.planName}" field="planName" width="100"  sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceName}" field="resourceName" width="100" formatterFunName="resourceNameLink"  sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceType}" field="resourceType" width="150"  sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.useRate}" field="useRate" width="70"  formatterFunName="viewUseRate2"  sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.area}" field="resourceInfo" width="150" formatterFunName="viewResourceTime"  sortable="false"></fd:dgCol>
	</fd:datagrid>
</div>
</body>