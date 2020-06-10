<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<head>
	<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
	<script src="${pageContext.request.contextPath}/plug-in/js/highcharts7.0.2/highcharts.js" type="text/javascript"></script>
</head>
<html>
<body>

<script type="text/javascript">
$(document).ready(
	function() {
		if ('${refreshTree}' == 'true') { // 非审批页面进入才刷新右侧树
			parent.loadTree("${projectId}", 1,"projStastistics");
		}
	});
</script>

<input type="hidden" id="tabSave" value="1" />
<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
<fd:tabs id="tt" fit="true" help="helpDoc:ProjectStatistic,helpDoc:PlanWarning,helpDoc:RiskResolutionStructure,helpDoc:RiskQualitativeAnalysis,helpDoc:RiskMonitoring,helpDoc:ProblemsOfStatistical">
	<fd:tab id="paTab"
		href="statisticalAnalysisController.do?goProjectAnalysisTab&projectId=${projectId}"
		title="{com.glaway.ids.pm.project.projStatistics.projectAnalysis}"></fd:tab>
	<fd:tab id="plan"
		href="projStatisticsController.do?goProjWarnPage&projectId=${projectId}"
		title="{com.glaway.ids.pm.project.projStatistics.projWarnPage}"></fd:tab>
	<c:if test="${isRiskPluginValid eq 'true'}">
		<fd:tab id="RBSTab"
			href="/ids-riskproblems-web/riskController.do?goRBSTab&projectId=${projectId}&entryPage=${entryPage}"
			 title="{com.glaway.ids.pm.project.projStatistics.RBSTab}"></fd:tab>
		<fd:tab id="risk"
			href="/ids-riskproblems-web/riskController.do?goRiskScore&projectId=${projectId}"
			 title="{com.glaway.ids.pm.project.projStatistics.riskScore}"></fd:tab>	
		<fd:tab id="riskControllTab"
			href="/ids-riskproblems-web/riskController.do?goRiskMonitorTab&projectId=${projectId}&entryPage=${entryPage}"
			 title="{com.glaway.ids.pm.project.projStatistics.riskMonitorTab}"></fd:tab>
		<fd:tab id="problemAnalyseTab"
			href="/ids-riskproblems-web/riskController.do?goProblemStatisticsTab&projectId=${projectId}&entryPage=${entryPage}"
			 title="{com.glaway.ids.pm.project.projStatistics.problemStatisticsTab}"></fd:tab>
	</c:if>
</fd:tabs>
</body>
</html>