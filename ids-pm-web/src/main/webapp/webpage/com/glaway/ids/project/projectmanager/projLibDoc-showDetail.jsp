<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<t:base type="jquery_iframe,easyui_iframe,tools"></t:base>
<body>
<fd:datagrid url="projLibController.do?getVersionHistory&bizId=${projLibDocumentVo.bizId}" idField="id" id="bizProjLibDocList" checkbox="false" fit="true" fitColumns="true">
	<fd:dgCol title="{com.glaway.ids.common.lable.operation}" field="operation" width="100"
		formatterFunName="operation" />
	<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="docName" width="100" />
	<fd:dgCol title="{com.glaway.ids.common.lable.code}" field="docNumber" width="100" />
	<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.version}" field="version" width="100" />
	<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.remark}" field="remark" width="150" />
	<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.bizCurrent}" field="status" width="100" 
		formatterFunName="formatterStatus" />
	<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.updater}" field="createName" width="150" />
	<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.updateTime}" field="createTime" width="150" formatterFunName="dateFormatter1" />
</fd:datagrid>
<fd:dialog id="detProjLibDocDialog" width="870px" height="580px"
	modal="true" title="{com.glaway.ids.pm.project.projectmanager.viewDetail}">
	<fd:dialogbutton name="关闭" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
</body>

<script>
	function viewProcessDef(procInstId, title) {
		createdetailwindow(title,
				'generateFlowDiagramController.do?initDrowFlowActivitiDiagram&procInstId='
						+ procInstId, 800, 600);
	}

	function dateFormatter1(val, row, index) {
		return val;
	}

	function formatterStatus(val, row, value) {
		var bizCurrent = row.status;
 		if (bizCurrent == 'nizhi') {
			return '<spring:message code="com.glaway.ids.pm.project.projectmanager.nizhi"/>';
		} else if (bizCurrent == 'guidang') {
			bizCurrent = '<spring:message code="com.glaway.ids.pm.project.projectmanager.guidang"/>';
		} else if (bizCurrent == 'shenpi') {
			bizCurrent = '<spring:message code="com.glaway.ids.pm.project.projectmanager.shenpi"/>';
		} else if (bizCurrent == 'shengxiao') {
			bizCurrent = '<spring:message code="com.glaway.ids.pm.project.projectmanager.shengxiao"/>';
		} 
		/* 		return '<a href=\'javascript:void(0)\' onclick="viewProcessDef(\''
		 + row.procInstId
		 + '\',\''
		 + row.title
		 + '\')" title=\'查看流程图\'  class=\'basis ui-icon-eye\' style=\'display:inline-block\'></a>'; */
/* 		var title = row.docName;		 
 		return '<a href="#" onclick="viewProcessDef(\'' + row.procInstId
				+ '\',\'' + row.title
				+ '\')"><div style="text-align:left">'
				+ bizCurrent + '</div></a>';  */
		return bizCurrent;
	}

	function openProjDocFlow(taskNumber) {
		var tabTitle = '审批流程查看';
		var url = '${webRoot}/taskFlowCommonController.do?taskFlowList&taskNumber=' + taskNumber;
		createdetailwindow_close(tabTitle, url, 800, 400);
	}

	function operation(val, row, index) {		
		var returnStr = '<a class="basis ui-icon-eye" style="display:inline-block;cursor:pointer;" onClick="showVersionDetail(\''
        + row.id + '\')" title="查看"></a>'
/*         var returnStr = '<span style="cursor:hand"><a onclick="showVersionDetail(\''
				+ row.id
				+ '\')"><span class="basis ui-icon-eye" title="查看">&nbsp;&nbsp;&nbsp;&nbsp;</span></a></span>'; */
		return returnStr;
	}

	function showVersionDetail(id) {
		url = 'projLibController.do?viewProjectDocDetail&viewFlog=1&id=' + id;
		createDialog('detProjLibDocDialog', url);
	}
</script>
