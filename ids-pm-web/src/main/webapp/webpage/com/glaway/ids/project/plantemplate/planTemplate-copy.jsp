<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>模板复制</title>
<t:base type="jquery,easyui,easyui_iframe,tools"></t:base>
<body>   
	<div class="easyui-layout" fit="true">
		<div data-options="region:'north',collapsible:false" style="height: 180px;">
		    <fd:inputText id="planTmplName" name="planTmplName" title="{com.glaway.ids.pm.project.plantemplate.name}" required="true" value="${tptmpl_.name}副本" />
			<fd:inputTextArea id="remark" name="remark" title="{com.glaway.ids.common.lable.remark}" value="${tptmpl_.remark}"/>
	    </div>
  	</div>
</body>

<script>
    function doCopyTemplate() {
    	var name = $('#planTmplName').val();
    	var remark = $('#remark').val();
    	if(name == '' || name == null || name == undefined) {
    		top.tip('<spring:message code="com.glaway.ids.pm.plantemplate.nameCannotEmpty"/>');
    		return;
    	} else if(name.length > 30) {
   			top.tip('<spring:message code="com.glaway.ids.pm.plantemplate.nameLengthBeyond"/>');
            return;
    	}
    	$.ajax({
            url : 'planTemplateController.do?doCopy&id=${tptmpl_.id}',
            type : 'post',
            data : {
            	name : name,
            	remark : remark
            },
            cache : false,
            success : function(data) {
            	var d = $.parseJSON(data);
            	top.tip(d.msg);
                if(d.success){
                    var win = $.fn.lhgdialog("getSelectParentWin");
                    win.reloadTable();
                    $.fn.lhgdialog("closeSelect");
                }
            }
        });
    }
</script>












