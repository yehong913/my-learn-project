<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划批量修改</title>
<t:base type="jquery,easyui,tools"></t:base>
	<style>

		.div-msg-btn{bottom:12px;}
		.lhgdialog_cancle{border: 1px solid #0C60AA;
			cursor: pointer;
			padding: 2px 16px;
			line-height: 22px;
			cursor: pointer;
			margin: 0px 8px 0 0;
			-moz-border-radius: 2px;
			-webkit-border-radius: 2px;
			border-radius: 2px;
			font-family: Microsoft Yahei,Arial,Tahoma;
			BACKGROUND-COLOR: #fff;}
		.lhgdialog_cancle:hover{ padding: 2px 16px;
			line-height: 22px;
			cursor: pointer;
			border: 1px solid #0C60AA;
			margin: 0px 8px 0 0;}
		.div-msg-btn div{right:16px}
	</style>


</head>
<body>
<script src="webpage/com/glaway/ids/pm/project/plan/planList.js"></script>
<script type="text/javascript">
	var hidFlag = true;
	var userList = $.parseJSON('${userList1}');
	var planLevelList = $.parseJSON('${planLevelList1}');
	var projectId2 = '${projectId}';
	var changeIds =[];
	var changeOwnerIds =[];
	var changeMilestoneIds = [];
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
								height:'240px',
								onSelect : function(index, row){
									debugger;
									var rows = $('#planModifyList').treegrid('getSelections');
									if(rows.length > 0){
									}else{
										rows = $('#planModifyList').treegrid('getRoots');
										for(var i = 0; i < rows.length; i++){
											var childs = $("#planModifyList").treegrid("getChildren",rows[i].id);
											if(childs.length > 0){
												for (var n = 0; n < childs.length; n++) {
													//changeIds.push(childs[n].id);
													if(changeIds.length > 0){
														var curChangeIds = changeIds.join(",");
														if(curChangeIds.indexOf(childs[n].id)>=0){
														}else{
															changeIds.push(childs[n].id);
														}
													}else{
														changeIds.push(childs[n].id);
													}
													$('#planModifyList').treegrid('update',{
														id: childs[n].id,
														row: {
															planLevel: row.id
														}
													});
												}
											}
										}
									}
									for(var i = 0; i < rows.length; i++){
										//changeIds.push(rows[i].id);
										if(changeIds.length > 0){
											var curChangeIds = changeIds.join(",");
											if(curChangeIds.indexOf(rows[i].id)>=0){
											}else{
												changeIds.push(rows[i].id);
											}
										}else{
											changeIds.push(rows[i].id);
										}

										$('#planModifyList').treegrid('update',{
											id: rows[i].id,
											row: {
												planLevel: row.id
											}
										});
									}
								}
							});
							$('#tinput').textbox('setText','');
							$('#tinput').textbox('textbox').keyup(function(e){
								var value = $('#tinput').textbox('getText');
								setTimeout(function(){
									doSearch(value);
								},1000);
							});
						},300);
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
								height:'240px',
								onSelect : function(index, row){
									var rows = $('#planModifyList').treegrid('getSelections');
									if(rows.length > 0){
									}else{
										rows = $('#planModifyList').treegrid('getRoots');
										for(var i = 0; i < rows.length; i++){
											var childs = $("#planModifyList").treegrid("getChildren",rows[i].id);
											if(childs.length > 0){
												for (var n = 0; n < childs.length; n++) {
													//changeOwnerIds.push(childs[n].id);
													if(changeOwnerIds.length > 0){
														var curChangeOwnerIds = changeOwnerIds.join(",");
														if(curChangeOwnerIds.indexOf(childs[n].id)>=0){
														}else{
															changeOwnerIds.push(childs[n].id);
														}
													}else{
														changeOwnerIds.push(childs[n].id);
													}
													$('#planModifyList').treegrid('update',{
														id: childs[n].id,
														row: {
															owner: row.id
														}
													});
												}
											}
										}
									}
									for(var i = 0; i < rows.length; i++){
										//changeOwnerIds.push(rows[i].id);
										if(changeOwnerIds.length > 0){
											var curChangeOwnerIds = changeOwnerIds.join(",");
											if(curChangeOwnerIds.indexOf(rows[i].id)>=0){
											}else{
												changeOwnerIds.push(rows[i].id);
											}
										}else{
											changeOwnerIds.push(rows[i].id);
										}
										$('#planModifyList').treegrid('update',{
											id: rows[i].id,
											row: {
												owner: row.id
											}
										});
									}
								}
							});
							/* $('#tinput1').textbox('setText','');
                            $('#tinput1').textbox('textbox').keyup(function(e){
                                var value = $('#tinput1').textbox('getText');
                                setTimeout(function(){
                                    doSearch(value);
                                },1000);
                            }); */
						},300);
					});
				});

				$('#tinput').textbox('textbox').keyup(function(e){
					hidFlag = false;
					var value = $('#tinput').textbox('getText');
					setTimeout(function(){
						doSearch(value);
					},800);
				});


				/* $('#tinput1').textbox('textbox').keyup(function(e){
                    hidFlag = false;
                    var value = $('#tinput1').textbox('getText');
                    setTimeout(function(){
                        doSearch1(value);
                    },800);
                }); */

			}
		});

		//去掉分割线
		$($("#ttt2").children("div")[0]).attr("style","border-left:0px solid #ccc;border-right:0px solid #fff;");


	});

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

function changeMilestone2(val){
	debugger;	
	var rows = $('#planModifyList').treegrid('getSelections');
	if(rows.length > 0){								
	}else{
		rows = $('#planModifyList').treegrid('getRoots');	
		for(var i = 0; i < rows.length; i++){
			var childs = $("#planModifyList").treegrid("getChildren",rows[i].id);
			if(childs.length > 0){
		    	for (var n = 0; n < childs.length; n++) {
		    		//changeMilestoneIds.push(childs[n].id);		    		
		    		if(changeMilestoneIds.length > 0){
		    			var curChangeMilestoneIds = changeMilestoneIds.join(",");
		    			if(curChangeMilestoneIds.indexOf(childs[n].id)>=0){
		    			}else{
		    			changeMilestoneIds.push(childs[n].id);
		    			}
		    		}else{
		    			changeMilestoneIds.push(childs[n].id);
		    		}
		    		if(val == "false" || val == 'false'){
		    			if(childs[n].workTime == "0"){
		    				top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
                            return false;
		    			}else{
		    				$('#planModifyList').treegrid('update',{
								id: childs[n].id,
								row: {
									milestone: val
								}
							});	
		    			}
		    		}else{
		    			$('#planModifyList').treegrid('update',{
							id: childs[n].id,
							row: {
								milestone: val
							}
						});		
		    		}
		    								    							    		
		    	}
	    	}
		}	
	}						
	for(var i = 0; i < rows.length; i++){
		if(changeMilestoneIds.length > 0){
		var curChangeMilestoneIds = changeMilestoneIds.join(",");
		if(curChangeMilestoneIds.indexOf(rows[i].id)>=0){
		}else{
		changeMilestoneIds.push(rows[i].id);
		}
		}else{
			changeMilestoneIds.push(rows[i].id);	
		}
		
		if(val == "false" || val == 'false'){
			if(rows[i].workTime == "0"){
				top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
                return false;
			}else{
				$('#planModifyList').treegrid('update',{
					id: rows[i].id,
					row: {
						milestone: val
					}
				}); 
			}
		}else{
			$('#planModifyList').treegrid('update',{
				id: rows[i].id,
				row: {
					milestone: val
				}
			}); 
		}
		
	}
}

</script>
<div border="false" class="easyui-panel div-msg" fit="true" style="width: 100%;">
    <input id="planLevelList" name="planLevelList" type="hidden" value="${planLevelList}">
    <input id="planLevelSelect" name="planLevelSelect" type="hidden">
		<fd:toolbar id="aa">
			<div fit="true" style="width: 100%;">
				<div class="easyui-panel" fit="true" style="width: 100%;">
					<a href="javascript:void(0);" class="easyui-menubutton"
						data-options="menu:'#mm1',iconCls:'icon-edit'">批量更新</a>
				</div>
				<div id="mm1" style="width: 150px;">
					<div id='t'>
						<span>计划等级</span>
						<div id="ttt"
							style="width: 161px; height: 278px; overflow-x: hidden; overflow-y: hidden;">
							<input class="easyui-textbox" style="width: 95%; float: right;"
								iconCls="" id="tinput" data-options="iconCls:'icon-remove'">
							<div id="dl" style="width: 95%; height:240px;display: none;"></div>
						</div>
					</div>
					
					<div>
						<span>里程碑</span>
						<div id="ttt2" style="width:30px;height:60px;" >
							<span>
								<table>								
									<tr>
										<td><input type="radio" id="yes" value="y" title="是" name="chooseRadio" onclick="changeMilestone2('true')" /></td>
										<td><label for="yes">是</label></td>
									</tr>
									<tr>
										<td><input type="radio" id="no" value="n" title="否" name="chooseRadio" onclick="changeMilestone2('false')"/></td>
										<td><label for="no">否</label></td>
									</tr>
								</table>
							</span>
						</div>
					</div>
				</div>
			</div>
			
		</fd:toolbar>
		<fd:treegrid id="planModifyList" idField="id" treeField="planName" singleSelect="false" toolbar="#aa"  style="width:100%;"
			url="planTemplateController.do?getListForPlanMassModify&planTemplateId=${planTemplateId}&projectTemplateId=${projectTemplateId}" fit="true" fitColumns="true">
		<fd:columns>
		    <fd:column title="{com.glaway.ids.pm.project.plan.planNumber}" field="planNumber"/>
		    <%-- <fd:column title="1111" field="id" width="25"  tipField="id" /> --%>
			<fd:column title="{com.glaway.ids.pm.project.plan.planName}" field="planName" width="50" formatter="viewPlanTwo" tipField="planName" />
			<fd:column title="{com.glaway.ids.pm.project.plan.planLevel}" field="planLevel" width="25" formatter="formatterByBusinessConfigTwo" 
					editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/planController.do?useablePlanLevelList',
					            method:'get',
					            valueField:'id',
					            textField:'name',
					            onChange:onChanges2
							}}" />
			
		
			<fd:column title="参考工期（天）" field="workTime" width="30" formatter="workTimeFormatter11" editor="textbox"/>
			<fd:column title="{com.glaway.ids.pm.project.plan.milestone}" field="milestone" width="30" formatter="delMilestone"     
					editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/planChangeMassController.do?queryMilestone',
					            method:'get',
					            valueField:'formKey',
					            textField:'formName',
					            onChange:onChanges 
							}}" />
		</fd:columns>
	</fd:treegrid>	
		
	<%--<fd:lazyLoadingTreeGrid url="planController.do?planModifyList" id="planModifyList"
		width="99%" height="80%" imgUrl="plug-in/icons_greenfolders/"
		initWidths="0,0,0,*,70,80,100,70,70,65,60"
		columnIds="id,parentPlanId,planNumber,planName,planLevelInfo,bizCurrentInfo,ownerInfo,planStartTime,planEndTime,workTime,milestone"
		header="ID,parentPlanId, ,计划名称,计划等级,状态,负责人,开始时间,结束时间,工期(天),里程碑"
		columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
		colAlign="left,left,left,,left,left,left,left,left,left,left,left"
		colSortings="na,na,na,na,na,na,na,na,na,na,na"
		colTypes="ro,ro,ro,tree,co,ro,ed,ed,ed,ro,co"
		enableTreeGridLines="true" enableLoadingStatus="true">
	</fd:lazyLoadingTreeGrid>--%>

</div>

	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.planinfo}">
	
<%-- 修改bug19267		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="updatePlanTimeTran"></fd:dialogbutton> --%>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>

<script type="text/javascript">
	 $(document).ready(function (){
		$("#planModifyList").treegrid({
			onClickRow:edit,
			onDblClickRow:edit2
		});
		$('#modifyMassCancelBtn').focus();
	});
	 
	 // 查看单条计划页面 修改时间 提交保存
	 function updatePlanTimeTran(iframe){
		var F=iframe.saveTimeTransient();	
		if(F=='change')
			{
				var win=$.fn.lhgdialog("getSelectParentWin");
				var planListSearch=win.planListSearch();
			}
	 }
	 
	var first = undefined;
	var changes = [];
	function saveModifyList() {
		debugger;
		changeMilestoneIds = changeMilestoneIds;
			 
		 if(changeMilestoneIds.length>0){ 
				for(var i = 0; i < changeMilestoneIds.length; i++){
					if(changeIds.length>0){
						var curChangeIds = changeIds.join(",");
						if(curChangeIds.indexOf(changeMilestoneIds[i]) >= 0){							
						}else{
						changeIds.push(changeMilestoneIds[i]);
						}
					}else{
						changeIds.push(changeMilestoneIds[i]);	
					}
				}
			} 
		if(changeIds != ""){
			changeIds = changeIds.join(",");
		}
			var t = $('#planModifyList');
			t.treegrid('endEdit', editingId);
			var rows = $('#planModifyList').treegrid('getChanges');	
			var rows2 = $('#planModifyList').treegrid('getRoots');
			var ids = [];
			var planLeveIds = [];
			var workTimes = [];
			var milestones = [];
			
			
			
			if(rows2.length > 0){
				for(var i = 0; i < rows2.length; i++){
					var aa = '1';
				
					debugger;
					if(changeIds.length>0){
					if(changeIds.indexOf(rows2[i].id) >= 0){												
							ids.push(rows2[i].id);
								var curPlanLevel = rows2[i].planLevel;
				    		var planLevelList = eval($('#planLevelList').val());
				    		for (var j = 0; j < planLevelList.length; j++) {
								if (curPlanLevel == planLevelList[j].id) {
									planLeveIds.push(curPlanLevel);
									break;
								}
								if (curPlanLevel == planLevelList[j].name) {
									planLeveIds.push(planLevelList[j].id);
									break;
								}
				    		}
				    		var curWorkTime = rows2[i].workTime;
				    		workTimes.push(curWorkTime);					    		
				    		var curMilestone = rows2[i].milestone;
				    		milestones.push(curMilestone);															
					}
					}
					
								
					
					debugger;
					var childs = $("#planModifyList").treegrid("getChildren",rows2[i].id);
			    	if(childs.length > 0){
				    	for (var n = 0; n < childs.length; n++) {
							var aa = '1';
							
							if(changeIds.length>0){
							if(changeIds.indexOf(childs[n].id) >= 0){						
			
									ids.push(childs[n].id);
	 								var curPlanLevel = childs[n].planLevel;
						    		var planLevelList = eval($('#planLevelList').val());
						    		for (var j = 0; j < planLevelList.length; j++) {
										if (curPlanLevel == planLevelList[j].id) {
											planLeveIds.push(curPlanLevel);
											break;
										}
										if (curPlanLevel == planLevelList[j].name) {
											planLeveIds.push(planLevelList[j].id);
											break;
										}
						    		}
						    			    		
						    		var curWorkTime = childs[n].workTime;
						    		workTimes.push(curWorkTime);					    		
						    		var curMilestone = childs[n].milestone;
						    		milestones.push(curMilestone);			
								/* } */						
							}
							}
							
				    	}
			    	}
			    	
				}	
			}	
						

			
	
			if(rows.length > 0){
				for(var i = 0; i < rows.length; i++){
					if(changeIds.indexOf(rows[i].id) < 0){
		    		ids.push(rows[i].id);
		    		if(rows[i].planLevel != null && rows[i].planLevel != '' && rows[i].planLevel != 'undefined'){
			    		var planLevelList = eval($('#planLevelList').val());
		    			for (var j = 0; j < planLevelList.length; j++) {
							if (rows[i].planLevel == planLevelList[j].id) {
								planLeveIds.push(rows[i].planLevel);
								break;
							}
							if (rows[i].planLevel == planLevelList[j].name) {
								planLeveIds.push(planLevelList[j].id);
								break;
							}
			    		}
		    		}
		    		else{
		    			planLeveIds.push(' ');
		    		}
		    		
		    		workTimes.push($.trim(rows[i].workTime));
		    		milestones.push(rows[i].milestone);
					}
				}
				
				for(var i = 0; i < changes.length; i++){
					var rowss = $('#planModifyList').treegrid('find',changes[i]);
					if(changeIds.indexOf(rowss[i].id) < 0){
		    		ids.push(rowss.id);
		    		if(rowss.planLevel != null && rowss.planLevel != '' && rowss.planLevel != 'undefined'){
			    		var planLevelList = eval($('#planLevelList').val());
			    		for (var j = 0; j < planLevelList.length; j++) {
							if (rowss.planLevel == planLevelList[j].id) {
								planLeveIds.push(rowss.planLevel);
								break;
							}
							if (rowss.planLevel == planLevelList[j].name) {
								planLeveIds.push(planLevelList[j].id);
								break;
							}
			    		}
		    		}
		    		else{
		    			planLeveIds.push(' ');
		    		}
		    		
		    		
		    		workTimes.push($.trim(rowss.workTime));
		    		milestones.push(rowss.milestone);
					}
				}
			}
	    	debugger;
			$.ajax({
				url : 'planTemplateController.do?saveModifyList',
				type : 'post',
				data : {
					ids : ids.join(','),
					planLeveIds : planLeveIds.join(','),
					workTimes : workTimes.join(','),
					milestones : milestones.join(','),
					projectTemplateId : '${projectTemplateId}',
					planTemplateId : '${planTemplateId}'
				},
				cache : false,
				success : function(data) {
					debugger;
					var win = $.fn.lhgdialog("getSelectParentWin");
					try{
						win.iframeFlush();
					}catch (e) {

					}

					try{
						win.planListSearch();
					}catch (e) {

					}
					$.fn.lhgdialog("closeSelect");

				}
			});
		}
	
	var rmStart;
	var rmEnd;
	var editingId;
	var rmTime;
	var rmilestone;
	function edit() {
		save();
	}
	
	function edit2() {
		
		debugger;
		first = 1;
		if (editingId != undefined) {
			$('#planModifyList').treegrid('select', editingId);
			return;
		}
		rows = $('#planModifyList').treegrid('getSelections');
		if (rows.length>1) {
			top.tip("如果修改单行数据，请选中一行操作");	
			return false;
		}				
		var row = $('#planModifyList').treegrid('getSelected');
	/* 	rmStart = row.planStartTime
		rmEnd = row.planEndTime; */
		rmTime=row.workTime;
		rmilestone = row.milestone;
		
		if (row) {
			editingId = row.id;
			$('#planModifyList').treegrid('beginEdit', editingId);
		}
		
		/* if(row.bizCurrent != "EDITING"||row.flowStatus != "NORMAL"){
			
		}else{
			if (row) {
				editingId = row.id;
				$('#planModifyList').treegrid('beginEdit', editingId);
			}
		} */
	}

	function save() {
		debugger;
		if (editingId != undefined) {
			var t = $('#planModifyList');
			t.treegrid('endEdit', editingId);
			var remremberId = editingId;
			editingId = undefined;
			var persons = 0;
			var roots = $("#planModifyList").treegrid("getRoots");
			if(roots.length > 0){
				for(var i = 0; i < roots.length; i++){
		    		if(roots[i].id == remremberId){
		    			var newTime=roots[i].workTime;
		    			
		    			if(roots[i].workTime == ''|| roots[i].workTime==undefined || roots[i].workTime == null){
		    					$('#planModifyList').treegrid('beginEdit', remremberId);
		    					editingId = remremberId;
		    					$('#planModifyList').treegrid('update',{
		    						id: remremberId,
		    						row: {
		    							workTime: rmTime
		    						}
		    					});
		    					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyPeriod"/>');
		    					return false;
		    				}else{
		    					var reg = new RegExp("^[1-9][0-9]{0,3}$");
		    					if(roots[i].workTime == "0"){
		    						if(roots[i].milestone == "否"){
		    							$('#planModifyList').treegrid('beginEdit', remremberId);
		                                editingId = remremberId;
	                                    $('#planModifyList').treegrid('update',{
	                                        id: remremberId,
	                                        row: {
	                                            workTime: rmTime,
	                                            milestone : rmilestone
	                                        }
	                                    });
	                                    top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
	                                    return false;
		    						}
		    					}
		    					else {
									if(roots[i].workTime != '0') {
										if(!reg.test(roots[i].workTime)){
											$('#planModifyList').treegrid('beginEdit', remremberId);
											editingId = remremberId;
											$('#planModifyList').treegrid('update',{
												id: remremberId,
												row: {
													workTime: rmTime
												}
											});
											top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
											return false;
										}
									}
								}
		    					//里程碑的工期可以为0
		    					/* if(roots[i].milestone == "否") {
		    						if(!reg.test(roots[i].workTime)){
	                                    $('#planModifyList').treegrid('beginEdit', remremberId);
	                                    editingId = remremberId;
	                                    $('#planModifyList').treegrid('update',{
	                                        id: remremberId,
	                                        row: {
	                                            workTime: rmTime
	                                        }
	                                    });
	                                    top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
	                                    return false;
	                                }
		    					} else {
		    						if(roots[i].workTime != '0') {
		    							if(!reg.test(roots[i].workTime)){
	                                        $('#planModifyList').treegrid('beginEdit', remremberId);
	                                        editingId = remremberId;
	                                        $('#planModifyList').treegrid('update',{
	                                            id: remremberId,
	                                            row: {
	                                                workTime: rmTime
	                                            }
	                                        });
	                                        top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
	                                        return false;
	                                    }
		    						}
		    					} */
		    					
		    				}
						var child2 = $("#planModifyList").treegrid("getChildren",remremberId);
				    	if(child2.length > 0){
				    		
				    		var preFlag = false;
				    		for(var y = 0; y < child2.length; y++){
    							if(child2[y].preposeIds != "" || child2[y].preposeIds != null || child2[y].preposeIds != undefined){
    								for(var y1 = 0; y1 < child2.length; y1++){
    									if(child2[y].preposeIds == child2[y1].id){    //存在前置计划且前置计划与当前子计划同一个父计划的情况
    										preFlag = true; 
    										break;
    									}
    								}
    							}
    						}
				    		
				    		if(!preFlag){
				    			for (var j = 0; j < child2.length; j++) {
						    		debugger;
									if(parseInt(child2[j].workTime)>parseInt(roots[i].workTime)) {
										$('#planModifyList').treegrid('beginEdit', remremberId);
					    				editingId = remremberId;
					    				top.tip('子计划工期必须收敛于父级计划工期');
					    				$("#planModifyList").treegrid('update',{
					    					id: remremberId,
					    					row: {
					    						workTime: rmTime
					    					}
					    				});
					    				return false;
									}
						    	}
				    		}else{
				    			$.ajax({
				    				type : 'POST',
				    				data : {
				    					planId : roots[i].id,
				    					workTime : roots[i].workTime,
				    					planTemplateId : '${planTemplateId}'
				    				},
				    				url : 'planTemplateController.do?checkParentPlanWorkTime',
				    				cache : false,
				    				success : function(data){
				    					var dat = $.parseJSON(data);
				    					if(!dat.success){
				    						$('#planModifyList').treegrid('beginEdit', remremberId);
						    				editingId = remremberId;
						    				$("#planModifyList").treegrid('update',{
						    					id: remremberId,
						    					row: {
						    						workTime: rmTime
						    					}
						    				});
						    				top.tip(dat.msg);
						    				return false;
				    					}
				    				}
				    			});
				    		}
				    		
				    	}
		    			
		    		}
					
					// 00000 TODO child
					debugger;
					var child = $("#planModifyList").treegrid("getChildren",roots[i].id);
			    	if(child.length > 0){
				    	for (var j = 0; j < child.length; j++) {
				    		if(child[j].id == remremberId){
				    
				    			if(child[j].workTime == ''|| child[j].workTime==undefined || child[j].workTime == null){
			    					$('#planModifyList').treegrid('beginEdit', remremberId);
			    					editingId = remremberId;
			    					$('#planModifyList').treegrid('update',{
		    							id: remremberId,
		    							row: {
		    								workTime: rmTime
		    							}
		    						});
			    					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyPeriod"/>');
			    				}else{
			    					var reg = new RegExp("^[1-9][1-9]{0,3}$");
			    					
			    					if(child[j].workTime == "0"){
			    						if(child[j].milestone == "否"){
			    							 $('#planModifyList').treegrid('beginEdit', remremberId);
			                                    editingId = remremberId;
			                                    
			                                    $('#planModifyList').treegrid('update',{
			                                        id: remremberId,
			                                        row: {
			                                            workTime: rmTime,
			                                            milestone : rmilestone
			                                        }
			                                    });
			                                    top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
			                                    return false;
			    						}
			    					}
									else {
										if(child[j].workTime != '0') {
											if(!reg.test(child[j].workTime)){
												$('#planModifyList').treegrid('beginEdit', remremberId);
												editingId = remremberId;
												$('#planModifyList').treegrid('update',{
													id: remremberId,
													row: {
														workTime: rmTime
													}
												});
												top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
												return false;
											}
										}
									}
			    					
			    					if(child[j].preposeIds == "" || child[j].preposeIds == null){   //当前子计划前置计划为空时，只需判断当前工期小于父计划工期即可
			    						if(parseInt(child[j].workTime) > parseInt(roots[i].workTime)){
			    							 $('#planModifyList').treegrid('beginEdit', remremberId);
			                                    editingId = remremberId;
			                                 $('#planModifyList').treegrid('update',{
		                                        id: remremberId,
		                                        row: {
		                                            workTime: rmTime
		                                        }
			                                  });
			    							top.tip("工期不能超过父计划工期");
			    							return false;
			    						}
			    					}else{   //存在前置计划
			    						var flag = false;
			    						for(var x = 0; x < child.length; x++){
			    							if(child[x].id == child[j].preposeIds){
			    								flag = true;
			    								break;
			    							}
			    						}
			    						if(!flag){   //该子计划的前置计划不与该子计划同一个父计划，此时只需判断该子计划的工期小于父计划即可
			    							if(parseInt(child[j].workTime) > parseInt(roots[i].workTime)){
				    							 $('#planModifyList').treegrid('beginEdit', remremberId);
				                                    editingId = remremberId;
				                                 $('#planModifyList').treegrid('update',{
			                                        id: remremberId,
			                                        row: {
			                                            workTime: rmTime
			                                        }
				                                  });
				    							top.tip("工期不能超过父计划工期");
				    							return false;
				    						}
			    						}else{  //如果前置计划与当前计划同一个父计划，则需当前计划+前置计划的工期小于父计划
			    							$.ajax({
			    								type : 'POST',
			    								data : {
			    									planTemplateId : '${planTemplateId}',
			    									planId : child[j].id,
			    									planName : child[j].planName,
			    									workTime : child[j].workTime,
			    									parentPlanId : child[j].parentPlanId,
			    									preposeIds : child[j].preposeIds
			    								},
			    								url : 'planTemplateController.do?checkPlanWorkTime',
			    								cache : false,
			    								success:function(data){
			    									var d = $.parseJSON(data);
			    									if(d.success){
			    										
			    									}else{
		    										 	$('#planModifyList').treegrid('beginEdit', remremberId);
					                                    	editingId = remremberId;
					                                 	$('#planModifyList').treegrid('update',{
					                                        id: remremberId,
					                                        row: {
					                                            workTime: rmTime
					                                        }
					                                  	});
			    										top.tip(d.msg);
			    										return false;
			    									}
			    								}
			    							});
			    						}
			    					} 
			    				}
				    		}
				    	}
				    }
				}	
			}
		}
	}
		
	function cancel(){
		if (editingId != undefined){
			$('#planModifyList').treegrid('cancelEdit', editingId);
			editingId = undefined;
		}
	}
	
	function selectUser() {
		var leaderName = $('#leaderName').val();
		var leader = $('#leader').val();
		$.fn.lhgdialog({
			title : '选择用户',
			content : 'url:webpage/common/commonSelectUser.jsp',
			icon : 'succeed',
			height : 400,
			width : 840,
			zIndex : 9999,
			cancelVal: '取消',
 		    cancel: true,
			ok : function() {
				iframe = this.iframe.contentWindow;
				var selectUsers = iframe.setSelectUsers();
				if (selectUsers != null && selectUsers.length > 0) {
					$('#leaderName').val(selectUsers[0].realName);
					$('#leader').val(selectUsers[0].userName);
				}
			}

		});
	}

	function selectUser2() {
		var deptLeaderName = $('#deptLeaderName').val();
		var deptLeader = $('#deptLeader').val();
		$.fn.lhgdialog({
			title : '选择用户',
			content : 'url:webpage/common/commonSelectUser.jsp',
			icon : 'succeed',
			height : 400,
			width : 840,
			zIndex : 9999,
			cancelVal: '取消',
 		    cancel: true,
			ok : function() {
				iframe = this.iframe.contentWindow;
				var selectUsers = iframe.setSelectUsers();
				if (selectUsers != null && selectUsers.length > 0) {
					$('#deptLeaderName').val(selectUsers[0].realName);
					$('#deptLeader').val(selectUsers[0].userName);
				}
			}

		});
	}
	
	//计划名称链接事件
	function viewPlanTwo(val, row, value) {
		/* if(row.bizCurrent != "EDITING" || row.flowStatus != "NORMAL"){
			return '<a href="#" onclick="viewPlan(\'' + row.id
			+ '\')" style="color:gray">' + row.planName + '</a>';
		}else{ */
		return '<a href="#" onclick="viewPlan(\'' + row.id
			+ '\')" style="color:blue">' + row.planName + '</a>';
		/* } */
		
	}
		
	// 查看计划信息
	function viewPlan(id) {
		debugger;
		var dialogUrl = 'planTemplateController.do?goViewPlan&planTemplateId=${planTemplateId}&id=${projectTemplateId}&planId=' + id;
	    createDialog('viewPlanDialog', dialogUrl);
		/* $.fn.lhgdialog({
			content : 'url:planController.do?goCheck&id=' + id,
			lock : true,
			width : 730,
			height : 490,
			zIndex : 5000,
			title : '计划信息',
			opacity : 0.3,
			cache : false
		}); */
	}
	
	
	function delMilestone(val, row, value){
		if(val != undefined && val != null && val == 'false'){
			return "否";
		}else if(val =='否'){
			return "否";
		}else if(val =='是'){
			return "是";
		}else{
			return "是";
		}
	}

	// 人员id、realName转换
	function viewUserRealNameTwo(val, row, index) {
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
	
	// 人员id、realName转换
	function formatterByBusinessConfigTwo(val, row, index) {
		var planLevelList = eval($('#planLevelList').val());
		for (var i = 0; i < planLevelList.length; i++) {
			if (val == planLevelList[i].id) {
				return planLevelList[i].name;
			}
			if (val == planLevelList[i].name) {
				return planLevelList[i].name;
			}
		}
	}
	
	function onChanges2(newValue, oldValue){
		if(newValue != undefined){
		var result2 = newValue;
		var planLevelList = eval($('#planLevelList').val());
		var aa = '1';
		for (var i = 0; i < planLevelList.length; i++) {
			if (newValue == planLevelList[i].id) {
				result2 = planLevelList[i].name;
				aa = '0';
			}
			if (newValue == planLevelList[i].name) {
				result2 = planLevelList[i].name;
				aa = '0';
			}
		}
		if(aa == '1'){
			$(this).combobox('setValue',"");	
		}else{
			$(this).combobox('setValue',result2);	
		}
		
		}
	}
	
	function onChanges3(newValue, oldValue){
		if(newValue != undefined){
		var result3 = newValue;
		var userList = eval($('#userList').val());
		var aa = '1';
		for (var i = 0; i < userList.length; i++) {
			if (newValue == userList[i].id) {
				result3 = userList[i].realName;
				aa = '0';
			}
			if (newValue == userList[i].realName) {
				result3 = userList[i].realName;
				aa = '0';
			}
		}
		if(aa == '1'){
			$(this).combobox('setValue',"");	
		}else{
			//$(this).combobox('setValue',result2);	
			$(this).combobox('setValue',result3);
		}
		//$(this).combobox('setValue',result3);
		}
	}
	
	//生命周期状态name、title转换
	function viewPlanBizCurrent(val, row, index) {
			var statusList = eval($('#statusList').val());
			for (var i = 0; i < statusList.length; i++) {
				if (val == statusList[i].name) {
					return statusList[i].title;
				}
			}
	}
	
	function onChanges(newValue, oldValue){
		var  result="否";
		debugger;
		if(newValue=='true' || newValue=='是' ){
			result="是";
		}
		$(this).combobox('setValue',result);
	}
	
	function save2() {
		debugger;
		if (editingId != undefined) {
			var flg=false;
			var t = $('#planModifyList');
			t.treegrid('endEdit', editingId);
			var remremberId = editingId;
			editingId = undefined;
			var persons = 0;
			var roots = $("#planModifyList").treegrid("getRoots");
			if(roots.length > 0){
				for(var i = 0; i < roots.length; i++){
		    		if(roots[i].id == remremberId){
		    			if(roots[i].workTime == ''|| roots[i].workTime==undefined || roots[i].workTime == null){
	    					$('#planModifyList').treegrid('beginEdit', remremberId);
	    					editingId = remremberId;
	    					$('#planModifyList').treegrid('update',{
	    						id: remremberId,
	    						row: {
	    							workTime: rmTime
	    						}
	    					});
	    					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyPeriod"/>');
	    					return false;
	    				}else{
	    					var reg = new RegExp("^[1-9][0-9]{0,3}$");
	    					if(roots[i].workTime == "0"){
	    						if(roots[i].milestone == "否"){
	    							$('#planModifyList').treegrid('beginEdit', remremberId);
	                                editingId = remremberId;
                                    $('#planModifyList').treegrid('update',{
                                        id: remremberId,
                                        row: {
                                            workTime: rmTime,
                                            milestone : rmilestone
                                        }
                                    });
                                    top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
                                    return false;
	    						}
	    					}
	    				}
		    			
		    			var child2 = $("#planModifyList").treegrid("getChildren",remremberId);
				    	if(child2.length > 0){
				    		
				    		var preFlag = false;
				    		for(var y = 0; y < child2.length; y++){
    							if(child2[y].preposeIds != "" || child2[y].preposeIds != null || child2[y].preposeIds != undefined){
    								for(var y1 = 0; y1 < child2.length; y1++){
    									if(child2[y].preposeIds == child2[y1].id){    //存在前置计划且前置计划与当前子计划同一个父计划的情况
    										preFlag = true; 
    										break;
    									}
    								}
    							}
    						}
				    		
				    		if(!preFlag){
				    			for (var j = 0; j < child2.length; j++) {
						    		debugger;
									if(parseInt(child2[j].workTime)>parseInt(roots[i].workTime)) {
										$('#planModifyList').treegrid('beginEdit', remremberId);
					    				editingId = remremberId;
					    				top.tip('子计划工期必须收敛于父级计划工期');
					    				$("#planModifyList").treegrid('update',{
					    					id: remremberId,
					    					row: {
					    						workTime: rmTime
					    					}
					    				});
					    				return false;
									}
						    	}
				    		}else{
				    			$.ajax({
				    				type : 'POST',
				    				data : {
				    					planId : roots[i].id,
				    					workTime : roots[i].workTime,
				    					projectTemplateId : '${projectTemplateId}',
				    					planTemplateId : '${planTemplateId}'
				    				},
				    				url : 'planTemplateController.do?checkParentPlanWorkTime',
				    				cache : false,
				    				success : function(data){
				    					var dat = $.parseJSON(data);
				    					if(!dat.success){
				    						$('#planModifyList').treegrid('beginEdit', remremberId);
						    				editingId = remremberId;
						    				$("#planModifyList").treegrid('update',{
						    					id: remremberId,
						    					row: {
						    						workTime: rmTime
						    					}
						    				});
						    				top.tip(dat.msg);
						    				return false;
				    					}
				    				}
				    			});
				    		}
				    		saveModifyList();
				    		
				    	}
				    	saveModifyList();
		    			
		    		}
					
		    		var child = $("#planModifyList").treegrid("getChildren",roots[i].id);
			    	if(child.length > 0){
				    	for (var j = 0; j < child.length; j++) {
				    		if(child[j].id == remremberId){
				    
				    			if(child[j].workTime == ''|| child[j].workTime==undefined || child[j].workTime == null){
			    					$('#planModifyList').treegrid('beginEdit', remremberId);
			    					editingId = remremberId;
			    					$('#planModifyList').treegrid('update',{
		    							id: remremberId,
		    							row: {
		    								workTime: rmTime
		    							}
		    						});
			    					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyPeriod"/>');
			    				}else{
			    					var reg = new RegExp("^[1-9][1-9]{0,3}$");
			    					
			    					if(child[j].workTime == "0"){
			    						if(child[j].milestone == "否"){
			    							 $('#planModifyList').treegrid('beginEdit', remremberId);
			                                    editingId = remremberId;
			                                    
			                                    $('#planModifyList').treegrid('update',{
			                                        id: remremberId,
			                                        row: {
			                                            workTime: rmTime,
			                                            milestone : rmilestone
			                                        }
			                                    });
			                                    top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
			                                    return false;
			    						}
			    					}
			    					
			    					if(child[j].preposeIds == "" || child[j].preposeIds == null){   //当前子计划前置计划为空时，只需判断当前工期小于父计划工期即可
			    						if(parseInt(child[j].workTime) > parseInt(roots[i].workTime)){
			    							 $('#planModifyList').treegrid('beginEdit', remremberId);
			                                    editingId = remremberId;
			                                 $('#planModifyList').treegrid('update',{
		                                        id: remremberId,
		                                        row: {
		                                            workTime: rmTime
		                                        }
			                                  });
			    							top.tip("工期不能超过父计划工期");
			    							return false;
			    						}
			    					}else{   //存在前置计划
			    						var flag = false;
			    						for(var x = 0; x < child.length; x++){
			    							if(child[x].id == child[j].preposeIds){
			    								flag = true;
			    								break;
			    							}
			    						}
			    						if(!flag){   //该子计划的前置计划不与该子计划同一个父计划，此时只需判断该子计划的工期小于父计划即可
			    							if(parseInt(child[j].workTime) > parseInt(roots[i].workTime)){
				    							 $('#planModifyList').treegrid('beginEdit', remremberId);
				                                    editingId = remremberId;
				                                 $('#planModifyList').treegrid('update',{
			                                        id: remremberId,
			                                        row: {
			                                            workTime: rmTime
			                                        }
				                                  });
				    							top.tip("工期不能超过父计划工期");
				    							return false;
				    						}
			    						}else{  //如果前置计划与当前计划同一个父计划，则需当前计划+前置计划的工期小于父计划
			    							$.ajax({
			    								type : 'POST',
			    								data : {
			    									planTemplateId : '${planTemplateId}',
			    									planId : child[j].id,
			    									planName : child[j].planName,
			    									workTime : child[j].workTime,
			    									parentPlanId : child[j].parentPlanId,
			    									preposeIds : child[j].preposeIds
			    								},
			    								url : 'planTemplateController.do?checkPlanWorkTime',
			    								cache : false,
			    								success:function(data){
			    									var d = $.parseJSON(data);
			    									if(d.success){
			    										
			    									}else{
		    										 	$('#planModifyList').treegrid('beginEdit', remremberId);
					                                    	editingId = remremberId;
					                                 	$('#planModifyList').treegrid('update',{
					                                        id: remremberId,
					                                        row: {
					                                            workTime: rmTime
					                                        }
					                                  	});
			    										top.tip(d.msg);
			    										return false;
			    									}
			    								}
			    							});
			    						}
			    					} 
			    				}
				    			saveModifyList();
				    		}
				    	}
				    }
				}	
			}

		}else{
			saveModifyList();
		}
	}
	
	function workTimeFormatter11(val, row, index) {
		if (val != undefined && val != null && val != '') {
			var a = val.indexOf(".");
			if(a == 0){
				val = val.split('.')[0];
				return val;
			}else{
				return val;
			}
			
		}else{
			return "";
		}
		
	}
		
	function closePlan() {
		$.fn.lhgdialog("closeSelect");
	} 
	function checkWorkTime()
	{
		var workTime = $('#workTime').val();
		var reg = new RegExp("^[0-9]*$");
		if (workTime == "") {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyPeriod"/>');
			return false;
		}
			
		if(!reg.test(workTime)){
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
			return false;
		}
	}
</script>

</body>
</html>