<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%
    Boolean isStandard = (Boolean) request.getAttribute("isStandard");
			pageContext.setAttribute("isStandard", isStandard);
%>
<!DOCTYPE html>
<html>
<head>
<title>模板详细信息</title>
<t:base type="jquery,easyui_iframe,tools"></t:base>
<script type="text/javascript" src="webpage/com/glaway/ids/rdtask/procTemplate-common.js"></script>
<!-- 页面时间格式化 -->
<body style="overflow-x: hidden;">
	<fd:panel  border="false" title="版本详情" collapsible="false" fit="false" width="100%">
		<fd:datagrid url="projTemplateController.do?getVersionHistory&bizId=${template.bizId}" width="100%"
	    		idField="id" id="procTmplListss" fitColumns="true" fit="false" checkbox="false">
			<fd:dgCol title="{com.glaway.ids.common.lable.operation}" field="operation" width="30"  formatterFunName="operation" />
			<fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.bizVersion}" field="bizVersion" width="30" formatterFunName="operation2" />
			<fd:dgCol title="{com.glaway.ids.rdflow.plan.modifyPeople}" field="creator" width="48" formatterFunName="updateFormatter1" />
			<fd:dgCol title="{com.glaway.ids.rdflow.plan.modifyTime}" field="createTime" width="75" formatterFunName="dateFormatter1" />
			<fd:dgCol title="{com.glaway.ids.common.lable.status}" field="bizCurrent" width="36"  formatterFunName="formatOp"/>
			<fd:dgCol title="{com.glaway.ids.common.lable.remark}" field="remark" width="75" />
		</fd:datagrid>
	</fd:panel>
	<fd:dialog id="saveAndSubmitDialog" width="530px" height="200px" modal="true"
			title="{com.glaway.ids.rdflow.plan.toApprove}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="saveAndSubmitOk"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="editFlowUp" height="600px" beforClose="test()" width="800px" max="false" min="false" maxFun="true" title="{com.glaway.ids.rdflow.plan.editFlowFlag}"></fd:dialog>
	<fd:dialog id="showVersionDetail" title="{com.glaway.ids.rdflow.plan.showVersionDetail}" width="1000" height="500" modal="true">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" id="showVersionDetail_closeBtn" callback="hideDialog"></fd:dialogbutton>
	</fd:dialog>
</body>

<script>
function dateFormatter1(val, row, index) {
	return val;
}
function updateFormatter1(val, row, index) {
	debugger;
	var realName=row.createName;
	if (realName != undefined && realName != null && realName != '') {
		return row.createFullName + "-" + row.createName;
	} else {
		return "";
	}
}
function dateFormatter(val, row, index) {
	//return dateFmtYYYYMMDD(val);
	return val.substr(0,11);
}
function formatStatus(val, row, value) {
	 
	if(val != undefined && val!=null && val!=''){
		if(val=='nizhi'){
			return '<spring:message code="com.glaway.ids.rdflow.plan.nizhi"/>';
		}else if(val=='qiyong'){
			return '<spring:message code="com.glaway.ids.rdflow.plan.qiyong"/>';
		}else if(val=='jinyong'){
			return '<spring:message code="com.glaway.ids.rdflow.plan.jinyong"/>';
		}else if(val=='shenhe'){
			return '<spring:message code="com.glaway.ids.rdflow.plan.shenhe"/>';
		}
	}
}

function operation(val, row, index){
	debugger;
	var returnStr ='<span style="cursor:hand"><a onclick="showVersionDetail2(\''+row.id+'\',\''+row.bizVersion+'\')" class="basis ui-icon-eye" title="查看" style="display: inline-block;cursor:hand;"></a></span>';
	return returnStr;
}

function showVersionDetail(id){
	openFlow_overdue2(id);
		
}

function openFlow_overdue2(id){
	 
	var newdate=new Date().getTime();
	$.ajax({
		type : 'POST',
		url : 'procTemplateController.do?downFile&newDate='+newdate,
		data :{
			id : id
		},
		success : function(data) {
			/*var url = "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/rdtaskworkfloweditor.jsp?templateId="+id+"&isEnableFlag=1&newDate="+newdate+"&userId=${userId}";*/
			var url = "${rdflowWeb_Nginx}/rdflowCommonController.do?goOpenEditorPage&templateId="+id+"&isEnableFlag=1&newDate="+newdate+"&userId=${userId}";
			$('#editFlowPt').lhgdialog("open","url:"+url);
		} 
	});
}

function operation2(val, row, index){
	var returnStr ='<span style="cursor:hand"><a style="color:blue" onclick="showVersionDetail2(\''+row.id+'\',\''+val+'\')">'+val+'</a></span>';
    return returnStr;
}

function showVersionDetail2(id,val){
		var url="${webRoot}/projTemplateController.do?goProjTemplateLayout&viewHistory=viewHistory&id="+id;
// 		$("#showVersionDetail").lhgdialog("open","url:"+url);
		createdetailwindow(val+'版本详情',url, 1000, 500);
		
}

function openFlow_overdue(){
	 
	var newdate=new Date().getTime();
	$.ajax({
		type : 'POST',
		url : 'procTemplateController.do?downFile&newDate='+newdate,
		data :{
			id : '${tptmpl_.id}'
		},
		success : function(data) {
			debugger
			var url = "${rdflowWeb_Nginx}/rdflowCommonController.do?goOpenEditorPage&templateId="+id+"&isEnableFlag=1&newDate="+newdate+"&userId=${userId}";
			/*var url = "${rdflowWeb_Nginx}/webpage/com/glaway/ids/rdtask/workFlow/examples/editors/rdtaskworkfloweditor.jsp?templateId=${tptmpl_.id}&isEnableFlag=1&newDate="+newdate+"&userId=${userId}";*/
			$('#editFlowPt').lhgdialog("open","url:"+url);
/* 			window.open(
					'webpage/com/glaway/ids/rdtask/workFlow/examples/editors/workfloweditor.html?templateId=${tptmpl_.id}&isEnableFlag=1',
					'编辑流程',
					'height=900,width=1440,top=10,left=0,toolbar=no,menubar=no,scrollbars=yes,location=no,status=no'); */
		} 
	});
}

function formatOp(value, row, index) {
	var status = "";
	if(value=='nizhi'){
		status = '拟制中';
	}else if(value=='qiyong'){
		status = '启用';
	}else if(value=='jinyong'){
		status = '禁用';
	}else if(value=='shenhe'){
		status = '审批中';
	}else {
		status = '修订中';
	}
	if(row.processInstanceId != '' && row.processInstanceId != null && row.processInstanceId != undefined){
		return '<a href=\'javascript:void(0)\' onclick="viewProcessDef(\''+row.processInstanceId +'\',\''+row.projTmplName+'\')" title=\'查看流程图\' ><font color=blue>'+status+'</font></a>'; 	      		
	}
	return status;
}

function viewProcessDef(procInstId,title)
{
	createdetailwindow(title+'-项目模板工作流','generateFlowDiagramController.do?initDrowFlowActivitiDiagram&procInstId='+procInstId,800,600);
}

</script>
<fd:dialog id="editFlowPt" height="900px"  width="1440px" title="查看" max="false" min="false" maxFun="true"></fd:dialog>

