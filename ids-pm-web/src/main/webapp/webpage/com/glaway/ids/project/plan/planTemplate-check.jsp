<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划查看</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<style type="text/css">
		.tabs-panels{ padding-top:5px}

	</style>
</head>
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
			/* setTimeout(function() {
				oldPlanTime();
			}, 500); */
		});
		$('#planTabs').tabs({
			onSelect : function(title) {
				$('#planTabs .panel-body').css('width', 'auto');
			}
		});
		$(".tabs-wrap").css('width', '100%');
	});
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
					debugger;
					var jsonResult = $.parseJSON(data);
					if(jsonResult.success) {
						if(jsonResult.obj!=null && jsonResult.obj.length>0) {
							for(var i=0; i<jsonResult.obj.length; i++) {
								$('#planTabs').tabs('add',{
									title: jsonResult.obj[i].title,
									id: jsonResult.obj[i].id,
									selected: 0,
									href: jsonResult.obj[i].href + '&id=${plan_.id}&nameStandardId=${nameStandardId}&templateId=${plan_.parentPlanId}&planStatus=${plan_.bizCurrent}'
								});
							}
						}
						$('#planTabs').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>');
					}
				}
			});
		}else{
			var type='${type}';
			$('#planTabs').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.input"/>',
				id: 'input',
				selected: 0,
				href: 'planTemplateController.do?goTemplateInputCheck&useObjectType=${useObjectType}&useObjectId=${planId}&planTemplateId=${planTemplateId}'
			});
			
			 $('#planTabs').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.output"/>',
				id: 'document',
				selected: 0,
				href: 'planTemplateController.do?goDocumentCheck&useObjectType=${useObjectType}&useObjectId=${planId}&planTemplateId=${planTemplateId}&projectId=${projectId}'
			});
			
/* 			$('#planTabs').tabs('add',{
				title: '<spring:message code="com.glaway.ids.pm.project.plan.resource"/>',
				id: 'resource',
				selected: 0,
				href: 'resourceLinkInfoController.do?goResourceCheck&useObjectType=PLAN&useObjectId=${plan_.id}'
			}); */
			
			if(type == 'planChangeAnalysis'){
				$('#planTabs').tabs('add',{
					title: '变更记录',
					id: 'changeRecord',
					selected: 0,
					href: 'planChangeController.do?goPlanChangeRecord&useObjectType=PLAN&useObjectId=${plan_.id}'
				});
			}
			
			$('#planTabs').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>'); 
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
		if(workTime1==$('#workTime').val() && planStartTime1==$('#planStartTime').datebox('getValue') && planEndTime1==$('#planEndTime').datebox('getValue'))
			{
				return false;
			}
		else { return true;}
	}
	
</script>

<body>
	<fd:tabs id="planTabs" tabPosition="top" fit="true">
		<fd:tab href="planTemplateController.do?goBasicCheck&planTemplateId=${planTemplateId}&planId=${planId}&projectTemplateId=${projectTemplateId }" 
			title="{com.glaway.ids.pm.project.plan.baseinfo}" id="planBasic"></fd:tab>
		<%-- <fd:tab href="inputsController.do?goInputCheck&useObjectType=PLAN&useObjectId=${plan_.id}" 
			title="{com.glaway.ids.pm.project.plan.input}" id="input"></fd:tab>
		<fd:tab href="deliverablesInfoController.do?goDocumentCheck&useObjectType=PLAN&useObjectId=${plan_.id}" 
			title="{com.glaway.ids.pm.project.plan.output}" id="document"></fd:tab>
		<fd:tab href="resourceLinkInfoController.do?goResourceCheck&useObjectType=PLAN&useObjectId=${plan_.id}"  
			title="{com.glaway.ids.pm.project.plan.resource}" id="resource"></fd:tab> --%>
	</fd:tabs>
</body>
</html>