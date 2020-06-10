<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>计划Excel导入</title>
  <t:base type="jquery,easyui_iframe,tools"></t:base>
  <script type="text/javascript">
  
	// 下载导入模板
	function downloadTemplate() {
 		//window.location.href='webpage/com/glaway/ids/project/plan/plan.xls';
		postFormDataToAction("planController.do?doDownloadExcelTemplate&isExport=false");
	}
	
	/**
	 * Jeecg Excel 导出
	 * 代入查询条件
	 */
	function postFormDataToAction(url) {
		var params = '';
		window.location.href = url + encodeURI(params);
	}
	
	function afterSubmitPlan(file, data, response) {
		top.jeasyui.util.tagMask('close');
        var win = $.fn.lhgdialog("getSelectParentWin");
        var d = $.parseJSON(data);
        top.tip(d.msg); 
        debugger
        if (!d.success) {
            $('#file_upload').uploadify('cancel', '*');
            if (d.obj != undefined || d.obj != 'undefined' || d.obj != null) {
            	debugger;
                win.plan_downErrorReport(d.obj);
            }
        }
        else {
        	var planListSearch = win.planListSearch();
            $.fn.lhgdialog("closeSelect");
        }
	}
	
	function importFormsubmit() {
  		var fileSize = $('#file_upload').data('uploadify').queueData.queueLength;
  		var file=$('#file_upload').data('uploadify').queueData;
  		if (fileSize <= 0) {
  			top.tip('<spring:message code="com.glaway.ids.common.selectTemplate"/>');
  		}
  		else {
			$('#file_upload').fduploadify('upload');
			var win = $.fn.lhgdialog("getSelectParentWin");
			var planListSearch = win.planListSearch();
  		}
	}
</script>
 </head>
 
 <body style="overflow-y: hidden; padding: 10px 0px 0px 0px;" scroll="no">
	<fd:label title="{com.glaway.ids.common.lable.downloadTemplate}" id="file_upload_label"
			jsFunc="downloadTemplate()" content="{com.glaway.ids.pm.project.plan.downloadExcel}" />
	<fd:uploadify name="files" id="file_upload" title="上传文件"
            uploader="${webRoot}/planController.do?doImportExcel&projectId=${plan_.projectId}&planId=${plan_.id}&type=${type}&userId=${userId}"
            dialog="false" auto="false" multi="false" showPanel="false" extend="*.xls;*.xlsx;">
        <fd:eventListener event="onUploadSuccess" listener="afterSubmitPlan" />
    </fd:uploadify>
	<input type="hidden" id="btn_sub" onclick="importFormsubmit()" />
</body>	
</html>
