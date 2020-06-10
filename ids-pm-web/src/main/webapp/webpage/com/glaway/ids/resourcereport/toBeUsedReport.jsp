<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>资源预估使用率报表</title>
<t:base type="jquery,easyui,tools"></t:base>
	<script src="${pageContext.request.contextPath }/plug-in/js/highcharts7.0.2/highcharts.js"></script>
<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			$("#startAndEnd_BeginDate").datebox('setValue', '${startDate}');
			$("#startAndEnd_EndDate").datebox('setValue', '${endDate}');

			debugger;
			var resourceId = "${resourceId}";
			var useObjectId = "${useObjectId}";
			var startDate = "${startDate}";
			var endDate = "${endDate}";
			var startTime = "${startTime}";
			var endTime = "${endTime}";
			var resourceLinkId = "${resourceLinkId}";
			var resourceUseRate = "${resourceUseRate}";
			$.ajax({
				type : "POST",
				url : 'resourceLinkInfoController.do?drawResourceToBeUsedRate',
				data : {
					'resourceId' : resourceId,
					'startDate' : startDate,
					'endDate' : endDate,
					'startTime' : startTime,
					'endTime' : endTime,
					'useObjectId' : useObjectId,
					'resourceLinkId' : resourceLinkId,
					'resourceUseRate' : resourceUseRate
				},
				dataType : "json",
				success : function(data) {
					// var date=data.date;
					// var jsonStr = JSON.stringify(data.json);

					debugger
					// var jsonStr = JSON.stringify(data);
					var jsonStr = JSON.stringify(data);
					var xName = [];
					var yData = [];
					var yData2 = [];
					var yData3 = [];
					if(jsonStr!=null && jsonStr!=""){
						var curList = eval(jsonStr);
						for (var i = 0; i < curList.length; i++) {
							xName.push(curList[i].date);
							yData.push(parseInt(curList[i].usedRate));
							yData2.push(parseInt(curList[i].occupationWarn));
							yData3.push(parseInt(curList[i].willBeUsed));
						}
					}

					Highcharts.chart('container', {
						title: {
							text: '资源使用情况',
							style :{fontSize:'30px', fontFamily:'微软雅黑'}
						},
						xAxis: [{
							type: 'datetime',
							categories: xName,
							/*crosshair: true,*/
							labels : {
								rotation : 45  //旋转角度
							}
						}],

						yAxis: {
							title: {
								text: ''
							},
						},

						tooltip: {
							formatter : function(){
								return '<b>' + this.series.name + '<br></br>'+
										Highcharts.dateFormat('%Y-%M-%D',this.x) + '</br>' +
										Highcharts.numberFormat(this.y,0);
							}
						},

						legend: {
							layout: 'vertical',
							align: 'right',
							verticalAlign: 'middle'
						},

						/*plotOptions: {
                            series: {
                                label: {
                                    connectorAllowed: false
                                },
                                pointStart: startDate,
                            }
                        },*/

						series: [{
							name: '已使用率',
							data: yData
						},{
							name: '预估使用率',
							data: yData3,
							color:'red'
						},{
							name: '预警使用率',
							data: yData2,
							color:'orange'
						}],
						credits: {
							enabled: false
						}


					});
					debugger

					var url = "resourceLinkInfoController.do?conditionSearchForCreatePlanAddResource";
					var params = "&resourceId=" + resourceId + "&startDate=" + startDate
							+ "&endDate=" + endDate;
					url = url + params;

					//查询dataGrid:
					$('#createPlanAddResourceList').datagrid({
						url : url,
						pageNumber : 1
					});

					<%--$("#myForm").attr("action",--%>
					<%--"${brsUrl}/brs/frameset?__report=idsreport/createPlanAddResource.rptdesign&__showtitle=false"--%>
					<%--+"&__toolbar=false"+date);--%>
					<%--$("#usedRateJson").val(jsonStr);--%>
					<%--$("#resourceId").val(resourceId);--%>
					<%--$("#useObectId").val(useObjectId);--%>
					<%--$("#myForm").submit(); --%>
				}
			});

		}, 200);
	});
</script>
<style type="text/css">
.search_private {
	overflow: hidden;
	width: 99%;
}
</style>
</head>
<body>
	<fd:searchform id="seachPlanTag" onClickSearchBtn="searchResource()" onClickResetBtn="reset()" 
		isMoreShow="false" styleClass="search_private">
		<fd:inputDateRange id="startAndEnd"
			title="{com.glaway.ids.config.resource.queryTime}" interval="1"
			value="${startDate}"></fd:inputDateRange>
	</fd:searchform>
	<fd:panel id="resourceContainer" border="false" collapsible="false" style="overflow-x:hidden;"
			  width="100%" >
		<div id="container" style="min-width: 310px; height: 320px;margin: 0 auto"></div>
	</fd:panel>
	<fd:panel id="createPlanAddResourcePanel" border="false" collapsible="false"
			  width="100%" height="300px">
		<fd:datagrid fit="true" fitColumns="true" idField="id"
					 id="createPlanAddResourceList" checkbox="true">
			<fd:dgCol title="项目" field="projectName" align="left" sortable="true"/>
			<fd:dgCol title="计划名称" field="planName" align="left" sortable="true"/>
			<fd:dgCol title="资源占用时间" field="planStartTime" align="left" sortable="true"/>
			<fd:dgCol title="使用百分比（%）" field="usedRate" align="left" sortable="true"/>
			<fd:dgCol title="计划进度（%）" field="progressRate" align="left" sortable="true"/>
			<fd:dgCol title="计划负责人" field="launcher" align="left" sortable="true"/>
			<fd:dgCol title="项目经理" field="managerName" align="left" sortable="true"/>
		</fd:datagrid>
	</fd:panel>
	<%--<iframe id="reportFrame" name="report" scrolling="auto" frameborder="0" width="99%" height="600px"
		src="${url}&startDate=${startDate}&endDate=${endDate}&startTime=${startTime}&endTime=${endTime}&resourceId=${resourceId}&useObjectId=${useObjectId}&resourceLinkId=${resourceLinkId}&resourceUseRate=${resourceUseRate}">
	</iframe>--%>
</body>
<script>
	function searchResource() {
		var startDate = $("#startAndEnd_BeginDate").datebox("getValue");
		var endDate = $("#startAndEnd_EndDate").datebox("getValue");
		$('#reportFrame').attr('src', '${url}&startDate='+startDate+'&endDate='+endDate+'&startTime=${startTime}&endTime=${endTime}&resourceId=${resourceId}&useObjectId=${useObjectId}&resourceLinkId=${resourceLinkId}&resourceUseRate=${resourceUseRate}')
	}
	function reset() {
		$("#startAndEnd_BeginDate").datebox('setValue', '${startDate}');
		$("#startAndEnd_EndDate").datebox('setValue', '${endDate}');
	}
</script>
</html>
