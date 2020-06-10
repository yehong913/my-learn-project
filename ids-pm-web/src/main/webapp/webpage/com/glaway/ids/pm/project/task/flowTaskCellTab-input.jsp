<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-panel" id="cellInput" fit="true">
	<input type="hidden" name="cellId" id="cellId" value="${cellId}" />
	<input type="hidden" name="parentPlanId" id="parentPlanId" value="${parentPlanId}" />
	<input type="hidden" id ='isEnableFlag' name='isEnableFlag' value="${isEnableFlag}"/>
	<script >
		$(function($) {
			if ("1" == $("#isEnableFlag").val()) {
				$("#openAddInnerinputResolve").hide();
				$("#openAddOutinputResolve").hide();
				$("#openAddLocaldocResolve").hide();
				$("#deleteInputsButton").hide();				
			}
		});

		function addInputs() {
			if ("1" == $("#isEnableFlag").val()) {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
				return;
			}
			// 新增输入
			var preposeIds = $('#preposeIds').val();
			var allPreposeIds = $('#allPreposeIds').val();
			var dialogUrl = '${pageContext.request.contextPath}/taskFlowResolveController.do?goAdd&type=INPUT'
					+'&allPreposeIds='+$("#allPreposeIds").val()
					+'&cellId='+$("#cellId").val()
					+'&parentPlanId='+$("#parentPlanId").val()
					+'&preposeIds='+preposeIds
					+'&useObjectId='+$('#useObjectId').val()
					+'&useObjectType='+$('#useObjectType').val();
			createDialog('addInputDialog', dialogUrl);
		}

		function addInputDialog(iframe) {
			iframe.submitSelectData();
		}
		
		function initInputsFlowTask() {
			$("#inputList").datagrid("reload");
		}

		// 删除资源
		function deleteInput(id) {
			if ("1" == $("#isEnableFlag").val()) {
				top.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
				return;
			}
			$.ajax({
				url : 'taskFlowResolveController.do?doDelInput',
				type : 'post',
				data : {
					'id' : id
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						$.ajax({
							type : 'POST',
							url : 'taskFlowResolveController.do?inputList',
							async : false,
							data : {
								cellId : $('#cellId').val(),
								parentPlanId : $('#parentPlanId').val()
							},
							success : function(data) {
								$("#inputList").datagrid("loadData", data);
							}
						});
					} else {
						var msg = d.msg.split('<br/>')
						top.tip(msg[0]); // John
					}
				}
			});
		}

		function addLinkResolve(val, row, value) {				
			if(row.originType == "LOCAL"){
	             return '<a  href="#" onclick="importDocResolve(\'' + row.docId + '\',\'' + row.docName + '\')" id="myDoc"  style="color:blue">'+val+'</a>';
			}
			else if(row.originType == 'PROJECTLIBDOC'){
				debugger;
				var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docIdShow+ "\",\""+ row.docName+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
				+ row.docName
				+ "</a>"
			if (row.ext3 == 'false') {
				path = row.docName;
			}
			return path; 
			}
			else if(row.originType == 'PLAN'){
				debugger;
				var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docId+ "\",\""+ row.docName+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
				+ row.docName
				+ "</a>"
 			if (row.ext3 == 'false') {
				path = row.docName;
			} 
			return path; 
			}
			
		}
		
		function importDocResolve(filePath,fileName){
			debugger;
			window.location.href = encodeURI('jackrabbitFileController.do?fileDown&fileName='+fileName+'&filePath=' + filePath);
		}
		
		
		
/* 		function addLink(val, row, value){
			debugger;
			if(val!=null&&val!=''&&row.originType == 'LOCAL'){
				return '<a  href="#" onclick="importDocResolve(\'' + row.docId + '\',\'' + row.docNameShow + '\')" id="myDoc"  style="color:blue">'+val+'</a>';
			}else if(row.originType == 'PROJECTLIBDOC'){
				var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docIdShow+ "\",\""+ row.docNameShow+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
					+ row.docNameShow
					+ "</a>"
				if (row.ext3 == false || row.ext3 == 'false') {
					path = row.docNameShow;
				}
				return path; 
			}else if(row.originType == 'PLAN'){
				var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.docId+ "\",\""+ row.docNameShow+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
				+ row.docNameShow
				+ "</a>"
			if (row.ext3 == false || row.ext3 == 'false') {
				path = row.docNameShow;
			}
			return path; 
			}
			else return ;
			
		}
		 */

		function openProjectDoc1(id, name,download,history) {
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
					createDialog('showDocDetailResolveDialog',url)
				} else {
					createDialog('showDocDetailResolveDialog',url)
				} 
			}

		
		function showDocDetail(id,download,detail) {
			var url = "projLibController.do?viewProjectDocDetail&opFlag=1&id=" + id + "&download=" + download + "&detail=" + detail;
			createDialog('showDocDetailFlowTaskInputDialog', url);
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
								ids : ids.join(',')
							},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								var msg = d.msg.split('<br/>')
								top.tip(msg[0]); // John
								if (d.success) {
									$.ajax({
										type : 'POST',
										url : 'taskFlowResolveController.do?inputList',
										async : false,
										data : {
											cellId : $('#cellId').val(),
											parentPlanId : $('#parentPlanId').val()
										},
										success : function(data) {
											$("#inputList").datagrid("loadData", data);
										}
									});
								}else{
									var msg = d.msg.split('<br/>')
									top.tip(msg[0]); // John
								}
							}
						});
					}
				});
			} else {
				top.tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
			}
		}
		
		
		function openAddDialogInputSureResolve(iframe){
			iframe.saveSelectedRowsResolve();
			return false;
		}

		function openAddDialogLocalSureResolve(iframe) {
			iframe.addFormsubmitResolve();
		    return false;
		}

		 function openAddResolve(dgId, url,id) {
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
		 
		 function resolveProjLibLinkOpt(a,b){
			 if(a.originTypeExt == 'DELIEVER'){
				 return false;
			 }
			 return true;
		 }
		 
		 function resolvePlanLinkOpt(a,b){
			 if(a.originTypeExt == 'DELIEVER'){
				 return false;
			 }
			 return true;
		 }
		 
		 //关联项目库：
		 function goResolveProjLibLink(id,index) {
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
				$("#"+'resolveCheckLineDialog').lhgdialog("open", "url:"+dialogUrl);
			}
		 
	 	//关联项目库确认：
		function resolveCheckLineDialog(iframe) {
			debugger;
			if (iframe.validateSelectedNum()) {
				var docId = iframe.getSelectionsId();
				var folderId = iframe.$("#folderId").val();
				var rowId = iframe.$("#rowId").val();
				 $.ajax({
					url : 'taskFlowResolveController.do?updateInputsProjLibLink',
					type : 'post',
					data : {
						fileId : docId,
						folderId : folderId,
						rowId : rowId,
						projectId : $('#projectId').val(),
						useObjectId : $('#useObjectId').val(),
						useObjectType : $('#useObjectType').val()
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
		function goResolvePlanLink(id,index){
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
						var url = 'planController.do?goSelectPlanInputs&projectId='+$("#projectId").val()+'&useObjectId='+$("#useObjectId").val()+'&useObjectType='+$("#useObjectType").val()+'&tempId='+row.id;
						//url = encodeURI(encodeURI(url))
						
						createDialog('resolvePlanInputsDialog',url);
					}
				}
			});
		}
	 	
		//关联计划确认：
		function resolvePlanInputsDialog(iframe){
			if (iframe.validateSelectedBeforeSave()) {
				debugger;
				var row = iframe.$('#planlist').datagrid('getSelections');
				var planId = row[0].id;
				var tempId = iframe.$("#tempId").val();
				var useObjectId = iframe.$("#useObjectId").val();
				var inputsName = iframe.$("#inputsName").val();
				
				$.ajax({
					type:'POST',
					data:{
						planId:planId,
						tempId : tempId,
						useObjectId : useObjectId,
						inputsName : inputsName
					},
					url : 'taskFlowResolveController.do?setInputsPlanLink',
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
				onclick="openAddResolve('inputList', 'taskFlowResolveController.do?goAdd&type=INNERTASK', 'openAddInnerDialogInputResolve')"
				iconCls="basis ui-icon-plus" />
			<fd:linkbutton
				value="{com.glaway.ids.pm.project.plan.createOutinput}"
				id="openAddOutinputResolve"
				onclick="openAddResolve('inputList', 'taskFlowResolveController.do?goAdd&type=DELIEVER', 'openAddOutDialogInputResolve')"
				iconCls="basis ui-icon-plus" />
			<fd:linkbutton
				value="{com.glaway.ids.pm.project.plan.createLocaldoc}"
				id="openAddLocaldocResolve"
				onclick="openAddResolve('inputList', 'taskFlowResolveController.do?goAdd&type=LOCAL', 'openAddDocDialogInputResolve')"
				iconCls="basis ui-icon-plus" />
			<!-- <fd:linkbutton value="{com.glaway.ids.common.btn.create}" id="openAddInputs"
				onclick="addInputs()" iconCls="basis ui-icon-plus" /> -->
			<fd:linkbutton onclick="deleteSelections('taskFlowResolveController.do?doBatchDel')" 
				iconCls="basis ui-icon-minus" value="{com.glaway.ids.common.btn.remove}" id="deleteInputsButton" />
		</fd:toolbarGroup>
	</fd:toolbar>
	
	<input id="preposeIds" name="preposeIds" type="hidden" value="${plan_.preposeIds}" />
	<input id="allPreposeIds"  name="allPreposeIds" type="hidden" value="${allPreposeIds}" />	
	<input id="parentPlanId" name="parentPlanId" type="hidden" value="${parentPlanId}" />
	<input id="projectId" name="projectId" type="hidden" value="${plan_.projectId}" />
	<input id="useObjectId" name="useObjectId" type="hidden" value="${plan_.id}" />
	<input id="useObjectType" name="useObjectType" type="hidden" value="PLAN" />
	
	<!-- editable datagrid -->
	<c:if test="${isEnableFlag == '1' }">
		<fd:datagrid toolbar="#inTB" idField="id" id="inputList" onLoadSuccess="hideOperateAreaColumn" fitColumns="true"
				url="taskFlowResolveController.do?inputList&cellId=${cellId}&parentPlanId=${parentPlanId}&projectId=${plan_.projectId}&useObjectId=${plan_.id}"
				checkbox="true" pagination="false" fit="true">
			<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="name" width="300"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="docName" formatterFunName="addLinkResolve" width="250"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.orgin}" field="originObjectName" width="300" />
		</fd:datagrid>
	</c:if>
	<c:if test="${isEnableFlag != '1' }">
		<fd:datagrid toolbar="#inTB" idField="id" id="inputList" onLoadSuccess="hideOperateAreaColumn" fitColumns="true"
				url="taskFlowResolveController.do?inputList&cellId=${cellId}&parentPlanId=${parentPlanId}&projectId=${plan_.projectId}&useObjectId=${plan_.id}"
				checkbox="true" pagination="false" fit="true">
				<fd:colOpt title="操作" width = "30">
					<fd:colOptBtn iconCls="basis ui-icon-pddata" tipTitle="项目库关联" onClick="goResolveProjLibLink" hideOption="resolveProjLibLinkOpt"/>
					<fd:colOptBtn iconCls="basis ui-icon-search" tipTitle="计划关联" onClick = "goResolvePlanLink" hideOption = "resolvePlanLinkOpt"/>
				</fd:colOpt>
			<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="name" width="300"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.docName}" field="docName" formatterFunName="addLinkResolve" width="250"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.deliverables.orgin}" field="originObjectName" width="300" />
		</fd:datagrid>
	</c:if>
	<fd:dialog id="addInputDialog" zIndex="4300" width="600px" height="400px" modal="true" title="{com.glaway.ids.pm.project.plan.inputs.addInputs}">
	    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addInputDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	<fd:dialog id="showDocDetailFlowTaskInputDialog" zIndex="4300" width="870px" height="550px" modal="true">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	<fd:dialog id="openAddInnerDialogInputResolve" width="800px" height="530px" modal="true" zIndex="4300"
		  title="{com.glaway.ids.pm.project.plan.addInnerinput}">
		  <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogInputSureResolve"></fd:dialogbutton>
		  <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="openAddOutDialogInputResolve" width="800px" height="530px" modal="true" zIndex="4300"
          title="{com.glaway.ids.pm.project.plan.addOutinput}">
          <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogInputSureResolve"></fd:dialogbutton>
          <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="openAddDocDialogInputResolve" width="400px" height="150px" modal="true" zIndex="4300"
          title="{com.glaway.ids.pm.project.plan.addLocal}">
          <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="openAddDialogLocalSureResolve"></fd:dialogbutton>
          <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	<fd:dialog id="resolveCheckLineDialog" width="1000px" height="500px" modal="true" title="项目库关联" zIndex="4300">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="resolveCheckLineDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>

	<fd:dialog id="resolvePlanInputsDialog" width="800px" height="580px" modal="true" title="选择来源计划" zIndex="4300">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="resolvePlanInputsDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	<fd:dialog id="showDocDetailResolveDialog" zIndex="4300" modal="true" width="1000" height="550" title="文档详情">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
</div>
