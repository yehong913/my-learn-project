<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
	<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
	<script type="text/javascript" src="${pageContext.request.contextPath }/plug-in/js/highcharts7.0.2/highcharts.js"></script>
	<script src="${pageContext.request.contextPath}/plug-in/js/highcharts7.0.2/oldie.js" ></script>
	<style>
		.select_dd{width:240px;}
		.select_dde{width:150px}
		.search_title{margin-top:5px}
		.button_nor_search{    color: #fff;
			display:inline-block;
			height: 24px;
			line-height: 24px;
			padding:4px 18px;
			vertical-align:middle;
			cursor: pointer;
			-moz-border-radius: 2px;
			-webkit-border-radius: 2px;
			border-radius: 2px;
			float: left;
			margin-top: 4px;
			BACKGROUND-COLOR: #0C60AA;}
	</style>
</head>
<body>


<div id="tool125"  style="position:relative;">

	<fd:combobox id="portletProjectIds" title="{com.glaway.ids.pm.project.projectmanager.auth.selectProject}"
				 panelMaxHeight="200" textField="id" multiple="true" selectedValue="${selectedValue}"
				 valueField="text" url="projStatisticsController.do?getSelectData&type=1"
				></fd:combobox>
	<a id ="confirm" onclick="showProjWarnReport()" class="button_nor_search">确定</a>
</div>
	<div id="container" style="margin-top:20px;width: 100%;height:294px;"></div>

	<script type="text/javascript">
	$(document).ready(
		function() {
			debugger;
			var projectIds ="${selectedValue}";
            var projectArray = projectIds.split(',');
			var str = "";
		    for (var i=0;i<projectArray.length;i++) {
		    	str+="'" + projectArray[i] + "'";
		    	if (i!=projectArray.length-1) {
		    		str += ",";
		    	}
		    }
		    showReport(str);
		});

	function showReport(str){
		var projectNames = [];
		var isWarnYesCnt = [];
		var isWarnNoCnt = [];
		$.post("projStatisticsController.do?getProjWarnReportData&str="+str,function (data) {
			debugger
			for(var i in data.rows){
				projectNames.push(data.rows[i].name);
				isWarnYesCnt.push(parseInt(data.rows[i].warnyesnumber));
				isWarnNoCnt.push(parseInt(data.rows[i].warnnonumber));
			}

			debugger
			$("#container").highcharts({
				chart: {
					type: 'column'
				},
				title: {
					text: ''
				},
				xAxis: {
					categories: projectNames
				},
				yAxis: {
					min: 0,
					title: {
						text: ''
					},
					stackLabels: {
						enable: false,
						style: {
							fontWeight: 'bold',
							color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
						}
					}
				},
				legend: {
					strokewidth: 0

				},
				colors:['#ed7768','#447ec1'],
				tooltip: {
					formatter: function () {
						return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + this.y + '<br/>' + '总量： ' + this.point.stackTotal;
					}
				},
				plotOptions: {
					column: {
						stacking: 'normal',
						dataLabels: {
							enabled: true,
							color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
							style: {
								textOutline: '1px 1px black'
							}
						}
					}
				},
				series: [{
					name: '<b style="font-weight: 500">是</b>',
					data: isWarnYesCnt
				},  {
					name: '<b style="font-weight: 500">否</b>',
					data: isWarnNoCnt
				}],
				credits:{
					enabled: false
				}
			});
		});
	}

	function showProjWarnReport() {
		var projectIds = $('#portletProjectIds').combobox('getValues');
		if (projectIds=="") {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.mustSelectOne"/>');
			return;
		}
		if (projectIds.length > 10) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.mostSelectTen"/>');
			return;
		}
		else {
			$.ajax({
				url : "projStatisticsController.do?ProjWarnPage&projectIds="+projectIds ,
				type : 'post',
				async : false,
				success :function() {
				}
			});

			var str = "";
		    for (var i=0;i<projectIds.length;i++) {
		    	str+="'" + projectIds[i] + "'";
		    	if (i!=projectIds.length-1) {
		    		str+= ",";
		    	}
		    }
			showReport(str);
		}
	}
	</script>

</body>
</html>
