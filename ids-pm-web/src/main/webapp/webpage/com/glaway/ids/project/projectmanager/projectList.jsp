<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<div id="projectTool">
			<fd:searchform id="searchProjectTag"
				onClickSearchBtn="searchProject()"
				onClickResetBtn="tagSearchResetProj()" help="helpDoc:ProjectList">
				<input id="lifeCycleList" name="lifeCycleList" type="hidden"
					value="${lifeCycleList}" />
				<input id="epsList" name="epsList" type="hidden" value="${epsList}" />
				<fd:inputText
					title="{com.glaway.ids.pm.project.projectmanager.projectNumber}"
					name="Project.projectNumber" id="searchProjectNumber"
					queryMode="like" maxLength="-1" />
				<fd:inputText
					title="{com.glaway.ids.pm.project.projectmanager.projectName}"
					name="Project.name" id="searchProjectName" queryMode="like"
					maxLength="-1" />
				<fd:inputText
					title="{com.glaway.ids.pm.project.projectmanager.projectManager}"
					id="searchProjectManager" maxLength="-1" />
				<fd:combobox id="searchPhase" textField="name" title="阶段"
					name="Project.phaseInfo.name" maxLength="-1" editable="false"
					valueField="id" multiple="true" prompt="全部"
					url="projectController.do?phaseList" queryMode="in"></fd:combobox>
				<fd:combobox id="bizCurrent" textField="title" title="状态"
					name="Project.bizCurrent" maxLength="-1" editable="false"
					valueField="name" multiple="true" prompt="全部"
					url="projectController.do?statusList" queryMode="in"></fd:combobox>
				<fd:combotree title="{com.glaway.ids.pm.project.projectmanager.type}"
					treeIdKey="id" url="projectController.do?epsCombotree"
					name="Project.eps" maxLength="-1" id="eps" treePidKey="pid"
					multiple="true" prompt="全部" panelHeight="300" treeName="name"
					queryMode="in"></fd:combotree>
				<input name="Project.epsInfo.id" id="searchEps" type="hidden" />
				<fd:inputNumber id="searchProcess" name="Project.process"
					validType="equalOneHundred[6]"
					title="{com.glaway.ids.pm.project.projectmanager.progress}"
					precision="2" suffix="%" queryMode="eq"></fd:inputNumber>
				<fd:inputDateRange id="searchProjCreateTime" interval="1"
					title="{com.glaway.ids.pm.project.projectmanager.createTime}"
					name="Project.createTime" opened="0"></fd:inputDateRange>
				<fd:inputText
					title="{com.glaway.ids.pm.project.projectmanager.creator}"
					id="createName" maxLength="-1" />
				<fd:inputDateRange id="searchStartProjTime" interval="1"
					title="{com.glaway.ids.pm.project.projectmanager.startTime}"
					name="Project.startProjectTime" opened="0"></fd:inputDateRange>
				<fd:inputDateRange id="searchEndProjTime" interval="1"
					title="{com.glaway.ids.pm.project.projectmanager.endTime}"
					name="Project.endProjectTime" opened="0"></fd:inputDateRange>
			</fd:searchform>

			<fd:toolbar>
				<fd:toolbarGroup align="left">
					<fd:linkbutton id="create"
						onclick="openAddProjectDialog('projectController.do?goAdd')"
						value="{com.glaway.ids.common.btn.create}"
						iconCls="basis ui-icon-plus" operationCode="addProject" />
					<fd:linkbutton id="start"
						onclick="startProcess('启动','projectController.do?startProcess','projectList')"
						value="{com.glaway.ids.pm.project.projectmanager.start}"
						iconCls="basis ui-icon-start" operationCode="startProject" />
					<fd:linkbutton id="pauseResume"
						onclick="startProcess('暂停','projectController.do?startProcess','projectList')"
						value="{com.glaway.ids.pm.project.projectmanager.pause}"
						iconCls="basis ui-icon-pause" operationCode="pauseResumeProject" />
					<fd:linkbutton id="pauseResume1"
						onclick="startProcess('恢复','projectController.do?startProcess','projectList')"
						value="{com.glaway.ids.pm.project.projectmanager.resume}"
						iconCls="basis basis ui-icon-recovery"
						operationCode="ResumeProject" />
					<fd:linkbutton id="close"
						onclick="startProcessForClose('关闭','projectController.do?startProcess','projectList')"
						value="{com.glaway.ids.pm.project.projectmanager.close}"
						iconCls="basis ui-icon-close" operationCode="closeProject" />
				</fd:toolbarGroup>
			</fd:toolbar>
		</div>

		<fd:datagrid checkbox="false" toolbar="#projectTool" checked="true"
			fit="true" fitColumns="true" idField="id" checkOnSelect="false"
			id="projectList" url="projectController.do?searchDatagrid" onDblClickRow="onClickRowBatch" onClickFunName="viewNameOnChange">
			<fd:colOpt title="{com.glaway.ids.pm.project.projectmanager.operate}"
				width="100">
				<fd:colOptBtn
					tipTitle="{com.glaway.ids.pm.project.projectmanager.update}"
					iconCls="basis ui-icon-pencil" hideOption="hideEdit"
					onClick="doEdit" operationCode="updateProject"></fd:colOptBtn>
				<fd:colOptBtn
					tipTitle="{com.glaway.ids.pm.project.projectmanager.saveAs}"
					iconCls="basis ui-icon-save" hideOption="hideTemplate"
					onClick="goSaveAsTemplate" operationCode="saveTemplate"></fd:colOptBtn>
				<fd:colOptBtn
					tipTitle="{com.glaway.ids.pm.project.projectmanager.viewWorkLog}"
					iconCls="basis ui-icon-eye" hideOption="hideProjLog"
					onClick="viewProjectLog" operationCode="queryProjectLog"></fd:colOptBtn>
				<fd:colOptBtn
					tipTitle="{com.glaway.ids.pm.project.projectmanager.delete}"
					iconCls="basis ui-icon-minus" hideOption="hideProjectDelete"
					onClick="doRemoveProject" operationCode="deleteProject"></fd:colOptBtn>
			</fd:colOpt>
			<fd:dgCol
				title="{com.glaway.ids.pm.project.projectmanager.projectNumber}"
				field="projectNumber" formatterFunName="alignFormat"  editor="{type:'text'}"/>

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.projectName}"
				field="name" formatterFunName="viewProject" editor="{type:'text'}"/>

			<fd:dgCol
				title="{com.glaway.ids.pm.project.projectmanager.projectManager}"
				field="projectManagerNames" formatterFunName="alignFormat" 
				editor="{type:'combobox',options:{
			                    url:'${pageContext.request.contextPath }/planController.do?useablePlanLevelList',
					            method:'get',
					            valueField:'id',
					            textField:'name',
					             panelMaxHeight:'150px'
							}}"/>

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.phase}"
				field="phaseInfo" formatterFunName="showPhase" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.bizCurrent}"
				field="bizCurrent" formatterFunName="viewBizCurrent" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.progress}"
				field="process" formatterFunName="processFunction" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.type}"
				field="eps" formatterFunName="showEPS" sortable="false" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.creator}"
				field="createName" formatterFunName="showCreateName" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.createTime}"
				field="createTime" formatterFunName="dateFormatterPro" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.startTime}"
				field="startProjectTime" formatterFunName="dateFormatter" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.endTime}"
				field="endProjectTime" formatterFunName="dateFormatter" />
		</fd:datagrid>

		<fd:dialog id="addAndSubmitDialog" width="850px" height="500px"
			modal="true" title="{com.glaway.ids.common.btn.create}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
				callback="saveAndSubmitOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="updateAndSubmitDialog" width="850px" height="500px"
			modal="true" title="{com.glaway.ids.common.btn.modify}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
				callback="updateAndSubmitOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="saveTemplateAndSubmitDialog" width="780px"
			height="400px" modal="true"
			title="{com.glaway.ids.pm.project.projectmanager.saveAs}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
				callback="saveTemplateAndSubmitOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="projectLogAndSubmitDialog" width="730px" height="400px"
			modal="true"
			title="{com.glaway.ids.pm.project.projectmanager.viewWorkLog}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>

		<fd:dialog id="startAndSubmitDialog" width="730px" height="260px"
			modal="true" title="{com.glaway.ids.pm.project.projectmanager.start}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
				callback="startAndSubmitOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="pauseAndSubmitDialog" width="730px" height="260px"
			modal="true" title="{com.glaway.ids.pm.project.projectmanager.pause}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
				callback="pauseAndSubmitOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="resumeAndSubmitDialog" width="730px" height="260px"
			modal="true" title="{com.glaway.ids.pm.project.projectmanager.resume}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
				callback="resumeAndSubmitOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="closeAndSubmitDialog" width="800px" height="260px"
			modal="true" title="{com.glaway.ids.pm.project.projectmanager.close}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
				callback="closeAndSubmitOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		<fd:dialog id="forceCloseAndSubmitDialog" width="800px" height="260px"
			modal="true" title="{com.glaway.ids.pm.project.projectmanager.close}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
				callback="forceCloseAndSubmitOk"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
				callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
		
		<fd:dialog id="addTemplateDialog" width="1000px" height="500px"
			modal="true" title="{com.glaway.ids.pm.project.projecttemplate.editTemplate}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addTemplateSubmit"></fd:dialogbutton>
			<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="closeAll"></fd:dialogbutton>
		</fd:dialog>
	</div>
</div>

<script
	src="webpage/com/glaway/ids/project/projectmanager/projectList.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				// 给时间控件加上样式
				$("#projectTool").find("input[name='queryBefStartProjTime']")
						.attr("class", "Wdate").attr("style",
								"height:20px;width:90px;").click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
				// 给时间控件加上样式
				$("#projectTool").find("input[name='queryAftStartProjTime']")
						.attr("class", "Wdate").attr("style",
								"height:20px;width:90px;").click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
				// 给时间控件加上样式
				$("#projectTool").find("input[name='queryBefEndProjTime']")
						.attr("class", "Wdate").attr("style",
								"height:20px;width:90px;").click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
				//给 时间控件加上样式
				$("#projectTool").find("input[name='queryAftEndProjTime']")
						.attr("class", "Wdate").attr("style",
								"height:20px;width:90px;").click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
			});

	function searchProject() {
		var searchProjectManager = $('#searchProjectManager').val();
		var createName = $('#createName').val();

		var queryParams = $("#projectList").fddatagrid('queryParams',
				"searchProjectTag");
		queryParams["searchProjectManager"] = searchProjectManager;
		queryParams["createName"] = createName;
		queryParams['bizCurrent'] = '';
		queryParams['phaseInfo'] = '';
		queryParams['eps'] = '';
		$('#searchProjectTag').find('*').each(function() {
			if ($(this).attr('name') == 'Project.bizCurrent') {
				queryParams["bizCurrent"] += "," + $(this).val();
			}
			if ($(this).attr('name') == 'Project.phaseInfo.name') {
				queryParams["phaseInfo"] += "," + $(this).val();
			}
			if ($(this).attr('name') == 'Project.eps') {
				queryParams["eps"] += "," + $(this).val();
			}
		});

		$('#projectList').datagrid({
			url : 'projectController.do?searchDatagrid',
			pageNumber : 1,
			queryParams : queryParams
		});
		
		$('#projectList').datagrid('unselectAll');
		$('#projectList').datagrid('clearSelections');
		$('#projectList').datagrid('clearChecked');
	}

	// 重置
	function tagSearchResetProj() {
		$('#searchProjectNumber').textbox("clear");
		$('#searchProjectName').textbox("clear");
		$('#searchProjectManager').textbox("clear");
		$('#createName').textbox("clear");
		$('#searchPhase').combobox("clear");
		$('#bizCurrent').combobox("clear");
		$('#searchPhase').combobox("clear");
		$('#eps').comboztree("clear");
		$('#searchProcess').numberbox("clear");
		$("#searchProjCreateTime_BeginDate").datebox("clear");
		$("#searchProjCreateTime_EndDate").datebox("clear");
		$("#searchStartProjTime_BeginDate").datebox("clear");
		$("#searchStartProjTime_EndDate").datebox("clear");
		$("#searchEndProjTime_BeginDate").datebox("clear");
		$("#searchEndProjTime_EndDate").datebox("clear");
	}

	function alignFormat(val, row, value) {
		if (val == undefined)
			val = '';
		//return '<div style="text-align:left">' + val + '</div>';
		return shtml = '<span title="'+val+'">' + val + '</span>';
	}

	function viewProject(val, row, value) {
		if (val == undefined)
			val = '';
		shtml = '<span title="'+val+'">' + val + '</span>';
		return '<a href="#" onclick="viewProjectDetail(\'' + row.id
				+ '\')" style="color:blue">' + shtml + '</a>';
		/* 		var s = "<a href='javascript:void(0)' style='color:blue' onclick='viewProjectDetail(\"" + row.id + "\")'>" + row.name + "</a>";
		 return '<div style="text-align:left">' + s + '</div>'; */
	}
	// 查看详细事件打开窗口
	function viewProjectDetail(id) {
		var teamId = '';
		/* $.ajax({
			url : 'projectController.do?getTeamId&id='+id,
			type : 'post',
			cache : false,
			async : false,
			success : function(data) {
				var d = $.parseJSON(data);
				teamId = d.obj;
			}
		}); */
		loadPage("#right_page_panel",
				"projectController.do?viewDetailBaseInfo&id=" + id + "&teamId=" + teamId
						+ '&refreshTree=true', "详细信息");
	}

	// 阶段转换
	function showPhase(phase) {
		if (phase != undefined && phase != null && phase != '') {
			if (phase.name != null && phase.name != '') {
				return '<div style="text-align:left">' + phase.name + '</div>';
			} else {
				return '<div style="text-align:left">' + phase.id + '</div>';
			}
		}
		return '';
	}

	// 生命周期状态name、title转换
	function viewBizCurrent(val, row, value) {
		if (val != undefined && val != null && val != '') {
			var bizCurrent = eval($('#lifeCycleList').val());
			for (var i = 0; i < bizCurrent.length; i++) {
				if (val == bizCurrent[i].name) {
					var s = bizCurrent[i].title;
					var resultUrl = s;
					if (row.bizCurrent != "EDITING" || row.status == "1") {
						resultUrl = '<a href="#" onclick="openFlowProjects(\''
								+ row.id
								+ '\')"><font color=blue><div style="text-align:left">'
								+ s + '</div></font></a>'
					}
					return resultUrl;
				}
			}
		}
	}
	// 弹出对象对应的流程
	function openFlowProjects(taskNumber) {
		var tabTitle = '项目工作流';
		var url = '${webRoot}/taskFlowCommonController.do?taskFlowList&taskNumber='
				+ taskNumber;
		createdetailwindow_close(tabTitle, url, 800, 400);
	}

	// eps 转换
	function showEPS(val, row, value) {
		if (val != undefined && val != null && val != '') {
			var epsList = eval($('#epsList').val());
			for (var i = 0; i < epsList.length; i++) {
				if (val == epsList[i].id) {
					var s = epsList[i].name;
					return '<div style="text-align:left">' + s + '</div>';
				}
			}
		}
	}

	// 创建者
	function showCreateName(val, row, value) {
		return row.creator.realName + '-' + row.creator.userName;
	}

	function dateFormatterPro(val, row, value) {
		return val.substr(0, 10);
	}

	function dateFormatter(val, row, value) {
		return dateFmtYYYYMMDD(val);
	}

	// 暂停、关闭、流程中且未驳回的项目不允许修改
	function hideEdit(row, index) {
		if (row.bizCurrent == "PAUSED" || row.bizCurrent == "CLOSED"
				|| (row.status == "1" && row.isRefuse != "1")) {
			return true;
		}
		//项目管理员可以修改所有项目
		var isPmo = "${isPmo}";
		if(isPmo == 'true'){
			return false;
		}
		//不是当前项目经理时隐藏
		var currentUserName = "${currentUserName}";
		var nameArr = row.projectManagerNames.split(",");
		var hideFlag = 0;
		for (var i = 0; i < nameArr.length; i++) {
			if (currentUserName == nameArr[i]) {
				hideFlag++;
			}
		}
		if(hideFlag == 0){
			return true;
		}
		return false;
	}

	function hideTemplate(row, index) {
		return false;
	}

	function hideProjLog(row, index) {
		return false;
	}

	// 执行、暂停、关闭、流程中且未驳回的项目不允许删除
	function hideProjectDelete(row, index) {
		if (row.bizCurrent == "STARTING" || row.bizCurrent == "PAUSED"
				|| row.bizCurrent == "CLOSED"
				|| (row.status == "1" && row.isRefuse != "1")) {
			return true;
		}
		var flag = true;
		debugger;
		if('${isPmo}'=='false'){
			if('${isManager}' == 'false'){
				return true;
			}else{
				$.ajax({
					type : 'POST',
					data : {
						projectId : row.id
					},
					url : 'projectController.do?judgeIsTeamManager',
					cache : false,
					async : false,
					success : function(data){
						var d = $.parseJSON(data);
						debugger;
						if(d.success){
							flag = true;
						}else{
							flag = false;
						}
					}
				});
			}
		}
		
		if(flag){
			return false;
		}else{
			return true;
		}
		
	}

	function doEdit(id) {
		openUpdateProjectDialog(
				'projectController.do?goUpdate&insertRecent=1&id=', id);
	}

	function goSaveAsTemplate(id) {
		saveAsTemplate('projTemplateController.do?goSaveAsTemplate&id=' + id,
				780, 400);
	}

	function viewProjectLog(id) {
		goProjectLog('projLogController.do?goProjectLog', id);
	}

	function doRemoveProject(id) {
		deleteALLSelect('projectController.do?doBatchDel', id);
	}


	// 打开新增项目基本信息页面
	function openAddProjectDialog(url) {
		$
				.ajax({
					url : "projRoleUsersController.do?isPMO", // 此判断已不需要
					type : 'post',
					cache : false,
					async : false,
					success : function(result) {
						if (true) {
							createDialog('addAndSubmitDialog', url);
						} else {
							tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPmoCanCreate"/>');
							return;
						}
					}
				});
	}

	// 打开修改项目基本信息页面
	function openUpdateProjectDialog(url, id) {
		$
				.ajax({
					url : "projRoleUsersController.do?isHasModifyAuth&id=" + id, // 此判断已不需要
					type : 'post',
					cache : false,
					async : false,
					success : function(result) {
						if (true) {
							var dialogUrl = url + id;
							createDialog('updateAndSubmitDialog', dialogUrl);
						} else {
							tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.noAuthorityUpdate"/>');
							return;
						}
					}
				});
	}

	// 打开另存为项目模板页面
	function saveAsTemplate(url, width, height) {
		width = width ? width : 650;
		height = height ? height : 300;
		if (width == "100%" || height == "100%") {
			width = document.body.offsetWidth;
			height = document.body.offsetHeight - 100;
		}
		createDialog('saveTemplateAndSubmitDialog', url);
	}

	// 打开查看项目日志页面
	function goProjectLog(url, projectId, width, height) {
		width = width ? width : 700;
		height = height ? height : 400;
		if (width == "100%" || height == "100%") {
			width = document.body.offsetWidth;
			height = document.body.offsetHeight - 100;
		}
		url += '&id=' + projectId;
		createDialog('projectLogAndSubmitDialog', url);
	}

	// 删除所选的项目
	function deleteALLSelect(url, projectId) {
		var ids = [];
		top.Alert.confirm(
						'<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmDel"/>',
						function(r) {
							if (r) {
								ids.push(projectId);
								$
								.ajax({
									url : url,
									type : 'post',
									data : {
										ids : ids
												.join(',')
									},
									cache : false,
									success : function(
											data) {
										var d = $
												.parseJSON(data);
										if (d.success) {
											var msg = d.msg;
											tip(msg);
											parent
													.loadTree(
															null,
															1);
											reloadTable();
											ids = '';
										}
									}
								});
								
							
								/**任务3647，具有删除按钮权限，如果是系统角色的项目管理员可删除，如果是系统角色的项目经理且是当前项目团队的项目经理也可删除，
								已按照此逻辑对删除按钮显示进行控制，有删除按钮的用户即可删除 --zhousuxia 2019/1/4
								*/
								
								/* $
										.ajax({
											url : "projRoleUsersController.do?isPMO",
											type : 'post',
											cache : false,
											async : false,
											success : function(result) {
												if (result) {
													$
															.ajax({
																url : url,
																type : 'post',
																data : {
																	ids : ids
																			.join(',')
																},
																cache : false,
																success : function(
																		data) {
																	var d = $
																			.parseJSON(data);
																	if (d.success) {
																		var msg = d.msg;
																		tip(msg);
																		parent
																				.loadTree(
																						null,
																						1);
																		reloadTable();
																		ids = '';
																	}
																}
															});
												} else {
													tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPmoCanDel"/>');
													return;
												}
											}
										}); */
							}
						});
	}

	/**
	 * 项目启动、暂停、恢复流程JS方法
	 * @param title 查看框标题
	 * @param url 目标页面地址
	 * @param id 主键字段
	 */
	function startProcess(title, url, id) {
		debugger;
		var rowsData = $('#' + id).datagrid('getSelections');

		var processChooseTip;
		var processConfirmTip;

		if (title == '启动') {
			processChooseTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.selectStartup"/>';
		} else if (title == '暂停') {
			processChooseTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.selectPause"/>';
		} else if (title == '恢复') {
			processChooseTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.selectResume"/>';
		}
		if (!rowsData || rowsData.length == 0) {
			tip(processChooseTip);
			return;
		}
		if (rowsData.length > 1) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlySelectOne"/>');
			return;
		}

		if (title == '启动') {
			if (rowsData[0].bizCurrent != 'EDITING') {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyStartupEditing"/>');
				return;
			}
			if (rowsData[0].status == '1' && rowsData[0].isRefuse != '1') {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.notRepeatSubmit"/>');
				return;
			}

			var names = rowsData[0].name + " - " + rowsData[0].projectNumber;
			processConfirmTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmStartup" arguments="' +  names + '"/>';

			/* $
					.ajax({
						url : "projRoleUsersController.do?isPMO",
						type : 'post',
						cache : false,
						async : false,
						success : function(result) {
							if (result) {
								top.Alert.confirm(processConfirmTip, function(r) {
									if (r) { */
										url += '&id=' + rowsData[0].id;
										createDialog('startAndSubmitDialog',
												url);
			/* 						}
								});
							} else {
								tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPmoCanStartup"/>');
								return;
							}
						}
					}); */
		} else if (title == '暂停') {
			if (rowsData[0].bizCurrent != 'STARTING') {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPauseStarting"/>');
				return;
			}

			if (rowsData[0].status == '1' && rowsData[0].isRefuse != '1') {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.notRepeatSubmit"/>');
				return;
			}

			var names = rowsData[0].name + " - " + rowsData[0].projectNumber;
			processConfirmTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmPause" arguments="' +  names + '"/>';

			$
					.ajax({
						url : "projRoleUsersController.do?isProjectManagerOrPMO&id="
								+ rowsData[0].id,
						type : 'post',
						cache : false,
						async : false,
						success : function(result) {
							if (result) {
								top.Alert.confirm(processConfirmTip, function(r) {
									if (r) {
										url += '&id=' + rowsData[0].id;
										createDialog('pauseAndSubmitDialog',
												url);
									}
								});
							} else {
								tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPmoOrManagerCanPause"/>');
								return;
							}
						}
					});
		} else if (title == '恢复') {
			if (rowsData[0].bizCurrent != 'PAUSED') {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyResumePausing"/>');
				return;
			}

			if (rowsData[0].status == '1' && rowsData[0].isRefuse != '1') {
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.notRepeatSubmit"/>');
				return;
			}

			var names = rowsData[0].name + " - " + rowsData[0].projectNumber;
			processConfirmTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmResume" arguments="' +  names + '"/>';

			$
					.ajax({
						url : "projRoleUsersController.do?isProjectManagerOrPMO&id="
								+ rowsData[0].id,
						type : 'post',
						cache : false,
						async : false,
						success : function(result) {
							if (result) {
								top.Alert.confirm(processConfirmTip, function(r) {
									if (r) {
										url += '&id=' + rowsData[0].id;
										createDialog('resumeAndSubmitDialog',
												url);
									}
								});
							} else {
								tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPmoOrManagerCanResume"/>');
								return;
							}
						}
					});
		}
	}

	/**
	 * 项目关闭流程JS方法
	 * @param title 查看框标题
	 * @param url 目标页面地址
	 * @param id 主键字段
	 */
	function startProcessForClose(title, url, id) {
		var rowsData = $('#' + id).datagrid('getSelections');
		var processConfirmTip;

		if (!rowsData || rowsData.length == 0) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.selectClose"/>');
			return;
		}
		if (rowsData.length > 1) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlySelectOne"/>');
			return;
		}

		if (rowsData[0].bizCurrent != 'STARTING') {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyCloseStarting"/>');
			return;
		}
		if (rowsData[0].status == '1' && rowsData[0].isRefuse != '1') {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.notRepeatSubmit"/>');
			return;
		}

		$
				.ajax({
					url : "projRoleUsersController.do?isProjectManagerOrPMO&id="
							+ rowsData[0].id,
					type : 'post',
					cache : false,
					async : false,
					success : function(result) {
						if (result) {
							$
									.ajax({
										url : 'projectController.do?checkPlan&id='
												+ rowsData[0].id,
										type : 'post',
										data : '',
										cache : false,
										success : function(data) {
											if (data == false) {
												var names = rowsData[0].name
														+ " - "
														+ rowsData[0].projectNumber;
												processConfirmTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmClose" arguments="' +  names + '"/>';
												top.Alert.confirm(
																processConfirmTip,
																function(r) {
																	if (r) {
																		url += '&closedProject=1&id='
																				+ rowsData[0].id;
																		createDialog(
																				'closeAndSubmitDialog',
																				url);
																	}
																});
											} else if (data == true) {
												var names = rowsData[0].name
														+ " - "
														+ rowsData[0].projectNumber;
												processConfirmTip = '<spring:message code="com.glaway.ids.pm.project.projectmanager.confirmForceClose" arguments="' +  names + '"/>';
												top.Alert.confirm(
																processConfirmTip,
																function(r) {
																	if (r) {
																		url += '&closedProject=1&id='
																				+ rowsData[0].id;
																		createDialog(
																				'forceCloseAndSubmitDialog',
																				url);
																	}
																});
											}
										}
									});
						} else {
							tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.onlyPmoOrManagerCanClose"/>');
							return;
						}
					}
				});
	}

	// 新增完成
	function saveAndSubmitOk(iframe) {
		if (saveBasic(iframe)) {
			reloadProjectList();
			return true;
		} else {
			return false;
		}
	}

	// 修改完成
	function updateAndSubmitOk(iframe) {
		if (saveUpdateBasic(iframe)) {
			reloadProjectList();
			return true;
		} else {
			return false;
		}
	}

	// 另存为模板完成
	function saveTemplateAndSubmitOk(iframe) {
		var name = iframe.$("#projTmplName").val();
		var team = iframe.$('#team').selectBooleanCheckbox('getValue');
		var plan = iframe.$('#plan').selectBooleanCheckbox('getValue');
		var lib = iframe.$('#lib').selectBooleanCheckbox('getValue');
		var libPower = iframe.$('#libPower').selectBooleanCheckbox('getValue');
		if( team == false && plan == false && lib == false && libPower == false) {
			top.tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.mustSelectOneWhenSaveAs"/>');
			return false;
		}
		if(name == null || name == undefined || name == ""){
			top.tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.nameNoEmpty"/>');
			return false;
		}
		if (saveAsTemplateBasic(iframe)) {
			return true;
		} else {
			return false;
		}
	}

	function startAndSubmitOk(iframe) {
		iframe.startSubmitProject();
		return false;
	}
	function pauseAndSubmitOk(iframe) {
		iframe.pauseProject();
		return false;
	}
	function resumeAndSubmitOk(iframe) {
		iframe.resumeProject();
		return false;
	}
	function closeAndSubmitOk(iframe) {
		iframe.closeProject();
		return false;
	}
	function forceCloseAndSubmitOk(iframe) {
		iframe.forceCloseProject();
		return false;
	}

	// 重新加载项目列表
	function reloadProjectList() {
		$('#projectList').datagrid('reload');
	}

	function saveBasic(iframe) {
		var formData = iframe.$('#projectBasicAddForm').serialize();
		if (iframe.$('#projectNumber').val() == "") {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.emptyNumber"/>');
			return false;
		}
		if (iframe.$('#name').val() == "") {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.emptyName"/>');
			return false;
		}
		if (iframe.$('#projectManagers').textbox('getValue') == "") {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.emptyManager"/>');
			return false;
		}
		if (iframe.$('#epsName').combotree('getValue') == "") {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.emptyEps"/>');
			return false;
		}
		if (iframe.$('#phase').combobox('getValue') == "") {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.emptyPhase"/>');
			return false;
		}
		if (vidateRemark(iframe.$('#remark').val(),
				'<spring:message code="com.glaway.ids.common.remarkLength"/>')) {
			if (vidateRepeat(iframe, '#projectBasicAddForm',
					'projectController.do?vidateRepeat')) {
				if (iframe.$('#projectBasicAddForm').form('validate')) {
					return postBasic(iframe, "projectController.do?doAdd",
							'#projectBasicAddForm');
				}
				return false;
			}
		}
	}

	function saveUpdateBasic(iframe) {
		var formData = iframe.$('#projectBasicUpdateForm').serialize();
		if (iframe.$('#process').val() == ''
				|| iframe.$('#process').val() == undefined) {
			iframe.$('#process').numberbox('setValue', 0);
		}
		if (iframe.$('#name').val() == "") {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.emptyName"/>');
			return false;
		}
		if (iframe.$('#endProjectTime').datebox('getValue') < iframe.$(
				'#startProjectTime').datebox('getValue')) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.startNoLaterThanEnd"/>');
			return false;
		}
		if (iframe.$('#phase').combobox('getValue') == "") {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.emptyPhase"/>');
			return false;
		}
		if (vidateRemark(iframe.$('#remark').val())) {
			if (iframe.$('#projectBasicUpdateForm').form('validate')) {
				return postBasic(iframe, "projectController.do?doUpdate&isUpdate=true",
						"#projectBasicUpdateForm");
			}
		}
		return false;
	}
	/**
	 * 另存为项目模板
	 * 
	 * @param iframe
	 * @returns {Boolean}
	 */
	function saveAsTemplateBasic(iframe) {
		 debugger;
		var formData = iframe.$('#projTemplateAddForm').serialize();
		var isEdit = iframe.$("#isEdit").selectBooleanCheckbox('getValue');
		//var isSwitch = $('#isSwitch').selectBooleanCheckbox('getValue');
		if (iframe.$('#projTemplateAddForm').form('validate')) {
			if (vidateRemark(iframe.$('#remark').val(),
					'<spring:message code="com.glaway.ids.common.remarkLength"/>')) {
				if (vidateRepeat(iframe, '#projTemplateAddForm',
						'projTemplateController.do?vidateRepeat')) {
					var tmpId = postBasic(iframe,
							"projTemplateController.do?doSaveAsTemplate",
							"#projTemplateAddForm");
					debugger;
					if(undefined == tmpId || null == tmpId || "" == tmpId){
						return false;
					} else {
						//iframe.$.fn.lhgdialog("closeSelect");
						if(isEdit) {
						var url='projTemplateController.do?goAddProjTemplate&id='+tmpId;
						$("#addTemplateDialog").lhgdialog('open','url:'+url);
						iframe.$.fn.lhgdialog("closeSelect");
						}else{
							iframe.$.fn.lhgdialog("closeAll");
						}
						//iframe.closeA();
						return true;
					}
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	// 批量修改行编辑
		
		var editIndex = undefined;
		var currentIndex = undefined;
		var changeLink = false;
		function endEditing(){
			if (editIndex == undefined){
				return true
				}
			if ($('#projectList').datagrid('validateRow', editIndex)){
				$('#projectList').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		
		// 批量修改行编辑
		function onClickRowBatch(index) {
			debugger;
			var rows = $('#projectList').datagrid('getRows');
			var row2 = rows[index];
			if(row2.bizCurrent =='FEEDBACKING'||row2.bizCurrent =='FINISH'){
				return false;
			}
			if (currentIndex != undefined && !changeLink){
				workTimeOnChange();
			}
			
			currentIndex = index;
			changeLink = false;
			if (editIndex != index) {
				if (endEditing()) {
					
					var row = $('#projectList').datagrid('getSelected');
					if(row.fromTemplate == 'true'){
						$('#projectList').datagrid('hideColumn', 'planName');
						$('#projectList').datagrid('showColumn', 'parentPlanName');
					}else{
						$('#projectList').datagrid('showColumn', 'planName');
						$('#projectList').datagrid('hideColumn', 'parentPlanName');
					}
					
					$('#projectList').datagrid('selectRow', index).datagrid(
							'beginEdit', index);
					editIndex = index;
				} else {
					$('#projectList').datagrid('selectRow', editIndex);
				}
			}
		}
		
		function viewNameOnChange(){
	        var rows = $('#projectList').datagrid('getRows');
	        var row = rows[currentIndex];
	        var oldValue = row.name;
	        // projectNumber 
	        $('#projectList').datagrid('endEdit', currentIndex);
	        var val = $.trim(rows[currentIndex].projectNumber);
	        changeLink = true;
	        editIndex = undefined;
	        if(val == '' || val==undefined || val == null){
	            top.tip('<spring:message code="com.glaway.ids.pm.project.numCannotEmpty"/>');
				$('#projectList').datagrid('beginEdit', currentIndex);
				var ed = $('#projectList').datagrid("getEditor",{index:currentIndex,field:'name'});
				$(ed.target).val(oldValue);
				$('#projectList').datagrid('endEdit', currentIndex);
	            return false;
	        }
	        if(val != null && val != '' && val != undefined){
	            $.ajax({
	                url : 'planViewController.do?doSaveName',
	                type : 'post',
	                data : {
	                	 id : row.id,
	                     name : val,
	                     status : row.status
	                },
	                cache : false,
	                success : function(data) {
	                	var d = $.parseJSON(data);
	                	if(d.success) {          
	                		currentIndex = undefined;
	                		refreshView(row.id, val, 'update');
	                	} else {
	                		top.tip(d.msg);        
	                		$('#projectList').datagrid('beginEdit', currentIndex);
	                	}
	          
	                }
	            });
	        }
	    }
		
		function addTemplateSubmit(iframe){
			debugger;
			iframe.saveAll();
		}
		
		function closeAll(iframe){
			//iframe.frameElement.api.close();
			iframe.$.fn.lhgdialog("closeAll");
		}
</script>

