<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增本地文档</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	function afterSubmitDocChange(data,file,response) {
		debugger;
		top.jeasyui.util.tagMask('close');
        var win = $.fn.lhgdialog("getSelectParentWin");
        var d = $.parseJSON(file);
        top.tip(d.msg);
        if (!d.success) {
            $('#file_upload').uploadify('cancel', '*');
        }
        else {
        	win.reloadTable();
            $.fn.lhgdialog("closeSelect");
        }
	}
	
	function afterSubmitChange() {
        var fileSize = $('#file_upload').data('uploadify').queueData.queueLength;
        if (fileSize <= 0) {
            top.tip('<spring:message code="com.glaway.ids.pm.project.plan.selectLocaldoc"/>');
        }
        else {
            $('#file_upload').fduploadify('upload');    
        }
	}
</script>
</head>
<body style="overflow-y: hidden; padding: 10px 0px 0px 0px;" scroll="no">
	<fd:uploadify name="files" id="file_upload" title="{com.glaway.ids.common.lable.uploadAttachment}"
		uploader="taskFlowResolveController.do?addFileAttachmentsChange&parentPlanId=${parentPlanId}&useObjectId=${inputs_.useObjectId}&type=LOCAL"
		extend="*.*;" auto="false" showPanel="false" dialog="false" multi="true"
		formData="documentTitle" >
		<fd:eventListener event="onUploadSuccess" listener="afterSubmitDocChange" />
	</fd:uploadify>
</body>
</html>
