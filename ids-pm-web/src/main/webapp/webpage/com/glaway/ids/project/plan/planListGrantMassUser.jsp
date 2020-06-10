<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划批量下达选择审批人</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<div style="float: left; padding: 5px;">
			<input type="hidden" id="leader" name="leader" />	
			<fd:inputSearchUser id="leaderId" name="leaderId" title="{com.glaway.ids.common.lable.leader}"  required="true">
				<fd:eventListener event="beforeAffirmClose" listener="setLeader" needReturn="true" />
			</fd:inputSearchUser>	
		</div>	
		
		<div style="float: left; padding: 5px;">
			<input type="hidden" id="deptLeader" name="deptLeader" />
			<fd:inputSearchUser id="deptLeaderId" name="deptLeaderId" title="{com.glaway.ids.common.lable.deptLeader}"  required="true">
				<fd:eventListener event="beforeAffirmClose" listener="setDeptLeader" needReturn="true" />
			</fd:inputSearchUser>
		</div>	
	</div>
</div>
</body>

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

function startAssignProcess(ids) {
	var flg = false;
	var leader = $('#leader').val();
	var deptLeader = $('#deptLeader').val();
	if (leader == '') {
		top.tip('<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
	}
	else if (deptLeader == '') {
		top.tip('<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
	}
	else{
    	$.ajax({
			url : 'planController.do?startAssignProcess&assignType='+"mass"+'&ids='+ids,
			type : 'post',
			data : {
				'ids' : ids,
				'leader' : leader,
				'deptLeader' : deptLeader,
				'kddProductType' : '${param.kddProductType}'
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					var win = $.fn.lhgdialog("getSelectParentWin");
					var planlistwin=$.fn.lhgdialog("getSelectParentWin").W;
					planlistwin.planListSearch();
					if('${param.kddProductType}'!=null&&"kddProduct"=='${param.kddProductType}'){
						top.tip('计划发布流程启动成功');
					}else{
						top.tip('项目计划发布流程启动成功');
					}
					$.fn.lhgdialog("closeSelect");
					win.closePlan();
				}
				
			}
		});
	}
	return flg;
}

function closePlan() {
	$.fn.lhgdialog("closeSelect");
}
</script>
