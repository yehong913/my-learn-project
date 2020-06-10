<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>修改文档</title>
<t:base type="jquery,easyui,tools"></t:base>
<script
	src="webpage/com/glaway/ids/pm/project/projectmanager/projLibDoc-add.js"></script>
<style type="text/css">
</style>
</head>
<body>
	<fd:form id="projLibDocUpdateForm" method="projectController.do?doSave" style="height:330px;">
		<input id="treeType" name="treeType" type="hidden" value="1">
		<input id="uploadPowerFlog" name="uploadPowerFlog" type="hidden" value="${uploadPowerFlog}">
		<input id="securityLevelFromFile" name="securityLevelFromFile" type="hidden">
		<input id="id" name="id" type="hidden" value="${doc.id}">
		<input id="docid" name="docid" type="hidden">
		<input id="projectId" name="projectId" type="hidden" value="${doc.projectId}">
		<input id="bizId" name="bizId" type="hidden" value="${doc.bizId}">
		<input id="method" name="method" type="hidden" value="${method}">
		<input id="docattachmentNameUpdate" name="docattachmentNameUpdate" type="hidden">
		<input id="docattachmentURLUpdate" name="docattachmentURLUpdate" type="hidden">
		<input id="docSecurityLevelFromUpdate" name="docSecurityLevelFromUpdate" type="hidden">
		<input id="docattachmentIdUpdate" name="docattachmentIdUpdate" type="hidden">	
		<input id="docattachmentSecurityLevelUpdate" name="docattachmentSecurityLevelUpdate" type="hidden">
		<input name="fileTypeId" id="fileTypeId" type="hidden">
		<input name="invalidIds" id="invalidIds" type="hidden">
		<fd:inputText id="docNumber" required="true" readonly="true" 
				name="docNumber" value="${doc.docNumber}" title="{com.glaway.ids.common.lable.code}" />
		<fd:inputText id="docName" name="docName" validType="regExp['/^(\w|[a-zA-Z0-9]|[\u4E00-\u9FA5])*$/','包含特殊字符']"
				title="{com.glaway.ids.common.lable.name}" required="true" value="${doc.docName}" />
		<fd:combobox id="securityLevel" textField="typename" title="{com.glaway.ids.pm.project.projectmanager.security}" 
				required="true" value="${fileSecurityLevel}" editable="false" valueField="typecode" panelMaxHeight="200" 
				url="projLibController.do?queryDocSecurityLevelListFile" onChange="getFileSecurityLevel()"></fd:combobox>
		<fd:inputText id="path" readonly="true" name="path" title="{com.glaway.ids.pm.project.projectmanager.path}" value="${doc.path}" />
	  	<input name="parentId" id="parentId" type="hidden" value="${doc.parentId}">

		<c:if test="${operStatus != 'reject'}">
			<fd:inputSearch id="addDeliveryStandard_docTypeName" required="true" name="docTypeName" title="文档类型"
							editable="false"  value="${repFileTypeName}" searcher="selectDocTypeForProjLibDoc()"/>
			<input name="docTypeId" id="addDeliveryStandard_docTypeId" type="hidden" value="${fileTypeId}"/>
			<fd:additionalAttrValRender entityUri="com.glaway.foundation.rep.entity.RepFile" mode="update" id = "RepFile_generalAddAttr"
										 attrName="${doc.id}" attrVal="${doc.id}" entityId="${doc.id}"></fd:additionalAttrValRender>
		</c:if>

		<c:if test="${operStatus eq 'reject'}">
			<fd:inputSearch id="addDeliveryStandard_docTypeName" required="true" name="docTypeName" title="文档类型"
							editable="false"  value="${repFileTypeName}" searcher="selectDocTypeForProjLibDoc()" readonly="true"/>
			<input name="docTypeId" id="addDeliveryStandard_docTypeId" type="hidden" value="${fileTypeId}"/>
			<fd:additionalAttrValRender entityUri="com.glaway.foundation.rep.entity.RepFile" mode="view" id = "RepFile_generalAddAttr"
										 attrName="${doc.id}" attrVal="${doc.id}" entityId="${doc.id}"></fd:additionalAttrValRender>
		</c:if>

		<fd:inputTextArea name="remark"
			id="remark" title="{com.glaway.ids.pm.project.projectmanager.remark}" value="${doc.remark}"></fd:inputTextArea>	
	</fd:form>

	<div id="p2" class="easyui-panel"
			style="width: 100%; height: 200px; padding: 0px;
			background: #fafafa;"
			data-options="closable:false,    
                collapsible:false,minimizable:false,maximizable:false">
		<!-- 工具栏 -->
		<fd:toolbar id="toolbar2">
			<fd:toolbarGroup align="left" id="toolbarGroup2">
				<c:if test="${download}">
					<fd:linkbutton
						onclick="deleteAttachmentList('批量删除附件','projLibController.do?doAttachmentListDel','attachmentListView')"
						value="{com.glaway.ids.common.msg.delete}" iconCls="l-btn-icon basis ui-icon-minus" />
					<span id='knowledgeFile_uploadspan' style="display: none;">
						<fd:uploadify name="up_button_import" id="up_button_import" afterUploadSuccessMode="multi" 
							uploader="projLibController.do?addFileAttachments&folderId=${folderId}&projectId=${projectId}"
							extend="*.*" auto="true" showPanel="false" multi="true" title="上传文件"
							dialog="false" onlyButton="true">
							<fd:eventListener event="onUploadSuccess"	listener="afterSuccessCall" />
						</fd:uploadify> 
					</span>  
				</c:if>
			</fd:toolbarGroup>
		</fd:toolbar>
		<fd:datagrid fit="false" checkbox="true" idField="id" height="150px" width="100%"
				id="attachmentListView" fitColumns="true"
				url="projLibController.do?getDocAttachment&docId=${doc.id}&fileSecurityLevel=${fileSecurityLevel}"
				pagination="false" onDblClickRow="onClickRowBatchUpdate"
				onClickFunName="workTimeOnChangeUpdate">
			<input id="id" value="id" type="hidden">
			<fd:colOpt title="{com.glaway.ids.common.lable.operation}">
				<fd:colOptBtn tipTitle="{com.glaway.ids.common.msg.delete}" iconCls="basis ui-icon-minus"
					onClick="deleteAttachmentLine"  hideOption="hideDownLoad" ></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.projectmanager.download}" iconCls="basis ui-icon-download"
					onClick="uploadAttachmentList"  hideOption="hideDownLoad"></fd:colOptBtn>						
				<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.view}" iconCls="basis ui-icon-eye"
					onClick="seeAttachmentList" hideOption="hideView"></fd:colOptBtn>
			</fd:colOpt>
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.attachmentName}" field="attachmentName" width="230"
				formatterFunName="formateDocName2" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.fileSecurity}" field="docSecurityLevel" width="100"
				editor="{
					type:'combobox',
					options:{   data: 'json',
								valueField:'typename',
								textField:'typename',
								editable:false
							}}"/>

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.uploader}" field="createName" width="160" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.uploadTime}" field="createTime" width="200"
				formatterFunName="dateFmtFulltime" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.downloadUrl}" field="dowmLoadUrl" width="280" hidden="true" />

		</fd:datagrid>
		<fd:dialog id="docTypeDialogForProjLibDoc" width="800px" height="480px" modal="true" title="{com.glaway.ids.common.deliveryStandard.docType}">
            <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="docTypeDialogForProjLibDoc"></fd:dialogbutton>
            <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
        </fd:dialog>
	</div>
</body>
<script type="text/javascript">
	var delectUpdateFlog = true;
	$(document).ready(function() {
		var method="${method}";		
		if(''!=method&&null!=method&&('update'==method||'revise'==method)){
			$('#docNumber').attr('disabled','disabled');
			$('#path').attr('disabled','disabled');			 
		}
		
		if('${upload}'==true||'${upload}'=='true'){
			//$('#knowledgeFile_uploadspan').show();
			$('#knowledgeFile_uploadspan').css("display","");
			//setTimeout(function() { $('.up_button_import_hidden_span').css('display', '');},400);
		}else{
			//$('#knowledgeFile_uploadspan').hide();
			//setTimeout(function() { $('.up_button_import_hidden_span').css('display', 'none');},400);
			$('#knowledgeFile_uploadspan').css("display","none");
		}
// 		alert('${doc.id}');
	});
	
	// 批量修改行编辑
	
	var editIndex = undefined;
	var currentIndex = undefined;
	var changeLink = false;
	function endEditing(){
		if (editIndex == undefined){
			return true
			}
		if ($('#projLibDocUpdateList').datagrid('validateRow', editIndex)){
			$('#projLibDocUpdateList').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	function workTimeOnChange(){
		var rows = $('#attachmentListView').datagrid('getRows');
		var row = rows[currentIndex];
			$('#attachmentListView').datagrid('endEdit', currentIndex);
		currentIndex = undefined;
		changeLink = true;
		editIndex = undefined;
		var rows2 = $('#attachmentListView').datagrid('getRows');
		var json = encodeURI($.toJSON(rows2));
		var rows2 = $('#attachmentListView').datagrid('getRows');
		var json = encodeURI($.toJSON(rows2));
	}
	
	// 批量修改行编辑
	function onClickRowBatch(index) {
		var rows = $('#projLibDocUpdateList').datagrid('getRows');
		if (currentIndex != undefined && !changeLink){
			workTimeOnChange();
		}
		currentIndex = index;
		changeLink = false;
		if (editIndex != index) {
			if (endEditing()) {
				var row = $('#projLibDocUpdateList').datagrid('getSelected');
				$('#projLibDocUpdateList').datagrid('selectRow', index).datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#projLibDocUpdateList').datagrid('selectRow', editIndex);
			}
		}
		synchCategory(index);
	}
	
	
	
	function synchCategory(rowIndex){
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
	
		$($('#projLibDocUpdateList').datagrid('getEditor',{index:rowIndex,field:'docSecurityLevel'}).target).combobox('loadData',data_editor_combobox);
	
		
	}
	
	// 批量修改行编辑
	
	var attachmentEditIndex = undefined;
	var attachmentCurrentIndex = undefined;
	var attachmentChangeLink = false;
	function endEditingUpdate(){
		if (attachmentEditIndex == undefined){
			return true
			}
		if ($('#attachmentListView').datagrid('validateRow', attachmentEditIndex)){
			$('#attachmentListView').datagrid('endEdit', attachmentEditIndex);
			attachmentEditIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	function workTimeOnChangeUpdate(){
		var rows = $('#attachmentListView').datagrid('getRows');
		var row = rows[attachmentCurrentIndex];
			$('#attachmentListView').datagrid('endEdit', attachmentCurrentIndex);
		attachmentCurrentIndex = undefined;
		attachmentChangeLink = true;
		attachmentEditIndex = undefined;
		var rows2 = $('#attachmentListView').datagrid('getRows');
		var json = encodeURI($.toJSON(rows2));

		
//		if(row != null && row != '' && row != undefined){
//			$.ajax({
//				url : 'projLibController.do?setDocSecurityLevelIntodataBaseVo',
//				type : 'post',
//				data : {
//					id : row.id,
//					docSecurityLevel : row.docSecurityLevel
//				},
//				cache : false,
//				success : function(data) {
//					if (data != null) {
//						refreshAttachmentListViewDatagrid();
//					}
//					else{
//						/* alert('<spring:message code="com.glaway.ids.pm.project.task.editDataFailure"/>'); */
//					} 
//				}
//			});
//		}
	}
	
				// 批量修改行编辑
	function onClickRowBatchUpdate(index) {
		var rows = $('#attachmentListView').datagrid('getRows');
		if (attachmentCurrentIndex != undefined && !attachmentChangeLink&&('${download}' == true||'${download}' == 'true')){
			workTimeOnChangeUpdate();
		}

		attachmentCurrentIndex = index;
		attachmentChangeLink = false;
		if (attachmentEditIndex != index) {
			if (endEditingUpdate()) {
				var row = $('#attachmentListView').datagrid('getSelected');
				$('#attachmentListView').datagrid('selectRow', index).datagrid('beginEdit', index);
				attachmentEditIndex = index;
			} else {
				$('#attachmentListView').datagrid('selectRow', attachmentEditIndex);
				
			}
		}
		synchCategoryUpdate(index);
	}
	
	function synchCategoryUpdate(rowIndex){
		var securityLevel_data = $('#securityLevel').combobox("getData");
		var securityLevel_value=  $('#securityLevel').combobox("getValue");
		
		var data_editor_comboboxUpdate=new Array();
		for(var i=0;i<securityLevel_data.length;i++){
			var object=securityLevel_data[i];
			var typecode=object.typecode;
			var typename=object.typename;
			if(parseInt(typecode)<=parseInt(securityLevel_value)){
			data_editor_comboboxUpdate.push(object);
			}
		}
	
		$($('#attachmentListView').datagrid('getEditor',{index:rowIndex,field:'docSecurityLevel'}).target).combobox('loadData',data_editor_comboboxUpdate);
	
		
	}
	
	  var uploadCount = 0;
	    
		 <%--  var libId = '<%=request.getParameter("libId")%>'; --%>

			function afterCall() {
				alert('上传成功');
			}
			
		
		
		function updateFileBasicAndUpload(){
			debugger;
			delectUpdateFlog = true;
			var curFlog = $('#uploadPowerFlog').val();
			if(curFlog=="1"){
			}else{
				 var allRows=$('#attachmentListView').datagrid('getRows');
				if(allRows.length>0){
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.attachment.confirmNotProvide"/>');
				return false;
				}
			}
			
		     var retOid=''
			 var result=true;

          var allRows=$('#attachmentListView').datagrid('getRows');
		  var docattachmentNameUpdate = "";
		  var docattachmentURLUpdate = "";
		  var docSecurityLevelFromUpdate = "";
		  //数据库中附件更新的数据id和SecurityLevel
		  var docattachmentIdUpdate = "";
		  var docattachmentSecurityLevelUpdate = "";
		  if(allRows.length>0){
			  workTimeOnChange();
		  for (var i = 0; i < allRows.length; i++) {
			  docattachmentNameUpdate =docattachmentNameUpdate +","+allRows[i].attachmentName;
			  docattachmentURLUpdate = docattachmentURLUpdate+","+allRows[i].dowmLoadUrl;
			  
			  var a =allRows[i].docSecurityLevel;
			  if(a == undefined||a==''){
				  a='====';
			  }
			  docSecurityLevelFromUpdate = docSecurityLevelFromUpdate+","+a; 
//			  docSecurityLevelFromUpdate = docSecurityLevelFromUpdate+","+allRows[i].docSecurityLevel;
//			  if(allRows[i].docSecurityLevel != null && allRows[i].docSecurityLevel != '' && allRows[i].docSecurityLevel != undefined){
//			  }else{
//				  var p = parseInt(i)+1;
//				  tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.docSecurityLevelNotEmpty" arguments="' + p + '"/>'); 
//				return false;  
//			  }
			}
		  }
			  var allattachmentListRows=$('#attachmentListView').datagrid('getRows');
			  if(allattachmentListRows.length>0){				  
				  workTimeOnChangeUpdate();
				for(var i = 0; i < allattachmentListRows.length; i++){
					docattachmentIdUpdate =docattachmentIdUpdate +","+allattachmentListRows[i].id;
					
					  var a =allattachmentListRows[i].docSecurityLevel;
					  if(a == undefined||a==''){
						  a='===';
					  }
					  docattachmentSecurityLevelUpdate = docattachmentSecurityLevelUpdate+","+a; 
					
//					docattachmentSecurityLevelUpdate = docattachmentSecurityLevelUpdate+","+allattachmentListRows[i].docSecurityLevel;	
//					if(allattachmentListRows[i].docSecurityLevel != null && allattachmentListRows[i].docSecurityLevel != '' && allattachmentListRows[i].docSecurityLevel != undefined){
//					  }else{
//						  var p = parseInt(i)+1;
//						  tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.docSecurityLevelNotEmptyNew" arguments="' + p + '"/>'); 
//						return false;  
//					  }
				}  
				  
			  }

		  $('#docattachmentNameUpdate').val(docattachmentNameUpdate);
		  $('#docattachmentURLUpdate').val(docattachmentURLUpdate);
		  $('#docSecurityLevelFromUpdate').val(docSecurityLevelFromUpdate);
		  $('#docattachmentIdUpdate').val(docattachmentIdUpdate);
		  $('#docattachmentSecurityLevelUpdate').val(docattachmentSecurityLevelUpdate);
		 var invalidIds = $('#invalidIds').val();
		 $('#fileTypeId').val($('#addDeliveryStandard_docTypeId').val());
		$.ajax({
			type : "POST", 
			url : 'projLibController.do?doUpdate',
			async : false,
			data :  $('#projLibDocUpdateForm').serialize(),
			success : function(data) {
				var d = $.parseJSON(data);
				var win = $.fn.lhgdialog("getSelectParentWin");
				tip(d.msg);
				if (d.success) {
					var delUrl='projLibController.do?deleteProjLibJackrabbitFile&type='+'update';
					$.ajax({
						type : 'POST',
						url : delUrl,
						cache : false,
						data : {
							invalidIds : invalidIds,
							docattachmentURL : docattachmentURLUpdate
						},
					success : function(data) {
					}
				});									
					//$('#attachmentListView').datagrid('reload');
					retOid=d.obj
					result=true;
					return retOid;
				}else{
					result= false;
				}
				win.tip(d.msg);
				 
			}
		});
			if(result){
				try {
					if(filedivLength("libUpdateFilediv")==0)
					{
						return true;
					}
					else
					{
						$('#docid').val(retOid);
						doUpdatecupload($('#projLibDocUpdateForm'));
					}
				} catch (e) {
					return true;
				}
			}
			return false;
		}
		
		function afterSuccessCall(file, data, response){
			top.jeasyui.util.tagMask('close');
			var jsonObj = $.parseJSON(data);
			var fileStr = jsonObj.obj;
			var size = (file.size)/(1024*1024);
			var securityLevel=$('#securityLevel').textbox("getText");
	        var uuid = fileStr.split(",")[2];
			var dowmLoadUrl = fileStr.split(",")[1];
			var attachmentName = fileStr.split(",")[0];
			var createName = fileStr.split(",")[4];
			var createTime = fileStr.split(",")[5];
			
			var invalidIds = $('#invalidIds').val();
			if(invalidIds != null && invalidIds != '' && invalidIds != undefined){
				invalidIds = invalidIds +","+ dowmLoadUrl;
				 $('#invalidIds').val(invalidIds);
			}else{
				invalidIds = dowmLoadUrl;
				$('#invalidIds').val(invalidIds);
			}
			$("#attachmentListViewMsg").hide();
			$('#attachmentListView').datagrid('appendRow',{
				dowmLoadUrl: dowmLoadUrl,
				attachmentName: attachmentName,
				docSecurityLevel: securityLevel,
				id : uuid,
				createName : createName,
				createTime : createTime
			});
//			if(size > 50 ){
//				top.tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.doc.sizeLimit"/>');
//				return false;
//			}
//            $('#projLibDocUpdateList').datagrid('reload');
//			$('#downloadlink').html(fileStr.split(',')[0]);
//			var url='projLibController.do?fileDown&filePath='+fileStr.split(',')[1];
//			url=encodeURI(url);
//			$('#downloadlink').attr('href',url);
//			$('filePath').val(fileStr.split(',')[1]);
//			$('#attachmentName').val(fileStr.split(',')[0]);
//			$('#attachmentURL').val(fileStr.split(',')[1]);
//			$('#clear').show();
//			$('#knowledgeFile_uploadspan').hide();
//			$('#knowFilediv').hide();
		}

		
		function formateDocName2(val, row, value){
			 var url=encodeURI("projLibController.do?fileDown&attachmentName="+ row.attachmentName+"&filePath="+ row.dowmLoadUrl);
			 url=encodeURI(url);
			if ((row.dowmLoadUrl != null)) {
				
					return  row.attachmentName ;
							
	
				} else {
					
				if (row.attachmentName != null) {
					
					return row.attachmentName;
		
				}
				return "";
			}

		}
		
		function formateDocName(val, row, value){
			 var url=encodeURI("projLibController.do?fileDown&attachmentName="+ row.attachmentName+"&filePath="+ row.dowmLoadUrl);
			 url=encodeURI(url);
			if ((row.dowmLoadUrl != null)) {
				
					return row.attachmentName ;
							
	
				} else {
					
				if (row.attachmentName != null) {
					
					return row.attachmentName;
		
				}
				return "";
			}

		}
		
		
			function deleteProjLibDocList(title, url, gname) {
//				var uuids = [];
				var rows = $('#projLibDocUpdateList').datagrid('getSelections');
				var exist = [];
				var all = $("#projLibDocUpdateList").datagrid('getRows');
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
											  $("#projLibDocUpdateList").datagrid("loadData",exist);
											  tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.attachment.confirmDelSuccess"/>');
//											for (var i = 0; i < rows.length; i++) {
//												uuids.push(rows[i].uuid);
//											}
//											$.ajax({
//												url : url,
//												type : 'post',
//												data : {
//													uuids : uuids.join(',')
//												},
//												cache : false,
//												success : function(data) {
//													var d = $.parseJSON(data);
//													var msg = d.msg;
//													if (d.success) {
//														tip(msg);
//														$('#projLibDocUpdateList').datagrid(
//														'reload');
//														$('#projLibDocUpdateList').datagrid(
//														'unselectAll');
//														ids = '';
//													} else {
//														tip(msg);
//													}
//												}
//											});
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
										$('#projLibDocUpdateList').datagrid('selectRow', index);
										var rows = $('#projLibDocUpdateList').datagrid('getSelections');
										var row = rows[rows.length - 1];
										var exist = [];
										var all = $("#projLibDocUpdateList").datagrid('getRows');
										  for(var i=0;i<all.length;i++){
											  var flag = 0;
												  if(all[i].uuid == row.uuid){
													  flag = 1;
												  }
											  if(flag == 0){
												  exist.push(all[i]);
											  }
										  }
										  $("#projLibDocUpdateList").datagrid("loadData",exist);
										  tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.attachment.confirmDelSuccess"/>');
//										$
//												.ajax({
//													url : 'projLibController.do?doProjLibDocListDel',
//													type : 'post',
//													data : {
//														uuids : row.uuid
//													},
//													cache : false,
//													success : function(data) {
//														var d = $.parseJSON(data);
//														var msg = d.msg;
//														if (d.success) {
//															tip(msg);
//															window.setTimeout('refreshProjLibDocListDatagrid()', 500);
//														} else {
//															tip(msg);
//														}
//													}
//												});
									}
								});
			}
		
			//刷新datagrid
			function refreshProjLibDocListDatagrid() {
				$('#projLibDocUpdateList').datagrid('reload');
			}
			
			//刷新datagrid
			function refreshAttachmentListViewDatagrid() {
				$('#attachmentListView').datagrid('reload');
			}
			
			function deleteAttachmentList(title, url, gname){
				var ids = [];
				var rows = $('#attachmentListView').datagrid('getSelections');
				var exist = [];
				var all = $("#attachmentListView").datagrid('getRows');
				if (rows.length > 0) {
					top.Alert.confirm(
							'确定删除吗？',
							function(r) {
								if (r) {
					  for(var i=0;i<all.length;i++){
						  var flag = 0;
						  for(var j=0;j<rows.length;j++){
							  if(all[i].id == rows[j].id){
								  flag = 1;
							  }
						  }
						  if(flag == 0){
							  exist.push(all[i]);
						  }
					  }
					  $("#attachmentListView").datagrid("loadData",exist);
					  tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.attachment.confirmDelSuccess"/>');
								}
							});
				} else {
					tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
				}
				
			}
			
			function deleteAttachmentLine(id,index) {
				$('#attachmentListView').datagrid('selectRow', index);
				var rows = $('#attachmentListView').datagrid('getSelections');
				var row = rows[rows.length - 1];
				var exist = [];
				var all = $("#attachmentListView").datagrid('getRows');
				top.Alert.confirm(
						'确定删除该条记录吗？',
						function(r) {
							if (r) {
				  for(var i=0;i<all.length;i++){
					  var flag = 0;
						  if(all[i].id == row.id){
							  flag = 1;
						  }
					  if(flag == 0){
						  exist.push(all[i]);
					  }
				  }
				  $("#attachmentListView").datagrid("loadData",exist);
				  tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.attachment.confirmDelSuccess"/>');
							}
						});
				
			}
			
			function updateAttachmentLine(id){
				createPpdateAttachmentwindowAdd("修改","projLibController.do?goAttachmentUpdate&id="+id,"","350");
			}
			
			function createPpdateAttachmentwindowAdd(title, url, width, height) {
				width = width ? width : 750;
				height = height ? height : 400;
				if (width == "100%" || height == "100%") {
					width = document.body.offsetWidth;
					height = document.body.offsetHeight - 100;
				}
					createDialog('attachmentUpdateDAndSubmitDialog',url);

			}
			
			
			
			// 批量修改行编辑
			function dataBaseSecretLevelCodeAndVoMapUpdate(index) {
				var rows = $('#attachmentListView').datagrid('getRows');
				if (attachmentCurrentIndex != undefined && !attachmentChangeLink&&('${download}' == true||'${download}' == 'true')){
					workTimeOnChangeUpdate();
				}

				attachmentCurrentIndex = index;
				attachmentChangeLink = false;
				if (attachmentEditIndex != index) {
					if (endEditingUpdate()) {
						var row = $('#attachmentListView').datagrid('getSelected');
						$('#attachmentListView').datagrid('selectRow', index).datagrid('beginEdit', index);
						attachmentEditIndex = index;
					} else {
						$('#attachmentListView').datagrid('selectRow', attachmentEditIndex);
						
					}
				}
			}
			
			function uploadProjLibDocListUpdate(uuid,index){
				$('#projLibDocUpdateList').datagrid('selectRow', index);
				var rows = $('#projLibDocUpdateList').datagrid('getSelections');
				var row = rows[rows.length - 1];
				 var url = encodeURI('projLibController.do?fileDown&filePath='+row.dowmLoadUrl+'&attachmentName='+row.attachmentName); 
					url = encodeURI(url);
					window.location.href = url;
					return "";
			}

			function uploadAttachmentList(uuid,index){
				$('#attachmentListView').datagrid('selectRow', index);
				var rows = $('#attachmentListView').datagrid('getSelections');
				var row = rows[rows.length - 1];
				var url = encodeURI('projLibController.do?fileDown&filePath='+row.dowmLoadUrl+'&attachmentName='+row.attachmentName);
				url = encodeURI(url);
				window.location.href = url;
				return "";
			}
			
 			function seeAttachmentList(id,index){				
				$('#attachmentListView').datagrid('selectRow', index);
				var rows = $('#attachmentListView').datagrid('getSelections');
				var row = rows[rows.length - 1];
				viewPDF(row.dowmLoadUrl);

			}
			
			
			function hideDownLoad(){
				if('${download}' == 'true'){
					return false;
				}else{
					workTimeOnChangeUpdate();
					return true
				}
			}
			
			
			function hideView(a,b){		
				var extendName =a.attachmentName.substring(a.attachmentName.lastIndexOf('.')+1);
				if(extendName=="pdf" || extendName=="doc" || extendName=="docx" || extendName=="xls" || extendName=="xlsx" || extendName=="ppt" || extendName=="pptx" || extendName=="txt" || extendName=="odt")
				{
					return false;
				}else{
					return true;
				}
				
			}
			
			function getFileSecurityLevel(){
				debugger
				var alldatabaseRows=$('#attachmentListView').datagrid('getRows');

				var ids = "";
				var dowmLoadUrls ="";
				var attachmentNames="";
				var docSecurityLevels="";
				var createNames = "";
				var createTimes = "";

				if(alldatabaseRows.length>0){
					for (var i = 0; i < alldatabaseRows.length; i++) {
						ids = ids+","+alldatabaseRows[i].id;
						dowmLoadUrls = dowmLoadUrls+","+alldatabaseRows[i].dowmLoadUrl;
						attachmentNames = attachmentNames+","+alldatabaseRows[i].attachmentName;
						docSecurityLevels = docSecurityLevels+","+alldatabaseRows[i].docSecurityLevel;
						createNames = createNames+","+alldatabaseRows[i].createName;
						createTimes = createTimes+","+alldatabaseRows[i].createTime;
					}
				}else{
					ids = ",===";
					dowmLoadUrls =",===";
					attachmentNames=",===";
					docSecurityLevels=",===";
					createNames=",===";
					createTimes=",===";
				}

				docSecurityLevelFrom = $('#securityLevel').combobox("getValue");
				$('#securityLevelFromFile').val(docSecurityLevelFrom);
				var securityLevel_data = $('#securityLevel').combobox("getData");
				data_editor_combobox=securityLevel_data;

				$.ajax({
					url : 'projLibController.do?dataBaseListByChangeSecurityLevel',
					type : 'post',
					data : {
						ids : ids,
						dowmLoadUrls : dowmLoadUrls,
						attachmentNames : attachmentNames,
						docSecurityLevels : docSecurityLevels,
						docSecurityLevelFrom : docSecurityLevelFrom,
						createNames : createNames,
						createTimes : createTimes

					},
					cache : false,
					success : function(data) {
						if (data != null) {
							//									refreshProjLibDocListDatagrid();
							var newRows = data.rows;
							if(newRows.length > 0){
								for(var i = 0; i < newRows.length; i++){
									$('#attachmentListView').datagrid('updateRow',{
										index: i,
										row: {
											id: newRows[i].id,
											dowmLoadUrl: newRows[i].dowmLoadUrl,
											attachmentName:newRows[i].attachmentName,
											docSecurityLevel: newRows[i].docSecurityLevel,
											createName: newRows[i].createName,
											createTime: newRows[i].createTime
										}
									});
								}
							}
						}
					}
				});
			}
			
			
			function updateProjLibDocOnCancel1(){
				if(delectUpdateFlog){					
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
			
			/**
	         * 选择文档类型页面
	         */
	        function selectDocTypeForProjLibDoc() {
	            var url = 'projLibController.do?goDocType';
	            createDialog('docTypeDialogForProjLibDoc',url);
	        }
			
	        function docTypeDialogForProjLibDoc(iframe) {
	            iframe.getSelectedDocRowAndShowInfo();	          
   	            var rowLength = iframe.$("#repFileTypeConfigList").datagrid('getSelections').length;
   				if(rowLength>0){
   					iframe.colseSelectForPage(); 		
   				}
	            return false;
	        }
	        
	        //显示自定义属性：
	        function userDefinedInfo(id){
	            $("#userDefinedInfo").load("projLibController.do?goShowUserDefinedInfo&type=update&id="+id);    
	        }
</script>
