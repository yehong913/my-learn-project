<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html> 
<html>
<head>
<title>流程任务下达选择审批人</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
<div>
	<fd:form id="selectedUsersForm">
		<input id="parentPlanId" name="parentPlanId" value="${parentPlanId}" type="hidden">	
		<input id="status" name="status" value="${status}" type="hidden">	
		<input id="parentPlanName" name="parentPlanName" value="${parentPlanName}" type="hidden">	
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

	function startAssignProcess() {
	 	var leader = $('#leader').val();
		var deptLeader = $('#deptLeader').val();
		var parentPlanId = $('#parentPlanId').val();
		var status = $('#status').val();
		var parentPlanName = $('#parentPlanName').val();
 		if (leader == '') {
			top.tip('<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
			return false;
		}
		if (deptLeader == '') {
			top.tip('<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
			return false;
		}
		var url = 'taskFlowResolveController.do?startAssignProcess';
		if ("ORDERED" == status) {
			url = 'taskFlowResolveController.do?startPlanChange';
		}
		top.jeasyui.util.commonMask('open','请稍候...');
		$.ajax({
			url : url,
			type : 'post',
			data : {
				"parentPlanId" : parentPlanId,
				"leader" : leader,
				"deptLeader" : deptLeader,
				"type" : 1 
			},
			cache : false,
			success : function(data) {
				top.jeasyui.util.commonMask('close');
				var d = $.parseJSON(data);
				var msg = d.msg.split('<br/>');
				top.tip(msg[0]);
				if (d.success) {
					var win2 = $.fn.lhgdialog("getSelectParentWin") ;
					$('#status').val("1");
					try {
						//刷新主页面
		                refreshTabsByTabsName('待办任务,首页');
					} catch (e) {
						// TODO: handle exception
					}
					setTimeout(function(){
						$.fn.lhgdialog("closeAll");
					},500);
				}
			}
		}); 
	}
	
	function closePlan() {
		window.parent.closeDiv("submit");
	}
</script>
</body>
</html>