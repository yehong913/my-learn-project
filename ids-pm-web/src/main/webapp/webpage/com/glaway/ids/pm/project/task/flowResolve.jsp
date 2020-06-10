<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>流程分解</title>
<t:base type="jquery,easyui,tools"></t:base>

<script type="text/javascript">
	function flowResolveTypeOnChange() {
		debugger
		var selectedType = $("#flowResolveType").selectOneRadio("getValue");
		if (selectedType == 'manualResolve') {
			$('#proTemplateList').datagrid('clearSelections');
			$('#proTemplateTreeDiv').panel({closed:true});
			$('#proTemplateDiv').panel({closed:true});
		} else {
			$('#proTemplateTreeDiv').panel({closed:false});
			$('#proTemplateDiv').panel({closed:false});
		}
	}

	//Field Formatters
	function procTmplNameFormatter(val, row, index) {
		var subName = row.procTmpSubName;
		if (subName != undefined && subName != '') {
			return '<a onclick="showDetail(\'' + row.id
					+ '\')" style="color:blue;">' + val + '(' + subName + ')'
					+ '</a>';
		} else {
			return '<a onclick="showDetail(\'' + row.id
					+ '\')" style="color:blue;">' + val + '</a>';
		}

	}

	// 人员id、realName转换
	function viewFirstName(val, row, index) {
		if (val != undefined && val != null && val != '') {
			var realName = row.firstFullName;
			if (realName != undefined && realName != null && realName != '') {
				return realName + "-" + row.firstName;
			} else {
				return row.firstBy;
			}
		}
	}

	// 人员id、realName转换
	function viewUserRealName(val, row, index) {
		if (val != undefined && val != null && val != '') {
			var realName = val.realName;
			if (realName != undefined && realName != null && realName != '') {
				return realName + "-" + val.userName;
			} else {
				return val.id;
			}
		}
	}

	function dateFormatter(val, row, index) {
		//return dateFmtYYYYMMDD(val);
		return val.substr(0, 11);
	}

	function statusFormat(val, row, index) {
		var procStatusList = eval($('#procStatusList').val());
		for (var i = 0; i < procStatusList.length; i++) {
			if (row.bizCurrent == procStatusList[i].name) {
				if (row.bizCurrent == "yishenpi") {
					return row.status;
				}
				return procStatusList[i].title;
			}
		}
	}

	function showDetail(id) {

		//var dialogUrl = '${pageContext.request.contextPath}/taskFlowResolveController.do?goChangeAddResource&cellId='+$("#cellId").val()+'&parentPlanId='+$("#parentPlanId").val();
		//createDialog('addResourceDialog',dialogUrl);

		//有分层暂时,无法替换
		$.fn
				.lhgdialog({
					content : 'url:${rdflowWeb_Nginx}/procTemplateController.do?goShowDetailForFlow&id='
							+ id,
					lock : true,
					width : 800,
					height : 550,
					title : '<spring:message code="com.glaway.ids.pm.project.plan.showVersionDetail"/>',
					opacity : 0.3,
					cache : false,
					button : [ {
						name : '<spring:message code="com.glaway.ids.common.btn.close"/>',
						focus : true
					} ]
				/* 
				cancelVal : '取消',
				cancel : true */
				});
	}

	function doFlowResolve() {
		window.top.progress("open");
		var newdate = new Date().getTime();
		var selectedType = $("#flowResolveType").selectOneRadio("getValue");
		if (selectedType != 'manualResolve') {
			var rows = $("#proTemplateList").datagrid("getSelections");
			if (rows.length != 1) {
				tip('<spring:message code="com.glaway.ids.pm.project.task.selectProcTemplate"/>');
				window.top.progress("close");
			} else {
				var curId = $("#proTemplateList").datagrid("getSelections")[0].id;
				$.ajax({
					url : '/ids-pm-web/taskFlowResolveController.do?templateResolve',
					type : 'post',
					data : {
						"id" : curId,
						"parentId" : '${parentPlanId}'
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success == true) {
							$.ajax({
								type : 'POST',
								url : 'taskFlowResolveController.do?getBackChangePlanFlag',
								success : function(data) {
									var d = $.parseJSON(data);
									if (d.success == true) {
										//点击分解按钮就新增log日志
										$.ajax({
											type : 'POST',
											url : 'planLogController.do?saveLogWithFlowResolve&newDate='
													+ newdate,
											async : false,
											data : {
												"planId" : $('#parentPlanId').val()
											}
										});

										$.ajax({
											type : 'POST',
											url : 'taskFlowResolveController.do?flowResolveEditor&newDate='
													+ newdate,
											data : {
												'id' : $('#parentPlanId').val(),
												'rdflowWeb_Nginx' : '${rdflowWeb_Nginx}'
											},
											success : function(data) {
												debugger
												<%--var furl = "${rdflowWeb_Nginx}/webpage/com/glaway/ids/rdtask/workFlow/examples/editors/workfloweditor.jsp?isEnableFlag=2&parentPlanId="--%>
												var furl = "${rdflowWeb_Nginx}/rdflowCommonController.do?goOpenEditorPage&isEnableFlag=2&parentPlanId="
														+ $('#parentPlanId').val()
														+ "&status=EDITING&newDate="
														+ newdate
														+ "&userId=${userId}";
												//$("#"+'editFlowTaskFr').lhgdialog("open","url:"+furl);

												var win = $.fn.lhgdialog("getSelectParentWin");
												win.createDia('editFlowTaskFr',furl);
												win.beginFun();
												window.top.progress("close");
												$.fn.lhgdialog("closeSelect");
												/* var win = $.fn.lhgdialog("getSelectParentWin");
												win.beginFun();
												$.fn.lhgdialog("closeSelect"); */
											}
										});
									} else {
										tip("研发流程任务已成功生成");
										var win = $.fn.lhgdialog("getSelectParentWin");
										win.beginFun();
										window.top.progress("close");
										$.fn.lhgdialog("closeSelect");
									}
								}
							});
						} else {
							window.top.progress("close");
							tip(d.msg);
						}
					}
				});
			}
		} else {
			$.ajax({
				url : 'taskFlowResolveController.do?templateResolve',
				type : 'post',
				data : {
					'id' : "",
					'parentId' : '${parentPlanId}'
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success == true) {
						//点击分解按钮就新增log日志
						$.ajax({
							type : 'POST',
							url : 'planLogController.do?saveLogWithFlowResolve',
							async : false,
							data : {
								'planId' : $('#parentPlanId').val()
							}
						});

						$.ajax({
						type : 'POST',
						url : 'taskFlowResolveController.do?flowResolveEditor&newDate='
								+ newdate,
						data : {
							'id' : $('#parentPlanId').val()
						},
						success : function(data) {
							/*var furl = "${rdflowWeb_Nginx}/webpage/com/glaway/ids/rdtask/workFlow/examples/editors/workfloweditor.jsp?isEnableFlag=2&parentPlanId="*/
                            var furl = "${rdflowWeb_Nginx}/rdflowCommonController.do?goOpenEditorPage&isEnableFlag=2&parentPlanId="
									+ $('#parentPlanId').val()
									+ "&newDate="
									+ newdate
									+ "&userId=${userId}";
							//$("#"+'editFlowTaskFr2').lhgdialog("open","url:"+furl);
							var win = $.fn.lhgdialog("getSelectParentWin");
							win.createDia('editFlowTaskFr2',furl);
							win.beginFun();
							window.top.progress("close");
							$.fn.lhgdialog("closeSelect");
							//win.beginFun();
							//$.fn.lhgdialog("closeSelect");
						}
					});
					} else {
						window.top.progress("close");
						tip(d.msg);
					}
				}
			});
		}
	}

	$(function() {
		if ("true" == $('#isFlowFlag').val()) {
			$('#flowResolveTypeDiv').attr("style", "display:none;");
			;
		}
		setTimeout(function() {
			setDefaultSelectedNode();
		}, 800);
	});

	function searchproTemplate() {
		var queryParams = $('#proTemplateList').datagrid('options').queryParams;

		$('#seachproTemplateTag').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});

		var procCategoryId = getNode("categoryTreeForProc").id;

		var url = encodeURI('taskFlowResolveController.do?conditionSearch&fromType=qiyong&procCategoryId='
				+ procCategoryId + '');
		url = encodeURI(url);
		$('#proTemplateList').datagrid({
			url : url,
			pageNumber : 1
		});
		$('#proTemplateList').datagrid('unselectAll');
		$('#proTemplateList').datagrid('clearSelections');
		$('#proTemplateList').datagrid('clearChecked');
	}
	//重置
	function tagSearchReset() {
		$('#procTmplNames').textbox("clear");
	}

	function flowResolveCanel() {
		$.fn.lhgdialog("closeSelect");
	}

	function refreshTab() {
		debugger
		var win = $.fn.lhgdialog('getSelectParentWin');
		win.beginFun();
		//$.fn.lhgdialog('closeSelect');
	}

	//名称链接事件
	var treeNodeId;
	function loadLibUrl(event, treeId, treeNode) {
		treeNodeId = treeNode.id
		searchList(treeNodeId);
		expandAll(true);
	}

	//查询
	function searchList(treeNodeId) {
		if (treeNodeId.length == 0) {

			return;
		}
		var searchObjArr = $('#seachproTemplateTag').serializeArray();
		var searchParam = new Object();
		if (searchObjArr != null && searchObjArr.length > 0) {
			for (var i = 0; i < searchObjArr.length; i++) {
				searchParam[searchObjArr[i].name] = searchObjArr[i].value;
			}
		}
		$('#proTemplateList')
				.datagrid(
						{
							url : 'taskFlowResolveController.do?conditionSearch&fromType=qiyong&procCategoryId='
									+ treeNodeId,
							queryParams : searchParam
						});
	}

	function setDefaultSelectedNode() {
		debugger
		var treeObj = $.fn.zTree.getZTreeObj("categoryTreeForProc");
		// 获取全部节点
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		// 查找设置的value
		if (nodes.length > 0) {
			var node = nodes[0];
			treeObj.selectNode(node);
			treeObj.setting.callback.onClick(null, node.id, node);
		}
	}

	//名称链接事件
	var treeNodeId;
	function loadLibUrl(event, treeId, treeNode) {
		debugger
		treeNodeId = treeNode.id
		searchList(treeNodeId);
		expandAll(true);
	}

	//查询
	function searchList(treeNodeId) {
		if (treeNodeId.length == 0) {
			tip('<spring:message code="com.glaway.ids.common.selectOneNode"/>');
			return;
		}
		var searchObjArr = $('#seachproTemplateTag').serializeArray();
		var searchParam = new Object();
		if (searchObjArr != null && searchObjArr.length > 0) {
			for (var i = 0; i < searchObjArr.length; i++) {
				searchParam[searchObjArr[i].name] = searchObjArr[i].value;
			}
		}
		$('#proTemplateList')
				.datagrid(
						{
							url : 'taskFlowResolveController.do?conditionSearch&fromType=qiyong&procCategoryId='
									+ treeNodeId,
							queryParams : searchParam
						});
	}

	function statusFormat(val, row, index) {
		var status;
		debugger
		if (row.bizCurrent == "qiyong") {
			status = "启用";
		} else if (row.bizCurrent == "jinyong") {
			status = "禁用";
		} else if (row.bizCurrent == "nizhi") {
			status = "拟制中";
		} else if (row.bizCurrent == "revise") {
			status = "修订中";
		} else {
			status = "审批中";
		}
		return status;
	}

	function firstByFormat(val, row, index) {
		return row.firstFullName + "-" + row.firstName;
	}

	function createNameFormat(val, row, index) {
		return row.createFullName + "-" + row.createName;
	}
</script>
</head>
<body class="easyui-layout" fit="true">
	<div id="flowResolveTypeDiv" data-options="region:'north'"
		style="height: 90px; border: 3px solid #fafafa;" class="easyui-panel">
		<div style="float: right;">
			<fd:helpButton help="helpDoc:ProcessDecomposition"></fd:helpButton>
		</div>
		<input id="parentPlanId" name="parentPlanId" type="hidden"
			value="${parentPlanId}" /> <input id="procStatusList"
			name="procStatusList" type="hidden" value="${procStatusList}" /> <input
			id="isFlowFlag" name="isFlowFlag" type="hidden" value="${isFlowFlag}" />
		<fd:selectOneRadio id="flowResolveType" columns="1"
			onclick="flowResolveTypeOnChange">
			<fd:selectItem itemValue="manualResolve" itemLabel="手工分解流程" />
			<fd:selectItem itemValue="templateResolve" itemLabel="选择研发流程模板"
				selected="true" />
		</fd:selectOneRadio>
	</div>
	<div id="proTemplateTreeDiv"
		data-options="region:'west',split:true,collapsed:false"
		style="padding: 1px; width: 20%;" title="&nbsp;">
		<fd:tree treeIdKey="id"
			url="taskFlowResolveController.do?getProcCategoryTree"
			lazy="true"
			lazyUrl="taskFlowResolveController.do?getProcCategoryTree"
			dnd="false" showIcon="true" id="categoryTreeForProc" treePidKey="pid"
			treeName="name" treeTitle="title" onClickFunName="loadLibUrl" />
	</div>
	<div id="proTemplateDiv" data-options="region:'center'" style="padding: 1px; width: 80%;"
		title="&nbsp;">
		<div id="proTemplateListtb">
			<fd:searchform id="seachproTemplateTag"
				onClickSearchBtn="searchproTemplate()"
				onClickResetBtn="tagSearchReset()">
				<fd:inputText title="{com.glaway.ids.pm.project.proctemplate.name}"
					name="procTmplNames" id="procTmplNames" queryMode="like"
					maxLength="-1" />
			</fd:searchform>
		</div>
		<%--去掉时间列bug23237： --%>
		<fd:datagrid idField="id" id="proTemplateList" fit="true"
			toolbar="#proTemplateListtb" fitColumns="true">
			<fd:dgCol title="{com.glaway.ids.pm.project.proctemplate.name}"
				field="procTmplName"
				formatterFunName="procTmplNameFormatter" />
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.bizVersion}"
				field="bizVersion" width="10" />
			<fd:dgCol title="{com.glaway.ids.common.lable.status}"
				field="bizCurrent" width="12" formatterFunName="statusFormat" />
			<%--<fd:dgCol title="{com.glaway.ids.common.lable.creator}"
				field="firstBy" width="100" formatterFunName="firstByFormat" />
			<fd:dgCol title="{com.glaway.ids.common.lable.createtime}"
				field="firstTime" width="110" />
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.modifyPeople}"
				field="creator" width="100" formatterFunName="createNameFormat" />
			<fd:dgCol title="{com.glaway.ids.pm.project.plan.modifyTime}"
				field="createTime" width="110" />--%>
		</fd:datagrid>
	</div>

</body>
</html>
