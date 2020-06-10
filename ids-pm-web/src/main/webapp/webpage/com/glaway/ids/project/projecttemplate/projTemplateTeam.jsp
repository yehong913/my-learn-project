<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目团队</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
	<fd:datagrid checkbox="false" checked="true" idField="id" fit="true"
		fitColumns="true" checkOnSelect="true" id="projTemplateTeamList"
		pagination="false"
		url="projTemplateController.do?getProjTemplateTeam&projectTemplateId=${projectTemplateId}">
		<fd:dgCol title="{com.glaway.ids.pm.project.projecttemplate.roleName}"
			field="roleName" align="center" width="400" />
	</fd:datagrid>
</body>
</html>