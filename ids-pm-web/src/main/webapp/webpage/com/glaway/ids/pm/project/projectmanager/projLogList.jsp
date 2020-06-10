<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目日志</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
</script>
</head>
<body>
	<div id="projLogtb" style="padding: 3px; margin 10px; height: auto">
		<fd:toolbar>
			<fd:toolbarGroup align="left">
				<fd:linkbutton onclick="exportProjectLog()"
					value="{com.glaway.ids.pm.project.projectmanager.auth.exportLog}"
					iconCls="basis ui-icon-export" />
			</fd:toolbarGroup>
		</fd:toolbar>
	</div>
	<fd:datagrid fit="true" idField="id" id="projLogList"
		url="projLogController.do?queryProjectLog&id=${param.id}"
		toolbar="#projLogtb" fitColumns="true">
		<fd:dgCol
			title="{com.glaway.ids.pm.project.projectmanager.projectNumber}"
			field="projectNumber" hidden="true" />

		<fd:dgCol title="{com.glaway.ids.common.lable.operation}"
			field="logInfo" />

		<fd:dgCol
			title="{com.glaway.ids.pm.project.projectmanager.auth.operator}"
			field="showName" />

		<fd:dgCol
			title="{com.glaway.ids.pm.project.projectmanager.auth.operateTime}"
			field="createTime" formatterFunName="dateFormatter" />

		<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.auth.remark}"
			field="remark" tipField="remark" />

	</fd:datagrid>

</body>
<script type="text/javascript">
	var projectId = "";
	$(document).ready(function() {
		projectId = '${param.id}';
		parent.loadTree(projectId, 1);
	})
	//导出项目日志Excel
	function exportProjectLog() {
		JeecgExcelExport("projLogController.do?exportProjectLog&projectId="
				+ projectId, "projLogList");
	}

	function dateFormatter(val, row, value) {
		//return dateFmtFulltime(val);
		return val.substr(0, 11);
	}
</script>
</html>

