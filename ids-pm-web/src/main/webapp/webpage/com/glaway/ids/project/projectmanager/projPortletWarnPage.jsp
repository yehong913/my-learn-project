<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<head>
	<t:base type="jquery,easyui,tools,lhgdialog"></t:base>
</head>
<body>
	<style>
		.select_dd{width:150px;}
		.select_dde{width:150px}
		.search_title{margin-top:5px}
	</style>
	<script type="text/javascript">
	$(document).ready(
		function() {
			var projectIds ="${selectedValue}";
			var projectWarnIframe = document.getElementById("projectPortletWarnId");
            var projectArray = projectIds.split(',');
			var str = "";
		    for (var i=0;i<projectArray.length;i++) {
		    	str+="'" + projectArray[i] + "'";
		    	if (i!=projectArray.length-1) {
		    		str += ",";
		    	}
		    } 
		    if (str != "") {		    	
		    	setTimeout(function() {
		    		projectWarnIframe.src="/brs/preview?__report=idsreport/projectportletwarn.rptdesign&__svg=false&idArr="+str;
	            }, 100);
		    }		    
		}); 
	
	function showProjWarnReport() {
		var projectIds = $('#portletProjectIds').combobox('getValues');
		var projectWarnIframe = document.getElementById("projectPortletWarnId");
		if (projectIds=="") {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.mustSelectOne"/>');
			return;
		}
		if (projectIds.length > 10) {
			tip('<spring:message code="com.glaway.ids.pm.project.projectmanager.mostSelectTen"/>');
			return;
		}
		else {
			$.ajax({
				url : "projStatisticsController.do?ProjWarnPage&projectIds="+projectIds ,
				type : 'post',  
				async : false,
				success :function() {
				}
			});
			
			var str = "";
		    for (var i=0;i<projectIds.length;i++) {
		    	str+="'" + projectIds[i] + "'";
		    	if (i!=projectIds.length-1) {
		    		str+= ",";
		    	}
		    } 
		    projectWarnIframe.src="/brs/preview?__report=idsreport/projectportletwarn.rptdesign&__svg=false&idArr="+str;
		}
	}
	</script>
	
	<div id="tool125" fit="true">	
		<fd:toolbar>
			<fd:toolbarGroup align="right">
				<div style="margin:8px 8px 4px">
					<fd:combobox id="portletProjectIds" title="{com.glaway.ids.pm.project.projectmanager.auth.selectProject}" 
						panelMaxHeight="200" textField="id" multiple="true" selectedValue="${selectedValue}" 
						valueField="text" url="projStatisticsController.do?getSelectData&type=1"  
						classStyle="select_dde" spanCss="select_dd"></fd:combobox>						
					<fd:linkbutton id ="confirm" onclick="showProjWarnReport()" value="{com.glaway.ids.common.btn.confirm}" 
						iconCls="basis ui-icon-right" plain="true" />
				</div>
			</fd:toolbarGroup>
		</fd:toolbar>
	</div>
	<div>
		<iframe id="projectPortletWarnId" width="100%" height="319px" scrolling="auto" src="" frameborder="0"></iframe>
	</div>
</body>

