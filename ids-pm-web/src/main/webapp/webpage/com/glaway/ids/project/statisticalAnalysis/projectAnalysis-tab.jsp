<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<div style=" height:1200px">
<fd:panel id="pa_tab_panel_progressRate" collapsible="true" closable="false" width="100%" 
		title="{com.glaway.ids.pm.project.statisticalAnalysis.projectProgress}" help="helpDoc:ProjectSchedule">
</fd:panel>
<fd:panel id="pa_tab_panel_completeRate" collapsible="true" closable="false" width="100%" fit="false"
		title="{com.glaway.ids.pm.project.statisticalAnalysis.planCompleteRate}" help="helpDoc:PlanAchievingRate">
</fd:panel>
<fd:panel id="pa_tab_panel_delayTask" collapsible="true" closable="false" width="100%" fit="false"
		title="{com.glaway.ids.pm.project.statisticalAnalysis.planDelayTask}" help="helpDoc:DeferredTask">
</fd:panel> 
<fd:panel id="pa_tab_panel_monthRate" collapsible="true" closable="false" width="100%" fit="false" 
		title="{com.glaway.ids.pm.project.statisticalAnalysis.planMonthRate}" help="helpDoc:MonthlyAchievingRate">
</fd:panel>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		var url1 = 'statisticalAnalysisController.do?goProjectAnalysisPic&projectId=${projectId}';
		var url2 = 'statisticalAnalysisController.do?goProjectAnalysisRate&projectId=${projectId}';
		var url3 = 'statisticalAnalysisController.do?goProjectAnalysisDelay&projectId=${projectId}';
		var url4 = 'statisticalAnalysisController.do?goProjectAnalysisMonth&projectId=${projectId}';
		$('#pa_tab_panel_progressRate').panel('open').panel('refresh', url1);
		$('#pa_tab_panel_completeRate').panel('open').panel('refresh', url2);
		$('#pa_tab_panel_delayTask').panel('open').panel('refresh', url3);
		$('#pa_tab_panel_monthRate').panel('open').panel('refresh', url4);
	}); 
</script>