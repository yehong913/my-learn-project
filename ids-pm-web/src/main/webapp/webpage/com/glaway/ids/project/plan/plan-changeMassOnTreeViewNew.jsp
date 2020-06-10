<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划批量变更审批流程中查看</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
var userList = $.parseJSON('${userList1}'); 
var planLevelList = $.parseJSON('${planLevelList1}');
	//编写自定义JS代码
  $(document).ready(function() {
		//当点击实时tab的时候，会主动刷新数据
		$('#planTabs').tabs({
			onSelect :function(title,index){
				if(index==0){
					var tab = $('#planTabs').tabs('getSelected');
					tab.panel('refresh', 'planChangeMassController.do?goChangeMassOne&projectId=${projectId}&id=${temporaryPlan_.id}');
				}else if(index==1){
					var tab = $('#planTabs').tabs('getSelected'); 
					tab.panel('refresh', 'planChangeMassController.do?goChangeMassTwo&projectId=${projectId}&id=${temporaryPlan_.id}');
				}else if(index==2){
					var tab = $('#planTabs').tabs('getSelected'); 
					tab.panel('refresh', 'planChangeMassController.do?goChangeMassThree&projectId=${projectId}&id=${temporaryPlan_.id}');
					//acceptPlan();
					window.setTimeout('initResourceChangeMass()',500);
			   }else if(index==3){
					var tab = $('#planTabs').tabs('getSelected'); 
					tab.panel('refresh', 'planChangeMassController.do?goChangeMassFour&projectId=${projectId}&id=${temporaryPlan_.id}');
					window.setTimeout('changeAnalysisInit()',500);
					//changeAnalysisInit();
			   }else if(index==4){
					var tab = $('#planTabs').tabs('getSelected'); 
					tab.panel('refresh', 'planChangeMassController.do?goChangeMassFive&projectId=${projectId}&id=${temporaryPlan_.id}');
					window.setTimeout('initChangeVo()',500);
					//initChangeVo();
			   }
			}
		});
		if('${isSubmit}' == '1'){
			$('#submit').show();
		}else{
			$('#submit').hide();
		}
	});


	function begin() {
		$('#changeReason').panel({closed:false});
		$('#changeInfo').panel({closed:true});
		$('#changeAnalysis').panel({closed:true});
		$('#changeTotal').panel({closed:true});
		$('#changeVo').panel({closed:true});
		$('#focusOne').focus();
	}
	
	function nextFour() {
		$('#changeReason').panel({closed:true});
		$('#changeInfo').panel({closed:true});
		$('#changeAnalysis').panel({closed:true});
		$('#changeTotal').panel({closed:true});
		$('#changeVo').panel({closed:false});
		initChangeVo();
	}
	

	function nextOne() {
		$('#changeReason').panel({closed:true});
		$('#changeInfo').panel({closed:false});
		$('#changeAnalysis').panel({closed:true});
		$('#changeTotal').panel({closed:true});
		$('#changeVo').panel({closed:true});
		//initChangeInfo();
	}

	function nextTwo() {
		acceptPlan();
	}
	
	function nextThree() {
		changeAnalysisInit();
	}

	function beforeTwo() {
		$('#changeReason').panel({closed:false});
		$('#changeInfo').panel({closed:true});
		$('#changeAnalysis').panel({closed:true});
		$('#changeTotal').panel({closed:true});
		$('#changeVo').panel({closed:true});
		$('#focusOne').focus();
	}

	function beforeThree() {
		$('#changeInfo').panel({closed:false});
		$('#changeReason').panel({closed:true});
		$('#changeAnalysis').panel({closed:true});
		$('#changeTotal').panel({closed:true});
		$('#changeVo').panel({closed:true});
		$('#focusTwo').focus();
	}
	
	function beforeFour() {
		$('#changeAnalysis').panel({closed:false});
		$('#changeInfo').panel({closed:true});
		$('#changeReason').panel({closed:true});
		$('#changeTotal').panel({closed:true});
		$('#changeVo').panel({closed:true});
		$('#focusThree').focus();
	}
	
	function beforeFive() {
		$('#changeAnalysis').panel({closed:true});
		$('#changeInfo').panel({closed:true});
		$('#changeReason').panel({closed:true});
		$('#changeTotal').panel({closed:false});
		$('#changeVo').panel({closed:true});
		$('#focusFour').focus();
	}
	
	
	function initResourceChangeMass() {
		$.ajax({
			type : 'POST',
			url : 'planChangeMassController.do?resourceFlowList',
			async : false,
			data : {},
			success : function(data) {
				var rows = data.rows;
				if(rows.length > 0){
					$("#fontDiv").attr("style","display:none;");
					$("#resourceList").datagrid("loadData",data);
					var merges= new Array();
					var rowspan = 0;
					var index = 0;
					var mergeCellVal = rows[0].resourceLinkId;
 					for(var i=0; i<rows.length; i++){
 						if(mergeCellVal != '' && mergeCellVal ==  rows[i].resourceLinkId){
 							rowspan = rowspan + 1;
 						}
 						else{
 							merges.push({
 								index: index,
 								rowspan: rowspan
 							});
 							mergeCellVal =  rows[i].resourceLinkId;
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
					$("#fontDiv").attr("style","display:block;");
					$("#resourceDiv").attr("style","display:none;");
				}
			} 
		});
	}
	
	
	function start() {
		var changeType = $('#changeType').val();
		var planChangeCategoryList = eval($('#planChangeCategoryListStr').val());
		for(var i=0;i<planChangeCategoryList.length;i++){
			if( $('#changeType').val() == planChangeCategoryList[i].id){
				$('#changeTypeAfter').val(planChangeCategoryList[i].name);
			}
		}
	}	
		function startChange() {
			var rows = $("#planChangeList").treegrid("getSelections");
			var ids = [];
			if (rows.length > 0) {
		    	for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				
		    	$.ajax({
					url : 'planChangeMassController.do?pdChangeAll&projectId=${projectId}',
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							initChangeInfo();
						}
					}
				});
		    	
		    }else{
				alert('<spring:message code="com.glaway.ids.pm.project.plan.selectChange"/>');
				return false;
		    }
		}
		
		
	
	function addTempPlan() {
			var changeRemark = $('#changeRemark').val();
			var changeType = $('#changeType').val();
			$.post('planChangeController.do?doSave', {
				'changeRemark' : changeRemark,
				'changeType' : changeType
			}, function(data) {
				startPlanChange();
				var win = $.fn.lhgdialog("getSelectParentWin");
				var planListSearch = win.planListSearch();
				$.fn.lhgdialog("closeSelect");
			});
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

											$("#changeTotalDiv").attr("style","display:block;");
											$("#changeTotalFontDiv").attr("style","display:none;");
										}
										else{
											
											$("#changeTotalFontDiv").attr("style","display:block;");
											$("#changeTotalDiv").attr("style","display:none;");
										}
										$('#clear').hide();

									}
								});
								
							}
						});

					}
					else{
						tip(msg);
					}
				}
			}
		)
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
	
	
	
	
	function startPlanChange() {
		var leader = $('#leader').val();
		var deptLeader = $('#deptLeader').val();
		var leaderName = $('#leaderName').val();
		var deptLeaderName = $('#deptLeaderName').val();
			if(leaderName == ''){
				alert('<spring:message code="com.glaway.ids.common.emptyOfficeLeader"/>');
				return false;
			}
			if(deptLeaderName == ''){
				alert('<spring:message code="com.glaway.ids.common.emptyDepartLeader"/>');
				return false;
			}
			
	    	$.ajax({
				url : 'planChangeMassController.do?startPlanChange&changeType='+"mass"+ '&leader=' + encodeURI(encodeURI(leader))+ '&deptLeader=' + encodeURI(encodeURI(deptLeader)),
				type : 'post',
				data : {
					'leader' : leader,
					'deptLeader' : deptLeader
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
					}
					if(d.success){
						var win = $.fn.lhgdialog("getSelectParentWin");
						var planListSearch = win.planListSearch();
						$.fn.lhgdialog("closeSelect");
					}
				}
			});
	}

	// 查看计划信息
	function viewPlan(id) {
		var dialogUrl = 'planController.do?goCheck&id=' + id;
		createDialog('viewPlanDialog',dialogUrl);
	}
	
	//自定义行显示
	function funName1(val, row, value) {
		var returnStr = '';
			returnStr +='<a href="#" onclick="modifyResource(\'' + row.id + '\')">'
				+ '[修改]' + '</a>' +'<a href="#" onclick="deleteResource(\'' + row.id + '\')">'
				+ '[删除]' + '</a>';
		return returnStr;
	}
	
	
	// 维护资源
	function modifyResource(id) {
		var rows = $("#resourceList").datagrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
		if(id == rows[i].resourceId){
			var resourceName = rows[i].resourceName;
			var resourceType = rows[i].resourceType;
			var start = dateFmtYYYYMMDD(rows[i].startTime);
			var finish = dateFmtYYYYMMDD(rows[i].endTime);
			$.fn.lhgdialog({
				content : 'url:planChangeMassController.do?goModify&useObjectId='+ $('#useObjectId').val() 
						+ '&useObjectType=' + $('#useObjectType').val() 
						+ '&resourceId=' + rows[i].resourceId
						+ '&planStartTime='	+ $('#planStartTime').val()
						+ '&planEndTime='	+ $('#planEndTime').val()
						+ '&useRate='	+ rows[i].useRate
						+ '&start='	+ start
						+ '&finish=' + finish,
				lock : true,
				width : 700,
				height : 400,
				zIndex : 2000,
				title : '修改资源',
				opacity : 0.3,
				cache : false,
				ok: function(){
			    	 iframe = this.iframe.contentWindow;
			    	 var flg=iframe.saveEditResource();
			    	 if(flg && initResource2(flg)){
			    		 return true;
			    	 }
					return false;
			    },
			});
         break;
		}
		}	
	}
	
	// 删除资源
	function deleteResource(id) {
		var gridname = 'resourceList';
		var url = 'planChangeMassController.do?doDelResource';
	   
	    var rows = $("#resourceList").datagrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
		if(id == rows[i].resourceId){
		 var ids = id;
	    if (rows.length > 0) {
	    	top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmDel"/>', 
	    		function(r) {
	    		if (r) {
		    	$.ajax({
					url : 'planChangeMassController.do?doDelResource',
					type : 'post',
					data : {
						'ids' : ids
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
						}
						if(d.success){
							initResource2();
						}
					}
				});
	    		}
	    		});
		} else {
			tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
		}
	    break;
	}
		}	
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
			url : 'planChangeMassController.do?resourceListfreshView',
			async : false,
			data : datas,
			success : function(data) {
				$("#resourceList").datagrid('unselectAll');
				$("#resourceList").datagrid("loadData",data);
				flg= true;
			} 
		});
		return flg;
	}
	
	function initChangeInfo() {
		$.ajax({
			type : 'POST',
			url : 'planChangeMassController.do?planChangeSelectListView',
			async : false,
			data : {},
			success : function(data) {
				$("#planChangeSelectList").treegrid("loadData",data);
			} 
		});
	}
	
	
	//计划名称链接事件
	function viewPlanTwo(id) {
		var dialogUrl = 'planController.do?goCheck&id=' + id;
		createDialog('viewPlanDialog', dialogUrl);
	}
	
	

	// 时间格式化
	function dateFormatter(val,row,value){
		return dateFmtYYYYMMDD(val);
	}

	// 人员id、realName转换
	function viewUserRealNameTwo(val, row, value){
		if(val != undefined && val != null && val != ''){
			if(val.realName != null && val.realName != ''){
				return val.realName;
			}else{
				return val.id;
			}
		}
		return val;
		
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
		}else {
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
			if (editingId != undefined) {
				$('#planChangeSelectList').treegrid('select', editingId);
				return;
			}
			var row = $('#planChangeSelectList').treegrid('getSelected');
			if (row) {
				editingId = row.id
				$('#planChangeSelectList').treegrid('beginEdit', editingId);
			}
		}
		function save() {
			if (editingId != undefined) {
				var t = $('#planChangeSelectList');
				t.treegrid('endEdit', editingId);
				editingId = undefined;
				var persons = 0;
				var rows = t.treegrid('getChildren');
				for (var i = 0; i < rows.length; i++) {
					var p = parseInt(rows[i].persons);
					if (!isNaN(p)) {
						persons += p;
					}
				}
				var frow = t.treegrid('getFooterRows')[0];
				frow.persons = persons;
				t.treegrid('reloadFooter');
			}
		}
		
		function cancel(){
			if (editingId != undefined){
				$('#planChangeSelectList').treegrid('cancelEdit', editingId);
				editingId = undefined;
			}
		}
		
		
		
		function acceptPlan(){
			

			var ids;
			var planLeveIds = [];
			var ownerIds = [];
			var planStartTimes = [];
			var planEndTimes = [];
			var workTimes = [];
			var milestones = [];
			ids = planChangeSelectList.getAllRowIds();
			if(ids != null && ids != ''){

				var item=ids.split(',');
				for (var i=0;i<item.length;i++){
					var planLeveId = planChangeSelectList.getRowAttribute(item[i],'planLevelInfo');	
					var ownerInfo = planChangeSelectList.getRowAttribute(item[i],'ownerInfo');
					var planStartTime = planChangeSelectList.getRowAttribute(item[i],'planStartTime');	
					var planEndTime = planChangeSelectList.getRowAttribute(item[i],'planEndTime');	
					var workTime = planChangeSelectList.getRowAttribute(item[i],'workTime');	
					var milestone = planChangeSelectList.getRowAttribute(item[i],'milestone');
					if(planLeveId !=null&&planLeveId!=''){
						planLeveIds.push(planLeveId);
					}else{
						planLeveIds.push("===");
					}
		    		ownerIds.push(ownerInfo);
		    		planStartTimes.push(planStartTime);
		    		planEndTimes.push(planEndTime);
		    		workTimes.push(workTime);
		    		milestones.push(milestone);
				}
				
				
				
				$.ajax({
					url : 'planChangeMassController.do?changePlanList',
					type : 'post',
					data : {
						ids : ids,
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
									initResourceChangeMass();
								}
							}
						}); 
					}
				}); 
			}
		}
		
		
		function viewResourceTime(val, row, value) {
			var startTime = row.startTime;
			var startTimeOverflow = row.startTimeOverflow;
			var endTime = row.endTime;
			var endTimeOverflow = row.endTimeOverflow;
			var resourceTime = "";
			if(startTimeOverflow == true){
				resourceTime = '<a href="#" style="color:red">' + dateFmtYYYYMMDD(startTime) + '</a>';
			}
			else{
				resourceTime = dateFmtYYYYMMDD(startTime);
			}
			if(endTimeOverflow == true){
				resourceTime = resourceTime + "~" + '<a href="#" style="color:red">' + dateFmtYYYYMMDD(endTime) + '</a>';
			}
			else{
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
			if(startTimeOverflow == true){
				childPostposeTime = '<a href="#" style="color:red">' + planStartTime	+ '</a>';
			}
			else{
				childPostposeTime = planStartTime;
			}
			if(endTimeOverflow == true){
				childPostposeTime = childPostposeTime + "~" + '<a href="#" style="color:red">' + planEndTime	+ '</a>';
			}
			else{
				childPostposeTime = childPostposeTime + "~" + planEndTime;
			}
			return childPostposeTime;
		}
	
	function closePlan() {
		$.fn.lhgdialog("closeSelect");
		
		
	}
	
	
	function initChangeVo() {
		$.ajax({
			type : 'POST',
			url : 'planChangeMassController.do?planChangeVo',
			async : false,
			data : {
			},
			success : function(data) {
				$("#planChangeVo").datagrid("loadData",data);
				//$('#focusFive').focus();
			} 
		});
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
			createDialog('resourceDialog',"resourceLinkInfoController.do?goToUsedRateReport&resourceId="+ row.resourceId 
					+ "&startTime=" + row.planStartTime + "&endTime=" + row.planEndTime);				
		} else {
			tip('<spring:message code="com.glaway.ids.pm.project.plan.resource.emptyUseInfo"/>');
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
	
	
	function delMilestoneTwo(val, row, value){
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
	function viewUserRealNameThree(val, row, index) {
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
	
</script>
</head>
<body>
    <input id="userList" name="userList" type="hidden" value="${userList}">
    <input id="planLevelList" name="planLevelList" type="hidden" value="${planLevelList}">
	<fd:tabs id="planTabs" tabPosition="top" fit="true">
		<fd:tab title="变更原因" id="planBasic"></fd:tab>
		<fd:tab title="计划详情" id="input"></fd:tab>
		<fd:tab title="关联资源" id="document"></fd:tab>
		<fd:tab title="影响分析" id="resource"></fd:tab>
		<fd:tab title="变更对比" id="resource2"></fd:tab>
	</fd:tabs>
	<fd:dialog id="resourceDialog" width="900px" height="800px" modal="true" title="{com.glaway.ids.pm.project.plan.resourcePicture}" maxFun="true">
	</fd:dialog>
</body>