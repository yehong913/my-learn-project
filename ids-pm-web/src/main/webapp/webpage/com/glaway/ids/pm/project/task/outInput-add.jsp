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
	
	<div id="documentListtb">
		<fd:searchform id="deliverablesSearchsForm" onClickSearchBtn="searchDocument()" onClickResetBtn="tagSearchReset()">
		<fd:inputText title="{com.glaway.ids.common.lable.code}"  id="no" name="no" queryMode="like"/>
		<fd:inputText title="{com.glaway.ids.common.lable.name}"  id="name" name="name" queryMode="like"/>
		</fd:searchform>
	</div>

	<fd:datagrid checkbox="true" toolbar="#documentListtb" checked="true"
		idField="id" checkOnSelect="false" id="deliverablesList" fit="true"
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
	function saveSelectedRowsResolve(){
		debugger;
	    var names = [];
	    var rows = $("#deliverablesList").datagrid('getChecked');
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
				url : 'taskFlowResolveController.do?doAdd',
				async : false,
				cache : false,
				type : 'post',
				data : datas,
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var win = $.fn.lhgdialog("getSelectParentWin") ;
						win.loadDataForInput();
						try{
							setTimeout(function(){$.fn.lhgdialog("closeSelect");},500);
						}catch(e){
							
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
	
	
	function searchDocument() {
		var no = $("#no").textbox("getValue");
		var name = $("#name").textbox("getValue");
		$('#deliverablesList')
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
		
		$('#deliverablesList').datagrid('unselectAll');
		$('#deliverablesList').datagrid('clearSelections');
		$('#deliverablesList').datagrid('clearChecked');
	}
	
	
/* 	function searchDocument() {
	    var name = $("#name").textbox("getValue");
	    var no = $("#no").textbox("getValue");
	    var datas = {
					name : name,
					no : no,
			        parentPlanId : '${parentPlanId}',
			        cellId : '${cellId}'
				};
		 $.ajax({
				type : 'POST',
				url : 'taskFlowResolveController.do?datagridSearchlist3',
				async : false,
				data : datas,
				success : function(data) {
					$("#deliverablesList").datagrid("loadData",data);
					$('#deliverablesList').datagrid('unselectAll');
					$('#deliverablesList').datagrid('clearSelections');
					$('#deliverablesList').datagrid('clearChecked');
				} 
		});
	} */


	//重置
	function tagSearchReset() {
		$('#name').textbox("setValue","");
		$('#no').textbox("setValue","");
	}
	
	function closePlan() {
		$.fn.lhgdialog("closeSelect");
	}
</script>
</body>