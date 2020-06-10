<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>基线提交选择审批人</title>
<t:base type="jquery,easyui,tools"></t:base>
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

function startAssignProcess() {
	var leader = $('#leader').val();
	var deptLeader = $('#deptLeader').val();
	var win = $.fn.lhgdialog("getSelectParentWin");
	
	if (leader == '') {
		win.tip('<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
		return false;
	}
	if (deptLeader == '') {
		win.tip('<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
		return false;
	}
   	$.ajax({
		url : 'basicLineController.do?startBasicLine&basicLineId=${basicLineId}',
		type : 'post',
		data : {
			'leader' : leader,
			'deptLeader' : deptLeader
		},
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			tip(d.msg);
			if (d.success) {
				var planListSearch = win.basicLineListSearch();
				$.fn.lhgdialog("closeSelect");
			}
		}
	});
}
</script>
</head>

<body>
	<fd:form id="submitBasicLineForm">
		<input id="leader" name="leader" type="hidden" /> 
		<fd:inputSearchUser id="leaderId" name="leaderId" title="{com.glaway.ids.pm.project.plan.basicLine.leader}" required="true">
			<fd:eventListener event="beforeAffirmClose" listener="setLeader" needReturn="true" />
		</fd:inputSearchUser>
		<input id="deptLeader" name="deptLeader" type="hidden" />
		<fd:inputSearchUser id="deptLeaderId" name="deptLeaderId" title="{com.glaway.ids.pm.project.plan.basicLine.deptLeader}" required="true">
			<fd:eventListener event="beforeAffirmClose" listener="setDeptLeader" needReturn="true" />
		</fd:inputSearchUser>		
	</fd:form>
	<input type="button" id="btn_sub" style="display: none;" onclick="startAssignProcess();">
</body>
</html>