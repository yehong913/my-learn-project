<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui_iframe,tools"></t:base>
<head>
	<title>流程任务批量编辑</title>
	<script>
		$(document).ready(
			function() {
				$.ajax({
					url : 'taskFlowResolveController.do?flowTaskListForChange&parentPlanId='+$("#parentPlanId").val(),
					type : 'post',
					data : {
						parentPlanId : $("#parentPlanId").val()
					},
					cache : false,
					success : function(data) {
						if (data != null) {
							$("#batchModifyEditList").datagrid("loadData",data);
						}
						else{
							top.tip('<spring:message code="com.glaway.ids.pm.project.task.getFlowTaskFailure"/>');
						}
					}
				});
		});
		
		var hidFlag = true;
		var userList = $.parseJSON('${userList1}'); 
		var planLevelList = $.parseJSON('${planLevelList1}');
		var projectId2 = '${projectId}';
		$(function(){	
			$('#mm1').menu({
				onShow:function(){
					var targetMenuItem = $(this).find("#t");
					var targetMenuItem1 = $(this).find("#t1");
					var widtt = $(targetMenuItem).width() - $(targetMenuItem).find("div.menu-rightarrow").width();
					$(targetMenuItem).find("div.menu-text").css("width",widtt);					
					widtt = $(targetMenuItem1).width() - $(targetMenuItem1).find("div.menu-rightarrow").width();
					$(targetMenuItem1).find("div.menu-text").css("width",widtt);
					
					$(targetMenuItem).find("div.menu-text").each(function(){
						$(this).unbind();
						$(this).bind('mouseenter.menu', function(e){
							setTimeout(function(){
								$('#dl').datalist({
									textField : 'name',
									valueField : 'id',
									data :	planLevelList,
									lines: true,
									height:'230px',
									width:'150px',
									onSelect : function(index, row){
										var rows = $('#batchModifyEditList').datagrid('getSelections');	
										if(rows.length == 0){
											rows = $('#batchModifyEditList').datagrid('getRows');
										}
										for(var i = 0; i < rows.length; i++){
											if(rows[i].bizCurrent != 'FINISH'  || rows[i].bizCurrent != 'FEEDBACKING'){
												var index = $('#batchModifyEditList').datagrid('getRowIndex',rows[i]);
												$('#batchModifyEditList').datagrid('updateRow',{
													index: index,
													row: {
														planLevel: row.id
													}
												});
											}
										}
									}
								});	
								$('#tinput').textbox('setText','');		
								$('#tinput').textbox('textbox').keyup(function(e){
									var value = $('#tinput').textbox('getText');
									setTimeout(function(){
										doSearch(value);
									},500);
								});
								
							},10);
						});
						
					}); 
					
					$(targetMenuItem1).find("div.menu-text").each(function(){
						$(this).unbind();
						$(this).bind('mouseenter.menu', function(e){
							setTimeout(function(){
								$('#dl1').datalist({
									textField : 'realName',
									valueField : 'id',
									data :	userList,
									lines: true,
									height:'230px',
									width:'150px',
									onSelect : function(index, row){
										var rows = $('#batchModifyEditList').datagrid('getSelections');	
										if(rows.length == 0){
											rows = $('#batchModifyEditList').datagrid('getRows');
										}
										for(var i = 0; i < rows.length; i++){
											if(rows[i].bizCurrent != 'FINISH' || rows[i].bizCurrent != 'FEEDBACKING'){
												var index = $('#batchModifyEditList').datagrid('getRowIndex',rows[i]);
												$('#batchModifyEditList').datagrid('updateRow',{
													index: index,
													row: {
														owner: row.id
													}
												});
											}
										}
									}
								});	
								
								$('#tinput1').textbox('setText','');
								
								$('#tinput1').textbox('textbox').keyup(function(e){
									var value = $('#tinput1').textbox('getText');
									setTimeout(function(){
										doSearch1(value);
									},500);
								});
								
							},10);
						});
						
					});  
										
					$('#tinput').textbox('textbox').keyup(function(e){
						hidFlag = false;
						var value = $('#tinput').textbox('getText');
						setTimeout(function(){
							doSearch(value);
						},500);
					});
					
					
					$('#tinput1').textbox('textbox').keyup(function(e){
						hidFlag = false;
						var value = $('#tinput1').textbox('getText');
						setTimeout(function(){
							doSearch1(value);
						},500);
					});
					
				}
			});
		});
	
		// 批量修改行编辑
		
		var editIndex = undefined;
		var currentIndex = undefined;
		var changeLink = false;
		function endEditing(){
			if (editIndex == undefined){
				return true
				}
			if ($('#batchModifyEditList').datagrid('validateRow', editIndex)){
				$('#batchModifyEditList').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		
		
		// 批量修改行编辑
		function onClickRowBatch(index) {
			var rows = $('#batchModifyEditList').datagrid('getRows');
 			var row2 = rows[index];
 			if(row2.bizCurrent =='FEEDBACKING'||row2.bizCurrent =='FINISH'){
 				return false;
 			}
			
			
			if (currentIndex != undefined && !changeLink){
				workTimeOnChange();
			}
			
			currentIndex = index;
			changeLink = false;
			if (editIndex != index) {
				if (endEditing()) {
					var row = $('#batchModifyEditList').datagrid('getSelected');
/* 					if(row.fromTemplate == 'true'){
						$('#batchModifyEditList').datagrid('hideColumn', 'planName');
						$('#batchModifyEditList').datagrid('showColumn', 'parentPlanName');
					}else{
						$('#batchModifyEditList').datagrid('showColumn', 'planName');
						$('#batchModifyEditList').datagrid('hideColumn', 'parentPlanName');
					} */
					
					if((row.fromTemplate == 'false'||row.fromTemplate==undefined) && (row.bizCurrent == 'EDITING' || row.bizCurrent == undefined)){
						$('#batchModifyEditList').datagrid('showColumn', 'planName');
						$('#batchModifyEditList').datagrid('hideColumn', 'parentPlanName');	
					}else{
						$('#batchModifyEditList').datagrid('hideColumn', 'planName');
						$('#batchModifyEditList').datagrid('showColumn', 'parentPlanName');
					}
					$('#batchModifyEditList').datagrid('selectRow', index).datagrid(
							'beginEdit', index);
					editIndex = index;
				} else {
					$('#batchModifyEditList').datagrid('selectRow', editIndex);
				}
			}
		}
		
		function doSearch1(value){
			var newUserList = [];
			for(var i=0;i<userList.length;i++)
			{
				var user = userList[i];
				if(value == "" || user.realName.indexOf(value)>=0)
				{
					newUserList.push(user);
				}
			}
			$('#dl1').datalist("loadData",newUserList);
		}
		  

		function doSearch(value){
			debugger;
			var newPlanLevelList = [];
			for(var i=0;i<planLevelList.length;i++)
			{
				var planLevel = planLevelList[i];
				if(planLevel.name.indexOf(value)>=0)
				{
					newPlanLevelList.push(planLevel);
				}
			}
			$('#dl').datalist("loadData",newPlanLevelList);	
			var rowHeight = $("#ttt .datagrid-row ").length * $("#ttt .datagrid-row ").height();
			if(rowHeight <= 0){
				rowHeight = 20;
			}
			//$("#ttt ").height(rowHeight);
			//$("#ttt .datalist ").height(rowHeight);
			//$("#ttt ").next(".menu-shadow").height(rowHeight);
			
		}

		
		function saveChanges() {
			if (currentIndex != undefined && !changeLink){
				$('#batchModifyEditList').datagrid('endEdit', currentIndex);
				var rows = $('#batchModifyEditList').datagrid('getRows');
	 			var row = rows[currentIndex];
				var val = $.trim(rows[currentIndex].workTime);
				currentIndex = undefined;
				changeLink = true;
				editIndex = undefined;
				if(val == ''){
					top.tip('<spring:message code="com.glaway.ids.pm.rdtask.flowResolve.flowtasknameExistCheck"/>');
					return false;
				}
				if(val < 1){
					top.tip('<spring:message code="com.glaway.ids.pm.project.task.minWorkTime"/>');
					return false;
				}
				if(val != null && val != '' && val != undefined){
					var cellInfos = [];
					for (var i = 0; i < rows.length; i++) {
			    		cellInfos.push(rows[i].cellId + "," + $.trim(rows[i].planName) + "," + $.trim(rows[i].workTime));
					}
					$.ajax({
						url : 'taskFlowResolveController.do?changeFlowTaskListAndSaveForChange',
						type : 'post',
						data : {
							id : row.id,
							projectId : row.projectId,
							parentPlanId : $("#parentPlanId").val(),
							planName : $.trim(row.planName),
							fromTemplate : row.fromTemplate,
							planLevel : row.planLevel,
							owner : row.owner,
							planStartTime : row.planStartTime,
							workTime : val
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							var msg = d.msg.split('<br/>');
							if(d.success){
								var win = $.fn.lhgdialog("getSelectParentWin");
								win.reloadCellInfos(cellInfos);
								win.cleanSelectCells();
								$.fn.lhgdialog("closeSelect");
							}else{
							 	top.tip(msg);
							 	return false;
							}

						}
					});
				}
			}
			else{						
				var rows = $('#batchModifyEditList').datagrid('getRows'); 
				var ids = [];
				var owners = [];
				var planNames = [];
				var fromTemplates = [];
				var planLevels = [];
				var planStartTimes = [];
				var planEndTimes = [];
				var workTimes = [];
				currentIndex = undefined;
				changeLink = true;
				editIndex = undefined;
				if (rows.length > 0) {
					var cellInfos = [];
					for (var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
						if(rows[i].owner == null || rows[i].owner ==  ""){
							owners.push(" ");
						}
						else{
							owners.push(rows[i].owner);
						}
						
						if(rows[i].planName == null || rows[i].planName ==  ""){
							planNames.push(" ");
						}
						else{
							planNames.push($.trim(rows[i].planName));
						}
						
						if(rows[i].fromTemplate == null || rows[i].fromTemplate ==  ""){
							fromTemplates.push(" ");
						}
						else{
							fromTemplates.push(rows[i].fromTemplate);
						}
						
						if(rows[i].planLevel == null || rows[i].planLevel ==  ""){
				    		planLevels.push(" ");
						}
						else{
				    		planLevels.push(rows[i].planLevel);
						}
						var workTime = $.trim(rows[i].workTime);
						if(workTime == ''){
							top.tip('<spring:message code="com.glaway.ids.pm.rdtask.flowResolve.flowtasknameExistCheck"/>');
							return false;
						}
						else if(workTime < 1){
							top.tip('<spring:message code="com.glaway.ids.pm.project.task.minWorkTime"/>');
							return false;
						}
						else{
				    		workTimes.push($.trim(rows[i].workTime));
						}
						planStartTimes.push(rows[i].planStartTime);
			    		planEndTimes.push(rows[i].planEndTime);
			    		cellInfos.push(rows[i].cellId + "," + $.trim(rows[i].planName) + "," + $.trim(rows[i].workTime));
					}
					$.ajax({
						url : 'taskFlowResolveController.do?doBatchSaveBasicInfoForChange',
						type : 'post',
						data : {
							ids : ids.join(','),
							owners : owners.join(','),
							planNames : planNames.join(','),
							fromTemplates : fromTemplates.join(','),
							planLevels : planLevels.join(','),
							planStartTimes : planStartTimes.join(','),
							planEndTimes : planEndTimes.join(','),
							workTimes : workTimes.join(','),
							parentPlanId : $("#parentPlanId").val()
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							var msg = d.msg.split('<br/>');
							top.tip(msg[0]); // John
							if(d.success){
								var win = $.fn.lhgdialog("getSelectParentWin");
								win.reloadCellInfos(cellInfos);
								win.cleanSelectCells();
								$.fn.lhgdialog("closeSelect");
							}else{
								return false;
							}
						}
					}); 
				}
				else{
					var win = $.fn.lhgdialog("getSelectParentWin");
					win.cleanSelectCells();
					$.fn.lhgdialog("closeSelect");
				}
			}
		}
		
		function planLevelShow(val, row, index) {
			var planLevel = eval($("#planLevelList").val());
			for (var i = 0; i < planLevel.length; i++) {
				if (val == planLevel[i].id) {
					return planLevel[i].name;
				}
				if (val == planLevel[i].name) {
					return planLevel[i].name;
				}
			}
			return "";
		}
		
		function userNameShow(val, row, index) {
			var userList = eval($('#userList').val());
			for (var i = 0; i < userList.length; i++) {
				if (val == userList[i].id) {
					return userList[i].realName;
				}
				if (val == userList[i].realName) {
					return userList[i].realName;
				}
			}
		}
	
		function workTimeOnChange(){
			var rows = $('#batchModifyEditList').datagrid('getRows');
 			var row = rows[currentIndex];
 			if(row.bizCurrent =='FEEDBACKING'||row.bizCurrent =='FINISH'){
 				currentIndex = undefined;
 				changeLink = true;
 				editIndex = undefined;
 				return false;
 			}
 			$('#batchModifyEditList').datagrid('endEdit', currentIndex);
			var val = $.trim(rows[currentIndex].workTime);
			currentIndex = undefined;
			changeLink = true;
			editIndex = undefined;
			if(val == ''){
				top.tip('<spring:message code="com.glaway.ids.pm.rdtask.flowResolve.flowtasknameExistCheck"/>');
				return false;
			}
			if(val < 1){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.minWorkTime"/>');
				return false;
			}
			if(val != null && val != '' && val != undefined){
				$.ajax({
					url : 'taskFlowResolveController.do?changeFlowTaskListForChange',
					type : 'post',
					data : {
						id : row.id,
						projectId : row.projectId,
						parentPlanId : row.parentPlanId,
						planName : $.trim(row.planName),
						fromTemplate : row.fromTemplate,
						planLevel : row.planLevel,
						owner : row.owner,
						planStartTime : row.planStartTime,
						workTime : val,
						flowTaskParentId : '${parentPlanId}'
					},
					cache : false,
					success : function(data) {
						if (data != null) {
							var newRows = data.rows;
							if(newRows.length > 0){
								for(var i = 0; i < newRows.length; i++){
									$('#batchModifyEditList').datagrid('updateRow',{
										index: i,
										row: {
											id: newRows[i].id,
											parentPlanId: newRows[i].parentPlanId,
											cellId:  newRows[i].cellId,
											bizCurrent:  newRows[i].bizCurrent,
											parentPlanName : $.trim(newRows[i].planName),
											planName : newRows[i].planName,
											owner : newRows[i].owner,
											workTimeReference : newRows[i].workTimeReference,
											workTime : newRows[i].workTime,
											planStartTime: newRows[i].planStartTime,
											planEndTime: newRows[i].planEndTime,
											planLevel: newRows[i].planLevel
										}
									});
								}
							}
						}
						else{
							top.tip('<spring:message code="com.glaway.ids.pm.project.task.editDataFailure"/>');
						} 
					}
				});
			}
		}

		
		function closeThisDialog() {
			$.fn.lhgdialog("closeSelect");
		}
	</script>
	<style type="text/css">
		body{margin:0; padding:0}
		#ttt .datagrid-wrap{
			padding: 0;}
		#ttt{
			padding: 0px;}

		#ttt1 .datagrid-wrap{
			padding: 0;}
		#ttt1{
			padding: 0px;}
	</style>
</head>
<body>
	<input type="hidden" id ='parentPlanId' name='parentPlanId' value="${parentPlanId}"/>
	<input type="hidden" id ='isEnableFlag' name='isEnableFlag' value="${isEnableFlag}"/>
	<input type="hidden" id ='planLevelList' name='planLevelList' value="${planLevelList}"/>
	<input type="hidden" id ='userList' name='userList' value="${userList}"/>
	<fd:toolbar id="tb">
		<div fit="true" style="width: 100%;">
			<div class="easyui-panel" fit="true" style="width: 100%;">
				<a href="#" class="easyui-menubutton"
					data-options="menu:'#mm1',iconCls:'icon-edit'">批量更新</a>
			</div>
			<div id="mm1" style="width: 150px;">
				<div id='t'>
					<span><spring:message code="com.glaway.ids.pm.project.task.planLevel"/></span>
					<div id="ttt"
						style="width: 152px; height: 268px;">
						<input class="easyui-textbox" style="width: 145px; float: right;"
							iconCls="" id="tinput" data-options="iconCls:'icon-remove'">
						<div id="dl" style="width: 100%; display: none;"></div>
					</div>
				</div>
				<div id='t1'>
					<span><spring:message code="com.glaway.ids.common.lable.owner"/></span>
					<div id="ttt1"
						style="width: 152px; height: 268px;">
						<input class="easyui-textbox" style="width: 145px; float: right;"
							iconCls="" id="tinput1" data-options="iconCls:'icon-remove'">
						<div id="dl1" style="width: 100%; display: none;"></div>
					</div>
				</div>
			</div>
		</div>
	</fd:toolbar>
	<c:if test="${isStandards == 'ok'}">
		<fd:datagrid toolbar="#tb" idField="id" id="batchModifyEditList" pagination="false" checkbox="true" fit="true" fitColumns="true" onDblClickRow="onClickRowBatch" onClickFunName="workTimeOnChange" width="700px" >
			<fd:dgCol title="cellId" field="cellId" hidden="true"/>
			<fd:dgCol title="{com.glaway.ids.common.lable.status}" field="bizCurrent" hidden="true"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.task.taskname}" field="parentPlanName" width="200" hidden="true"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.task.taskname}" field="planName" width="200"  editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/procTemplateController.do?standardValue',
					            method:'get',
					            valueField:'name',
					            textField:'name',
					             panelMaxHeight:'150px',
							}}" sortable="false"/>
			<fd:dgCol title="{com.glaway.ids.common.lable.owner}" field="owner" width="100" formatterFunName="userNameShow"
					 editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/taskFlowResolveController.do?projectMembers&parentPlanId=${parentPlanId}',
					            method:'get',
					            valueField:'id',
					            textField:'realName',
					             panelMaxHeight:'150px'
							}}" sortable="false"/>
			
			<fd:dgCol title="{com.glaway.ids.pm.project.task.workTimeReference}" field="workTimeReference" width="80" sortable="false"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.task.workTime}" field="workTime" width="30" editor="{type:'numberbox'}" sortable="false"/>
			<fd:dgCol title="{com.glaway.ids.common.lable.starttime}" field="planStartTime" width="80" sortable="false"/>
			<fd:dgCol title="{com.glaway.ids.common.lable.endtime}" field="planEndTime" width="80" sortable="false"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.task.planLevel}" field="planLevel" width="100" formatterFunName="planLevelShow" 
					editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/planController.do?useablePlanLevelList',
					            method:'get',
					            valueField:'id',
					            textField:'name',
					             panelMaxHeight:'150px'
							}}" sortable="false"/>
		</fd:datagrid>
	 </c:if>
	 
	 <c:if test="${isStandards == 'nook'}">
	 	<fd:datagrid toolbar="#tb" idField="id" id="batchModifyEditList" pagination="false" checkbox="true" fit="true" fitColumns="true" onDblClickRow="onClickRowBatch" onClickFunName="workTimeOnChange" width="700px" >
			<fd:dgCol title="cellId" field="cellId" hidden="true"/>
			<fd:dgCol title="{com.glaway.ids.common.lable.status}" field="bizCurrent" hidden="true"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.task.taskname}" field="parentPlanName" width="200" hidden="true"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.task.taskname}" field="planName" width="200" editor="{type:'text'}" sortable="false"/>
			<fd:dgCol title="{com.glaway.ids.common.lable.owner}" field="owner" width="100" formatterFunName="userNameShow"
					 editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/taskFlowResolveController.do?projectMembers&parentPlanId=${parentPlanId}',
					            method:'get',
					            valueField:'id',
					            textField:'realName',
					             panelMaxHeight:'150px'
							}}" sortable="false"/>
			
			<fd:dgCol title="{com.glaway.ids.pm.project.task.workTimeReference}" field="workTimeReference" width="80" sortable="false"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.task.workTime}" field="workTime" width="30" editor="{type:'numberbox'}" sortable="false"/>
			<fd:dgCol title="{com.glaway.ids.common.lable.starttime}" field="planStartTime" width="80" sortable="false"/>
			<fd:dgCol title="{com.glaway.ids.common.lable.endtime}" field="planEndTime" width="80" sortable="false" />
			<fd:dgCol title="{com.glaway.ids.pm.project.task.planLevel}" field="planLevel" width="100" formatterFunName="planLevelShow" 
					editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/planController.do?useablePlanLevelList',
					            method:'get',
					            valueField:'id',
					            textField:'name',
					             panelMaxHeight:'150px'
							}}" sortable="false"/>
		</fd:datagrid>
	</c:if>
</body>
