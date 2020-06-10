<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>上传Word布局模板</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<div id="selectSNgen_tool">
		<fd:searchform id="selectSNgen_searchForm"
			onClickResetBtn="selectSNgen_reset()"
			onClickSearchBtn="selectSNgen_search()">
			<fd:inputText title="{com.glaway.foundation.common.name}" name="SerialNumberGeneratorInfo.name" id="serialNumberGeneratorInfo_Name" queryMode="like" />
			<c:choose>
				<c:when test="${param.ruleList==null }">
					<fd:combobox title="{com.glaway.foundation.common.type}" data="_全部,0_生成器类,1_配置编号规则" name="SerialNumberGeneratorInfo.generatorType" id="serialNumberGeneratorInfo_Type" queryMode="eq" textField="text" editable="false" valueField="value"></fd:combobox>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="SerialNumberGeneratorInfo.generatorType" value="1"/>
					<input type="hidden" name="SerialNumberGeneratorInfo.generatorType_condition" value="eq"/>
				</c:otherwise>
			</c:choose>
			<fd:inputText title="{com.glaway.foundation.snGenerator.ruleDesc}" name="SerialNumberGeneratorInfo.description" id="serialNumberGeneratorInfo_Description" queryMode="like" />
			<input type="hidden" id="status__" name="SerialNumberGeneratorInfo.status" value="1"/>
			<input type="hidden" id="status_condition__" name="SerialNumberGeneratorInfo.status_condition" value="eq"/>
		</fd:searchform>
	</div>
	<fd:datagrid checkbox="false" 
		toolbar="#selectSNgen_tool" checked="false" idField="id" fitColumns="true"
		checkOnSelect="false" id="selectSNgen_datagrid">
			<fd:dgCol title="{com.glaway.foundation.common.name}" field="name" width="150" />
			<fd:dgCol title="{com.glaway.foundation.common.type}" field="generatorType" width="150" formatterFunName="showGeneratorType_"/>
			<fd:dgCol title="规则说明" field="description"  width="450" />
			<fd:dgCol title="初始值" field="startValue" formatterFunName="showStartValue"/>
			<fd:dgCol title="{com.glaway.foundation.common.createBy}" field="createFullName" formatterFunName="showCreateFullNameStr"/>
			<fd:dgCol title="{com.glaway.foundation.common.createTime}" field="createTime" width="120"/>
	</fd:datagrid>
</body>
<script type="text/javascript">

$(function(){
	setTimeout(function(){
		var rowId = '${rowId}';
		if(rowId != '' || rowId != undefined){
			var rows = $('#selectSNgen_datagrid').datagrid('getRows');
			for(var i = 0; i < rows.length;i++){
				var id = rows[i].id;
				if(id == rowId){
					var index = $('#selectSNgen_datagrid').datagrid('getRowIndex',id);
					$('#selectSNgen_datagrid').datagrid('selectRow',index);
				}
			}
		}
	},500);
	
});

function showGeneratorType_(val, row, value) {
	if(val && val=='1') {
		return '<spring:message code="com.glaway.foundation.snGenerator.setRule"/>';
	}
	return '<spring:message code="com.glaway.foundation.snGenerator.class"/>';
}

function showCreateFullNameStr(val, row, value) {
	return row.createFullName +'-'+ row.createName;
}

function showStartValue(val, row, value) {
	var showStr = '';
	if(row.generatorType=='1') {
		showStr = row.description;
		showStr = showStr.split(' , ')[0];
		showStr = showStr.substring(showStr.length-1);
	}
	return showStr;
}

	$(document).keypress(function(e) {
		//回车监听事件
		if (e.keyCode == 13) {
			selectSNgen_search();
		}
	});
	//查询
	function selectSNgen_search() {
		var queryParams = $('#selectSNgen_datagrid').datagrid('options').queryParams;
		$('#selectSNgen_tool').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#selectSNgen_datagrid').datagrid({
			url : 'serialNumberController.do?datagridInfo&isAjax=true',
			pageNumber : 1
		});
	}

	//重置
	function selectSNgen_reset() {		
		$('#serialNumberGeneratorInfo_Name').textbox('setValue', '');
		$('#serialNumberGeneratorInfo_Description').textbox('setValue', '');
		<c:if test="${param.ruleList==null }">
			$('#serialNumberGeneratorInfo_Type').combobox('setValue', '');
		</c:if>
	}

	function selectSNgen_returnSelect() {
		var selectRows = $("#selectSNgen_datagrid").datagrid("getSelections");
		return selectRows;
	}
	
	$(function(){
		selectSNgen_search();
	});

	window.onload = function(){
		document.onmousedown = function(e) {
			var e = e || window.event;
			if(e.button == 2) {
				$('#selectSNgen_datagrid').datagrid('clearSelections');
			}
		}
		document.oncontextmenu = function(e) {
			if(e && e.preventDefault){
				e.preventDefault();
			}else{
				window.event.returnValue = false;
			}
		}
	};
</script>