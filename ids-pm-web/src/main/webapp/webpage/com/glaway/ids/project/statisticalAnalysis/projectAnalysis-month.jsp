<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
	function showmonthRate() {
		var year = $('#pa_year').combobox('getValues');
		if(year == '' || year == undefined || year == null){
			top.tip('<spring:message code="com.glaway.ids.pm.project.statisticalAnalysis.selectYear"/>');
			return false;
		}
		exportYearFlag = year;
		var projectId = $('#pa_monthRate_projectId').val();
		$('#pa_monthRate')
				.datagrid(
						{
							url : "statisticalAnalysisController.do?searchMonthDatagrid&projectId="
									+ projectId + '&year=' + year
						});
	}
	function resetmonthRate() {
		debugger;
		var year ="${curYear}";
		if(year!=""||year != undefined|| year != null){
			$("#pa_year").combobox("setValue",year);
		}
	}
</script>

<div id="monthRateTool">
	<input type="hidden" id="pa_monthRate_projectId" value="${projectId}" />
	<fd:searchform id="projectAnalysisSearchForm" isMoreShow="false"
		onClickSearchBtn="showmonthRate()" onClickResetBtn="resetmonthRate()">
		<fd:combobox title="{com.glaway.ids.pm.project.statisticalAnalysis.year}" name="pa_year" id="pa_year" queryMode="in"
			editable="false" maxLength="-1" panelMaxHeight="150"
			url="statisticalAnalysisController.do?yearCombo" textField="name"
			value="${curYear}" valueField="id" />
	</fd:searchform>
</div>

	<fd:groupgrid id="pa_monthRate"
		url="statisticalAnalysisController.do?searchMonthDatagrid&projectId=${projectId}"
		pagination="false" fitColumns="true" style="width:100%">
		<fd:columns>
			<fd:group>
				<fd:column colspan="1" title="{com.glaway.ids.pm.project.statisticalAnalysis.time}" field="time" align="center"></fd:column>
				<fd:column colspan="3" title="{com.glaway.ids.pm.project.statisticalAnalysis.wbs}" field="wbs" align="center"></fd:column>
				<fd:column colspan="3" title="{com.glaway.ids.pm.project.statisticalAnalysis.task}" field="task" align="center"></fd:column>
			</fd:group>
			<fd:group>
				<fd:column title="{com.glaway.ids.pm.project.statisticalAnalysis.month}"  field="month"></fd:column>
				<fd:column title="{com.glaway.ids.pm.project.statisticalAnalysis.wbstotal}" width="80" field="wbstotal"></fd:column>
				<fd:column title="{com.glaway.ids.pm.project.statisticalAnalysis.wbscomplete}" width="80" field="wbscomplete"></fd:column>
				<fd:column title="{com.glaway.ids.pm.project.statisticalAnalysis.wbsrate}" width="80" field="wbsrate"></fd:column>
				<fd:column title="{com.glaway.ids.pm.project.statisticalAnalysis.tasktotal}" width="80" field="tasktotal"></fd:column>
				<fd:column title="{com.glaway.ids.pm.project.statisticalAnalysis.taskcomplete}" width="80" field="taskcomplete"></fd:column>
				<fd:column title="{com.glaway.ids.pm.project.statisticalAnalysis.taskrate}" width="80" field="taskrate"></fd:column>
			</fd:group>
		</fd:columns>
	</fd:groupgrid>
