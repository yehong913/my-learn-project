<%@ page language="java" import="java.util.*"
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>业务配置管理</title>
	<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
<div id="${param.type}tool">
	<fd:form id="searchConfigForm${param.type}">
		<%--<div style="float:right;margin-right: 10px;">
			<c:if test="${param.type=='PHARSE'}">
				<fd:panel help="helpDoc:ProjectPhase"></fd:panel>
			</c:if>
			<c:if test="${param.type=='PLANLEVEL'}">
				<fd:panel help="helpDoc:PlanLevel"></fd:panel>
			</c:if>
		</div>--%>
		<fd:searchform id="${param.type}seachTag"
					   onClickSearchBtn="searchTestDept('${param.type}')"
					   onClickResetBtn="searchReset('${param.type}')" help="helpDoc:PlanLevel">
			<fd:inputText id="no${param.type}" name="BusinessConfig.no"
						  title="{com.glaway.ids.common.lable.code}" queryMode="like"></fd:inputText>
			<fd:inputText id="name${param.type}" name="BusinessConfig.name"
						  title="{com.glaway.ids.common.lable.name}" queryMode="like"></fd:inputText>
			<fd:combobox id="stopFlag${param.type}" url="epsConfigController.do?epsStatusList" queryMode="in" multiple="false"
						 title="{com.glaway.ids.common.lable.status}" prompt="全部" textField="name" valueField="id"
						 name="NameStandard.stopFlag"  editable="false">

			</fd:combobox>
		</fd:searchform>
	</fd:form>

	<fd:toolbar>
		<fd:toolbarGroup align="left">
			<fd:linkbutton
					onclick="addBusinessConfig('新增','planBusinessConfigController.do?type=${param.type}&goAdd','businessConfigList${param.type}','${param.type}')"
					value="{com.glaway.ids.common.btn.create}"
					iconCls="l-btn-icon basis ui-icon-plus"
					operationCode="BConfigAdd${param.type}" />
			<fd:linkbutton
					onclick="deleteALLSelectConfig('批量删除','planBusinessConfigController.do?doBatchDel&type=${param.type}','businessConfigList${param.type}','${param.type}')"
					value="{com.glaway.ids.common.btn.remove}"
					iconCls="l-btn-icon basis ui-icon-minus"
					operationCode="BConfigDel${param.type}" />
			<fd:linkbutton
					onclick="startOrStopBusinessConfig('启用','planBusinessConfigController.do?doStartOrStop&state=start&type=${param.type}','businessConfigList${param.type}','${param.type}')"
					value="{com.glaway.ids.common.btn.start}"
					iconCls="basis ui-icon-enable"
					operationCode="BConfigSat${param.type}" />
			<fd:linkbutton
					onclick="startOrStopBusinessConfig('禁用','planBusinessConfigController.do?doStartOrStop&state=stop&type=${param.type}','businessConfigList${param.type}','${param.type}')"
					value="{com.glaway.ids.common.btn.stop}"
					iconCls="basis ui-icon-forbidden"
					operationCode="BConfigSto${param.type}" />

			<fd:linkbutton onclick="businessConfigListImport()"
						   value="{com.glaway.ids.common.btn.import}"
						   iconCls="basis ui-icon-import"
						   operationCode="BConfigImport${param.type}" />
			<fd:linkbutton onclick="businessConfigListExport()"
						   value="{com.glaway.ids.common.btn.export}"
						   iconCls="basis ui-icon-export"
						   operationCode="BConfigExport${param.type}" />
		</fd:toolbarGroup>
	</fd:toolbar>
</div>

<fd:datagrid checkbox="true" checked="true" checkOnSelect="true"
			 idField="id" toolbar="${param.type}tool"
			 id="businessConfigList${param.type}" fit="true" fitColumns="true"
			 url="planBusinessConfigController.do?searchDatagrid&type=${param.type}">
	<fd:colOpt title="{com.glaway.ids.common.lable.operation}" width="60">
		<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.modify}"
					  iconCls="basis ui-icon-pencil" onClick="beforeupdateLine"
					  hideOption="hiedeBusinessConfigUpdate"
					  operationCode="BConfigMod${param.type}"></fd:colOptBtn>
		<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.remove}"
					  iconCls="basis ui-icon-minus" onClick="beforedeleteLine"
					  operationCode="BConfigDel${param.type}"></fd:colOptBtn>
		<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.start}"
					  iconCls="basis ui-icon-enable" onClick="beforestartLine"
					  hideOption="hideBusinessConfigStart"
					  operationCode="BConfigSat${param.type}"></fd:colOptBtn>
		<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.stop}"
					  iconCls="basis ui-icon-forbidden" onClick="beforestopLine"
					  hideOption="hideBusinessConfigStop"
					  operationCode="BConfigSto${param.type}"></fd:colOptBtn>
	</fd:colOpt>
	<fd:dgCol title="{com.glaway.ids.common.lable.code}" field="no" />
	<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="name" />
	<fd:dgCol title="{com.glaway.ids.common.lable.status}"
			  field="stopFlag" />
	<fd:dgCol title="{com.glaway.ids.common.lable.remark}"
			  field="configComment" width="300"
			  formatterFunName="formatterBusinessComment" />
</fd:datagrid>

<fd:dialog id="addBusinessConfigDialog" width="800px" height="300px"
		   modal="true" title="{com.glaway.ids.common.btn.create}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
					 callback="addBusinessConfigOkFunction"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
					 callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="updateBusinessConfigDialog" width="800px" height="300px"
		   modal="true" title="{com.glaway.ids.common.btn.modify}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
					 callback="updateLineOkFunction"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
					 callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
<fd:dialog id="importBusinessConfigDialog" width="400px" height="150px"
		   modal="true" title="{com.glaway.ids.common.btn.import}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
					 callback="businessConfigListImportOkFunction" />
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
					 callback="hideDiaLog" />
</fd:dialog>
</div>
</div>


<script type="text/javascript">
	function reloadBusinessConfigTable(type) {
		refreshDatagrid();
		searchTestDept(type);
	}

	function refreshDatagrid() {
		$('#businessConfigList${param.type}').datagrid('unselectAll');
		$('#businessConfigList${param.type}').datagrid('clearSelections');
		$('#businessConfigList${param.type}').datagrid('clearChecked');
		$('#businessConfigList${param.type}').datagrid('reload');
	}

	function hiedeBusinessConfigUpdate(row, index) {
		if (row.stopFlag == '<spring:message code="com.glaway.ids.common.stop"/>') {
			return true;
		} else {
			return false;
		}
	}

	function hideBusinessConfigStart(row, index) {
		if (row.stopFlag == '<spring:message code="com.glaway.ids.common.start"/>') {
			return true;
		} else {
			return false;
		}
	}

	function hideBusinessConfigStop(row, index) {
		if (row.stopFlag == '<spring:message code="com.glaway.ids.common.stop"/>') {
			return true;
		} else {
			return false;
		}
	}

	function formatterBusinessComment(value, row, index) {
		if (value == undefined)
			value = '';
		var shtml;
		shtml = '<span title="'+value+'">' + value + '</span>';
		return shtml;
	}

	function beforeupdateLine(id, index) {
		$.ajax({
			type : "GET",
			url : "planBusinessConfigController.do?getEntityById&id=" + id,
			success : function(data) {
				updateLine(index, data.configType);
			}
		});
	}

	function beforedeleteLine(id, index) {
		debugger;
		$.ajax({
			type : "GET",
			url : "planBusinessConfigController.do?getEntityById&id=" + id,
			success : function(data) {
				deleteLine(index, data.configType);
			}
		});
	}

	function beforestartLine(id, index) {
		$
				.ajax({
					type : "GET",
					url : "planBusinessConfigController.do?getEntityById&id="
							+ id,
					success : function(data) {
						startOrStopLine(index, data.configType,
								'<spring:message code="com.glaway.ids.common.start"/>');
					}
				});
	}

	function beforestopLine(id, index) {
		$
				.ajax({
					type : "GET",
					url : "planBusinessConfigController.do?getEntityById&id="
							+ id,
					success : function(data) {
						startOrStopLine(index, data.configType,
								'<spring:message code="com.glaway.ids.common.stop"/>');
					}
				});
	}

	function operation(value, row, index) {
		var type = row.configType;
		var shtml;
		shtml = '<a onclick="updateLine('
				+ index
				+ ',\''
				+ type
				+ '\')"><span class="basis ui-icon-pencil" title="修改">&nbsp;&nbsp;&nbsp;&nbsp;</span></a></a>'
				+ '<a onclick="deleteLine('
				+ index
				+ ',\''
				+ type
				+ '\')"><span class="basis ui-icon-minus" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></a>';
		if (row.stopFlag == '<spring:message code="com.glaway.ids.common.start"/>') {
			shtml = shtml
					+ '<a onclick="startOrStopLine('
					+ index
					+ ',\''
					+ type
					+ '\',\'禁用\')"><span class="basis ui-icon-forbidden" title="禁用">&nbsp;&nbsp;&nbsp;&nbsp;</span></a>';
		} else {
			shtml = shtml
					+ '<a onclick="startOrStopLine('
					+ index
					+ ',\''
					+ type
					+ '\',\'启用\')"><span class="basis ui-icon-enable" title="启用">&nbsp;&nbsp;&nbsp;&nbsp;</span></a>';
		}
		return shtml;
	}

	function addBusinessConfig(title, url, gname, type) {
		createDialog('addBusinessConfigDialog', url);
	}

	function addBusinessConfigOkFunction(iframe) {
		var type = '${param.type}';
		saveOrUp(iframe);
		//window.setTimeout('reloadBusinessConfigTable(\'' + type + '\')', 500);//等待0.5秒，让分类树结构加载出来
		return false;
	}

	function updateLine(index, type) {
		var row;
		var all = $("#businessConfigList" + type).datagrid('getRows');
		for (var i = 0; i < all.length; i++) {
			if (i == index) {
				row = all[i];
			}
		}

		var title = '修改';
		var url = 'planBusinessConfigController.do?goUpdate&type=' + type;
		if (row.stopFlag == '<spring:message code="com.glaway.ids.common.stop"/>') {
			tip('<spring:message code="com.glaway.ids.common.updateLimit"/>');
			return;
		}
		url += '&id=' + row.id;
		createDialog('updateBusinessConfigDialog', url);
	}

	function updateLineOkFunction(iframe) {
		var type = '${param.type}';
		saveOrUp(iframe);
		/* 			window.setTimeout('reloadBusinessConfigTable(\'' + type + '\')',
                            500);//等待0.5秒，让分类树结构加载出来 */
		return false;
	}

	function deleteLine(index, type) {
		var row;
		var all = $("#businessConfigList" + type).datagrid('getRows');
		for (var i = 0; i < all.length; i++) {
			if (i == index) {
				row = all[i];
			}
		}
		var url = 'planBusinessConfigController.do?doBatchDel&type=' + type;

		top.Alert.confirm(
				'<spring:message code="com.glaway.ids.common.confirmDel"/>',
				function(r) {
					if (r) {
						$
								.ajax({
									url : url,
									type : 'post',
									data : {
										ids : row.id
									},
									cache : false,
									success : function(data) {
										var d = $.parseJSON(data);
										if (d.success) {
											var msg = d.msg;
											tip(msg);
											reloadBusinessConfigTable('${param.type}');
										}
									}
								});
					}
				});
	}

	function startOrStopLine(index, type, flag) {
		var row;
		var all = $("#businessConfigList" + type).datagrid('getRows');
		for (var i = 0; i < all.length; i++) {
			if (i == index) {
				row = all[i];
			}
		}
		var url = '';
		if (flag == '<spring:message code="com.glaway.ids.common.start"/>') {
			url = 'planBusinessConfigController.do?doStartOrStop&state=start&type='
					+ type;
		} else {
			url = 'planBusinessConfigController.do?doStartOrStop&state=stop&type='
					+ type;
		}

		if (flag == '<spring:message code="com.glaway.ids.common.start"/>'
				&& row.stopFlag == '<spring:message code="com.glaway.ids.common.start"/>') {
			tip('<spring:message code="com.glaway.ids.common.onlyStartStopped"/>');
			return;
		}
		if (flag == '<spring:message code="com.glaway.ids.common.stop"/>'
				&& row.stopFlag == '<spring:message code="com.glaway.ids.common.stop"/>') {
			tip('<spring:message code="com.glaway.ids.common.onlyStopStarted"/>');
			return;
		}

		top.Alert.confirm(
				'<spring:message code="com.glaway.ids.common.confirmOperate" arguments="' + flag + '"/>',
				function(r) {
					if (r) {
						$
								.ajax({
									url : url,
									type : 'post',
									data : {
										ids : row.id
									},
									cache : false,
									success : function(data) {
										var d = $.parseJSON(data);
										if (d.success) {
											var msg = d.msg;
											tip(msg);
											reloadBusinessConfigTable('${param.type}');
										}
									}
								});
					}
				});
	}

	//查询
	function searchTestDept(type) {
		var searchObjArr = $('#searchConfigForm' + type).serializeArray();
		var searchParam = new Object();
		if (searchObjArr != null && searchObjArr.length > 0) {
			for (var i = 0; i < searchObjArr.length; i++) {
				searchParam[searchObjArr[i].name] = searchObjArr[i].value;
			}
		}
		$('#businessConfigList' + type).datagrid({
			url : 'planBusinessConfigController.do?searchDatagrid&type=' + type,
			queryParams : searchParam
		});
		$('#businessConfigList${param.type}').datagrid('unselectAll');
		$('#businessConfigList${param.type}').datagrid('clearSelections');
		$('#businessConfigList${param.type}').datagrid('clearChecked');
	}

	//重置
	function searchReset(type) {
		$('#no' + type).textbox("setValue", "");
		$('#name' + type).textbox("setValue", "");
		$('#stopFlag' + type).combobox("clear");
	}

	function updateConfig(title, url, id, width, height) {
		var type = '${param.type}';
		gridname = id;
		var rowsData = $('#' + id).datagrid('getSelections');
		if (!rowsData || rowsData.length == 0) {
			tip('<spring:message code="com.glaway.ids.common.config.selectEdit"/>');
			return;
		}
		if (rowsData.length > 1) {
			tip('<spring:message code="com.glaway.ids.common.config.selectOneEdit"/>');
			return;
		}
		if (rowsData[0].stopFlag == '<spring:message code="com.glaway.ids.common.stop"/>') {
			tip('<spring:message code="com.glaway.ids.common.updateLimit"/>');
			return;
		}

		url += '&id=' + rowsData[0].id;
		createwindow(title, url, width, height);
	}

	function deleteALLSelectConfig(title, url, gname, type) {
		gridname = gname;
		var ids = [];
		var rows = $("#" + gname).datagrid('getSelections');
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
										url : url,
										type : 'post',
										data : {
											ids : ids.join(',')
										},
										cache : false,
										success : function(data) {
											var d = $
													.parseJSON(data);
											if (d.success) {
												var msg = d.msg;
												tip(msg);
												reloadBusinessConfigTable('${param.type}');
											}
										}
									});
						}
					});
		} else {
			tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
		}
	}

	function startOrStopBusinessConfig(title, url, gname, type) {
		gridname = gname;
		var ids = [];
		var rows = $("#" + gname).datagrid('getSelections');
		if (rows.length > 0) {
			for (var i = 0; i < rows.length; i++) {
				if (title == '<spring:message code="com.glaway.ids.common.start"/>'
						&& rows[i].stopFlag == '<spring:message code="com.glaway.ids.common.start"/>') {
					tip('<spring:message code="com.glaway.ids.common.onlyStartStopped"/>');
					return;
				}
				if (title == '<spring:message code="com.glaway.ids.common.stop"/>'
						&& rows[i].stopFlag == '<spring:message code="com.glaway.ids.common.stop"/>') {
					tip('<spring:message code="com.glaway.ids.common.onlyStopStarted"/>');
					return;
				}
			}
			top.Alert.confirm(
					'<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + title + '"/>',
					function(r) {
						if (r) {
							for (var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
							}
							$
									.ajax({
										url : url,
										type : 'post',
										data : {
											ids : ids.join(',')
										},
										cache : false,
										success : function(data) {
											var d = $
													.parseJSON(data);
											if (d.success) {
												var msg = d.msg;
												tip(msg);
												reloadBusinessConfigTable('${param.type}');
											}
										}
									});
						}
					});
		} else {
			tip('<spring:message code="com.glaway.ids.common.selectOperate"/>');
		}
	}

	function businessConfig_downErrorReport(dataListAndErrorMap) {
		var url = 'planBusinessConfigController.do?downErrorReport&paramType=${param.type}';
		downloadErrorReport(url, dataListAndErrorMap);
	}

	function reloadBusinessConfigList() {
		$("#businessConfigList${param.type}").datagrid('reload');
	}
	//导入模板
	function businessConfigListImport() {
		gridname = 'businessConfig';
		url = 'planBusinessConfigController.do?goImport&paramType=${param.type}';
		createDialog('importBusinessConfigDialog', url);
	}

	function businessConfigListImportOkFunction(iframe) {
		saveOrUp(iframe);
		$("#businessConfigList${param.type}").datagrid('reload');
		return false;
	}
	//导出模版
	function businessConfigListExport() {
		var params = '&configType=${param.type}';
		var no = $('#no${param.type}').val();
		if (no != null && no != '' && no != undefined) {
			params = params + '&no=' + no;
		}
		var name = $('#name${param.type}').val();
		if (name != null && name != '' && name != undefined) {
			params = params + '&name=' + name;
		}
		var stopFlag = $('#stopFlag${param.type}').combobox('getValue');
		if (stopFlag != null && stopFlag != '' && stopFlag != undefined) {
			params = params + '&stopFlag=' + stopFlag;
		}
		params = params + '&downloadType=1';
		var url = encodeURI('planBusinessConfigController.do?doDownloadBusinessConfig'
				+ params);
		url = encodeURI(url);
		window.location.href = url;
	}
</script>
</body>
</html>