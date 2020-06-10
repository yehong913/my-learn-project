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
<div class="easyui-layout" fit="true">
		<input id="statusList" name="statusList" type="hidden" value="${statusList}">
		<input id="planId" name="planId" type="hidden" value="${planId}">
		<input id="preposeIds" name="preposeIds" type="hidden" value="${preposeIds}">
			
		 <fd:searchform id="seachPerposeTag"  onClickSearchBtn="searchPerpose()" onClickResetBtn="tagsearchPerposeReset()">
			<fd:inputText title="{com.glaway.ids.pm.project.plan.planNo}" name="Plan.planNumber" id="searchPlanNumber" queryMode="like"/>
			<fd:inputText title="{com.glaway.ids.pm.project.plan.planName}" name="Plan.planName" id="searchPlanName" queryMode="like"/>
			<fd:combobox id="searchPlanLevel" textField="name" title="{com.glaway.ids.pm.project.plan.planLevel}" name="Plan.planLevelInfo.id" editable="false" valueField="id" prompt="全部"
				url="planController.do?useablePlanLevelList" queryMode="in" multiple="true"></fd:combobox>
			<fd:combobox id="searchBizCurrent" textField="title" title="{com.glaway.ids.common.lable.status}" name="Plan.bizCurrent"  editable="false" valueField="name" prompt="全部"
				url="planController.do?statusList" queryMode="in" multiple="true"></fd:combobox>
			<fd:inputText title="{com.glaway.ids.common.lable.owner}"  id="searchOwner"/>
			<fd:inputDateRange id="planDateRange" interval="0" title="开始时间" name="Plan.planStartTime" opened="0"></fd:inputDateRange>
			<fd:inputDateRange id="planEndDateRange" interval="0" title="结束时间" name="Plan.planEndTime" opened="0"></fd:inputDateRange>
			 <fd:inputNumber title="{com.glaway.ids.pm.project.plan.workTime}" name="Plan.workTime" id="searchWorkTime" queryMode="le&&ge" />
		</fd:searchform>

			<%--<fd:lazyLoadingTreeGrid url="planController.do?planPreposeList&id=${planId}&projectId=${projectId}" id="planPreposeList"
				width="100%" height="100%" imgUrl="plug-in/icons_greenfolders/"
				initWidths="52,*,70,80,100,90,90,70"
				columnIds="planNumber,displayNameNode,planLevelInfo,bizCurrentInfo,ownerInfo,planStartTime,planEndTime,workTime"
				header=" 编号,计划名称,计划等级,状态,负责人,开始时间,结束时间,工期(天)"
				columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
				colAlign="left,left,left,left,left,left,left,left"
				colSortings="na,na,na,na,na,na,na,na"
				colTypes="ro,tree,ro,ro,ro,ro,ro,ro"
				enableTreeGridLines="true" enableLoadingStatus="true"
				enableMultiselect="true" onLoadSuccess="selectedExistPrepose">
			</fd:lazyLoadingTreeGrid>--%>

			<fd:lazytreegrid id="planPreposeList" idField="id" treeField="displayNameNode" singleSelect="false" style="width:100%;height:99%;" imgUrl="plug-in/dhtmlxSuite/imgs/"
							 url="planController.do?planPreposeList&id=${planId}&projectId=${projectId}" >
				<fd:columns>
					<fd:column field="planNumber" title="编号" />
					<fd:column field="displayNameNode" title="计划名称" />
					<fd:column field="planLevelInfo" title="计划等级" />
					<fd:column field="bizCurrentInfo" title="状态" />
					<fd:column field="ownerInfo" title="负责人" />
					<fd:column field="planStartTime" title="开始时间" />
					<fd:column field="planEndTime" title="结束时间" />
					<fd:column field="workTime" title="工期(天)" />
				</fd:columns>
                <fd:eventListener event="onLoadSuccess" listener="selectedExistPrepose"></fd:eventListener>
			</fd:lazytreegrid>

</div>
	<script src="webpage/com/glaway/ids/pm/project/plan/planList.js"></script>
	<script type="text/javascript">
		setTimeout(function(){
			$("#mygrid_planPreposeList_gridbox").children(1).eq(1).eq(0).css("overflow-x","hidden");
		}, 300);
		
		// 选择计划前置树加载完毕之后，将已选的前置设置为选中状态
		function selectedExistPrepose() {
		    debugger;
			var preposeIds = $('#preposeIds').val();
			if(preposeIds != null && preposeIds != '' && preposeIds != undefined){
				var ids = preposeIds.split(",");
				for(var i = 0; i < ids.length; i++){
					mygrid_planPreposeList.selectRowById(ids[i],true,true,true);
					mygrid_planPreposeList.openItem(ids[i]);
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

			$.ajax({
				url : 'planController.do?searchDatagridForPrepose&projectId=${projectId}&planId=${planId}',
				type : 'post',
				data : {
					planNumber : planNumber,
					planName : planName,
					planLevel : planLevel,
					bizCurrent : bizCurrent,
					userName : userName,
					planStartTime : planStartTime,
					planEndTime : planEndTime,
					workTime : workTime,
					workTime_condition : workTime_condition,
					progressRate : progressRate,
					progressRate_condition : progressRate_condition,
					risk : risk
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					mygrid_planPreposeList.clearAll();
					mygrid_planPreposeList.parse(d.obj, 'js');
				}
			});
		}
		
		
		//重置
		function tagsearchPerposeReset() {
			$('#searchPlanNumber').textbox("clear");
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
