<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>模板详情</title>
<t:base type="jquery,easyui_iframe,tools"></t:base>
<body style="overflow-x: hidden;">
	<fd:panel border="false" id="panel1" collapsible="false" fit="true" width="100%">
		<fd:form id="planTemplateShowDetailformobj">
			<fd:inputText id="planTmplName" name="planTmplName" readonly="true" 
					title="{com.glaway.ids.pm.project.plantemplate.name}"
					value="${tptmpl_.name}" />
			<fd:inputTextArea id="remark" title="{com.glaway.ids.common.lable.remark}" name="remark"
					readonly="true" value="${tptmpl_.remark}" />                 
		</fd:form>
	</fd:panel>
</body>

