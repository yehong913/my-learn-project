<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src = "webpage/com/glaway/ids/javascript/ajaxfileupload.js"></script>
</head>
<body>
<div class="easyui-layout" fit="true">
	<div>
		<input id="statusList" name="statusList" type="hidden" value="${statusList}">
		<input id="planId" name="planId" type="hidden" value="${planId}">
		<input id="preposeIds" name="preposeIds" type="hidden" value="${preposeIds}">
			
		<div id="planListtbTool">
			 <fd:searchform id="seachPerposeTag"  onClickSearchBtn="searchPerpose()" onClickResetBtn="tagsearchPerposeReset()">
				<%-- <fd:inputText title="{com.glaway.ids.pm.project.plan.planNo}" name="Plan.planNumber" id="searchPlanNumber" queryMode="like"/> --%>
				<fd:inputText title="{com.glaway.ids.pm.project.plan.planName}" name="Plan.planName" id="searchPlanName" queryMode="like"/>
				<fd:combobox id="searchPlanLevel" textField="name" title="{com.glaway.ids.pm.project.plan.planLevel}" name="Plan.planLevelInfo.id" editable="false" valueField="id" prompt="全部"
					url="planController.do?planLevelList" queryMode="in" multiple="true"></fd:combobox>	
				<%-- <fd:combobox id="searchBizCurrent" textField="title" title="{com.glaway.ids.common.lable.status}" name="Plan.bizCurrent"  editable="false" valueField="name" prompt="全部"
					url="planController.do?statusList" queryMode="in" multiple="true"></fd:combobox>		
				<fd:inputText title="{com.glaway.ids.common.lable.owner}"  id="searchOwner"/>
				<fd:inputDateRange id="planDateRange" interval="0" title="开始时间" name="Plan.planStartTime" opened="0"></fd:inputDateRange>
				<fd:inputDateRange id="planEndDateRange" interval="0" title="结束时间" name="Plan.planEndTime" opened="0"></fd:inputDateRange> --%>
				 <fd:inputNumber title="参考工期(天)" name="Plan.workTime" id="searchWorkTime" queryMode="le&&ge" />
			</fd:searchform>
		</div>
	
		<div style="width:780px;height:450px;">
			<fd:lazyLoadingTreeGrid url="planTemplateController.do?planPreposeList&id=${planId}&projectTemplateId=${projectTemplateId}&planTemplateId=${planTemplateId}&parentPlanId=${parentPlanId}" id="planPreposeList"
				width="100%" height="100%" imgUrl="plug-in/icons_greenfolders/"
				initWidths="20,540,100,100"
				columnIds="planNumber,planName,planLevelInfo,workTime"
				header=" ,计划名称,计划等级,参考工期(天)"
				columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
				colAlign="left,left,left,left"
				colSortings="na,na,na,na"
				colTypes="ro,tree,ro,ro"
				enableTreeGridLines="true" enableLoadingStatus="true"
				enableMultiselect="true" onLoadSuccess="selectedExistPrepose">
			</fd:lazyLoadingTreeGrid>
		</div>
	</div>
</div>	
	<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
	<script type="text/javascript">
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
			var workTime = $('#searchWorkTime').val();
			if (workTime < 0) {
				workTime = -1;
			}
			
			var planName = $('#searchPlanName').val();
		
			
			var planLevel = '';
			var workTime_condition = '';
		
			$('#seachPerposeTag').find('*').each(function() {
				if ($(this).attr('name') == 'Plan.planLevelInfo.id') {
					if (planLevel == '') {
						planLevel = $(this).val();
					} else {
						planLevel = planLevel + "," + $(this).val();
					}
				}
				
				if ($(this).attr('name') == 'Plan.workTime_condition') {
					workTime_condition = $(this).val();
				}

				
			});

			$.ajax({
				url : 'planTemplateController.do?searchDatagridForPrepose&planId=${planId}&projectTemplateId=${projectTemplateId}&planTemplateId=${planTemplateId}',
				type : 'post',
				data : {
					
					planName : planName,
					planLevel : planLevel,
					workTime : workTime,
					workTime_condition : workTime_condition
					
				},
				cache : false,
				success : function(data) {
					debugger;
					var d = $.parseJSON(data);
					planPreposeList.clearAll();
					planPreposeList.parse(d.obj, 'js');
				}
			});
		}
		
		
		//重置
		function tagsearchPerposeReset() {
			
			$('#searchPlanName').textbox("clear");
			$('#searchWorkTime').textbox("clear");
			$('#searchPlanLevel').combobox("clear");
			
		}
		
	</script>
</body>
</html>
