<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划下达审批流程中查看页面</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<div class="easyui-layout">
		<fd:form id="formId">
			<input id="statusList" name="statusList" type="hidden" value="${statusList}">
			<fd:lazyLoadingTreeGrid url="planController.do?assignListViewTree" id="planAssignViewList"
				width="100%" height="380px" enableMultiselect="true"
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
	<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
	<fd:dialog id="viewPlanDialog" width="800px" height="530px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<script type="text/javascript">
		
		// 查看计划信息
		function viewPlan_(id) {
			var dialogUrl = 'planController.do?goCheck&id=' + id;
			createDialog('viewPlanDialog',dialogUrl);
		}
		
		function closePlan() {
			$.fn.lhgdialog("closeSelect");
		}
	</script>
</body>
</html>
