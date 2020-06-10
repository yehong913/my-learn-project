<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-panel" id="cellInput" fit="true">
	<input type="hidden" name="cellId" id="cellId" value="${cellId}" />
	<input type="hidden" name="parentPlanId" id="parentPlanId" value="${parentPlanId}" />
	<input type="hidden" id ='isEnableFlag' name='isEnableFlag' value="${isEnableFlag}" />
	<script>
		$(function($) {
			if("1" == $("#isEnableFlag").val()) {
				$("#openAddInnerinputResolve").hide();
				$("#openAddOutinputResolve").hide();
				$("#openAddLocaldocResolve").hide();
				$("#deleteChangeInputsButton").hide();
			}
		});
	
		function addInputs() { 
			if("1" == $("#isEnableFlag").val()){
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityAdd"/>');
				return;
			}
			// 新增输入
			var preposeIds = $('#preposeIds').val();
			var allPreposeIds = $('#allPreposeIds').val();
			var dialogUrl = '${pageContext.request.contextPath}/taskFlowResolveController.do?goChangeAdd&type=INPUT' 
					+'&allPreposeIds='+$("#allPreposeIds").val()
					+'&cellId='+$("#cellId").val()
					+'&parentPlanId='+$("#parentPlanId").val()
					+'&preposeIds='+preposeIds
					+'&useObjectId='+ $('#useObjectId').val()
					+'&useObjectType='+$('#useObjectType').val();
			createDialog('addInputDialog', dialogUrl);
		}
		
		function addInputDialog(iframe) {
			 iframe.submitSelectData();
		}

		
		function initInputsChangeFlow() {
			$("#inputList").datagrid("reload");
		}

		function addLink(val, row, value) {
			if (val != null && val != '') {
				if ((row.havePower == true || row.havePower == 'true') && '${userLevel}' >= row.securityLevel) {
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
					+ "\")' id='myDoc' style='color:blue'>" + val +"</a>";
				} else {
					return val;
				}
			} else {
				return;
			}
		}

		function showDocDetail(id,download,detail){
			var url="projLibController.do?viewProjectDocDetail&opFlag=1&id="+id+ "&download=" + download+ "&detail=" + detail;
			createdetailwindow2("<spring:message code='com.glaway.ids.pm.project.plan.basicLine.showDocDetail'/>", url, "1000", "550");
		}

		function hideOperateAreaColumn() {
			if ("1" == $("#isEnableFlag").val()) {
				$('#inputList').datagrid('hideColumn', 'operateArea');
			}
		}

		// 删除交付项
		function deleteSelections(url) {
			var rows = $("#inputList").datagrid('getSelections');
			var ids = [];
			var cellId = $('#cellId').val();
			var parentPlanId = $('#parentPlanId').val();
			if (rows.length > 0) {
				top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmDel"/>', function(r) {
					if (r) {
						for (var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						$.ajax({
							url : url,
							type : 'post',
							data : {
								ids : ids.join(','),
								parentPlanId : parentPlanId
							},
							cache : false,
							success : function(data) {
								$('#inputList').datagrid('reload');
							}
						});
					}
				});
			} else {
				top.tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
			}
		}
		
		function addLinkChange(val, row, value) {
			debugger
			if (row.docName != undefined && row.docName != null && row.docName != '') {				
			}else{
				return '';
			}
			if(row.originType == "LOCAL"){
	             return '<a  href="#" onclick="importDocChange(\'' + row.docId + '\',\'' + row.docName + '\')" id="myDoc"  style="color:blue">'+val+'</a>';
			}
			else if(row.originType == 'PROJECTLIBDOC'){
				debugger;
				var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDocChange(\""+ row.docIdShow+ "\",\""+ row.docName+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
				+ row.docName
				+ "</a>"
			if (row.ext3 == 'false') {
				path = row.docName;
			}
			return path; 
			}
			else if(row.originType == 'PLAN'){
				debugger;
				var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDocChange(\""+ row.docId+ "\",\""+ row.docName+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
				+ row.docName
				+ "</a>"
 			if (row.ext3 == 'false') {
				path = row.docName;
			} 
			return path; 
			}
			
		}
		
		function importDocChange(filePath,fileName){
			debugger;
			window.location.href = encodeURI('jackrabbitFileController.do?fileDown&fileName='+fileName+'&filePath=' + filePath);
		}
		
		function openProjectDocChange(id, name,download,history) {
			if (download == false || download == 'false') {
				download = "false";
			}
			if (history == false || history == 'false') {
				history = "false";
			}
			var url = "projLibController.do?viewProjectDocDetail&id=" + id
					+ "&download=" + download + "&history=" + history;
			createdetailwindow2("文档详情", url, "870", "580")
		}

			function createdetailwindow2(title, url, width, height) {
				width = width ? width : 700;
				height = height ? height : 400;
				if (width == "100%" || height == "100%") {
					width = document.body.offsetWidth;
					height = document.body.offsetHeight - 100;
				}
				if (typeof (windowapi) == 'undefined') {
					createDialog('showDocDetailChangeDialog',url)
				} else {
					createDialog('showDocDetailChangeDialog',url)
				} 
			}

		
		function showDocDetail(id,download,detail) {
			var url = "projLibController.do?viewProjectDocDetail&opFlag=1&id=" + id + "&download=" + download + "&detail=" + detail;
			createDialog('showDocDetailFlowTaskInputDialog', url);
		}
		
		
		
		 function openAddChange(dgId, url,id) {
			 debugger
			 if ("1" == $("#isEnableFlag").val()) {
					top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
					return;
				}
/* 		    var allRows = $("#"+dgId).datagrid("getRows"); */
		    var ids = '';
		    gridname = dgId;
		    var dialogUrl = url +'&allPreposeIds='+$("#allPreposeIds").val()
			+'&cellId='+$("#cellId").val()
			+'&parentPlanId='+$("#parentPlanId").val()
			+'&preposeIds='+preposeIds
			+'&useObjectId='+$('#useObjectId').val()
			+'&useObjectType='+$('#useObjectType').val();
		    $("#"+id).lhgdialog("open",'url:' + dialogUrl+"&ids="+ids);   
		}
		 
			function openAddDialogInputSureChange(iframe){
				iframe.saveSelectedRowsChange();
				return false;
			}

			function openAddDialogLocalSureChange(iframe) {
				iframe.afterSubmitChange();
			    return false;
			}
			
			
			 function changeProjLibLinkOpt(a,b){
				 if(a.originTypeExt == 'DELIEVER'){
					 return false;
				 }
				 return true;
			 }
			 
			 function changePlanLinkOpt(a,b){
				 if(a.originTypeExt == 'DELIEVER'){
					 return false;
				 }
				 return true;
			 }
			 
			 //关联项目库：
			 function goChangeProjLibLink(id,index) {
					debugger;
					
					var row;
					$('#inputList').datagrid('selectRow',index);
					var all = $('#inputList').datagrid('getRows');
					for(var i=0;i<all.length;i++){
						var inx = $("#inputList").datagrid('getRowIndex', all[i]);
						if(inx == index){
							row = all[i];
						}
					}
					
					idRow=row.id;
					var dialogUrl = 'projLibController.do?goProjLibLayout0&id='+$("#projectId").val()+'&rowId='+idRow;
					$("#"+'changeCheckLineDialog').lhgdialog("open", "url:"+dialogUrl);
				}
			 
		 	//关联项目库确认：
			function changeCheckLineDialog(iframe) {
				debugger;
				if (iframe.validateSelectedNum()) {
					var docId = iframe.getSelectionsId();
					var folderId = iframe.$("#folderId").val();
					var rowId = iframe.$("#rowId").val();
					 $.ajax({
						url : 'taskFlowResolveController.do?updateProjLibAndPlanLinkChange',
						type : 'post',
						data : {
							fileId : docId,
							folderId : folderId,
							rowId : rowId,
							projectId : $('#projectId').val(),
							parentPlanId : $('#parentPlanId').val(),
							useObjectId : $('#useObjectId').val(),
							useObjectType : $('#useObjectType').val(),
							originType :'PROJECTLIBDOC'							
						},
						cache : false,
						success : function(data) {
							debugger;
							var d = $.parseJSON(data);
							$('#inputList').datagrid('reload');
						}
					}); 
					return true;
				} else {
					return false;
				}
			}
		 	
		 	//关联计划：
			function goChangePlanLink(id,index){
				debugger;
				var row;
				$('#inputList').datagrid('selectRow',index);
				var all = $('#inputList').datagrid('getRows');
				for(var i=0;i<all.length;i++){
					var inx = $("#inputList").datagrid('getRowIndex', all[i]);
					if(inx == index){
						row = all[i];
					}
				}
				
				$.ajax({
					type:'POST',
					data:{inputsName : row.name},
					url:'planController.do?setInputsNameToSession',
					cache:false,
					success:function(data){
						var d = $.parseJSON(data);
						if(d.success){
							
							$.ajax({
								type:'POST',
								data:{useObjectId : row.useObjectId,
									parentPlanId : $("#parentPlanId").val()
								},
								url:'taskFlowResolveController.do?getPlanIdByChangeInputUseObjectId',
								cache:false,
								success:function(data){
									var d = $.parseJSON(data);
									var url = 'planController.do?goSelectPlanInputs&projectId='+$("#projectId").val()+'&useObjectId='+d.obj+'&useObjectType='+$("#useObjectType").val()+'&tempId='+row.id;
									createDialog('changePlanInputsDialog',url);
								}
							});
							
						
						}
					}
				});
			}
		 	
			//关联计划确认：
			function changePlanInputsDialog(iframe){
				if (iframe.validateSelectedBeforeSave()) {
					debugger;
					var row = iframe.$('#planlist').datagrid('getSelections');
					var planId = row[0].id;
					var tempId = iframe.$("#tempId").val();
					//var useObjectId = iframe.$("#useObjectId").val();
					var useObjectId = $("#useObjectId").val();
					var inputsName = iframe.$("#inputsName").val();
					
					$.ajax({
						type:'POST',
						data:{
							planId:planId,
							tempId : tempId,
							parentPlanId : $('#parentPlanId').val(),
							useObjectId : useObjectId,
							inputsName : inputsName,
							originType :'PLAN'
						},
						url : 'taskFlowResolveController.do?updateProjLibAndPlanLinkChange',
						cache:false,
						success : function(data){
							debugger;
							$('#inputList').datagrid('reload');
						}
					});
				}else{
					return false;
				}
			}
			
	</script>
	<fd:toolbar id="inTB">
		<fd:toolbarGroup align="left">
			<fd:linkbutton
				value="{com.glaway.ids.pm.project.plan.createInnerinput}"
				id="openAddInnerinputResolve"
				onclick="openAddChange('inputList', 'taskFlowResolveController.do?goChangeAdd&type=INNERTASK', 'openAddInnerDialogInputChange')"
				iconCls="basis ui-icon-plus" />
			<fd:linkbutton
				value="{com.glaway.ids.pm.project.plan.createOutinput}"
				id="openAddOutinputResolve"
				onclick="openAddChange('inputList', 'taskFlowResolveController.do?goChangeAdd&type=DELIEVER', 'openAddOutDialogInputChange')"
				iconCls="basis ui-icon-plus" />
			<fd:linkbutton
				value="{com.glaway.ids.pm.project.plan.createLocaldoc}"
				id="openAddLocaldocResolve"
				onclick="openAddChange('inputList', 'taskFlowResolveController.do?goChangeAdd&type=LOCAL', 'openAddDocDialogInputChange')"
				iconCls="basis ui-icon-plus" />
<%-- 			<fd:linkbutton value="{com.glaway.ids.common.btn.create}" id="openAddInputs"
				onclick="addInputs()" iconCls="basis ui-icon-plus" /> --%>
			<fd:linkbutton
				onclick="deleteSelections('taskFlowResolveController.do?doDelChangeInputs&parentPlanId=${parentPlanId}&cellId=${cellId}')" 
				iconCls="basis ui-icon-minus" value="{com.glaway.ids.common.btn.remove}" id="deleteChangeInputsButton" />
		</fd:toolbarGroup>
	</fd:toolbar>

	<input id="preposeIds" name="preposeIds" type="hidden" value="${plan_.preposeIds}" />
	<input id="allPreposeIds"  name="allPreposeIds" type="hidden" value="${allPreposeIds}" />
	<input id="projectId" name="projectId" type="hidden" value="${projectId}" />
	<input id="parentPlanId" name="parentPlanId" type="hidden" value="${parentPlanId}" />
	<input id="useObjectId" name="useObjectId" type="hidden" value="${plan_.id}" />
		
	<c:if test="${isEnableFlag == '1' }">
		<fd:datagrid toolbar="#inTB" idField="id" id="inputList"  onLoadSuccess="hideOperateAreaColumn" fitColumns="true"
				url="taskFlowResolveController.do?changeInputList&cellId=${cellId}&parentPlanId=${parentPlanId}"
				checkbox="true" pagination="false" fit="true">
			<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="name" width="300" />
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="docName" width="250" formatterFunName="addLinkChange"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.orgin}"  field="originObjectName"  width="300" />
		</fd:datagrid>
	</c:if>
	<c:if test="${isEnableFlag != '1' }">
		<fd:datagrid toolbar="#inTB" idField="id" id="inputList"  onLoadSuccess="hideOperateAreaColumn" fitColumns="true"
				url="taskFlowResolveController.do?changeInputList&cellId=${cellId}&parentPlanId=${parentPlanId}"
				checkbox="true" pagination="false" fit="true">
			<fd:colOpt title="操作" width = "30">
					<fd:colOptBtn iconCls="basis ui-icon-pddata" tipTitle="项目库关联" onClick="goChangeProjLibLink" hideOption="changeProjLibLinkOpt"/>
					<fd:colOptBtn iconCls="basis ui-icon-search" tipTitle="计划关联" onClick = "goChangePlanLink" hideOption = "changePlanLinkOpt"/>
			</fd:colOpt>
			<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="name" width="300" />
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="docName" width="250" formatterFunName="addLinkChange"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.orgin}"  field="originObjectName"  width="300" />
		</fd:datagrid>
	</c:if>
</div>
	<fd:dialog id="openAddInnerDialogInputChange" width="800px" height="530px" modal="true" zIndex="4300"
		  title="{com.glaway.ids.pm.project.plan.addInnerinput}">
		  <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogInputSureChange"></fd:dialogbutton>
		  <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="openAddOutDialogInputChange" width="800px" height="530px" modal="true" zIndex="4300"
          title="{com.glaway.ids.pm.project.plan.addOutinput}">
          <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogInputSureChange"></fd:dialogbutton>
          <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="openAddDocDialogInputChange" width="400px" height="150px" modal="true" zIndex="4300"
          title="{com.glaway.ids.pm.project.plan.addLocal}">
          <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogLocalSureChange"></fd:dialogbutton>
          <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>

	<fd:dialog id="addInputDialog" width="600px" height="400px" modal="true" title="{com.glaway.ids.pm.project.plan.inputs.addInputs}" zIndex="4300">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addInputDialog" />
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog" />
	</fd:dialog>
	
	<fd:dialog id="showDocDetailChangeFlowDialog" zIndex="4300" modal="true" width="1000" height="550" title="{com.glaway.ids.pm.project.plan.basicLine.showDocDetail}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog" />
	</fd:dialog>
		<fd:dialog id="showDocDetailChangeDialog" zIndex="4300" modal="true" width="1000" height="550" title="文档详情">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
		<fd:dialog id="changeCheckLineDialog" width="1000px" height="500px" modal="true" title="项目库关联" zIndex="4300">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="changeCheckLineDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>

	<fd:dialog id="changePlanInputsDialog" width="800px" height="580px" modal="true" title="选择来源计划" zIndex="4300">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="changePlanInputsDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
