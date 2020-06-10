<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目分类</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	function formsubmit() {
		$('#modifyForm').form('submit', {
			onSubmit : function(param) {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close'); // 如果表单是无效的则隐藏进度条
				}
				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
				var d = $.parseJSON(data);
				var win = $.fn.lhgdialog("getSelectParentWin");
				top.tip(d.msg);
				if (d.success) {
					win.reloadEpsTable();
					$.fn.lhgdialog("closeSelect");
				} else {
					return false;
				}
			}
		});
	}
</script>
</head>
<body>
	<fd:form id="modifyForm" url="epsConfigController.do?doUpdate">
		<input id="id" name="id" type="hidden" value="${epsConfig_.id }">
		<input id="parentId" name="parentId" type="hidden"
			value="${epsConfig_.parentId }">
		<input id="path" name="path" type="hidden" value="${epsConfig_.path }">
		<input id="stopFlag" name="stopFlag" type="hidden"
			value="${epsConfig_.stopFlag }">
		<fd:inputText id="no" title="{com.glaway.ids.common.lable.code}" readonly="true"
			required="true" value='${epsConfig_.no}'></fd:inputText>
		<fd:inputText id="name" title="{com.glaway.ids.common.lable.name}"
			required="true" value='${epsConfig_.name}'></fd:inputText>
		<fd:inputTextArea id="configComment"
			title="{com.glaway.ids.common.lable.remark}"
			value='${epsConfig_.configComment}'></fd:inputTextArea>
		<input type="button" id="btn_sub" style="display: none;"
			onclick="formsubmit();">
	</fd:form>
</body>