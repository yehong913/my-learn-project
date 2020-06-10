<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>按部门设置</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
     function saveSetConditionByDept() {
    	 var name = $("#viewName").val();
    	 var deptId = $("#user_depart").combobox("getValue");
         var isSwitch = $('#isSwitch').selectBooleanCheckbox('getValue');
         if (name == null || name == undefined || name == '') {
             top.tip('<spring:message code="com.glaway.ids.pm.project.planview.nameCannotEmpty"/>');
             return;
         }
         if (deptId == null || deptId == undefined || deptId == '') {
             top.tip('<spring:message code="com.glaway.ids.pm.project.planview.departmentCannotEmpty"/>');
             return;
         }
         $.ajax({
             url : 'planViewController.do?doSaveSetByDept',
             type : 'post',
             data : {
            	 name : name,
            	 deptId : deptId
             },
             cache : false,
             success : function(data) {
            	 debugger;
                 var d = $.parseJSON(data);
                 var win = $.fn.lhgdialog("getSelectParentWin");
                 top.tip(d.msg);
                 win.$('#setPlanView').combobox('setValue', '');
                 if (d.success) {
                	 //刷新视图            
                	 win.switchPlanTreeReload(isSwitch,d.obj,name);
//                      if(isSwitch) {
//                     	 win.$('#switchPlanView').comboztree('setValue', d.obj);
//                     	 win.$('#switchPlanView').comboztree('setText', name);
//                     	 //切换视图
//                     	// win.switchPlanList();
//                      }
                     $.fn.lhgdialog("closeSelect");
                 }
             }
         });
     }
</script>
</head>
<body>
    <fd:toolbar help="helpDoc:ViewAccordingDepartment"></fd:toolbar>
	<div class="easyui-layout">
		<fd:inputText id="viewName" name="viewName" title="{com.glaway.ids.pm.project.planview.name}" required="true" />
		<fd:combotree url="planViewController.do?dpeartCombotree" required="true"
            treePidKey="pid" id="user_depart" treeIdKey="id" editable="false"
            treeName="name" title="{com.glaway.foundation.user.userDepart}"
            onHidePanel="deptChange" multiple="true" panelHeight="250"/>
        <fd:selectBooleanCheckbox id="isSwitch" title="{com.glaway.ids.pm.project.planview.switch}" value="true">
        </fd:selectBooleanCheckbox>           
	</div>
</body>
</html>
