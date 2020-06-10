<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="plug-in/jquery-easyui/themes/icon.css">
</head>
<body>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
	<input id="useObjectId" name="useObjectId" type="hidden" value="${resourceLinkInfo_.useObjectId}">
	<input id="useObjectType" name="useObjectType" type="hidden" value="${resourceLinkInfo_.useObjectType}">
	<input id="startTime" name="startTime" type="hidden" value="${resourceLinkInfo_.startTime}">
	<input id="endTime" name="endTime" type="hidden" value="${resourceLinkInfo_.endTime}">
	<div id="resourceListtb">
		<fd:searchform id="deliverablesSearchForm" onClickSearchBtn="searchResource()" onClickResetBtn="tagSearchReset()">
		<fd:inputText title="{com.glaway.ids.common.lable.code}"  id="no" queryMode="like"/>
		<fd:inputText title="{com.glaway.ids.common.lable.name}"  id="searchName" queryMode="like"/>
		<fd:combotree title="{com.glaway.ids.pm.project.plan.resource.resourceType}" treeIdKey="id"   url="resourceLinkInfoController.do?combotree"  id="path" treePidKey="pid" editable="false" multiple="true" 
		  panelHeight="200"   treeName="name" queryMode="eq" prompt="全部"></fd:combotree>
		</fd:searchform>
	</div>

	<fd:datagrid checkbox="true"  fitColumns="true"  checked="true" checkOnSelect="true" idField="id" toolbar="#resourceListtb" id="resourceInfoList"  pagination="false"
		url="resourceLinkInfoController.do?datagridlistForPlan&useObjectId=${resourceLinkInfo_.useObjectId}&useObjectType=${resourceLinkInfo_.useObjectType}" fit="true">
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceNo}"  field="no"  sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceName}" field="name" formatterFunName="resourceNameLink"  sortable="false"></fd:dgCol>
		<fd:dgCol title="{com.glaway.ids.pm.project.plan.resource.resourceType}" field="path"  sortable="false"></fd:dgCol>
	</fd:datagrid>
	</div>
</div>	
<fd:dialog id="resourceDialog" width="900px" height="800px" modal="true" title="{com.glaway.ids.pm.project.plan.resourcePicture}" maxFun="true"> 
</fd:dialog>
	<script type="text/javascript">
/* 	$(function() {
		$('#path').combotree({
			multiple : false
		});
	}); */
		function getLoadData(){
			    var ids = [];
			    var  datas;
			    var rows = $("#resourceInfoList").datagrid('getSelections');
			    if (rows.length > 0) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					 datas= {
							useObjectId : $("#useObjectId").val(),
							useObjectType : $("#useObjectType").val(),
							ids : ids.join(',')
						};
					$.ajax({
						url : 'resourceLinkInfoController.do?doAddForPlan',
						async : false,
						cache : false,
						type : 'post',
						data : datas,
						cache : false,
						success : function(data) {
							
						}
					});
				} else {
					tip('<spring:message code="com.glaway.ids.pm.project.plan.selectAdd"/>');
				}
			    return datas;
		}
		
		
		
		function searchResource() {
		    var name = $("#searchName").textbox("getValue");
		    var no = $("#no").textbox("getValue");
		    var path = $("#path").combo("getText");
		    var  datas;
				 datas= {
						name : name,
						no : no,
				        path : path,
						useObjectId : $("#useObjectId").val(),
						useObjectType : $("#useObjectType").val()
					};
				$.ajax({
					url : 'resourceLinkInfoController.do?search',
					async : false,
					cache : false,
					type : 'post',
					data : datas,
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							$.ajax({
								type : 'POST',
								url : 'resourceLinkInfoController.do?datagridSearchlist',
								async : false,
								data : datas,
								success : function(data) {
									$("#resourceInfoList").datagrid("loadData",data);
									$('#resourceInfoList').datagrid('unselectAll');
									$('#resourceInfoList').datagrid('clearSelections');
									$('#resourceInfoList').datagrid('clearChecked');
								} 
							});
						}
					}
				});
		}
		
		//重置
		function tagSearchReset() {
			$('#searchName').textbox("setValue","");
			$('#no').textbox("setValue","");
			/* $('.easyui-combotree').combotree("unselectAll"); */
/*  			 var treeObj = $.fn.zTree.getZTreeObj("pathTree");
			var nodes = treeObj.getSelectedNodes();
			for (var i = 0; i < nodes.length; i++) {
				alert(nodes[i].text);
			   treeObj.selectNode(nodes[i], false);
			}  */
		/* 	$("a[class='level2 curSelectedNode']").removeClass('curSelectedNode'); */
		
			 $('#path').comboztree("clear");
		}
		
		
		// 资源名称链接事件
		function resourceNameLink(val, row, value) {
			return '<a href="#" onclick="viewResourceCharts(\'' + row.id + '\')" style="color:blue">' + val + '</a>';
		}
		
		// 资源名称链接事件
		function viewResourceCharts(id){
			var resourceLinkId = "";
			var resourceUseRate = "";
			if($("#startTime").val() != null && $("#startTime").val() != '' && $("#endTime").val() != null && $("#endTime").val() != ''){
				
				var startTime = dateFmtYYYYMMDD($("#startTime").val());
				var endTime = dateFmtYYYYMMDD($("#endTime").val());
				createDialog('resourceDialog',"resourceLinkInfoController.do?goToUsedRateReport&resourceId="+ id 
						+ "&startTime=" + startTime + "&endTime=" + endTime);
			}
		}
		
		
		// 维护资源
		function addResourcePath() {
			$.fn.lhgdialog({
				content : 'url:resourceController.do?resourceForPlan',
				lock : true,
				width : 500,
				height : 350,
				zIndex : 9999,
				title : '选择资源分类',
				opacity : 0.3,
			});

		}
		
		
		
		
			
		function closePlan() {
			$.fn.lhgdialog("closeSelect");
		}
	</script>



</body>