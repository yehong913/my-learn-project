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
<body class="easyui-layout" fit="true">
    <script type="text/javascript">
        //获取交付物列表选择行数据
		function saveSelectedRowsChange() {
        	debugger;
			var names = [];
			var deliverIds = [];
			var isOutputDeliverRequired = [];
			var datas;
			var originObjectIds = [];
			var originObjectNames = [];
			var rows = $("#deliverablesListChange").datagrid('getSelections');
		    
			if (rows.length > 0) {
				for (var i = 0; i < rows.length; i++) {
					names.push(rows[i].name);	
					deliverIds.push(rows[i].deliverId);			
					originObjectIds.push(rows[i].originObjectId);
					originObjectNames.push(rows[i].cellName);
				}
			    
				datas = {
					useObjectId : '${useObjectId}',
					useObjectType : 'PLAN',
					'type' : 'INNERTASK',
					deliverNames : names.join(','),
					deliverIds : deliverIds.join(','),
					originObjectIds : originObjectIds.join(','),
					originObjectNames : originObjectNames.join(','),
					parentPlanId : '${parentPlanId}'
				};

				$.ajax({
							url : 'taskFlowResolveController.do?doAddInnerChangeInputs',
							async : false,
							cache : false,
							type : 'post',
							data : datas,
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								var win = $.fn.lhgdialog("getSelectParentWin");
								win.reloadTable();
								$.fn.lhgdialog("closeSelect");
								/* top.tip(d.msg); */
								
							}
						});
			} else {
				var win = $.fn.lhgdialog("getSelectParentWin");
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.selectAdd"/>');
			}
			return datas;
		}
	</script>

  <div region="center" style="padding: 1px;" >
        <input id="cellId" name="cellId" type="hidden" value="${cellId}">
        <input id="type" name="type" type="hidden" value="${type}">
        
        <fd:searchform id="deliverablesSearchForm" onClickSearchBtn="searchDocumentForConditionResolve()" onClickResetBtn="tagSearchResetForTaskDeliverResolve()" isMoreShow="false">
            <fd:inputText title="{com.glaway.ids.common.lable.code}" id="no" queryMode="like" />
            <fd:inputText title="{com.glaway.ids.config.deliveryStandard.deliveryName}" id="searchName" queryMode="like" />
        </fd:searchform>            
        <fd:datagrid toolbar="#deliverablesSearchForm" idField="id" id="deliverablesListChange" fitColumns="true"
            url="taskFlowResolveController.do?datagridlistForInnerChangeInputs&useObjectId=${useObjectId}&parentPlanId=${parentPlanId}&cellId=${cellId}"
            checkbox="true" pagination="false" fit="true">
        <fd:dgCol title="{com.glaway.ids.common.lable.code}" field="no" width="70"  sortable="false" formatterFunName="preStyleResolve"/>
        <fd:dgCol title="{com.glaway.ids.config.deliveryStandard.deliveryName}" field="name" width="200"  sortable="false" formatterFunName="preStyleResolve"/>
        <fd:dgCol title="{com.glaway.ids.config.namestandard.namestandardName}" field="cellName" width="200"  sortable="false" formatterFunName="preStyleResolve"/>
        <fd:dgCol title="{com.glaway.ids.common.lable.remark}" field="remark" width="190"  sortable="false" formatterFunName="preStyleResolve"/>
        
    </fd:datagrid>
    </div>
</body>
<!-- 国际化修改 -->
<script type="text/javascript">
    //前置活动输出样式
	function preStyleResolve(val, row, index) {
		if (val != undefined && val != null && val != '') {
			var isPreOutput = row.isPreOutput;
			if ( isPreOutput == 'true') {
				return "<div style=\"color:red;\">" + val + "</div>";
			}
			else {
				return val;
			}
		}
	}

	function searchDocumentForConditionResolve() {
		var name = $("#searchName").textbox("getValue");
		var no = $("#no").textbox("getValue");
		var datas;
		datas = {
			name : name,
			no : no,
			ids : '${deliverIds}'
		};
	    $.ajax({
            type : 'POST',
            url:'taskFlowResolveController.do?datagridlistForInnerInputs&useObjectId=${useObjectId}&parentPlanId=${parentPlanId}&cellId=${cellId}',
            async : false,
            data : datas,
            success : function(data) {
                $("#deliverablesListChange").datagrid("loadData",data);
            } 
        });
		
		$('#deliverablesListChange').datagrid('unselectAll');
		$('#deliverablesListChange').datagrid('clearSelections');
		$('#deliverablesListChange').datagrid('clearChecked');
	}
	
	function tagSearchResetForTaskDeliverResolve() {
		$("#no").textbox("clear");
		$("#searchName").textbox("clear");
	}
</script>
</html>
