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
	//新增流程节点
	function addRepFileTypeProcessTask() {
		var dialogUrl = "repFileTypeConfigController.do?goAddTask&repFileTypeId=${repFileTypeId}&formId=${formId}";
		createDialog('createAddTaskDialog', dialogUrl);
	}

	//删除流程节点
	function batchDelRepFileTypeProcessTask(){
		debugger;
		var rows = $("#repFileTypeProcessList").datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}

			top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.bpmn.confirmBatchDel"/>', function(r) {
				if (r) {
					$.ajax({
						url : 'repFileTypeConfigController.do?doDelRepFileTypeTask',
						type : 'post',
						data : {
							ids : ids.join(','),
							repFileTypeId : '${repFileTypeId}'
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								tip(d.msg);
								reloadTaskDataGrid();
								$("#repFileTypeProcessList").datagrid('unselectAll');
								ids = '';
							}
						}
					});
				}
			});

		}else{
			top.tip('<spring:message code="com.glaway.ids.pm.config.bpmn.selectDeleteData" />');
		}
	}
	function moveProcessTask(type){
		debugger;
		var rows = $("#repFileTypeProcessList").datagrid('getSelections');
		var allRows = $("#repFileTypeProcessList").datagrid('getRows');
		var size = allRows
		var ids = [];
		if (rows.length > 0) {
			if(rows.length > 1){
				top.tip('<spring:message code="com.glaway.ids.pm.config.bpmn.onlySelectOneData" />');
				return false;
			}
			if(type == 'up') {
				if(rows[0].orderNum == '1') {
					top.tip('<spring:message code="com.glaway.ids.pm.config.bpmn.notMoveFirstRowUp" />');
					return false;
				}
			}
			if(type == 'down') {
				var maxOrder = allRows.length;
				if(rows[rows.length-1].orderNum == maxOrder) {
					top.tip('<spring:message code="com.glaway.ids.pm.config.bpmn.notMoveLastRowDown" />');
					return false;
				}
			}
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}

			$.ajax({
				url : 'repFileTypeConfigController.do?doMoveRepFileTypeTask',
				type : 'post',
				data : {
					type : type,
					ids : ids.join(','),
					repFileTypeId : '${repFileTypeId}'
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						tip(d.msg);
						reloadTaskDataGrid();
						$("#repFileTypeProcessList").datagrid('unselectAll');
						ids = '';
					}
				}
			});

		}else{
			top.tip('<spring:message code="com.glaway.ids.pm.config.bpmn.selectOperationData" />');
		}
	}

	function delRepFileTypeConfigTask(tmp,index){
		debugger;
		var row = $("#repFileTypeProcessList").datagrid("getRows")[index];
		top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.bpmn.confirmBatchDel"/>', function(r) {
			if (r) {
				$.ajax({
					url : 'repFileTypeConfigController.do?doDelRepFileTypeTask',
					type : 'post',
					data : {
						ids : row.id,
						repFileTypeId : '${repFileTypeId}'
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTaskDataGrid();
							$("#repFileTypeProcessList").datagrid('unselectAll');
							ids = '';
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

	function doConfirmTask(iframe) {
		iframe.saveProcessTask();
		return false;
	}

	function formatRemark(val,row,index) {
		var comment = '';
		if(val != null && val != undefined && val != ''){
			comment= val;
		}
		return '<span title="'+comment+'">' + comment + '</span>';
	}

	function formatType(val,row,index) {
		debugger;
		value = '';
		if(val == 'singleSign'){
			value = '单人审批';
		}else if(val == 'vieSign'){
			value = '竞争审批';
		}else{
			value = '会签模式';
		}
		return value;
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

	function reloadTaskDataGrid(){
		$('#repFileTypeProcessList').datagrid('unselectAll');
		$('#repFileTypeProcessList').datagrid("reload");
	}

</script>
<div id="repFileTypeProcessTop">
	<c:if test="${type ne 'view'}">
		<fd:toolbar id="repFileTypeProcessToolbar">
			<fd:linkbutton value="{com.glaway.ids.pm.config.repFileTypeConfig.add}" iconCls="basis ui-icon-plus"  onclick = "addRepFileTypeProcessTask();"></fd:linkbutton>
			<fd:linkbutton value="{com.glaway.ids.pm.config.repFileTypeConfig.delete}" iconCls="basis ui-icon-minus" onclick = "batchDelRepFileTypeProcessTask();"></fd:linkbutton>
			<fd:linkbutton value="{com.glaway.ids.pm.config.repFileTypeConfig.up}" iconCls="basis basis ui-icon-up" onclick = "moveProcessTask('up');"></fd:linkbutton>
			<fd:linkbutton value="{com.glaway.ids.pm.config.repFileTypeConfig.down}" iconCls="basis basis ui-icon-down" onclick = "moveProcessTask('down');" ></fd:linkbutton>
		</fd:toolbar>
	</c:if>
</div>
<fd:datagrid toolbar="repFileTypeProcessTop" checkbox="${type ne 'view'}"  pagination="false" idField="id" id="repFileTypeProcessList"
			 url="repFileTypeConfigController.do?getRepFileTypeApproveProcess&repFileTypeId=${repFileTypeId}" fit="true" fitColumns="true" >
	<fd:colOpt title="{com.glaway.ids.common.lable.operation}" width="50" hidden="${type eq 'view'}">
		<fd:colOptBtn iconCls="basis ui-icon-minus" tipTitle="{com.glaway.ids.pm.config.repFileTypeConfig.delete}" onClick = "delRepFileTypeConfigTask"/>
	</fd:colOpt>
	<fd:dgCol field="name" title="名称"/>
	<fd:dgCol field="approveType" title="方式" formatterFunName="formatType"/>
	<fd:dgCol field="numbers" title="可选人数" formatterFunName="formatDataType"/>
	<fd:dgCol field="remark" title="备注" formatterFunName="formatRemark"/>
</fd:datagrid>
<fd:dialog id="createAddTaskDialog" title="{com.glaway.foundation.common.add}" width="780" height="300">
	<fd:dialogbutton name="{com.glaway.foundation.common.ok}" callback="doConfirmTask"></fd:dialogbutton>
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

