<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划单条下达选择审批人</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	var sub_flag = true;
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
		if(sub_flag){
			sub_flag = false;
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
				url : 'planController.do?startPlanFlow&assignType='+"single",
				type : 'post',
				data : {
					'assignId' : '${assignId}',
					'leader' : leader,
					'deptLeader' : deptLeader
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					var msg = d.msg;
					top.tip(msg);
					if (d.success) {
						//刷新主页面
						refreshTabsByTabsName('待办任务,首页');
// 					W.getData();
						/*try{
                            $.fn.lhgdialog("getSelectParentWin").parent.flashTaskShow();
                        }catch(e){

                        }

                        try{
                            $.fn.lhgdialog("getSelectParentWin").parent.$("#startTaskListMore_wait").datagrid('reload');
                        }catch(e){

                        }*/
						$.fn.lhgdialog("closeAll");
					}
				}
			});
		}
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