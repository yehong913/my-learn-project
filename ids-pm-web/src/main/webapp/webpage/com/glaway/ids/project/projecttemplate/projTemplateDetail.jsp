<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>模板详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
</head>
<body>
	<fd:form id="projTemplateDetailForm"
		method="projTemplateController.do?goTemplateDetail&id=${projectTemplateId}">
		<input id="projectTemplateId" name="projectTemplateId" type="hidden"
			value="${projectTemplateId}" />
		<fd:inputText id="projTmplName" readonly="true"  name="projTmplName"
			type="text" title="{com.glaway.ids.pm.projecttemplate.name}"
			value='${projectTemplate.projTmplName}' />
		<fd:inputText id="createName" readonly="true"  name="createName"
			type="text" title="{com.glaway.ids.pm.project.projecttemplate.creator}"
			value='${projectTemplate.createName}' />
		<fd:inputDate id="createTime" name="createTime"
			value="${projectTemplate.createTime}" formatter="yyyy-MM-dd"
			readonly="true" type="text"
			title="{com.glaway.ids.pm.project.projecttemplate.createTime}" />
		<fd:inputText id="status" readonly="true"  name="status" type="text"
			title="{com.glaway.ids.pm.project.projecttemplate.status}"
			value='${projectTemplate.status}' />
		<fd:inputTextArea name="remark" id="remark"
			title="{com.glaway.ids.pm.project.projecttemplate.remark}"
			readonly="true" value="${projectTemplate.remark}" />
	</fd:form>
</body>