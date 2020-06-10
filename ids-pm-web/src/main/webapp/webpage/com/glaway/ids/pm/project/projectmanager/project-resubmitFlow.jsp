<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>

<head>
<title>项目流程重新提交页面、项目审批中查看业务详情页面</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	//名称链接事件
	function loadUrl(event, treeId, treeNode) {
		debugger;
		var url = treeNode.dataObject.url;
		if (treeNode.name == "详细信息" || treeNode.name == "计划"
				|| treeNode.name == "团队" || treeNode.name == "项目库"
				|| treeNode.name == "风险清单" || treeNode.name == "问题") {
			url += "&isViewPage=true" + '&refreshTree=false'
					+ '&iframeTarget=resubmitFlow' + '&viewPlan=view'
					+ '&isView=${isView}';
		}
		loadPage("#process_right_page_panel", url, treeNode.name);
		
	}
</script>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			var isRefuse = '${project_.isRefuse}';
			var isView = '${isView}';
			if (isRefuse == "1" && isView != "true") { // 驳回状态的任务显示提交审批按钮
				$("#resubmitProjectTool").css('display', '');
			}
			$('#projectResubmitCloseBtn').focus();
			setTimeout('setDefaultSelectedNode()', 500);
		});

		function projResubmitFlow() {
			var resubmitUrl = 'projectController.do?resubmitProjectFlow&id='
					+ '${project_.id}' + '&oper=' + '${oper}';

			$.ajax({
				type : 'POST',
				url : resubmitUrl,
				data : '',
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
// 						W.getData();
						try{
		                	$.fn.lhgdialog("getSelectParentWin").parent.flashTaskShow();
		                }catch(e){
		                	
		                }
		                
		                try{
		                	$.fn.lhgdialog("getSelectParentWin").parent.$("#startTaskListMore_wait").datagrid('reload');
		                }catch(e){
		                	
		                }
						$.fn.lhgdialog("getSelectParentWin").tip(d.msg);
						$.fn.lhgdialog("closeSelect");
					}
				}
			});
		}
		
		
		function setDefaultSelectedNode(){
			var treeObj = $.fn.zTree.getZTreeObj("projectMenuList");
		// 获取全部节点
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		// 查找设置的value
		if (nodes.length > 0) {
			var node = nodes[1];
			treeObj.selectNode(node);
			treeObj.setting.callback.onClick(null, node.id, node);
		}
		}

		function createResubmitFlowDialog(dialogUrl) {
			createDialog('viewResubmitFlowDialog', dialogUrl);
		}
		function closeProjectResubmit() {
			$.fn.lhgdialog("closeSelect");
		}
	</script>
	<fd:dialog id="viewResubmitFlowDialog" width="750px" height="500px"
		modal="true" title="{com.glaway.ids.pm.project.plan.viewPlan}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.close}"
			callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	<c:if test="${(project_.isRefuse eq 1) && !(isView eq true)}">
		<div border="false" class="easyui-panel div-msg" fit="true">
	</c:if>
	<c:if test="${!(project_.isRefuse eq 1) || (isView eq true)}">
		<div border="false" class="easyui-panel" fit="true">
	</c:if>
		<div class="easyui-layout" fit="true">
			<div region="west" style="padding: 1px; width: 200px;border-right: 1px solid #cccccc;"
				title="<spring:message code='com.glaway.ids.pm.project.projectmanager.projectList'/>">
				<fd:tree treeIdKey="id"
					url="projectMenuController.do?list&projectId=${project_.id}"
					treeName="" treeTitle="" id="projectMenuList" treePidKey="pid"
					onClickFunName="loadUrl" />
			</div>
			<div region="center">
				<div class="easyui-panel" title="&nbsp;" fit="true" border="false"
					id="process_right_page_panel"></div>
			</div>
		</div>
	
		<div id="resubmitProjectTool" class="div-msg-btn" style="display: none;">
			<div>
				<fd:linkbutton id="resubmit" onclick="projResubmitFlow()"
					value="{com.glaway.ids.common.btn.submit}" classStyle="button_nor" />
				<fd:linkbutton onclick="closeProjectResubmit()"
					id="projectResubmitCloseBtn"
					value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" />
			</div>
		</div>
	</div>
</body>
<html>