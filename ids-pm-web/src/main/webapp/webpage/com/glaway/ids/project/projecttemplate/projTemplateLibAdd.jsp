<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目库</title>
<t:base type="jquery,easyui_iframe,tools,DatePicker"></t:base>
</head>
<body>
	<c:if test="${opFlag != '' && opFlag != null}">
		<t:base type="jquery,easyui,tools"></t:base>
	</c:if>
	<script type="text/javascript">
	var right_id = "#projlib_right_page_panel"
	
	$(document).ready(function() {
		setTimeout('setDefaultSelectedNode()', 500);
		blindRightMenu();
	})
	
	function setDefaultSelectedNode(){
			var treeObj = $.fn.zTree.getZTreeObj("libMenuForProjTemplate");
		// 获取全部节点
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		// 查找设置的value
		if (nodes.length > 0) {
			var node = nodes[0];
			treeObj.selectNode(node);
			treeObj.setting.callback.onClick(null, node.id, node);
		}
	}
	
	var libUrl = "";

	// 名称链接事件
	function loadLibUrl(event, treeId, treeNode) {
		debugger;
		var a = treeNode.dataObject.indexOf('noPower');
  		if(a != -1){
  			top.tip("您没有权限访问该文件夹");
  			return false;
  		}
  		libUrl = treeNode.dataObject;
		if('${opFlag}' == '1'){
			loadPage("" + right_id, treeNode.dataObject+"&opFlag=${opFlag}", "文档列表");
		}else{
			loadPage("" + right_id, treeNode.dataObject, "文档列表");
		}
		
	}
	
	function refshLibList(){
		if(libUrl != ""){
			loadPage("" + right_id, libUrl, "文档列表");
		}
	} 

	function refreshRightPage() {
		$("" + right_id).panel("open").panel("refresh");
	}

	function libShowRightMenu(e, treeId, treeNode) {
		 var treeObj = $.fn.zTree.getZTreeObj("libMenuForProjTemplate");
		treeObj.selectNode(treeNode, true); 
		var nodes = treeObj.getSelectedNodes();
		for(var i = 0;i<nodes.length;i++){
			if(treeNode.id == nodes[i].id){
				//treeObj.selectAllNode(nodes[i], true);
				treeObj.selectNode(nodes[i]); 
			}else{
				treeObj.selectNode(nodes[i], false);
			}
	    	//treeObj.selectNode(nodes[i]); 
	    }

		$('#power').show();
		if(treeNode.pid != null&&treeNode.pid != 'null'){
			$('#power').hide();
		}
		var hidPowerType='${param.kddProductType}';
		if(hidPowerType!=null&&"kddProduct"==hidPowerType){
			$('#power').hide();
		}
		var opFlag = '${opFlag}';
		var isHidProjLibOper = '${isHidProjLibOper}';
		if (opFlag == '1' || isHidProjLibOper == 'true' || '${hideRight}' == '1') {
			$('#projlib_rightClick_menu').menu('hide', {});
		}else{
			$('#projlib_rightClick_menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
			if ('${addProjLibFoder}' == 'true' || '${addProjLibFoder}') {
				$('#addProjLibFoder').show();
			}
			
			if ('${updateProjLibFoder}' == 'true' || '${updateProjLibFoder}') {
				$('#updateProjLibFoder').show();
				
			}
			if ('${powerProjLibFoder}' == 'true' || '${powerProjLibFoder}') {
				$('#power').show();
				
			}
		}

	}
	
	function iframesave(){
		if(true) {
			$.fn.lhgdialog("closeSelect");
		} else {
			//return false;
		}
	}
	
	 function createFilewindow(title, url, width, height) {
		   var node = getLibTreeNode();
		    if(node==null){
			 return   
		    }
		        	
		    if(title=='修改'){
				 var nodeLevel = node.level;
				 if(nodeLevel == 0)
				{
					 var msg = '<spring:message code="com.glaway.ids.common.tree.notOperateRoot"/>'
					 tip(msg);
					 return
				}	 
		    }
		    var templateId=$("#tmpId").val();
			
		    url+="&folderId="+node.id+"&templateId="+templateId;
			width = width ? width : 450;
			height = height ? height : 85;
			if (width == "100%" || height == "100%") {
				width = document.body.offsetWidth;
				height = document.body.offsetHeight - 100;
			}
			if (title == '新增') {
				createDialog('addAndSubmitDialog',url);
			
				 
			}else{
				createDialog('uodateAndSubmitDialog',url);
	
			}

		}
	 
	 function createFilewindow3(title, url, width, height,node) {
		   //var node = getLibTreeNode();
		    if(node==null){
			 return   
		    }
		        	
		    if(title=='修改'){
				 var nodeLevel = node.level;
				 if(nodeLevel == 0)
				{
					 var msg = '<spring:message code="com.glaway.ids.common.tree.notOperateRoot"/>'
					 tip(msg);
					 return
				}	 
		    }
		    var projectId='${projectId}';
			
		    url+="&folderId="+node.id+"&projectId="+projectId;
			width = width ? width : 450;
			height = height ? height : 85;
			if (width == "100%" || height == "100%") {
				width = document.body.offsetWidth;
				height = document.body.offsetHeight - 100;
			}
			if (title == '新增') {
				createDialog('addAndSubmitDialog',url);
			
				 
			}else{
				createDialog('uodateAndSubmitDialog',url);
	
			}

		}
	 function addAndSubmitOk(iframe){
		debugger;
		 var treeObj = $.fn.zTree.getZTreeObj("libMenuForProjTemplate");
		 var nodes = treeObj.getSelectedNodes();
		 var tId=nodes[0].tId;
		 saveFolder(iframe, tId);
		 return false;
	 }
	 function  updateAndSubmitOk(iframe){
		 var name = iframe.$('#docName').val();
		 if (name != '') {
				if (name.length > 30) {
					top.tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.nameLength"/>');
					return false;
				}
			}else{
				top.tip('<spring:message code="com.glaway.ids.pm.project.plan.emptyName"/>');
				return false;
			}
		 var result=false;
		 debugger;
		 var treeObj = $.fn.zTree.getZTreeObj("libMenuForProjTemplate");
		 var nodes = treeObj.getSelectedNodes();
		 var tId=nodes[0].tId;
		 result=saveUpdatFolder(iframe, tId);
		if(result == true)
		{
			window.setTimeout('reloadTree()',500);
		} else {
			
			return false;
		}
	 }
	/*  function delFolderTreeNodes(title,url){
			var node = getLibTreeNode();
		    if(node==null){
		    	return;  
		    }
		    var nodeLevel = node.level;
		    if(nodeLevel == 0)
			{
		    	var msg = '<spring:message code="com.glaway.ids.common.tree.notDelRoot"/>';
				tip(msg);
		    	return;
			}	 
		    doFolderExcute(url,'<spring:message code="com.glaway.ids.pm.project.projectmanager.library.confirmDel"/>',node.id);  
		} */
	 function delFolderTreeNodesAndShowParentNode(title,url){
		 debugger;
			var node = getLibTreeNode();
		  if(node==null){
		    	return;  
		    }
		    var nodeLevel = node.level;
		    if(nodeLevel == 0)
			{
		    	var msg = '<spring:message code="com.glaway.ids.common.tree.notDelRoot"/>';
				tip(msg);
		    	return;
			}	 
			var  parent=  node.getParentNode();
		    doFolderExcute(url,'<spring:message code="com.glaway.ids.pm.project.projectmanager.library.confirmDel"/>',node,parent.tId);  
		}
	 function moveFolerNode(direct){
			var node = getLibTreeNode();
			if(node == null || node==undefined){
				return;
			}
			if(node.parentTId == null||node.parentTId == 'null'){
				tip('<spring:message code="com.glaway.ids.common.tree.notMoveRoot"/>');
				return;
			}
			
			var parent = node.getParentNode();;//父节点
			var brothers = parent.children;//兄弟节点
			var upBrother = null;//最近的前一个兄弟节点
			var downBrother = null;//最近的后一个兄弟节点
			
			for(var i=0;i<brothers.length;i++){
				if(brothers[i].id == node.id){
					if(i>0){
						upBrother = brothers[i-1];
					}
					else{
						upBrother = null;
					}
					if((i+1)<brothers.length){
						downBrother = brothers[i+1];
					}
					else{
						downBrother = null;
					}
				}
			}
			
			if(direct == 'up'){
				if(upBrother == null || upBrother == undefined){
					tip('<spring:message code="com.glaway.ids.common.tree.moveToTop"/>');
					return;
				}
				else{
					//请求后台更换节点顺序
					$.ajax({
							type:"GET",
							url:"projLibController.do?changeNodeOrder&srcId="+node.id+'&destId='+upBrother.id+'&operate=up',
							success:function(data){
								window.setTimeout('reloadTree()',500);//等待0.5秒，让分类树结构加载出来
							}
						});
				}
			}
			
			else if(direct == 'down'){
				if(downBrother == null || downBrother == undefined){
					tip('已移动到底部');
					return;
				}
				else{
					//请求后台更换节点顺序
					$.ajax({
							type:"GET",
							url:"projLibController.do?changeNodeOrder&srcId="+node.id+'&destId='+downBrother.id+'&operate=down',
							success:function(data){
								window.setTimeout('reloadTree()',500);//等待0.5秒，让分类树结构加载出来
							}
						});
				}
			}
			
			else if(direct == 'left'){
				if(parent.parentTId == null||parent.parentTId == 'null'){
					tip('<spring:message code="com.glaway.ids.common.tree.moveToBottom"/>');
					return;
				}
				else{
					var pparent = parent.getParentNode();//爷爷节点
					var pbrothers = pparent.children;//父亲兄弟节点
					var lastpBrother = pbrothers[(pbrothers.length-1)];
					var brothersNum = brothers.length;
					
					var url1="projLibController.do?checkFileName";
					$.ajax({
						url :url1,
						type : 'post',
						data : {
							'parentId' : pparent.id,
							'fileName' : node.name
						},
						cache : false,
						async : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								//请求后台更换节点顺序
								$.ajax({
										type:"GET",
										url:"projLibController.do?changeNodeOrder&srcId="+node.id+'&destId='+parent.id+'&operate=left',
										success:function(data){
											window.setTimeout('reloadTreeAndRight()',500);//等待0.5秒，让分类树结构加载出来
										}
									});
							}else{
								var msg = d.msg;
								tip(msg);
								return false;
							}
						}
					});

				}
			}
			
			else if(direct == 'right'){
//					if(downBrother == null || downBrother == undefined){
//						tip('不能向右移动');
//						return;
//					}
				if(upBrother == null || upBrother == undefined){
					tip('<spring:message code="com.glaway.ids.common.tree.notMoveToRight"/>');
					return;
				}
				else{
					var url1="projLibController.do?checkFileName";
					$.ajax({
						url :url1,
						type : 'post',
						data : {
							'parentId' : upBrother.id,
							'fileName' : node.name
						},
						cache : false,
						async : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								var arrayData = new Array();
								$.ajax({
									type:"GET",
									//url:"projLibController.do?changeNodeOrder&srcId="+node.id+'&destId='+downBrother.id+'&operate=right',
									url:"projLibController.do?changeNodeOrder&srcId="+node.id+'&destId='+upBrother.id+'&operate=right',
									success:function(data){
										window.setTimeout('reloadTreeAndRight()',500);//等待0.5秒，让分类树结构加载出来
									}
								});
							}else{
								var msg = d.msg;
								tip(msg);
								return false;
							}
						}
					});
				}
			}
		}

	 function getLibTreeNode(){
			var treeObj = $.fn.zTree.getZTreeObj("libMenuForProjTemplate");
			var nodes = treeObj.getSelectedNodes();
			if(nodes.length>1)
			{
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.onlySelectOne"/>');
				return null;
			}else if(nodes.length==0){
				tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.mustSelectOne"/>');
				return null;
			}
			var node = nodes[0];
	     return node;
	}
	 function getProjectId(){
	     return '${projectId}';
	}
	 
	 
	function validateSelectedNum() {
		var treeObj = $.fn.zTree.getZTreeObj("libMenuForProjTemplate");
		var nodes = treeObj.getSelectedNodes();
		if(nodes.length>0)
		{
 			var chs = $(projLibGrid).datagrid("getChecked");
			if (chs == null || chs.length >1||chs.length ==0) {
				tip('请选择一条数据');
				return false;
			} else{
				return true;
			}
		}else{
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.library.mustSelectOne"/>');
			return false;
		}
	}
	
	function getSelectionsId(){
		var datas  =  getSelectionData();
		return datas[0].id;
	}
	
	function getSelectionsName(){
		var datas  =  getSelectionData();
		return datas[0].docName;
	}
	
	function getSelectionVersion(){
		var datas  =  getSelectionData();
		return datas[0].version;
	}
	function getSelectionStatus(){
		var datas  =  getSelectionData();
		return datas[0].status;
	}
	
	function beforeDrag(a, b, c) {
		var treeObj = $.fn.zTree.getZTreeObj("libMenuForProjTemplate");
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
		/* if('true'=='${updateTreeSequence}'){
		}else{
			return false;
		} */
		return true;
	}
	function beforeDrop(treeId, treeNode, targetNode, moveType, isCopy) {
		if (targetNode.pid == 'null' && moveType != 'inner') {
			tip('<spring:message code="com.glaway.ids.common.tree.notOperateRoot"/>');
			return false;
		}
		var parentId = '';
		if (moveType == 'inner') {
			parentId = targetNode.id;
		} else {
			parentId = targetNode.pid;
		}
		var result = 'true';
			$.ajax({
				url : 'projLibController.do?moveprojLibTreeNode',
				type : 'post',
				async : false,
				data : {
					parentId : parentId,
					name : treeNode[0].name,
					id : treeNode[0].id
				},
				success : function(data) {
					var d = $.parseJSON(data);
					var msg = d.msg;
					if (!d.success) {
						tip(msg);
						result = 'false';	
					}
				}
			});	
			if (result == 'false') {
				return false;
			}
	}
	
	function saveFolder(iframe,tId){
		//var templateId = $("#tmpId").val();
		var templateId = parent.$("#tmpId").val();
		if(null == templateId || undefined == templateId || "" == templateId){
			templateId =  '${templateId}';
		}
		//var fileName = iframe.$('#docName').val();
		var fileName = iframe.document.getElementById("docName").value;
		 if(fileName == ""||fileName == null||fileName == 'undefined')
			{			
				 tip("名称不能为空");
				 return false;
			}
		$.ajax({
			url :"projTemplateController.do?checkFileName",
			type : 'post',
			data : {
				'templateId' : templateId,
				'fileName' : fileName
			},
			cache : false,
			async : false,
			success : function(data) {
				var d = $.parseJSON(data);
				//var id = iframe.$('#id').val();
				var id = iframe.document.getElementById("id").value;
				//var parentId = iframe.$('#parentId').val();
				var parentId = iframe.document.getElementById("parentId").value;
				//var type = iframe.$('#type').val();
				var type = iframe.document.getElementById("type").value;
				var url="projTemplateController.do?doAddFolder";
				if (d.success) {
					$.ajax({
						url :url,
						type : 'post',
						data : {
							'id' : id,
							'parentId' : parentId,
							'type' : type,
							'templateId' : templateId,
							'fileName' : fileName,
							'isModify' : '${isModify}'
						},
						cache : false,
						async : false,
						success : function(data) {
							debugger;
							var d = $.parseJSON(data);
							if (d.success) {														
								var msg = d.msg;
								tip(msg);
								window.setTimeout(function(){
									reloadTree2(tId, d.obj, 'save');
								},500);
									
								iframe.closeWindow();					
							}
						}
					});
					
				}else{
					var msg = d.msg;
					tip(msg);
					return false;
				}
			}
		});
		
	}
	
	function reloadTree2(tId, node, type){
		var treeObj = $.fn.zTree.getZTreeObj("libMenuForProjTemplate");
		//刷新树
		var selectNode= treeObj.getNodeByTId(tId);
		if(null != type && type == 'update'){
			//刷新树
			selectNode.title=node;
			selectNode.name=node;
			treeObj.updateNode(selectNode);
			treeObj.selectNode(selectNode)
		}else{
			treeObj.addNodes(selectNode, node, true);
		}
	}
	
	function saveUpdatFolder(iframe, tId){
		var parentId = iframe.$('#parentId').val();
		var fileName = iframe.$('#docName').val();
		var templateId = '${templateId}';
		var id = iframe.$('#id').val();
		var type = iframe.$('#type').val();
		debugger;
		var oldName = iframe.$('#oldName').val();
		 if(fileName == ""||fileName == null||fileName == 'undefined')
			{			
				 tip("名称不能为空");
				 return false;
			}
		var url1="projTemplateController.do?checkFileName";
		if( oldName == fileName) {
			top.tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.folder.updateSuccess" />')
			iframe.closeWindow();
		} else {
		$.ajax({
			url :url1,
			type : 'post',
			data : {
				'templateId' : templateId,
				'fileName' : fileName
			},
			cache : false,
			async : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var url="projTemplateController.do?doUpdateFolder";
					$.ajax({
						url :url,
						type : 'post',
						data : {
							'id' : id,
							'parentId' : parentId,
							'type' : type,
							'templateId' : templateId,
							'fileName' : fileName,
							'isModify' : '${isModify}'
						},
						cache : false,
						async : false,
						success : function(data) {
							debugger;
							var d = $.parseJSON(data);
							if (d.success) {
								var msg = d.msg;
								tip(msg);
								window.setTimeout(new function(){
									reloadTree2(tId, fileName, 'update');
									//parent.refreshRightPage();
								},500);		
								iframe.closeWindow();
							}
						}
					});
				}else{
					var msg = d.msg;
					tip(msg);
					return false;
				}
			}
		});
		}
	}
	
	function blindRightMenu(){
		debugger;
		$("#projlib_rightClick_menu").menu({
			 onClick:function(item){
				 var name=item.name;
				var treeObj = $.fn.zTree.getZTreeObj("libMenuForProjTemplate");
				var nodes = treeObj.getSelectedNodes();
				 var node = nodes[0];
				/* treeObj.selectAllNode(item, false); */
				 var title="";
				 var url="";
				 if(name=="add"){
					 createFilewindow('新增','projLibController.do?goAddFolder',450,80);
				 }
				 if(name=="update"){
					 createFilewindow('修改','projLibController.do?goUpdateFolder',450,80);
				 }
				 if(name=="power"){
					 var url="${webRoot}/projTemplateController.do?goPower";
					 title="权限管理";
					 createFilewindow2(title,url,"","");
				 }
			 }
		});
	}
	
	function createFilewindow2(title, url, width, height) {
		var projectId = getProjectId();
	    url+="&templateId="+'${templateId}';
			$.fn.lhgdialog({
				content : 'url:' + url,
				lock : true,
				width : 1050,
				height : 600,
				parent : windowapi,
				title : title,
				opacity : 0.3,
				cache : false
			});
		
	}
	
	function doFolderExcute(url,tipMsg,node,pNodetId){
		top.Alert.confirm(tipMsg, function(r) {
				if (r) {
					$.ajax({
						url :url,
						type : 'post',
						data : {
							ids : $.toJSON(node.id)
						},
						cache : false,
						async : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								var msg = d.msg;
								tip(msg);
								var treeObj = $.fn.zTree.getZTreeObj("libMenuForProjTemplate");
								treeObj.removeNode(node);
								setTimeout(function(){
									setSelectedNode(pNodetId);
								}, 450);
							}else{
								tip(d.msg);
							}
						}
					});
				}
		});
	}
	
	function setSelectedNode(nodeId){
		var treeObj = $.fn.zTree.getZTreeObj("libMenuForProjTemplate");
		
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		if (nodes.length > 0) {
		var selectNode= treeObj.getNodeByTId(nodeId);
		
		treeObj.selectNode(selectNode);
		treeObj.setting.callback.onClick(null, selectNode.id, selectNode);
		}
	}
</script>


	<div class="easyui-layout" fit="true">
		<input id="treeType" name="treeType" type="hidden" value="${treeType}">
		<input id="rowId" name="rowId" type="hidden" value="${rowId}">
		<input type="hidden" id="step" name="step" value="4"/>
		<div region="west" style="padding: 1px; width: 220px" title="&nbsp;"
			id="projlib_left_page_panel">
			<fd:toolbar id="projLibLayoutOper">
				<fd:toolbarGroup align="left">
					<fd:linkbutton id="addProjLibDocButton" onclick="createFilewindow('新增','projLibController.do?goAddFolder',450,80)"
						value="" iconCls="basis ui-icon-plus" />
					<fd:tooltip id="addProjLibDocTip" forId="addProjLibDocButton" content="{com.glaway.ids.common.btn.create}"></fd:tooltip>
					<fd:linkbutton id="updateProjLibDocButton" onclick="createFilewindow('修改','projLibController.do?goUpdateFolder',450,80)"
						value="" iconCls="basis ui-icon-pencil" />
					<fd:tooltip id="updateProjLibDocTip" forId="updateProjLibDocButton" content="{com.glaway.ids.common.btn.modify}"></fd:tooltip>
					<fd:linkbutton id="deleteProjLibDocButton" onclick="delFolderTreeNodesAndShowParentNode('删除','projTemplateController.do?doDelFolder')"
						value="" iconCls="basis ui-icon-minus" />
					<fd:tooltip id="deleteProjLibDocTip" forId="deleteProjLibDocButton" content="{com.glaway.ids.common.btn.remove}"></fd:tooltip>
				</fd:toolbarGroup>
			</fd:toolbar>
			<fd:dialog id="addAndSubmitDialog" width="450px" height="85px"
				modal="true" title="{com.glaway.ids.common.btn.create}">
				<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="addAndSubmitOk"></fd:dialogbutton>
				<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
			</fd:dialog>
			<fd:dialog id="uodateAndSubmitDialog" width="450px" height="85px"
				modal="true" title="{com.glaway.ids.common.btn.modify}">
				<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="updateAndSubmitOk"></fd:dialogbutton>
				<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
			</fd:dialog>
			<fd:tree treeIdKey="id"
				url="projTemplateController.do?getProjLibTree&templateId=${templateId}"
				treeName=""
				lazyUrl="projTemplateController.do?getProjLibTree&templateId=${templateId}"
				lazy="true" treeTitle="" id="libMenuForProjTemplate" treePidKey="pid" dnd="true"
				onClickFunName="loadLibUrl" onRightClickFunName="libShowRightMenu" beforeDrop="beforeDrop" beforeDrag="beforeDrag"/>

			<fd:menu id="projlib_rightClick_menu">
				<fd:menuitem text="{com.glaway.ids.pm.project.projectmanager.library.addFile}" onclick="viewPlanTask()" id="addNode"
					name="add" />
				<fd:menuitem text="{com.glaway.ids.pm.project.projectmanager.library.updateFile}" onclick="viewPlanTask()" id="editNode"
					name="update"  />
				<fd:menuitem text="{com.glaway.ids.pm.project.projectmanager.library.authManager}" onclick="viewPlanTask()" id="power"
					name="power"  />
				<!-- operationCode="powerProjLibFoder" -->
			</fd:menu>
		</div>
		<div region="center">
			<div class="easyui-panel" title="&nbsp;" fit="true" border="false"
				id="projlib_right_page_panel"></div>
		</div>
	</div>

</body>
<html />
