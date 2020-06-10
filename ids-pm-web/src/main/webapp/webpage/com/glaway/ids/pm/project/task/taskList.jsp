<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript"  src ="webpage/com/glaway/ids/project/task/taskList.js"></script>

<script type="text/javascript" >
	// 计划名称链接事件
	function viewTaskInfo(val, row, value) {
		return '<a href="#" onclick="viewTask(\'' + row.id + '\',\'' + row.planName + '\')" style="color:blue">' + row.planName
				+ '</a>';
	}
	
	var tabTitle = '';
	
	function viewTask(id,planName) {
		if(tabTitle!='') {
			$('#maintabs').tabs('close', tabTitle);
		}
		tabTitle = '<spring:message code="com.glaway.ids.pm.project.task.tasknameTitle"/>'+planName;
		addTab(tabTitle,'taskDetailController.do?goCheck&id='+id,'pictures')
	}


</script>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<input id="statusList" name="statusList" type="hidden" value="${statusList}">
		<input id="warningDay" name="warningDay" type="hidden" value="${warningDay}">
		<input id="warningDayFlag" name="warningDayFlag" type="hidden" value="${warningDayFlag}">
		<input id="currentDate" name="currentDate" type="hidden" value="${currentDate}">
		<div id="toolbar"
			style="padding: 3px; margin 10px; height: auto">
		</div>
			
		<fd:datagrid url="taskController.do?datagrid" toolbar="#toolbar" idField="id" id="taskList">
			<fd:dgCol title="{com.glaway.ids.pm.project.task.taskname}" field="planName" width="150" formatterFunName="viewTaskInfo" tipField="planName" />
			<fd:dgCol title="{com.glaway.ids.common.lable.project}" field="project" width="200" formatterFunName="viewProjectName"/>
			<fd:dgCol title="{com.glaway.ids.common.lable.planstarttime}" field="planStartTime" width="100" />	 
			<fd:dgCol title="{com.glaway.ids.common.lable.planendtime}" field="planEndTime" width="100" />
			<fd:dgCol title="{com.glaway.ids.pm.project.task.taskstatus}" field="bizCurrent" width="100" formatterFunName="viewPlanBizCurrent" />
			<fd:dgCol title="{com.glaway.ids.common.lable.progress}" field="progressRate" width="50" formatterFunName="viewTaskProgressRate"  />
		</fd:datagrid>
	</div>
	
</div>