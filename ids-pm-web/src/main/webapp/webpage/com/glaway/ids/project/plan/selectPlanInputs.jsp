<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script src = "webpage/com/glaway/ids/javascript/ajaxfileupload.js"></script>
</head>
<body>
<div class="easyui-panel" fit="true">
	<input id="tempId" name="tempId" type="hidden" value="${tempId}">
	<input id="planId" name="planId" type="hidden" value="${planId}">
	<input id="useObjectId" name="useObjectId" type="hidden" value="${useObjectId}">
	<input id="inputsName" name="inputsName" type="hidden" value="${inputsName}">
		
			<div id="planlisttb">
			 <fd:searchform id="seachPerposeTag"  onClickSearchBtn="searchPerpose()" onClickResetBtn="tagsearchPerposeReset()">
				<fd:inputText title="{com.glaway.ids.pm.project.plan.planName}" name="Plan.planName" id="searchPlanName" queryMode="like"/>
				<fd:combobox id="searchPlanLevel" textField="name" title="{com.glaway.ids.pm.project.plan.planLevel}" name="Plan.planLevelInfo.id" editable="false" valueField="id" prompt="全部"
					url="planController.do?useablePlanLevelList" queryMode="in" multiple="true"></fd:combobox>	
				<fd:combobox id="searchBizCurrent" textField="title" title="{com.glaway.ids.common.lable.status}" name="Plan.bizCurrent"  editable="false" valueField="name" prompt="全部"
					url="planController.do?statusListForInputs" queryMode="in" multiple="true"></fd:combobox>	
				<fd:inputText title="{com.glaway.ids.common.lable.owner}"  id="searchOwner"/>
				<fd:inputDateRange id="planDateRange" interval="0" title="{com.glaway.ids.common.lable.starttime}" name="Plan.planStartTime" opened="0"></fd:inputDateRange>
				<fd:inputDateRange id="planEndDateRange" interval="0" title="{com.glaway.ids.common.lable.endtime}" name="Plan.planEndTime" opened="0"></fd:inputDateRange>	
				<fd:inputNumber  title="{com.glaway.ids.pm.project.plan.workTime}" name="Plan.workTime" id="searchWorkTime" queryMode="le&&ge" />
			</fd:searchform>
			</div>
			<fd:datagrid id="planlist" checkbox="false" toolbar="#planlisttb" fitColumns="true"  pagination="false" rownumbers="false"
				url="planController.do?planInputsList&parentPlanId=${parentPlanId}&id=${planId}&projectId=${projectId}" idField="id" fit="true" onLoadSuccess="selectIds">
			<fd:dgCol title="编号" field="planNumber" width="20" sortable="true"  />
			<fd:dgCol title="计划名称" field="displayName" width="30" sortable="true" formatterFunName="viewBasicLineInfo"  />
			<fd:dgCol title="计划等级" field="planLevelInfo" width="30" sortable="true" />
			<fd:dgCol title="状态" field="bizCurrentInfo" width="20" sortable="true" />
			<fd:dgCol title="负责人" field="ownerInfo" width="40" sortable="true" />
			<fd:dgCol title="开始时间" field="planStartTime" width="30" sortable="true" />
			<fd:dgCol title="结束时间" field="planEndTime" width="30" sortable="true" />
			<fd:dgCol title="工期" field="workTime" width="15"  sortable="true" />
		</fd:datagrid>
</div>	
<fd:dialog id="viewPlanTaskDialog" width="790px" height="500px"
		modal="true" title="{com.glaway.ids.pm.project.plan.viewTaskInfo}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
	<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
	<script type="text/javascript">
	
	
	function validateSelectedBeforeSave(){
		debugger;
		var rows = $("#planlist").datagrid('getSelections');
		if(rows == '' || rows == null){
			top.tip('请选择一条数据');
			return false;
		}else{
			return true;
		}
		
	}
	
	// 计划名称链接事件
	function viewBasicLineInfo(val, row, index) {
		if(val == undefined)val = '';
		shtml = '<span title="'+val+'">'+val+'</span>';
		return '<a href="#" onclick="viewBasicLine(\'' + row.id + '\')" style="color:blue">' + shtml + '</a>';		
	}
	
	// 查看计划信息
	function viewBasicLine(id) {
		var dialogUrl = 'planController.do?goCheck&id=' + id;
		createDialog('viewPlanTaskDialog', dialogUrl);
	}
	
	// 选择计划前置树加载完毕之后，将已选的前置设置为选中状态
	function selectIds() {
 		var selectIds = '${selectIds}';
		if(selectIds != null && selectIds != '' && selectIds != undefined){
			var ids = selectIds.split(",");
			for(var i = 0; i < ids.length; i++){
				$('#planlist').datagrid('selectRecord',ids[i]);	
			} 
		}
	}
	
	function getLoadData(){
	    var ids = [];
	    var  datas;
	    var rows = $("#planlist").datagrid('getSelections');
	    if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			 datas= {
					ids : ids.join(',')
				};
			$.ajax({
				url : 'riskProblemTaskController.do?doAddTask'+ '&taskId=${taskId}',
				async : false,
				cache : false,
				type : 'post',
				data : datas,
				cache : false,
				success : function(data) {
					
				}
			});
		} else {
			tip('<spring:message code="com.glaway.ids.pm.project.plan.selectAdd"/>');
		}
	    return datas;
}
	
	
	
		// 选择计划前置树加载完毕之后，将已选的前置设置为选中状态
		function selectedExistPrepose() {
			var preposeIds = $('#preposeIds').val();
			if(preposeIds != null && preposeIds != '' && preposeIds != undefined){
				var ids = preposeIds.split(",");
				for(var i = 0; i < ids.length; i++){
					planPreposeList.selectRowById(ids[i],true,true,true);
					planPreposeList.openItem(ids[i]);
				} 
			}
		}
	
		// 查看计划信息
		function viewPlan(id) {
			$.fn.lhgdialog({
				content : 'url:planController.do?goCheck&id=' + id,
				lock : true,
				width : 630,
				height : 400,
				zIndex : 5000,
				title : '计划信息',
				opacity : 0.3,
				cache : false
			});
		}
		
		function closePlan() {
			$.fn.lhgdialog("closeSelect");
		}
		
		//计划名称链接事件
		function viewPlanInfoSearch(val, row, index) {
			if(row.result != "false"){
				return '<a href="#" onclick="viewPlan(\'' + row.id
				+ '\')" style="color:gray">' + val + '</a>';
			}else{
				return '<a href="#" onclick="viewPlan(\'' + row.id
				+ '\')" style="color:blue">' + val + '</a>';
			}		
		}
		
		
		function searchPerpose(){
			//var planName = $('#searchPlanName').val();
			
			//var bizCurrent = '';
		/* 	$('#seachPerposeTag').find('*').each(function() {
				if ($(this).attr('name') == 'Plan.bizCurrent') {
					if (bizCurrent == '') {
						bizCurrent = $(this).val();
					} else {
						bizCurrent = bizCurrent + "," + $(this).val();
					}
				}
			}); */
			
		/* 	var queryParams = $("#planlist").fddatagrid('queryParams', "seachPerposeTag");
			queryParams['bizCurrent'] = '';
			
			$('#seachPerposeTag').find('*').each(function() {
				if ($(this).attr('name') == 'Plan.bizCurrent') {
					queryParams["bizCurrent"] += "," + $(this).val();
					if (bizCurrent == '') {
						bizCurrent = $(this).val();
					} else {
						bizCurrent = bizCurrent + "," + $(this).val();
					}
				}
			}); */
			

			
			var userName = $('#searchOwner').val();
			var progressRate = $('#searchProgressRate').val();
			if (progressRate < 0) {
				progressRate = -1;
			}
			var workTime = $('#searchWorkTime').val();
			if (workTime < 0) {
				workTime = -1;
			}
			var planNumber = $('#searchPlanNumber').val();
			var planName = $('#searchPlanName').val();
		
			var planStartTime = '';
			var planEndTime = '';
			var planStartTime_Begin = $('#planDateRange_BeginDate').datebox('getValue');
			var planStartTime_End = $('#planDateRange_EndDate').datebox('getValue')
			var planEndTime_Begin = $('#planEndDateRange_BeginDate').datebox('getValue');
			var planEndTime_End = $('#planEndDateRange_EndDate').datebox('getValue')
			
			if (planStartTime_Begin != null && planStartTime_Begin != '' && planStartTime_Begin != undefined
					&& planStartTime_End != null && planStartTime_End != '' && planStartTime_End != undefined) {
				planStartTime = planStartTime_Begin + "," +planStartTime_End;
			}
			if (planEndTime_Begin != null && planEndTime_Begin != '' && planEndTime_Begin != undefined
					&& planEndTime_End != null && planEndTime_End != '' && planEndTime_End != undefined) {
				planEndTime = planEndTime_Begin + "," +planEndTime_End;
			}
			
			var bizCurrent = '';
			var risk = '';
			var planLevel = '';
			var workTime_condition = '';
			var progressRate_condition = '';
			$('#seachPerposeTag').find('*').each(function() {
				if ($(this).attr('name') == 'Plan.planLevelInfo.id') {
					if (planLevel == '') {
						planLevel = $(this).val();
					} else {
						planLevel = planLevel + "," + $(this).val();
					}
				}
				if ($(this).attr('name') == 'Plan.risk') {
					if (risk == '') {
						risk = $(this).val();
					} else {
						risk = risk + "," + $(this).val();
					}
				}
				if ($(this).attr('name') == 'Plan.bizCurrent') {
					if (bizCurrent == '') {
						bizCurrent = $(this).val();
					} else {
						bizCurrent = bizCurrent + "," + $(this).val();
					}
				}
				if ($(this).attr('name') == 'Plan.workTime_condition') {
					workTime_condition = $(this).val();
				}

				if ($(this).attr('name') == 'Plan.progressRate_condition') {
					progressRate_condition = $(this).val();
				}
			});
			
			var url = 'planController.do?searchDatagrid&planId=${planId}&inputsName='+$("#inputsName").val()+'&projectId=${projectId}'+"&planName="+planName+"&bizCurrent="+bizCurrent+"&planLevel="+planLevel+"&userName="+userName+"&planStartTime="+planStartTime+"&planEndTime="+planEndTime+"&workTime="+workTime+"&workTime_condition="+workTime_condition;
			url = encodeURI(encodeURI(url))
			$('#planlist') .datagrid( {
				url : url,
				pageNumber : 1
			});

		/* 	$.ajax({
				url : 'planController.do?searchDatagridForProblem&projectId=${projectId}',
				type : 'post',
				data : {
					planName : planName,
					bizCurrent : bizCurrent
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					planPreposeList.clearAll();
					planPreposeList.parse(d.obj, 'js');
				}
			}); */
		}
		
		
		//重置
		function tagsearchPerposeReset() {
			$('#searchPlanName').textbox("clear");
			$('#searchWorkTime').textbox("clear");
			$("#planDateRange_BeginDate").datebox("clear");
			$("#planDateRange_EndDate").datebox("clear");
			$("#planEndDateRange_BeginDate").datebox("clear");
			$("#planEndDateRange_EndDate").datebox("clear");
			$('#searchPlanLevel').combobox("clear");
			$('#searchBizCurrent').combobox("clear");
			$('#searchOwner').textbox("clear");
		}
		
	</script>
</body>
</html>
