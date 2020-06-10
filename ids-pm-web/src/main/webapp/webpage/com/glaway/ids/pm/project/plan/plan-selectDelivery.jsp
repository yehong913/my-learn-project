<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>交付项标准名称选择</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
	.searchImg {
		display:none
	}
</style>
<script type="text/javascript">
	$(function(){
		debugger;
		$('.searchImg').hide();
		$('.searchImg').css('display','none');
		$('.searchImg').css('visibility','hidden');
		
	})
	function searchList() {
		var no = $("#no").textbox("getValue");
		var name = $("#name").textbox("getValue");
		$('#configList')
				.datagrid(
						{
							url : 'planController.do?searchConfigsFromParams&name='
									+ encodeURI(encodeURI(name))
									+ '&no='
									+ encodeURI(encodeURI(no))
									+ '&ids='
									+ '${param.ids}',
							pageNumber : 1
						});
		
		$('#configList').datagrid('unselectAll');
		$('#configList').datagrid('clearSelections');
		$('#configList').datagrid('clearChecked');
	}
	function resetSearch() {
		$('#no').textbox("setValue", "");
		$('#name').textbox("setValue", "");
	}
	
	function getLoadData(){
	    var ids = [];
	    var datas;
	    var rows = $("#configList").datagrid('getSelections');
	    if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			 datas= {
					useObjectId : $("#useObjectId").val(),
					useObjectType : $("#useObjectType").val(),
					projectId : '${projectId}',
					ids : ids.join(',')
				};
			$.ajax({
				url : 'inputsController.do?setInputs',
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
	
	
	function getLoadDataForPlanChange(){
	    var ids = [];
	    var datas;
	    var rows = $("#configList").datagrid('getSelections');
	    if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			 datas= {
					useObjectId : $("#useObjectId").val(),
					useObjectType : $("#useObjectType").val(),
					projectId : '${projectId}',
					ids : ids.join(',')
				};
			$.ajax({
				url : 'inputsController.do?setInputsForPlanChange',
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
	
</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<input id="useObjectId" name="useObjectId" type="hidden" value="${useObjectId}">
		<input id="useObjectType" name="useObjectType" type="hidden" value="${useObjectType}">
		<div region="center" style="padding: 1px;">
			<div id="nmselecttool">
				<fd:form id="addForm" url="nameStandardController.do?doAdd">
					<fd:searchform id="seachnmselectTag" isMoreShow="${hideMoreShow }"
						onClickResetBtn="resetSearch();" onClickSearchBtn="searchList();">
						<fd:inputText id="no" name="no"
							title="{com.glaway.ids.common.lable.code}" queryMode="like"></fd:inputText>
						<fd:inputText id="name" name="name"
							title="{com.glaway.ids.common.lable.name}" queryMode="like"></fd:inputText>
					</fd:searchform>
				</fd:form>
				<fd:toolbar>
					<fd:toolbarGroup align="left">
						<fd:linkbutton disabled="true"></fd:linkbutton>
					</fd:toolbarGroup>
				</fd:toolbar>
			</div>
			<fd:datagrid checkbox="true" toolbar="#nmselecttool" checked="true"
				idField="id" checkOnSelect="false" id="configList" fit="true"
				fitColumns="true"
				url="planController.do?flag=start&ids=${param.ids}&namestandardDeliverables&useObjectId=${useObjectId}&source=${source}">
				<fd:dgCol field="no" title="{com.glaway.ids.common.lable.code}" />
				<fd:dgCol field="name" title="{com.glaway.ids.common.lable.name}" />
				<fd:dgCol field="stopFlag"
					title="{com.glaway.ids.common.lable.status}" />
			</fd:datagrid>
		</div>

	</div>

</body>
</html>
