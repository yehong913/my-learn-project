<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<t:base type="jquery,easyui_iframe,tools"></t:base>
<head>
	<title>活动基本信息批量修改</title>
	<%-- <script type="text/javascript" src="<%=basePath %>/webpage/com/glaway/ids/project/task/taskCell-add.js"></script> --%>
	<script>
		//保存批量修改后的数据
		/* function saveChanges() {
			var rows = $('#batchModifyEditList').datagrid('getChanges');
			console.log(rows);
			$.post("taskFlowTemplateController.do?doBatchSaveBasicInfo",
					{
						changeRows : rows
					},
					function(data){
						console.log(data);
					});
		} */
		$(function(){
			$.post("procTemplateController.do?standardValue");
			$.post("procTemplateController.do?isStandard");}	
			)
			
			
		function cellNameShow(val, row, index) {
			var standardValue = eval($("#standardValue").val());
			for (var i = 0; i < standardValue.length; i++) {
				if (val == standardValue[i].id) {
					row.cellName=standardValue[i].name;
					return standardValue[i].name;
				}
			}
			if(val == undefined || val == "undefined" || val == ""){
				val = row.cellName;
			}
			return val;
		}
		/* function nameStandardOnChange(newVal,oldVal){
			var standardValue = eval($("#standardValue").val());
			for (var i = 0; i < standardValue.length; i++) {
				if (newVal == standardValue[i].id) {
					$(this).combobox('setValue',standardValue[i].name);
				}
			}
		}	 */
		
		function close1(){
			window.parent.closeDiv("muchUpdate");
		}
		
		function isNecessaryByBatchModify(val, row, index) {
			if (val == "true")
				return '<div>' +
						'<input type="checkbox" checked="checked"/>' +
						'</div>';
			else
				return "<div>" +
				"<input readOnly='readOnly' type='checkbox'>" +
				"</div>";
		}
	</script>
	<style type="text/css">
		body{margin:0; padding:0}
	</style>
</head>
<body>
	<input type="hidden" id ='standardValue' name='standardValue' value="${standardValue}"/>
	<input type="hidden" id ='isStandard' name='isStandard' value="${isStandard}"/>
	<input type="hidden" id ='taskId' name='taskId' value="${templateId}"/>
	<c:if test="${isStandards == 'ok'}">
	<fd:datagrid toolbar="#tb" url="taskFlowTemplateController.do?datagrid&templateId=${templateId}"  idField="id" id="batchModifyEditList" checkbox="false" pagination="false" 
		fit="true" fitColumns="true" onClickFunName="onClickRowBatch">
				<fd:dgCol title="{com.glaway.ids.pm.project.task.activityname}" field="nameStandardId" width="300" align="center" formatterFunName="cellNameShow" editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/procTemplateController.do?standardValue',
					            method:'get',
					            valueField:'id',
					            textField:'name'
							}}"/>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.isRequired}" field="isCellRequired" width="100" align="center" editor="{type:'checkbox',options:{
								on:'true',
								off:'false'
							}}" formatterFunName="isNecessaryByBatchModify"/>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.workTimeReference}" field="refDuration" width="100" align="center" editor="{type:'numberbox',options:{
				            onChange:onChanges
						}}"/> 
		<fd:dgCol title="cellName" field="cellName" width="100" hidden ="true" align="center"/> 
	</fd:datagrid>
	</c:if>
	
	<c:if test="${isStandards == 'nook'}">
		<fd:datagrid toolbar="#tb" url="taskFlowTemplateController.do?datagrid&templateId=${templateId}"  idField="id" id="batchModifyEditList" checkbox="false" pagination="false" 
		fit="true" fitColumns="true" onClickFunName="onClickRowBatch">
				<fd:dgCol title="{com.glaway.ids.pm.project.task.activityname}" field="nameStandardId" width="300" align="center" editor="{type:'text'}" formatterFunName="cellNameShow" />	
		<fd:dgCol title="{com.glaway.ids.pm.project.task.isRequired}" field="isCellRequired" width="100" align="center" editor="{type:'checkbox',options:{
								on:'true',
								off:'false'
							}}" formatterFunName="isNecessaryByBatchModify"/>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.workTimeReference}" field="refDuration" width="100" align="center" editor="{type:'numberbox',options:{
				            onChange:onChanges
						}}"/> 
		<fd:dgCol title="cellName" field="cellName" width="100" hidden ="true" align="center"/> 
	</fd:datagrid>
	</c:if>
</body>
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

function onChanges(newValue, oldValue){
	if(newValue != undefined && newValue < 1){
			$(this).numberbox('setValue',1);	
	}
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

function closeThisDialog() {
	$.fn.lhgdialog("closeSelect");
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
	var ids = [];
	var cellName = [];
	var nameStandardId = [];
	var isCellRequired = [];
	var refDuration = [];
	if (changeRows.length > 0) {
		var cellInfos = [];
		for (var i = 0; i < changeRows.length; i++) {
			ids.push(changeRows[i].id);
			if(changeRows[i].nameStandardId != undefined && changeRows[i].nameStandardId != "undefined" && changeRows[i].nameStandardId != ""){
				cellName.push(changeRows[i].nameStandardId);
			}else{
				cellName.push(changeRows[i].cellName);
			}
			nameStandardId.push(changeRows[i].nameStandardId);
			isCellRequired.push(changeRows[i].isCellRequired);
			refDuration.push(changeRows[i].refDuration);
			cellInfos.push(changeRows[i].cellIndex + "," + $.trim(changeRows[i].cellName) + "," + $.trim(changeRows[i].refDuration));
		}
	}
if(ids.length==0){
	var win = $.fn.lhgdialog("getSelectParentWin");
	win.cleanSelectCells();
	/* window.parent.closeDiv("muchUpdate");
	window.parent.cleanSelectCells(); */
	top.tip('<spring:message code="com.glaway.ids.pm.project.task.saveSuccess"/>');
	$.fn.lhgdialog("closeSelect");
}

if($("#isStandard").val() == 'true'){
		$.ajax({
			url : 'deliverablesInfoController.do?pdNameForOthers',
			type : 'post',
			data : {
				cellName : cellName.join(",")
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				var msg = d.msg;
				if(d.success){
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
							/* window.parent.closeDiv("muchUpdate");
							window.parent.cleanSelectCells(); */	
							var win = $.fn.lhgdialog("getSelectParentWin");
							win.reloadCellInfos(cellInfos);
							win.cleanSelectCells();
							$.fn.lhgdialog("closeSelect");
						}
					});
				}else{
					editIndex = undefined;
					top.tip(msg);
					return false;
				}
			}
		});
}else{
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
			var win = $.fn.lhgdialog("getSelectParentWin");
			win.reloadCellInfos(cellInfos);
			win.cleanSelectCells();
			$.fn.lhgdialog("closeSelect");
			/* window.parent.closeDiv("muchUpdate");
			window.parent.cleanSelectCells(); */	
		}
		//tip("dasdsadsa");
	});
  }
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
