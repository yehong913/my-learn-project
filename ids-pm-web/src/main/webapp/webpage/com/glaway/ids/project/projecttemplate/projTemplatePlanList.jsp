<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>计划模板详细</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
	<fd:lazytreegrid id="planAddList" idField="id" treeField="planName" url="projTemplateDetailController.do?getPlanList&type=${type}&id=${projectTemplateId}&viewHistory=${viewHistory}"
	 	 	 style="width:100%;height:100%;" imgUrl="plug-in/dhtmlxSuite/imgs/" >
			<fd:columns>
				<fd:column field="planNumber" title="序号" width="50"></fd:column>
				<fd:column field="planName" title="计划名称 "></fd:column>
				<fd:column field="planLevelInfo" title="计划等级"></fd:column>
				<fd:column field="workTime" title="参考工期（天）"></fd:column>
				<fd:column field="milestone" title="里程碑" width="80"></fd:column>
				<fd:column field="inputs" title="输入项名称"></fd:column>
				<fd:column field="delivaryName" title="交付项名称"></fd:column>
				<fd:column field="preposePlans" title="前置计划"></fd:column>
			</fd:columns>
		</fd:lazytreegrid>
 <fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
	 <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
 </fd:dialog>
</body>
<script type="text/javascript">
	function viewPlan_(id){
		var dialogUrl = 'planTemplateController.do?goViewPlan&planTemplateId=${planTemplateId}&id=${projectTemplateId}&planId='+id+'&projectId='+$("#projectId").val();
		createDialog('viewPlanDialog', dialogUrl);
	}
</script>
</html>

