<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
	<style>

		input[disabled]{background:#f0f0f0; border:1px solid #f0f0f0}
		.save_info{
			text-align:right;padding-right: 40px;padding-bottom: 10px
		}

	</style>
	<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
	<script type="text/javascript">
	var tabUrlObj = new Object();
	var tabTitleArr = new Array();
		$(function($) {
			if($('#id').val() != "" && $('#id').val() != undefined ){
				debugger
			/* 	$('#tt').tabs('enableTab', 1); 
				$('#tt').tabs('enableTab', 2); 
				$('#tt').tabs('enableTab', 3); 
				$('#tt').tabs('enableTab', 4); */
				var tab = $('#tt').tabs('getTab',2);
				if(tab != null  && '${isSave}' != "true"){
					$('#tt').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>');
				}else{
					if('${plan_.taskNameType}'!='') {
						$.ajax({
							url: 'taskFlowResolveController.do?getTabsByTaskNameTypeForFlowChange',
							data: {'taskNameType': '${plan_.taskNameType}'},
							type: "POST",
							success: function(data) {
								var jsonResult = $.parseJSON(data);
								tabTitleArr = new Array();
								if(jsonResult.success) {
									if(jsonResult.obj!=null && jsonResult.obj.length>0) {
										for(var i=0; i<jsonResult.obj.length; i++) {
											tabUrlObj[jsonResult.obj[i].id] = jsonResult.obj[i].href + '&templateId='+'${parentPlanId}' + '&cellId=' + '${cellId}' + '&id=' + '${plan_.id}'+'&nameStandardId=${nameStandardId}' + '&oldId=${plan_.planId}'
											/* if(jsonResult.obj[i].title == '参考'){
												$('#tt').tabs('add',{
													title: '<spring:message code="com.glaway.ids.pm.project.plan.reference"/>',
													id: 'cellReference',
													selected: 0,
													href: 'taskFlowResolveController.do?goChangeTab&tabIndex=3&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}'
												});
												tabTitleArr.push('<spring:message code="com.glaway.ids.pm.project.plan.reference"/>');
											}else{ */
												var subUrl = '&isEnableFlag=${isEnableFlag}&isChangeProcessView=${isChangeProcessView}';
												if('${isChangeProcessView}' == 'true'){
													subUrl = subUrl + '&tempTemplateId=${templateId}&tempTemplateNodeId=${templateNodeId}';
												}
												$('#tt').tabs('add',{
													title: jsonResult.obj[i].title,
													id: jsonResult.obj[i].id,
													selected: 0,
													onLoad : 1,
													//href: jsonResult.obj[i].href + '&templateId='+'${templateId}' + '&cellId=' + '${cellId}' + '&id=' + '${tcbi.id}'
													href: 'taskFlowTemplateController.do?goFlowTempNodeTabPage&id='+jsonResult.obj[i].id + subUrl
												});
												tabTitleArr.push(jsonResult.obj[i].title);
										/* 	} */
										}
									}
									else {
										var parentPlanId = $("#parentPlanId").val();
										var projectId = $("#projectId").val();
										var enterType = $("#enterType").val();
										$.ajax({
											url: 'tabCombinationTemplateController.do?goTabView2',
											data: {
												"planId" : parentPlanId,
												"fromDetailType" : 'mxgraphPlanChange',
												"isEnableFlag" : '${isEnableFlag}'
											},
											type: "POST",
											success: function(data) {
													var json = $.parseJSON(data);
													if(json.success) {
														var obj = json.obj;
														var selected = 0 ;
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
																if(enterType=='planChangeStart'){
																	//通过分解按钮进入的：
																	if(obj[i].displayUsage!='1'){
																		var curUrl = obj[i].url;
																		var planId= "{plan_.planId}";
																		var pladIdValue = "${plan_.planId}";
																		var projectId = "{plan_.projectId}";
																		var projectIdValue = "${plan_.projectId}";
																		curUrl = curUrl.replace(/{plan_.id}/g,pladIdValue).replace(/{plan_.projectId}/g,projectIdValue);
																		curUrl = curUrl +"&isEnableFlag=${isEnableFlag}";
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
																					href: 'tabCombinationTemplateController.do?goTabCommonDetail&enterType=0&id=${projectId}&isStandard=${isStandard}&showBasicButtom=true&isEnableFlag=${isEnableFlag}&fromDetailType=mxgraphPlanChange&planId=${plan_.id}&enterType=0&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
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
																				href: 'tabCombinationTemplateController.do?goTabCommonDetail&enterType=0&id=${projectId}&isStandard=${isStandard}&showBasicButtom=true&isEnableFlag=${isEnableFlag}&fromDetailType=mxgraphPlanChange&planId=${plan_.id}&enterType=0&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
																			});
																			selected = 0;
																		}
																	}

																}else{
																	//通过待办或其他进入的：
																	if(obj[i].displayUsage!='1'){
																		debugger;
																		var curUrl = obj[i].url;
																		var planId= "{plan_.planId}";
																		var pladIdValue = "${plan_.planId}";
																		var projectId = "{plan_.projectId}";
																		var projectIdValue = "${plan_.projectId}";
																		curUrl = curUrl.replace(/{plan_.id}/g,pladIdValue).replace(/{plan_.projectId}/g,projectIdValue);
																		curUrl = curUrl +"&isEnableFlag=${isEnableFlag}";
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
																					href: 'applyFlowResolveForChangeController.do?goTabCommonDetailChange&cellId=${cellId}&enterType=0&id=${projectId}&isStandard=${isStandard}&showBasicButtom=true&isEnableFlag=${isEnableFlag}&fromDetailType=mxgraphPlanChange&parentPlanId='+parentPlanId+'&enterType=0&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
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
																				href: 'applyFlowResolveForChangeController.do?goTabCommonDetailChange&cellId=${cellId}&enterType=0&id=${projectId}&isStandard=${isStandard}&showBasicButtom=true&isEnableFlag=${isEnableFlag}&fromDetailType=mxgraphPlanChange&parentPlanId='+parentPlanId+'&enterType=0&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
																			});
																			selected = 0;
																		}
																	}
																}
															}
														}
													}
												}
											});

									}
									/* $('#tt').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>'); */
								}
							}
						});
					}
				}
			}
			if($('#fromTemplate').val() == "true"){
				$("#planName").attr("disabled", "true");
				$("#planStartTime").attr("disabled", "true");
				$("#workTime").attr("disabled", "true");
				$("#planEndTime").attr("disabled", "true");
				$("#ownerDept").attr("disabled", "true");
			}
			var cellId = '${cellId}';
			var combCellName = '${plan_.planName}';
			var refDuration = '${plan_.workTime}';
			if(''==combCellName){
				combCellName = '任务名称';
				refDuration = 1;
			}
			window.parent.cellAttributeChange(cellId,combCellName,refDuration);
			//如果选中为必填，需要改变字体颜色
			if('true'=='${plan_.required}'){
				window.parent.changeFontColor('red');
			}else{
				window.parent.changeFontColor('black');
			}

			if("1" == $("#isEnableFlag").val()){
				$("#saveNodeInfo").hide();
				$("#planName").attr("disabled", "true");
				$("#owner").attr("disabled", "true");
				$("#planLevel").attr("disabled", "true");
				$("#taskNameType").attr("disabled", "true");
				$("#preposePlans").attr("disabled", "true");
				$("#remark").attr("disabled", "true");
				/* $("#preposePlans").attr("readonly", "true"); */
			}
		});

		function saveNodeInfo(){
			debugger
			if("1" == $("#isEnableFlag").val()){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
				return;
			}
			var id = $('#id').val();
			var cellId = $('#cellId').val();
			var parentPlanId = $('#parentPlanId').val();
			var planName;
			if ("${isStandard}" == "false") {
				planName = $('#planName').val();
			} else {
				planName = $('#planName').combobox('getText');
			}
			var workTime = $('#workTime').val();
			var taskNameType = $('#taskNameType').combobox('getValue');
			if (planName == "") {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.emptyName"/>');
				return false;
			}
			if(planName.length>30){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.nameLength"/>');
				return false;
			}

			if (workTime == "") {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.emptyPeriod"/>');
				return false;
			}
			$.ajax({
				type : 'POST',
				url : 'planController.do?checkTabCombinationTemplateIsExist',
				data : {
					activityId : taskNameType
				},
				cache : false,
				success : function (data) {
					var d = $.parseJSON(data);
					if(d.success){
						var tabCbTemplateId = d.obj;
						$.ajax({
							url : 'applyFlowResolveForChangeController.do?saveFlowTaskForChange',
							type : 'post',
							data : {
								id : id,
								cellId : cellId,
								parentPlanId : parentPlanId,
								planName : planName,
								workTime : workTime,
								taskNameType:taskNameType,
								tabCbTemplateId:tabCbTemplateId
							},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								if(d.success == true){
									window.parent.saveSuccess(cellId, planName, workTime);
									//如果选中为必填，需要改变字体颜色
									window.parent.saveGraph();
									debugger;
									if(d.obj != undefined && d.obj != ''){
										var baseInfoId = d.obj;
										if (baseInfoId != undefined && baseInfoId != '') {
											$("#id").val(baseInfoId);
											var index0Tab = $('#tt').tabs('getSelected');
											index0Tab.panel('refresh', 'taskFlowResolveController.do?goChangeTab&tabIndex=0&cellId='+cellId+'&parentPlanId='+parentPlanId+'&isEnableFlag='+$("#isEnableFlag").val()+'&isSave=true');
										}
									}
								}
								else{
									var msg = d.msg.split('<br/>')
									top.tip(msg[0]);
								}
							}
						});
					}else{
						top.tip("当前计划类型不存在启用的页签组合模板");
					}
				}
			});
		}

 		/**
		 * 选择外部前置页面
		 */
		 /*	function selectPreposePlan(hiddenId, textId, url) {
			if ($('#projectId').val() != "") {
				url = url + '&projectId=' + $('#projectId').val();
			}
			if ($('#parentPlanId').val() != "") {
				url = url + '&parentPlanId=' + $('#parentPlanId').val();
			}
			if ($('#id').val() != "") {
				url = url + '&id=' + $('#id').val();
			}
			//createPopupwindow('项目计划', url, '', '', hiddenId, textId);
			createDialog('preposePlanDialog',url);
		}

		function preposePlanDialog(iframe){
	    	var preposeIds;
    		var preposePlans;
    		var preposeEndTime;
    		var selectedId = iframe.planPreposeList.getSelectedRowId();
    		if(selectedId != undefined && selectedId != null && selectedId != '' ){
    			var selectedIdArr = selectedId.split(",");
    			if(selectedIdArr.length > 0){
	  				for(var i=0;i<selectedIdArr.length;i++){
	  					var id = selectedIdArr[i];
	    				if(preposeIds != null && preposeIds != '' && preposeIds != undefined){
			    			preposeIds = preposeIds + ',' + id;
			    		}else{
			    			preposeIds = id;
			    		}
	    				var planName = iframe.planPreposeList.getRowAttribute(id,'displayName');
			    		if(preposePlans != null && preposePlans != '' && preposePlans != undefined){
			    			preposePlans = preposePlans + ',' + planName;
			    		}else{
			    			preposePlans = planName;
			    		}
			    		var planEndTime = iframe.planPreposeList.getRowAttribute(id,'planEndTime');
			    		if(preposeEndTime != null && preposeEndTime != '' && preposeEndTime != undefined){
			    			if(preposeEndTime < planEndTime){
			    				preposeEndTime = planEndTime;
			    			}
			    		}else{
			    			preposeEndTime = planEndTime;
			    		}
	    			}
    			}
   			}
    		if (preposeIds != null && preposeIds != ''
				&& preposeIds != undefined) {
			$('#preposeIds').val(preposeIds);
		    } else {
			$('#preposeIds').val('');
		    }
		    if (preposePlans != null && preposePlans != ''
			&& preposePlans != undefined) {
		    $('#preposePlans').textbox("setValue",preposePlans);
	        } else {
		    $('#preposePlans').textbox("setValue","");
	        }

		}
		*/

		/**国际化转移方法**/
		// 工期联动
		/* function workTimeLinkage(inputType) {
			var win = $.fn.lhgdialog("getSelectParentWin");
			var planStartTime = $('#planStartTime').datebox('getValue');
			var workTime = $('#workTime').val();
			var planEndTime = $('#planEndTime').datebox('getValue');
			var projectId = $('#projectId').val();
			if (inputType == 'planEndTime') {
				inputType1 = inputType;
			}
			if (inputType == 'workTime') {
				inputType2 = inputType;
			}

			if (inputType1 == 'planEndTime' && inputType2 == 'workTime') {
				inputType1 = undefined;
				inputType2 = undefined
				return false;
			}

			if (inputType == 'planStartTime') {
				if (planStartTime != null && planStartTime != ''
						&& planStartTime != undefined) {
					if (workTime != null && workTime != ''
							&& workTime != undefined) {
						if (workTime == 0) {
							$('#workTime').textbox("setValue", "");
							win.tip('<spring:message code="com.glaway.ids.pm.project.plan.periodNotZero"/>');
						} else {
							$
									.ajax({
										url : 'planController.do?getEndTimeByStartTimeAndWorkTime',
										type : 'post',
										data : {
											projectId : projectId,
											planStartTime : planStartTime,
											workTime : workTime
										},
										cache : false,
										success : function(data) {
											if (data != null) {
												var d = $.parseJSON(data);
												if (d.success == true) {
													planEndTime = d.obj;
													$('#planEndTime').datebox(
															"setValue",
															planEndTime);
												} else {
													$('#planStartTime')
															.datebox(
																	"setValue",
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
											planEndTime : planEndTime
										},
										cache : false,
										success : function(data) {
											if (data != null) {
												var d = $.parseJSON(data);
												if (d.success == true) {
													workTime = d.obj;
													$('#workTime').textbox(
															"setValue",
															workTime);
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
						if (workTime == 0) {
							$('#workTime').textbox("setValue", "");
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
											workTime : workTime
										},
										cache : false,
										success : function(data) {
											if (data != null) {
												var d = $.parseJSON(data);
												if (d.success == true) {
													planEndTime = d.obj;
													$('#planEndTime').datebox(
															"setValue",
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
											planEndTime : planEndTime
										},
										cache : false,
										success : function(data) {
											if (data != null) {
												var d = $.parseJSON(data);
												if (d.success == true) {
													workTime = d.obj;
													if (workTime == 0) {
														$('#workTime').textbox(
																"setValue", "");
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
		} */

		function getPlanNamechange(isStandard){
			var planName = $('#planName').combobox('getValue');
			if('false' != isStandard && planName != ''){
				$.ajax({
					url : 'planController.do?getTaskNameType',
					type : 'post',
					data : {
						planName : planName
					},
					cache : false,
					success : function(data) {
						if (data != null) {
							if (d.success == true) {
	                            var taskNameType = $('#taskNameType')
	                                .combobox("getValue");
	                            if(d.obj != "" && d.obj != null && d.obj != 'null'){
	                                if(d.obj != taskNameType){
	                                    $("#fromType").val("planNameChange");
	                                    $('#taskNameType')
	                                        .combobox("setValue",
	                                            d.obj);
	                                }
	                            }

							} else {
							}
						} else {
							win.tip(d.msg);
						}
					}
				});
			}

		}


		function taskNameTypeChange(isStandard){
		    //启用活动名称库，计划类型的值改变才需要清空计划名称的值，否则不需要
	        if(('true' == isStandard || 'ok' == isStandard)){
	            var taskNameType = $("#taskNameType").combobox('getValue');
	            var fromType = $("#fromType").val();
	            if(fromType == '' || fromType == ""){
	                $("#planName").combobox('setText','');
	                $("#planName").combobox('setValue','');
	                $('#planName').combobox({
	                    url : encodeURI(encodeURI('planController.do?standardValueExceptDesign&activeCategory='+taskNameType)),
	                    onLoadSuccess : function() {

	                    }
	                });
	            }


	            $("#fromType").val('');
	        }

	    }

	</script>

	<div border="false" class="easyui-panel div-msg" id="baseInfo" fit="true">
		<div class="easyui-panel" fit="true">
			<fd:form id="baseInfoFrm">
			<input id="isStandard" name="isStandard" type="hidden" value="${isStandard}">
			<input type="hidden" name="cellId" id="cellId" value="${cellId}" />
			<input type="hidden" name="parentPlanId" id="parentPlanId" value="${parentPlanId}" />
			<input type="hidden" id ='isEnableFlag' name='isEnableFlag' value="${isEnableFlag}"/>
			<input type="hidden" name="id" id="id" value="${plan_.id}" />
			<input type="hidden" name="fromTemplate" id="fromTemplate" value="${plan_.fromTemplate}" />
			<input type="hidden" name="fromType" id="fromType" value="" />

			<c:if test="${isEnableFlag !='1' && empty plan_.id}">
				<fd:combobox id="taskNameType" textField="name" title="{com.glaway.ids.pm.project.task.taskNameType}" selectedValue=""
	                     editable="false" valueField="id" onChange="taskNameTypeChange('${isStandard}')" panelMaxHeight="200"
	                     required="true" url="planController.do?getTaskNameTypes" value="${plan_.taskNameType}"/>

				<c:choose>
					<c:when test="${isStandard=='ok'}">
						<fd:combobox title="{com.glaway.ids.pm.project.task.taskname}" id="planName" textField="name"
							editable="true" valueField="id" required="true" selectedValue="${plan_.planName}"
							panelMaxHeight="200" onChange="getPlanNamechange('${isStandard}')"
							url="taskFlowResolveController.do?standardValue"></fd:combobox>
					</c:when>
					<c:when test="${isStandard=='true'}">
						<fd:combobox title="{com.glaway.ids.pm.project.task.taskname}" id="planName" textField="name"
							editable="true" valueField="id" selectedValue="${plan_.planName}"
							validType="comboboxDistinctValidate['planName']"
							panelMaxHeight="200" onChange="getPlanNamechange('${isStandard}')"
							url="taskFlowResolveController.do?standardValue" maxLength="30" required="true"></fd:combobox>
					</c:when>
					<c:otherwise>
						<fd:inputText id="planName" name="name" required="true"
							title="{com.glaway.ids.pm.project.task.taskname}" value="${plan_.planName}"></fd:inputText>
					</c:otherwise>
				</c:choose>

				<fd:inputText id="workTimeReference" name="workTimeReference"
					title="{com.glaway.ids.pm.project.task.workTimeReference}"
					value="${plan_.workTimeReference}" readonly="true"></fd:inputText>

				<fd:selectBooleanCheckbox id="required" name="required" title="{com.glaway.ids.pm.project.task.requiredTask}" value="${plan_.required}" disabled="true"/>

				<fd:inputTextArea id="remark" name="remark" title="{com.glaway.ids.pm.project.task.remark}" value="${plan_.remark}" readonly="true"></fd:inputTextArea>

			</c:if>

			<c:if test="${isEnableFlag=='1' || ! empty plan_.id}">
				<fd:combobox id="taskNameType" textField="name" title="{com.glaway.ids.pm.project.task.taskNameType}" selectedValue=""
	                     editable="false" valueField="id" panelMaxHeight="200" readonly="true"
	                     required="true" url="planController.do?getTaskNameTypesAll" value="${plan_.taskNameType}"/>

				<c:choose>
					<c:when test="${isStandard=='ok'}">
						<fd:combobox title="{com.glaway.ids.pm.project.task.taskname}" id="planName" textField="name"
							editable="true" valueField="id" required="true" selectedValue="${plan_.planName}"
							panelMaxHeight="200" readonly="true"
							url="taskFlowResolveController.do?standardValue"></fd:combobox>
					</c:when>
					<c:when test="${isStandard=='true'}">
						<fd:combobox title="{com.glaway.ids.pm.project.task.taskname}" id="planName" textField="name"
							editable="true" valueField="id" selectedValue="${plan_.planName}"  readonly="true"
							validType="comboboxDistinctValidate['planName']"
							panelMaxHeight="200"
							url="taskFlowResolveController.do?standardValue" maxLength="30" required="true"></fd:combobox>
					</c:when>
					<c:otherwise>
						<fd:inputText id="planName" name="name" required="true" readonly="true"
							title="{com.glaway.ids.pm.project.task.taskname}" value="${plan_.planName}"></fd:inputText>
					</c:otherwise>
				</c:choose>

				<fd:inputText id="workTimeReference" name="workTimeReference"
					title="{com.glaway.ids.pm.project.task.workTimeReference}"
					value="${plan_.workTimeReference}" readonly="true"></fd:inputText>

				<fd:selectBooleanCheckbox id="required" name="required" title="{com.glaway.ids.pm.project.task.requiredTask}" value="${plan_.required}" disabled="true"/>

				<fd:inputTextArea id="remark" name="remark" title="{com.glaway.ids.pm.project.task.remark}" value="${plan_.remark}" readonly="true"></fd:inputTextArea>

			</c:if>
			<div style="display: none;">
	       		 <fd:inputText id="workTime"   title="{com.glaway.ids.pm.project.task.workTime}" value='${plan_.workTime == 0 ? 1 :  plan_.workTime }' required="true" validType="regExp['/^[0-9\s]{1,30}$/','该输入项为正整数']"/>
	        </div>
			</fd:form>

		</div>

		<c:if test="${isEnableFlag !='1' && empty plan_.id}">
			<div class="div-msg-btn">
				<div>
					<fd:linkbutton value="{com.glaway.ids.common.btn.save}" classStyle="button_nor" id="saveNodeInfo" onclick="saveNodeInfo()"/>
				</div>
			</div>
		</c:if>
	</div>

	<fd:dialog id="preposePlanDialog" width="800px" height="580px" modal="true"  title="{com.glaway.ids.pm.project.task.selectOutPrepose}" zIndex="4200">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="preposePlanDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>