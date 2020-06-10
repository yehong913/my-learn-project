<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新建文档</title>
<t:base type="jquery,easyui,tools,DatePicker,lhgdialog"></t:base>
<script type="text/javascript">
    var delectFlog = true;
    var delectFlogBeforClose = false;
	$(document).ready(function() {
		 //		$('#clear').hide();
 		 var havePath ='${doc.path}';
		if (havePath != null && havePath != '' && havePath != undefined) {
			if('${upload}'==true||'${upload}'=='true'){
				//$('#knowledgeFile_uploadspan').show();
				$('#knowledgeFile_uploadspan').css("display","");
				//setTimeout(function() { $('.up_button_import_hidden_span').css('display', '');},400);
			}else{
				$('#knowledgeFile_uploadspan').css("display","none");
				//$('#knowledgeFile_uploadspan').hide();
				//setTimeout(function() { $('.up_button_import_hidden_span').css('display', 'none');},400);
			}
			
		}else{
			$('#knowledgeFile_uploadspan').css("display","none");
			//$('#knowledgeFile_uploadspan').hide();
			//setTimeout(function() { $('.up_button_import_hidden_span').css('display', 'none');},400);
		}   
	});
	
	// 批量修改行编辑
	
	var editIndex = undefined;
	var currentIndex = undefined;
	var changeLink = false;
	function endEditing(){
		if (editIndex == undefined){
			return true
			}
		if ($('#projLibDocList').datagrid('validateRow', editIndex)){
			$('#projLibDocList').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
		
	}
	

	
	function workTimeOnChange(){
		var rows = $('#projLibDocList').datagrid('getRows');
		var row = rows[currentIndex];
			$('#projLibDocList').datagrid('endEdit', currentIndex);
		currentIndex = undefined;
		changeLink = true;
		editIndex = undefined;
		var rows2 = $('#projLibDocList').datagrid('getRows');
		var securityLevel=$('#securityLevel').textbox("getValue");
	}
	
	// 批量修改行编辑
	function onClickRowBatch(index,data) {
		var rows = $('#projLibDocList').datagrid('getRows');
		if (currentIndex != undefined && !changeLink){
			workTimeOnChange();
		}
		currentIndex = index;
		changeLink = false;
		if (editIndex != index) {
			if (endEditing()) {
				var row = $('#projLibDocList').datagrid('getSelected');
				$('#projLibDocList').datagrid('selectRow', index).datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#projLibDocList').datagrid('selectRow', editIndex);
			}
		}
		synchCategory(index,data);
	}
	
	function synchCategory(rowIndex,rowData){
		var securityLevel_data = $('#securityLevel').combobox("getData");
		var securityLevel_value=  $('#securityLevel').combobox("getValue");
		
		var data_editor_combobox=new Array();
		for(var i=0;i<securityLevel_data.length;i++){
			var object=securityLevel_data[i];
			var typecode=object.typecode;
			var typename=object.typename;
			if(parseInt(typecode)>=parseInt(securityLevel_value)){
			data_editor_combobox.push(object);
			}
		}
	
		$($('#projLibDocList').datagrid('getEditor',{index:rowIndex,field:'docSecurityLevel'}).target).combobox('loadData',data_editor_combobox);
	
		
	}
	
	   var uploadCount = 0;
	    
	 <%--  var libId = '<%=request.getParameter("libId")%>'; --%>
		function afterCall() {
			alert('上传成功');
		}
		

	
	var docId = null;
	function saveFileBasicAndUpload()
	{   
		debugger;
		delectFlog = false;
		
		 var retOid;
	     var result=true;
	   
	      
		  var allRows=$('#projLibDocList').datagrid('getRows');
		  var dowmLoadUrls = [];
		  var attachmentNames = [];
		  var docattachmentName = "";
		  var docattachmentURL = "";
		  var docAttachmentShowName = "";
		  if(allRows.length>0){
			  endEditing();
		  for (var i = 0; i < allRows.length; i++) {
			 /*  docattachmentName =docattachmentName +","+allRows[i].attachmentName;
			  docattachmentURL = docattachmentURL+","+allRows[i].dowmLoadUrl;
			  docAttachmentShowName = docAttachmentShowName+","+allRows[i].attachmentShowName; */
			  
			  if(docattachmentName == '' || docattachmentName == null){
				  docattachmentName = allRows[i].attachmentName;
			  }else{
				  docattachmentName =docattachmentName +","+allRows[i].attachmentName;
			  }
			  if(docattachmentURL == '' || docattachmentURL == null){
				  docattachmentURL = allRows[i].dowmLoadUrl;
			  }else{
				  docattachmentURL = docattachmentURL+","+allRows[i].dowmLoadUrl;
			  }
			  if(docAttachmentShowName == '' || docAttachmentShowName == null){
				  docAttachmentShowName = allRows[i].attachmentShowName;
			  }else{
				  docAttachmentShowName = docAttachmentShowName+","+allRows[i].attachmentShowName;
			  }
			  
//			  if(allRows[i].docSecurityLevel != null && allRows[i].docSecurityLevel != '' && allRows[i].docSecurityLevel != undefined){
//			  }else{
//				  var p = parseInt(i)+1;
//				  tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.docSecurityLevelNotEmpty" arguments="' + p + '"/>'); 
//				return false;  
//			  }
			}
//		  workTimeOnChange();
		  
		  $('#docattachmentName').val(docattachmentName);
		  $('#docattachmentURL').val(docattachmentURL);
		  $('#docAttachmentShowName').val(docAttachmentShowName);
		  }
		 
		$.ajax({
			type : "POST", 
			url : "projLibController.do?doAddForLocalDoc&attachmentNames="+ attachmentNames+"&dowmLoadUrls="+ dowmLoadUrls,
			async : false,
			data :  $('#projLibDocAddForm').serialize(),
			success : function(data) {
				debugger;
				var d = $.parseJSON(data);				
				if (d.success) {
					docId = d.obj;
					retOid=d.obj;
					result=true;
					var invalidIds = $('#invalidIds').val();
					var delUrl='projLibController.do?deleteProjLibJackrabbitFile&type='+'add';
					$.ajax({
						type : 'POST',
						url : delUrl,
						async : false,
						data : {
							invalidIds : invalidIds,
							docattachmentURL : docattachmentURL
						},
					success : function(data) {
					}
				});
					if('${yanfa}' == 'yanfa'){
						if(result){
								var win = $.fn.lhgdialog("getSelectParentWin");
								win.uploadFile(retOid);
								return true;
							
						} 
						}
					
					return false;		
				}else{
					result= false;
				}
				
				if('${yanfa}' == 'yanfa'){}else{
				var win = $.fn.lhgdialog("getSelectParentWin");
				$.fn.lhgdialog("closeSelect");
				win.tip(d.msg);
				}
					 
			}
		});	
		return true;
	}
	
	
	function afterSuccessCall(file, data, response){
		debugger;
		top.jeasyui.util.tagMask('close');
		var jsonObj = $.parseJSON(data);
		var fileStr = jsonObj.obj;
		var size = (file.size)/(1024*1024);
		if(size > 50 ){
			top.tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.doc.sizeLimit"/>');
			return false;
		}
// 		var securityLevel=$('#securityLevel').textbox("getText");
//		var allRows=$('#projLibDocList').datagrid('getRows');
//		alert(allRows.length);
		var attachmentShowName = fileStr.split(",")[3];
        var uuid = fileStr.split(",")[2];
		var dowmLoadUrl = fileStr.split(",")[1];
		var attachmentName = fileStr.split(",")[0];
		//隐藏字段，处理冗余数据
		var invalidIds = $('#invalidIds').val();
		if(invalidIds != null && invalidIds != '' && invalidIds != undefined){
			invalidIds = invalidIds +","+ dowmLoadUrl;
			 $('#invalidIds').val(invalidIds);
		}else{
			invalidIds = dowmLoadUrl;
			$('#invalidIds').val(invalidIds);
		}
		
		$('#projLibDocList').datagrid('appendRow',{
			dowmLoadUrl: dowmLoadUrl,
			attachmentName: attachmentName,
			attachmentShowName: attachmentShowName,
			uuid : uuid
		});


//		  if(allRows.length>0){
//		  for (var i = 0; i < allRows.length; i++) {
//			  alert(444);
//			  alrt(allRows[i].dowmLoadUrl);
//						$('#projLibDocList').datagrid('updateRow',{
//							index: i,
//							row: {
//								dowmLoadUrl: allRows[i].dowmLoadUrl,
//								attachmentName: allRows[i].attachmentName,
//								docSecurityLevel:  securityLevel
//							}
//						});			   
//			}
//		  }
//		  else{
//		$('#projLibDocList').datagrid('reload');
//		  }
//		$('#downloadlink').html(fileStr.split(',')[0]);
//		var url='projLibController.do?fileDown&filePath='+fileStr.split(',')[1];
//		url=encodeURI(url);
//		$('#downloadlink').attr('href',url);
//		$('filePath').val(fileStr.split(',')[1]);
//		$('#attachmentName').val(fileStr.split(',')[0]);
//		$('#attachmentURL').val(fileStr.split(',')[1]);
//		$('#clear').show();
	}

	

	function deleteProjLibDocList(title, url, gname) {
//		var uuids = [];
		var rows = $('#projLibDocList').datagrid('getSelections');
		var exist = [];
		var all = $("#projLibDocList").datagrid('getRows');
		if (rows.length > 0) {
			top.Alert.confirm(
							'确定删除吗？',
							function(r) {
								if (r) {
									  for(var i=0;i<all.length;i++){
										  var flag = 0;
										  for(var j=0;j<rows.length;j++){
											  if(all[i].uuid == rows[j].uuid){
												  flag = 1;
											  }
										  }
										  if(flag == 0){
											  exist.push(all[i]);
										  }
									  }
									  $("#projLibDocList").datagrid("loadData",exist);
									  tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.attachment.confirmDelSuccess"/>');
								}
							});
		} else {
			tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
		}
	}	
		
		function deleteProjLibDocLine(uuid,index) {
			top.Alert.confirm(
					'确定删除该条记录吗？',
					function(r) {
						if (r) {
			$('#projLibDocList').datagrid('selectRow', index);
			var rows = $('#projLibDocList').datagrid('getSelections');
			var exist = [];
			var all = $("#projLibDocList").datagrid('getRows');
			var row = rows[rows.length - 1];
			  for(var i=0;i<all.length;i++){
				  var flag = 0;
					  if(all[i].uuid == row.uuid){
						  flag = 1;
					  }
				  if(flag == 0){
					  exist.push(all[i]);
				  }
			  }
			  $("#projLibDocList").datagrid("loadData",exist);
			  tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.attachment.confirmDelSuccess"/>');
						}
					});
		}
	
		//刷新datagrid
		function refreshProjLibDocListDatagrid() {
			$('#projLibDocList').datagrid('reload');
		}
		
		
		function uploadProjLibDocList(uuid,index){
			$('#projLibDocList').datagrid('selectRow', index);
			var rows = $('#projLibDocList').datagrid('getSelections');
			var row = rows[rows.length - 1];
			 var url = encodeURI('projLibController.do?fileDown&filePath='+row.dowmLoadUrl+'&attachmentName='+row.attachmentName); 
				url = encodeURI(url);
				window.location.href = url;
				return "";
		}

		function uploadProjLibDocList(uuid,index){
			$('#projLibDocList').datagrid('selectRow', index);
			var rows = $('#projLibDocList').datagrid('getSelections');
			var row = rows[rows.length - 1];
			 var url = encodeURI('projLibController.do?fileDown&filePath='+row.dowmLoadUrl+'&attachmentName='+row.attachmentName); 
				url = encodeURI(url);
				window.location.href = url;
				return "";
		}
		
		function hideDownLoad(){
			if('${download}' == true){
				return false;
			}else{
				return true
			}
		}
		
		
		function getFileSecurityLevel(){
//			workTimeOnChange();
              var securityLevel = $('#securityLevel').combobox("getText");
			  var allRows=$('#projLibDocList').datagrid('getRows');
			  var uuids = "";
			  var dowmLoadUrls ="";
			  var attachmentNames="";
			  var docSecurityLevels="";
			  var docSecurityLevelFrom = "";
		  
			  if(allRows.length>0){
			  for (var i = 0; i < allRows.length; i++) {
				  uuids = uuids+","+allRows[i].uuid;
				  dowmLoadUrls = dowmLoadUrls+","+allRows[i].dowmLoadUrl;
				  attachmentNames = attachmentNames+","+allRows[i].attachmentName;
				  docSecurityLevels = docSecurityLevels+","+allRows[i].docSecurityLevel;
				}
			  }else{
				  uuids = ",===";
				  dowmLoadUrls =",===";
				  attachmentNames=",===";
				  docSecurityLevels=",==="; 
			  }
			  
			docSecurityLevelFrom = $('#securityLevel').combobox("getValue");
			$('#securityLevelFromFile').val(docSecurityLevelFrom);
			var securityLevel_data = $('#securityLevel').combobox("getData");
			data_editor_combobox=securityLevel_data;
			//update_editor_combobox(securityLevel_data)
			
			
			$.ajax({
				url : 'projLibController.do?queryDocSecurityLevelList',
				type : 'post',
				data : {
					uuids : uuids,
					dowmLoadUrls : dowmLoadUrls,
				    attachmentNames : attachmentNames,
				    docSecurityLevels : docSecurityLevels,
				    docSecurityLevelFrom : docSecurityLevelFrom,
				    
				},
				cache : false,
				success : function(data) {
					if (data != null) {
//						refreshProjLibDocListDatagrid();
						var newRows = data.rows;
						if(newRows.length > 0){
							for(var i = 0; i < newRows.length; i++){
								$('#projLibDocList').datagrid('updateRow',{
									index: i,
									row: {
										uuid: newRows[i].uuid,
										dowmLoadUrl: newRows[i].dowmLoadUrl,
										attachmentName:newRows[i].attachmentName,
										docSecurityLevel: newRows[i].docSecurityLevel
									}
								});
							}
						}
						var exist = [];
						var all = $("#projLibDocList").datagrid('getRows');
						  for(var i=0;i<all.length;i++){
								  exist.push(all[i]);
						  }				
					}
				}
			});
		}
		
		
		function addProjLibDocOnCancel1(){
			debugger;
			if(delectFlog){
				delectFlogBeforClose = true;
				var invalidIds = iframe.$("#invalidIds").val();
				var delUrl='projLibController.do?deleteProjLibJackrabbitFile&type='+'updateCancel';
				$.ajax({
					type : 'POST',
					url : delUrl,
					data : {
						invalidIds : invalidIds
					},
				success : function(data) {
				}
			});	
			}
		}
		
</script>
<script
	src="webpage/com/glaway/ids/pm/project/projectmanager/projLibDoc-add.js"></script>
<style type="text/css">
</style>
</head>
<body>
	<fd:form id="projLibDocAddForm">
			<input id="uploadPowerFlog" name="uploadPowerFlog" type="hidden" value="${uploadPowerFlog}">
			<input id="securityLevelFromFile" name="securityLevelFromFile" type="hidden" value="${securityLevelFromFile}">
			<input id="yanfa" name="yanfa" type="hidden" value="${yanfa}">
			<input id="treeType" name="treeType" type="hidden" value="1">
			<input id="fileId" name="fileId" type="hidden" value="${doc.id}">
			<input id="id" name="id" type="hidden">
			<input id="projectId" name="projectId"  type="hidden" value="${doc.projectId}">
			<input id="type" name="type" type="hidden" value="1">
			<input id="docattachmentName" name="docattachmentName" type="hidden">
			<input id="docattachmentURL" name="docattachmentURL" type="hidden">
			<input id="docAttachmentShowName" name="docAttachmentShowName" type="hidden">
			<input id="docSecurityLevelFrom" name="docSecurityLevelFrom" type="hidden">
			<input name="invalidIds" id="invalidIds" type="hidden">
			<input name="useObjectId" id="useObjectId" type="hidden" value = "${useObjectId}">
			<input name="useObjectType" id="useObjectType" type="hidden" value = "${useObjectType}">
	</fd:form>
	<%-- <div>
		<fd:form id="projLibDocAddForm" method="projectController.do?doSave">
			
			
			<fd:inputText id="docName" name="docName"
					title="{com.glaway.ids.common.lable.name}" required="true"
					value="${doc.docName}" />
			<fd:combobox id="securityLevel"
					textField="typename"
					title="{com.glaway.ids.pm.project.projectmanager.security}"
					required="true" value="${fileSecurityLevel}" editable="false"
					valueField="typecode" panelMaxHeight="200"
					url="projLibController.do?queryDocSecurityLevelListFile"
					onChange="getFileSecurityLevel()"></fd:combobox>
			<c:choose>
				<c:when test="${yanfa == 'yanfa'}">
					<fd:inputSearch id="path" required="true" name="path"
						title="{com.glaway.ids.pm.project.projectmanager.path}" value=""
						editable="false"
						searcher="selectDocPath('#parentId','#path','#projectId','#node')" />
					<input name="parentId" id="parentId" type="hidden" value="${doc.parentId}">
					<input name="node" id="node" type="hidden">
				</c:when>
				<c:otherwise>
					<fd:inputSearch id="path" required="true" name="path"
						title="{com.glaway.ids.pm.project.projectmanager.path}"
						value="${doc.path}" editable="false"
						searcher="selectDocPath('#parentId','#path','#projectId','#node')" />
					<input name="parentId" id="parentId" type="hidden" value="${doc.parentId}">
				</c:otherwise>
			</c:choose>
			<fd:inputTextArea name="remark" id="remark"
					title="{com.glaway.ids.pm.project.projectmanager.remark}"
					value="${doc.remark}">${doc.remark}</fd:inputTextArea>
		</fd:form>
	</div> --%>

	
		<!-- 工具栏 -->
		<fd:toolbar id="projLibDoctool22">
			<fd:toolbarGroup align="left">
				<fd:linkbutton
					onclick="deleteProjLibDocList('批量删除','projLibController.do?doProjLibDocListDel','projLibDocUpdateList')"
					value="{com.glaway.ids.common.btn.remove}"
					iconCls="l-btn-icon basis ui-icon-minus" />
				<!-- <span id='knowledgeFile_uploadspan' style="display: none;">  -->
				<fd:uploadify
					name="up_button_import" id="up_button_import" title="上传文件"
					afterUploadSuccessMode="multi"
					uploader="projLibController.do?addFileAttachments&folderId=${folderId}&projectId=${projectId}"
					extend="*.*" auto="true" showPanel="false" multi="true"
					dialog="false" onlyButton="true">
					<fd:eventListener event="onUploadSuccess" listener="afterSuccessCall" />
				</fd:uploadify>
				<!-- </span> -->
			</fd:toolbarGroup>
		</fd:toolbar>
		<fd:datagrid id="projLibDocList" checkbox="true"
			toolbar="#projLibDoctool" fitColumns="true" idField="uuid" fit="true"
			pagination="false" url="" height="98%"
			width="100%">
			<fd:colOpt title="{com.glaway.ids.pm.project.projectmanager.operate}" width="60">
				<fd:colOptBtn
					tipTitle="{com.glaway.ids.pm.project.projectmanager.delete}"
					iconCls="basis ui-icon-minus" onClick="deleteProjLibDocLine"></fd:colOptBtn>
				<fd:colOptBtn
					tipTitle="{com.glaway.ids.pm.project.projectmanager.download}"
					iconCls="basis ui-icon-download" onClick="uploadProjLibDocList"></fd:colOptBtn>
			</fd:colOpt>
			<fd:dgCol title="名称" field="attachmentShowName" width="100" sortable="true" hidden="true"></fd:dgCol>
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.path}"
				field="dowmLoadUrl" width="100" sortable="true" hidden="true"></fd:dgCol>
			<fd:dgCol
				title="{com.glaway.ids.pm.project.projectmanager.attachmentName}"
				field="attachmentName" width="100" sortable="true"></fd:dgCol>
			<%-- <fd:dgCol
				title="{com.glaway.ids.pm.project.projectmanager.fileSecurity}"
				field="docSecurityLevel" width="120"
				editor="{
						type:'combobox',
			options:{   data: 'json',
						valueField:'typename',
						textField:'typename',
						editable:false
					}}" /> --%>
		</fd:datagrid>
	
</body>