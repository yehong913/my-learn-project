<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>

<script type="text/javascript">
var bSearch=false;
var selectsGet='${selects}';
var selectModeGet='${selectMode}';
var groupNameGet='${groupName}';
var groupCodeGet='${groupCode}';
var handlerGet='${handler}';
var keyGet='${key}';

var selects='';
var selectMode='multi';
var groupName='';
var groupCode='';
var handler='';
var key='';
var param=frameElement.api.data;

$(function(){
	
	var dialogData=frameElement.api.data;
	if (null!=dialogData && 0!=dialogData.length){
		var selectsPost=frameElement.api.data.selects;
		var selectModePost=frameElement.api.data.selectMode;
		var groupNamePost=frameElement.api.data.groupName;
		var groupCodePost=frameElement.api.data.groupCode;
		var handlerPost=frameElement.api.data.handler;
		var keyPost=frameElement.api.data.key;		
		
		if (''==groupNamePost||null==groupNamePost||undefined==groupNamePost){
			groupName=groupNameGet;
		}else {
			groupName=groupNamePost;
		}
		if (''==groupCodePost||null==groupCodePost||undefined==groupCodePost){
			groupCode=groupCodeGet;
		}else {
			groupCode=groupCodePost;
		}
		if (''==selectModePost||null==selectModePost||undefined==selectModePost){
			selectMode=selectModeGet;
		}else {
			selectMode=selectModePost;
		}
		if (''==selectsPost||null==selectsPost||undefined==selectsPost){
			selects=selectsGet;
		}else {
			selects=selectsPost;
		}
		if (''==handlerPost||null==handlerPost||undefined==handlerPost){
			handler=handlerGet;
		}else {
			handler=handlerPost;
		}
		if (''==keyPost||null==keyPost||undefined==keyPost){
			key=keyGet;
		}else {
			key=keyPost;
		}
		
	}
	else {
		selects=selectsGet;
		selectMode=selectModeGet;
		groupName=groupNameGet;
		groupCode=groupCodeGet;
		handler=handlerGet;
		key=keyGet;
	}
	
	$('#right_to_left').click(function(){
		var rows=$('#group_list_select').datalist('getChecked');
		for (var i=rows.length-1;i>=0;i--){
			var row=rows[i];
			var index=$('#group_list_select').datalist('getRowIndex',row);
			right_to_left(index, row);
		}
	});
	
	$('#left_to_right').click(function(){
		var rows=$('#group_list').datagrid('getChecked');
		for (var i=0;i<rows.length;i++){
			var row=rows[i];
			var index=$('#group_list').datagrid('getRowIndex',row);
			var node=$('#group_list').parent().find('div[class=datagrid-body]').find('tr[datagrid-row-index='+index+']').slice(0,1);
	    	if (!node.hasClass('disable_backcolor')){
				left_to_right(row);
			}
		}
		$('#group_list').datagrid('clearChecked');
	});
	$('#btn_north_search').click(function(){
		var val=$('#north_search_input').val();
		if (''==val||undefined==val){
			$('#group_list').datagrid('reload');
		}else {
			var rows=$('#group_list').datagrid('getRows');
			for (var i=rows.length-1;i>=0;i--){
				var row=rows[i];
				var index=$('#group_list').datagrid('getRowIndex',row);
				$('#group_list').datagrid('deleteRow',index);
			}
			
			bSearch=true;
			$('#group_list').datagrid('reload');
		}
	});
	
	$('#group_list_select').datalist({
	    url: 'repFileTypeConfigController.do?getAdditionalAttributeList&isAjax=true&bPage=false&entityUri=${entityUri}&fieldName=${fieldName}&fieldValue='+encodeURI('${fieldValue}'),
	    checkbox: false,
	    lines: true,
	    singleSelect:false,
	    textField:'title',
	    valueField:'title',
	    width:'95%',
	    queryParams: {
			selects:selects
		},
	    onDblClickRow:function(index, row){
	    	right_to_left(index, row);
	    },
	    fitColumns:true,
	    onLoadSuccess:function(data){
	    	$('#group_list').datagrid({
	    	    url:'repFileTypeConfigController.do?datagrid&isAjax=true&bPage=false',
	    	    singleSelect:'single'==selectMode?true:false,
	    	    width:'100%',
	    	    queryParams: {
	    	    	params:$.toJSON(param)
	    	    },
	    	    fitColumns:true,
	    	    columns:[[
	  	    	    {field:'title',title:'<spring:message code="com.glaway.foundation.attribute.tag"/>'},
	    	        {field:'name',title:'<spring:message code="com.glaway.foundation.attribute.name"/>'},
	    	        {field:'dataType',title:'<spring:message code="com.glaway.foundation.attribute.type"/>'},
	    	        {field:'dataLength',title:'<spring:message code="com.glaway.foundation.attribute.length"/>'},
	    	        {field:'dataPrecision',title:'<spring:message code="com.glaway.foundation.attribute.precision"/>'},
	    	        {field:'require',title:'<spring:message code="com.glaway.foundation.common.ifRequired"/>',formatter:function(value,row,index){
	    	        	return roleList_requireFormat(value,row,index);
	    	        }}
	    	    ]],
	    	    onDblClickRow:function(index, row){
	    			var node=$('#group_list').parent().find('div[class=datagrid-body]').find('tr[datagrid-row-index='+index+']').slice(0,1);
	    	    	if (!node.hasClass('disable_backcolor')){
	    		    	left_to_right(row);
	    	    	}
	    	    },
	    	    onLoadSuccess:function(data){
	    	    	var rowsSrc=data.rows;
	    	    	checkGroupList(rowsSrc);
	    	    }
	    	});
	    }
	});
});

function northEnterKeyEvent(event) {
	if(event.keyCode==13) {
		$("#btn_north_search").focus();
	}
}
function checkGroupList(rowsSrc){
	
	for (var i=0;i<rowsSrc.length;i++){
		var rowSrc=rowsSrc[i];
		var idSrc=rowSrc.id;
		
		var rows=$('#group_list_select').datalist('getRows');
    	for (var j=0;j<rows.length;j++){
    		var row=rows[j];
    		var id=row.id;
    		if (idSrc==id){
    			var index=$('#group_list').datagrid('getRowIndex',rowSrc);
    			if (-1!=index){
	    			$('#group_list').parent().find('div[class=datagrid-body]').find('tr[datagrid-row-index='+index+']').addClass('disable_backcolor');
    			}
    			break;
    		}
    	}
	}

	if (bSearch){
		var val=$('#north_search_input').val();
		var rows=$('#group_list').datalist('getRows');
		for (var i=rows.length-1;i>=0;i--){
			var row=rows[i];
			var name = row.name;
			if (-1==name.indexOf(val) ){
				$('#group_list').datagrid('deleteRow',i);
			}
		}
		bSearch = false;
	}
}
function left_to_right(row){
	$('#group_list_select').datalist('appendRow',{
		id:row.id,
		name:row.name,
		title:row.title
	});
	
	/* $('#group_list_select').datalist('addClass',"datagrid-btable"); */
	var table1 = $('.datagrid-view2').find("table");
	$(table1).addClass("datagrid-btable"); 
	
	var index2=$('#group_list').datagrid('getRowIndex',row);
	if (-1!=index2){
		var trObj=$('#group_list').parent().find('div[class=datagrid-body]').find('tr[datagrid-row-index='+index2+']');
		$(trObj).addClass('disable_backcolor');
	}
}

function right_to_left(index, row){	
	var id=row.id;
	var name=row.name;
	var rows=$('#group_list').datagrid('getRows');
	for (var i=0;i<rows.length;i++){
		var rowTemp=rows[i];
		if (id==rowTemp.id){
			var index2=$('#group_list').datagrid('getRowIndex',rowTemp);
			if (-1!=index2){
				$('#group_list').parent().find('div[class=datagrid-body]').find('tr[datagrid-row-index='+index2+']').removeClass('disable_backcolor');
				break;
			}
		}
	}
	$('#group_list_select').datalist('deleteRow',index);
}

function getSelectGroups(){
	var ids='';
	var rows=$('#group_list_select').datalist('getRows');
	for (var i=0;i<rows.length;i++){
		var row=rows[i];
		if (i==rows.length-1){
			ids=ids+row.id;
		}else {
			ids+=row.id+',';
		}
	}
	return ids;
}

function saveEntityFieldAddAttrSet() {
	debugger;
	var formVali = $('#entityFieldAddAttrForm').form('validate');
	if(formVali) {
		var selectAddAttrRows = $('#group_list_select').datalist('getRows');
		if(selectAddAttrRows.length>0) {
			var configData = new Object();
			configData.oldId = '${oldId}';
			configData.entityUri = '${entityUri}';
			configData.fieldName = $('#entityFieldName').val();
			configData.fieldValue = $('#entityFieldValue').val();
			configData.addAttrRows = selectAddAttrRows;
			$.ajax({
				url: 'repFileTypeConfigController.do?saveOrUpdateEntityAttributeAdditionalAttribute',
				data: {'configDataMapStr': $.toJSON(configData)},
				type: "POST",
				success: function(data) {
					var d = $.parseJSON(data);
				    if (d.success) {
						top.tip("新增成功");
				    	$.fn.lhgdialog("getSelectParentWin").reloadTable();
						$.fn.lhgdialog("closeSelect");
					}else{
						top.tip("新增失败");
					}
				}
			});
		}
		else {
			top.tip('请至少选择一个软属性');
		}
	}
}
</script>

<style type="text/css">
.disable_backcolor{
	color:#AAAAB0 !important;
}
.datalist div{
	border:0 !important;
}
.datagrid-cell-c1-name{
	width:1000px !important;
}
</style>
<div class="easyui-layout" fit="true">
	<div region="west" style="padding: 1px;width:48%;border:0;" title="" >
		<div class="easyui-layout" fit="true">
			<div region="north" style="padding: 1px;height:100%;" title="<spring:message code="com.glaway.foundation.common.waitSelect"/>" collapsible="false">
				<div class="easyui-layout" fit="true">
					<div region="north" id="north_tool_div" style="background-color: rgb(227,227,227);height:30px;">
						<span style="float:right;">
							<input id="north_search_input" onkeydown="northEnterKeyEvent(event);" />
							<fd:linkbutton iconCls="icon-search" value="{com.glaway.foundation.common.query}" id="btn_north_search"/>
						</span>
					</div>
					<div region="center" style="border:1;">
						<div id="group_div" style="border:1;">
							<table id="group_list"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div region="center" style="padding: 1px;width:4%;text-align:center;" title="" >
		<div style="height:45%;">
		</div>
		<div>
			<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain" group="" id="right_to_left">
				<span class="l-btn-left l-btn-icon-left">
					<span class="l-btn-text l-btn-empty">&nbsp;</span>
					<span class="l-btn-icon pagination-prev">&nbsp;</span>
				</span>
			</a>
		</div>
		<div>
			<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain" group="" id="left_to_right">
				<span class="l-btn-left l-btn-icon-left">
					<span class="l-btn-text l-btn-empty">&nbsp;</span>
					<span class="l-btn-icon pagination-next">&nbsp;</span>
				</span>
			</a>
		</div>
	</div>
	<div region="east" style="padding: 1px;width:48%;" title="<spring:message code="com.glaway.foundation.common.dbclickRemove"/>" collapsible="false">
		<div class="easyui-layout" fit="true">
			<div region="north" id="north_tool_div" style="background-color: rgb(227,227,227);height:30px;">

				<span style="float:right;">
					<fd:linkbutton iconCls="basis ui-icon-up" value="上移" onclick="moveUp()"/>
					<fd:linkbutton iconCls="basis ui-icon-down" value="下移" onclick="moveDown()"/>
				</span>
			</div>
			<fd:form id="entityFieldAddAttrForm">
				<input type="hidden" id="entityUri" value="${entityUri}"/>
				<input type="hidden" id="entityFieldName" value="${fieldName}"/>
				<input type="hidden" id="entityFieldValue" value="${fieldValue}"/>
				<div region="center" style="border:0;">
					<div id="group_list_select" style="border:0;">
					<table id="group_list2" hidden="true"></table>
					</div>
				</div>
			</fd:form>
		</div>
	</div>
</div>
<script type="text/javascript" >
function moveUp() {
	var selectArr = $('#group_list_select').datalist('getSelections');
	if(selectArr.length==1) {
		var idx = $('#group_list_select').datalist('getRowIndex', selectArr[0]);
		if(idx > 0) {
			$('#group_list_select').datalist('deleteRow', idx).datalist('insertRow', {index: idx-1, row: selectArr[0]}).datalist('selectRow', idx-1);
		}
	}
	else if(selectArr.length > 0) {
		top.tip('只能移动一个软属性');
	}
	else {
		top.tip('请选择需要上移的软属性');
	}
}

function moveDown() {
	var selectArr = $('#group_list_select').datalist('getSelections');
	if(selectArr.length==1) {
		var allRows = $('#group_list_select').datalist('getRows');
		var idx = $('#group_list_select').datalist('getRowIndex', selectArr[0]);
		if(idx < allRows.length-1) {
			$('#group_list_select').datalist('deleteRow', idx).datalist('insertRow', {index: idx+1, row: selectArr[0]}).datalist('selectRow', idx+1);
		}
	}
	else if(selectArr.length > 0) {
		top.tip('只能移动一个软属性');
	}
	else {
		top.tip('请选择需要下移的软属性');
	}
}

function roleList_requireFormat(val,row,value){
	var require=row.require;
	if (0==require){
		return '<spring:message code="com.glaway.foundation.common.nonRequired"/>';
	}else if (1==require){
		return '<spring:message code="com.glaway.foundation.common.required"/>';
	}
}

</script>
</html>