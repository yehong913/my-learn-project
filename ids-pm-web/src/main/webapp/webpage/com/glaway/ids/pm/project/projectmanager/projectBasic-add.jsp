<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新建项目</title>
<t:base type="jquery,easyui_iframe,tools"></t:base>
</head>

<body>
		<fd:form id="projectBasicAddForm">
			 <fd:inputText id="projectNumber" required="true" name="projectNumber"
				type="text"
				title="项目代号" 
				value="${project.projectNumber}" maxLength="30"/>
			<fd:inputText id="workNo"  name="workNo"
				type="text" title="工作令号" />	
			<fd:inputText id="name" name="name" type="text"
				title="{com.glaway.ids.pm.project.projectmanager.projectName}"
				required="true" />
			<fd:combobox id="zzpt" name="zzpt" required="true" editable="false"
				title="装载平台"
				textField="name" valueField="code" panelMaxHeight="200" selectedValue="kj" 
				url="systemController.do?getTypeByTypeGroupCode&typeGroupCode=zzpt" />
			<fd:inputText id="createName" name="createName" type="text"
				readonly="true" 
				title="{com.glaway.ids.pm.project.projectmanager.creator}"
				value="${project.createName}" />
			<fd:inputDate id="createTime" name="createTime"
				value="${project.createTime}" formatter="yyyy-MM-dd" readonly="true"
				type="text"
				title="{com.glaway.ids.pm.project.projectmanager.createTime}" />
			<fd:inputSearchUser id="projectManagers" name="projectManagers"
				title="项目助理"
				multiple="true" required="true" maxLength="-1"
				roleInputVal="${codeId}">
			</fd:inputSearchUser>
			<fd:comboGrid id="projectTemplate" name="projectTemplate"
				url="projectController.do?datagridTemplate"
				title="{com.glaway.ids.pm.project.projectmanager.projectTemplate}"
				idField="id" textField="projTmplName" multiple="false"
				prompt="不使用模板" panelHeight="300" panelWidth="250" toolbar="#toolbar" style="height:30px;">
				<fd:columns>
					<fd:column field="projTmplName" width="145" title=""></fd:column>
					<fd:column field="remark" width="50" title=""
						formatter="projectViewOption"></fd:column>
				</fd:columns>
			</fd:comboGrid>
			<fd:inputDate id="startProjectTime" name="startProjectTime"
				title="{com.glaway.ids.pm.project.projectmanager.startTime}"
				validType="validateStartTime['#startProjectTime']" editable="false"
				formatter="yyyy-MM-dd" />
			<fd:inputDate id="endProjectTime" name="endProjectTime"
				title="{com.glaway.ids.pm.project.projectmanager.endTime}"
				validType="validateEndTime['#endProjectTime']" editable="false"
				formatter="yyyy-MM-dd" />
		<%--	<fd:inputDate id="startProjectTime1" name="startProjectTime1" onChange="dateVali('begin')"
						  title="startProjectTime1"
						  editable="false"
						  formatter="yyyy-MM-dd" />
			<fd:inputDate id="endProjectTime2" name="endProjectTime2" onChange="dateVali('end')"
						  title="endProjectTime2"
						  editable="false"
						  formatter="yyyy-MM-dd" />--%>
			<fd:dictCombobox dictCode="timeType" id="projectTimeType"
				editable="false" selectedValue="workDay"
				title="{com.glaway.ids.pm.project.projectmanager.time}" />
			<fd:combotree title="{com.glaway.ids.pm.project.projectmanager.type}"
				treeIdKey="id" url="projectController.do?getEPSTree" name="epsName"
				id="epsName" treePidKey="pid" panelHeight="250" multiple="false"
				treeName="name" required="true" />
			<fd:combobox id="phase" name="phase" required="true" editable="false"
				title="{com.glaway.ids.pm.project.projectmanager.phase}"
				textField="name" valueField="id" panelMaxHeight="200"
				url="projectController.do?phaseListForDetail" />
			<fd:combobox id="mj" name="mj" required="true" editable="false"
				title="密级"
				textField="name" valueField="code" panelMaxHeight="200" selectedValue="1"
				url="systemController.do?getTypeByTypeGroupCode&typeGroupCode=mj" />
			<fd:inputTextArea name="projectMarkNo" id="projectMarkNo" 
				title="项目标的" />
			<fd:inputTextArea name="remark" id="remark"
				title="{com.glaway.ids.pm.project.projectmanager.remark}" />		
		</fd:form>

	<fd:dialog id="WPSPlanList" width="900px" height="500px" modal="true"
		title="{com.glaway.ids.pm.project.projectmanager.projectTemplateDetail}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}"
			callback="hideDiaLog" />
	</fd:dialog>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$("#projectTemplate").combogrid({
								onOpen : function() {
									$(".datagrid-header").hide();
									//$(".datagrid-header .datagrid-header-row").hide();
								}
							});

							//项目开始结束时间默认选中今天
							$('#startProjectTime').datebox('setValue',
									dateFmtYYYYMMDD(new Date()));
							$('#endProjectTime').datebox('setValue',
									dateFmtYYYYMMDD(new Date()));
							$
									.extend(
											$.fn.validatebox.defaults.rules,
											{
												validateStartTime : {
													validator : function(value,
															param) {
														var end = $(
																'#endProjectTime')
																.datebox(
																		'getValue');
														var endTime = $.fn.datebox.defaults
																.parser(end);
														var modifyTime = $.fn.datebox.defaults
																.parser(value);
														if (modifyTime > endTime) {
															return false;
														}
														else {
															//$('#endProjectTime').validatebox('validate');
														}
														return true;
													},
													message : '<spring:message code="com.glaway.ids.pm.project.projectmanager.startNoLaterThanEnd"/>'
												},
												validateEndTime : {
													validator : function(value,
															param) {
														var start = $(
																'#startProjectTime')
																.datebox(
																		'getValue');
														var startTime = $.fn.datebox.defaults
																.parser(start);
														var modifyTime = $.fn.datebox.defaults
																.parser(value);
														if (modifyTime < startTime) {
															return false;
														}
														return true;
													},
													message : '<spring:message code="com.glaway.ids.pm.project.projectmanager.endNoEarlierThanStart"/>'
												}
											});
						});

		function projectViewOption(value, row, index) {
			if (row.id == null) {
				return '';
			} else {
				return '<a title="查看模板" class="basis ui-icon-eye hideRowStyle" style="display: inline-block; cursor: pointer;" onclick="openDialogWPSPlans(\''
						+ row.id + '\')"/>';
			}
		}

		function openDialogWPSPlans(id) {
			var url = "projTemplateController.do?goProjTemplateLayout&id=" + id;
			createDialog('WPSPlanList', url);
		}

		function timeValidate(){

		}
	</script>
</body>