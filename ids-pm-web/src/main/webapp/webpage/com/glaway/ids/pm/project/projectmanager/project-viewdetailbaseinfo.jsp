<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
<body style="overflow-y: auto;overflow-x: hidden;">
	<script type="text/javascript">
		$(document).ready(function() {
			
			if ('${refreshTree}' == 'true') { // 非审批页面进入才刷新右侧树
				loadTree("${project_.id}", 1, "detail");
			}

			var isViewPage = '${isViewPage}';
			var bizCurrent = '${project_.bizCurrent}';
			var status = '${project_.status}';
			var isRefuse = '${project_.isRefuse}';
			var projectTimeTypeIsModify = '${projectTimeTypeIsModify}';

			if (isViewPage == 'true') { // 非项目管理员和非项目经理，或项目审批页面    只读
				//$("#toolBar").css('display', 'none');
				$('#epsName').textbox({
					readonly : true
				});
				if (bizCurrent == '拟制中') {
					$("#processSpan").css('display', 'none');
				}
			} else {
				if (status == "1" && isRefuse != "1") { // 审批流程中且未驳回    只读
					//$("#toolBar").css('display', 'none');
					$('#epsName').textbox({
						readonly : true
					});
					if (bizCurrent == '拟制中') {
						$("#processSpan").css('display', 'none');
					}
				} else { // 1、审批流程正常结束或撤销；2、审批流程中但已驳回    根据生命周期判断
					if (bizCurrent == '拟制中') {
						$("#toolBarDiv").attr("style","display:");
						accessBtnShow();
						$("#pauseResume").css('display', 'none');
						$("#resume").css('display', 'none');
						$("#close").css('display', 'none');

						$("#processSpan").css('display', 'none');

						$('#projectNumber').textbox({
							readonly : false
						});
						$('#projectName').textbox({
							readonly : false
						});
						$('#projectManagers').searchbox({
							readonly : false
						});
						$('#startProjectTime').datebox({
							readonly : false
						});
						$('#endProjectTime').datebox({
							readonly : false
						});
						$('#phase').combobox({
							readonly : false
						});
						$('#remark').textbox({
							readonly : false
						});
						changeoverTextboxReadonlySty("projectNumber", false);
						changeoverTextboxReadonlySty("projectName", false);
						changeoverTextboxReadonlySty("projectManagers", false);
						changeoverTextboxReadonlySty("startProjectTime", false);
						changeoverTextboxReadonlySty("endProjectTime", false);
						changeoverTextboxReadonlySty("phase", false);

						if (projectTimeTypeIsModify == null || projectTimeTypeIsModify == "") {
							$('#projectTimeType').combobox({
								readonly : false
							});
							changeoverTextboxReadonlySty("projectTimeType", false);
						}
					} else if (bizCurrent == '执行中') {
						//$("#delete").css('display', 'none');
						$("#toolBarDiv").attr("style","display:");
						accessBtnShow();
						$("#start").css('display', 'none');
						$("#resume").css('display', 'none');

						$('#projectNumber').textbox({
							readonly : false
						});
						$('#projectName').textbox({
							readonly : false
						});
						$('#projectManagers').searchbox({
							readonly : false
						});
						$('#startProjectTime').datebox({
							readonly : false
						});
						$('#endProjectTime').datebox({
							readonly : false
						});
						$('#epsName').textbox({
							readonly : true
						});
						$('#phase').combobox({
							readonly : false
						});
						$('#process').numberbox({
							readonly : false
						});
						$('#remark').textbox({
							readonly : false
						});
						changeoverTextboxReadonlySty("projectNumber", false);
						changeoverTextboxReadonlySty("projectName", false);
						changeoverTextboxReadonlySty("projectManagers", false);
						changeoverTextboxReadonlySty("startProjectTime", false);
						changeoverTextboxReadonlySty("endProjectTime", false);
						changeoverTextboxReadonlySty("epsName", true);
						changeoverTextboxReadonlySty("phase", false);
						changeoverTextboxReadonlySty("process", false);

						if ($('#process').numberbox('getValue') == "") {
							$('#process').numberbox('setValue', '0.00');
						}
					} else if (bizCurrent == '已暂停') {
						$("#toolBarDiv").attr("style","display:");
						accessBtnShow();
						$("#update").css('display', 'none');
						//$("#delete").css('display', 'none');
						$("#start").css('display', 'none');
						$("#pauseResume").css('display', 'none');
						$("#close").css('display', 'none');

						$('#epsName').textbox({
							readonly : true
						});
						changeoverTextboxReadonlySty("epsName", true);
					} else if (bizCurrent == '已关闭') {
						//$("#toolBar").css('display', 'none');

						$('#epsName').textbox({
							readonly : true
						});
						changeoverTextboxReadonlySty("epsName", true);
					}
				}
			}

			setTimeout(function(){
				$("#phase").combobox('setValue','${project_.phaseInfo.id}');
				$("#phase").combobox('setText','${project_.phase}');
			},500);

			$.extend($.fn.validatebox.defaults.rules, {
				validateStartTime : {
					validator : function(value, param) {
						var end = $('#endProjectTime').val();
						var endTime = $.fn.datebox.defaults.parser(end);
						var modifyTime = $.fn.datebox.defaults.parser(value);
						if (modifyTime > endTime) {
							return false;
						}
						return true;
					},
					message : '<spring:message code="com.glaway.ids.pm.project.projectmanager.startNoLaterThanEnd"/>'
				},
				validateEndTime : {
					validator : function(value, param) {
						var start = $('#startProjectTime').val();
						var startTime = $.fn.datebox.defaults.parser(start);
						var modifyTime = $.fn.datebox.defaults.parser(value);
						if (modifyTime < startTime) {
							return false;
						}
						return true;
					},
					message : '<spring:message code="com.glaway.ids.pm.project.projectmanager.endNoEarlierThanStart"/>'
				}
			});
		});

		function accessBtnShow() {
			if ('${saveProject}' == 'true' || '${saveProject}') {
				$('#update').show();
			}

			if ('${pauseResumeProjDetail}' == 'true' || '${pauseResumeProjDetail}') {
				$('#pauseResume').show();

			}

			if ('${resumeProjDetail}' == 'true' || '${resumeProjDetail}') {
				$('#resume').show();
			}

			if ('${startProjDetail}' == 'true' || '${startProjDetail}') {
				$('#start').show();
			}

			if ('${closeProjDetail}' == 'true' || '${closeProjDetail}') {
				$('#close').show();

			}
		}

		function setEpsName() {
			var eps = '${project_.eps}';
			$('#epsName').comboztree('setValue', eps);
		}

		function changeoverTextboxReadonlySty(id, readonly) {
			setTimeout(function(){
				if (readonly) {
					$('#glaway_input_readonly_' + id).attr('class', 'glaway_input_readonly');
				}
				else {
					$("#glaway_input_readonly_" + id).attr('class', 'glaway_search_box_width_330');
				}
			},100);
		}

		$('#startProjectTime').datebox({
			onSelect : function(date) {
				var datastr = date.getFullYear()
						+ "-"
						+ (parseInt(date.getMonth() + 1, 10) < 10 ? "0"
								+ parseInt(date.getMonth() + 1, 10)
								: parseInt(date.getMonth() + 1, 10))
						+ "-"
						+ (parseInt(date.getDate(), 10) < 10 ? "0"
								+ parseInt(date.getDate(), 10) : parseInt(
								date.getDate(), 10));
				$('#startProjectTime').val(datastr);
			}
		});

		$('#endProjectTime').datebox({
			onSelect : function(date) {
				var datastr = date.getFullYear()
						+ "-"
						+ (parseInt(date.getMonth() + 1, 10) < 10 ? "0"
								+ parseInt(date.getMonth() + 1, 10)
								: parseInt(date.getMonth() + 1, 10))
						+ "-"
						+ (parseInt(date.getDate(), 10) < 10 ? "0"
								+ parseInt(date.getDate(), 10) : parseInt(
								date.getDate(), 10));
				$('#endProjectTime').val(datastr);
			}
		});

		function dateFormatter(val, row, value) {
			return val.substr(0, 19);
		}

		function refresh() {
			try {
				$("#right_page_panel").panel("open").panel("refresh");
			} catch (e) {
			};
			try {
				parent.$("#right_page_panel").panel("open").panel("refresh");
			} catch (e) {
			};
		}

		function saveUpdateBasic() {
			var formData = $('#projectBasicUpdateForm').serialize();
			if ($('#process').val() == '' || $('#process').val() == undefined) {
				$('#process').numberbox('setValue', 0);
			}else {
				if($('#process').val()>100||$('#process').val()<0){
					tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.checkProcess"/>');
					return false;
				}
			}
			if ($('#projectName').val() == "") {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.emptyName"/>');
				return false;
			}
			if ($('#endProjectTime').datebox('getValue') < $('#startProjectTime')
					.datebox('getValue')) {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.startNoLaterThanEnd"/>');
				return false;
			}
			if ($('#phase').combobox('getValue') == "") {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.emptyPhase"/>');
				return false;
			}
			if ($('#projectNumber').val() == "" || $('#projectNumber').val() == undefined || $('#projectNumber').val() == null) {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.emptyNumber"/>');
				return false;
			}
			if (vidateRemark($('#remark').val())) {
				if ($('#projectBasicUpdateForm').form('validate')) {
					postBasic("projectController.do?doUpdate&pagetype=info",
							"#projectBasicUpdateForm");
				}
			}
			return false;
		}

		function vidateRemark(remark) {
			if (remark != null && remark != '' && remark != 'undefined') {
				if (remark.length > 200) {
					tip('<spring:message code="com.glaway.ids.common.remarkLength"/>');
					return false;
				}
			}
			return true;
		}

		function postBasic(url, formName) {
			var formData = $(formName).serialize();
			$.ajax({
				type : 'POST',
				url : url,
				data : formData,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						tip(d.msg);
						refresh();
					} else {
						tip(d.msg);
					}
				}
			});
		}

		function deleteProject(url) {
			top.Alert.confirm(
					'<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmDel"/>',
					function(r) {
						if (r) {
							var ids = [];
							var id = '${project_.id}';
							ids.push(id);
							$
									.ajax({
										url : "projRoleUsersController.do?isPMO",
										type : 'post',
										cache : false,
										async : false,
										success : function(result) {
											if (result) {
												$
														.ajax({
															url : url,
															type : 'post',
															data : {
																ids : ids
																		.join(',')
															},
															cache : false,
															success : function(
																	data) {
																var d = $
																		.parseJSON(data);
																if (d.success) {
																	var msg = d.msg;
																	tip(msg);
																	refresh();
																	parent.loadTree(null,1);
																	ids = '';
																}
															}
														});
											} else {
												tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPmoCanDel"/>');
												return;
											}
										}
									});
						}
					});
		}

		/**
		 * 项目启动、暂停、恢复流程JS方法
		 * @param title 查看框标题
		 * @param url 目标页面地址
		 * @param id 主键字段
		 */
		function startProcess(title, url, id) {
			var processConfirmTip;

			if (title == '启动') {
				var names = '${project_.name}' + " - "
						+ '${project_.projectNumber}';
				processConfirmTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmStartup" arguments="' +  names + '"/>';

				$.ajax({
					url : "projRoleUsersController.do?isProjectManagerOrPMO&id="
							+ '${project_.id}',
					type : 'post',
					cache : false,
					async : false,
					success : function(result) {
						if (result) {
							top.Alert.confirm(processConfirmTip, function(r) {
								if (r) {
									url += '&id=' + '${project_.id}';
									createDialog('startsAndSubmitDialog',
											url);
								}
							});
						} else {
							tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPmoCanStartup"/>');
							return;
						}
					}
				});

			} else if (title == '暂停') {
				var names = '${project_.name}' + " - "
						+ '${project_.projectNumber}';
				processConfirmTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmPause" arguments="' +  names + '"/>';

				$
						.ajax({
							url : "projRoleUsersController.do?isProjectManagerOrPMO&id="
									+ '${project_.id}',
							type : 'post',
							cache : false,
							async : false,
							success : function(result) {
								if (result) {
									top.Alert.confirm(processConfirmTip, function(r) {
										if (r) {
											url += '&id=' + '${project_.id}';
											createDialog('pauseAndSubmitDialog',
													url);
										}
									});
								} else {
									tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPmoOrManagerCanPause"/>');
									return;
								}
							}
						});
			} else if (title == '恢复') {
				var names = '${project_.name}' + " - "
						+ '${project_.projectNumber}';
				processConfirmTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmResume" arguments="' +  names + '"/>';

				$
						.ajax({
							url : "projRoleUsersController.do?isProjectManagerOrPMO&id="
									+ '${project_.id}',
							type : 'post',
							cache : false,
							async : false,
							success : function(result) {
								if (result) {
									top.Alert.confirm(processConfirmTip, function(r) {
										if (r) {
											url += '&id=' + '${project_.id}';
											createDialog('resumeAndSubmitDialog',
													url);
										}
									});
								} else {
									tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPmoOrManagerCanResume"/>');
									return;
								}
							}
						});
			}
		}

		/**
		 * 项目关闭流程JS方法
		 * @param title 查看框标题
		 * @param url 目标页面地址
		 * @param id 主键字段
		 */
		function startProcessForClose(title, url, id) {
			var processConfirmTip;

			$.ajax({
				url : "projRoleUsersController.do?isProjectManagerOrPMO&id="
						+ '${project_.id}',
				type : 'post',
				cache : false,
				async : false,
				success : function(result) {
					if (result) {
						$
								.ajax({
									url : 'projectController.do?checkPlan&id='
											+ '${project_.id}',
									type : 'post',
									data : '',
									cache : false,
									success : function(data) {
										if (data == false) {
											var names = '${project_.name}'
													+ " - "
													+ '${project_.projectNumber}';
											processConfirmTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmClose" arguments="' +  names + '"/>';
											top.Alert.confirm(
													processConfirmTip,
													function(r) {
														if (r) {
															url += '&closedProject=1&id='
																	+ '${project_.id}';
															createDialog(
																	'closePAndSubmitDialog',
																	url);
														}
													});
										} else if (data == true) {
											var names = '${project_.name}'
													+ " - "
													+ '${project_.projectNumber}';
											processConfirmTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmForceClose" arguments="' +  names + '"/>';
											top.Alert.confirm(
													processConfirmTip,
													function(r) {
														if (r) {
															url += '&closedProject=1&id='
																	+ '${project_.id}';
															createDialog(
																	'closePAndSubmitDialog',
																	url);
														}
													});
										}
									}
								});
					} else {
						tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPmoOrManagerCanClose"/>');
						return;
					}
				}
			});
		}

		function startsAndSubmitOk(iframe) {
			iframe.startSubmitProject();
			return false;
		}

		function pauseAndSubmitOk(iframe) {
			iframe.pauseProject();
			return false;
		}

		function resumeAndSubmitOk(iframe) {
			iframe.resumeProject();
			return false;
		}

		function closePAndSubmitOk(iframe) {
			iframe.closeProject();
			return false;
		}

		function hideBtn() {
			$("#toolBarDiv").attr("style","display:none");
		}
	</script>
<div id="toolBarDiv" style="display: none;">
<fd:toolbar id="toolBar">
	<fd:toolbarGroup align="left">
		<fd:linkbutton id="update"
					   onclick="saveUpdateBasic()" value="{com.glaway.ids.common.btn.save}"
					   iconCls="basis ui-icon-save" />
		<%-- 		<fd:linkbutton id="delete"
                    onclick="deleteProject('projectController.do?doBatchDel')"
                    value="{com.glaway.ids.pm.project.projectmanager.delete}"
                    iconCls="basis ui-icon-minus" /> --%>
		<fd:linkbutton id="start"
					   onclick="startProcess('启动','projectController.do?startProcess&detail=detail','projLogList')"
					   value="{com.glaway.ids.pm.project.projectmanager.start}"
					   iconCls="basis ui-icon-start" />
		<fd:linkbutton id="pauseResume"
					   onclick="startProcess('暂停','projectController.do?startProcess&detail=detail','projLogList')"
					   value="{com.glaway.ids.pm.project.projectmanager.pause}"
					   iconCls="basis ui-icon-pause" />
		<fd:linkbutton id="resume"
					   onclick="startProcess('恢复','projectController.do?startProcess&detail=detail','projLogList')"
					   value="{com.glaway.ids.pm.project.projectmanager.resume}"
					   iconCls="basis ui-icon-recovery" />
		<fd:linkbutton id="close"
					   onclick="startProcessForClose('关闭','projectController.do?startProcess&detail=detail','projLogList')"
					   value="{com.glaway.ids.pm.project.projectmanager.close}"
					   iconCls="basis ui-icon-close" />
	</fd:toolbarGroup>
</fd:toolbar>
</div>
	<fd:form id="projectBasicUpdateForm">
		<input id="id" name="id" type="hidden" value="${project_.id}" />
		<fd:inputText id="projectNumber" name="projectNumber" type="text"
					  title="项目编号" value="${project_.projectNumber}" readonly="true"/>
		<fd:inputText id="projectCreateName" name="createName" type="text"
					  title="创建者" value="${project_.createName}" readonly="true"  />
		<fd:inputText id="projectName" name="name" type="text"
					  value='${project_.name}' title="项目名称" required="true"
					  readonly="true"  />
		<fd:inputDate id="createTime" name="createTime" type="text"
					  value="${project_.createTime}" formatter="yyyy-MM-dd" title="创建时间"
					  readonly="true" />
		<fd:inputSearchUser id="projectManagers" name="projectManagers"
							readonly="true"
							title="{com.glaway.ids.pm.project.projectmanager.projectManager}"
							multiple="true" required="true" maxLength="-1"
							value="${project_.projectManagers}" roleInputVal="${codeId}">
		</fd:inputSearchUser>
		<c:if test="${!(projTemplate eq '1')}">
			<fd:combobox id="projectTemplate" name="projectTemplate"
						 title="项目模板" panelMaxHeight="200"
						 value="${projTemplate.projTmplName }" readonly="true" />
		</c:if>
		<c:if test="${projTemplate eq '1'}">
			<fd:combobox id="projectTemplate" name="projectTemplate"
						 title="项目模板" panelMaxHeight="200" value="不使用模板" readonly="true"  />
		</c:if>
		<fd:inputDate id="startProjectTime" name="startProjectTime"
					  value='${project_.startProjectTime}' title="开始时间"
					  validType="validateStartTime['#startProjectTime']" editable="false"
					  formatter="yyyy-MM-dd" readonly="true" />
		<fd:inputDate id="endProjectTime" name="endProjectTime"
					  value='${project_.endProjectTime}' title="结束时间"
					  validType="validateEndTime['#endProjectTime']" editable="false"
					  formatter="yyyy-MM-dd" readonly="true" />
		<fd:inputText id="projectBizCurrent" name="bizCurrent" type="text"
					  title="状态" value="${project_.bizCurrent}" readonly="true"  />
		<fd:dictCombobox dictCode="timeType" id="projectTimeType"
						 title="工期设置" selectedValue="${project_.projectTimeType}"
						 editable="false" readonly="true"  />
		<%--<fd:combotree readonly="true" title="项目分类" treeIdKey="id"  url="projectController.do?getEPSTree" id="epsName"  searchTree="false"--%>
		<%--treePidKey="pid" name="name" value="${project_.epsName}"  panelHeight="250"  multiple="true" onLoadSuccess="setTopic">--%>
		<%--</fd:combotree>--%>
		<fd:combotree id="epsName" name="epsName" title="项目分类" treeIdKey="id"
					  treePidKey="pid" treeName="name" panelHeight="250" multiple="false"
					  value="${project_.epsName}" url="projectController.do?getEPSTree"
					  required="true" onLoadSuccess="setEpsName()" readonly="true"/>
		<fd:combobox id="phase" name="phase" title="阶段" valueField="id"
					 textField="name" panelMaxHeight="200" required="true" editable="false"
					 selectedValue="${project_.phase}"
					 url="projectController.do?phaseListForDetail&projectId=${project_.id}"
					 readonly="true" />
		<fd:inputNumber id="process" name="process"
						validType="equalOneHundred[6]" title="进度"
						value="${project_.process}" precision="2" suffix="%"
						readonly="true" />
		<fd:inputTextArea name="remark" id="remark" title="备注"
						  value="${project_.remark}" readonly="true" />
	</fd:form>
	<div class="panel-header panel-header-noborder" style="clear: both;"><div class="panel-title">项目日志</div></div>


	<fd:datagrid fit="false" fitColumns="true" idField="id"
				 pagination="false" id="projLogList" width="100%"
				 url="projLogController.do?queryProjectLogNotPage&id=${project_.id}">
		<fd:dgCol title="项目编号" field="projectNumber" hidden="true" />
		<fd:dgCol title="操作" field="logInfo" width="180" />
		<fd:dgCol title="操作者" field="showName" width="100" />
		<fd:dgCol title="操作时间" field="createTime" width="140"
				  formatterFunName="dateFormatter" />
		<fd:dgCol title="备注" field="remark" tipField="remark" />
	</fd:datagrid>


<fd:dialog id="startsAndSubmitDialog" width="800px" height="260px"
		   modal="true" title="{com.glaway.ids.pm.project.projectmanager.start}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
					 callback="startsAndSubmitOk"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
					 callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="pauseAndSubmitDialog" width="800px" height="260px"
		   modal="true" title="{com.glaway.ids.pm.project.projectmanager.pause}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
					 callback="pauseAndSubmitOk"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
					 callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="resumeAndSubmitDialog" width="800px" height="260px"
		   modal="true" title="{com.glaway.ids.pm.project.projectmanager.resume}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
					 callback="resumeAndSubmitOk"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
					 callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="closePAndSubmitDialog" width="800px" height="260px"
		   modal="true" title="{com.glaway.ids.pm.project.projectmanager.close}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
					 callback="closePAndSubmitOk"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
					 callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
</body>
</html>