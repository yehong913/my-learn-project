<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>项目计划查看</title>
	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function() {
			// 页面初始化  
			debugger;
			$(function() {
				setTimeout(function() {
					openTab();
				}, 500);
				// 获得初始计划 开始时间，结束时间，工期
				setTimeout(function() {
					oldPlanTime();
				}, 500);
			});
			$('#tt').tabs({
				onSelect : function(title) {
					$('#tt .panel-body').css('width', 'auto');
				}
			});
			$(".tabs-wrap").css('width', '100%');
		});


		//参考判断js
		var isReference = true;
		function showReferenceTab(){
			if(isReference && '${temporaryPlan_.bizCurrent}'!="EDITING"){
				return true;
			}else{
				return false;
			}
		}

		//风险清单判断js
		var isRiskList = true;
		function showRiskListTab(){
			if(isRiskList && '${temporaryPlan_.bizCurrent}'!="EDITING"){
				return true;
			}else{
				return false;
			}
		}

		//问题判断js
		var isProblem = true;
		function showProblemTab(){
			if(isProblem && '${temporaryPlan_.bizCurrent}'!="EDITING"){
				return true;
			}else{
				return false;
			}
		}

		//评审判断js
		var isReview = true;
		function showReviewTab(){
			if(isReview && '${temporaryPlan_.bizCurrent}'!="EDITING"){
				return true;
			}else{
				return false;
			}
		}


		function openTab() {
			debugger;
			if('${isOut}' == '1'){
				$.ajax({
					url: 'taskFlowResolveController.do?getTabsByTaskNameType',
					data: {
						useCode : 'viewPlanInfo'
					},
					type: "POST",
					success: function(data) {
						var jsonResult = $.parseJSON(data);
						if(jsonResult.success) {
							if(jsonResult.obj!=null && jsonResult.obj.length>0) {
								for(var i=0; i<jsonResult.obj.length; i++) {
									$('#tt').tabs('add',{
										title: jsonResult.obj[i].title,
										id: jsonResult.obj[i].id,
										selected: 0,
										onLoad : 1,
										href: jsonResult.obj[i].href + '&id=${temporaryPlan_.id}&nameStandardId=${nameStandardId}&templateId=${temporaryPlan_.parentPlanId}&planStatus=${temporaryPlan_.bizCurrent}'
									});
								}
							}
							$('#tt').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>');
						}
					}
				});
			}else{
				var type='${type}';
				$.ajax({
					url: 'tabCombinationTemplateController.do?goTabView2',
					data: {
						planId : '${temporaryPlan_.id}'/* ,
					displayAccess:'0' */
					},
					type: "POST",
					success: function(data) {
						var json = $.parseJSON(data);
						if(json.success) {
							var obj = json.obj;
							var selected = 1 ;
							for(var i=0;i<obj.length;i++){
								var endFlag = true;
								if(obj[i].displayAccess != "" && obj[i].displayAccess != null){
									try {
										endFlag = eval(obj[i].displayAccess);
									} catch (e) {
										endFlag = true;
									}
								}
								if(endFlag){
									if(obj[i].displayUsage!='1'){
										var curUrl = obj[i].url;
										var planId= "{temporaryPlan_.id}";
										var pladIdValue = "${temporaryPlan_.id}";
										var projectId = "{temporaryPlan_.projectId}";
										var projectIdValue = "${temporaryPlan_.projectId}";
										curUrl = curUrl.replace(/{temporaryPlan_.id}/g,pladIdValue).replace(/{temporaryPlan_.projectId}/g,projectIdValue);

										$('#tt').tabs('add',{
											title: obj[i].name,
											id: obj[i].typeId,
											selected: selected,
											onLoad : 1,
											href: curUrl
										});
										selected = 0;
									}else{
										if(obj[i].projectModel == "${riskproblems_service}"){
											if('${isRiskPluginValid}' == 'true'){
												$('#tt').tabs('add',{
													title: obj[i].name,
													id: obj[i].typeId,
													selected: selected,
													onLoad : 1,
													href: 'tabCombinationTemplateController.do?goTabCommonDetail&onlyReadonly=1&id=${temporaryPlan_.projectId}&planId=${temporaryPlan_.id}&enterType=0&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
												});
												selected = 0;
											}
										}
										else{
											$('#tt').tabs('add',{
												title: obj[i].name,
												id: obj[i].typeId,
												selected: selected,
												onLoad : 1,
												href: 'tabCombinationTemplateController.do?goTabCommonDetail&onlyReadonly=1&id=${temporaryPlan_.projectId}&planId=${temporaryPlan_.id}&enterType=0&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
											});
											selected = 0;
										}
									}
								}
							}
						}
					}
				});
				/* 			$('#tt').tabs('add',{
                                title: '<spring:message code="com.glaway.ids.pm.project.plan.input"/>',
				id: 'input',
				selected: 0,
				href: 'inputsController.do?goInputCheck&useObjectType=PLAN&useObjectId=${temporaryPlan_.id}&projectId=${projectId}'
			});
			
			 $('#tt').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.output"/>',
				id: 'document',
				selected: 0,
				href: 'deliverablesInfoController.do?goDocumentCheck&useObjectType=PLAN&useObjectId=${temporaryPlan_.id}'
			});
			
			$('#tt').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.resource"/>',
				id: 'resource',
				selected: 0,
				href: 'resourceLinkInfoController.do?goResourceCheck&useObjectType=PLAN&useObjectId=${temporaryPlan_.id}'
			}); */

				if(type == 'planChangeAnalysis'){
					$('#tt').tabs('add',{
						title: '变更记录',
						id: 'changeRecord',
						selected: 0,
						onLoad : 1,
						href: 'planChangeController.do?goPlanChangeRecord&useObjectType=PLAN&useObjectId=${temporaryPlan_.id}'
					});
				}

				/* 			$('#tt').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>');  */
			}
		}
		/**国际化js转移过来的方法**/
		var inputTypeTotal = "1";
		function workTimeLinkage(inputType) {
			debugger;
			if (inputType == 'planStartTime') {
				inputTypeTotal = inputTypeTotal + "1";
			}
			if (inputType == 'planEndTime') {
				inputTypeTotal = inputTypeTotal + "2";
			}
			if (inputType == 'workTime') {
				inputTypeTotal = inputTypeTotal + "3";
			}
			var win = $.fn.lhgdialog("getSelectParentWin");
			var planStartTime = $('#planStartTime').datebox('getValue');
			var workTime = $('#workTime').val();
			var planEndTime = $('#planEndTime').datebox('getValue');
			var projectId = $('#projectId').val();
			var milestone = $('#milestone').textbox('getValue');
			if (milestone == "否") {
				milestone = "false";
			} else if (milestone == "是") {
				milestone = "true";
			}
			/* if (inputType == 'planEndTime') {
                inputType1 = inputType;
            }
            if (inputType == 'workTime') {
                inputType2 = inputType;
            }
            var inputTypeTotalIndex = inputTypeTotal.indexOf('123');
            if (inputType1 == 'planEndTime' && inputType2 == 'workTime'
                    && inputTypeTotalIndex < 0) {
                inputType1 = undefined;
                inputType2 = undefined
                return false;
            } */
			if (inputType == 'planStartTime') {
				if (planStartTime != null && planStartTime != ''
						&& planStartTime != undefined) {
					if (workTime != null && workTime != '' && workTime != undefined) {
						if (workTime == 0 && milestone != "true") {
							$('#workTime').textbox("setValue", "1");
							win
									.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
						} else {
							$
									.ajax({
										url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
										type : 'post',
										data : {
											projectId : projectId,
											planStartTime : planStartTime,
											planEndTime : planEndTime,
											workTime : workTime,
											milestone : milestone
										},
										cache : false,
										success : function(data) {
											if (data != null) {
												var d = $.parseJSON(data);
												if (d.success == true) {
													planEndTime = d.obj;
													$('#planEndTime')
															.datebox("setValue",
																	planEndTime);
												} else {
													$('#planStartTime')
															.datebox("setValue",
																	planEndTime);
													win.tip(d.msg);
												}
											} else {
												win
														.tip('<spring:message code="com.glaway.ids.pm.project.plan.endTimeError"/>');
											}
										}
									});
						}
					} else if (planEndTime != null && planEndTime != ''
							&& planEndTime != undefined) {
						if (planEndTime < planStartTime) {
							$('#planEndTime').datebox("setValue", "");
						} else {
							$
									.ajax({
										url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
										type : 'post',
										data : {
											projectId : projectId,
											planStartTime : planStartTime,
											planEndTime : planEndTime,
											workTime : workTime,
											milestone : milestone
										},
										cache : false,
										success : function(data) {
											if (data != null) {
												var d = $.parseJSON(data);
												if (d.success == true) {
													workTime = d.obj;
													$('#workTime').textbox(
															"setValue", workTime);
												} else {
													win.tip(d.msg);
												}
											} else {
												win
														.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
											}
										}
									});
						}
					}
				} else {
					win
							.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
				}
			} else if (inputType == 'workTime') {
				if (workTime != null && workTime != '' && workTime != undefined) {
					if (planStartTime != null && planStartTime != ''
							&& planStartTime != undefined) {
						if (workTime == 0 && milestone != "true") {
							$('#workTime').textbox("setValue", "1");
							win
									.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
						} else {
							$
									.ajax({
										url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
										type : 'post',
										data : {
											projectId : projectId,
											planStartTime : planStartTime,
											planEndTime : planEndTime,
											workTime : workTime,
											milestone : milestone
										},
										cache : false,
										success : function(data) {
											if (data != null) {
												var d = $.parseJSON(data);
												if (d.success == true) {
													planEndTime = d.obj;
													$('#planEndTime')
															.datebox("setValue",
																	planEndTime);
												} else {
													win.tip(d.msg);
												}
											} else {
												win
														.tip('<spring:message code="com.glaway.ids.pm.project.plan.endTimeError"/>');
											}
										}
									});
						}
					} else {
						win
								.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
					}
				}
			} else if (inputType == 'planEndTime') {
				if (planEndTime != null && planEndTime != ''
						&& planEndTime != undefined) {
					if (planStartTime != null && planStartTime != ''
							&& planStartTime != undefined) {
						if (planEndTime < planStartTime) {
							win
									.tip('<spring:message code="com.glaway.ids.pm.project.plan.startNoLaterThanEnd"/>');
						} else {
							$
									.ajax({
										url : 'planController.do?getWorkTimeByStartTimeAndEndTime',
										type : 'post',
										data : {
											projectId : projectId,
											planStartTime : planStartTime,
											planEndTime : planEndTime,
											workTime : workTime,
											milestone : milestone
										},
										cache : false,
										success : function(data) {
											if (data != null) {
												var d = $.parseJSON(data);
												if (d.success == true) {
													workTime = d.obj;
													if (workTime == 0
															&& milestone != "true") {
														$('#workTime').textbox(
																"setValue", "1");
														win
																.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
													} else {
														$('#workTime').textbox(
																"setValue",
																workTime);
													}
												} else {
													win.tip(d.msg);
												}
											} else {
												win
														.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodError"/>');
											}
										}
									});
						}
					} else {
						win
								.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyStart"/>');
					}
				}
			}
		}

		var planStartTime1='';
		var workTime1='';
		var planEndTime1='';

		function oldPlanTime(){
			planStartTime1 = $('#planStartTime').datebox('getValue');
			workTime1 = $('#workTime').val();
			planEndTime1 = $('#planEndTime').datebox('getValue');
		}
		function isTimeChange(){
			alert(workTime1+"="+$('#workTime').val()+","+planStartTime1+"="+$('#planStartTime').datebox('getValue'))
			if(workTime1==$('#workTime').val() && planStartTime1==$('#planStartTime').datebox('getValue') && planEndTime1==$('#planEndTime').datebox('getValue'))
			{
				return false;
			}
			else { return true;}
		}

		function saveTimeTransient(){
			debugger;
			var flag="same";
			if(isTimeChange())  {
				var planStartTime = $('#planStartTime').datebox('getValue');
				var workTime = $('#workTime').val();
				var planEndTime = $('#planEndTime').datebox('getValue');
				var win = $.fn.lhgdialog("getSelectParentWin");
				/* alert(win.$("#planStartTime").datebox("getValue")); */
				rows = win.$('#planModifyList').treegrid('getRoots');
				for(var i=0; i< rows.length;i++)
				{
					var childs = win.$("#planModifyList").treegrid("getChildren",rows[i].id);
					//alert(rows[i].id);
					for(var j in childs)
					{
					}
				}
				var d=$('#formobj').serialize()
				$.ajax({
					type : 'post',
					url : 'planController.do?doSave',
					async : false,
					/* data : 	$('#formobj').serialize(), */
					data :{
						'id' : 	'${temporaryPlan_.id}',
						'planNumber' : '${temporaryPlan_.planNumber}',
						'projectId' : '${temporaryPlan_.projectId}',
						'parentPlanId' : '${temporaryPlan_.parentPlanId}',
						'beforePlanId' : '${temporaryPlan_.beforePlanId}',
						'planStartTime' : planStartTime,
						'planEndTime' : planEndTime,
						'bizCurrent' : '${temporaryPlan_.bizCurrent}',
						'flowStatus' : '${temporaryPlan_.flowStatus}',
						'remark' : '${temporaryPlan_.remark}',
						'planName' : '${temporaryPlan_.planName}',
						'owner' : '${temporaryPlan_.owner}',
						'planLevel' : '${temporaryPlan_.planLevel}',
						'workTime' : workTime,
						'milestone'	:'${temporaryPlan_.milestone}',
						'preposeIds' : '${temporaryPlan_.preposeIds}',
						'taskNameType' : '${temporaryPlan_.taskNameType}',
						'taskType' : '${temporaryPlan_.taskType}',
					},
					success : function(data){
						var d=$.parseJSON(data);
						if(d.success)
						{
							win.$("#planModifyList").treegrid("reload");
							flag="change";
							$.fn.lhgdialog("closeSelect");
						}
					}
				})
			}
			return flag;
		}
	</script>
</head>
<body>
<fd:tabs id="tt" tabPosition="top" fit="true">
	<%-- 		<fd:tab href="planController.do?goBasicCheck&id=${temporaryPlan_.id}" 
                title="{com.glaway.ids.pm.project.plan.baseinfo}" id="planBasic"></fd:tab> --%>
	<%-- <fd:tab href="inputsController.do?goInputCheck&useObjectType=PLAN&useObjectId=${temporaryPlan_.id}" 
        title="{com.glaway.ids.pm.project.plan.input}" id="input"></fd:tab>
    <fd:tab href="deliverablesInfoController.do?goDocumentCheck&useObjectType=PLAN&useObjectId=${temporaryPlan_.id}" 
        title="{com.glaway.ids.pm.project.plan.output}" id="document"></fd:tab>
    <fd:tab href="resourceLinkInfoController.do?goResourceCheck&useObjectType=PLAN&useObjectId=${temporaryPlan_.id}"  
        title="{com.glaway.ids.pm.project.plan.resource}" id="resource"></fd:tab> --%>
</fd:tabs>
</body>