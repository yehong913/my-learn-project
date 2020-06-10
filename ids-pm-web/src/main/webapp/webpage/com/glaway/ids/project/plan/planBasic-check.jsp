<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<fd:form id="formobj">
		<input id="id" name="id" type="hidden" value="${plan_.id}">
		<input id="projectId" name="projectId" type="hidden" value="${plan_.projectId}">
		<input id="ownerShow" name="ownerShow" type="hidden"
			value="${ownerShow}">

		<fd:inputText id="planName"
			title="{com.glaway.ids.pm.project.plan.planName}" name="planName"
			readonly="true" value="${plan_.planName}" />
		<fd:inputText id="owner" title="{com.glaway.ids.common.lable.owner}"
			name="owner" readonly="true" value="${ownerShow}" />

		<fd:inputText id="planLevel"
			title="{com.glaway.ids.pm.project.plan.planLevel}" name="planLevel"
			readonly="true" value="${plan_.planLevelName}" />
		<fd:inputText id="ownerDept"
			title="{com.glaway.ids.pm.project.plan.dept}" name="ownerDept"
			readonly="true" value="${plan_.ownerDept}" />
		<!-- 需调整时间 把第一个when分支 readonly='true' 改为 'false'-->	
		<c:choose>
			<c:when test="${ plan_.bizCurrent == 'EDITING' or plan_.bizCurrent == 'STARTING'}">
				<fd:inputDate id="planStartTime" value="${plan_.planStartTime}"
					editable="false" name="planStartTime"
					title="{com.glaway.ids.common.lable.starttime}" readonly="true" onChange="workTimeLinkage('planStartTime')"></fd:inputDate>
				<fd:inputDate id="planEndTime" value="${plan_.planEndTime}"
					editable="false" name="planEndTime" onChange="workTimeLinkage('planEndTime')"
					title="{com.glaway.ids.common.lable.endtime}" readonly="true"></fd:inputDate>
				<fd:inputText id="workTime"
					title="{com.glaway.ids.pm.project.plan.workTime}" name="workTime" onChange="workTimeLinkage('workTime')"
					editable="true"	readonly="true" value="${plan_.workTime}" />	
			</c:when>
			<c:otherwise>
				<fd:inputDate id="planStartTime" value="${plan_.planStartTime}"
					editable="false" name="planStartTime"
					title="{com.glaway.ids.common.lable.starttime}" readonly="true"></fd:inputDate>
				<fd:inputDate id="planEndTime" value="${plan_.planEndTime}"
					editable="false" name="planEndTime"
					title="{com.glaway.ids.common.lable.endtime}" readonly="true"></fd:inputDate>
				<fd:inputText id="workTime"
					title="{com.glaway.ids.pm.project.plan.workTime}" name="workTime"
					editable="false"	readonly="true" value="${plan_.workTime}" />	
			</c:otherwise>
		</c:choose>
		<fd:inputText id="milestone"
			title="{com.glaway.ids.pm.project.plan.milestone}" name="milestone"
			readonly="true" value="${plan_.milestoneName}" />

		<fd:combobox id="taskNameType" textField="name"
			valueField="id"
			title="{com.glaway.ids.pm.project.plan.taskNameType}"
			selectedValue="${plan_.taskNameType}" readonly="true"
			editable="false" required="true"
			url="planController.do?getTaskNameTypes">
		</fd:combobox>
		<fd:combobox id="taskType" textField="name" valueField="name"
			title="{com.glaway.ids.pm.project.plan.taskType}"
			selectedValue="${plan_.taskType}" readonly="true" editable="false"
			required="true"
			url="planController.do?getTaskTypes&parentPlanId=${plan_.parentPlanId}&projectId=${plan_.projectId}">
		</fd:combobox>

		<fd:inputText id="preposePlans"
			title="{com.glaway.ids.pm.project.plan.preposePlans}"
			name="preposePlans" readonly="true" value="${plan_.preposePlans}" />
		<fd:inputText title="{com.glaway.ids.common.lable.status}" id="status"
			name="status" value="${plan_.status}" readonly="true" />

		<fd:inputText title="{com.glaway.ids.pm.project.projecttemplate.creator}"
			id="createFullName" name="createFullName"
			value="${plan_.createFullName}-${plan_.createName}" readonly="true" />
		<fd:inputDate
			title="{com.glaway.ids.pm.project.projecttemplate.createTime}"
			id="createTime" name="createTime" value="${plan_.createTime}"
			readonly="true" />

		<c:if test="${plan_.bizCurrent == 'EDITING'}">
			<div style="display: none;">
		</c:if>
		<c:if test="${plan_.bizCurrent != 'EDITING'}">
			<div>
		</c:if>
		<c:if
			test="${plan_.assignerInfo.realName != '' && plan_.assignerInfo.realName != null}">
			<fd:inputText title="{com.glaway.ids.common.lable.assigner}"
				id="assignerName" name="assignerName"
				value="${plan_.assignerInfo.realName}-${plan_.assignerInfo.userName}"
				readonly="true" />
		</c:if>
		<c:if
			test="${plan_.assignerInfo.realName == '' || plan_.assignerInfo.realName == null}">
			<fd:inputText title="{com.glaway.ids.common.lable.assigner}"
				id="assignerName" name="assignerName"
				value="${plan_.assignerInfo.realName}" readonly="true" />
		</c:if>
		<fd:inputDate title="{com.glaway.ids.common.lable.assignTime}"
			id="assignTime" name="assignTime" value="${plan_.assignTime}"
			readonly="true" />
		</div>

		<fd:inputTextArea id="remark"
			title="{com.glaway.ids.pm.project.plan.remark}" name="remark"
			value="${plan_.remark}" readonly="true" />
	</fd:form>
</body>