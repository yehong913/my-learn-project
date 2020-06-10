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
		<fd:form id="planChangeForm">	
		<fd:inputText id="changeType"  title="{com.glaway.ids.pm.project.plan.changeType}"  name="changeType" readonly="true" value="${temporaryPlan_.changeTypeInfo.name}" />	
		<fd:inputTextArea id="changeRemark" title="{com.glaway.ids.common.lable.remark}" name="changeRemark" readonly="true" value="${temporaryPlan_.changeRemark}"  ></fd:inputTextArea>	
		</fd:form>
</body>