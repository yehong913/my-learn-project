<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html> 
<html>
<head>
<title>流程任务变更选择审批人</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
<div>
	<fd:form id="selectedUsersForm">
		<input id="parentPlanId" name="parentPlanId" value="${parentPlanId}" type="hidden">	
		<input id="status" name="status" value="${status}" type="hidden">	
		<input id="changeType" name="changeType" value="${changeType}" type="hidden">	
		<input id="changeRemark" name="changeRemark" value="${changeRemark}" type="hidden">	
		<input type="hidden" id="leader" name="leader" />	
		<fd:inputSearchUser id="leaderId" name="leaderId" title="{com.glaway.ids.common.lable.leader}"  required="true">
			<fd:eventListener event="beforeAffirmClose" listener="setLeader" needReturn="true" />
		</fd:inputSearchUser>
		<input type="hidden" id="deptLeader" name="deptLeader" />	
		<fd:inputSearchUser id="deptLeaderId" name="deptLeaderId" title="{com.glaway.ids.common.lable.deptLeader}"  required="true">
			<fd:eventListener event="beforeAffirmClose" listener="setDeptLeader" needReturn="true" />
		</fd:inputSearchUser>
	</fd:form>
</div>	
	
<script type="text/javascript">
	function setLeader(obj) {
		var username = ""; // 工号
		if (obj && obj.length > 0) {
			var singleUser = obj[0].split(':');
			username = singleUser[2];
		}
		$('#leader').val(username);
		
		return true;
	}
	
	function setDeptLeader(obj) {
		var username = ""; // 工号
		if (obj && obj.length > 0) {
			var singleUser = obj[0].split(':');
			username = singleUser[2];
		}
		$('#deptLeader').val(username);
		
		return true;
	}
	
	function closePlan() {
		window.parent.closeDiv("submit");
	}
</script>
</body>
</html>