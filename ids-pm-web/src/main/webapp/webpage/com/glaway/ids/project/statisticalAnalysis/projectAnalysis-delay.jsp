<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	function formatPname(value, row, index) {
		var shtml;
		shtml = '<span title="'+value+'">' + value + '</span>';
		return '<a href="javascript:void(0)" onclick="showPlanDetailDialog(\'' + row.id
				+ '\')" style="color:blue">' + shtml + '</a>';
	}
	
	function showPlanDetailDialog(id) {
		var dialogUrl = 'planController.do?goCheck&id=' + id;
		createDialog('showPlanDetailDialog', dialogUrl);
	}
</script>

<fd:datagrid idField="id" id="pa_delayTask" checkbox="false" pagination="true" width="100%"
	url="statisticalAnalysisController.do?searchDelayDatagrid&projectId=${projectId}"
	fit="false" fitColumns="true" height="310px">
	<fd:dgCol field="rate" title="{com.glaway.ids.pm.project.statisticalAnalysis.progress}" />
	<fd:dgCol field="pname" title="{com.glaway.ids.pm.project.statisticalAnalysis.planName}" formatterFunName="formatPname" />
	<fd:dgCol field="status" title="{com.glaway.ids.pm.project.statisticalAnalysis.planStatus}" />
	<fd:dgCol field="level" title="{com.glaway.ids.pm.project.statisticalAnalysis.planLevel}" />
	<fd:dgCol field="oname" title="{com.glaway.ids.pm.project.statisticalAnalysis.owner}" />
	<fd:dgCol field="stime" title="{com.glaway.ids.pm.project.statisticalAnalysis.startTime}" />
	<fd:dgCol field="etime" title="{com.glaway.ids.pm.project.statisticalAnalysis.endTime}" />
	<fd:dgCol field="aname" title="{com.glaway.ids.pm.project.statisticalAnalysis.aname}" />
	<fd:dgCol field="atime" title="{com.glaway.ids.pm.project.statisticalAnalysis.atime}" />
	<fd:dgCol field="type" title="{com.glaway.ids.pm.project.statisticalAnalysis.planType}" />
</fd:datagrid>

<fd:dialog id="showPlanDetailDialog" width="750px" height="500px"
	modal="true" title="{com.glaway.ids.pm.project.statisticalAnalysis.detail}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>