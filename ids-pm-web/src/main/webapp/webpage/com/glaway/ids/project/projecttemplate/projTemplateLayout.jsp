<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>模板详情</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
var right_id="";
$(document).ready(
		function() {
			setTimeout('setDefaultSelectedNode()', 1000);
		});


	function setDefaultSelectedNode(){
		debugger;
			var treeObj = $.fn.zTree.getZTreeObj("templateMenu");
		// 获取全部节点
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		// 查找设置的value
		if (nodes.length > 1) {
			var node = nodes[1];
			treeObj.selectNode(node);
			treeObj.setting.callback.onClick(null, node.id, node);
		}
		}
	// 名称链接事件
	function loadUrl(event, treeId, treeNode) {
		var url = treeNode.dataObject.url+"&viewHistory=${viewHistory}";
		loadPage("#menu_right_page_panel", url,treeNode.name);
	}

	function refreshRightPage() {
		$("#menu_right_page_panel").panel("open").panel("refresh");
	}
	 
	/*$(document).ready(function() {
		loadPage("#menu_right_page_panel","projTemplateController.do?goTemplateDetail&id=${projectTemplateId}","");
	});*/
</script>
</head>

<body>
	<div class="easyui-layout" fit="true">
		<div region="west" style="padding: 1px; width: 150px" title="<spring:message code='com.glaway.ids.pm.project.projecttemplate.projecttemplate'/>" id="menu_left_page_panel">
			<fd:tree treeIdKey="id"
				url="projTemplateController.do?getProjTemplateLayout&projectTemplateId=${projectTemplateId}&viewHistory=${viewHistory}" treeName=""
				treeTitle="" id="templateMenu" treePidKey="pid"
				onClickFunName="loadUrl"  />
				
		</div>
		<div region="center">
			<div class="easyui-panel" title="&nbsp;" fit="true" border="false" id="menu_right_page_panel">
			</div>
		</div>
	</div>
</body>