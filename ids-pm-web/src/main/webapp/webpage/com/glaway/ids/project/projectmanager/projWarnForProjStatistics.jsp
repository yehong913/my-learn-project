<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
<body>

<div >
	<fd:panel id="projectwarnBrs" border="false" collapsible="false" style="overflow-x:hidden;"
			width="100%" >
		<div id="projWarnContainer" style="min-width: 400px;height: 400px;margin: 0 auto;max-width:600px;"></div>
	</fd:panel>
	<fd:panel id="projectwarnGrid" border="false" collapsible="false" style="overflow-x:hidden;"
			  width="99%" height="400px" >
		<fd:datagrid checkbox="false" fit="true" fitColumns="true" idField="id" checkOnSelect="false"
					 id="projectwarnGridList" url="projStatisticsController.do?queryProjectwarnGrid&projectId=${projectId}" >

			<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.executeSituation}"
					  field="progressRate" formatterFunName="showProgressRate"/>

			<fd:dgCol title="{com.glaway.ids.pm.project.projecttemplate.planName}"
					  field="planName" formatterFunName="showPlanName"  />

			<fd:dgCol title="{com.glaway.ids.pm.project.projecttemplate.planLevel}"
					  field="planLevelName"  />

			<fd:dgCol title="{com.glaway.ids.pm.project.projecttemplate.status}"
					  field="bizCurrent" formatterFunName="showBizCurrent"  />

			<fd:dgCol title="{com.glaway.ids.pm.project.statisticalAnalysis.owner}"
					  field="ownerShow"  />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.startTime}"
					  field="planStartTime" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.endTime}"
					  field="planEndTime" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projecttemplate.workTimes}"
					  field="workTime"  formatterFunName="showWorkTime"/>
		</fd:datagrid>
		<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
	</fd:panel>
</div>

<script type="text/javascript">
	function viewPlanPage(url) {
		$.fn.lhgdialog({
			content: 'url:'+url,
			width : 800,
			height : 490,
			title : '<spring:message code="com.glaway.ids.pm.project.projStatistics.planInfo"/>',
			button : [
				{
					name:'<spring:message code="com.glaway.ids.common.btn.close"/>',
					focus : true
				}]
		});
	}


	function showPlanName(val, row, value) {
		if (val == undefined){
			val = '';
		}
		shtml = '<span title="'+val+'">' + val + '</span>';
		return '<a href="#" onclick="viewPlanDetail(\'' + row.id
				+ '\')" style="color:blue">' + shtml + '</a>';
	}


	// 查看计划信息
	function viewPlanDetail(id) {
		var dialogUrl = 'planController.do?goCheck&id=' + id;
		createDialog('viewPlanDialog', dialogUrl);
	}


	function showBizCurrent(val,row,index){
		if(val == "EDITING"){
			return "拟制中"
		}else if(val == "LAUNCHED"){
			return "已发布"
		}else if(val == "TOBERECEIVED"){
			return "待接收"
		}else if(val == "ORDERED"){
			return "执行中"
		}else if(val == "FEEDBACKING"){
			return "完工确认"
		}else if(val == "FINISH"){
			return "已完工"
		}else if(val == "INVALID"){
			return "已废弃"
		}
	}


	function showWorkTime(val,row,index){
		return val+"天";
	}

	function showProgressRate(val,row,index){
		return val+"%";
	}

	$(function(){
		var resList = eval('${resList}');
		var series = [];
		for(var i in resList){
			if(i == "0"){
				var obj = {
					name:resList[i].WARN,
					y:parseInt(resList[i].WARNNUMBER),
					sliced: true,
					selected : true
				};
				series.push(obj);
			}else{
				var obj = {
					name:resList[i].WARN,
					y:parseInt(resList[i].WARNNUMBER)
				};
				series.push(obj);
			}

		}

		try {
			Highcharts.chart('projWarnContainer', {
				chart:{
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false,
					type : 'pie'
				},
				title : {
					text:'计划预警',
					style:{
						fontWeight:"bold",
						fontSize : '24px'
					}
				},
				tooltip:{
					pointFormat:'{series.name}:<b>{point.percentage:.2f}%</b>'
				},
				plotOptions:{
					pie:{
						allowPointSelect:true,
						cursor:'pointer',
						dataLabels:{
							enabled:true,
							format:'<b>{point.name}</b>:{point.percentage:.2f}%',
							style:{
								color:(Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
							}
						}
					}
				},
				series:[{
					name : '占比',
					colorByPoint:true,
					data:series
				}],
				credits: {
					enabled: false
				}
			});
		} catch (e) {

		}


	});
</script>



</body>
</html>