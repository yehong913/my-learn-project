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
			setTimeout('setDefaultSelectedNode()', 500);
			loadPage("#menu_right_page_panel","projTemplateController.do?goTemplateDetail&id=${taskNumber}","");
		}); 

	function setDefaultSelectedNode(){
			var treeObj = $.fn.zTree.getZTreeObj("templateMenu");
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
	function loadUrl(event, treeId, treeNode) {
		loadPage("#menu_right_page_panel", treeNode.dataObject.url,treeNode.name);
	}

	function refreshRightPage() {
		$("#menu_right_page_panel").panel("open").panel("refresh");
	}
	function backSubmit2() {
		debugger;
		
		$.ajax({
			type : 'POST',
			url : 'projTemplateController.do?doBackSubmit&projTmpId=${taskNumber}&taskId=${taskId}',
			data : {
				taskId : '${taskId}',
				projTmpId : '${taskNumber}'
			},
			success : function(data) {
// 				W.getData();
				try{
                	$.fn.lhgdialog("getSelectParentWin").parent.flashTaskShow();
                }catch(e){
                	
                }
                
                try{
                	$.fn.lhgdialog("getSelectParentWin").parent.$("#startTaskListMore_wait").datagrid('reload');
                }catch(e){
                	
                }
				$.fn.lhgdialog("closeSelect");
			}
		});
	}
	function closePlan() {
		$.fn.lhgdialog("closeSelect");
	}
	 
</script>
</head>

<body>
	<div class="easyui-layout" fit="true">
		<div region="west" style="padding: 1px; width: 150px" title="<spring:message code='com.glaway.ids.pm.project.projecttemplate.projecttemplate'/>" id="menu_left_page_panel">
			<fd:tree treeIdKey="id"
				url="projTemplateController.do?getProjTemplateLayout&projectTemplateId=${taskNumber}" treeName=""
				treeTitle="" id="templateMenu" treePidKey="pid"
				onClickFunName="loadUrl"  />
		</div>
		<div region="center">
			<div class="easyui-panel" title="&nbsp;" fit="true" border="false" id="menu_right_page_panel">
			</div>
		</div>
		<div region="south" style="padding: 1px; height: 60px">
			<div>
				<center>
				<fd:toolbarGroup align="right">
					<fd:linkbutton id="backSubmit" onclick="backSubmit2()" value="提交审批" classStyle="button_nor" />
					<fd:linkbutton onclick="closePlan()" value="{com.glaway.ids.common.btn.cancel}" classStyle="button_nor" id="focusThree" />
				</fd:toolbarGroup>
			</div>
		</div>
	</div>
</body>