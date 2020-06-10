<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

	<div>
		<fd:datagrid idField="id" id="pa_completeRate" checkbox="false" width="100%"
			url="statisticalAnalysisController.do?searchRateDatagrid&projectId=${projectId}"
			pagination="false" fit="false" fitColumns="true">
			<fd:dgCol field="type" title="{com.glaway.ids.pm.project.statisticalAnalysis.type}" />
			<fd:dgCol field="rate" title="{com.glaway.ids.pm.project.statisticalAnalysis.rate}" />
			<fd:dgCol field="total" title="{com.glaway.ids.pm.project.statisticalAnalysis.total}" />
			<fd:dgCol field="complete" title="{com.glaway.ids.pm.project.statisticalAnalysis.complete}" />
			<fd:dgCol field="uncomplete" title="{com.glaway.ids.pm.project.statisticalAnalysis.uncomplete}" />
			<fd:dgCol field="delay" title="{com.glaway.ids.pm.project.statisticalAnalysis.delay}" />
		</fd:datagrid>
	</div>
