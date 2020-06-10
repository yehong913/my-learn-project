<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<div id="projectTool">
			<fd:searchform id="searchProjectTag"
				onClickSearchBtn="searchProject()"
				onClickResetBtn="tagSearchResetProj()">
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
				<fd:combobox title="团队成员" id="isTeamMember" textField="text" 
				    editable="false" valueField="value" name="approve"  
				    data="true_是,false_全部" selectedValue="true"></fd:combobox>
				<fd:inputText
					title="{com.glaway.ids.pm.project.projectmanager.projectManager}"
					id="searchProjectManager" maxLength="-1" />
				<fd:combotree title="{com.glaway.ids.pm.project.projectmanager.type}"
					treeIdKey="id" url="projectController.do?epsCombotree"
					name="Project.eps" maxLength="-1" id="eps" treePidKey="pid"
					multiple="true" prompt="全部" panelHeight="300" treeName="name"
					queryMode="in"></fd:combotree>
				<input name="Project.epsInfo.id" id="searchEps" type="hidden" />
			</fd:searchform>
		</div>

		<fd:datagrid checkbox="true" toolbar="#projectTool" checked="true"
			fit="true" fitColumns="true" idField="id" checkOnSelect="false"
			id="projectList" url="projectController.do?searchDatagrid">
			<fd:dgCol
				title="{com.glaway.ids.pm.project.projectmanager.projectNumber}"
				field="projectNumber" formatterFunName="alignFormat" />
				
			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.projectName}"
				field="name" />

			<fd:dgCol
				title="{com.glaway.ids.pm.project.projectmanager.projectManager}"
				field="projectManagerNames" formatterFunName="alignFormat" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.phase}"
				field="phaseInfo" formatterFunName="showPhase" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.bizCurrent}"
				field="bizCurrent" formatterFunName="viewBizCurrent" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.type}"
				field="eps" formatterFunName="showEPS" sortable="false" />

			<fd:dgCol title="{com.glaway.ids.pm.project.projectmanager.creator}"
				field="createName" formatterFunName="showCreateName" />

		</fd:datagrid>
	</div>
</div>

<script type="text/javascript">

	function searchProject() {
		var searchProjectManager = $('#searchProjectManager').val();
		var isTeamMember = $('#isTeamMember').combobox("getValue");
		var queryParams = $("#projectList").fddatagrid('queryParams',
				"searchProjectTag");
		queryParams["searchProjectManager"] = searchProjectManager;
		queryParams['eps'] = '';
		$('#searchProjectTag').find('*').each(function() {
			if ($(this).attr('name') == 'Project.eps') {
				queryParams["eps"] += "," + $(this).val();
			}
		});

		$('#projectList').datagrid({
			url : 'projectController.do?searchDatagrid&entryPage=PLANVIEW&isTeamMember='+isTeamMember,
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
		$('#isTeamMember').combobox("setValue","true");
		$('#eps').comboztree("clear");
	}

	function alignFormat(val, row, value) {
		if (val == undefined)
			val = '';
		//return '<div style="text-align:left">' + val + '</div>';
		return shtml = '<span title="'+val+'">' + val + '</span>';
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
						resultUrl = '<div style="text-align:left">'
								+ s + '</div>'
					}
					return resultUrl;
				}
			}
		}
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

	function getProjectInfos() {
		var rows = $('#projectList').datagrid('getSelections');
        var ids = [];
        var names = [];
        if(rows.length > 0){
            for(var i = 0; i < rows.length; i++){
            	ids.push(rows[i].id);
            	names.push(rows[i].projectNumber + '-' + rows[i].name);
            }
            var win = $.fn.lhgdialog("getSelectParentWin");
            win.$('#projectNames').textbox("setValue",names);
            win.$('#projectIds').val(ids);
        }
        $.fn.lhgdialog("closeSelect");
	}
</script>

