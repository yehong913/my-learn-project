<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划申请变更审批中查看</title>
<t:base type="jquery,easyui_iframe,tools,DatePicker"></t:base>
<script src="webpage/com/glaway/ids/project/plantemplate/planTemplate.js"></script>
<script type="text/javascript">
	//编写自定义JS代码
	
</script>
</head>
<body>
	<fd:form id="submitApproveTable">
		<input type="hidden"  id="planId" value=${planId}>
		<input type="hidden"  id="planName" value=${planName}>
		<input type="hidden"  id="planId"/>
<%-- 		<span class="glaway_search_box_width_660" sizset="false" sizcache007647271649987991="41.71828182845905 20 25">
			<span title="<spring:message code='com.glaway.ids.pm.project.plan.planName'/>"  style="padding-left: 25px">
				<spring:message code="com.glaway.ids.pm.project.plan.planName"/>
			</span>
			<span style="right: 0px;padding-left: 20px;" >
	        	<a href="#" id="downloadlink" onclick="viewPlan()" style="color:blue;padding-top: 20px;">${planName}</a>
	        </span>
		</span> --%>
		<fd:label title="{com.glaway.ids.pm.project.plan.planName}" id="downloadlink" jsFunc="viewPlan()" content="${planName}">
		</fd:label>
		<fd:combobox id="changeType" textField="name" title="{com.glaway.ids.pm.project.task.changeType}" editable="false" 
			valueField="id" required="true" panelMaxHeight="200" readonly="true" 
			url="planChangeMassController.do?planChangeCategorylList" value="${planownerApplychangeInfo_.changeType}">
		</fd:combobox> 
		<fd:inputTextArea id="changeRemark" title="{com.glaway.ids.common.lable.remark}" name="changeRemark" readonly="true" 
			value="${planownerApplychangeInfo_.changeRemark}"></fd:inputTextArea>
	</fd:form>
	<fd:dialog id="leaderDialogPlan" width="1040px" height="550px" modal="true" title="选人">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="leaderDialogPlan"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
<script>
	//查看计划信息
	function viewPlan() {
		var dialogUrl = 'planController.do?goCheck&id=' + $('#planId').val();
		createDialog('viewPlanDialog', dialogUrl);
	}
	
	function closePlan(){
		$.fn.lhgdialog("closeSelect");
	}

	function startPlanTemProcess() {
		var leader = $('#leaderPlan').val();
		var changeType = $('#changeType').combobox('getValue');
		var changeRemark = $('#changeRemark').val();
		if(changeType == ''){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.changeReasonExistCheck"/>');
		}
		if(leader == ''){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.selectLeaderPlease"/>');
		}
		$('#submitApproveTable')
				.form(
						'submit',
						{
							url : 'taskFlowResolveController.do?doSubmitApprove&planTemplateId=${planTemplate_.id}',
							queryParams : {
								'leader' : leader,
								'changeType':changeType,
								'changeRemark':changeRemark,
								'planId' : $('#planId').val()
							}, 
							onSubmit : function() {//提交
								var isValid = $(this).form('validate');
								return isValid; // 返回false终止表单提交
							},
							success : function(data) {
								 
								var d = $.parseJSON(data);
								if (d.success) {
									top.tip(d.msg);
									$.fn.lhgdialog("closeAll");
								}

							}

						}); 
	}
</script>
</body>
</html>