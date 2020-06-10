<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
<script type="text/javascript">
	var right_id = "#projectLibTemplate_right_page_panel"

	$(document).ready(
					function() {
						loadPage(
								right_id,
								'${rootId}'
								);
						setTimeout('setDefaultSelectedNode()', 500)
					});
	
	
		function setDefaultSelectedNode(){
				var treeObj = $.fn.zTree.getZTreeObj("libDocMenu");
			// 获取全部节点
			var nodes = treeObj.transformToArray(treeObj.getNodes());
			// 查找设置的value
			if (nodes.length > 0) {
				var node = nodes[0];
				treeObj.selectNode(node);
				treeObj.setting.callback.onClick(null, node.id, node);
			}
			}

	// 名称链接事件
	function loadLib(event, treeId, treeNode) {
		loadPage(
				right_id,
				treeNode.id);
	}

	function loadPage(panelId, id) {
		if (id != null && id != '') {
			$(panelId)
					.panel(
							{
								href : 'projectLibTemplateController.do?goPowerList&isView=1&templateId=${templateId}&fileId='
										+ id
							});
		}
	}
</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="north" style="height: 200px">
				<fd:inputText id="name" name="name"
					title="{com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateName}"
					maxLength="30" readonly="true" value="${name }"></fd:inputText>
			<fd:inputTextArea id="remark"
				title="{com.glaway.ids.common.lable.remark}" readonly="true"
				value="${remark }" name="remark" maxLength="200"></fd:inputTextArea>
		</div>
		<div region="west" style="padding: 1px; width: 20%; border: 0;"
			title="">
			<div class="easyui-layout" fit="true">
				<div region="south" style="padding: 0px; height: 100%; border: 0;"
					collapsible="false" title="<spring:message code='com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateConstruction'/>">
					<div class="easyui-layout" fit="true">
						<div id="projlib_left_page_panel" region="center"
							style="padding: 1px; width: 330px; broder: 0;" title="">
							<fd:tree treeIdKey="id"
								url="projectLibTemplateController.do?getProjectLibTemplateTree&templateId=${templateId}"
								lazy="true"
								lazyUrl="projectLibTemplateController.do?getProjectLibTemplateTree&templateId=${templateId}"
								id="libDocMenu" treePidKey="pid" treeName="name"
								treeTitle="title" onClickFunName="loadLib" />
						</div>
					</div>
				</div>

			</div>
		</div>
		<div region="east" style="padding: 1px; width: 80%; border: 0;"
			title="" collapsible="false">
			<div class="easyui-layout" fit="true">
				<div region="south" style="padding: 0px; height: 100%; border: 0;"
					collapsible="false"
					title="<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePower"/>">
					<div id="projectLibTemplate_right_page_panel" region="center"
						style="border: 0; width: 820px;"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>