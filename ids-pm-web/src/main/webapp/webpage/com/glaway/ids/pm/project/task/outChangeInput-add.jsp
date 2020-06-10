<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="plug-in/jquery-easyui/themes/icon.css">

</head>
<body>
<div class="easyui-panel" fit="true">
	<input id="cellId" name="cellId" type="hidden" value="${cellId}">
	<input id="parentPlanId" name="parentPlanId" type="hidden" value="${parentPlanId}">
	
	<div id="documentListtbChange">
		<fd:searchform id="deliverablesSearchsForm" onClickSearchBtn="searchDocumentChange()" onClickResetBtn="tagSearchResetChange()">
		<fd:inputText title="{com.glaway.ids.common.lable.code}"  id="no" name="no" queryMode="like"/>
		<fd:inputText title="{com.glaway.ids.common.lable.name}"  id="name" name="name" queryMode="like"/>
		</fd:searchform>
	</div>

	<fd:datagrid checkbox="true" toolbar="#documentListtbChange" checked="true"
		idField="id" checkOnSelect="false" id="deliverablesChangeList" fit="true"
		fitColumns="true"
		url="planController.do?flag=start&ids=${outExistInputIds}&namestandardDeliverables">
		<fd:dgCol field="no" title="{com.glaway.ids.common.lable.code}" />
		<fd:dgCol field="name" title="{com.glaway.ids.common.lable.name}" />
		<fd:dgCol field="stopFlag"
			title="{com.glaway.ids.common.lable.status}" />
	</fd:datagrid>
			
</div>

<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
<script type="text/javascript">
	function saveSelectedRowsChange(){
		debugger;
	    var names = [];
	    var rows = $("#deliverablesChangeList").datagrid('getChecked');
	    if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				names.push(rows[i].name);
			}
			 datas= {
					cellId : $("#cellId").val(),
					parentPlanId : $("#parentPlanId").val(),
					"type" : 'INPUT',
					names : names.join(',')
				};
			$.ajax({
				url : 'taskFlowResolveController.do?doChangeAdd',
				async : false,
				cache : false,
				type : 'post',
				data : datas,
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var win = $.fn.lhgdialog("getSelectParentWin") ;						
	 					win.reloadTable();
						try {
	 						debugger
	 						 setTimeout(function() {
		 						$.fn.lhgdialog("closeSelect");
	 					     }, 500)
	 					} catch (e) {
	 						// TODO: handle exception
	 					}
					}
					else{
						top.tip(d.msg);
					} 
				}
			});
		} else {
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.selectAdd"/>');
		}
	}
	
	
	function searchDocumentChange() {
		var no = $("#no").textbox("getValue");
		var name = $("#name").textbox("getValue");
		$('#deliverablesChangeList')
				.datagrid(
						{
							url : 'planController.do?searchConfigsFromParams&name='
									+ encodeURI(encodeURI(name))
									+ '&no='
									+ encodeURI(encodeURI(no))
									+ '&ids='
									+ '${outExistInputIds}',
							pageNumber : 1
						});
		
		$('#deliverablesChangeList').datagrid('unselectAll');
		$('#deliverablesChangeList').datagrid('clearSelections');
		$('#deliverablesChangeList').datagrid('clearChecked');
	}
	

	//重置
	function tagSearchResetChange() {
		$('#name').textbox("setValue","");
		$('#no').textbox("setValue","");
	}
	
	function closePlan() {
		$.fn.lhgdialog("closeSelect");
	}
</script>
</body>