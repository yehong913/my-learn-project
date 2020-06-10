<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划下达驳回后页面</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">

	$(document).ready(function (){
		$('#cancelBtn').focus();
	});
	
	function startPlanFlow() {
		var dialogUrl = 'planController.do?goAssignPlanOneEdit&id=${formId}';
		createDialog('assignPlanDialog', dialogUrl);
	}
		
	//修改计划
	function editPlan(id) {
		$.fn.lhgdialog({
			content : 'url:planController.do?goUpdate&id=' + id,
			lock : true,
			width : 630,
			height : 400,
			zIndex : 2100,
			title : '修改计划',
			opacity : 0.3,
			cache : false
		});
	}
	
	// 查看计划信息
	function viewPlan_(id) {
		var dialogUrl = 'planController.do?goCheck&id=' + id;
		createDialog('viewPlanDialog',dialogUrl);
	}
	
	function closePlan() {
		$.fn.lhgdialog("closeSelect");
	}
	
	function assignPlanDialog(iframe){
		iframe.startAssignProcess();
		return false;
	}
	
</script>
</head>
<body>
		<div class="easyui-layout">
			<fd:form id="formId">
				<fd:lazyLoadingTreeGrid url="planController.do?assignListViewTree" id="planAssignViewList"
					width="100%" height="330px" enableMultiselect="true"
					initWidths="0,0,200,*,*,*,*,*" imgUrl="plug-in/icons_greenfolders/"
					columnIds="id,planNumber,planName,planLevelInfo,ownerInfo,planStartTime,planEndTime,workTime"
					header="ID, ,计划名称,计划等级,负责人,开始时间,结束时间,工期(天)"
					columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
					colAlign="left,left,left,left,left,left,left,left"
					colSortings="na,na,na,na,na,na,na,na,"
					colTypes="ro,ro,tree,ro,ro,ro,ro,ro"
					enableTreeGridLines="true" enableLoadingStatus="true">
				</fd:lazyLoadingTreeGrid>
			</fd:form>
		</div>
		<div class="div-msg-btn">
			<div>
				<fd:linkbutton onclick="startPlanFlow()" value="{com.glaway.ids.common.btn.confirm}" classStyle="button_nor" />
				<fd:linkbutton id="cancelBtn" onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" />				
			</div>
		</div>
	<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
		<fd:dialog id="assignPlanDialog" width="400px" height="150px" modal="true" title="{com.glaway.ids.pm.project.plan.assignPlan}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="assignPlanDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
</body>
</html>
