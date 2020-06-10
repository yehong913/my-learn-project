<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划另存为模板</title>
<t:base type="jquery_iframe,easyui_iframe,tools,DatePicker,autocomplete"></t:base>
	<script type="text/javascript"
	<script src="webpage/com/glaway/ids/project/plantemplate/planTemplate.js"></script>
	<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
</head>
<div id="tepm">
	<fd:form id="formobj" >
		<fd:inputText title="{com.glaway.ids.pm.project.plantemplate.name}" id="name" name="name"
			required="true"></fd:inputText>
		<fd:inputText id="creatorName" name="creatorName" title="{com.glaway.ids.common.lable.creator}"
			value="${creator_.realName}-${creator_.userName}"  readonly="true" ></fd:inputText>
		<input id="creatorId" name="creatorId" type="hidden" value="${creator_.id}">
		<fd:inputText id="createdTime" name="createdTime" title="{com.glaway.ids.common.lable.createtime}"
			readonly="true" ></fd:inputText>
		<fd:inputTextArea id="remark" name="remark" title="{com.glaway.ids.common.lable.remark}">
		</fd:inputTextArea>
	</fd:form>
<script>

	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1,
			"d+" : this.getDate(),
			"h+" : this.getHours(),
			"m+" : this.getMinutes(),
			"s+" : this.getSeconds()
		}
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	}

	$(document).ready(
			function() {
				//给时间控件加上样式
				$("#createdTime").textbox("setValue",getNowDay());
			});

	function getNowDay() {
		var date = new Date();
		var temple = date.format("yyyy/MM/dd");
		return temple;
	}
	//用于区分保存和保存提交按钮
	var btnType = "";
	//获取保存后的计划模板id
	function afterSubmit(planTemplateId) {


		if(btnType=='submit'){
			var dialogUrl = 'planTemplateController.do?goSubmitApprove&planTemplateId='
				+ planTemplateId;
			createDialog('submitApproveDialog', dialogUrl);
		}
	}
	function planSaveTemValidateFun(){
		var win = $.fn.lhgdialog("getSelectParentWin");
		if($("#name").val()==""){
			win.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyName"/>');
			return false;
		}
	}

	function planSaveAsTemplateSubmit(){
		$("#formobj").form('submit',{
				url:'planTemplateController.do?savePlanTemplateByPlanject&planId=${plan_.id}&projectId=${projectId}',
				onSubmit:function(){//提交
					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
			    success:function(data){

			    	var d = $.parseJSON(data);
			    	var win = $.fn.lhgdialog("getSelectParentWin");
				    win.tip(d.msg);
				    //判断如果转模板 成功 跳提交 页面   如果转模板失败则  关窗口
				    if(d.success == true)
			    	{
			    		var planTemplateId=d.obj;
					    afterSubmit(planTemplateId);
			    	}else
			    	{
			    		$.fn.lhgdialog("closeSelect");
			    	}
					//$.fn.lhgdialog("closeSelect");

			    }

		});
	}

	function planSaveAsTemplate(){
		$("#formobj").form('submit',{
				url:'planTemplateController.do?savePlanTemplateByPlanject&planId=${plan_.id}&projectId=${param.projectId}',
				onSubmit:function(){//提交
					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
			    success:function(data){
			    	var d = $.parseJSON(data);
			    	var win = $.fn.lhgdialog("getSelectParentWin");
				    win.tip(d.msg);
					$.fn.lhgdialog("closeSelect");

			    }

		});
	}

	function submitApproveOK(iframe){
		iframe.startPlanTemProcess();
		return false;
	}

</script>
</div>
<fd:dialog id="submitApproveDialog" width="400px" height="180px" modal="true"
			title="{com.glaway.ids.pm.project.plan.toApprove}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="submitApproveOK"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
</html>


