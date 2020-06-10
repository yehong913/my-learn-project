<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Excel导入</title>
<t:base type="jquery,easyui_iframe,tools"></t:base>
<script type="text/javascript">
	function doSubmit(d, file, response) {
		top.jeasyui.util.tagMask('close');
		var win = $.fn.lhgdialog("getSelectParentWin");
		var d = $.parseJSON(file);
		top.tip(d.msg);	
		if (!d.success) {
			$('#businessConfig_upload').uploadify('cancel', '*');
			if (d.obj) {
				win.businessConfig_downErrorReport(d.obj);
			}
		}
		else {
			win.reloadBusinessConfigList();
			$.fn.lhgdialog("closeSelect");
		}
	}

	function businessConfigTemplateExport() {
		var url = 'planBusinessConfigController.do?doDownloadBusinessConfig&configType=${configType}';
		window.location.href = url + '&downloadType=0';
	}

	function importFormsubmit() {
		var fileSize = $('#businessConfig_upload').data('uploadify').queueData.queueLength;
		if (fileSize <= 0) {
			top.tip('<spring:message code="com.glaway.ids.common.selectTemplate"/>');
		} else {
			$('#businessConfig_upload').fduploadify('upload');
			var win = $.fn.lhgdialog("getSelectParentWin");
			win.reloadBusinessConfigTable('${configType}');
		}
	}
</script>
</head>
<body style="overflow-y: hidden; padding: 10px 0px 0px 0px;" scroll="no">
	<fd:label title='{com.glaway.ids.common.lable.downloadTemplate}' id="file_upload_label" 
		jsFunc="businessConfigTemplateExport()" content="${configName}导入模板.xls" />
	<fd:uploadify name="files" id="businessConfig_upload" title="上传文件"
		uploader="planBusinessConfigController.do?doImportExcel&configType=${configType}"
		extend="*.xls;" auto="false" showPanel="false" dialog="false" multi="false">
		<fd:eventListener event="onUploadSuccess" listener="doSubmit" />
	</fd:uploadify>
	<input type="button" onclick="importFormsubmit()" id="btn_sub" style="display: none" />
</body>
</html>
