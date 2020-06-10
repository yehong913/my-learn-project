<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>另存为项目模板</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	$(document).ready(function() {

	});
</script>

</head>
<body>
<script type="text/javascript">
function closeAllDialog() {
	setTimeout(function() {
		$.fn.lhgdialog("closeAll");
	}, 100)
}
	function checkLib(){
		var lib = $('#lib').selectBooleanCheckbox('getValue');
		var libPower = $('#libPower').selectBooleanCheckbox('getValue');
		if(lib == "on" || lib){
		}else{
			$('#libPower').selectBooleanCheckbox('setValue',false);}
	}
	function checkLibPower(){
		var lib = $('#lib').selectBooleanCheckbox('getValue');
		var libPower = $('#libPower').selectBooleanCheckbox('getValue');
		if(libPower == "on" || libPower){
			$('#lib').selectBooleanCheckbox('setValue',true);
		}
	}
</script>
	<div style="padding: 1px;">
		<fd:form id="projTemplateAddForm">
			<input id="projectId" name="projectId" type="hidden"
				value="${projectId}" />
			<fd:inputText id="projTmplName" name="projTmplName" type="text"
				required="true"
				title="{com.glaway.ids.pm.projecttemplate.name}" />
			<fd:inputText id="createName" name="createName" readonly="true" 
				title="{com.glaway.ids.pm.project.projecttemplate.creator}"
				value="${projectTemplate.createName}" />
			<div style="clear:both;padding-left: 38px">
				<spring:message code="com.glaway.ids.pm.project.projecttemplate.saveAs" />
			</div>
			<fd:selectBooleanCheckbox id="plan"
				title="{com.glaway.ids.pm.project.projecttemplate.savePlans}"
				value="true" name="savePlans" />
			<fd:selectBooleanCheckbox id="team"
				title="{com.glaway.ids.pm.project.projecttemplate.saveTeams}"
				value="true" name="saveTeams" />
			<div onclick="checkLib()">
			<fd:selectBooleanCheckbox id="lib"
				title="{com.glaway.ids.pm.project.projecttemplate.saveLibs}"
				value="true" name="saveLibs" />
			</div>		
			<div onclick="checkLibPower()">
			<fd:selectBooleanCheckbox  id="libPower"
				title="{com.glaway.ids.pm.project.projecttemplate.saveLibPowers}"
				value="true" name="saveLibPower" />	
			</div>		
			<fd:inputTextArea
				title="{com.glaway.ids.pm.project.projecttemplate.remark}"
				name="remark" id="remark" value="${projectTemplate.remark}" />
		</fd:form>
		<fd:selectBooleanCheckbox  id="isEdit"
				title="{com.glaway.ids.pm.project.projecttemplate.isEdit}"
				value="true" name="isEdit" />	
	</div>
</body>
