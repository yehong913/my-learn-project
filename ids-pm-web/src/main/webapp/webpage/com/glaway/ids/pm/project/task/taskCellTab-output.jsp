<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
/*  $(document).ready(function (){
	var curCellName = '${tcbi.cellName}';
	if(''!=curCellName && null!=curCellName && curCellName != undefined){
	 	$.ajax({
			url : 'taskFlowTemplateController.do?nameStandardDeliveryQuery',
			type : 'post',
			data : {
				curCellName : curCellName
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);			
				if(d.success){
					$("#nameStandardDeliveryIds").val(d.obj);
				}
			}
		}); 
	}
});  */
	

	function isNecessary(val, row, index) {
		/* var nameStandardDeliveryIds = $("#nameStandardDeliveryIds").val(); */
		var isOutputDeliverRequired = row.isOutputDeliverRequired;	
		var  isNameStandardDefault = row.isNameStandardDefault;	
		if (('${flagUpdate}' == "false" && isNameStandardDefault=="true") || "1" == "${isEnableFlag}" ){
			if("true"==isOutputDeliverRequired){
				return "<div>"
				+ "<input readOnly='readOnly' onclick='return false;' checked='checked' type='checkbox'>"
				+ "</div>";
			}
			else{
				return "<div>"
				+ "<input readOnly='readOnly' onclick='return false;' type='checkbox'>"
				+ "</div>";	
			}
		}
		else{
			if (val == "true") {
				return '<div>'
						+ '<input type="checkbox" onclick="isNecessarySelect(this,\''
						+ row.id + '\')" checked="checked"/>' + '</div>';
			} else{
				return "<div>"
						+ "<input readOnly='readOnly' onclick='isNecessarySelect(this,\""
						+ row.id + "\")'  type='checkbox'>" + "</div>";
			}
		}
	}
	//删除选择的行
	function removeSels(dgId) {
		var rows = $('#' + dgId).datagrid('getChecked');
		if (rows.length == 0) {
			var msg = '<spring:message code="com.glaway.ids.pm.project.task.selectBatchDel"/>';
			tip(msg);
			return;
		}
		var ids = [];
		for (var i = 0; i < rows.length; ++i) {
			var nameStandardDeliveryIds = $("#nameStandardDeliveryIds").val();
			var curDeliverId = rows[i].deliverId;
			var curFlog = false;
			if ('' != nameStandardDeliveryIds
					&& null != nameStandardDeliveryIds
					&& nameStandardDeliveryIds != undefined) {
				if (nameStandardDeliveryIds.replace(/\"/g, "\'").indexOf(
						curDeliverId) != -1) {
					curFlog = true;
				}
			}

			if ('${flagUpdate}' == "false" && curFlog == true) {
				tip('<spring:message code="com.glaway.ids.pm.project.task.deliveryDelLimit"/>');
				return;
			}
			/* 		if(rows[i].isOutputDeliverRequired=='true'){
			 tip('<spring:message code="com.glaway.ids.pm.project.task.noDelNecessaryDelivery"/>');
			 return;
			 } */
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
					$('#' + dgId).datagrid('reload');
					$('#' + dgId).datagrid('clearSelections');
					//var win = $.fn.lhgdialog("getSelectParentWin");
					tip(d.msg);
					//$("#objTargetEditList").datagrid('unselectAll');
				}
			}
		});
	}
</script>
<style>
<!--
.ui_state_highlight{background:#f00}
-->
</style>
<div class="easyui-layout" id="cellOutput">
<input id="nameStandardDeliveryIds" name="nameStandardDeliveryIds" type="hidden" value=${nameStandardDeliveryIds}>
	<fd:toolbar id="outTB">
		<fd:toolbarGroup align="left">
			<c:if test="${isEnableFlag!='1'}">			
			<fd:linkbutton value="{com.glaway.ids.common.btn.create}"
				onclick="openAdd('objTargetEditList', '${pageContext.request.contextPath }/taskFlowTemplateController.do?goAdd&type=OUTPUT&baseInfoId=${tcbi.id}','${tcbi.id}', 'openAddDialog')"
				iconCls="basis ui-icon-plus"  id ="openAdd1"/>
			<fd:linkbutton value="{com.glaway.ids.common.btn.remove}" onclick="removeSels('objTargetEditList')"
				iconCls="basis ui-icon-minus" id = "removeSels1" />
			</c:if>
		</fd:toolbarGroup>
	</fd:toolbar>
	<!-- editable datagrid -->
	<fd:datagrid toolbar="#outTB" idField="id" id="objTargetEditList"
		checkbox="true" fitColumns="true"
		url="taskFlowTemplateController.do?datagrid&type=OUTPUT&id=${tcbi.id}"
		pagination="false" fit="false">
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.deliverableName}" field="deliverName" width="300" align="center"
			editor="'text'" />
		<fd:dgCol title="{com.glaway.ids.pm.project.task.required}" field="isOutputDeliverRequired" width="30"
			align="center" editor="'checkbox'"  formatterFunName="isNecessary" />
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.target}" field="inputOrOutputName" width="300" align="center" />
	</fd:datagrid>
</div>
<fd:dialog id="openAddDialog" width="800px" height="530px" modal="true" zIndex="4300"
		  title="{com.glaway.ids.pm.project.task.addOutput}">
		  <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogSure"></fd:dialogbutton>
		  <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<script type="text/javascript">
	function openAddDialogSure(iframe){
		iframe.saveSelectedRows('${tcbi.id}');
		return false;
	}
</script>

