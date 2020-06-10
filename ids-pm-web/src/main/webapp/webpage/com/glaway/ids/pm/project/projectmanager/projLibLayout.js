
function createFilewindow(title, url, width, height) {
	var node = getLibTreeNode();
    if(node==null){
    	return;
    }
        	
    if(title=='修改'){
		 var nodeLevel = node.level;
		 if(nodeLevel == 0)
		{
			 var msg = "根节点不能修改";
			 tip(msg);
			 return;
		}	 
    }
  
    url+="&folderId="+node.id+"&projectId="+node.projectId;
	width = width ? width : 400;
	height = height ? height : 85;
	if (width == "100%" || height == "100%") {
		width = document.body.offsetWidth;
		height = document.body.offsetHeight - 100;
	}
	if (typeof (windowapi) == 'undefined') {
		$.fn.lhgdialog({
			content : 'url:' + url,
			lock : true,
			width : width,
			height : height,
			title : title,
			opacity : 0.3,
			cancelVal : '取消',
			okVal : '确定',
			cancel : true, /*为true等价于function(){}*/
			ok : function() {
				var result=false;
				iframe=this.iframe.contentWindow;
				if(title=='修改'){
					result=saveUpdatFolder(iframe);
				} else {
					result=saveFolder(iframe);
				}
				//alert(result);
				if(result)
				{
					window.setTimeout('reloadTreeAndRight()',500);
				}					
				return result;
			}
		});
	}else{
		$.fn.lhgdialog({
			content : 'url:' + url,
			lock : true,
			width : width,
			height : height,
			parent : windowapi,
			title : title,
			opacity : 0.3,
			cache : false,
			cancelVal : '取消',
			okVal : '确定',
			cancel : true, /*为true等价于function(){}*/
			ok : function() {
				var result=false;
				iframe=this.iframe.contentWindow
				if(title=='修改'){
					result=saveUpdatFolder(iframe);
				} else {
					//result=saveFolder(iframe);
					saveFolder(iframe);
				}
			}
		});
	}
}

function createFilewindow2(title, url, width, height) {
	var projectId = getProjectId();
    url+="&projectId="+projectId;
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

function saveFolder(iframe){
	var parentId = iframe.$('#parentId').val();
	var fileName = iframe.$('#docName').val();
	 if(fileName == ""||fileName == null||fileName == 'undefined')
		{			
			 tip("名称不能为空");
			 return false;
		}
	var url1="projLibController.do?checkFileName";
	$.ajax({
		url :url1,
		type : 'post',
		data : {
			'parentId' : parentId,
			'fileName' : fileName
		},
		cache : false,
		async : false,
		success : function(data) {
			var d = $.parseJSON(data);
			var id = iframe.$('#id').val();
			var parentId = iframe.$('#parentId').val();
			var type = iframe.$('#type').val();
			var projectId = iframe.$('#projectId').val();
			var docName = iframe.$('#docName').val();
			var url="projLibController.do?doAdd";
			if (d.success) {
				$.ajax({
					url :url,
					type : 'post',
					data : {
						'id' : id,
						'parentId' : parentId,
						'type' : type,
						'projectId' : projectId,
						'docName' : docName
					},
					cache : false,
					async : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {														
							var msg = d.msg;
							tip(msg);
							window.setTimeout('reloadTreeAndRight()',500);									
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

function saveFolder(iframe,tId){
	var parentId = iframe.$('#parentId').val();
	var fileName = iframe.$('#docName').val();
	 if(fileName == ""||fileName == null||fileName == 'undefined')
		{			
			 tip("名称不能为空");
			 return false;
		}
	var url1="projLibController.do?checkFileName";
	$.ajax({
		url :url1,
		type : 'post',
		data : {
			'parentId' : parentId,
			'fileName' : fileName
		},
		cache : false,
		async : false,
		success : function(data) {
			var d = $.parseJSON(data);
			var id = iframe.$('#id').val();
			var parentId = iframe.$('#parentId').val();
			var type = iframe.$('#type').val();
			var projectId = iframe.$('#projectId').val();
			var docName = iframe.$('#docName').val();
			var url="projLibController.do?doAdd";
			debugger
			if (d.success) {
				$.ajax({
					url :url,
					type : 'post',
					data : {
						'id' : id,
						'parentId' : parentId,
						'type' : type,
						'projectId' : projectId,
						'docName' : docName
					},
					cache : false,
					async : false,
					success : function(data) {
						var d = $.parseJSON(data);
						debugger;
						if (d.success) {
							var msg = d.msg;
							tip("文件夹新增成功");
							window.setTimeout(new function(){
								debugger;
								reloadTree2(tId, d.obj, 'save');
								parent.refreshRightPage();
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
 
function saveUpdatFolder(iframe, tId){
	var parentId = iframe.$('#parentId').val();
	var fileName = iframe.$('#docName').val();
	var id = iframe.$('#id').val();
	 if(fileName == ""||fileName == null||fileName == 'undefined')
		{			
			 tip("名称不能为空");
			 return false;
		}
	var url1="projLibController.do?checkFileName";
	$.ajax({
		url :url1,
		type : 'post',
		data : {
			'id' : id,
			'parentId' : parentId,
			'fileName' : fileName
		},
		cache : false,
		async : false,
		success : function(data) {
			var d = $.parseJSON(data);
			var id = iframe.$('#id').val();
			var parentId = iframe.$('#parentId').val();
			var type = iframe.$('#type').val();
			var projectId = iframe.$('#projectId').val();
			var docName = iframe.$('#docName').val();
			if (d.success) {
				var url="projLibController.do?doUpdateFolder";
				$.ajax({
					url :url,
					type : 'post',
					data : {
						'id' : id,
						'parentId' : parentId,
						'type' : type,
						'projectId' : projectId,
						'docName' : docName
					},
					cache : false,
					async : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							//window.setTimeout('reloadTreeAndRight()',500); 
							window.setTimeout(new function(){
								reloadTree2(tId, fileName, 'update');
								parent.refreshRightPage();
							},500);		
							
						}
					}
				});
				/*var url="projLibController.do?doUpdateFolder";
				var formName="#projLibFolderAddForm";
				if (iframe.$("#projLibFolderAddForm").form('validate')) {
					return  postCommonBasic(iframe, url, formName);
				}
				return false;*/
			}else{
				var msg = d.msg;
				tip(msg);
				return false;
			}
		}
	});
}
 
function reloadTreeAndRight(){
	reloadTree();
	//刷新左侧文件夹下文档
	parent.refreshRightPage();
}
 
function reloadTree(){
	var treeObj = $.fn.zTree.getZTreeObj("libMenuForProject");
	//刷新树
	treeObj.reAsyncChildNodes(null, "refresh");
}
 
function reloadTree2(tId, node, type){
	debugger;
	var treeObj = $.fn.zTree.getZTreeObj("libMenuForProject");
	//刷新树
	var nodes = treeObj.transformToArray(treeObj.getNodes());
	if (nodes.length > 0) {
	var selectNode= treeObj.getNodeByTId(tId);
	if(null != type && type == 'update'){
		var treeObj = $.fn.zTree.getZTreeObj("libMenuForProject");
		//刷新树
		selectNode.title=node;
		selectNode.name=node;
		treeObj.updateNode(selectNode);
		treeObj.selectNode(selectNode)
	}else{
		treeObj.addNodes(selectNode, node, true);
	}
	}
}
function doFolderExcute(url,tipMsg,datas){
	Alert.confirm(tipMsg, function(r) {
			if (r) {
				$.ajax({
					url :url,
					type : 'post',
					data : {
						'datas' : $.toJSON(datas)
					},
					cache : false,
					async : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							window.setTimeout('reloadTree()',500); 
							setTimeout('setDefaultSelectedNode()', 1000)
						}
					}
				});
			}
		});
}

function doFolderExcute2(url,tipMsg,datas,parentNode){
	$.ajax({
		url :'projLibController.do?beforeDelFolder',
		type : 'get',
		data : {
			'datas' : $.toJSON(datas)
		},
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				Alert.confirm(tipMsg, function(r) {
					if (r) {
						$.ajax({
							url :url,
							type : 'post',
							data : {
								'datas' : $.toJSON(datas)
							},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								if (d.success) {
									var msg = d.msg;
									tip(msg);
									setTimeout(function(){
										setDefaultSelectedNode();
									}, 1000);
									window.setTimeout('reloadTree()',500);
								}
							}
						});
					}
				});
			} else {
				var msg = d.msg;
				tip(msg);
			}
		}
	});
}