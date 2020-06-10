<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<title>修改</title>
<t:base type="jquery,easyui,tools"></t:base>
<script>
	
function closeCurDialog(){
	//解决系统卡点击多次确定按钮会保存多条数据
	window.top.progress("open");
	
	var rows = $("#repFileTypeProcessList").datagrid('getRows');
	var ids = [];
	debugger;
		 for (var i = 0; i < rows.length; i++) {
             ids.push(rows[i].id);
         }
		 var fileTypeName = $("#fileTypeName").val();
		 $.ajax({
             url : encodeURI('repFileTypeConfigController.do?doDeployRepFileTypeTaskFlow'),
             type : 'post',
             data : {
                 ids : ids.join(','),
                 repFileTypeId : '${repFileTypeId}'
             },
             cache : false,
             success : function(data) {  
             	var d = $.parseJSON(data);
                tip(d.msg);
                window.top.progress("close");
                if(d.success){
                	$("#repFileTypeProcessList").datagrid('unselectAll');
                    var win = $.fn.lhgdialog("getSelectParentWin");
                 	win.reloadRepFileTypeConfigList();
                 	$.fn.lhgdialog("closeSelect");
                }
               
             }
         });
}

</script>
<body style="overflow-x: hidden;">
	<input type="hidden" id="fileTypeName" name="fileTypeName" value="${fileTypeName }">
	<input type="hidden" id="repFileTypeId" name="repFileTypeId" value="${repFileTypeId }">
	<div class="easyui-layout" fit="true"> 
		 <div data-options="region:'north',title:''" style="height:95%;">
	    	<fd:tabs id="repFileTypeConfigSetTabs" tabPosition="top" fit="true" >
	    		<fd:tab href="repFileTypeConfigController.do?goApproveProcessTab&repFileTypeId=${repFileTypeId}" 
					title="{com.glaway.ids.pm.config.repFileTypeConfig.approveProcess}" id="approveProcessTab"></fd:tab>
				<fd:tab href="repFileTypeConfigController.do?goCustomAttributeTab&repFileTypeId=${repFileTypeId}&entityUri=${entityUri}&fieldName=${repFileTypeId}&fieldValue=${repFileTypeId}" 
					title="{com.glaway.ids.pm.config.repFileTypeConfig.customAttribute}" id="customAttributeTab"></fd:tab>
	    	</fd:tabs>
    	</div>   
	</div>
</body>