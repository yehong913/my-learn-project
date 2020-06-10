//获得当前树
function getTree(treeId){
	var treeObj = $("#"+treeId).ztree("getTree");
	return treeObj;
}

//重新加载当前树
function refresh(treeId){
	$("#"+treeId).ztree("refresh");
}

//展开当前树节点
function expandAll(treeId, expandFlag) {
	if (expandFlag == true) {
		$("#"+treeId).ztree("expandAll");
	} else {
		$("#"+treeId).ztree("collapseAll");
	}
}

//获取选择节点
function getNodes(treeId){
	var nodes = $("#"+treeId).ztree("getSelected");
	return nodes;
}

//获取选择节点中的第一个节点
function getNode(treeId){
	var nodes = getNodes(treeId);
	if (nodes == null) {
		return null;
	}
	return nodes[0];
}

//获得当前节点的前一个节点
function getPreNode(treeId){
	var node = $("#"+treeId).ztree("getPreNode");
	if (node == null) {
		return null;
	}
	return node;
}

//获得当前节点的下一个节点
function getNextNode(treeId){
	var node = $("#"+treeId).ztree("getNextNode");
	if (node == null) {
		return null;
	}
	return node;
}

//获得父结点
function getParentNode(treeId){
	var node = $("#"+treeId).ztree("getParentNode");
	if (node == null) {
		return null;
	}
	return node;
}

//移到节点前
function moveNodePro(treeId,node,showTip,url){
	if (node == null || node == undefined) {
		tip(showTip);
		return;
	} else {
		moveNodeByUrl(treeId,url+node.id);
	}
}
//移动节点
function moveNodeByUrl(treeId,url){
	//请求后台更换节点顺序
	$.ajax({
		type : "GET",
		url : url,
		success : function(data) {
			refresh(treeId);
			//window.setTimeout('refresh('+treeId+')', 500);//等待0.5秒，让分类树结构加载出来
		}
	});
}