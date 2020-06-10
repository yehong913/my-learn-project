<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划单条下达选择审批人</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	$(document).ready(function (){
		$('#cancelBtn').focus();
	});
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
		if (leader == '') {
			tip('<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
			return false;
		}
		if (deptLeader == '') {
			tip('<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
			return false;
		}
	   	$.ajax({
			url : 'planController.do?startAssignProcess&assignType='+"single",
			type : 'post',
			data : {
				'assignId' : '${assignId}',
				'leader' : leader,
				'deptLeader' : deptLeader,
				'kddProductType' : '${param.kddProductType}'
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				var msg = d.msg;
				top.tip(msg);
				if (d.success) {
					var win = $.fn.lhgdialog("getSelectParentWin");
					var planListSearch = win.planListSearch();
					$.fn.lhgdialog("closeSelect");
				}
			}
		});
	}
</script>
</head>
<body>
	<input type="hidden" id="leader" name="leader" value="${leader}"/>	
	<fd:inputSearchUser id="leaderId" name="leaderId" title="{com.glaway.ids.common.lable.leader}"  required="true" value="${leaderId}">
		<fd:eventListener event="beforeAffirmClose" listener="setLeader" needReturn="true" />
	</fd:inputSearchUser>
	<input type="hidden" id="deptLeader" name="deptLeader" value="${deptLeader}"/>
	<fd:inputSearchUser id="deptLeaderId" name="deptLeaderId" title="{com.glaway.ids.common.lable.deptLeader}"  required="true" value="${deptLeaderId}">
		<fd:eventListener event="beforeAffirmClose" listener="setDeptLeader" needReturn="true" />
	</fd:inputSearchUser>
</body>
</html>