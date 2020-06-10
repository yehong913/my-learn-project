<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<t:base type="jquery,easyui_iframe,tools"></t:base>
<head>
	<title>自动匹配</title>
	<script >
		$(function($) {
			if ("1" == $("#isEnableFlag").val()) {		
			}
		});

		function closeThisDialog() {
			$.fn.lhgdialog("closeSelect");
		}
		
		
		// 自动匹配文档及其来源
		function autoInputsChangeMatch(url) {
			top.jeasyui.util.commonMask('open','请稍候...');
			$.ajax({
				url : url,
				type : 'post',
				data : {
					uuId : $('#uuId').val(),
					projectId :  $('#projectId').val(),
					parentPlanId : $("#parentPlanId").val()
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					var msg = d.msg.split('<br/>')
					top.tip(msg[0]); // John
					if (d.success) {
						top.jeasyui.util.commonMask('close');
						$("#inputChangeMatchList").datagrid("reload");
					}
				}
			});
		}
		
		// 保存文档及其来源
		function saveInputsChangeMatch(url) {
			$.ajax({
				url : url,
				type : 'post',
				data : {
					uuId : $('#uuId').val(),
					parentPlanId : $('#parentPlanId').val()
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					var msg = d.msg.split('<br/>')
					top.tip(msg[0]); // John
					if (d.success) {
						var win =$.fn.lhgdialog("getSelectParentWin");	
						win.cleanSelectCells();
						$.fn.lhgdialog("closeSelect");
					}
				}
			});
		}
		
		
		// 清除选中行的文档及其来源
		function deleteDocumentChangeMatch(url) {
			var rows = $("#inputChangeMatchList").datagrid('getSelections');
			var ids = [];
			if (rows.length > 0) {
				top.Alert.confirm('确定清除选中行的文档及其来源吗？', function(r) {
					if (r) {
						for (var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						$.ajax({
							url : url,
							type : 'post',
							data : {
								uuId : $('#uuId').val(),
								ids : ids.join(',')
							},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								var msg = d.msg.split('<br/>')
								top.tip(msg[0]); // John
								if (d.success) {
									$("#inputChangeMatchList").datagrid("reload");
								}
							}
						});
					}
				});
			} else {
				top.tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
			}
		}
		
		//关联项目库：
		 function goChangeProjLibMatchLink(id,index) {
				debugger;
				
				var row;
				$('#inputChangeMatchList').datagrid('selectRow',index);
				var all = $('#inputChangeMatchList').datagrid('getRows');
				for(var i=0;i<all.length;i++){
					var inx = $("#inputChangeMatchList").datagrid('getRowIndex', all[i]);
					if(inx == index){
						row = all[i];
					}
				}
				
				idRow=row.id;
				var dialogUrl = 'projLibController.do?goProjLibLayout0&id='+$("#projectId").val()+'&rowId='+idRow;
				$("#"+'changeMatchLineDialog').lhgdialog("open", "url:"+dialogUrl);
			}
		 
	 	//关联项目库确认：
		function changeMatchLineDialog(iframe) {
			debugger;
			if (iframe.validateSelectedNum()) {
				var docId = iframe.getSelectionsId();
				var folderId = iframe.$("#folderId").val();
				var rowId = iframe.$("#rowId").val();
				 $.ajax({
					url : 'taskFlowResolveController.do?updateChangeMatchProjLibAndPlanLink',
					type : 'post',
					data : {
						fileId : docId,
						folderId : folderId,
						rowId : rowId,
						projectId : $('#projectId').val(),
/* 						useObjectId : $('#useObjectId').val(),
						useObjectType : $('#useObjectType').val(), */
						'originType' : 'PROJECTLIBDOC',
						uuId : $('#uuId').val()
					},
					cache : false,
					success : function(data) {
						debugger;
						var d = $.parseJSON(data);
						$('#inputChangeMatchList').datagrid('reload');
					}
				}); 
				return true;
			} else {
				return false;
			}
		}
	 	
	 	//关联计划：
		function goChangePlanMatchLink(id,index){
			debugger;
			var row;
			$('#inputChangeMatchList').datagrid('selectRow',index);
			var all = $('#inputChangeMatchList').datagrid('getRows');
			for(var i=0;i<all.length;i++){
				var inx = $("#inputChangeMatchList").datagrid('getRowIndex', all[i]);
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
								createDialog('changePlanInputsMatchDialog',url);
							}
						});
					}
				}
			});
		}
	 	
		//关联计划确认：
		function changePlanInputsMatchDialog(iframe){
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
						inputsName : inputsName,
						'originType' : 'PLAN',
						uuId : $('#uuId').val()
					},
					url : 'taskFlowResolveController.do?updateChangeMatchProjLibAndPlanLink',
					cache:false,
					success : function(data){
						debugger;
						$('#inputChangeMatchList').datagrid('reload');
					}
				});
			}else{
				return false;
			}
		}
		
		
	    //前置活动输出样式
		function originStyleChangeMatch(val, row, index) {
	    	debugger;
			if (val != undefined && val != null && val != '') {
				var matchFlag = row.matchFlag;
				if ( matchFlag == 'true') {
					return "<div style=\"color:red;\">" + val + "</div>";
				}
				else {
					return val;
				}
			}else{
				return '';
			}
		}
		
		function addLinkChangeMatch(val, row, value) {	
			debugger;
			if (row.docName != undefined && row.docName != null && row.docName != '') {				
			}else{
				return '';
			}
			if(row.originType == 'PROJECTLIBDOC'){
			debugger;
			var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDocMatch(\""+ row.docIdShow+ "\",\""+ row.docName+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
			+ row.docName
			+ "</a>"
			if (row.ext3 == 'false') {				
				path = row.docName;
				
			}
			return path; 
			}
			else if(row.originType == 'PLAN'){
				debugger;
				var path = "<a  href='#' title='查看' style='color:blue' onclick='openProjectDocMatch(\""+ row.docId+ "\",\""+ row.docName+ "\",\""+ row.ext1+"\",\""+ row.ext2+"\")'>"
				+ row.docName
				+ "</a>"
 			if (row.ext3 == 'false') { 			
					path = row.docName;				
			} 
			return path;
			
			}
			else{
				return '';
			}
			
		}
				
		function openProjectDocMatch(id, name,download,history) {
			if (download == false || download == 'false') {
				download = "false";
			}
			if (history == false || history == 'false') {
				history = "false";
			}
			var url = "projLibController.do?viewProjectDocDetail&id=" + id
					+ "&download=" + download + "&history=" + history;
			createdetailwindowMatch("文档详情", url, "870", "580")
		}

			function createdetailwindowMatch(title, url, width, height) {
				width = width ? width : 700;
				height = height ? height : 400;
				if (width == "100%" || height == "100%") {
					width = document.body.offsetWidth;
					height = document.body.offsetHeight - 100;
				}
				if (typeof (windowapi) == 'undefined') {
					createDialog('showDocDetailChangeMatchDialog',url)
				} else {
					createDialog('showDocDetailChangeMatchDialog',url)
				} 
			}
	</script>
</head>
<body>	
	<div fit="true" style="width: 100%;">	
	<fd:toolbar id="inTB">
		<div class="easyui-panel" fit="true" style="width: 100%;">
		<fd:toolbarGroup align="left">
		<fd:linkbutton onclick="saveInputsChangeMatch('taskFlowResolveController.do?doBatchChangeMatchSave')" 
				iconCls="basis ui-icon-save" value="保存" id="saveInputsChangeMatchButton" />
		<fd:linkbutton onclick="autoInputsChangeMatch('taskFlowResolveController.do?autoInfoChangeMatch')" 
				iconCls="basis ui-icon-matching" value="自动匹配" id="autoInputsChangeMatchButton" />
			<fd:linkbutton onclick="deleteDocumentChangeMatch('taskFlowResolveController.do?deleteChangeDocumentMatch')" 
				iconCls="basis ui-icon-cancel" value="清除来源" id="deleteDocumentChangeMatchButton" />
		</fd:toolbarGroup>
		</div>
	</fd:toolbar>
	</div>
	<input type="hidden" id ='parentPlanId' name='parentPlanId' value="${parentPlanId}"/>
	<input type="hidden" id ='uuId' name='uuId' value="${uuId}"/>
	<input id="projectId" name="projectId" type="hidden" value="${projectId}" />	
	<fd:datagrid toolbar="#inTB" idField="id" id="inputChangeMatchList" pagination="false" fit="true" fitColumns="true"  checkbox="true" url="taskFlowResolveController.do?outInputMatchList&uuId=${uuId}&parentPlanId=${parentPlanId}&type=CHANGE">		
			<fd:colOpt title="操作" width = "80">
				<fd:colOptBtn iconCls="basis ui-icon-pddata" tipTitle="项目库关联" onClick="goChangeProjLibMatchLink" hideOption="changeProjLibLinkOpt"/>
				<fd:colOptBtn iconCls="basis ui-icon-search" tipTitle="计划关联" onClick = "goChangePlanMatchLink" hideOption = "changePlanLinkOpt"/>
			</fd:colOpt>
		<fd:dgCol title="活动名称" tipField="活动名称" field="useObjectName" width="200"/>
		<fd:dgCol title="输入" tipField="输入" field="name" width="100"/>		
		<fd:dgCol title="文档" tipField="文档" field="docName"  width="100" formatterFunName="addLinkChangeMatch" />
		<fd:dgCol title="来源" tipField="来源" field="originObjectNameShow" width="100" formatterFunName="originStyleChangeMatch"/>
	</fd:datagrid>	
	<fd:dialog id="changeMatchLineDialog" width="1000px" height="500px" modal="true" title="项目库关联" >
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="changeMatchLineDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>

	<fd:dialog id="changePlanInputsMatchDialog" width="800px" height="580px" modal="true" title="选择来源计划" >
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="changePlanInputsMatchDialog"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	<fd:dialog id="showDocDetailChangeMatchDialog" modal="true" width="1000" height="550" title="文档详情" >
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	</body>
	</html>