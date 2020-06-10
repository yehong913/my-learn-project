<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
	<style>
		input[disabled]{background:#f0f0f0; border:1px solid #f0f0f0}
	</style>
	<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
	<script>
	var tabUrlObj = new Object();
		$(function($) {
			debugger
			if($('#id').val() != "" && $('#id').val() != undefined ){
				var tab = $('#tt').tabs('getTab',2);
				if(tab != null && '${change}' != "1" && '${isSave}' != "true"){
					$('#tt').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>');
				}else{
					try {
						$('#tt').tabs('close', 5);
					} catch (e) {
						// TODO: handle exception
					}
					try {
						$('#tt').tabs('close', 4);
					} catch (e) {
						// TODO: handle exception
					}
					try {
						$('#tt').tabs('close', 3);
					} catch (e) {
						// TODO: handle exception
					}
					try {
						$('#tt').tabs('close', 2);
					} catch (e) {
						// TODO: handle exception
					}
					try {
						$('#tt').tabs('close', 1);
					} catch (e) {
						// TODO: handle exception
					}
					
					if('${plan_.taskNameType}'!='') {
						$.ajax({
							url: 'taskFlowResolveController.do?getTabsByTaskNameTypeForFlow',
							data: {'taskNameType': '${plan_.taskNameType}'},
							type: "POST",
							success: function(data) {
								var jsonResult = $.parseJSON(data);
								if(jsonResult.success) {
									if(jsonResult.obj!=null && jsonResult.obj.length>0) {
										for(var i=0; i<jsonResult.obj.length; i++) {
											tabUrlObj[jsonResult.obj[i].id] = jsonResult.obj[i].href + '&templateId='+'${parentPlanId}' + '&cellId=' + '${cellId}' + '&id=' + '${plan_.id}'+'&nameStandardId=${nameStandardId}&isEnableFlag=${isEnableFlag}';
											if(jsonResult.obj[i].title == '参考' && '${isKlmPluginValid}' == 'true'){											
											}else{
												$('#tt').tabs('add',{
													title: jsonResult.obj[i].title,
													id: jsonResult.obj[i].id,
													selected: 0,
													onLoad : 1,
													href: 'taskFlowTemplateController.do?goFlowTempNodeTabPage&id='+jsonResult.obj[i].id + '&isEnableFlag=${isEnableFlag}'
												});
											}

										}
									}
									else {
										
										$.ajax({
											url: 'tabCombinationTemplateController.do?goTabView2',
											data: {
												planId : '${plan_.id}'
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
																if(obj[i].displayUsage!='1'){
																	var curUrl = obj[i].url;
																	var planId= "{plan_.id}";
																	var pladIdValue = "${plan_.id}";
																	var projectId = "{plan_.projectId}";
																	var projectIdValue = "${plan_.projectId}";
																	curUrl = curUrl.replace(/{plan_.id}/g,pladIdValue).replace(/{plan_.projectId}/g,projectIdValue);

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
																				href: 'tabCombinationTemplateController.do?goTabCommonDetail&enterType=0&id=${plan_.projectId}&isStandard=${isStandard}&showBasicButtom=true&isEnableFlag=${isEnableFlag}&fromDetailType=planResolve&planId=${plan_.id}&enterType=0&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
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
																			href: 'tabCombinationTemplateController.do?goTabCommonDetail&enterType=0&id=${plan_.projectId}&isStandard=${isStandard}&showBasicButtom=true&isEnableFlag=${isEnableFlag}&fromDetailType=planResolve&planId=${plan_.id}&enterType=0&tabId='+obj[i].typeId+'&title='+encodeURI(obj[i].name)+'&index='+i+''
																		});
																		selected = 0;
																	}
																}
															} 
														}
													}
												}
											});
										
										/* $('#tt').tabs('add',{
											title: '<spring:message code="com.glaway.ids.pm.project.plan.input"/>',
											id: 'cellInput',
											selected: 0,
											href: 'taskFlowResolveController.do?goTab&tabIndex=2&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}'
										});
										$('#tt').tabs('add',{
											title: '<spring:message code="com.glaway.ids.pm.project.plan.output"/>',
											id: 'cellOutput',
											selected: 0,
											href: 'taskFlowResolveController.do?goTab&tabIndex=4&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}'
										});
										$('#tt').tabs('add',{
											title: '<spring:message code="com.glaway.ids.pm.project.plan.resource"/>',
											id: 'cellResource',
											selected: 0,
											href: 'taskFlowResolveController.do?goTab&tabIndex=5&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}'
										}); */
									}
									
									$('#tt').tabs('select', '<spring:message code="com.glaway.ids.pm.project.plan.baseinfo"/>');
								}
							}
						});
					}
				}
			}
			if($('#fromTemplate').val() == "true"){
				$("#planName").attr("disabled", "true");
				$("#taskNameType").attr("disabled", "true");
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
				$("#taskNameType").attr("disabled", "true");
				$("#workTimeReference").attr("disabled", "true");
				$("#remark").attr("disabled", "true");
			}
		});
	
		function saveNodeInfo(){
			debugger;
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
			var remark = $('#remark').val();			
			var taskNameType = $('#taskNameType').combobox('getValue');
			
			if (planName == "") {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.emptyName"/>');
				return false;
			}
			if(planName.length>30){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.nameLength"/>')
				return false;
			}

			var workTime = $('#workTime').val();

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
						top.jeasyui.util.commonMask('open','请稍候...');
						$.ajax({
							url : 'taskFlowResolveController.do?saveFlowTask',
							type : 'post',
							data : {
								id : id,
								cellId : cellId,
								parentPlanId : parentPlanId,
								planName : planName,
								taskNameType : taskNameType,
								workTime : workTime,
								tabCbTemplateId : tabCbTemplateId
							},
							cache : false,
							success : function(data) {
								debugger;
								var d = $.parseJSON(data);
								top.jeasyui.util.commonMask('close');
								if(d.success == true){
									window.parent.saveSuccess(cellId, planName, workTime);
									//如果选中为必填，需要改变字体颜色
									window.parent.saveGraph();
									baseInfoId = d.obj;
									if (baseInfoId != undefined && baseInfoId != '') {
										/* $('#tt').tabs('enableTab', 1);
                                        $('#tt').tabs('enableTab', 2);
                                        $('#tt').tabs('enableTab', 3);
                                        $('#tt').tabs('enableTab', 4); */
										$("#id").val(baseInfoId);
										var index0Tab = $('#tt').tabs('getSelected');
										var change = "0";
										if(planName != '${plan_.planName}'){
											change = "1";
										}else{
											change = "0";
										}
										try {
											$('#tt').tabs('close', 5);
										} catch (e) {
											// TODO: handle exception
										}
										try {
											$('#tt').tabs('close', 4);
										} catch (e) {
											// TODO: handle exception
										}
										try {
											$('#tt').tabs('close', 3);
										} catch (e) {
											// TODO: handle exception
										}
										try {
											$('#tt').tabs('close', 2);
										} catch (e) {
											// TODO: handle exception
										}
										try {
											$('#tt').tabs('close', 1);
										} catch (e) {
											// TODO: handle exception
										}
										index0Tab.panel('refresh', 'taskFlowResolveController.do?goTab&tabIndex=0&cellId=${cellId}&parentPlanId=${parentPlanId}&isSave=true&change='+change);
									}
								}
								else{
									var msg = d.msg.split('<br/>')
									top.tip(msg[0]); // John
								}
							}
						});


					}else{
						top.tip("当前计划类型不存在启用的页签组合模板");
					}
				}
			});
			

		}
			
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

<div border="false" class="easyui-panel div-msg" fit="true">
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
				
				<fd:inputTextArea id="remark" name="remark" title="{com.glaway.ids.pm.project.task.remark}" value="${plan_.remark}"  readonly="true"></fd:inputTextArea>
				
			</c:if>
			<div style="display: none;">
	       		 <fd:inputText id="workTime"   title="{com.glaway.ids.pm.project.task.workTime}" value='${plan_.workTime == 0 ? 1 :  plan_.workTime }' required="true" validType="regExp['/^[0-9\s]{1,30}$/','该输入项为正整数']"/>
	        </div>
		</fd:form>
	</div>
	
	<div class="div-msg-btn" id="saveNodeInfoButton">
		<c:if test="${isEnableFlag !='1' && empty plan_.id}">
		<div>
			<fd:linkbutton value="{com.glaway.ids.common.btn.save}" classStyle="button_nor" id="saveNodeInfo" onclick="saveNodeInfo()"/>
		</div>
		</c:if>
	</div>
</div>