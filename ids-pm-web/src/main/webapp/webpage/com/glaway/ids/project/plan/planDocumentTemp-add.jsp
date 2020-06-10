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
<div region="center" style="padding: 1px;">
	<input id="useObjectId" name="useObjectId" type="hidden" value="${deliverablesInfo_.useObjectId}">
	<input id="useObjectType" name="useObjectType" type="hidden" value="${deliverablesInfo_.useObjectType}">
	<div id="documentListtb">
		<fd:searchform id="deliverablesSearchForm" onClickSearchBtn="searchDocument()" onClickResetBtn="tagSearchReset()" isMoreShow="false">
		<fd:inputText title="{com.glaway.ids.common.lable.code}"  id="no" queryMode="like"/>
		<fd:inputText title="{com.glaway.ids.common.lable.name}"  id="searchName" queryMode="like"/>
		</fd:searchform>
		<%-- <fd:toolbar id="toolbar">
			<fd:toolbarGroup align="right">
					<fd:linkbutton onclick="searchDocument();" value="查询" classStyle="button_nor"/>
					<fd:linkbutton onclick="tagSearchReset()" value="重置" classStyle="button_nor"/>
			</fd:toolbarGroup>
		</fd:toolbar> --%>
	</div>

	 <fd:datagrid checkbox="true"  fitColumns="true"  checked="true" checkOnSelect="true" idField="id" toolbar="#documentListtb" id="deliverablesList"  pagination="true"
				  url="deliverablesInfoController.do?datagridlistForPlanChange&method=search&useObjectId=${deliverablesInfo_.useObjectId}&useObjectType=${deliverablesInfo_.useObjectType}" fit="true">
		<fd:dgCol title="{com.glaway.ids.common.lable.code}"  field="no" />
		<fd:dgCol title="{com.glaway.ids.common.lable.name}"  field="name" />
		<fd:dgCol title="{com.glaway.ids.common.lable.remark}"  field="configComment" />
		<fd:dgCol title="{com.glaway.ids.common.lable.status}"  field="stopFlag" />
	</fd:datagrid>
	
<%--	<fd:lazydatagrid id="deliverablesList" idField="id" url="planChangeController.do?documentdatagridlist"
	  style="height:500px;" toolbar="#documentListtb" fit="true" fitColumns="true" >		
		<fd:columns>
			<fd:column checkbox="true" field="ck"></fd:column>
			<fd:column  field="no" width="80">编号</fd:column>
			<fd:column  field="name" width="80" >名称</fd:column>
			<fd:column  field="remark" width="80">备注</fd:column>
			<fd:column  field="stopFlag" width="80">状态</fd:column>
		</fd:columns>
	</fd:lazydatagrid>--%>
	</div>
</div>
<script src="webpage/com/glaway/ids/project/plan/planList.js"></script>
<script type="text/javascript">
function getLoadData(){
	    var names = [];
	    var ids = [];
	    var  datas;
	    var rows = $("#deliverablesList").lazydatagrid('getChecked');
	    if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				names.push(rows[i].name);
				ids.push(rows[i].id);
			}
			 datas= {
					useObjectId : $("#useObjectId").val(),
					useObjectType : $("#useObjectType").val(),
					names : names.join(','),
					ids : ids.join(',')
				};
			$.ajax({
				url : 'planChangeController.do?doAddDocument',
				async : false,
				cache : false,
				type : 'post',
				data : datas,
				cache : false,
				success : function(data) {
				}
			});
		} else {
			tip("请选择需要添加的数据");
		}
	    return datas;
}

function searchDocument() {
    var name = $("#searchName").textbox("getValue");
    var no = $("#no").textbox("getValue");
    var  datas;
    datas= {
                name : name,
                no : no,
				useObjectId : $("#useObjectId").val(),
				useObjectType : $("#useObjectType").val(),
                method : 'search'
        };
    $.ajax({
        type : 'POST',
        url : 'deliverablesInfoController.do?datagridlistForPlanChange',
        async : false,
        data : datas,
        success : function(data) {
            $("#deliverablesList").datagrid("loadData",data);
            $('#deliverablesList').datagrid('unselectAll');
            $('#deliverablesList').datagrid('clearSelections');
            $('#deliverablesList').datagrid('clearChecked');
        }
    });

		// $.ajax({
		// 	url : 'planChangeController.do?searchDocument',
		// 	async : false,
		// 	cache : false,
		// 	type : 'post',
		// 	data : datas,
		// 	cache : false,
		// 	success : function(data) {
		// 		var d = $.parseJSON(data);
		// 		if (d.success) {
		// 			var msg = d.msg;
		// 			$.ajax({
		// 				type : 'POST',
		// 				url : 'planChangeController.do?datagridDocumentSearchlist',
		// 				async : false,
		// 				data : datas,
		// 				success : function(data) {
		// 					$("#deliverablesList").datagrid("loadData",data);
		// 					$('#deliverablesList').datagrid('unselectAll');
		// 					$('#deliverablesList').datagrid('clearSelections');
		// 					$('#deliverablesList').datagrid('clearChecked');
		// 				}
		// 			});
		// 		}
		// 	}
		// });
}

//重置
function tagSearchReset() {
	$('#searchName').textbox("setValue","");
	$('#no').textbox("setValue","");
}

	
function refrshDocument() {
	$.ajax({
		url : 'planChangeController.do?documentList',
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
			
 			win = $.fn.lhgdialog("getSelectParentWin");
			win.reloadTable()
			$('#deliverablesInfoList').datagrid('reload');
			$("#deliverablesInfoList").datagrid('unselectAll');
		}
	});
	
}
	
function closePlan() {
	$.fn.lhgdialog("closeSelect");
}
</script>
</body>