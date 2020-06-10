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
/* 				$('#'+'viewResourceChartsCftct').lhgdialog('open','url:'+dialogUrl); */
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
			
			var dialogUrl = '${pageContext.request.contextPath}/taskFlowResolveController.do?goChangeAddResource&cellId='+$("#cellId").val()+'&parentPlanId='+$("#parentPlanId").val();
			createDialog('addResourceDialog',dialogUrl);
			//有分层,暂时无法替换
		}
		
		function addResourceDialog(iframe){
			iframe.submitSelectData();
		}
		
		function initResourceChangeFlow() {
			$("#resourceList").datagrid("reload");
		}
		
		//自定义行显示
		function funFlowtaskResource(val, row, index) {
			if("1" == $("#isEnableFlag").val()){
				$('resourceList').datagrid('hideColumn','operateArea')
			}else{
				var returnStr = '<a class="basis ui-icon-pencil" style="display:inline-block;cursor:pointer;" onClick="modifyResource(\''
		            + index + '\')" title="<spring:message code='com.glaway.ids.common.btn.modify'/>"></a>'
		            + '<a class="basis ui-icon-minus" style="display:inline-block;cursor:pointer;" onClick="deleteResource(\''
		            + index + '\')" title="<spring:message code='com.glaway.ids.common.btn.remove'/>"></a>';
				return returnStr;
			}
		}
		
		// 维护资源
		function modifyResource(index) {
			if("1" == $("#isEnableFlag").val()){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
				return;
			}
			var rows = $("#resourceList").datagrid('getRows');
			var dialogUrl='${pageContext.request.contextPath}/taskFlowResolveController.do?goModifyForChange&id=' 
							+ rows[index].id + '&useObjectId=' + rows[index].useObjectId + '&cellId='+$("#cellId").val() +'&parentPlanId=' +$("#parentPlanId").val();
			createDialog('modifyResourceDialog',dialogUrl);
			
		}
		function modifyResourceDialog(iframe){
	    	 iframe.submitEditResource();
		}
		
		// 删除资源
		function deleteResource(index) {
			debugger
			var parentPlanId =  $("#parentPlanId").val();
			if("1" == $("#isEnableFlag").val()){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
				return;
			}
			var gridname = 'resourceList';
		    var rows = $("#resourceList").datagrid('getRows');
			 var row = rows[index];
	    	$.ajax({
				url : 'taskFlowResolveController.do?doDelChangeResource',
				type : 'post',
				data : {
					'id' : row.id,
					'useObjectId' : row.useObjectId,
					'parentPlanId': parentPlanId
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						$.ajax({
							type : 'POST',
							url : 'taskFlowResolveController.do?changeResourceList',
							async : false,
							data : {
									cellId : $('#cellId').val(), 
									parentPlanId : $('#parentPlanId').val()
								},
							success : function(data) {
								$("#resourceList").datagrid("loadData",data);
							} 
						});
					}
					else{
						var msg = d.msg.split('<br/>')
						top.tip(msg[0]); // John
					}
				}
			});
		}
	
		function hideOperateColumn(){
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
	<fd:datagrid toolbar="#resourceTB" idField="id" id="resourceList"  onLoadSuccess="hideOperateColumn" fitColumns="true"
		url="taskFlowResolveController.do?changeResourceList&cellId=${cellId}&parentPlanId=${parentPlanId}"
		checkbox="false" pagination="false" fit="true" >
			<fd:dgCol title="{com.glaway.ids.common.lable.operation}" field="operateArea" formatterFunName="funFlowtaskResource"  width="70"></fd:dgCol>
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
	