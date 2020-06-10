<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划申请变更驳回修改</title>
<t:base type="jquery,easyui_iframe,tools,DatePicker"></t:base>
<script src="webpage/com/glaway/ids/project/plantemplate/planTemplate.js"></script>
<script type="text/javascript">
	//编写自定义JS代码
	
</script>
</head>
<body>
	<div border="false" class="easyui-panel div-msg" fit="true">
		<div class="easyui-panel" fit="true">
			<fd:form id="submitApproveTable">
				<input type="hidden" id="planId" value="${planId}" />
				<input type="hidden" id="planName" value="${planName}" />
				<input type="hidden" id="planId2" value="${planownerApplychangeInfo_.id}" />
				<fd:label title="{com.glaway.ids.pm.project.plan.planName}" id="downloadlink" jsFunc="viewPlan()" content="${planName}">
				</fd:label>
				<%-- <fd:combobox id="changeType" textField="name" title="{com.glaway.ids.pm.project.task.changeType}" editable="false" 
					valueField="id" required="true" panelMaxHeight="200" value="${planownerApplychangeInfo_.changeType}"
					url="planChangeMassController.do?planChangeCategorylList">
				</fd:combobox> --%>
				<fd:combotree title="{com.glaway.ids.pm.project.plan.changeType}" treeIdKey="id" name="changeType" value="${planownerApplychangeInfo_.changeType}"
					url="planChangeMassController.do?planChangeCategorylList" id="changeType" treePidKey="parentId"
					editable="false" multiple="false" panelHeight="200" treeName="name" required="true"
					prompt="请选择"></fd:combotree> 
				<fd:inputTextArea id="changeRemark" title="{com.glaway.ids.common.lable.remark}" name="changeRemark" 
					value="${planownerApplychangeInfo_.changeRemark}">
				</fd:inputTextArea>	
			</fd:form>
		</div>
		
		<div class="div-msg-btn">
			<div>
				<fd:linkbutton onclick="submit()" value="{com.glaway.ids.pm.project.task.submitAgain}" classStyle="button_nor" />
				<fd:linkbutton onclick="cancelProcess()" value="{com.glaway.ids.pm.project.task.dropProcess}" classStyle="button_nor" />
				<fd:linkbutton id="cancelBtn" onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" />
			</div>		
		</div>
	</div>

	<fd:dialog id="leaderDialogPlan" width="1040px" height="550px" modal="true" title="{com.glaway.ids.pm.project.plan.basicLine.chosePeople}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="leaderDialogPlan"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
<script>
	$(document).ready(function() {
		$('#cancelBtn').focus();
		setTimeout(function() {
			$('#changeType').comboztree('setText', '${changetypeName}');
		}, 500)
	});

	//查看计划信息
	function viewPlan() {
		var dialogUrl = 'planController.do?goCheck&id=' + $('#planId').val();
		createDialog('viewPlanDialog', dialogUrl);
	}
	
	function closePlan(){
		$.fn.lhgdialog("closeSelect");
	}

	function submit() {
		var changeType = $('#changeType').combobox('getValue');
		var changeRemark = $('#changeRemark').val();
		/* iframe.startPlanTemProcess(); */
		$.post('taskFlowResolveController.do?startChangeApplyForWorkFlow', {
			'changeType':changeType,
			'changeRemark':changeRemark,
			'planId' : $('#planId').val(),
			'id' : $('#planId2').val()
		}, function(data) {//刷新
			top.tip('<spring:message code="com.glaway.ids.pm.rdtask.task.submitsuccess"/>');
// 			W.getData();
			try{
            	$.fn.lhgdialog("getSelectParentWin").parent.flashTaskShow();
            }catch(e){
            	
            }
            
            try{
            	$.fn.lhgdialog("getSelectParentWin").parent.$("#startTaskListMore_wait").datagrid('reload');
            }catch(e){
            	
            }
			$.fn.lhgdialog("closeSelect");
		}); 
	}
	
	function cancelProcess() {
		var changeType = $('#changeType').combobox('getValue');
		var changeRemark = $('#changeRemark').val();
		/* iframe.startPlanTemProcess(); */
		$.post('taskFlowResolveController.do?cancelChangeApplyForWorkFlow', {
			'changeType':changeType,
			'changeRemark':changeRemark,
			'planId' : $('#planId').val(),
			'id' : $('#planId2').val()
		}, function(data) {//刷新
			top.tip('<spring:message code="com.glaway.ids.pm.rdtask.task.submitsuccess"/>');
// 			W.getData();
			try{
            	$.fn.lhgdialog("getSelectParentWin").parent.flashTaskShow();
            }catch(e){
            	
            }
            
            try{
            	$.fn.lhgdialog("getSelectParentWin").parent.$("#startTaskListMore_wait").datagrid('reload');
            }catch(e){
            	
            }
			$.fn.lhgdialog("closeSelect");
		}); 
	}
</script>
</body>
</html>