<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>发布视图</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
     function selectProject() {
    	 var url = 'planViewController.do?goSelectProject';
    	 createDialog('selectProjectDialog', url);
     }
     
     function setProject(iframe) {
    	 iframe.getProjectInfos();       
         return false;
     }
     
     function publishView() {
    	 var planViewInfoId = $("#planViewInfoId").val();
    	 var projectIds = $("#projectIds").val();
    	 var userIds = $('#selectUserIdHidden_users').val();
    	 var name = $("#viewName").textbox("getValue");
    	 if(name == '' || name == null){
    		 top.tip("视图名称为空");
    		 return false;
    	 }
    	 $.ajax({
             url : 'planViewController.do?doPublishView',
             type : 'post',
             data : {
                 planViewInfoId : planViewInfoId,
                 projectIds : projectIds,
                 userIds : userIds,
                 name : name
             },
             cache : false,
             success : function(data) {
                 var d = $.parseJSON(data);
                 top.tip(d.msg);
                 if (d.success) {
                	 var win = $.fn.lhgdialog("getSelectParentWin");               	 
                	 win.$('#planViewList').datagrid("reload");
                	 win.setRefreshValue(planViewInfoId, name , projectIds, userIds);
                     $.fn.lhgdialog("closeSelect");                            
                 }
             }
         });
     }
 
</script>
</head>
<body>
	<div class="easyui-layout">
	    <input type="hidden" id ='planViewInfoId' name='planViewInfoId' value="${planViewInfoId}"/>
		<fd:inputText id="viewName" name="viewName" title="{com.glaway.ids.pm.project.planview.name}" value="${viewName}" required="true"/>
		<fd:inputSearchUser id="users" name="users"
                title="{com.glaway.ids.pm.project.planview.users}"
                multiple="true" maxLength="-1"
                roleInputVal="${codeId}" prompt="全部">
        </fd:inputSearchUser>
		<fd:inputSearch id="projectNames" title="{com.glaway.ids.pm.project.planview.projects}" editable="false" searcher="selectProject()" 
		        prompt="全部"/>
		<input name="projectIds" id="projectIds" type="hidden">
	</div>
</body>
<fd:dialog id="selectProjectDialog" width="900px" height="400px" 
        modal="true" title="项目">
    <fd:dialogbutton name="{com.glaway.ids.common.btn.confirm}" callback="setProject"></fd:dialogbutton>
    <fd:dialogbutton name="{com.glaway.ids.common.btn.cancel}" callback="hideDiaLog"></fd:dialogbutton>
</fd:dialog>
</html>
