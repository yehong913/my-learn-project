<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Excel导入</title>
<t:base type="jquery,easyui,tools"></t:base>
<script>
	// 下载导入模板
	function downloadTemplate() {
		window.location.href='planTemplateController.do?doDownloadExcelTemplate';
	}

	function downloadTemplateForProjTemp() {
		window.location.href='projTemplateController.do?doDownloadExcelTemplate';
	}
	
	function afterSubmitPlantemplate(file, data, response) {
        top.jeasyui.util.tagMask('close');
        var win = $.fn.lhgdialog("getSelectParentWin");
        var d = $.parseJSON(data);
        top.tip(d.msg); 
        if (!d.success) {
            $('#file_upload').uploadify('cancel', '*');
            if (d.obj) {
                planTemplate_downErrorReport(d.obj);
            }
        }
        else {
        	debugger;
            //var planListSearch = win.planListSearch();
           if( null != '${projectTemplateId}' && '${projectTemplateId}' != '' && '${projectTemplateId}' != undefined) {
				try{
					win.saveProjectTemplateExcelSuccess(d.obj);
				}catch (e) {

				}
				try{
					win.iframeFlush();
				}catch (e) {

				}
	            $.fn.lhgdialog("closeSelect");
		   } else {
				win.savePlanTemplateExcelSuccess(d.obj);
				$.fn.lhgdialog("closeSelect");
		   }
        }
    }
    
    function planTemFormsubmit() {
        var fileSize = $('#file_upload').data('uploadify').queueData.queueLength;
        var file=$('#file_upload').data('uploadify').queueData;
        if (fileSize <= 0) {
            top.tip('<spring:message code="com.glaway.ids.common.selectTemplate"/>');
        }
        else {
            $('#file_upload').fduploadify('upload');
        }
    }

	function planTemplate_downErrorReport(dataListAndErrorMap) {
		top.Alert.confirm(
				'<spring:message code="com.glaway.ids.common.confirmDownloadErrorReport"/>',
				function(r) {
					if (r) {
						var url = 'planTemplateController.do?downErrorReport';
						downloadErrorReport(url,dataListAndErrorMap);
					}
				});
	}
</script>
</head>
<body>
	<%-- <fd:inputText id="name" name="name" title="{com.glaway.ids.common.lable.name}" required="true"  ></fd:inputText> --%>
    <c:if test="${null !=projectTemplateId && '' !=projectTemplateId }">
		<fd:label title="{com.glaway.ids.common.lable.downloadTemplate}" id="file_upload_label"
				  jsFunc="downloadTemplateForProjTemp()" content="{com.glaway.ids.pm.project.plan.downloadExcel}" />
		<fd:uploadify name="name" id="file_upload" title="上传文件" 
				uploader="projTemplateController.do?doImportExcel&projectTemplateId=${projectTemplateId}"
				auto="false" multi="false" showPanel="false" dialog="false" extend="*.xls;*.xlsx;">
			<fd:eventListener event="onUploadSuccess" listener="afterSubmitPlantemplate" />
		</fd:uploadify>
    </c:if>    
    <c:if test="${null !=planTemplateId && '' !=planTemplateId }">
		<fd:label title="{com.glaway.ids.common.lable.downloadTemplate}" id="file_upload_label"
				  jsFunc="downloadTemplate()" content="{com.glaway.ids.pm.project.plan.downloadExcel}" />
		<fd:uploadify name="name" id="file_upload" title="上传文件" 
				uploader="planTemplateController.do?doImportExcel&planTemplateId=${planTemplateId}"
				auto="false" multi="false" showPanel="false" dialog="false" extend="*.xls;*.xlsx;">
			<fd:eventListener event="onUploadSuccess" listener="afterSubmitPlantemplate" />
		</fd:uploadify>
    </c:if>    
	<%-- <fd:inputTextArea id="remark" title="备注" name="remark" validType="onlyTextCheckOnLength[0,200]" ></fd:inputTextArea> --%>
	<input type="button" onclick="planTemFormsubmit()" id="btn_sub" style="display:none"/>
	
</body>
</html>