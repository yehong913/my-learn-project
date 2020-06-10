<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新建文档</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function closeWindow(){
	$.fn.lhgdialog("closeSelect");
}	
</script>

<style type="text/css">
</style>
</head>
<body>
	<div style="padding: 1px;">
			<fd:form id="projLibFolderAddForm" method="projectController.do?doSave">
			<input id="id" name="id" type="hidden"  value="${doc.id}">
			<input id="parentId" name="parentId" type="hidden" value="${doc.parentId}">
			<input id="type" name="type" type="hidden" value="${doc.type}">
			<input id="oldName" name="oldName" type="hidden" value="${doc.docName}">
			<input id="projectId" name="projectId" type="hidden" value="${doc.projectId}">
				<tr>
					<td class="value"><fd:inputText id="docName" name="docName" title="{com.glaway.ids.common.lable.name}" required="true" value="${doc.docName}" /></td>
				</tr>
		</fd:form>
	</div>
</body>
</html>