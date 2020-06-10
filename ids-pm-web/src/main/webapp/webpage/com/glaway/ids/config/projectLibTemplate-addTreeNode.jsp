<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目库权限模板新增库结构</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	function formsubmit() {
		var name = $('#name').val();
		var parentId = '${parentId}';
		var templateId = '${templateId}';
		if (name == '' || name == undefined) {
			top
					.tip('<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateNodeNotNull"/>');
			return;
		}
		$.ajax({
			url : 'projectLibTemplateController.do?doAddTreeNode',
			type : 'post',
			data : {
				name : name,
				parentId : parentId,
				templateId : templateId
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				top.tip(d.msg);
				if (d.success) {
					var win = $.fn.lhgdialog("getSelectParentWin");
					win.setParentSelectedNode(parentId,d.obj,'save');
					setTimeout(function() {
						$.fn.lhgdialog("closeSelect");
					}, 200);
				} else {
					return false;
				}
			}
		});
	}
</script>
</head>
<body>
	<fd:form id="addLibForm"
		url="projectLibTemplateController.do?doAddTreeNode">
		<fd:inputText id="name" title="{com.glaway.ids.common.lable.name}"
			required="true"></fd:inputText>
		<input type="button" id="btn_sub" style="display: none;"
			onclick="formsubmit();">
	</fd:form>
</body>