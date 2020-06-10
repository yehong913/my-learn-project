<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>项目分类</title>
	<t:base type="jquery,easyui,tools"></t:base>
	<script type="text/javascript">
	$(document).ready(function() {
		setTimeout('setDefaultSelectedNode()', 500)
	})
	
		
		function setDefaultSelectedNode(){
			var treeObj = $.fn.zTree.getZTreeObj("projEpstree");
		// 获取全部节点
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		// 查找设置的value
		if (nodes.length > 0) {
			var node = nodes[0];
			treeObj.selectNode(node);
			treeObj.setting.callback.onClick(null, node.id, node);
		}
		}
	
	function getSelectNode()
	{
		var node= getNode("projEpstree");
		return node;
	}
	</script>
</head>
 
<body>
	<div style="width: 100%; height: 200px;">
		<fd:tree treeIdKey="id" url="projEPSController.do?getEPSTree" id="projEpstree" treePidKey="pid"></fd:tree>
	</div>
</body>