<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>修改路径</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function closeWindow(){
	$.fn.lhgdialog("closeSelect");
}

function saveProjLibFolderUpdatePath(){
	$.ajax({
		type : "POST", 
		url : "projLibController.do?doUpdatePath",
		async : false,
		data :  $('#projLibFolderUpdatePathForm').serialize(),
		success : function(data) {
			debugger;
			var d = $.parseJSON(data);	
			var msg = d.msg;
			top.tip(msg);
			if (d.success) {
				var win = $.fn.lhgdialog("getSelectParentWin");
				win.searchProjLibDoc();
				setTimeout(function(){
					$.fn.lhgdialog("closeSelect");
				},500);
			}			
		}
	});	
}
</script>
<script
	src="webpage/com/glaway/ids/pm/project/projectmanager/projLibDoc-add.js"></script>
<style type="text/css">
</style>
</head>
<body>
	<div style="padding: 1px;">
		<fd:form id="projLibFolderUpdatePathForm" method="projLibController.do?doUpdatePath">
			<input id="repFileIds" name="repFileIds" type="hidden"  value="${repFileIds}">
			<input name="parentId" id="parentId" type="hidden" value="${doc.parentId}">
			<input id="treeType" name="treeType" type="hidden" value="">
			<input id="projectId" name="projectId" type="hidden" value="${doc.projectId}">
			<input id="yanfa" name="yanfa" type="hidden" value="${yanfa}">
			<fd:inputText id="oldPath" name="oldPath"
					title="{com.glaway.ids.pm.project.projectmanager.oldPath}" required="true" readonly="true"
					value="${doc.path}" />
			
			<fd:inputSearch id="path" required="true" name="path"
						title="{com.glaway.ids.pm.project.projectmanager.newPath}"
						value="${doc.path}" editable="false"
						searcher="selectDocPath('#parentId','#path','#projectId','#node')" />					
		</fd:form>
	</div>
</body>