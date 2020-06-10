<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html id="123">
<head>
<title>按时间查询计划</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	$(function() {

	})
	function saveAsView() {
		var viewId = $("#viewId").val();
		var showColumnIds = $("#showColumnIds").val();
		var name = $("#planViewName").val();
		if (name == null || name == undefined || name == '') {
			top
					.tip('<spring:message code="com.glaway.ids.pm.project.planview.nameCannotEmpty"/>');
			return;
		}
		$.ajax({
			url : 'planViewController.do?doViewSaveAs',
			type : 'post',
			data : {
				viewId : viewId,
				showColumnIds : showColumnIds,
				name : name,
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if ('true' == d.succuess) {
					var win = $.fn.lhgdialog("getSelectParentWin");

					$.fn.lhgdialog("closeSelect");
				}
			}
		})
	}
	function saveAsNewView() {
		debugger;
		var name = $('#planViewName').val();
		var viewId = $('#viewId').val();
		var showColumnIds = $('#showColumnIds').val();
		if (name == '' || name == null || name == undefined) {
			top
					.tip('<spring:message code="com.glaway.ids.pm.project.planview.nameCannotEmpty"/>');
			return;
		} else {
			
			if(viewId == '0' || viewId=='1' || viewId=='全部计划' || viewId=='私有视图')
			{
			 top.tip('<spring:message code="com.glaway.ids.pm.project.planview.notPublicPrivate"/>');
			 $.fn.lhgdialog("closeSelect");
			}else
				{
			$.ajax({
				url : 'planViewController.do?doViewSaveAs',
				type : 'post',
				data : {
					viewId : viewId,
					showColumnIds :showColumnIds,
					name : name,
					planNumber : '${planNumber}',
					planName : '${planName}',
					isDelay : '${isDelay}',
					planLevel : '${planLevel}',
					bizCurrent : '${bizCurrent}',
					userName : '${userName}',
					planStartTime : '${planStartTime}',
					planEndTime : '${planEndTime}',
					workTime : '${workTime}',
					workTime_condition : '${workTimeCondition}',
					progressRate : '${progressRate}',
					progressRate_condition : '${progressRateCondition}',
					taskNameType : '${taskNameType}',
					taskType : '${taskType}'
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
						top.tip(d.msg);
					if (d.success) {
						var win = $.fn.lhgdialog("getSelectParentWin");
						 var treeObj = win.$.fn.zTree.getZTreeObj("switchPlanViewTree");
		        		treeObj.reAsyncChildNodes(null, "refresh");
						$.fn.lhgdialog("closeSelect");
					}
				}
			})
				}
		}
	}
</script>
</head>
<body>
	<div id="plan" border="false" class="easyui-panel div-msg" fit="true">
		<div class="easyui-panel" fit="true" id="zhezhao">
			<fd:form id="planAddForm">
				<input id="viewId" name="viewId" type="hidden" value="${viewId }">
				<input id="showColumnIds" name="showColumnIds" type="hidden" value="${showColumnIds }">
				<fd:inputText id="planViewName" name="planViewName"
					title="{com.glaway.ids.pm.project.planView.planViewName}"
					readonly="false" required="true" value="${viewName}" />
			</fd:form>
		</div>
	</div>
</body>
</html>