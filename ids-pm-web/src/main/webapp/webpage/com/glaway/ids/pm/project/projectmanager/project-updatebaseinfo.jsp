<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目修改</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
</style>
</head>

<body>
	<fd:form id="projectBasicUpdateForm">
		<input id="codeId" name="codeId" type="hidden" value="${codeId}" />
		<input id="id" name="id" type="hidden" value="${project_.id}" />
		<fd:inputText id="projectNumber" name="projectNumber" type="text"
			title="{com.glaway.ids.pm.project.projectmanager.projectNumber}"
			value="${project_.projectNumber}" required="true" readonly="true" />
		<fd:inputText id="createName" name="createName" type="text"
			title="{com.glaway.ids.pm.project.projectmanager.creator}"
			value="${project_.createName}" readonly="true" />
		<fd:inputText id="name" name="name" type="text"
			value='${project_.name}'
			title="{com.glaway.ids.pm.project.projectmanager.projectName}"
			required="true"  readonly="true"/>
		<fd:inputDate id="createTime" name="createTime" type="text"
			value="${project_.createTime}" formatter="yyyy-MM-dd"
			title="{com.glaway.ids.pm.project.projectmanager.createTime}"
			readonly="true" />
		<fd:inputSearchUser id="projectManagers" name="projectManagers"
			title="{com.glaway.ids.pm.project.projectmanager.projectManager}"
			multiple="true" required="true" maxLength="-1" readonly="true"
			value='${project_.projectManagers}' roleInputVal="${codeId}">
		</fd:inputSearchUser>
		<fd:combobox id="projectTemplate" name="projectTemplate"
			title="{com.glaway.ids.pm.project.projectmanager.projectTemplate}"
			panelMaxHeight="200" readonly="true" >
			<option value="">不使用模板</option>
			<c:forEach items="${projTemplateList}" var="projTemplate">
				<c:if test="${projTemplate.id eq project_.projectTemplate}"> 
					<option value="${projTemplate.id}" selected ="selected">${projTemplate.projTmplName}</option>
				</c:if>
			</c:forEach>
		</fd:combobox>
		<fd:inputDate id="startProjectTime" name="startProjectTime"
			value='${project_.startProjectTime}' title="{com.glaway.ids.pm.project.projectmanager.startTime}"
			validType="validateStartTime['#startProjectTime']" editable="false"
			formatter="yyyy-MM-dd" />
 		<fd:inputDate id="endProjectTime" name="endProjectTime"
			value='${project_.endProjectTime}' title="{com.glaway.ids.pm.project.projectmanager.endTime}"
			validType="validateEndTime['#endProjectTime']" editable="false"
			formatter="yyyy-MM-dd" />			
		<fd:dictCombobox dictCode="timeType" id="projectTimeType"
			editable="false" selectedValue="${project_.projectTimeType}"
			title="{com.glaway.ids.pm.project.projectmanager.time}" />
		<fd:combotree name="epsName" id="epsName"
			title="{com.glaway.ids.pm.project.projectmanager.type}" treeIdKey="id"
			url="projectController.do?getEPSTree" treePidKey="pid"
			panelHeight="250" multiple="false" treeName="name" required="true"
			value="${project_.epsName}" onLoadSuccess="setEpsName()" />
		<fd:combobox id="phase" name="phase" required="true" editable="false"
			title="{com.glaway.ids.pm.project.projectmanager.phase}" valueField="id"
			textField="name" selectedValue="${project_.phase}"
			panelMaxHeight="200"
			url="projectController.do?phaseListForDetail&projectId=${project_.id}" />
		<div id="processSpan">
			<fd:inputNumber id="process" name="process"
				validType="equalOneHundred[6]"
				title="{com.glaway.ids.pm.project.projectmanager.progress}"
				value="${project_.process}" precision="2" suffix="%" />
		</div>
		<fd:inputTextArea name="remark" id="remark"
			title="{com.glaway.ids.pm.project.projectmanager.remark}" value="${project_.remark}"/>
	</fd:form>
	
	<script type="text/javascript">
		$(document).ready(function() {
			debugger;
			var bizCurrent = '${project_.bizCurrent}';
			var projectTimeTypeIsModify = '${projectTimeTypeIsModify}';
			
			setTimeout(function(){
				$("#phase").combobox('setValue','${project_.phaseInfo.id}');
				$("#phase").combobox('setText','${project_.phase}');
			},500);
			
			
			if (bizCurrent == 'EDITING') {
				$("#processSpan").css('display', 'none');

				if (projectTimeTypeIsModify != null && projectTimeTypeIsModify == "1") {
					 $('#projectTimeType').combobox({
						readonly : true
					}); 
					changeoverTextboxReadonlySty("projectTimeType", true);
				}
				$('#projectNumber').textbox({
					readonly : false
				});
				$('#name').textbox({
					readonly : false
				});
				$('#projectManagers').textbox({
					readonly : false
				});
				changeoverTextboxReadonlySty("projectNumber", false);
				changeoverTextboxReadonlySty("name", false);
				changeoverTextboxReadonlySty("projectManagers", false);
			} else if (bizCurrent == 'STARTING') {
				if ($('#process').numberbox('getValue') == "") {
					$('#process').numberbox('setValue', '0.00');
				}
				$('#projectNumber').textbox({
					readonly : false
				});
				$('#name').textbox({
					readonly : false
				});
				$('#projectManagers').textbox({
					readonly : false
				});
				$('#projectTimeType').combobox({
					readonly : true
				});
				$('#epsName').textbox({
					readonly : true
				});
				changeoverTextboxReadonlySty("projectNumber", false);
				changeoverTextboxReadonlySty("name", false);
				changeoverTextboxReadonlySty("projectManagers", false);
				changeoverTextboxReadonlySty("projectTimeType", true);
				changeoverTextboxReadonlySty("epsName", true);
			}
			parent.loadTree("${project_.id}", "${insertRecent}");

			$.extend($.fn.validatebox.defaults.rules, {
				validateStartTime : {
					validator : function(value, param) {
						var end = $('#endProjectTime').datebox('getValue');
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
						var start = $('#startProjectTime').datebox('getValue');
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

		function setEpsName() {
			var eps = '${project_.eps}';
			$('#epsName').comboztree('setValue', eps);
		}
		
		function setPhaseName() {
			var phase = '${project_.phase}';
			$('#phase').comboztree('setValue', phase);
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
	</script>
</body>
</html>
