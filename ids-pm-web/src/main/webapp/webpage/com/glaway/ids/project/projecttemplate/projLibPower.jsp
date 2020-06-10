<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
<script type="text/javascript">
var right_id = "#projlib_right_page_panel";
var treeNodeUrl = "";
var fileIdRm = '${fileIdRm}';
var begin = "0";
$(document).ready(
		function() {
			loadPage("" + right_id, '${url}', '<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.authConfig"/>'); 
			setTimeout('setDefaultSelectedNode()', 500);
		});


	function setDefaultSelectedNode(){
			var treeObj = $.fn.zTree.getZTreeObj("libMenu2");
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
	function loadLibUrl(event, treeId, treeNode) {
		var boxes = document.getElementsByName("checked");
		var checks = "";
		for(var i=0;i<boxes.length;i++){
				if(boxes[i].checked == true){
					checks = checks + '1';
				}else{
					checks = checks + '0';
				}
		}
		var url1="projTemplateController.do?checkSave";
		$.ajax({
			url :url1,
			type : 'post',
			data : {
				'checks' : checks,
				'fileId' : fileIdRm,
				'templateId' : '${templateId}',
				'begin' : begin
			},
			cache : false,
			async : false,
			success : function(data) {
				var d = $.parseJSON(data);
				begin = "1";
				debugger;
				if (d.success) {
					//treeNodeUrl = treeNode.dataObject+"&id="+treeNode.id;
					fileIdRm = treeNode.id;
					//loadPage("" + right_id, treeNode.dataObject+"&id="+treeNode.id, '<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.authConfig"/>');
					loadLibPage(
							"#projlib_right_page_panel",
							treeNode.id,
							'<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePower"/>'); 
				}else{
					top.Alert.confirm(d.msg,
							function(r) {
								if (r) {
									$.post('projTemplateController.do?doSavePower', {
										'checks' : checks,
										'fileId' : fileIdRm,
										'templateId' : '${templateId}',
									}, function(data) {
										top.tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.saveSuccess"/>');
									});
									//treeNodeUrl = treeNode.dataObject+"&id="+treeNode.id;
									fileIdRm = treeNode.id;
									//loadPage("" + right_id, treeNode.dataObject+"&id="+treeNode.id, '<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.authConfig"/>'); 
									loadLibPage(
											"#projlib_right_page_panel",
											treeNode.id,
											'<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePower"/>'); 
								}else{
									fileIdRm = treeNode.id;
									loadLibPage(
											"#projlib_right_page_panel",
											treeNode.id,
											'<spring:message code="com.glaway.ids.pm.config.projectLibTemplate.projectLibTemplatePower"/>'); 
								}
							});
				}
			}
		});

	}
	
	
	function loadPage(panelId,url, title) {
		debugger;
		if(null!=url&&""!=url){
			$(panelId).panel({
				href : url,
				title : title
			});
		}
		
}
	
	function loadLibPage(panelId, id, title) {
		if (id != null && id != '') {
			$(panelId)
					.panel(
							{
								href : 'projTemplateController.do?goPowerList&templateId=${templateId}&fileId='
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
	
	function reloadTree(){
		debugger;
		var treeObj = $.fn.zTree.getZTreeObj("libMenu2");
		//刷新树
		treeObj.reAsyncChildNodes(null, "refresh");
	}
	
	function apply(){
		debugger;
		var libId=$('#templete').combobox('getValue');
		if(libId !=''){
			$.ajax({
				url : 'projTemplateController.do?doCheckPower',
				type : 'post',
				data : {
					'templateId' : '${templateId}',
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					debugger;
						if(d.success) {
							top.Alert.confirm(d.msg, function(r) {
							if (r) {
								$.ajax({
									url : 'projTemplateController.do?applyTemplete',
									type : 'post',
									data : {
										'templateId' : '${templateId}',
										'fileId' : fileIdRm,
										'libId' : libId
									},
									cache : false,
									success : function(data) {
										var d = $.parseJSON(data);
										var msg = d.msg;
										window.setTimeout('reloadTree()',500); 
										loadPage("" + right_id, '${url}', '<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.authConfig"/>'); 
										var win = $.fn.lhgdialog("getSelectParentWin");
										win.reloadTree();
										win.refshLibList();
										}
								});
								  }
							});
								
						}else {
							$.ajax({
								url : 'projTemplateController.do?applyTemplete',
								type : 'post',
								data : {
									'templateId' : '${templateId}',
									'fileId' : fileIdRm,
									'libId' : libId
								},
								cache : false,
								success : function(data) {
									var d = $.parseJSON(data);
									var msg = d.msg;
									window.setTimeout('reloadTree()',500); 
									loadPage("" + right_id, '${url}', '<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.authConfig"/>'); 
									var win = $.fn.lhgdialog("getSelectParentWin");
									win.reloadTree();
									win.refshLibList();
									}
							});
						}
					}
			});
			
			
			
		}else{
			top.tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.selectTemplate"/>');
		}
	}
	
	function clickIcon(id){
		//var templeteId=$('#templete').combobox('getValue');
		if(id !=''){
			var dialogUrl = 'projectLibTemplateController.do?goDetail&templateId=' + id;
			createDialog('showProjectLibTemplateDetailDialog', dialogUrl);
		}else{
			top.tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.selectTemplate"/>');
		}
	}
	
	function projectViewOption(value, row, index) {
		if (row.id == null) {
			return '';
		} else {
			return '<a title="查看模板" class="basis ui-icon-eye hideRowStyle" style="display: inline-block; cursor: pointer;" onclick="clickIcon(\''
					+ row.id + '\')"/>';
		}
	}
</script>
</head>
<body>
<%-- <fd:combobox title="权限模板"  id="templete" textField="name"  editable="false" valueField="id" name="approve"  url="projLibController.do?projectLibTemplateList" required="true" ></fd:combobox>	
<fd:linkbutton id="viewTemplete" onclick="clickIcon()" iconCls="basis ui-icon-eye"/> --%>
<div style="float:right;"><fd:helpButton help="helpDoc:LibraryStructureManagement"/></div>
	<fd:comboGrid id="templete" name="templete" url="projLibController.do?projectLibTemplateList" title="{com.glaway.ids.pm.project.projectmanager.auth.authTemplate}" idField="id" textField="name" value="${libTmpId}"
									 multiple="false" prompt="{com.glaway.ids.pm.project.projectmanager.auth.noUse}" panelHeight="320" panelWidth="202" toolbar="#toolbar" style="height:28px;">
	<fd:columns>
		<fd:column field="name"  width="145" title="" ></fd:column>
		<fd:column field="remark" width="50" title=""  formatter="projectViewOption">
		</fd:column>
	</fd:columns>
</fd:comboGrid>
<div style="padding-top: 5px;">
<fd:linkbutton id="apply" onclick="apply()" value="{com.glaway.ids.pm.project.projectmanager.auth.use}" iconCls="basis ui-memu-templetmanage" />


</div>						
<div class="easyui-layout"  fit="true">
	<div region="west" style="padding: 1px; width: 20%; border: 0;"
		title="">
		<div class="easyui-layout" fit="true">
			
			<div region="south" style="padding: 0px;height:100%;border:0;" title="<spring:message code="com.glaway.ids.pm.project.projectmanager.auth.authLibrary"/>" collapsible="false">
				<div class="easyui-layout" fit="true">
					<div id="projlib_left_page_panel" region="center" style="padding: 1px;width:330px;broder:0;" title="" >
						<fd:tree treeIdKey="id"
			 				url="projTemplateController.do?getProjLibTree&templateId=${templateId}" 
							treeName="name" treeTitle="" id="libMenu2" treePidKey="pid" 
			 				onClickFunName="loadLibUrl"/> 
					</div>
				</div>
			</div>
			
		</div>
	</div>
	<div region="east" style="padding: 1px;width:80%;" title="" collapsible="false">
		<div id="projlib_right_page_panel" style="border: 0;width:830px;"></div>
	</div>
</div>
</body>
		<fd:dialog id="showProjectLibTemplateDetailDialog" width="1050px"
			height="600px" modal="true" title="{com.glaway.ids.pm.project.projectmanager.auth.viewDetail}">
			<fd:dialogbutton name="{com.glaway.ids.common.btn.close}" callback="hideDiaLog"></fd:dialogbutton>
		</fd:dialog>
</html>