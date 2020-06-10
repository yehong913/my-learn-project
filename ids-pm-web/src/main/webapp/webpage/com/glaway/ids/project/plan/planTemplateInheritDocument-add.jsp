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
<div class="easyui-layout" fit="true">
	<input id="useObjectId" name="useObjectId" type="hidden" value="${useObjectId}">
	<input id="useObjectType" name="useObjectType" type="hidden" value="${useObjectType}">

	<fd:datagrid checkbox="true"  fitColumns="true"  checked="true" checkOnSelect="true" idField="id"  id="deliverablesList2"  pagination="false" height="250px"
		url="deliverablesInfoController.do?datagridInheritlistForTemplate&planTemplateId=${planTemplateId}&projectTemplateId=${projectTemplateId }" fit="false">
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.deliverableName}"  field="name"  sortable="false"/>
	</fd:datagrid>
	<%-- <div style="float: right; padding: 5px;">
		<fd:linkbutton onclick="add()" value="{com.glaway.ids.common.btn.confirm}" classStyle="button_nor" />
		<fd:linkbutton onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" />	
	</div> --%>
	</div>

<script src="webpage/com/glaway/ids/pm/project/plan/planList.js"></script>
<script type="text/javascript">
function addDocument() {
    gridname= 'deliverablesList2';
    var names = [];
    var rows = $("#deliverablesList2").datagrid('getSelections');
    if (rows.length > 0) {
		for ( var i = 0; i < rows.length; i++) {
			names.push(rows[i].name);
		}
		var datas= {
				useObjectId : $("#useObjectId").val(),
				useObjectType : $("#useObjectType").val(),
				names : names.join(',')
			};
		$.ajax({
			url : 'deliverablesInfoController.do?doAdd',
			async : false,
			cache : false,
			type : 'post',
			data : datas,
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					//reloadTable();
					//$("#deliverablesList").datagrid('unselectAll');
					names='';
					$.fn.lhgdialog("closeSelect");
				}
			}
		});
				
	} else {
		tip('<spring:message code="com.glaway.ids.pm.project.plan.pleaseChosePlanToAssign"/>');
	}
}
function add(){
	    var names = [];
	    var  datas;
	    var rows = $("#deliverablesList2").datagrid('getSelections');
	    if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				names.push(rows[i].name);
			}
			 datas= {
					useObjectId : $("#useObjectId").val(),
					useObjectType : $("#useObjectType").val(),
					names : names.join(',')
				};
			$.ajax({
				url : 'deliverablesInfoController.do?doAddInheritlistForTemplate',
				async : false,
				cache : false,
				type : 'post',
				data : datas,
				cache : false,
				success : function(data) {
					/* var win = $.fn.lhgdialog("getSelectParentWin");
					var planListSearch = win.initDocument();
					$.fn.lhgdialog("closeSelect"); */
				}
			});
		} else {
			top.tip('<spring:message code="com.glaway.ids.pm.project.plan.selectAdd"/>');
		}
	    return datas;
}
	
function refrshDocument() {
	$.ajax({
		url : 'deliverablesInfoController.do?list',
		async : false,
		cache : false,
		type : 'post',
		data : {
			useObjectId : $("#useObjectId").val(),
			useObjectType : $("#useObjectType").val()
			},
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			
/* 			win = $.fn.lhgdialog("getSelectParentWin");
			win.reloadTable()
			$('#deliverablesInfoList').datagrid('reload');
			$("#deliverablesInfoList").datagrid('unselectAll'); */
		}
	});
	
}
	
function closePlan() {
	$.fn.lhgdialog("closeSelect");
}
</script>
</body>