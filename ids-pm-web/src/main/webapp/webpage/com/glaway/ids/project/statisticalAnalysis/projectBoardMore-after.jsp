<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
	<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
	<script type="text/javascript" src="${pageContext.request.contextPath }/plug-in/js/highcharts7.0.2/highcharts.js"></script>
<style type="text/css">
.projectBoardPortlet{margin:0;padding:0}
.projectBoardPortlet li {
	list-style: none;
	float: left;
	margin: 0px;
	font-size:12px;
	padding:0;
}
</style>
</head>
<body>
<div>
	<ul class="projectBoardPortlet">
		<c:forEach items="${voList}" var="vo">
			<li style="border:1px solid #ccc;margin:5px">
				<table>
					<tr>
						<td>
							<table width="100%">
								<tr>
									<td>
										<table width="100%">
											<tr>
												<td><a href='#' onclick="goDetail('${vo.pid}')" style='color: blue'>${vo.pname}</td>
											</tr>
											<tr>
												<td>
													<a id="${vo.pid}_mname" title="${vo.mname}" style="color: black"> 
														<c:if test="${fn:length(vo.mname) < 20}">${vo.mname}</c:if> 
														<c:if test="${fn:length(vo.mname) > 20}">${vo.showName}</c:if>
													</a>
												</td>
												<td>${vo.unum}</td>
											</tr>
											<tr>
												<td>${vo.time }</td>
											</tr>
										</table>
									</td>
									<td align="right">
										<c:if test="${vo.color eq 'red'}">
											<img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/red.png">
										</c:if> 
										<c:if test="${vo.color eq 'green'}">
											<img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/green.png">
										</c:if> 
										<c:if test="${vo.color eq 'orange'}">
											<img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/orange.png">
										</c:if>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<div id="projectBoardMoreAfterContainer${vo.pid}" style="min-width: 320px;height: 250px;margin: 0 auto;max-width:320px;"></div>
							<%--<c:if test="${riskPluginValid eq true}">
								<iframe id="project${vo.pid }" width="320px" height="250px" scrolling="auto" frameborder="0"
									src="/brs/preview?__report=idsreport/projectBoard.rptdesign&__svg=false&projectId=${vo.pid}&projectName=${vo.pname}&manager=${vo.mname}&number=${vo.unum}&time=${vo.time}&planLevel=${vo.planlevel}"></iframe>
							</c:if>
							<c:if test="${riskPluginValid eq false}">
								<iframe id="project${vo.pid }" width="320px" height="250px" scrolling="auto" frameborder="0"
									src="/brs/preview?__report=idsreport/projectBoardWithoutRisk.rptdesign&__svg=false&projectId=${vo.pid}&projectName=${vo.pname}&manager=${vo.mname}&number=${vo.unum}&time=${vo.time}&planLevel=${vo.planlevel}"></iframe>
							</c:if>--%>
						</td>
					</tr>
				</table>
			</li>
		</c:forEach>
	</ul>
</div>

<script type="text/javascript">
	function show(){
		<c:forEach items="${voList}" var="vo">
			showProjectBoardMoreAfterReport("${vo.pid}","${vo.planlevel}");
		</c:forEach>
	}

	function showProjectBoardMoreAfterReport(projectId,planLevel){
		var categories = [];
		var data_one = [];
		var data_two = [];
		var data_three = [];
		$.post("statisticalAnalysisController.do?getProjectBoardReportData&projectId="+projectId+"&planLevel="+planLevel,function(data){
			debugger;
			for(var i in data.rows){
				categories.push(data.rows[i].name);
				data_one.push(parseInt(data.rows[i].number1));
				data_two.push(parseInt(data.rows[i].number2));
				data_three.push(parseInt(data.rows[i].number4));
			}

			var chart = Highcharts.chart('projectBoardMoreAfterContainer'+projectId, {
				chart: {
					type: 'column'
				},
				title: {
					text: ''
				},
				xAxis: {
					categories: categories
				},
				yAxis: {
					min: 0,
					title: {
						text: ''
					}
				},
				tooltip: {
					pointFormat: '<span style="color:{series.color}"> <b>{point.y}</b>' +
							'({point.percentage:.0f}%)<br/>',
					//:.0f 表示保留 0 位小数，详见教程：https://www.hcharts.cn/docs/basic-labels-string-formatting
					shared: true
				},
				plotOptions: {
					column: {
						stacking: 'percent'
					}
				},
				legend: {
					enabled:false

				},
				colors:['#447EC1','#ed7768','#f6ae63'],
				series: [{
					name: '',
					data: data_one
				}, {
					name: '',
					data: data_two
				}, {
					name: '',
					data: data_three
				}],
				credits:{
					enabled: false
				}
			});
		});
	}
</script>
</body>
</html>