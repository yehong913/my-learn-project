<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目库文档详情</title>
<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
	<script type="text/javascript">	
		$(document).ready(function() {				
			var docRefused = '${docRefused}';
            if(docRefused != null && docRefused == "docRefused") {
            	$("#resubmitTool").css('display','');
            }	
		});	
		
		function hideDownLoad(a,b) {
			if ('${download}' == 'true' || '${download}' == true) {
				var extendName =a.attachmentName.substring(a.attachmentName.lastIndexOf('.')+1);
				if(extendName=="pdf" || extendName=="doc" || extendName=="docx" || extendName=="xls" || extendName=="xlsx" 
						|| extendName=="ppt" || extendName=="pptx" || extendName=="txt" || extendName=="odt") {
					return false;
				} else {
					return true;
				}							
			} else {
				return true;
			}			
		}
		
		function hideDownLoad1() {
			if ('${download}' == 'true' || '${download}' == true) {
				return false;
			} else {
				return true;
			}
		}
		
		function hideDetail(a,b) {
			var extendName =a.attachmentName.substring(a.attachmentName.lastIndexOf('.')+1);
			if(extendName=="pdf" || extendName=="doc" || extendName=="docx" || extendName=="xls" || extendName=="xlsx" || extendName=="ppt" || extendName=="pptx" || extendName=="txt" || extendName=="odt")
			{
				if ('${detail}' == 'true' || '${detail}' == true) {
					return false;
				} else {
					return true;
				}
			}else{
				return true;
			}
		}
		
		function docResubmitFlow() {
			var docId = '${doc.id}';
			var resubmitUrl = "projLibController.do?submitDocResubmitFlow&taskId=${taskId}&taskNumber=${taskNumber}&id="+docId;

			$.ajax({
				type : 'POST',
				url : resubmitUrl,
				data : '',
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
// 						W.getData();
						try{
		                	$.fn.lhgdialog("getSelectParentWin").parent.flashTaskShow();
		                }catch(e){
		                	
		                }
		                
		                try{
		                	$.fn.lhgdialog("getSelectParentWin").parent.$("#startTaskListMore_wait").datagrid('reload');
		                }catch(e){
		                	
		                }
						$.fn.lhgdialog("getSelectParentWin").tip(d.msg);
						$.fn.lhgdialog("closeSelect");						
					}
				} 
			});			
		}
		
		//文档状态
		function formatStatus(val, row, value) {
			var status;
			var bizCurrent = eval($('#lifeCycleList').val());
		    for(var i=0;i<bizCurrent.length;i++){
					if(val == bizCurrent[i].name){
						status = bizCurrent[i].title;
						var resultUrl = status;
						if(val != "nizhi"||row.operStatus=='reject'){
							resultUrl = '<a href="#" onclick="openDocHisoryFlow(\''
								+ row.id + '\')"><font color=blue><div style="text-align:left">' +status+ '</div></font></a>'
						}
						
						return resultUrl;
					}
			}
		    return status;
		}
		
		function openDocHisoryFlow(taskNumber){
			var tabTitle = '历史版本审批流程查看';
			var url = '${webRoot}/taskFlowCommonController.do?taskFlowList&taskNumber='+taskNumber;
			createdetailwindow_close(tabTitle, url, 800, 400);
		}

		function formateDocName(val, row, value){
			 var url=encodeURI("projLibController.do?fileDown&filePath="+ row.dowmLoadUrl);
			 url=encodeURI(url);
			var UserSecurityLevel= '${securityLevel}';
			var securityLevel=	"${doc.securityLevelId}";
			if ((row.dowmLoadUrl != null)) {
				return row.attachmentName ;
			} else {
				
			if (row.attachmentName != null) {
				return row.attachmentName;
			}
			return "";
		}
		}
		
		function seeAttachmentList(uuid,index){
			$('#docHisList').datagrid('selectRow', index);
			var rows = $('#docHisList').datagrid('getSelections');
			var row = rows[rows.length - 1];
			viewPDF(row.dowmLoadUrl);
		}
		
		function uploadAttachmentList(uuid,index){
			$('#docHisList').datagrid('selectRow', index);
			var rows = $('#docHisList').datagrid('getSelections');
			var row = rows[rows.length - 1];
// 		 	var url = encodeURI('projLibController.do?fileDown&filePath='+row.dowmLoadUrl+'&attachmentName='+row.attachmentName); 
// 			url = encodeURI(url);
// 			window.location.href = url;
// 			return "";
			window.location.href = encodeURI('projLibController.do?fileDown&filePath='+row.dowmLoadUrl+'&attachmentName='+row.attachmentName);
		}
		
		function closeProjLibDocDtailResubmit() {
			$.fn.lhgdialog("closeSelect");
		}
	</script>
	</head>
	
<body style="overflow:hidden;">
	<c:if test="${docRefused!=null && docRefused=='docRefused'}">
		<div border="false" fit="true">
	</c:if>
	<c:if test="${docRefused!='docRefused'}">
		<div border="false" fit="true">
	</c:if>
		<c:if test="${opFlag!=1}">
		 	<div id="p1" class="easyui-panel" title="<spring:message code="com.glaway.ids.pm.project.projectmanager.baseInfo" />"     
		   		style="width:100%; padding:1px"   
		        data-options="closable:false, collapsible:false,minimizable:false,maximizable:false">  
		    </div>
		</c:if>
		<div style="height:380px;overflow:auto;">
			<fd:form id="projLibDocDtailForm" method="projectController.do?doSave">
				<input id="id" name="id" type="hidden" value="${doc.id}">
				<input id="bizId" name="bizId" type="hidden" value="${doc.bizId}">
				<input id="lifeCycleList" name="lifeCycleList" type="hidden" value="${lifeCycleList}"/>
				<fd:inputText id="docNumber"  required="required" 
						name="docNumber" value="${doc.docNumber}" title="{com.glaway.ids.common.lable.code}" readonly="true"/>
				<fd:inputText id="docName" name="docName" title="{com.glaway.ids.common.lable.name}" readonly="true"
						required="required" value="${doc.docName}" />
				<fd:inputText id="securityLevel"  required="required" 
						name="securityLevel" value="${doc.securityLevel}" title="{com.glaway.ids.pm.project.projectmanager.security}" readonly="true"/>
		  		<fd:inputText id="path" name="path" title="{com.glaway.ids.pm.project.projectmanager.path}" readonly="true"
						required="required" value="${doc.path}" />
				<fd:inputText id="createName"  required="required" 
						name="createName" value="${doc.createName}" title="{com.glaway.ids.pm.project.projectmanager.creator}" readonly="true"/>
		  		<fd:inputText id="createTimeStr" name="createTimeStr" title="{com.glaway.ids.pm.project.projectmanager.createTime}" readonly="true"
					  	required="required" value="${doc.createTimeStr}" />
			  	<fd:inputText id="updateName"  required="required" 
						name="updateName" value="${doc.updateName}" title="{com.glaway.ids.pm.project.projectmanager.updater}" readonly="true"/>
				<fd:inputText id="updateTimeStr" name="updateTimeStr" title="{com.glaway.ids.pm.project.projectmanager.updateTime}" readonly="true"
					  	required="required" value="${doc.updateTimeStr}" />
				<c:if test="${opFlag==1 && download}">		 	
					<span style="display:inline-block; margin:0 6px 0 70px;"><spring:message code='com.glaway.ids.pm.project.projectmanager.doc'/></span>
					<span style="display:inline-block; position:relative; top:-10px; margin-top:0px;">
						<a title='${doc.attachmentName}' style='color:blue; ' href='projLibController.do?fileDown&filePath=${doc.dowmLoadUrl}'>
							<div style='white-space:nowrap;width:570px;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow:hidden;'>
								${doc.attachmentName}
							</div>
						</a>
					</span>				
				</c:if>
				
				<c:if test="${!empty repFileTypeName}">				
					<fd:inputSearch id="addDeliveryStandard_docTypeName" required="true" name="docTypeName" title="文档类型" 
			    		editable="false"  value="${repFileTypeName}" readonly="true"/>
		
					<fd:additionalAttrValRender  entityUri="com.glaway.foundation.rep.entity.RepFile" mode="view" id = "RepFile_generalAddAttr"
							attrName="${doc.id}" attrVal="${doc.id}" entityId="${doc.id}"></fd:additionalAttrValRender>
				</c:if>		
															
				<fd:inputTextArea name="remark" id="remark" title="{com.glaway.ids.pm.project.projectmanager.remark}" readonly="true"
					 	value="${doc.remark}"></fd:inputTextArea>				
			</fd:form>
		</div>
		
		
		<c:if test="${opFlag!=1}">
			<div id="p2" class="easyui-panel" title="<spring:message code="com.glaway.ids.pm.project.projectmanager.attachmentList"/>"     
		        style="width:100%;height:180px;padding:1px;background:#fafafa; overflow: auto;"   
		       	data-options="closable:false, collapsible:false,minimizable:false,maximizable:false"> 
			   	<fd:datagrid fit="true" idField="id" id="docHisList" fitColumns="true" url="projLibController.do?getDocAttachmentView&docId=${doc.id}&viewFlog=${viewFlog}" pagination="false">
					<fd:colOpt title="{com.glaway.ids.pm.project.projectmanager.operate}" width="90">
						<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.view}" iconCls="basis ui-icon-eye"
							onClick="seeAttachmentList" hideOption="hideDetail" ></fd:colOptBtn>
						<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.projectmanager.download}" iconCls="basis ui-icon-download"
							onClick="uploadAttachmentList" hideOption="hideDownLoad1"></fd:colOptBtn>
					</fd:colOpt>
			     	<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.attachmentName}" field="attachmentName" width="250"  formatterFunName="formateDocName" />
			     	<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.security}" field="docSecurityLevel" width="180"  />
					<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.uploader}" field="createName" width="160" />
					<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.uploadTime}" field="createTime" width="200" formatterFunName="dateFmtFulltime" />
					<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.downloadUrl}" field="dowmLoadUrl" width="280" hidden="true" />
				</fd:datagrid>
			</div>  
		</c:if>

		<div id="resubmitTool" class="div-msg-btn" style="display:none;">
			<div>
				<fd:linkbutton id="resubmit" onclick="docResubmitFlow()"
					value="{com.glaway.ids.common.btn.submit}" classStyle="button_nor" />
				<fd:linkbutton onclick="closeProjLibDocDtailResubmit()"
					id="projectProjLibDocDtailCloseBtn"
					value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" />
			</div>
		</div>
	</div>
</body>
</html>