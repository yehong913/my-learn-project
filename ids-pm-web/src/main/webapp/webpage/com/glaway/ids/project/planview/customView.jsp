<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>自定义视图</title>
<t:base type="jquery,easyui,tools"></t:base>
<script src="webpage/com/glaway/ids/pm/project/plan/planList.js"></script>
<style>
.planChoose {
	color: grey
}
.easyui-fluid{margin-bottom:10px;}
</style>
</head>

<script type="text/javascript">

$(function(){
	
	setTimeout(function(){
		$("#mySelectGrid_gridbox").css('height','316px');
		$("#mySelectGrid_gridbox .objbox").css('height','280px');
		$("#SelectedPlanGrid_gridbox").css('height','316px');
		$("#SelectedPlanGrid_gridbox .objbox").css('height','280px');
	},500);
	
});

function setPlanStyle(){
	debugger;
	var allSelectedRowsId = new Array();
	allSelectedRowsId = mySelectGrid.getSelectedRowId();
	
	for(var i = 0;i<allSelectedRowsId.split(',').length;i++){
		var selectedRows = mySelectGrid.getRowById(allSelectedRowsId.split(',')[i]);
		$(selectedRows).find('td').each(function(e){
			$(this).addClass('planChoose');
		});
	}
	
	/* $('#mySelectGrid_gridbox').find('table tr').each(function(i){
		if($(this).hasClass('rowselected')) {
			$(this).addClass('planChoose');
		}
	}); */
}
function testabcd() {
	
	$('#mySelectGrid_gridbox').find('table tr').each(function(i){
		if($(this).hasClass('rowselected')) {
			$(this).addClass('planChoose');
		}
	});
}
function mySelectSearchPlan() {
	debugger;
	//可以展开的 节点添加 count_类属性
	addCountClass();
	// 统计已经展开节点的索引
	var expands = new Array();
	var ix = 0;
	var inde = 0;
	var reg2 = RegExp(/minus/);
	$(".count_").each(function() {
		inde = inde + 1;
		if ($(this).attr("src").match(reg2)) {
			expands[ix++] = inde - 1;
		}
	});

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
	var planStartTime_Begin = $('#planDateRange_BeginDate').datebox(
			'getValue');
	var planStartTime_End = $('#planDateRange_EndDate').datebox('getValue')
	var planEndTime_Begin = $('#planEndDateRange_BeginDate').datebox(
			'getValue');
	var planEndTime_End = $('#planEndDateRange_EndDate')
			.datebox('getValue')

	if (planStartTime_Begin != null && planStartTime_Begin != ''
			&& planStartTime_Begin != undefined
			&& planStartTime_End != null && planStartTime_End != ''
			&& planStartTime_End != undefined) {
		planStartTime = planStartTime_Begin + "," + planStartTime_End;
	}
	if (planEndTime_Begin != null && planEndTime_Begin != ''
			&& planEndTime_Begin != undefined && planEndTime_End != null
			&& planEndTime_End != '' && planEndTime_End != undefined) {
		planEndTime = planEndTime_Begin + "," + planEndTime_End;
	}

	var bizCurrent = '';
	var planLevel = '';
	var workTime_condition = '';
	var progressRate_condition = '';
	var taskNameType = '';
	var taskType = '';
	var isDelay = '';
	$('#mySelectSearchPlanTag').find('*').each(function() {
		if ($(this).attr('name') == 'Plan.planLevelInfo.id') {
			if (planLevel == '') {
				planLevel = $(this).val();
			} else {
				planLevel = planLevel + "," + $(this).val();
			}
		}
		if ($(this).attr('name') == 'Plan.isDelay') {
			if (isDelay == '') {
				isDelay = $(this).val();
			} else {
				isDelay = isDelay + "," + $(this).val();
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

		if ($(this).attr('name') == 'Plan.taskNameType') {
			if (taskNameType == '') {
				taskNameType = $(this).val();
			} else {
				taskNameType = taskNameType + "," + $(this).val();
			}
		}

		if ($(this).attr('name') == 'Plan.taskType') {
			if (taskType == '') {
				taskType = $(this).val();
			} else {
				taskType = taskType + "," + $(this).val();
			}
		}
	});

	$.ajax({
		url : 'planController.do?searchDatagridForSelectedPlanList&type=mySelect&tempViewId=${tempViewId}&projectId=${projectId}&isModify=${isModify}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}',
		type : 'post',
		data : {
			planNumber : planNumber,
			planName : planName,
			isDelay : isDelay,
			planLevel : planLevel,
			bizCurrent : bizCurrent,
			userName : userName,
			planStartTime : planStartTime,
			planEndTime : planEndTime,
			workTime : workTime,
			workTime_condition : workTime_condition,
			progressRate : progressRate,
			progressRate_condition : progressRate_condition,
			taskNameType : taskNameType,
			taskType : taskType
		},
		cache : false,
		success : function(data) {
		    debugger;
			var d = $.parseJSON(data);
			mySelectGrid.clearAll();
			mySelectGrid.parse(d.obj, 'js');
			doExpandSelect(expands);
		}
	});
}


function doExpandSelect(expands) {
	addCountClass();
	$(".count_")
			.each(
					function(index) {
						for ( var i in expands) {
							if (index == expands[i]) {
								this.parentNode.parentNode.parentNode.parentNode.parentNode.grid
										.doExpand(this);
							}
						}

					});
}



function selectedSearchPlan() {

	var userName = $('#searchOwner1').val();
	var progressRate = $('#searchProgressRate1').val();
	if (progressRate < 0) {
		progressRate = -1;
	}
	var workTime = $('#searchWorkTime1').val();
	if (workTime < 0) {
		workTime = -1;
	}
	var planNumber = $('#searchPlanNumber1').val();
	var planName = $('#searchPlanName1').val();

	var planStartTime = '';
	var planEndTime = '';
	var planStartTime_Begin = $('#planDateRange1_BeginDate').datebox(
			'getValue');
	var planStartTime_End = $('#planDateRange1_EndDate').datebox('getValue')
	var planEndTime_Begin = $('#planEndDateRange1_BeginDate').datebox(
			'getValue');
	var planEndTime_End = $('#planEndDateRange1_EndDate')
			.datebox('getValue')

	if (planStartTime_Begin != null && planStartTime_Begin != ''
			&& planStartTime_Begin != undefined
			&& planStartTime_End != null && planStartTime_End != ''
			&& planStartTime_End != undefined) {
		planStartTime = planStartTime_Begin + "," + planStartTime_End;
	}
	if (planEndTime_Begin != null && planEndTime_Begin != ''
			&& planEndTime_Begin != undefined && planEndTime_End != null
			&& planEndTime_End != '' && planEndTime_End != undefined) {
		planEndTime = planEndTime_Begin + "," + planEndTime_End;
	}

	var bizCurrent = '';
	var planLevel = '';
	var workTime_condition = '';
	var progressRate_condition = '';
	var taskNameType = '';
	var taskType = '';
	var isDelay = '';
	$('#selectedSearchPlanTag').find('*').each(function() {
		debugger
		if ($(this).attr('name') == 'Plan.planLevelInfo.id') {
			if (planLevel == '') {
				planLevel = $(this).val();
			} else {
				planLevel = planLevel + "," + $(this).val();
			}
		}
		if ($(this).attr('name') == 'Plan.isDelay') {
			if (isDelay == '') {
				isDelay = $(this).val();
			} else {
				isDelay = isDelay + "," + $(this).val();
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

		if ($(this).attr('name') == 'Plan.taskNameType') {
			if (taskNameType == '') {
				taskNameType = $(this).val();
			} else {
				taskNameType = taskNameType + "," + $(this).val();
			}
		}

		if ($(this).attr('name') == 'Plan.taskType') {
			if (taskType == '') {
				taskType = $(this).val();
			} else {
				taskType = taskType + "," + $(this).val();
			}
		}
	});

	$.ajax({
		url : 'planController.do?searchDatagridForSelectedPlanList&type=selectedPlan&tempViewId=${tempViewId}&projectId=${projectId}&isModify=${isModify}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}',
		type : 'post',
		data : {
			planNumber : planNumber,
			planName : planName,
			isDelay : isDelay,
			planLevel : planLevel,
			bizCurrent : bizCurrent,
			userName : userName,
			planStartTime : planStartTime,
			planEndTime : planEndTime,
			workTime : workTime,
			workTime_condition : workTime_condition,
			progressRate : progressRate,
			progressRate_condition : progressRate_condition,
			taskNameType : taskNameType,
			taskType : taskType
		},
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			SelectedPlanGrid.clearAll();
			SelectedPlanGrid.parse(d.obj, 'js');
		}
	});
}

//重置
function tagSearchReset() {
	$('#searchPlanNumber').textbox("clear");
	$('#searchPlanName').textbox("clear");
	$('#searchWorkTime').textbox("clear");
	$('#searchProgressRate').textbox("clear");
	$("#planDateRange_BeginDate").datebox("clear");
	$("#planDateRange_EndDate").datebox("clear");
	$("#planEndDateRange_BeginDate").datebox("clear");
	$("#planEndDateRange_EndDate").datebox("clear");
	$('#searchPlanLevel').combobox("clear");
	$('#searchBizCurrent').combobox("clear");
	$('#searchIsDelay').combobox("clear");
	$('#searchOwner').textbox("clear");

	$('#searchTaskNameType').combobox("clear");
	$('#searchTaskType').combobox("clear");
}

//重置
function tagSearchReset1() {
	$('#searchPlanNumber1').textbox("clear");
	$('#searchPlanName1').textbox("clear");
	$('#searchWorkTime1').textbox("clear");
	$('#searchProgressRate1').textbox("clear");
	$("#planDateRange1_BeginDate").datebox("clear");
	$("#planDateRange1_EndDate").datebox("clear");
	$("#planEndDateRange1_BeginDate").datebox("clear");
	$("#planEndDateRange1_EndDate").datebox("clear");
	$('#searchPlanLevel1').combobox("clear");
	$('#searchBizCurrent1').combobox("clear");
	$('#searchIsDelay1').combobox("clear");
	$('#searchOwner1').textbox("clear");

	$('#searchTaskNameType1').combobox("clear");
	$('#searchTaskType1').combobox("clear");
}


function addMySelectPlan(){
	debugger;
	var rowId = mySelectGrid.getSelectedRowId();
	var tempViewId = '${tempViewId}';
	if(rowId == '' || rowId == null){
		top.tip("请至少选择一条数据");
		return false;
	}else{
		$.ajax({
			type : 'POST',
			data :{
				planId : rowId,
				tempViewId : tempViewId
			},
			url : 'planViewController.do?addMySelectPlan',
			cache : false,
			success : function(data){
				debugger;
				var d = $.parseJSON(data);
				if(d.success){
					$.ajax({
						type : 'POST',
						data : {
							tempViewId : tempViewId,
							projectId : '${projectId}',
							isModify : '${isModify}',
							planModifyOperationCode : '${planModifyOperationCode}',
							planAssignOperationCode : '${planAssignOperationCode}',
							planDeleteOperationCode : '${planDeleteOperationCode}',
							planChangeOperationCode : '${planChangeOperationCode}',
							planDiscardOperationCode : '${planDiscardOperationCode}',
							planUnconcernOperationCode : '${planUnconcernOperationCode}',
							planConcernOperationCode : '${planConcernOperationCode}',
							isPmo : '${isPmo}',
							isProjectManger : '${isProjectManger}'
						},
						url : 'planController.do?getSelectedPlanList',
						cache : false,
						success : function(data){
							var d1 = $.parseJSON(data);
							SelectedPlanGrid.clearAll();
							SelectedPlanGrid.parse(d1.obj, 'js');
						}
					});
					setPlanStyle();
				}
			}
		});
	}
	
}

function removePlan(){
	debugger;
	var rowId = SelectedPlanGrid.getSelectedRowId();
	var tempViewId = '${tempViewId}';
	if(rowId == '' || rowId == null){
		top.tip("请至少选择一条数据");
		return false;
	}else{
		$.ajax({
			type : 'POST',
			data :{
				planId : rowId,
				tempViewId : tempViewId
			},
			url : 'planViewController.do?removeSelectedPlan',
			cache : false,
			success : function(data){
				var d = $.parseJSON(data);
				if(d.success){
					/* SelectedPlanGrid.deleteRow(rowId);
					mySelectSearchPlan(); */
					$.ajax({
						type : 'POST',
						data : {
							tempViewId : tempViewId,
							projectId : '${projectId}',
							isModify : '${isModify}',
							planModifyOperationCode : '${planModifyOperationCode}',
							planAssignOperationCode : '${planAssignOperationCode}',
							planDeleteOperationCode : '${planDeleteOperationCode}',
							planChangeOperationCode : '${planChangeOperationCode}',
							planDiscardOperationCode : '${planDiscardOperationCode}',
							planUnconcernOperationCode : '${planUnconcernOperationCode}',
							planConcernOperationCode : '${planConcernOperationCode}',
							isPmo : '${isPmo}',
							isProjectManger : '${isProjectManger}'
						},
						url : 'planController.do?getSelectedPlanList',
						cache : false,
						success : function(data){
							var d1 = $.parseJSON(data);
							SelectedPlanGrid.clearAll();
							SelectedPlanGrid.parse(d1.obj, 'js');
							mySelectSearchPlan();
						}
					}); 
				}
			}
		});
	}
	
}


function saveCustomView() {
	 var name = $("#viewName").val();
    var isSwitch = $('#isSwitch').selectBooleanCheckbox('getValue');
    if (name == null || name == undefined || name == '') {
        top.tip('<spring:message code="com.glaway.ids.pm.project.planview.nameCannotEmpty"/>');
        return;
    }
    
    $.ajax({
    	type:'POST',
    	url:'planViewController.do?checkViewNameBeforeSave',
    	data:{
    		name : name
    	},
    	cache:false,
    	success:function(data){
    		var d = $.parseJSON(data);
    		if(d.success){
    			 $.ajax({
    			        url : 'planViewController.do?doSaveCustomView',
    			        type : 'post',
    			        data : {
    			       		name : name,
    			       		tempViewId : '${tempViewId}',
    			       		projectId : '${projectId}'
    			        },
    			        cache : false,
    			        success : function(data) {
    			       	 debugger;
    			            var d = $.parseJSON(data);
    			            var win = $.fn.lhgdialog("getSelectParentWin");
    			            top.tip(d.msg);
    			            win.$('#setPlanView').combobox('setValue', '');
    			            if (d.success) {
    			           	 //刷新视图            
    			           		win.switchPlanTreeReload(isSwitch,d.obj,name);
    			           	
//    			                 if(isSwitch) {
//    			                	 win.$('#switchPlanView').comboztree('setValue', d.obj);
//    			                	 win.$('#switchPlanView').comboztree('setText', name);
//    			                	 //切换视图
//    			                	// win.switchPlanList();
//    			                 }
							setTimeout(function(){
    			                $.fn.lhgdialog("closeSelect");
							 },500);
    			            }
    			        }
    			    });
    		}else{
    			top.tip(d.msg);
    			return false;
    		}
    	}
    });
    
    
}

// $(function(){
// 	var mySelectGridFlag = window.setInterval(function(){
// 		if(mySelectGrid==null) {
// 		}
// 		else {
// 			mySelectGrid.attachEvent("onOpenStart", function(id, m) {
// 				debugger;
// 				setTimeout(function(){
// 					mySelectSearchPlan();
// 				},5000)
				
// // alert(1);
// // 				setPlanStyle();
// // 				alert(2);

// 				debugger;
// 			    return m == 1 ? "closed": "opened"; 
// 			});
// 			window.clearInterval(mySelectGridFlag);
// 		}
// 	},500);
// });


function addCountClass() {
	var reg = RegExp(/plus|minus/);
	$(".objbox img").each(function() {
		if ($(this).attr("src").match(reg)) {
			$(this).addClass("count_");
		}
	});

}

</script>


<body >
<div class="easyui-layout" fit="true">
    <input type="hidden" id ='projectIds' name='projectIds'/>
    <input type="hidden" id ='userIds' name='userIds'/>
    <input type="hidden" id ='planViewInfoId' name='planViewInfoId'/>
    <input type="hidden" id ='planViewName' name='planViewName'/>
    <div region="north" style="padding: 1px; width: 100%;height:15%;" title="" id="north_page_panel">
    	<fd:inputText id="viewName" name="viewName" title="{com.glaway.ids.pm.project.planview.name}" required="true" />
    	<fd:selectBooleanCheckbox id="isSwitch" title="{com.glaway.ids.pm.project.planview.switch}" value="true">
        </fd:selectBooleanCheckbox>  
        <fd:toolbar help="helpDoc:CustomView"></fd:toolbar>
    </div>
    <div region="center">
       <div class="easyui-layout" data-options="fit:true">   
            <div data-options="region:'west',title:'选择计划',collapsed:false" style="width:48%">
            	<fd:form id="mySelectPlanListTreegridForm">
					<fd:searchform id="mySelectSearchPlanTag" onClickSearchBtn="mySelectSearchPlan()" onClickResetBtn="tagSearchReset()" isMoreShow="true">
						<fd:inputText title="{com.glaway.ids.pm.project.plan.planName}" name="Plan.planName" id="searchPlanName" value="${planName}"
								queryMode="like" />
						<fd:combobox id="searchIsDelay" textField="name" title="是否延期"
								name="Plan.isDelay" editable="false" valueField="id" value="${isDelay}"
								url="planController.do?planIsDelayList" queryMode="in"
								multiple="true" prompt="全部" />
						<fd:combobox id="searchPlanLevel" textField="name" title="{com.glaway.ids.pm.project.plan.planLevel}"
								name="Plan.planLevelInfo.id" editable="false" valueField="id" value="${planLevel}"
								url="planController.do?useablePlanLevelList" queryMode="in"
								multiple="true" prompt="全部" />
						<fd:combobox id="searchBizCurrent" textField="title" title="{com.glaway.ids.common.lable.status}"
								name="Plan.bizCurrent" editable="false" valueField="name" value="${bizCurrent}"
								prompt="全部" url="planController.do?statusList" queryMode="in"
								multiple="true" />
						<fd:inputText title="编号" name="Plan.planNumber" value="${planNumber}"
								id="searchPlanNumber" queryMode="like" />
						<fd:inputText title="{com.glaway.ids.common.lable.owner}" id="searchOwner" value="${owner}" />
						<fd:inputDateRange id="planDateRange" interval="0" title="{com.glaway.ids.common.lable.starttime}" 
								name="Plan.planStartTime" opened="0" />
						<fd:inputDateRange id="planEndDateRange" interval="0" title="{com.glaway.ids.common.lable.endtime}"
								name="Plan.planEndTime" opened="0" />
						<fd:inputNumber title="{com.glaway.ids.pm.project.plan.workTime}" name="Plan.workTime" value="${workTime}"
								id="searchWorkTime" queryMode="le&&ge" />
						<fd:inputNumber title="{com.glaway.ids.common.lable.progress}" name="Plan.progressRate" value="${progressRate}"
								id="searchProgressRate" queryMode="le&&ge" />
						<fd:combobox id="searchTaskNameType" textField="typename"
								title="{com.glaway.ids.pm.project.plan.taskNameType}" name="Plan.taskNameType" editable="false" value="${taskNameType}"
								valueField="typecode" url="taskFlowResolveController.do?getTaskNameTypes"
								queryMode="in" multiple="true" prompt="全部" />
						<fd:combobox id="searchTaskType" textField="name" title="{com.glaway.ids.pm.project.plan.taskType}" value="${taskType}"
								name="Plan.taskType" editable="false" valueField="name"
								url="planController.do?taskTypeList" queryMode="in" multiple="true"
								prompt="全部" />
					</fd:searchform>
					<fd:toolbar id="toolbar">
						<div style=" height:34px; line-height:34px;">
							<fd:toolbarGroup align="left">
								
								<fd:linkbutton id="addMySelectPlanBtn" onclick="addMySelectPlan()" value="添加"
									iconCls="basis ui-icon-plus" />
								<%-- <fd:linkbutton id="addMySelectPlanBtn12" onclick="testabc()" value="添加"
								iconCls="basis ui-icon-plus" /> --%>
							</fd:toolbarGroup>
							
						</div>
					</fd:toolbar>
		
		
					<fd:lazyLoadingTreeGrid id="mySelectGrid"
							url="planController.do?listForCustomView&flag=customView&projectId=${projectId}&isModify=${isModify}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}"
							width="100%" height="320px;" imgUrl="plug-in/dhtmlxSuite/imgs/"
							initWidths="58,230,100,100,80,80,120,80,80,120,80,80,120,80,120,80" enableMultiselect="true"
							columnIds="planNumber,planName,planLevelInfo,taskNameTypeDisplay,taskType,bizCurrentInfo,ownerInfo,planStartTime,planEndTime,assignerInfo,assignTime,workTime,preposePlans,milestone,creator,createTime"
							header="编号,计划名称,计划等级,计划类型,计划类别,状态,负责人,开始时间,结束时间,发布人,发布时间,工期(天),前置计划,里程碑,创建者,创建时间"
							columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
							colAlign="left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left"
							colSortings="na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na"
							colTypes="ro,tree,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
							enableTreeGridLines="true" enableLoadingStatus="false" 
							onRightClick="onRightClick" onLoadSuccess="treeLoadSuccess">
					</fd:lazyLoadingTreeGrid>
		
				</fd:form>
            </div>   
            <div data-options="region:'center',title:'已选计划'" style="width:48%">
            	<%-- <iframe id = "selectPlanFrame" src="planViewController.do?goSelectedPlanPage" style="width:100%;height:100%" ></iframe> --%>
            	<fd:form id="selectedPlanListTreegridForm">
					<fd:searchform id="selectedSearchPlanTag" onClickSearchBtn="selectedSearchPlan()" onClickResetBtn="tagSearchReset1()" isMoreShow="true">
						<fd:inputText title="{com.glaway.ids.pm.project.plan.planName}" name="Plan.planName" id="searchPlanName1" value="${planName}"
								queryMode="like" />
						<fd:combobox id="searchIsDelay1" textField="name" title="是否延期"
								name="Plan.isDelay" editable="false" valueField="id" value="${isDelay}"
								url="planController.do?planIsDelayList" queryMode="in"
								multiple="true" prompt="全部" />
						<fd:combobox id="searchPlanLevel1" textField="name" title="{com.glaway.ids.pm.project.plan.planLevel}"
								name="Plan.planLevelInfo.id" editable="false" valueField="id" value="${planLevel}"
								url="planController.do?useablePlanLevelList" queryMode="in"
								multiple="true" prompt="全部" />
						<fd:combobox id="searchBizCurrent1" textField="title" title="{com.glaway.ids.common.lable.status}"
								name="Plan.bizCurrent" editable="false" valueField="name" value="${bizCurrent}"
								prompt="全部" url="planController.do?statusList" queryMode="in"
								multiple="true" />
						<fd:inputText title="编号" name="Plan.planNumber" value="${planNumber}"
								id="searchPlanNumber1" queryMode="like" />
						<fd:inputText title="{com.glaway.ids.common.lable.owner}" id="searchOwner1" value="${owner}" />
						<fd:inputDateRange id="planDateRange1" interval="0" title="{com.glaway.ids.common.lable.starttime}" 
								name="Plan.planStartTime" opened="0" />
						<fd:inputDateRange id="planEndDateRange1" interval="0" title="{com.glaway.ids.common.lable.endtime}"
								name="Plan.planEndTime1" opened="0" />
						<fd:inputNumber title="{com.glaway.ids.pm.project.plan.workTime}" name="Plan.workTime" value="${workTime}"
								id="searchWorkTime1" queryMode="le&&ge" />
						<fd:inputNumber title="{com.glaway.ids.common.lable.progress}" name="Plan.progressRate" value="${progressRate}"
								id="searchProgressRate1" queryMode="le&&ge" />
						<fd:combobox id="searchTaskNameType1" textField="typename"
								title="{com.glaway.ids.pm.project.plan.taskNameType}" name="Plan.taskNameType" editable="false" value="${taskNameType}"
								valueField="typecode" url="taskFlowResolveController.do?getTaskNameTypes"
								queryMode="in" multiple="true" prompt="全部" />
						<fd:combobox id="searchTaskType1" textField="name" title="{com.glaway.ids.pm.project.plan.taskType}" value="${taskType}"
								name="Plan.taskType" editable="false" valueField="name"
								url="planController.do?taskTypeList" queryMode="in" multiple="true"
								prompt="全部" />
					</fd:searchform>
					<fd:toolbar id="toolbar1">
						<div style=" height:34px; line-height:34px;">
							<fd:toolbarGroup align="left">
								
								<fd:linkbutton id="removePlanBtn" onclick="removePlan()" value="移除"
									iconCls="basis ui-icon-minus" />
							</fd:toolbarGroup>
							
						</div>
					</fd:toolbar>
		
		
					<fd:lazyLoadingTreeGrid id="SelectedPlanGrid"
							url="planController.do?getSelectedPlanListAll&tempViewId=${tempViewId}&projectId=${projectId}&isModify=${isModify}&planModifyOperationCode=${planModifyOperationCode}&planAssignOperationCode=${planAssignOperationCode}&planDeleteOperationCode=${planDeleteOperationCode}&planChangeOperationCode=${planChangeOperationCode}&planDiscardOperationCode=${planDiscardOperationCode}&isPmo=${isPmo}&isProjectManger=${isProjectManger}&planConcernOperationCode=${planConcernOperationCode}&planUnconcernOperationCode=${planUnconcernOperationCode}"
							width="100%" height="320px;" imgUrl="plug-in/dhtmlxSuite/imgs/"
							initWidths="58,230,100,100,80,80,120,80,80,120,80,80,120,80,120,80" enableMultiselect="true"
							columnIds="planNumber,planName,planLevelInfo,taskNameTypeDisplay,taskType,bizCurrentInfo,ownerInfo,planStartTime,planEndTime,assignerInfo,assignTime,workTime,preposePlans,milestone,creator,createTime"
							header="编号,计划名称,计划等级,计划类型,计划类别,状态,负责人,开始时间,结束时间,发布人,发布时间,工期(天),前置计划,里程碑,创建者,创建时间"
							columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
							colAlign="left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left"
							colSortings="na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na"
							colTypes="ro,tree,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
							enableTreeGridLines="true" enableLoadingStatus="false"
							onRightClick="onRightClick" onLoadSuccess="treeLoadSuccess">
					</fd:lazyLoadingTreeGrid>
		
				</fd:form>
            </div>   
        </div>   
    </div>
</div>

</body>

</html>
