<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增计划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	function afterSubmitDocChange(data,file,response) {
		debugger;
		top.jeasyui.util.tagMask('close');
		var jsonObj = $.parseJSON(file);
		var fileStr = jsonObj.obj;
		var size = (file.size)/(1024*1024);
		if(size > 50 ){
			top.tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.doc.sizeLimit"/>');
			return false;
		}
		var attachmentShowName = fileStr.split(",")[3];
		var uuid = fileStr.split(",")[2];
		var dowmLoadUrl = fileStr.split(",")[1];
		var attachmentName = fileStr.split(",")[0];
		//隐藏字段，处理冗余数据
		var invalidIds = $('#invalidIds').val();
		if(invalidIds != null && invalidIds != '' && invalidIds != undefined){
			invalidIds = invalidIds +","+ dowmLoadUrl;
			$('#invalidIds1').val(invalidIds);
		}else{
			invalidIds = dowmLoadUrl;
			$('#invalidIds1').val(invalidIds);
		}

		/* $('#projLibDocList').datagrid('appendRow',{
			dowmLoadUrl: dowmLoadUrl,
			attachmentName: attachmentName,
			attachmentShowName: attachmentShowName,
			uuid : uuid
		}); */
		$('#docattachmentName1').val(attachmentName);
		$('#docattachmentURL1').val(dowmLoadUrl);
		$('#docAttachmentShowName1').val(attachmentShowName);
	}

/*	function afterSubmitChange() {
		var fileSize = $('#file_upload').data('uploadify').queueData.queueLength;
		if (fileSize <= 0) {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.selectLocaldoc"/>');
		}
		else {
			$('#file_upload').fduploadify('upload');
		}
	}*/


</script>

<body>
<fd:uploadify name="files" id="file_upload" title="{com.glaway.ids.common.lable.uploadAttachment}"
			  uploader="planController.do?addFileAttachments&projectId=${projectId}"
			  extend="*.*;" auto="true" showPanel="false" dialog="false" multi="true"
			  formData="documentTitle" >
	<fd:eventListener event="onUploadSuccess" listener="afterSubmitDocChange" />
</fd:uploadify>

<input id="docattachmentName1" name="docattachmentName1" type="hidden">
<input id="docattachmentURL1" name="docattachmentURL1" type="hidden">
<input id="docAttachmentShowName1" name="docAttachmentShowName1" type="hidden">
<input id="docSecurityLevelFrom1" name="docSecurityLevelFrom1" type="hidden">
<input name="invalidIds1" id="invalidIds1" type="hidden">

<%--	<fd:uploadify width="120"
				  name="up_button_import" id="up_button_import" title="新增本地文档"
				  afterUploadSuccessMode="multi"
				  uploader="planController.do?addFileAttachments&projectId=${projectId}"
				  extend="*.*" auto="true" showPanel="false" multi="false"
				  dialog="false" onlyButton="true">
		<fd:eventListener event="onUploadSuccess" listener="afterSuccessCall" />
	</fd:uploadify>--%>
</body>
</head>
</html>