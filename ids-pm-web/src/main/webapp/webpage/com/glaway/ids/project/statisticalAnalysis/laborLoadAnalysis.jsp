<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
	<t:base type="jquery,easyui,tools"></t:base>
	<script type="text/javascript" src="plug-in/js/highcharts7.0.2/highcharts.js"></script>
</head>

<body>
	<fd:panel id="laborLoadSearchPanel" border="false" collapsible="false"
			  width="100%">
		<fd:searchform id="laborLoadAnalysisSearchForm"
					   onClickSearchBtn="showLaborLoadAnalysisReport()"
					   onClickResetBtn="resetLaborLoadAnalysisReport()"
					   help="helpDoc:PersonnelLoadAnalysis">
			<fd:combobox
					title="{com.glaway.ids.pm.project.statisticalAnalysis.orderType}"
					name="lla_orderType" id="lla_orderType" queryMode="in"
					editable="false" maxLength="-1" panelMaxHeight="150"
					url="statisticalAnalysisController.do?orderTypeCombo"
					textField="name" valueField="id" selectedValue="1" />
			<fd:inputDateRange id="statisticsTime" interval="0"
							   title="{com.glaway.ids.pm.project.statisticalAnalysis.statisticsTime}"
							   value="" name="statisticsTime" opened="0"></fd:inputDateRange>
			<fd:combotree
					title="{com.glaway.ids.pm.project.statisticalAnalysis.dept}"
					treeIdKey="id"
					url="statisticalAnalysisController.do?planOwnerDepartCombo"
					name="lla_departName" maxLength="-1" id="lla_departName"
					treePidKey="pid" multiple="true"
					prompt="{com.glaway.ids.common.lable.selectall}" panelHeight="150"
					treeName="name" queryMode="in"></fd:combotree>
			<fd:combobox
					title="{com.glaway.ids.pm.project.statisticalAnalysis.project}"
					name="lla_projectName" id="lla_projectName" queryMode="in"
					editable="true" maxLength="-1" panelMaxHeight="150"
					url="statisticalAnalysisController.do?projCombo" textField="name"
					valueField="id" />
			<fd:combotree title="{com.glaway.ids.pm.project.projectmanager.type}"
						  treeIdKey="id" url="statisticalAnalysisController.do?projTypeCombo"
						  name="lla_projectType" maxLength="-1" id="lla_projectType"
						  treePidKey="pid" multiple="true"
						  prompt="{com.glaway.ids.common.lable.selectall}" panelHeight="150"
						  treeName="name" queryMode="in"></fd:combotree>
			<fd:inputText
					title="{com.glaway.ids.pm.project.statisticalAnalysis.owner}"
					id="lla_owner" maxLength="-1" />
		</fd:searchform>
	</fd:panel>
	<fd:panel id="laborLoadPicPanel" border="false" collapsible="false"
			  width="100%">
		<fd:panel id="laborLoadCharts" border="false" collapsible="false" style="overflow-x:hidden;"
				  width="100%" >
			<div id="container" style="min-width: 600px;height: 330px;"></div>
		</fd:panel>
		<fd:panel id="laborLoadGrid" border="false" collapsible="false" style="overflow-x:hidden;"
				  width="100%" height="400x">
			<fd:datagrid checkbox="false" fit="true" fitColumns="true" idField="id" checkOnSelect="false"
						 id="laborLoadList">
				<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.owner}" field="ownerName" sortable="fale"/>
				<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.projectNumber}" field="projectNumber" sortable="fale"/>
				<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.projectName}" field="projectName" sortable="fale"/>
				<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.planName}" field="planName"  sortable="fale"/>
				<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.progress}" field="progressRate" sortable="fale"/>
				<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.planStatus}" field="status" sortable="fale"/>
				<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.planLevel}" field="planlevel" sortable="fale"/>
				<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.startTime}" field="startTime" sortable="fale"/>
				<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.endTime}" field="endTime" sortable="fale"/>
				<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.aname}" field="assignerName" sortable="fale"/>
				<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.atime}" field="assignTime" sortable="fale"/>
				<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.planType}" field="planType" sortable="fale"/>
			</fd:datagrid>
		</fd:panel>
	</fd:panel>

<script type="text/javascript">
	$(document).ready(function() {
		/*window.setTimeout("resetLaborLoadAnalysisReport()", 500); // 等待0.5秒，让分类树结构加载出来
		window.setTimeout("showLaborLoadAnalysisReport()", 500); // 等待0.5秒，让分类树结构加载出来*/
		window.setTimeout(function(){
			resetLaborLoadAnalysisReport();
			showLaborLoadAnalysisReport();
		},500);
	});


	function showLaborLoadAnalysisReport() {
		debugger
		var beginDate = $("#statisticsTime_BeginDate").datebox('getValue');
		var endDate = $("#statisticsTime_EndDate").datebox('getValue');
		var departId = '';
		var projectId = $('#lla_projectName').combobox('getValues');
		var projectType = '';
		var owner = $("#lla_owner").textbox('getValue');
		var seq = $('#lla_orderType').combobox('getValues')[0];

		var laborLoadAnalysisIframe = document
				.getElementById("laborLoadAnalysisReport_");

		$('#laborLoadAnalysisSearchForm').find('*').each(function() {
			if ($(this).attr('name') == 'lla_projectType') {
				if (projectType == '') {
					projectType = $(this).val();
				} else {
					projectType = projectType + "," + $(this).val();
				}
			} else if ($(this).attr('name') == 'lla_departName') {
				if (departId == '') {
					departId = $(this).val();
				} else {
					departId = departId + "," + $(this).val();
				}
			}
		})
		//部门
		var departIdStr = ' ';
		if (departId != undefined && departId != '') {
			var departIds = departId.split(',');
			for (var i = 0; i < departIds.length; i++) {
				departIdStr += "'" + departIds[i] + "'";
				if (i < departIds.length - 1) {
					departIdStr += ',';
				}
			}
		}
		//项目
		if (projectId == undefined || projectId == '') {
			projectId = ' ';
		}
		//项目类型
		var projectTypeStr = ' ';
		if (projectType != undefined && projectType != '') {
			var projectTypes = projectType.split(',');
			for (var i = 0; i < projectTypes.length; i++) {
				projectTypeStr += "'" + projectTypes[i] + "'";
				if (i < projectTypes.length - 1) {
					projectTypeStr += ',';
				}
			}
		}
		//计划负责人
		if (owner == undefined || owner == '') {
			owner = ' ';
		}
		var params = "&beginDate=" + beginDate + "&endDate=" + endDate
				+ "&departId=" + departIdStr + "&projectId=" + projectId
				+ "&projectType=" + projectTypeStr + "&owner=" + owner
				+ "&seq=" + seq;
		var url = 'statisticalAnalysisController.do?searchlaborLoadList';
		url = url + params;
		$('#laborLoadList').datagrid({
			url : url,
			queryParams : params,
			pageNumber : 1
		});

		$.ajax({
			url : 'statisticalAnalysisController.do?getLaborLoadListCharts&projectId='+projectId,
			type : 'get',
			data : {
				beginDate : beginDate,
				endDate : endDate,
				departId : departId,
				projectType : projectType,
				owner : owner,
				seq : seq
			},
			cache : false,
			success : function (data) {
				var d = $.parseJSON(data);
				if (d.success) {
					debugger
					var infos = d.obj;
					/*for (var i = 0; i < infos.length; i++) {
						$.each(infos[i],function (key,value) {
							infoArr.push([key,value]);
						})
					}*/
					var color,data,name;
					var array = [];
					var xvalue = infos.xvalue;
					var df = infos.series;
					for (i in df) {
						color = df[i].color;
						data = df[i].data;
						name = df[i].name;
						array.push({name,data,color})
					};
					var options = {
						chart : {
							type : 'bar'  //指定图表的类型
						},
						title : {
							text : '计划情况TOP10'
						},
						credits:{
							enabled:false
						},
						xAxis : {
							categories : xvalue
						},
						yAxis : {
							min : 0,
							title : {
								text : ''
							}
						},
						legend : {       //数据标注
							reversed : true
						},
						plotOptions : {
							series : {
								stacking : 'normal'
							}
						},
						series : array
					};

					$('#container').highcharts(options);

				}
			}
		});
	}
	function resetLaborLoadAnalysisReport() {
		$('#statisticsTime_BeginDate').datebox('setValue',
				dateFmtYYYYMMDD(new Date()));
		$('#statisticsTime_EndDate').datebox('setValue',
				dateFmtYYYYMMDD(new Date()));
		$("#lla_departName").comboztree("clear");
		$("#lla_projectName").combobox("clear");
		$("#lla_projectType").comboztree("clear");
		$("#lla_orderType").combobox("setValue", "1");
		$("#lla_owner").textbox("clear");
	}
</script>
</body>
</html>

