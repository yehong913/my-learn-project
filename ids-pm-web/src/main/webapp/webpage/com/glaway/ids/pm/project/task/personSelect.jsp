<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>

<t:base type="jquery,easyui_iframe,tools_iframe"></t:base>

<script type="text/javascript">
var curSelectDepartNode;
var bSearch=false;
var dept_data='';
var selectModeGet='${selectMode}';
var roleCodeGet='${roleCode}';
var groupCodeGet='${groupCode}';
var departNameGet='${departName}';
var selectsGet='${selects}';
var handlerGet='${handler}';
var keyGet='${key}';
var modeGet='${mode}';

var selectMode='';
var roleCode='';
var groupCode='';
var departName='';
var selectsPost='';
var handler='';
var key='';
var depart_url='';
var mode='';
var selectCode='';
var id_dept_tree, name_dept_tree, pid_dept_tree;
var settingdept_tree = {
	async : {},
	view : {},
	data : {
		key : {},
		simpleData : {}
	},
	callback : {},
	check : {},
	edit : {
		drag : {}
	}
};
settingdept_tree.data.key.name = "name";
settingdept_tree.data.simpleData.enable = true;
settingdept_tree.data.simpleData.idKey = "id";
settingdept_tree.data.simpleData.pIdKey = "pid";
settingdept_tree.data.simpleData.rootPId = "null";
settingdept_tree.callback.onClick = departTreeClick;

var param=frameElement.api.data;

$(function(){
	var dialogData=frameElement.api.data;
	if (null!=dialogData && 0!=dialogData.length){
		
		var selectModePost=frameElement.api.data.selectMode;
		var roleCodePost=frameElement.api.data.roleCode;
		var groupCodePost=frameElement.api.data.groupCode;
		var departNamePost=frameElement.api.data.departName;
		var selectsPost=frameElement.api.data.selects;
		var handlerPost=frameElement.api.data.handler;
		var keyPost=frameElement.api.data.key;
		var modePost=frameElement.api.data.mode;
		var selectCodePost=frameElement.api.data.selectCode;
		
		if (''==selectModePost||null==selectModePost||undefined==selectModePost){
			selectMode=selectModeGet;
		}else {
			selectMode=selectModePost;
		}
		if (''==roleCodePost||null==roleCodePost||undefined==roleCodePost){
			roleCode=roleCodeGet;
		}else {
			roleCode=roleCodePost;
		}
		if (''==groupCodePost||null==groupCodePost||undefined==groupCodePost){
			groupCode=groupCodeGet;
		}else {
			groupCode=groupCodePost;
		}
		if (''==departNamePost||null==departNamePost||undefined==departNamePost){
			departName=departNameGet;
		}else {
			departName=departNamePost;
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
		if (''==modePost||null==modePost||undefined==modePost){
			mode=modeGet;
		}else {
			mode=modePost;
		}
		if(''==selectCodePost||null==selectCodePost||undefined==selectCodePost){
			selectCode='';
		}else{
			selectCode=selectCodePost;
		}
			
		
		
	} else {
		selectMode=selectModeGet;
		roleCode=roleCodeGet;
		groupCode=groupCodeGet;
		departName=departNameGet;
		selects=selectsGet;
		handler=handlerGet;
		key=keyGet;
		mode=modeGet;
	}
	
	depart_url=encodeURI('generalSelectUserController.do?doGetDeptTree&isAjax=true&departName='+departName);
	depart_url=encodeURI(depart_url);
	
	$('#right_to_left').click(function(){
		var rows=$('#user_list_select').datalist('getChecked');
		for (var i=rows.length-1;i>=0;i--){
			var row=rows[i];
			var index=$('#user_list_select').datalist('getRowIndex',row);
			right_to_left(index, row);
		}
	});
	$('#left_to_right').click(function(){
		var rows=$('#user_list').datalist('getChecked');
		for (var i=0;i<rows.length;i++){
			var row=rows[i];
			if ('unchecked'==row.checked){
				left_to_right(row);
			}
		}
		$('#user_list').datalist('clearChecked');
	});
	$('#btn_north_search').click(function(){
		var val=$('#north_search_input').val();
		if (''==val||undefined==val){
			var type=getRatioCheckedVal();
			if ('dept'==type){
				$.post(depart_url,
						function(data) {
							$.fn.zTree.init($("#dept_tree"),settingdept_tree,data);
							dept_data=data;
						});
				
				
			} else if ('role'==type){
				$('#role_list').datagrid('reload');
			} else if ('group'==type){
				$('#group_list').datagrid('reload');
			}
		}else {
			var rows=$('#user_list').datalist('getRows');
			for (var i=rows.length-1;i>=0;i--){
				var row=rows[i];
				var index=$('#user_list').datalist('getRowIndex',row);
				$('#user_list').datalist('deleteRow',index);
			}
			
			var type=getRatioCheckedVal();
			if ('dept'==type){
				$.post(depart_url,
						function(data) {
							deptTreeFilter(val, data);
						});
				selectDepart()

			}else if ('role'==type){
				bSearch=true;
				$('#role_list').datagrid('reload');
			}else if ('group'==type){
				bSearch=true;
				$('#group_list').datagrid('reload');
			}
		}
	});
	$('#btn_south_search').click(function(){
		var val=$('#south_search_input').val();
		if (''==val||undefined==val){
			var type=getRatioCheckedVal();
			if ('dept'==type){
				$('#dept_div').find('a').each(function(){
					if ($(this).hasClass('curSelectedNode')){
						$(this).click();
						return;
					}
				});				
			}else if ('role'==type){
				var rows=$('#role_list').datagrid('getChecked');
				if (1==rows.length){
					var row=rows[0];
					var index=$('#role_list').datagrid('getRowIndex',row);
					var curTr=$('#role_list').parent().find('table[class=datagrid-btable]').find('tr[datagrid-row-index='+index+']').slice(0,1);
					$(curTr).click();
				}
			}else if ('group'==type){
				var rows=$('#group_list').datagrid('getChecked');
				if (1==rows.length){
					var row=rows[0];
					var index=$('#group_list').datagrid('getRowIndex',row);
					var curTr=$('#group_list').parent().find('table[class=datagrid-btable]').find('tr[datagrid-row-index='+index+']').slice(0,1);
					$(curTr).click();
				}
			}
		}else{
			var rows=$('#user_list').datalist('getRows');
			for (var i=rows.length-1;i>=0;i--){
				var row=rows[i];
				var index=$('#user_list').datalist('getRowIndex',row);
				$('#user_list').datalist('deleteRow',index);
			}
			
			bSearch=true;
			var type=getRatioCheckedVal();
			if ('dept'==type){
				$('#dept_div').find('a').each(function(){
					if ($(this).hasClass('curSelectedNode')){
						$(this).click();
						return;
					}
				});	
			}else if ('role'==type){
				var rows=$('#role_list').datagrid('getChecked');
				if (1==rows.length){
					var row=rows[0];
					var index=$('#role_list').datagrid('getRowIndex',row);
					var curTr=$('#role_list').parent().find('table[class=datagrid-btable]').find('tr[datagrid-row-index='+index+']').slice(0,1);
					$(curTr).click();
				}
			}else if ('group'==type){
				var rows=$('#group_list').datagrid('getChecked');
				if (1==rows.length){
					var row=rows[0];
					var index=$('#group_list').datagrid('getRowIndex',row);
					var curTr=$('#group_list').parent().find('table[class=datagrid-btable]').find('tr[datagrid-row-index='+index+']').slice(0,1);
					$(curTr).click();
				}
			}
			
		}
	});
	
	$('#depart_show_sub_ck').click(function(){
		var checked=$('#depart_show_sub_ck').attr('checked');
		var curSelectNodeId=curSelectDepartNode.id;
		if ('checked'==checked){
			var departStack=new Array();
			getAllSubDepart(departStack, curSelectDepartNode);
			var ids=curSelectNodeId;
			for (var i=0;i<departStack.length;i++){
				var departItem=departStack[i];
				ids+=','+departItem.id;
			}
			$('#user_list').datalist({ 
			    url: 'generalSelectUserController.do?doGetSelectUserByDepartId&isAjax=true',
			    checkbox: false,
			    lines: true,
			    singleSelect:'multi'==selectMode?false:true,
			    textField:'username',
			    valueField:'userid',
			    width:'100%',
			    height:200,
			    loadMsg:'<spring:message code="com.glaway.ids.pm.project.task.pageLoading"/>',
			    queryParams: {
			    	params:$.toJSON(param),'departId':ids
			    },
			    onDblClickRow:function(index, row){
			    	if ('unchecked'==row.checked){
				    	left_to_right(row);
			    	}
			    },
			    onLoadSuccess:function(data){
			    	var rowsSrc=data.rows;
			    	checkUserList(rowsSrc);
			    }
			});
		}else {
			$('#user_list').datalist({ 
			    url: 'generalSelectUserController.do?doGetSelectUserByDepartId&isAjax=true&departId='+curSelectNodeId, 
			    checkbox: false,
			    lines: true,
			    singleSelect:'multi'==selectMode?false:true,
			    textField:'username',
			    valueField:'userid',
			    width:'100%',
			    height:200,
			    loadMsg:'<spring:message code="com.glaway.ids.pm.project.task.pageLoading"/>',
			    queryParams: {
			    	params:$.toJSON(param)
			    },
			    onDblClickRow:function(index, row){
			    	if ('unchecked'==row.checked){
				    	left_to_right(row);
			    	}
			    },
			    onLoadSuccess:function(data){
			    	var rowsSrc=data.rows;
			    	checkUserList(rowsSrc);
			    }
			});
		}
	});
	
	if (getShowOrHidden('depart')){
		$('#dept_radio_div').show();
	}
	if (getShowOrHidden('role')){
		$('#role_radio_div').show();
	}
	if (getShowOrHidden('group')){
		$('#group_radio_div').show();
	}
	
	$('#dept_radio').click(function(){
		radio_onchange();
	});
	$('#role_radio').click(function(){
		radio_onchange();
	});
	$('#group_radio').click(function(){
		radio_onchange();
	});
	
	$('#user_list').datalist({ 
	    checkbox: false,
	    lines: true,
	    singleSelect:true,
	    textField:'username',
	    valueField:'userid',
	    loadMsg:'<spring:message code="com.glaway.ids.pm.project.task.pageLoading"/>',
	    data:[],
	    onDblClickRow:function(index, row){
	    	right_to_left(index, row);
	    }
	});
	
	$('#user_list_select').datalist({ 
	    url: 'generalSelectUserController.do?doGetSelectUser&isAjax=true',
	    checkbox: false,
	    lines: true,
	    singleSelect:false,
	    textField:'username',
	    valueField:'userid',
	    width:'100%',
	    height:200,
	    queryParams: {
			selects:selects
		},
	    onDblClickRow:function(index, row){
	    	right_to_left(index, row);
	    }
	});

	$('input[name=radio_type]').each(function(){
		$(this).removeAttr('checked');
	});
	var firstCode=getFirstRadio();
	if ('depart'==firstCode){
		$('#dept_radio').attr('checked','checked');
		$('#dept_radio').click();
	}else if ('role'==firstCode){
		$('#role_radio').attr('checked','checked');
		$('#role_radio').click();
	}else if ('group'==firstCode){
		$('#group_radio').attr('checked','checked');
		$('#group_radio').click();
	}
	
	
	
});
function selectDepart(){
	var firstCode=getFirstRadio();
	if("depart"!=firstCode||selectCode.length==0){
		return true;
	}
	var treeObj = $.fn.zTree.getZTreeObj("dept_tree");
	// 得到ztree树
	var treeObj = $.fn.zTree.getZTreeObj(ztreeId);
	// 获取全部节点
	var nodes = treeObj.transformToArray(treeObj.getNodes());
	// 查找设置的value
	if (nodes.length > 0) {
		for (var i = 0; i < nodes.length; i++) {
			if (nodes[i][idKey] == selectCode) {
				treeObj.selectNode(nodes[i])
				break;
			}
		}
	}
	return false;

}
function selectRoles(){
	var firstCode=getFirstRadio();
	if("role"!=firstCode||selectCode.length==0){
		return true;
	}
	$("#role_list").datagrid("selectRecord",selectCode);
	var index = $("#role_list").datagrid("getRowIndex",selectCode);
	$("#role_list").scrollTo(index);
	return false;
}
function selectGroup(){
	var firstCode=getFirstRadio();
	if("group"!=firstCode||selectCode.length==0){
		return true;;
	}
	$("#group_list").datagrid("selectRecord",selectCode);
	var index = $("#group_list").datagrid("getRowIndex",selectCode);
	$("#group_list").scrollTo(index);
	return false;
}
function getAllSubDepart(departStack, treeNode){
	if(selectCode.length == 0)return;
	var treeObj = $.fn.zTree.getZTreeObj("dept_tree");
	var nodes = treeObj.getNodesByParam('pid', treeNode.id, treeNode);
	if (undefined!=nodes){
		for (var i=0;i<nodes.length;i++){
			var nodeItem=nodes[i];
			departStack.push(nodeItem);
			
			getAllSubDepart(departStack, nodeItem);
		}
	}
}
function getFirstRadio(){
	if (''==mode||null==mode||undefined==mode){
		mode='211';
		return 'depart';
	}
	var checkCode=mode.indexOf('2');
	if (-1==checkCode){
		return 'null';
	}else if ('0'==checkCode){
		return 'depart';
	}else if ('1'==checkCode){
		return 'role';
	}else if ('2'==checkCode){
		return 'group';
	}
}

function getShowOrHidden(type){
	if (''==mode||null==mode||undefined==mode){
		mode='211';
	}
	if('depart'==type){
		var code=mode.substr(0,1);
		if ('1'==code||'2'==code){
			return true;
		}else if ('0'==code){
			return false;
		}
	}else if ('role'==type){
		var code=mode.substr(1,1);
		if ('1'==code||'2'==code){
			return true;
		}else if ('0'==code){
			return false;
		}
	}else if ('group'==type){
		var code=mode.substr(2,1);
		if ('1'==code||'2'==code){
			return true;
		}else if ('0'==code){
			return false;
		}
	}
}
function northEnterKeyEvent(event) {
	if(event.keyCode==13) {
		$("#btn_north_search").focus();
	}
}
function southEnterKeyEvent(event) {
	if(event.keyCode==13) {
		$("#btn_south_search").focus();
	}
}
function deptTreeFilter(val, nodes){
	var tree_node=new Array();
	for (var i=0;i<nodes.length;i++){
		var node=nodes[i];
		var name=node.name;
		if (-1!=name.indexOf(val)){
			var nodeTemp=new Object();
			nodeTemp.id=node.id;
			nodeTemp.name=node.name;
			nodeTemp.open=node.open;
			nodeTemp.title=node.title;
			nodeTemp.pid=node.pid;
			tree_node.push(nodeTemp);
		}
	}
	$.fn.zTree.init($("#dept_tree"),settingdept_tree,tree_node);
}
function getRatioCheckedVal(){
	var val='';
	$('input[name=radio_type]').each(function(){
		if ($(this).attr('checked')){
			val=$(this).val();
		}	
	});
	return val;
}
function radio_onchange(){
	$('input[name=radio_type]').each(function(){
		if ($(this).attr('checked')){
			$('#depart_show_sub_span').hide();
			
			var val=$(this).val();
			if ('dept'==val){
				$('#dept_div').show();
				$('#dept_div').html('<div id="dept_tree" class="ztree"></div>');
				
				$.post(depart_url,
						function(data) {
							$.fn.zTree.init($("#dept_tree"),settingdept_tree,data);
							dept_data=data;
							var first_node=$('#dept_tree_1_a');
							if (undefined!=first_node && null!=first_node){
								$(first_node).click();
							}
						});
				$('#depart_show_sub_span').show();
			}else if ('role'==val){
				$('#dept_div').show();
				$('#dept_div').html('<table id="role_list"/>');
				
				$('#role_list').datagrid({    
				    url:'generalSelectUserController.do?doRoleGrid&isAjax=true&roleCode='+roleCode,
				    singleSelect:true,
				    fitColumns : true,
				    loadMsg:'<spring:message code="com.glaway.ids.pm.project.task.pageLoading"/>',
				    columns:[[
				        {field:'roleCode',title:'<spring:message code="com.glaway.ids.common.lable.rolecode"/>',width:'50%'},    
				        {field:'roleName',title:'<spring:message code="com.glaway.ids.common.lable.rolename"/>',width:'50%'}
				    ]],
				    idField:'id',
				    width:'100%',
				    height:200,
				    onSelect:function(index, row){
				    	
				    	$('#user_list_div').html('<div id="user_list"></div>');
				    	
				    	$('#user_list').datalist({
				    	    url: 'generalSelectUserController.do?doGetSelectUserByRoleId&isAjax=true&roleId='+row.id+'&handler='+handler+'&key='+key,
				    	    checkbox: false,
				    	    lines: true,
				    	    singleSelect:'multi'==selectMode?false:true,
				    	    textField:'username',
				    	    valueField:'userid',
				    	    loadMsg:'<spring:message code="com.glaway.ids.pm.project.task.pageLoading"/>',
				    	    width:'100%',
				    	    height:200,
				    	    queryParams: {
				    	    	params:$.toJSON(param)
				    	    },
				    	    onDblClickRow:function(index, row){
				    	    	if ('unchecked'==row.checked){
				    		    	left_to_right(row);
				    	    	}
				    	    },
				    	    onLoadSuccess:function(data){
				    	    	var rowsSrc=data.rows;
				    	    	checkUserList(rowsSrc);
				    	    }
				    	});
				    },
		    	    onLoadSuccess:function(data){
		    	    	filterItem();
		    	    	if(selectRoles()){
		    	    		$('#role_list').datagrid('selectRow',0);
		    	    	}
		    	    	
		    	    	
		    	    }
				});
			}else if ('group'==val){
				$('#dept_div').show();
				$('#dept_div').html('<table id="group_list" />');
				
				$('#group_list').datagrid({
				    url:'generalSelectUserController.do?doGroupGrid&isAjax=true&groupCode='+groupCode,
				    singleSelect:true,
				    fitColumns : true,
				    columns:[[
				        {field:'groupCode',title:'<spring:message code="com.glaway.ids.common.lable.groupcode"/>',width:'50%'},    
				        {field:'groupName',title:'<spring:message code="com.glaway.ids.common.lable.groupname"/>',width:'50%'}
				    ]],
				    width:'100%',
				    height:200,
				    idField:'id',
				    onSelect:function(index, row){
				    	
				    	$('#user_list_div').html('<div id="user_list"></div>');
				    	$('#user_list').datalist({ 
				    	    url: 'generalSelectUserController.do?doGetSelectUserByGroupId&isAjax=true&groupId='+row.id+'&handler='+handler+'&key='+key, 
				    	    checkbox: false,
				    	    lines: true,
				    	    singleSelect:'multi'==selectMode?false:true,
				    	    textField:'username',
				    	    valueField:'userid',
				    	    loadMsg:'<spring:message code="com.glaway.ids.pm.project.task.pageLoading"/>',
				    	    width:'100%',
				    	    height:200,
				    	    queryParams: {
				    	    	params:$.toJSON(param)
				    	    },
				    	    onDblClickRow:function(index, row){
				    	    	if ('unchecked'==row.checked){
				    		    	left_to_right(row);
				    	    	}
				    	    },
				    	    onLoadSuccess:function(data){
				    	    	var rowsSrc=data.rows;
				    	    	checkUserList(rowsSrc);
				    	    }
				    	});
				    },
		    	    onLoadSuccess:function(data){
		    	    	filterItem();
						if(selectGroup()){
							$('#group_list').datagrid('selectRow',0);
						}
		    	    	
		    	    }
				});
			}
		}
	});
	
	var rows=$('#user_list').datalist('getRows');
	for (var i=rows.length-1;i>=0;i--){
		var row=rows[i];
		var index=$('#user_list').datalist('getRowIndex',row);
		$('#user_list').datalist('deleteRow',index);
	}
}
function departTreeClick(event, treeId, treeNode) {
	
	curSelectDepartNode=treeNode;
	
	$('#user_list_div').html('<div id="user_list"></div>');
	
	var checked=$('#depart_show_sub_ck').attr('checked');
	var curSelectNodeId=curSelectDepartNode.id;
	var ids=curSelectNodeId;
	if ('checked'==checked){
		var departStack=new Array();
		getAllSubDepart(departStack, curSelectDepartNode);
		for (var i=0;i<departStack.length;i++){
			var departItem=departStack[i];
			ids+=','+departItem.id;
		}
	}
	
	$('#user_list').datalist({ 
	    url: 'generalSelectUserController.do?doGetSelectUserByDepartId&isAjax=true', 
	    checkbox: false,
	    lines: true,
	    singleSelect:'multi'==selectMode?false:true,
	    textField:'username',
	    valueField:'userid',
	    loadMsg:'<spring:message code="com.glaway.ids.pm.project.task.pageLoading"/>',
	    width:'100%',
	    height:200,
	    queryParams: {
	    	params:$.toJSON(param),'departId':ids
	    },
	    onDblClickRow:function(index, row){
	    	if ('unchecked'==row.checked){
		    	left_to_right(row);
	    	}
	    },
	    onLoadSuccess:function(data){
	    	var rowsSrc=data.rows;
	    	checkUserList(rowsSrc);
	    }
	});
}
function filterItem(){
	if (bSearch){
		var val=$('#north_search_input').val();
		var type=getRatioCheckedVal();
		if ('dept'==type){
			
		}else if ('role'==type){
			var rows=$('#role_list').datalist('getRows');
			for (var i=rows.length-1;i>=0;i--){
				var row=rows[i];
				var roleCode=row.roleCode;
				var roleName=row.roleName;
				if (-1==roleCode.indexOf(val) && -1==roleName.indexOf(val)){
					$('#role_list').datalist('deleteRow',i);
				}
			}
		}else if ('group'==type){
			var rows=$('#group_list').datalist('getRows');
			for (var i=rows.length-1;i>=0;i--){
				var row=rows[i];
				var groupCode=row.groupCode;
				var groupName=row.groupName;
				if (-1==groupCode.indexOf(val) && -1==groupName.indexOf(val)){
					$('#group_list').datalist('deleteRow',i);
				}
			}
		}
		
		bSearch=false;
	}
}
function checkUserList(rowsSrc){
	for (var i=0;i<rowsSrc.length;i++){
		var rowSrc=rowsSrc[i];
		var idSrc=rowSrc.userid;
		
		var rows=$('#user_list_select').datalist('getRows');
    	for (var j=0;j<rows.length;j++){
    		var row=rows[j];
    		var id=row.userid;
    		if (idSrc==id){
    			var index=$('#user_list').datalist('getRowIndex',rowSrc);
    			if (-1!=index){
    				$('#user_list').datalist('updateRow',{
    					index:index,
    					row:{
    						userid:rowSrc.userid,
    						username:rowSrc.username,
    						checked:'checked'
    					}
    				});
	    			$('#user_list').parent().find('div[class=datagrid-body]').find('tr[datagrid-row-index='+index+']').addClass('disable_backcolor');
    			}
    			break;
    		}
    	}
	}

	if (bSearch){
		var val=$('#south_search_input').val();
		var rows=$('#user_list').datalist('getRows');
		for (var i=rows.length-1;i>=0;i--){
			var row=rows[i];
			var username=row.username;
			if (-1==username.indexOf(val)){
				$('#user_list').datalist('deleteRow',i);
			}
		}
		bSearch = false;
	}
}
function left_to_right(row){
	if ('single'==selectMode){
		
		var rowsSelect=$('#user_list_select').datalist('getRows');
		for (var i=rowsSelect.length-1;i>=0;i--){
			var rowTemp=rowsSelect[i];
			var index=$('#user_list_select').datalist('getRowIndex',rowTemp);
			$('#user_list_select').datalist('deleteRow',index);
		}
		
		var rows=$('#user_list').datalist('getRows');
		for (var i=rows.length-1;i>=0;i--){
			var rowTemp=rows[i];
			
			var index2=$('#user_list').datalist('getRowIndex',rowTemp);
			if (-1!=index2){
				$('#user_list').datalist('updateRow',{
					index:index2,
					row:{
						userid:rowTemp.userid,
						username:rowTemp.username,
						checked:'unchecked',
						name:rowTemp.name,
						realName:rowTemp.realName
					}
				});
				
				var trObj=$('#user_list').parent().find('div[class=datagrid-body]').find('tr[datagrid-row-index='+index2+']');
				$(trObj).removeClass('disable_backcolor');
			}
		}
		
		var index2=$('#user_list').datalist('getRowIndex',row);
		if (-1!=index2){
			$('#user_list').datalist('updateRow',{
				index:index2,
				row:{
					userid:row.userid,
					username:row.username,
					checked:'checked',
					name:row.name,
					realName:row.realName
				}
			});
			
			var trObj=$('#user_list').parent().find('div[class=datagrid-body]').find('tr[datagrid-row-index='+index2+']');
			$(trObj).addClass('disable_backcolor');
		}
		
		$('#user_list_select').datalist('appendRow',{
			username:row.username,
			userid:row.userid,
			name:row.name,
			realName:row.realName
		});
		
	}else if ('multi'==selectMode){
		$('#user_list_select').datalist('appendRow',{
			username:row.username,
			userid:row.userid,
			name:row.name,
			realName:row.realName
		});
		
		var index2=$('#user_list').datalist('getRowIndex',row);
		if (-1!=index2){
			$('#user_list').datalist('updateRow',{
				index:index2,
				row:{
					userid:row.userid,
					username:row.username,
					checked:'checked',
					name:row.name,
					realName:row.realName
				}
			});
			
			var trObj=$('#user_list').parent().find('div[class=datagrid-body]').find('tr[datagrid-row-index='+index2+']');
			$(trObj).addClass('disable_backcolor');
		}
	}
}
function right_to_left(index, row){
	var userid=row.userid;
	var username=row.username;
	var name=row.name;
	var realName=row.realName;
	var rows=$('#user_list').datalist('getRows');
	for (var i=0;i<rows.length;i++){
		var rowTemp=rows[i];
		if (userid==rowTemp.userid){
			var index2=$('#user_list').datalist('getRowIndex',rowTemp);
			if (-1!=index2){
				$('#user_list').datalist('updateRow',{
					index:index2,
					row:{
						userid:userid,
						username:username,
						checked:'unchecked',
						name:name,
						realName:realName
					}
				});
				$('#user_list').parent().find('div[class=datagrid-body]').find('tr[datagrid-row-index='+index2+']').removeClass('disable_backcolor');
				break;
			}
		}
	}
	
	$('#user_list_select').datalist('deleteRow',index);
}
function getSelectUsers(){
	$.ajax({
		url:'generalSelectUserController.do?doDeleteParamCache&key='+key,
		context:document.body,
		dataType:"json",
		async:false,
		success:function(data){
			
		}
	});
	
	var ids='';
	var rows=$('#user_list_select').datalist('getRows');
	for (var i=0;i<rows.length;i++){
		var row=rows[i];
		if (i==rows.length-1){
			ids=ids+row.userid+':'+row.username+':'+row.name+':'+row.realName;
		}else {
			ids+=row.userid+':'+row.username+':'+row.name+':'+row.realName+',';
		}
	}
	if (''==ids){
		return ':::';
	}
	return ids;
}
function checkNSearch(){	
	var val=$('#north_search_input').val();	
	if (''==val||undefined==val){		
	}else{
		
		$('#north_search_input').val($.trim(val));			
	}
}
function checkSSearch(){	
	var val=$('#south_search_input').val();	
	if (''==val||undefined==val){		
	}else{		
		$('#south_search_input').val($.trim(val));			
	}
}

</script>
<style type="text/css">
.disable_backcolor{
	color:#AAAAB0;
}
.datalist div{
	border:0 !important;
}
.datagrid-cell-c2-username{
	width:1000px !important;
}
</style>
</head>
<body>
<div class="easyui-layout" style="width:100%;" fit="true">
	<div region="west" style="padding: 1px;width:48%;border:0;" title="" >
		<div class="easyui-layout" fit="true" >
			<div region="north" style="padding: 1px;height:50%;" title="<spring:message code='com.glaway.ids.pm.project.task.tobeSelect'/>" collapsible="false">
				<div class="easyui-layout" fit="true">
					<div region="north" id="north_tool_div" style="background-color: rgb(227,227,227);height:32px;">
						<span style="float:left;display:none;" id="dept_radio_div">
							<input id="dept_radio" name="radio_type" type="radio" value="dept" checked="checked"/>
							<span><spring:message code="com.glaway.ids.common.lable.department"/></span>
						</span>
						<span style="float:left;display:none;" id="role_radio_div">
							<input id="role_radio" name="radio_type" type="radio" value="role"/>
							<span><spring:message code="com.glaway.ids.common.lable.role"/></span>
						</span>
						<span style="float:left;display:none;" id="group_radio_div">
							<input id="group_radio" name="radio_type" type="radio" value="group"/>
							<span><spring:message code="com.glaway.ids.common.lable.group"/></span>
						</span>
						<span style="float:right;">
							<input id="north_search_input" onkeydown="northEnterKeyEvent(event);" style="vertical-align:middle;" onblur="checkNSearch()"/>
							<a class="easyui-linkbutton button_nor_search  button_nor_searchClick" data-options="plain:true" id="btn_north_search"><spring:message code="com.glaway.ids.common.btn.search"/></a>
						</span>
					</div>
					<div region="center" style="border:0;">
						<div id="dept_div" style="border:0;">
							<div id="dept_tree" class="ztree"></div>
						</div>
					</div>
				</div>
			</div>
			
			<div region="south" style="padding: 0px;height:50%;border:0;" title="">
				<div class="easyui-layout" fit="true">
					<div region="north" style="text-align:right;background-color: rgb(227,227,227);width:100%;height:30px;border:0;">
						<span id="depart_show_sub_span" style="display:none;" >
							<input id="depart_show_sub_ck" type="checkbox" style="vertical-align:middle;" />
							<span style="margin-right:20px;"><spring:message code="com.glaway.ids.common.lable.showAll"/></span>
						</span>
						<span><spring:message code="com.glaway.ids.common.lable.username"/></span>
						<input id="south_search_input" onkeydown="southEnterKeyEvent(event);" style="vertical-align:middle;" onblur="checkSSearch()"/>
						<a class="easyui-linkbutton button_nor_search  button_nor_searchClick" data-options="plain:true" id="btn_south_search"><spring:message code="com.glaway.ids.common.btn.search"/></a>
					</div>
					<div id="user_list_div" region="center" style="padding: 1px;width:4%;broder:0;" title="" >
						<div id="user_list"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div region="center" style="padding: 1px;width:4%;text-align:center;" title="" >
		<div style="height:45%;">
		</div>
		<div>
			<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain" group="" id="left_to_right">
				<span class="l-btn-left l-btn-icon-left">
					<span class="l-btn-text l-btn-empty">&nbsp;</span>
					<span class="l-btn-icon pagination-next">&nbsp;</span>
				</span>
			</a>
		</div>
		<div>
			<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain" group="" id="right_to_left">
				<span class="l-btn-left l-btn-icon-left">
					<span class="l-btn-text l-btn-empty">&nbsp;</span>
					<span class="l-btn-icon pagination-prev">&nbsp;</span>
				</span>
			</a>
		</div>
	</div>
	<div region="east" style="padding: 1px;width:48%;" title="<spring:message code='com.glaway.ids.common.lable.username'/>" collapsible="false">
		<div id="user_list_select"></div>
	</div>
</div>

</body>
</html>





