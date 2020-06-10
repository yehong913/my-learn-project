<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>变更原因</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>

<body>
	<fd:inputText id="changeType"  title="{com.glaway.ids.pm.project.plan.changeType}"  name="changeType" 
		readonly="true" value="${temporaryPlan_.changeTypeInfo.name}"/>
	<c:if test="${temporaryPlan_.changeInfoDocName != '' && temporaryPlan_.changeInfoDocName != null}">
		<fd:label title="{com.glaway.ids.common.lable.attachment}" id="file_upload_label"
			jsFunc="downloadAttachment()" content="${temporaryPlan_.changeInfoDocName}" />
	</c:if>
	<fd:inputTextArea id="changeRemark" title="{com.glaway.ids.common.lable.remark}" name="changeRemark" 
		readonly="true" value="${temporaryPlan_.changeRemark}"></fd:inputTextArea>	

<script type="text/javascript">
	// 下载导入模板
	function downloadAttachment2() {
		$.ajax({
			url : 'planChangeController.do?fileDown22',
			type : 'post',
			data : {},
			cache : false,
			success : function(data) {
				if (data != null) {
					var d = $.parseJSON(data);
					window.location.href='jackrabbitFileController.do?fileDown&filePath=${temporaryPlan_.changeInfoDocPath}&fileName='+d.obj;
				}
			}
		});
		//window.location.href='planChangeController.do?fileDown22&filePath=${temporaryPlan_.changeInfoDocPath}';
	}

	// 下载导入模板
	function downloadAttachment() {
		//window.location.href='planChangeController.do?fileDown22&filePath=${temporaryPlan_.changeInfoDocPath}';
		window.location.href='jackrabbitFileController.do?fileDown&filePath=${temporaryPlan_.changeInfoDocPath}&fileName=${temporaryPlan_.changeInfoDocName}';
	}

</script>
</body>
</html>