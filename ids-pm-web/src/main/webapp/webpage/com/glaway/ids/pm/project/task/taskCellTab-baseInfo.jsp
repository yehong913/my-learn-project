<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<c:if test="${isEnableFlag!='1'}">
	<div border="false" class="easyui-panel div-msg" id="baseInfo" fit="true">
</c:if>
<c:if test="${isEnableFlag=='1'}">
	<div border="false" class="easyui-panel" id="baseInfo" fit="true">
</c:if>
	<div class="easyui-panel" fit="true">
		<fd:form id="baseInfoFrm" method="POST"
			url="taskFlowTemplateController.do?doAddBaseInfo">
			<input type="hidden" name="cellId" id="cellId" value="${cellId}" />
			<input type="hidden" name="templateId" id="templateId"
				value="${templateId}" />
			<input type="hidden" name="id" id="id" value="${tcbi.id}" />
			<c:if test="${isEnableFlag!='1'}">
				<c:choose>
					<c:when test="${isStandard=='ok'}">
						<!-- 启用活动名称库 -->
						<input id="cellName" name="cellName" type="hidden" value="" />
						<fd:combobox id="nameStandardId"
							title="{com.glaway.ids.pm.project.task.activityname}"
							textField="name" editable="true" valueField="id"
							panelMaxHeight="200" required="true"
							url="procTemplateController.do?standardValue" maxLength="-1"
							selectedValue="${tcbi.cellName}" onChange="modifySave()"></fd:combobox>
					</c:when>
					<c:when test="${isStandard=='true'}">
						<!--启用活动名称库 +强制使用活动名称-->
						<input id="cellName" name="cellName"
							title="{com.glaway.ids.pm.project.task.activityname}" type="hidden"
							value="" />
						<fd:combobox id="nameStandardId"
							title="{com.glaway.ids.pm.project.task.activityname}"
							textField="name" editable="true" valueField="id"
							panelMaxHeight="200"
							url="procTemplateController.do?standardValue"
							validType="comboboxDistinctValidate['nameStandardId']" required="true"
							selectedValue="${tcbi.cellName}" onChange="modifySave()"></fd:combobox>
					</c:when>
					<c:otherwise>
						<!--不勾选任何选项-->
						<fd:inputText onChange="modifySave()" id="cellName"
							name="cellName" value="${tcbi.cellName}" required="true"
							title="{com.glaway.ids.pm.project.task.activityname}" maxLength="30"></fd:inputText>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${isEnableFlag=='1'}">
				<fd:inputText onChange="modifySave()" id="cellName" name="cellName"
					value="${tcbi.cellName}" editable="false" readonly="true" 
					title="{com.glaway.ids.pm.project.task.activityname}"
					></fd:inputText>
			</c:if>
			<c:if test="${isEnableFlag=='1'}">
				<fd:inputText id="refDuration" name="refDuration"
					title="{com.glaway.ids.pm.project.task.workTimeReference}"
					value="${tcbi.refDuration}" onChange="modifySave()"
					editable="false" readonly="true" 
					></fd:inputText>
			</c:if>
			<c:if test="${isEnableFlag!='1'}">
				<fd:inputText id="refDuration" name="refDuration"
					title="{com.glaway.ids.pm.project.task.workTimeReference}"
					value="${tcbi.refDuration}" onChange="modifySave()"
					validType="regExp['/^[1-9\s]{1}[0-9\s]{0,8}$/','参考工期为不超过9位的正整数']"></fd:inputText>
			</c:if>
			<fd:selectBooleanCheckbox id="isCellRequired"
				title="{com.glaway.ids.pm.project.task.requiredTask}"
				value="${tcbi.isCellRequired}" name="isCellRequired" />
			<c:if test="${isEnableFlag!='1'}">
				<fd:inputTextArea id="cellRemark" name="cellRemark"
					title="{com.glaway.ids.pm.project.task.remark}"
					onChange="modifySave()" value="${tcbi.cellRemark}" maxLength="200"></fd:inputTextArea>
			</c:if>
			<c:if test="${isEnableFlag=='1'}">
				<fd:inputTextArea id="cellRemark" name="cellRemark"
					title="{com.glaway.ids.pm.project.task.remark}" editable="false"
					readonly="true" onChange="modifySave()" value="${tcbi.cellRemark}"
					maxLength="200"></fd:inputTextArea>
			</c:if>
		</fd:form>
	</div>
	
	<c:if test="${isEnableFlag!='1'}">
		<div class="div-msg-btn">
			<div>
				<fd:linkbutton value="{com.glaway.ids.common.btn.save}"
					classStyle="button_nor" id="saveBaseInfo" onclick="saveBaseInfo()" />
			</div>
		</div>
	</c:if>
</div>
<script>
var tabUrlObj = new Object();
	$(function($) {
		if ('${tcbi.id}' != "") {
			//$('#tt').tabs('enableTab', 1);
			//$('#tt').tabs('enableTab', 2);
		}
		if ('${isEnableFlag}' == '1') {
			$("#saveBaseInfo").attr("disabled", "disabled");
			$("#cellRemark").attr("disabled", "disabled");
			$("#isCellRequired").attr("disabled", "disabled");

		}
		
		var tab = $('#tt').tabs('getTab',2);
		var combCellName = '${tcbi.cellName}';
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
			if('${tcbi.nameStandardId}'!='') {
				$.ajax({
					url: 'taskFlowTemplateController.do?getTabsByNamestandard',
					data: {'nameStandardId': '${tcbi.nameStandardId}'},
					type: "POST",
					success: function(data) {
						var jsonResult = $.parseJSON(data);
						if(jsonResult.success) {
							if(jsonResult.obj!=null && jsonResult.obj.length>0) {
								for(var i=0; i<jsonResult.obj.length; i++) {
									tabUrlObj[jsonResult.obj[i].id] = jsonResult.obj[i].href + '&templateId='+'${templateId}' + '&cellId=' + '${cellId}' + '&id=' + '${tcbi.id}' + '&nameStandardId=' + '${tcbi.nameStandardId}&isEnableFlag=${param.isEnableFlag}';
									if(jsonResult.obj[i].title == '参考'){
										$('#tt').tabs('add',{
											title: '<spring:message code="com.glaway.ids.pm.project.plan.reference"/>',
											id: 'cellReference',
											selected: 0,
											onLoad : 1,
											href: 'taskFlowTemplateController.do?goTab&tabIndex=3&cellId=${param.cellId}&templateId=${param.templateId}&isEnableFlag=${param.isEnableFlag}&isAjax=true'
										});
									}else{
										$('#tt').tabs('add',{
											title: jsonResult.obj[i].title,
											id: jsonResult.obj[i].id,
											selected: 0,
											onLoad : 1,
											href: 'taskFlowTemplateController.do?goFlowTempNodeTabPage&id='+jsonResult.obj[i].id + '&isEnableFlag=${param.isEnableFlag}'
										});
									}
								}
							}
							else {
								$('#tt').tabs('add',{
									title: '<spring:message code="com.glaway.ids.pm.project.plan.input"/>',
									id: 'cellInput',
									selected: 0,
									onLoad : 1,
									href: 'taskFlowTemplateController.do?goTab&tabIndex=2&cellId=${param.cellId}&templateId=${param.templateId}&isEnableFlag=${param.isEnableFlag}&isAjax=true'
								});
								$('#tt').tabs('add',{
									title: '<spring:message code="com.glaway.ids.pm.project.plan.output"/>',
									id: 'cellOutput',
									selected: 0,
									onLoad : 1,
									href: 'taskFlowTemplateController.do?goTab&tabIndex=4&cellId=${param.cellId}&templateId=${param.templateId}&isEnableFlag=${param.isEnableFlag}&isAjax=true'
								});
								/* $('#tt').tabs('add',{
									title: '<spring:message code="com.glaway.ids.pm.project.plan.reference"/>',
									id: 'cellReference',
									selected: 0,
									href: 'taskFlowTemplateController.do?goTab&tabIndex=3&cellId=${param.cellId}&templateId=${param.templateId}&isEnableFlag=${param.isEnableFlag}&isAjax=true'
								}); */
							}
						}
					}
				});
			}
		}

		var refDuration = $('#refDuration').val();
		if ('' == combCellName) {
			combCellName = 'Task';
		} else {
			combCellName = combCellName
					+ ","
					+ refDuration
					+ "<spring:message code='com.glaway.ids.pm.project.task.day'/>";
			//top.tip(combCellName);
		}
		window.parent.cellAttributeChange($('#cellId').val(), combCellName, $(
				'#refDuration').val());
		//如果选中为必填，需要改变字体颜色
		if ("on" == '${tcbi.isCellRequired}'
				|| "true" == '${tcbi.isCellRequired}') {
			window.parent.changeFontColor('red');
			window.parent.isCellRequiredCell("true");
		} else {
			window.parent.changeFontColor('black');
			window.parent.isCellRequiredCell("flse");
		}
	})

	//校验表单
	function checkForm() {
		var isValid = true;
		$(".validatebox")
				.each(
						function(i) {
							if ($(this).val() == "") {
								var title = $(this).attr('title');
								title = title == undefined ? '' : title;
								tip(title
										+ "<spring:message code='com.glaway.ids.pm.project.task.cannotEmpty'/>");
								isValid = false;
								return false;
							}
							return true;
						});
		return isValid;
	}
	baseInfoId = '';
	//保存基本信息
	function saveBaseInfo() {
		if ('${isStandard}' == 'true') {
			var planName = $('#nameStandardId').combobox('getText');
			$
					.ajax({
						url : 'deliverablesInfoController.do?pdNameForOther',
						type : 'post',
						data : {
							planName : planName
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							var msg = d.msg;
							if (d.success) {
								if (!$("#refDuration").val().match(
										/^[1-9\s]{1}[0-9\s]{0,8}$/)) {
									tip('<spring:message code="com.glaway.ids.pm.project.task.referPeriodError"/>');
									return;
								}
								if ($("#cellRemark").val().length > 200) {
									tip('<spring:message code="com.glaway.ids.common.remarkLength"/>');
									return;
								}
								var url = "taskFlowTemplateController.do?doAddBaseInfo";
								var data = $('#baseInfoFrm').serialize();
								if ("${isStandard}" != "false" && "${isStandard}" != "") {
									var combCellName = $('#nameStandardId')
											.combobox('getText');
									$('#cellName').val(combCellName);
									url += "&cellName="
											+ encodeURI(encodeURI(combCellName));
								}
								$.ajax({
											url : url,
											type : 'post',
											data : data,
											cache : false,
											success : function(data) {
												var d = $.parseJSON(data);
												if (d.success) {
													window.parent.saveSuccess(
															$('#cellId').val(),
															$('#cellName')
																	.val(),
															$('#refDuration')
																	.val());
													//如果选中为必填，需要改变字体颜色
													var isCellRequired = $(
															'#isCellRequired')
															.selectBooleanCheckbox(
																	'getValue');
													if ("on" == isCellRequired
															|| true == isCellRequired) {
														window.parent
																.changeFontColor('red');
														window.parent
																.isCellRequiredCell("true");
													} else {
														window.parent
																.changeFontColor('black');
														window.parent
																.isCellRequiredCell("false");
													}
													window.parent.saveGraph();
													try {
														baseInfoId = d.obj;

													} catch (e) {
														top.tip(e);
													}

													if (baseInfoId != undefined
															&& baseInfoId != '') {
														try {
															$("#id").val(baseInfoId);
														} catch (e) {
															top.tip(e);
														}

													}
													;
													var index0Tab = $('#tt').tabs('getSelected');
													var change = "0";
													if(planName != '${tcbi.cellName}'){
														change = "1";
													}else{
														change = "0";
													}
													index0Tab.panel('refresh', 'taskFlowTemplateController.do?goTab&tabIndex=1&cellId=${param.cellId}&templateId=${param.templateId}&isStandard=${param.isStandard}&refDuration=${param.refDuration}&isEnableFlag=${param.isEnableFlag}&isAjax=true&isSave=true&change='+change);
												} else {
													var msg = d.msg
															.split('<br/>');
													tip(msg[0]);
												}
											}
										});
							} else {
								top.tip('名称不在活动名称库中，请重新选择！');
								return false;
							}
						}
					});
		} else {
			if ("${isStandard}" == "ok") {
				var tmp1 = $("#nameStandardId").combobox("getText");
				if (tmp1.length > 30) {
					tip('<spring:message code="com.glaway.ids.pm.project.task.nameLength"/>');
					return;
				}
			}
			if (!$("#refDuration").val().match(/^[1-9\s]{1}[0-9\s]{0,8}$/)) {
				tip('<spring:message code="com.glaway.ids.pm.project.task.referPeriodError"/>');
				return;
			}
			if ($("#cellRemark").val().length > 200) {
				tip('<spring:message code="com.glaway.ids.common.remarkLength"/>');
				return;
			}
			var url = "taskFlowTemplateController.do?doAddBaseInfo";
			var data = $('#baseInfoFrm').serialize();
			if ("${isStandard}" != "false" && "${isStandard}" != "") {
				var combCellName = $('#nameStandardId').combobox('getText');
				$('#cellName').val(combCellName);
				url += "&cellName=" + encodeURI(encodeURI(combCellName));
			}
			$
					.ajax({
						url : url,
						type : 'post',
						data : data,
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								window.parent.saveSuccess($('#cellId').val(),
										$('#cellName').val(), $('#refDuration')
												.val());
								var isCellRequired = $('#isCellRequired')
										.selectBooleanCheckbox('getValue');
								if ("on" == isCellRequired
										|| true == isCellRequired) {
									window.parent.changeFontColor('red');
									window.parent.isCellRequiredCell("true");
								} else {
									window.parent.changeFontColor('black');
									window.parent.isCellRequiredCell("false");
								}
								window.parent.saveGraph();
								try {
									baseInfoId = d.obj;

								} catch (e) {
									top.tip(e);
								}

								if (baseInfoId != undefined && baseInfoId != '') {
									try {
										$("#id").val(baseInfoId);
									} catch (e) {
										top.tip(e);
									}

								}
								;
								var index0Tab = $('#tt').tabs('getSelected');
								var change = "0";
								if(planName != '${tcbi.cellName}'){
									change = "1";
								}else{
									change = "0";
								}
								index0Tab.panel('refresh', 'taskFlowTemplateController.do?goTab&tabIndex=1&cellId=${param.cellId}&templateId=${param.templateId}&isStandard=${param.isStandard}&refDuration=${param.refDuration}&isEnableFlag=${param.isEnableFlag}&isAjax=true&isSave=true&change='+change);
							} else {
								var msg = d.msg.split('<br/>');
								tip(msg[0]);
							}
						}
					});
		}
	}
	function modifySave() {
		window.parent.modifySave();
	}

	$.extend(
					$.fn.validatebox.defaults.rules,
					{
						lessOneHundred : {
							validator : function(value, param) {
								if (value >= 100) {
									$(this).val('');
								} else if (value < 100) {
									var valueChange = value.substr(0, 5);
									$(this).val(valueChange);
									var len = $.trim(valueChange).length;
									if (len >= param[0]) {
										$(this)
												.val(
														valueChange.substr(0,
																param[0]));
									}
								}
								return true;
							},
							message : '<spring:message code="com.glaway.ids.pm.project.task.numberUpper"/>'
						},
						equalOneHundred : {
							validator : function(value, param) {
								if (value > 100) {
									$(this).val('');
								} else if (value < 100) {
									var valueChange = value.substr(0, 5);
									$(this).val(valueChange);
									var len = $.trim(valueChange).length;
									if (len >= param[0]) {
										$(this)
												.val(
														valueChange.substr(0,
																param[0]));
									}
								}
								return true;
							},
							message : '<spring:message code="com.glaway.ids.pm.project.task.numberUpperEqual"/>'
						},
						comboboxDistinctValidate : {
							validator : function(value, param) {
								var dataVals = $("#" + param[0]).combobox(
										"getData");
								var count = 0;
								for ( var i in dataVals) {
									var dataValue = dataVals[i].name;
									if (dataValue == value) {//如果输入值==名称
										break;
									}

									if (dataValue.indexOf(value) == 0) {//如果输入值能匹配到
										break;
									}
									count++;
								}
								if (dataVals.length != 0
										&& count == dataVals.length) {
									$("#" + param[0]).combobox('reset');//如果匹配不到
									return false;
								}
								return true;
							},
							message : '<spring:message code="com.glaway.ids.pm.project.task.noMatchItem"/>'
						}

					});
</script>