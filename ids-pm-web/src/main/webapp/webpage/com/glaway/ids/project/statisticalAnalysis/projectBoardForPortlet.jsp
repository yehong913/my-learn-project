<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
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
		.tuli{text-align:center; font-size:12px;}
		.tuli img{margin:3px 5px 0 10px}
	</style>
</head>
<body>

<div>

    <div id="projectBoardPortlet" class="easyui-panel" style="width: 100%" >
		<ul class="projectBoardPortlet">
			<c:forEach items="${voList}" var="vo">
				<li style="border:0px solid #ccc;margin:0; width: 49%; height:295px; ">
					<div style="margin: 4px -4px; padding:4px;">
						<div><a href='javascript:void(0);' onclick="goDetail('${vo.pid}')" style='color: #0c60aa;display:block; margin: 4px 100px 4px 0; font-size: 14px;'>${vo.pname}</a>
							<span style="display:inline-block; width: 40px; height: 14px; float: right; margin-top: -22px; margin-right: 22px;">${vo.unum}</span>
							<span style="display:inline-block; width: 14px; height: 14px; float: right; margin-top: -20px; margin-right:16px">
								<c:if test="${vo.color eq 'red'}">
									<img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/red.png">
								</c:if>
								<c:if test="${vo.color eq 'green'}">
									<img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/blue.png">
								</c:if>
								<c:if test="${vo.color eq 'orange'}">
									<img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/orange.png">
								</c:if>
							</span>
						</div>
						<div>

						<a id="${vo.pid}_mname" title="${vo.mname}" style="color: #333; margin:4px 0; display:inline-block">
							<c:if test="${fn:length(vo.mname) < 20}">${vo.mname}</c:if>
							<c:if test="${fn:length(vo.mname) > 20}">${vo.showName}</c:if>
						</a>

						</div>
						<div style="color:#999">${vo.time}</div>
					</div>

					<div id="projectBoardForPortalContainer${vo.pid}" style="min-width: 98%;height: 190px;margin: 0 auto;max-width:98%; margin-top:20px"></div>

								<%--<c:if test="${riskPluginValid eq true}">
									<iframe id="project${vo.pid}" width="300px" height="235px" scrolling="no" frameborder="0" 
										src="${brsUrl}/brs/preview?__report=idsreport/projectBoard.rptdesign&__svg=false&projectId=${vo.pid}&projectName=${vo.pname}&manager=${vo.mname}&number=${vo.unum}&time=${vo.time}&planLevel=${vo.planlevel}"></iframe>
								</c:if>
								<c:if test="${riskPluginValid eq false}">
									<iframe id="project${vo.pid}" width="300px" height="235px" scrolling="no" frameborder="0" 
										src="${brsUrl}/brs/preview?__report=idsreport/projectBoardWithoutRisk.rptdesign&__svg=false&projectId=${vo.pid}&projectName=${vo.pname}&manager=${vo.mname}&number=${vo.unum}&time=${vo.time}&planLevel=${vo.planlevel}"></iframe>
								</c:if>--%>

				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="tuli"><img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/red.png" width="12px" height="12px">报警  <img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/orange.png" width="12px" height="12px">预警  <img src="webpage/com/glaway/ids/project/statisticalAnalysis/img/blue.png" width="12px" height="12px">正常</div>
</div>
<script type="text/javascript">
	$(document).ready(
		function() {
			setTimeout(function(){
				<c:forEach items="${voList}" var="vo">
					showProjectBoardReport("${vo.pid}","${vo.planlevel}");
				</c:forEach>
			},1000);
	});

	function showProjectBoardReport(projectId,planLevel){
		var categories = [];
		var data_one = [];
		var data_two = [];
		var data_four = [];
		$.post("statisticalAnalysisController.do?getProjectBoardReportData&projectId="+projectId+"&planLevel="+planLevel,function(data){
			debugger;
			for(var i in data.rows){
				categories.push(data.rows[i].name);
				data_one.push(parseInt(data.rows[i].number1));
				data_two.push(parseInt(data.rows[i].number2));
				data_four.push(parseInt(data.rows[i].number4));
			}

			var chart = Highcharts.chart('projectBoardForPortalContainer'+projectId, {
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
					data: data_four
				}],
				credits:{
					enabled: false
				}
			});

		});
	}

	var tabTitle ="";
	function viewMorePb() {
		debugger;
		if (tabTitle != '') {
			$('#maintabs').tabs('close', tabTitle);
		}
		tabTitle = '<spring:message code="com.glaway.ids.common.portal.projectboard"/>';
		var url = '/ids-pm-web/statisticalAnalysisController.do?goProjectBoard&isIframe=true';
		top.addTab(tabTitle, url, 'pictures');
	}

	function goDetail(projectId) {
		var title = '项目计划';
		if (!top.$('#maintabs').tabs('exists', title)) {
			top.addTabById(title,
					"/ids-pm-web/projectMenuController.do?projectMenu&isIframe=true&afterIframe=true&projectId=" + projectId,
					'pictures','4028efee4c3617ec014c362eed6c0012');
		} else {
			top.$('#maintabs').tabs('close', title);
			top.addTabById(title,
					"/ids-pm-web/projectMenuController.do?projectMenu&isIframe=true&afterIframe=true&projectId=" + projectId,
					'pictures','4028efee4c3617ec014c362eed6c0012');
		}
	}
</script>
</body>
</html>
