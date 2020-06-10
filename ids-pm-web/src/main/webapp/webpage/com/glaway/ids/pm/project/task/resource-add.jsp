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
	<input id="startTime" name="startTime" type="hidden" value="${startTime}">
	<input id="endTime" name="endTime" type="hidden" value="${endTime}">
	<div id="resourceListtbss">
		<fd:searchform id="deliverablesSearchForm" onClickSearchBtn="searchResource()" onClickResetBtn="tagSearchReset()">
		<fd:inputText title="{com.glaway.ids.common.lable.code}"  id="no" queryMode="like"/>
		<fd:inputText title="{com.glaway.ids.common.lable.name}"  id="searchName" queryMode="like"/>
		<fd:combotree title="{com.glaway.ids.pm.project.plan.resource.resourceType}" treeIdKey="id"   url="resourceLinkInfoController.do?combotree"  id="path" treePidKey="pid" editable="false" multiple="true" 
		  panelHeight="200"   treeName="name" queryMode="eq" prompt="{com.glaway.ids.common.lable.selectall}"></fd:combotree>
		</fd:searchform>
	</div>
	<fd:datagrid checkbox="true"  fitColumns="true"  checked="true" checkOnSelect="true" idField="id" 
		id="resourceInfoList"  pagination="false" toolbar="#resourceListtbss" fit="true"
		url="taskFlowResolveController.do?resourceInfoList&cellId=${cellId}&parentPlanId=${parentPlanId}">
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceNo}"  field="no"   sortable="false"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceName}" field="name" formatterFunName="resourceNameLink"  sortable="false"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceType}" field="path"  sortable="false"></fd:dgCol>
	</fd:datagrid>
</div>

	<script type="text/javascript">
		function submitSelectData(){
		    var ids = [];
		    var datas;
		    var rows = $("#resourceInfoList").datagrid('getSelections');
		    if (rows.length > 0) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				datas= {
					cellId : $("#cellId").val(),
					parentPlanId : $("#parentPlanId").val(),
					ids : ids.join(',')
					};
				$.ajax({
					url : 'taskFlowResolveController.do?doAddResource',
					async : false,
					cache : false,
					type : 'post',
					data : datas,
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var win = $.fn.lhgdialog("getSelectParentWin") ;
							win.initResourceFlowTask();
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
		
		// 资源名称链接事件
		function resourceNameLink(val, row, value) {
			return '<a href="#" onclick="viewResourceCharts(\'' + row.id + '\')" style="color:blue">' + val + '</a>';
		}
		
		// 资源名称链接事件
		function viewResourceCharts(id){
			var resourceLinkId = "";
			var resourceUseRate = "";
			
			if($("#startTime").val() != null && $("#startTime").val() != '' && $("#endTime").val() != null && $("#endTime").val() != ''){
				
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();
				var url = "resourceLinkInfoController.do?goToUsedRateReport&resourceId="+ id 
						+ "&startTime=" + startTime + "&endTime=" + endTime;
				$("#"+'viewResourceChartsDialog').lhgdialog("open","url:"+url);
			}
		}
		
		//重置
		function tagSearchReset() {
			$('#searchName').textbox("setValue","");
			$('#no').textbox("setValue","");
			$('#path').comboztree("clear");
		}
		
		
		function searchResource() {
		    var name = $("#searchName").textbox("getValue");
		    var no = $("#no").textbox("getValue");
		    var path = $("#path").combo("getText");
		    var  datas = {
						name : name,
						no : no,
				        path : path,
				        parentPlanId : '${parentPlanId}',
				        cellId : '${cellId}'
					};

			$.ajax({
				type : 'POST',
				url : 'taskFlowResolveController.do?datagridSearchlist&cellId=${cellId}&parentPlanId=${parentPlanId}',
				async : false,
				data : datas,
				success : function(data) {
					$("#resourceInfoList").datagrid("loadData",data);
					$('#resourceInfoList').datagrid('unselectAll');
					$('#resourceInfoList').datagrid('clearSelections');
					$('#resourceInfoList').datagrid('clearChecked');
					
				} 
			});
		}
	</script>
	</body>
	<fd:dialog id="viewResourceChartsDialog"  height="600px" width="800px" title="{com.glaway.ids.pm.project.plan.resource}" maxFun="true"></fd:dialog>