<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划批量变更</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>

<script>
		window.onload = function(){
			setTimeout(function(){
				$("#planChangeSelectList").treegrid({
					onClickRow:edit,
					onDblClickRow:edit2
				});
				begin();
			},500);
		}
		
		function begin() {
			/* $('#changeReason').panel({closed:false}); */
			$('#changeReason').show();
			$('#changeInfo').panel({closed:true});
			$('#changeAnalysis').panel({closed:true});
			$('#changeTotal').panel({closed:true});
			$('#changeVo').panel({closed:true});
			$('#focusOne').focus();
		}
		
		function nextFour() {
			/* $('#changeReason').panel({closed:true}); */
			$('#changeReason').hide();
			$('#changeInfo').panel({closed:true});
			$('#changeAnalysis').panel({closed:true});
			$('#changeTotal').panel({closed:true});
			$('#changeVo').panel({closed:false});
			initChangeVo();
		}
		

		function nextOne() {
			//var changeType = $('#changeType').combobox('getValue');
			var changeType = $('#changeType').combotree('getValues');
			var win = $.fn.lhgdialog("getSelectParentWin");
			var changeRemark = $('#changeRemark').val();
			if(changeType == ""){
				win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyChangeType"/>');
				return false;
			}
			if(changeRemark.length>200){
				win.tip('<spring:message code="com.glaway.ids.common.remarkLength"/>')
				return false;
			}
			if(!startChange()){
				return false;
			};
		}

		function nextTwo(){
			
			debugger;
			var win = $.fn.lhgdialog("getSelectParentWin");
			var t = $('#planChangeSelectList');
			t.treegrid('endEdit', editingId);
			
			$('#planChangeSelectList').treegrid('selectAll');
			var rows2 = $('#planChangeSelectList').treegrid('getSelections');
			var userList = eval($('#userList').val());
			if(rows2.length > 0){
				for(var ii = 0; ii < rows2.length; ii++){
					var aa = '1';
					for (var jj = 0; jj < userList.length; jj++) {
						if (rows2[ii].owner == userList[jj].id) {
							aa = '0';
						}
						if (rows2[ii].owner == userList[jj].realName) {
							aa = '0';
						}
					}
					if(aa == '1'){
						var a = ii +1;
						tip(rows2[ii].planName+'的负责人不能为空');
						return false;
					}
				}	
			}
			
			var rows = t.treegrid('getSelected');
			var startProjectTime = $('#startProjectTime').val();
			var endProjectTime = $('#endProjectTime').val();
			if(editingId != undefined){
				if(endProjectTime < rows.planEndTime){
					win.tip("计划【"+rows.planName+"】的时间需收敛于项目的时间"+startProjectTime+"~"+endProjectTime);
					return false;
				}
				
				if(rows.planStartTime < startProjectTime){
					win.tip("计划【"+rows.planName+"】的时间需收敛于项目的时间"+startProjectTime+"~"+endProjectTime);
					return false;
				}
				
				if(rows.planEndTime < rows.planStartTime){
					win.tip('<spring:message code="com.glaway.ids.pm.project.plan.endNoEarlierThanStartParam" arguments="' +rows.planName+ '"/>');
					return false;
				}
				
				if(rows.planStartTime == ""){
					win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
					return false;
				}
				
				if(rows.planEndTime == ""){
					win.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyEnd"/>');
					return false;
				}
				
				if(rows.planEndTime < rows.planStartTime){
					win.tip('<spring:message code="com.glaway.ids.pm.project.plan.endNoEarlierThanStartParam" arguments="' +rows.planName+ '"/>');
					return false;
				}
				var dayNum = dateCompare(getDateByFmtStr(rows.planStartTime), getDateByFmtStr(rows.planEndTime)) + 1;
				var planStartTime = getDateByFmtStr(rows.planStartTime);
				var planEndTime = getDateByFmtStr(rows.planEndTime);
				var projectId = rows.projectId;
				
				if(planEndTime != null && planEndTime != '' && planEndTime != undefined){
					if(planStartTime != null && planStartTime != '' && planStartTime != undefined){
						if(planEndTime < planStartTime){
							win.tip('<spring:message code="com.glaway.ids.pm.project.plan.startNoLaterThanEnd"/>');
						}else{
							$.ajax({
								url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
								type : 'post',
								data : {
									projectId : projectId,
									planStartTime : rows.planStartTime,
									planEndTime : rows.planEndTime
								},
								cache : false,
								success : function(data) {
									if (data != null) {
										var d = $.parseJSON(data);
										if(d.success == true){
											dayNum = d.obj;
											if(dayNum == 0){
												dayNum = dayNum;
												win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
											}
											else{
												dayNum = dayNum;
								    			$('#planChangeSelectList').treegrid('update',{
								    				id: rows.id,
								    				row: {
								    					workTime: dayNum
								    				}
								    			});
											}
										}
										else{
											win.tip(d.msg);
										}
									}
									else{
										win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
									}
								}
							});
						}
					}else{
						win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
					}
				}
			}
			acceptPlan();
		}
		
		function nextThree() {
			changeAnalysisInit();
		}
		
		
		
		
		
		function initChangeVo() {
			$.ajax({
				type : 'POST',
				url : 'planChangeMassController.do?planChangeMassVo',
				async : false,
				data : {
				},
				success : function(data) {
					$("#planChangeVo").datagrid("loadData",data);
					$('#focusFive').focus();
				} 
			});
		}

		function beforeTwo() {
			/* $('#changeReason').panel({closed:false}); */
			$('#changeReason').show();
			$('#changeInfo').panel({closed:true});
			$('#changeAnalysis').panel({closed:true});
			$('#changeTotal').panel({closed:true});
			$('#changeVo').panel({closed:true});
			$('#focusOne').focus();
		}

		function beforeThree() {
			$('#changeInfo').panel({closed:false});
			/* $('#changeReason').panel({closed:true}); */
			$('#changeReason').hide();
			$('#changeAnalysis').panel({closed:true});
			$('#changeTotal').panel({closed:true});
			$('#changeVo').panel({closed:true});
			$('#focusTwo').focus();
		}
		
		function beforeFour() {
			$('#changeAnalysis').panel({closed:false});
			$('#changeInfo').panel({closed:true});
			/* $('#changeReason').panel({closed:true}); */
			$('#changeReason').hide();
			$('#changeTotal').panel({closed:true});
			$('#changeVo').panel({closed:true});
			$('#focusThree').focus();
		}
		
		
		function beforeFive() {
			$('#changeAnalysis').panel({closed:true});
			$('#changeInfo').panel({closed:true});
			/* $('#changeReason').panel({closed:true}); */
			$('#changeReason').hide();
			$('#changeTotal').panel({closed:false});
			$('#changeVo').panel({closed:true});
			$('#focusFour').focus();
		}
		
		function initResourceChangeMass() {
			$.ajax({
				type : 'POST',
				url : 'planChangeMassController.do?resourceList',
				async : false,
				data : {},
				success : function(data) {
					debugger;
					var rows = data.rows;
					if(rows.length > 0){
						$('#resourceDiv').panel({closed:false});
						$('#fontDiv').panel({closed:true});
						$("#resourceList").datagrid("loadData",data);
						
						var merges= new Array();
						var rowspan = 0;
						var index = 0;
						var mergeCellVal = rows[0].useObjectId;
	 					for(var i=0; i<rows.length; i++){
	 						if(mergeCellVal != '' && mergeCellVal ==  rows[i].useObjectId){
	 							rowspan = rowspan + 1;
	 						}
	 						else{
	 							merges.push({
	 								index: index,
	 								rowspan: rowspan
	 							});
	 							mergeCellVal =  rows[i].useObjectId;
	 							rowspan = 1;
	 							index = i; 
	 						}
						}
	 					
	 					merges.push({
							index: index,
							rowspan: rowspan
						});
	 					
						for(var i =0;i<merges.length; i++){
							$("#resourceList").datagrid('mergeCells',{
								index: merges[i].index,
								field: 'planName',
								rowspan: merges[i].rowspan
							});
						}
						
					}
					else{
						$('#resourceDiv').panel({closed:true});
						//$("#fontDiv").attr("style","height: 500px");
						$('#fontDiv').panel({closed:false});
					}
				} 
			});
		}
		
		
		function start() {
			//var changeType = $('#changeType').combobox('getValue');
			var changeType = $('#changeType').combotree('getValues');
			var planChangeCategoryList = eval($('#planChangeCategoryListStr').val());
			for(var i=0;i<planChangeCategoryList.length;i++){
				if( changeType == planChangeCategoryList[i].id){
					$('#changeTypeAfter').val(planChangeCategoryList[i].name);
				}
			}
		}	
			function startChange() {
				var win = $.fn.lhgdialog("getSelectParentWin");
				//var rows = $("#planChangeList").treegrid("getSelections");
				var ids = planChangeList.getSelectedRowId();
				//var ids = [];
				if (ids != null && ids != '') {
					$('#changeReason').hide();
					$('#changeInfo').panel({closed:false});
					$('#changeAnalysis').panel({closed:true});
					$('#changeTotal').panel({closed:true});
					$('#changeVo').panel({closed:true});
					$('#focusTwo').focus();
			    	/* for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					} */
					setTimeout(function(){
						$.ajax({
							url : 'planChangeMassController.do?pdChangeAll&projectId'+$('#projectId').val(),
							type : 'post',
							data : {
								ids : ids
							},
							cache : false,
							async:false,
							success : function(data) {
								var d = $.parseJSON(data);
								if (d.success) {
									initChangeInfo();
								}
							}
						});
					},500);

					/* $('#changeReason').panel({closed:true}); */
			    }else{
			    	win.tip('<spring:message code="com.glaway.ids.pm.project.plan.selectChange"/>');
					return false;
			    }
			}
			
		
		function addTempPlan() {
				top.progress("open");
				var changeRemark = $('#changeRemark').val();
				//var changeType = $('#changeType').combobox('getValue');
				var changeType = $('#changeType').combotree('getValue');
				var win = $.fn.lhgdialog("getSelectParentWin");
				var leader = $('#leader').val();
				var deptLeader = $('#deptLeader').val();
				var leaderName = $('#leaderName').val();
				var deptLeaderName = $('#deptLeaderName').val();
				if(leaderName == ''){
					win.tip('<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
					top.progress("close");
					return false;
				}
				else if(deptLeaderName == ''){
					win.tip('<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
					top.progress("close");
					return false;
				}
				else{
					$.post('planChangeMassController.do?doSave', {
						'changeRemark' : changeRemark,
						'changeType' : changeType
					}, function(data) {
						startPlanChange();
						top.progress("close");
					});
				}
		}
		
	
		/**
		 * 选择前置计划页面
		 */
		function selectPreposePlan(hiddenId, textId) {
			var url = 'planController.do?goPlanPreposeTree';
			if ($('#projectId').val() != "") {
				url = url + '&projectId=' + $('#projectId').val();
			}
			if ($('#parentPlanId').val() != "") {
				url = url + '&parentPlanId=' + $('#parentPlanId').val();
			}
			if ($('#id').val() != "") {
				url = url + '&id=' + $('#id').val();
			}
			createPopupwindow('项目计划', url, '', '', hiddenId, textId);
		}

		/**
		 * 查看时的弹出窗口
		 */
		function createPopupwindow(title, url, width, height, hiddenId, textId) {
			width = width ? width : 650;
			height = height ? height : 400;
			if (width == "100%" || height == "100%") {
				width = document.body.offsetWidth;
				height = document.body.offsetHeight - 100;
			}
			$.fn.lhgdialog({
				content : 'url:' + url,
				lock : true,
				width : width,
				height : height,
				title : title,
				opacity : 0.3,
				cache : false
			});

		}

		/**
		 * 前置计划选择后，更新页面值（每次均覆盖）
		 */
		function savePreposePlan(preposeIds, preposePlans, preposeEndTime) {
			if (preposeIds != null && preposeIds != ''
					&& preposeIds != undefined) {
				$('#preposeIds').val(preposeIds);
			} else {
				$('#preposeIds').val('');
			}
			if (preposePlans != null && preposePlans != ''
					&& preposePlans != undefined) {
				$('#preposePlans').val(preposePlans);
			} else {
				$('#preposePlans').val('');
			}
			// 前置计划的最晚完成时间
			if (preposeEndTime != null && preposeEndTime != ''
					&& preposeEndTime != undefined) {
				$('#preposeEndTime').val(preposeEndTime);
			} else {
				$('#preposeEndTime').val('');
			}
		}

		
		function viewStartEndTime(val, row, value) {
			var start = row.startTime;
			var end = row.endTime;
			if((start != undefined && start !=null && start != '') 
				&& (end != undefined && end !=null && end != '')){
				return dateFmtYYYYMMDD(start) + "~" + dateFmtYYYYMMDD(end);
			}
			return "";
		}

	function changeAnalysisInit() {
		var win = $.fn.lhgdialog("getSelectParentWin");
			$.post('planChangeMassController.do?checkChildPostposeInfluenced',
				{}, 
				function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						if(msg == null || msg == '' || msg == undefined|| msg == '<br/>'){
							
							var showChildAnalysis = false;
							var showPostposeAnalysis = false;
							
							$.ajax({
								url : 'planChangeMassController.do?temporaryPlanChildList',
								type : 'post',
								data : {},
								cache : false,
								success : function(data) {
									$("#temporaryPlanChildList").datagrid("loadData",data);
									var rows = data.rows;
									if(rows.length > 0){
										var merges= new Array();
										var rowspan = 0;
										var index = 0;
										var mergeCellVal = rows[0].parentPlanId;
					 					for(var i=0; i<rows.length; i++){
					 						
					 						if(showChildAnalysis == false){
						 						if(rows[i].startTimeOverflow == true 
						 								|| rows[i].endTimeOverflow  == true){
						 							showChildAnalysis = true;
						 						}
					 						}
					 						
					 						if(mergeCellVal != '' && mergeCellVal ==  rows[i].parentPlanId){
					 							rowspan = rowspan + 1;
					 						}
					 						else{
					 							merges.push({
					 								index: index,
					 								rowspan: rowspan
					 							});
					 							mergeCellVal =  rows[i].parentPlanId;
					 							rowspan = 1;
					 							index = i; 
					 						}
										}
					 					
					 					merges.push({
											index: index,
											rowspan: rowspan
										});
					 					
										for(var i =0;i<merges.length; i++){
											$("#temporaryPlanChildList").datagrid('mergeCells',{
												index: merges[i].index,
												field: 'parentPlanName',
												rowspan: merges[i].rowspan
											});
										}
									}
									
									$.ajax({
										url : 'planChangeMassController.do?temporaryPlanPostposeList',
										type : 'post',
										data : {},
										cache : false,
										success : function(data) {
											$("#temporaryPlanPostposeList").datagrid("loadData",data);
											var rows = data.rows;
											if(rows.length > 0){
												var merges= new Array();
												var rowspan = 0;
												var index = 0;
												var mergeCellVal = rows[0].preposePlanId;
							 					for(var i=0; i<rows.length; i++){
							 						
							 						if(showPostposeAnalysis == false){
								 						if(rows[i].startTimeOverflow == true 
								 								|| rows[i].endTimeOverflow  == true){
								 							showPostposeAnalysis = true;
								 						}
							 						}
							 						
							 						if(mergeCellVal != '' && mergeCellVal ==  rows[i].preposePlanId){
							 							rowspan = rowspan + 1;
							 						}
							 						else{
							 							merges.push({
							 								index: index,
							 								rowspan: rowspan
							 							});
							 							mergeCellVal =  rows[i].preposePlanId;
							 							rowspan = 1;
							 							index = i; 
							 						}
												}
							 					
							 					merges.push({
													index: index,
													rowspan: rowspan
												});
							 					
												for(var i =0;i<merges.length; i++){
													$("#temporaryPlanPostposeList").datagrid('mergeCells',{
														index: merges[i].index,
														field: 'preposePlanName',
														rowspan: merges[i].rowspan
													});
												}
											}
											
											if(showChildAnalysis == true 
													|| showPostposeAnalysis == true){
												$('#changeTotalDiv').panel({closed:false});
												$('#changeTotalFontDiv').panel({closed:true});
											}
											else{
												$('#changeTotalDiv').panel({closed:true});
												$('#changeTotalFontDiv').panel({closed:false});
											}
											
											$('#changeTotal').panel({closed:false});
											$('#changeInfo').panel({closed:true});
											/* $('#changeReason').panel({closed:true}); */
											$('#changeReason').hide();
											$('#changeAnalysis').panel({closed:true});
											$('#changeVo').panel({closed:true});
											$('#clear').hide();
											$('#focusFour').focus();
										}
									});
									
								}
							});

						}
						else{
							win.tip(msg);
						}
					}
				}
			)
		}
	
		function setLeader(obj) {
			var username = ""; // 工号
			if (obj && obj.length > 0) {
				var singleUser = obj[0].split(':');
				username = singleUser[2];
			}
			$('#leader').val(username);
			
			return true;
		}
		
		function setDeptLeader(obj) {
			var username = ""; // 工号
			if (obj && obj.length > 0) {
				var singleUser = obj[0].split(':');
				username = singleUser[2];
			}
			$('#deptLeader').val(username);
			
			return true;
		}
	
		// 使用百分比
		function viewUseRate2(val, row, value){
			return progressRateGreen2(val);
		}
		
		function progressRateGreen2(val){
			if(val == undefined || val == null || val == ''){
				val = 0;
			}
			return val + '%' ;
		}
		
		function startPlanChange() {
			var leader = $('#leader').val();
			var deptLeader = $('#deptLeader').val();
		    	$.ajax({
					url : 'planChangeMassController.do?startPlanChange&changeType='+"mass"+'&leader='+leader+'&deptLeader='+deptLeader,
					type : 'post',
					data : {
						'leader' : leader,
						'deptLeader' : deptLeader
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							top.tip("变更成功");
						}
						if(d.success){
							var win = $.fn.lhgdialog("getSelectParentWin");
							var planListSearch = win.planListSearch();
							$.fn.lhgdialog("closeSelect");
						}
						// window.top.progress("close");
					}
				});
		}

		// 查看计划信息
		function viewPlan_(id) {
			var dialogUrl = 'planController.do?goCheck&id=' + id;
			createDialog('viewPlanDialog',dialogUrl);
		}
		
		//自定义行显示
		function funName1(val, row, index) {
			var returnStr = '<a class="basis ui-icon-pencil" style="display:inline-block;cursor:pointer;" onClick="modifyResource(\''
	            + index + '\')" title="<spring:message code='com.glaway.ids.common.btn.modify'/>"></a>'
	            + '<a class="basis ui-icon-minus" style="display:inline-block;cursor:pointer;" onClick="deleteResource(\''
	            + index + '\')" title="<spring:message code='com.glaway.ids.common.btn.remove'/>"></a>';
			return returnStr;
		}
		
		
		// 维护资源
		function modifyResource(index) {
			var rows = $("#resourceList").datagrid('getRows');
				var resourceName = rows[index].resourceName;
				var resourceType = rows[index].resourceType;
				var start = dateFmtYYYYMMDD(rows[index].startTime);
				var finish = dateFmtYYYYMMDD(rows[index].endTime);
				var dialogUrl = 'planChangeMassController.do?goModify&useObjectId='+ $('#useObjectId').val() 
				+ '&useObjectType=' + $('#useObjectType').val() 
				+ '&resourceId=' + rows[index].resourceId
				+ '&planStartTime='	+ $('#planStartTime').val()
				+ '&planEndTime='	+ $('#planEndTime').val()
				+ '&useRate='	+ rows[index].useRate
				+ '&start='	+ start
				+ '&planNameForResource=' + encodeURI(encodeURI(rows[index].planName))
				+ '&finish=' + finish;
				createDialog('modifyResourceDialog',dialogUrl);
		}
		
		function modifyResourceDialog(iframe){

			 var flg=iframe.saveEditResource();
	    	 if(flg && initResource2(flg)){
	    		 return true;
	    	 }
			return false;
		}
		
		
		
		// 删除资源
		function deleteResource(index) {
			var gridname = 'resourceList';
			var url = 'planChangeMassController.do?doDelResource';
			var win = $.fn.lhgdialog("getSelectParentWin");
		    var rows = $("#resourceList").datagrid('getRows');
			 var ids = rows[index].id;
			 var planName = rows[index].planName;
			 top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmDel"/>', 
		    		function(r) {
				 if (r) {
			    	$.ajax({
						url : 'planChangeMassController.do?doDelResource',
						type : 'post',
						data : {
							'ids' : ids,
							'planName' : planName
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								var msg = d.msg;
								win.tip(msg);
							}
							if(d.success){
								initResource2();
							}
						}
					});
				 }
		    		});
			}
		
		function initResource2(this_) {
			var datas="";
			var flg=false;
			if(this_ != null && this_ != '' && this_ != undefined){
				datas=this_;
			}else{
				datas={useObjectId : $('#useObjectId').val(),useObjectType : $('#useObjectType').val()}
			}
			$.ajax({
				type : 'POST',
				url : 'planChangeMassController.do?resourceListfresh',
				async : false,
				data : datas,
				success : function(data) {
					$("#resourceList").datagrid('unselectAll');
					$("#resourceList").datagrid("loadData",data);
					flg= true;
					var rows = data.rows;
					if(rows.length > 0){
						var merges= new Array();
						var rowspan = 0;
						var index = 0;
						var mergeCellVal = rows[0].useObjectId;
	 					for(var i=0; i<rows.length; i++){
	 						if(mergeCellVal != '' && mergeCellVal ==  rows[i].useObjectId){
	 							rowspan = rowspan + 1;
	 						}
	 						else{
	 							merges.push({
	 								index: index,
	 								rowspan: rowspan
	 							});
	 							mergeCellVal =  rows[i].useObjectId;
	 							rowspan = 1;
	 							index = i; 
	 						}
						}
	 					
	 					merges.push({
							index: index,
							rowspan: rowspan
						});
						for(var i =0;i<merges.length; i++){
							$("#resourceList").datagrid('mergeCells',{
								index: merges[i].index,
								field: 'planName',
								rowspan: merges[i].rowspan
							});
						}
					}
					
					
				} 
			});
			return flg;
		}
		
		function initChangeInfo() {
			$.ajax({
				type : 'POST',
				url : 'planChangeMassController.do?planChangeSelectList',
				async : false,
				data : {},
				success : function(data) {
					$("#planChangeSelectList").treegrid("loadData",data);
				} 
			});
		}
		
		
		//计划名称链接事件
		function viewPlanTwo(val, row, value) {
			return '<a href="javascript:void(0);" style="color:blue;" onclick="viewPlan_(\'' + row.id
					+ '\')">' + row.planName + '</a>';
		}
		
		

		// 时间格式化
		function dateFormatter(val,row,value){
			return dateFmtYYYYMMDD(val);
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
		
		
		
		
		function workTimeFormatterTwo(val, row, value){
			if(val != undefined && val != null && val != ''){
				return val+"天";
			}
			return "";
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
		
		var editFormElementIndex = undefined;
		function endFormElementEditing(){
			if (editFormElementIndex == undefined){return true}
			if ($('#planChangeSelectList').treegrid('validateRow', editFormElementIndex)){
				$('#planChangeSelectList').treegrid('endEdit', editFormElementIndex);
				editFormElementIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		
		function onClickPlanRow(index) {
			debugger;
			if (editFormElementIndex != index){
				if (endFormElementEditing()){
					$('#planChangeSelectList').treegrid('selectRow', index).datagrid('beginEdit', index);
					editFormElementIndex = index;
				} else {
					$('#planChangeSelectList').treegrid('selectRow', editFormElementIndex);
				}
			}
		}
			
			
		var editingId;
		function edit() {
			save();
			/* if (editingId != undefined) {
				$('#planChangeSelectList').treegrid('select', editingId);
				return;
			}
			rows = $('#planChangeSelectList').treegrid('getSelections');
			if (rows.length>1) {
				top.tip("如果修改单行数据，请选中一行操作");	
				return false;
			}
			
			var row = $('#planChangeSelectList').treegrid('getSelected');
			if (row) {
				editingId = row.id
				$('#planChangeSelectList').treegrid('beginEdit', editingId);
			} */
		}
		
		
		var first = undefined;
		function edit2() {
			
			debugger;
			first = 1;
			if (editingId != undefined) {
				$('#planChangeSelectList').treegrid('select', editingId);
				return;
			}
			rows = $('#planChangeSelectList').treegrid('getSelections');
			if (rows.length>1) {
				top.tip("如果修改单行数据，请选中一行操作");	
				return false;
			}				
			var row = $('#planChangeSelectList').treegrid('getSelected');
		/* 	rmStart = row.planStartTime
			rmEnd = row.planEndTime; */
			/* rmTime=row.workTime;
			rmilestone = row.milestone; */
			
			if (row) {
				editingId = row.id;
				$('#planChangeSelectList').treegrid('beginEdit', editingId);
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
			var win = $.fn.lhgdialog("getSelectParentWin");
			var startProjectTime = $('#startProjectTime').val();
			var endProjectTime = $('#endProjectTime').val();
			if (editingId != undefined) {
				var t = $('#planChangeSelectList');
				debugger;
				//var oldStart = t.treegrid('getSelections')[editingId].planStartTime;
				var	oldStart = t.treegrid('getSelected').planStartTime;
				var rmTime = t.treegrid('getSelected').workTime;
				var rmEnd = t.treegrid('getSelected').planEndTime;
				t.treegrid('endEdit', editingId);
				var remremberId = editingId;
				editingId = undefined;
				var persons = 0;
				var roots = $("#planChangeSelectList").treegrid("getRoots");
				if(roots.length > 0){
					for(var i = 0; i < roots.length; i++){
			    		if(roots[i].id == remremberId){
			    			if(endProjectTime < roots[i].planEndTime){
			    				win.tip("计划【"+roots[i].planName+"】的时间需收敛于项目的时间"+startProjectTime+"~"+endProjectTime);
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planEndTime: rmEnd
				    				}
				    			});
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planStartTime: oldStart
				    				}
				    			});
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					workTime: rmTime
				    				}
				    			});
			    				return false;
			    			}
			    			
			    			if(roots[i].planStartTime < startProjectTime){
			    				win.tip("计划【"+roots[i].planName+"】的时间需收敛于项目的时间"+startProjectTime+"~"+endProjectTime);
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planStartTime: oldStart
				    				}
				    			});
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planEndTime: rmEnd
				    				}
				    			});
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					workTime: rmTime
				    				}
				    			});
			    				return false;
			    			}
			    			
			    			if(roots[i].planStartTime == ""){
			    				win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planStartTime: oldStart
				    				}
				    			});
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planEndTime: rmEnd
				    				}
				    			});
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					workTime: rmTime
				    				}
				    			});
			    				return false;
			    			}
			    			
			    			if(roots[i].planEndTime == ""){
			    				win.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyEnd"/>');
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planStartTime: oldStart
				    				}
				    			});
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planEndTime: rmEnd
				    				}
				    			});
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					workTime: rmTime
				    				}
				    			});
			    				return false;
			    			}
			    			
			    			if(roots[i].planEndTime < roots[i].planStartTime){
			    				win.tip('<spring:message code="com.glaway.ids.pm.project.plan.endNoEarlierThanStartParam" arguments="' +roots[i].planName+ '"/>');
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planStartTime: oldStart
				    				}
				    			});
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planEndTime: rmEnd
				    				}
				    			});
			    				
			    				
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					workTime: rmTime
				    				}
				    			});
			    				
			    				return false;
			    			}
			    			if(roots[i].workTime == ''|| roots[i].workTime==undefined || roots[i].workTime == null){
		    					$('#planChangeSelectList').treegrid('beginEdit', remremberId);
		    					editingId = remremberId;
		    					$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					workTime: rmTime
				    				}
				    			});
		    					$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planStartTime: oldStart
				    				}
				    			});
			    				$('#planChangeSelectList').treegrid('update',{
				    				id: roots[i].id,
				    				row: {
				    					planEndTime: rmEnd
				    				}
				    			});
			    				
		    					top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyPeriod"/>');
		    					return false;
		    				}else{
		    					var reg = new RegExp("^[1-9][0-9]{0,3}$");
		    				 	if(!reg.test(roots[i].workTime)){
		    						$('#planChangeSelectList').treegrid('beginEdit', remremberId);
			    					editingId = remremberId;
			    					$('#planChangeSelectList').treegrid('update',{
					    				id: roots[i].id,
					    				row: {
					    					workTime: rmTime
					    				}
					    			});
			    					$('#planChangeSelectList').treegrid('update',{
					    				id: roots[i].id,
					    				row: {
					    					planStartTime: oldStart
					    				}
					    			});
				    				$('#planChangeSelectList').treegrid('update',{
					    				id: roots[i].id,
					    				row: {
					    					planEndTime: rmEnd
					    				}
					    			});
				    				
		    						top.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodLowerToUpper"/>');
		    						return false;
		    					} 
		    				}
			    			var dayNum = dateCompare(getDateByFmtStr(roots[i].planStartTime), getDateByFmtStr(roots[i].planEndTime)) + 1;
			    			var planStartTime = roots[i].planStartTime;
			    			var planEndTime = roots[i].planEndTime;
			    			var projectId = roots[i].projectId;
			    			var workTime = roots[i].workTime;
			    			
			    			if(planEndTime != null && planEndTime != '' && planEndTime != undefined){
			    				if(planStartTime != null && planStartTime != '' && planStartTime != undefined){
			    					debugger;
			    					if(planEndTime < planStartTime){
			    						$('#planChangeSelectList').treegrid('update',{
						    				id: roots[i].id,
						    				row: {
						    					planStartTime: oldStart
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: roots[i].id,
						    				row: {
						    					planEndTime: rmEnd
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: roots[i].id,
						    				row: {
						    					workTime: rmTime
						    				}
						    			});
			    						win.tip('<spring:message code="com.glaway.ids.pm.project.plan.startNoLaterThanEnd"/>');
					    				return false;
			    					}else{
			    						
			    						if(parseInt(rmTime) != parseInt(workTime) || rmEnd != planEndTime || rmEnd != planStartTime){
			    							//to
				    						if(parseInt(rmTime) == parseInt(workTime) && rmEnd != planEndTime){// work
				    							 $.ajax({
						    							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
						    							type : 'post',
						    							data : {
						    								projectId : projectId,
						    								planStartTime : roots[i].planStartTime,
						    								planEndTime : roots[i].planEndTime
						    							},
						    							cache : false,
						    							success : function(data) {
						    								if (data != null) {
						    									var d = $.parseJSON(data);
						    									var a = d.msg.indexOf('<spring:message code="com.glaway.ids.pm.project.plan.getPeriodSuccess"/>');
						    									if(d.success){
						    										dayNum = d.obj;
						    										if(dayNum == 0){
						    											dayNum = dayNum;
						    											win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
						    										}
						    										else{
						    											dayNum = dayNum;
						    							    			$('#planChangeSelectList').treegrid('update',{
						    							    				id: roots[i].id,
						    							    				row: {
						    							    					workTime: dayNum
						    							    				}
						    							    			});
						    										}
						    									}
						    									else{
						    										win.tip(d.msg);
						    									}
						    								}
						    								else{
						    									win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
						    								}
						    							}
						    						}); 
				    						}else{// end
				    							$.ajax({
					    							url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
					    							type : 'post',
					    							data : {
					    								projectId : projectId,
					    								planStartTime : roots[i].planStartTime,
					    								workTime : roots[i].workTime
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
					    							    			$('#planChangeSelectList').treegrid('update',{
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
			    						}
			    						
			    		
			    					}
			    				}else{
			    					$('#planChangeSelectList').treegrid('update',{
					    				id: roots[i].id,
					    				row: {
					    					planEndTime: rmEnd
					    				}
					    			});
			    					win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
				    				return false;
			    				}
			    			}
			    			
			    				var p = parseInt(roots[i].persons);
			    				if (!isNaN(p)) {
			    					persons += p;
			    				}
			    				var frow = t.treegrid('getFooterRows')[0];
			    				frow.persons = persons;
			    				t.treegrid('reloadFooter');
			    			break;
			    		}
						
						
						var child = $("#planChangeSelectList").treegrid("getChildren",roots[i].id);
				    	if(child.length > 0){
					    	for (var j = 0; j < child.length; j++) {
					    		if(child[j].id == remremberId){
					    			if(endProjectTime < child[j].planEndTime){
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					planEndTime: rmEnd
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					planStartTime: oldStart
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					workTime: rmTime
						    				}
						    			});
					    				win.tip("计划【"+child[j].planName+"】的时间需收敛于项目的时间"+startProjectTime+"~"+endProjectTime);
					    				return false;
					    			}
					    			
					    			if(child[j].planStartTime < startProjectTime){
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					planStartTime: oldStart
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					planEndTime: rmEnd
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					workTime: rmTime
						    				}
						    			});
					    				win.tip("计划【"+child[j].planName+"】的时间需收敛于项目的时间"+startProjectTime+"~"+endProjectTime);
					    				return false;
					    			}
					    			
					    			
					    			if(child[j].planStartTime == ""){
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					planStartTime: oldStart
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					planEndTime: rmEnd
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					workTime: rmTime
						    				}
						    			});
					    				win.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyStart"/>');
					    				return false;
					    			}
					    			
					    			if(child[j].planEndTime == ""){
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					planStartTime: oldStart
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					planEndTime: rmEnd
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					workTime: rmTime
						    				}
						    			});
					    				win.tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyEnd"/>');
					    				return false;
					    			}
					    			
					    			if(child[j].planEndTime < child[j].planStartTime){
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					planStartTime: oldStart
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					planEndTime: rmEnd
						    				}
						    			});
					    				$('#planChangeSelectList').treegrid('update',{
						    				id: child[j].id,
						    				row: {
						    					workTime: rmTime
						    				}
						    			});
					    				win.tip('<spring:message code="com.glaway.ids.pm.project.plan.endNoEarlierThanStartParam" arguments="' + child[j].planName+ '"/>');
					    				return false;
					    			}
					    			var dayNum = dateCompare(getDateByFmtStr(child[j].planStartTime), getDateByFmtStr(child[j].planEndTime)) + 1;
					    			
					    			var planStartTime = getDateByFmtStr(child[j].planStartTime);
					    			
					    			
					    			var planEndTime = getDateByFmtStr(child[j].planEndTime);
					    			
					    			var projectId = child[j].projectId;
					    			
					    			if(planEndTime != null && planEndTime != '' && planEndTime != undefined){
					    				if(planStartTime != null && planStartTime != '' && planStartTime != undefined){
					    					if(planEndTime < planStartTime){// repeat validate,  delete this branch
					    						$('#planChangeSelectList').treegrid('update',{
								    				id: child[j].id,
								    				row: {
								    					planStartTime: oldStart
								    				}
								    			});
							    				$('#planChangeSelectList').treegrid('update',{
								    				id: child[j].id,
								    				row: {
								    					planEndTime: rmEnd
								    				}
								    			});
							    				$('#planChangeSelectList').treegrid('update',{
								    				id: child[j].id,
								    				row: {
								    					workTime: rmTime
								    				}
								    			});
					    						win.tip('<spring:message code="com.glaway.ids.pm.project.plan.startNoLaterThanEnd"/>');
					    					}else{
					    						//to
					    						if(rmTime == workTime && rmEnd != planEndTime){// work
					    							$.ajax({
						    							url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
						    							type : 'post',
						    							data : {
						    								projectId : projectId,
						    								planStartTime : child[j].planStartTime,
						    								planEndTime : child[j].planEndTime
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
						    							    			$('#planChangeSelectList').treegrid('update',{
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
						    								planStartTime : child[j].planStartTime,
						    								workTime : child[j].workTime
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
						    							    			$('#planChangeSelectList').treegrid('update',{
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
					    					}
					    				}else{
					    					win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
					    				}
					    			}
					    			
					    				var p = parseInt(child[j].persons);
					    				if (!isNaN(p)) {
					    					persons += p;
					    				}
					    				var frow = t.treegrid('getFooterRows')[0];
					    				frow.persons = persons;
					    				t.treegrid('reloadFooter');
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
				$('#planChangeSelectList').treegrid('cancelEdit', editingId);
				editingId = undefined;
			}
		}
			
			
		
		function acceptPlan(){
			var win = $.fn.lhgdialog("getSelectParentWin");
			var roots = $("#planChangeSelectList").treegrid("getRoots");
			var ids = [];
			var planLeveIds = [];
			var ownerIds = [];
			var planStartTimes = [];
			var planEndTimes = [];
			var workTimes = [];
			var milestones = [];

			/* if(changeIds.length>0){
				if(changeIds.indexOf(roots[i].id) >= 0){												
						ids.push(roots[i].id);
							var curPlanLevel = roots[i].planLevel;
			    		var planLevelList = eval($('#planChangeSelectList').val());
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
			    		var curOwner = roots[i].owner;
			    		ownerIds.push(curOwner);					    		
			    		var curPlanStartTime = roots[i].planStartTime;
			    		planStartTimes.push(curPlanStartTime);
			    		var curPlanEndTime = roots[i].planEndTime;
			    		planEndTimes.push(curPlanEndTime);					    		
			    		var curWorkTime = roots[i].workTime;
			    		workTimes.push(curWorkTime);					    		
			    		var curMilestone = roots[i].milestone;
			    		milestones.push(curMilestone);															
				}
				} */
				for(var i = 0; i < roots.length; i++){
					var child = $("#planChangeSelectList").treegrid("getChildren",roots[i].id);						
		    		ids.push(roots[i].id);
		    		
		    		var planLevelList = eval($('#planLevelList').val());
		    		var pdPlanLevels = 0;
		    		for (var j = 0; j < planLevelList.length; j++) {
		    			if(roots[i].planLevel == undefined || roots[i].planLevel == ''){
		    				planLeveIds.push("===");
		    				pdPlanLevels = 1;
							break;
		    			}
					if (roots[i].planLevel == planLevelList[j].id) {
						planLeveIds.push(roots[i].planLevel);
						pdPlanLevels = 1;
						break;
					}
					if (roots[i].planLevel == planLevelList[j].name) {
						planLeveIds.push(planLevelList[j].id);
						pdPlanLevels = 1;
						break;
					}
		    		}
		    		if(pdPlanLevels == 0){
		    			planLeveIds.push("===");
		    		}
		    		
		    		var userList = eval($('#userList').val());
		    		for (var j = 0; j < userList.length; j++) {
					if (roots[i].owner == userList[j].id) {
						ownerIds.push(roots[i].owner);
						break;
					}
					if (roots[i].owner == userList[j].realName) {
						ownerIds.push(userList[j].id);
						break;
					}
		    		}
		    		
		    		planStartTimes.push(roots[i].planStartTime);
		    		planEndTimes.push(roots[i].planEndTime);
		    		workTimes.push($.trim(roots[i].workTime));
		    		milestones.push(roots[i].milestone);
			    	if(child.length > 0){
				    	for (var j = 0; j < child.length; j++) {
				    		ids.push(child[j].id);
				    		
				    		var planLevelList = eval($('#planLevelList').val());
				    		var pdPlanLevels = 0;
				    		for (var n = 0; n < planLevelList.length; n++) {
				    			if(child[j].planLevel == undefined || child[j].planLevel == ''){
				    				pdPlanLevels = 1;
				    				planLeveIds.push("===");
									break;
				    			}
				    			
							if (child[j].planLevel == planLevelList[n].id) {
								pdPlanLevels = 1;
								planLeveIds.push(child[j].planLevel);
								break;
							}
							if (child[j].planLevel == planLevelList[n].name) {
								pdPlanLevels = 1;
								planLeveIds.push(planLevelList[n].id);
								break;
							}
				    		}
				    		if(pdPlanLevels == 0){
				    			planLeveIds.push("===");
				    		}
				    		
				    		var userList = eval($('#userList').val());
				    		for (var n = 0; n < userList.length; n++) {
							if (child[j].owner == userList[n].id) {
								ownerIds.push(child[j].owner);
								break;
							}
							if (child[j].owner == userList[n].realName) {
								ownerIds.push(userList[n].id);
								break;
							}
				    		}
				    		
				    		
				    		
				    		planStartTimes.push(child[j].planStartTime);
				    		planEndTimes.push(child[j].planEndTime);
				    		workTimes.push($.trim(child[j].workTime));
				    		milestones.push(child[j].milestone);
						}
			    	}
				}
				
				
				$.ajax({
					url : 'planChangeMassController.do?changePlanList',
					type : 'post',
					data : {
						ids : ids.join(','),
						planLeveIds : planLeveIds.join(','),
						ownerIds : ownerIds.join(','),
						planStartTimes : planStartTimes.join(','),
						planEndTimes : planEndTimes.join(','),
						workTimes : workTimes.join(','),
						milestones : milestones.join(',')
					},
					cache : false,
					success : function(data) {
						$.ajax({
							url : 'planChangeMassController.do?checkChangePlanList',
							type : 'post',
							data : {},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								if (d.success) {
									var msg = d.msg;
									if(msg == null || msg == '' || msg == undefined|| msg == '<br/>'){
										initResourceChangeMass();

										$('#changeInfo').panel({closed:true});
										/* $('#changeReason').panel({closed:true}); */
										$('#changeReason').hide();
										$('#changeAnalysis').panel({closed:false});
										$('#changeTotal').panel({closed:true});
										$('#changeVo').panel({closed:true});
										$('#focusThree').focus();
									}
									else{
										win.tip(msg);
									}
								}
							}
						}); 
					}
				}); 
		}
		
		function viewResourceTime(val, row, value) {
			var startTime = row.startTime;
			var startTimeOverflow = row.startTimeOverflow;
			var endTime = row.endTime;
			var endTimeOverflow = row.endTimeOverflow;
			var resourceTime = "";
			if(startTimeOverflow == true){
				var timeLimit = "使用区间需要收敛在"+row.planStartTime+"~"+row.planEndTime+"内";
				resourceTime = '<a href="javascript:void(0)" style="color:red" class="tips_resourceList"  title="'+timeLimit+'" >' + dateFmtYYYYMMDD(startTime) + '</a>';
	                 /*  $('#this').tooltip(
							{
								position : 'right',
								content : '<span style="color:#fff">This is the tooltip message.</span>',
						}); */
		} else {
			resourceTime = dateFmtYYYYMMDD(startTime);
		}
		if (endTimeOverflow == true) {
			var timeLimit = "使用区间需要收敛在"+row.planStartTime+"~"+row.planEndTime+"内";
			resourceTime = resourceTime + "~"
					+ '<a href="javascript:void(0)" style="color:red" class="tips_resourceList" title="'+timeLimit+'"  >'
					+ dateFmtYYYYMMDD(endTime) + '</a>';
					
/*             $('#this').tooltip(
					{
						position : 'right',
						content : '<span style="color:#fff">This is the tooltip message.</span>',
				}); */
		} else {
			resourceTime = resourceTime + "~" + dateFmtYYYYMMDD(endTime);
		}
		return resourceTime;
	}

	function childPostposeTime(val, row, value) {
		var planStartTime = row.planStartTime;
		var startTimeOverflow = row.startTimeOverflow;
		var planEndTime = row.planEndTime;
		var endTimeOverflow = row.endTimeOverflow;
		var childPostposeTime = "";
		if (startTimeOverflow == true) {
			var timeLimit = row.timeAreaTip;
			childPostposeTime = '<a href="javascript:void(0)" style="color:red" class="tips_temporaryPlanChildList" title="'+timeLimit+'" >'
					+ planStartTime + '</a>';
/*             $('#ee').tooltip(
					{
						position : 'right',
						content : '<span style="color:#fff">This is the tooltip message.</span>',
						onShow : function() {
							$(this).tooltip('tip').css({
								backgroundColor : '#666',
								borderColor : '#666'
							});
						}
				}); */
		} else {
			childPostposeTime = planStartTime;
		}
		if (endTimeOverflow == true) {
			var timeLimit = row.timeAreaTip;
			childPostposeTime = childPostposeTime + "~"
					+ '<a href="javascript:void(0)" style="color:red" class="tips_temporaryPlanChildList" title="'+timeLimit+'" >' + planEndTime + '</a>';
/*             $('#ff').tooltip(
					{
						position : 'right',
						content : '<span style="color:#fff">This is the tooltip message.</span>',
						onShow : function() {
							$(this).tooltip('tip').css({
								backgroundColor : '#666',
								borderColor : '#666'
							});
						}
				}); */		
		} else {
			childPostposeTime = childPostposeTime + "~" + planEndTime;
		}
		return childPostposeTime;
	}
	
	
	function childPostposeTime2(val, row, value) {
		var planStartTime = row.planStartTime;
		var startTimeOverflow = row.startTimeOverflow;
		var planEndTime = row.planEndTime;
		var endTimeOverflow = row.endTimeOverflow;
		var childPostposeTime = "";
		if (startTimeOverflow == true) {
			var timeLimit = row.timeAreaTip;
			childPostposeTime = '<a href="javascript:void(0)" style="color:red" class="tips_temporaryPlanPostposeList" title="'+timeLimit+'">'
					+ planStartTime + '</a>';
/*             $('#ee').tooltip(
					{
						position : 'right',
						content : '<span style="color:#fff">This is the tooltip message.</span>',
						onShow : function() {
							$(this).tooltip('tip').css({
								backgroundColor : '#666',
								borderColor : '#666'
							});
						}
				}); */
		} else {
			childPostposeTime = planStartTime;
		}
		if (endTimeOverflow == true) {
			var timeLimit = row.timeAreaTip;
			childPostposeTime = childPostposeTime + "~"
					+ '<a href="javascript:void(0)" style="color:red" class="tips_temporaryPlanPostposeList" title="'+timeLimit+'">' + planEndTime + '</a>';
/*             $('#ff').tooltip(
					{
						position : 'right',
						content : '<span style="color:#fff">This is the tooltip message.</span>',
						onShow : function() {
							$(this).tooltip('tip').css({
								backgroundColor : '#666',
								borderColor : '#666'
							});
						}
				}); */		
		} else {
			childPostposeTime = childPostposeTime + "~" + planEndTime;
		}
		return childPostposeTime;
	}

	function closePlan() {
		$.ajax({
			type : 'POST',
			url : 'planChangeMassController.do?closePlan',
			async : false,
			data : {
			},
			success : function(data) {
			}
		});
		$.fn.lhgdialog("closeSelect");
	}

	// 资源名称链接事件
	function resourceNameLink(val, row, value) {
		return '<a href="#" onclick="viewResourceCharts(\'' + row.id
				+ '\')" style="color:blue">' + val + '</a>';
	}

	// 资源名称链接事件
	function viewResourceCharts(id) {
		var rows = $("#resourceList").datagrid('getRows');
		var row;
		for (var i = 0; i < rows.length; i++) {
			if (id == rows[i].id) {
				row = rows[i];
				break;
			}
		}
		if (row.useRate != null && row.useRate != '' && row.startTime != null
				&& row.startTime != '' && row.endTime != null
				&& row.endTime != '') {

			var startTime = dateFmtYYYYMMDD(row.startTime);
			var endTime = dateFmtYYYYMMDD(row.endTime);
			createDialog('resourceDialog',"resourceLinkInfoController.do?goToToBeUsedRateReport&resourceId="+ row.resourceInfo.id + "&resourceLinkId=" 
					+ row.linkId + "&resourceUseRate=" + row.useRate + "&startTime=" + startTime + "&endTime=" + endTime + "&useObjectId="+row.useObjectId);				
		} else {
			tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyUseInfo"/>');
		}
	}
	
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
									var rows = $('#planChangeSelectList').treegrid('getSelections');
									if(rows.length > 0){								
									}else{
										rows = $('#planChangeSelectList').treegrid('getRoots');	
										for(var i = 0; i < rows.length; i++){
											var childs = $("#planChangeSelectList").treegrid("getChildren",rows[i].id);
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
										    		$('#planChangeSelectList').treegrid('update',{
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
										$('#planChangeSelectList').treegrid('update',{
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
								height:'240px',
								onSelect : function(index, row){
									var rows = $('#planChangeSelectList').treegrid('getSelections');
									if(rows.length > 0){								
									}else{
										rows = $('#planChangeSelectList').treegrid('getRoots');	
										for(var i = 0; i < rows.length; i++){
											var childs = $("#planChangeSelectList").treegrid("getChildren",rows[i].id);
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
										    		$('#planChangeSelectList').treegrid('update',{
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
										$('#planChangeSelectList').treegrid('update',{
											id: rows[i].id,
											row: {
												owner: row.id
											}
										}); 
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
	var rows = $('#planChangeSelectList').treegrid('getSelections');
	if(rows.length > 0){								
	}else{
		rows = $('#planChangeSelectList').treegrid('getRoots');	
		for(var i = 0; i < rows.length; i++){
			var childs = $("#planChangeSelectList").treegrid("getChildren",rows[i].id);
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
		    		$('#planChangeSelectList').treegrid('update',{
						id: childs[n].id,
						row: {
							milestone: val
						}
					});								    							    		
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
		$('#planChangeSelectList').treegrid('update',{
			id: rows[i].id,
			row: {
				milestone: val
			}
		});
	}
} 

</script>
	<input id="projectId" value="${projectIdForPlan}" type="hidden">
	<div id="changeReason" >
		<div class="easyui-layout">
			<input id="planChangeCategoryListStr" name="planChangeCategoryListStr" type="hidden" value="${planChangeCategoryListStr}">
		    <input id="userList" name="userList" type="hidden" value="${userList}">
	       	<input id="planLevelList" name="planLevelList" type="hidden" value="${planLevelList}">
	        <input id="startProjectTime" name="startProjectTime" type="hidden" value="${startProjectTime}">
	        <input id="endProjectTime" name="endProjectTime" type="hidden" value="${endProjectTime}">
			<fd:form id="planChangeForm" help="helpDoc:ChangePlan">	
				<%-- <fd:combobox id="changeType" textField="name" title="{com.glaway.ids.pm.project.plan.changeType}" selectedValue="${temporaryPlan_.changeType}" 
					editable="false" valueField="id" required="true" panelMaxHeight="200" url="planChangeMassController.do?planChangeCategorylList" >
				</fd:combobox> --%>
				<div style="height:210px;">
					<fd:combotree title="{com.glaway.ids.pm.project.plan.changeType}" treeIdKey="id" name="changeType"
						url="planChangeMassController.do?planChangeCategorylList" id="changeType" treePidKey="parentId"
						editable="false" multiple="false" panelHeight="200" treeName="name" required="true"
						prompt="请选择"></fd:combotree> 
					<fd:inputTextArea id="changeRemark" title="{com.glaway.ids.common.lable.remark}" name="changeRemark" value="${temporaryPlan_.changeRemark}" />
				</div>
				
		
				<fd:lazyLoadingTreeGrid id="planChangeList"
					url="planChangeMassController.do?planChangeList&projectId=${projectIdForPlan}" 
					width="100%" height="800px" enableMultiselect="true"
					initWidths="0,52,200,*,*,*,*,*" imgUrl="plug-in/icons_greenfolders/"
					columnIds="id,planNumber,planName,planLevelInfo,ownerInfo,planStartTime,planEndTime,workTime"
					header="ID,编号,计划名称,计划等级,负责人,开始时间,结束时间,工期(天)"
					columnStyles="['font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;','font-weight: bold;']"
					colAlign="left,left,left,left,left,left,left,left"
					colSortings="na,na,na,na,na,na,na,na,"
					colTypes="ro,ro,tree,ro,ro,ro,ro,ro"
					enableTreeGridLines="true" enableLoadingStatus="false">
				</fd:lazyLoadingTreeGrid>
			</fd:form>
		</div>
		
		<div class="div-msg-btn">
			<div>
				<fd:linkbutton onclick="nextOne()" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" />
				<fd:linkbutton onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" id="focusOne"/>				
			</div>
		</div>
	</div>
	
	<div border="false" class="easyui-panel div-msg" id="changeInfo" fit="true">
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
							<div id="dl" style="width: 95%; height:220px; display: none;"></div>
						</div>
					</div>
					<div id="t1">
						<span>负责人</span>
						<div id="ttt1"
							style="width: 161px; height: 278px; overflow-x: hidden; overflow-y: hidden;">
							<input class="easyui-textbox" style="width: 95%; float: right;"
								iconCls="" id="tinput1" data-options="iconCls:'icon-remove'">
							<div id="dl1" style="width: 95%; height:220px; display: none;"></div>
						</div>
					</div>
					<div>
						<span>里程碑</span>
						<div id="ttt2" style="width:30px;height:60px;" >
							<span>
								<table>								
									<tr>
										<td><input type="radio" id="yes" value="y" title="是" name="chooseRadio" onclick="changeMilestone2('是')" /></td>
										<td><label for="yes">是</label></td>
									</tr>
									<tr>
										<td><input type="radio" id="no" value="n" title="否" name="chooseRadio" onclick="changeMilestone2('否')"/></td>
										<td><label for="no">否</label></td>
									</tr>
								</table>
							</span>
						</div>
					</div>
				</div>
			</div>
			
		</fd:toolbar>
		<fd:treegrid id="planChangeSelectList" idField="id" treeField="planName" singleSelect="false" toolbar="#aa"
				fit="true" fitColumns="true">
			<fd:columns>
		     	<fd:column title="{com.glaway.ids.pm.project.plan.planNumber}" field="planNumber"/>
				<fd:column title="{com.glaway.ids.pm.project.plan.planName}" field="planName" width="25" formatter="viewPlanTwo" tipField="planName"/>
				<fd:column title="{com.glaway.ids.pm.project.plan.planLevel}" field="planLevel" width="12" formatter="formatterByBusinessConfigTwo" editor="{type:'combobox',options:{
				                    url:'${pageContext.request.contextPath }/planController.do?useablePlanLevelList',
						            method:'get',
						            valueField:'id',
						            textField:'name',
						            onChange:onChanges2
								}}" />
				<fd:column title="{com.glaway.ids.common.lable.owner}" field="owner" width="17" formatter="viewUserRealNameTwo" editor="{type:'combobox',options:{
				                    url:'${pageContext.request.contextPath }/planChangeMassController.do?queryUserListForModify&projectId=${projectIdForPlan}',
						            method:'get',
						            valueField:'userKey',
						            textField:'userName',
						            onChange:onChanges3
								}}" />
				<fd:column title="{com.glaway.ids.common.lable.starttime}" field="planStartTime" width="17" editor="datebox"/>
				<fd:column title="{com.glaway.ids.common.lable.endtime}" field="planEndTime" width="17" editor="datebox"/>
				<fd:column title="{com.glaway.ids.pm.project.plan.workTime}" field="workTime" width="12"   formatter="workTimeFormatter" editor="textbox"/>
				<fd:column title="{com.glaway.ids.pm.project.plan.milestone}" field="milestone" width="12"  formatter="delMilestone"  editor="{type:'combobox',options:{
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
				<fd:linkbutton onclick="beforeTwo()" value="{com.glaway.ids.pm.project.plan.beforeone}" classStyle="button_nor" />
				<fd:linkbutton onclick="nextTwo()" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" />
				<fd:linkbutton onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" id="focusTwo"/>				
			</div> 
		</div>
	</div>	

	<div border="false" class="easyui-panel div-msg" id="changeAnalysis" fit="true">
		<div class="easyui-panel" fit="true" id="fontDiv" style="padding-bottom:5px;padding-top:20px;">
			<label><spring:message code="com.glaway.ids.pm.project.plan.changeAnaly"/></label>
		</div>
		
		<div id="resourceDiv" class="easyui-panel" fit="true">
			<fd:datagrid id="resourceList" checkbox="false" pagination="false" 
				fitColumns="true" idField="planId" fit="true">
				<fd:dgCol  title="{com.glaway.ids.pm.project.plan.planName}" field="planName" width="100"  sortable="false"></fd:dgCol>
				<fd:dgCol title="{com.glaway.ids.common.lable.operation}" field="operateArea" formatterFunName="funName1"  width="70"  sortable="false"></fd:dgCol>
				<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceName}" field="resourceName" width="100" formatterFunName="resourceNameLink"  sortable="false"></fd:dgCol>
				<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceType}" field="resourceType" width="150"  sortable="false"></fd:dgCol>
				<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.useRate}" field="useRate" width="70"  formatterFunName="viewUseRate2"  sortable="false"></fd:dgCol>
				<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.area}" field="resourceInfo" width="150" formatterFunName="viewResourceTime"  sortable="false"></fd:dgCol>
			</fd:datagrid>
		</div>
	 	<div class="div-msg-btn">
			<div>
			<fd:linkbutton onclick="beforeThree()" value="{com.glaway.ids.pm.project.plan.beforeone}" classStyle="button_nor" />
			<fd:linkbutton onclick="nextThree()" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" />
			<fd:linkbutton onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" id="focusThree" />		
			</div>
		</div>	
	</div>
	
	 <div border="false" class="easyui-panel div-msg" id="changeTotal" fit="true">
	 	<div id="changeTotalFontDiv" style="padding-bottom:5px;padding-top:20px;" class="easyui-panel" fit="true">
			<label><spring:message code="com.glaway.ids.pm.project.plan.noChange"/></label>
	 	</div>
		 	
	 	<div id="changeTotalDiv" class="easyui-panel" fit="true">
	 		<div style="padding-top: 5px; padding-bottom: 5px;">
				<label>子计划 </label>
			</div>
		    <div class="easyui-panel" style="height:200px">
				<fd:datagrid id="temporaryPlanChildList" checkbox="false" pagination="false"
					fitColumns="true" idField="planId" fit="true">
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.parentPlanName}" field="parentPlanName" width="130"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.planName}" field="planName" width="130"  sortable="false"></fd:dgCol>
					<fd:dgCol title="发布人" field="assignerName" width="100"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.common.lable.owner}" field="ownerName" width="100"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.planTime}" field="planStartTime" width="150"   sortable="false"
						formatterFunName="childPostposeTime" ></fd:dgCol>
				</fd:datagrid>
			</div>
			
			<div style="padding-top: 20px; padding-bottom: 5px;">
				<label><spring:message code="com.glaway.ids.pm.project.plan.afterPlan"/></label>
			</div>
			<div class="easyui-panel" style="height:200px">
				<fd:datagrid id="temporaryPlanPostposeList" checkbox="false" pagination="false"
					fitColumns="true" idField="planId" fit="true">					
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.preposePlans}" field="preposePlanName" width="130"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.planName}" field="planName" width="130"  sortable="false"></fd:dgCol>
					<fd:dgCol title="发布人" field="assignerName" width="100"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.common.lable.owner}" field="ownerName" width="100"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.planTime}" field="planStartTime" width="150"   sortable="false"
						formatterFunName="childPostposeTime2" ></fd:dgCol>
				</fd:datagrid>
			</div>
		</div>
		
		 <div class="div-msg-btn">
			<div>
				<fd:linkbutton onclick="beforeFour()" value="{com.glaway.ids.pm.project.plan.beforeone}" classStyle="button_nor" />
				<fd:linkbutton onclick="nextFour()" value="{com.glaway.ids.pm.project.plan.nextone}" classStyle="button_nor" />
				<fd:linkbutton onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" id="focusFour" />		
			</div>
		</div>	
	</div>
	
 	<div border="false" class="easyui-panel div-msg div-msg-fit" id="changeVo" fit="false" style="width:100%;height:100%;">
		<div class="easyui-panel div-msg-fit-panel" fit="false" style="width:100%;height:100%;">
		 	<fd:panel id="searchUserPanel" border="false" collapsible="true" fit="false" width="100%">
				<input id="leader" name="leader" type="hidden" /> 
				<fd:inputSearchUser id="leaderName" name="leaderId" title="{com.glaway.ids.pm.project.plan.basicLine.leader}" required="true">
					<fd:eventListener event="beforeAffirmClose" listener="setLeader" needReturn="true" />
				</fd:inputSearchUser>
				<input id="deptLeader" name="deptLeader" type="hidden" />
				<fd:inputSearchUser id="deptLeaderName" name="deptLeaderId" title="{com.glaway.ids.pm.project.plan.basicLine.deptLeader}" required="true">
					<fd:eventListener event="beforeAffirmClose" listener="setDeptLeader" needReturn="true" />
				</fd:inputSearchUser>
			</fd:panel>
				<fd:datagrid id="planChangeVo" checkbox="false" pagination="false" fitColumns="true" idField="planId" fit="false" width="100%">
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.planName}" field="planName" width="130"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.field}" field="field" width="130"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.type}" field="type" width="120"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.changeBefore}" field="changeBefore" width="120"  sortable="false"></fd:dgCol>
					<fd:dgCol title="{com.glaway.ids.pm.project.plan.changeAfter}" field="changeAfter" width="150"  sortable="false"></fd:dgCol>
				</fd:datagrid>
		</div>
		
	 	<div class="div-msg-btn">
			<div>
				<fd:linkbutton onclick="beforeFive()" value="{com.glaway.ids.pm.project.plan.beforeone}" classStyle="button_nor" />
				<fd:linkbutton onclick="addTempPlan()" id="addTempPlanButton" value="{com.glaway.ids.common.btn.submit}" classStyle="button_nor" />
				<fd:linkbutton onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" id="focusFive" />		
			</div>
		</div>
	</div>
	
	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
	    <fd:dialogbutton name="关闭" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="modifyResourceDialog" width="800px" height="350px" modal="true" title="{com.glaway.ids.pm.project.plan.modifyResource}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="modifyResourceDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="resourceDialog" width="900px" height="800px" modal="true" title="{com.glaway.ids.pm.project.plan.resourcePicture}" maxFun="true">
	</fd:dialog>
	
	<script type="text/javascript">
	function onChanges(newValue, oldValue) {
		var  result="否";
		if(newValue=='true' || newValue=='是' ){
			result="是";
		}
		$(this).combobox('setValue',result);
		
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
	
	</script>
</body>
