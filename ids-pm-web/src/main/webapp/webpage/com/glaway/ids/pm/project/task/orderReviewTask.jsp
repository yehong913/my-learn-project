<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>下发评审任务</title>
<t:base type="jquery_iframe,easyui_iframe,tools"></t:base>
<!-- <script type="text/javascript"
	src="webpage/com/glaway/ids/project/task/tabRefresh.js"></script> -->
<script type="text/javascript" src="plug-in/accordion/js/left_ids_menu.js"></script>

</head>
<body>
	<fd:form id="orderReviewTaskForm">
		<fd:inputText id="orderReviewTaskName" readonly="true" name="planName" value="${plan_.planName}" 
			title="{com.glaway.ids.pm.project.task.reviewtaskName}" required="true" />
		<input type="hidden" id="taskId" value="${plan_.id}" name="id" />
		<fd:combobox title="{com.glaway.ids.common.lable.reviewtype}" name="orderReviewTaskType" required="true" 
			id="orderReviewTaskType" editable="false" maxLength="-1" url="taskDetailController.do?reviewTypeCombo" 
			textField="name" valueField="id">
		</fd:combobox>
		<fd:inputSearchUser id="owner" name="owner" title="{com.glaway.ids.common.lable.applicat}" 
			required="true" editable="false" value="${plan_.owner}">
		</fd:inputSearchUser>
		<fd:inputDate id="orderReviewTaskApplyTime" value="${plan_.planEndTime}" editable="false" name="planEndTime" 
			title="{com.glaway.ids.pm.project.task.applyDeadline}" required="true" >
		</fd:inputDate>
	</fd:form>
	<fd:dialog id="selectApplyerDialog" width="800px" height="550px"
		modal="true" title="{com.glaway.ids.pm.project.task.selectReviewApplicat}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="selectApplyerOk"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
</body>


<script type="text/javascript">

/* function setLeader(obj) {
	var username = ""; // 工号
	if (obj && obj.length > 0) {
		var singleUser = obj[0].split(':');
		username = singleUser[0];
	}
	$('#perIds').val(username);
	
	return true;
} */

function searchApplyer(id, url) {
	$("#" + id).lhgdialog("open", "url:" + url);
}

function selectApplyerOk(iframe) {
	var ids = '';
	var showNames = '';
	var userNames = '';
	var realNames = '';
	var selectUsers = iframe.getSelectUsers();
	var item = selectUsers.split(',');
	for (var i = 0; i < item.length; i++) {
		var userItem = item[i];
		var singleUser = userItem.split(':');
		if (i == item.length - 1) {
			ids = ids + singleUser[0];
			showNames = showNames + singleUser[1];
			userNames = userNames + singleUser[2];
		} else {
			ids = ids + singleUser[0] + ',';
			showNames = showNames + singleUser[1] + ',';
			userNames = userNames + singleUser[2] + ',';
		}
	}
	$('#perIds').val(ids);
	$('#reviewApplyer').searchbox("setValue", showNames);
	return true; 
}

/**
 * 提交下发评审任务表单
 */
function submit(){
	$('#orderReviewTaskForm').form('submit', {
		url : 'taskDetailController.do?orderReviewTask',
		onSubmit : function(param) {//提交表单
			var date=$('#orderReviewTaskApplyTime').datebox('getValue');
			if(date>'${plan_.planEndTime}'||date<'${plan_.planStartTime}'){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.reviewInPlanTime"/>');
				return false;
			}
			
			var orderReviewTaskType=$('#orderReviewTaskType').combobox('getValue');
			if(orderReviewTaskType == ''){
				top.tip('评审类型不能为空');
				return false;
			}
			var isValid = $(this).form('validate');
			return isValid; // 返回false终止表单提交
		},
		success : function(data) {//表单提交成功
			var d = $.parseJSON(data);
			var obj=d.obj;
			var str=obj.split(':');
		    var win = $.fn.lhgdialog("getSelectParentWin");
		    win.tip(d.msg);
			
			top.addTabByIdOrRefresh(str[0], 'taskDetailController.do?goCheck&isIframe=true&id='+str[1], 'pictures',str[1])
			$.fn.lhgdialog("closeSelect");
		}
	}); 
	
	
}
</script>


</html>




