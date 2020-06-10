<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html id="123">
<head>
<title>按时间查询计划</title>
<t:base type="jquery,easyui_iframe,tools,DatePicker,lhgdialog"></t:base>
<script src="webpage/com/glaway/ids/pm/project/plan/planList.js"></script>
</head>
<body>
	<div id="plan" border="false" class="easyui-panel div-msg" fit="true">
		<div class="easyui-panel" fit="true" id="zhezhao">
			<fd:form id="planAddForm" help="helpDoc:ViewAccordingTime">
				<input id="viewId" name="viewId" type="hidden" value="${viewId }">
				<fd:inputText id="planViewName" name="planViewName"
					title="{com.glaway.ids.pm.project.planView.planViewName}"
					readonly="false" required="true" value="" />
				<%-- <fd:combobox id="taskTypeID"  textField="taskType" title="{com.glaway.ids.pm.project.planView.expandTo}" selectedValue="${ownerShow}" 
						onChange="ownerChange()" editable="true" valueField="id" required="true" 
					 	panelMaxHeight="200" url="planController.do?userList2&projectId=${plan_.projectId}" /> --%>

				<div style="display: inline-block">
					<fd:combobox id="timeId" name="timeId" required="true"
						title="{com.glaway.ids.pm.project.planView.timeCondition}"
						textField="name" selectedValue="year" valueField="id"
						editable="false" url="planViewController.do?timeDesign"
						onChange="onChangeLink('true')" />
					<fd:combobox id="yearId" name="yearId" required="true"
						editable="false" title="" textField="name"
						selectedValue="${tempYear }" valueField="id"
						url="planViewController.do?yearDesign" multiple="true"
						panelHeight="120" />
					<div id="seasonDivId">
						<fd:combobox id="seasonPkMonthId" name="seasonPkMonthId"
							required="false" editable="false" title="" textField="name"
							valueField="id" selectedValue="1"
							url="planViewController.do?seasonDesign" />
					</div>
				</div>
				<fd:combobox id="planRangeId" name="planRangeId"
					title="{com.glaway.ids.pm.project.planView.expandRange}"
					textField="monthRange" selectedValue="toComplete" valueField="id"
					data="toComplete_需完成的计划_checked,toStart_需开始的计划,execPlan_执行的计划" />
				<fd:selectBooleanCheckbox id="isSwitch" value="true"
					title="{com.glaway.ids.pm.project.planView.switchView}"
					name="isSwitch">
				</fd:selectBooleanCheckbox>
			</fd:form>
		</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#seasonDivId").hide();
		setTimeout('initDefaultYear()',200);
	})
	function initDefaultYear(){
		debugger;
		var yearName = $('#yearId').combobox("getText");
		var defaultYear = '${tempYear}'?'${tempYear}':new Date().getFullYear();
		url = 'planViewController.do?yearDesign' ;
		$('#yearId').combobox('clear');
		$('#yearId').combobox('reload', encodeURI(encodeURI(url)));
		$('#yearId').combobox({
			onLoadSuccess : function() {
				var arr = $('#yearId').combobox('getData');
				$('#yearId').combobox('setValue', defaultYear);
				$('#yearId').combobox('setText', defaultYear);
			}
		});
	}
	function setTimeCondition() {
		debugger;
		var isSwitch = $('#isSwitch').selectBooleanCheckbox('getValue');
		var viewId = $("#viewId").val();
		var viewName = $("#planViewName").val();
		var timeC = $("#timeId").combobox('getValue');
		var yearC = $("#yearId").combobox('getText');
		var seasonmonthC = $("#seasonPkMonthId").combobox('getText');
		var seasontext = $("#seasonPkMonthId").combobox('getText');
		var planRangeC = $("#planRangeId").combobox('getValue');
		if (viewName == null || viewName == undefined || viewName == '') {
             top.tip('<spring:message code="com.glaway.ids.pm.project.planview.nameCannotEmpty"/>');
             return;
        }	
		$.ajax({
			url : 'planViewController.do?doViewSetByTime',
			type : 'post',
			data : {
				isSwitch : isSwitch,
				viewId : viewId,
				viewName : viewName,
				timeC : timeC,
				yearC : yearC,
				seasonmonthC : seasonmonthC,
				planRangeC : planRangeC
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				debugger;
				top.tip(d.msg);
				if (d.success) {
					var win = $.fn.lhgdialog("getSelectParentWin");
					/* var treeObj = win.$.fn.zTree
							.getZTreeObj("switchPlanViewTree");
					treeObj.refresh(); */
					$.fn.lhgdialog("closeSelect");
					//切换视图
					if (isSwitch) {
						 win.switchPlanTreeReload(isSwitch,d.obj,viewName);
					}
				}
				else
					{
						if(d.msg=='视图名称重复<br/>'){
						}else
						{
							$.fn.lhgdialog("closeSelect");
						}
					}
			}
		})
	}
	function onChangeLink(isLink) {
		if ('true' == isLink)
			var va = $("#timeId").combobox('getValue');
		var url = "";
		debugger;
		{
			if (va == 'season') {
				$("#yearId").combobox({
					multiple : false
				});
				url = "planViewController.do?seasonDesign&type=" + va;
				$("#seasonPkMonthId").combobox("clear");
				$("#seasonPkMonthId").combobox('reload', url);
				$('#seasonPkMonthId').combobox(
						{
							multiple : true,
							onLoadSuccess : function() {
								$("#seasonPkMonthId").combobox('setValue','0');
								//$("#seasonPkMonthId").combobox('setValue','${curSM}');
							}
						});
				$("#seasonDivId").show();
			} else if (va == 'month') {
				$("#yearId").combobox({
					multiple : false
				});
				url = "planViewController.do?seasonDesign&type=" + va;
				$("#seasonPkMonthId").combobox("clear");
				$("#seasonPkMonthId").combobox('reload', url);
				$('#seasonPkMonthId').combobox(
						{
							multiple : true,
							onLoadSuccess : function() {
								$("#seasonPkMonthId").combobox('setValue','0');
								//$("#seasonPkMonthId").combobox('setValue','${curSM}');
							}
						});
				$("#seasonDivId").show();
			} else if (va == 'year') {
				$("#yearId").combobox({
					multiple : true
				});
				$("#seasonPkMonthId").combobox({
					multiple : false
				});
				$("#seasonDivId").hide();
				//$('#attr_selectUserDefaultVal').combobox('disableValidation');
			}

		}
	}
</script>
</html>