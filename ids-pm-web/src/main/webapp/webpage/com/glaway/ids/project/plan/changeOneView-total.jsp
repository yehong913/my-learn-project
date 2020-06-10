<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>统计分析</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div class="easyui-panel" fit="true">	
		<fd:datagrid id="planChangeVo" checkbox="false" pagination="false" fitColumns="true" idField="planId"
			fit="true" url="planChangeController.do?changeAnalysisTotal&formId=${formId}">
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.field}" field="field" width="150"  sortable="false"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.type}" field="type" width="120"  sortable="false"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.changeBefore}" field="changeBefore" width="120"  sortable="false"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.changeAfter}" field="changeAfter" width="150"  sortable="false"></fd:dgCol>
		</fd:datagrid>
	</div>
</body>