<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">


	<div region="center" style="padding: 1px;" id="showList">
		<input id="lifeCycleList" name="lifeCycleList" type="hidden"
			value="${lifeCycleList}" /> <input id="securityLevel"
			name="securityLevel" type="hidden" value="${securityLevel}" /> <input
			id="list" type="hidden" value="${list}" /> <input id="detail"
			type="hidden" value="${detail}" /> <input id="create" type="hidden"
			value="${create}" /> <input id="remove" type="hidden"
			value="${remove}" /> <input id="update" type="hidden"
			value="${update}" /> <input id="download" type="hidden"
			value="${download}" /> <input id="upload" type="hidden"
			value="${upload}" /> <input id="history" type="hidden"
			value="${history}" /> <input id="revise" type="hidden"
			value="${revise}" /> <input id="rollback" type="hidden"
			value="${rollback}" /> <input id="approve" type="hidden"
			value="${approve}" /> <input id="isCreate" type="hidden"
			value="${isCreate}" /> <input id="havePower" type="hidden"
			value="${havePower}" />
			<input id="webRoot" type="hidden"
			value="${webRoot}" />
			<input id = "folderId" name = "folderId" type="hidden" value = "${folderId}">
		<div id="projLibDocListTb">
			<fd:searchform id="searchProjLibDocTag"
				onClickSearchBtn="searchProjLibDoc()"
				onClickResetBtn="tagSearchResetProjLibDoc()" help="help:LibraryStructureManagement">
				<fd:inputText title="{com.glaway.ids.common.lable.code}" name="RepFile.fileNumber"
					id="searchProjLibDocNumber" queryMode="like" />
				<fd:inputText title="{com.glaway.ids.common.lable.name}" name="RepFile.fileName"
					id="searchProjLibDocName" queryMode="like" />
				<fd:inputText title="{com.glaway.ids.pm.project.projectmanager.creator}" id="projLibDocCreator" />
				<%-- <fd:inputDateTime name="RepFile.firstTime" title="创建时间" id="searchProjLibDocCreateTime" queryMode="le&&ge"></fd:inputDateTime>--%>
				<fd:inputDateRange id="searchProjLibDocCreateTime" interval="1"
					title="{com.glaway.ids.pm.project.projectmanager.createTime}" name="RepFile.firstTime" opened="0"></fd:inputDateRange>
				<fd:inputText title="{com.glaway.ids.pm.project.projectmanager.updater}" id="projLibDocModifor" />
				<%--<fd:inputDateTime name="RepFile.updateTime" title="修改时间" id="searchProjLibDocModifyTime" queryMode="le&&ge"></fd:inputDateTime>--%>
				<fd:inputDateRange id="searchProjLibDocModifyTime" interval="1"
					title="{com.glaway.ids.pm.project.projectmanager.updateTime}" name="RepFile.updateTime" opened="0"></fd:inputDateRange>
				<fd:dictCombobox dictCode="secretLevel" name="RepFile.securityLevel"
					id="searchSecurityLevel" editable="false" multiple="true"
					prompt="{com.glaway.ids.common.lable.selectall}" title="{com.glaway.ids.pm.project.projectmanager.security}" queryMode="in" />
				<fd:combobox id="repFileBizCurrent" textField="title" title="{com.glaway.ids.pm.project.projectmanager.bizCurrent}"
					name="RepFile.bizCurrent" editable="false" valueField="name"
					multiple="true" prompt="{com.glaway.ids.common.lable.selectall}" url="projLibController.do?statusList"
					queryMode="in"></fd:combobox>
					
				 <fd:inputSearch id="addDeliveryStandard_docTypeName" name="docTypeName" title="文档类型" 
	    			editable="false" searcher="selectDocTypeForProjLibDocList()" />
	    			<input name="docTypeId" id="addDeliveryStandard_docTypeId" type="hidden" />
  	
		    	<div id="userDefinedInfo" >				
				</div>
					
			</fd:searchform>
			<c:if test="${opFlag != 1}">
			<fd:toolbar>
				<fd:toolbarGroup align="left" id="projLibOperButton">
					<fd:linkbutton id="libCreate" 
						onclick="createaddwindowAdd('新增','projLibController.do?goAdd&folderId=${folderId}&projectId=${projectId}','','350')"
						value="{com.glaway.ids.common.btn.create}" iconCls="basis ui-icon-plus" />

					<fd:linkbutton id="libDelete" 
						onclick="doController('删除','projLibController.do?doBatchDel','确定删除选中的文档吗?')"
						value="{com.glaway.ids.common.btn.remove}" iconCls="basis ui-icon-minus" />
						
					<fd:linkbutton id="libUpdatePath" 
						onclick="createLibUpdatePath('修改路径','projLibController.do?goUpdatePath&folderId=${folderId}&projectId=${projectId}','','350')"
						value="{com.glaway.ids.pm.config.updatePath}" iconCls="basis basis ui-icon-comment" operationCode="updatePathProjLibDoc"/>
						
					<%-- <fd:linkbutton id="createBatch" 
						onclick="createBatch('快速批量创建文档','projLibController.do?goAddBatch&folderId=${folderId}&projectId=${projectId}','','350')"
						value="快速批量创建文档" iconCls="basis ui-icon-plus" /> --%>
				</fd:toolbarGroup>
				<!--  
				<fd:toolbarGroup align="right">
					<fd:linkbutton onclick="searchProjLibDoc();" value="查询"
						iconCls="basis ui-icon-search" plain="true" />
					<fd:linkbutton onclick="tagSearchResetProjLibDoc()" value="重置"
						iconCls="basis ui-icon-cancel" plain="true" />
				</fd:toolbarGroup> 
				-->
			</fd:toolbar>
			</c:if>
		</div>
		<c:if test="${opFlag == 1}">
		<fd:datagrid checkbox="false" idField="id" fit="true"
			fitColumns="true" id="projLibDocList"
			pagination="true"
			url="projLibController.do?searchDatagrid&field=id&folderId=${folderId}&projectId=${projectId}"
			toolbar="projLibDocListTb">
			<%--<fd:dgOptCol title="操作" formatterFunName="formatterLibOpertion"  />--%>
			<fd:dgCol title="{com.glaway.ids.common.lable.code}" field="docNumber" />
			<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="docName"
				formatterFunName="viewProjectDoc1" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.version}" field="version" width="100" align="left"
				sortable="true" formatterFunName="formatterVersion" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.path}" field="path" sortable="false" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.creator}" field="createName" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.createTime}" field="createTime"
				formatterFunName="dateFmtYYYYMMDD" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.updater}" field="updateName" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.updateTime}" field="updateTime"
				formatterFunName="dateFmtYYYYMMDD" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.security}" field="securityLevel"
				formatterFunName="formatSecurity" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.bizCurrent}" field="status" formatterFunName="formatStatus" />
		</fd:datagrid>
		</c:if>			
		<c:if test="${opFlag != 1}">
		<fd:datagrid checkbox="true" checked="true" idField="id" fit="true"
			fitColumns="true" checkOnSelect="true" id="projLibDocList"
			pagination="true"
			url="projLibController.do?searchDatagrid&field=id&folderId=${folderId}&projectId=${projectId}"
			toolbar="projLibDocListTb">
			<%--<fd:dgOptCol title="操作" formatterFunName="formatterLibOpertion"  />--%>
			<fd:colOpt title="{com.glaway.ids.pm.project.projectmanager.operate}" width="100">
				<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.projectmanager.update}" iconCls="basis ui-icon-pencil"
					hideOption="hideEdit" onClick="doEdit"
					></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.projectmanager.revise}" iconCls="basis ui-icon-revise"
					hideOption="hideRevise" onClick="doRevise"
					></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.projectmanager.submit}"
					iconCls="basis ui-icon-submitted_approval"
					hideOption="hideApproval" onClick="doApproval"
					></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.projectmanager.delete}" iconCls="basis ui-icon-minus"
					hideOption="hideDelete" onClick="doRemove"
					></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.projectmanager.back}" iconCls="basis ui-icon-return"
					hideOption="hideBack" onClick="doBack"
					></fd:colOptBtn>
			</fd:colOpt>
			<fd:dgCol title="{com.glaway.ids.common.lable.code}" field="docNumber" />
			<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="docName" formatterFunName="viewProjectDoc1" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.version}" field="version" width="60" align="left"
				sortable="true" formatterFunName="formatterVersion" />
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.security}" field="securityLevel"
				formatterFunName="formatSecurity" width="60"/>
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.bizCurrent}" field="status" width="80" formatterFunName="formatStatus" />
		</fd:datagrid>
		</c:if>	
		<fd:dialog id="addDAndSubmitDialog" width="775px" height="530px" beforClose="addProjLibDocOnCancel"
			modal="true" title="{com.glaway.ids.common.btn.create}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addDAndSubmitOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="cancelAddProjLib"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="updateDAndSubmitDialog" width="775px" height="580px" beforClose="updateProjLibDocOnCancel"
			modal="true" title="{com.glaway.ids.pm.project.projectmanager.update}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="updateDAndSubmitDialog"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="cancelUpdateProjLib"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="ReviseAndSubmitDialog" width="775px" height="580px"
			modal="true" title="{com.glaway.ids.pm.project.projectmanager.revise}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="ReviseAndSubmitDialog"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="SubmitDialog" width="760px" height="260px" modal="true"
			title="{com.glaway.ids.pm.project.projectmanager.submit}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="beOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="beCancel"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="openAndSubmitDialog" width="870px" height="500px"
			modal="true" title="{com.glaway.ids.pm.project.projectmanager.docDetail}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="bizProjLibDocDialog" width="1000px"
			height="420px" modal="true" title="{com.glaway.ids.pm.project.projectmanager.viewHistory}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>		
		<fd:dialog id="createLibUpdatePathDialog" width="400px" height="120px" 
			modal="true" title="{com.glaway.ids.pm.config.updatePath}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="createLibUpdatePathDialog"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="docTypeDialogForProjLibDocList" width="800px" height="480px" modal="true" title="{com.glaway.ids.common.deliveryStandard.docType}">
	    	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="docTypeDialogForProjLibDocList"></fd:dialogbutton>
	    	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
    	</fd:dialog>   	
    	<fd:dialog id="createBatchDialog" width="775px" height="450px" 
			modal="true" title="{com.glaway.ids.common.btn.create}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="createBatchOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="projLibDetailDialog" width="750px" height="600px" 
			modal="true" title="文档详情">
			<fd:dialogbutton name="关闭" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
	</div>
</div>


<script src="webpage/com/glaway/ids/pm/project/projectmanager/projLibDocList.js"></script>
<script type="text/javascript">
	var projLibGrid = '#projLibDocList';
	$(document).ready(
			function() {
				debugger;
				if ('${addProjLibDoc}' == 'true' || '${addProjLibDoc}') {
					$('#libCreate').show();
				}

				if ('${deleteProjLibDoc}' == 'true' || '${deleteProjLibDoc}') {
					$('#libDelete').show();
				}

				if ('${updatePathProjLibDoc}' == 'true' || '${updatePathProjLibDoc}') {
					$('#libUpdatePath').show();
				}

				if ("${opFlag}" == '1' || "${isHidProjLibOper}" == "true") {

					$("#projLibOperButton").css('display', 'none');
				}
				/*$('#projectList').datagrid(
						{
							url : 'projLibController.do?searchDatagrid'
									+ '&folderId=' + '${folderId}'
									+ '&projectId=' + '${projectId}'
						});*/
				//if ("${havePower}" == 'true') {
				if ("${list}" == false || "${list}" == 'false') {
					$('#showList').hide();
				}
				debugger;
				if ("${create}" == false || "${create}" == 'false') {
					$('#libCreate').hide();
				}
				if ("${remove}" == false || "${remove}" == 'false') {
					$('#libDelete').hide();
				}
				/*$('#projectList').datagrid('hideColumn','');*/
				//}
				/* if ("${havePower}" == 'false') {
						if ("${isCreate}" == 'true') {

						} else {
							$('#libDelete').hide();
						}
					} */
			});

	//自定义行显示
	function formatterLibOpertion(val, row, value) {
		//var currentUserId = '${currentUserId}';
		var returnStr = "";

		if (row.status == "nizhi"
				&& ("${opFlag}" == null || "${opFlag}" == '')) {
			//returnStr +="<img src='plug-in/easyui/themes/icons/image_edit.png'  id='ok'  title='修改' onclick='createaddwindow(\"修改\",\"projLibController.do?goUpdate&docId="+ row.id+"\")'></img> ";
			returnStr += "<a title='修改' onclick='createaddwindow(\"修改\",\"projLibController.do?goUpdate&docId="
					+ row.id
					+ "\")' ><span class=\"basis ui-icon-pencil\" title=\"修改\">&nbsp;&nbsp;&nbsp;&nbsp;</span> </a>&nbsp;";
		}
		if (row.status == "guidang"
				&& ("${opFlag}" == null || "${opFlag}" == '')) {
			//returnStr +="<img src='plug-in/easyui/themes/icons/image_edit.png'  id='ok'  title='修订' onclick='createaddwindow(\"修订\",\"projLibController.do?goRevise&docId="+ row.id+"\")'></img> ";
			returnStr += "<a title='修订' onclick='createaddwindow(\"修订\",\"projLibController.do?goRevise&docId="
					+ row.id
					+ "\")' ><span class=\"basis ui-icon-revise\" title=\"修订\">&nbsp;&nbsp;&nbsp;&nbsp;</span> </a>&nbsp;";
		}
		if (row.status == "nizhi"
				&& ("${opFlag}" == null || "${opFlag}" == '')) {
			//returnStr +="<img src='plug-in/easyui/themes/icons/image_edit.png'  id='ok'  title='提交审批' onclick='submitToApprove(\""+ row.id +"\",\""+ row.docNumber +"\",\""+ row.docName +"\",\"projLibController.do?submitToApprove\")'></img> ";
			returnStr += "<a title='提交审批' onclick='submitToApprove(\""
					+ row.id
					+ "\",\""
					+ row.docNumber
					+ "\",\""
					+ row.docName
					+ "\",\"projLibController.do?submitToApprove\")' ><span class=\"basis ui-icon-submitted_approval\" title=\"提交审批\">&nbsp;&nbsp;&nbsp;&nbsp;</span> </a>&nbsp;";
		}
		if (row.status == "nizhi" || row.status == "guidang") {
			//returnStr +="<img src='plug-in/easyui/themes/icons/edit_remove.png'  id='ok'  title='删除' onclick='doFunction(\""+ row.id +"\",\"projLibController.do?doBatchDel\",\"确定删除此文档吗?\")'></img> ";
			returnStr += "<a title='删除' onclick='doFunction(\""
					+ row.id
					+ "\",\"projLibController.do?doBatchDel\",\"确定删除此文档吗?\")' ><span class=\"basis ui-icon-minus\" title=\"删除\">&nbsp;&nbsp;&nbsp;&nbsp;</span> </a>&nbsp;";
		}
		if (row.status == "nizhi"
				&& ("${opFlag}" == null || "${opFlag}" == '')) {
			//returnStr +="<img src='plug-in/easyui/themes/icons/edit_remove.png'  id='ok'  title='回退' onclick='doFunction(\""+ row.id +"\",\"projLibController.do?doBackVersion\",\"确定回退此文档吗?\")'></img> ";
			returnStr += "<a title='回退' onclick='doFunction(\""
					+ row.id
					+ "\",\"projLibController.do?doBackVersion\",\"确定回退此文档吗?\")' ><span class=\"basis ui-icon-back\" title=\"回退\">&nbsp;&nbsp;&nbsp;&nbsp;</span> </a>&nbsp;";
		}
		return returnStr;
	}

	function doFunction(id, url, tipMsg) {
		var rows = $('' + projLibGrid).datagrid('getRows');
		var index = $('' + projLibGrid).datagrid('getRowIndex', id);
		var templates = new Array();
		templates.push(rows[index]);
		doExcute(url, tipMsg, templates);
	}

	function submitToApprove(id, docNumber, docName, docTypeId, tipMsg) {
		debugger;
		$.ajax({
			url : 'projLibController.do?submitProcess',
			type : 'post',
			data : {
				id : id,
				entityName : '${entityName}',
				businessType : '${businessType}'+docTypeId,
				docName : docName
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					$('#SubmitDialog').lhgdialog(
							"open",
							"url:"
							+ 'commonFlowController.do?getDynamicForm&taskNumber='+ id
							+ '&entityName=${entityName}'
							+ '&businessType='+d.obj);
				} else {
					top.tip(d.msg);
				}
			}
		});
	}

	function hideEdit(row, index) {
		debugger;
		if (row.update == false || row.update == 'false') {
			return true;
		}
		if (row.status == "nizhi"
				&& ("${opFlag}" == null || "${opFlag}" == '')
				&& "${isHidProjLibOper}" == "false" && ('${updateProjLibDoc}' == 'true' || '${updateProjLibDoc}')) {
			return false;
		} else {
			return true;
		}
	}

	function hideRevise(row, index) {
		if (row.revise == false || row.revise == 'false') {
			return true;
		}

		if (row.status == "guidang"
				&& ("${opFlag}" == null || "${opFlag}" == '')
				&& "${isHidProjLibOper}" == "false"  && ('${reverseProjLibDoc}' == 'true' || '${reverseProjLibDoc}')) {
			if (row.operStatus == 'reject') {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	function hideApproval(row, index) {
		if (row.approve == false || row.approve == 'false') {
			return true;
		}

		if (row.status == "nizhi"
				&& ("${opFlag}" == null || "${opFlag}" == '')
				&& "${isHidProjLibOper}" == "false" && ('${approveProjLibDoc}' == 'true' || '${approveProjLibDoc}')) {
			return false;

		} else {
			return true;
		}
	}

	function hideDelete(row, index) {
		//if ("${havePower}" == 'true') {
		if (row.remove == false || row.remove == 'false') {
			return true;
		}
		//}
		/* if ("${havePower}" == 'false') {
				if ("${isCreate}" == 'true') {

				} else {
					return true;
				}
			} */

		if ((row.status == "nizhi")
				&& ("${opFlag}" == null || "${opFlag}" == '')
				&& "${isHidProjLibOper}" == "false"  && ('${deleteProjLibDoc}' == 'true' || '${deleteProjLibDoc}')) {
			return false;
		} else {
			return true;
		}
	}

	function hideBack(row, index) {
		//if ("${havePower}" == 'true') {
		if (row.rollback == false) {
			return true;
		}
		//}
		/* if ("${havePower}" == 'false') {
				if ("${isCreate}" == 'true') {

				} else {
					return true;
				}
			} */

		if (row.status == "nizhi"
				&& ("${opFlag}" == null || "${opFlag}" == '')
				&& "${isHidProjLibOper}" == "false") {
			if (row.operStatus == 'reject') {
				return true;
			} else {
				if('${deleteProjLibDoc}' == 'true' || '${deleteProjLibDoc}'){
					return false;
				}else{
					return true;
				}

			}
		} else {
			return true;
		}
	}

	function doEdit(id,index) {
		var url = "projLibController.do?doCheckLastVersion";
		$.ajax({
			url : url,
			type : 'post',
			data : {
				'id' : id
			},
			cache : false,
			async : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var row = $('#projLibDocList').datagrid('getData').rows[index];
					createaddwindowAdd("修改", "projLibController.do?goUpdate&folderId=${folderId}&projectId=${projectId}&docId="
							+ id+'&upload='+row.upload+'&download='+row.download+'&operStatus='+row.operStatus, "", "350");
				} else {
					tip("当前文档不是最新版本，请及时刷新页面");
				}
			}
		});
	}

	function doRevise(id,index) {
		var url = "projLibController.do?doCheckLastVersion";
		$.ajax({
			url : url,
			type : 'post',
			data : {
				'id' : id
			},
			cache : false,
			async : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var row = $('#projLibDocList').datagrid('getData').rows[index];
					createaddwindow("修订", "projLibController.do?goRevise&folderId=${folderId}&projectId=${projectId}&docId=" + id+'&upload='+row.upload+'&download='+row.download);
				} else {
					tip("当前文档不是最新版本，请及时刷新页面");
				}
			}
		});


	}

	function doApproval(id, index) {
		var row = $('#projLibDocList').datagrid('getData').rows[index];
		submitToApprove(row.id, row.docNumber, row.docName,
				row.docTypeId);
	}

	function doRemove(id, index) {
		doFunction(
				id,
				"projLibController.do?doBatchDel",
				'<spring:message code="com.glaway.ids.pm.project.projectmanager.library.doc.confirmDel"/>');
	}

	function doBack(id, index) {
		var url = "projLibController.do?doCheckBackVersion";
		$
				.ajax({
					url : url,
					type : 'post',
					data : {
						'id' : id
					},
					cache : false,
					async : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							doFunction(
									id,
									"projLibController.do?doBackVersion",
									'<spring:message code="com.glaway.ids.pm.project.projectmanager.library.doc.confirmRollback"/>');
						} else {
							var msg = d.msg;
							tip(msg);
							reload();
						}
					}
				});

	}

	function searchProjLibDoc() {
		var projLibDocCreator = $('#projLibDocCreator').val();
		var projLibDocModifor = $('#projLibDocModifor').val();

		/*
        var queryParams = $('#projLibDocList').datagrid('options').queryParams;
        queryParams['RepFile.bizCurrent'] = '';
        queryParams['RepFile.securityLevel'] = '';
        $('#searchProjLibDocTag').find('*').each(function() {
            if($(this).attr('name') == 'RepFile.bizCurrent'){
                queryParams[$(this).attr('name')] += ","+ $(this).val();
            }else if($(this).attr('name') == 'RepFile.bizCurrent_condition'){
                queryParams[$(this).attr('name')] = 'in';
            }else if($(this).attr('name') == 'RepFile.securityLevel'){
                queryParams[$(this).attr('name')] += ","+ $(this).val();
            }else if($(this).attr('name') == 'RepFile.securityLevel_condition'){
                queryParams[$(this).attr('name')] = 'in';
            }else{
                queryParams[$(this).attr('name')] = $(this).val();
            }
        });*/

		var queryParams = $("#projLibDocList").fddatagrid('queryParams',
				"searchProjLibDocTag");
		queryParams["projLibDocCreator"] = projLibDocCreator;
		queryParams["projLibDocModifor"] = projLibDocModifor;

		queryParams['bizCurrent'] = '';
		queryParams['securityLevel'] = '';

		$('#searchProjLibDocTag').find('*').each(function() {
			if ($(this).attr('name') == 'RepFile.bizCurrent') {
				queryParams["bizCurrent"] += "," + $(this).val();
			}
			if ($(this).attr('name') == 'RepFile.securityLevel') {
				queryParams["securityLevel"] += "," + $(this).val();
			}

		});

		//var url=encodeURI('projLibController.do?searchDatagrid&projLibDocCreator='+projLibDocCreator+'&projLibDocModifor='+projLibDocModifor+'&folderId='+'${folderId}'+'&projectId='+'${projectId}');
		//url=encodeURI(url);
		var url = 'projLibController.do?searchDatagrid' + '&folderId='
				+ '${folderId}' + '&projectId=' + '${projectId}';
		$('#projLibDocList').datagrid({
			url : url,
			pageNumber : 1,
			queryParams : queryParams
		});

		$('#projLibDocList').datagrid('unselectAll');
		$('#projLibDocList').datagrid('clearSelections');
		$('#projLibDocList').datagrid('clearChecked');
	}

	function searchProjLibDocBackUp() {
		var searchObjArr = $('#searchProjLibDocForm').serializeArray();
		var searchParam = new Object();
		var projLibDocCreator = $('#projLibDocCreator').val();
		var projLibDocModifor = $('#projLibDocModifor').val();
		if (searchObjArr != null && searchObjArr.length > 0) {
			for (var i = 0; i < searchObjArr.length; i++) {
				searchParam[searchObjArr[i].name] = searchObjArr[i].value;
				if (searchObjArr[i].name == 'Project.createByInfo.id') {
					searchParam[searchObjArr[i].name + '_condition'] = 'in';
				}
				if (searchObjArr[i].name == 'Project.epsInfo.id') {
					searchParam[searchObjArr[i].name + '_condition'] = 'in';
				}
			}
		}
		var url = encodeURI('projLibController.do?searchDatagrid&projLibDocCreator='
				+ projLibDocCreator
				+ '&projLibDocModifor='
				+ projLibDocModifor
				+ '&folderId='
				+ '${folderId}'
				+ '&projectId=' + '${projectId}');
		url = encodeURI(url);
		$('#projLibDocList').datagrid({
			url : url,
			queryParams : searchParam
		});
	}

	//重置
	function tagSearchResetProjLibDoc() {
		$('#searchProjLibDocNumber').textbox("clear");
		$('#searchProjLibDocName').textbox("clear");
		$('#projLibDocCreator').textbox("clear");
		//$('#searchProjLibDocCreateTime').datebox("clear");
		$("#searchProjLibDocCreateTime_BeginDate").datebox("clear");
		$("#searchProjLibDocCreateTime_EndDate").datebox("clear");
		$('#projLibDocModifor').textbox("clear");
		//$('#searchProjLibDocModifyTime').datebox("clear");
		$("#searchProjLibDocModifyTime_BeginDate").datebox("clear");
		$("#searchProjLibDocModifyTime_EndDate").datebox("clear");
		$('#searchSecurityLevel').combobox("clear");
		$('#repFileBizCurrent').combobox("clear");
		$('#addDeliveryStandard_docTypeName').textbox("clear");
		$('#addDeliveryStandard_docTypeId').val("");
		var attributeIds = $('#attributeIds').val();
		if(attributeIds != null && attributeIds != '' && attributeIds != undefined){
			var ss = attributeIds.split(";");
			for(var i=0; i<ss.length; i++) {
				$("#"+ss[i]).textbox("clear");
			}
			$("#userDefinedInfo").empty();
		}
	}

	function createaddwindow(title, url, width, height) {
		width = width ? width : 750;
		height = height ? height : 400;
		if (width == "100%" || height == "100%") {
			width = document.body.offsetWidth;
			height = document.body.offsetHeight - 100;
		}
		if (title == '新增') {

			createDialog('addDAndSubmitDialog', url);

		} else if(title == '修改'){
			createDialog('updateDAndSubmitDialog', url);
		}else{
			createDialog('ReviseAndSubmitDialog', url);
		}

	}

	function createaddwindowAdd(title, url, width, height) {
		var upload = "true";
		var download = "true";
		//if ("${havePower}" == 'true') {
		if ("${upload}" == false || "${upload}" == 'false') {
			upload = "false";
		}
		if ("${download}" == false || "${download}" == 'false') {
			download = "false";
		}
		//}
		/* if ("${havePower}" == 'false') {
				if ("${isCreate}" == 'true') {
					upload = "true";
					download = "true";
				}
			} */
		url = url + "&upload=" + upload + "&download=" + download;
		width = width ? width : 750;
		height = height ? height : 400;
		if (width == "100%" || height == "100%") {
			width = document.body.offsetWidth;
			height = document.body.offsetHeight - 100;
		}
		if (title == '新增') {

			createDialog('addDAndSubmitDialog', url);

		} else {
			createDialog('updateDAndSubmitDialog', url);
		}

	}

	//批量新增：
	function createBatch(title, url, width, height) {
		createDialog('createBatchDialog', url);
	}

	function createBatchOk(iframe) {
		iframe.saveDoc(iframe);
		return false;
	}



	function createLibUpdatePath(title, url, width, height){
		var flag = true;
		var ids = [];
		var names = [];
		var rows = $("#projLibDocList").datagrid('getSelections');
		if (rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}

			url = url+"&repFileIds=" + ids;
			createDialog('createLibUpdatePathDialog', url);

		} else {
			top.tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.selectUpdatePath"/>');
		}

	}

	function createLibUpdatePathDialog(iframe){
		iframe.saveProjLibFolderUpdatePath();
		return false;
	}


	function createdetailwindow2(title, url, width, height) {
		width = width ? width : 700;
		height = height ? height : 400;
		if (width == "100%" || height == "100%") {
			width = document.body.offsetWidth;
			height = document.body.offsetHeight - 100;
		}
		if (typeof (windowapi) == 'undefined') {
			createDialog('openAndSubmitDialog', url);
		} else {
		}

	}

	function viewProjectDoc1(val, row, value) {
		var path = "<a title='查看' style='color:blue' onclick='openProjectDoc1(\""+ row.id+ "\",\""+ row.docName+ "\",\""+ row.download+"\",\""+ row.history+"\")'>"
				+ row.docName
				+ "</a>"
		//if ("${havePower}" == 'true') {
		if (row.detail == false || row.detail == 'false') {
			path = row.docName;
		}
		//}
		/* if("${havePower}" == 'false'){
				if("${isCreate}" == 'true'){

				}else{

					path = row.docName;
				}
			} */
		return path;
	}

	function openProjectDoc1(id, name,download,history) {
		//if ("${havePower}" == 'true') {
		if (download == false || download == 'false') {
			download = "false";
		}
		//}
		/* if ("${havePower}" == 'false') {
				if ("${isCreate}" == 'true') {
					download = "true";
				}
			} */
		//if ("${havePower}" == 'true') {
		if (history == false || history == 'false') {
			history = "false";
		}
		//}
		/* if ("${havePower}" == 'false') {
				if ("${isCreate}" == 'true') {
					history = "true";
				}
			} */
		var url = "projLibController.do?viewProjectDocDetail&id=" + id
				+ "&download=" + download + "&history=" + history;
		createDialog('projLibDetailDialog', url);
//		$("#projLibDetailDialog").lhgdialog("open",url);
	}

	function addDAndSubmitOk(iframe) {
		var result = false;
		result = saveDoc(iframe);
		reload();
		//searchProjLibDoc();
		return result;
	}
	function updateDAndSubmitDialog(iframe) {
		var result = false;
		result = saveUpdatDoc(iframe);
		reload();
		return result;
	}
	function ReviseAndSubmitDialog(iframe) {
		var result = false;
		result = saveUpdatDoc(iframe);
		reload();
		return result;
	}

	function SubmitOk(iframe) {
		var result = false;
		iframe.sumbitDocApprove();
		reload();
		/* setTimeout("reload()" , 1000); */
		return result;
	}
	function getSelectionData() {
		var chs = $(projLibGrid).datagrid("getChecked");
		var datas = new Array();
		if (chs == null || chs.length == 0) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.doc.selectDoc"/>');
			return;
		}
		for (var i = 0; i < chs.length; i++) {
			var data = chs[i];
			datas.push(data);
		}
		return datas;
	}
	function doController(title, url, tipMsg) {
		var datas = getSelectionData();
		var rows = $("#projLibDocList").datagrid('getSelections');
		for (var i = 0; i < datas.length; i++) {
			if (datas[i].status != 'nizhi') {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.onlyDeleteEditing"/>');
				return;
			}
		}
		if (datas == null || datas == 'undefined') {
			return datas;
		} else {
			doExcute(url, tipMsg, datas);
		}

	}

	function formatterVersion(val, row, index) {
		if(row.history == true){
			return '<span style="cursor:hand"><a onclick="showBizVersion(\''
					+ row.id + '\')" style="color:blue;">' + val
					+ '</a></span>';
		}else{
			return val;
		}

	}

	function showBizVersion(id) {
		url = 'projLibController.do?goVersionHistory&id=' + id;
		createDialog('bizProjLibDocDialog', url);
	}


	function cancelAddProjLib(iframe){
		var invalidIds = iframe.$("#invalidIds").val();
		var delUrl='projLibController.do?deleteProjLibJackrabbitFile&type='+'addCancel';
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

	function cancelUpdateProjLib(iframe){
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

	function addProjLibDocOnCancel(){
		var projLibDocDialog = $('#addDAndSubmitDialog').lhgdialog("getWin");
		projLibDocDialog.addProjLibDocOnCancel1();
	}

	function updateProjLibDocOnCancel(){
		var projLibDocDialog = $('#updateDAndSubmitDialog').lhgdialog("getWin");
		projLibDocDialog.updateProjLibDocOnCancel1();
	}

	/**
	 * 选择文档类型页面
	 */
	function selectDocTypeForProjLibDocList() {
		var url = 'projLibController.do?goDocType';
		createDialog('docTypeDialogForProjLibDocList',url);
	}

	function docTypeDialogForProjLibDocList(iframe) {
		iframe.getSelectedDocRowAndShowInfo();
		var rowLength = iframe.$("#repFileTypeConfigList").datagrid('getSelections').length;
		if(rowLength>0){
			iframe.colseSelectForPage();
		}
		return false;
	}

	//显示自定义属性：
	function userDefinedInfo(id){
		$("#userDefinedInfo").load("projLibController.do?goShowUserDefinedInfoView&id="+id);
	}

	function beOk(iframe) {
		var inputs = iframe.document.getElementsByTagName("input");
		if(inputs.length>0){
			for(var i=0;i<inputs.length;i++){
				if(inputs[i].name.indexOf("leader")>=0) {
					var leader = iframe.$('input[name='+inputs[i].name+']').val();
					if(leader == null || leader == ''){
						top.tip("所有选择不能为空");
						return false;
					}
				}
			}
		}

		var options = {
			submitCallback : function() {
				refreshDatagrid();
				setTimeout(function() {
					$.fn.lhgdialog("closeAll")
				}, 100);
			}
		}
		setTimeout(function() {
			iframe.submitForm(options);
		}, 100)
		return false;

	}


	function beCancel(iframe) {
		refreshDatagrid();
		setTimeout(function() {
			$.fn.lhgdialog("closeAll")
		}, 100)
	}

	function beClose() {
		refreshDatagrid()
		setTimeout(function() {
			$.fn.lhgdialog("closeAll")
		}, 100)
		return true;
	}

	function refreshDatagrid() {
		$('#projLibDocList').datagrid('unselectAll');
		$('#projLibDocList').datagrid('reload');
	}
</script>