//输入
window.TC002 = new Object();
var _this = window.TC002;
var onlyReadonly;
var enterType;
var fromType;
var fromDetailType;
var isEnableFlag;
!function() {
	//按钮控制
	_this.isEditor = function() {
		debugger
		// 4028f00d6db34426016db365b27c0000为PLM计划类型
		if (fromType == '4028f00d6db34426016db365b27c0000'&&onlyReadonly == '1') {
			$("#TC002关联PLM系统Div").show();
		} else {
			$("#TC002关联PLM系统Div").hide();
		}
		$("#TC002新增内部输入Div").hide();
		$("#TC002新增外部输入Div").hide();
		if (enterType == '2' || onlyReadonly == '1') {
			$("#TC002新增输入项Div").hide();
			$("#TC002新增本地文档Div").hide();
			$("#TC002删除Div").hide();
			$("#TC002项目库关联Div").hide();
			$("#TC002计划关联Div").hide();
		} else {
			$("#TC002新增输入项Div").show();
			$("#TC002新增本地文档Div").show();
			$("#TC002删除Div").show();
			$("#TC002项目库关联Div").show();
			$("#TC002计划关联Div").show();
		}

		if ('planResolve' == fromDetailType
				|| 'mxgraphPlanChange' == fromDetailType) {
			$("#TC002新增内部输入Div").show();
			$("#TC002新增外部输入Div").show();
			$("#TC002新增输入项Div").hide();
		} else if ('1' == fromDetailType) {
			$("#TC002新增输入项Div").hide();
			$("#TC002新增本地文档Div").hide();
			$("#TC002删除Div").hide();
			$("#TC002项目库关联Div").hide();
			$("#TC002计划关联Div").hide();
		}
	}

	//dataGride列控制
	_this.isShowColumn = function() {
		// 4028f00d6db34426016db365b27c0000为PLM计划类型
		if (fromType == '4028f00d6db34426016db365b27c0000') {
			if (planBizCurrent == "ORDERED" && enterType == '2') {
				setTimeout(function() {
					$('#' + dataGrideId).datagrid('hideColumn', 'docNameShow');
				}, 10);
			} else {
				setTimeout(function() {
					$('#' + dataGrideId).datagrid('hideColumn', 'docNameShow');
					$('#' + dataGrideId).datagrid('hideColumn', 'versionCode');
				}, 10);
			}
		} else {
			setTimeout(function() {
				$('#' + dataGrideId).datagrid('hideColumn', 'fileType');
				$('#' + dataGrideId).datagrid('hideColumn', 'versionCode');
			}, 10);
		}

	}

	//展示来源
	_this.showOrigin = function(val, row, value) {
		fromDetailType = $("#fromDetailType").val();
		if ('planResolve' == fromDetailType
				|| 'mxgraphPlanChange' == fromDetailType) {
			return row.originObjectName;
		} else {
			if (row.originType == "LOCAL") {
				return "本地文档";
			} else if (row.originType == "PLM") {
				return "PLM系统";
			} else {
				return val;
			}
		}
	}

	_this.showFileType = function(val, row, value) {
		if (row.originType != "PLM") {
			return "文档";
		} else {
			return val;
		}
	}

	// 名称显示：
	_this.addNameLink = function(val, row, value) {
		if (fromType == '4028f00d6db34426016db365b27c0000'){
			if (row.originType == 'LOCAL') {
				return '<a  href="#" onclick="TC002.importDoc(\'' + row.docId
					+ '\',\'' + row.name
					+ '\')" id="myDoc"  style="color:blue">' + row.docName
					+ '</a>';
			} else if (row.originType == 'PROJECTLIBDOC'
				&& (row.docName != 'undefined' && row.docName != '' && row.docName != null)) {
				var path = "<a  href='#' title='查看' style='color:blue' onclick='TC002.openProjectDoc1(\""
					+ row.docIdShow
					+ "\",\""
					+ row.docName
					+ "\",\""
					+ row.ext1
					+ "\",\""
					+ row.ext2
					+ "\")'>"
					+ row.docName
					+ "</a>"
				return path;
			} else if (row.originType == 'PLAN'
				&& (row.docName != 'undefined' && row.docName != '' && row.docName != null)) {
				var path = "<a  href='#' title='查看' style='color:blue' onclick='TC002.openProjectDoc1(\""
					+ row.docIdShow
					+ "\",\""
					+ row.docName
					+ "\",\""
					+ row.ext1
					+ "\",\""
					+ row.ext2
					+ "\")'>"
					+ row.docName
					+ "</a>"
				return path;
			}else if (row.originType == 'PLM'&&onlyReadonly != '1') {
				var path = "<a  href='#' title='PLM详情' style='color:blue' onclick='TC002.plmShowPage(\""
					+ row.docId
					+ "\",\""
					+ row.fileType
					+"\",\""
					+val
					+ "\")'>"
					+ val
					+ "</a>"
				return path;
			} else {
				return val;
			}
		}else{
				return val;
		}
	}

	// 文档链接：
	_this.addLink = function(val, row, value) {
		if (row.originType == 'LOCAL') {
			return '<a  href="#" onclick="TC002.importDoc(\'' + row.docId
					+ '\',\'' + row.name
					+ '\')" id="myDoc"  style="color:blue">' + row.docName
					+ '</a>';
		} else if (row.originType == 'PROJECTLIBDOC'
				&& (row.docName != 'undefined' && row.docName != '' && row.docName != null)) {
			var path = "<a  href='#' title='查看' style='color:blue' onclick='TC002.openProjectDoc1(\""
					+ row.docIdShow
					+ "\",\""
					+ row.docName
					+ "\",\""
					+ row.ext1
					+ "\",\""
					+ row.ext2
					+ "\")'>"
					+ row.docName
					+ "</a>"
			/*
			 * if (row.ext3 == false || row.ext3 == 'false') { path = row.name; }
			 */
			return path;
		} else if (row.originType == 'PLAN'
				&& (row.docName != 'undefined' && row.docName != '' && row.docName != null)) {
			var path = "<a  href='#' title='查看' style='color:blue' onclick='TC002.openProjectDoc1(\""
					+ row.docIdShow
					+ "\",\""
					+ row.docName
					+ "\",\""
					+ row.ext1
					+ "\",\""
					+ row.ext2
					+ "\")'>"
					+ row.docName
					+ "</a>"
			/*
			 * if (row.ext3 == false || row.ext3 == 'false') { path = row.name; }
			 */
			return path;
		} else {
			return "";
		}
	}

	_this.plmShowPage = function(id, type,val) {
		if (type == "文档") {
			type = 'doc';
		} else if (type == "图档") {
			type = 'epm';
		} else {
			type = 'part';
		}
		var url = "/plm-web/IDSIntegratedPLMController/selectInfoBefore.do?type="
				+ type + "&id=" + id + '&isIframe=true&afterIframe=true';
		top.addTabByIdOrRefresh(val, url, '', id);
	}

	_this.importDoc = function(filePath, fileName) {
		window.location.href = encodeURI('jackrabbitFileController.do?fileDown&fileName='
				+ fileName + '&filePath=' + filePath);
	}

	_this.openProjectDoc1 = function(id, name, download, history) {
		if (download == false || download == 'false') {
			download = "false";
		}
		if (history == false || history == 'false') {
			history = "false";
		}
		var url = "/ids-pm-web/projLibController.do?viewProjectDocDetail&id="
				+ id + "&download=" + download + "&history=" + history;
		// createdetailwindow("文档详情", url, "870", "580")
		$("#planDocViewDetail").lhgdialog("open", 'url:' + url);
	}

	// 新增输入：
	function openAddResolve(dgId, url, id) {
		if ("1" == $("#isEnableFlag").val()) {
			top
					.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
			return;
		}
		/* var allRows = $("#"+dgId).datagrid("getRows"); */
		var ids = '';
		gridname = dgId;
		var dialogUrl = url + '&allPreposeIds=' + $("#allPreposeIds").val()
				+ '&cellId=' + $("#cellId").val() + '&parentPlanId='
				+ $("#parentPlanId").val() + '&preposeIds=' + preposeIds
				+ '&useObjectId=' + $('#useObjectId').val() + '&useObjectType='
				+ $('#useObjectType').val();
		$("#" + id).lhgdialog("open", 'url:' + dialogUrl + "&ids=" + ids);
	}

	function openAddDialogInputSureResolve(iframe) {
		iframe.saveSelectedRowsResolve();
		return false;
	}

	function openAddDialogLocalSureResolve(iframe) {
		iframe.addFormsubmitResolve();
		return false;
	}

	// 新增本地文档：
	_this.addLocalFile = function() {
		if ("1" == $("#isEnableFlag").val()) {
			top
					.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
			return;
		}
		var url = 'taskFlowResolveController.do?goAdd&type=LOCAL';
		fromDetailType = $('#fromDetailType').val();
		if ('mxgraphPlanChange' == fromDetailType) {
			url = 'taskFlowResolveController.do?goChangeAdd&type=LOCAL'
		}
		dataGrideId = "Inputs";
		var dialogUrl = url + '&allPreposeIds=' + $("#allPreposeIds").val()
				+ '&cellId=' + $("#cellId").val() + '&parentPlanId='
				+ $("#parentPlanId").val() + '&preposeIds='
				+ $("#preposeIds").val() + '&useObjectId=' + $("#planId").val()
				+ '&useObjectType=' + "PLAN";
		$("#openAddDialogLocalSure").lhgdialog("open", 'url:' + dialogUrl + "");
	}

	// 新增内部输入：
	_this.openAddInnerInput = function() {
		debugger
		if ("1" == $("#isEnableFlag").val()) {
			top
					.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
			return;
		}
		var url = 'taskFlowResolveController.do?goAdd&type=INNERTASK';

		fromDetailType = $('#fromDetailType').val();
		if ('mxgraphPlanChange' == fromDetailType) {
			url = 'taskFlowResolveController.do?goChangeAdd&type=INNERTASK'
		}

		dataGrideId = "Inputs";
		var dialogUrl = url + '&allPreposeIds=' + $("#allPreposeIds").val()
				+ '&cellId=' + $("#cellId").val() + '&parentPlanId='
				+ $("#parentPlanId").val() + '&preposeIds='
				+ $("#preposeIds").val() + '&useObjectId=' + $("#planId").val()
				+ '&useObjectType=' + "PLAN";
		$("#openAddInnerDialogInput")
				.lhgdialog("open", 'url:' + dialogUrl + "");
	}

	// 新增外部输入：
	_this.openAddOutInput = function() {
		debugger
		if ("1" == $("#isEnableFlag").val()) {
			top
					.tip('<spring:message code="com.glaway.ids.pm.project.task.noAuthorityEdit"/>');
			return;
		}
		var url = 'taskFlowResolveController.do?goAdd&type=DELIEVER';
		fromDetailType = $('#fromDetailType').val();
		if ('mxgraphPlanChange' == fromDetailType) {
			url = 'taskFlowResolveController.do?goChangeAdd&type=DELIEVER'
		}
		dataGrideId = "Inputs";
		var dialogUrl = url + '&allPreposeIds=' + $("#allPreposeIds").val()
				+ '&cellId=' + $("#cellId").val() + '&parentPlanId='
				+ $("#parentPlanId").val() + '&preposeIds='
				+ $("#preposeIds").val() + '&useObjectId=' + $("#planId").val()
				+ '&useObjectType=' + "PLAN";
		$("#openAddOutDialogInput").lhgdialog("open", 'url:' + dialogUrl + "");
	}

	// 项目库挂接：：
	_this.goProjLibLink = function(id, index) {
		var rows = $('#Inputs').datagrid('getSelections');
		if (rows.length == 0) {
			top.tip("请至少选择一条数据");
			return false;
		}
		if (rows.length > 1) {
			top.tip("当前操作只能选择一条数据");
			return false;
		}
		debugger
		var row;
		index = $('#Inputs').datagrid('getRowIndex',
				$('#Inputs').datagrid('getSelected'));
		var all = $('#Inputs').datagrid('getRows');
		for (var i = 0; i < all.length; i++) {
			var inx = $("#Inputs").datagrid('getRowIndex', all[i]);
			if (inx == index) {
				row = all[i];
			}
		}

		idRow = row.id;
		if (row.originTypeExt == "DELIEVER") {
			var dialogUrl = 'projLibController.do?goProjLibLayout0&id='
					+ $("#projectId").val() + '&rowId=' + idRow;
			$("#taskDocCheckLineDialog").lhgdialog("open", "url:" + dialogUrl);
		} else {
			top.tip("只有外部输入才能关联项目库");
			return false;
		}

	}

	_this.loadData = function(this_) {
		debugger
		_this.isEditor();
		_this.isShowColumn();
		var datas = "";
		var flg = false;
		if (this_ != null && this_ != '' && this_ != undefined) {
			datas = this_;
		} else {
			datas = {
				useObjectId : $("#planId").val(),
				useObjectType : "PLAN",
				projectId : $('#projectId').val()
			}
		}
		$.ajax({
			type : 'POST',
			url : 'inputsController.do?list',
			async : false,
			data : datas,
			success : function(data) {
				try {
					if (data == null) {
						data = new Array();
					}

					$("#Inputs").datagrid("clearSelections");
					$("#Inputs").datagrid("loadData", data);
				} catch (e) {
					// alert('3e:'+e)
				} finally {
					flg = true;
				}
			}
		});
		return flg;
	}

	// 计划挂接：：
	_this.goPlanLink = function(id, index) {
		var rows = $('#Inputs').datagrid('getSelections');
		if (rows.length == 0) {
			top.tip("请至少选择一条数据");
			return false;
		}
		if (rows.length > 1) {
			top.tip("当前操作只能选择一条数据");
			return false;
		}

		dataGrideId = "Inputs";
		var row;
		index = $('#' + dataGrideId).datagrid('getRowIndex',
				$('#Inputs').datagrid('getSelected'));
		var all = $('#' + dataGrideId).datagrid('getRows');
		for (var i = 0; i < all.length; i++) {
			var inx = $("#" + dataGrideId).datagrid('getRowIndex', all[i]);
			if (inx == index) {
				row = all[i];
			}
		}

		if (row.originTypeExt == "DELIEVER") {
			fromDetailType = $('#fromDetailType').val();
			if ('mxgraphPlanChange' == fromDetailType) {
				$
						.ajax({
							type : 'POST',
							data : {
								inputsName : row.name
							},
							url : 'planController.do?setInputsNameToSession',
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								if (d.success) {

									$
											.ajax({
												type : 'POST',
												data : {
													cellId : row.cellId,
													parentPlanId : $(
															"#parentPlanId")
															.val()
												},
												url : 'applyFlowResolveForChangeController.do?getPlanIdByChangeInputUseObjectId',
												cache : false,
												success : function(data) {
													var d = $.parseJSON(data);
													var url = 'planController.do?goSelectPlanInputs&projectId='
															+ $("#projectId")
																	.val()
															+ '&useObjectId='
															+ d.obj
															+ '&useObjectType='
															+ $(
																	"#useObjectType")
																	.val()
															+ '&tempId='
															+ row.id;
													createDialog(
															'changePlanInputsDialog',
															url);
												}
											});

								}
							}
						});

			} else {
				$
						.ajax({
							type : 'POST',
							data : {
								inputsName : row.name
							},
							url : 'planController.do?setInputsNameToSession',
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								if (d.success) {
									var url = 'planController.do?goSelectPlanInputs&projectId='
											+ $("#projectId").val()
											+ '&useObjectId='
											+ $("#planId").val()
											+ '&useObjectType='
											+ "PLAN"
											+ '&tempId=' + row.id;
									// url = encodeURI(encodeURI(url))

									createDialog('planInputsDialog', url);
								}
							}
						});
			}
		} else {
			top.tip("只有外部输入才能关联计划");
			return false;
		}
	}

	// 删除：
	_this.deleteSelectionsInputs = function(griname, url) {
		debugger
		var rows = $("#Inputs").datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			top.Alert
					.confirm(
							'确定删除该记录？',
							function(r) {
								if (r) {
									for (var i = 0; i < rows.length; i++) {
										ids.push(rows[i].id);
									}
									fromDetailType = $('#fromDetailType').val();
									if ('mxgraphPlanChange' == fromDetailType) {
										var cellId = $('#cellId').val();
										var parentPlanId = $('#parentPlanId')
												.val();
										url = 'applyFlowResolveForChangeController.do?doDelChangeInputs';
										$.ajax({
											url : url,
											type : 'post',
											data : {
												ids : ids.join(','),
												parentPlanId : parentPlanId,
												cellId : cellId
											},
											cache : false,
											success : function(data) {
												$("#Inputs").datagrid(
														"clearSelections");
												$('#Inputs').datagrid('reload');
											}
										});
									} else {
										$
												.ajax({
													url : 'taskFlowResolveController.do?doBatchDel',
													type : 'post',
													data : {
														ids : ids.join(',')
													},
													cache : false,
													success : function(data) {
														var d = $
																.parseJSON(data);
														var msg = d.msg
																.split('<br/>')
														top.tip(msg[0]); // John
														if (d.success) {
															$
																	.ajax({
																		type : 'POST',
																		url : 'taskFlowResolveController.do?inputList',
																		async : false,
																		data : {
																			cellId : $(
																					'#cellId')
																					.val(),
																			parentPlanId : $(
																					'#parentPlanId')
																					.val()
																		},
																		success : function(
																				data) {
																			$(
																					"#Inputs")
																					.datagrid(
																							"clearSelections");
																			$(
																					"#Inputs")
																					.datagrid(
																							"loadData",
																							data);
																		}
																	});
														} else {
															var msg = d.msg
																	.split('<br/>')
															top.tip(msg[0]); // John
														}
													}
												});
									}
								}
							});
		} else {
			top.tip('请选择需要删除的记录');
		}
	}

}();

//输入刷新：
function loadDataForInput() {
	$("#Inputs").datagrid("reload");
}


function planInputsDialog(iframe) {
	if (iframe.validateSelectedBeforeSave()) {
		debugger;
		var row = iframe.$('#planlist').datagrid('getSelections');
		var planId = row[0].id;
		var tempId = iframe.$("#tempId").val();
		var useObjectId = iframe.$("#useObjectId").val();
		var inputsName = iframe.$("#inputsName").val();

		fromDetailType = $('#fromDetailType').val();
		if ('mxgraphPlanChange' == fromDetailType) {
			$
					.ajax({
						type : 'POST',
						data : {
							planId : planId,
							tempId : tempId,
							parentPlanId : $('#parentPlanId').val(),
							cellId : cellId,
							inputsName : inputsName,
							originType : 'PLAN'
						},
						url : 'applyFlowResolveForChangeController.do?updateProjLibAndPlanLinkChange',
						cache : false,
						success : function(data) {
							debugger;
							$("#Inputs").datagrid("clearSelections");
							$('#Inputs').datagrid('reload');
						}
					});
		} else {
			$.ajax({
				type : 'POST',
				data : {
					planId : planId,
					tempId : tempId,
					useObjectId : useObjectId,
					inputsName : inputsName
				},
				url : 'taskFlowResolveController.do?setInputsPlanLink',
				cache : false,
				success : function(data) {
					debugger;
					$("#Inputs").datagrid("clearSelections");
					$('#Inputs').datagrid('reload');
				}
			});
		}
	} else {
		return false;
	}
}

function taskDocCheckLineDialog(iframe) {
	debugger
	if (iframe.validateSelectedNum()) {
		var docId = iframe.getSelectionsId();
		var folderId = iframe.$("#folderId").val();
		var rowId = iframe.$("#rowId").val();
		fromDetailType = $('#fromDetailType').val();
		if ('mxgraphPlanChange' == fromDetailType) {
			$
					.ajax({
						url : 'applyFlowResolveForChangeController.do?updateProjLibAndPlanLinkChange',
						type : 'post',
						data : {
							fileId : docId,
							folderId : folderId,
							rowId : rowId,
							projectId : $('#projectId').val(),
							parentPlanId : $('#parentPlanId').val(),
							cellId : $('#cellId').val(),
							useObjectType : "PLAN",
							originType : 'PROJECTLIBDOC'
						},
						cache : false,
						success : function(data) {
							debugger;
							var d = $.parseJSON(data);
							$("#Inputs").datagrid("clearSelections");
							$('#Inputs').datagrid('reload');
						}
					});
		} else {
			$.ajax({
				url : 'taskFlowResolveController.do?updateInputsProjLibLink',
				type : 'post',
				data : {
					fileId : docId,
					folderId : folderId,
					rowId : rowId,
					projectId : $('#projectId').val(),
					useObjectId : $('#planId').val(),
					useObjectType : "PLAN"
				},
				cache : false,
				success : function(data) {
					debugger;
					var d = $.parseJSON(data);
					$("#Inputs").datagrid("clearSelections");
					$('#Inputs').datagrid('reload');
				}
			});
		}
		return true;
	} else {
		return false;
	}
}

function openAddDialogInputSure(iframe) {
	fromDetailType = $('#fromDetailType').val();
	if ('mxgraphPlanChange' == fromDetailType) {
		iframe.saveSelectedRowsChange();
	} else {
		iframe.saveSelectedRowsResolve();
	}
	return false;
}

function openAddDialogLocalSure(iframe) {
	fromDetailType = $('#fromDetailType').val();
	if ('mxgraphPlanChange' == fromDetailType) {
		iframe.afterSubmitChange();
	} else {
		iframe.addFormsubmitResolve();
	}
	return false;
}

var planBizCurrent;
var fromType;
var dataGrideId;

$(function() {
	debugger
	enterType = $("#enterType").val();
	onlyReadonly = $("#onlyReadonly").val();
	planBizCurrent = $("#planBizCurrent").val();
	isEnableFlag = $("#isEnableFlag").val();
	fromDetailType = $("#fromDetailType").val();
	_this.isEditor();
	fromType = $('#fromType').val();
	var tab = $('#tt').tabs('getSelected');
	var index = $('#tt').tabs('getTabIndex', tab);
	var planId = $("#planId").val();
	var taskNumber = $("#taskNumber").val();
	_this.isShowColumn();
	dataGrideId = "Inputs";

	var url = "";
	fromDetailType = $('#fromDetailType').val();
	var cellId = $("#cellId").val();
	var parentPlanId = $("#parentPlanId").val();
	var projectId = $("#projectId").val();
	if ('planResolve' == fromDetailType) {
		url = "taskFlowResolveController.do?inputList&cellId=" + cellId
				+ "&parentPlanId=" + parentPlanId + "&projectId=" + projectId
				+ "&useObjectId=" + planId + "";
	} else if ('planChange' == fromDetailType) {
		url = 'planChangeController.do?inputListView&formId=' + taskNumber
				+ '&planId=' + planId;
	} else if ('mxgraphPlanChange' == fromDetailType) {
		url = "applyFlowResolveForChangeController.do?changeInputList&cellId="
				+ cellId + "&parentPlanId=" + parentPlanId + ""
	}

	else {
		url = 'taskDetailController.do?getInputsRelationList&id=' + planId;
	}

	$('#' + dataGrideId).datagrid({
		url : url,
		pageNumber : 1
	});
});
