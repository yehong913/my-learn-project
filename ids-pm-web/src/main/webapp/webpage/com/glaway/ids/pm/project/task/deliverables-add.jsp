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
	<input id="type" name="type" type="hidden" value="${type}">
	
	<div id="documentListtb">
		<fd:searchform id="deliverablesSearchsForm" onClickSearchBtn="searchDocument()" onClickResetBtn="tagSearchReset()">
		<fd:inputText title="{com.glaway.ids.common.lable.code}"  id="no" queryMode="like"/>
		<fd:inputText title="{com.glaway.ids.common.lable.name}"  id="searchName" queryMode="like"/>
		</fd:searchform>
	</div>

<%--  	<fd:lazydatagrid id="deliverablesList" idField="id" url="taskFlowResolveController.do?deliverablesList&type=${type}&cellId=${cellId}&parentPlanId=${parentPlanId}"
	  toolbar="#documentListtb" fit="true" fitColumns="true">		
		<fd:columns>
			<fd:column checkbox="true" field="ck"></fd:column>
			<fd:column  field="no" width="80">编号</fd:column>
			<fd:column  field="name" width="80" >名称</fd:column>
			<fd:column  field="configComment" width="80">备注</fd:column>
			<fd:column  field="stopFlag" width="80">状态</fd:column>
		</fd:columns>
	</fd:lazydatagrid> --%>
	
	
 	<fd:datagrid checkbox="true" fitColumns="true"  checked="true" checkOnSelect="true" idField="id" toolbar="#documentListtb" id="deliverablesList"  pagination="true"
			url="taskFlowResolveController.do?deliverablesList&type=${type}&cellId=${cellId}&parentPlanId=${parentPlanId}" fit="true">
			<fd:dgCol title="编号" field="no" sortable="false"  width="80" />
			<fd:dgCol title="名称"  field="name" sortable="false" width="80"/>
			<fd:dgCol title="备注"  field="configComment" sortable="false" width="80"/>
			<fd:dgCol title="状态"  field="stopFlag" sortable="false" width="80"/>
		</fd:datagrid> 
		
</div>

<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
<script type="text/javascript">
	function submitSelectData(){
		debugger;
	    var names = [];
		var datas;
	    var rows = $("#deliverablesList").lazydatagrid('getChecked');
	    if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				names.push(rows[i].name);
			}
			datas = {
					'cellId' : $("#cellId").val(),
					'parentPlanId' : $("#parentPlanId").val(),
					'type' : $("#type").val(),
					'names' : names.join(',')
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
						debugger
						var win = $.fn.lhgdialog("getSelectParentWin") ;
						win.initOutputsFlowTask();
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
		debugger;
	    var name = $("#searchName").textbox("getValue");
	    var no = $("#no").textbox("getValue");
	    var datas = {
				'name' : name,
				'no' : no,
				'parentPlanId' : '${parentPlanId}',
				'cellId' : '${cellId}'
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
	}


	//重置
	function tagSearchReset() {
		$('#searchName').textbox("setValue","");
		$('#no').textbox("setValue","");
	}
	
	function closePlan() {
		$.fn.lhgdialog("closeSelect");
	}
</script>
</body>