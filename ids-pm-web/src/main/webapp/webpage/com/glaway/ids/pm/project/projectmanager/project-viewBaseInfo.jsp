<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划</title>
<t:base type="jquery,easyui,tools"></t:base>
<style type="text/css">
.panel-title{
    font-size: 12px;
    font-weight: bold;
    color: #0E2D5F;
    height: 36px;
    line-height: 36px;
    height: 30px;
}
</style>
<script src="webpage/com/glaway/ids/pm/project/plan/planList.js"></script>
</head>
<body> 

<script type="text/javascript">

function dateFormatter(val, row, value) {
	return val.substr(0, 19);
}

</script>

<fd:panel  border="false" title="详细信息" collapsible="false" fit="false" width="100%" height="400px">
		<fd:form id="projectBasicUpdateForm">
			<input id="id" name="id" type="hidden" value="${project_.id}" />
			<fd:inputText id="projectNumber" name="projectNumber" type="text"
				title="项目编号" value="${project_.projectNumber}" required="true"
				readonly="true"  />
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
			<fd:combotree id="epsName" name="epsName" title="项目分类" treeIdKey="id"
				treePidKey="pid" treeName="name" panelHeight="250" multiple="false"
				value="${project_.epsName}" url="projEPSController.do?getEPSTree"
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
</fd:panel>

<fd:panel  border="false" title="项目日志" collapsible="false" fit="false" width="100%"  height="200px">
		<fd:datagrid fit="true" fitColumns="true" idField="id"
				pagination="false" id="projLogList"
				url="projLogController.do?queryProjectLogNotPage&id=${project_.id}">
			<fd:dgCol title="项目编号" field="projectNumber" hidden="true" />
			<fd:dgCol title="操作" field="logInfo" width="180" />
			<fd:dgCol title="操作者" field="showName" width="100" />
			<fd:dgCol title="操作时间" field="createTime" width="140"
					formatterFunName="dateFormatter" />
			<fd:dgCol title="备注" field="remark" tipField="remark" width="280" />
		</fd:datagrid>
</fd:panel>

<fd:dialog id="startsAndSubmitDialog" width="730px" height="260px"
		modal="true" title="{com.glaway.ids.pm.project.projectmanager.start}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
		callback="startsAndSubmitOk"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
		callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="pauseAndSubmitDialog" width="730px" height="260px"
		modal="true" title="{com.glaway.ids.pm.project.projectmanager.pause}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
		callback="pauseAndSubmitOk"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
		callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="resumeAndSubmitDialog" width="730px" height="260px"
		modal="true" title="{com.glaway.ids.pm.project.projectmanager.resume}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
		callback="resumeAndSubmitOk"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
		callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="closePAndSubmitDialog" width="730px" height="260px"
		modal="true" title="{com.glaway.ids.pm.project.projectmanager.close}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
		callback="closePAndSubmitOk"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
		callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
	
</body>
</html>
