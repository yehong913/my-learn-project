<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
<!-- <script type="text/javascript"
	src="webpage/com/glaway/ids/project/task/taskCell-add.js"></script> -->
<style type="text/css">
	body {
		margin: 1px;
		padding: 0
	}
	
	input[disabled] {
		background: #f0f0f0;
		border: 1px solid #f0f0f0
	}
</style>

</head>
<body>
	<fd:tabs id="tt" tabPosition="top" fit="true">
		<fd:tab title="{com.glaway.ids.pm.project.plan.nodeinfo}" id="baseInfo"
			href="taskFlowResolveController.do?goTab&tabIndex=0&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}&refDuration=${refDuration}"></fd:tab>
<%-- 		<fd:tab title="{com.glaway.ids.pm.project.plan.input}" id="cellInput"
			href="taskFlowResolveController.do?goTab&tabIndex=2&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}"></fd:tab>
		<fd:tab title="{com.glaway.ids.pm.project.plan.reference}" id="cellReference"
			href="taskFlowResolveController.do?goTab&tabIndex=3&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}"></fd:tab>
		<fd:tab title="{com.glaway.ids.pm.project.plan.output}" id="cellOutput"
			href="taskFlowResolveController.do?goTab&tabIndex=4&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}"></fd:tab>
		<fd:tab title="{com.glaway.ids.pm.project.plan.resource}" id="cellResource"
			href="taskFlowResolveController.do?goTab&tabIndex=5&cellId=${cellId}&parentPlanId=${parentPlanId}&isEnableFlag=${isEnableFlag}"></fd:tab> --%>
	</fd:tabs>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		/* $('#tt').tabs('disableTab', 1);
		$('#tt').tabs('disableTab', 2);
		$('#tt').tabs('disableTab', 3);
		$('#tt').tabs('disableTab', 4); */
	});
</script>

<!-- 国际化修改 -->
<script type="text/javascript">
//Javascript Document
//edit by wyw

editIndex = undefined;

//结束编辑
function endEditing(dgId) {
	if (editIndex == undefined)
		return true;
	if ($('#' + dgId).datagrid('validateRow', editIndex)) {
		$('#' + dgId).datagrid('selectRow', editIndex).datagrid('endEdit',
				editIndex);
		editIndex = undefined;
		return true;
	} else
		return false;
}

//新增交付物
function openAdd(dgId, url, baseInfoId, id) {
	var allRows = $("#"+dgId).datagrid("getRows");
	var ids = '';
	if(allRows.length>0){
		for(var i in allRows){
			ids += ','+allRows[i].deliverId;
			
		}
		ids = ids.substring(1);
	}
	gridname = dgId;
	
	$("#"+id).lhgdialog("open",'url:' + url+"&ids="+ids+"&baseInfoId="+baseInfoId);
	
	

	
}

//取消节点交付项
function cancelCellDel(){
	setTimeout(
			function(){
				$.fn.lhgdialog("closeSelect");
			},500
		)
}

//新增
function append(dgId, row) {
	$('#' + dgId).datagrid('appendRow', row);
}
//接口调用删除选择的行
function removeSels(dgId) {
	var rows = $('#' + dgId).datagrid('getChecked');
	if (rows.length == 0) {
		var msg = '<spring:message code="com.glaway.ids.pm.project.task.selectBatchDel"/>';
		tip(msg);
		return;
	}
	var ids = [];
	for (var i = 0; i < rows.length; ++i) {
		if(rows[i].isNameStandardDefault=='true'){
			tip('<spring:message code="com.glaway.ids.pm.project.task.deliveryDelLimit"/>');
			return;
		}
		if(rows[i].isOutputDeliverRequired=='true'){
			tip('<spring:message code="com.glaway.ids.pm.project.task.noDelNecessaryDelivery"/>');
			return;
		}
		ids.push(rows[i].id);
	}
	$.ajax({
		url : 'taskFlowTemplateController.do?doDelBatchDeliverItem',
		type : "POST",
		dataType : "text",
		data : {
			ids : ids.join(",")
		},
		async : false,
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				tip(d.msg);
				$('#' + dgId).datagrid('reload');
				$('#' + dgId).datagrid('clearSelections');
				//$("#objTargetEditList").datagrid('unselectAll');
			}
		}
	});
}
//删除
function removeit(dgId) {
	$('#' + dgId).datagrid('endEdit', editIndex)
	var rows = $('#objResEditList').datagrid('getChecked');
	editIndex = undefined;
}
//输入项点击行事件
function onClickRowRes(index) {
	if (editIndex != index) {
		if (endEditing('objResEditList')) {
			$('#objResEditList').datagrid('selectRow', index).datagrid(
					'beginEdit', index);
			editIndex = index;
		} else {
			$('#objResEditList').datagrid('selectRow', editIndex);
		}
	}
}
//输出项点击行事件
function onClickRowTarget(index, row) {
	if (row.isNameStandardDefault) {
		tip('<spring:message code="com.glaway.ids.pm.project.task.deliveryEditLimit"/>');
		return;
	}
	if (editIndex != index) {
		if (endEditing('objTargetEditList')) {
			$('#objTargetEditList').datagrid('selectRow', index).datagrid(
					'beginEdit', index);
			editIndex = index;
		} else {
			$('#objTargetEditList').datagrid('selectRow', editIndex);
		}
	}
}

//批量修改行编辑
function onClickRowBatch(index, row) {
	if (editIndex != index) {
		if (endEditing('batchModifyEditList')) {
			$('#batchModifyEditList').datagrid('selectRow', index).datagrid(
					'beginEdit', index);
			editIndex = index;
		} else {
			$('#batchModifyEditList').datagrid('selectRow', editIndex);
		}
	}
}

function isNecessary(val, row, index) {
	if (val == "true"){
		return '<div>' +
				'<input type="checkbox" onclick="isNecessarySelect(this,\''+row.id +'\')" checked="checked"/>' +
				'</div>';
	}	
	else
		return "<div>" +
		"<input readOnly='readOnly' onclick='isNecessarySelect(this,\""+row.id +"\")'  type='checkbox'>" +
		"</div>";
}
function isNecessarySelect(el,id){
	if(el.checked){
		$.post("taskFlowTemplateController.do?isNecessarySelect",{itemId:id,isNecessaryValue:"true"},function(data){
			var data = $.parseJSON(data);
			tip(data.msg);
			if(data.success){
				$('#objTargetEditList').datagrid('reload');
			}
		});
	}else{
		$.post("taskFlowTemplateController.do?isNecessarySelect",{itemId:id,isNecessaryValue:"false"},function(data){
			var data = $.parseJSON(data);
			tip(data.msg);
			if(data.success){
				$('#objTargetEditList').datagrid('reload');
			}
		});
	}
	//$.post("taskFlowTemplateController.do?isNecessarySelect",{})
}

function saveChanges() {
	var rows = $('#batchModifyEditList').datagrid('getRows');
	for (var i = 0; i < rows.length; i++) {
		$('#batchModifyEditList').datagrid('endEdit', i);
	}
	var changeRows = $("#batchModifyEditList").datagrid("getChanges");
	// console.log(changeRows);
	// var rows = JSON.stringify(changeRows);

	var ids = [];
	var cellName = [];
	var nameStandardId = [];
	var isCellRequired = [];
	var refDuration = [];
	if (changeRows.length > 0) {
		for (var i = 0; i < changeRows.length; i++) {
			ids.push(changeRows[i].id);
			cellName.push(changeRows[i].cellName);
			nameStandardId.push(changeRows[i].nameStandardId);
			isCellRequired.push(changeRows[i].isCellRequired);
			refDuration.push(changeRows[i].refDuration);
		}
	}
if(ids.length==0){
	window.parent.closeDiv("muchUpdate");
	window.parent.cleanSelectCells();
	top.tip('<spring:message code="com.glaway.ids.pm.project.task.saveSuccess"/>');
	return;
}
	$.post("taskFlowTemplateController.do?doBatchSaveBasicInfo", {
		ids : ids.join(","),
		cellName : cellName.join(","),
		nameStandardId : nameStandardId.join(","),
		isCellRequired : isCellRequired.join(","),
		refDuration : refDuration.join(","),
		isStandard : $("#isStandard").val(),
		taskId : $("#taskId").val()
	}, function(data) {
		var d= $.parseJSON(data);
		var b = d.msg.split('<br/>')
		top.tip(b[0]);
		if(d.success){
			window.parent.closeDiv("muchUpdate");
			window.parent.cleanSelectCells();	
		}
		//tip("dasdsadsa");
	});
}

function showAbleCheckbox(val, row, index){
	if(val=='true'){
		row.myflag="true";
		return '<div>' +
		'<input type="checkbox" checked="checked" id="showAbleCheck_'+row.id+'" />' +
		'</div>';
		
	}else{
		row.myflag="false";
		return '<div>' +
		'<input type="checkbox" id="showAbleCheck_'+row.id+'" />' +
		'</div>';
	}
}

//function showAbleCheckbox(val, row, index){
//		return '<div>' +
//		'<input type="checkbox"  onclick="checkChange('+row+')"  />' +
//		'</div>';
//}
//
//function checkChange(row){
//	top.tip(this)
//}

//批量修改行编辑
function onClickRowItem(index, row) {
	if (editIndex != index) {
		if (endEditing('deliverablesList')) {
			$('#deliverablesList').datagrid('selectRow', index).datagrid(
					'beginEdit', index);
			editIndex = index;
		} else {
			$('#deliverablesList').datagrid('selectRow', editIndex);
		}
	}
}

function tagSearchResetForTaskDeliver(){
	$("#no").textbox("clear");
	$("#searchName").textbox("clear");
}


</script>
