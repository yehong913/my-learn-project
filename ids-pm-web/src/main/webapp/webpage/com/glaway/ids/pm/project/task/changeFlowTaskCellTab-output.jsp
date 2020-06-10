<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-panel" id="cellOutput" fit="true">
	<input type="hidden" id ='isEnableFlag' name='isEnableFlag' value="${isEnableFlag}"/>
	<script >
	$(function($) {
		if("1" == $("#isEnableFlag").val()){
			$("#inheritParent").hide();
			$("#openAddOutputs").hide();
		}
	});


	function isNecessary(val, row, index) {
		if (val == "true")
			return "是";
		else
			return "否";
	}
	
	function addOutputs(){
		if("1" == $("#isEnableFlag").val()){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
			return;
		}
		gridname = 'deliverablesInfoList';
		var dialogUrl = '${pageContext.request.contextPath}/taskFlowResolveController.do?goChangeAdd&type=OUTPUT&cellId='+$("#cellId").val()+'&parentPlanId='+$("#parentPlanId").val();
		createDialog('addOutDialog',dialogUrl);
	}
	
	function addOutDialog(iframe){
   	 	iframe.submitSelectData();
	}
	
	// 删除输出
	function deleteOutput(index) {
		if("1" == $("#isEnableFlag").val()){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
			return;
		}
	    var rows = $("#outputList").datagrid('getRows');
		var id = rows[index].id;
		var useObjectId = rows[index].useObjectId;
		if(rows[index].required == 'true'){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.noDelNecessaryOutput"/>');
		}
		else{
			var parentPlanId = $('#parentPlanId').val();
			$.ajax({
				url : 'taskFlowResolveController.do?doDelChangeOutput',
				type : 'post',
				data : {
					'id' : id,
					'useObjectId' : useObjectId,
					'parentPlanId' : parentPlanId
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						$("#outputList").datagrid("reload");
						if(d.obj != '' && d.obj != null && d.obj != 'undefined'){
							var nodeId_fnType = d.obj.split(":");
							var nodeId = nodeId_fnType[0];
							if(nodeId_fnType.length > 1){
								var param = nodeId_fnType[0].split(",");
								var node = param[0];
								var deliverId = param[1];
								var fnType = nodeId_fnType[1].split(",");
								for(var i=0;i<fnType.length;i++){
									eval("outSystemDelDocSynchronization_" + fnType[i]+ "(parentPlanId,node,deliverId)");
								}
							}
						}
					}
					else{
						var msg = d.msg.split('<br/>')
						top.tip(msg[0]); // John
					}
				}
			});
		}
	}
	
	//自定义行显示
	function funFlowtaskOut(val, row, index) {
		var returnStr = '<a class="basis ui-icon-minus" style="display:inline-block;cursor:pointer;" onClick="deleteOutput(\''
            + index + '\')" title="<spring:message code='com.glaway.ids.common.btn.remove'/>"></a>';
		return returnStr;
	}
	
	function initOutputsChangeFlow() {
		$("#outputList").datagrid("reload");
	}
	
	
	// 新增计划-继承父项输出
	function inheritParent() {
		if("1" == $("#isEnableFlag").val()){
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
			return;
		}
		if($("#parentPlanId").val()!=null&&$("#parentPlanId").val()!=undefined){
			var dialogUrl = '${pageContext.request.contextPath}/taskFlowResolveController.do?goAddInheritForChange&cellId='+$("#cellId").val()+'&parentPlanId='+$("#parentPlanId").val();
			createDialog('inheritParentDialog',dialogUrl);
			
		}else{
			top.tip('<spring:message code="com.glaway.ids.pm.project.task.deliveryNoParent"/>');
		}

	}
	
	function inheritParentDialog(iframe){
   		iframe.submitSelectData();
	}
	
	function addLink(val, row, value){
		if(val!=null&&val!=''){
			if((row.havePower == true || row.havePower == 'true')&& '${userLevel}' >= row.securityLevel){
				/* return '<a  href="#" onclick="showDocDetail(\'' + row.docId + '\',this)" id="myDoc"  style="color:blue">'+val+'</a>'; */
				return "<a href='#' onclick='showDocDetail(\""
				+ row.docId
				+ "\""
				+ ','
				+ "\""
				+ row.download
				+ "\""
				+ ','
				+ "\""
				+ row.detail
				+ "\")'  id='myDoc'  style='color:blue'>" + val +"</a>";
			}else{
				return val;
			}
		}else return ;
		
	}
	
	
	function showDocDetail(id,download,detail){
		var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id+ "&download=" + download+ "&detail=" + detail;
		createdetailwindow2("<spring:message code='com.glaway.ids.pm.project.plan.basicLine.showDocDetail'/>", url, "1000", "550");
	}
	
	
	function createdetailwindow2(title, url, width, height) {
		width = width ? width : 700;
		height = height ? height : 400;
		if (width == "100%" || height == "100%") {
			width = document.body.offsetWidth;
			height = document.body.offsetHeight - 100;
		}
		if (typeof (windowapi) == 'undefined') {
			createDialog('showDocDetailOutputDialog',url)
		} else {
			createDialog('showDocDetailOutputDialog',url)
		} 
	}
	
	function hideOperateAreaColumn(){
		if("1" == $("#isEnableFlag").val()){
			$('#outputList').datagrid('hideColumn', 'operateArea');
		}
	}
</script>
	<fd:toolbar id="outTB">
		<fd:toolbarGroup align="left">
			<fd:linkbutton value="{com.glaway.ids.pm.project.plan.inheritParent}" onclick="inheritParent()"
				iconCls="basis ui-icon-copy"  id ="inheritParent"/>
			<fd:linkbutton value="{com.glaway.ids.common.btn.create}" onclick="addOutputs()"
				iconCls="basis ui-icon-plus"  id ="openAddOutputs"/>
		</fd:toolbarGroup>
	</fd:toolbar>
	<!-- editable datagrid -->
	<fd:datagrid toolbar="#outTB" idField="id" id="outputList" onLoadSuccess="hideOperateAreaColumn"
		checkbox="false" fitColumns="true"
		url="taskFlowResolveController.do?changeOutputList&cellId=${cellId}&parentPlanId=${parentPlanId}"
		pagination="false" fit="true">
		<fd:dgCol title="{com.glaway.ids.common.lable.operation}" field="operateArea" formatterFunName="funFlowtaskOut" width="40"/>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.deliverableName}" field="name" width="280" />
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="docName" width="200" formatterFunName="addLink"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.task.required}" field="required" width="30" formatterFunName="isNecessary"/>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.target}" field="result" width="250"/>
	</fd:datagrid>
</div>
	<fd:dialog id="addOutDialog" width="800px" height="530px" modal="true" title="{com.glaway.ids.pm.project.plan.deliverables.addOutputs}" zIndex="4300">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addOutDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="inheritParentDialog" width="400px" height="300px" modal="true" zIndex="4300" title="{com.glaway.ids.pm.project.plan.deliverables.inheritParentDeliverables}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="inheritParentDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="showDocDetailOutputDialog" zIndex="4300" modal="true" width="1000" height="550" title="{com.glaway.ids.pm.project.plan.basicLine.showDocDetail}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>