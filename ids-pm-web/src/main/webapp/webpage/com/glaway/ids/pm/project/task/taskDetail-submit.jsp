<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>完工反馈提交审批</title>
<t:base type="jquery_iframe,easyui_iframe,tools"></t:base> 
<script src="webpage/com/glaway/ids/project/plantemplate/planTemplate.js"></script>
</head>
<body>
	<fd:form id="formobj">
		<input type="hidden" id="leader" name="leader" value='${leader}' />	
		<fd:inputSearchUser id="leaderId" name="leaderId" title="{com.glaway.ids.common.lable.approver}" 
				prompt="{com.glaway.ids.common.lable.choosePlease}" required="true" value='${leaderId}'> 
			<fd:eventListener event="beforeAffirmClose" listener="setLeader" needReturn="true" /> 
		</fd:inputSearchUser>
	</fd:form>
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

function submittt() {
	var leader = $('#leader').val();
	var url='taskFeedbackController.do?doSubmitApprove&planId=${taskId}&leader='+leader;
	
	if(leader == null || leader == ""){
		top.tip("审批人为空");
		return false;
	}
	$('#formobj').form('submit', {
		url : url,
		onSubmit : function(param) {//提交表单
			var isValid = $(this).form('validate');
			return isValid; // 返回false终止表单提交 
		}, 
		success : function(data) {//表单提交成功
			var d = $.parseJSON(data);
		    var win = $.fn.lhgdialog("getSelectParentWin");
		    
			if (d.success) {
				var currTab = top.getTabById('${taskId}');
				top.tabRefresh(currTab);
				win.tip(d.msg);	
				//刷新主页面
				var currTab=top.getTabById('${tabId}');
				top.tabRefresh(currTab);
				$.fn.lhgdialog("closeSelect");
				$.fn.lhgdialog("closeAll");
			}else{
				win.tip(d.msg);	
			} 
		}
	}); 
}

function afterSubmit(file,data,response) {
	$.fn.lhgdialog("getSelectParentWin").$('#mm-tabupdate').click();
}

function validateFun() {
	if ($("#leader").val()=="") {
		tip('<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
		return false;
	}
}
</script>

</html>






