<%@ page language="java" import="java.util.*"
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<t:base type="jquery,easyui_iframe,tools,DatePicker"></t:base>
</head>

<body>
<script type="text/javascript">
	var exportProjectFlag = ""; //导出标记,导出结果为查询项目
	var exportYearFlag = "1";
	var exportProjectName = "";

	function showProjectAnalysisReport() {
		var projectId = $('#pa_projectName').combobox('getValues');
		var projectName = $('#pa_projectName').combobox('getText');
		if (projectId == null || projectId == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.statisticalAnalysis.selectProject"/>');
			return false;
		}
		exportProjectFlag = projectId;
		exportProjectName = projectName;
		var url1 = 'statisticalAnalysisController.do?goProjectAnalysisPic&projectId='
				+ projectId;
		var url2 = 'statisticalAnalysisController.do?goProjectAnalysisRate&projectId='
				+ projectId;
		var url3 = 'statisticalAnalysisController.do?goProjectAnalysisDelay&projectId='
				+ projectId;
		var url4 = 'statisticalAnalysisController.do?goProjectAnalysisMonth&projectId='
				+ projectId;
		$('#pa_panel_progressRate').panel('open').panel('refresh', url1);
		$('#pa_panel_completeRate').panel('open').panel('refresh', url2);
		$('#pa_panel_delayTask').panel('open').panel('refresh', url3);
		$('#pa_panel_monthRate').panel('open').panel('refresh', url4);
	}

	function resetProjectAnalysisReport() {
		$("#pa_projectName").combobox("clear");
		exportProjectFlag = ""
	}

	function goDetail() {
		var projectId = $('#pa_projectName').combobox('getValue');
		if (projectId == null || projectId == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.statisticalAnalysis.selectProject"/>');
			return false;
		}
		debugger;
		$.ajax({
			type : 'POST',
			url:'statisticalAnalysisController.do?checkIsTeamUser',
			data:{
				projectId : projectId
			},
			cache:false,
			success:function(data){
				var d = $.parseJSON(data);
				if(d.success){
					var title = '<spring:message code="com.glaway.ids.pm.project.statisticalAnalysis.projectManage"/>';
					if (!top.$('#maintabs').tabs('exists', title)) {
						top.addTabOrRefresh(title,
								"${webRoot}/projectMenuController.do?projectMenu&projectId=" + projectId + "&fromType=fromProjectAnalysis&isIframe=true&afterIframe=true",
								'pictures');
					} else {
						top.$('#maintabs').tabs('close', title);
						top.addTabOrRefresh(title,
								"${webRoot}/projectMenuController.do?projectMenu&projectId=" + projectId + "&fromType=fromProjectAnalysis&isIframe=true&afterIframe=true",
								'pictures');
					}
				}else{
					top.tip("非项目团队成员不可访问");
				}
			}
		});

	}

	//导出
	function projectAnalysisExport() {
		if (exportProjectFlag != "") {
			var projectId = exportProjectFlag;
			var projectName = exportProjectName;
			var year = exportYearFlag;
			var url = 'statisticalAnalysisController.do?doExport';
			var params = "&projectId=" + projectId + "&projectName=" + projectName + "&year=" + year;
			window.location.href = encodeURI(url + encodeURI(params));
		} else {
			top.tip('<spring:message code="com.glaway.ids.pm.project.statisticalAnalysis.selectAndSearchProject"/>');
		}
	}
</script>

<div style="fit:true;height:95%;">

	<fd:searchform id="projectAnalysisSearchForm" isMoreShow="false"
				   onClickSearchBtn="showProjectAnalysisReport()"
				   onClickResetBtn="resetProjectAnalysisReport()"
				   help="helpDoc:StatisticProjectAnalysis">
		<fd:combobox title="{com.glaway.ids.pm.project.statisticalAnalysis.project}"
					 name="pa_projectName" id="pa_projectName" queryMode="in"
					 editable="true" panelMaxHeight="150"
					 url="statisticalAnalysisController.do?projCombo"
					 textField="name" valueField="id" required="true" />
	</fd:searchform>

	<fd:toolbar>
		<fd:toolbarGroup align="left">
			<fd:linkbutton onclick="projectAnalysisExport()" value="导出"
						   iconCls="basis ui-icon-export" />
		</fd:toolbarGroup>
	</fd:toolbar>
	<div >
		<fd:panel id="pa_panel_progressRate" collapsible="true" closable="false"
				  title="{com.glaway.ids.pm.project.statisticalAnalysis.projectProgress}"
				  width="100%" style="overflow: hidden;"></fd:panel>
		<fd:panel id="pa_panel_completeRate" collapsible="true" closable="false"
				  title="{com.glaway.ids.pm.project.statisticalAnalysis.planCompleteRate}"
				  width="100%" fit="false"></fd:panel>
		<fd:panel id="pa_panel_delayTask" collapsible="true" closable="false"
				  title="{com.glaway.ids.pm.project.statisticalAnalysis.planDelayTask}"
				  width="100%" fit="false"></fd:panel>
		<fd:panel id="pa_panel_monthRate" collapsible="true" closable="false"
				  title="月度达成率" width="100%" fit="false"></fd:panel>
	</div>
	<div style="margin-top: 30px;">
		<fd:linkbutton id="pa_planDetail" onclick="goDetail()" text="计划明细表" classStyle="button_nor" />
	</div>

	<fd:dialog id="exportProjectAnalysisDialog" width="420px" height="170px" modal="true" title="导出">
		<fd:dialogbutton name="确定" callback="saveOrUpdatePA" />
		<fd:dialogbutton name="取消" callback="hideDiaLog" />
	</fd:dialog>
</div>
</body>
</html>
