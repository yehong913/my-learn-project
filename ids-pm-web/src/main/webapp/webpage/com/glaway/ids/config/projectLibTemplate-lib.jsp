<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
<script type="text/javascript">
	var right_id = "#projectLibTemplate_right_page_panel";
	var currentNodeId = '';

	$(document).ready(function() {
		currentNodeId = '${rootId}';
		loadPage(right_id, currentNodeId, '<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePower"/>');
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
		if (currentNodeId == '') {
			currentNodeId = treeNode.id;
			loadPage(right_id, treeNode.id, '<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePower"/>');
		} else {
			checkRoleFileAuthExistChange(treeNode.id);
		}
	}

	function loadPage(panelId, nodeId, title) {
		currentNodeId = nodeId;
		if (nodeId != null && nodeId != '') {
			$(panelId)
					.panel(
							{
								href : 'projectLibTemplateController.do?goPowerList&templateId=${templateId}&fileId='
										+ nodeId,
								title : title
							});
		}
	}

	function checkRoleFileAuthExistChange(newNodeId) {
		var splitFlag = '${splitFlag}';
		var templateId = '${templateId}';
		var resultStr = '';
		var boxes = document.getElementsByName("checked");
		for (var i = 0; i < boxes.length; i++) {
			if (boxes[i].checked == true) {
				resultStr += '1';
			} else {
				resultStr += '0';
			}
			if ((i + 1) % splitFlag == 0 && i != 0) {
				resultStr += ',';
			}
		}
		$.ajax({
			url : 'projectLibTemplateController.do?doCheck',
			type : 'post',
			data : {
				templateId : templateId,
				fileId : currentNodeId,
				resultStr : resultStr
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePowerChange"/>', function(r) {
						if (r) {
							saveBaseInfoOnChange(templateId, currentNodeId,
									resultStr, newNodeId);
						} else {
							loadPage(right_id, newNodeId, '<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePower"/>');
						}
					});
				} else {
					loadPage(right_id, newNodeId, '<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePower"/>');
				}
			}
		});
	}

	function saveBaseInfoOnChange(templateId, fileId, resultStr, newNodeId) {
		$.ajax({
			url : 'projectLibTemplateController.do?doSave',
			type : 'post',
			data : {
				templateId : templateId,
				fileId : fileId,
				resultStr : resultStr
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				top.tip(d.msg);
				if (d.success) {
					loadPage(right_id, newNodeId, '<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePower"/>');
				}
			}
		});
	}

	function addTreeNode() {
		var parentId = '';
		var treeObj = $.fn.zTree.getZTreeObj("libDocMenu");
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length > 1) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.onlySelectOne"/>');
			return;
		} else if (nodes.length == 0) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.mustSelectOne"/>');
			return;
		}
		var node = nodes[0];
		parentId = node.id;
		/* 		if (parentId == null || parentId.length != 32) {
		 tip('<spring:message code="com.glaway.ids.common.tree.selectRoot"/>');
		 return;
		 } */
		var url = 'projectLibTemplateController.do?goAddTreeNode&templateId=${templateId}&parentId='
				+ parentId;
		createDialog('addTreeNodeDialog', url);
	}

	function addTreeNodeOkFunction(iframe) {
		saveOrUp(iframe);
		//window.setTimeout('refresh("libDocMenu")', 500);//等待0.5秒，让分类树结构加载出来
		$('#projectLibTemplate_right_page_panel').panel('open').panel('clear');
		currentNodeId = '';
		return false;
	}

	function updateTreeNode() {
		var treeObj = $.fn.zTree.getZTreeObj("libDocMenu");
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length > 1) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.onlySelectOne"/>');
			return;
		} else if (nodes.length == 0) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.mustSelectOne"/>');
			return;
		}
		var node = nodes[0];
		if (node != null) {
			if (node.pid == 'null') {
				tip('<spring:message code="com.glaway.ids.common.tree.notOperateRoot"/>');
				return;
			}
		} else {
			tip('<spring:message code="com.glaway.ids.common.selectOperate"/>');
			return;
		}
		var url = 'projectLibTemplateController.do?goUpdateTreeNode&templateId=${templateId}&id='
				+ node.id;
		createDialog('updateTreeNodeDialog', url);
	}

	function updateTreeNodeOkFunction(iframe) {
		saveOrUp(iframe);
		//window.setTimeout('refresh("libDocMenu")', 500);//等待0.5秒，让分类树结构加载出来
		$('#projectLibTemplate_right_page_panel').panel('open').panel('clear');
		currentNodeId = '';
		return false;
	}

	function deleteTreeNode() {
		var treeObj = $.fn.zTree.getZTreeObj("libDocMenu");
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length > 1) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.onlySelectOne"/>');
			return;
		} else if (nodes.length == 0) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.mustSelectOne"/>');
			return;
		}
		var node = nodes[0];
		if (node != null) {
			if (node.pid == 'null') {
				tip('<spring:message code="com.glaway.ids.common.tree.notOperateRoot"/>');
				return;
			}
			checkChildrenAndDelete(node.id);
		} else {
			tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
			return;
		}
	}

	function checkChildrenAndDelete(treeNodeId) {
		$.ajax({
			url : 'projectLibTemplateController.do?checkTreeNode',
			type : 'post',
			data : {
				id : treeNodeId
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					tip('<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateNodeExitsChildren"/>');//等待0.5秒，让分类树结构加载出来
				} else {
					doDelete(treeNodeId);
				}
			}
		});
	}

	function doDelete(treeNodeId) {
		top.Alert.confirm('<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateNodeNeedDelete"/>', function(r) {
			if (r) {
				$.ajax({
					url : 'projectLibTemplateController.do?doDeleteTreeNode',
					type : 'post',
					data : {
						id : treeNodeId
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							window.setTimeout('refresh("libDocMenu")', 500);//等待0.5秒，让分类树结构加载出来
							setTimeout('setDefaultSelectedNode()', 1000)
						}
					}
				});
			}
		});
	}

	function onDrag() {

	}
	function onDrop(a, b, c, d, e) {

	}
	function beforeDrag(a, b, c) {
		var treeObj = $.fn.zTree.getZTreeObj("libDocMenu");
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length > 1) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.onlySelectOne"/>');
			return false;
		} else if (nodes.length == 0) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.mustSelectOne"/>');
			return false;
		}
		var node = nodes[0];
		if (node != null) {
			if (node.pid == 'null') {
				tip('<spring:message code="com.glaway.ids.common.tree.notOperateRoot"/>');
				return false;
			}
		}
	}
	function beforeDrop(treeId, treeNode, targetNode, moveType, isCopy) {
		if (targetNode.pid == 'null' && moveType != 'inner') {
			tip('<spring:message code="com.glaway.ids.common.tree.notOperateRoot"/>');
			return false;
		}
		var result = 'true';
		$.ajax({
			url : 'projectLibTemplateController.do?moveTreeNode',
			type : 'post',
			async : false,
			data : {
				targetId : targetNode.id,
				moveType : moveType,
				name : treeNode[0].name,
				id : treeNode[0].id
			},
			success : function(data) {
				var d = $.parseJSON(data);
				var msg = d.msg;
				if (!d.success) {
					result = 'false';
					tip(msg);
				}
			}
		});
		if (result == 'false') {
			return false;
		} 
	}
	function onDragMove() {

	}
		
	function setParentSelectedNode(parentId,node,type) {
		debugger
		var treeObj = $.fn.zTree.getZTreeObj("libDocMenu");
		//刷新树
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		if (nodes.length > 0) {
			var selectNode= getParentNode(parentId);
			if(null != type && type == 'update'){
				var treeObj = $.fn.zTree.getZTreeObj("libDocMenu");
				//刷新树
				selectNode.title=node;
				selectNode.name=node;
				treeObj.updateNode(selectNode);
				treeObj.selectNode(selectNode)
				treeObj.setting.callback.onClick(null, selectNode.id, selectNode);
			}else{
				treeObj.addNodes(selectNode, node, true);
				treeObj.setting.callback.onClick(null, selectNode.id, selectNode);
			}
		}
	}
	
	function getParentNode(parentId) {
		var treeObj = $.fn.zTree.getZTreeObj("libDocMenu");
		// 获取全部节点
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		// 查找设置的value
		var flagInfo = false; 
		var endNode = "";
		if (nodes.length > 0) {
			for(var i=0; i<nodes.length; i++) {
				var curId = nodes[i].id;
				if(curId == parentId){
					flagInfo = true;
					endNode = nodes[i];
					break;
				}
			}
			
		}
		if(flagInfo){
			return endNode;
			
		}
	}

</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="west" style="padding: 1px; width: 20%; border: 0;"
			title="">
			<div class="easyui-layout" fit="true">
				<div region="south" style="padding: 0px; height: 100%; border: 0;"
					collapsible="false" title="<spring:message code='com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateConstruction'/>">
					<div class="easyui-layout" fit="true">
						<div id="projlib_left_page_panel" region="center"
							style="padding: 1px; width: 330px; broder: 0;" title="">
							<fd:toolbar id="projectLibTemplateOperation">
								<fd:toolbarGroup align="left">
									<fd:linkbutton id="addTreeNoderesButton"
										onclick="addTreeNode()" value=""
										iconCls="l-btn-icon basis ui-icon-plus" />
									<fd:tooltip id="addTreeNoderesTip" forId="addTreeNoderesButton"
										content="{com.glaway.ids.common.btn.create}" />
									<fd:linkbutton id="updateTreeNoderesButton"
										onclick="updateTreeNode()" value=""
										iconCls="l-btn-icon basis ui-icon-pencil" />
									<fd:tooltip id="updateTreeNoderesTip"
										forId="updateTreeNoderesButton" content="{com.glaway.ids.common.btn.edit}" />
									<fd:linkbutton id="deleteTreeNodesresButton"
										onclick="deleteTreeNode()" value=""
										iconCls="l-btn-icon basis ui-icon-minus" />
									<fd:tooltip id="deleteTreeNodesresTip"
										forId="deleteTreeNodesresButton" content="{com.glaway.ids.common.btn.remove}" />
								</fd:toolbarGroup>
							</fd:toolbar>
							<fd:tree treeIdKey="id"
								url="projectLibTemplateController.do?getProjectLibTemplateTree&templateId=${templateId}"
								lazy="true" dnd="true"
								lazyUrl="projectLibTemplateController.do?getProjectLibTemplateTree&templateId=${templateId}"
								id="libDocMenu" treePidKey="pid" treeName="name"
								treeTitle="title" onClickFunName="loadLib"
								beforeDrag="beforeDrag" beforeDrop="beforeDrop" onDrag="onDrag"
								onDrop="onDrop" onDragMove="onDragMove" />
						</div>
					</div>
				</div>

			</div>
		</div>
		<div region="east" style="padding: 1px; width: 80%;" title=""
			collapsible="false">
			<div id="projectLibTemplate_right_page_panel"
				style="border: 0; width: 830px;"></div>
		</div>
	</div>
	<fd:dialog id="addTreeNodeDialog" width="800px" height="300px"
		modal="true" title="{com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateAddNode}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addTreeNodeOkFunction"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="updateTreeNodeDialog" width="800px" height="300px"
		modal="true" title="{com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplateUpdateNode}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="updateTreeNodeOkFunction"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
</body>
</html>