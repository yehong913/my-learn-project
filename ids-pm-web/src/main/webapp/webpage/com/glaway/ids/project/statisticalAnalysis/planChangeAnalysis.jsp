<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript" src="plug-in/Highcharts-4.1.5/js/highcharts.js"></script>
<script type="text/javascript">
	function viewPlanPage(url) {
				$.fn.lhgdialog({
						content : 'url:' + url,
						width : 900,
						height : 490,
						title : '计划信息',
						button : [ {
							name : '关闭',
						focus : true
						} ]
					});
	}



	function showPlanChangeAnalysisReportNew() {
		debugger;
		//查询条件：
		var changeDateS = $("#changeTime_BeginDate").datebox('getValue');
		var changeDateE = $("#changeTime_EndDate").datebox('getValue');
		var startDateS = $("#startTime_BeginDate").datebox('getValue');
		var startDateE = $("#startTime_EndDate").datebox('getValue');
		var endDateS = $("#endTime_BeginDate").datebox('getValue');
		var endDateE = $("#endTime_EndDate").datebox('getValue');
		var projectId = $('#planChange_projectName').combobox('getValue');
		var projectType = $("#planChange_projectType").combotree('getValue');
		var projectManager = $("#projectManager").textbox('getValue');
		var projectOwner = $("#projectOwner").textbox('getValue');
		// var planChangeAnalysisIframe = document
		// 		.getElementById("planChangeAnalysisReport");
		var conditionCount = 0; //已选条件数

		$('#planChangeAnalysisSearchForm').find('*').each(function() {
			if ($(this).attr('name') == 'projectType') {
				if (projectType == '') {
					projectType = $(this).val();
				} else {
					projectType = projectType + "," + $(this).val();
				}
			}
		})

		if (changeDateS != '') {
            conditionCount++;
        }
		if (changeDateE != '') {
            conditionCount++;
		}
		if (startDateS != '') {
            conditionCount++;
        }
		if (startDateE != '') {
            conditionCount++;
        }
		if (endDateS != '') {
            conditionCount++;
		}
		if (endDateE != '') {
            conditionCount++;
        }
		//项目名称
		if (projectId == undefined || projectId == '') {
		} else {
			conditionCount++;
		}
		//项目类型
		var projectTypeStr = '';
		if (projectType != undefined && projectType != '') {
			var projectTypes = projectType.split(',');
			for (var i = 0; i < projectTypes.length; i++) {
				projectTypeStr += "'" + projectTypes[i] + "'";
				if (i < projectTypes.length - 1) {
					projectTypeStr += ',';
				}
			}
			conditionCount++;
		}
		//项目经理
		if (projectManager == undefined || projectManager == '') {
		} else {
			conditionCount++;
		}
		//项目负责人
		if (projectOwner == undefined || projectOwner == '') {
		} else {
			conditionCount++;
		}
		if (conditionCount == 0) {
			tip('请至少选择一个条件再查询!');
			return false;
		}

		debugger
		$.ajax({
			url : 'statisticalAnalysisController.do?getPlanChangeHighchartsInfo',
			type : 'post',
			data : {
                changeDateS : changeDateS,
                changeDateE : changeDateE,
                startDateS : startDateS,
                startDateE : startDateE,
                endDateS : endDateS,
                endDateE : endDateE,
                projectId : projectId,
                projectType : projectTypeStr,
                projectManager : projectManager,
                projectOwner : projectOwner
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					debugger;
					var xName = [];
					var yData = [];
					var yData2 = [];
					if(d.obj!=null && d.obj!=""){
						var curList = eval(d.obj);
						for (var i = 0; i < curList.length; i++) {
							xName.push(curList[i].changereason);
							yData.push(curList[i].changenumber);
							yData2.push(curList[i].pointNumber);

						}
					}
					$("#container").highcharts({
						chart: {
							zoomType: 'xy'
						},
						title: {
							text: '计划变更分析'
						},
						xAxis: [{
							categories: xName,
							/*crosshair: true,*/
							labels : {
								rotation : 45  //旋转角度
							}
						}],
						yAxis:[{
							//y轴左边数据：
							labels:{
								/*style:{
									color: Highcharts.getOptions().color[1]
								}*/
							},
							title : {
								text : '变更次数',
								/*style:{
									color: Highcharts.getOptions().color[1]
								}*/
							}
						},{
							//y轴右边数据：
							title : {
								text : '百分比',
								/*style:{
									color: Highcharts.getOptions().color[0]
								}*/
							},

							labels:{
								format:'{value}%',
								/*style:{
									color: Highcharts.getOptions().color[0]
								}*/
							},
							opposite: true
						}],
						tooltip: {
							shared : true
							// pointFormat : '{point.y}'
						},
						legend : {
							/*enabled : false*/
							layout:'vertical',
							align:'left',
							x:120,
							verticalAlign: 'top',
							floating : true,
							backgroundColor: 'rgba(255,255,255,0.25)'
						},
						/*plotOptions: {
							column: {
								borderWidth: 0
							}
						},*/
						series: [{
							name:'变更次数',
					        type:'column',
							data: yData,
							dataLabels : {
								enabled : true,
								crop : false,
								overflow : 'none',
								color : '#FFFFFF',
								align : 'center'
							},
							tooltip:{
								valueSuffix:''
							}
						},{
							name:'百分比',
							type:'spline',
							yAxis:1,
							data: yData2,
							color:'orange',
							lineWidth:1,
							tooltip:{
								valueSuffix:'%'
							}
						}],
						/*credits: {
							enabled: false
						}*/
					});
					var url = "statisticalAnalysisController.do?conditionSearch";
					var params = "&changeDateS=" + changeDateS + "&changeDateE="
							+ changeDateE + "&startDateS=" + startDateS + "&startDateE="
							+ startDateE + "&endDateS=" + endDateS + "&endDateE="
							+ endDateE + "&projectId=" + projectId + "&projectType="
							+ projectTypeStr + "&projectOwner=" + projectOwner
							+ "&projectManager=" + projectManager;
					url = url + params;

					//查询dataGrid:
					$('#planChangeAnalysisReportList').datagrid({
						url : url,
						pageNumber : 1
					});
				}
			}
		});
	}

	function resetPlanChangeAnalysisReport() {
		$("#changeTime_BeginDate").datebox("clear");
		$("#changeTime_EndDate").datebox("clear");
		$("#startTime_BeginDate").datebox('clear');
		$("#startTime_EndDate").datebox('clear');
		$("#endTime_BeginDate").datebox('clear');
		$("#endTime_EndDate").datebox('clear');
		$('#planChange_projectName').combobox('clear');
		$("#planChange_projectType").comboztree('clear');
		$("#projectManager").textbox('clear');
		$("#projectOwner").textbox('clear');
	}
</script>

<fd:panel id="laborLoadSearchPanel" border="false" collapsible="false"
		width="100%">
		<fd:searchform id="planChangeAnalysisSearchForm"
			onClickSearchBtn="showPlanChangeAnalysisReportNew()"
			onClickResetBtn="resetPlanChangeAnalysisReport()">
			<div style="float:right;">
			<fd:helpButton help="helpDoc:ChangeOfPlanAnalysis"/>
			</div>
			<fd:inputDateRange id="changeTime" interval="0"
				title="{com.glaway.ids.pm.project.statisticalAnalysis.changeTime}"
				value="" name="changeTime" opened="0"></fd:inputDateRange>
			<fd:inputDateRange id="startTime" interval="0"
				title="{com.glaway.ids.pm.project.statisticalAnalysis.planStartTime}"
				value="" name="startTime" opened="0"></fd:inputDateRange>
			<fd:inputDateRange id="endTime" interval="0"
				title="{com.glaway.ids.pm.project.statisticalAnalysis.planEndTime}"
				value="" name="endTime" opened="0"></fd:inputDateRange>
			<fd:combotree title="{com.glaway.ids.pm.project.projectmanager.type}"
				treeIdKey="id" url="statisticalAnalysisController.do?projTypeCombo"
				name="planChange_projectType" maxLength="-1"
				id="planChange_projectType" treePidKey="pid" multiple="true"
				prompt="{com.glaway.ids.common.lable.selectall}" panelHeight="150"
				treeName="name" queryMode="in"></fd:combotree>
			<fd:combobox
				title="{com.glaway.ids.pm.project.statisticalAnalysis.projectName}"
				name="planChange_projectName" id="planChange_projectName"
				queryMode="in" editable="true" maxLength="-1" panelMaxHeight="150"
				url="statisticalAnalysisController.do?projCombo" textField="name"
				valueField="id" />
			<fd:inputText
				title="{com.glaway.ids.pm.project.statisticalAnalysis.projectManager}"
				id="projectManager" maxLength="-1" />
			<fd:inputText
				title="{com.glaway.ids.pm.project.statisticalAnalysis.planOwner}"
				id="projectOwner" maxLength="-1" />
		</fd:searchform>
		<div id="container" style="min-width: 310px; height: 360px;margin: 0 auto"></div>
		</fd:panel>
		<fd:panel id="laborLoadPicPanel" border="false" collapsible="false"
				width="100%" height="400px">
			<fd:datagrid fit="true" fitColumns="true" idField="id"
						 id="planChangeAnalysisReportList" checkbox="true">
				<fd:dgCol title="变更原因" field="changeReason" align="left" sortable="true"/>
				<fd:dgCol title="变更发起人" field="ownerName" align="left" sortable="true"/>
				<fd:dgCol title="变更次数" field="changeNumber" align="left" sortable="true"/>
				<fd:dgCol title="项目名称" field="projectName" align="left" sortable="true"/>
				<fd:dgCol title="计划名称" field="planName" align="left" sortable="true"/>
				<fd:dgCol title="计划负责人" field="assignerName" align="left" sortable="true"/>
			</fd:datagrid>
		</fd:panel>

