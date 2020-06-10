<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>新增本地文档</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript">
        function afterSuccessCall(file, data, response){
            debugger;
            var win = $.fn.lhgdialog("getSelectParentWin");
            top.jeasyui.util.tagMask('close');
            var jsonObj = $.parseJSON(data);
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
                $('#invalidIds').val(invalidIds);
            }else{
                invalidIds = dowmLoadUrl;
                $('#invalidIds').val(invalidIds);
            }

            /* $('#projLibDocList').datagrid('appendRow',{
                dowmLoadUrl: dowmLoadUrl,
                attachmentName: attachmentName,
                attachmentShowName: attachmentShowName,
                uuid : uuid
            }); */
            win.$('#docattachmentName').val(attachmentName);
            win.$('#docattachmentURL').val(dowmLoadUrl);
            win.$('#docAttachmentShowName').val(attachmentShowName);

            $.ajax({
                type : "POST",
                url : "projLibController.do?doAddForTemplateLocalDoc&attachmentNames="+ encodeURI(attachmentName)+"&dowmLoadUrls="+ encodeURI(attachmentName),
                async : false,
                data :   win.$('#projLibDocAddForm').serialize(),
                success : function(data) {
                    debugger;
                    var d = $.parseJSON(data);
                    if (d.success) {
                        win.initInputs_('')
                        $.fn.lhgdialog("closeSelect");
                    }else{
                        $('#file_upload').uploadify('cancel', '*');
                    }


                }
            });
            return true;
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
              afterUploadSuccessMode="multi"
              uploader="planController.do?addFileAttachments&projectId=${projectId}"
              extend="*.*;" auto="false" showPanel="false" dialog="false" multi="false">
    <fd:eventListener event="onUploadSuccess" listener="afterSuccessCall" />
</fd:uploadify>
</body>
</html>
