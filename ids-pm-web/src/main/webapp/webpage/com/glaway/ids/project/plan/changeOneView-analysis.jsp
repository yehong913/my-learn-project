<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>关联分析</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
	<c:if test="${infoFlg == 'nothing'}">
		<div style="padding-top:20px;padding-bottom:5px;padding-left:20px;">
			<label id="planChildTimeArea">${msg}</label>
		</div>
	</c:if>
	<c:if test="${infoFlg == 'influence'}">
		<div style="padding-top:20px;padding-bottom:5px;">
			<label id="planChildTimeArea">${msg_child}</label>
		</div>
		<div style="height:150px">
			<fd:datagrid id="temporaryPlanChildList" checkbox="false" pagination="false" fitColumns="true" idField="planId" fit="true" 
				url="planChangeController.do?childListForView&formId=${formId}">
				<fd:dgCol title="{com.glaway.ids.pm.project.plan.planName}" field="planName" width="150"  sortable="false"></fd:dgCol>
				<fd:dgCol title="发布人" field="assignerName" width="120"  sortable="false"></fd:dgCol>
				<fd:dgCol title="{com.glaway.ids.common.lable.owner}" field="ownerName" width="120"  sortable="false"></fd:dgCol>
				<fd:dgCol title="{com.glaway.ids.pm.project.plan.planTime}" field="planStartTime" width="150" formatterFunName="childPostposeTime"  sortable="false"></fd:dgCol>
			</fd:datagrid>		
		</div>
		<div style="padding-top:20px;padding-bottom:5px;">
			<label id="planPostposeTimeArea">${msg_postpose}</label>
		</div>
		<div style="height:150px">
			<fd:datagrid id="temporaryPlanPostposeList" checkbox="false" pagination="false" fitColumns="true" idField="planId" fit="true"
				url="planChangeController.do?postposeListForView&formId=${formId}">
				<fd:dgCol title="{com.glaway.ids.pm.project.plan.planName}" field="planName" width="150"></fd:dgCol>
				<fd:dgCol title="发布人" field="assignerName" width="120"></fd:dgCol>
				<fd:dgCol title="{com.glaway.ids.common.lable.owner}" field="ownerName" width="120"></fd:dgCol>
				<fd:dgCol title="{com.glaway.ids.pm.project.plan.planTime}" field="planStartTime" width="150" formatterFunName="childPostposeTime"></fd:dgCol>
			</fd:datagrid>
		</div>
	</c:if>

	<script type="text/javascript">

		function childPostposeTime(val, row, index) {
			debugger;
			var planStartTime = row.planStartTime;
			var startTimeOverflow = row.startTimeOverflow;
			var planEndTime = row.planEndTime;
			var endTimeOverflow = row.endTimeOverflow;
			var childPostposeTime = "";
			if (startTimeOverflow == true) {
				childPostposeTime = '<a href="#" style="color:red">' + planStartTime + '</a>';
			} else {
				childPostposeTime = planStartTime;
			}
			if (endTimeOverflow == true) {
				childPostposeTime = childPostposeTime + "~" + '<a href="#" style="color:red">' + planEndTime + '</a>';
			} else {
				childPostposeTime = childPostposeTime + "~" + planEndTime;
			}
			return childPostposeTime;
		}

	</script>

</body>

</html>