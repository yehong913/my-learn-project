<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>模板项目库</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
<script type="text/javascript">
	$(document).ready(function() {
		setTimeout('setDefaultSelectedNode()', 500);
		loadLibPage("#menu_auth_page_panel_2",'${rootId}','<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePower"/>');	
	});

	function setDefaultSelectedNode() {
		var treeObj = $.fn.zTree.getZTreeObj("libMenu");
		// 获取全部节点
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		// 查找设置的value
		if (nodes.length > 0) {
			var node = nodes[0];
			treeObj.selectNode(node);
			treeObj.setting.callback.onClick(null, node.id, node);
		}
	}
	function loadLibPage(panelId, id, title) {
		if (id != null && id != '') {
			$(panelId)
					.panel(
							{
								href : 'projTemplateController.do?goPowerList&isView=1&templateId=${templateId}&fileId='
										+ id,
								title : title
							});
		}
	}
	// 名称链接事件
	function loadLib(event, treeId, treeNode) {
		debugger;
		 loadLibPage(
				"#menu_auth_page_panel_2",
				treeNode.id,
				'<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePower"/>'); 
	}
	// 名称链接事件
</script>
	<script type="text/javascript">

	</script>
	<div class="easyui-layout" fit="true">
		<div region="west" collapsible="false"
			style="padding: 1px; width: 150px"
			title="<spring:message code='com.glaway.ids.pm.project.projecttemplate.projecttemplate'/>"
			id="menu_left_page_panel_2">
			<fd:tree treeIdKey="id"
 				url="projTemplateController.do?getProjLibTree&templateId=${templateId}" 
				treeName="name" treeTitle="" id="libMenu" treePidKey="pid" 
 				onClickFunName="loadLib"/> 
		</div>
		<div region="center" collapsible="false">
			<div class="easyui-panel" title="&nbsp;" fit="true" border="false"
				id="menu_auth_page_panel_2"></div>
		</div>
	</div>
</body>
</html>
