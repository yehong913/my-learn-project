<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划批量修改</title>
<t:base type="jquery,easyui,tools"></t:base>

<div border="false" class="easyui-panel div-msg" fit="true">
	<input id="projectId" name="projectId" type="hidden" value="${projectId}"> 
	<input id="statusList" name="statusList" type="hidden" value="${statusList}">
    <input id="userList" name="userList" type="hidden" value="${userList}">
    <input id="planLevelList" name="planLevelList" type="hidden" value="${planLevelList}">
    <input id="planLevelSelect" name="planLevelSelect" type="hidden">
		<fd:toolbar id="aa">
			<div fit="true" style="width: 100%;">
				<div class="easyui-panel" fit="true" style="width: 100%;">
					<a href="#" class="easyui-menubutton"
						data-options="menu:'#mm1',iconCls:'icon-edit'">批量更新</a>
				</div>
				<div id="mm1" style="width: 165px;overflow-x: hidden; overflow-y: hidden;">
					<div id='t'>
						<span>计划等级</span>
						<div id="ttt"
							style="width: 161px; height: 278px; overflow-x: hidden; overflow-y: hidden;">
							<input class="easyui-textbox" style="width: 140px; height:22px; float: right;"
								iconCls="" id="tinput" data-options="iconCls:'icon-remove'">
							<div id="dl" style=" display: none;"></div>
						</div>
					</div>
					<div id='t1'>
						<span>负责人</span>
						<div id="ttt1"
							style="width: 161px; height: 278px; overflow-x: hidden; overflow-y: hidden;">
							<input class="easyui-textbox" style="width: 140px;  height:22px;float: right;"
								iconCls="" id="tinput1" data-options="iconCls:'icon-remove'">
							<div id="dl1" style="width: 95%; display: none;"></div>
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
		<fd:treegrid id="planModifyList" idField="id" treeField="planName" singleSelect="false" toolbar="#aa" 
			url="planController.do?planModifyList&projectId=${projectId}" fit="true" fitColumns="true" >
		<fd:columns>
		    <fd:column title="{com.glaway.ids.pm.project.plan.planNumber}" field="planNumber" />
			<fd:column title="{com.glaway.ids.pm.project.plan.planName}" field="planName" formatter="viewPlanTwo" tipField="planName" width="350" />
			<fd:column title="{com.glaway.ids.pm.project.plan.planLevel}" field="planLevel"  formatter="formatterByBusinessConfigTwo"
					editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/planController.do?useablePlanLevelList',
					            method:'get',
					            valueField:'id',
					            textField:'name',
					            onChange:onChanges2
							}}" />
			<fd:column title="{com.glaway.ids.common.lable.status}" field="bizCurrent" formatter="viewPlanBizCurrent"  />
			<fd:column title="{com.glaway.ids.common.lable.owner}" field="owner" formatter="viewUserRealNameTwo"
					editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/planChangeMassController.do?queryUserListForModify&projectId=${projectId}',
					            method:'get',
					            valueField:'userKey',
					            textField:'userName',
					              onChange:onChanges3
							}}" />
			<fd:column title="{com.glaway.ids.common.lable.starttime}" field="planStartTime" editor="datebox" width="150" />
			<fd:column title="{com.glaway.ids.common.lable.endtime}" field="planEndTime" editor="datebox" width="150"  />
			<fd:column title="{com.glaway.ids.pm.project.plan.workTime}" field="workTime" formatter="workTimeFormatter11" editor="textbox" width="75"/>
			<fd:column title="{com.glaway.ids.pm.project.plan.milestone}" field="milestone" formatter="delMilestone"
					editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/planChangeMassController.do?queryMilestone',
					            method:'get',
					            valueField:'formKey',
					            textField:'formName',
					            onChange:onChanges 
							}}" />
		</fd:columns>
	</fd:treegrid>	
		
	
	<div class="div-msg-btn">
		<div>
			<fd:linkbutton onclick="save2()" value="{com.glaway.ids.common.btn.confirm}" classStyle="button_nor" />
			<fd:linkbutton id="modifyMassCancelBtn" onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" />
		</div>			
	</div>
</div>	

	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.planinfo}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
</body>
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
			debugger;
			var targetMenuItem = $(this).find("#t");
			var targetMenuItem1 = $(this).find("#t1");
			var widtt = $(targetMenuItem).width() - $(targetMenuItem).find("div.menu-rightarrow").width()-5;
			$(targetMenuItem).find("div.menu-text").css("width",widtt);					
			widtt = $(targetMenuItem1).width() - $(targetMenuItem1).find("div.menu-rightarrow").width()-5;
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
				height:'250px',
				onSelect : function(index, row){						
					var rows = $('#planModifyList').treegrid('getSelections');
					if(rows.length > 0){								
					}else{
						rows = $('#planModifyList').treegrid('getRoots');	
						for(var i = 0; i < rows.length; i++){
							debugger						
							var childs = $("#planModifyList").treegrid("getChildren",rows[i].id);
							if(childs.length > 0){
						    	for (var n = 0; n < childs.length; n++) {						    	
						    		//changeIds.push(childs[n].id);
						    		if(childs[n].bizCurrent != "EDITING" || childs[n].flowStatus != "NORMAL"){							
									}else{		
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
					}						
					for(var i = 0; i < rows.length; i++){
						if(rows[i].bizCurrent != "EDITING" || rows[i].flowStatus != "NORMAL"){							
						}else{	
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
							height:'250px',
							onSelect : function(index, row){
								var rows = $('#planModifyList').treegrid('getSelections');
								if(rows.length > 0){								
								}else{
									rows = $('#planModifyList').treegrid('getRoots');	
									for(var i = 0; i < rows.length; i++){
										var childs = $("#planModifyList").treegrid("getChildren",rows[i].id);
										if(childs.length > 0){
									    	for (var n = 0; n < childs.length; n++) {
									    		if(childs[n].bizCurrent != "EDITING" || childs[n].flowStatus != "NORMAL"){							
												}else{		
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
								}						
								for(var i = 0; i < rows.length; i++){
									if(rows[i].bizCurrent != "EDITING" || rows[i].flowStatus != "NORMAL"){							
										}else{	
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
							}		
						});
						$('#tinput1').textbox('setText','');		
						$('#tinput1').textbox('textbox').keyup(function(e){
							var value = $('#tinput1').textbox('getText');
							setTimeout(function(){
								doSearch(value);
							},1000);
						});
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
			
			
			$('#tinput1').textbox('textbox').keyup(function(e){
				hidFlag = false;
				var value = $('#tinput1').textbox('getText');
				setTimeout(function(){
					doSearch1(value);
				},800);
			});
			
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

</script>
</head>
<body>
<script>
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
		    		if(childs[n].bizCurrent != "EDITING" || childs[n].flowStatus != "NORMAL"){							
					}else{	
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
		if(rows[i].bizCurrent != "EDITING" || rows[i].flowStatus != "NORMAL"){							
		}else{
			if(changeMilestoneIds.length > 0){
			var curChangeMilestoneIds = changeMilestoneIds.join(",");
			if(curChangeMilestoneIds.indexOf(rows[i].id)>=0){
			}else{
			changeMilestoneIds.push(rows[i].id);
			}
			}else{
				changeMilestoneIds.push(rows[i].id);	
			}
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
			var t = $('#planModifyList');
			t.treegrid('endEdit', editingId);
			var rows = $('#planModifyList').treegrid('getChanges');	
			var rows2 = $('#planModifyList').treegrid('getRoots');
			
			
			var userList = eval($('#userList').val());
			if(rows2.length > 0){
				for(var i = 0; i < rows2.length; i++){
					var aa = '1';
					for (var j = 0; j < userList.length; j++) {
						if (rows2[i].owner == userList[j].id) {
							aa = '0';
						}
						if (rows2[i].owner == userList[j].realName) {
							aa = '0';
						}
					}
					if(aa == '1'){
						var a = i +1;
						tip('编号'+rows2[i].planNumber+'计划【'+rows2[i].planName+'】的负责人不能为空');
						return false;
					}
					
								
					
					var childs = $("#planModifyList").treegrid("getChildren",rows2[i].id);
			    	if(childs.length > 0){
				    	for (var n = 0; n < childs.length; n++) {
							var aa = '1';
							for (var j = 0; j < userList.length; j++) {
								if (childs[n].owner == userList[j].id) {
									aa = '0';
								}
								if (childs[n].owner == userList[j].realName) {
									aa = '0';
								}
							} 
							if(aa == '1'){
								var a = i +1;
								tip('编号'+childs[n].planNumber+'计划【'+childs[n].planName+'】的负责人不能为空');
								return false;
							}
							
							
				    	}
			    	}
			    	
				}	
			}	 
			
			var changePlanList = new Array();
			if(rows2.length > 0){
				for(var i = 0; i < rows2.length; i++){
					var changePlanObj = new Object();
					changePlanObj['id'] = rows2[i].id;
					changePlanObj['planLevel'] = rows2[i].planLevel;
					changePlanObj['ownerId'] = rows2[i].owner;
					if(userList.length > 0){
						for(var j = 0; j < userList.length; j++){
							if(userList[j].realName == rows2[i].owner){
								changePlanObj['ownerId'] = userList[j].id;
								break;
							}
						}
					}
					changePlanObj['planStartTime'] = rows2[i].planStartTime;
					changePlanObj['planEndTime'] = rows2[i].planEndTime;
					changePlanObj['workTime'] = rows2[i].workTime;
					changePlanObj['milestone'] = rows2[i].milestone;
					changePlanList.push(changePlanObj);
					
					var childs = $("#planModifyList").treegrid("getChildren",rows2[i].id);
					if(childs.length > 0){
						for(var c = 0; c < childs.length; c++){
							var changePlanObj = new Object();
							changePlanObj['id'] = childs[c].id;
							changePlanObj['planLevel'] = childs[c].planLevel;
							changePlanObj['ownerId'] = childs[c].owner;
							if(userList.length > 0){
								for(var u = 0; u < userList.length; u++){
									if(userList[u].realName == childs[c].owner){
										changePlanObj['ownerId'] = userList[u].id;
										break;
									}
								}
							}
							changePlanObj['planStartTime'] = childs[c].planStartTime;
							changePlanObj['planEndTime'] = childs[c].planEndTime;
							changePlanObj['workTime'] = childs[c].workTime;
							changePlanObj['milestone'] = childs[c].milestone;
							changePlanList.push(changePlanObj);
						}
					}
					
				}
				
			}
			
			
	    	debugger;
			$.ajax({
				url : 'planController.do?saveModifyList',
				type : 'post',
				data : {
/* 					ids : ids.join(','),
					planLeveIds : planLeveIds.join(','),
					ownerIds : ownerIds.join(','),
					planStartTimes : planStartTimes.join(','),
					planEndTimes : planEndTimes.join(','),
					workTimes : workTimes.join(','),
					milestones : milestones.join(',') */
					changePlanList : JSON.stringify(changePlanList)
				},
				cache : false,
				success : function(data) {
					var win = $.fn.lhgdialog("getSelectParentWin");
					var planListSearch = win.planListSearch();
					$.fn.lhgdialog("closeSelect");
				}
			});
		}
	
	var rmStart;
	var rmEnd;
	var editingId;
	var rmTime
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
		rmStart = row.planStartTime
		rmEnd = row.planEndTime;
		rmTime=row.workTime;
		if(row.bizCurrent != "EDITING"||row.flowStatus != "NORMAL"){
			
		}else{
			if (row) {
				editingId = row.id;
				$('#planModifyList').treegrid('beginEdit', editingId);
			}
		}
	}
	function save23()
	{
		debugger;
			//var child2 = $("#planModifyList").treegrid("getChildren",remremberId);
		/* var child2 = $('#planModifyList').treegrid('getSelections');
		var old=child2[0].workTime
			alert(rmTime+"="+child2[0].workTime);
		if (editingId != undefined) {
			var t = $('#planModifyList');
			t.treegrid('endEdit', editingId);
			alert(child2[0].planStartTime);
			var remremberId = editingId;
			editingId = undefined;
			var persons = 0;
			var roots = $("#planModifyList").treegrid("getRoots");
			if(roots.length > 0){
				for(var i = 0; i < roots.length; i++){
		    		if(roots[i].id == remremberId){
		    			alert(rmTime+"="+child2[0].workTime+",for");
		    			alert(roots[i].planStartTime);
		    		}
				}
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
		    			var newStart = roots[i].planStartTime;
		    			var newEnd = roots[i].planEndTime;
		    			var newTime=roots[i].workTime;
		    			if(roots[i].planStartTime == ""){
		    				$('#planModifyList').treegrid('beginEdit', remremberId);
		    				$("#planModifyList").treegrid('update',{
		    					id: remremberId,
		    					row: {
		    						planStartTime: rmStart
		    					}
		    				});
		    				editingId = remremberId;
		    				tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
		    				return false;
		    			}
		    			
		    			if(roots[i].planEndTime == ""){
		    				$('#planModifyList').treegrid('beginEdit', remremberId);
		    				editingId = remremberId;
		    				$("#planModifyList").treegrid('update',{
		    					id: remremberId,
		    					row: {
		    						planEndTime: rmEnd
		    					}
		    				});
		    				tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyEnd"/>');
		    				return false;
		    			}
		    			
		    			if(roots[i].planEndTime < roots[i].planStartTime){
		    				$('#planModifyList').treegrid('beginEdit', remremberId);
		    				editingId = remremberId;
		    				$("#planModifyList").treegrid('update',{
		    					id: remremberId,
		    					row: {
		    						planStartTime: rmStart
		    					}
		    				});
		    				$("#planModifyList").treegrid('update',{
		    					id: remremberId,
		    					row: {
		    						planEndTime: rmEnd
		    					}
		    				});
		    				tip('<spring:message code="com.glaway.ids.pm.project.plan.endNoEarlierThanStart"/>');
		    				return false;
		    			}
		    			
		    			if((roots[i].planEndTime > '${qualityPlanEndTime}') || (roots[i].planEndTime < '${qualityPlanStartTime}')){
		    				$('#planModifyList').treegrid('beginEdit', remremberId);
		    				editingId = remremberId;
		    				//"计划开始时间必须收敛于父级的计划时间或项目时间:"+parentStartTime+'~'+parentEndTime
		    				$("#planModifyList").treegrid('update',{
		    					id: remremberId,
		    					row: {
		    						planEndTime: rmEnd
		    					}
		    				});
		    				top.tip('<spring:message code="com.glaway.ids.pm.project.plan.startInParentTime" arguments="' +'${qualityPlanStartTime}'+'~'+'${qualityPlanEndTime}'+ '"/>');
		    				return false;
		    			}
						if((roots[i].planStartTime > '${qualityPlanEndTime}') || (roots[i].planStartTime < '${qualityPlanStartTime}')){
							$('#planModifyList').treegrid('beginEdit', remremberId);
							$("#planModifyList").treegrid('update',{
								id: remremberId,
								row: {
									planStartTime: rmStart
								}
							});
							editingId = remremberId;
							//"计划开始时间必须收敛于父级的计划时间或项目时间:"+parentStartTime+'~'+parentEndTime

							top.tip('<spring:message code="com.glaway.ids.pm.project.plan.startInParentTime" arguments="' +'${qualityPlanStartTime}'+'~'+'${qualityPlanEndTime}'+ '"/>');
							return false;
						}

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
		    					//里程碑的工期可以为0
		    					if(roots[i].milestone == "否") {
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
		    					}
		    					
		    				}
						var child2 = $("#planModifyList").treegrid("getChildren",remremberId);
				    	if(child2.length > 0){
					    	for (var j = 0; j < child2.length; j++) {
					    			if((child2[j].planEndTime > roots[i].planEndTime) || (child2[j].planStartTime < roots[i].planStartTime)){
					    				$('#planModifyList').treegrid('beginEdit', remremberId);
					    				editingId = remremberId;
					    				$("#planModifyList").treegrid('update',{
					    					id: remremberId,
					    					row: {
					    						planEndTime: rmEnd
					    					}
					    				});
					    				$("#planModifyList").treegrid('update',{
					    					id: remremberId,
					    					row: {
					    						planStartTime: rmStart
					    					}
					    				});
					    				top.tip('子计划时间必须收敛于父级计划时间');
					    				return false;
					    			}
									if(child2[j].workTime>roots[i].workTime) {
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
				    	}
		    			
		    			// ajax_!!
						$.ajax({
							url : 'planController.do?getAfterTime',
							type : 'post',
							data : {
								id : roots[i].id,
								preposeEndTime : roots[i].planEndTime
							},
							cache : false,
							success : function(data) {
								debugger;
								if (data != null) {
									var d = $.parseJSON(data);
									if(d.success == true){
										preposeEndTime = d.obj;
										ids = d.msg;
										var idsLst = ids.split(',');
										for(var i=0;i<idsLst.length;i++){
											if(i == idsLst.length-1){
												var b = idsLst[i].split('<br/>');
												idsLst[i] = b[0];
											}
											var rowss = $('#planModifyList').treegrid('find',idsLst[i]);
											if(rowss != null){
												changes.push(idsLst[i]);
												var days = dateCompare(getDateByFmtStr(rowss.planStartTime), getDateByFmtStr(rowss.planEndTime)) + 1;
												var projectId = roots[i].projectId;
					    						$.ajax({
					    							url : 'planController.do?getWork',
					    							type : 'post',
					    							data : {
					    								projectId : projectId,
					    								planStartTime :rowss.planStartTime,
					    								planEndTime : rowss.planEndTime
					    							},
					    							cache : false,
					    							success : function(data) {
					    								if (data != null) {
					    									var d = $.parseJSON(data);
					    									var a = d.msg.indexOf("获取计划工期成功");
					    									if(d.success){
					    										days = d.obj;
					    									}
					    									else{
					    									}
					    								}
					    							}
					    						});
					    						var rowss3 = $('#planModifyList').treegrid('find',idsLst[i]);
					    						debugger;
					    						if(rowss3.planEndTime < preposeEndTime){
									    			$('#planModifyList').treegrid('update',{
									    				id: idsLst[i],
									    				row: {
									    					planStartTime: preposeEndTime,
									    					planEndTime:preposeEndTime,
									    					workTime : days
									    				}
									    			});
					    						}else{
									    			$('#planModifyList').treegrid('update',{
									    				id: idsLst[i],
									    				row: {
									    					planStartTime: preposeEndTime,
									    					workTime : days
									    				}
									    			});
					    						}
											}
										}
									}
									if(d.success == true){
										debugger;
										var rowss2 = $('#planModifyList').treegrid('find',remremberId);
						    			var dayNum = dateCompare(getDateByFmtStr(rowss2.planStartTime), getDateByFmtStr(rowss2.planEndTime)) + 1;
						    			//var planStartTime = getDateByFmtStr(rowss2.planStartTime);
						    			var planStartTime = rowss2.planStartTime;
						    			var projectId = rowss2.projectId;
						    			var planEndTime = rowss2.planEndTime;
						    			var workTime = rowss2.workTime;
						    			// label_2
						    			if(planEndTime != null && planEndTime != '' && planEndTime != undefined){
						    				if(planStartTime != null && planStartTime != '' && planStartTime != undefined){
						    					// label_1
						    					if(planEndTime < planStartTime){
						    						$('#planModifyList').treegrid('update',{
						    							id: remremberId,
						    							row: {
						    								planStartTime: rmStart,
						    								planEndTime: rmEnd
						    							}
						    						});
						    						tip('<spring:message code="com.glaway.ids.pm.project.plan.startNoLaterThanEnd"/>');
						    						return false;
						    					}else{
						    						
						    						//to
						    						if(rmTime == workTime && rmEnd != planEndTime){// work
						    							$.ajax({
							    							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
							    							type : 'post',
							    							data : {
							    								projectId : projectId,
							    								planStartTime : rowss2.planStartTime,
							    								planEndTime : rowss2.planEndTime
							    							},
							    							cache : false,
							    							success : function(data) {
							    								if (data != null) {
							    									var d = $.parseJSON(data);
							    									var a = d.msg.indexOf("获取计划工期成功");
							    									if(d.success){
							    										dayNum = d.obj;
							    										if(dayNum == 0){
							    											dayNum = dayNum;
							    											tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
							    										}
							    										else{
							    											dayNum = dayNum;
							    							    			$('#planModifyList').treegrid('update',{
							    							    				id: remremberId,
							    							    				row: {
							    							    					workTime: dayNum
							    							    				}
							    							    			});
							    										}
							    									}
							    									else{
							    									}
							    								}
							    								else{
							    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
							    								}
							    							}
							    						});
						    						}else{// end
						    							$.ajax({
							    							url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
							    							type : 'post',
							    							data : {
							    								projectId : projectId,
							    								planStartTime : rowss2.planStartTime,
							    								workTime : rowss2.workTime
							    							},
							    							cache : false,
							    							success : function(data) {
							    								if (data != null) {
							    									var d = $.parseJSON(data);
							    									var a = d.msg.indexOf("获取计划工期成功");
							    									debugger;
							    									if(d.success){
							    										dayNum = d.obj;
							    										if(dayNum == 0){
							    											dayNum = dayNum;
							    											tip(d.msg);
							    										}
							    										else{
							    											dayNum = dayNum;
							    							    			$('#planModifyList').treegrid('update',{
							    							    				id: remremberId,
							    							    				row: {
							    							    					planEndTime: d.obj
							    							    				}
							    							    			});
							    										}
							    									}
							    									else{
							    									}
							    								}
							    								else{
							    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
							    								}
							    							}
							    						});
						    						}
						    						//to
						    						/* $.ajax({
						    							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
						    							type : 'post',
						    							data : {
						    								projectId : projectId,
						    								planStartTime : rowss2.planStartTime,
						    								planEndTime : rowss2.planEndTime
						    							},
						    							cache : false,
						    							success : function(data) {
						    								if (data != null) {
						    									var d = $.parseJSON(data);
						    									var a = d.msg.indexOf("获取计划工期成功");
						    									if(d.success){
						    										dayNum = d.obj;
						    										if(dayNum == 0){
						    											dayNum = dayNum;
						    											tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
						    										}
						    										else{
						    											dayNum = dayNum;
						    							    			$('#planModifyList').treegrid('update',{
						    							    				id: remremberId,
						    							    				row: {
						    							    					workTime: dayNum
						    							    				}
						    							    			});
						    										}
						    									}
						    									else{
						    									}
						    								}
						    								else{
						    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
						    								}
						    							}
						    						}); */
						    					// label_1
						    					}
						    				}else{
						    					tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
						    				}
						    			// label_2	
						    			}
						    				var p = parseInt(roots[i].persons);
						    				if (!isNaN(p)) {
						    					persons += p;
						    				}
						    				var frow = t.treegrid('getFooterRows')[0];
						    				frow.persons = persons;
						    				t.treegrid('reloadFooter');
									}
									if(d.success == false){
										var rowss2 = $('#planModifyList').treegrid('find',remremberId);
						    			var dayNum = dateCompare(getDateByFmtStr(rowss2.planStartTime), getDateByFmtStr(rowss2.planEndTime)) + 1;
						    			var planStartTime = rowss2.planStartTime;
						    			var projectId = rowss2.projectId;
						    			var planEndTime = rowss2.planEndTime;
						    			var workTime = rowss2.workTime;
						    			if(planEndTime != null && planEndTime != '' && planEndTime != undefined){
						    				if(planStartTime != null && planStartTime != '' && planStartTime != undefined){
						    					if(planEndTime < planStartTime){
						    						$('#planModifyList').treegrid('update',{
						    							id: remremberId,
						    							row: {
						    								planStartTime: rmStart,
						    								planEndTime: rmEnd
						    							}
						    						});
						    						tip('<spring:message code="com.glaway.ids.pm.project.plan.startNoLaterThanEnd"/>');
						    						return false;
						    					}else{
						    						//to
						    						if(rmTime == workTime && rmEnd != planEndTime){// work
						    							$.ajax({
							    							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
							    							type : 'post',
							    							data : {
							    								projectId : projectId,
							    								planStartTime : rowss2.planStartTime,
							    								planEndTime : rowss2.planEndTime
							    							},
							    							cache : false,
							    							success : function(data) {
							    								if (data != null) {
							    									var d = $.parseJSON(data);
							    									var a = d.msg.indexOf("获取计划工期成功");
							    									if(d.success){
							    										dayNum = d.obj;
							    										if(dayNum == 0){
							    											dayNum = dayNum;
							    											tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
							    										}
							    										else{
							    											dayNum = dayNum;
							    							    			$('#planModifyList').treegrid('update',{
							    							    				id: remremberId,
							    							    				row: {
							    							    					workTime: dayNum
							    							    				}
							    							    			});
							    										}
							    									}
							    									else{
							    									}
							    								}
							    								else{
							    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
							    								}
							    							}
							    						});
						    						}else{// end
						    							$.ajax({
							    							url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
							    							type : 'post',
							    							data : {
							    								projectId : projectId,
							    								planStartTime : rowss2.planStartTime,
							    								workTime : rowss2.workTime
							    							},
							    							cache : false,
							    							success : function(data) {
							    								if (data != null) {
							    									var d = $.parseJSON(data);
							    									var a = d.msg.indexOf("获取计划工期成功");
							    									debugger;
							    									if(d.success){
							    										dayNum = d.obj;
							    										if(dayNum == 0){
							    											dayNum = dayNum;
							    											tip(d.msg);
							    										}
							    										else{
							    											dayNum = dayNum;
							    							    			$('#planModifyList').treegrid('update',{
							    							    				id: remremberId,
							    							    				row: {
							    							    					planEndTime: d.obj
							    							    				}
							    							    			});
							    										}
							    									}
							    									else{
							    									}
							    								}
							    								else{
							    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
							    								}
							    							}
							    						});
						    						}
						    						//to
						    						/* $.ajax({
						    							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
						    							type : 'post',
						    							data : {
						    								projectId : projectId,
						    								planStartTime : rowss2.planStartTime,
						    								planEndTime : rowss2.planEndTime
						    							},
						    							cache : false,
						    							success : function(data) {
						    								if (data != null) {
						    									var d = $.parseJSON(data);
						    									var a = d.msg.indexOf("获取计划工期成功");
						    									if(d.success){
						    										dayNum = d.obj;
						    										if(dayNum == 0){
						    											dayNum = dayNum;
						    											tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
						    										}
						    										else{
						    											dayNum = dayNum;
						    							    			$('#planModifyList').treegrid('update',{
						    							    				id: remremberId,
						    							    				row: {
						    							    					workTime: dayNum
						    							    				}
						    							    			});
						    										}
						    									}
						    									else{
						    									}
						    								}
						    								else{
						    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
						    								}
						    							}
						    						}); */
						    					}
						    				}else{
						    					tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
						    				}
						    			}
						    				var p = parseInt(roots[i].persons);
						    				if (!isNaN(p)) {
						    					persons += p;
						    				}
						    				var frow = t.treegrid('getFooterRows')[0];
						    				frow.persons = persons;
						    				t.treegrid('reloadFooter');
									}
								}
								else{
								}
							}
						// ajax_!!	
						});
                      break;
		    		}
					
					// 00000 TODO child
					var child = $("#planModifyList").treegrid("getChildren",roots[i].id);
			    	if(child.length > 0){
				    	for (var j = 0; j < child.length; j++) {
				    		if(child[j].id == remremberId){
				    			if(child[j].planStartTime == ""){
				    				$('#planModifyList').treegrid('beginEdit', remremberId);
				    				editingId = remremberId;
				    				$('#planModifyList').treegrid('update',{
		    							id: remremberId,
		    							row: {
		    								planStartTime: rmStart
		    							}
		    						});
				    				tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
				    				return false;
				    			}
				    			
				    			if(child[j].planEndTime == ""){
				    				$('#planModifyList').treegrid('beginEdit', remremberId);
				    				$('#planModifyList').treegrid('update',{
		    							id: remremberId,
		    							row: {
		    								planEndTime: rmEnd
		    							}
		    						});
				    				editingId = remremberId;
				    				tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyEnd"/>');
				    				return false;
				    			}
				    			
				    			if(child[j].planEndTime < child[j].planStartTime){
				    				$('#planModifyList').treegrid('beginEdit', remremberId);
				    				editingId = remremberId;
				    				$('#planModifyList').treegrid('update',{
		    							id: remremberId,
		    							row: {
		    								planStartTime: rmStart,
		    								planEndTime: rmEnd
		    							}
		    						});
				    				tip('<spring:message code="com.glaway.ids.pm.project.plan.endNoEarlierThanStart"/>');
				    				return false;
				    			}
				    			
				    			if((child[j].planEndTime > '${qualityPlanEndTime}') || (child[j].planStartTime < '${qualityPlanStartTime}')){
				    				$('#planModifyList').treegrid('beginEdit', remremberId);
				    				editingId = remremberId;
				    				$('#planModifyList').treegrid('update',{
		    							id: remremberId,
		    							row: {
		    								planStartTime: rmStart,
		    								planEndTime: rmEnd
		    							}
		    						});
				    				//"计划开始时间必须收敛于父级的计划时间或项目时间:"+parentStartTime+'~'+parentEndTime
				    				top.tip('<spring:message code="com.glaway.ids.pm.project.plan.startInParentTime" arguments="' +'${qualityPlanStartTime}'+'~'+'${qualityPlanEndTime}'+ '"/>');
				    				return false;
				    			}
				    			
				    			if((child[j].planEndTime > child[j].parentPlan.planEndTime) || (child[j].planStartTime < child[j].parentPlan.planStartTime)){
				    				$('#planModifyList').treegrid('beginEdit', remremberId);
				    				editingId = remremberId;
				    				$('#planModifyList').treegrid('update',{
		    							id: remremberId,
		    							row: {
		    								planStartTime: rmStart,
		    								planEndTime: rmEnd
		    							}
		    						});
				    				top.tip('计划时间必须收敛于父级计划【'+ child[j].parentPlan.planStartTime + '~'+ child[j].parentPlan.planEndTime +'】的时间');
				    				return false;
				    			}
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
			    				}else{
			    					var reg = new RegExp("^[1-9][1-9]{0,3}$");
			    					//里程碑的工期可以为0
	                                if(roots[i].milestone == "否") {
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
	                                }
			    				}
				    			
				    			
								$.ajax({
									url : 'planController.do?getAfterTime',
									type : 'post',
									data : {
										id : child[j].id,
										preposeEndTime : child[j].planEndTime
									},
									cache : false,
									success : function(data) {
										if (data != null) {
											var d = $.parseJSON(data);
											if(d.success == true){
												preposeEndTime = d.obj;
												ids = d.msg;
												var idsLst = ids.split(',');
												for(var i=0;i<idsLst.length;i++){
													if(i == idsLst.length-1){
														var b = idsLst[i].split('<br/>');
														idsLst[i] = b[0];
													}
													var rowss = $('#planModifyList').treegrid('find',idsLst[i]);
													if(rowss != null){
														changes.push(idsLst[i]);
														var days = dateCompare(getDateByFmtStr(rowss.planStartTime), getDateByFmtStr(rowss.planEndTime)) + 1;
														var projectId = child[j].projectId;
							    						$.ajax({
							    							url : 'planController.do?getWork',
							    							type : 'post',
							    							data : {
							    								projectId : projectId,
							    								planStartTime :rowss.planStartTime,
							    								planEndTime : rowss.planEndTime
							    							},
							    							cache : false,
							    							success : function(data) {
							    								if (data != null) {
							    									var d = $.parseJSON(data);
							    									var a = d.msg.indexOf("获取计划工期成功");
							    									if(d.success){
							    										days = d.obj;
							    									}
							    									else{
							    									}
							    								}
							    							}
							    						});
							    						
							    						var rowss3 = $('#planModifyList').treegrid('find',idsLst[i]);
							    						if(rowss3.planEndTime < preposeEndTime){
											    			$('#planModifyList').treegrid('update',{
											    				id: idsLst[i],
											    				row: {
											    					planStartTime: preposeEndTime,
											    					planEndTime:preposeEndTime,
											    					workTime : days
											    				}
											    			});
							    						}else{
											    			$('#planModifyList').treegrid('update',{
											    				id: idsLst[i],
											    				row: {
											    					planStartTime: preposeEndTime,
											    					workTime : days
											    				}
											    			});
							    						}

													}
												}
												
												
												
											}
											if(d.success == true){
												var rowss2 = $('#planModifyList').treegrid('find',remremberId);
								    			var dayNum = dateCompare(getDateByFmtStr(rowss2.planStartTime), getDateByFmtStr(rowss2.planEndTime)) + 1;
								    			var planStartTime = rowss2.planStartTime;
								    			var projectId = rowss2.projectId;
								    			var planEndTime = rowss2.planEndTime;
								    			var workTime = rowss2.workTime;
								    			if(planEndTime != null && planEndTime != '' && planEndTime != undefined){
								    				if(planStartTime != null && planStartTime != '' && planStartTime != undefined){
								    					if(planEndTime < planStartTime){
								    						$('#planModifyList').treegrid('update',{
								    							id: remremberId,
								    							row: {
								    								planStartTime: rmStart,
								    								planEndTime: rmEnd
								    							}
								    						});
								    						tip('<spring:message code="com.glaway.ids.pm.project.plan.startNoLaterThanEnd"/>');
								    						return false;
								    					}else{
								    						//to
								    						if(rmTime == workTime && rmEnd != planEndTime){// work
								    							$.ajax({
									    							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
									    							type : 'post',
									    							data : {
									    								projectId : projectId,
									    								planStartTime : rowss2.planStartTime,
									    								planEndTime : rowss2.planEndTime
									    							},
									    							cache : false,
									    							success : function(data) {
									    								if (data != null) {
									    									var d = $.parseJSON(data);
									    									var a = d.msg.indexOf("获取计划工期成功");
									    									if(d.success){
									    										dayNum = d.obj;
									    										if(dayNum == 0){
									    											dayNum = dayNum;
									    											tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
									    										}
									    										else{
									    											dayNum = dayNum;
									    							    			$('#planModifyList').treegrid('update',{
									    							    				id: remremberId,
									    							    				row: {
									    							    					workTime: dayNum
									    							    				}
									    							    			});
									    										}
									    									}
									    									else{
									    									}
									    								}
									    								else{
									    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
									    								}
									    							}
									    						});
								    						}else{// end
								    							$.ajax({
									    							url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
									    							type : 'post',
									    							data : {
									    								projectId : projectId,
									    								planStartTime : rowss2.planStartTime,
									    								workTime : rowss2.workTime
									    							},
									    							cache : false,
									    							success : function(data) {
									    								if (data != null) {
									    									var d = $.parseJSON(data);
									    									var a = d.msg.indexOf("获取计划工期成功");
									    									debugger;
									    									if(d.success){
									    										dayNum = d.obj;
									    										if(dayNum == 0){
									    											dayNum = dayNum;
									    											tip(d.msg);
									    										}
									    										else{
									    											dayNum = dayNum;
									    							    			$('#planModifyList').treegrid('update',{
									    							    				id: remremberId,
									    							    				row: {
									    							    					planEndTime: d.obj
									    							    				}
									    							    			});
									    										}
									    									}
									    									else{
									    									}
									    								}
									    								else{
									    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
									    								}
									    							}
									    						});
								    						}
								    						//to
								    						/* $.ajax({
								    							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
								    							type : 'post',
								    							data : {
								    								projectId : projectId,
								    								planStartTime : rowss2.planStartTime,
								    								planEndTime : rowss2.planEndTime
								    							},
								    							cache : false,
								    							success : function(data) {
								    								if (data != null) {
								    									var d = $.parseJSON(data);
								    									var a = d.msg.indexOf("获取计划工期成功");
								    									if(d.success){
								    										dayNum = d.obj;
								    										if(dayNum == 0){
								    											dayNum = dayNum;
								    											tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
								    										}
								    										else{
								    											dayNum = dayNum;
								    							    			$('#planModifyList').treegrid('update',{
								    							    				id: remremberId,
								    							    				row: {
								    							    					workTime: dayNum
								    							    				}
								    							    			});
								    										}
								    									}
								    									else{
								    									}
								    								}
								    								else{
								    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
								    								}
								    							}
								    						}); */
								    					}
								    				}else{
								    					tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
								    				}
								    			}
								    				var p = parseInt(child[j].persons);
								    				if (!isNaN(p)) {
								    					persons += p;
								    				}
								    				var frow = t.treegrid('getFooterRows')[0];
								    				frow.persons = persons;
								    				t.treegrid('reloadFooter');
											}
											if(d.success == false){
												var rowss2 = $('#planModifyList').treegrid('find',remremberId);
								    			var dayNum = dateCompare(getDateByFmtStr(rowss2.planStartTime), getDateByFmtStr(rowss2.planEndTime)) + 1;
								    			var planStartTime = rowss2.planStartTime;
								    			var projectId = rowss2.projectId;
								    			var planEndTime = rowss2.planEndTime;
								    			var workTime = rowss2.workTime;
								    			
								    			if(planEndTime != null && planEndTime != '' && planEndTime != undefined){
								    				if(planStartTime != null && planStartTime != '' && planStartTime != undefined){
								    					if(planEndTime < planStartTime){
								    						$('#planModifyList').treegrid('update',{
								    							id: remremberId,
								    							row: {
								    								planStartTime: rmStart,
								    								planEndTime: rmEnd
								    							}
								    						});
								    						tip('<spring:message code="com.glaway.ids.pm.project.plan.startNoLaterThanEnd"/>');
								    						return false;
								    					}else{
								    						//to
								    						if(rmTime == workTime && rmEnd != planEndTime){// work
								    							$.ajax({
									    							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
									    							type : 'post',
									    							data : {
									    								projectId : projectId,
									    								planStartTime : rowss2.planStartTime,
									    								planEndTime : rowss2.planEndTime
									    							},
									    							cache : false,
									    							success : function(data) {
									    								if (data != null) {
									    									var d = $.parseJSON(data);
									    									var a = d.msg.indexOf("获取计划工期成功");
									    									if(d.success){
									    										dayNum = d.obj;
									    										if(dayNum == 0){
									    											dayNum = dayNum;
									    											tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
									    										}
									    										else{
									    											dayNum = dayNum;
									    							    			$('#planModifyList').treegrid('update',{
									    							    				id: remremberId,
									    							    				row: {
									    							    					workTime: dayNum
									    							    				}
									    							    			});
									    										}
									    									}
									    									else{
									    									}
									    								}
									    								else{
									    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
									    								}
									    							}
									    						});
								    						}else{// end
								    							$.ajax({
									    							url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
									    							type : 'post',
									    							data : {
									    								projectId : projectId,
									    								planStartTime : rowss2.planStartTime,
									    								workTime : rowss2.workTime
									    							},
									    							cache : false,
									    							success : function(data) {
									    								if (data != null) {
									    									var d = $.parseJSON(data);
									    									var a = d.msg.indexOf("获取计划工期成功");
									    									debugger;
									    									if(d.success){
									    										dayNum = d.obj;
									    										if(dayNum == 0){
									    											dayNum = dayNum;
									    											tip(d.msg);
									    										}
									    										else{
									    											dayNum = dayNum;
									    							    			$('#planModifyList').treegrid('update',{
									    							    				id: remremberId,
									    							    				row: {
									    							    					planEndTime: d.obj
									    							    				}
									    							    			});
									    										}
									    									}
									    									else{
									    									}
									    								}
									    								else{
									    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
									    								}
									    							}
									    						});
								    						}
								    						//to
								    						/* $.ajax({
								    							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
								    							type : 'post',
								    							data : {
								    								projectId : projectId,
								    								planStartTime : rowss2.planStartTime,
								    								planEndTime : rowss2.planEndTime
								    							},
								    							cache : false,
								    							success : function(data) {
								    								if (data != null) {
								    									var d = $.parseJSON(data);
								    									var a = d.msg.indexOf("获取计划工期成功");
								    									if(d.success){
								    										dayNum = d.obj;
								    										if(dayNum == 0){
								    											dayNum = dayNum;
								    											tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
								    										}
								    										else{
								    											dayNum = dayNum;
								    							    			$('#planModifyList').treegrid('update',{
								    							    				id: remremberId,
								    							    				row: {
								    							    					workTime: dayNum
								    							    				}
								    							    			});
								    										}
								    									}
								    									else{
								    									}
								    								}
								    								else{
								    									tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
								    								}
								    							}
								    						}); */
								    					}
								    				}else{
								    					tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
								    				}
								    			}
								    				var p = parseInt(child[j].persons);
								    				if (!isNaN(p)) {
								    					persons += p;
								    				}
								    				var frow = t.treegrid('getFooterRows')[0];
								    				frow.persons = persons;
								    				t.treegrid('reloadFooter');
											}
										}
										else{
										}
									}
								});
		                      break;
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
		if(row.bizCurrent != "EDITING" || row.flowStatus != "NORMAL"){
			return '<a href="#" onclick="viewPlan(\'' + row.id
			+ '\')" style="color:gray">' + row.planName + '</a>';
		}else{
			return '<a href="#" onclick="viewPlan(\'' + row.id
			+ '\')" style="color:blue">' + row.planName + '</a>';
		}
		
	}
		
	// 查看计划信息
	function viewPlan(id) {
		var dialogUrl = 'planController.do?goCheck&id=' + id;
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
		debugger;
		if(newValue != undefined){
		var resultVal = newValue;
		var resultName;
		var planLevelList = eval($('#planLevelList').val());
		var aa = '1';
		for (var i = 0; i < planLevelList.length; i++) {
			if (newValue == planLevelList[i].id) {
				resultName = planLevelList[i].name;
				resultVal = planLevelList[i].id;
				aa = '0';
			}
			if (newValue == planLevelList[i].name) {
				resultName = planLevelList[i].name;
				resultVal = planLevelList[i].id;
				aa = '0';
			}
		}
		if(aa == '1'){
			$(this).combobox('setValue',"");	
		}else{
			$(this).combobox('setValue',resultVal);
			$(this).combobox('setText',resultName);
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
		    			if(roots[i].planStartTime == ""){
		    				tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyStart"/>');
		    				edit2()
		    				return false;
		    			}
		    			
		    			if(roots[i].planEndTime == ""){
		    				tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyEnd"/>');
		    				edit2()
		    				return false;
		    			}
		    			
		    			if(roots[i].planEndTime < roots[i].planStartTime){
		    				tip('<spring:message code="com.glaway.ids.pm.project.plan.endNoEarlierThanStart"/>');
		    				edit2()
		    				return false;
		    			}
		    			
		    			if((roots[i].planEndTime > '${qualityPlanEndTime}') || (roots[i].planStartTime < '${qualityPlanStartTime}')){
		    				//"计划开始时间必须收敛于父级的计划时间或项目时间:"+parentStartTime+'~'+parentEndTime
		    				top.tip("计划开始时间必须收敛于父级的计划时间或项目时间:"+'${qualityPlanStartTime}'+'~'+'${qualityPlanEndTime}');
		    				edit2()
		    				return false;
		    			}
		    			
		    			
						var child2 = $("#planModifyList").treegrid("getChildren",remremberId);
				    	if(child2.length > 0){
					    	for (var j = 0; j < child2.length; j++) {
					    			if((child2[j].planEndTime > roots[i].planEndTime) || (child2[j].planStartTime < roots[i].planStartTime)){
					    				$('#planModifyList').treegrid('beginEdit', remremberId);
					    				editingId = remremberId;
					    				top.tip('子计划时间必须收敛于父级计划时间');
					    				return false;
					    			}
		
					    	}
				    	}
		    					    						    			
						$.ajax({
							url : 'planController.do?getAfterTime',
							type : 'post',
							data : {
								id : roots[i].id,
								preposeEndTime : roots[i].planEndTime
							},
							cache : false,
							success : function(data) {
								if (data != null) {
									var d = $.parseJSON(data);
									if(d.success == true){
										preposeEndTime = d.obj;
										ids = d.msg;
										var idsLst = ids.split(',');
										for(var i=0;i<idsLst.length;i++){
											if(i == idsLst.length-1){
												var b = idsLst[i].split('<br/>');
												idsLst[i] = b[0];
											}
											var rowss = $('#planModifyList').treegrid('find',idsLst[i]);
											if(rowss != null){
												changes.push(idsLst[i]);
												var days = dateCompare(getDateByFmtStr(rowss.planStartTime), getDateByFmtStr(rowss.planEndTime)) + 1;
												var projectId = roots[i].projectId;
					    						$.ajax({
					    							url : 'planController.do?getWork',
					    							type : 'post',
					    							data : {
					    								projectId : projectId,
					    								planStartTime :rowss.planStartTime,
					    								planEndTime : rowss.planEndTime
					    							},
					    							cache : false,
					    							success : function(data) {
					    								if (data != null) {
					    									var d = $.parseJSON(data);
					    									var a = d.msg.indexOf("获取计划工期成功");
					    									if(d.success){
					    										days = d.obj;
					    									}
					    									else{
					    									}
					    								}
					    								else{
					    								}
					    							}
					    						});
								    			$('#planModifyList').treegrid('update',{
								    				id: idsLst[i],
								    				row: {
								    					planStartTime: preposeEndTime,
								    					workTime : days
								    				}
								    			});
											}
										}
									}
									
									saveModifyList();
									if(d.success == false){
						    			$('#planModifyList').treegrid('update',{
						    				id: remremberId,
						    				row: {
			    								planStartTime : rmStart,
			    								planEndTime : rmEnd
						    				}
						    			});
										tip(d.msg);
									}
								}
								else{
								}
							}
						});
                      break;
		    		}
					
					var child = $("#planModifyList").treegrid("getChildren",roots[i].id);
			    	if(child.length > 0){
				    	for (var j = 0; j < child.length; j++) {
				    		if(child[j].id == remremberId){
				    			if(child[j].planStartTime == ""){
				    				$('#planModifyList').treegrid('beginEdit', remremberId);
				    				editingId = remremberId;
				    				tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyStart"/>');
				    				return false;
				    			}
				    			
				    			if(child[j].planStartTime == ""){
				    				$('#planModifyList').treegrid('beginEdit', remremberId);
				    				editingId = remremberId;
				    				tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyEnd"/>');
				    				return false;
				    			}
				    			
				    			if(child[j].planEndTime < child[j].planStartTime){
				    				$('#planModifyList').treegrid('beginEdit', remremberId);
				    				editingId = remremberId;
				    				tip('<spring:message code="com.glaway.ids.pm.project.plan.endNoEarlierThanStart"/>');
				    				return false;
				    			}
				    			
				    			if((child[j].planEndTime > '${qualityPlanEndTime}') || (child[j].planStartTime < '${qualityPlanStartTime}')){
				    				$('#planModifyList').treegrid('beginEdit', remremberId);
				    				editingId = remremberId;
				    				//"计划开始时间必须收敛于父级的计划时间或项目时间:"+parentStartTime+'~'+parentEndTime
				    				top.tip('<spring:message code="com.glaway.ids.pm.project.plan.startInParentTime" arguments="' +'${qualityPlanStartTime}'+'~'+'${qualityPlanEndTime}'+ '"/>');
				    				return false;
				    			}
				    			
				    			if((child[j].planEndTime > child[j].parentPlan.planEndTime) || (child[j].planStartTime < child[j].parentPlan.planStartTime)){
				    				$('#planModifyList').treegrid('beginEdit', remremberId);
				    				editingId = remremberId;
				    				top.tip('计划时间必须收敛于父级计划【'+ child[j].parentPlan.planStartTime + '~'+ child[j].parentPlan.planEndTime +'】的时间');
				    				return false;
				    			}
				    			
								$.ajax({
									url : 'planController.do?getAfterTime',
									type : 'post',
									data : {
										id : child[j].id,
										preposeEndTime : child[j].planEndTime
									},
									cache : false,
									success : function(data) {
										if (data != null) {
											var d = $.parseJSON(data);
											if(d.success == true){
												preposeEndTime = d.obj;
												ids = d.msg;
												var idsLst = ids.split(',');
												
												for(var i=0;i<idsLst.length;i++){
													
													if(i == idsLst.length-1){
														var b = idsLst[i].split('<br/>');
														idsLst[i] = b[0];
													}
													
													var rowss = $('#planModifyList').treegrid('find',idsLst[i]);
													
													if(rowss != null){
														changes.push(idsLst[i]);
														var days = dateCompare(getDateByFmtStr(rowss.planStartTime), getDateByFmtStr(rowss.planEndTime)) + 1;
														var projectId = child[j].projectId;
							    						$.ajax({
							    							url : 'planController.do?getWork',
							    							type : 'post',
							    							data : {
							    								projectId : projectId,
							    								planStartTime :rowss.planStartTime,
							    								planEndTime : rowss.planEndTime
							    							},
							    							cache : false,
							    							success : function(data) {
							    								if (data != null) {
							    									var d = $.parseJSON(data);
							    									var a = d.msg.indexOf("获取计划工期成功");
							    									if(d.success){
							    										days = d.obj;
							    									}
							    									else{
							    									}
							    								}
							    								else{
							    								}
							    							}
							    						});
														
										    			$('#planModifyList').treegrid('update',{
										    				id: idsLst[i],
										    				row: {
										    					planStartTime: preposeEndTime,
										    					workTime : days
										    				}
										    			});
													}
												}
											}
											
											saveModifyList();
											
											if(d.success == false){
								    			$('#planModifyList').treegrid('update',{
								    				id: remremberId,
								    				row: {
					    								planStartTime : rmStart,
					    								planEndTime : rmEnd
								    				}
								    			});
												tip(d.msg);
											}
										}
										else{
										}
									}
								});
		                      break;
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

