<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划基本信息查看</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
</script>
</head>
<body>
			 <div style="padding-bottom:5px;padding-top:5px;height: 260px" class="easyui-panel"  >
				<label>子计划 </label>
				<fd:datagrid id="temporaryPlanChildList" checkbox="false" pagination="false"
					fitColumns="true"  idField="planId" fit="true">
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.parentPlanName}" field="parentPlanName" width="130"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.planName}" field="planName" width="130"  sortable="false"></fd:dgCol>
					<fd:dgCol title="发布人" field="assignerName" width="100"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.common.lable.owner}" field="ownerName" width="100"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.planTime}" field="planStartTime" width="150"   sortable="false"
						formatterFunName="childPostposeTime" tipField="timeAreaTip"></fd:dgCol>
				</fd:datagrid>
			</div>
			<div style="padding-top:20px;padding-bottom:5px;height: 260px" class="easyui-panel" >
				<label><spring:message code="com.glaway.ids.pm.project.plan.afterPlan"/></label>
				<fd:datagrid id="temporaryPlanPostposeList" checkbox="false" pagination="false"
					fitColumns="true"  idField="planId" fit="true">
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.preposePlans}" field="preposePlanName" width="130"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.planName}" field="planName" width="130"  sortable="false"></fd:dgCol>
					<fd:dgCol title="发布人" field="assignerName" width="100"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.common.lable.owner}" field="ownerName" width="100"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.planTime}" field="planStartTime" width="150" 
						formatterFunName="childPostposeTime" tipField="timeAreaTip"  sortable="false"></fd:dgCol>
				</fd:datagrid>
			</div>
</body>