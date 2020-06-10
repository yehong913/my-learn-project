<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>项目分类</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  	function getSelectNode(event, treeId, treeNode)
	{
  		var treeObj = $.fn.zTree.getZTreeObj("projLibtree");
  		var nodes = treeObj.getSelectedNodes();
  		debugger;
  		if(nodes.length>1)
  		{
  			$.messager.alert('<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.warn"/>', '<spring:message code="com.glaway.ids.pm.project.projectmanager.library.onlySelectOne"/>');
  			return null;
  		}	
  		var node = nodes[0];
  		var a = node.dataObject.indexOf('noPower');
  		if(a != -1){
  			top.tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.noAuth"/>');
  			return false;
  		}
        return node;
	}
  </script>
 </head>
 <body>
	<div style="width: 100%; height: 200px;">
	<fd:tree treeIdKey="id"
		url="projLibController.do?getProjLibTree&projectId=${projectId}&havePower=${havePower}&treeType=${treeType}" treeName=""
		treeTitle="" id="projLibtree" treePidKey="pid"
		onClickFunName="getSelectNode"/>
	</div>
<script type="text/javascript">
$(document).ready(
		function() {
			setTimeout('setDefaultSelectedNode()', 500);
		});


	function setDefaultSelectedNode(){
			var treeObj = $.fn.zTree.getZTreeObj("projLibtree");
		// 获取全部节点
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		// 查找设置的value
		if (nodes.length > 0) {
			var node = nodes[0];
			treeObj.selectNode(node);
			treeObj.setting.callback.onClick(null, node.id, node);
		}
		}
	
</script>
</body>