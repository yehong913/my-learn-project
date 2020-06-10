<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<fd:form id="formobj">
		<input id="id" name="id" type="hidden" value="${detail.id}">
		
		

		<fd:inputText id="planName"
			title="{com.glaway.ids.pm.project.plan.planName}" name="planName"
			readonly="true" value="${detail.planName}" />
		

		<fd:inputText id="planLevel"
			title="{com.glaway.ids.pm.project.plan.planLevel}" name="planLevel"
			readonly="true" value="${detail.planLevelName}" />
		
		
		<fd:inputText id="workTime"
			title="{com.glaway.ids.pm.project.plan.workTime}" name="workTime" onChange="workTimeLinkage('workTime')"
			editable="true"	readonly="true" value="${detail.workTime}" />	
			
		<fd:inputText id="milestone"
			title="{com.glaway.ids.pm.project.plan.milestone}" name="milestone"
			readonly="true" value="${detail.milestoneName}" />


		<fd:inputText id="preposePlans"
			title="{com.glaway.ids.pm.project.plan.preposePlans}"
			name="preposePlans" readonly="true" value="${detail.preposePlans}" />
		

		<fd:inputTextArea id="remark"
			title="{com.glaway.ids.pm.project.plan.remark}" name="remark"
			value="${detail.remark}" readonly="true" />
	</fd:form>
</body>