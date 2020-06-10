<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<input id="basicLineStatusList" name="basicLineStatusList" type="hidden" value="${basicLineStatusList}">
		<div id="basicLineListtb">
			<fd:searchform id="seachBasicLineTag" onClickSearchBtn="searchBasicLine()" onClickResetBtn="tagSearchReset()" help="helpDoc:BaselineManagement">
				<fd:inputText title="{com.glaway.ids.common.lable.name}" name="BasicLine.basicLineName" id="basicLineName" queryMode="like" />
				<fd:inputText title="{com.glaway.ids.common.lable.creator}" id="userName" />
				<fd:inputDateRange id="createTimeRangeForBasicLine" interval="0" title="{com.glaway.ids.common.lable.createtime}"
					name="BasicLine.createTime" opened="0" />
				<fd:combobox id="searchBizCurrentForBasicLine" textField="title"
					title="{com.glaway.ids.common.lable.status}" name="BasicLine.bizCurrent" editable="false"
					valueField="name" multiple="true" prompt="全部"
					url="/ids-pm-web/basicLineController.do?statusList" queryMode="in" />
			</fd:searchform>

			<fd:toolbar id="toolbar">
				<fd:toolbarGroup align="left">
					<fd:linkbutton id="addBasicLine" onclick="addBasicLine()"
						value="{com.glaway.ids.pm.project.plan.basicLine.addBasic}" iconCls="basis ui-icon-plus"
						operationCode="basicLineAdd" />
					<fd:linkbutton id="contrastBasicLine" onclick="contrastBasicLine()"
						value="{com.glaway.ids.pm.project.plan.basicLine.basicCompare}" iconCls="basis ui-icon-baselinecontrast"
						operationCode="basicLineContrast" />
					<fd:linkbutton id="contrastCurrentPlan" onclick="contrastCurrentPlan()"
						value="当前计划对比" iconCls="basis ui-icon-baselinecontrast"/>
				</fd:toolbarGroup>
			</fd:toolbar>
		</div>

		<fd:datagrid id="basicLineList" checkbox="true" toolbar="#basicLineListtb" fitColumns="true"
				url="/ids-pm-web/basicLineController.do?searchDatagrid&projectId=${projectId}" idField="id" fit="true">
			<fd:colOpt title="{com.glaway.ids.common.lable.operation}" width="7">
				<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.plan.basicLine.copy}" iconCls="basis ui-icon-copy"
					hideOption="hideCopy()" onClick="copyBasicLine"
					operationCode="basicLineCopyCol"></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.remove}" iconCls="basis ui-icon-minus"
					hideOption="hideDelete()" onClick="deleteBasicLine"
					operationCode="basicLinedeleteCol"></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.submit}"
					iconCls="basis ui-icon-submitted_approval"
					hideOption="hideSubmit()" onClick="submitBasicLine"
					operationCode="basicLineSubmitCol"></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.plan.basicLine.froze}" iconCls="basis ui-icon-frozen"
					hideOption="hideFreeze()" onClick="freezeBasicLine"
					operationCode="basicLineFrzzezCol"></fd:colOptBtn>
				<fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.plan.basicLine.fire}" iconCls="basis ui-icon-thaw"
					hideOption="hideThaw()" onClick="thawBasicLine"
					operationCode="basicLineThawCol"></fd:colOptBtn>
			</fd:colOpt>
			<fd:dgCol title="{com.glaway.ids.common.lable.name}" field="basicLineName" width="20"
					formatterFunName="viewBasicLineInfo"  sortable="true" />
			<fd:dgCol title="{com.glaway.ids.common.lable.creator}" field="createByInfo" width="5" formatterFunName="viewUserRealName" sortable="true" />
			<fd:dgCol title="{com.glaway.ids.common.lable.createtime}" field="createTime" width="10" sortable="true" />
			<fd:dgCol title="{com.glaway.ids.common.lable.remark}" field="remark" width="50" sortable="true" />
			<fd:dgCol title="{com.glaway.ids.common.lable.status}" field="bizCurrent" width="5" formatterFunName="viewPlanBizCurrent" sortable="true" />
		</fd:datagrid>
	</div>
</div>

<fd:dialog id="addBasicLineDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.basicLine.addBasic}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addBasicLineDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="copyBasicLineDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.basicLine.copyBasic}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="copyBasicLineDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="submitBasicLineDialog" width="400px" height="150px" modal="true" title="{com.glaway.ids.pm.project.plan.basicLine.submitBasic}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="submitBasicLineDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="viewBasicLineDialog" width="750px" height="500px" modal="true" title="{com.glaway.ids.pm.project.plan.basicLine.basicInfo}">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="contrastCurrentPlanDialog" width="750px" height="400px" modal="true" title="选择当前计划">
	<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="contrastCurrentPlanDialog"></fd:dialogbutton>
	<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>

<fd:dialog id="showBasicLine" width="1380px" height="600px" modal="true" title="基线对比" maxFun="true">
</fd:dialog>

<script type="text/javascript">
	function searchBasicLine() {
		var userName = $('#userName').val();
		var queryParams = $("#basicLineList").fddatagrid('queryParams', "seachBasicLineTag");
		queryParams["userName"] = userName;
		queryParams['bizCurrent'] = '';

		$('#seachBasicLineTag').find('*').each(function() {
			if ($(this).attr('name') == 'BasicLine.bizCurrent') {
				queryParams["bizCurrent"] += "," + $(this).val();
			}
		});

		$('#basicLineList') .datagrid( {
			url : '/ids-pm-web/basicLineController.do?searchDatagrid&projectId=${projectId}',
			pageNumber : 1,
			queryParams : queryParams
		});

		$('#basicLineList').datagrid('unselectAll');
		$('#basicLineList').datagrid('clearSelections');
		$('#basicLineList').datagrid('clearChecked');
	}

	//重置
	function tagSearchReset() {
		$('#basicLineName').textbox("clear");
		$("#createTimeRangeForBasicLine_BeginDate").datebox("clear");
		$("#createTimeRangeForBasicLine_EndDate").datebox("clear");
		$('#userName').textbox("clear");
		$('#searchBizCurrentForBasicLine').combobox("clear");
	}
</script>
<script type="text/javascript">
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

	// 时间格式化
	function dateFormatter(val, row, value) {
		return dateFmtYYYYMMDD(val);
	}

	//生命周期状态name、title转换
	function viewPlanBizCurrent(val, row, index) {
		var status;
		var basicLineStatusList = eval($('#basicLineStatusList').val());
		for (var i = 0; i < basicLineStatusList.length; i++) {
			if (val == basicLineStatusList[i].name) {
				status = basicLineStatusList[i].title;
			}
		}

		//如果没有流程，则不需要点击
		var resultUrl = status;

		if ("1" == row.flowFlag) {
			resultUrl = '<a href="#" onclick="openFlowBasicLines(\'' + row.id
					+ '\')"><font color=blue>' + status + '</font></a>';
		}

		return resultUrl;
	}

	//弹出对象对应的流程
	function openFlowBasicLines(taskNumber) {
		var tabTitle = '<spring:message code="com.glaway.ids.pm.project.plan.basicLine.basictaskflow"/>';
		var url = '/ids-pm-web/taskFlowCommonController.do?taskFlowList&taskNumber=' + taskNumber;
		createdetailwindow_close(tabTitle, url, 800, 400);
	}

	function basicLineListSearch() {
		$('#basicLineList').datagrid('reload');
	}

	// 新增基线
	function addBasicLine() {
		var dialogUrl = '/ids-pm-web/basicLineController.do?goAddBasicLine&projectId=${projectId}';
		createDialog('addBasicLineDialog', dialogUrl);
	}

	function addBasicLineDialog(iframe) {
		saveOrUp(iframe);
		return false;
	}

	// 复制基线
	function copyBasicLine(a, b) {
		$('#basicLineList').datagrid('unselectAll');
		$('#basicLineList').datagrid('selectRow', b);
		var row = $('#basicLineList').datagrid('getSelected');
		var dialogUrl = '/ids-pm-web/basicLineController.do?goCopyBasicLine&projectId=${projectId}&id=' + row.id;
		createDialog('copyBasicLineDialog', dialogUrl);
	}

	function copyBasicLineDialog(iframe) {
		saveOrUp(iframe);
		return false;
	}

	// 删除基线
	function deleteBasicLine(a, b) {
		$('#basicLineList').datagrid('unselectAll');
		$('#basicLineList').datagrid('selectRow', b);
		var row = $('#basicLineList').datagrid('getSelected');
		top.Alert.confirm('<spring:message code="com.glaway.ids.common.confirmDel"/>', function(r) {
			if (r) {
				$.ajax({
					url : '/ids-pm-web/basicLineController.do?doDelBasicLine&id=' + row.id,
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
							$('#basicLineList').datagrid('reload');
						}
					}
				});
			}
		});
	}

	// 提交基线
	function submitBasicLine(a, b) {
		$('#basicLineList').datagrid('unselectAll');
		$('#basicLineList').datagrid('selectRow', b);
		var row = $('#basicLineList').datagrid('getSelected');
		var dialogUrl = '/ids-pm-web/basicLineController.do?goSubmitBasicLine&id=' + row.id;
		createDialog('submitBasicLineDialog', dialogUrl);
	}

	function submitBasicLineDialog(iframe) {
		saveOrUp(iframe);
		return false;
	}

	// 冻结基线
	function freezeBasicLine(a, b) {
		$('#basicLineList').datagrid('unselectAll');
		$('#basicLineList').datagrid('selectRow', b);
		var row = $('#basicLineList').datagrid('getSelected');
		top.Alert.confirm('<spring:message code="com.glaway.ids.pm.project.plan.basicLine.confirmFroz"/>', function(r) {
			if (r) {
				$.ajax({
					url : '/ids-pm-web/basicLineController.do?doFrozeBasicLine&id=' + row.id,
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
							$('#basicLineList').datagrid('reload');
						}
					}
				});
			}
		});
	}

	// 解冻基线
	function thawBasicLine(a, b) {
		$('#basicLineList').datagrid('unselectAll');
		$('#basicLineList').datagrid('selectRow', b);
		var row = $('#basicLineList').datagrid('getSelected');
		top.Alert.confirm('<spring:message code="com.glaway.ids.pm.project.plan.basicLine.confirmFire"/>', function(r) {
			if (r) {
				$.ajax({
					url : '/ids-pm-web/basicLineController.do?doUseBasicLine&id=' + row.id,
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
							$('#basicLineList').datagrid('reload');
						}
					}
				});
			}
		});
	}

	// 计划名称链接事件
	function viewBasicLineInfo(val, row, index) {
		if(val == undefined)val = '';
		shtml = '<span title="'+val+'">'+val+'</span>';
		return '<a href="#" onclick="viewBasicLine(\'' + row.id + '\')" style="color:blue">' + shtml + '</a>';
	}

	// 查看计划信息
	function viewBasicLine(id) {
		var dialogUrl = '/ids-pm-web/basicLineController.do?goBasicLineView&taskNumber=' + id;
		createDialog('viewBasicLineDialog', dialogUrl);
	}

	//当前计划进行对比
	function contrastCurrentPlan() {
		var rows = $('#basicLineList').datagrid('getSelections');
		if (rows.length == 1) {
			if (rows[0].bizCurrent != 'ENABLE') {
				tip('请选择一条启用的基线和当前计划进行对比');
				return false;
			}
			var dialogUrl = '/ids-pm-web/basicLineController.do?goContrastCurrentPlan&projectId=${projectId}&selectedId=' + rows[0].id;
			createDialog('contrastCurrentPlanDialog', dialogUrl);
		} else {
			tip('请选择一条启用的基线和当前计划进行对比');
			return false;
		}
	}

	function contrastCurrentPlanDialog(iframe) {
		saveOrUp(iframe);
		return false;
	}

	//基线对比
	function contrastBasicLine() {
		var rows = $('#basicLineList').datagrid('getSelections');
		for (var i = 0; i < rows.length; i++) {
			if (rows[i].bizCurrent != 'ENABLE') {
				tip('<spring:message code="com.glaway.ids.pm.project.plan.basicLine.exitNousedBasic"/>');
				return false;
			}
		}
		if (rows.length == 2) {
			$('#showBasicLine').lhgdialog('open','url:/ids-pm-web/basicLineController.do?basicLineContrast&id1='+ rows[0].id + '&id2=' + rows[1].id);
			/* window.open(
						"webpage/com/glaway/ids/project/plan/basicLineCompare.jsp?id1="
								+ rows[0].id + "&id2=" + rows[1].id,
						'ganttView',
						'height='
								+ screen.height
								+ ',width='
								+ screen.width
								+ ',toolbar=no,menubar=yes,scrollbars=yes,location=yes,status=no'); */
		} else {
			tip('<spring:message code="com.glaway.ids.pm.project.plan.basicLine.choseBasic"/>');
		}
	}

	function hideCopy(row, index) {
		if (row.bizCurrent == 'APPROVING' || row.bizCurrent == 'EDITING'
				|| row.bizCurrent == 'ENABLE' || row.bizCurrent == 'FREEZING') {
			return false;
		} else {
			return true;
		}
	}

	function hideDelete(row, index) {
		if (row.bizCurrent == 'EDITING') {
			return false;
		} else {
			return true;
		}
	}

	function hideSubmit(row, index) {
		if (row.bizCurrent == 'EDITING') {
			return false;
		} else {
			return true;
		}
	}

	function hideFreeze(row, index) {
		if (row.bizCurrent == 'ENABLE') {
			return false;
		} else {
			return true;
		}
	}

	function hideThaw(row, index) {
		if (row.bizCurrent == 'FREEZING') {
			return false;
		} else {
			return true;
		}
	}
</script>

