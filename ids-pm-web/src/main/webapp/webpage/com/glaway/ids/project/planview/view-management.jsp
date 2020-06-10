<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>管理视图</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body >
<div class="easyui-layout" fit="true">
    <input type="hidden" id ='projectIds' name='projectIds'/>
    <input type="hidden" id ='userIds' name='userIds'/>
    <input type="hidden" id ='planViewInfoId' name='planViewInfoId'/>
    <input type="hidden" id ='planViewName' name='planViewName'/>
    <div region="west" style="padding: 1px; width: 550px" title="视图列表" id="left_page_panel">
        <fd:datagrid id="planViewList" idField="id" fit="true"
                fitColumns="true" pagination="false" 
                onDblClickRow="onDoubleClickRow"
                onClickFunName="viewNameOnChange"
                url="planViewController.do?searchDatagrid&projectId=${projectId}">
                <fd:colOpt title="{com.glaway.ids.common.lable.operation}"
                    width="60">
                    <fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.planview.publish}"
                        iconCls="basis ui-icon-submitted_approval" onClick="goPublishOrCancelView"
                        hideOption="hidePublishButton"></fd:colOptBtn>
                    <fd:colOptBtn tipTitle="{com.glaway.ids.pm.project.planview.cancelPublish}"
	                    iconCls="basis ui-icon-closethick" onClick="goPublishOrCancelView"
	                    hideOption="hideCancelButton"></fd:colOptBtn>
                    <fd:colOptBtn tipTitle="{com.glaway.ids.common.btn.remove}"
                        iconCls="basis ui-icon-minus" onClick="deleteView"
                        hideOption="hideDeleteButton"></fd:colOptBtn>
                </fd:colOpt>
                <fd:dgCol title="{com.glaway.ids.pm.project.planview.name}"
                    field="name" width="130" editor="{type:'text'}"/>
                <fd:dgCol title="{com.glaway.ids.pm.project.planview.status}"
                    field="status" width="70" formatterFunName="showStatus"/>
                <fd:dgCol title="{com.glaway.ids.pm.project.planview.publishPerson}"
                    field="publishPersonName" width="65" />
                <fd:dgCol title="{com.glaway.ids.pm.project.planview.publishDept}"
                    field="publishDeptName" width="60" />
        </fd:datagrid>
    </div>
    <div region="center" style="margin-left: 10px;">
        <fd:toolbar help="helpDoc:ViewManagement"></fd:toolbar>
        <div class="easyui-panel" title="视图展示条件" fit="true" border="false" id="right_page_panel"></div>
    </div>
</div>
<fd:dialog id="publishViewDialog" width="750px" height="400px" beforClose="refreshByPublish"
        modal="true" title="{com.glaway.ids.pm.project.planview.publish}">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="publishViewOk"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
</body>
<script type="text/javascript">
	function showStatus(val, row, index) {
	    if (val != undefined && val != null && val != '') {
	        var status = row.status;
	        if("PUBLIC" == status) {
	        	return '已发布';
	        } else if("PRIVATE" == status){
	        	return '未发布';
	        }	       
	    }
	}

	// 修改视图名称
    var editIndex = undefined;
    var currentIndex = undefined;
    var changeLink = false;
    function endEditing(){
        if (editIndex == undefined){
            return true
            }
        if ($('#planViewList').datagrid('validateRow', editIndex)){
            $('#planViewList').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    
    // 修改视图名称
    function onDoubleClickRow(index) {
		debugger
        var rows = $('#planViewList').datagrid('getRows');
        var row = rows[index];
        if (currentIndex != undefined && !changeLink){
        	viewNameOnChange();
        }        
        currentIndex = index;
        changeLink = false;
        if (editIndex != index) {
            if (endEditing()) {
            	if(row.createBy == '${currentUserId}') {
            		$('#planViewList').datagrid('selectRow', index).datagrid(
                            'beginEdit', index);
                    editIndex = index;	
            	}
            } else {
                $('#planViewList').datagrid('selectRow', editIndex);
            }
        }
    }
    
    function viewNameOnChange(){
    	loadUrl();
        var rows = $('#planViewList').datagrid('getRows');
        var row = rows[currentIndex];
        var oldValue = row.name;
//         alert(row.name);
        $('#planViewList').datagrid('endEdit', currentIndex);
        var val = $.trim(rows[currentIndex].name);
        changeLink = true;
        editIndex = undefined;
        if(val == ''){
            top.tip('<spring:message code="com.glaway.ids.pm.project.planview.nameCannotEmpty"/>');
			$('#planViewList').datagrid('beginEdit', currentIndex);
			var ed = $('#planViewList').datagrid("getEditor",{index:currentIndex,field:'name'});
			$(ed.target).val(oldValue);
			$('#planViewList').datagrid('endEdit', currentIndex);
            return false;
        }
        if(val != null && val != '' && val != undefined){
            $.ajax({
                url : 'planViewController.do?doSaveName',
                type : 'post',
                data : {
                	 id : row.id,
                     name : val,
                     status : row.status
                },
                cache : false,
                success : function(data) {
                	var d = $.parseJSON(data);
                	if(d.success) {          
                		currentIndex = undefined;
                		refreshView(row.id, val, 'update');
                	} else {
                		top.tip(d.msg);        
                		$('#planViewList').datagrid('beginEdit', currentIndex);
                	}
          
                }
            });
        }
    }
    
    //加载右边视图查询条件
    function loadUrl() {
    	var row = $("#planViewList").datagrid('getSelected');
        loadPage("#right_page_panel", 'planViewController.do?goViewSearchInfo&planViewInfoId='+row.id, '');
        jeasyui.util.panelMask('right_page_panel','open','视图展示条件');
        setTimeout(function() {
            jeasyui.util.panelMask('right_page_panel','close');
        }, 1000);
    }
    
    function goPublishOrCancelView(id, index) {
    	var rows = $('#planViewList').datagrid('getRows')
    	var viewName = rows[index].name;
    	var status = rows[index].status;
    	if(status == 'PRIVATE') {
    		var url = 'planViewController.do?goPublishView&planViewInfoId='+id+'&viewName='+encodeURI(viewName);
            createDialog('publishViewDialog', url);
    	} else {
    		top.Alert.confirm(
    	             '<spring:message code="com.glaway.ids.pm.project.planview.confirmCancelPublish"/>',
    	             function(r) {
    	                 if (r) {
    	                     $.ajax({
    	                         url : 'planViewController.do?doCancelPublishView',
    	                         type : 'post',
    	                         data : {
    	                             id : id,
    	                             name : viewName
    	                         },
    	                         cache : false,
    	                         success : function(data) {
    	                             var d = $.parseJSON(data);
    	                             top.tip(d.msg);
    	                             if (d.success) {
    	                                 refreshView(id, name, 'cancel');  
    	                             }
    	                         }
    	                     });
    	                 }
    	        });
    	}   	
    }
    
    function deleteView(id, index) {
   	    top.Alert.confirm(
             '<spring:message code="com.glaway.ids.common.confirmDel"/>',
             function(r) {
                 if (r) {
                	 $.ajax({
                         url : 'planViewController.do?doDeleteView',
                         type : 'post',
                         data : {
                             id : id
                         },
                         cache : false,
                         success : function(data) {
                             var d = $.parseJSON(data);
                             top.tip(d.msg);
                             if (d.success) {
                            	 $('#planViewList').datagrid("reload");
                            	 $('#right_page_panel').empty();
                            	 /* var rows = $('#planViewList').datagrid('getRows');
                                 var name = rows[index].name;
                                 refreshView(id, name, 'delete');   */
                             }
                         }
                     });
                 }
        });
    }
    
    function refreshView(id, name, type) {
        $('#planViewList').datagrid("reload");
        win = $.fn.lhgdialog("getSelectParentWin");
        win.refreshViewTree(id, name, type);
    }
    
    function publishViewOk(iframe) {
    	iframe.publishView();
    	return false;
    }
    
    function hidePublishButton(id,index){
        var row = $("#planViewList").datagrid("getRows")[index];
        if(row.createBy == '${currentUserId}' && row.status == '<spring:message code="com.glaway.ids.pm.project.planview.status.private"/>'){
        	return false;
        }
        return true;
    }
    
    function hideCancelButton(id,index){
        var row = $("#planViewList").datagrid("getRows")[index];
        if(row.createBy == '${currentUserId}' && row.status == '<spring:message code="com.glaway.ids.pm.project.planview.status.public"/>'){
            return false;
        }
        return true;
    }
    
    function hideDeleteButton(id,index){
        var row = $("#planViewList").datagrid("getRows")[index];
        if(row.createBy == '${currentUserId}'){
            return false;
        }
        return true;
    }
    
    /*刷新页面所需要的值*/
    function setRefreshValue(planViewInfoId, name , projectIds, userIds) {
        $('#planViewInfoId').val(planViewInfoId);
        $('#planViewName').val(name);
        $('#projectIds').val(projectIds);
        $('#userIds').val(userIds);
        
    }
    
    function refreshByPublish() {
        var projectIds = $('#projectIds').val();
        var userIds = $('#userIds').val();
        var planViewInfoId = $('#planViewInfoId').val();
        var name = $('#planViewName').val();
        var win = $.fn.lhgdialog("getSelectParentWin");
        win.refreshViewTreeByPublish(projectIds, userIds, planViewInfoId, name);
    }
</script>

</html>
