<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新建项目库权限模板</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	function saveTemplate() {
		var isEditFlag = $('#isEditFlag').selectBooleanCheckbox('getValue');
		var name = $('#name').val();
		var remark = $('#remark').val();
		if (name == '' || name == undefined) {
			top
					.tip('<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateNameIsNotNull"/>');
			return;
		}
		$
				.ajax({
					url : 'projectLibTemplateController.do?doAdd',
					type : 'post',
					data : {
						name : name,
						remark : remark
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						top.tip(d.msg);
						if (d.success) {
							if ("on" == isEditFlag || true == isEditFlag) {
								$('#editProjectLibDialog')
										.lhgdialog(
												"open",
												"url:"
														+ 'projectLibTemplateController.do?goLibMenu&templateId='
														+ d.obj);
							} else {
								$.fn.lhgdialog("closeSelect");
							}
						} else {
							return false;
						}
					}
				});
	}

	function closeAll() {
		setTimeout(function() {
			$.fn.lhgdialog("closeAll");
		}, 300)
	}

	function beClose() {
		$.fn.lhgdialog("getSelectParentWin").closeAllDialog();
		return true;
	}

	function closeAllDialog() {
		setTimeout(function() {
			$.fn.lhgdialog("closeAll");
		}, 300)
	}
</script>
</head>
<body>
	<!-- 表单区域 -->
	<fd:form id="projectLibAddForm"
		url="projectLibTemplateController.do?doAdd">
		<fd:inputText id="name" name="name"
			title="{com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateName}"
			maxLength="30" required="true"></fd:inputText>
		<fd:inputTextArea id="remark"
			title="{com.glaway.ids.common.lable.remark}" name="remark"
			maxLength="200"></fd:inputTextArea>
		<fd:selectBooleanCheckbox id="isEditFlag" value="true"
			title="{com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateEditor}"
			name="isEditFlag" />
		<input type="button" id="btn_sub" style="display: none;"
			onclick="saveTemplate();">
	</fd:form>
	<fd:dialog id="editProjectLibDialog" height="800px" width="600px"
		beforClose="beClose" max="false" min="false" maxFun="true"
		title="{com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateEdit}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}"
			callback="closeAll"></fd:dialogbutton>
	</fd:dialog>

</body>