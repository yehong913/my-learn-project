<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户组</title>
<t:base type="jquery,easyui_iframe,tools,DatePicker"></t:base>
<script type="text/javascript">
	$(function() {
	})
</script>
</head>
<body>
	<fd:datagrid checked="false" idField="id" id="groupUserList" fit="true"
		fitColumns="true" pagination="false"
		url="projRolesController.do?groupDatagrid&groupId=${groupId}">
		<fd:dgCol field="userName" title="{com.glaway.ids.pm.project.projectmanager.role.userName}" />
		<fd:dgCol field="departName" title="{com.glaway.ids.pm.project.projectmanager.role.departName}" />
		<fd:dgCol field="email" title="{com.glaway.ids.pm.project.projectmanager.role.email}" />
		<fd:dgCol field="status" title="{com.glaway.ids.pm.project.projectmanager.role.status}" />
	</fd:datagrid>
</body>