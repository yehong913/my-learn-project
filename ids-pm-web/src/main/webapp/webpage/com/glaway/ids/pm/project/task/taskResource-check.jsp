<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-panel" fit="true">
	<fd:datagrid checkbox="false" toolbar="#tagListResource"
		pagination="true" fit="true" fitColumns="true" idField="id"
		id="resourceList"
		url="taskDetailController.do?resourceList&useObjectId=${deliverablesInfo_.useObjectId}&useObjectType=${deliverablesInfo_.useObjectType}">
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceName}"
			field="resourceName" width="200" formatterFunName="resourceNameLink"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceType}"
			field="resourceType" width="200"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.useRate}"
			field="useRate" width="100" formatterFunName="viewUseRate"></fd:dgCol>
	</fd:datagrid>
</div>
<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
<script type="text/javascript">
	$(document).ready();
	// 资源名称链接事件
	function resourceNameLink(val, row, value) {
		return '<a href="#" onclick="viewResourceCharts(\'' + row.id
				+ '\')" style="color:blue">' + val + '</a>';
	}

	// 资源名称链接事件
	function viewResourceCharts(id) {
		var rows = $("#resourceList").datagrid('getRows');
		var index = $("#resourceList").datagrid('getRowIndex', id);
		var row = rows[index];
		if (row.useRate != null && row.useRate != '' && row.startTime != null
				&& row.startTime != '' && row.endTime != null
				&& row.endTime != '') {
			var url = "resourceLinkInfoController.do?goToUsedRateReport&resourceId="
					+ row.resourceInfo.id
					+ "&startTime=${startTime}&endTime=${endTime}";
			$("#" + 'viewResourceChartsTr').lhgdialog("open", "url:" + url);
		} else {
			top
					.tip('<spring:message code="com.glaway.ids.pm.project.task.resource.emptyUseInfo"/>');
		}
	}

	function viewResourceType(val, row, value) {
		var resoure = row.resourceInfo;
		if (resoure != undefined && resoure != null && resoure != '') {
			var parent = resoure.parent;
			if (parent != undefined && parent != null && parent != '') {
				return parent.name;
			}
		}
		return val;
	}

	// 使用百分比
	function viewUseRate(val, row, value) {
		return progressRateGreen2(val);
	}

	function progressRateGreen2(val) {
		if (val == undefined || val == null || val == '') {
			val = 0;
		}
		return val + '%';
	}
</script>
<fd:dialog id="viewResourceChartsTr" height="600px" width="800px"
	maxFun="true"></fd:dialog>