<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划基线查看</title>
<t:base type="jquery,easyui,tools"></t:base>
<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
</head>
<body>
	<div class="easyui-layout">
		<fd:form id="basicLineAddForm">	
			<div style = "height:200px">
				<fd:inputText id="basicLineName" title="{com.glaway.ids.pm.project.plan.basicLine.basicName}"  
					name="ownerDept" value="${basicLine_.basicLineName}" readonly="true" />	
				<fd:inputTextArea id="remark" title="{com.glaway.ids.common.lable.remark}" 
					name="remark" value="${basicLine_.remark}" readonly="true" />
			</div>
			<fd:lazyLoadingTreeGrid url="basicLineController.do?basicLineViewList&basicLineId=${basicLineId}" id="basicLineAddList"
					width="100%" height="280px" imgUrl="plug-in/icons_greenfolders/"
					initWidths="*,70,80,100,80,80,70"
					columnIds="planName,planLevelInfo,ownerInfo,planStartTime,planEndTime,workTime"
					header="计划名称,计划等级,负责人,开始时间,结束时间,工期(天)"
					columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
					colAlign="left,left,left,left,left,left"
					colSortings="na,na,na,na,na,na"
					colTypes="tree,ro,ro,ro,ro,ro"
					enableTreeGridLines="true" enableLoadingStatus="true"
					enableMultiselect="true">
			</fd:lazyLoadingTreeGrid>
	    </fd:form>
	</div>	
	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog" />
	</fd:dialog>
	
	<script type="text/javascript">
		// 查看计划信息
		function viewPlan(id) {
			var dialogUrl = 'basicLineController.do?goCheck&id=' + id;
			createDialog('viewPlanDialog', dialogUrl);
		}
	</script>
</body>
</html>
