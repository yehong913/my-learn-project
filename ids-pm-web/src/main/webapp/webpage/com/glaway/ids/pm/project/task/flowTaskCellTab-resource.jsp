<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-panel" id="cellInput" fit="true">
	<input type="hidden" name="cellId" id="cellId" value="${cellId}" />
	<input type="hidden" name="parentPlanId" id="parentPlanId" value="${parentPlanId}" />
	<input type="hidden" id ='isEnableFlag' name='isEnableFlag' value="${isEnableFlag}"/>
	<script >
		$(function($) {
			if("1" == $("#isEnableFlag").val()){
				$("#openAddResource").hide();
			}
		});
	
		// 资源名称链接事件
		function resourceNameLink(val, row, value) {
			return '<a href="#" onclick="viewResourceCharts(\'' + row.id + '\')" style="color:blue">' + val + '</a>';
		}
	
		// 资源名称链接事件
		function viewResourceCharts(id){
		    var rows = $("#resourceList").datagrid('getRows');
		    var index = $("#resourceList").datagrid('getRowIndex', id);
		 	var row = rows[index];
			if(row.useRate != null && row.useRate != '' && row.startTime != null && row.startTime != '' && row.endTime != null && row.endTime != ''){
 				var url = "resourceLinkInfoController.do?goToToBeUsedRateReport&resourceId="+ row.resourceInfo.id + "&resourceLinkId=" 
				          + row.id + "&resourceUseRate=" + row.useRate + "&startTime=" + row.startTime + "&endTime=" + row.endTime + "&useObjectId=${planId}";
				parent.resourceChartsFtctShow(url);
			}
			else{
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.resource.emptyUseInfo"/>');
			}
		}
	
		// 资源使用区间
		function viewStartEndTime(val, row, value) {
			var start = row.startTime;
			var end = row.endTime;
			if((start != undefined && start !=null && start != '') 
				&& (end != undefined && end !=null && end != '')){
				return start + "~" + end;
			}
			return "";
		}
		
		// 资源使用率
		function progressRate(val, row, value){
			if(val == undefined || val == null || val == ''){
				val = 0;
			}
			return val + '%' ;
		}
	
		// 新增资源
		function addResource(){
			if("1" == $("#isEnableFlag").val()){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
				return;
			}
			gridname = 'deliverablesInfoList';
			var dialogUrl = 'taskFlowResolveController.do?goAddResource&cellId='+$("#cellId").val()+'&parentPlanId='+$("#parentPlanId").val();
			createDialog('addResourceDialog',dialogUrl);
		}
		
		function addResourceDialog(iframe){
			 iframe.submitSelectData();
		}
		
		
		function initResourceFlowTask() {
			$("#resourceList").datagrid("reload");
		}
		
		//自定义行显示
		function funNameResource(val, row, index) {
			var returnStr = '<a class="basis ui-icon-pencil" style="display:inline-block;cursor:pointer;" onClick="modifyResource(\''
	            + index + '\')" title="<spring:message code='com.glaway.ids.common.btn.modify'/>"></a>'
	            + '<a class="basis ui-icon-minus" style="display:inline-block;cursor:pointer;" onClick="deleteResource(\''
	            + index + '\')" title="<spring:message code='com.glaway.ids.common.btn.remove'/>"></a>';
			return returnStr;
		}
		
		
		
		// 维护资源
		function modifyResource(index) {
			if("1" == $("#isEnableFlag").val()){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
				return;
			}
			var rows = $("#resourceList").datagrid('getRows');				
			var dialogUrl = 'taskFlowResolveController.do?goModify'+ '&id=' + rows[index].id;
				createDialog('modifyResourceDialog',dialogUrl);
		}
		
		function modifyResourceDialog(iframe){
			iframe.submitEditResource();
		}
		
		// 删除资源
		function deleteResource(index) {
			if("1" == $("#isEnableFlag").val()){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
				return;
			}
			var gridname = 'resourceList';
		    var rows = $("#resourceList").datagrid('getRows');
			 var ids = rows[index].id;
			    	$.ajax({
						url : 'taskFlowResolveController.do?doDelResource',
						type : 'post',
						data : {
							'ids' : ids
						},
						cache : false,
						success : function(data) {
							$("#resourceList").datagrid("reload");
						}
					});
			}
		
		function hideOperateAreaColumn(){
			if("1" == $("#isEnableFlag").val()){
				$('#resourceList').datagrid('hideColumn', 'operateArea');
			}
		}
		
		
	</script>
	<fd:toolbar id="resourceTB">
		<fd:toolbarGroup align="left">
			<fd:linkbutton value="{com.glaway.ids.common.btn.create}" id="openAddResource"
				onclick="addResource()"
				iconCls="basis ui-icon-plus" />
		</fd:toolbarGroup>
	</fd:toolbar>
	<!-- editable datagrid -->
	<fd:datagrid toolbar="#resourceTB" idField="id" id="resourceList" onLoadSuccess="hideOperateAreaColumn"
		url="taskFlowResolveController.do?resourceList&cellId=${cellId}&parentPlanId=${parentPlanId}" fitColumns="true"
		checkbox="false" pagination="false" fit="true" >
			<fd:dgCol title="{com.glaway.ids.common.lable.operation}" field="operateArea" formatterFunName="funNameResource"  width="70"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceName}" field="resourceName" width="150" formatterFunName="resourceNameLink"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceType}" field="resourceType" width="200" hidden="true"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.area}" field="resourceInfo" width="200" formatterFunName="viewStartEndTime"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.useRate}" field="useRate" width="70" formatterFunName="progressRate"></fd:dgCol>
	</fd:datagrid>
</div>
	<fd:dialog id="addResourceDialog" width="600px" height="400px" modal="true" title="{com.glaway.ids.pm.project.plan.resource.addResource}" zIndex="4300">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addResourceDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="modifyResourceDialog" width="730px" height="250px" modal="true" title="{com.glaway.ids.pm.project.plan.resource.editResource}" zIndex="4300">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="modifyResourceDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	