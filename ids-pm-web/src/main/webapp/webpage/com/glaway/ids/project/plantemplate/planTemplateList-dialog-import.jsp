<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<t:base type="jquery,easyui_iframe,tools"></t:base>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="center" style="padding: 1px;" style="height: auto">
			<div id="planTemplateList"
				style="padding: 3px;height: auto">
				<fd:searchform id="planTemplateTag"
					onClickSearchBtn="searchPlanTemplate2()"
					onClickResetBtn="tagSearchResetForPlanTemplate()">
					<fd:inputText title="{com.glaway.ids.pm.project.plantemplate.name}" maxLength="-1" name="name"
						id="planTemplateName" queryMode="like" />
					<fd:inputText title="{com.glaway.ids.common.lable.creator}" maxLength="-1" name="createName"
						id="creatorName" />
					<%-- <fd:inputDateTime name="PlanTemplate.createTime" title="创建时间" id="createTime" queryMode="le&&ge&&lt&&gt&&eq"></fd:inputDateTime> --%>
					<fd:inputDateRange id="createTime" interval="1"
						name="PlanTemplate.createTime" title="{com.glaway.ids.common.lable.createtime}"></fd:inputDateRange>
					<div style="display: none;">
						<fd:combobox id="PlanTemplateBizCurrent" maxLength="-1"
							textField="title" title="{com.glaway.ids.common.lable.status}" name="PlanTemplate.bizCurrent"
							multiple="true" editable="false" prompt="全部" valueField="name"
							url="planTemplateController.do?lifeCycleStatus" queryMode="eq"></fd:combobox>
					</div>
				</fd:searchform>
			</div>

			<fd:datagrid
				url="planTemplateController.do?conditionSearch&field=id&isDialog=true&datagrid=1"
				toolbar="#planTemplateList" idField="id" id="plantemplates"
				style="height: 290px;" fit="true" fitColumns="true">
				<fd:dgOptCol title="{com.glaway.ids.pm.project.plantemplate.name}" formatterFunName="planTemp_WPSPlanList"></fd:dgOptCol>
				<fd:dgCol field="remark" title="{com.glaway.ids.common.lable.remark}" />
				<fd:dgCol field="createName" title="{com.glaway.ids.common.lable.creator}" />
				<fd:dgCol field="createTimeStr" title="{com.glaway.ids.common.lable.createtime}" width="150" />

			</fd:datagrid>
					<fd:dialog id="allPlanImportSearchDialog2" width="940px" height="500px"
			modal="true" title="{com.glaway.ids.pm.project.plan.planTemplateDeatail}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
			<%-- 
		 	<fd:datagrid url="planTemplateController.do?datagrid&field=id"
			toolbar="#planTemplateList" idField="id"
			id="plantemplates" style="height: 300px;" fit="true" fitColumns="true">
			<fd:dgCol field="id" title="" hidden="true" />
			<fd:dgOptCol title="模板名称" formatterFunName="planTemp_WPSPlanList"></fd:dgOptCol>
			<fd:dgCol field="remark" title="备注" />
			<fd:dgCol field="createName" title="创建者" />
			<fd:dgCol field="createTimeStr" title="创建时间" width="150"/>
			</fd:datagrid>
			--%>

			<%-- 
			<fd:datagrid
				url="planTemplateController.do?datagrid&field=id&isDialog=true"
				toolbar="#planTemplateList" idField="id" id="plantemplates" 
				style="height: 300px;" fit="true" fitColumns="true">
				<fd:dgCol field="id" title="id" hidden="true" />
				<fd:dgOptCol title="模板名称" formatterFunName="WPSPlanList" ></fd:dgOptCol>
				<fd:dgCol field="remark" title="备注" />
				<fd:dgCol field="createName" title="创建者" />
				<fd:dgCol field="createTimeStr" title="创建时间" width="150" />
			</fd:datagrid>
		--%>
		</div>
	</div>
	<script type="text/javascript">
		function planTemp_WPSPlanList(val, row, value) {
			return '<a href="#" onclick="planTemp_openDialogWPSPlans(\''
					+ row.id + '\')"><font color=blue>' + row.name
					+ '</font></a>';
		}
		function planTemp_openDialogWPSPlans(param) {
			var url = 'planTemplateDetailController.do?planTemplateDetail&planTemplateId='+ param;
			$("#"+ 'allPlanImportSearchDialog2').lhgdialog("open","url:"+ url);

		}

		function getPlanTemplateDialog() {
			var rows = $("#plantemplates").datagrid('getSelections');
			if (rows.length == 1) {
				return rows[0];
			}
			else{
				return null;
			}
		}

		function searchPlanTemplate() {

			var queryParams = $('#plantemplates').datagrid('options').queryParams;
			$('#planTemplateTag')
					.find('*')
					.each(
							function() {

								if ($(this).attr('name') == 'PlanTemplate.bizCurrent') {
									queryParams[$(this).attr('name')] += ","
											+ $(this).val();
								} else if ($(this).attr('name') == 'PlanTemplate.bizCurrent_condition') {
									queryParams[$(this).attr('name')] = 'in';
								} else
									queryParams[$(this).attr('name')] = $(this)
											.val();
							});

			$('#plantemplates').datagrid({
				url : 'planTemplateController.do?conditionSearch',
				//queryParams : queryParams,
				pageNumber : 1
			});
		}
		function searchPlanTemplate2() {

			var queryParams = $("#plantemplates").fddatagrid('getQueryParams');
			//var queryParams = $('#plantemplates').datagrid('options').queryParams;
			queryParams['PlanTemplate.bizCurrent'] = '';

			/* $('#plantemplates').find('*').each(function() {
				
			if($(this).attr('name') == 'PlanTemplate.bizCurrent'){
				queryParams[$(this).attr('name')] += ","+$(this).val();
			}else
			if($(this).attr('name') == 'PlanTemplate.bizCurrent_condition'){
				queryParams[$(this).attr('name')] = 'in';
			}
			}) ;*/

			$('#plantemplates')
					.datagrid(
							{
								url : 'planTemplateController.do?conditionSearch&isDialog=true',
								queryParams : queryParams,
								pageNumber : 1
							});
			
			$('#plantemplates').datagrid('unselectAll');
			$('#plantemplates').datagrid('clearSelections');
			$('#plantemplates').datagrid('clearChecked');
		}
		function tagSearchResetForPlanTemplate() {
			$("#planTemplateName").textbox("clear");
			$("#creatorName").textbox("clear");
			$("#PlanTemplateBizCurrent").combobox("clear");
			/* $("#createTime").combo("clear"); */
			$("#createTime_BeginDate").datebox("clear");
			$("#createTime_EndDate").datebox("clear");

		}
	</script>
</body>
</html>