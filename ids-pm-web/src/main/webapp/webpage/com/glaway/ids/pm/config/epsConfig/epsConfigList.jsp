<%@ page language="java" import="java.util.*"
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<body>
<div class="easyui-layout"  fit="true">
	<%--<div style="float: right; margin-right: 10px;">
		<fd:helpButton help="helpDoc:ProjectClassfication"></fd:helpButton>
	</div>--%>
	<fd:searchform id="seachEpsTag" onClickSearchBtn="searchTreeNode();"
		onClickResetBtn="searchEpsReset();" isMoreShow="false" help="helpDoc:ProjectClassfication">
		<fd:inputText id="epsConfigNo" name="EpsConfig.no"
			title="{com.glaway.ids.common.lable.code}" queryMode="like"></fd:inputText>
		<fd:inputText id="epsConfigName" name="EpsConfig.name"
			title="{com.glaway.ids.common.lable.name}" queryMode="like"></fd:inputText>
			
		<fd:combobox id="epStopFlag" textField="name" title="{com.glaway.ids.common.lable.status}" name="EpsConfig.stopFlag" 
				editable="false" valueField="id" prompt="全部" url="epsConfigController.do?epsStatusList" queryMode="in" multiple="false" >
		</fd:combobox>
	</fd:searchform>

	<fd:toolbar>
		<fd:toolbarGroup align="left">
			<fd:linkbutton
				onclick="addTreeNode('新增','epsConfigController.do?goAdd','mygrid',null,null)"
				value="{com.glaway.ids.common.btn.create}"
				iconCls="l-btn-icon basis ui-icon-plus" operationCode="epsAdd" />
			<fd:linkbutton
				onclick="deleteTreeNodes('批量删除','epsConfigController.do?doBatchDel','epsTreeTable')"
				value="{com.glaway.ids.common.btn.remove}"
				iconCls="l-btn-icon basis ui-icon-minus" operationCode="epsBatchDel" />
			<fd:linkbutton
				onclick="startOrStopEps('启用','epsConfigController.do?doStartOrStop&state=start','epsTreeTable')"
				value="{com.glaway.ids.common.btn.start}"
				iconCls="basis ui-icon-enable" operationCode="epsBatchStart" />
			<fd:linkbutton
				onclick="startOrStopEps('禁用','epsConfigController.do?doStartOrStop&state=stop','epsTreeTable')"
				value="{com.glaway.ids.common.btn.stop}"
				iconCls="basis ui-icon-forbidden" operationCode="epsBatchStop" />
		</fd:toolbarGroup>
	</fd:toolbar>

	<fd:lazytreegrid id="eps_mygrid" idField="id" treeField="name" singleSelect="false" style="width:100%;height:90%;"
		url="epsConfigController.do?searchNodes&updateEpsConfigCode=${updateEpsConfigCode}&deleteEpsConfigCode=${deleteEpsConfigCode}&stopEpsConfigCode=${stopEpsConfigCode}&startEpsConfigCode=${startEpsConfigCode}" >
		<fd:columns>
			<fd:column field="optBtn" title="{com.glaway.foundation.common.operation}" />
			<fd:column field="no" title="编号" />
			<fd:column field="name" title="名称" />
			<fd:column field="stopFlag" title="状态" />
			<fd:column field="configComment" title="备注" />
		</fd:columns>
		<fd:eventListener event="onRightClick" listener="onClickAdd"></fd:eventListener>
	</fd:lazytreegrid>

	<fd:menu id="menusAddMore">
	   <fd:menuitem text="在下方插入新分类" onclick="addEpsAfter()"
			iconCls="basis ui-icon-plus" id="addEpsAfter"
			/>
		<fd:menuitem text="新增子分类" onclick="addEpsChild()"
			iconCls="basis ui-icon-plus" id="addEpsChild"
			 />
	</fd:menu>
	
	<fd:dialog id="addTreeNodeDialog" width="800px" height="300px"
		modal="true" title="{com.glaway.ids.common.btn.create}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
			callback="epsConfigOkFunction"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
			callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	<fd:dialog id="updateNodeLineDialog" width="800px" height="300px"
		modal="true" title="{com.glaway.ids.common.btn.modify}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
			callback="epsConfigOkFunction"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
			callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	<fd:dialog id="addEpsAfterDialog" width="800px" height="300px"
		modal="true" title="{com.glaway.ids.common.btn.create}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
			callback="addEpsAfterFunction"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
			callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
	<fd:dialog id="addEpsChildDialog" width="800px" height="300px"
		modal="true" title="{com.glaway.ids.common.btn.create}">
		<fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}"
			callback="addEpsChildFunction"></fd:dialogbutton>
		<fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}"
			callback="hideDiaLog"></fd:dialogbutton>
	</fd:dialog>
	
</div>
<script type="text/javascript">
function epsMygridMsg(){
	$('#eps_mygridMsg').css('display','none');
}

var depart_data='';

	function reloadEpsTable() {
		searchTreeNode();
	}

	function formatComment(value, row, index) {
		if (value == undefined)
			value = '';
		var shtml;
		shtml = '<span title="'+value+'">' + value + '</span>';
		return shtml;
	}

	function formatNo(value, row, index) {
		var shtml;
		shtml = '<div id="no'+row.id+'">' + row.no + '</div>'
		return shtml;
	}

	function formatName(value, row, index) {
		var shtml;
		shtml = '<div id="name'+row.id+'">' + row.name + '</div>'
		return shtml;
	}
	//树内右侧点击事件
	function onClickAdd(rowId, cellIndex, event, row)
	{
		
		mygrid_eps_mygrid.selectRowById(rowId);
		// 禁止IE、Chrome和Firefox的右键事件
		document.oncontextmenu = function(event) {
			return false;
		}
		
		$('#menusAddMore').menu('show', {
			left : event.screenX,
			top : event.clientY
		});
		
		
		$('#addEpsAfter').show();
		$('#addEpsChild').show();
		
		
	}
	//右键在下方插入平级项目分类   保存新增信息
	function addEpsAfterFunction(iframe)
	{
		saveOrUp(iframe);
		window.setTimeout("reloadEpsTable()", 500);//等待0.5秒，让分类树结构加载出来
		return false;
	}
	
	//右键插入子项目分类  保存新增信息
	function addEpsChildFunction(iframe)
	{
		saveOrUp(iframe);
		window.setTimeout("reloadEpsTable()", 500);//等待0.5秒，让分类树结构加载出来
		return false;
	}
	
	//新增平级的项目分类  在下方插入平级的项目分类
	function addEpsAfter()
	{
		debugger;
		var url="epsConfigController.do?goRightAdd";
		var selectedId = mygrid_eps_mygrid.getSelectedRowId();
		if (selectedId != undefined && selectedId != null && selectedId != '') {
			var q = selectedId.split(",").length;
			if (q == 1) {
				var flag = 'start';
				var stopFlag = mygrid_eps_mygrid.getRowAttribute(selectedId,
						'stopFlag');
				if (stopFlag == '<spring:message code="com.glaway.ids.common.stop"/>') {
					tip('<spring:message code="com.glaway.ids.pm.config.eps.createChildLimit"/>');
					return;
				}
				if (stopFlag == '<spring:message code="com.glaway.ids.common.start"/>') {
					flag = 'start';
				} else {
					flag = 'stop';
				}
				var type='after';
				url += '&parentId=' + selectedId + '&stopFlag=' + flag+'&type='+type;
			} else {
				tip('<spring:message code="com.glaway.ids.pm.config.eps.onlySelectOneParent"/>');
				return;
			}
		}
		createDialog('addEpsAfterDialog', url);
	}
	
	//在下方插入子项目分类
	function addEpsChild()
	{
		debugger;
		var url="epsConfigController.do?goRightAdd";
		var selectedId = mygrid_eps_mygrid.getSelectedRowId();
		if (selectedId != undefined && selectedId != null && selectedId != '') {
			var q = selectedId.split(",").length;
			if (q == 1) {
				var flag = 'start';
				var stopFlag = mygrid_eps_mygrid.getRowAttribute(selectedId,
						'stopFlag');
				if (stopFlag == '<spring:message code="com.glaway.ids.common.stop"/>') {
					tip('<spring:message code="com.glaway.ids.pm.config.eps.createChildLimit"/>');
					return;
				}
				if (stopFlag == '<spring:message code="com.glaway.ids.common.start"/>') {
					flag = 'start';
				} else {
					flag = 'stop';
				}
				var type='child';
				url += '&parentId=' + selectedId + '&stopFlag=' + flag+'&type='+type;
			} else {
				tip('<spring:message code="com.glaway.ids.pm.config.eps.onlySelectOneParent"/>');
				return;
			}
		}
		createDialog('addEpsChildDialog', url);
	}
	
	function updateNodeLine(id) {
		var selectedId = id;
		var stopFlag = mygrid_eps_mygrid.getRowAttribute(selectedId,
				'stopFlag');
		var url = 'epsConfigController.do?goUpdate';
		var title = '<spring:message code="com.glaway.ids.common.btn.modify"/>';

		if (stopFlag == '<spring:message code="com.glaway.ids.common.stop"/>') {
			tip('<spring:message code="com.glaway.ids.common.updateLimit"/>');
			return;
		}
		url += '&id=' + selectedId;
		createDialog('updateNodeLineDialog', url);
	}
	function epsConfigOkFunction(iframe) {
		saveOrUp(iframe);
		window.setTimeout("reloadEpsTable()", 500);//等待0.5秒，让分类树结构加载出来
		return false;
	}

	function deleteNodeLine(id) {
		var selectedId = id;
		var url = 'epsConfigController.do?doBatchDel';
		//var title = '<spring:message code="com.glaway.ids.common.btn.remove"/>';
		//var children = eps_mygrid.getRowAttribute(selectedId, 'rows');
		var msg = '';
		$.post('epsConfigController.do?doBatchDelIsHaveChildList',{'ids':selectedId},function(data){
			if(data.success){
				msg = '<spring:message code="com.glaway.ids.pm.config.eps.confirmOperateWithSon" arguments="删除"/>';
			}else{
				msg = '<spring:message code="com.glaway.ids.pm.config.eps.confirmBatchDel"/>';
			}
			top.Alert.confirm(msg, function(r) {
				if (r) {
					$.ajax({
						url : url,
						type : 'post',
						data : {
							ids : id
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								var msg = d.msg;
								tip(msg);
								ids = '';
								window.setTimeout("reloadEpsTable()", 500);//等待0.5秒，让分类树结构加载出来
							}
						}
					});
				}
			});
		},'json');
/* 		if (children.length > 0) {
			msg = '<spring:message code="com.glaway.ids.config.eps.confirmOperateWithSon" arguments="' + title + '"/>';
		} else {
			msg = '<spring:message code="com.glaway.ids.pm.config.eps.confirmDel"/>';
		} */

	}
	
	

	function startOrStopNodeEpsLine(id, type) {
		var stopFlag = mygrid_eps_mygrid.getRowAttribute(id, 'stopFlag');
		var url = '';
		if (type == '<spring:message code="com.glaway.ids.common.start"/>') {
			url = 'epsConfigController.do?doStartOrStop&state=start';
		} else {
			url = 'epsConfigController.do?doStartOrStop&state=stop';
		}

		if (type == '<spring:message code="com.glaway.ids.common.start"/>'
				&& stopFlag == '<spring:message code="com.glaway.ids.common.start"/>') {
			tip('<spring:message code="com.glaway.ids.common.onlyStartStopped"/>');
			return;
		}
		if (type == '<spring:message code="com.glaway.ids.common.stop"/>'
				&& stopFlag == '<spring:message code="com.glaway.ids.common.stop"/>') {
			tip('<spring:message code="com.glaway.ids.common.onlyStopStarted"/>');
			return;
		}

		var children = mygrid_eps_mygrid.getRowAttribute(id, 'rows');
		var msg = '';
		if (type == '禁用') {
			if (children.length > 0) {
				msg = '<spring:message code="com.glaway.ids.pm.config.eps.confirmOperateWithSon" arguments="' + type + '"/>';
			} else {
				msg = '<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + type + '"/>';
			}
		} else {
			msg = '<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + type + '"/>';
		}

		top.Alert.confirm(msg, function(r) {
			if (r) {
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : id
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							window.setTimeout("reloadEpsTable()", 500);//等待0.5秒，让分类树结构加载出来
						}
					}
				});
			}
		});
	}

	function addTreeNode(title, url, id, width, height) {
		var selectedId = mygrid_eps_mygrid.getSelectedRowId();
// 		if(selectedId != null && selectedId != '' && selectedId != undefined){
// 			url = url+'&parentId='+selectedId;
// 		}
		createDialog('addTreeNodeDialog', url);
	}

	function deleteTreeNodes(title, url, gname) {
		var selectedId = mygrid_eps_mygrid.getSelectedRowId();
		tittle = "<spring:message code="com.glaway.ids.common.btn.remove"/>";
		if (selectedId != undefined && selectedId != null && selectedId != '') {
			var selectedIdArr = selectedId.split(",");
			if (selectedIdArr.length == 0) {
				tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
				return;
			}
			var msg='';
			$.post('epsConfigController.do?doBatchDelIsHaveChildList',{'ids':selectedId},function(data){
				if(data.success){
					msg = '<spring:message code="com.glaway.ids.pm.config.eps.confirmOperateWithSon" arguments="' + tittle + '"/>';
				}else{
					msg = '<spring:message code="com.glaway.ids.pm.config.eps.confirmBatchDel"/>';
				}
				top.Alert.confirm(msg, function(r) {
					if (r) {
						$.ajax({
							url : url,
							type : 'post',
							data : {
								ids : selectedId
							},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								if (d.success) {
									var msg = d.msg;
									tip(msg);
									window.setTimeout("reloadEpsTable()", 500);//等待0.5秒，让分类树结构加载出来
								}
							}
						});
					}
				});
			},'json');

/* 			for (var i = 0; i < selectedIdArr.length; i++) {
				var children = eps_mygrid.getRowAttribute(
						selectedIdArr[i], 'rows');
				if (children.length > 0) {
					msg = '<spring:message code="com.glaway.ids.config.eps.confirmOperateWithSon" arguments="' + tittle + '"/>';
					break;
				} else {
					msg = '<spring:message code="com.glaway.ids.pm.config.eps.confirmBatchDel"/>';
				}
			} */

		} else {
			tip('<spring:message code="com.glaway.ids.common.selectDel"/>');
		}
	}
	function startOrStopEps(title, url, gname) {
		var selectedId = mygrid_eps_mygrid.getSelectedRowId();
		var msg = '';
		if (selectedId != undefined && selectedId != null && selectedId != '') {
			var selectedIdArr = selectedId.split(",");
			for (var i = 0; i < selectedIdArr.length; i++) {
				var stopFlag = mygrid_eps_mygrid.getRowAttribute(
						selectedIdArr[i], 'stopFlag');
				var children = mygrid_eps_mygrid.getRowAttribute(
						selectedIdArr[i], 'rows');
				var parent = mygrid_eps_mygrid.getRowAttribute(
						selectedIdArr[i], 'parentId');
				if (parent != 'ROOT') {
					var stopFlag_ = mygrid_eps_mygrid.getRowAttribute(parent,
							'stopFlag');
					if (stopFlag_ == '<spring:message code="com.glaway.ids.common.stop"/>') {
						tip('<spring:message code="com.glaway.ids.pm.config.eps.parentStop"/>');
						return;
					}
				}
				if (title == '<spring:message code="com.glaway.ids.common.start"/>'
						&& stopFlag == '<spring:message code="com.glaway.ids.common.start"/>') {
					tip('<spring:message code="com.glaway.ids.common.onlyStartStopped"/>');
					return;
				}
				if (title == '<spring:message code="com.glaway.ids.common.stop"/>'
						&& stopFlag == '<spring:message code="com.glaway.ids.common.stop"/>') {
					tip('<spring:message code="com.glaway.ids.common.onlyStopStarted"/>');
					return;
				}
				if (title == '<spring:message code="com.glaway.ids.common.stop"/>') {
					if (children.length > 0) {
						msg = '<spring:message code="com.glaway.ids.pm.config.eps.confirmOperateWithSon" arguments="' + title + '"/>';
					} else {
						msg = '<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + title + '"/>';
					}
				} else {
					msg = '<spring:message code="com.glaway.ids.common.confirmBatchOperate" arguments="' + title + '"/>';
				}
			}
			top.Alert.confirm(msg, function(r) {
				if (r) {
					$.ajax({
						url : url,
						type : 'post',
						data : {
							ids : selectedId
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								var msg = d.msg;
								tip(msg);
								window.setTimeout("reloadEpsTable()", 500);//等待0.5秒，让分类树结构加载出来
							}
						}
					});
				}
			});
		} else {
			tip('<spring:message code="com.glaway.ids.common.selectOperate"/>');
		}
	}
	//查询
	function searchTreeNode() {
		var no = $('#epsConfigNo').textbox("getValue");
		var name = $('#epsConfigName').textbox("getValue");
		var stopFlag = $('#epStopFlag').combobox("getValue");
		$
				.ajax({
					url : "epsConfigController.do?doSearch&updateEpsConfigCode=${updateEpsConfigCode}&deleteEpsConfigCode=${deleteEpsConfigCode}&stopEpsConfigCode=${stopEpsConfigCode}&startEpsConfigCode=${startEpsConfigCode}&name="
							+ encodeURI(encodeURI(name))
							+ '&no='
							+ encodeURI(encodeURI(no))
							+ '&stopFlag='
							+ encodeURI(encodeURI(stopFlag)),
					type : 'post',
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						mygrid_eps_mygrid.clearAll();
						mygrid_eps_mygrid.parse(d.obj, 'js');
					}
				});
	}

	//重置
	function searchEpsReset() {
		$('#epsConfigNo').textbox("setValue", "");
		$('#epsConfigName').textbox("setValue", "");
		$('#epStopFlag').combobox("clear");
	}
</script>
</body>
