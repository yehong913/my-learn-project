<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="plug-in/jquery-easyui/themes/icon.css">
<%-- <script type="text/javascript"
	src="<%=basePath%>/webpage/com/glaway/ids/project/task/taskCell-add.js"></script> --%>
</head>
<body class="easyui-layout" fit="true">
	<script type="text/javascript">
	//获取交付物列表选择行数据
	function saveSelectedRows(baseInfoId){
		 
	    var names = [];
	    var ids =[];
	    var isOutputDeliverRequired = [];
	    var  datas;
	    
	    var rows = $("#deliverablesList").lazydatagrid('getChecked');
	    
	    //return ;
	    
	    if (rows.length > 0) {
	    	
			for ( var i = 0; i < rows.length; i++) {
				names.push(rows[i].name);
				ids.push(rows[i].id);
				isOutputDeliverRequired.push(rows[i].isOutputDeliverRequired);
			}
			//return ;
			
			isOutputDeliverRequired.push('');
			 datas= {
					 baseInfoId : baseInfoId,
					 type : $("#type").val(),
					 deliverNames : names.join(','),
					 deliverIds :ids.join(','),
					 isOutputDeliverRequired:isOutputDeliverRequired.join(',')
				};
			 
			$.ajax({
				url : 'taskFlowTemplateController.do?doAddDeliverItem',
				async : false,
				cache : false,
				type : 'post',
				data : datas,
				cache : false,
				success : function(data) {
					 
					var d = $.parseJSON(data);
					var win = $.fn.lhgdialog("getSelectParentWin");
					win.reloadTable();
					top.tip(d.msg);
					datas=d.msg;
					cancelCellDel();
				}
			});
		} else {
			var win = $.fn.lhgdialog("getSelectParentWin");
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.selectAdd"/>');
		}
	    return datas;
	}
	
</script>

	<c:if test="${type=='OUTPUT'}">
		<script>
/* $(function(){
	$("#deliverablesList").datagrid({
		onLoadSuccess:function(){
			$(".datagrid-header-check").children("input").remove();
			$(".datagrid-header-check").append("<div class='datagrid-cell-check' style='width:40px !important'><span>必要</span></div>");
			$(".datagrid-header-check").attr("class","datagrid-cell datagrid-cell-c2-configComment").attr("style","width : 50px !important;");
			$(".datagrid-cell-check").attr("style","width:58px !important");
		},
		onCheck:function(row){
			row.isOutputDeliverRequired= "true";
		},
		onUncheck:function(row){
			row.isOutputDeliverRequired= "false";
		}
	});
	
}) */

/* function loadFactor(){
	$(".datagrid-header-check").children("input").remove();
	$(".datagrid-header-check").append("<div class='datagrid-cell-check' style='width:40px !important'><span>必要</span></div>");
	$(".datagrid-header-check").attr("class","datagrid-cell datagrid-cell-c2-configComment").attr("style","width : 50px !important;");
	$(".datagrid-cell-check").attr("style","width:58px !important");
} */

function defaultVal (){
	return "false";
}

</script>
	</c:if>

	<div region="center" style="padding: 1px;">
		<input id="cellId" name="cellId" type="hidden" value="${cellId}">
		<input id="type" name="type" type="hidden" value="${type}">
		
		<fd:searchform id="deliverablesSearchForm" onClickSearchBtn="searchDocumentForCondition()" onClickResetBtn="tagSearchResetForTaskDeliver()" isMoreShow="false">
			<fd:inputText title="{com.glaway.ids.common.lable.code}" id="no" queryMode="like" />
			<fd:inputText title="{com.glaway.ids.common.lable.name}" id="searchName" queryMode="like" />
		</fd:searchform>
		
		<%-- <fd:treegrid id="deliverablesList" idField="id" treeField="planName"
			singleSelect="false" rownumbers="true" checkOnSelect="false"
			selectOnCheck="false"
			url="deliverablesInfoController.do?datagridlistForTask&method=search&stopFlag=START&ids=${ids}&templateId=${templateId}&cellId=${cellId}"
			 fit="true" fitColumns="true" toolbar="#deliverablesSearchForm">
			<fd:columns>
				<fd:column title="{com.glaway.ids.common.lable.code}" field="no" width="50" />
				<fd:column title="{com.glaway.ids.common.lable.name}" field="name" width="200" />
				<fd:column title="{com.glaway.ids.common.lable.remark}" field="configComment" width="190" />
				<fd:column title="isOutputDeliverRequired" hidden="true"
					field="isOutputDeliverRequired" width="190" />
			</fd:columns>
		</fd:treegrid> --%>
	<fd:lazydatagrid id="deliverablesList" idField="id" url="deliverablesInfoController.do?datagridlistForTask&method=search&stopFlag=START&ids=${ids}&templateId=${templateId}&cellId=${cellId}"
	  toolbar="#deliverablesSearchForm" fit="true" fitColumns="true">		
		<fd:columns>
			<fd:column checkbox="true" field="ck"></fd:column>
			<fd:column  field="no" width="80">编号</fd:column>
			<fd:column  field="name" width="80" >名称</fd:column>
			<fd:column  field="configComment" width="80">备注</fd:column>
		<%-- <fd:column title="isOutputDeliverRequired" hidden="true"
					field="isOutputDeliverRequired" width="190" /> --%>
		</fd:columns>
	</fd:lazydatagrid>
	</div>
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
		top.tip(msg);
		return;
	}
	var ids = [];
	for (var i = 0; i < rows.length; ++i) {
		if(rows[i].isNameStandardDefault=='true'){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.deliveryDelLimit"/>');
			return;
		}
		if(rows[i].isOutputDeliverRequired=='true'){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.noDelNecessaryDelivery"/>');
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
				top.tip(d.msg);
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
		top.tip('<spring:message code="com.glaway.ids.pm.project.task.deliveryEditLimit"/>');
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
			top.tip(data.msg);
			if(data.success){
				$('#objTargetEditList').datagrid('reload');
			}
		});
	}else{
		$.post("taskFlowTemplateController.do?isNecessarySelect",{itemId:id,isNecessaryValue:"false"},function(data){
			var data = $.parseJSON(data);
			top.tip(data.msg);
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

function searchDocumentForCondition() {
  var name = $("#searchName").textbox("getValue");
  var no = $("#no").textbox("getValue");
  var  datas;
		 datas= {
				name : name,
				no : no,
				ids:'${ids}'
			};
		 $.ajax({
				type : 'POST',
				 url:'deliverablesInfoController.do?datagridlistForTask&method=search&stopFlag=START',
				async : false,
				data : datas,
				success : function(data) {
					$("#deliverablesList").datagrid("loadData",data);
				} 
		});
		/*  $("#deliverablesList").treegrid({
			 url:'deliverablesInfoController.do?datagridlistForTask&method=search&stopFlag=START',
			 queryParams :datas
		 }); */
}
function tagSearchResetForTaskDeliver(){
	$("#no").textbox("clear");
	$("#searchName").textbox("clear");
}


</script>
</html>
