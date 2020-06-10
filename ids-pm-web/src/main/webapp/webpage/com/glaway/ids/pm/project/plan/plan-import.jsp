<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>计划Mpp导入</title>
  <t:base type="jquery,easyui_iframe,tools"></t:base>
  <script type="text/javascript">
  
	// 下载导入模板
	function downloadTemplate() {
 		window.location.href='webpage/com/glaway/ids/pm/project/plan/plan.mpp';
		//postFormDataToAction("planController.do?exportPlanTemplate&projectId=${plan_.projectId}");
	}
	
	/**
	 * Jeecg Excel 导出
	 * 代入查询条件
	 */
	function postFormDataToAction(url) {
		var params = '';
		window.location.href = url + encodeURI(params);
	}
	
	var serverMsg = '上传发生失败！'
	function afterSubmitPlan(file, data, response) {
		top.jeasyui.util.tagMask('close');
		var jsonObj = $.parseJSON(data);
		if (jsonObj == null) {
			serverMsg = "成功";
		} else {
			serverMsg = jsonObj.msg;
		}
		var win = $.fn.lhgdialog("getSelectParentWin");
		var a = serverMsg.indexOf("成功");
		top.tip(serverMsg);
		if (a > -1) {
			var planListSearch = win.planListSearch();
			$.fn.lhgdialog("closeSelect");
		}
		else{
			$('#file_upload').uploadify('cancel', '*');
		}
	}
	
	function importFormsubmit() {
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
 
 <body style="overflow-y: hidden; padding: 10px 0px 0px 0px;" scroll="no">
	<fd:label title="{com.glaway.ids.common.lable.downloadTemplate}" id="file_upload_label"
			jsFunc="downloadTemplate()" content="{com.glaway.ids.pm.project.plan.downloadMpp}" />
	<fd:uploadify name="files" id="file_upload" title="上传文件" 
			uploader="${webRoot}/planController.do?importPlan&planId=${plan_.parentPlanId}&projectId=${plan_.projectId}&type=${type}&realPlanId=${plan_.id}&currentUserId=${currentUserId}"
			auto="false" multi="false" showPanel="false" dialog="false" extend="*.mpp;">
		<fd:eventListener event="onUploadSuccess" listener="afterSubmitPlan" />
	</fd:uploadify>
	<input type="hidden" id="btn_sub" onclick="importFormsubmit()" />
</body>	
</html>
