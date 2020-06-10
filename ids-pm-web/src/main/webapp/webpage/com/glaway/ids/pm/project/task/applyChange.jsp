<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>申请计划变更</title>
<t:base type="jquery,easyui_iframe,tools,DatePicker,lhgdialog"></t:base>
<script src="webpage/com/glaway/ids/project/plantemplate/planTemplate.js"></script>
<script type="text/javascript">
	//编写自定义JS代码
	
</script>
</head>
<body>
	<fd:form id="changeApplyForm">
		<input type="hidden" id="planId" value='${planId}' />
		<input type="hidden" id="planName" value='${planName}' />	
		<fd:label title="{com.glaway.ids.pm.project.plan.planName}" id="downloadlink" jsFunc="viewPlan()" content="${planName}">
		</fd:label>
		<%-- <fd:combobox id="changeType" textField="name" title="{com.glaway.ids.pm.project.task.changeType}" editable="false" 
			valueField="id" required="true" panelMaxHeight="200"
			url="planChangeMassController.do?planChangeCategorylList" >
		</fd:combobox> --%>
						  <fd:combotree title="{com.glaway.ids.pm.project.plan.changeType}" treeIdKey="id" name="changeType"
					url="planChangeMassController.do?planChangeCategorylList" id="changeType" treePidKey="parentId"
					editable="false" multiple="false" panelHeight="200" treeName="name" required="true"
					prompt="请选择"></fd:combotree> 
		
		<fd:inputTextArea id="changeRemark" title="{com.glaway.ids.common.lable.remark}" name="changeRemark">
		</fd:inputTextArea>	
		
		<input type="hidden" id="leader" name="leader" />
		<fd:inputSearchUser id="leaderId" name="leaderId" title="{com.glaway.ids.common.lable.leader}"  required="true">
			<fd:eventListener event="beforeAffirmClose" listener="setLeader" needReturn="true" />
		</fd:inputSearchUser>
	</fd:form>

	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
		
	<script>
		// 查看计划信息
		function viewPlan() {
			var dialogUrl = 'planController.do?goCheck&id=' + $('#planId').val();
			createDialog('viewPlanDialog', dialogUrl);
		}
		
		function closePlan(){
			$.fn.lhgdialog("closeSelect");
		}
	
		function startPlanTemProcess() {
			var leader = $('#leader').val();
			//var changeType = $('#changeType').combobox('getValue');
			var changeType = $('#changeType').combotree('getValue');
			var changeRemark = $('#changeRemark').val();
			$('#changeApplyForm').form(
							'submit',
							{
								url : 'taskFlowResolveController.do?startChangeApplyProcess',
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
		
		function setLeader(obj) {
			var username = ""; // 工号
			if (obj && obj.length > 0) {
				var singleUser = obj[0].split(':');
				username = singleUser[2];
			}
			$('#leader').val(username);
			
			return true;
		}
	</script>
</body>
</html>