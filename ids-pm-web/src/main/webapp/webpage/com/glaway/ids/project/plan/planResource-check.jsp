<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-panel" fit="true">
	<fd:datagrid checkbox="false" fitColumns="true" toolbar="#tagListResource" pagination="false"
			url="resourceLinkInfoController.do?list&useObjectId=${deliverablesInfo_.useObjectId}&useObjectType=${deliverablesInfo_.useObjectType}" 
			idField="id" id="resourceList" fit="true">
		<fd:dgCol title="id" field="id" hidden="true"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceName}" field="resourceName" width="150" formatterFunName="resourceNameLink"  sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceType}" field="resourceType" width="150"  sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.common.lable.starttime}" field="startTime" width="80"  sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.common.lable.endtime}" field="endTime" width="80"  sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.useRate}" field="useRate" width="100" formatterFunName="viewUseRate2"  sortable="false"></fd:dgCol>
	</fd:datagrid>
</div>

<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
<fd:dialog id="resourceDialog" width="900px" height="800px" 
		modal="true" title="{com.glaway.ids.pm.project.plan.resourcePicture}" maxFun="true">
</fd:dialog>
<script type="text/javascript">
	$(document).ready();

	// 资源名称链接事件
	function resourceNameLink(val, row, value) {
		return '<a href="#" onclick="viewResourceCharts(\'' + row.id + '\')" style="color:blue">' + val + '</a>';
	}

	// 资源名称链接事件
	function viewResourceCharts(id) {
	    var rows = $("#resourceList").datagrid('getRows');
	    var index = $("#resourceList").datagrid('getRowIndex', id);
	 	var row = rows[index];
	 	var planId="${planId}";
		if (row.useRate != null && row.useRate != '' && row.startTime != null && row.startTime != '' && row.endTime != null && row.endTime != '') {
			createDialog('resourceDialog',"resourceLinkInfoController.do?goToUsedRateReport&resourceId="
						+ row.resourceInfo.id + "&startTime=${startTime}&endTime=${endTime}"); 
		}
		else {
			alert('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyUseInfo"/>');
		}
	}
	
	// 使用百分比
	function viewUseRate2(val, row, value) {
		return progressRateGreen2(val);
	}
	
	function progressRateGreen2(val) {
		if (val == undefined || val == null || val == '') {
			val = 0;
		}
		return val + '%' ;
	}
	
	function viewStartEndTime(val, row, value) {
		var start = row.startTime;
		var end = row.endTime;
		if ((start != undefined && start !=null && start != '') 
			&& (end != undefined && end !=null && end != '')) {
			return dateFmtYYYYMMDD(start) + "~" + dateFmtYYYYMMDD(end);
		}
		return "";
	}
</script>
