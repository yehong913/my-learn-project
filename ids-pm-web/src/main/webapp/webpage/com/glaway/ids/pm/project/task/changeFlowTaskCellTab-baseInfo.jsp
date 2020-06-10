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
			debugger
			if($('#id').val() != "" && $('#id').val() != undefined ){
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
										
										$('#tt').tabs('add',{
											title: '<spring:message code="com.glaway.ids.pm.project.plan.input"/>',
											id: 'cellInput',
											selected: 0,
											onLoad : 1,
											href: 'taskFlowResolveController.do?goChangeTab&tabIndex=2&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}'
										});
										tabTitleArr.push('<spring:message code="com.glaway.ids.pm.project.plan.input"/>');
										$('#tt').tabs('add',{
											title: '<spring:message code="com.glaway.ids.pm.project.plan.output"/>',
											id: 'cellOutput',
											selected: 0,
											onLoad : 1,
											href: 'taskFlowResolveController.do?goChangeTab&tabIndex=4&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}'
										});
										/* if('${klmFlag}' == 'true' || '${klmFlag}' == true){
											tabTitleArr.push('<spring:message code="com.glaway.ids.pm.project.plan.output"/>');
											$('#tt').tabs('add',{
												title: '<spring:message code="com.glaway.ids.pm.project.plan.reference"/>',
												id: 'cellReference',
												selected: 0,
												href: 'taskFlowResolveController.do?goChangeTab&tabIndex=3&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}'
											});
											tabTitleArr.push('<spring:message code="com.glaway.ids.pm.project.plan.reference"/>');
										} */
										
										$('#tt').tabs('add',{
											title: '<spring:message code="com.glaway.ids.pm.project.plan.resource"/>',
											id: 'cellResource',
											selected: 0,
											onLoad : 1,
											href: 'taskFlowResolveController.do?goChangeTab&tabIndex=5&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}'
										});
										tabTitleArr.push('<spring:message code="com.glaway.ids.pm.project.plan.resource"/>');
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
				$("#saveBaseInfo").hide();
				$("#planName").attr("disabled", "true");
				$("#owner").attr("disabled", "true");
				$("#planLevel").attr("disabled", "true");
				$("#taskNameType").attr("disabled", "true");
				$("#preposePlans").attr("disabled", "true");
				$("#remark").attr("disabled", "true");
				/* $("#preposePlans").attr("readonly", "true"); */
			}
		});
	
		function saveBaseInfo(){
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
				planName = $('#planName').combobox('getValue');
			}
			var owner = $('#owner').combobox('getValue'); 
			var workTime = $('#workTime').val(); 
			var planLevel = $('#planLevel').combobox('getValue');
			var planLevelList = eval($('#planLevelList').val());
			for (var i = 0; i < planLevelList.length; i++) {
				if (planLevel == planLevelList[i].name) {
					planLevel = planLevelList[i].id;
				}
			}
			var remark = $('#remark').val();
			var preposeIds = $('#preposeIds').val();
			var preposePlans = $('#preposePlans').val();
			var allPreposeIds = $('#allPreposeIds').val();
			var taskNameType = $('#taskNameType').combobox('getValue');
			
			if (planName == "") {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.emptyName"/>');
				return false;
			}
			if(planName.length>30){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.nameLength"/>');
				return false;
			}
			if (owner == "") {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.emptyManager"/>');
				return false;
			}
			var userList = eval($('#userList2').val());
			for (var i = 0; i < userList.length; i++) {
				if (owner == userList[i].realName) {
					owner = userList[i].id;
				}
			}

			if (workTime == "") {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.emptyPeriod"/>');
				return false;
			}
			
			$.ajax({
				url : 'taskFlowResolveController.do?saveFlowTaskForChange',
				type : 'post',
				data : {
					id : id,
					cellId : cellId,
					parentPlanId : parentPlanId,
					planName : planName,
					owner : owner, 
					workTime : workTime,
					planLevel : planLevel,
					remark : remark,
					preposeIds : preposeIds,
					preposePlans : preposePlans,
				    allPreposeIds : allPreposeIds,
				    taskNameType:taskNameType
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
								<c:if test="${!empty outwards}">
									<c:forEach items="${outwards}" var="out">
	 									var outType = '${out.optionValue}';
										eval("outSystemDeliverInit_" + outType + "(parentPlanId, baseInfoId)");
									</c:forEach>
								</c:if>
								$("#id").val(baseInfoId);
								if(tabTitleArr.length>0) {
									for(var i=0; i<tabTitleArr.length; i++) {
										$('#tt').tabs('close', tabTitleArr[i]); 
										//alert($.toJSON($('#tt').tabs('getTab',i)));
									}
								}
								var index0Tab = $('#tt').tabs('getSelected'); 
								index0Tab.panel('refresh', 'taskFlowResolveController.do?goChangeTab&tabIndex=1&cellId='+cellId+'&parentPlanId='+parentPlanId+'&isEnableFlag='+isEnableFlag+'&isSave=true');
							}
						}
					}
					else{
						var msg = d.msg.split('<br/>')
						top.tip(msg[0]);
					}
				}
			});
		}
		
		/**
		 * 选择外部前置页面
		 */
		function selectPreposePlan(hiddenId, textId, url) {
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
		
		/**国际化转移方法**/
		// 工期联动
		function workTimeLinkage(inputType) {
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
		}
		
		function getPlanNamechange(isStandard){
			if('false' != isStandard){
				var planName = $('#planName').combobox('getValue');
				$.ajax({
					url : 'taskFlowResolveController.do?getTaskNameType',
					type : 'post',
					data : {
						planName : planName
					},
					cache : false,
					success : function(data) {
						if (data != null) {
							var d = $.parseJSON(data);
							if (d.success == true) {
								$('#taskNameType')
										.combobox("setValue",
												d.obj);
							} else {
								$('#taskNameType')
								.combobox("setValue",
										d.obj);
								win.tip(d.msg);
							}
						} else {
							win.tip(d.msg);
						}
					}
				});
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
				<input id="departList" name="departList" type="hidden" value="${departList}">
				<input id="ownerShow"  name="ownerShow" type="hidden" value="${ownerShow}">	
				<input id="userList2"  name="userList2" type="hidden" value="${userList2}">	
				<input id="planLevelShow"  name="planLevelShow" type="hidden" value="${planLevelShow}">	
				
				<input type="hidden" name="id" id="id" value="${plan_.id}" />
				<input type="hidden" name="planId" id="planId" value="${plan_.planId}" />
				<input type="hidden" name="fromTemplate" id="fromTemplate" value="${plan_.fromTemplate}" />
				<input id="allPreposeIds"  name="allPreposeIds" type="hidden" value="${allPreposeIds}">
			    <input id="allPreposeNames"  name="allPreposeNames" type="hidden" value="${allPreposeNames}">
			    <input id="planLevelList"  name="planLevelList" type="hidden" value="${planLevelList}">	
				
				<c:choose>
					<c:when test="${isStandard=='ok'}">
						<fd:combobox title="{com.glaway.ids.pm.project.task.taskname}" id="planName" textField="name"
							editable="true" valueField="name" required="true" selectedValue="${plan_.planName}" 
							panelMaxHeight="200" onChange="getPlanNamechange('true')"
							url="planController.do?standardValueExceptDesign"></fd:combobox>
					</c:when>
					<c:when test="${isStandard=='true'}">
						<fd:combobox title="{com.glaway.ids.pm.project.task.taskname}" id="planName" textField="name"
							editable="true" valueField="name" selectedValue="${plan_.planName}" 
							validType="comboboxDistinctValidate['planName']"
							panelMaxHeight="200" onChange="getPlanNamechange('true')"
							url="planController.do?standardValueExceptDesign" maxLength="30" required="true"></fd:combobox>
					</c:when>
					<c:otherwise>
						<fd:inputText id="planName" name="name" required="true" value="${plan_.planName}"
							title="{com.glaway.ids.pm.project.task.taskname}"></fd:inputText>
					</c:otherwise>
				</c:choose>
				
				<fd:combobox id="owner" textField="realName" title="{com.glaway.ids.common.lable.owner}" selectedValue="${ownerShow}" onChange="ownerChange()" editable="false" valueField="id" 
					required="true" panelMaxHeight="150" url="taskFlowResolveController.do?projectMembers&parentPlanId=${parentPlanId}" />
			
				<fd:inputDate id="planStartTime" title="{com.glaway.ids.pm.project.task.taskStartTime}" value='${plan_.planStartTime}' onChange="workTimeLinkage('planStartTime')" editable="false" readonly="true" />
				
				<fd:inputText id="workTimeReference" title="{com.glaway.ids.pm.project.task.workTimeReference}" value='${plan_.workTimeReference}' readonly="true" />	
			
				<fd:inputDate id="planEndTime" title="{com.glaway.ids.pm.project.task.taskEndTime}" value='${plan_.planEndTime}' editable="false" onChange="workTimeLinkage('planEndTime')" readonly="true" />
	            <fd:inputText id="workTime" readonly="true"  title="{com.glaway.ids.pm.project.task.workTime}" value='${plan_.workTime == 0 ? 1 :  plan_.workTime }' required="true" validType="regExp['/^[0-9\s]{1,30}$/','该输入项为正整数']"/>
		
				<fd:inputText id="ownerDept"  title="{com.glaway.ids.pm.project.plan.dept}" readonly="true" name="ownerDept"  value="${plan_.ownerDept}" />
				<fd:selectBooleanCheckbox id="required" name="required" title="{com.glaway.ids.pm.project.task.requiredTask}" value="${plan_.required}" disabled="true"/>
				<fd:combobox id="planLevel" textField="name" title="{com.glaway.ids.pm.project.task.planLevel}" selectedValue="${planLevelShow}" editable="false" valueField="id"
					panelMaxHeight="150" url="planController.do?useablePlanLevelList" />					
				<fd:combobox id="taskNameType" textField="typename" title="{com.glaway.ids.pm.project.task.taskNameType}" selectedValue="${plan_.taskNameType}" readonly="true" editable="false" valueField="typecode"
						 required="true" url="taskFlowResolveController.do?getTaskNameTypes"></fd:combobox>
				<fd:inputSearch id="preposePlans" title="{com.glaway.ids.pm.project.task.outPrepose}"  value="${plan_.preposePlans}" editable="false" searcher="selectPreposePlan('#preposeIds','#preposePlans','planController.do?outPlanPreposeTree')"></fd:inputSearch>		
				<input name="preposeIds" id="preposeIds" value="${plan_.preposeIds}" type="hidden"></input>
			    	
				<fd:inputTextArea id="remark" name="remark" title="{com.glaway.ids.pm.project.task.remark}" value="${plan_.remark}" ></fd:inputTextArea>	
			</fd:form>
					
		</div>
		
		<div class="div-msg-btn">
			<div>
				<fd:linkbutton value="{com.glaway.ids.common.btn.save}" classStyle="button_nor" id="saveBaseInfo" onclick="saveBaseInfo()"/>
			</div>
		</div>
	</div>
	
	<fd:dialog id="preposePlanDialog" width="800px" height="580px" modal="true"  title="{com.glaway.ids.pm.project.task.selectOutPrepose}" zIndex="4200">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="preposePlanDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	