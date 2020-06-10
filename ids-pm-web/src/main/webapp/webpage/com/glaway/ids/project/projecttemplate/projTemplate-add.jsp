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
		});

	function setDefaultSelectedNode(){
			var treeObj = $.fn.zTree.getZTreeObj("templateMenuAdd");
		// 获取全部节点
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		// 查找设置的value
		if (nodes.length > 0) {
			var node = nodes[1];
			treeObj.selectNode(node);
			treeObj.setting.callback.onClick(null, node.id, node);
		}
		}
	// 名称链接事件
	function toUrl(event, treeId, treeNode) {
		var	tmpId = getCurrentId();
		debugger;
		if( null == tmpId || undefined == tmpId || "" == tmpId) {
			tmpId = $("#tmpId").val();
		}
		if( null == tmpId || undefined == tmpId || "" == tmpId) {
			tmpId = '${templateId}';
		}
		if(undefined == tmpId || null == tmpId || "" == tmpId) {
			tmpId=$('div iframe').contents().find('#templateId').val();
		}
		var url = treeNode.dataObject.url+tmpId+"&method=${method}";
		if( null == tmpId || undefined == tmpId || "" == tmpId) {
			if(saveTmpData(treeNode,url)) {
				loadPage("#menuadd_right_page_panel", url,treeNode.name);
			} else {
				setDefaultSelectedNode();
			}
		} else {
			saveTmpData(treeNode,url)
			//loadPage("#menuadd_right_page_panel", url,treeNode.name);
		}
		$("#tmpId").val(tmpId);
		$("#templateId").val(tmpId);
	}



	function saveTmpData(treeNode,url) {
		debugger;
		var b= true;
		var treeObj = $.fn.zTree.getZTreeObj("templateMenuAdd");
		var saved= $('div iframe').contents().find('#saved').val();
		var name= $('div iframe').contents().find('#projTmplName').val();
		var remark= $('div iframe').contents().find('#remark').val();
		var id=$('div iframe').contents().find('#templateId').val();
		var step = $('div iframe').contents().find('#step').val();
		if("1"==step){
			if(treeNode.dataObject.url.indexOf("Detail") > 0){ } else {
				b = false ;
				if(undefined == id || null == id || "" == id)
					{
						top.tip('<spring:message code='com.glaway.ids.pm.projecttemplate.saveTemplateNameFirst'/>');
					}else{
						
					}
				
			var bizCurrent = $("#tmpBiz").val();
			if(null == bizCurrent || undefined == bizCurrent || '' == bizCurrent){
			    bizCurrent = $("#bizCurrent").val();
			}
			if(bizCurrent == "qiyong") {
						$.ajax({
							url : 'projTemplateController.do?doSaveNewTemplate',
							type : 'post',
							data : {
								name : name,
								remark : remark,
								templateId : id,
								method : '${method}'
							},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								debugger;
								b = d.success;
								if (d.success) {
									var msg = d.msg;
									tip(msg);
									$("#tmpName").val(d.obj.projTmplName);
									$('div iframe').contents().find('#name2').val(d.obj.projTmplName);
									$("#tmpRemark").val(d.obj.remark);
									$('div iframe').contents().find('#remark2').val(d.obj.remark);
									$("#tmpId").val(d.obj.persientId);

									$('div iframe').contents().find('#templateId').val(d.obj.persientId);
									$('#projTemplateAddForm').form('load',{
										projTmplName : d.obj.projTmplName,
											remark :	d.obj.remark
									});
									$("#bizCurrent").val(d.obj.bizCurrent);
									$("#tmpBiz").val(d.obj.bizCurrent);
									var win = $.fn.lhgdialog("getSelectParentWin");
									win.$("#projTemplateList").datagrid('clearSelections');
									win.$("#projTemplateList").datagrid('reload');
									var url2 = treeNode.dataObject.url+d.obj.persientId+"&method=${method}";
									loadPage("#menuadd_right_page_panel", url2,treeNode.name);
								}else{
									$.fn.lhgdialog("getSelectParentWin").top.tip(d.msg);
								}
							}
						});
				// });
				
				
			}  else {
				
			var name2 = $('div iframe').contents().find('#name2').val();
			var remark2 = $('div iframe').contents().find('#remark2').val();
			if(undefined == name2 || null == name2 || "" == name2) {
				
			}
			else if(name != name2 || remark != remark2)	{
				top.Alert.confirm('<spring:message code='com.glaway.ids.pm.projecttemplate.saveTemplateBaseInfo'/>', function(r) {
					if (r) {
						$.ajax({
							url : 'projTemplateController.do?doSaveNewTemplate',
							type : 'post',
							data : {
								name : name,
								remark : remark,
								templateId : id,
								method : '${method}'
							},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								debugger;
								b = d.success;
								if (d.success) {
									var msg = d.msg;
									tip(msg);
									$("#tmpName").val(d.obj.projTmplName);
									$('div iframe').contents().find('#name2').val(d.obj.projTmplName);
									$("#tmpRemark").val(d.obj.remark);
									$('div iframe').contents().find('#remark2').val(d.obj.remark);
									$("#tmpId").val(d.obj.persientId);

									$('div iframe').contents().find('#templateId').val(d.obj.persientId);
									$('#projTemplateAddForm').form('load',{
										projTmplName : d.obj.projTmplName,
											remark :	d.obj.remark
									});
									var win = $.fn.lhgdialog("getSelectParentWin");
									win.$("#projTemplateList").datagrid('reload');
									var url2 = treeNode.dataObject.url+d.obj.persientId+"&method=${method}";
									loadPage("#menuadd_right_page_panel", url2,treeNode.name);
								}else{
									$.fn.lhgdialog("getSelectParentWin").top.tip(d.msg);
								}
							}
						});
					}else{
						loadPage("#menuadd_right_page_panel", url,treeNode.name);
					}
				});	
			}else{
				loadPage("#menuadd_right_page_panel", url,treeNode.name);
			}
				
			}
			$("#tmpName").val($("#projTmplName").val());
			$("#tmpRemark").val($("#remark").val());
			$("#tmpId").val($('div iframe').contents().find('#templateId').val());
			}
		}else if("2"==step){ 	loadPage("#menuadd_right_page_panel", url,treeNode.name);
		}else if("3"== step){	loadPage("#menuadd_right_page_panel", url,treeNode.name);
		}else if("4" == step){ 	loadPage("#menuadd_right_page_panel", url,treeNode.name);
		} else { loadPage("#menuadd_right_page_panel", url,treeNode.name); }
		return b ;
	}
	function refreshRightPage() {
		$("#menuadd_right_page_panel").panel("open").panel("refresh");
	}
	function saveAll(method) {
		debugger;
		var b = false;
		var name=$("#projTmplName").val();
		if(undefined == name || null == name || "" == name) {
			name=$("#tmpName").val();
		}
		if(undefined == name || null == name || "" == name) {
			name=$("#projTmplName").val();
		}
		if(undefined == name || null == name || "" == name) {
			name=$("#projTmplName1").val();
		}
		if(undefined == name || null == name || "" == name) {
			name=document.getElementById("tabiframe").contentWindow.$("#projTmplName").val();
		}
		var remark=$("#remark").val();
		if(undefined == remark || null == remark || "" == remark) {
			remark=$("#tmpRemark").val();
		}
		if(undefined == remark || null == remark || "" == remark) {
			remark=document.getElementById("tabiframe").contentWindow.$("#remark").val();
		}
		var tempalteId=$("#tmpId").val();
		if(undefined == tempalteId || null == tempalteId || "" == tempalteId) {
			tempalteId='${templateId}';
		}
		if(undefined == tempalteId || null == tempalteId || "" == tempalteId) {
			tempalteId=$('div iframe').contents().find('#templateId').val();
		}
			if($("#step").val() == "1") {
				if(undefined == name || null == name || "" == name) {
					top.tip('<spring:message code="com.glaway.ids.pm.project.projecttemplate.nameNoEmpty"/>');
					return false;
				}
			}
		$.ajax({
			url : 'projTemplateController.do?doSaveNewTemplate',
			type : 'post',
			data : {
				name : name,
				remark : remark,
				templateId : tempalteId,
				method : method
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				debugger;
				if (d.success) {
					var msg = d.msg;
					top.tip(msg);
					$("#tmpName").val(d.obj.projTmplName);
					$("#tmpRemark").val(d.obj.remark);
					$("#tmpId").val(d.obj.persientId);

					$('div iframe').contents().find('#templateId').val(d.obj.persientId);
					$('#projTemplateAddForm').form('load',{
						projTmplName : d.obj.projTmplName,
							remark :	d.obj.remark
					});
					var win = $.fn.lhgdialog("getSelectParentWin");
					win.$("#projTemplateList").datagrid('clearSelections');
					win.$("#projTemplateList").datagrid('reload');
					$.fn.lhgdialog("closeSelect");
				}else{
					$.fn.lhgdialog("getSelectParentWin").top.tip(d.msg);
				}
			}
		});
	}
	
	function getCurrentId() {
		var id = "";
		var tempalteId=$("#tmpId").val();
		if(undefined == tempalteId || null == tempalteId || "" == tempalteId) {
			tempalteId='${templateId}';
		}
		if(undefined == tempalteId || null == tempalteId || "" == tempalteId) {
			tempalteId=$('div iframe').contents().find('#templateId').val();
		}
		$.ajax({
			url : 'projTemplateController.do?getCurrentId',
			type : 'post',
			data : {
				templateId : tempalteId,
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				debugger;
				if (d.success) {
					id=d.obj.persientId;
					$("#tmpId").val(d.obj.persientId);
					$('div iframe').contents().find('#templateId').val(d.obj.persientId);
				}
			}
		});
		return id;
	}

	/*function addPlan1() {
		aaa();
	}*/

	function iframeFlush() {
		debugger
		$('#tabiframe')[0].contentWindow.planListSearch();
	}
</script>
</head>

<body>
	<div class="easyui-layout" fit="true">
		<div region="west" style="padding: 1px; width: 150px;border-right: 1px solid #cccccc;" title="<spring:message code='com.glaway.ids.pm.project.projecttemplate.projecttemplate'/>" id="menu_left_page_panel">
			<input type="hidden" id="tmpId" name="tmpId" value="${templateId }">	
			<input type="hidden" id="tmpName" name="tmpName" value="">	
			<input type="hidden" id="tmpRemark" name="tmpRemark" value="">	
			<input type="hidden" id="tmpBiz" name="tmpBiz" value="">	
			<fd:tree treeIdKey="id"
				url="projTemplateController.do?getAddTemplate" treeName=""
				treeTitle="" id="templateMenuAdd" treePidKey="pid"
				onClickFunName="toUrl"  />
		<%--	<fd:linkbutton id="addPlanBtn1" onclick="addPlan1()" value="新增计划"
						   iconCls="basis ui-icon-plus" />--%>
		</div>
		<div region="center">
			<div class="easyui-panel" title="&nbsp;" fit="true" border="false" id="menuadd_right_page_panel">
			</div>
		</div>
	</div>
</body>
