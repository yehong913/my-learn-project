<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>问题详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
<script type="text/javascript">
var entityUri = "com.glaway.foundation.rep.entity.RepFileType";
var fieldName = '${fieldName}';
var fieldValue = encodeURI('${fieldValue}');
//新增软属性
function addRepFileTypeConfigProperty() {
	var dialogUrl = "repFileTypeConfigController.do?goAddAttr&entityUri="+entityUri+"&fieldName="+fieldName+"&fieldValue="+fieldValue;
	createDialog('createAddPropertyDialog', dialogUrl);
}

//删除软属性
function delRepFileTypeConfigPropertyByIndex(id,index){
	var row = $("#repFileTypeConfigCustomAttrList").datagrid("getRows")[index];	
	top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.confirmBatchDel"/>', function(r) {
	   if (r) {
			$.ajax({
				url :'repFileTypeConfigController.do?deleteAdditionalAttributeInfo&ids='+row.id+'&entityUri='+entityUri+'&fieldName='+fieldName+'&fieldValue='+fieldValue,
				type : 'post',
				cache : false,
				success : function(data) {
					debugger;
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
						reloadTable();
						$('#repFileTypeConfigCustomAttrList').datagrid('clearSelections');
					}
				}
			});
		}
	});
}


//批量删除
function delRepFileTypeConfigProperty() {
	var allSelect=$('#repFileTypeConfigCustomAttrList').datagrid('getSelections');
	if (0>=allSelect.length){
		tip('请选择需要删除的记录');
		return;
	}
	var ids='';
	for (var i=0;i<allSelect.length;i++){
		var select=allSelect[i];
		if(i==allSelect.length-1){
			ids=ids+select.id;
		}else{
			ids=ids+select.id+',';
		}
	}
	top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.repFileTypeConfig.confirmBatchDel"/>', function(r) {
	   if (r) {
			$.ajax({
				url :'repFileTypeConfigController.do?deleteAdditionalAttributeInfo&ids='+ids+'&entityUri='+entityUri+'&fieldName='+fieldName+'&fieldValue='+fieldValue,
				type : 'post',
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
						reloadTable();
						$('#repFileTypeConfigCustomAttrList').datagrid('clearSelections');
					}
				}
			});
		}
	});
}


function doConfirmOper(iframe) {
	iframe.saveEntityFieldAddAttrSet();
	 return false;
}

function showIsRequire(val,row,index) {
	if(val == "0"){
		return "非必填";
	}else if(val == "1"){
		return "必填";
	}
}


function formatDataType(val,row,value){
	if(val=='java.lang.String')
	{
		return '<spring:message code="com.glaway.foundation.common.type.string"/>';	
	}
	else if(val=='java.lang.Double')
	{
		return '<spring:message code="com.glaway.foundation.common.type.number"/>';
	}
	else if(val=='java.util.Date')
	{
		return '<spring:message code="com.glaway.foundation.common.type.date"/>';	
	}
	else
	{
		return val;
	}
}


	 
</script>
	<div id="repFileTypeConfigCustomAttrTop">
	    <c:if test="${type ne 'view'}">
			<fd:toolbar id="repFileTypeConfigCustomAttrToolbar"> 
				<fd:linkbutton value="{com.glaway.ids.pm.config.repFileTypeConfig.add}" iconCls="basis ui-icon-plus"  onclick = "addRepFileTypeConfigProperty();"></fd:linkbutton>
				<fd:linkbutton value="{com.glaway.ids.pm.config.repFileTypeConfig.delete}" iconCls="basis ui-icon-minus" onclick = "delRepFileTypeConfigProperty();"></fd:linkbutton>
			</fd:toolbar>
	    </c:if>
	</div>
	<fd:datagrid toolbar="repFileTypeConfigCustomAttrTop" checkbox="${type ne 'view'}"  pagination="false"
	idField="id" id="repFileTypeConfigCustomAttrList" 
	url="repFileTypeConfigController.do?getEntityAttributeAdditionalAttributeByEntityUri&entityUri=${entityUri}&entityAttrName=${fieldName}&entityAttrVal=${fieldValue}" fit="true" fitColumns="true">
		<fd:colOpt title="{com.glaway.ids.common.lable.operation}" width="50" hidden="${type eq 'view'}">
			<fd:colOptBtn iconCls="basis ui-icon-minus" tipTitle="{com.glaway.ids.pm.config.repFileTypeConfig.delete}" onClick = "delRepFileTypeConfigPropertyByIndex"/>
		</fd:colOpt>
		<fd:dgCol field="title" title="{com.glaway.foundation.attribute.tag}"/>
		<fd:dgCol field="name" title="{com.glaway.foundation.attribute.name}"/>
		<fd:dgCol field="require" title="是否必填" formatterFunName="showIsRequire"/>
		<fd:dgCol field="dataType" title="类型" formatterFunName="formatDataType"/>
	</fd:datagrid>
	<fd:dialog id="createAddPropertyDialog" title="{com.glaway.foundation.common.add}" width="1030" height="600">
		<fd:dialogbutton name="{com.glaway.foundation.common.ok}" callback="doConfirmOper"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.foundation.common.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="createUpdateProblemDialog" title="{com.glaway.foundation.common.update}" width="900" height="480">
		<fd:dialogbutton name="{com.glaway.foundation.common.ok}" callback="doConfirmOper"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.foundation.common.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="viewProblemDialog" title="详情" width="900" height="480">
		<fd:dialogbutton name="{com.glaway.foundation.common.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="qualityProblemGoSubmit" width="800px" height="180px" modal="true"
			title="{com.glaway.ids.project.plan.toApprove}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="qualityProblemGoSubmitProblem"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="selectLeaderDialog" width="700px" height="150px"
			beforClose="beCancel" modal="true" title="{com.glaway.ids.pm.project.risk.choseLeader}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="beOk"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="viewPlanDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
</body>
</html>
