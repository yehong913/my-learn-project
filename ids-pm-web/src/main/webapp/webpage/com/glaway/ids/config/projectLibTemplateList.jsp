<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
<body>
<div class="easyui-layout" fit="true">
	<div region="center">
		<div id="projectLibTemplateTool">
			<fd:form id="searchprojectLibTemplateForm">
				<fd:searchform id="searchprojectLibTemplateSearchForm"
							   onClickSearchBtn="searchProjectLibTemplate();"
							   onClickResetBtn="searchFormReset();" isMoreShow="true"
							   help="helpDoc:TemplatesOfProjectLibraryPermiss">
					<fd:inputText id="projectLibTemplateName"
								  name="ProjectLibTemplate.name" title="{com.glaway.ids.common.lable.name}" queryMode="like"></fd:inputText>
					<fd:inputText id="projectLibTemplateCreator"
								  name="ProjectLibTemplate.creator" title="{com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePerson}" queryMode="like"></fd:inputText>
					<fd:inputDateRange id="projectLibTemplateCreateTime" interval="0"
									   title="{com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateTime}" name="ProjectLibTemplate.createTime" opened="0"></fd:inputDateRange>
					<fd:combobox id="projectLibTemplateStatus" title="{com.glaway.ids.common.lable.status}"
								 name="ProjectLibTemplate.status" queryMode="like" editable="false">
						<fd:selectItem itemLabel="全部" itemValue="" selected="true" />
						<fd:selectItem itemLabel="启用" itemValue="1" />
						<fd:selectItem itemLabel="禁用" itemValue="0" />
					</fd:combobox>
				</fd:searchform>
			</fd:form>

			<fd:toolbar>
				<fd:toolbarGroup align="left">
					<fd:linkbutton onclick="addProjectLibTemplate()" value="{com.glaway.ids.common.btn.create}"
								   iconCls="l-btn-icon basis ui-icon-plus"
								   operationCode="projectLibTemplateAdd" />
					<fd:linkbutton
							onclick="deleteProjectLibTemplates('批量删除','projectLibTemplateController.do?doDelete','projectLibTemplateTreeTable')"
							value="{com.glaway.ids.common.btn.remove}" iconCls="l-btn-icon basis ui-icon-minus"
							operationCode="projectLibTemplateBatchDel" />
					<fd:linkbutton onclick="startOrStopProjectLibTemplate('1')"
								   value="{com.glaway.ids.common.btn.start}" iconCls="basis ui-icon-enable"
								   operationCode="projectLibTemplateBatchStart" />
					<fd:linkbutton onclick="startOrStopProjectLibTemplate('0')"
								   value="{com.glaway.ids.common.btn.stop}" iconCls="basis ui-icon-forbidden"
								   operationCode="projectLibTemplateBatchStop" />
				</fd:toolbarGroup>
			</fd:toolbar>
		</div>

		<fd:datagrid id="projectLibTemplateDatagrid" checkbox="true"
					 fitColumns="true" toolbar="#projectLibTemplateTool" pagination="true"
					 singleSelect="false"
					 url="projectLibTemplateController.do?searchDatagrid" idField="id"
					 fit="true">
			<fd:colOpt title="{com.glaway.ids.common.lable.operation}" width="60">
				<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.modify}" iconCls="basis ui-icon-pencil"
							  onClick="updateLine" hideOption="hideProjectLibTemplateUpdate"
							  operationCode="projectLibTemplateUpdate"></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.remove}" iconCls="basis ui-icon-minus"
							  hideOption="hideProjectLibTemplateDelete" onClick="deleteLine"
							  operationCode="projectLibTemplateDelete"></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.start}" iconCls="basis ui-icon-enable"
							  onClick="startLine" hideOption="hideProjectLibTemplateStart"
							  operationCode="projectLibTemplateStart"></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.stop}" iconCls="basis ui-icon-forbidden"
							  onClick="stopLine" hideOption="hideProjectLibTemplateStop"
							  operationCode="projectLibTemplateStop"></fd:colOptBtn>
			</fd:colOpt>
			<fd:dgCol title="{com.glaway.ids.pm.project.plantemplate.name}" field="name"
					  formatterFunName="formatterShowName" />
			<fd:dgCol title="{com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePerson}" field="creator"
					  formatterFunName="formatterCreator" />
			<fd:dgCol title="{com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateTime}" field="createTime"
					  formatterFunName="formatterTime" />
			<fd:dgCol title="{com.glaway.ids.common.lable.status}" field="status"
					  formatterFunName="formatterStatus" />
			<fd:dgCol title="{com.glaway.ids.common.lable.remark}" field="remark" width="300" />
		</fd:datagrid>
		<fd:dialog id="addProjectLibTemplateDialog" width="800px"
				   height="220px" modal="true" title="{com.glaway.ids.common.btn.create}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="projectLibTemplateOkFunction"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="updateProjectLibTemplateDialog" width="800px"
				   height="220px" modal="true" title="{com.glaway.ids.common.btn.modify}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="projectLibTemplateOkFunction"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="showProjectLibTemplateDetailDialog" width="1100px"
				   height="600px" modal="true" title="{com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateView}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
	</div>
</div>
</body>
<script type="text/javascript">
	function hideProjectLibTemplateUpdate(row, index) {
		if (row.status == '0') {
			return true;
		}
	}

	function deleteProjectLibTemplates() {
		var rows = $("#projectLibTemplateDatagrid").datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			top.Alert.confirm(
					'<spring:message code="com.glaway.ids.common.confirmBatchDel"/>',
					function(r) {
						if (r) {
							for (var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
							}
							$
									.ajax({
										url : 'projectLibTemplateController.do?doDelete',
										type : 'post',
										data : {
											ids : ids.join(',')
										},
										cache : false,
										success : function(data) {
											var d = $.parseJSON(data);
											if (d.success) {
												var msg = d.msg;
												tip(msg);
												reloadProjectLibTemplateDatagrid();
											}
										}
									});
						}
					});
		} else {
			tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
		}

	}

	function deleteLine(id, index) {
		top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateNeedByDel"/>', function(r) {
			if (r) {
				$.ajax({
					url : 'projectLibTemplateController.do?doDelete',
					type : 'post',
					data : {
						ids : id
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadProjectLibTemplateDatagrid();
						}
					}
				});

			}
		});
	}

	function startOrStopProjectLibTemplate(type) { //type = 0 表示禁用 /1 表示启用
		var rows = $("#projectLibTemplateDatagrid").datagrid('getSelections');
		var showType = '';
		if (type == '1') {
			showType = '<spring:message code="com.glaway.ids.common.start"/>';
		} else {
			showType = '<spring:message code="com.glaway.ids.common.stop"/>';
		}
		if (rows.length == 0) {
			top
					.tip('<spring:message code="com.glaway.ids.common.selectOperate"/>');
			return;
		}
		var currentType = '';
		var currentShowType = '';
		if (showType == '<spring:message code="com.glaway.ids.common.start"/>') {
			currentType = '0';
			currentShowType = '<spring:message code="com.glaway.ids.common.stop"/>';
		} else if (showType == '<spring:message code="com.glaway.ids.common.stop"/>') {
			currentType = '1';
			currentShowType = '<spring:message code="com.glaway.ids.common.start"/>';
		}
		if (rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				if (rows[i].status == type) {
					tip('<spring:message code="com.glaway.ids.common.operateLimit" arguments="' + currentShowType + ',' + showType + '"/>');
					return;
				}
			}
			var ids = [];
			top.Alert.confirm(
					'<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + showType + '"/>',
					function(r) {
						if (r) {
							for (var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
							}
							$
									.ajax({
										url : 'projectLibTemplateController.do?doStartOrStop',
										type : 'post',
										data : {
											ids : ids.join(","),
											status : type
										},
										cache : false,
										success : function(data) {
											var d = $.parseJSON(data);
											if (d.success) {
												var msg = d.msg;
												top.tip(msg);
												reloadProjectLibTemplateDatagridNew();
											}
										}
									});
						}
					});
		}
	}

	function startLine(id, index) {
		debugger;
		/* $('#projectLibTemplateDatagrid').datagrid('selectRow', index);
		var rows = $('#projectLibTemplateDatagrid').datagrid('getSelections');
		if(rows.length>1){
			top.Alert.confirm('<spring:message code="com.glaway.ids.pm.project.plantemplate.checkOperationRows"/>');
			return;
		}
		var row = rows[rows.length - 1]; */
		top.Alert.confirm(
				'<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="启用"/>',
				function(r) {
					if (r) {
						$
								.ajax({
									url : 'projectLibTemplateController.do?doStartOrStop',
									type : 'post',
									data : {
										ids : id,
										status : '1'
									},
									cache : false,
									success : function(data) {
										var d = $.parseJSON(data);
										if (d.success) {
											var msg = d.msg;
											tip(msg);
											reloadProjectLibTemplateDatagridNew();
										}
									}
								});
					}
				});
	}

	function stopLine(id, index) {
		/* $('#projectLibTemplateDatagrid').datagrid('selectRow', index);
		var rows = $('#projectLibTemplateDatagrid').datagrid('getSelections');
		var row = rows[rows.length - 1]; */
		top.Alert.confirm(
				'<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="禁用"/>',
				function(r) {
					if (r) {
						$
								.ajax({
									url : 'projectLibTemplateController.do?doStartOrStop',
									type : 'post',
									data : {
										ids : id,
										status : '0'
									},
									cache : false,
									success : function(data) {
										var d = $.parseJSON(data);
										if (d.success) {
											var msg = d.msg;
											tip(msg);
											reloadProjectLibTemplateDatagrid();
										}
									}
								});
					}
				});
	}

	function updateLine(id, index) {
		var url = 'projectLibTemplateController.do?goUpdate&templateId=' + id;
		createDialog('updateProjectLibTemplateDialog', url);
	}

	function formatterTime(value, row, index) {
		return dateFmtYYYYMMDD(value);
	}

	/**
	 * 时间格式化 yyyy-MM-dd
	 *
	 * @param date
	 * @returns
	 */
	function dateFmtYYYYMMDD(val) {
		if (val != undefined && val != null && val != '') {
			var now = new Date(val);
			var year = now.getFullYear();
			var month = now.getMonth() + 1;
			if (month < 10) {
				month = "0" + month;
			}
			var day = now.getDate();
			if (day < 10) {
				day = "0" + day;
			}
			return year + '-' + month + '-' + day;
		}
		return val;
	}

	function formatterStatus(value, row, index) {
		var showStatus = '';
		if (value == '1') {
			showStatus = '<spring:message code="com.glaway.ids.common.start"/>';
		} else {
			showStatus = '<spring:message code="com.glaway.ids.common.stop"/>';
		}
		return showStatus;
	}

	function formatterShowName(value, row, index) {
		return '<span style="cursor:hand"><a onclick="showProjectLibTemplateDetail(\''
				+ row.id + '\')" style="color:blue;">' + value + '</a></span>';
	}

	function formatterCreator(value, row, index) {
		return row.createFullName + '-' + row.createName;
	}

	function showProjectLibTemplateDetail(id) {
		var dialogUrl = 'projectLibTemplateController.do?goDetail&templateId='
				+ id;
		createDialog('showProjectLibTemplateDetailDialog', dialogUrl);
	}

	function hideProjectLibTemplateStart(row, index) {
		if (row.status == '1') {
			return true;
		}
	}

	function hideProjectLibTemplateStop(row, index) {
		if (row.status == '0') {
			return true;
		}
	}

	//新增
	function addProjectLibTemplate() {
		createDialog('addProjectLibTemplateDialog',
				'projectLibTemplateController.do?goAdd');
	}

	function projectLibTemplateOkFunction(iframe) {
		saveOrUp(iframe);
		window.setTimeout("reloadProjectLibTemplateDatagrid()", 500);//等待0.5秒,刷新列表
		return false;
	}

	function editProjectLib(id) {
		createDialog('addProjectLibTemplateDialog',
				'projectLibTemplateController.do?goLibMenu&templateId=' + id);
	}

	function reloadProjectLibTemplateDatagrid() {
		$('#projectLibTemplateDatagrid').datagrid('unselectAll');
		$('#projectLibTemplateDatagrid').datagrid("reload");

	}

	function reloadProjectLibTemplateDatagridNew() {
		$('#projectLibTemplateDatagrid').datagrid('unselectAll');
		$('#projectLibTemplateDatagrid').datagrid("reload");
	}

	function searchProjectLibTemplate() {
		var searchParam = $("#projectLibTemplateDatagrid").fddatagrid(
				'queryParams', "searchprojectLibTemplateSearchForm");
		$('#projectLibTemplateDatagrid').datagrid({
			url : 'projectLibTemplateController.do?searchDatagrid',
			queryParams : searchParam
		});
		$('#projectLibTemplateDatagrid').datagrid('unselectAll');
		$('#projectLibTemplateDatagrid').datagrid('clearSelections');
		$('#projectLibTemplateDatagrid').datagrid('clearChecked');
	}

	//重置
	function searchFormReset() {
		$('#projectLibTemplateName').textbox("setValue", "");
		$('#projectLibTemplateCreator').textbox("setValue", "");
		$('#projectLibTemplateStatus').combobox("setValue", "");
		$("#projectLibTemplateCreateTime_BeginDate").datebox("clear");
		$("#projectLibTemplateCreateTime_EndDate").datebox("clear");
	}
</script>
</html>
