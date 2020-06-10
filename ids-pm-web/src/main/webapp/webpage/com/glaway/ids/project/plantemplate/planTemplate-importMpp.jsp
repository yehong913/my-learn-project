<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目计划模板列表</title>
<t:base type="jquery,easyui,tools"></t:base>
<script>

	// 下载导入模板
	function downloadTemplate() {
		window.location.href='webpage/com/glaway/ids/project/plantemplate/ModelFiles/mpp导入模板.mpp';
	}
	
	var serverMsg = '上传发生失败！'
	function afterSubmitPlantemplate(file, data, response){
		top.jeasyui.util.tagMask('close');
		var jsonObj = $.parseJSON(data);
		debugger;
		/* if (jsonObj == null) {
			serverMsg = '<spring:message code="com.glaway.ids.pm.project.plantemplate.plantemplate.doImportThree"/>';
		} else {
			serverMsg = jsonObj.msg;
		}
		
		var a = serverMsg.indexOf("成功");
		top.tip(jsonObj.msg); */
		var win = $.fn.lhgdialog("getSelectParentWin");
		top.tip(jsonObj.msg);
		if(jsonObj.success){
			win.savePlanTemplateExcelSuccess('${planTemplateId}');
			$.fn.lhgdialog("closeSelect");
			
		}else{
			$('#file_upload').uploadify('cancel', '*');
			
		}
		/* if(a > -1){
			var planListSearch = win.reloadTable();
			$.fn.lhgdialog("closeSelect");
		}
		else{
			$('#file_upload').uploadify('cancel', '*');
		} */
	}
	
	function planTemMppFormsubmit() {
		var fileSize = $('#file_upload').data('uploadify').queueData.queueLength;
  		if (fileSize <= 0) {
  			top.tip('<spring:message code="com.glaway.ids.common.selectTemplate"/>');
  		}
  		else {	
			$('#file_upload').fduploadify('upload');
  		}
	}
</script>
</head>
<body>
	<fd:label title="{com.glaway.ids.common.lable.downloadTemplate}" id="file_upload_label"
		jsFunc="downloadTemplate()" content="{com.glaway.ids.pm.project.plan.downloadMpp}" />
	<fd:uploadify name="name" id="file_upload" title="上传文件" 
			uploader="planTemplateController.do?doImportPlanTemMpp&planTemplateId=${planTemplateId }&currentUserId=${currentUserId}" 
			auto="false" multi="false" showPanel="false" dialog="false" extend="*.mpp;" formData="name,remark">
		<fd:eventListener event="onUploadSuccess" listener="afterSubmitPlantemplate" />
	</fd:uploadify>
	<input type="button" onclick="planTemMppFormsubmit()"  id="btn_sub" style="display:none"/>

</body>
</html>