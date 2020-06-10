<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script >
$(function(){
	$("#objResEditList").datagrid({
		onLoadSuccess:function(){
			if("${isEnableFlag}"=='1'){
				$("input[type='checkbox']").attr("disabled","disabled");
				$("#openAdd").attr("disabled","disabled");
				$("#removeSels2").attr("disabled","disabled");
			}
		},
		onSelect: function(index,row){
			$('#objResEditList').datagrid("unselectRow",index);
		}
	});
	
	
	
})


//接口调用删除选择的行
function removeSels2(dgId) {
	var rows = $('#' + dgId).datagrid('getChecked');
	if (rows.length == 0) {
		var msg = '<spring:message code="com.glaway.ids.pm.project.task.selectBatchDel"/>';
		top.tip(msg);
		return;
	}
	var ids = [];
	for (var i = 0; i < rows.length; ++i) {
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



	function openAddDialogInputSure(iframe){
		iframe.saveSelectedRows('${tcbi.id}');
		return false;
	}


</script>
<div class="easyui-layout" id="cellInput">
	<fd:toolbar id="inTB">
		<fd:toolbarGroup align="left">
		<c:if test="${isEnableFlag!='1'}">
			<fd:linkbutton value="{com.glaway.ids.common.btn.create}" id="openAdd"
				onclick="openAdd('objResEditList', '${pageContext.request.contextPath }/taskFlowTemplateController.do?goAdd&type=INPUT&baseInfoId=${tcbi.id}&templateId=${templateId}&cellId=${cellId}','${tcbi.id}', 'openAddDialogInput')"
				iconCls="basis ui-icon-plus" />
			<fd:linkbutton value="{com.glaway.ids.common.btn.remove}" onclick="removeSels2('objResEditList')" id = "removeSels2"
				iconCls="basis ui-icon-minus" />
		</c:if>		
		</fd:toolbarGroup>
	</fd:toolbar>
	<!-- editable datagrid -->
	<fd:datagrid toolbar="#inTB" idField="id" id="objResEditList" fitColumns="true"
		url="taskFlowTemplateController.do?datagrid&type=INPUT&id=${tcbi.id}&cellId=${cellId}&templateId=${templateId}"
		checkbox="true" pagination="false" fit="false">
		<fd:dgCol title="{com.glaway.ids.pm.project.task.inputName}" field="deliverName" width="300" align="center"
			editor="'text'" />
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.orgin}"  field="inputOrOutputName" align="center" />
	</fd:datagrid>
</div>
<fd:dialog id="openAddDialogInput" width="800px" height="530px" modal="true" zIndex="4300"
		  title="{com.glaway.ids.pm.project.task.addInput}">
		  <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogInputSure"></fd:dialogbutton>
		  <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

